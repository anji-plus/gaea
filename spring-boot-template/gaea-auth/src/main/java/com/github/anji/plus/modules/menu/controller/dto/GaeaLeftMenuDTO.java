package com.github.anji.plus.modules.menu.controller.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 菜单表(GaeaMenu)实体类
 *
 * @author lirui
 * @since 2021-02-02 13:36:43
 */
@ApiModel(value = "菜单表")
public class GaeaLeftMenuDTO {
    /**
     * 菜单代码
     */
    @ApiModelProperty(value = "菜单代码")
    private String menuCode;
    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String menuName;
    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id")
    private String parentCode;
    /**
     * 菜单路径
     */
    @ApiModelProperty(value = "菜单路径")
    private String menuUrl;
    /**
     * base64图标或者iconfont字体
     */
    @ApiModelProperty(value = "base64图标或者iconfont字体")
    private String menuIcon;
    /**
     * 重定向地址
     */
    @ApiModelProperty(value = "重定向地址")
    private String redirectUrl;
    /**
     * 该路由需要加载的页面组件的文件路径
     */
    @ApiModelProperty(value = "该路由需要加载的页面组件的文件路径")
    private String component;
    /**
     * 是否隐藏菜单 0为未隐藏 默认0
     */
    @ApiModelProperty(value = "是否隐藏菜单 0为未隐藏 默认0")
    private Integer hidden;
    /**
     * 是否一直显示 0 否 1.是
     */
    @ApiModelProperty(value = "是否一直显示 0 否 1.是")
    private Integer alwaysShow;

    @TableField(exist = false)
    private List<GaeaLeftMenuDTO> children;

    /**
     * 元数据
     */
    public Map<String,String> meta;

    /**
     * 按钮
     */
    public Set<String> permission;

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

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
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

    public List<GaeaLeftMenuDTO> getChildren() {
        return children;
    }

    public void setChildren(List<GaeaLeftMenuDTO> children) {
        this.children = children;
    }

    public Map<String, String> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, String> meta) {
        this.meta = meta;
    }

    public Set<String> getPermission() {
        return permission;
    }

    public void setPermission(Set<String> permission) {
        this.permission = permission;
    }
}
