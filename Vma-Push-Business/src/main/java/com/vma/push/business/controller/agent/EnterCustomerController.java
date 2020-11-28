package com.vma.push.business.controller.agent;

import com.vma.push.business.dto.req.ReqAddWeiChatUser;
import com.vma.push.business.dto.req.ReqEnterList;
import com.vma.push.business.dto.req.ReqUpdateDeploy;
import com.vma.push.business.dto.req.enterprise.ReqEnterpriseAllId;
import com.vma.push.business.dto.req.superback.*;
import com.vma.push.business.dto.rsp.RspEnterInfo;
import com.vma.push.business.dto.rsp.RspEnterpriseAllId;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.RspTemplate;
import com.vma.push.business.dto.rsp.superback.RspEnterpariseList;
import com.vma.push.business.service.ICustomerService;
import com.vma.push.business.service.IStaffInfoService;
import com.vma.push.business.service.ISuperEnterpriseService;
import com.vma.push.business.util.IndexCreate;
import com.vma.push.business.util.UserDataUtil;
import com.vma.push.business.util.UuidUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/6/13.
 */
@RestController(value = "agentEnterCustomerController")
@RequestMapping("/V1.0")
@Api(value = "客户管理", description = "代理商--企业管理", tags = {"EnterCustomerController"})
public class EnterCustomerController {
	private Logger LOG = Logger.getLogger(this.getClass());

	@Autowired
	private ISuperEnterpriseService enterpriseService;
	@Autowired
	private ICustomerService iCustomerService;

	@Autowired
	private IndexCreate indexCreate;
	@Autowired
	private ISuperEnterpriseService iSuperEnterpriseService;

	@Autowired
	private IStaffInfoService staffInfoService;


	@ApiOperation(value = "企业列表", notes = "企业列表")
	@RequestMapping(value = "/agent/enterprise/list", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqEnterList", value = "查询条件", required = true, dataType = "ReqEnterList")
	public RspPage<RspEnterpariseList> enterlist(@RequestBody ReqEnterList reqEnterList, HttpServletRequest request) {
		LOG.info("企业列表");
		reqEnterList.setRole(1);
		reqEnterList.setParent_id(UserDataUtil.getCustomId(request));
		return enterpriseService.enterpariseList(reqEnterList);
	}

	@ApiOperation(value = "企业信息", notes = "企业信息")
	@RequestMapping(value = "/agent/enterprise/{id}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "id", value = "地理商id", required = true, dataType = "String", paramType = "path")
	public RspEnterInfo enterInfo(@PathVariable("id") String id, HttpServletRequest request) {
		LOG.info("企业信息");
		return enterpriseService.enterInfo(id);
	}

	@ApiOperation(value = "新增企业", notes = "新增企业")
	@RequestMapping(value = "/agent/enterprise", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqEnterList", value = "新增地区总代", required = true, dataType = "ReqAddEnterprise")
	public void enterpriseAdd(@RequestBody ReqAddEnterprise reqEnterList, HttpServletRequest request) {
		LOG.info("新增企业");
		reqEnterList.setType(5);
		reqEnterList.setRole(1);
		String id = UuidUtil.getRandomUuid();
		String enterprise_no = indexCreate.getIndex("AE", "AE");
		reqEnterList.setEnterprise_no(enterprise_no);
		System.out.println(UserDataUtil.getCustomId(request));
		reqEnterList.setParent_id(UserDataUtil.getCustomId(request));
		reqEnterList.setId(id);
		reqEnterList.setCreate_by(UserDataUtil.getAdminId(request));
		System.out.println(reqEnterList);
		enterpriseService.addEnterAndAdmin(reqEnterList);
		//添加各种密钥
		//  查询 iSuperEnterpriseService.selectEnterpriseId(id);
	}

	@ApiOperation(value = "test", notes = "test")
	@RequestMapping(value = "/agent/test", method = RequestMethod.POST)
	public List<ReqAddWeiChatUser> test() {
		return staffInfoService.wechatUsers("10bff76b-273f-4921-b840-2d049df24f6d");
	}

	@ApiOperation(value = "编辑企业", notes = "编辑企业")
	@RequestMapping(value = "/agent/enterprise", method = RequestMethod.PUT)
	@ApiImplicitParam(name = "reqEnterInfo", value = "编辑企业", required = true, dataType = "ReqEditEnterprise")
	public void enterpriseEdit(@RequestBody ReqEditEnterprise reqEnterInfo, HttpServletRequest request) {
		LOG.info("编辑企业");
		reqEnterInfo.setType(1); //admin表权限
		enterpriseService.editEnterAndAdmin(reqEnterInfo);

		// 修改 iSuperEnterpriseService.updateEnterseAllId(reqEnterpriseAllId);
	}

	@ApiOperation(value = "获取模板列表", notes = "获取模板列表")
	@RequestMapping(value = "/agent/template/list", method = RequestMethod.GET)
	public List<RspTemplate> enterInfo(HttpServletRequest request) {
		LOG.info("获取模板列表");
		return enterpriseService.findTemplate();
	}


	@ApiOperation(value = "增加名片", notes = "增加名片")
	@RequestMapping(value = "/agent/add_card", method = RequestMethod.PUT)
	@ApiImplicitParam(name = "reqCardNum", value = "添加名片", required = true, dataType = "ReqCardNum")
	public void addCard(@RequestBody ReqCardNum reqCardNum, HttpServletRequest request) {
		LOG.info("添加名片");
		enterpriseService.addCard(reqCardNum, request);

	}


	/**
	 * 重置密码
	 *
	 * @param
	 */
	@ApiOperation(value = "重置密码", notes = "重置密码")
	@RequestMapping(value = "/agent/update_password", method = RequestMethod.PUT)
	@ApiImplicitParam(name = "reqUpdatePassword", required = true, value = "重置密码", dataType = "ReqUpdatePassword")
	public void updatePassword(@RequestBody ReqUpdatePassword reqUpdatePassword) {
		LOG.info("重置密码");
		enterpriseService.updatePassword(reqUpdatePassword);
	}


	/**
	 * 修改状态
	 *
	 * @param
	 */
	@ApiOperation(value = "修改状态（启用与禁用）", notes = "修改状态（启用与禁用）")
	@RequestMapping(value = "/agent/update_status", method = RequestMethod.PUT)
	@ApiImplicitParam(name = "reqUpdateEnterStatus", required = true, value = "修改状态", dataType = "ReqUpdateEnterStatus")
	public void updateStatus(@RequestBody ReqUpdateEnterStatus reqUpdateEnterStatus) {
		LOG.info("修改状态（启用与禁用）");
		enterpriseService.updateStatus(reqUpdateEnterStatus);
	}

	@ApiOperation(value = "根据id查询", notes = "根据id查讯")
	@RequestMapping(value = "/agent/enterprise_all_id/{id}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "id", value = "查看参数", paramType = "path", required = true, dataType = "String")
	public RspEnterpriseAllId selectEnterpriseId(@PathVariable String id) {
		return iSuperEnterpriseService.selectEnterpriseId(id);
	}


	@ApiOperation(value = "修改各种密钥", notes = "修改各种密钥")
	@RequestMapping(value = "/agent/update_all_id", method = RequestMethod.PUT)
	@ApiImplicitParam(name = "reqEnterpriseAllId", required = true, value = "修改各种密钥", dataType = "ReqEnterpriseAllId")
	public void updateEnterseAllId(@RequestBody ReqEnterpriseAllId reqEnterpriseAllId) throws IOException {
		iSuperEnterpriseService.updateEnterseAllId(reqEnterpriseAllId);
	}

	@ApiOperation(value = "修改状态（启用与禁用）", notes = "修改状态（启用与禁用）")
	@RequestMapping(value = "/agent/update_deploy", method = RequestMethod.PUT)
	@ApiImplicitParam(name = "reqUpdateDeploy", required = true, value = "修改部署", dataType = "ReqUpdateDeploy")
	public void change(@RequestBody ReqUpdateDeploy reqUpdateDeploy) {
		LOG.info("修改部署状态（是否部署）");
		enterpriseService.updateDeploy(reqUpdateDeploy);
	}
}
