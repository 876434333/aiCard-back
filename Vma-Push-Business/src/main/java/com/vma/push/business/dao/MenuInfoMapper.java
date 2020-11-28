package com.vma.push.business.dao;

import com.vma.push.business.dto.rsp.menu.RspMenu;
import com.vma.push.business.entity.MenuInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by chenzui on 2018/5/8.
 */
public interface MenuInfoMapper extends BaseDao<MenuInfo,String> {
    public List<RspMenu> selectChild(@Param("id") String id);
}
