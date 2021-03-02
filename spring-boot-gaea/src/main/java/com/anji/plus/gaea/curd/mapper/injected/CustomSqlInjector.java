package com.anji.plus.gaea.curd.mapper.injected;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.anji.plus.gaea.curd.mapper.methods.CustomInsertBatch;
import com.anji.plus.gaea.curd.mapper.methods.UpdateFieldsById;

import java.util.List;

/**
 * 自定义注入
 * @author lr
 * @since 2021-01-12
 */
public class CustomSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new CustomInsertBatch());
        methodList.add(new UpdateFieldsById());
        return methodList;
    }
}
