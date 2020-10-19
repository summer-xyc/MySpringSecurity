package com.summer.security.interceptor.handler;

import com.summer.security.pojo.RespBean;
import com.summer.security.utils.SecurityUserUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setContentType("application/json;charset=utf-8");
        RespBean respBean = RespBean.ok("登录成功!", SecurityUserUtil.getCurrentUser());
        new MyWebMvcWrite().writeToWeb(response, respBean);
        System.out.println("登录成功!");
    }
}
