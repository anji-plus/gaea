package com.anjiplus.gaea.archiver.service;

import com.anjiplus.gaea.archiver.consant.DBType;
import com.anjiplus.gaea.archiver.sqlbuilder.ISQLBuilder;
import com.anjiplus.gaea.archiver.sqlbuilder.SQLBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Service
public class DBOperatorService implements IDBOperatorService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private DBType getDBType() throws SQLException {
        String databaseProductName=jdbcTemplate.getDataSource().getConnection().getMetaData().getDatabaseProductName();
        if(databaseProductName == null || databaseProductName.trim().length() == 0){
            throw new RuntimeException(String.format("%s database not supported", databaseProductName));
        }
        return Optional.ofNullable(DBType.getDbType(databaseProductName)).orElseThrow(()->
            new RuntimeException(String.format("%s database not supported", databaseProductName))
        );
    }

    private ISQLBuilder getSQLBuilder(){
        try{
            return SQLBuilderFactory.getSQLBuilder(getDBType());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean existTable(String tableName) {
        try{
            String sql = getSQLBuilder().existTable(tableName);
            jdbcTemplate.queryForRowSet(sql);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean existTableField(String tableName, String fieldName) {
        try{
            String sql = getSQLBuilder().existTableField(tableName, fieldName);
            SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);

            boolean existField = false;
            while (sqlRowSet.next()){
                String fileName = sqlRowSet.getString("Field");
                if(fileName.toLowerCase().trim().equals(fieldName.trim())){
                    existField = true;
                    break;
                }
            }
            return existField;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Date getNow() {
        String sql = getSQLBuilder().getNow();
        Map<String, Object> map = jdbcTemplate.queryForMap(sql);
        Date now = (Date)map.get("now");
        return null;
    }

    @Override
    public Long countRows(String sourceTableName) {
        return null;
    }

    @Override
    public List<String> getCrossMonthList(String tablename, String timefield, Integer maxDaysBeforeArchive) {
        String sql = getSQLBuilder().getCrossMonthList(tablename, timefield, maxDaysBeforeArchive);
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

        //yyyy-MM
        List<String> monthList = new ArrayList<String>();
        if(list == null ||list.isEmpty()){
            return monthList;
        }

        for(Map<String, Object> item: list){
            String monthStr = (String) item.get("month");
            monthList.add(monthStr);
        }
        return monthList;
    }

    @Override
    public String createArchiverTableIfNotExist(String sourceTableName, String monthStr){
        monthStr = monthStr.replace("-","");
        String archiveTableName = String.format("%s_%s", sourceTableName, monthStr);
        if(existTable(archiveTableName) == false){
            String sql = getSQLBuilder().createArchiverTableIfNotExist(archiveTableName, sourceTableName);
            jdbcTemplate.execute(sql);
        }
        return archiveTableName;
    }

    @Override
    public boolean doArchiver(String sourceTableName, Date startTime, Date endTime) {
        return false;
    }
}
