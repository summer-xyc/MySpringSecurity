package com.summer.security.mapper;

import com.summer.security.model.SysMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuMapper extends JpaRepository<SysMenu, Long>, JpaSpecificationExecutor<SysMenu> {

    /*  void deleteById(Long id);*/

    /*@Query(value = "update SysMenu m  set m.menuType =  ?1  ,where  m.money=?1")
    void updateById(SysMenu sysMenu);*/


    @Query(value = "select id, menu_type, parent_id, title, title_en, icon_pic, path, component, element_id,\n" +
            "        enabled, request_url, sort_order    from   sys_menu\n" +
            "        where enabled=true \n" +
            "        and id in \n" +
            "        (select mr.menu_id from sys_user_role u_r,sys_menu_role mr where u_r.role_id=mr.role_id and\n" +
            "        u_r.user_id= :userId )\n" +
            "        order by id \n" +
            "        ", nativeQuery = true)
    Page<SysMenu> findSysMenuList(Long userId, Pageable pageable);


    @Query(value = "select  distinct  m.id from  sys_menu m left join sys_menu_role mr on m.id = mr.menu_id " +
            " where  m.enabled = TRUE AND mr.role_id = :roleId  order by m.id ", nativeQuery = true)
    List<Long> findSysMenuIdListByRoleId(long roleId);


    @Query(value = "select m.id, m.menu_type, m.parent_id, m.title, m.title_en, m.icon_pic, m.path, m.component, m.element_id,\n" +
            "        m.enabled, m.request_url, m.sort_order from sys_menu m  LEFT JOIN sys_menu_role mr ON m.id = mr.menu_id " +
            "        where  mr.role_id = :roleId AND m.enabled = TRUE order by m.id", nativeQuery = true)
    List<SysMenu> findSysMenuListByRoleId(Long roleId);

    @Query(value = "update sys_menu  set parent_id = :parentId where parent_id = :id ",nativeQuery = true)
    void  updateParentId(Long parentId,Long id);


}













