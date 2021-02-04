package com.anjiplus.gaea.archiver.consant;
/**
 * 归档组件支持的数据库类型
 *
 * @author 木子李·De
 * @since 2021/2/3 14:16
 */
public enum DBType {

    MYSQL("mysql", "MySql数据库"),
    MARIADB("mariadb", "MariaDB数据库"),
    ORACLE("oracle", "Oracle数据库"),
    SQL_SERVER("sqlserver", "SQLServer数据库"),

    //以下还未支持，后期逐步扩展
    DB2("db2", "DB2数据库"),
    H2("h2", "H2数据库"),
    HSQL("hsql", "HSQL数据库"),
    SQLITE("sqlite", "SQLite数据库"),
    POSTGRE_SQL("postgresql", "Postgre数据库"),
    DM("dm", "达梦数据库"),
    XU_GU("xugu", "虚谷数据库"),
    KINGBASE_ES("kingbasees", "人大金仓数据库"),
    PHOENIX("phoenix", "Phoenix HBase数据库"),
    GAUSS("zenith", "Gauss 数据库"),
    OTHER("other", "其他数据库");

    private final String db;
    private final String desc;

    DBType(final String db, final String desc) {
        this.db = db;
        this.desc = desc;
    }

    public static DBType getDbType(String dbType) {
        DBType[] dbTypes = values();
        int size = dbTypes.length;

        for(int i = 0; i < size; ++i) {
            DBType type = dbTypes[i];
            if (type.db.equalsIgnoreCase(dbType)) {
                return type;
            }
        }

        return OTHER;
    }

    public String getDb() {
        return db;
    }

    public String getDesc() {
        return desc;
    }
}
