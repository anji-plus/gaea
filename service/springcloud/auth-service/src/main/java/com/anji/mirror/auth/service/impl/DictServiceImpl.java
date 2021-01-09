package com.anji.mirror.auth.service.impl;

import com.anji.mirror.auth.domain.po.DictPO;
import com.anji.mirror.auth.domain.vo.DictVO;
import com.anji.mirror.auth.mapper.DictMapper;
import com.anji.mirror.auth.service.DictService;
import com.anji.mirror.common.enums.DeleteFlagEnum;
import com.anji.mirror.common.enums.EnableFlagEnum;
import com.anji.mirror.common.enums.RepCodeEnum;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.utils.BeanUtils;
import com.anji.mirror.common.utils.StringPatternUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-08-20
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, DictPO> implements DictService {

    @Autowired
    private DictMapper dictMapper;

    /** 根据数据库必填项，校验是否为空，不校验主键
     * @param dictVO
     * @return
     */
    private ResponseModel validateCreateFieldNotNull(DictVO dictVO){
        if(dictVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        /* 该片段由生成器产生，请根据实际情况修改
        if(dictVO.getDictId() == null){
            return RepCodeEnum.NULL_ERROR.parseError("dictId");
        }
        if(StringUtils.isBlank(dictVO.getDictName())){
            return RepCodeEnum.NULL_ERROR.parseError("dictName");
        }
        if(StringUtils.isBlank(dictVO.getDictDesc())){
            return RepCodeEnum.NULL_ERROR.parseError("dictDesc");
        }
        if(StringUtils.isBlank(dictVO.getItemName())){
            return RepCodeEnum.NULL_ERROR.parseError("itemName");
        }
        if(StringUtils.isBlank(dictVO.getItemValue())){
            return RepCodeEnum.NULL_ERROR.parseError("itemValue");
        }
        if(StringUtils.isBlank(dictVO.getItemDesc())){
            return RepCodeEnum.NULL_ERROR.parseError("itemDesc");
        }
        if(StringUtils.isBlank(dictVO.getItemExtend())){
            return RepCodeEnum.NULL_ERROR.parseError("itemExtend");
        }
        if(dictVO.getEnableFlag() == null){
            return RepCodeEnum.NULL_ERROR.parseError("enableFlag");
        }
        if(dictVO.getDeleteFlag() == null){
            return RepCodeEnum.NULL_ERROR.parseError("deleteFlag");
        }
        if(dictVO.getSort() == null){
            return RepCodeEnum.NULL_ERROR.parseError("sort");
        }
        if(StringUtils.isBlank(dictVO.getRemark())){
            return RepCodeEnum.NULL_ERROR.parseError("remark");
        }
        if(StringUtils.isBlank(dictVO.getCreatedBy())){
            return RepCodeEnum.NULL_ERROR.parseError("createdBy");
        }
        if(dictVO.getCreatedTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("createdTime");
        }
        if(StringUtils.isBlank(dictVO.getUpdatedBy())){
            return RepCodeEnum.NULL_ERROR.parseError("updatedBy");
        }
        if(dictVO.getUpdatedTime() == null){
            return RepCodeEnum.NULL_ERROR.parseError("updatedTime");
        }
        */
        return ResponseModel.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel create(RequestModel<DictVO> requestModel) {
        //参数校验
        DictVO dictVO = requestModel.getData();
        ResponseModel valid = validateCreateFieldNotNull(dictVO);
        if(valid.isError()){
            return valid;
        }
        //业务校验
        //...todo

        //业务处理
        String operator = requestModel.getOpUserName();

        if(dictVO.getEnableFlag()==null){
            dictVO.setEnableFlag(EnableFlagEnum.ENABLE.getCodeValue());
        }
        dictVO.setDeleteFlag(DeleteFlagEnum.UNDELETED.getCodeValue());
        dictVO.setCreatedBy(operator);
        dictVO.setCreatedTime(LocalDateTime.now());
        dictVO.setUpdatedBy(operator);
        dictVO.setUpdatedTime(LocalDateTime.now());

        DictPO dictPO = new DictPO();
        BeanUtils.copyProperties(dictVO, dictPO);
        boolean flag = save(dictPO);

        //返回结果
        if(flag){
            return ResponseModel.success(dictPO);
        }else{
            return ResponseModel.fail(RepCodeEnum.FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel updateById(RequestModel<DictVO> requestModel) {
        //参数校验
        DictVO dictVO = requestModel.getData();
        if(dictVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long dictId = dictVO.getDictId();
        if(dictId == null){
            return RepCodeEnum.NULL_ERROR.parseError("dictId");
        }
        //业务校验
        //...todo

        //业务处理
        DictPO dictPO = getById(dictId);
        if(dictPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("dictId="+dictId.longValue());
        }
        dictVO.setUpdatedBy(requestModel.getOpUserName());
        dictVO.setUpdatedTime(LocalDateTime.now());
        BeanUtils.copyProperties(dictVO, dictPO, true);
        boolean flag = updateById(dictPO);

        //返回结果
        if(flag){
            return ResponseModel.success(dictPO);
        }else{
            return ResponseModel.fail("修改失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel deleteById(RequestModel<DictVO> requestModel) {
        //参数校验
        DictVO dictVO = requestModel.getData();
        if(dictVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long dictId = dictVO.getDictId();
        if(dictId == null){
            return RepCodeEnum.NULL_ERROR.parseError("dictId");
        }

        //业务处理
        DictPO dictPO = getById(dictId);
        if(dictPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("dictId="+dictId.longValue());
        }
        boolean flag = removeById(dictId);

        //返回结果
        if(flag){
            return ResponseModel.success("删除成功");
        }else{
            return ResponseModel.fail("删除失败");
        }
    }

    @Override
    public ResponseModel queryById(RequestModel<DictVO> requestModel) {
        //参数校验
        DictVO dictVO = requestModel.getData();
        if(dictVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        Long dictId = dictVO.getDictId();
        if(dictId == null){
            return RepCodeEnum.NULL_ERROR.parseError("dictId");
        }

        //业务处理
        DictPO dictPO = getById(dictId);
        if(dictPO == null){
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("dictId="+dictId.longValue());
        }

        //返回结果
        BeanUtils.copyProperties(dictPO, dictVO);
        return ResponseModel.success(dictVO);
    }

    @Override
    public ResponseModel queryByPage(RequestModel<DictVO> requestModel) {
        //参数校验
        DictVO dictVO = requestModel.getData();
        if(dictVO == null){
            return RepCodeEnum.NULL_ERROR.parseError("requestModel data");
        }
        //...todo

        //分页参数
        Page<DictVO> page = new Page<DictVO>(requestModel.getCurrentPage(), requestModel.getPageSize());
        dictVO.setDeleteFlag(DeleteFlagEnum.UNDELETED.getCodeValue());

        //业务处理
        IPage<DictVO> pageList = dictMapper.queryByPage(page, dictVO);

        //返回结果
        return ResponseModel.success(pageList);
    }

    @Override
    public ResponseModel queryForCodeSelect(RequestModel<DictVO> requestModel) {
        Page<DictVO> page = new Page<DictVO>(1, Integer.MAX_VALUE);
        page.addOrder(OrderItem.desc("dict_name"));
        page.addOrder(OrderItem.asc("sort"));

        DictVO dictVO = new DictVO();
        dictVO.setDeleteFlag(DeleteFlagEnum.UNDELETED.getCodeValue());
        dictVO.setEnableFlag(EnableFlagEnum.ENABLE.getCodeValue());
        IPage<DictVO> pageList = dictMapper.queryByPage(page, dictVO);

        Map<String, List<Map<String,Object>>> codeMap = new HashMap<>();
        if(pageList.getRecords() == null || pageList.getRecords().size() == 0){
            return ResponseModel.success(codeMap);
        }

        List<Map<String,Object>> mapList;
        for(DictVO base : pageList.getRecords()){

            Map<String,Object> node = new HashMap<>();
            if(codeMap.containsKey(base.getDictName())){
                mapList = codeMap.get(base.getDictName());
            }else {
                mapList = new ArrayList<>();
            }
            node.put("labelEng", base.getItemName());
            node.put("label", base.getItemDesc());

            String codeValue = base.getItemValue();

            if(StringPatternUtil.StringMatch(codeValue,"[0-9]*")){
                try{
                    int intCodeValue = Integer.parseInt(codeValue);
                    node.put("value", intCodeValue);
                }catch (Exception e){
                    node.put("value", base.getItemValue());
                }
            }else{
                node.put("value", base.getItemValue());
            }

            String codeExtend = base.getItemExtend();
            if(StringUtils.isBlank(codeExtend)){
                codeExtend = "";
            }
            node.put("extend", codeExtend);
            mapList.add(node);
            codeMap.put(base.getDictName(),mapList);
        }

        return ResponseModel.success(codeMap);
    }
}
