package com.anji.plus.gaea.config;

import com.anji.plus.gaea.holder.UserContentHolder;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 自动补充插入或更新时的值
 * @author lr
 * @since 2021-01-24 18:26
 */
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        String username = UserContentHolder.getContext().getUsername();
        this.setFieldValByName("createBy", username,metaObject);
        this.setFieldValByName("createTime", new Date(),metaObject);
        this.setFieldValByName("updateBy", username,metaObject);
        this.setFieldValByName("updateTime", new Date(),metaObject);
        this.setFieldValByName("version", Integer.valueOf(1),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String username = UserContentHolder.getContext().getUsername();
        this.setFieldValByName("updateBy", username,metaObject);
        this.setFieldValByName("updateTime", new Date(),metaObject);
        this.setFieldValByName("version", this.getFieldValByName("version",metaObject),metaObject);
    }
}
