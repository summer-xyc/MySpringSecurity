package com.summer.security.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *用户-角色表
 */
@Entity
@Table(name = "sys_user_role")
@Data
public class SysUserRole {

    @Id
    private Long id;

    private Long userId;

    private Long roleId;

}
