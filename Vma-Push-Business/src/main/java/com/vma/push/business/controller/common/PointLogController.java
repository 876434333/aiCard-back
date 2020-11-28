package com.vma.push.business.controller.common;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.dto.req.superback.ReqAddPoint;
import com.vma.push.business.dto.req.superback.ReqPiontLogPage;
import com.vma.push.business.dto.rsp.superback.RspPage;
import com.vma.push.business.dto.rsp.superback.RspPointLog;
import com.vma.push.business.service.IKjPointLogService;
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
 * created by linzh on 2018/6/14
 */
@RestController(value = "CommonPointLogController")
@RequestMapping("/V1.0")
@Api(value = "迹点明细API", description = "迹点管理API", tags = {"PointLogController"})
public class PointLogController extends ControllerExceptionHandler {
    private Logger LOG = Logger.getLogger(this.getClass());
    @Autowired
    private IKjPointLogService kjPointLogService;


    @ApiOperation(value = "条件查询列表", notes = "条件查询列表")
    @RequestMapping(value = "/common/point/list", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqPiontLogPage",value = "迹点列表",required = true,dataType = "ReqPiontLogPage")
    public RspPage<RspPointLog> piontPage(@RequestBody ReqPiontLogPage reqPiontLogPage){
        LOG.info("条件查询迹点列表");
        return kjPointLogService.findPointLogPage(reqPiontLogPage);
    }

}
