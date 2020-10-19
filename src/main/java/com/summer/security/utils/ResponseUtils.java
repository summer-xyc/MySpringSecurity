package com.summer.security.utils;


import com.summer.security.pojo.Response;

/**
 * @Date: 2019/3/6-16:25
 * @Description: 对象统一返回格式工具类
 * 应遵循：
 * 成功逻辑码  200
 * 登陆信息失效码 801
 * controller层错误码 401起499止
 * service层错误码 501起599止
 **/
public class ResponseUtils {

    public static Response SUCCESS() {
        return new Response(200, "SUCCESS", null);
    }

    public static Response SUCCESS(Object bean) {
        return new Response(200, "SUCCESS", bean);
    }

    public static Response SUCCESS(Object bean, long total) {
        return new Response(total, bean);
    }

    public static Response FAIL() {
        return new Response(400, "FAIL", null);
    }

    public static Response FAIL(String msg) {
        return new Response(400, msg, null);
    }

    public static Response build(Integer status, String msg) {
        return new Response(status, msg, null);
    }

    public static Response build(Object bean) {
        return new Response(200, "SUCCESS", bean);
    }

    public static Response build(Integer status, String msg, Object bean) {
        return new Response(status, msg, bean);
    }

    /**
     * 身份失效
     *
     * @return
     */
    public static Response invalid() {
        return new Response(801, "登录信息失效，请重新登录", null);
    }

}

