package com.summer.security.service.impl;

import com.summer.security.mapper.SysPermissionMapper;
import com.summer.security.mapper.SysPermissionRoleMapper;
import com.summer.security.mapper.SysRoleMapper;
import com.summer.security.model.SysPermission;
import com.summer.security.model.SysPermissionRole;
import com.summer.security.pojo.Response;
import com.summer.security.service.PermissionService;
import com.summer.security.utils.IdUtil;
import com.summer.security.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysPermissionRoleMapper sysPermissionRoleMapper;

    @Override
    public Response createPermission(SysPermission sysPermission) {
        sysPermission.setId(IdUtil.generateNumberId());
        //sysPermissionMapper.insertSelective(sysPermission);
        sysPermissionMapper.save(sysPermission);
        return ResponseUtils.SUCCESS(sysPermission.getId());
    }

    @Override
    public Response addToPermission(Long roleId, Long permissionId) {
        if (null == /*sysRoleMapper.selectByPrimaryKey(roleId)*/sysRoleMapper.findById(roleId).get()) {
            return ResponseUtils.build(501, "角色不存在");
        }
        if (null == /*sysPermissionMapper.selectByPrimaryKey(permissionId)*/sysPermissionMapper.findById(permissionId).get()) {
            return ResponseUtils.build(502, "权限不存在");
        }
//        SysPermissionRole sysPermissionRole = sysPermissionRoleMapper.selectByRidPid(roleId, permissionId);
        SysPermissionRole sysPermissionRole = sysPermissionRoleMapper.findByRoleIdAndPermissionId(roleId, permissionId);
        if (null != sysPermissionRole) {
            return ResponseUtils.SUCCESS(sysPermissionRole.getId());
        }
        sysPermissionRole = new SysPermissionRole();
        sysPermissionRole.setId(IdUtil.generateNumberId());
        sysPermissionRole.setRoleId(roleId);
        sysPermissionRole.setPermissionId(permissionId);
//        sysPermissionRoleMapper.insertSelective(sysPermissionRole);
        sysPermissionRoleMapper.save(sysPermissionRole);
        return ResponseUtils.SUCCESS(sysPermissionRole.getId());
    }
}
