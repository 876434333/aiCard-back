package com.vma.push.business.controller.sale;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.dao.ClickActionLogMapper;
import com.vma.push.business.dto.req.Page;
import com.vma.push.business.dto.req.ReqUpdateRate;
import com.vma.push.business.dto.req.ReqUpdateTime;
import com.vma.push.business.dto.req.ReqfollowUser;
import com.vma.push.business.dto.rsp.RspActionLog;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.service.IClickActionLogService;
import com.vma.push.business.service.IUserStaffService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;


/**
 * Created by chenzui on 2018/5/2.
 */
@RestController
@RequestMapping("/v3.0")
@Api(value = "动作记录", description = "销售端--动作记录", tags = {"ActionLogController"})
@Service
public class ActionLogController extends ControllerExceptionHandler {
	private Logger LOG = Logger.getLogger(this.getClass());
	@Autowired
	private IClickActionLogService clickActionLogService;

	@Autowired
	private ClickActionLogMapper clickActionLogMapper;

	@Autowired
	private IUserStaffService userStaffService;

	@ApiOperation(value = "获取该用户名片被查看情况", notes = "获取该用户名片被查看情况")
	@RequestMapping(value = "/action/list", method = RequestMethod.POST)
	public RspPage<RspActionLog> getActionListByUser(@RequestBody Page page, HttpServletRequest request) {
		LOG.info("获取该用户名片被查看情况");
		//List<RspActionLog>
		String staffid = UserDataUtil.getMyStaffId(request);
		RspPage<RspActionLog> rspPage = clickActionLogService.getActionListByUser(staffid, page.getPage_num(), page.getPage_size());
		return rspPage;
	}

	@ApiOperation(value = "用户跟进", notes = "用户跟进")
	@RequestMapping(value = "/follow", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqfollowUser", value = "用户id 内容", required = true, dataType = "ReqfollowUser")
	public String userhead(@RequestBody ReqfollowUser reqfollowUser, HttpServletRequest request) {
		return userStaffService.follow(reqfollowUser, request);
	}

	@ApiOperation(value = "设置成交日期", notes = "设置成交日期")
	@RequestMapping(value = "/clinch_time", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqUpdateTime", value = "用户id 成交时间", required = true, dataType = "ReqUpdateTime")
	public String setclinchtime(@RequestBody ReqUpdateTime reqUpdateTime, HttpServletRequest request) {
//		reqUpdateTime.setStaff_id(UserDataUtil.getStaffId(request));
		reqUpdateTime.setStaff_id(UserDataUtil.getMyStaffId(request));
		Integer count = userStaffService.updateClinchTime(reqUpdateTime);
		if (count > 0) {
			ReqfollowUser reqfollowUser = new ReqfollowUser();
			reqfollowUser.setUser_id(reqUpdateTime.getUser_id());
			String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(reqUpdateTime.getTimes());
			dateStr = "更新预计成交日期：" + dateStr;
			reqfollowUser.setContent(dateStr);
			userhead(reqfollowUser, request);
			return "success";
		} else {
			return "fail";
		}

	}


	@ApiOperation(value = "设置成交率", notes = "设置成交率")
	@RequestMapping(value = "/clinch_rate", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqUpdateRate", value = "用户id 成交率", required = true, dataType = "ReqUpdateRate")
	public String setclinchRate(@RequestBody ReqUpdateRate reqUpdateRate, HttpServletRequest request) {
//		reqUpdateRate.setStaff_id(UserDataUtil.getStaffId(request));
		reqUpdateRate.setStaff_id(UserDataUtil.getMyStaffId(request));
		Integer count = userStaffService.updateClinchRate(reqUpdateRate);
		if (count > 0) {
			ReqfollowUser reqfollowUser = new ReqfollowUser();
			reqfollowUser.setUser_id(reqUpdateRate.getUser_id());
			String rate = reqUpdateRate.getRate().toString();
			rate = "更新预计成交率：" + rate + "%";
			reqfollowUser.setContent(rate);
			userhead(reqfollowUser, request);
			return "success";
		} else {
			return "fail";
		}

	}

}
