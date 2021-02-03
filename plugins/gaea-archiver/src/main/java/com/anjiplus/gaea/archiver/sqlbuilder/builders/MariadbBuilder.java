package com.anjiplus.gaea.archiver.sqlbuilder.builders;

import com.anjiplus.gaea.archiver.sqlbuilder.ISQLBuilder;

import java.util.Date;
/**
 * mariadb相关sql语句
 *
 * @author 木子李·De
 * @since 2021/2/3 14:16
 */
public class MariadbBuilder implements ISQLBuilder {

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
    public String getCrossMonthList(String tablename, String timefield, Integer maxDaysBeforeArchive) {
        String sql = String.format("select date_format(%s,'%%Y-%%m') month from %s where %s < DATE_SUB(NOW(), interval %d day) group by date_format(%s,'%%Y-%%m')", timefield, tablename, timefield, maxDaysBeforeArchive, timefield);
        return sql;
    }

    @Override
    public String createArchiverTableIfNotExist(String archiveTableName, String sourceTableName) {
        String sql = String.format("create table %s like %s", archiveTableName, sourceTableName);
        return sql;
    }


    @Override
    public String doArchiver(String sourceTableName, Date startTime, Date endTime) {
        return null;
    }
}