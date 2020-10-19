package com.summer.security.controller;

import com.summer.security.model.SysUser;
import com.summer.security.pojo.Response;
import com.summer.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "用户controller", tags = {"用户操作接口"})
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("添加用户")
    @PostMapping("create")
    public Response createUser(SysUser sysUser) {
        return userService.createUser(sysUser);
    }
}
