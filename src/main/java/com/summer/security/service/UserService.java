package com.summer.security.service;

import com.summer.security.model.SysUser;
import com.summer.security.pojo.Response;

public interface UserService {
    Response createUser(SysUser sysUser);

}
