package com.anjiplus.gaea.archiver.service;

import java.util.Date;
import java.util.List;
/**
 * 数据库操作接口定义类
 *
 * @author 木子李·De
 * @since 2021/2/3 14:16
 */
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

    /** 查询本次归档的任务，跨了哪几个月份
     * @param tablename
     * @param timefield
     * @param maxDaysBeforeArchive
     * @return
     */
    List<String> getCrossMonthList(String tablename, String timefield, Integer maxDaysBeforeArchive);

    /** 创建归档表
     * @param sourceTableName
     * @param monthStr
     * @return
     */
    String createArchiverTableIfNotExist(String sourceTableName, String monthStr);

    /** 执行归档
     * @param sourceTableName
     * @param startTime
     * @param endTime
     * @return
     */
    boolean doArchiver(String sourceTableName, Date startTime, Date endTime);
}
