package com.anji.plus.common;

/**
 * <pre>
 * CacheTimeConstants
 * </pre>
 *
 * @author peiyanni
 * @version CacheTimeConstants.java
 */
public final class CacheTimeConstants {
    private CacheTimeConstants() {
    }
    //通用值 1天
    public static final int CHCHE_COMMON_TIME_ONE_DAYS = 1;
    //通用值 60分钟
    public static final int CHCHE_COMMON_TIME_SIXTY_MINUTES = 60;
    //登陆失败次数失效时间
    public static final int CHCHE_VALID_TIME_THREE_MINUTE = 3;
    //token登陆失效时间
    public static final int CHCHE_VALID_TIME_FIFTEEN_MINUTE = 15;
    //角色与菜单失效时间
    public static final int CHCHE_VALID_TIME_THIRTY_MINUTE = 30;
    //安卓、IOS登陆失效时间设置
    public static final int CHCHE_VALID_TIME_THIRTY_DAYS = 30;
    //字典查询值，设置生效时间
    public static final int CHCHE_VALID_TIME_ONE_DAYS = 1;
    //高级查询，设置生效时间
    public static final int QUERY_CONDITION_TIME_THIRTY_DAYS = 30;
    // 本地缓存时间43200秒(0.5天)
    public static final int LOCAL_CACHE_TIME_HALF_DAY_TO_SECONDS = 43200;
    // MDM基础数据缓存时间10天
    public static final int CACHE_MDM_TIME_TEN_DAYS = 10;
    //安卓、IOS登陆失效时间设置 30分钟转换为秒
    public static final int CACHE_VALID_TIME_THIRTY_MINUTE_TO_SENCONDS = 1800;
    // 限行数据缓存时间2天
    public static final int CACHE_LIMIT_DRIVER_TIME_TWO_DAYS = 2;
    // 本地缓存时间300秒(5分钟)
    public static final int CACHE_LIMIT_DRIVER_TIME_FIVE_MINUTE = 43200;
}
