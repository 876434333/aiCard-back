package com.vma.push.business.service;

import com.vma.push.business.dto.rsp.superback.RspAdminMenu;
import com.vma.push.business.entity.MenuResource;

import java.util.List;

/**
 * created by linzh on 2018/6/14
 */
public interface IMenuResourceService extends IBaseService<MenuResource,String>{
    public List<RspAdminMenu> getMenuByUserId(String UserId); //查询用户所拥有的菜单
    public List<RspAdminMenu> getMenuByRole(String UserId); //查询系统菜单列表
}
