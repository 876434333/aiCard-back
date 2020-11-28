package com.vma.push.business.controller.sys;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.dto.req.ReqAccountSelect;
import com.vma.push.business.dto.req.ReqAddAccount;
import com.vma.push.business.dto.req.ReqUpdateAccount;
import com.vma.push.business.dto.rsp.RspAccount;
import com.vma.push.business.dto.rsp.RspAccountOne;
import com.vma.push.business.dto.rsp.RspAgent;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.entity.Admin;
import com.vma.push.business.service.ISuperStaffService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/5/8 16:05
 */
@RestController
@RequestMapping("/v3.0")
@Api(value = "超级后台账号管理API", description = "超级后台--超级后台账号管理", tags = {"SuperStaffController"})
public class SuperStaffController  extends ControllerExceptionHandler {
    @Autowired
  private ISuperStaffService iSuperStaffService;

    /**
     * 账号列表
     * @param reqAccountSelect
     * @return
     */
    @ApiOperation(value = "账号列表", notes = "账号列表")
    @RequestMapping(value = "/account/select", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqAccountSelect",value = "查看列表",dataType = "ReqAccountSelect")
    public RspPage<RspAccount> findAllAccount(@RequestBody ReqAccountSelect reqAccountSelect,HttpServletRequest request){
        String adminId = UserDataUtil.getStaffId(request);
        RspPage<RspAccount> rspPage = iSuperStaffService.findAllAccount(reqAccountSelect,adminId);
        return  rspPage;
    }

  /**
   * 代理商列表
   * @return
   */
    @ApiOperation(value = "代理商列表",notes ="代理商列表" )
    @RequestMapping(value = "/agent/page", method = RequestMethod.POST)
    public List<RspAgent> findAllRspAgent(){
      return iSuperStaffService.findAllAgent();
    }

    /**
     * 添加账号
     * @param reqAddAccount
     */
    @ApiOperation(value = "添加账号", notes = "添加账号")
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqAddAccount",value = "添加账号",dataType = "ReqAddAccount")
    public void addAccount(@RequestBody ReqAddAccount reqAddAccount,HttpServletRequest request){
        String adminId = UserDataUtil.getStaffId(request);
        iSuperStaffService.addAccount(reqAddAccount,adminId);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @ApiOperation(value = "查看账号详情", notes = "查看账号详情")
    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "查看账号参数",paramType = "path",required = true,dataType = "String")
    public RspAccountOne findAccountById(@PathVariable String id){
        return  iSuperStaffService.findAccountById(id);
    }


    /**
     * 修改账号
     * @param reqUpdateAccount
     */
    @ApiOperation(value = "修改账号",notes = "修改账号")
    @RequestMapping(value = "/account",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateAccount",required = true,value = "修改产品",dataType = "ReqUpdateAccount")
    public void updateAccount(@RequestBody ReqUpdateAccount reqUpdateAccount){
      iSuperStaffService.updateAccount(reqUpdateAccount);
    }

}
