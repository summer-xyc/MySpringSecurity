package com.summer.security.mapper;

import com.summer.security.model.SysUser;
import com.summer.security.pojo.SecuritySysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper extends JpaRepository<SysUser,Long> {

    SecuritySysUser findByName(String name);
}
