package com.summer.security.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.summer.security.model.SysRole;

import javax.persistence.JoinColumn;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: Galen
 * @Date: 2019/4/4-10:41
 * @Description: 适配 Security 权限
 **/
public class SecuritySysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    //@JsonProperty("id")
    private Long id;
    //@JsonProperty("request_url")
    private String url;
    //@JsonProperty("title")
    private String title;

    //@JoinColumn(name = "id", referencedColumnName = "id")
    private List<SysRole> roles;


    public SecuritySysPermission(Long id, String requestUrl,String title, Long role_id, String name_en, String name_cn) {
        this.id = id;
        this.url =requestUrl;
        this.title = title;
        SysRole sysRole = new SysRole();
        sysRole.setId(role_id);
        sysRole.setNameEn(name_en);
        sysRole.setNameCn(name_cn);
        this.roles.add(sysRole);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }
}
