package com.vma.push.business.controller.superback;

import com.vma.push.business.dto.req.ReqEnterList;
import com.vma.push.business.dto.req.RspEnterDetailList;
import com.vma.push.business.dto.req.customer.ReqApplyPage;
import com.vma.push.business.dto.req.enterprise.ReqEnterpriseAllId;
import com.vma.push.business.dto.req.superback.ReqAddEnterprise;
import com.vma.push.business.dto.req.superback.ReqEditEnterprise;
import com.vma.push.business.dto.req.superback.ReqFindParam;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.rsp.customer.RspApplyEnterprise;
import com.vma.push.business.dto.rsp.customer.RspApplyList;
import com.vma.push.business.service.ICustomerService;
import com.vma.push.business.service.ISuperEnterpriseService;
import com.vma.push.business.util.IndexCreate;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by huxiangqiang on 2018/6/13.
 */
@RestController(value = "SuperAreaCustomerController")
@RequestMapping("/V1.0")
@Api(value = "地区总代理管理", description = "超级后台--地区总代理管理", tags = {"AreaCustomerController"})
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
    @RequestMapping(value = "/super/area/list",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqEnterList",value = "查询条件",required = true,dataType = "ReqEnterList")
    public RspPage<RspEnterList> arealist(@RequestBody ReqEnterList reqEnterList, HttpServletRequest request){
        LOG.info("查询地区总代列表");
        reqEnterList.setRole(3);
        reqEnterList.setParent_id(UserDataUtil.getCustomId(request));
        return enterpriseService.enterList(reqEnterList,request);
    }



    @ApiOperation(value = "地区总代信息",notes = "地区总代信息")
    @RequestMapping(value = "/super/area/{id}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "地区总代id",required = true,dataType = "String",paramType = "path")
    public RspAreaInfo areaInfo(@PathVariable("id") String id, HttpServletRequest request){
        LOG.info("地区总代信息");
        return enterpriseService.areaInfo(id);
    }

    @ApiOperation(value = "下级代理商列表",notes ="下级代理商列表" )
    @RequestMapping(value = "/super/area/agent/list",method = RequestMethod.POST)
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
    @RequestMapping(value = "/super/area/agent/{id}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "地理商id",required = true,dataType = "String",paramType = "path")
    public RspAgentInfo agentInfo(@PathVariable("id") String id, HttpServletRequest request){
        LOG.info("下级代理商信息");
        return enterpriseService.agentInfo(id,request);
    }



}
