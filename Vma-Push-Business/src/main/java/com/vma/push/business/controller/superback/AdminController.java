package com.vma.push.business.controller.superback;

import com.alibaba.druid.wall.violation.ErrorCode;
import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dto.req.ReqAdminMenu;
import com.vma.push.business.dto.req.ReqUserMeunRole;
import com.vma.push.business.dto.req.superback.ReqAddAdmin;
import com.vma.push.business.dto.req.superback.ReqAdminPage;
import com.vma.push.business.dto.req.superback.ReqUpdateAdmin;
import com.vma.push.business.dto.rsp.RspUserMenuRole;
import com.vma.push.business.dto.rsp.superback.RspAdmin;
import com.vma.push.business.dto.rsp.superback.RspPage;
import com.vma.push.business.entity.Admin;
import com.vma.push.business.service.IAdminService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by linzh on 2018/6/14
 */
@RestController(value = "AdminController")
@RequestMapping("/V1.0")
@Api(value = "超级后台员工管理API", description = "超级后台--员工管理", tags = {"AdminController"})
public class AdminController extends ControllerExceptionHandler {
    private Logger LOG = Logger.getLogger(this.getClass());
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "分配权限", notes = "分配权限")
    @RequestMapping(value = "/common/admin/grant", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqAdminMenu",value = "后台系统登陆",dataType = "ReqAdminMenu")
    public void grant(@RequestBody ReqAdminMenu reqAdminMenu, HttpServletRequest request){
        Map<String,String> ret = new HashMap<String,String>();
        adminService.grantMenu(reqAdminMenu);
    }

    @ApiOperation(value = "获取用户权限", notes = "获取用户权限菜单")
    @RequestMapping(value = "/common/admin/grant/{id}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "查看企业参数",paramType = "path",required = true,dataType = "String")
    public List<RspUserMenuRole> userMenuRoles(@PathVariable String id, HttpServletRequest request){
        ReqUserMeunRole reqAdminMenu=new ReqUserMeunRole();
        reqAdminMenu.setParent_id("0");
        Integer role=Integer.parseInt(UserDataUtil.getUserType(request));
        reqAdminMenu.setRole(role);
        reqAdminMenu.setUser_id(id);
        return adminService.userMenuRole(reqAdminMenu);
    }

    @ApiOperation(value = "修改用户权限", notes = "修改用户权限菜单")
    @RequestMapping(value = "/common/admin/grant/{id}", method = RequestMethod.PUT)
    @ApiImplicitParam(name = "id",value = "查看企业参数",required = true,dataType = "String",paramType = "path")
    public void userMenuRoles(@PathVariable String id, @RequestBody List<RspUserMenuRole> lists, HttpServletRequest request){
        adminService.editUserMenuRole(lists,id);
    }




        @ApiOperation(value = "添加员工", notes = "添加员工")
    @RequestMapping(value = "/common/admin", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqAddAdmin",value = "员工信息",dataType = "ReqAddAdmin")
    public void add(@RequestBody ReqAddAdmin reqAddAdmin, HttpServletRequest request){
        String adminId = UserDataUtil.getAdminId(request);
        String customId = UserDataUtil.getCustomId(request);
        if(UserDataUtil.getUserType(request) != null){
            int type = Integer.valueOf(UserDataUtil.getUserType(request));
            reqAddAdmin.setCustom_id(customId);
            reqAddAdmin.setCreate_id(adminId);
            reqAddAdmin.setType(type);
            adminService.addAdmin(reqAddAdmin);
        }
        else{
            throw new BusinessException(ErrorCode.BITWISE,"请登陆");
        }
    }

    @ApiOperation(value = "修改员工信息", notes = "修改员工信息")
    @RequestMapping(value = "/common/admin", method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateAdmin",value = "员工信息",dataType = "ReqUpdateAdmin")
    public void update(@RequestBody ReqUpdateAdmin reqUpdateAdmin, HttpServletRequest request){
        adminService.updateAdmin(reqUpdateAdmin);
    }

    @ApiOperation(value = "员工明细",notes = "查询员工明细")
    @RequestMapping(value = "/common/admin/{id}",method = RequestMethod.GET)
    public RspAdmin getById(
            @ApiParam(required=true, name="id", value="员工ID") @PathVariable("id") String id){
        LOG.info("员工明细");
        Admin admin = adminService.selectByPrimaryKey(id);
        RspAdmin rspAdmin = new RspAdmin();
        rspAdmin.setName(admin.getName());
        rspAdmin.setLogin(admin.getLogin());
        rspAdmin.setRemark(admin.getRemark());
        rspAdmin.setPass_word(admin.getPassWord());
        return rspAdmin;
    }

    @ApiOperation(value = "条件查询列表", notes = "条件查询列表")
    @RequestMapping(value = "/common/admin/list", method = RequestMethod.POST)
    public RspPage<RspAdmin> adminPage(@RequestBody ReqAdminPage reqAdminPage, HttpServletRequest request){
        LOG.info("条件查询员工列表");
        return adminService.findAdminBySelect(reqAdminPage,request);

    }

    @ApiOperation(value = "启用/禁用员工账号", notes = "启用/禁用员工账号")
    @RequestMapping(value = "/common/admin/{id}/status/{status}", method = RequestMethod.PUT)
    public void updateStatus(
            @ApiParam(required=true, name="id", value="员工ID") @PathVariable("id") String id,
            @ApiParam(required=true, name="status", value="状态") @PathVariable("status")  Integer status){
        LOG.info("启用/禁用员工账号");
        Admin admin = adminService.selectByPrimaryKey(id);
        if(admin != null){
            admin.setStatus(status);
            adminService.updateAdminStatus(admin);
        }
    }
    @ApiOperation(value = "删除员工",notes = "删除员工")
    @RequestMapping(value = "/common/admin/{id}",method = RequestMethod.DELETE)
    public void delete(@ApiParam(required=true, name="id", value="ID") @PathVariable("id")  String id){
        Admin admin = adminService.selectByPrimaryKey(id);
        if(admin.getRole().equals(1)){
            throw new BusinessException(ErrCode.ADMIN_ROLE,"管理员不能删除");
        }else {
            adminService.deleteByPrimaryKey(id);
        }
    }
}
