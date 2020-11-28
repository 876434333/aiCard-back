package com.vma.push.business.controller.area;

import com.vma.push.business.dto.req.ReqEnterList;
import com.vma.push.business.dto.req.superback.ReqAddEnterprise;
import com.vma.push.business.dto.req.superback.ReqEditEnterprise;
import com.vma.push.business.dto.rsp.RspAgentInfo;
import com.vma.push.business.dto.rsp.RspEnterInfo;
import com.vma.push.business.dto.rsp.RspEnterList;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.superback.RspEnterpariseList;
import com.vma.push.business.service.ICustomerService;
import com.vma.push.business.service.ISuperEnterpriseService;
import com.vma.push.business.util.UserDataUtil;
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
@RestController(value = "areaEnterCustomerController")
@RequestMapping("/V1.0")
@Api(value = "客户管理", description = "地区总代--企业管理", tags = {"EnterCustomerController"})
public class EnterCustomerController {
    private Logger LOG = Logger.getLogger(this.getClass());

    @Autowired
    private ISuperEnterpriseService enterpriseService;
    @Autowired
    private ICustomerService iCustomerService;

    private ISuperEnterpriseService iSuperEnterpriseService;





    @ApiOperation(value = "企业列表",notes ="企业列表" )
    @RequestMapping(value = "/area/enterprise/list",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqEnterList",value = "查询条件",required = true,dataType = "ReqEnterList")
    public RspPage<RspEnterpariseList> enterlist(@RequestBody ReqEnterList reqEnterList, HttpServletRequest request){
        LOG.info("企业列表");
        reqEnterList.setRole(1);
        reqEnterList.setParent_id(UserDataUtil.getCustomId(request));
        reqEnterList.setIs_deploy(1);
        return enterpriseService.enterpariseList(reqEnterList);
    }
    @ApiOperation(value = "企业信息",notes = "企业信息")
    @RequestMapping(value = "/area/enterprise/{id}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "地理商id",required = true,dataType = "String",paramType = "path")
    public RspEnterInfo enterInfo(@PathVariable("id") String id, HttpServletRequest request){
        LOG.info("企业信息");
        return enterpriseService.enterInfo(id);
    }







}
