package com.vma.push.business.controller.superback;

import com.vma.push.business.dto.req.superback.ReqAddRcdCard;
import com.vma.push.business.dto.req.superback.ReqGetRecommendCard;
import com.vma.push.business.dto.req.system.ReqAddPoinRate;
import com.vma.push.business.dto.req.system.ReqUpdatePoint;
import com.vma.push.business.dto.rsp.superback.RspGetRecommendCardList;
import com.vma.push.business.dto.rsp.system.RspPointRate;
import com.vma.push.business.service.ISystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Create By ChenXiAoBin
 * on 2018/6/14
 */
@RestController
@RequestMapping("/V1.0")
@Api(value = "系统管理API", description = "系统管理后台--系统管理", tags = {"SystemController"})
public class SystemController {
	@Autowired
	private ISystemService systemService;
//
//    @ApiOperation(value = "迹点价格设置", notes = "迹点价格设置")
//    @RequestMapping(value = "/super/addPointRate", method = RequestMethod.POST)
//    @ApiImplicitParam(name = "reqAddPoinRate",value = "迹点价格设置",required = true,dataType = "ReqAddPoinRate")
//    public void addPointRate(@RequestBody ReqAddPoinRate reqAddPoinRate){
//          iSystemService.addPointRate(reqAddPoinRate);
//    }


	@ApiOperation(value = "查看迹点价格", notes = "查看迹点价格")
	@RequestMapping(value = "/super/point/config", method = RequestMethod.GET)
	public RspPointRate selectPoint() {
		RspPointRate rspPointRate = systemService.selectPoint();
		return rspPointRate;
	}

	@ApiOperation(value = "修改迹点配置", notes = "修改迹点配置")
	@RequestMapping(value = "/super/point/config", method = RequestMethod.PUT)
	@ApiImplicitParam(name = "reqUpdatePoint", value = "修改迹点配置", dataType = "ReqUpdatePoint")
	public void updatePointRate(@RequestBody ReqUpdatePoint reqUpdatePoint) {
		systemService.updatePoint(reqUpdatePoint);
	}

	@ApiOperation(value = "获取需要推荐的名片", notes = "获取需要推荐的名片")
	@RequestMapping(value = "/super/recommend/getStaff", method = RequestMethod.POST)
	public List<RspGetRecommendCardList> getStaff(@RequestBody ReqGetRecommendCard reqGetRecommendCard) {
		return systemService.getRecommendStaffList(reqGetRecommendCard);
	}

	@ApiOperation(value = "获取推荐的名片", notes = "获取推荐的名片")
	@RequestMapping(value = "/super/recommend/getRecommendCardList", method = RequestMethod.GET)
	public List<RspGetRecommendCardList> getRecommendCardList() {
		return systemService.getRecommendCardList();
	}

	@ApiOperation(value = "新增推荐的名片", notes = "新增推荐的名片")
	@RequestMapping(value = "/super/recommend/addRecommendCard", method = RequestMethod.POST)
	public void addRecommendCard(@RequestBody ReqAddRcdCard reqAddRcdCard) {
		systemService.addRcdCard(reqAddRcdCard);
	}

	@ApiOperation(value = "删除推荐的名片", notes = "删除推荐的名片")
	@RequestMapping(value = "/super/recommend/deleteRecommendCard/{recommendId}", method = RequestMethod.GET)
	public void addRecommendCard(@PathVariable("recommendId")String recommendId) {
		systemService.deleteRecommendCard(recommendId);
	}
}
