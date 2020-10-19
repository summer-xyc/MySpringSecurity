package com.summer.security.mapper;

import com.summer.security.model.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleMapper extends JpaRepository<SysRole,Long> {


    SysRole findByNameEn(String NameEn);

    List<SysRole> findAllByGroupType(Integer getGroupType);

    @Query(value = "select  r.id from  SysRole r join SysUserRole u_r on r.id = u_r.roleId where u_r.userId = :userId order by r.id")
    List<Long> findgetUserSysRoleIdList(Long userId);

    @Query(value = "select r  from SysRole r where r.groupType = :groupType and r.onAlone = 0 order by r.id ")
    List<SysRole> findSysRoleList(Integer groupType);

    @Query(value = "SELECT " +
            "        r.id, r.name_en, r.name_cn,r.group_type,r.on_alone " +
            "        FROM " +
            "        sys_role r " +
            "        LEFT JOIN sys_user_role u_r ON r.id = u_r.role_id " +
            "        WHERE " +
            "        u_r.user_id =   :userId " +
            "        ORDER BY " +
            "        r.id ",nativeQuery = true)
    List<SysRole> findAllById(Long userId);
}
