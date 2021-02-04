package com.anjiplus.gaea.archiver.sqlbuilder;

import com.anjiplus.gaea.archiver.consant.DBType;
import com.anjiplus.gaea.archiver.sqlbuilder.builders.MariadbBuilder;
import com.anjiplus.gaea.archiver.sqlbuilder.builders.MysqlBuilder;
import com.anjiplus.gaea.archiver.sqlbuilder.builders.OracleBuilder;
import com.anjiplus.gaea.archiver.sqlbuilder.builders.SqlserverBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 * SQL构建器 注册类
 *
 * @author 木子李·De
 * @since 2021/2/3 14:16
 */
public class SQLBuilderRegistry {
    private final Map<DBType, ISQLBuilder> sqlBuilderMap = new HashMap<DBType, ISQLBuilder>();

    public SQLBuilderRegistry(){
        this.sqlBuilderMap.put(DBType.MYSQL, new MysqlBuilder());
        this.sqlBuilderMap.put(DBType.MARIADB, new MariadbBuilder());
        this.sqlBuilderMap.put(DBType.ORACLE, new OracleBuilder());
        this.sqlBuilderMap.put(DBType.SQL_SERVER, new SqlserverBuilder());
    }

    public ISQLBuilder getSQLBuilder(DBType dbType){
        return this.sqlBuilderMap.get(dbType);
    }

    public Collection<ISQLBuilder> getSQLBuilders(){
        return Collections.unmodifiableCollection(this.sqlBuilderMap.values());
    }
}
