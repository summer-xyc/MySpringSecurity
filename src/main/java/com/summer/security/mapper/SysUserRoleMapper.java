package com.summer.security.mapper;

import com.summer.security.model.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRoleMapper extends JpaRepository<SysUserRole,Long> {

    SysUserRole findByRoleIdAndUserId(Long RoleId,Long UserId);


//    SysUserRole findByRoleIdaAndUserId(Long RoleId,Long UserId);
//    @Query(value = " select u.id from Sys")
//    List<Long> findUserByRid(Long roleId);

    List<SysUserRole> findAllByRoleId(Long RoleId);

}
