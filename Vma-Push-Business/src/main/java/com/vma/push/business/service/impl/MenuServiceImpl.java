package com.vma.push.business.service.impl;

import com.vma.push.business.dao.MenuInfoMapper;
import com.vma.push.business.dto.rsp.menu
        .RspMenu;
import com.vma.push.business.entity.MenuInfo;
import com.vma.push.business.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenzui on 2018/5/8.
 */
@Service
public class MenuServiceImpl extends AbstractServiceImpl<MenuInfo,String> implements IMenuService {
    @Autowired
    private MenuInfoMapper mapper;

    public MenuServiceImpl(MenuInfoMapper base) {
        super(base);
        this.mapper = base;
    }

    @Override
    public List<RspMenu> list(String staffId) {
        List<RspMenu> parents = mapper.selectChild("0");
        for (RspMenu menu:parents) {
            List<RspMenu> childs = mapper.selectChild(menu.getId());
            menu.setChild(childs);
        }

        return parents;
    }
}
