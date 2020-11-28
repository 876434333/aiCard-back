package com.vma.push.business.controller.sys;

import com.vma.push.business.dto.req.Page;
import com.vma.push.business.dto.req.ReqAddWebApplyLog;
import com.vma.push.business.dto.req.ReqPageApply;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.entity.WebApplyLog;
import com.vma.push.business.service.IWebAppLogService;
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
@RequestMapping("/v3.0")
@Api(value = "WebApplyLogController",description = "运营官网相关",tags = {"WebApplyLogController"})
public class WebApplyLogController {


    @Autowired
    private IWebAppLogService webAppLogService;

    @ApiOperation(value = "",notes = "")
    @ApiImplicitParam(value = "page",name = "page",dataType = "Page")
    @RequestMapping(value = "/web_apply/page",method = RequestMethod.POST)
    public RspPage<ReqAddWebApplyLog> findPage(@RequestBody ReqPageApply page){
        return webAppLogService.findPage(page);
    }
}
