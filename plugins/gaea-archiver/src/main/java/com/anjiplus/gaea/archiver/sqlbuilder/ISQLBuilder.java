package com.anjiplus.gaea.archiver.sqlbuilder;

import java.util.Date;
import java.util.List;
/**
 * SQL语句构建类接口定义
 *
 * @author 木子李·De
 * @since 2021/2/3 14:16
 */
public interface ISQLBuilder {

    /** 判断表名是否存在
     * @param tableName
     * @return
     */
    String existTable(String tableName);

    /** 判断表中列名是否存在
     * @param tableName
     * @param fieldName
     * @return
     */
    String existTableField(String tableName, String fieldName);

    /** 获取数据库当前时间
     * @return
     */
    String getNow();

    /** 统计有多少条数据要归档
     * @param sourceTableName
     * @return
     */
    String countRows(String sourceTableName);

    /** 查询本次归档的任务，跨了哪几个月份
     * @param tablename
     * @param timefield
     * @param maxDaysBeforeArchive
     * @return
     */
    String getCrossMonthList(String tablename, String timefield, Integer maxDaysBeforeArchive);

    /**创建归档表
     * @param archiveTableName
     * @param sourceTableName
     * @return sql
     */
    String createArchiverTableIfNotExist(String archiveTableName, String sourceTableName);

    /**执行归档
     * @param sourceTableName
     * @param startTime
     * @param endTime
     * @return
     */
    String doArchiver(String sourceTableName, Date startTime, Date endTime);
}
