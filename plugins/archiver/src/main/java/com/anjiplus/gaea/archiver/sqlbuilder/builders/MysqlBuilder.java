package com.anjiplus.gaea.archiver.sqlbuilder.builders;

import com.anjiplus.gaea.archiver.sqlbuilder.SQLBuilder;

import java.util.Date;

public class MysqlBuilder implements SQLBuilder {

    @Override
    public String existTable(String tableName) {
        String sql = String.format("desc %s", tableName);
        return sql;
    }

    @Override
    public String existTableField(String tableName, String fieldName) {
        String sql = String.format("desc %s", tableName);
        return sql;
    }

    @Override
    public String getNow() {
        String sql="select now() as now";
        return sql;
    }

    @Override
    public String countRows(String sourceTableName) {
        return null;
    }

    @Override
    public String getArchiverTableName(String sourceTableName, String monthStr) {
        monthStr = monthStr.replace("-","");
        String archiveTableName = String.format("%s_%s", sourceTableName, monthStr);
        return archiveTableName;
    }

    @Override
    public String createArchiverTableIfNotExist(String sourceTableName, String monthStr) {
        String archiveTableName = getArchiverTableName( sourceTableName, monthStr);
        String sql = String.format("create table %s like %s", archiveTableName, sourceTableName);
        return sql;
    }

    @Override
    public String doArchiver(String sourceTableName, Date startTime, Date endTime) {
        return null;
    }
}
