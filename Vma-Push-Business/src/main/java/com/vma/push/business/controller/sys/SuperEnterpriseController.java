package com.vma.push.business.controller.sys;

/**
 * Created by ChenXiaoBin
 * 2018/5/5 1:36
 */

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.dto.req.ReqAddEnterprise;
import com.vma.push.business.dto.req.ReqEnterpriseSelect;
import com.vma.push.business.dto.req.ReqUpdateEnterprise;
import com.vma.push.business.dto.req.enterprise.ReqEnterpriseAllId;
import com.vma.push.business.dto.rsp.RspAllEnterprise;
import com.vma.push.business.dto.rsp.RspEnterprise;
import com.vma.push.business.dto.rsp.RspEnterpriseAllId;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.service.ISuperEnterpriseService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/v3.0")
@Api(value = "超级后台管理API", description = "超级后台--超级后台管理", tags = {"SuperEnterpriseController"})
public class SuperEnterpriseController  extends ControllerExceptionHandler {
    @Autowired
    private ISuperEnterpriseService iSuperEnterpriseService;

    /**
     * 企业信息列表
     * @param reqEnterpriseSelect
     * @return
     */
    @ApiOperation(value = "企业信息列表", notes = "企业信息列表")
    @RequestMapping(value = "/enterprise/page", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqEnterpriseSelect",value = "查看产品列表",dataType = "ReqEnterpriseSelect")
    public RspPage<RspAllEnterprise> findAllEnterprise(@RequestBody ReqEnterpriseSelect reqEnterpriseSelect,HttpServletRequest request){
        //String adminId =  UserDataUtil.getStaffId(request);
        String adminId = "1";
        return iSuperEnterpriseService.findAllEnterprise(reqEnterpriseSelect,adminId);
    }

    /**
     * 根据id查询企业
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查看企业信息", notes = "根据id查看企业信息")
    @RequestMapping(value = "/enterprise/{id}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "查看企业参数",paramType = "path",required = true,dataType = "String")
    public RspAllEnterprise findEnterprise (@PathVariable String id){
        RspAllEnterprise rspAllEnterprise = iSuperEnterpriseService.findEnterpriseById(id);
        return  rspAllEnterprise;
    }

    /**
     * 修改企业信息
     * @param reqUpdateEnterprise
     */
    @ApiOperation(value = "修改企业信息",notes = "修改企业信息")
    @RequestMapping(value = "/enterprise/update",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateEnterprise",value = "修改企业信息",dataType = "ReqUpdateEnterprise")
    public void updateEnterprise (@RequestBody ReqUpdateEnterprise reqUpdateEnterprise){
        iSuperEnterpriseService.updateEnterprise(reqUpdateEnterprise);
    }

    /**
     * 添加企业信息
     * @param reqAddEnterprise
     */
    @ApiOperation(value = "添加企业信息",notes = "添加企业信息")
    @RequestMapping(value = "/enterprise", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqAddEnterprise",value = "添加企业信息",dataType = "ReqAddEnterprise")
    public void addEnterprise(@RequestBody ReqAddEnterprise reqAddEnterprise,HttpServletRequest request){
        String id = UserDataUtil.getStaffId(request);
        iSuperEnterpriseService.addEnterprise(reqAddEnterprise,id);
    }

    @ApiOperation(value = "修改各种密钥",notes = "修改各种密钥")
    @RequestMapping(value = "/updateAllId",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqEnterpriseAllId",required = true,value = "修改各种密钥",dataType = "ReqEnterpriseAllId")
    public void updateEnterseAllId(@RequestBody ReqEnterpriseAllId reqEnterpriseAllId) throws IOException {
        iSuperEnterpriseService.updateEnterseAllId(reqEnterpriseAllId);
    }

    @ApiOperation(value = "根据id查询各种密钥", notes = "根据id查询各种密钥")
    @RequestMapping(value = "/enterpriseAllId/{id}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "查看参数",paramType = "path",required = true,dataType = "String")
    public RspEnterpriseAllId selectEnterpriseId(@PathVariable String id){
        return  iSuperEnterpriseService.selectEnterpriseId(id);
    }

    @ApiOperation(value = "列表", notes = "列表")
    @RequestMapping(value = "/enterpriseALlId/page", method = RequestMethod.POST)
    public RspPage<RspEnterpriseAllId> selectEnterpriseIdList(HttpServletRequest request){
        String adminId =  UserDataUtil.getStaffId(request);
        return iSuperEnterpriseService.selectEnterpriseIdList(adminId);
    }
}
