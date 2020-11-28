package com.vma.push.business.controller.superback;

import com.vma.push.business.dto.req.RspEnterDetailList;
import com.vma.push.business.dto.req.customer.ReqApplyPage;
import com.vma.push.business.dto.req.enterprise.ReqEnterpriseAllId;
import com.vma.push.business.dto.req.superback.ReqAddEnterprise;
import com.vma.push.business.dto.req.superback.ReqEditEnterprise;
import com.vma.push.business.dto.req.superback.ReqFindParam;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.customer.RspApplyEnterprise;
import com.vma.push.business.dto.rsp.customer.RspApplyList;
import com.vma.push.business.service.ICustomerService;
import com.vma.push.business.service.ISuperEnterpriseService;
import com.vma.push.business.dto.req.ReqEnterList;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.util.IndexCreate;
import com.vma.push.business.util.UserDataUtil;
import com.vma.push.business.util.UuidUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by huxiangqiang on 2018/6/13.
 */
@RestController(value = "SuperOemCustomerController")
@RequestMapping("/V1.0")
@Api(value = "贴牌商管理", description = "超级后台--贴牌商管理", tags = {"oemCustomerController"})
public class OemCustomerController {
    private Logger LOG = Logger.getLogger(this.getClass());

    @Autowired
    private ISuperEnterpriseService enterpriseService;
    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private IndexCreate indexCreate;

    private ISuperEnterpriseService iSuperEnterpriseService;

    @ApiOperation(value = "贴牌商列表",notes ="贴牌商列表" )
    @RequestMapping(value = "/super/oem/list",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqEnterList",value = "查询条件",required = true,dataType = "ReqEnterList")
    public RspPage<RspEnterList> oemlist(@RequestBody ReqEnterList reqEnterList, HttpServletRequest request){
        LOG.info("查询贴牌商列表");
        reqEnterList.setRole(4); //贴牌商权限
        reqEnterList.setParent_id("0"); //贴牌商上级id 默认是0
        return enterpriseService.enterList(reqEnterList,request);
    }



    @ApiOperation(value = "贴牌商信息",notes = "贴牌商信息")
    @RequestMapping(value = "/super/oem/{id}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "贴牌商id",required = true,dataType = "String",paramType = "path")
    public RspOemInfo oemInfo(@PathVariable("id") String id, HttpServletRequest request){
        LOG.info("贴牌商信息");
        return enterpriseService.oemInfo(id);
    }



    @ApiOperation(value = "下级地区总代列表",notes ="下级地区总代列表" )
    @RequestMapping(value = "/super/oem/area/list",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqFindParam",value = "查询条件",required = true,dataType = "ReqFindParam")
    public RspPage<RspEnterDetailList> arealist(@RequestBody ReqFindParam reqFindParam, HttpServletRequest request){
        LOG.info("下级地区总代列表");
        ReqEnterList reqEnterList=new ReqEnterList();
        reqEnterList.setRole(3);
        reqEnterList.setParent_id(reqFindParam.getParent_id());
        reqEnterList.setName_or_phone(reqFindParam.getName_or_phone());
        reqEnterList.setPage_num(reqFindParam.getPage_num());
        reqEnterList.setPage_size(reqFindParam.getPage_size());
        return enterpriseService.enterDetailList(reqEnterList);
    }
    @ApiOperation(value = "下级地区总代信息",notes = "下级地区总代信息")
    @RequestMapping(value = "/super/oem/area/{id}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "地区总代id",required = true,dataType = "String",paramType = "path")
    public RspAreaInfo areaInfo(@PathVariable("id") String id, HttpServletRequest request){
        LOG.info("下级地区总代信息");
        return enterpriseService.areaInfo(id);
    }

    @ApiOperation(value = "下级代理商列表",notes ="下级代理商列表" )
    @RequestMapping(value = "/super/oem/agent/list",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqFindParam",value = "查询条件",required = true,dataType = "ReqFindParam")
    public RspPage<RspEnterDetailList> agentlist(@RequestBody ReqFindParam reqFindParam, HttpServletRequest request){
        LOG.info("下级代理商列表");
        ReqEnterList reqEnterList=new ReqEnterList();
        reqEnterList.setRole(2);
        reqEnterList.setParent_id(reqFindParam.getParent_id());
        reqEnterList.setName_or_phone(reqFindParam.getName_or_phone());
        reqEnterList.setPage_num(reqFindParam.getPage_num());
        reqEnterList.setPage_size(reqFindParam.getPage_size());
        return enterpriseService.enterDetailList(reqEnterList);
    }

    @ApiOperation(value = "下级代理商信息",notes = "下级代理商信息")
    @RequestMapping(value = "/super/oem/agent/{id}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "地理商id",required = true,dataType = "String",paramType = "path")
    public RspAgentInfo agentInfo(@PathVariable("id") String id, HttpServletRequest request){
        LOG.info("下级代理商信息");
        return enterpriseService.agentInfo(id,request);
    }

    @ApiOperation(value = "新增贴牌商",notes ="新增贴牌商" )
    @RequestMapping(value = "/super/oem",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqEnterList",value = "新增贴牌商",required = true,dataType = "ReqAddEnterprise")
    public void oemAdd(@RequestBody ReqAddEnterprise reqEnterList, HttpServletRequest request){
        LOG.info("新增贴牌商");
        reqEnterList.setType(2); //admin表权限
        reqEnterList.setRole(4); //企业表权限
        String id= UuidUtil.getRandomUuid();
        String enterprise_no=indexCreate.getIndex("SO","SO");
        reqEnterList.setEnterprise_no(enterprise_no);
        reqEnterList.setParent_id(UserDataUtil.getCustomId(request));
        reqEnterList.setId(id);
        reqEnterList.setCreate_by(UserDataUtil.getAdminId(request));
        enterpriseService.addEnterAndAdmin(reqEnterList);
    }

    @ApiOperation(value = "编辑贴牌商",notes ="编辑贴牌商" )
    @RequestMapping(value = "/super/oem",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqEnterInfo",value = "编辑贴牌商",required = true,dataType = "ReqEditEnterprise")
    public void oemEdit(@RequestBody ReqEditEnterprise reqEnterInfo, HttpServletRequest request){
        LOG.info("编辑贴牌商");
        reqEnterInfo.setType(2); //admin表权限
        enterpriseService.editEnterAndAdmin(reqEnterInfo);
    }

}
