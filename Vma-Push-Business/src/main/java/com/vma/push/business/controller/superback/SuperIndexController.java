package com.vma.push.business.controller.superback;

import com.vma.push.business.dto.req.ReqPointNumber;
import com.vma.push.business.dto.req.staff.ReqCardNumber;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.service.IEnterpriseService;
import com.vma.push.business.service.IKjPointLogService;
import com.vma.push.business.service.IStaffInfoService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by JINzm on 2018/6/14.
 */
@RestController
@RequestMapping("/V1.0/super")
@Api(value = "首页", description = "超级管理后台--首页", tags = {"SuperIndexController"})
public class SuperIndexController {
    @Autowired
    private IEnterpriseService enterpriseService;
    @Autowired
    private IStaffInfoService staffInfoService;
    @Autowired
    private IKjPointLogService kjPointLogService;

    @ApiOperation(value = "当前企业下的代理商与企业数量",notes ="当前企业下的代理商与企业数量" )
    @RequestMapping(value = "/index/enterprise_number",method = RequestMethod.GET)
    public RspEnterpriseNumber getEnterpriseNumber(HttpServletRequest request){
//        String enterpriseId= UserDataUtil.getEnterpriseId(request);
        Integer roleId = 4;
        return enterpriseService.getEnterpriseNumber(roleId,"0");
    }

    @ApiOperation(value = "名片数量",notes ="名片数量" )
    @RequestMapping(value = "/index/card_number",method = RequestMethod.GET)
    public RepCardNumber getCard_number(HttpServletRequest request){
        ReqCardNumber reqCardNumber = new ReqCardNumber();
        reqCardNumber.setEnterprise_id(UserDataUtil.getCustomId(request));
        return staffInfoService.getCallingCardNumber(reqCardNumber);
    }

    @ApiOperation(value = "前N天每天名片数量",notes ="前N天每天名片数量" )
    @RequestMapping(value = "/index/days_card_number/{day_number}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "day_number",value = "天数",required = true,dataType = "Integer",paramType = "path")
    public List<RspDaysCardNumber> getDays_Card_number(HttpServletRequest request, @PathVariable("day_number")  Integer day_number){
        ReqCardNumber reqCardNumber = new ReqCardNumber();
        reqCardNumber.setEnterprise_id(UserDataUtil.getCustomId(request));
        reqCardNumber.setDayNumber(day_number-1);
        return staffInfoService.getCallingCardNumberByDayNumber(reqCardNumber);
    }

    @ApiOperation(value = "迹点数据",notes ="迹点数据" )
    @RequestMapping(value = "/index/point_number",method = RequestMethod.GET)
    public RspPointNumber getPoint_Number(HttpServletRequest request){
        ReqPointNumber req = new ReqPointNumber();
        req.setEnterprise_id(UserDataUtil.getCustomId(request));
        return kjPointLogService.getPointNumber(req);
    }

    @ApiOperation(value = "前N天每天迹点数据",notes ="前N天每天迹点数据" )
    @RequestMapping(value = "/index/days_point_number/{day_number}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "day_number",value = "天数",required = true,dataType = "Integer",paramType = "path")
    public List<RspDaysPointNumber> getPoint_NumberByDayNumber(HttpServletRequest request, @PathVariable("day_number")  Integer day_number){
        ReqPointNumber req = new ReqPointNumber();
        req.setEnterprise_id(UserDataUtil.getCustomId(request));
        req.setDayNumber(day_number-1);
        return kjPointLogService.getPointNumberByDayNumber(req);
    }
}
