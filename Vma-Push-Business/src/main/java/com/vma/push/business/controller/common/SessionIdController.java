package com.vma.push.business.controller.common;

import com.vma.push.business.dao.EnterpriseMapper;
import com.vma.push.business.dao.StaffMapper;
import com.vma.push.business.dao.UserFormRelaMapper;
import com.vma.push.business.dao.UserInfoMapper;
import com.vma.push.business.dto.req.ReqCode;
import com.vma.push.business.dto.req.ReqMiniSendMsg;
import com.vma.push.business.entity.Staff;
import com.vma.push.business.entity.UserInfo;
import com.vma.push.business.service.IMessageService;
import com.vma.push.business.util.RedisUtil;
import com.vma.push.business.util.SmallSoftApi;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenzui on 2018/5/11.
 */
@RestController
@RequestMapping("/v4.0")
@Api(value = "sessionId", description = "辅助工具", tags = {"SessionIdController"})
public class SessionIdController {

	@Autowired
	private SmallSoftApi smallSoftApi;
	@Autowired
	private StaffMapper staffMapper;
	@Autowired
	private EnterpriseMapper enterpriseMapper;
	@Autowired
	private UserFormRelaMapper userFormRelaMapper;
	@Autowired
	private IMessageService messageService;
	@Autowired
	private UserInfoMapper userInfoMapper;

	@ApiOperation(value = "获取sessionId对应的值", notes = "获取sessionId对应的值")
	@RequestMapping(value = "/common/session/{id}", method = RequestMethod.GET)
	public String getValue(@ApiParam(value = "id", name = "id") @PathVariable(value = "id") String id) {
		return RedisUtil.get(id + "_enterprise_id");
	}

	@ApiOperation(value = "..", notes = "..")
	@ApiImplicitParam(value = "reqCode", name = "reqCode", required = true, dataType = "ReqCode")
	@RequestMapping(value = "/common/code", method = RequestMethod.POST)
	public String code(@RequestBody ReqCode reqCode) throws IOException {
//        return RedisUtil.get(id+"_enterprise_id");
		return smallSoftApi.code(reqCode.getStaff_id(), reqCode.getDepartment_id(), reqCode.getEnterprise_id(), reqCode.getApp_id(), reqCode.getSecure());

	}

	@ApiOperation(value = "推送未读消息到小程序", notes = "推送未读消息到小程序")
	@RequestMapping(value = "/common/send_to_mini", method = RequestMethod.POST)
	public String sendtomini(@RequestParam(value = "userid") String userid, HttpServletRequest request) throws Exception {
		ReqMiniSendMsg reqMiniSendMsg = new ReqMiniSendMsg();
		String staffid = UserDataUtil.getStaffId(request);
		String deptid = UserDataUtil.getDepartmentId(request);
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		String staffname = staffMapper.staffName(staffid);
		String templateid = enterpriseMapper.findmsgtmp(enterpriseId);
		String formid = userFormRelaMapper.getForm(userid);
		if (formid == null) {
			return "";
		}
		reqMiniSendMsg.setStaff_id(staffid);
		reqMiniSendMsg.setDepartment_id(deptid);
		reqMiniSendMsg.setEnterprise_id(enterpriseId);
		String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		reqMiniSendMsg.setTimes(dateStr);
		reqMiniSendMsg.setContent("您有未读消息，请打开小程序查看");
		reqMiniSendMsg.setStaff_name(staffname);
		reqMiniSendMsg.setTemplate_id(templateid);
		reqMiniSendMsg.setForm_id(formid);
		reqMiniSendMsg.setUser_id(userid);
		return smallSoftApi.message(reqMiniSendMsg);

	}

	@ApiOperation(value = "推送未读消息到销售端", notes = "推送未读消息到销售端")
	@RequestMapping(value = "/common/mini_send_to_sale", method = RequestMethod.POST)
	public String miniSendToSale(@RequestParam(value = "staffId") String staffId, HttpServletRequest request) throws Exception{
		String sendUserId = UserDataUtil.getUserId(request); // 发送方userId
		Staff staff = staffMapper.selectByPrimaryKey(staffId);
		String recipientUserId = userInfoMapper.findUserId(staff.getOpenid()); // 接受方userId
		String formId = userFormRelaMapper.getForm(recipientUserId);
		if (formId == null) {
			return "";
		}
		ReqMiniSendMsg reqMiniSendMsg = new ReqMiniSendMsg();
		reqMiniSendMsg.setStaff_id(staffId);
		reqMiniSendMsg.setDepartment_id(staff.getDepartmentId());
		reqMiniSendMsg.setEnterprise_id(staff.getEnterpriseId());
		String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		reqMiniSendMsg.setTimes(dateStr);
		reqMiniSendMsg.setContent("您有未读消息，请打开小程序查看");
		String templateId = enterpriseMapper.findmsgtmp(staff.getEnterpriseId());
		UserInfo sendUserInfo = userInfoMapper.selectByPrimaryKey(sendUserId);
		reqMiniSendMsg.setStaff_name(sendUserInfo.getNickName());
		reqMiniSendMsg.setTemplate_id(templateId);
		reqMiniSendMsg.setForm_id(formId);
		reqMiniSendMsg.setUser_id(recipientUserId);
		return smallSoftApi.miniSendTosale(reqMiniSendMsg);
	}

	@ApiOperation(value = "推送未读消息到销售端", notes = "推送未读消息到销售端")
	@RequestMapping(value = "/common/send_to_sale", method = RequestMethod.GET)
	public void sendtosale(@RequestParam(value = "userid") String userid, HttpServletRequest request) {
		String enterprise_id = UserDataUtil.getEnterpriseId(request);
		String wx_id = staffMapper.findWxId(userid);
		String[] userids = {wx_id};
		String content = "您在ai雷达中有未读消息，请注意查看";
		messageService.sendMsg_ai(userids, content, enterprise_id);
	}


	@ApiOperation(value = "..", notes = "..")
	@RequestMapping(value = "/keepalive", method = RequestMethod.GET)
	public String test() {
		return "ok";
	}
}
