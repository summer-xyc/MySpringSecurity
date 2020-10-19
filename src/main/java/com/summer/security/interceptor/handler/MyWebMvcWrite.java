package com.summer.security.interceptor.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.summer.security.pojo.RespBean;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 页面信息
 */
public class MyWebMvcWrite {

    /**
     *输出信息到页面
     * @param response
     * @param respBean
     * @throws IOException
     */
    public void writeToWeb(HttpServletResponse response, RespBean respBean) throws IOException {
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.write(om.writeValueAsString(respBean));
        out.flush();
        out.close();

    }
}
