package com.summer.security.service;


import com.summer.security.pojo.Response;

public interface RoleService {
    /**
     * @Description: 增加一个角色
     * @Date: 2019/4/19-14:40
     * @Param: [nameEn, nameCn]
     * @return: com.apl.model.GalenResponse
     **/
    Response createRole(String nameEn, String nameCn);

    /**
     * @Description: 修改角色属性
     * @Date: 2019/4/23-11:01
     * @Param: [roleId, nameEn, nameCn]
     * @return: com.apl.model.GalenResponse
     **/
    Response modifyRole(Long roleId, String nameEn, String nameCn);

    /**
     * @Description: 添加用户为xxx角色
     * @Date: 2019/3/22-9:23
     * @Param: [userId, roleId]
     * @return: java.lang.String
     **/
    Response addToRole(Long userId, Long roleId);

    /**
     * @Description: 查看所有系统角色
     * @Date: 2019/4/26-16:42
     * @Param: []
     * @return: com.apl.model.AplResponse
     **/
    Response getAllSysRoleList();

    /**
     * @Description: 查询系统角色, 不包含独立角色
     * @Date: 2019/4/19-15:03
     * @Param: []
     * @return: com.apl.model.AplResponse
     **/
    Response getSysRoleList();

    /**
     * @Description: 查询系统角色, 检测是否包含
     * @Date: 2019/4/24-14:49
     * @Param: [userId]
     * @return: com.apl.model.GalenResponse
     **/
    Response getSysRoleList(Long userId);

    /**
     * @Description: 查看xx用户的角色列表
     * @Date: 2019/4/20-14:32
     * @Param: [userId]
     * @return: com.apl.model.GalenResponse
     **/
    Response getUserSysRoleList(Long userId);

    /**
     * @Description: 移除xx用户的一个角色
     * @Date: 2019/4/20-14:03
     * @Param: [userId, roleId]
     * @return: com.apl.model.GalenResponse
     **/
    Response removeUserSysRole(Long userId, Long roleId);

    /**
     * @Description: 移除一个角色
     * @Date: 2019/4/20-14:17
     * @Param: [roleId]
     * @return: com.apl.model.GalenResponse
     **/
    Response removeSysRole(Long roleId);
}
