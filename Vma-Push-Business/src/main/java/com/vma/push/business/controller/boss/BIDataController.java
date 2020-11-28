package com.vma.push.business.controller.boss;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.dto.req.ReqGetByRate;
import com.vma.push.business.dto.req.staff.*;
import com.vma.push.business.dto.req.userInfo.ReqLog;
import com.vma.push.business.dto.req.userInfo.ReqQueryUserInfo;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.RspUserByRate;
import com.vma.push.business.dto.req.ReqFunnel;
import com.vma.push.business.dto.rsp.actionLog.UserActionLog;
import com.vma.push.business.dto.rsp.boss.*;
import com.vma.push.business.dto.rsp.staff.RspAnalysis;
import com.vma.push.business.dto.rsp.staff.RspStaff;
import com.vma.push.business.dto.rsp.staff.RspStaffOrder;
import com.vma.push.business.dto.rsp.staff.RspStaffOrderPage;
import com.vma.push.business.dto.rsp.userInfo.DataItem;
import com.vma.push.business.dto.rsp.userInfo.RspAnalysisData;
import com.vma.push.business.dto.rsp.userInfo.RspUserInfoList;
import com.vma.push.business.service.IBossService;
import com.vma.push.business.service.IStaffInfoService;
import com.vma.push.business.service.IUserInfoService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenzui on 2018/5/15.
 */
@RestController
@RequestMapping("/v3.0")
@Api(value = "BOSS雷达", description = "BOSS雷达", tags = {"BIDataController"})
public class BIDataController extends ControllerExceptionHandler {

	private Logger LOG = Logger.getLogger(this.getClass());
	@Autowired
	private IBossService bossService;

	@Autowired
	private IUserInfoService userInfoService;

	@Autowired
	private IStaffInfoService staffInfoService;

	@ApiOperation(value = "查询总览数据", notes = "查询总览数据")
	@RequestMapping(value = "/boss/index/view", method = RequestMethod.GET)
	public List<RspViewData> viewdata(@ApiParam(value = "1汇总2昨日3近七天4近30天", name = "type") @RequestParam(value = "type") Integer type,
									  @ApiParam(value = "department_id", name = "department_id") @RequestParam(value = "department_id") String departmentId,
									  HttpServletRequest request) {
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		//String enterpriseId = "56c369ec-0c96-40a8-8d9e-a69727f19180";
		return bossService.viewdata(type, null, departmentId, enterpriseId);
	}

	@ApiOperation(value = "查询用户新增情况", notes = "查询用户新增情况")
	@RequestMapping(value = "/boss/index/user", method = RequestMethod.GET)
	public List<DataItem> countUserAdd(@ApiParam(value = "7,15,30", name = "day") @RequestParam(value = "day") Integer day,
									   @ApiParam(value = "department_id", name = "department_id") @RequestParam(value = "department_id") String departmentId,
									   HttpServletRequest request) {
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		//String enterpriseId = "56c369ec-0c96-40a8-8d9e-a69727f19180";
		Map param = new HashMap();
		param.put("day", day);
		param.put("departmentId", departmentId);
		param.put("enterpriseId", enterpriseId);
		return bossService.countUserAdd(param);
	}

	@ApiOperation(value = "查询用户跟进新增情况", notes = "查询用户跟进新增情况")
	@RequestMapping(value = "/boss/index/attach", method = RequestMethod.GET)
	public List<DataItem> countUserAttach(@ApiParam(value = "7,15,30", name = "day") @RequestParam(value = "day") Integer day,
										  @ApiParam(value = "department_id", name = "department_id") @RequestParam(value = "department_id") String departmentId,
										  HttpServletRequest request) {
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		Map param = new HashMap();
		param.put("day", day);
		param.put("departmentId", departmentId);
		param.put("enterpriseId", enterpriseId);
		return bossService.countUserAttach(param);
	}

	@ApiOperation(value = "新增订单数", notes = "新增订单数")
	@RequestMapping(value = "/boss/index/order", method = RequestMethod.GET)
	public List<DataItem> countOrder(@ApiParam(value = "7,15,30", name = "day") @RequestParam(value = "day") Integer day,
									 @ApiParam(value = "department_id", name = "department_id") @RequestParam(value = "department_id") String departmentId,
									 HttpServletRequest request) {
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		Map param = new HashMap();
		param.put("day", day);
		param.put("departmentId", departmentId);
		param.put("enterpriseId", enterpriseId);
		return bossService.countOrder(param);
	}

	@ApiOperation(value = "新增订单数", notes = "新增订单数")
	@RequestMapping(value = "/boss/index/im", method = RequestMethod.GET)
	public List<DataItem> countUserIm(@ApiParam(value = "7,15,30", name = "day") @RequestParam(value = "day") Integer day,
									  @ApiParam(value = "department_id", name = "department_id") @RequestParam(value = "department_id") String departmentId,
									  HttpServletRequest request) {
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		Map param = new HashMap();
		param.put("day", day);
		param.put("departmentId", departmentId);
		param.put("enterpriseId", enterpriseId);
		return bossService.countOrder(param);
	}

	@ApiOperation(value = "销售排行", notes = "销售排行")
	@RequestMapping(value = "/boss/staff/order", method = RequestMethod.GET)
	public List<RspStaff> staff(
			@ApiParam(value = "department_id", name = "department_id") @RequestParam(value = "department_id") String departmentId,
			HttpServletRequest request) {
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		List<RspStaff> staffs = staffInfoService.getStaffByDepart(enterpriseId, departmentId);
		Map param = new HashMap();
		param.put("departmentId", departmentId);
		param.put("enterpriseId", enterpriseId);
		for (RspStaff rspStaff : staffs) {
			param.put("staffId", rspStaff.getId());
			rspStaff.setUser_num(staffInfoService.countUser(param));
		}
		return staffs;
	}

	@ApiOperation(value = "销售排行->根据条件查询", notes = "销售排行")
	@RequestMapping(value = "/boss/staff/orderBy", method = RequestMethod.POST)
	public List<RspStaffOrder> staffOrderBy(HttpServletRequest request, @RequestBody ReqStaffOrder reqStaffOrder) {
		reqStaffOrder.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
		List<RspStaffOrder> staffs = staffInfoService.staffOrderBy(reqStaffOrder);
		return staffs;
	}

	@ApiOperation(value = "销售排行->根据条件查询XX人的客户信息", notes = "销售排行->根据条件查询XX人的客户信息")
	@RequestMapping(value = "/boss/staff/custom", method = RequestMethod.POST)
	public RspStaffOrderPage staffCustom(HttpServletRequest request, @RequestBody ReqCustomList req) {
		return staffInfoService.customOfStaffDetail(req);
	}

	@ApiOperation(value = "销售排行->根据session_id返回该销售人员的ID", notes = "销售排行->返回该销售人员的ID")
	@RequestMapping(value = "/boss/staff", method = RequestMethod.GET)
	public String staffDetail(HttpServletRequest request) {
		return UserDataUtil.getStaffId(request);
	}

	@ApiOperation(value = "能力分析", notes = "能力分析")
	@RequestMapping(value = "/boss/staff/analysis", method = RequestMethod.GET)
	public RspAnalysis analysis(@ApiParam(value = "department_id", name = "department_id") @RequestParam(value = "department_id") String departmentId,
								HttpServletRequest request) {
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		return bossService.analysis(enterpriseId, departmentId);
	}


	@ApiOperation(value = "漏斗详情", notes = "漏斗详情")
	@RequestMapping(value = "/user/rate", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqGetByRate", value = "漏斗详情", dataType = "ReqGetByRate")
	public RspPage<RspUserByRate> findUserByRate(@RequestBody ReqGetByRate reqGetByRate, HttpServletRequest request) {
		reqGetByRate.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
		return bossService.getUserByRate(reqGetByRate);
	}

	//成交率漏斗
	@ApiOperation(value = "成交率漏斗", notes = "成交率漏斗")
	@RequestMapping(value = "/boss/index/funnel", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqFunnel", value = "查询条件", required = true, dataType = "ReqFunnel")
	public List<RspFunnel> funnel(@RequestBody ReqFunnel reqFunnel, HttpServletRequest request) {
		String enterprise_id = UserDataUtil.getEnterpriseId(request);
		reqFunnel.setEnterprise_id(enterprise_id);
		return bossService.funnel(reqFunnel);
	}

	//订单量&销售金额
	@ApiOperation(value = "订单量&销售金额", notes = "订单量&销售金额")
	@RequestMapping(value = "/boss/index/order_money", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqFunnel", value = "查询条件", required = true, dataType = "ReqFunnel")
	public RspOrderAndMoney orderAndMoney(@RequestBody ReqFunnel reqFunnel, HttpServletRequest request) {
		String enterprise_id = UserDataUtil.getEnterpriseId(request);
		reqFunnel.setEnterprise_id(enterprise_id);
		return bossService.orderAndMoney(reqFunnel);
	}

	@ApiOperation(value = "能力排行-用户信息", notes = "能力排行-用户信息")
	@RequestMapping(value = "/boss/user_info/{id}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "id", value = "员工id", required = true, dataType = "String", paramType = "path")
	public RspUserInfo userInfo(@PathVariable("id") String id, HttpServletRequest request) {
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		return bossService.userInfo(id, enterpriseId);
	}

	@ApiOperation(value = "能力排行-用户客户情况", notes = "能力排行-用户客户情况")
	@RequestMapping(value = "/boss/user_cus_info/{id}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "id", value = "员工id", required = true, dataType = "String", paramType = "path")
	public RspUserCusInfo userCusInfo(@PathVariable("id") String id, HttpServletRequest request) {
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		return bossService.userCusInfo(id, enterpriseId);
	}

	@ApiOperation(value = "能力排行-总览数据", notes = "能力排行-总览数据")
	@RequestMapping(value = "/boss/view/{id}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "id", value = "员工id", required = true, dataType = "String", paramType = "path")
	public List<RspViewData> viewdata2(@PathVariable("id") String id,
									   @ApiParam(value = "1汇总2昨日3近七天4近30天", name = "type") @RequestParam(value = "type") Integer type,
									   HttpServletRequest request) {
		String departmentId = UserDataUtil.getDepartmentId(request);
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		return bossService.viewdata(type, id, departmentId, enterpriseId);
	}

	@ApiOperation(value = "能力排行-各种图", notes = "能力排行-各种图")
	@RequestMapping(value = "/boss/analysis/{id}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "id", value = "员工id", required = true, dataType = "String", paramType = "path")
	public RspAnalysisData analysisData(@PathVariable("id") String id,
										@ApiParam(value = "user_id") @RequestParam(value = "user_id", required = false) String userId,
										HttpServletRequest request) {
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		if (userId == null) {
			String departmentId = UserDataUtil.getDepartmentId(request);
			return staffInfoService.queryUserData(id, departmentId, enterpriseId);
		} else {
			return userInfoService.queryUserData(userId, id, enterpriseId);
		}
	}


	@ApiOperation(value = "能力排行-互动跟进", notes = "能力排行-互动跟进")
	@RequestMapping(value = "/boss/log/page", method = RequestMethod.POST)
	public RspPage<UserActionLog> queryUserActionLog(@RequestBody ReqLog reqLog, HttpServletRequest request) {
		LOG.info("查询用户动作");
		reqLog.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
		return userInfoService.queryUserActionLog(reqLog);
	}

	@ApiOperation(value = "能力排行-跟进", notes = "能力排行-跟进")
	@RequestMapping(value = "/boss/follow/page", method = RequestMethod.POST)
	public RspPage<UserActionLog> queryFollowLog(@RequestBody ReqLog reqLog, HttpServletRequest request) {
		LOG.info("查询用户动作");
		reqLog.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
		return userInfoService.queryFollowLog(reqLog);
	}

	@ApiOperation(value = "能力排行-跟进跟进/跟进", notes = "跟进跟进/跟进")
	@RequestMapping(value = "/boss/follow_and_log/page", method = RequestMethod.POST)
	public RspPage<UserActionLog> queryFollowLogAndAction(@RequestBody ReqLog reqLog, HttpServletRequest request) {
		LOG.info("能力排行-跟进跟进/跟进");
		reqLog.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
		if (reqLog.getType() == 1) {
			return userInfoService.queryUserActionLog(reqLog);
		} else {
			return userInfoService.queryFollowLog(reqLog);
		}
	}
}
