package com.vma.push.business.controller.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vma.push.business.common.Constants;
import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dto.req.Page;
import com.vma.push.business.dto.req.ReqSystemLogin;
import com.vma.push.business.dto.rsp.RepAllProduct;
import com.vma.push.business.dto.rsp.RspAdminLogin;
import com.vma.push.business.dto.rsp.RspOrder;
import com.vma.push.business.entity.Order;
import com.vma.push.business.entity.Staff;
import com.vma.push.business.service.IBasicService;
import com.vma.push.business.service.IStaffInfoService;
import com.vma.push.business.util.MD5Helper;
import com.vma.push.business.util.RedisUtil;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/5/2 16:40
 */
@RestController
@RequestMapping("/v1.0")
@Api(value = "企业后台管理登录API", description = "企业管理后台--登录", tags = {"StaffLoginController"})
public class StaffLoginController  extends ControllerExceptionHandler {
    @Autowired
    private IBasicService basicService;



    @ApiOperation(value = "企业后台登陆", notes = "企业后台登陆")
    @RequestMapping(value = "system/login", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqSystemLogin",value = "企业后台系统登陆",dataType = "ReqSystemLogin")
    public String login( @RequestBody ReqSystemLogin reqSystemLogin){
        String token = "";
        RspAdminLogin rspAdminLogin = basicService.login(reqSystemLogin);
        token =  MD5Helper.getMD5Str(rspAdminLogin.getCustomId());
        UserDataUtil.setAdminId(token,rspAdminLogin.getId());
        UserDataUtil.setStaffId(token,rspAdminLogin.getId());
        UserDataUtil.setEnterpriseId(token,rspAdminLogin.getCustomId());
        return token;

    }

}
