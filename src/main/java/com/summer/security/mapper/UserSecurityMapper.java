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

    @Query(value = " select  m1.id,m1.path,m1.component,m1.icon_pic,m1.title,m1.title_en,m1.parent_id,m1.request_url,m1.sort_order, " +
            "        m2.id as id2,m2.path as path2,m2.component as component2,m2.icon_pic as icon_pic2,m2.title as title2,  " +
            "        m2.title_en as title_en2,m2.parent_id as parent_id2,m2.request_url as request_url2,m2.sort_order as sort_order2 " +
            "        from  SysMenu m1, SysMenu m2  where m1.enabled=true " +
            "        and m2.enabled=true " +
            "        and m1.menu_type=1 " +
            "        and m2.menu_type=1 " +
            "        and m1.id=m2.parent_id " +
            "        and m2.id in " +
            "        (select mr.menu_id from sys_user_role u_r,sys_menu_role mr where u_r.role_id=mr.role_id and " +
            "        u_r.user_id=#{userId}) " +
            "        order by m1.sort_order,m2.sort_order")
    List<SecuritySysMenu> findMenuByUid(Long userId);

}





















