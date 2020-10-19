package com.summer.security.mapper;

import com.summer.security.model.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SysMenuMapper extends JpaRepository<SysMenu,Long> {

  /*  void deleteById(Long id);*/

    @Query(value = "update SysMenu m  set m.menuType =  ?1  ,where  m.money=?1")
    void updateById(SysMenu sysMenu);

}
