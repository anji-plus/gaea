package com.anjiplus.gaea.archiver.config;
/**
 * 归档组件配置项
 *
 * @author 木子李·De
 * @since 2021/2/3 14:16
 */
public class ArchiverTable {

    /* 要归档的表名 */
    private String tablename;

    /* 归档依据的时间列名 */
    private String timefield;

    /* 归档多久之前的数据，将n天前的数据，移动到归档表 */
    private Integer maxDaysBeforeArchive;

    /* 删除多久之前的历史数据，归档表中，已归档的数据保留期限，超过期限的数据将删除。*/
    private Integer maxDaysBeforeDelete;

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getTimefield() {
        return timefield;
    }

    public void setTimefield(String timefield) {
        this.timefield = timefield;
    }

    public Integer getMaxDaysBeforeArchive() {
        return maxDaysBeforeArchive;
    }

    public void setMaxDaysBeforeArchive(Integer maxDaysBeforeArchive) {
        this.maxDaysBeforeArchive = maxDaysBeforeArchive;
    }

    public Integer getMaxDaysBeforeDelete() {
        return maxDaysBeforeDelete;
    }

    public void setMaxDaysBeforeDelete(Integer maxDaysBeforeDelete) {
        this.maxDaysBeforeDelete = maxDaysBeforeDelete;
    }
}
