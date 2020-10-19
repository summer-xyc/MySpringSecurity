package com.summer.security.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 菜单表
 */
@Entity
@Table(name = "sys_menu")
@Data
public class SysMenu {

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 菜单类型（1：左侧主菜单；2：页面中的按钮；3：页面中标签）
     */
    private Integer menuType;

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
     * 是否有效（默认1：有效；0：无效；）
     */
    private Boolean enabled;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 菜单的排序
     */
    private Integer sortOrder;

    /**
     * 角色选择（前端使用）
     */
    private Boolean onChoose;
}
