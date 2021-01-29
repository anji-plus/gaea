package com.github.anji.plus.gaea.curd.service;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.anji.plus.gaea.annotation.UnionUnique;
import com.github.anji.plus.gaea.annotation.UnionUniqueCode;
import com.github.anji.plus.gaea.annotation.Unique;
import com.github.anji.plus.gaea.constant.BaseOperationEnum;
import com.github.anji.plus.gaea.constant.GaeaConstant;
import com.github.anji.plus.gaea.curd.dto.Query;
import com.github.anji.plus.gaea.curd.entity.GaeaBaseEntity;
import com.github.anji.plus.gaea.curd.mapper.GaeaBaseMapper;
import com.github.anji.plus.gaea.curd.params.PageParam;
import com.github.anji.plus.gaea.exception.BusinessException;
import com.github.anji.plus.gaea.exception.BusinessExceptionBuilder;
import com.github.anji.plus.gaea.utils.GaeaAssert;
import com.github.anji.plus.gaea.utils.GaeaUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.property.PropertyNamer;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static com.github.anji.plus.gaea.code.ResponseCode.*;


/**
 * 基础service
 *
 * @author lirui
 * @since 2021-01-12
 */
public interface GaeaBaseService<P extends PageParam, T extends GaeaBaseEntity> {
    /**
     * 获取直接操作数据库接口
     *
     * @return
     */
    GaeaBaseMapper<T> getMapper();

    /**
     * 包装返回实体
     *
     * @param entity
     * @return
     */
    default T wrapperEntity(T entity) {
        return entity;
    }

    /**
     * 根据id查询记录
     *
     * @param id
     * @return
     */
    default T selectOne(Long id) {
        T t = getMapper().selectById(id);
        GaeaAssert.notNull(t, RECORD_NO_EXIST);
        return wrapperEntity(t);
    }


    /**
     * 根据id查询记录
     *
     * @param column 字段
     * @param value  字段对应的值
     * @return
     */
    default T selectOne(String column, Object value) {
        List<T> list = list(column, value);

        if (list.isEmpty()) {
            throw BusinessExceptionBuilder.build(RECORD_NO_EXIST);
        }

        if (list.size() > 1) {
            throw BusinessExceptionBuilder.build(MULTI_RECORDS);
        }
        return wrapperEntity(list.get(0));
    }

    /**
     * 分页
     *
     * @param pageParam
     * @return
     */
    default IPage<T> page(P pageParam) {
        return page(pageParam, null);
    }

    /**
     * 分页，指定查询条件即忽略扩展的条件
     *
     * @param pageParam
     * @param wrapper   指定参数
     * @return
     */
    default IPage<T> page(P pageParam, Wrapper<T> wrapper) {
        Page<T> page = new Page<>();
        page.setCurrent(pageParam.getPageNumber());
        page.setSize(pageParam.getPageSize());

        //设置排序
        if (StringUtils.equals(GaeaConstant.ASC, pageParam.getOrder()) && StringUtils.isNotBlank(pageParam.getOrder())) {
            page.addOrder(OrderItem.asc(pageParam.getSort()));
        } else if (StringUtils.equals(GaeaConstant.DESC, pageParam.getOrder()) && StringUtils.isNotBlank(pageParam.getOrder())) {
            page.addOrder(OrderItem.desc(pageParam.getSort()));
        } else {
            page.addOrder(OrderItem.desc(GaeaConstant.ID));
        }
        //当有自定义条件时，去掉参数组成的查询条件
        if (wrapper != null) {
            return resultHandler(getMapper().selectPage(page, wrapper));
        }

        return resultHandler(getMapper().selectPage(page, extensionWrapper(pageParam, getWrapper(pageParam))));
    }


    /**
     * 排序结果处理(作用：需要对排序结果进行处理时，使用)
     *
     * @param iPage
     * @return
     */
    default IPage<T> resultHandler(IPage<T> iPage) {
        return iPage;
    }

    /**
     * 扩展查询条件
     *
     * @param param   查询参数
     * @param wrapper 基本查询条件
     * @return
     */
    default Wrapper<T> extensionWrapper(P param, QueryWrapper<T> wrapper) {
        return wrapper;
    }

    /**
     * 抽象查询条件
     *
     * @param param 查询条件
     * @return
     */
    default QueryWrapper<T> getWrapper(P param) {

        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        //条件的值
        Field[] fields = param.getClass().getDeclaredFields();

        Arrays.stream(fields).forEach(field -> {
            try {
                boolean flag;
                field.setAccessible(true);
                //列名
                String column;
                if (field.isAnnotationPresent(Query.class) && StringUtils.isNotBlank(field.getAnnotation(Query.class).column())) {
                    column = field.getAnnotation(Query.class).column();
                } else {
                    column = GaeaUtils.camelToUnderline(field.getName());
                }

                if (field.get(param) instanceof String) {
                    flag = StringUtils.isNoneBlank((String) field.get(param));
                } else {
                    flag = field.get(param) != null;
                }
                if (!flag) {
                    return;
                }

                //判断是否是模糊查询
                if (field.isAnnotationPresent(Query.class)) {
                    switch (field.getAnnotation(Query.class).value()) {
                        case LIKE:
                            queryWrapper.like(column, field.get(param));
                            break;
                        case IN:
                            Object value = field.get(param);
                            if (value instanceof String && ((String) value).contains(GaeaConstant.SPLIT)) {
                                String[] split = ((String) value).split(GaeaConstant.SPLIT);
                                List<String> list = Arrays.asList(split);
                                queryWrapper.in(column, list);
                            }
                            break;
                        case GT:
                            queryWrapper.gt(column, field.get(param));
                            break;
                        case LT:
                            queryWrapper.lt(column, field.get(param));
                            break;
                        case BWT:
                            String[] split = column.split(GaeaConstant.SPLIT);
                            queryWrapper.lt(split[0], field.get(param))
                                    .gt(split[1], field.get(param));
                            break;
                        default:
                            queryWrapper.eq(column, field.get(param));
                    }

                } else {
                    queryWrapper.eq(column, field.get(param));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return queryWrapper;
    }

    /**
     * 操作前处理
     *
     * @param entity        前端传递的对象
     * @param operationEnum 操作类型
     * @throws BusinessException 阻止程序继续执行或回滚事务
     */
    default void processBeforeOperation(T entity, BaseOperationEnum operationEnum) throws BusinessException {
    }

    /**
     * 操作后续处理
     *
     * @param entity
     * @param operationEnum 操作类型
     * @throws BusinessException 阻止程序继续执行或回滚事务
     */
    default void processAfterOperation(T entity, BaseOperationEnum operationEnum) throws BusinessException {
    }

    /**
     * lambda表达式转换成列名
     *
     * @param function
     * @return
     */
    default String getColumn(SFunction<T, ?> function) {
        SerializedLambda lambda = LambdaUtils.resolve(function);
        String fieldName = PropertyNamer.methodToProperty(lambda.getImplMethodName());
        Class<?> implClass = lambda.getImplClass();

        try {
            Field field = implClass.getDeclaredField(fieldName);
            if (field.isAnnotationPresent(TableField.class)) {
                fieldName = field.getAnnotation(TableField.class).value();
                return fieldName;
            }
        } catch (NoSuchFieldException e) {

        }
        return GaeaUtils.camelToUnderline(fieldName);
    }


    /**
     * 保存数据
     *
     * @param entity
     * @return
     * @throws BusinessException 业务异常
     */
    @Transactional(rollbackFor = Exception.class)
    default Integer insert(T entity) throws BusinessException {
        //保存前处理
        processBeforeOperation(entity, BaseOperationEnum.INSERT);

        //校验唯一索引
        checkUniqueField(entity, false);
        //保存
        Integer result = getMapper().insert(entity);

        //保存失败
        if (result == null || result < 1) {
            throw BusinessExceptionBuilder.build(INSERT_FAILURE);
        }

        //保存后处理
        processAfterOperation(entity, BaseOperationEnum.INSERT);

        return result;
    }

    /**
     * 保存数据
     *
     * @param entities
     * @return
     * @throws BusinessException 业务异常
     */
    @Transactional(rollbackFor = Exception.class)
    default Integer insertBatch(List<T> entities) throws BusinessException {

        //保存
        Integer result = getMapper().insertBatch(entities);

        //保存失败
        if (result == null || result < 1) {
            throw BusinessExceptionBuilder.build(INSERT_FAILURE);
        }
        return result;
    }

    /**
     * 更新数据
     *
     * @param entity
     * @return
     * @throws BusinessException 业务异常
     */
    @Transactional(rollbackFor = Exception.class)
    default Integer update(T entity) throws BusinessException {
        //更新前处理
        processBeforeOperation(entity, BaseOperationEnum.UPDATE);
        //校验唯一索引
        checkUniqueField(entity, true);
        //更新
        Integer result = getMapper().updateById(entity);

        //更新失败
        if (result == null || result < 1) {
            throw BusinessExceptionBuilder.build(UPDATE_FAILURE);
        }

        //更新后处理
        processAfterOperation(entity, BaseOperationEnum.UPDATE);
        return result;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    default Integer deleteById(Serializable id) {
        T t = getById(id);
        if (t == null) {
            throw BusinessExceptionBuilder.build(RECORD_NO_EXIST);
        }
        //删除前处理
        processBeforeOperation(t, BaseOperationEnum.DELETE);
        Integer result = getMapper().deleteById(id);

        //删除失败
        if (result == null || result < 1) {
            throw BusinessExceptionBuilder.build(DELETE_FAILURE);
        }

        //删除后处理
        processAfterOperation(t, BaseOperationEnum.DELETE);
        return result;
    }

    /**
     * 批量删除
     *
     * @param idList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean deleteByIds(Collection<? extends Serializable> idList) {
        return SqlHelper.retBool(getMapper().deleteBatchIds(idList));
    }

    /**
     * 删除
     *
     * @param lambdaQueryWrapper
     */
    @Transactional(rollbackFor = Exception.class)
    default void delete(LambdaQueryWrapper<T> lambdaQueryWrapper) {
        getMapper().delete(lambdaQueryWrapper);
    }

    /**
     * 校验唯一
     *
     * @param entity   实体对象
     * @param isUpdate 是否是更新
     */
    default void checkUniqueField(T entity, boolean isUpdate) {
        //获取所有属性
        Field[] fields = entity.getClass().getDeclaredFields();

        //判断单一索引
        for (Field field : fields) {
            if (field.isAnnotationPresent(Unique.class)) {
                Unique unique = field.getDeclaredAnnotation(Unique.class);
                QueryWrapper<T> wrapper = Wrappers.query();
                Integer integer;
                try {
                    Object value = getFieldValue(entity, field);
                    //如果没有指定列，默认是字段的驼峰转下划线
                    String column;
                    if (StringUtils.isBlank(unique.column())) {
                        //字段，驼峰转下划线
                        column = GaeaUtils.camelToUnderline(field.getName());
                    } else {
                        column = unique.column();
                    }
                    wrapper.eq(column, value);
                    if (isUpdate) {
                        wrapper.ne(GaeaConstant.ID, entity.getId());
                    }
                    integer = getMapper().selectCount(wrapper);
                } catch (Exception e) {
                    continue;
                }
                if (integer > 0) {
                    throw BusinessExceptionBuilder.build(unique.code(), field.getName());
                }
            }
        }

        //判断联合索引
        //用户存放各分组的聚合索引
        Map<String, QueryWrapper<T>> unionUniqueMap = new HashMap<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(UnionUnique.class)) {
                try {
                    UnionUnique[] unionUniques = field.getDeclaredAnnotationsByType(UnionUnique.class);
                    for (UnionUnique unionUnique : unionUniques) {
                        String group = unionUnique.group();
                        Object value = getFieldValue(entity, field);
                        String column;
                        if (StringUtils.isBlank(unionUnique.column())) {
                            //字段，驼峰转下划线
                            column = GaeaUtils.camelToUnderline(field.getName());
                        } else {
                            column = unionUnique.column();
                        }
                        if (unionUniqueMap.containsKey(group)) {
                            QueryWrapper<T> unionWrapper = unionUniqueMap.get(group);
                            unionWrapper.eq(column, value);
                        } else {
                            QueryWrapper<T> unionWrapper = Wrappers.query();
                            unionWrapper.eq(column, value);
                            unionUniqueMap.put(group, unionWrapper);
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }

        //遍历聚集索引
        unionUniqueMap.entrySet().stream().forEach(entry -> {
            QueryWrapper<T> queryWrapper = entry.getValue();
            if (isUpdate) {
                queryWrapper.ne(GaeaConstant.ID, entity.getId());
            }
            //查询
            Integer result = getMapper().selectCount(queryWrapper);

            if (result > 0) {
                String group = entry.getKey();
                //错误提示
                Class<? extends GaeaBaseEntity> aClass = entity.getClass();
                UnionUniqueCode[] unionUniqueCodes = aClass.getAnnotationsByType(UnionUniqueCode.class);

                for (UnionUniqueCode unionUniqueCode : unionUniqueCodes) {
                    if (StringUtils.equals(unionUniqueCode.group(), group)) {
                        throw BusinessExceptionBuilder.build(unionUniqueCode.code());
                    }
                }
            }
        });


    }

    /**
     * 获取属性值
     *
     * @param entity
     * @param field
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    default Object getFieldValue(T entity, Field field) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), entity.getClass());
        Method readMethod = propertyDescriptor.getReadMethod();
        return readMethod.invoke(entity);
    }

    /**
     * 根据指定字段查询对应的值
     *
     * @param column
     * @param value
     * @return
     */
    default List<T> list(String column, Object value) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(column, value);
        return getMapper().selectList(queryWrapper);
    }

    /**
     * 查询数量
     *
     * @param wrapper
     * @return
     */
    default Integer count(Wrapper<T> wrapper) {
        return getMapper().selectCount(wrapper);
    }

    /**
     * 根据指定条件查询对应的记录
     *
     * @param wrapper
     * @return
     */
    default List<T> list(Wrapper<T> wrapper) {
        return getMapper().selectList(wrapper);
    }

    /**
     * ResponseBean
     * 根据ID查询记录
     *
     * @param id
     * @return
     */
    default T getById(Serializable id) {
        return getMapper().selectById(id);
    }

    /**
     * 查询所有
     *
     * @return
     */
    default List<T> findAll() {
        return getMapper().selectList(Wrappers.emptyWrapper());
    }

    /**
     * 根据指定字段更新值
     *
     * @param id
     * @param column
     * @param value
     */
    default Integer updateColumn(Long id, String column, Object value) {
        Map<String, Object> params = new HashMap<>(1);
        params.put(column, value);
        return getMapper().updateFieldsById(params, id);
    }

}
