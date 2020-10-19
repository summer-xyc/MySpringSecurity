package com.summer.security.mapper;

import com.summer.security.model.SysPermissionRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysPermissionRoleMapper extends JpaRepository<SysPermissionRole,Long> {



    SysPermissionRole findByRoleIdAndPermissionId(Long RoleId,Long PermissionId);


}
