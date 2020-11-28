package com.vma.push.business.controller.sale;

import com.vma.push.business.dto.req.*;
import com.vma.push.business.dto.rsp.ResPageAction;
import com.vma.push.business.dto.rsp.RspActionDetail;
import com.vma.push.business.dto.rsp.RspInteract;
import com.vma.push.business.dto.rsp.RspInteractDetail;
import com.vma.push.business.dto.rsp.boss.RspViewData;
import com.vma.push.business.dto.rsp.userInfo.RspActionData;
import com.vma.push.business.dto.rsp.userInfo.RspAnalysisData;
import com.vma.push.business.service.IBossService;
import com.vma.push.business.service.IStaffInfoService;
import com.vma.push.business.service.IUserInfoService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by chenzui on 2018/5/25.
 */

@RestController
@RequestMapping("/v3.0")
@Api(value = "BI数据", description = "销售端--销售人员数据统计", tags = {"BIStaffDataController"})
public class BIStaffDataController {

    @Autowired
    private IBossService bossService;

    @Autowired
    private IStaffInfoService staffInfoService;

    @ApiOperation(value = "查询总览数据",notes ="查询总览数据" )
    @RequestMapping(value = "/ai/index/view",method = RequestMethod.GET)
    public List<RspViewData> viewdata(@ApiParam(value = "1汇总2昨日3近七天4近30天",name = "type") @RequestParam(value = "type") Integer type,
                                      HttpServletRequest request){
        String staffId = UserDataUtil.getStaffId(request);
        String departmentId = UserDataUtil.getDepartmentId(request);
        String enterpriseId = UserDataUtil.getEnterpriseId(request);
        return bossService.viewdata(type,staffId,departmentId,enterpriseId);
    }

    @ApiOperation(value = "查询用户AI分析数据",notes ="查询用户AI分析数据" )
    @RequestMapping(value = "/staff/ai/analysis",method = RequestMethod.GET)
    public RspAnalysisData analysisData(HttpServletRequest request){
//        String staffId = UserDataUtil.getStaffId(request);
//        String departmentId=UserDataUtil.getDepartmentId(request);
//        String enterpriseId =  UserDataUtil.getEnterpriseId(request);
        String staffId = UserDataUtil.getMyStaffId(request);
        String departmentId=UserDataUtil.getMyDepartmentId(request);
        String enterpriseId =  UserDataUtil.getMyEnterpriseId(request);
        return staffInfoService.queryUserData(staffId,departmentId,enterpriseId);
    }

    @ApiOperation(value = "行为汇总",notes ="行为汇总" )
    @RequestMapping(value = "/action",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqActionData",value = "时间不传默认七天内",required = true,dataType = "ReqActionData")
    public List<RspActionData> actionData(@RequestBody ReqActionData reqActionData, HttpServletRequest request){
//        String staffId = UserDataUtil.getStaffId(request);
        String staffId = UserDataUtil.getMyStaffId(request);
        reqActionData.setStaff_id(staffId);
        return staffInfoService.actionData(reqActionData);
    }

    @ApiOperation(value = "行为明细",notes ="行为明细" )
    @RequestMapping(value = "/action/action_by_code",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqActionCode",value = "时间不传默认七天内",required = true,dataType = "ReqActionCode")
    public ResPageAction<RspActionDetail> actionDetail(@RequestBody ReqActionCode reqActionCode, HttpServletRequest request){
//        String staffId = UserDataUtil.getStaffId(request);
        String staffId = UserDataUtil.getMyStaffId(request);
        reqActionCode.setStaff_id(staffId);
        return staffInfoService.actionDetail(reqActionCode);
    }

    @ApiOperation(value = "互动汇总",notes ="互动汇总" )
    @RequestMapping(value = "/interact",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqInteract",value = "时间不传默认七天内",required = true,dataType = "ReqInteract")
    public ResPageAction<RspInteract> interact(@RequestBody ReqInteract reqInteract, HttpServletRequest request){
//        String staffId = UserDataUtil.getStaffId(request);
        String staffId = UserDataUtil.getMyStaffId(request);
        reqInteract.setStaff_id(staffId);
        return staffInfoService.interactData(reqInteract);
    }

    @ApiOperation(value = "互动明细",notes ="互动明细" )
    @RequestMapping(value = "/interact/detail",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqInteractDetail",value = "时间不传默认七天内",required = true,dataType = "ReqInteractDetail")
    public List<RspInteractDetail> interactDetail(@RequestBody ReqInteractDetail reqInteractDetail, HttpServletRequest request){
//        String staffId = UserDataUtil.getStaffId(request);
        String staffId = UserDataUtil.getMyStaffId(request);
        reqInteractDetail.setStaff_id(staffId);
        return staffInfoService.interactDetail(reqInteractDetail);
    }
}
