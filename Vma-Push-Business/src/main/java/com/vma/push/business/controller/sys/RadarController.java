package com.vma.push.business.controller.sys;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dto.req.ReqStaffSelect;
import com.vma.push.business.dto.req.ReqUpdateRadar;
import com.vma.push.business.dto.rsp.RspRadarPage;
import com.vma.push.business.dto.rsp.radar.EnterpriseRadarInfo;
import com.vma.push.business.dto.rsp.staff.RspStaff;
import com.vma.push.business.entity.Enterprise;
import com.vma.push.business.entity.Staff;
import com.vma.push.business.service.IBasicService;
import com.vma.push.business.service.IStaffInfoService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ChenXiaoBin
 * 2018/5/4 11:31
 */
@RestController
@RequestMapping("/v1.0")
@Api(value = "雷达权限", description = "管理后台--雷达权限管理", tags = {"RadarController"})
public class RadarController  extends ControllerExceptionHandler {
    private Logger LOG = Logger.getLogger(this.getClass());
    @Autowired
    private IStaffInfoService staffInfoService;

    @Autowired
    private IBasicService basicService;



    /**
     * 权限列表条件查询
     *
     * @return
     */
    @ApiOperation(value = "条件查询雷达列表", notes = "条件查询雷达列表")
    @RequestMapping(value = "/radar/select", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqStaffSelect", value = "查看产品列表", dataType = "ReqStaffSelect")
    public RspRadarPage<RspStaff> findStaffSelect(@RequestBody ReqStaffSelect reqStaffSelect, HttpServletRequest request){
        String enterpriseId = UserDataUtil.getEnterpriseId(request);
        reqStaffSelect.setEnterprise_id(enterpriseId);
        return staffInfoService.findStaffSelect(reqStaffSelect);

     }


    @ApiOperation(value = "修改雷达状态",notes = "修改雷达状态")
    @RequestMapping(value = "/radar/update",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateRadar",required = true,value = "修改雷达状态",dataType = "ReqUpdateRadar")
    public void updateStatus(@RequestBody ReqUpdateRadar reqUpdateRadar,HttpServletRequest request){
        LOG.info("修改雷达状态");
        String staffId = reqUpdateRadar.getId();
        String enterpriseId = UserDataUtil.getEnterpriseId(request);
        Staff staff = staffInfoService.selectByPrimaryKey(staffId);
        if(!enterpriseId.equals(staff.getEnterpriseId())){
            throw new BusinessException(ErrCode.ILLEGAL_REQUEST,"非法请求");
        }
        staff.setOpenAi(reqUpdateRadar.getOpen_ai());
        staff.setOpenBoss(reqUpdateRadar.getOpen_boss());
        staffInfoService.updateByPrimaryKeySelective(staff);
        basicService.updateCardUsedInfo(enterpriseId,staff);


    }


    @ApiModelProperty(value = "获取企业雷达使用情况")
    @RequestMapping(value = "radar/info",method = RequestMethod.GET)
    public EnterpriseRadarInfo getRadarInfo(HttpServletRequest request){
        String enterpriseId = UserDataUtil.getEnterpriseId(request);
        Enterprise enterprise =  basicService.selectByPrimaryKey(enterpriseId);
        EnterpriseRadarInfo info =  new EnterpriseRadarInfo();
        info.setTotal(enterprise.getMoneySum());
        info.setUsed(enterprise.getMoneySum()-enterprise.getMoneyLeave());//总绩点数-剩余绩点数==已用绩点数
        return info;
    }
}
