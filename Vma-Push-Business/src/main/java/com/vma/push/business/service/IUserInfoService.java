package com.vma.push.business.service;


import com.vma.push.business.dto.req.Page;
import com.vma.push.business.dto.req.ReqAdduser;
import com.vma.push.business.dto.req.ReqUserFormAdd;
import com.vma.push.business.dto.req.staff.ReqCarAction;
import com.vma.push.business.dto.req.userInfo.ReqAddUser;
import com.vma.push.business.dto.req.userInfo.ReqLog;
import com.vma.push.business.dto.req.userInfo.ReqQueryUserInfo;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.rsp.actionLog.UserActionLog;
import com.vma.push.business.dto.rsp.mini.RepRecommendCard;
import com.vma.push.business.dto.rsp.staff.RspStaffLogin;
import com.vma.push.business.dto.rsp.store.RspPageWithDiscount;
import com.vma.push.business.dto.rsp.store.RspUserCartList;
import com.vma.push.business.dto.rsp.superback.RspLoginInfo;
import com.vma.push.business.dto.rsp.userInfo.*;
import com.vma.push.business.entity.UserInfo;

import java.util.List;



/**
 * Created by huangjb on 2018/2/23.
 */
public interface IUserInfoService extends IBaseService<UserInfo,String> {

	public RspMiniInfo login(ReqAdduser reqAdduser);

	public RspPage<RspUserInfoList> queryUsers(ReqQueryUserInfo reqQueryUserInfo);

	public List<RspLabelType> queryUserLabelInfo(String userId);

	/**
	 * 根据用户ID，销售人员ID,企业ID查找业务动作
	 *
	 * @param reqLog
	 * @return
	 */
	public RspPage<UserActionLog> queryUserActionLog(ReqLog reqLog);
	public RspPage<UserActionLog> queryFollowLog(ReqLog reqLog);


	/**
	 * 查询AI雷达用户详情里的数据分析
	 *
	 * @param userId
	 * @param staffId
	 * @param enterprise
	 * @return
	 */
	public RspAnalysisData queryUserData(String userId, String staffId, String enterprise);


	/**
	 * 查询用户明细
	 *
	 * @param userId
	 * @param staffId
	 * @param enterpriseId
	 * @return
	 */
	public RspUserDetail4Sale queryDetail(String userId, String staffId, String enterpriseId);

	RspUserInfo findUserInfoByOpenId(String open_id,String endi);//根据open_id查询用户

	void addUserInfo(ReqAddUser reqAddUser,String enid);


	void updateCarAction(ReqCarAction reqCarAction);

	RspStaffLogin findStaffByOpenId(String id);

	/**
	 * 获取客户列表  销售端 历史聊天记录中用
	 *
	 * @param staffid
	 * @param enid
	 * @return
	 */
	RspUserandStaff userinfo(String staffid, String enid);

	/**
	 * 获取客户信息  客户详情中使用
	 *
	 * @param id
	 * @param staffid
	 * @param enterpriseid
	 * @return
	 */
	RspUserSimpleInfo simpleUserInfo(String id, String staffid, String enterpriseid);

	/**
	 * 编辑用户信息
	 * @param rspUserDetail4Sale
	 */
	void editUser(RspUserDetail4Sale rspUserDetail4Sale);

	List<RspHeadName2> userhead(List<String> id);

	/*
	新增关系
	 */
	void userformAdd(ReqUserFormAdd reqUserFormAdd);

	/**
	 * 通过用户id获取openid
	 * @param userid
	 * @return
	 */
	String openByUserId(String userid);

	RspLoginInfo userInfo(String id);

	String isPhone(String userId);

	RspHeadName userhead2(String id);

	RspPageWithDiscount<RspUserCartList> userShopCartList(String userId, Page page,String staffid);

	int updataLastStaffId(String enterpriseId, String staffId,String openid);

	String createRela(ReqAdduser reqAdduser);

	List<RepRecommendCard> getRecommendCardList();

	void viewRecommendCard(String staffId);
	boolean isExchangContact(String userId,String staffId);
}








