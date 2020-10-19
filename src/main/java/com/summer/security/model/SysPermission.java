package com.summer.security.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 权限表（url菜单）
 */
@Entity
@Table(name = "sys_permission")
@Data
public class SysPermission {

    @Id
    private Long id;

    private String url;

    private String path;

    private String component;

    private String name;

    private String iconcls;

    private Boolean keepalive;

    private Boolean requireauth;

    private Integer parentid;

    private Boolean enabled;
}
