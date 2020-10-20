package com.summer.security.controller;


import com.summer.security.pojo.RespBean;
import com.summer.security.pojo.Response;
import com.summer.security.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sang on 2017/12/29.
 */
@Api(value = "登录", tags = "登录，注销等操作")
@RestController
public class LoginController {

    @ApiOperation(value = "swagger端测试登录入口")
    @PostMapping("/login")
    public RespBean login(String username, String password) {
        System.out.println(username + "------" + password);
        return RespBean.ok("登录成功!");
    }

    @ApiOperation(value = "swagger端测试注销入口")
    @PostMapping("/logout")
    public Response logout() {
        System.out.println("注销成功!");
        return ResponseUtils.SUCCESS("注销成功!");
    }

    @ApiOperation(value = "提示跳转到登录页面")
    @GetMapping("/login_p")
    public Response loginP() {
        return ResponseUtils.invalid();
    }


}
