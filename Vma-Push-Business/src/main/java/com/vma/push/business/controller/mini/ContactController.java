package com.vma.push.business.controller.mini;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vma.push.business.dao.UserInfoMapper;
import com.vma.push.business.dao.UserStaffRelaMapper;
import com.vma.push.business.dto.ContactCard;
import com.vma.push.business.dto.req.mini.ReqSearch;
import com.vma.push.business.dto.req.userInfo.ReqLog;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.actionLog.UserActionLog;
import com.vma.push.business.entity.UserStaffRela;
import com.vma.push.business.service.IContactService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ljh
 * @Description: 通讯录、名片夹相关api
 * @date 2018-10-29 14:25
 */
@RestController
@RequestMapping("/v2.0")
@Api(value = "微信小程序", description = "小程序--通讯录相关api", tags = {"ContactController"})
public class ContactController {

	@Autowired
	private IContactService contactService;

	@Autowired
	private UserStaffRelaMapper userStaffRelaMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;  // 这么干违背MVC的，先这么干

	@ApiOperation(value = "通讯录名片夹列表", notes = "通讯录名片夹列表")
	@RequestMapping(value = "/mini/contact/cardList/{userId}/{openId}", method = RequestMethod.GET)
	public List<ContactCard> cardList(@PathVariable("userId") String userId, @PathVariable("openId") String openId, HttpServletRequest request) {
		userId = UserDataUtil.getUserId(request);
		return contactService.getContactCardList(userId, openId);
	}

	@ApiOperation(value = "名片夹", notes = "名片夹")
	@RequestMapping(value = "/mini/contact/cardHolderList", method = RequestMethod.GET)
	public Object cardHolderList(HttpServletRequest request, @RequestParam Integer page, @RequestParam String relaStatus) {
		String userId = UserDataUtil.getUserId(request);
		String openId = userInfoMapper.selectByPrimaryKey(userId).getOpenId();
		PageHelper.startPage(page, 10);
		return new PageInfo<>(contactService.getCardList(userId, openId, relaStatus));
	}

	@ApiOperation(value = "获取销售的staffId", notes = "获取销售的staffId")
	@RequestMapping(value = "/mini/contact/getStaffId/{openId}", method = RequestMethod.GET)
	public String getStaffId(@PathVariable("openId") String openId) {
		return contactService.getStaffId(openId);
	}

	@ApiOperation(value = "获取交接人员信息", notes = "获取交接人员信息")
	@RequestMapping(value = "/mini/contact/receiver/{id}")
	public ContactCard getReceiverStaff(@PathVariable String id) {
		return contactService.getReceiverStaff(id);
	}

	@ApiOperation(value = "通讯录（员工列表）", notes = "通讯录（员工列表）")
	@RequestMapping(value = "/mini/contact/staffList/{staffId}/{openId}", method = RequestMethod.GET)
	public Object staffList(@PathVariable("staffId") String staffId, @PathVariable("openId") String openId, @RequestParam Integer page) {
		PageHelper.startPage(page, 10);
		return new PageInfo<>(contactService.getContactStaffList(staffId, openId));
	}

	@ApiOperation(value = "通讯录（置顶）", notes = "通讯录（置顶）")
	@RequestMapping(value = "/mini/contact/setPosition/{relaId1}/{relaId2}", method = RequestMethod.GET)
	public void setPosition(@PathVariable("relaId1") String relaId1, @PathVariable("relaId2") String relaId2) {
		UserStaffRela userStaffRela1 = userStaffRelaMapper.selectByPrimaryKey(relaId1);
		UserStaffRela userStaffRela2 = userStaffRelaMapper.selectByPrimaryKey(relaId2);
		//当前置顶的名片取消置顶
		if (userStaffRela1 != null) {
			userStaffRela1.setPosition(null);
			userStaffRelaMapper.updateByPrimaryKey(userStaffRela1);
		}
		if (userStaffRela2 != null) {
			userStaffRela2.setPosition("1");
			userStaffRelaMapper.updateByPrimaryKey(userStaffRela2);
		}
	}

	@ApiOperation(value = "通讯录（取消置顶）", notes = "通讯录（取消置顶）")
	@RequestMapping(value = "/mini/contact/delPosition/{relaId}", method = RequestMethod.GET)
	public void delPosition(@PathVariable("relaId") String relaId) {
		UserStaffRela userStaffRela = userStaffRelaMapper.selectByPrimaryKey(relaId);
		if (userStaffRela != null) {
			userStaffRela.setPosition(null);
			userStaffRelaMapper.updateByPrimaryKey(userStaffRela);
		}
	}

	@ApiOperation(value = "收藏名片", notes = "收藏名片")
	@RequestMapping(value = "/mini/contact/collectCard/{id}", method = RequestMethod.GET)
	public void collectCard(@PathVariable("id") String id) {
		contactService.collectCard(id, 1);
	}

	@ApiOperation(value = "取消收藏名片", notes = "取消收藏名片")
	@RequestMapping(value = "/mini/contact/deleteCollectCard/{id}", method = RequestMethod.GET)
	public void deleteCollectCard(@PathVariable("id") String id) {
		contactService.collectCard(id, 0);
	}

	@ApiOperation(value = "获取收藏名片夹", notes = "获取收藏名片夹")
	@RequestMapping(value = "/mini/contact/getCollectCardList", method = RequestMethod.GET)
	public Object cardHolderList(HttpServletRequest request, @RequestParam Integer page) {
		String userId = UserDataUtil.getUserId(request);
		PageHelper.startPage(page, 10);
		return new PageInfo<>(contactService.getCollectCardList(userId));
	}

	@ApiOperation(value = "搜索名片", notes = "搜索名片")
	@RequestMapping(value = "/mini/contact", method = RequestMethod.POST)
	public Object searchCard(@RequestBody ReqSearch reqSearch, HttpServletRequest request) {
		return contactService.searchCard(reqSearch, request);
	}

	@ApiOperation(value = "获取看过我的名片列表", notes = "获取看过我的名片列表")
	@RequestMapping(value = "/mini/contact/getSeeMyselfCardList", method = RequestMethod.GET)
	public Object getSeeMyselfCardList(HttpServletRequest request) {
//		PageHelper.startPage(page, 10);
//		return new PageInfo<>(contactService.getSeeMyselfCardList(request));
		return contactService.getSeeMyselfCardList(request);
	}
}
