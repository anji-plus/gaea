package com.github.anji.plus.modules.menu.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.anji.plus.gaea.annotation.Unique;
import com.github.anji.plus.gaea.curd.entity.GaeaBaseEntity;

import java.io.Serializable;

/**
 * 菜单表(GaeaMenu)实体类
 *
 * @author lirui
 * @since 2021-02-02 13:36:43
 */
@TableName("gaea_menu")
public class GaeaMenu extends GaeaBaseEntity implements Serializable {
    /**
     * 菜单代码
     */
    @Unique
    private String menuCode;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 系统代码，参考数据字典SYSTEM_CODE
     */
    private String sysCode;
    /**
     * 父级id
     */
    private String parentCode;
    /**
     * 菜单路径
     */
    private String path;
    /**
     * base64图标或者iconfont字体
     */
    private String menuIcon;
    /**
     * 排序，升序
     */
    private Integer sort;
    /**
     * 0--已禁用 1--已启用  DIC_NAME=ENABLE_FLAG
     */
    private Integer enabled;
    /**
     * 0--未删除 1--已删除 DIC_NAME=DEL_FLAG
     */
    private Integer deleteFlag;
    /**
     * 重定向地址
     */
    private String redirectUrl;
    /**
     * 该路由需要加载的页面组件的文件路径
     */
    private String component;
    /**
     * 是否隐藏菜单 0为未隐藏 默认0
     */
    private Integer hidden;
    /**
     * 是否一直显示 0 否 1.是
     */
    private Integer alwaysShow;

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }

    public Integer getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(Integer alwaysShow) {
        this.alwaysShow = alwaysShow;
    }
}
