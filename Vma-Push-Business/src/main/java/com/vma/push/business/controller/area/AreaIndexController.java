package com.vma.push.business.controller.area;

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
 * Created by JINzm on 2018/6/15.
 */
@RestController
@RequestMapping("/V1.0/area")
@Api(value = "首页", description = "地区总代管理后台--首页", tags = {"AreaIndexController"})
public class AreaIndexController {
    @Autowired
    private IEnterpriseService enterpriseService;
    @Autowired
    private IStaffInfoService staffInfoService;
    @Autowired
    private IKjPointLogService kjPointLogService;

    @ApiOperation(value = "下属企业数量",notes ="下属企业数量" )
    @RequestMapping(value = "/index/enterprise_number",method = RequestMethod.GET)
    public RspEnterpriseHistoryNumber getEnterpriseNumber(HttpServletRequest request){
        String enterpriseId= UserDataUtil.getCustomId(request);
//        String enterpriseId= "62f7f13a-525a-4f8e-8fa6-bc36fdcbd719";
        return enterpriseService.getEnterpriseHistoryNumber(enterpriseService.selectByPrimaryKey(enterpriseId).getRole()-1,enterpriseId);
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
