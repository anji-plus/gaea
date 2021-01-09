package com.anji.mirror.generator;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;

public class DataSourceBuild {

    final static String driverName = "com.mysql.cj.jdbc.Driver";
    final static String url = "jdbc:mysql://10.108.26.151:3306/nla_auth?characterEncoding=utf8&useSSL=true";
    final static String username = "cst-admin";
    final static String password = "cst-admin@dev1234";

    private static DataSourceBuild instance;

    public static DataSourceBuild getInstance(){
        if(instance == null){
            instance= new DataSourceBuild();
        }
        return instance;
    }

    private DataSourceConfig dataSourceConfig;

    public DataSourceConfig getSource(){
        if(dataSourceConfig != null){
            return dataSourceConfig;
        }
        dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDriverName(driverName).
                setUrl(url)
                .setUsername(username)
                .setPassword(password);
        return dataSourceConfig;
    }

}
