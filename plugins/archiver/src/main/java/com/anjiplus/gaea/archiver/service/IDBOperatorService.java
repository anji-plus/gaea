package com.anjiplus.gaea.archiver.service;

import java.util.Date;

public interface IDBOperatorService {

    /** 判断表名是否存在
     * @param tableName
     * @return
     */
    boolean existTable(String tableName);

    /** 判断表中列名是否存在
     * @param tableName
     * @param fieldName
     * @return
     */
    boolean existTableField(String tableName, String fieldName);

    /** 获取数据库当前时间
     * @return
     */
    Date getNow();

    /** 统计有多少条数据要归档
     * @param sourceTableName
     * @return
     */
    Long countRows(String sourceTableName);

    /**创建归档表
     * @param sourceTableName
     * @param monthStr
     * @return
     */
    String createArchiverTableIfNotExist(String sourceTableName, String monthStr);

    /**执行归档
     * @param sourceTableName
     * @param startTime
     * @param endTime
     * @return
     */
    boolean doArchiver(String sourceTableName, Date startTime, Date endTime);
}
