package com.vma.push.business.controller.sys;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.dto.req.ReqSuperLogin;
import com.vma.push.business.service.ISuperLoginService;
import com.vma.push.business.util.MD5Helper;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ChenXiaoBin
 * 2018/5/13 15:47
 */
@RestController
@RequestMapping("/v3.0")
@Api(value = "超级后台登录API", description = "超级后台--超级后台登陆", tags = {"SuperLoginController"})
public class SuperLoginController  extends ControllerExceptionHandler {
    @Autowired
    private ISuperLoginService iSuperLoginService;

    @ApiOperation(value = "超级后台登陆", notes = "超级后台登陆")
    @RequestMapping(value = "super/login", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqSuperLogin",value = "超级后台系统登陆",dataType = "ReqSuperLogin")
    public String superLogin(@RequestBody ReqSuperLogin reqSuperLogin){
        String token = "";
        String  Id = iSuperLoginService.login(reqSuperLogin);//获取ID
        token =  MD5Helper.getMD5Str(Id);
        UserDataUtil.setStaffId(token,Id);
        return token;
    }

}
