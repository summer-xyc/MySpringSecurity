package com.summer.security.interceptor.handler;

import com.summer.security.pojo.RespBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注销登录处理
 */
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        RespBean respBean = RespBean.ok("注销成功!");
        new MyWebMvcWrite().writeToWeb(response, respBean);
        System.out.println("注销成功!");
    }
}
