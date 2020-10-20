package com.summer.security.service.impl;


import com.summer.security.mapper.SysMenuRoleMapper;
import com.summer.security.mapper.SysRoleMapper;
import com.summer.security.mapper.SysUserMapper;
import com.summer.security.mapper.SysUserRoleMapper;
import com.summer.security.model.SysRole;
import com.summer.security.model.SysUserRole;
import com.summer.security.pojo.Response;
import com.summer.security.pojo.SecuritySysUser;
import com.summer.security.service.RoleService;
import com.summer.security.utils.IdUtil;
import com.summer.security.utils.ResponseUtils;
import com.summer.security.utils.SecurityUserUtil;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysMenuRoleMapper sysMenuRoleMapper;

    @Override
    public Response createRole(String nameEn, String nameCn) {
        /**
         * @Description: 查看管理员的类型
         **/
        SecuritySysUser securitySysUser = SecurityUserUtil.getCurrentUser();
        if (null == securitySysUser) {
            return ResponseUtils.invalid();
        }
        /**
         * @Description: 查看角色是否已经存在
         **/
//        SysRole sysRole = sysRoleMapper.selectByRoleName(nameEn);
        SysRole sysRole = sysRoleMapper.findByNameEn(nameEn);
        if (sysRole == null) {
            sysRole = new SysRole();
            sysRole.setId(IdUtil.generateNumberId());
            sysRole.setNameEn(nameEn);
            sysRole.setNameCn(nameCn);
            sysRole.setGroupType(securitySysUser.getUserType());
//            sysRoleMapper.insertSelective(sysRole);
            sysRoleMapper.save(sysRole);
        }
        return ResponseUtils.SUCCESS(sysRole.getId());
    }

    @Override
    public Response modifyRole(Long roleId, String nameEn, String nameCn) {
        /**
         * @Description: 查看管理员的类型
         **/
        SecuritySysUser securitySysUser = SecurityUserUtil.getCurrentUser();
        if (null == securitySysUser) {
            return ResponseUtils.invalid();
        }
//        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
        SysRole sysRole = sysRoleMapper.findById(roleId).get();
        if (null == sysRole) {
            return ResponseUtils.build(501, "角色不存在！");
        }
        /**
         * @Description: 查看角色名是否已经存在
         **/
//        SysRole sysRoleOfName = sysRoleMapper.selectByRoleName(nameEn);
        SysRole sysRoleOfName = sysRoleMapper.findByNameEn(nameEn);
        if (sysRoleOfName == null) {
            sysRole.setNameEn(nameEn);
            sysRole.setNameCn(nameCn);
//            sysRoleMapper.updateByPrimaryKeySelective(sysRole);
            sysRoleMapper.save(sysRole);
            return ResponseUtils.SUCCESS(roleId);
        }
        /**
         * @Description: 角色名已经存在，判断是否是当前记录
         **/
        if (roleId.equals(sysRoleOfName.getId())) {
            sysRole.setNameCn(nameCn);
//            sysRoleMapper.updateByPrimaryKeySelective(sysRole);
            sysRoleMapper.save(sysRole);
            return ResponseUtils.SUCCESS(roleId);
        }
        return ResponseUtils.build(502, "角色名已经存在！");
    }

    @Override
    public Response addToRole(Long userId, Long roleId) {
        if (null == /*sysUserMapper.selectByPrimaryKey(userId)*/sysUserMapper.findById(userId).get()) {
            return ResponseUtils.build(501, "用户不存在");
        }
        if (null == /*sysRoleMapper.selectByPrimaryKey(roleId)*/sysRoleMapper.findById(roleId).get()) {
            return ResponseUtils.build(502, "角色不存在");
        }
//        SysUserRole sysUserRole = sysUserRoleMapper.selectByUidRid(userId, roleId);
        SysUserRole sysUserRole = sysUserRoleMapper.findByRoleIdAndUserId(roleId,userId);
        if (null != sysUserRole) {
            return ResponseUtils.SUCCESS(sysUserRole.getId());
        }
        sysUserRole = new SysUserRole();
        sysUserRole.setId(IdUtil.generateNumberId());
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleId(roleId);
//        sysUserRoleMapper.insertSelective(sysUserRole);
        sysUserRoleMapper.save(sysUserRole);
        return ResponseUtils.SUCCESS(sysUserRole.getId());
    }

    @Override
    public Response getAllSysRoleList() {
        SecuritySysUser securitySysUser = SecurityUserUtil.getCurrentUser();
        if (null == securitySysUser) {
            return ResponseUtils.invalid();
        }
        if (0 == securitySysUser.getRoles().size()) {
            return ResponseUtils.build(501, "没有查看系统角色的权限");
        }
        if (1 != securitySysUser.getRoles().size() && !securitySysUser.getRoles().get(0).getOnAlone()) {
            return ResponseUtils.build(502, "不是独立权限的角色");
        }
//        List<SysRole> sysRoleList = sysRoleMapper.getAllSysRoleList(securitySysUser.getRoles().get(0).getGroupType());
        List<SysRole> sysRoleList = sysRoleMapper.findAllByGroupType(securitySysUser.getRoles().get(0).getGroupType());
        return ResponseUtils.SUCCESS(sysRoleList);
    }

    @Override
    public Response getSysRoleList() {
        SecuritySysUser securitySysUser = SecurityUserUtil.getCurrentUser();
        if (null == securitySysUser) {
            return ResponseUtils.invalid();
        }
        if (0 == securitySysUser.getRoles().size()) {
            return ResponseUtils.build(501, "没有查看系统角色的权限");
        }
        if (1 != securitySysUser.getRoles().size() && !securitySysUser.getRoles().get(0).getOnAlone()) {
            return ResponseUtils.build(502, "不是独立权限的角色");
        }
       // List<SysRole> sysRoleList = sysRoleMapper.getSysRoleList(securitySysUser.getRoles().get(0).getGroupType());
        List<SysRole> sysRoleList = sysRoleMapper.findAllByGroupType(securitySysUser.getRoles().get(0).getGroupType());
        return ResponseUtils.SUCCESS(sysRoleList);
    }

    @Override
    public Response getSysRoleList(Long userId) {
        SecuritySysUser securitySysUser = SecurityUserUtil.getCurrentUser();
        if (null == securitySysUser) {
            return ResponseUtils.invalid();
        }
        if (0 == securitySysUser.getRoles().size()) {
            return ResponseUtils.build(501, "没有查看系统角色的权限");
        }
        if (1 != securitySysUser.getRoles().size() && !securitySysUser.getRoles().get(0).getOnAlone()) {
            return ResponseUtils.build(502, "不是独立权限的角色");
        }
//        List<Long> userRoleIdList = sysRoleMapper.getUserSysRoleIdList(userId);
        List<Long> userRoleIdList = sysRoleMapper.findgetUserSysRoleIdList(userId);
//        List<SysRole> sysRoleList = sysRoleMapper.getSysRoleList(securitySysUser.getRoles().get(0).getGroupType());
        List<SysRole> sysRoleList = sysRoleMapper.findSysRoleList(securitySysUser.getRoles().get(0).getGroupType());
        for (SysRole sysRole : sysRoleList) {
            if (userRoleIdList.contains(sysRole.getId())) {
                sysRole.setOnChoose(true);
            } else {
                sysRole.setOnChoose(false);
            }
        }
        return ResponseUtils.SUCCESS(sysRoleList);
    }

    @Override
    public Response getUserSysRoleList(Long userId) {
//        List<SysRole> sysRoleList = sysRoleMapper.getUserSysRoleList(userId);
        List<SysRole> sysRoleList =sysRoleMapper.findAllById(userId);
        return ResponseUtils.SUCCESS(sysRoleList);
    }

    @Override
    public Response removeUserSysRole(Long userId, Long roleId) {
//        SysUserRole sysUserRole = sysUserRoleMapper.selectByUidRid(userId, roleId);
        SysUserRole sysUserRole = sysUserRoleMapper.findByRoleIdAndUserId(roleId,userId);
        if (null != sysUserRole) {
//            sysUserRoleMapper.deleteByPrimaryKey(sysUserRole.getId());
            sysUserRoleMapper.delete(sysUserRole);
        }
        return ResponseUtils.SUCCESS("移除成功！");
    }

    @Override
    @Transactional
    public Response removeSysRole(Long roleId) {
        /**
         * @Author: Galen
         * @Description: 查看这个角色还有么有人在使用
         **/
        /*List<Long> userList = sysUserRoleMapper.selectUserByRid(roleId);
        if (null == userList || 0 != userList.size()) {
            return ResponseUtils.build(501, "存在用户正在关联此角色！");
        }*/
        /**
         * @Author: Galen
         * @Description: 删除此角色资源
         **/
//        sysRoleMapper.deleteByPrimaryKey(roleId);
        sysRoleMapper.delete(sysRoleMapper.findById(roleId).get());
        /**
         * @Author: Galen
         * @Description: 删除此角色与用户的关联（防止存在脏数据）
         **/
//        sysUserRoleMapper.deleteByRoleId(roleId);
        sysUserRoleMapper.deleteAll(sysUserRoleMapper.findAllByRoleId(roleId));
        /**
         * @Author: Galen
         * @Description: 删除此角色与菜单的关联（防止存在脏数据）
         **/
//        sysMenuRoleMapper.deleteByRoleId(roleId);
        sysMenuRoleMapper.deleteAll(sysMenuRoleMapper.findAllByRoleId(roleId));
        return ResponseUtils.SUCCESS("移除成功！");
    }
}
