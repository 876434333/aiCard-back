package com.vma.push.business.controller.sys;

import com.vma.push.business.dto.req.ReqHandover;
import com.vma.push.business.dto.rsp.RspHandover;
import com.vma.push.business.dto.rsp.boss.RspViewData;
import com.vma.push.business.service.IStaffInfoService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/5/27.
 */
@RestController
@RequestMapping("/v1.0")
@Api(value = "工作交接", description = "管理后台--工作交接", tags = {"HandoverController"})
public class HandoverController {

    @Autowired
    private IStaffInfoService staffInfoService;

    @ApiOperation(value = "工作交接 需要session_id",notes ="工作交接" )
    @RequestMapping(value = "/handover",method = RequestMethod.POST)
    public void handover(@RequestBody ReqHandover reqHandover, HttpServletRequest request){
       String enterpriseId=UserDataUtil.getEnterpriseId(request);
        List<RspHandover> result=staffInfoService.handover(reqHandover.getFromid(),reqHandover.getToid(),enterpriseId);
        //return result;
    }
}
