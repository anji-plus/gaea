package com.anji.plus.gaea.common.util;

import com.anji.plus.gaea.common.MagicValueConstants;
import com.anji.plus.gaea.common.dto.DynamicQueryBo;
import com.anji.plus.gaea.common.dto.SortColumn;
import com.anji.plus.gaea.common.enums.DynamicQueryOperatorType;
import com.anji.plus.gaea.common.enums.DynamicQueryValueType;
import com.anji.plus.gaea.common.dto.BaseQueryBO;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * DynamicQueryUtil
 * 动态封装,获取QueryWrapper
 * 高级查询+排序
 * 在mapper中使用 @Param(Constants.WRAPPER) Wrapper query
 * 在xml中
 * <if test="ew.emptyOfWhere == false">
 *        ${ew.customSqlSegment} 该值是WHERE + sql语句
 *  </if>
 *
 *  ${ew.getSqlSegment}  该值是不带WHERE
 *  ew.sqlSegment是不包括WHERE
 *
 *  getParamNameValuePairs().toString()
 * </pre>
 * </pre>
 *
 * @author peiyanni
 * @version DynamicQueryUtil.java
 */
@Slf4j
public class DynamicQueryUtil {

    private DynamicQueryUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static final String COMMA = ",";

    private static final String BLANK = "";

    /**
     * 通过baseQueryBO 获取 QueryWrapper
     * 获取高级查询条件、排序
     * @param baseQueryBO
     * @return
     */
    public static QueryWrapper getSortQueryWrapper(BaseQueryBO baseQueryBO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (baseQueryBO == null) {
            return queryWrapper;
        }
        _getQueryWrapper(baseQueryBO.getDynamicQueryBos(), baseQueryBO.getSortColumns(), queryWrapper);
        return queryWrapper;
    }

    /**
     * 组装高级查询的查询条件
     *
     * @param dynamicQueryBos
     * @return
     */
    public static QueryWrapper getQueryWrapper(List<DynamicQueryBo> dynamicQueryBos) {
        return getQueryWrapper(dynamicQueryBos, null);
    }

    /**
     * 组装高级查询的排序
     *
     * @param sortColumns
     * @return
     */
    public static QueryWrapper getSortQueryWrapper(List<SortColumn> sortColumns) {
        return getSortQueryWrapper(sortColumns, null);
    }

    /**
     * 组装高级查询的排序
     *
     * @param sortColumns
     * @return
     */
    public static QueryWrapper getSortQueryWrapper(List<SortColumn> sortColumns, QueryWrapper queryWrapper) {
        if (queryWrapper == null) {
            queryWrapper = new QueryWrapper();
        }
        if (sortColumns != null) {
            for (SortColumn sortColumn : sortColumns) {
                if (sortColumn.isAsc()) {
                    queryWrapper.orderByAsc(sortColumn.getColumnName());
//                    queryWrapper.orderByAsc(HumpUtil.humpToLine(sortColumn.getColumnName()));
                } else {
                    queryWrapper.orderByDesc(sortColumn.getColumnName());
//                    queryWrapper.orderByDesc(HumpUtil.humpToLine(sortColumn.getColumnName()));
                }
            }

        }
        return queryWrapper;
    }


    /**
     * 组装高级查询
     *
     * @param dynamicQueryBos
     * @param queryWrapper
     * @return
     */
    public static QueryWrapper getQueryWrapper(List<DynamicQueryBo> dynamicQueryBos, QueryWrapper queryWrapper) {
        if (queryWrapper == null) {
            queryWrapper = new QueryWrapper();
        }
        return _getQueryWrapper(dynamicQueryBos, null, queryWrapper);
    }

    /**
     * 组装高级、排序查询
     *
     * @param dynamicQueryBos
     * @param queryWrapper
     * @return
     */
    public static QueryWrapper getSortQueryWrapper(List<DynamicQueryBo> dynamicQueryBos, List<SortColumn> sortColumns, QueryWrapper queryWrapper) {
        if (queryWrapper == null) {
            queryWrapper = new QueryWrapper();
        }
        return _getQueryWrapper(dynamicQueryBos, sortColumns, queryWrapper);
    }

    /**
     * 组装高级查询 sql
     *
     * @param dynamicQueryBos
     * @param queryWrapper
     * @return
     */
    private static QueryWrapper _getQueryWrapper(List<DynamicQueryBo> dynamicQueryBos, List<SortColumn> sortColumns, final QueryWrapper<Object> queryWrapper) {

        if (dynamicQueryBos != null) {
            dynamicQueryBos.stream().forEach(bo -> {

                String name = bo.getName();
                if (StringUtils.isBlank(name)) {
                    return;
                }

                DynamicQueryOperatorType operatorType = bo.getDynamicQueryOperatorType();
                if (operatorType == null) {
                    return;
                }

                DynamicQueryValueType valueType = bo.getDynamicQueryValueType();
                if (valueType == null) {
                    return;
                }

                String value = bo.getValue();
                if (StringUtils.isEmpty(value)) {
                    queryWrapper.eq(MagicValueConstants.STRING_ONE,MagicValueConstants.ONE);
                    // 236_高级查询时支持空值查询
                    // “等于”、“不等于”两种查询条件下支持空值查询
                    if (operatorType == DynamicQueryOperatorType.EQ) {
                        // 如果是数值字段，不输入任何值，即表示查询“字段内容=NULL”
                        // 如果是字符字段，不输入任何值，即表示查询“字段内容=NULL OR TRIM(字段内容)=‘’ ”
                        // .如果是枚举字段，不输入任何值，表示查询“字段内容=NULL ,枚举字段 数据库存的要么是字符串要么是数值
                        if (valueType == DynamicQueryValueType.NUMBER) {
                            queryWrapper.isNull(name);
                        } else if (valueType == DynamicQueryValueType.CHARACTER) {
                            queryWrapper.and(wrapper -> wrapper.isNull(name).or().eq(name, BLANK));
                        }
                    } else if (operatorType == DynamicQueryOperatorType.NE) {
                        if (valueType == DynamicQueryValueType.NUMBER) {
                            queryWrapper.and(wrapper -> wrapper.isNotNull(name));
                        } else if (valueType == DynamicQueryValueType.CHARACTER) {
                            // oracle中空字符串和null是一个，如果 写<>'' 反而会查不到数据，所以去掉.ne(name, BLANK)
                            queryWrapper.and(wrapper -> wrapper.isNotNull(name));
                        }
                    }
                    return;
                }
                Object v = value;
                if (valueType == DynamicQueryValueType.NUMBER) {
                    try {
                        v = Long.parseLong(value);
                    } catch (Exception e) {
                        v = value;
                        log.error("高级查询参数转Long出错，不转当字符串查询：" + v, e);
                    }
                }
                switch (operatorType) {
                    case EQ:
                        queryWrapper.eq(name, v);
                        break;
                    case GE:
                        queryWrapper.ge(name, v);
                        break;
                    case GT:
                        queryWrapper.gt(name, v);
                        break;
                    case IN:
                        queryWrapper.in(name, Arrays.asList(bo.getValue().split(COMMA)));
                        break;
                    case LE:
                        queryWrapper.le(name, v);
                        break;
                    case NE:
                        queryWrapper.ne(name, v);
                        break;
                    case LT:
                        queryWrapper.lt(name, v);
                        break;
                    case LIKE:
                        queryWrapper.like(name, v);
                        break;
                }
            });
        }

        if (sortColumns != null) {
            getSortQueryWrapper(sortColumns, queryWrapper);
        }
        return queryWrapper;
    }

//    public static void main(String[] args) {
//        List<DynamicQueryBo> dynamicQueryBos = new ArrayList<>();
//        DynamicQueryBo bo1 = new DynamicQueryBo();
//        bo1.setName("name1");
//        bo1.setOperator("=");
//        bo1.setValue("nv");
//        bo1.setValueType(0);
//        dynamicQueryBos.add(bo1);
//
//        DynamicQueryBo bo2 = new DynamicQueryBo();
//        bo2.setName("name2");
//        bo2.setOperator("LIKE");
//        bo2.setValue("addad");
//        bo2.setValueType(0);
//        dynamicQueryBos.add(bo2);
//
//        DynamicQueryBo bo3 = new DynamicQueryBo();
//        bo3.setName("name3");
//        bo3.setOperator(">=");
//        bo3.setValue("1233");
//        bo3.setValueType(1);
//        dynamicQueryBos.add(bo3);
//
//        DynamicQueryUtil dynamicQueryUtil = new DynamicQueryUtil();
////        QueryWrapper queryWrapper = dynamicQueryUtil.getQueryWrapper(dynamicQueryBos, null);
//
//        BaseQueryBO baseQueryBO = new BaseQueryBO() {
//            @Override
//            public Object queryBOToEntity() {
//                return null;
//            }
//        };
//        baseQueryBO.setDynamicQueryBos(null);
//
//        List<SortColumn> sortColumns = new ArrayList<>();
//        SortColumn sortColumn = new SortColumn();
//        sortColumn.setColumnName("a.codeName");
//        sortColumn.setAsc(true);
//
//        SortColumn sortColumn1 = new SortColumn();
//        sortColumn1.setColumnName("b.acbd");
//        sortColumn1.setAsc(true);
//
//        sortColumns.add(sortColumn);
//        baseQueryBO.setSortColumns(sortColumns);
//        QueryWrapper queryWrapper = getSortQueryWrapper(baseQueryBO);
//
//        System.out.println(queryWrapper.getCustomSqlSegment());
//        System.out.println(queryWrapper.getSqlSegment());
//        System.out.println(queryWrapper.getCustomSqlSegment().toString());
//    }
}
