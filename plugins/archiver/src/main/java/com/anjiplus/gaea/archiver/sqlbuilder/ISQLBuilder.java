package com.anjiplus.gaea.archiver.sqlbuilder;

import java.util.Date;

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
