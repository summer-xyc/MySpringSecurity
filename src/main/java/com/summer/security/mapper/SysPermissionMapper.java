package com.summer.security.mapper;

import com.summer.security.model.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysPermissionMapper extends JpaRepository<SysPermission,Long> {
}
