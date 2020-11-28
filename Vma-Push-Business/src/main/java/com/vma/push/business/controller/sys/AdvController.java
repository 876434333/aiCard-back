package com.vma.push.business.controller.sys;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.dto.req.*;
import com.vma.push.business.dto.rsp.RspAdv;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.entity.Adv;
import com.vma.push.business.service.IAdvService;
import com.vma.push.business.util.SmallSoftApi;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by ChenXiaoBin
 * 2018/5/3 11:14
 */
@RestController
@RequestMapping("/v1.0")
@Api(value = "广告管理API", description = "管理后台--广告管理", tags = {"AdvController"})
public class AdvController extends ControllerExceptionHandler{
    private Logger LOG = Logger.getLogger(this.getClass());
    @Autowired
    private IAdvService advService;

    @Autowired
    private SmallSoftApi api;

    /**
     * 模糊条件查询广告
     * @param
     * @return
     */
    @ApiOperation(value = "条件查询列表", notes = "条件查询列表")
    @RequestMapping(value = "/adv/pageSelect", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqAdvSelect",value = "查看广告列表",dataType = "ReqAdvSelect")
    public RspPage<RspAdv> findProductBySelect(@RequestBody ReqAdvSelect reqAdvSelect ,HttpServletRequest request){
        LOG.info("条件查询广告列表");
        reqAdvSelect.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
        return advService.findAdvBySelect(reqAdvSelect,request);

    }

    /**
     * 添加广告
     * @param
     */
    @ApiOperation(value = "添加广告", notes = "添加广告")
    @RequestMapping(value = "/adv", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqAddAdv",value = "添加产品",required = true,dataType = "ReqAddAdv")
    public void addProduct(@RequestBody ReqAddAdv reqAddAdv ,HttpServletRequest request){
        LOG.info("添加广告到数据库");
       advService.addAdv(reqAddAdv, request);
    }

    /**
     * 修改广告
     * @param
     */
    @ApiOperation(value = "修改广告",notes = "修改广告")
    @RequestMapping(value = "/adv",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateAdv",required = true,value = "修改广告",dataType = "ReqUpdateAdv")
    public void updateProduct(@RequestBody ReqUpdateAdv reqUpdateAdv,HttpServletRequest request){
        LOG.info("修改指定的广告");
        advService.updateAdv(reqUpdateAdv,request);

        advService.orderAdv(request);
    }

    @ApiOperation(value = "广告明细",notes = "查询广告明细")
    @RequestMapping(value = "/adv/{id}",method = RequestMethod.GET)
    public RspAdv getById(
            @ApiParam(required=true, name="id", value="广告ID") @PathVariable("id") String id){
        LOG.info("广告明细");
        Adv adv = advService.selectByPrimaryKey(id);
        RspAdv rspAdv = new RspAdv();
        rspAdv.setA_order(adv.getaOrder());
        rspAdv.setHref(adv.getHref());
        rspAdv.setImg_url(adv.getImgUrl());
        rspAdv.setId(adv.getId());
        rspAdv.setTitle(adv.getTitle());
        rspAdv.setLocation(adv.getLocation());
        return rspAdv;
    }

}
