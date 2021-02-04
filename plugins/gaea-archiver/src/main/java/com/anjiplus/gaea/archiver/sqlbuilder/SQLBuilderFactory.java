package com.anjiplus.gaea.archiver.sqlbuilder;

import com.anjiplus.gaea.archiver.consant.DBType;

import java.util.Optional;
/**
 * SQL构建器工厂
 *
 * @author 木子李·De
 * @since 2021/2/3 14:16
 */
public class SQLBuilderFactory {

    private static final SQLBuilderRegistry sqlBuilderRegistry = new SQLBuilderRegistry();

    public static ISQLBuilder getSQLBuilder(DBType dbType){
        return Optional.ofNullable(sqlBuilderRegistry.getSQLBuilder(dbType)).orElseThrow(()->
            new RuntimeException(String.format("%s database not supported", dbType.getDb()))
        );
    }
}
