package com.summer.security.mapper;

import com.summer.security.model.SysMenu;
import com.summer.security.pojo.SecuritySysMenu;
import com.summer.security.pojo.SecuritySysPermission;
import com.summer.security.pojo.SecuritySysUser;
import com.summer.security.service.impl.UserSecurityService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface UserSecurityMapper extends JpaRepository<SysMenu, Long> {

    //SecuritySysUser  findBy(String userName);
    //new com.summer.security.pojo.SecuritySysPermission(m.id,m.request_url,m.title, r.id as role_id,r.name_en,r.name_cn)
    //m.id as id,m.request_url as request_url,m.title as title, r.id as role_id,r.name_en,r.name_cn
/*    @Query(value = " select new com.summer.security.pojo.SecuritySysPermission(m.id,m.request_url,m.title, r.id as role_id,r.name_en,r.name_cn) from sys_menu m left join sys_menu_role mr on m.id=mr.menu_id " +
            "        left join sys_role r on mr.role_id=r.id  " +
            "        WHERE m.enabled=true  " +
            "        order by m.id desc ",nativeQuery = true)*/
    @Query(value = " select new com.summer.security.pojo.SecuritySysPermission(m.id,m.requestUrl,m.title, r.id ,r.nameEn,r.nameCn) from SysMenu m join SysMenuRole mr on m.id = mr.id" +
            " join SysRole r on mr.roleId = r.id " +
            " where m.enabled =true  " +
            " order by  m.id desc ")
    List<SecuritySysPermission> findAllPermission();

    @Query(value = " select  new com.summer.security.pojo.SecuritySysMenu(m1.id,m1.path,m1.component,m1.iconPic,m1.title,m1.titleEn,m1.parentId,m1.requestUrl,m1.sortOrder, " +
            "        m2.id as id2,m2.path as path2,m2.component as component2,m2.iconPic as icon_pic2,m2.title as title2,  " +
            "        m2.titleEn as title_en2,m2.parentId as parent_id2,m2.requestUrl as request_url2,m2.sortOrder as sort_order2 ) " +
            "        from  SysMenu m1, SysMenu m2  where m1.enabled=true " +
            "        and m2.enabled=true " +
            "        and m1.menuType=1 " +
            "        and m2.menuType=1 " +
            "        and m1.id=m2.parentId " +
            "        and m2.id in " +
            "        (select mr.menuId from SysUserRole u_r,SysMenuRole mr where u_r.roleId=mr.roleId and " +
            "        u_r.userId= :userId) " +
            "        order by m1.sortOrder,m2.sortOrder")
    List<SecuritySysMenu> findMenuByUid(Long userId);


    @Query(value = "select " +
            "        m.elementId " +
            "        from SysMenu m " +
            "        where m.menuType=2 " +
            "        and m.parentId = :menuId " +
            "        and m.id in " +
            "        (select mr.menuId from SysUserRole u_r,SysMenuRole mr where u_r.roleId=mr.roleId and " +
            "        u_r.userId= :userId) " +
            "        order by m.sortOrder")
    List<String> findButtonElementIdByUid(Long userId,Long menuId);

}





















