package com.summer.security.service;


import com.summer.security.model.SysPermission;
import com.summer.security.pojo.Response;

public interface PermissionService {

    /**
     * @Description: 添加一个权限
     **/
    Response createPermission(SysPermission sysPermission);

    /**
     * @Description: 给角色添加权限
     **/
    Response addToPermission(Long roleId, Long permissionId);
}
