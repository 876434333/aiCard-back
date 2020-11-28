package com.vma.push.business.controller.common;

import com.vma.push.business.dto.req.ReqAddWebApplyLog;
import com.vma.push.business.entity.WebApplyLog;
import com.vma.push.business.service.IWebAppLogService;
import com.vma.push.business.util.UuidUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by chenzui on 2018/6/2.
 */
@RestController
@RequestMapping("/v4.0")
@Api(value = "WebApplyController",description = "运营官网相关",tags = {"WebApplyController"})
public class WebApplyController {


    @Autowired
    private IWebAppLogService webAppLogService;

    @ApiOperation(value = "",notes = "")
    @ApiImplicitParam(name = "reqAddWebApplyLog",value = "reqAddWebApplyLog",dataType = "ReqAddWebApplyLog")
    @RequestMapping(value = "/webApp",method = RequestMethod.POST)
    public void add(@RequestBody ReqAddWebApplyLog reqAddWebApplyLog){

        WebApplyLog webApplyLog = new WebApplyLog();
        webApplyLog.setApplyType(reqAddWebApplyLog.getApply_type());
        webApplyLog.setCompanyName(reqAddWebApplyLog.getCompany_name());
        webApplyLog.setCreateTime(new Date());
        webApplyLog.setMobile(reqAddWebApplyLog.getMobile());
        webApplyLog.setPlatformId(reqAddWebApplyLog.getPlatform_id());
        webApplyLog.setRemarks(reqAddWebApplyLog.getRemarks());
        webApplyLog.setName(reqAddWebApplyLog.getName());

        webAppLogService.insertSelective(webApplyLog);
    }
}
