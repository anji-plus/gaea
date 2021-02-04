package com.anjiplus.gaea.archiver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
/**
 * 归档组件配置项
 *
 * @author 木子李·De
 * @since 2021/2/3 14:16
 */
@ConfigurationProperties(ArchiverProperties.PREFIX)
public class ArchiverProperties {
    public static final String PREFIX = "spring.anjiplus.gaea.archiver";

    private boolean enabled = true;

    /* 归档触发的定时器 */
    private String archiveScheduledCron;

    /* 将n天前的数据，移动到归档表，tables配置项中策略优先加载 */
    private Integer maxDaysBeforeArchive;

    /* 归档表中，已归档的数据保留期限，超过期限的数据将删除。tables配置项中策略优先加载 */
    private Integer maxDaysBeforeDelete;

    /* 归档表配置 */
    private List<ArchiverTable> tables = new ArrayList<ArchiverTable>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getArchiveScheduledCron() {
        return archiveScheduledCron;
    }

    public void setArchiveScheduledCron(String archiveScheduledCron) {
        this.archiveScheduledCron = archiveScheduledCron;
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

    public List<ArchiverTable> getTables() {
        return tables;
    }

    public void setTables(List<ArchiverTable> tables) {
        this.tables = tables;
    }
}
