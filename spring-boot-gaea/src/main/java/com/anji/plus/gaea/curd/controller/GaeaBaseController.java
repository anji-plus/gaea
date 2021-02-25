package com.anji.plus.gaea.curd.controller;



import com.anji.plus.gaea.annotation.AccessKey;
import com.anji.plus.gaea.annotation.Permission;
import com.anji.plus.gaea.holder.UserContentHolder;
import com.anji.plus.gaea.utils.GaeaBeanUtils;
import com.anji.plus.gaea.utils.GaeaUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.anji.plus.gaea.bean.ResponseBean;
import com.anji.plus.gaea.code.ResponseCode;
import com.anji.plus.gaea.curd.dto.GaeaBaseDTO;
import com.anji.plus.gaea.curd.entity.GaeaBaseEntity;
import com.anji.plus.gaea.curd.params.PageParam;
import com.anji.plus.gaea.curd.service.GaeaBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.anji.plus.gaea.bean.ResponseBean.builder;


/**
 * 基础controller
 *
 * @author lr
 * @since 2021-01-12
 */
public abstract class GaeaBaseController<P extends PageParam, T extends GaeaBaseEntity, D extends GaeaBaseDTO> {

    /**
     * 记录日志
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取实际服务类
     *
     * @return
     */
    public abstract GaeaBaseService<P, T> getService();

    /**
     * 获取当前Controller数据库实体Entity
     *
     * @return
     */
    public abstract T getEntity();

    /**
     * 获取当前Controller数据传输DTO
     *
     * @return
     */
    public abstract D getDTO();

    /**
     * 分页模板
     *
     * @param param
     * @return
     */
    @GetMapping("/pageList")
    public ResponseBean pageList(P param) {
        IPage iPage = getService().page(param);
        List<T> records = iPage.getRecords();
        List<D> list = records.stream()
                .map(entity -> GaeaBeanUtils.copyAndFormatter(entity, getDTO()))
                .collect(Collectors.toList());

        Page<D> pageDto = new Page<>();
        pageDto.setCurrent(iPage.getCurrent());
        pageDto.setRecords(list);
        pageDto.setPages(iPage.getPages());
        pageDto.setTotal(iPage.getTotal());
        pageDto.setSize(iPage.getSize());
        return responseSuccessWithData(pageDto);
    }

    /**
     * 根据ID查询相关记录
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @AccessKey
    public ResponseBean detail(@PathVariable("id") Long id) {
        logger.info("{}根据ID查询服务开始，id为：{}", this.getClass().getSimpleName(), id);
        T result = getService().selectOne(id);

        D dto = getDTO();
        GaeaBeanUtils.copyAndFormatter(result, dto);
        ResponseBean responseBean = responseSuccessWithData(resultDtoHandle(dto));
        logger.info("{}根据ID查询结束，结果：{}", this.getClass().getSimpleName(), GaeaUtils.toJSONString(responseBean));
        return responseBean;
    }

    /**
     * 对明细结果进行处理,子类可以覆盖
     * @param d
     * @return
     */
    protected D resultDtoHandle(D d) {
        return d;
    }

    /**
     * 插入
     *
     * @param dto
     * @return
     * @throws Exception
     */
    @PostMapping
    public ResponseBean insert(@Validated @RequestBody D dto) {
        logger.info("{}新增服务开始，参数：{}", this.getClass().getSimpleName(), GaeaUtils.toJSONString(dto));

        ResponseBean responseBean = responseSuccess();
        T entity = getEntity();
        //dto转为数据库实体
        BeanUtils.copyProperties(dto, entity);
        //插入
        getService().insert(entity);

        logger.info("{}新增服务结束，结果：{}", this.getClass().getSimpleName(), GaeaUtils.toJSONString(responseBean));
        return responseBean;
    }


    /**
     * 根据ID修改对饮记录
     *
     * @param dto
     * @return
     * @throws Exception
     */
    @PutMapping
    public ResponseBean update(@Validated @RequestBody D dto) {
        String username = UserContentHolder.getContext().getUsername();
        logger.info("{}更新服务开始,更新人：{}，参数：{}", this.getClass().getSimpleName(), username, GaeaUtils.toJSONString(dto));
        T entity = getEntity();
        //dto转换entity
        BeanUtils.copyProperties(dto, entity);

        getService().update(entity);

        logger.info("{}更新服务结束，结果：{}", this.getClass().getSimpleName(), GaeaUtils.toJSONString(entity));

        return responseSuccess();
    }


    /**
     * 根据ID删除指定记录,这里被删除的记录会进入删除记录表
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseBean deleteById(@PathVariable("id") Long id) {
        logger.info("{}删除服务开始，参数ID：{}", this.getClass().getSimpleName(), id);
        getService().deleteById(id);
        logger.info("{}删除服务结束", this.getClass().getSimpleName());
        return responseSuccess();
    }

    /**
     * 删除批量ID对应的记录
     *
     * @param ids
     * @return
     */
    @PostMapping("/delete/batch")
    @Permission(value = "del")
    public ResponseBean deleteBatchIds(@RequestBody List<Long> ids) {
        logger.info("{}批量删除服务开始，批量参数Ids：{}", this.getClass().getSimpleName(), GaeaUtils.toJSONString(ids));
        boolean deleteCount = getService().deleteByIds(ids);

        ResponseBean responseBean = responseSuccessWithData(deleteCount);

        logger.info("{}批量删除服务结束，结果：{}", this.getClass().getSimpleName(), GaeaUtils.toJSONString(responseBean));
        return responseBean;
    }


    /**
     * 构建成功响应实例
     *
     * @return
     */
    public ResponseBean responseSuccess() {
        return builder().build();
    }


    /**
     * 构建成功响应实例
     *
     * @param code 响应编码
     * @param args 响应参数
     * @return
     */
    public ResponseBean responseSuccess(String code, Object... args) {
        return builder().code(code).args(args).build();
    }

    /**
     * 构建成功响应实例
     *
     * @param data
     * @return
     */
    public ResponseBean responseSuccessWithData(Object data) {
        return builder().data(data).build();
    }

    /**
     * 构建成功响应实例
     * @param code 响应编码
     * @param data 响应数据
     * @param args 响应参数
     * @return
     */
    public ResponseBean responseSuccessWithData(String code, Object data, Object... args) {
        return builder().code(code).data(data).args(args).build();
    }

    /**
     * 构建失败响应实例
     *
     * @return
     */
    public ResponseBean failure() {
        return builder().code(ResponseCode.FAIL_CODE).build();
    }

    /**
     * 构建失败响应实例
     *
     * @param code 响应编码
     * @param args 响应参数
     * @return
     */
    public ResponseBean failure(String code, Object... args) {
        return builder().code(code).args(args).build();
    }

    /**
     * 构建失败响应实例,包含响应数据
     * @param code 响应编码
     * @param data 响应数据
     * @param args 响应参数
     * @return
     */
    public ResponseBean failureWithData(String code, Object data, Object... args) {
        return builder().code(code).args(args).data(data).build();
    }
}
