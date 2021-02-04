package com.anjiplus.gaea.archiver.service;

import com.anjiplus.gaea.archiver.config.ArchiverProperties;
import com.anjiplus.gaea.archiver.config.ArchiverTable;
import com.anjiplus.gaea.archiver.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 归档业务实现类
 *
 * @author 木子李·De
 * @since 2021/2/3 14:16
 */
@Service
public class ArchiverService implements IArchiverService{

    private static Logger logger = LoggerFactory.getLogger(ArchiverService.class);

    @Autowired
    private ArchiverProperties archiverProperties;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    private IDBOperatorService dbOperatorService;

    @Override
    public List<ArchiverTable> validNeedArchiverTable(){
        //配置项检查
        List<ArchiverTable> needArchiveTables = new ArrayList<ArchiverTable>();
        for(ArchiverTable archiverTable: archiverProperties.getTables()){
            //判断表名是否为空
            if(archiverTable.getTablename() == null || archiverTable.getTablename().trim().equals("")){
                logger.warn("gaea archiver detect a blank table, please check your configuration");
                continue;
            }

            String tableName = archiverTable.getTablename().trim();
            //判断列名是否为空
            if(archiverTable.getTimefield() == null || archiverTable.getTimefield().trim().equals("")){
                logger.warn("gaea archiver detect a blank field name for table [{}], please check your configuration", tableName);
                continue;
            }
            String fieldName = archiverTable.getTimefield().trim();

            //判断表名、列名是否存在
            boolean isExist = dbOperatorService.existTableField(tableName, fieldName);
            if(isExist == false){
                logger.warn("gaea archiver detect field [{}] not exist in table [{}], please check your configuration", fieldName, tableName);
                continue;
            }
            //判断配置项，归档多久之前的数据
            if(archiverTable.getMaxDaysBeforeArchive() == null || archiverTable.getMaxDaysBeforeArchive().intValue() <= 0){
                if(archiverProperties.getMaxDaysBeforeArchive() != null && archiverTable.getMaxDaysBeforeArchive().intValue() > 0){
                    archiverTable.setMaxDaysBeforeArchive(archiverProperties.getMaxDaysBeforeArchive());
                }else{
                    logger.warn("gaea archiver task will be disable for table [{}], because detect maxDaysBeforeArchive less than 0.", tableName);
                    continue;
                }
            }
            //判断配置项，删除多久之前的历史数据
            if(archiverTable.getMaxDaysBeforeDelete() == null || archiverTable.getMaxDaysBeforeDelete().intValue() <= 0){
                if(archiverProperties.getMaxDaysBeforeDelete() != null && archiverProperties.getMaxDaysBeforeDelete().intValue() > 0){
                    archiverTable.setMaxDaysBeforeDelete(archiverProperties.getMaxDaysBeforeDelete());
                }
            }
            if(archiverTable.getMaxDaysBeforeDelete() != null && archiverTable.getMaxDaysBeforeDelete().intValue() <= archiverTable.getMaxDaysBeforeArchive().intValue()){
                archiverTable.setMaxDaysBeforeDelete(null);
                logger.warn("gaea delete task will be disable for table [{}], because maxDaysBeforeDelete {} less than maxDaysBeforeArchive {}", tableName, archiverTable.getMaxDaysBeforeDelete(), archiverTable.getMaxDaysBeforeArchive());
            }
            needArchiveTables.add(archiverTable);
        }

        return needArchiveTables;
    }

    @Override
    public void archiveTable(ArchiverTable archiverTable){
        String tablename = archiverTable.getTablename();
        String timefield = archiverTable.getTimefield();
        Integer maxDaysBeforeArchive = archiverTable.getMaxDaysBeforeArchive();
        Integer maxDaysBeforeDelete = archiverTable.getMaxDaysBeforeDelete();

        //查询出历史数据，有哪些月份
        //select date_format(request_time,'%Y-%m') from t_log group by date_format(request_time,'%Y-%m');
        List<String> monthList = dbOperatorService.getCrossMonthList(tablename, timefield, maxDaysBeforeArchive);
        for(String monthStr: monthList){

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
            TransactionStatus status = dataSourceTransactionManager.getTransaction(def); // 获得事务状态

            //创建归档表
            String archiveTableName = dbOperatorService.createArchiverTableIfNotExist(tablename, monthStr);

            //获取某月第一天和最后一天
            String startDay = String.format("%s-01", monthStr);
            String endDay = DateUtil.formatDate(DateUtil.addMonth(startDay, 1));
            String archiveSQl = String.format("insert into %s select * from %s where %s>='%s' and %s<'%s'", archiveTableName, tablename, timefield, startDay, timefield, endDay);
            String deletesQL = String.format("delete from %s where %s>='%s' and %s<'%s'", tablename, timefield, startDay, timefield, endDay);
            int archivecount = jdbcTemplate.update(archiveSQl);
            int deleteCount = jdbcTemplate.update(deletesQL);
            if(archivecount != deleteCount){
                dataSourceTransactionManager.rollback(status);
            }
            dataSourceTransactionManager.commit(status);

            logger.info("archive table {} -> {} success, row count={}", tablename, archiveTableName, archivecount);
        }

    }

    @Override
    public void dropOldArchive(ArchiverTable archiverTable) {
        String tablename = archiverTable.getTablename();
        String timefield = archiverTable.getTimefield();
        Integer maxDaysBeforeArchive = archiverTable.getMaxDaysBeforeArchive();
        Integer maxDaysBeforeDelete = archiverTable.getMaxDaysBeforeDelete();

        //根据tablename，扫描所有归档表

        //根据maxDaysBeforeDelete判断归档表是否已经可以删除

        //如果已过期，删除前，先dump到服务器

        //dump完成后drop table
        
        //logger.info("drop old archive table {} success", tablename);
    }

    @Override
    public void doArchiveTable(){
        //判断归档开关是否打开
        logger.info("loading gaea archiver");
        if(archiverProperties == null || archiverProperties.isEnabled() == false || archiverProperties.getTables() == null || archiverProperties.getTables().size() == 0){
            logger.info("gaea archiver will disable, beause it's configuration item enabled = false or table list is empty");
            return;
        }
        //开始时间
        LocalDateTime startTime = LocalDateTime.now();

        //检查配置项中配置的归档表，校验表名列名是否正确
        List<ArchiverTable> archiverTableList = validNeedArchiverTable();
        //启动归档任务 删除旧归档
        for (ArchiverTable archiverTable: archiverTableList) {
            archiveTable(archiverTable);
            dropOldArchive(archiverTable);
        }

        //结束时间
        LocalDateTime endTime = LocalDateTime.now();
        long second = Duration.between(startTime, endTime).toMillis()/1000;
        logger.info("gaea archiver finish table count {}, time cost {} second", archiverTableList.size(), second);
    }

}
