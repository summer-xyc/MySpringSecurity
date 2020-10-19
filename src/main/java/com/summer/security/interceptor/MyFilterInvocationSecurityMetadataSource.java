package com.summer.security.interceptor;

import com.summer.security.mapper.UserSecurityMapper;
import com.summer.security.model.SysRole;
import com.summer.security.pojo.SecuritySysPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * FilterInvocationSecurityMetadataSource（权限资源过滤器接口）继承了 SecurityMetadataSource（权限资源接口）
 * Spring Security是通过SecurityMetadataSource来加载访问时所需要的具体权限；Metadata是元数据的意思。
 * 自定义权限资源过滤器，实现动态的权限验证
 * 它的主要责任就是当访问一个url时，返回这个url所需要的访问权限
 */
@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private UserSecurityMapper userSecurityMapper;

    //是用来对资源路径或者url的字符串做匹配使用的。采用的是Ant风格的格式
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private static final Logger log = LoggerFactory.getLogger(MyFilterInvocationSecurityMetadataSource.class);

    /**
     * 返回本次访问需要的权限，可以有多个权限
     *
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getFullRequestUrl();
        //去数据库 查询资源
        List<SecuritySysPermission> permissionList = userSecurityMapper.findAllPermission();
        for (SecuritySysPermission permission : permissionList) {
            if (antPathMatcher.match(permission.getUrl(), requestUrl) && permission.getRoles().size() > 0) {
                List<SysRole> roles = permission.getRoles();
                int size = roles.size();
                String[] values = new String[size];
                for (int i = 0; i < size; i++) {
                    values[i] = roles.get(i).getNameEn();
                }
                log.info("当前访问路径是{},这个url所需要的访问权限是{}", requestUrl, values);
                return SecurityConfig.createList(values);
            }
        }
        /**
         * @Description: 如果本方法返回null的话，意味着当前这个请求不需要任何角色就能访问
         * 此处做逻辑控制，如果没有匹配上的，返回一个默认具体权限，防止漏缺资源配置
         **/
        log.info("当前访问路径是{},这个url所需要的访问权限是{}", requestUrl, "ROLE_LOGIN");
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    /**
     *此处方法如果做了实现，返回了定义的权限资源列表，
     *Spring Security会在启动时校验每个ConfigAttribute是否配置正确，
     *如果不需要校验，这里实现方法，方法体直接返回null即可。
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     *方法返回类对象是否支持校验，
     *web项目一般使用FilterInvocation来判断，或者直接返回true
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
