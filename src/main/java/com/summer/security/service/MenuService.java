package com.summer.security.service;


import com.summer.security.pojo.Response;

public interface MenuService {

    /**
     * @Description: 添加一个权限菜单
     * @Date: 2019/4/26-14:54
     * @Param: [menuType, parentId, title, titleEn, iconPic, path, component, elementId, requestUrl, sortOrder]
     * @return: com.galen.security.pojo.GalenResponse
     **/
    Response createSysMenu(Integer menuType, Long parentId, String title, String titleEn, String iconPic, String path, String component,
                           String elementId, String requestUrl, Integer sortOrder);

    /**
     * @Description: 更新权限菜单
     * @Date: 2019/4/19-15:22
     * @Param: [id, title, titleEn, iconPic, path, component, elementId, requestUrl, sortOrder]
     * @return: com.galen.security.pojo.GalenResponse
     **/
    Response modifySysMenu(Long id, String title, String titleEn, String iconPic, String path, String component, String elementId,
                           String requestUrl, Integer sortOrder);

    /**
     * @Description: 给角色添加权限
     * @Date: 2019/4/19-10:30
     * @Param: [roleId, menuId]
     * @return: com.galen.security.pojo.GalenResponse
     **/
    Response addToSysMenu(Long roleId, Long menuId);

    /**
     * @Description: 获取当前用户的菜单
     * @Date: 2019/4/18-20:04
     * @Param: []
     * @return: com.galen.security.pojo.GalenResponse
     **/
    Response getMenusByUser();

    /**
     * @Description: 获取当前用户的当前菜单页面的按钮
     * @Date: 2019/4/19-11:42
     * @Param: [menuId]
     * @return: com.galen.security.pojo.GalenResponse
     **/
    Response getButtonByUser(Long menuId);

    /**
     * @Description: 查看系统权限资源管理(原始数据)
     * @Date: 2019/4/27-10:58
     * @Param: [pageBegin, pageSize]
     * @return: com.apl.model.AplResponse
     **/
    Response getAllSysMenuList(Integer pageBegin, Integer pageSize);

    /**
     * @Description: 获取系统权限列表
     * @Date: 2019/4/19-14:53
     * @Param: []
     * @return: com.galen.security.pojo.GalenResponse
     **/
    Response getSysMenuList();

    /**
     * @Description: 获取系统权限列表, 核查此角色是否有添加此权限
     * @Date: 2019/4/24-16:27
     * @Param: [roleId]
     * @return: com.galen.security.pojo.GalenResponse
     **/
    Response getSysMenuList(Long roleId);

    /**
     * @Description: 查看xx角色的权限资源
     * @Date: 2019/4/20-14:00
     * @Param: [roleId]
     * @return: com.galen.security.pojo.GalenResponse
     **/
    Response getRoleSysMenuList(Long roleId);

    /**
     * @Description: 移除xx角色的一个权限资源
     * @Date: 2019/4/20-14:03
     * @Param: [roleId, menuId]
     * @return: com.galen.security.pojo.GalenResponse
     **/
    Response removeRoleSysMenu(Long roleId, Long menuId);

    /**
     * @Author: Galen
     * @Description: 移除一个权限资源
     * @Date: 2019/4/20-14:17
     * @Param: [menuId]
     * @return: com.galen.security.pojo.GalenResponse
     **/
    Response removeSysMenu(Long menuId);
}
