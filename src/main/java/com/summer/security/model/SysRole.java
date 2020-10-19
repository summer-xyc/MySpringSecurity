package com.summer.security.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *角色表
 */
@Entity
@Table(name = "sys_role")
@Data
public class SysRole {

    @Id
    private Long id;
    /**
     * 角色英文名称
     */
    private String nameEn;

    /**
     * 角色中文名称
     */
    private String nameCn;

    /**
     * 角色类型
     */
    private Integer groupType;

    /**
     * 角色独立
     */
    private Boolean onAlone;

    /**
     * 角色选择（前端使用）
     */
    private Boolean onChoose;
}
