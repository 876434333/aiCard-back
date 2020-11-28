package com.vma.push.business.dao;


import com.vma.push.business.dto.req.ReqEditMenuRole;
import com.vma.push.business.dto.req.ReqUserMeunRole;
import com.vma.push.business.dto.rsp.RspUserMenuRole;
import com.vma.push.business.dto.rsp.superback.RspAdminMenu;
import com.vma.push.business.entity.MenuResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * created by linzh on 2018/6/14
 */
public interface MenuResourceMapper extends BaseDao<MenuResource,String> {
    public List<RspAdminMenu> getMenuByUserId(String UserId);
    public List<RspAdminMenu> getMenuByRole(String role);

    public List<RspAdminMenu> getMenuChildByUserId(@Param("userId") String UserId,@Param("menuId") String menuId);
    List<RspUserMenuRole> userMenuRole(ReqUserMeunRole reqUserMeunRole);
    Integer isRole(@Param("userid") String userid,@Param("menuid") String menuid);
    void editUserMenuRole(ReqEditMenuRole reqEditMenuRole);
    Integer isSeted(ReqEditMenuRole reqEditMenuRole);
    void addUserMenuRole(ReqEditMenuRole reqEditMenuRole);
}
