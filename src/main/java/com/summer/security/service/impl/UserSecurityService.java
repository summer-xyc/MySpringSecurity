package com.summer.security.service.impl;

import com.summer.security.mapper.SysUserMapper;
import com.summer.security.mapper.UserSecurityMapper;
import com.summer.security.pojo.SecuritySysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserSecurityMapper userSecurityMapper;

    @Autowired
    private SysUserMapper sysUserMapper;


    /**
     *实现了UserDetailsService接口中的loadUserByUsername方法
     *执行登录,构建Authentication对象必须的信息,
     *如果用户不存在，则抛出UsernameNotFoundException异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SecuritySysUser byUserName = sysUserMapper.findByName(username);
        if (byUserName == null) {
            throw new UsernameNotFoundException("用户名不对");
        }
        return byUserName;
    }
}
