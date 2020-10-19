package com.summer.security.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 权限-角色关联
 */
@Entity
@Table(name = "sys_permission_role")
@Data
public class SysPermissionRole {
    @Id
    private Long id;

    private Long permissionId;

    private Long roleId;


}
