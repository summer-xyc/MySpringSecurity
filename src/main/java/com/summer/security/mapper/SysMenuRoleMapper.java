package com.summer.security.mapper;

import com.summer.security.model.SysMenuRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuRoleMapper extends JpaRepository<SysMenuRole, Long> {

    SysMenuRole findByRoleIdAndMenuId(Long RoleId, Long MenuId);


    List<SysMenuRole> findAllByRoleId(Long RoleId);

    void deleteByRoleIdAndAndMenuId(Long RoleId, Long MenuId);


    @Query(value = "delete from sys_menu_role\n" +
            "        where menu_id = :menuId ", nativeQuery = true)
    void deleteByMenuId(Long menuId);
}
