package com.vma.push.business.service.impl;

import com.vma.push.business.dao.StaffMapper;
import com.vma.push.business.dao.StaffQuitMapper;
import com.vma.push.business.dao.UserInfoMapper;
import com.vma.push.business.dao.UserStaffRelaMapper;
import com.vma.push.business.dto.ContactCard;
import com.vma.push.business.dto.ContactStaff;
import com.vma.push.business.dto.req.mini.ReqSearch;
import com.vma.push.business.dto.rsp.mini.ResOpenId;
import com.vma.push.business.entity.Staff;
import com.vma.push.business.service.IContactService;
import com.vma.push.business.util.UserDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements IContactService {

	@Autowired
	private StaffMapper staffMapper;
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private StaffQuitMapper staffQuitMapper;
	@Autowired
	private UserStaffRelaMapper userStaffRelaMapper;

	@Override
	public List<ContactCard> getContactCardList(String userId, String openId) {
		return staffMapper.queryContactCardListByUserId(userId, openId, 3);
	}

	@Override
	public List<ContactCard> getCardList(String userId, String openId, String relaStatus) {
		return staffMapper.queryCardListByUserId(userId, openId, relaStatus);
	}

	@Override
	public List<ContactStaff> getContactStaffList(String staffId, String openId) {
		return staffMapper.queryContactStaffListByEntId(staffId, openId);
	}

	@Override
	public ContactCard getReceiverStaff(String id) {
		// 递归查找最后的交接人
		String staffId = this.getLastReceiverId(id);
		return staffMapper.getReceiverStaff(staffId);
	}

	@Override
	public String getStaffId(String open_id) {
		return staffMapper.queryStaffIdByOpenId(open_id);
	}

	// 递归查找最后的交接人
	private String getLastReceiverId(String staffId) {
		Staff staff = null;
		staff = staffMapper.selectByPrimaryKey(staffId);
		if (staff.getStatus() == 1) {
			// 交接人在职状态，直接返回，退出递归
			return staff.getId();
		} else {
			String recevierStaffId = staffQuitMapper.getRecevierStaffId(staff.getId());
			return this.getLastReceiverId(recevierStaffId);
		}
	}

	/**
	 * 收藏名片/取消收藏
	 *
	 * @param id
	 */
	@Override
	public void collectCard(String id, int isCollect) {
		Integer result = userStaffRelaMapper.updataIsCollect(id, isCollect);
	}

	@Override
	public List<ContactCard> getCollectCardList(String userId) {
		return staffMapper.getCollectCardList(userId);
	}

	/**
	 * 搜索名片
	 *
	 * @param reqSearch
	 * @param request
	 * @return
	 */
	@Override
	public List<ContactCard> searchCard(ReqSearch reqSearch, HttpServletRequest request) {
		String userId = UserDataUtil.getUserId(request);
		String searchComment = reqSearch.getSearchComment();
		return staffMapper.searchCard(userId, searchComment);
	}

	@Override
	public List<ResOpenId> getSeeMyselfCardList(HttpServletRequest request) {
		String userId = UserDataUtil.getUserId(request);
		String openId = userInfoMapper.selectByPrimaryKey(userId).getOpenId();
		List<Staff> staffList = staffMapper.getStaffListByOpenId(openId);
		List<String> staffIdList = new ArrayList<>();
		for (Staff staff : staffList) {
			staffIdList.add(staff.getId());
		}
		return userStaffRelaMapper.getSeeMyselfCardList(staffIdList);
	}
}
