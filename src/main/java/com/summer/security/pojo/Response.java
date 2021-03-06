package com.summer.security.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoxi
 * @date 2019/1/28 11:05
 * TODO: 自定义响应数据的结构
 */
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 数据库总条数
    private long total;

    // 响应中的数据
    private Object bean;

    public static Response build(Integer status, String msg, Object bean) {
        return new Response(status, msg, bean);
    }

    public static Response ok() {
        return new Response(0, null);
    }

    public static Response ok(Object bean) {
        return new Response(0, bean);
    }

    public static Response ok(long total, Object bean) {
        return new Response(total, bean);
    }

    public Response() {

    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public static Response build(Integer status, String msg) {
        return new Response(status, msg, null);
    }

    public static Response invalid() {
        return new Response(801, "登录信息超时", null);
    }

    public Response(Integer status, String msg, Object bean) {
        this.status = status;
        this.msg = msg;
        this.bean = bean;
    }

    public Response(long total, Object bean) {
        this.status = 200;
        this.msg = "OK";
        this.total = total;
        this.bean = bean;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param clazz    TaotaoResult中的object类型
     * @return
     */
    public static Response formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, Response.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("bean");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static Response format(String json) {
        try {
            return MAPPER.readValue(json, Response.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz    集合中的类型
     * @return
     */
    public static Response formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("bean");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

}

