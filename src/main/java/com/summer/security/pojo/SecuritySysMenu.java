package com.summer.security.pojo;

import lombok.Data;

import java.util.List;

/**
 * 菜单表
 *
 * @author wcyong
 * @date 2019-04-18
 */
@Data
public class SecuritySysMenu {
    /**
     * 主键
     */
    private Long id;

    /**
     * 父级菜单id
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 菜单英文名称
     */
    private String titleEn;

    /**
     * 菜单图标
     */
    private String iconPic;

    /**
     * vue组件根路径
     */
    private String path;

    /**
     * vue的组件名
     */
    private String component;

    /**
     * 按钮id（页面级别唯一)
     */
    private String elementId;

    /**
     * 权限方式（默认0：允许；1：限制）
     */
    private Boolean onLimit;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 菜单的排序
     */
    private Integer sortOrder;

    public SecuritySysMenu(){};

    public SecuritySysMenu(Long id, String path, String component, String iconPic, String title, String titleEn, Long parentId, String requestUrl, Integer sortOrder,
                           Long id2, String path2, String component2, String icon_pic2, String title2,
                           String title_en2, Long parent_id2, String request_url2, Integer sort_order2) {
        this.id =id;
        this.path = path;
        this.component =component;
        this.iconPic = iconPic;
        this.title =title;
        this.titleEn = titleEn;
        this.parentId = parentId;
        this.requestUrl =requestUrl;
        this.sortOrder =sortOrder;
        SecuritySysMenu securitySysMenu = new SecuritySysMenu();
        securitySysMenu.id =id2;
        securitySysMenu.path = path2;
        securitySysMenu.component =component2;
        securitySysMenu.iconPic = icon_pic2;
        securitySysMenu.title =title2;
        securitySysMenu.titleEn = title_en2;
        securitySysMenu.parentId = parent_id2;
        securitySysMenu.requestUrl =request_url2;
        securitySysMenu.sortOrder =sort_order2;
        this.children.add(securitySysMenu);
    }


    private List<SecuritySysMenu> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getIconPic() {
        return iconPic;
    }

    public void setIconPic(String iconPic) {
        this.iconPic = iconPic == null ? null : iconPic.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component == null ? null : component.trim();
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId == null ? null : elementId.trim();
    }

    public Boolean getOnLimit() {
        return onLimit;
    }

    public void setOnLimit(Boolean onLimit) {
        this.onLimit = onLimit;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<SecuritySysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SecuritySysMenu> children) {
        this.children = children;
    }
}