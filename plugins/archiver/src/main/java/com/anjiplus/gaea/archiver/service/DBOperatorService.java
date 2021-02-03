package com.anjiplus.gaea.archiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class MySQLOperatorService implements IDBOperatorService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean existTable(String tableName) {
        String sql = String.format("desc %s", tableName);
        try{
            jdbcTemplate.queryForRowSet(sql);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean existTableField(String tableName, String fieldName) {
        String sql = String.format("desc %s", tableName);
        try{
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
        String sql="select now() as now";
        Map<String, Object> map = jdbcTemplate.queryForMap(sql);
        Date now = (Date)map.get("now");
        return null;
    }

    @Override
    public Long countRows(String sourceTableName) {
        return null;
    }

    @Override
    public String createArchiverTableIfNotExist(String sourceTableName, String monthStr){
        monthStr = monthStr.replace("-","");
        String archiveTableName = String.format("%s_%s", sourceTableName, monthStr);
        if(existTable(archiveTableName) == false){
            String sql = String.format("create table %s like %s", archiveTableName, sourceTableName);
            jdbcTemplate.execute(sql);
        }
        return archiveTableName;
    }

    @Override
    public boolean doArchiver(String sourceTableName, Date startTime, Date endTime) {
        return false;
    }
}
