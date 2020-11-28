package com.vma.push.business.service;

import com.vma.push.business.dto.req.ReqAdminMenu;
import com.vma.push.business.dto.req.ReqAdvSelect;
import com.vma.push.business.dto.req.ReqUserMeunRole;
import com.vma.push.business.dto.req.superback.ReqAddAdmin;
import com.vma.push.business.dto.req.superback.ReqAdminPage;
import com.vma.push.business.dto.req.superback.ReqUpdateAdmin;
import com.vma.push.business.dto.req.superback.ReqUpdatePassword;
import com.vma.push.business.dto.rsp.RspUserMenuRole;
import com.vma.push.business.dto.rsp.superback.RspPage;
import com.vma.push.business.dto.rsp.superback.RspAdmin;
import com.vma.push.business.entity.Admin;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * created by linzh on 2018/6/14
 */
public interface IAdminService extends IBaseService<Admin,String>{
    public void grantMenu(ReqAdminMenu reqAdminMenu); //分配员工权限
    public void addAdmin(ReqAddAdmin reqAddAdmin); //添加员工
    public void updateAdmin(ReqUpdateAdmin reqUpdateAdmin); //修改员工
    public RspPage<RspAdmin> findAdminBySelect(ReqAdminPage reqAdminPage, HttpServletRequest request);//条件查询
    public void updateAdminStatus(Admin admin); //启用/禁用员工账号
    public List<RspUserMenuRole> userMenuRole(ReqUserMeunRole reqUserMeunRole);

    void updatePassword(ReqUpdatePassword reqUpdatePassword);//修改密码
    //修改用户菜单权限
    void editUserMenuRole(List<RspUserMenuRole> lists,String id);
    Admin getAdmin(String adminId);
}
