package com.anji.plus.gaea.curd.mapper.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 更新指定字段
 * @author lr
 * @since 2021-01-12
 */
public class UpdateFieldsById extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        final String sql = "<script>UPDATE %s SET %s WHERE id = #{id}</script>";

        String tableName = tableInfo.getTableName();

        String updateSql = prepareUpdateSql(tableInfo);

        String formatSql = String.format(sql, tableName, updateSql);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, formatSql, modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, "updateFieldsById", sqlSource, new NoKeyGenerator(), null, null);
    }

    /**
     * 组装需要更新的sql
     * @param tableInfo
     * @return
     */
    private String prepareUpdateSql(TableInfo tableInfo) {
        final StringBuilder valueSql = new StringBuilder();
        valueSql.append("<foreach collection=\"map\" item=\"value\" index=\"key\"  separator=\",\">");
        valueSql.append("${key} = #{value}");
        valueSql.append("</foreach>");
        return valueSql.toString();
    }


}
