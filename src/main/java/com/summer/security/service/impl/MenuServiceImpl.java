package com.summer.security.service.impl;


import com.summer.security.mapper.SysMenuMapper;
import com.summer.security.mapper.SysMenuRoleMapper;
import com.summer.security.mapper.SysRoleMapper;
import com.summer.security.mapper.UserSecurityMapper;
import com.summer.security.model.SysMenu;
import com.summer.security.model.SysMenuRole;
import com.summer.security.model.SysRole;
import com.summer.security.pojo.Response;
import com.summer.security.pojo.SecuritySysMenu;
import com.summer.security.service.MenuService;
import com.summer.security.utils.IdUtil;
import com.summer.security.utils.ResponseUtils;
import com.summer.security.utils.SecurityUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuRoleMapper sysMenuRoleMapper;
    @Autowired
    private UserSecurityMapper userSecurityMapper;

    private List<SysMenu> sourceList;//使得遍历同一份数据源

    @Override
    @Transactional
    public Response createSysMenu(Integer menuType, Long parentId, String title, String titleEn, String iconPic, String path, String component,
                                  String elementId, String requestUrl, Integer sortOrder) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(IdUtil.generateNumberId());
        sysMenu.setMenuType(menuType);
        sysMenu.setParentId(parentId);
        sysMenu.setTitle(title);
        sysMenu.setTitleEn(titleEn);
        sysMenu.setIconPic(iconPic);
        sysMenu.setPath(path);
        sysMenu.setComponent(component);
        sysMenu.setElementId(elementId);
        sysMenu.setRequestUrl(requestUrl);
        sysMenu.setSortOrder(sortOrder);
        sysMenu.setEnabled(true);
        //  sysMenuMapper.insertSelective(sysMenu);
        sysMenuMapper.save(sysMenu);
        /**
         * @Author: Galen
         * @Description: 自动给超级管理员加入权限
         **/
        //    SysRole sysRole = sysRoleMapper.selectByRoleName("ROLE_admin");
        SysRole sysRole = sysRoleMapper.findByNameEn("ROLE_admin");
        if (null != sysRole) {
//            SysMenuRole sysMenuRole = sysMenuRoleMapper.selectByMidPid(sysRole.getId(), sysMenu.getId());
            SysMenuRole sysMenuRole = sysMenuRoleMapper.findByRoleIdAndMenuId(sysRole.getId(), sysMenu.getId());
            if (null != sysMenuRole) {
                return ResponseUtils.SUCCESS(sysMenuRole.getId());
            }
            sysMenuRole = new SysMenuRole();
            sysMenuRole.setId(IdUtil.generateNumberId());
            sysMenuRole.setRoleId(sysRole.getId());
            sysMenuRole.setMenuId(sysMenu.getId());
//            sysMenuRoleMapper.insertSelective(sysMenuRole);
            sysMenuRoleMapper.save(sysMenuRole);
        }
        return ResponseUtils.SUCCESS(sysMenu.getId());
    }

    @Override
    public Response modifySysMenu(Long id, String title, String titleEn, String iconPic, String path, String component,
                                  String elementId, String requestUrl, Integer sortOrder) {
//        SysMenu sysMenuDb = sysMenuMapper.selectByPrimaryKey(id);
        SysMenu sysMenuDb = sysMenuMapper.findById(id).get();
        if (null == sysMenuDb) {
            return ResponseUtils.build(501, "权限资源不存在");
        }
        if (null != title && 1 == sysMenuDb.getMenuType()) {
            return ResponseUtils.build(502, "请传菜单标题");
        }
        if (null != elementId && 2 == sysMenuDb.getMenuType()) {
            return ResponseUtils.build(503, "请传入元素id");
        }
        SysMenu sysMenuModify = new SysMenu();
        sysMenuModify.setId(id);
        sysMenuModify.setTitle(title);
        sysMenuModify.setTitleEn(titleEn);
        sysMenuModify.setIconPic(iconPic);
        sysMenuModify.setPath(path);
        sysMenuModify.setComponent(component);
        sysMenuModify.setElementId(elementId);
        sysMenuModify.setRequestUrl(requestUrl);
        sysMenuModify.setSortOrder(sortOrder);
//        sysMenuMapper.updateByPrimaryKeySelective(sysMenuModify);
        sysMenuMapper.save(sysMenuModify);
        return ResponseUtils.SUCCESS(id);
    }

    @Override
    public Response addToSysMenu(Long roleId, Long menuId) {
        if (null == /*sysRoleMapper.selectByPrimaryKey(roleId)*/sysRoleMapper.findById(roleId)) {
            return ResponseUtils.build(501, "角色不存在");
        }
        if (null == /*sysMenuMapper.selectByPrimaryKey(menuId)*/sysMenuMapper.findById(menuId)) {
            return ResponseUtils.build(502, "权限不存在");
        }
//        SysMenuRole sysMenuRole = sysMenuRoleMapper.selectByMidPid(roleId, menuId);
        SysMenuRole sysMenuRole = sysMenuRoleMapper.findByRoleIdAndMenuId(roleId, menuId);
        if (null != sysMenuRole) {
            return ResponseUtils.SUCCESS(sysMenuRole.getId());
        }
        sysMenuRole = new SysMenuRole();
        sysMenuRole.setId(IdUtil.generateNumberId());
        sysMenuRole.setRoleId(roleId);
        sysMenuRole.setMenuId(menuId);
//        sysMenuRoleMapper.insertSelective(sysMenuRole);
        sysMenuRoleMapper.save(sysMenuRole);
        return ResponseUtils.SUCCESS(sysMenuRole.getId());
    }

    @Override
    public Response getMenusByUser() {
        Long userId = SecurityUserUtil.getCurrentUserId();
        if (null == userId) {
            return ResponseUtils.invalid();
        }
//        List<SecuritySysMenu> securitySysMenuList = userSecurityMapper.getMenuByUid(userId);
        List<SecuritySysMenu> securitySysMenuList = userSecurityMapper.findMenuByUid(userId);
        return ResponseUtils.SUCCESS(securitySysMenuList);
    }

    @Override
    public Response getButtonByUser(Long menuId) {
        Long userId = SecurityUserUtil.getCurrentUserId();
        if (null == userId) {
            return ResponseUtils.invalid();
        }
//        List<String> buttonIdList = userSecurityMapper.getButtonElementIdByUid(userId, menuId);
        List<String> buttonIdList = userSecurityMapper.findButtonElementIdByUid(userId, menuId);
        return ResponseUtils.SUCCESS(buttonIdList);
    }

    @Override
    public Response getAllSysMenuList(Integer pageBegin, Integer pageSize) {
        Long userId = SecurityUserUtil.getCurrentUserId();
        if (null == userId) {
            return ResponseUtils.invalid();
        }
        PageRequest pageRequest = PageRequest.of(pageBegin, pageSize);

        /*Specification<SysMenu> specification = (Specification<SysMenu>) (root, quest, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            Predicate p1 = criteriaBuilder.equal(root.get("userId"), userId);
            list.add(p1);
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        Page<SysMenu> sysMenus = sysMenuMapper.findAll(specification, pageRequest);*/
        Page<SysMenu> sysMenuList = sysMenuMapper.findSysMenuList(userId,pageRequest);
        return ResponseUtils.SUCCESS(sysMenuList.getContent(), sysMenuList.getTotalPages());
    }

    @Override
    public Response getSysMenuList() {
        Long userId = SecurityUserUtil.getCurrentUserId();
        if (null == userId) {
            return ResponseUtils.invalid();
        }
//        sourceList = sysMenuMapper.getSysMenuList(userId);
        PageRequest pageRequest = PageRequest.of(0, 10);
        sourceList = sysMenuMapper.findSysMenuList(userId,pageRequest).getContent();
        return ResponseUtils.SUCCESS(arrangeToTree());
    }

    @Override
    public Response getSysMenuList(Long roleId) {
        Long userId = SecurityUserUtil.getCurrentUserId();
        if (null == userId) {
            return ResponseUtils.invalid();
        }
//        List<Long> roleMenuId = sysMenuMapper.getSysMenuIdListByRoleId(roleId);
        List<Long> roleMenuId = sysMenuMapper.findSysMenuIdListByRoleId(roleId);
//        sourceList = sysMenuMapper.getSysMenuList(userId);
        PageRequest pageRequest = PageRequest.of(0, 10);
        sourceList = sysMenuMapper.findSysMenuList(userId,pageRequest).getContent();
        for (SysMenu sysMenu : sourceList) {
            if (roleMenuId.contains(sysMenu.getId())) {
                sysMenu.setOnChoose(true); //属于此角色
            }
        }
        return ResponseUtils.SUCCESS(arrangeToTree());
    }

    /**
     * @Description: 规整为树形结构
     * @Date: 2019/4/25-9:58
     * @Param: [list]
     * @return: java.util.List<com.apl.tl.pojo.SysMenu>
     **/
    private List<SysMenu> arrangeToTree() {
        Iterator<SysMenu> menuIterator = sourceList.iterator();
        List<SysMenu> resultTreeList = new ArrayList<>();
        SysMenu sysMenuStock;
        while (menuIterator.hasNext()) {
            SysMenu sysMenu = menuIterator.next();
            //判断parent=0是根节点
            if (sysMenu.getParentId().equals(0) || sysMenu.getParentId() == 0) {
                sysMenuStock = (SysMenu) sysMenu.clone();
                if (null == sysMenuStock.getChildren()) {
                    sysMenuStock.setChildren(new ArrayList<>());
                }
                resultTreeList.add(sysMenuStock);
                //子集
                getChildren(sysMenuStock);
            }
        }
        return resultTreeList;
    }

    /**
     * @Author: Galen
     * @Description: 查子菜单（暂时不用递归）
     * @Date: 2019/4/25-17:52
     * @Param: [sysMenuStock]
     * @return: void
     **/
    private void getChildren(SysMenu sysMenuStock) {
        Iterator<SysMenu> menuIterator = sourceList.iterator();
        while (menuIterator.hasNext()) {
            SysMenu sysMenuSub = menuIterator.next();
            //节点的id = 子集的父级id
            if (sysMenuStock.getId().equals(sysMenuSub.getParentId())) {
                SysMenu sysMenuStockChild = (SysMenu) sysMenuSub.clone();
                if (null == sysMenuStockChild.getChildren()) {
                    sysMenuStockChild.setChildren(new ArrayList<>());
                }
                sysMenuStock.getChildren().add(sysMenuStockChild);
                //子集
                getChildren2(sysMenuStockChild);
            }
        }
    }

    private void getChildren2(SysMenu sysMenuStock) {
        Iterator<SysMenu> menuIterator = sourceList.iterator();
        while (menuIterator.hasNext()) {
            SysMenu sysMenuSub = menuIterator.next();
            //节点的id = 子集的父级id
            if (sysMenuStock.getId().equals(sysMenuSub.getParentId())) {
                sysMenuStock.getChildren().add((SysMenu) sysMenuSub.clone());
            }
        }
    }

    @Override
    public Response getRoleSysMenuList(Long roleId) {
        List<SysMenu> sysMenuList = sysMenuMapper.findSysMenuListByRoleId(roleId);
        return ResponseUtils.SUCCESS(sysMenuList);
    }

    @Override
    @Transactional
    public Response removeRoleSysMenu(Long roleId, Long menuId) {
        /**
         * @Author: Galen
         * @Description: 删除此菜单和角色的关联
         **/
//        SysMenuRole sysMenuRole = sysMenuRoleMapper.selectByMidPid(roleId, menuId);
        SysMenuRole sysMenuRole = sysMenuRoleMapper.findByRoleIdAndMenuId(roleId, menuId);
        if (null != sysMenuRole) {
//            sysMenuRoleMapper.deleteByPrimaryKey(sysMenuRole.getId());
            sysMenuRoleMapper.delete(sysMenuRole);

            /**
             * @Author: Galen
             * @Description: 同时删除此菜单的子菜单和角色的关联
             **/
//            sysMenuRoleMapper.deleteByParentId(roleId, menuId);
            sysMenuRoleMapper.deleteByRoleIdAndAndMenuId(roleId,menuId);
        }
        return ResponseUtils.SUCCESS("移除成功！");
    }

    @Override
    @Transactional
    public Response removeSysMenu(Long menuId) {
//        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(menuId);
        SysMenu sysMenu = sysMenuMapper.findById(menuId).get();
        if (null == sysMenu) {
            //防止多次点击
            return ResponseUtils.SUCCESS("移除成功！");
        }
        /**
         * @Author: Galen
         * @Description: 把子菜单的parentId改为 此菜单的parentId，实现所有菜单往上移动一层级
         **/
        sysMenuMapper.updateParentId(sysMenu.getParentId(),sysMenu.getId());
        /**
         * @Author: Galen
         * @Description: 删除这个菜单
         **/
//        sysMenuMapper.deleteByPrimaryKey(menuId);
        sysMenuMapper.delete(sysMenu);
        /**
         * @Author: Galen
         * @Description: 移除关联数据
         **/
        sysMenuRoleMapper.deleteByMenuId(menuId);
        return ResponseUtils.SUCCESS("移除成功！");
    }
}
