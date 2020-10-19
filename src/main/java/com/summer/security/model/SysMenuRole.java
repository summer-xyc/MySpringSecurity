package com.summer.security.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 菜单-角色关联
 */
@Entity
@Table(name = "sys_menu_role")
@Data
public class SysMenuRole {

    @Id
    private Long id;

    /**
     * 菜单编号
     */
    private Long menuId;

    /**
     * 角色编号
     */
    private Long roleId;
}
