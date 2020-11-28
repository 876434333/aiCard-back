package com.vma.push.business.controller.superback;

import com.vma.push.business.dto.req.ReqEnterList;
import com.vma.push.business.dto.req.ReqUpdateDeploy;
import com.vma.push.business.dto.req.customer.ReqApplyPage;
import com.vma.push.business.dto.req.enterprise.ReqEnterpriseAllId;
import com.vma.push.business.dto.req.superback.ReqAddEnterprise;
import com.vma.push.business.dto.req.superback.ReqEditEnterprise;
import com.vma.push.business.dto.req.superback.ReqUpdateEnterStatus;
import com.vma.push.business.dto.req.superback.ReqUpdatePassword;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.rsp.customer.RspApplyEnterprise;
import com.vma.push.business.dto.rsp.customer.RspApplyList;
import com.vma.push.business.dto.rsp.superback.RspEnterpariseList;
import com.vma.push.business.service.IAdminService;
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
@RestController(value = "SuperEnterCustomerController")
@RequestMapping("/V1.0")
@Api(value = "企业管理", description = "超级后台--企业管理", tags = {"EnterCustomerController"})
public class EnterCustomerController {
    private Logger LOG = Logger.getLogger(this.getClass());

    @Autowired
    private ISuperEnterpriseService enterpriseService;
    @Autowired
    private ICustomerService iCustomerService;
    @Autowired
    private IndexCreate indexCreate;
    @Autowired
    private ISuperEnterpriseService iSuperEnterpriseService;
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "企业列表",notes ="企业列表" )
    @RequestMapping(value = "/super/enterprise/list",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqEnterList",value = "查询条件",required = true,dataType = "ReqEnterList")
    public RspPage<RspEnterpariseList> enterlist(@RequestBody ReqEnterList reqEnterList, HttpServletRequest request){
        LOG.info("查询企业列表");
        reqEnterList.setRole(1);
        reqEnterList.setParent_id(UserDataUtil.getCustomId(request));
//        reqEnterList.setIs_deploy(1);
        return enterpriseService.enterpariseList(reqEnterList);
    }


    @ApiOperation(value = "企业信息",notes = "企业信息")
    @RequestMapping(value = "/super/enterprise/{id}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "地理商id",required = true,dataType = "String",paramType = "path")
    public RspEnterInfo enterInfo(@PathVariable("id") String id, HttpServletRequest request){
        LOG.info("企业信息");
        return enterpriseService.enterInfo(id);
    }



    @ApiOperation(value = "条件查询申请列表", notes = "条件查询申请列表")
    @RequestMapping(value = "/super/apply/list", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqApplyPage",value = "查看申请列表",dataType = "ReqApplyPage")
    public RspPage<RspApplyList> applyList(@RequestBody ReqApplyPage reqApplyPage){

        return iCustomerService.applyList(reqApplyPage);

    }

    @ApiOperation(value = "申请列表-企业详情", notes = "申请列表-企业详情")
    @RequestMapping(value = "/super/apply/{id}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "查看详情参数",paramType = "path",required = true,dataType = "String")
    public RspApplyEnterprise detailById(@PathVariable String id){
        return iCustomerService.detailById(id);
    }

    @ApiOperation(value = "修改",notes = "修改")
    @RequestMapping(value = "/super/update",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqEnterpriseAllId",required = true,value = "修改",dataType = "ReqEnterpriseAllId")
    public void updateEnterseAllId(@RequestBody ReqEnterpriseAllId reqEnterpriseAllId) throws IOException {
        enterpriseService.updateEnterseAllId(reqEnterpriseAllId);
    }

    /**
     * 重置密码
     * @param
     */
    @ApiOperation(value = "重置密码",notes = "重置密码")
    @RequestMapping(value = "/common/account/password/reset",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdatePassword",required = true,value = "重置密码",dataType = "ReqUpdatePassword")
    public void updatePassword(@RequestBody ReqUpdatePassword reqUpdatePassword){
        adminService.updatePassword(reqUpdatePassword);
    }

    /**
     * 修改状态
     * @param
     */
    @ApiOperation(value = "修改状态（启用与禁用）",notes = "修改状态（启用与禁用）")
    @RequestMapping(value = "/common/account/status",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateEnterStatus",required = true,value = "修改状态",dataType = "ReqUpdateEnterStatus")
    public void updateStatus(@RequestBody ReqUpdateEnterStatus reqUpdateEnterStatus){
        LOG.info("修改状态（启用与禁用）");
        enterpriseService.updateStatus(reqUpdateEnterStatus);
    }

    @ApiOperation(value = "修改状态（是否部署）",notes = "修改状态（是否部署状态）")
    @RequestMapping(value = "/super/update_deploy",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateDeploy",required = true,value = "修改部署",dataType = "ReqUpdateDeploy")
    public void change(@RequestBody ReqUpdateDeploy reqUpdateDeploy){
        LOG.info("修改部署状态（是否部署）");
        enterpriseService.updateDeploy(reqUpdateDeploy);
    }
}
