package com.vma.push.business.service;

import com.vma.push.business.dto.rsp.menu.RspMenu;
import com.vma.push.business.entity.MenuInfo;

import java.util.List;

/**
 * Created by chenzui on 2018/5/8.
 */
public interface IMenuService extends IBaseService<MenuInfo,String>{

    public List<RspMenu> list(String staffId);
}
