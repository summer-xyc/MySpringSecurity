package com.summer.security.mapper;

import com.summer.security.model.SysMenuRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuRoleMapper extends JpaRepository<SysMenuRole,Long> {

    SysMenuRole findByRoleIdAndMenuId(Long RoleId,Long MenuId);


    List<SysMenuRole> findAllByRoleId(Long RoleId);
}
