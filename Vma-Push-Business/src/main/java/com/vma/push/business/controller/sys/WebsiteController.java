package com.vma.push.business.controller.sys;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.dto.req.website.ReqWebSite;
import com.vma.push.business.dto.rsp.RspWebsite;
import com.vma.push.business.dto.rsp.boss.RspViewData;
import com.vma.push.business.dto.rsp.userInfo.DataItem;
import com.vma.push.business.entity.WebsiteTemplate;
import com.vma.push.business.service.IBossService;
import com.vma.push.business.service.IWebsiteService;
import com.vma.push.business.util.UserDataUtil;
import com.vma.push.business.util.UuidUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenzui on 2018/5/15.
 */
@RestController
@RequestMapping("/v1.0")
@Api(value = "官网管理", description = "管理后台--官网管理", tags = {"WebsiteController"})
public class WebsiteController extends ControllerExceptionHandler {

    private Logger LOG = Logger.getLogger(this.getClass());

    @Autowired
    private IWebsiteService websiteService;
    @Autowired
    private IBossService bossService;

    @ApiOperation(value = "官网管理",notes = "官网管理")
    @RequestMapping(value = "/website",method = RequestMethod.POST)
    @ApiImplicitParam(value = "public class ReqWebSite {\n" +
            "    String type;\n" +
            "    String text_content;\n" +
            "}",name = "reqWebSites",dataType ="List<ReqWebSite>",required = true)
    public void website(@RequestBody List<ReqWebSite> reqWebSites, HttpServletRequest request){
        LOG.info("官网模版参数");
        String enterpriseId = UserDataUtil.getEnterpriseId(request);
        websiteService.add(reqWebSites,enterpriseId);

    }


    @ApiOperation(value = "查询总览数据",notes ="查询总览数据" )
    @RequestMapping(value = "/boss/index/view",method = RequestMethod.GET)
    public List<RspViewData> viewdata(@ApiParam(value = "1汇总2昨日3近七天4近30天",name = "type") @RequestParam(value = "type") Integer type ,HttpServletRequest request){
       String enterpriseId = UserDataUtil.getEnterpriseId(request);
//       String departmentId = UserDataUtil.getDepartmentId(request);
        return bossService.viewdata(type,null,"1",enterpriseId);
    }


    @ApiOperation(value = "查询用户新增情况",notes = "查询用户新增情况")
    @RequestMapping(value = "/boss/index/user",method = RequestMethod.GET)
    public List<DataItem> countUserAdd(@ApiParam(value = "7,15,30",name = "day") @RequestParam(value = "day") Integer day,HttpServletRequest request){
        String enterpriseId = UserDataUtil.getEnterpriseId(request);
//        String departmentId = UserDataUtil.getDepartmentId(request);
        Map param = new HashMap();
        param.put("day",day);
        param.put("departmentId","1");
        param.put("enterpriseId",enterpriseId);
        return bossService.countUserAdd(param);
    }



    @ApiOperation(value = "根据enterprise_id查询",notes = "查询")
    @RequestMapping(value = "/findWebsite",method = RequestMethod.GET)
    public List<RspWebsite>  findWebsiteByEnterprise(HttpServletRequest request){
        String id = UserDataUtil.getEnterpriseId(request);
       return websiteService.findWebsiteByEnterprise(id);
    }

}
