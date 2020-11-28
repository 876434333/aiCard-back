package com.vma.push.business.controller.oem;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.dto.req.superback.ReqAddPoint;
import com.vma.push.business.dto.req.superback.ReqPiontLogPage;
import com.vma.push.business.dto.rsp.RspPointDetail;
import com.vma.push.business.dto.rsp.superback.RspCustomDetail;
import com.vma.push.business.dto.rsp.superback.RspPage;
import com.vma.push.business.dto.rsp.superback.RspPointLog;
import com.vma.push.business.service.IEnterpriseService;
import com.vma.push.business.service.IKjPointLogService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * created by linzh on 2018/6/14
 */
@RestController(value = "OemPointLogController")
@RequestMapping("/V1.0")
@Api(value = "迹点管理API", description = "管理后台--迹点管理", tags = {"KjPointLogController"})
public class KjPointLogController extends ControllerExceptionHandler {
    private Logger LOG = Logger.getLogger(this.getClass());
    @Autowired
    private IKjPointLogService kjPointLogService;
    @Autowired
    private IEnterpriseService enterpriseService;
    /**
     * 增加迹点
     */
    @ApiOperation(value = "操作迹点（添加/扣减）", notes = "添加/扣减迹点")
    @RequestMapping(value = "/oem/point/operate", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqAddPoint",value = "添加/扣减迹点",required = true,dataType = "ReqAddPoint")
    public void add(@RequestBody ReqAddPoint reqAddPoint, HttpServletRequest request){
        String userId = UserDataUtil.getAdminId(request);
        reqAddPoint.setCreate_by(userId);
        reqAddPoint.setEnterprise_id(UserDataUtil.getCustomId(request));
        LOG.info("添加/扣减迹点");
        if(reqAddPoint.getContent()!=null) {
            reqAddPoint.setContent("("+reqAddPoint.getContent()+")");//事项
        }else{
            reqAddPoint.setContent("");//事项
        }
        kjPointLogService.addPoint(reqAddPoint);
    }

    @ApiOperation(value = "上级对我迹点操作记录", notes = "上级对我迹点操作记录")
    @RequestMapping(value = "/oem/point/list", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqPiontLogPage",value = "迹点列表",required = true,dataType = "ReqPiontLogPage")
    public RspPage<RspPointLog> piontPage(@RequestBody ReqPiontLogPage reqPiontLogPage, HttpServletRequest request){
        LOG.info("上级对我迹点操作记录");
        reqPiontLogPage.setIs_me(1);
        return kjPointLogService.findPointLogPage(reqPiontLogPage,request);

    }

    @ApiOperation(value = "我对下级迹点操作记录", notes = "我对下级迹点操作记录")
    @RequestMapping(value = "/oem/point/sub/list", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqPiontLogPage",value = "迹点列表",required = true,dataType = "ReqPiontLogPage")
    public RspPage<RspPointLog> pointPage(@RequestBody ReqPiontLogPage reqPiontLogPage, HttpServletRequest request){
        LOG.info("我对下级迹点操作记录");
        reqPiontLogPage.setIs_me(0);
        return kjPointLogService.findPointLogPage(reqPiontLogPage,request);

    }

    @ApiOperation(value = "查询企业迹点详情", notes = "查询企业迹点详情")
    @RequestMapping(value = "/oem/point/detail", method = RequestMethod.GET)
    public RspPointDetail pointDetail(HttpServletRequest request){
        LOG.info("条件查询迹点列表");
        return enterpriseService.pointDetail(UserDataUtil.getCustomId(request));

    }

/*    @ApiOperation(value = "贴牌商详细信息",notes = "贴牌商详细信息")
    @RequestMapping(value = "/oem/point/get/{custom_id}",method = RequestMethod.GET)
    public RspCustomDetail getById(
            @ApiParam(required=true, name="custom_id", value="贴牌商ID") @PathVariable("custom_id") String custom_id){
        LOG.info("贴牌商明细");
        return kjPointLogService.getCustomDetail(custom_id);
    }*/
}
