package com.anji.mirror.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.anji.mirror.auth.domain.po.LogPO;
import com.anji.mirror.auth.mapper.LogMapper;
import com.anji.mirror.auth.service.LogService;
import com.anji.mirror.common.enums.RepCodeEnum;
import com.anji.mirror.common.model.LogVO;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.security.Constant;
import com.anji.mirror.common.service.RedisService;
import com.anji.mirror.common.utils.BeanUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户操作日志表 服务实现类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, LogPO> implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private RedisService redisService;

    /** 根据数据库必填项，校验是否为空，不校验主键
     * @param logVO
     * @return
     */
    private ResponseModel validateCreateFieldNotNull(LogVO logVO){
        if(logVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        /* 该片段由生成器产生，请根据实际情况修改
        if(logVO.getLogId() == null){
            return RepCodeEnum.NULL_ERROR.parseError("logId");
        }
        if(logVO.getUserId() == null){
            return RepCodeEnum.NULL_ERROR.parseError("userId");
        }
        if(StringUtils.isBlank(logVO.getUserName())){
            return RepCodeEnum.NULL_ERROR.parseError("userName");
        }
        if(StringUtils.isBlank(logVO.getRequestUrl())){
            return RepCodeEnum.NULL_ERROR.parseError("requestUrl");
        }
        if(StringUtils.isBlank(logVO.getPageTitle())){
            return RepCodeEnum.NULL_ERROR.parseError("pageTitle");
        }
        if(StringUtils.isBlank(logVO.getRequestParam())){
            return RepCodeEnum.NULL_ERROR.parseError("requestParam");
        }
        if(StringUtils.isBlank(logVO.getResponseParam())){
            return RepCodeEnum.NULL_ERROR.parseError("responseParam");
        }
        if(StringUtils.isBlank(logVO.getSourceIp())){
            return RepCodeEnum.NULL_ERROR.parseError("sourceIp");
        }
        if(logVO.getRequestTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestTime");
        }
        */
        return ResponseModel.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel create(RequestModel<LogVO> requestModel) {
        //参数校验
        LogVO logVO = requestModel.getData();
        ResponseModel valid = validateCreateFieldNotNull(logVO);
        if(valid.isError()){
            return valid;
        }
        //业务校验
        //...todo

        //业务处理
        String operator = requestModel.getOpUserName();
        logVO.setRequestTime(LocalDateTime.now());

        LogPO logPO = new LogPO();
        BeanUtils.copyProperties(logVO, logPO);
        boolean flag = save(logPO);

        //返回结果
        if(flag){
            return ResponseModel.success(logPO);
        }else{
            return ResponseModel.fail(RepCodeEnum.FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel updateById(RequestModel<LogVO> requestModel) {
        //参数校验
        LogVO logVO = requestModel.getData();
        if(logVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long logId = logVO.getLogId();
        if(logId == null){
            return RepCodeEnum.NULL_ERROR.parseError("logId");
        }
        //业务校验
        //...todo

        //业务处理
        LogPO logPO = getById(logId);
        if(logPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("logId="+logId.longValue());
        }
        BeanUtils.copyProperties(logVO, logPO, true);
        boolean flag = updateById(logPO);

        //返回结果
        if(flag){
            return ResponseModel.success(logPO);
        }else{
            return ResponseModel.fail("修改失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel deleteById(RequestModel<LogVO> requestModel) {
        //参数校验
        LogVO logVO = requestModel.getData();
        if(logVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long logId = logVO.getLogId();
        if(logId == null){
            return RepCodeEnum.NULL_ERROR.parseError("logId");
        }

        //业务处理
        LogPO logPO = getById(logId);
        if(logPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("logId="+logId.longValue());
        }
        boolean flag = removeById(logId);

        //返回结果
        if(flag){
            return ResponseModel.success("删除成功");
        }else{
            return ResponseModel.fail("删除失败");
        }
    }

    @Override
    public ResponseModel queryById(RequestModel<LogVO> requestModel) {
        //参数校验
        LogVO logVO = requestModel.getData();
        if(logVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long logId = logVO.getLogId();
        if(logId == null){
            return RepCodeEnum.NULL_ERROR.parseError("logId");
        }

        //业务处理
        LogPO logPO = getById(logId);
        if(logPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("logId="+logId.longValue());
        }

        //返回结果
        BeanUtils.copyProperties(logPO, logVO);
        return ResponseModel.success(logVO);
    }

    @Override
    public ResponseModel queryByPage(RequestModel<LogVO> requestModel) {
        //参数校验
        LogVO logVO = requestModel.getData();
        if(logVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        //...todo

        //分页参数
        Page<LogVO> page = new Page<LogVO>(requestModel.getCurrentPage(), requestModel.getPageSize());

        //业务处理
        IPage<LogVO> pageList = logMapper.queryByPage(page, logVO);
        //返回结果
        return ResponseModel.success(pageList);
    }

    @Override
    public void saveOperatorLog(LogVO logVO) {
        try{
            String apiPath = logVO.getRequestUrl();
            Object jsonStr = redisService.hget(Constant.MVC_PATH_OPERATION_LOG_HASH_TABLE, apiPath);
            if(jsonStr == null){
                return;
            }

            JSONObject jsonObject = JSONObject.parseObject((String)jsonStr);
            String pageTitle = jsonObject.getString("pageTitle");
            Boolean saveRequestData = jsonObject.getBoolean("isSaveRequestData");
            if(saveRequestData == null ||saveRequestData.booleanValue() == false){
                logVO.setRequestParam("");
            }
            logVO.setPageTitle(pageTitle);

            //保存
            LogPO logPO = new LogPO();
            BeanUtils.copyProperties(logVO, logPO);
            save(logPO);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
