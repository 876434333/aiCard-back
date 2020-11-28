package com.vma.push.business.service.impl;

import com.vma.push.business.dao.AdminMapper;
import com.vma.push.business.dao.MenuResourceMapper;
import com.vma.push.business.dto.rsp.superback.RspAdminMenu;
import com.vma.push.business.entity.Admin;
import com.vma.push.business.entity.MenuResource;
import com.vma.push.business.service.ILoginService;
import com.vma.push.business.service.IMenuResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by linzh on 2018/6/14
 */
@Service
public class MenuResourceServiceImp extends AbstractServiceImpl<MenuResource,String> implements IMenuResourceService {
    @Autowired
    private MenuResourceMapper resourceMapper;

    public MenuResourceServiceImp(MenuResourceMapper mapper) {
        super(mapper);
        this.resourceMapper = mapper;
    }
    @Override
    public List<RspAdminMenu> getMenuByUserId(String userId){
        List<RspAdminMenu> resources = resourceMapper.getMenuByUserId(userId);
        child(userId,resources);
        return resources;
    }

    @Override
    public List<RspAdminMenu> getMenuByRole(String UserId) {
        return null;
    }

    private void child(String userId, List<RspAdminMenu> resources){
        for(RspAdminMenu menu:resources){
            if(menu.getIs_leaf().equals("0")){
                List<RspAdminMenu> child = resourceMapper.getMenuChildByUserId(userId,menu.getId());
                menu.setChild(child);
                child(userId,child);
            }
        }
    }
}
