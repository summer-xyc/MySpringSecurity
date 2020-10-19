package com.summer.security.service.impl;

import com.summer.security.mapper.SysUserMapper;
import com.summer.security.model.SysUser;
import com.summer.security.pojo.Response;
import com.summer.security.service.UserService;
import com.summer.security.utils.IdUtil;
import com.summer.security.utils.MD5Util;
import com.summer.security.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    public Response createUser(SysUser sysUser) {
        if (null != /*sysUserMapper.selectByUsername(sysUser.getUsername())*/sysUserMapper.findByName(sysUser.getUsername())) {
            return ResponseUtils.build(501, "用户已经存在");
        }
        sysUser.setId(IdUtil.generateNumberId());
        sysUser.setPassword(MD5Util.encode(sysUser.getPassword()));
    //    sysUserMapper.insertSelective(sysUser);
        sysUserMapper.save(sysUser);
        return ResponseUtils.SUCCESS(sysUser);
    }
}
