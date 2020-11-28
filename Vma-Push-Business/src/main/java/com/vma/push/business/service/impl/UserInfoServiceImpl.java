package com.vma.push.business.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vma.push.business.common.ActionCode;
import com.vma.push.business.common.Constants;
import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.config.PlatformEnterprise;
import com.vma.push.business.dao.*;
import com.vma.push.business.dto.req.ReqAdduser;
import com.vma.push.business.dto.req.ReqUserFormAdd;
import com.vma.push.business.dto.req.staff.ReqCarAction;
import com.vma.push.business.dto.req.userInfo.ReqAddUser;
import com.vma.push.business.dto.req.userInfo.ReqCountAction;
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
import com.vma.push.business.entity.*;
import com.vma.push.business.service.IUserInfoService;
import com.vma.push.business.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.*;

/**
 * Created by huangjb on 2018/2/23. extends AbstractServiceImpl<OsmLogicNode, String>
 */
@Service
public class UserInfoServiceImpl extends AbstractServiceImpl<UserInfo, String> implements IUserInfoService {

	@Autowired
	private UserInfoMapper mapper;

	@Autowired
	private LabelTypeMapper labelTypeMapper;

	@Autowired
	private LabelInfoMapper labelInfoMapper;

	@Autowired
	private ClickActionLogMapper clickActionLogMapper;

	@Autowired
	private UserStaffRelaMapper userStaffRelaMapper;

	@Autowired
	private EnterpriseMapper enterpriseMapper;

	@Autowired
	private StaffMapper staffMapper;

	@Autowired
	private WeChatApi weChatApi;

	@Autowired
	private TlsUtil tlsUtil;

	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private UserFormRelaMapper userFormRelaMapper;

	@Autowired
	private SysConfigMapper sysConfigMapper;
	@Autowired
	private RecommendCardMapper recommendCardMapper;


	public UserInfoServiceImpl(UserInfoMapper mapper) {
		super(mapper);
		this.mapper = mapper;
	}


	@Override
	public RspMiniInfo login(ReqAdduser reqAdduser) {

		UserInfo userInfo = mapper.selectByOpenId(reqAdduser.getOpen_id());
		RspMiniInfo rspMiniInfo = new RspMiniInfo();
		String token = "";
		if (userInfo == null) {//客户不存在   新增客户  建立用户/客户关系
			Staff staff = staffMapper.selectByPrimaryKey(reqAdduser.getEmployee_id());//根据销售人id查staff
			rspMiniInfo.setStaff_id(staff.getId());//获取员工id
			rspMiniInfo.setEnterprise_id(staff.getEnterpriseId());
			rspMiniInfo.setDepartment_id(staff.getDepartmentId());
			userInfo = reqAdduser.touserinfo();
			userInfo.setWxSoftId(weChatApi.findAppId(staff.getEnterpriseId()));
			mapper.insertSelective(userInfo);//添加用户

			UserStaffRela userStaffRela = new UserStaffRela();
			userStaffRela.setId(UuidUtil.getRandomUuid());
			userStaffRela.setCreateTime(new Date());
			userStaffRela.setEnterpriseId(staff.getEnterpriseId());//添加企业ID
			userStaffRela.setModifyTime(new Date());
			userStaffRela.setFroms(reqAdduser.getFroms());//名片来源
			userStaffRela.setStaffId(reqAdduser.getEmployee_id());//员工id
			userStaffRela.setUserId(userInfo.getId());//用户id
			userStaffRela.setDepartmentId(staff.getDepartmentId());

			if (!StringUtils.isEmpty(reqAdduser.getFrom_open_id())) {
				UserInfo from = mapper.selectByOpenId(reqAdduser.getFrom_open_id());
				userStaffRela.setFromUserId(from.getId());
			}
			userStaffRela.setFromUserId(reqAdduser.getFrom_open_id());
			UserStaffRela rela = userStaffRelaMapper.queryStaffInfoByUser(reqAdduser.getEmployee_id(), userInfo.getId(), userStaffRela.getEnterpriseId());//获取先前的用户关系

			if (rela == null) {
				userStaffRelaMapper.insertSelective(userStaffRela);
				rspMiniInfo.setIs_first(1);//第一次关注该名片
			}

		} else {
			Staff staff = staffMapper.selectByPrimaryKey(reqAdduser.getEmployee_id());
			rspMiniInfo.setStaff_id(staff.getId());//获取员工id
			rspMiniInfo.setEnterprise_id(staff.getEnterpriseId());
			rspMiniInfo.setDepartment_id(staff.getDepartmentId());
			UserStaffRela rela = userStaffRelaMapper.queryStaffInfoByUser(reqAdduser.getEmployee_id(), userInfo.getId(), staff.getEnterpriseId());//获取先前的用户关系
			if (rela == null) {
				UserStaffRela userStaffRela = new UserStaffRela();
				userStaffRela.setId(UuidUtil.getRandomUuid());
				userStaffRela.setCreateTime(new Date());
				userStaffRela.setEnterpriseId(staff.getEnterpriseId());//添加企业ID
				userStaffRela.setModifyTime(new Date());
				userStaffRela.setFroms(reqAdduser.getFroms());//名片来源
				userStaffRela.setStaffId(reqAdduser.getEmployee_id());//员工id
				userStaffRela.setUserId(userInfo.getId());//用户id
				userStaffRela.setDepartmentId(staff.getDepartmentId());
				userStaffRelaMapper.insertSelective(userStaffRela);
				rspMiniInfo.setIs_first(1);//第一次关注该名片
			}
		}

		//注册im
		tlsUtil.addUser(userInfo.getId(), userInfo.getNickName(), reqAdduser.getEnterprise_id());
		token = MD5Helper.getMD5Str(userInfo.getId());
		RedisUtil.set(token + "_user_id", userInfo.getId(), Constants.LOGIN_KEEP_TIME);
		RedisUtil.set(token + "_enterprise_id", reqAdduser.getEnterprise_id(), Constants.LOGIN_KEEP_TIME);
		RedisUtil.set(token + "_staff_id", reqAdduser.getEmployee_id(), Constants.LOGIN_KEEP_TIME);
		RedisUtil.set(token + "_department_id", reqAdduser.getDepartmentId(), Constants.LOGIN_KEEP_TIME);
		// TODO: 2018/5/7 环信IM
		rspMiniInfo.setToken(token);
		rspMiniInfo.setUser_id(userInfo.getId());
		return rspMiniInfo;
	}

	@Override
	public RspPage<RspUserInfoList> queryUsers(ReqQueryUserInfo reqQueryUserInfo) {
		RspPage<RspUserInfoList> rspPage = new RspPage<>();
		Page page = PageHelper.startPage(reqQueryUserInfo.getPage_num(), reqQueryUserInfo.getPage_size(), true);
		List<RspUserInfoList> orders = mapper.queryUsers(reqQueryUserInfo);
		rspPage.setData_list(orders);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		return rspPage;
	}

	@Override
	public List<RspLabelType> queryUserLabelInfo(String userId) {
		List<RspLabelType> types = labelTypeMapper.queryAll();
		for (RspLabelType type : types) {
			List<RspUserLabelInfo> userLabelInfos = labelInfoMapper.queryUserLabelInfo(userId, type.getId());
			type.setLabelInfo(userLabelInfos);
		}

		return types;
	}

	@Override
	public RspPage<UserActionLog> queryUserActionLog(ReqLog reqLog) {
		RspPage<UserActionLog> rspPage = new RspPage<>();
		Page page = PageHelper.startPage(reqLog.getPage_num(), reqLog.getPage_size(), true);
		List<UserActionLog> list = clickActionLogMapper.queryUserActionLog(reqLog);
		rspPage.setData_list(list);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		return rspPage;
	}

	@Override
	public RspPage<UserActionLog> queryFollowLog(ReqLog reqLog) {
		RspPage<UserActionLog> rspPage = new RspPage<>();
		Page page = PageHelper.startPage(reqLog.getPage_num(), reqLog.getPage_size(), true);
		List<UserActionLog> list = clickActionLogMapper.queryFollowLog(reqLog);
		rspPage.setData_list(list);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		return rspPage;
	}

	@Override
	public RspAnalysisData queryUserData(String userId, String staffId, String enterpriseId) {
		//1查询饼图数据
		ReqCountAction reqCountAction = new ReqCountAction();
		reqCountAction.setUserId(userId);
		reqCountAction.setStaffId(staffId);
		reqCountAction.setEnterpriseId(enterpriseId);
		List<String> me = new ArrayList<>();
		me.add(ActionCode.ACTION_CODE_1001);
		me.add(ActionCode.ACTION_CODE_1002);
		me.add(ActionCode.ACTION_CODE_1004);
		me.add(ActionCode.ACTION_CODE_1005);
		me.add(ActionCode.ACTION_CODE_1006);
		me.add(ActionCode.ACTION_CODE_1007);
		me.add(ActionCode.ACTION_CODE_1008);
		me.add(ActionCode.ACTION_CODE_1009);
		me.add(ActionCode.ACTION_CODE_1010);

		List<String> enterprise = new ArrayList<>();
		enterprise.add(ActionCode.ACTION_CODE_1011);
		enterprise.add(ActionCode.ACTION_CODE_1012);
		enterprise.add(ActionCode.ACTION_CODE_1013);
		enterprise.add(ActionCode.ACTION_CODE_1014);

		List<String> offer = new ArrayList<>();
		offer.add(ActionCode.ACTION_CODE_1015);
		offer.add(ActionCode.ACTION_CODE_1016);
		offer.add(ActionCode.ACTION_CODE_1017);
		offer.add(ActionCode.ACTION_CODE_1018);
		offer.add(ActionCode.ACTION_CODE_1019);
		offer.add(ActionCode.ACTION_CODE_1020);

		reqCountAction.setCodes(me);
		DataItem meItem = clickActionLogMapper.countActionByUser(reqCountAction);
		meItem.setName("对我感兴趣");

		reqCountAction.setCodes(enterprise);
		DataItem enItem = clickActionLogMapper.countActionByUser(reqCountAction);
		enItem.setName("对公司感兴趣");

		reqCountAction.setCodes(offer);
		DataItem offerItem = clickActionLogMapper.countActionByUser(reqCountAction);
		offerItem.setName("对产品感兴趣");

		List<DataItem> pie = new ArrayList<>();
		pie.add(meItem);
		pie.add(offerItem);
		pie.add(enItem);

		//查询与我互动的数据
		List<String> action = new ArrayList<>();
		action.add(ActionCode.ACTION_CODE_1001);
		reqCountAction.setCodes(action);
		DataItem action1001 = clickActionLogMapper.countActionByUser(reqCountAction);
		action1001.setName("查看名片");

		action.clear();
		action.add(ActionCode.ACTION_CODE_1002);
		reqCountAction.setCodes(action);
		DataItem action1002 = clickActionLogMapper.countActionByUser(reqCountAction);
		action1002.setName("名片点赞");

		action.clear();
		action.add(ActionCode.ACTION_CODE_1004);
		reqCountAction.setCodes(action);
		DataItem action1004 = clickActionLogMapper.countActionByUser(reqCountAction);
		action1004.setName("转发名片");

		action.clear();
		action.add(ActionCode.ACTION_CODE_1005);
		reqCountAction.setCodes(action);
		DataItem action1005 = clickActionLogMapper.countActionByUser(reqCountAction);
		action1005.setName("呼叫座机");


		action.clear();
		action.add(ActionCode.ACTION_CODE_1006);
		reqCountAction.setCodes(action);
		DataItem action1006 = clickActionLogMapper.countActionByUser(reqCountAction);
		action1006.setName("呼叫手机");
		List<DataItem> bar = new ArrayList<>();
		bar.add(action1001);
		bar.add(action1002);
		bar.add(action1004);
		bar.add(action1005);
		bar.add(action1006);

		List<DataItem> line = clickActionLogMapper.countUserActionByDay(userId, staffId, enterpriseId);

		RspAnalysisData rspAnalysisData = new RspAnalysisData();
		rspAnalysisData.setBar(bar);
		rspAnalysisData.setPie(pie);
		rspAnalysisData.setLine(line);

		return rspAnalysisData;
	}

	@Override
	public RspUserDetail4Sale queryDetail(String userId, String staffId, String enterpriseId) {
//        List<String> label = mapper.queryUserLabels(userId,staffId);

		RspUserDetail4Sale user = mapper.queryUserByStaff(enterpriseId, userId, staffId);
//        user.setLabel(label);
		return user;
	}

	/**
	 * 根据open_id查询用户名密码
	 *
	 * @param open_id
	 * @return
	 */
	@Override
	public RspUserInfo findUserInfoByOpenId(String open_id, String enid) {
		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);

		RspUserInfo userInfo = mapper.findUserInfoByOpenId(open_id);
		if (userInfo == null) {
			return userInfo;
		} else {
			userInfo.setAcount_type(sysConfig.getAccountType());
			userInfo.setSdk_id(sysConfig.getImSdkApp());
			userInfo.setSig(tlsUtil.getSig(userInfo.getId(), enid));
			return userInfo;
		}
	}

	/**
	 * 添加用户名
	 *
	 * @param reqAddUser
	 */
	@Override
	public void addUserInfo(ReqAddUser reqAddUser, String enid) {
		//mapper.addUserInfo(reqAddUser);
		String id = mapper.findUserId(reqAddUser.getOpen_id());
		UserInfo userInfo = new UserInfo();
		userInfo.setId(UuidUtil.getRandomUuid());
		userInfo.setOpenId(reqAddUser.getOpen_id());
		userInfo.setHxImLogin(reqAddUser.getHx_im_login());
		userInfo.setHxImPassword(reqAddUser.getHx_im_password());
		userInfo.setId(id);
		//注册腾讯im
		tlsUtil.addUser(userInfo.getId(), userInfo.getNickName(), enid);

		mapper.updateByPrimaryKeySelective(userInfo);
	}

	/**
	 * 开启屏蔽名片
	 *
	 * @param reqCarAction
	 */
	@Override
	public void updateCarAction(ReqCarAction reqCarAction) {
		String user_id = mapper.findUserId(reqCarAction.getOpen_id());
		reqCarAction.setUser_id(user_id);
		userStaffRelaMapper.updateCarAction(reqCarAction);
	}

	@Override
	public RspStaffLogin findStaffByOpenId(String id) {

		return staffMapper.findStaffByOpenId(id);
	}

	@Override
	public RspUserandStaff userinfo(String staffid, String enid) {
		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);

		RspUserandStaff rspUserandStaff = new RspUserandStaff();
		rspUserandStaff.setAcount_type(sysConfig.getAccountType());
		rspUserandStaff.setSdk_app_id(sysConfig.getImSdkApp());
		//rspUserandStaff.setUser_list(staffMapper.userinfo(staffid,enid));
		System.out.print("获取sig-------staffid:" + staffid + "---enid:" + enid);
		rspUserandStaff.setUserSig(tlsUtil.getSig(staffid, enid));
		System.out.print("用户信息-------staffid:" + staffid + "---enid:" + enid);
		RspStaffinfo staffinfo = staffMapper.staffInfo(staffid, enid);
		rspUserandStaff.setId(staffid);
		if (staffinfo == null) {
			return rspUserandStaff;
		} else {
			rspUserandStaff.setPass_word(staffinfo.getPass_word());
			rspUserandStaff.setHead_icon(staffinfo.getHead_icon());
			rspUserandStaff.setName(staffinfo.getName());
			rspUserandStaff.setSoft_img_url(staffinfo.getSoft_img_url());
			return rspUserandStaff;
		}


	}

	@Override
	public RspUserSimpleInfo simpleUserInfo(String id, String staffid, String enterpriseid) {
		return userStaffRelaMapper.simpleUserInfo(id, staffid, enterpriseid);
	}

	@Override
	public void editUser(RspUserDetail4Sale rspUserDetail4Sale) {
		userStaffRelaMapper.editUser(rspUserDetail4Sale);
	}

	@Override
	public List<RspHeadName2> userhead(List<String> ids) {
		List<RspHeadName2> list = new ArrayList<>();
		for (String userid : ids) {
			RspHeadName rspHeadName = userInfoMapper.userhead(userid);
			if (rspHeadName != null) {
				RspHeadName2 rspHeadName2 = new RspHeadName2();
				rspHeadName2.setId(userid);
				rspHeadName2.setHead_icon(rspHeadName.getHead_icon());
				rspHeadName2.setNick_name(rspHeadName.getNick_name());
				list.add(rspHeadName2);
			}
		}
		return list;
	}

	@Override
	public void userformAdd(ReqUserFormAdd reqUserFormAdd) {
		if (userFormRelaMapper.isexist(reqUserFormAdd) > 0) {
			//存在删除
			//userFormRelaMapper.userformDel(reqUserFormAdd);
		} else {
			//不存在添加
			userFormRelaMapper.userformAdd(reqUserFormAdd);
		}

	}

	@Override
	public String openByUserId(String userid) {
		return userInfoMapper.openByUserId(userid);
	}

	@Override
	public RspLoginInfo userInfo(String id) {
		return userInfoMapper.userInfo(id);
	}

	@Override
	public String isPhone(String userId) {
		return userInfoMapper.isPhone(userId);
	}

	@Override
	public RspHeadName userhead2(String id) {
		return userInfoMapper.userhead(id);
	}

	@Override
	public RspPageWithDiscount<RspUserCartList> userShopCartList(String userId, com.vma.push.business.dto.req.Page pageSelect, String staffid) {
		System.out.println("用戶ID" + userId);
		RspPageWithDiscount<RspUserCartList> rspPage = new RspPageWithDiscount<RspUserCartList>();
		Page page = PageHelper.startPage(pageSelect.getPage_num(), pageSelect.getPage_size(), true);
		rspPage.setData_list(userInfoMapper.userShopCartList(userId, staffid));
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		rspPage.setDiscount(staffMapper.findDiscount(staffid));
		return rspPage;

	}

	/**
	 * 更新last_staff_id
	 *
	 * @param staffId 被查看的staffId
	 * @param openid  查看者的openid
	 * @return
	 */
	@Override
	public int updataLastStaffId(String enterpriseId, String staffId, String openid) {
		Staff staff = staffMapper.selectByPrimaryKey(staffId);
		UserInfo userInfo = userInfoMapper.selectByOpenId(openid);
		userInfo.setLastStaffId(staffId);
		userInfoMapper.updateByPrimaryKeySelective(userInfo);
		if (staff.getOpenid().equals(openid)) {
			return 1;// 自己的名片
		} else {
			return 0;// 别人的名片
		}
	}

	/**
	 * 平台模式用户通过扫码或者链接进来，记录用户信息
	 *
	 * @param reqAdduser
	 * @return
	 */
	@Override
	public String createRela(ReqAdduser reqAdduser) {
		UserInfo userInfo = mapper.selectByOpenId(reqAdduser.getOpen_id());
		Staff staff = staffMapper.selectByPrimaryKey(reqAdduser.getEmployee_id());//根据销售人id查staff
		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);
		// 1、判断openid是否存在
		if (userInfo == null) {
			userInfo = reqAdduser.touserinfo();
			userInfo.setWxSoftId(sysConfig.getPlatformMiniAppId());
			userInfo.setFromUserId(mapper.selectByOpenId(staff.getOpenid()).getId());
			mapper.insertSelective(userInfo);//添加用户
			tlsUtil.addUser(userInfo.getId(), userInfo.getNickName(), staff.getEnterpriseId());
		}
		// 2、判断是否建立关系
		UserStaffRela userStaffRela1 = userStaffRelaMapper.queryStaffInfoByUser(staff.getId(), userInfo.getId(), staff.getEnterpriseId());
		if (userStaffRela1 == null) {
			UserStaffRela userStaffRela = new UserStaffRela();
			userStaffRela.setId(UuidUtil.getRandomUuid());
			userStaffRela.setCreateTime(new Date());
			userStaffRela.setEnterpriseId(staff.getEnterpriseId());//添加企业ID
			userStaffRela.setModifyTime(new Date());
			userStaffRela.setFroms(reqAdduser.getFroms());//名片来源
			userStaffRela.setStaffId(reqAdduser.getEmployee_id());//员工id
			userStaffRela.setUserId(userInfo.getId());//用户id
			userStaffRela.setDepartmentId(staff.getDepartmentId());
			userStaffRelaMapper.insertSelective(userStaffRela);
		}
		// 修改最后一次查看名片
		userInfo.setLastStaffId(staff.getId());
		userInfoMapper.updateByPrimaryKeySelective(userInfo);
		String token = MD5Helper.getMD5Str(userInfo.getId());
		RedisUtil.set(token + "_user_id", userInfo.getId(), Constants.LOGIN_KEEP_TIME);
		return token;
	}

	@Override
	public List<RepRecommendCard> getRecommendCardList() {
		return recommendCardMapper.miniGetRecommendCard();
	}

	@Override
	public void viewRecommendCard(String staffId) {
		// 推荐名片浏览数量加1
		RecommendCard recommendCard = recommendCardMapper.getRecommendCardByStaffId(staffId);
		int viewNum = recommendCard.getViewNumber();
		recommendCard.setViewNumber(viewNum + 1);
		recommendCard.setNotifyTime(new Date());
		recommendCardMapper.updateByPrimaryKeySelective(recommendCard);
	}

	/**
	 * 判断是否交换联系方式
	 *
	 * @param userId
	 * @param staffId
	 * @return
	 */
	@Override
	public boolean isExchangContact(String userId, String staffId) {
		String phone = userStaffRelaMapper.getPhone(userId, staffId);
		if (StringUtils.isEmpty(phone)) {
			return false;
		} else {
			return true;
		}
	}
}

