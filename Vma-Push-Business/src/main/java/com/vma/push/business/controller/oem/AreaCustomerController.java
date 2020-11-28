package com.vma.push.business.controller.oem;

import com.vma.push.business.dto.req.ReqEnterList;
import com.vma.push.business.dto.req.RspEnterDetailList;
import com.vma.push.business.dto.req.superback.ReqAddEnterprise;
import com.vma.push.business.dto.req.superback.ReqEditEnterprise;
import com.vma.push.business.dto.req.superback.ReqFindParam;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.service.ICustomerService;
import com.vma.push.business.service.ISuperEnterpriseService;
import com.vma.push.business.util.IndexCreate;
import com.vma.push.business.util.UserDataUtil;
import com.vma.push.business.util.UuidUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by huxiangqiang on 2018/6/13.
 */
@RestController(value = "oemAreaCustomerController")
@RequestMapping("/V1.0")
@Api(value = "客户管理", description = "贴牌商--地区总代理管理", tags = {"AreaCustomerController"})
public class AreaCustomerController {
    private Logger LOG = Logger.getLogger(this.getClass());

    @Autowired
    private ISuperEnterpriseService enterpriseService;
    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private IndexCreate indexCreate;

    private ISuperEnterpriseService iSuperEnterpriseService;


    @ApiOperation(value = "地区总代列表",notes ="地区总代列表" )
    @RequestMapping(value = "/oem/area/list",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqEnterList",value = "查询条件",required = true,dataType = "ReqEnterList")
    public RspPage<RspEnterList> arealist(@RequestBody ReqEnterList reqEnterList, HttpServletRequest request){
        LOG.info("地区总代列表");
        reqEnterList.setRole(3);
        reqEnterList.setParent_id(UserDataUtil.getCustomId(request));
        return enterpriseService.enterList(reqEnterList,request);
    }



    @ApiOperation(value = "地区总代信息",notes = "地区总代信息")
    @RequestMapping(value = "/oem/area/{id}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "地区总代id",required = true,dataType = "String",paramType = "path")
    public RspAreaInfo areaInfo(@PathVariable("id") String id, HttpServletRequest request){
        LOG.info("地区总代信息");
        return enterpriseService.areaInfo(id);
    }


    @ApiOperation(value = "下级代理商列表",notes ="下级代理商列表" )
    @RequestMapping(value = "/oem/area/agent/list",method = RequestMethod.POST)
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
    @RequestMapping(value = "/oem/area/agent/{id}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "地理商id",required = true,dataType = "String",paramType = "path")
    public RspAgentInfo agentInfo(@PathVariable("id") String id, HttpServletRequest request){
        LOG.info("下级代理商信息");
        return enterpriseService.agentInfo(id,request);
    }

    @ApiOperation(value = "新增地区总代",notes ="新增地区总代" )
    @RequestMapping(value = "/oem/area",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqEnterList",value = "新增地区总代",required = true,dataType = "ReqAddEnterprise")
    public void areaAdd(@RequestBody ReqAddEnterprise reqEnterList, HttpServletRequest request){
        LOG.info("新增地区总代");
        reqEnterList.setType(3);
        reqEnterList.setRole(3);
        String id= UuidUtil.getRandomUuid();
        String enterprise_no=indexCreate.getIndex("OAR","OAR");
        reqEnterList.setEnterprise_no(enterprise_no);
        reqEnterList.setParent_id(UserDataUtil.getCustomId(request));
        reqEnterList.setId(id);
        enterpriseService.addEnterAndAdmin(reqEnterList);
    }
    @ApiOperation(value = "编辑地区总代",notes ="编辑地区总代" )
    @RequestMapping(value = "/oem/area",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqEnterInfo",value = "编辑地区总代",required = true,dataType = "ReqEditEnterprise")
    public void areaEdit(@RequestBody ReqEditEnterprise reqEnterInfo, HttpServletRequest request){
        LOG.info("编辑地区总代");
        reqEnterInfo.setType(3); //admin表权限
        enterpriseService.editEnterAndAdmin(reqEnterInfo);
    }





}
