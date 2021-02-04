package com.anjiplus.gaea.archiver.service;

import com.anjiplus.gaea.archiver.config.ArchiverTable;

import java.util.List;
/**
 * 归档业务接口定义类
 *
 * @author 木子李·De
 * @since 2021/2/3 14:16
 */
public interface IArchiverService {

    /**检查配置项中配置的归档表，校验表名列名是否正确
     * @return 有效的List<ArchiverTable>
     */
    List<ArchiverTable> validNeedArchiverTable();


    /**归档一张表
     * @param archiverTable
     */
    void archiveTable(ArchiverTable archiverTable);


    /** 删除旧的归档表
     * @param archiverTable
     */
    void dropOldArchive(ArchiverTable archiverTable);

    /**
     * 执行归档任务
     */
    void doArchiveTable();

}
