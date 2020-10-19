package com.summer.security.utils;

import com.summer.security.pojo.SecuritySysUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 获取用户
 */
public class SecurityUserUtil {

    /**
     * 从认证中心里面获取当前用户权限信息
     * @return
     */
    public static SecuritySysUser getCurrentUser() {
        return (SecuritySysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取当前使用者的编号
     * @return
     */
    public static Long getCurrentUserId() {
        SecuritySysUser securitySysUser = getCurrentUser();
        if (null == securitySysUser) {
            return null;
        }
        return securitySysUser.getId();
    }


}
