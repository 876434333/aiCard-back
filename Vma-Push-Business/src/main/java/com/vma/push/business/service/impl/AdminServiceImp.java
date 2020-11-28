package com.vma.push.business.service.impl;

import com.alibaba.druid.wall.violation.ErrorCode;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dao.AdminMapper;
import com.vma.push.business.dao.MenuResourceMapper;
import com.vma.push.business.dao.UserMenuRefMapper;
import com.vma.push.business.dto.req.ReqAdminMenu;
import com.vma.push.business.dto.req.ReqEditMenuRole;
import com.vma.push.business.dto.req.ReqUserMeunRole;
import com.vma.push.business.dto.req.superback.ReqAddAdmin;
import com.vma.push.business.dto.req.superback.ReqAdminPage;
import com.vma.push.business.dto.req.superback.ReqUpdateAdmin;
import com.vma.push.business.dto.req.superback.ReqUpdatePassword;
import com.vma.push.business.dto.rsp.RspUserMenuRole;
import com.vma.push.business.dto.rsp.superback.RspAdminMenu;
import com.vma.push.business.dto.rsp.superback.RspPage;
import com.vma.push.business.dto.rsp.superback.RspAdmin;
import com.vma.push.business.entity.Admin;
import com.vma.push.business.entity.UserMenuRef;
import com.vma.push.business.service.IAdminService;
import com.vma.push.business.util.UserDataUtil;
import com.vma.push.business.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * created by linzh on 2018/6/14
 */
@Service
public class AdminServiceImp  extends AbstractServiceImpl<Admin,String> implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserMenuRefMapper refMapper;

    @Autowired
    private MenuResourceMapper resourceMapper;
    public AdminServiceImp(AdminMapper mapper) {
        super(mapper);
        this.adminMapper = mapper;
    }
    @Override
    public void grantMenu(ReqAdminMenu reqAdminMenu){
        String menuId = reqAdminMenu.getAdmin_id();
        String[] strArray = null;
        strArray = menuId.split(",");
        //删除原先的权限
        refMapper.deleRef(reqAdminMenu.getAdmin_id());
        for(int i=0; i<strArray.length; i++){
            UserMenuRef ref = new UserMenuRef();
            ref.setMenuId(strArray[i]);
            ref.setUserId(reqAdminMenu.getAdmin_id());
            ref.setId(UuidUtil.getRandomUuid());

            //添加新的权限
            refMapper.insert(ref);
        }

    }

    @Override
    public void addAdmin(ReqAddAdmin reqAddAdmin){

        Admin admin = new Admin();
        admin.setRole(0);
        admin.setId(UuidUtil.getRandomUuid());
        admin.setPassWord(reqAddAdmin.getPass_word());
        admin.setName(reqAddAdmin.getName());
        admin.setCreateId(reqAddAdmin.getCreate_id());
        admin.setCustomId(reqAddAdmin.getCustom_id());
        admin.setRemark(reqAddAdmin.getRemark());
        admin.setType(reqAddAdmin.getType());
        admin.setLogin(reqAddAdmin.getLogin());
        admin.setCreateTime(new Date());
        if (adminMapper.isExist(admin)>0){
            throw new BusinessException(ErrorCode.BITWISE,"用户名已存在");
        }else
        {
            adminMapper.insertSelective(admin);
        }

    }

    @Override
    public void updateAdmin(ReqUpdateAdmin reqUpdateAdmin){
        Admin admin = new Admin();
        admin.setName(reqUpdateAdmin.getName());
        admin.setRemark(reqUpdateAdmin.getRemark());
        admin.setLogin(reqUpdateAdmin.getLogin());
        admin.setPhone(reqUpdateAdmin.getLogin());
        admin.setId(reqUpdateAdmin.getId());
        if (adminMapper.isExist(admin)>0){
            throw new BusinessException(ErrorCode.BITWISE,"用户名已存在");
        }
        else{
            adminMapper.updateByPrimaryKeySelective(admin);
        }

    }

    @Override
    public RspPage<RspAdmin> findAdminBySelect(ReqAdminPage reqAdminPage, HttpServletRequest request){
        reqAdminPage.setCustom_id(UserDataUtil.getCustomId(request));
        RspPage<RspAdmin> rspPage = new RspPage<>();
        Page page = PageHelper.startPage(reqAdminPage.getPage_num(),reqAdminPage.getPage_size(),true);
        List<RspAdmin> rspAdmin = adminMapper.findPage(reqAdminPage);
        rspPage.setData_list(rspAdmin);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return rspPage;
    }

    @Override
    public void updateAdminStatus(Admin admin){
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public List<RspUserMenuRole> userMenuRole(ReqUserMeunRole reqUserMeunRole) {
        List<RspUserMenuRole> resources = resourceMapper.userMenuRole(reqUserMeunRole);
        for (RspUserMenuRole list:resources){
            if (resourceMapper.isRole(reqUserMeunRole.getUser_id(),list.getMenu_id())==null){
                list.setIs_role(0);
            }else{
                list.setIs_role(resourceMapper.isRole(reqUserMeunRole.getUser_id(),list.getMenu_id()));
            }

            ReqUserMeunRole reqUserMeunRole1=new ReqUserMeunRole();
            reqUserMeunRole1.setRole(reqUserMeunRole.getRole());
            reqUserMeunRole1.setParent_id(list.getMenu_id());
            reqUserMeunRole1.setUser_id(reqUserMeunRole.getUser_id());
            list.setChild(userMenuRole(reqUserMeunRole1));
        }

        //二次处理数据
        for (RspUserMenuRole list2:resources){
            if (list2.getChild().size()>0){
                //如果子节点中role=0 则为0
                list2.setIs_role(isRole2(list2.getChild()));
            }
        }
        return resources;
    }
    public Integer isRole2(List<RspUserMenuRole> lists){
        Integer result=1;
        for(RspUserMenuRole list:lists){
            if (list.getIs_role()==0){
                result=0;
                break;
            }
            result=isRole(list.getChild());
        }
        return result;
    }

    /**
     * 重置密码
     * @param reqUpdatePassword
     */
    @Override
    public void updatePassword(ReqUpdatePassword reqUpdatePassword) {
      adminMapper.updatePassword(reqUpdatePassword);
    }

    @Override
    public void editUserMenuRole(List<RspUserMenuRole> lists,String id) {
        for(RspUserMenuRole list:lists){
            //System.out.println(list.getMenu_id()+":"+list.getIs_Role()+":"+id);
            ReqEditMenuRole reqEditMenuRole=new ReqEditMenuRole();
            reqEditMenuRole.setUserid(id);
            reqEditMenuRole.setMenuid(list.getMenu_id());
            //设置节点权限，如果子节点中有role等于1 则设置成1  否则0
            if (list.getChild().size()==0){
                reqEditMenuRole.setRole(list.getIs_role());
            }else{
                reqEditMenuRole.setRole(isRole(list.getChild()));
            }

            //判断原先是否设置过菜单权限，有的修改，没有增加；
            if (resourceMapper.isSeted(reqEditMenuRole)>0){
                //修改
                resourceMapper.editUserMenuRole(reqEditMenuRole);
            }else{
                //增加
                resourceMapper.addUserMenuRole(reqEditMenuRole);
            }

            editUserMenuRole(list.getChild(),id);
        }
    }
    public Integer isRole(List<RspUserMenuRole> lists){
        Integer result=0;
        for(RspUserMenuRole list:lists){
            if (list.getIs_role()==1){
                result=1;
                break;
            }
            result=isRole(list.getChild());
        }
        return result;
    }
/*
    private void child(String userId, List<RspUserMenuRole> resources){
        for(RspUserMenuRole menu:resources){
            if(menu.getIs_leaf().equals("0")){
                List<RspUserMenuRole> child = resourceMapper.getMenuChildByUserId(userId,menu.getId());
                menu.setChild(child);
                child(userId,child);
            }
        }
    }*/

    @Override
    public Admin getAdmin(String adminId) {
        return adminMapper.queryEnterAdmin(adminId);
    }
}
