package com.anji.mirror.push.service.impl;

import com.alibaba.fastjson.JSON;
import com.anji.mirror.push.mapper.PushHistoryMapper;
import com.anji.mirror.push.service.PushHistoryService;
import com.anji.mirror.common.enums.RepCodeEnum;
import com.anji.mirror.common.model.RequestModel;
import com.anji.mirror.common.model.ResponseModel;
import com.anji.mirror.common.utils.BeanUtils;
import com.anji.mirror.push.utils.DateTimeUtil;
import com.anjiplus.gaea.push.domain.po.PushHistoryPO;
import com.anjiplus.gaea.push.domain.vo.PushHistoryVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author anji gaea teams
 * @since 2020-10-26
 */
@Slf4j
@Service
public class PushHistoryServiceImpl extends ServiceImpl<PushHistoryMapper, PushHistoryPO> implements PushHistoryService {

    @Autowired
    private PushHistoryMapper pushHistoryMapper;

    /**
     * 根据数据库必填项，校验是否为空，不校验主键
     *
     * @param pushHistoryVO
     * @return
     */
    private ResponseModel validateCreateFieldNotNull(PushHistoryVO pushHistoryVO) {
        if (pushHistoryVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("请求数据");
        }
        return ResponseModel.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel create(RequestModel<PushHistoryVO> requestModel) {
        //参数校验
        PushHistoryVO pushHistoryVO = requestModel.getData();
        ResponseModel valid = validateCreateFieldNotNull(pushHistoryVO);
        if (valid.isError()) {
            return valid;
        }
        //业务校验
        //...todo

        //业务处理
        String operator = requestModel.getOpUserName();
        pushHistoryVO.setCreatedBy(operator);
        pushHistoryVO.setCreatedTime(LocalDateTime.now());
        pushHistoryVO.setUpdatedBy(operator);
        pushHistoryVO.setUpdatedTime(LocalDateTime.now());

        PushHistoryPO pushHistoryPO = new PushHistoryPO();
        BeanUtils.copyProperties(pushHistoryVO, pushHistoryPO);
        boolean flag = save(pushHistoryPO);

        //返回结果
        if (flag) {
            return ResponseModel.success(pushHistoryPO);
        } else {
            return ResponseModel.fail(RepCodeEnum.FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel updateById(RequestModel<PushHistoryVO> requestModel) {
        //参数校验
        PushHistoryVO pushHistoryVO = requestModel.getData();
        if (pushHistoryVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("请求数据");
        }
        Long pushHistoryId = pushHistoryVO.getPushId();
        if (pushHistoryId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("推送id");
        }
        //业务校验
        //...todo

        //业务处理
        PushHistoryPO pushHistoryPO = getById(pushHistoryId);
        if (pushHistoryPO == null) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("历史数据");
        }
        BeanUtils.copyProperties(pushHistoryVO, pushHistoryPO, true);
        boolean flag = updateById(pushHistoryPO);

        //返回结果
        if (flag) {
            return ResponseModel.success(pushHistoryPO);
        } else {
            return ResponseModel.fail("修改失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel deleteById(RequestModel<PushHistoryVO> requestModel) {
        //参数校验
        PushHistoryVO pushHistoryVO = requestModel.getData();
        if (pushHistoryVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("请求数据");
        }
        Long pushHistoryId = pushHistoryVO.getPushId();
        if (pushHistoryId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("推送id");
        }

        //业务处理
        PushHistoryPO pushHistoryPO = getById(pushHistoryId);
        if (pushHistoryPO == null) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("历史数据");
        }
        boolean flag = removeById(pushHistoryId);

        //返回结果
        if (flag) {
            return ResponseModel.success("删除成功");
        } else {
            return ResponseModel.fail("删除失败");
        }
    }

    @Override
    public ResponseModel queryById(RequestModel<PushHistoryVO> requestModel) {
        //参数校验
        PushHistoryVO pushHistoryVO = requestModel.getData();
        if (pushHistoryVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("推送请求数据");
        }
        Long pushHistoryId = pushHistoryVO.getPushId();
        if (pushHistoryId == null) {
            return RepCodeEnum.NULL_ERROR.parseError("推送id");
        }

        //业务处理
        PushHistoryPO pushHistoryPO = getById(pushHistoryId);
        if (pushHistoryPO == null) {
            return RepCodeEnum.NOT_EXIST_ERROR.parseError("历史数据");
        }

        //返回结果
        BeanUtils.copyProperties(pushHistoryPO, pushHistoryVO);
        return ResponseModel.success(pushHistoryVO);
    }

    @Override
    public ResponseModel queryByPage(RequestModel<PushHistoryVO> requestModel) {
        //参数校验
        PushHistoryVO pushHistoryVO = requestModel.getData();
        if (pushHistoryVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("请求数据");
        }

        //分页参数
        Page<PushHistoryVO> page = new Page<PushHistoryVO>(requestModel.getCurrentPage(), requestModel.getPageSize());

        //业务处理
        IPage<PushHistoryVO> pageList = pushHistoryMapper.queryByPage(page, pushHistoryVO);

        //返回结果
        return ResponseModel.success(pageList);
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void alertEventArchive() {
        Map<String, String> threeMonthsAgoTimestamp = null;
        try {
            threeMonthsAgoTimestamp = DateTimeUtil.getMonthsAgoTimestamp(-3);
        } catch (ParseException e) {
            log.error("ParseException", e);
        }
        //创建表
        log.info("创建表--------------->{},{}","t_history", JSON.toJSONString(threeMonthsAgoTimestamp));
        pushHistoryMapper.createTable(threeMonthsAgoTimestamp);
        //复制数据至归档表
        log.info("复制数据至归档表----------------------------");

        pushHistoryMapper.copyArchiveData(threeMonthsAgoTimestamp);
        //删除原表数据
        log.info("删除t_history表三个月前数据-------------");
        pushHistoryMapper.deleteArchiveData(threeMonthsAgoTimestamp);

    }
}
