package com.vma.push.business.controller.mini;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.dto.req.ReqClickInfo;
import com.vma.push.business.entity.ClickActionLog;
import com.vma.push.business.service.IClickActionLogService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by huxiangqiang on 2018/4/27.
 */
@RestController
@RequestMapping("/v2.0")
@Api(value = "销售人员名片", description = "小程序--销售人员名片", tags = {"BusinessCardController"})
public class BusinessCardController  extends ControllerExceptionHandler {
    private Logger LOG = Logger.getLogger(this.getClass());
    @Autowired
    private IClickActionLogService clickActionLogService;

    @RequestMapping(value = "/ClickAction",method = RequestMethod.POST)
    @ApiOperation(value = "点击行为记录",notes = "点击行为记录")
    @ApiImplicitParam(name = "reqClickInfo",value = "点击行为记录",required = true,dataType = "ReqClickInfo")
    public void ClickAction(@RequestBody  ReqClickInfo reqClickInfo,HttpServletRequest request){
        clickActionLogService.click(reqClickInfo,request);
    }
}
