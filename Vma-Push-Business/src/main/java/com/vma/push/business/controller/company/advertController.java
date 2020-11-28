package com.vma.push.business.controller.company;

import com.vma.push.business.dto.req.ReqAddAdvNew;
import com.vma.push.business.dto.req.ReqAdvSelectNew;
import com.vma.push.business.dto.req.ReqUpdateAdvNew;
import com.vma.push.business.dto.rsp.RspAdvNew;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.entity.Adv;
import com.vma.push.business.service.IAdvService;
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
 * Created by huxiangqiang on 2018/7/13.
 */
@RestController(value = "AdvertController")
@RequestMapping("/V1.0")
@Api(value = "企业后台", description = "广告管理", tags = {"advertController"})
public class advertController {

    private Logger LOG = Logger.getLogger(this.getClass());
    @Autowired
    private IAdvService advService;
    /**
     * 添加广告
     * @param
     */
    @ApiOperation(value = "添加广告", notes = "添加广告")
    @RequestMapping(value = "/company/adv", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqAddAdv",value = "添加产品",required = true,dataType = "ReqAddAdv")
    public void addProduct(@RequestBody ReqAddAdvNew req , HttpServletRequest request){
        LOG.info("添加广告到数据库");
        advService.newAddAdv(req, request);

        advService.orderAdv(request);
    }

    /**
     * 修改广告
     * @param
     */
    @ApiOperation(value = "修改广告",notes = "修改广告")
    @RequestMapping(value = "/company/adv",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "req",required = true,value = "修改广告",dataType = "ReqUpdateAdvNew")
    public void updateProduct(@RequestBody ReqUpdateAdvNew req ,HttpServletRequest request){
        LOG.info("修改指定的广告");
        advService.updateAdvNew(req,request);

        advService.orderAdv(request);
    }
    /**
     * 模糊条件查询广告
     * @param
     * @return
     */
    @ApiOperation(value = "条件查询列表", notes = "条件查询列表")
    @RequestMapping(value = "/company/adv/list", method = RequestMethod.POST)
    @ApiImplicitParam(name = "req",value = "查看广告列表",dataType = "ReqAdvSelectNew")
    public RspPage<RspAdvNew> findProductBySelect(@RequestBody ReqAdvSelectNew req , HttpServletRequest request){
        LOG.info("条件查询广告列表");
        req.setEnterprise_id(UserDataUtil.getCustomId(request));
        return advService.findAdvBySelectNew(req,request);

    }
    @ApiOperation(value = "广告明细",notes = "查询广告明细")
    @RequestMapping(value = "/company/adv/{id}",method = RequestMethod.GET)
    public RspAdvNew getById(@ApiParam(required=true, name="id", value="广告ID") @PathVariable("id") String id){
        LOG.info("广告明细");
        Adv adv = advService.selectByPrimaryKey(id);
        RspAdvNew rsp = new RspAdvNew();
        rsp.setA_order(adv.getaOrder());
        rsp.setHref(adv.getHref());
        rsp.setImg_url(adv.getImgUrl());
        rsp.setId(adv.getId());
        rsp.setTitle(adv.getTitle());
        rsp.setLocation(adv.getLocation());
        rsp.setBegin_time(adv.getBeginTime());
        rsp.setFinish_time(adv.getFinishTime());
        rsp.setStatus(adv.getStatus());
        return rsp;
    }

    @ApiOperation(value = "删除广告",notes = "删除广告")
    @RequestMapping(value = "/company/adv/{id}",method = RequestMethod.DELETE)
    public void deleteById(@ApiParam(required=true, name="id", value="广告ID") @PathVariable("id") String id , HttpServletRequest request){
        LOG.info("广告明细");
        advService.deleteByPrimaryKey(id);

        advService.orderAdv(request);
    }

    @ApiOperation(value = "广告置顶",notes = "广告置顶")
    @RequestMapping(value = "/company/adv/{id}",method = RequestMethod.POST)
    public void toBeNo1(@ApiParam(required=true, name="id", value="广告ID") @PathVariable("id") String id, HttpServletRequest request){
        LOG.info("广告置顶");
        advService.toBeNo1(id,UserDataUtil.getCustomId(request));

        advService.orderAdv(request);
    }
}
