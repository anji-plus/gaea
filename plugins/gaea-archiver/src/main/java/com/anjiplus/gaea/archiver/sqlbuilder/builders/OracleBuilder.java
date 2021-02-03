package com.anjiplus.gaea.archiver.sqlbuilder.builders;

import com.anjiplus.gaea.archiver.sqlbuilder.ISQLBuilder;

import java.util.Date;
/**
 * oracle相关sql语句
 *
 * @author 木子李·De
 * @since 2021/2/3 14:16
 */
public class OracleBuilder implements ISQLBuilder {
    @Override
    public String existTable(String tableName) {
        return null;
    }

    @Override
    public String existTableField(String tableName, String fieldName) {
        return null;
    }

    @Override
    public String getNow() {
        return null;
    }

    @Override
    public String countRows(String sourceTableName) {
        return null;
    }

    @Override
    public String getCrossMonthList(String tablename, String timefield, Integer maxDaysBeforeArchive) {
        return null;
    }

    @Override
    public String createArchiverTableIfNotExist(String archiverTableName, String sourceTableName) {
        return null;
    }

    @Override
    public String doArchiver(String sourceTableName, Date startTime, Date endTime) {
        return null;
    }
}
