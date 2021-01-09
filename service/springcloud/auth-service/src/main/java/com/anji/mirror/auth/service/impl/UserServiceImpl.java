package com.anji.mirror.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anji.mirror.auth.domain.po.OrgPO;
import com.anji.mirror.auth.domain.po.UserPO;
import com.anji.mirror.auth.domain.po.UserRoleOrgPO;
import com.anji.mirror.auth.domain.vo.RoleOrgVO;
import com.anji.mirror.auth.domain.vo.SettingVO;
import com.anji.mirror.auth.domain.vo.StringParamVO;
import com.anji.mirror.auth.domain.vo.UserVO;
import com.anji.mirror.common.enums.DeleteFlagEnum;
import com.anji.mirror.common.enums.EnableFlagEnum;
import com.anji.mirror.auth.mapper.OrgMapper;
import com.anji.mirror.auth.mapper.RoleOrgMapper;
import com.anji.mirror.auth.mapper.UserMapper;
import com.anji.mirror.auth.mapper.UserRoleOrgMapper;
import com.anji.mirror.auth.service.MenuService;
import com.anji.mirror.auth.service.SettingService;
import com.anji.mirror.auth.service.UserService;
import com.anji.mirror.auth.utils.JwtTokenUtil;
import com.anji.mirror.common.enums.RepCodeEnum;
import com.anji.mirror.common.model.OptionVO;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.model.TreeVO;
import com.anji.mirror.common.security.Authentication;
import com.anji.mirror.common.security.Constant;
import com.anji.mirror.common.service.RedisService;
import com.anji.mirror.common.utils.AESUtil;
import com.anji.mirror.common.utils.BeanUtils;
import com.anji.mirror.common.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@Service
@RefreshScope
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserRoleOrgMapper userRoleOrgMapper;

    @Autowired
    private OrgMapper orgMapper;

    @Autowired
    private RoleOrgMapper roleOrgMapper;

    @Value("${customer.user.login.tokenExpiredSecond:2592000}")
    private Long tokenExpiredSecond;

    //开启行为验证码二次校验
    @Value("${aj.captcha.login.second.check:true}")
    private boolean captchaSecondCheck;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private SettingService settingService;

    /**
     * 根据数据库必填项，校验是否为空，不校验主键
     *
     * @param userVO
     * @return
     */
    private ResponseModel validateCreateFieldNotNull(UserVO userVO) {
        if (userVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        if (StringUtils.isBlank(userVO.getLoginName())) {
            return RepCodeEnum.NULL_ERROR.parseError("loginName");
        }
        if (StringUtils.isBlank(userVO.getPassword())) {
            return RepCodeEnum.NULL_ERROR.parseError("password");
        }
        if (StringUtils.isBlank(userVO.getRealName())) {
            return RepCodeEnum.NULL_ERROR.parseError("realName");
        }
        /* 该片段由生成器产生，请根据实际情况修改
        if(userVO.getUserId() == null){
            return RepCodeEnum.NULL_ERROR.parseError("userId");
        }
        if(StringUtils.isBlank(userVO.getPhone())){
            return RepCodeEnum.NULL_ERROR.parseError("phone");
        }
        if(StringUtils.isBlank(userVO.getEmail())){
            return RepCodeEnum.NULL_ERROR.parseError("email");
        }
        if(userVO.getDeleteFlag() == null){
            return RepCodeEnum.NULL_ERROR.parseError("deleteFlag");
        }
        if(StringUtils.isBlank(userVO.getRemark())){
            return RepCodeEnum.NULL_ERROR.parseError("remark");
        }
        if(StringUtils.isBlank(userVO.getRecommender())){
            return RepCodeEnum.NULL_ERROR.parseError("recommender");
        }
        if(userVO.getLastLoginTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("lastLoginTime");
        }
        if(StringUtils.isBlank(userVO.getLastLoginIp())){
            return RepCodeEnum.NULL_ERROR.parseError("lastLoginIp");
        }
        if(StringUtils.isBlank(userVO.getCreatedBy())){
            return RepCodeEnum.NULL_ERROR.parseError("createdBy");
        }
        if(userVO.getCreatedTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("createdTime");
        }
        if(StringUtils.isBlank(userVO.getUpdatedBy())){
            return RepCodeEnum.NULL_ERROR.parseError("updatedBy");
        }
        if(userVO.getUpdatedTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("updatedTime");
        }
        */
        return ResponseModel.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel create(RequestModel<UserVO> requestModel) {
        //参数校验
        UserVO userVO = requestModel.getData();
        ResponseModel valid = validateCreateFieldNotNull(userVO);
        if (valid.isError()) {
            return valid;
        }
        //业务校验
        //判断用户名是否存在
        QueryWrapper<UserPO> selectMapper = new QueryWrapper<>();
        selectMapper.eq("login_name", userVO.getLoginName());
        List<UserPO> poList = userMapper.selectList(selectMapper);
        if (poList.size()>0){
            return RepCodeEnum.EXIST_ERROR.parseError("登录名");
        }

        //业务处理
        String operator = requestModel.getOpUserName();
        if (userVO.getEnableFlag() == null) {
            userVO.setEnableFlag(EnableFlagEnum.ENABLE.getCodeValue());
        }

        if (StringUtils.isNotBlank(userVO.getPassword()) && StringUtils.isNotBlank(userVO.getConfirmPassword())) {
            String password = userVO.getPassword();
            String confirmPassword = userVO.getConfirmPassword();
            if (!password.equals(confirmPassword)) {
                return ResponseModel.fail(RepCodeEnum.USER_INCONSISTENT_PASSWORD_ERROR);
            }

            try {
                password = AESUtil.aesDecrypt(password, null);
            } catch (Exception e) {
                return ResponseModel.fail(RepCodeEnum.USER_PASSWORD_DECRYPTION_FAILED_ERROR);
            }
            if(password.length() < 6 || password.length() > 16 ){
                return ResponseModel.fail(RepCodeEnum.USER_PASSWORD_LENGTH_ERROR);
            }
            password = MD5Util.encryptBySalt(password);
            userVO.setPassword(password);
        }
        userVO.setDeleteFlag(DeleteFlagEnum.UNDELETED.getCodeValue());
        userVO.setCreatedBy(operator);
        userVO.setCreatedTime(LocalDateTime.now());
        userVO.setUpdatedBy(operator);
        userVO.setUpdatedTime(LocalDateTime.now());

        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(userVO, userPO);
        boolean flag = save(userPO);

        //返回结果
        if (flag) {
            return ResponseModel.success();
        } else {
            return ResponseModel.fail(RepCodeEnum.FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel updateById(RequestModel<UserVO> requestModel) {
        //参数校验
        UserVO userVO = requestModel.getData();
        if (userVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long userId = userVO.getUserId();
        if (userId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("userId");
        }
        //业务校验
        //...todo
//        if (StringUtils.isNoneBlank(userVO.getPassword())) {
//            String md5Password = MD5Util.encryptBySalt(userVO.getPassword());
//            userVO.setPassword(md5Password);
//        }

        //业务处理
        UserPO userPO = getById(userId);
        if (userPO == null) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("userId=" + userId.longValue());
        }
        if (StringUtils.isNotBlank(userVO.getLoginName()) && !userPO.getLoginName().equals(userVO.getLoginName())) {
            return ResponseModel.fail("登录名不允许修改");
        }

        if (StringUtils.isNotBlank(userVO.getPassword()) && StringUtils.isNotBlank(userVO.getConfirmPassword())) {
            String password = userVO.getPassword();
            String confirmPassword = userVO.getConfirmPassword();
            if (!password.equals(confirmPassword)) {
                return ResponseModel.fail(RepCodeEnum.USER_INCONSISTENT_PASSWORD_ERROR);
            }

            try {
                password = AESUtil.aesDecrypt(password, null);
            } catch (Exception e) {
                return ResponseModel.fail(RepCodeEnum.USER_PASSWORD_DECRYPTION_FAILED_ERROR);
            }
            if(password.length() < 6 || password.length() > 16 ){
                return ResponseModel.fail(RepCodeEnum.USER_PASSWORD_LENGTH_ERROR);
            }
            password = MD5Util.encryptBySalt(password);
            userVO.setPassword(password);
        }else {
            userVO.setPassword(null);
        }
        if (userId.longValue() == Constant.ADMIN_USER_ID) {
            //不允许修改admin密码、状态
            userVO.setPassword(null);
            userVO.setEnableFlag(EnableFlagEnum.ENABLE.getCodeValue());
            userVO.setDeleteFlag(DeleteFlagEnum.UNDELETED.getCodeValue());
        }
        userVO.setUpdatedBy(requestModel.getOpUserName());
        userVO.setUpdatedTime(LocalDateTime.now());

        BeanUtils.copyProperties(userVO, userPO, true);
        boolean flag = updateById(userPO);

        //返回结果
        if (flag) {
            if (null != userVO.getEnableFlag() && userVO.getEnableFlag() == 0) {
                //代表禁用该用户
                //清空用户的redis token
                String key = String.format(Constant.TOKEN, userId);
                redisService.delete(key);
            }
            return ResponseModel.success();
        } else {
            return ResponseModel.fail("修改失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel deleteById(RequestModel<UserVO> requestModel) {
        //参数校验
        UserVO userVO = requestModel.getData();
        if (userVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long userId = userVO.getUserId();
        if (userId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("userId");
        }

        //业务处理
        UserPO userPO = getById(userId);
        if (userPO == null) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("userId=" + userId.longValue());
        }
        boolean flag = removeById(userId);

        //返回结果
        if (flag) {
            //清空删除用户的redis token
            String key = String.format(Constant.TOKEN, userId);
            redisService.delete(key);
            return ResponseModel.success("删除成功");
        } else {
            return ResponseModel.fail("删除失败");
        }
    }

    @Override
    public ResponseModel queryById(RequestModel<UserVO> requestModel) {
        //参数校验
        UserVO userVO = requestModel.getData();
        if (userVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long userId = userVO.getUserId();
        if (userId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("userId");
        }

        //业务处理
        UserPO userPO = getById(userId);
        if (userPO == null) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("userId=" + userId.longValue());
        }

        //返回结果
        BeanUtils.copyProperties(userPO, userVO);
        return ResponseModel.success(userVO);
    }

    @Override
    public ResponseModel queryByPage(RequestModel<UserVO> requestModel) {
        //参数校验
        UserVO userVO = requestModel.getData();
        if (userVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        //...todo

        List<Long> opOrgIdList = requestModel.getOpOrgIdList();
        log.info("opOrgIdList: {}", opOrgIdList);
        userVO.setOrgIdLists(opOrgIdList);
        //分页参数
        Page<UserVO> page = new Page<UserVO>(requestModel.getCurrentPage(), requestModel.getPageSize());
        userVO.setDeleteFlag(DeleteFlagEnum.UNDELETED.getCodeValue());

        //业务处理
        IPage<UserVO> pageList = userMapper.queryByPage(page, userVO);

        //用户名和邮箱掩码处理
        if(pageList != null && pageList.getRecords() != null){
            pageList.getRecords().stream().forEach(userVO1 -> {
                userVO1.setPhone(com.anji.mirror.common.utils.StringUtils.maskPhoneWithF3T4(userVO1.getPhone()));
                userVO1.setEmail(com.anji.mirror.common.utils.StringUtils.maskEmail(userVO1.getEmail()));
            });
        }
        
        //返回结果
        return ResponseModel.success(pageList);
    }

    @Override
    public ResponseModel login(RequestModel<UserVO> requestModel) {
        //参数校验
        UserVO userVO = requestModel.getData();
        if (userVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        String loginName = userVO.getLoginName();
        String password = userVO.getPassword();

        if (StringUtils.isBlank(loginName)) {
            return ResponseModel.fail(RepCodeEnum.USER_NAME_NULL_ERROR);
        }
        if (StringUtils.isBlank(password)) {
            return ResponseModel.fail(RepCodeEnum.USER_PASSWORD_NULL_ERROR);
        }

        //验证行为验证码
        if (captchaSecondCheck) {
            if (StringUtils.isBlank(requestModel.getData().getCaptchaVerification())) {
                return RepCodeEnum.BLANK_ERROR.parseError("captchaVerification");
            }
            CaptchaVO captchaVO = new CaptchaVO();
            captchaVO.setCaptchaVerification(requestModel.getData().getCaptchaVerification());
            com.anji.captcha.model.common.ResponseModel verification = captchaService.verification(captchaVO);
            if (!verification.isSuccess()) {
                return ResponseModel.error(verification.getRepMsg());
            }
        }

        ResponseModel responseModel = new ResponseModel();

        try {
            password = AESUtil.aesDecrypt(password, null);
        } catch (Exception e) {
            return ResponseModel.fail(RepCodeEnum.USER_LOGIN_ERROR, "密码解密失败");
        }
        password = MD5Util.encryptBySalt(password);

        //判断用户是否锁定


        //业务处理
        //根据登录名，查询用户信息，判断是否删除、禁用、密码是否正确
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", userVO.getLoginName());
        UserPO userPOInDB = getOne(queryWrapper);
        if (userPOInDB == null) {
            return ResponseModel.fail(RepCodeEnum.USER_NOTEXIST_ERROR);
        }
        // 表中所有的字典字段，请使用枚举来判断，枚举在数据库t_dict维护，并用代码生成器生成。
        // new Integer(0).equals(accessUserVOInDB.getEnableFlag())
        if (EnableFlagEnum.DISABLE.getCodeValue() == userPOInDB.getEnableFlag()) {
            return ResponseModel.fail(RepCodeEnum.USER_ENABLE_ERROR);
        }
        if (DeleteFlagEnum.DELETED.getCodeValue() == userPOInDB.getDeleteFlag()) {
            return ResponseModel.fail(RepCodeEnum.USER_NOTEXIST_ERROR);
        }


        String userLimitKey = String.format(Constant.LIMIT_USER_LOGIN, userPOInDB.getLoginName());

        //获取系统登录安全配置
        long lockLoginFailTimes = 3;
        long sessionExpiredSecond = tokenExpiredSecond;
        SettingVO settingVO = settingService.queryBySettingName("login_config");
        if(settingVO != null){
            Long expiredMinute = settingVO.getLongInSettingValueJson("session-expired");
            Long failTimes = settingVO.getLongInSettingValueJson("lock_login_fail_times");
            if(expiredMinute != null){
                sessionExpiredSecond = expiredMinute.longValue() * 60;
            }
            if(failTimes != null){
                lockLoginFailTimes = failTimes.longValue();
            }
        }
        //尝试登陆次数超过上限，请三分钟后再次登录
        if (redisService.exists(userLimitKey)) {
            String userLimitLogin = redisService.getString(userLimitKey);
            if (Long.parseLong(userLimitLogin) > lockLoginFailTimes) {
                return ResponseModel.fail(RepCodeEnum.USER_LOGIN_ATTEMPTS_EXCEEDS_THE_UPPER_LIMIT);
            }
        }

        if (!StringUtils.equals(password, userPOInDB.getPassword())) {
            responseModel.setRepCodeEnum(RepCodeEnum.USER_PASSWORD_ERROR);
            //限制用户登陆次数
            redisService.incr(userLimitKey, 1L, 180L);
            return responseModel;
        }

        //用户状态和密码校验通过，构造返回用户信息、token、权限
        UserVO userVOResult = new UserVO();
        BeanUtils.copyProperties(userPOInDB, userVOResult, "password");

        //根据userId生成token，在filter中可以从token中反解出userId
        long userId = userVOResult.getUserId().longValue();
        String token = JwtTokenUtil.createToken(String.valueOf(userId));
        userVOResult.setToken(token);

        String accessUserKey = String.format(Constant.ACCESS_USER, userId);
        String tokenKey = String.format(Constant.TOKEN, userId);

        //如果是大屏演示用户，不要限制单点登录了，如果已经登录，直接返回之前的登录状态
        if (userPOInDB.getLoginName() == Constant.USER_NAME_PLAYER && redisService.exists(tokenKey)
                && redisService.exists(accessUserKey)) {
            UserVO redisUser = (UserVO) redisService.getObject(accessUserKey);
            return ResponseModel.success(redisUser);
        }

        //根据userid查询用户的权限
        //用于添加用户的权限。只要把用户权限添加到authorities
        Map<String, Map> grantedAuthorities = menuService.queryMenuActionCodeByUserId(userId);
        Map<String, List<Long>> accessWithOrgIds =  grantedAuthorities.get("accessWithOrgIds");
        Map<String,List<String>> accessWithOrgCodes = grantedAuthorities.get("accessWithOrgCodes");
        userVOResult.setAuthorityWithOrgIds(accessWithOrgIds);
        userVOResult.setAuthorityWithOrgCodes(accessWithOrgCodes);

        //缓存用户信息
        redisService.setObject(accessUserKey, userVOResult, sessionExpiredSecond);
        redisService.setString(tokenKey, token, sessionExpiredSecond);

        //更新用户最后登录ip和时间
        UserPO userPO = new UserPO();
        userPO.setUserId(userId);
        userPO.setLastLoginIp(requestModel.getOpSourceIP());
        userPO.setLastLoginTime(LocalDateTime.now());
        updateById(userPO);

        //返回结果
        return ResponseModel.success(userVOResult);
    }


    @Override
    public ResponseModel logout(RequestModel<UserVO> requestModel) {
        return ResponseModel.success();
    }

    /**
     * 用户分配组织角色
     *
     * @param requestModel
     * @return
     */
    @Override
    public ResponseModel queryRoleTree(RequestModel<UserVO> requestModel) {
        Long userId = requestModel.getData().getUserId();
        if (userId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("userId");
        }

        //该用户已经关联的组织角色
        QueryWrapper<UserRoleOrgPO> userRoleOrgQueryWrapper = new QueryWrapper<UserRoleOrgPO>();
        userRoleOrgQueryWrapper.select("role_id", "org_id").eq("user_id", userId);
        //组装出选中的id
        List<UserRoleOrgPO> userRoleOrgList = userRoleOrgMapper.selectList(userRoleOrgQueryWrapper);
        List<String> checkedIds = userRoleOrgList.stream()
                .map(userRoleOrgPO -> String.format("%s_%s", userRoleOrgPO.getOrgId(), userRoleOrgPO.getRoleId()))
                .collect(Collectors.toList());
        //获取当前登录用户所属组织
        List<Long> userOrgList = null;
        if (!requestModel.getOpUserId().equals(Constant.ADMIN_USER_ID)) {
            userOrgList = requestModel.getOpOrgIdList();
        }
        //所有的角色组织
        List<RoleOrgVO> roleOrgVOS = roleOrgMapper.queryAllRoleOrg();
        //所有的组织
        QueryWrapper<OrgPO> queryWrapper = new QueryWrapper<OrgPO>();
        queryWrapper.select("org_id", "org_name", "org_code", "org_parent_code")
                .eq("delete_flag", DeleteFlagEnum.UNDELETED.getCodeValue())
                .and(Wrapper -> Wrapper.eq("enable_flag", EnableFlagEnum.ENABLE.getCodeValue()));
        List<OrgPO> orgList = orgMapper.selectList(queryWrapper);
        List<TreeVO> tree = buildOrgRoleTree(orgList, roleOrgVOS, userOrgList, "0");

        //返回结果
        JSONObject repData = new JSONObject();
        repData.put("treeData", tree);
        repData.put("checkedIds", checkedIds);

        return ResponseModel.success(repData);
    }

    /**
     * @param requestModel
     * @return
     */
    @Override
    @Transactional
    public ResponseModel saveRoleTree(RequestModel<UserVO> requestModel) {
        //参数校验
        Long userId = requestModel.getData().getUserId();
        List<String> orgRoleIds = requestModel.getData().getOrgRoleIds();
        if (userId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("roleId");
        }
        if (orgRoleIds == null) {
            return RepCodeEnum.NULL_ERROR.parseError("orgIds");
        }

        //获取当前用户所属组织
        List<Long> opOrgIdList = requestModel.getOpOrgIdList();

        //获取被修改用户的用户权限

        //该用户已经关联的组织角色
        QueryWrapper<UserRoleOrgPO> userRoleOrgQueryWrapper = new QueryWrapper<UserRoleOrgPO>();
        userRoleOrgQueryWrapper.select("role_id", "org_id").eq("user_id", userId);
        //组装出选中的id
        List<UserRoleOrgPO> userRoleOrgList = userRoleOrgMapper.selectList(userRoleOrgQueryWrapper);
        List<String> checkedIds = userRoleOrgList.stream()
                .map(userRoleOrgPO -> String.format("%s_%s", userRoleOrgPO.getOrgId(), userRoleOrgPO.getRoleId()))
                .collect(Collectors.toList());
        List<String> orgRoleIdsCopy = new ArrayList<>(orgRoleIds);

        orgRoleIdsCopy.removeAll(checkedIds);
        List<Long> collect1 = orgRoleIdsCopy.stream().map(s -> {
            String orgId = s.split("_")[0];
            return Long.parseLong(orgId);
        }).distinct().collect(Collectors.toList());

        if (!(collect1.isEmpty() || opOrgIdList.containsAll(collect1))) {
            return ResponseModel.fail(RepCodeEnum.GATEWAY_AUTH_ERROR);
        }

        checkedIds.removeAll(new ArrayList<>(orgRoleIds));
        List<Long> collect = checkedIds.stream().map(s -> {
            String orgId = s.split("_")[0];
            return Long.parseLong(orgId);
        }).distinct().collect(Collectors.toList());
        if (!(collect.isEmpty() || opOrgIdList.containsAll(collect))) {
            return ResponseModel.fail(RepCodeEnum.GATEWAY_AUTH_ERROR);
        }


        //清除菜单的旧关联按钮
        userRoleOrgQueryWrapper.clear();
        userRoleOrgQueryWrapper.eq("user_id", userId);
        userRoleOrgMapper.delete(userRoleOrgQueryWrapper);
        orgRoleIds.forEach(s -> {
            UserRoleOrgPO userRoleOrgPO = new UserRoleOrgPO();
            if (s.contains("_")) {
                String orgId = s.split("_")[0];
                String roleId = s.split("_")[1];
                userRoleOrgPO.setOrgId(Long.valueOf(orgId));
                userRoleOrgPO.setRoleId(Long.valueOf(roleId));
                userRoleOrgPO.setUserId(userId);
                userRoleOrgMapper.insert(userRoleOrgPO);
            }
        });
        //清空删除用户的redis token
        String key = String.format(Constant.TOKEN, userId);
        redisService.delete(key);
        return updateById(requestModel);
    }

    /**
     * 查询用户的信息及权限
     *
     * @param userId
     * @return
     */
    @Override
    public void refreshUserCache(Long userId) {


        String tokenKey = String.format(Constant.TOKEN, userId);
        if (!redisService.exists(tokenKey)) {
            return;
        }

        //判断用户信息是否存在
        String userKey = String.format(Constant.ACCESS_USER, userId);
        if (!redisService.exists(userKey)) {
            return;
        }
        UserVO userVOResult = (UserVO) redisService.getObject(userKey);


        //根据userid查询用户的权限
        //用于添加用户的权限。只要把用户权限添加到authorities
        Map<String, Map> grantedAuthorities = menuService.queryMenuActionCodeByUserId(userId);
        Map<String, List<Long>> accessWithOrgIds =  grantedAuthorities.get("accessWithOrgIds");
        Map<String,List<String>> accessWithOrgCodes = grantedAuthorities.get("accessWithOrgCodes");
        userVOResult.setAuthorityWithOrgIds(accessWithOrgIds);
        userVOResult.setAuthorityWithOrgCodes(accessWithOrgCodes);
        //缓存用户信息
        redisService.setObject(userKey, userVOResult, redisService.getExpire(userKey));
    }

    /************************** 以下为服务内部方法 ****************************/
    /*@Override
    public UserVO queryById(Long userId) {
        if (userId == null) {
            return null;
        }
        UserVO userVO = null;
        //目前只有在取用户信息走redis，修改用户并未更新redis缓存，待优化
//        String userKey = String.format(Constant.ACCESS_USER, userId);
//        if (redisService.exists(userKey)) {
//            userVO = (UserVO) redisService.getObject(userKey);
//            if (userVO != null) {
//                return userVO;
//            }
//        }

        //业务处理
        UserPO userPO = getById(userId);
        if (userPO == null) {
            return null;
        }
        BeanUtils.copyProperties(userPO, userVO, "password");
        Map<String, List<Long>> grantedAuthorities = menuService.queryMenuActionCodeByUserId(userId);
        userVO.setAuthorityWithOrgIds(grantedAuthorities);

        //返回结果
        return userVO;
    }*/
    /************************** 以上为服务内部方法 ****************************/

    /************************** 以下为跨服务调用方法 ****************************/
    /**
     * 判断token是否过期, 未过期token，判断用户是否有权限请求接口
     *
     * @param token
     * @param servletPath
     * @return
     */
    @Override
    public Authentication getUserAuthByToken(String token, String servletPath) {
        //======================================== 第一阶段，判断token是否有效 ========================================
        if (StringUtils.isBlank(token) || StringUtils.isBlank(servletPath)) {
            return Authentication.fail();
        }

        //token是否由我们后端生成
        String userIdStr = JwtTokenUtil.parseToken(token);
        if (StringUtils.isBlank(userIdStr)) {
            return Authentication.fail();
        }

        //判断是否过期
        long userId = Long.parseLong(userIdStr);
        String tokenKey = String.format(Constant.TOKEN, userId);
        if (!redisService.exists(tokenKey)) {
            return Authentication.fail();//token已经过期
        }
        //token有效
        String savedToken = redisService.getString(tokenKey);
        if (!StringUtils.equals(token, savedToken)) {
            //token已经被挤下线
            return Authentication.fail();
        }

        //判断用户信息是否存在
        String userKey = String.format(Constant.ACCESS_USER, userId);
        if (!redisService.exists(userKey)) {
            return Authentication.fail();//用户信息已经过期
        }
        UserVO userVO = (UserVO) redisService.getObject(userKey);

        //延长token和用户过期时间
        try {
            long expiredTime = tokenExpiredSecond == null ? 43200 : tokenExpiredSecond.longValue();
            String accessUserKey = String.format(Constant.ACCESS_USER, userId);
            redisService.setObject(accessUserKey, userVO, expiredTime);
            redisService.setString(tokenKey, token, expiredTime);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        //======================================== 第二阶段，判断是否有权限 ========================================
        Authentication authentication = Authentication.pass();
        authentication.setUserId(userVO.getUserId());
        authentication.setLoginName(userVO.getLoginName());

        String requirePermission = (String) redisService.hget(Constant.MVC_PATH_PERMISSION_HASH_TABLE, servletPath);
        //该接口不需要权限
        if (StringUtils.isBlank(requirePermission)) {
            authentication.setOrgIdList(new ArrayList<Long>());
            authentication.setOrgCodeList(new ArrayList<String>());
            return authentication;
        }
        String[] requirePermissionArr = requirePermission.trim().split("\\|");

        //获取用户拥有的权限码
        Map<String, List<Long>> authorityWithOrgIds = userVO.getAuthorityWithOrgIds();
        Map<String, List<String>> authorityWithOrgCodes = userVO.getAuthorityWithOrgCodes();

        //判断用户权限是否满足
        boolean match = false;
        if (requirePermission.contains("&")) {
            String[] permissions = requirePermission.split("&");

            Set<Long> orgIdList = new HashSet<>();
            match = Arrays.stream(permissions).allMatch(str -> {
                List<Long> list = authorityWithOrgIds.get(str.trim());
                if (null != list && list.size() > 0) {
                    if (orgIdList.size() < 1) {
                        orgIdList.addAll(list);
                    } else {
                        orgIdList.retainAll(list);
                    }
                }
                return authorityWithOrgIds.containsKey(str.trim());
            });
            authentication.setOrgIdList(new ArrayList<>(orgIdList));

            Set<String> orgCodeList = new HashSet<>();
            match = Arrays.stream(permissions).allMatch(str -> {
                List<String> list = authorityWithOrgCodes.get(str.trim());
                if (null != list && list.size() > 0) {
                    if (orgCodeList.size() < 1) {
                        orgCodeList.addAll(list);
                    } else {
                        orgCodeList.retainAll(list);
                    }
                }
                return authorityWithOrgCodes.containsKey(str.trim());
            });
            authentication.setOrgCodeList(new ArrayList<>(orgCodeList));

        } else if (requirePermission.contains("|")) {
            String[] permissions = requirePermission.split("\\|");
            Set<Long> orgIdList = new HashSet<>();
            match = Arrays.stream(permissions).anyMatch(str -> {
                List<Long> list = authorityWithOrgIds.get(str.trim());
                if (null != list && list.size() > 0) {
                    orgIdList.addAll(list);
                }
                return authorityWithOrgIds.containsKey(str.trim());
            });
            authentication.setOrgIdList(new ArrayList<>(orgIdList));

            Set<String> orgCodeList = new HashSet<>();
            match = Arrays.stream(permissions).anyMatch(str -> {
                List<String> list = authorityWithOrgCodes.get(str.trim());
                if (null != list && list.size() > 0) {
                    orgCodeList.addAll(list);
                }
                return authorityWithOrgCodes.containsKey(str.trim());
            });
            authentication.setOrgCodeList(new ArrayList<>(orgCodeList));

        } else {
            match = authorityWithOrgIds.containsKey(requirePermission.trim());
            authentication.setOrgIdList(authorityWithOrgIds.get(requirePermission.trim()));
            authentication.setOrgCodeList(authorityWithOrgCodes.get(requirePermission.trim()));
        }
        authentication.setHasPermission(match);

        return authentication;
    }

    @Override
    public ResponseModel queryUserByUserNameOrMail(RequestModel<StringParamVO> requestModel) {
        StringParamVO param=requestModel.getData();
        if(param ==null || org.springframework.util.StringUtils.isEmpty(param.getParam()))
            return ResponseModel.success();
        UserVO userVO=new UserVO();
        userVO.setRealName(param.getParam());
        userVO.setLoginName(param.getParam());
        userVO.setEmail(param.getParam());
        userVO.setPhone(param.getParam());
        List<UserVO> list= userMapper.queryUserByUserNameOrMail(userVO);
        return ResponseModel.success(list);
    }

    /**
     * 用户自己根据旧密码修改密码
     *
     * @param requestModel
     * @return
     */
    @Override
    public ResponseModel updatePassword(RequestModel<UserVO> requestModel) {
        //参数校验
        UserVO userVO = requestModel.getData();
        if (userVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        if (StringUtils.isBlank(userVO.getPassword())) {
            return RepCodeEnum.NULL_ERROR.parseError("password");
        }
        if (StringUtils.isBlank(userVO.getOldPassword())) {
            return RepCodeEnum.NULL_ERROR.parseError("oldPassword");
        }
        if (StringUtils.isBlank(userVO.getConfirmPassword())) {
            return RepCodeEnum.NULL_ERROR.parseError("confirmPassword");
        }
        if (!userVO.getConfirmPassword().equals(userVO.getPassword())) {
            //密码和确认密码不一致
            return ResponseModel.fail(RepCodeEnum.USER_INCONSISTENT_PASSWORD_ERROR);
        }
        //新密码不能与老密码一样
        if(StringUtils.equals(userVO.getOldPassword(), userVO.getPassword())){
            return ResponseModel.fail(RepCodeEnum.USER_PASSWORD_CONFIG_PASSWORD_CANOT_EQUAL);
        }

        String password = userVO.getPassword();
        String oldPassword = userVO.getOldPassword();

        try {
            oldPassword = AESUtil.aesDecrypt(oldPassword, null);
            password = AESUtil.aesDecrypt(password, null);
        } catch (Exception e) {
            return ResponseModel.fail(RepCodeEnum.USER_PASSWORD_DECRYPTION_FAILED_ERROR);
        }
        if(password.length() < 6 || password.length() > 16 || oldPassword.length() < 6 || oldPassword.length() > 16){
            return ResponseModel.fail(RepCodeEnum.USER_PASSWORD_LENGTH_ERROR);
        }
        password = MD5Util.encryptBySalt(password);
        oldPassword = MD5Util.encryptBySalt(oldPassword);
        UserPO userPO = userMapper.selectById(requestModel.getOpUserId());
        if (!oldPassword.equals(userPO.getPassword())) {
            return ResponseModel.fail(RepCodeEnum.USER_OLD_PASSWORD_ERROR);
        }
        userPO.setUpdatedBy(requestModel.getOpUserName());
        userPO.setUpdatedTime(LocalDateTime.now());
        userPO.setPassword(password);
        boolean flag = updateById(userPO);
        //返回结果
        if (flag) {
            return ResponseModel.success("更新密码成功");
        } else {
            return ResponseModel.fail("更新密码失败");
        }

    }

    @Override
    public ResponseModel selectOption(RequestModel<UserVO> requestModel) {
        UserVO userVO = requestModel.getData();
       //参数校验
        if (userVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }

        //该校验 后期需要放开
        /*if (StringUtils.isBlank(userVO.getRealName())){
            return RepCodeEnum.NULL_ERROR.parseError("realName");
        }
        if (StringUtils.isBlank(userVO.getPhone())){
            return RepCodeEnum.NULL_ERROR.parseError("phone");
        }*/
        //业务处理
        List<UserVO> userList = userMapper.queryByLoginNameOrPhone(userVO);

        List<OptionVO> collect = userList.stream().map(user -> {
            OptionVO optionVO = new OptionVO();
            optionVO.setLabel(user.getRealName());
            optionVO.setValue(user.getLoginName());
            return optionVO;
        }).collect(Collectors.toList());
        //返回结果
        return ResponseModel.success(collect);
    }

    /************************** 以上为跨服务调用方法 ****************************/

    /**
     * 组织角色树
     *
     * @param orgList
     * @param pid
     * @return
     */
    private static List<TreeVO> buildOrgRoleTree(List<OrgPO> orgList, List<RoleOrgVO> roleOrgVOS, List<Long> userOrgList, Object pid) {
        List<TreeVO> childList = new ArrayList<>();
        orgList.forEach(orgPO -> {
            if (orgPO.getOrgParentCode().equals(pid)) {
                TreeVO treeVO = new TreeVO();
                String roleTreeId = String.format("%s_%s", orgPO.getOrgId(), orgPO.getOrgCode());
                treeVO.setId(roleTreeId);
                treeVO.setLabel(orgPO.getOrgName());
                if (null != userOrgList && !userOrgList.contains(orgPO.getOrgId())) {
                    treeVO.setDisabled(true);
                }
                childList.add(treeVO);
            }
        });

        childList.forEach(treeVO -> {
            String orgId = treeVO.getId().toString().split("_")[0];
            String orgCode = treeVO.getId().toString().split("_")[1];
            treeVO.setId(Long.valueOf(orgId));
            List<TreeVO> treeList = buildOrgRoleTree(orgList, roleOrgVOS, userOrgList, orgCode);
            List<TreeVO> collect = roleOrgVOS.stream().filter(roleOrgVO -> roleOrgVO.getOrgId().equals(Long.valueOf(orgId)))
                    .map(roleOrgVO -> {
                        TreeVO treeEntity = new TreeVO();
                        String roleOrgTreeId = String.format("%s_%s", orgId, roleOrgVO.getRoleId());
                        treeEntity.setId(roleOrgTreeId);
                        treeEntity.setLabel(roleOrgVO.getRoleName());
                        if (null != userOrgList && !userOrgList.contains(Long.parseLong(orgId))) {
                            treeEntity.setDisabled(true);
                        }
                        return treeEntity;
                    }).collect(Collectors.toList());
            if (!treeList.isEmpty()) {
                collect.addAll(treeList);
            }
            treeVO.setChildren(collect);
        });
        return childList;
    }
}
