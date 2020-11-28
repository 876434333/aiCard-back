package com.vma.push.business.service.impl;

import com.github.pagehelper.*;
import com.github.pagehelper.Page;
import com.google.gson.Gson;
import com.vma.push.business.common.ActionCode;
import com.vma.push.business.common.Constants;
import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.config.PlatformEnterprise;
import com.vma.push.business.dao.*;
import com.vma.push.business.dto.AttachmentAudio;
import com.vma.push.business.dto.AttachmentImage;
import com.vma.push.business.dto.CardResumeInfo;
import com.vma.push.business.dto.req.*;
import com.vma.push.business.dto.req.mini.ReqInvite;
import com.vma.push.business.dto.req.staff.*;
import com.vma.push.business.dto.req.store.ReqDiscount;
import com.vma.push.business.dto.req.store.ReqEnterDiscount;
import com.vma.push.business.dto.req.store.ReqStaffDiscount;
import com.vma.push.business.dto.req.userInfo.ReqCountAction;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.rsp.mini.ResInvite;
import com.vma.push.business.dto.rsp.staff.*;
import com.vma.push.business.dto.rsp.staff.RspStaffOrder;
import com.vma.push.business.dto.rsp.staff.RspTemplate;
import com.vma.push.business.dto.rsp.store.RspDiscount;
import com.vma.push.business.dto.rsp.store.RspDiscountList;
import com.vma.push.business.dto.rsp.userInfo.DataItem;
import com.vma.push.business.dto.rsp.userInfo.RspActionData;
import com.vma.push.business.dto.rsp.userInfo.RspAnalysisData;
import com.vma.push.business.dto.rsp.userInfo.RspUserLabelInfo;
import com.vma.push.business.entity.*;
import com.vma.push.business.service.IStaffInfoService;
import com.vma.push.business.util.*;
import com.vma.push.business.util.JSONArray;
import com.vma.push.business.util.JSONObject;
import org.apache.ibatis.builder.BuilderException;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by huxiangqiang on 2018/4/23.
 */
@Service
public class StaffInfoServiceImpl implements IStaffInfoService {
	private static Logger logger = Logger.getLogger(StaffInfoServiceImpl.class);

	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private StaffMapper staffInfoMapper;

	@Autowired
	private IStaffInfoService staffInfoService;
	@Autowired
	private BiActionLogMapper actionLogMapper;

	@Autowired
	private ClickActionLogMapper clickActionLogMapper;

	@Autowired
	private EnterpriseMapper enterpriseMapper;

	@Autowired
	private StaffIntroMapper staffIntroMapper;

	@Autowired
	private WeChatApi weChatApi;

	@Autowired
	private OfferSpecMapper offerSpecMapper;

	@Autowired
	private SmallSoftApi smallSoftApi;

	@Autowired
	private TotalsMapper totalMapper;

	@Autowired
	private TlsUtil tlsUtil;

	@Autowired
	private UserStaffRelaMapper userStaffRelaMapper;

	@Autowired
	private DeployMapper deployMapper;

	@Autowired
	private QiniuUtils qiniuUtils;

	@Autowired
	private SysConfigMapper sysConfigMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		return staffInfoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Staff record) {
		return staffInfoMapper.insert(record);
	}

	@Override
	public int insertSelective(Staff record) {
		return staffInfoMapper.insertSelective(record);
	}

	@Override
	public Staff selectByPrimaryKey(String id) {
		return staffInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Staff record) {
		return staffInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Staff record) {
		return staffInfoMapper.updateByPrimaryKey(record);
	}


	@Override
	public String insertApi(Employee employee, String enterprise_id) {
		String result = "";
		String token = weChatApi.Contacts_token(enterprise_id);
		String url = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=" + token;
		Map param = WeChatApi.transBean2Map(employee);
		result = HttpUtil.httpPostReq(url, param).getResponse_str();
		return result;
	}

	@Override
	public RspEnStaff queryStaff(String id, String enid) {
		return staffInfoMapper.queryStaff(id, enid);
	}

	@Override
	public String updateApi(Employee employee, String enterprise_id) {
		String result = "";
		String token = weChatApi.Contacts_token(enterprise_id);
		String url = "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=" + token;
		Map param = WeChatApi.transBean2Map(employee);
		result = HttpUtil.httpPostReq(url, param).getResponse_str();
		return result;
	}

	@Override
	public String deleteApi(String id, String enterprise_id) {
		String result = "";
		String token = weChatApi.Contacts_token(enterprise_id);
		System.out.print(token);
		String url = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=" + token + "&userid=" + id;
		result = HttpUtil.httpGetReq(url).getResponse_str();
		return result;
	}

	@Override
	public List<ReqAddWeiChatUser> wechatUsers(String enterprise_id) {
		return this.wechatUsers(enterprise_id, null);
	}

	@Override
	public List<ReqAddWeiChatUser> wechatUsers(String enterprise_id, String department_id) {
		String result = "";
		String token = weChatApi.Contacts_token(enterprise_id);
		System.out.print(token);
		String url;
		if (department_id == null) {
			url = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=" + token + "&department_id=1&fetch_child=1&status=0";
		} else {
			url = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=" + token + "&department_id=" + department_id + "&fetch_child=1&status=0";
		}
		result = HttpUtil.httpGetReq(url).getResponse_str();
		org.json.JSONObject jsonObject = new org.json.JSONObject(result);
		int errcode = new JSONObject(result).getInt("errcode");
		List<ReqAddWeiChatUser> userlist = new ArrayList<>();
		if (errcode == 0) {
			org.json.JSONArray resultJsonArray = jsonObject.getJSONArray("userlist");
			for (int i = 0; i < resultJsonArray.length(); i++) {
				ReqAddWeiChatUser weiChatUser = new ReqAddWeiChatUser();
				org.json.JSONObject users = resultJsonArray.getJSONObject(i);
				weiChatUser.setUserid(users.getString("userid"));
				weiChatUser.setName(users.getString("name"));
				weiChatUser.setDepartment(String.valueOf(users.getJSONArray("department").get(0)));
				weiChatUser.setPhone(users.getString("mobile"));
				String newurl = qiniuUtils.newUlr(users.getString("avatar"));
				weiChatUser.setHead_icon(newurl);
				weiChatUser.setMail(users.getString("email"));
				weiChatUser.setPosition(users.getString("position"));
				userlist.add(weiChatUser);
			}
		}
		return userlist;
	}

	/**
	 * @param reqAddStaffInfo
	 * @param enterprise_id
	 * @param isSyncEntWx     add by plh at 2018-11-01 for 增加一个参数，用于是否要同步到微信
	 * @return
	 * @throws IOException
	 */
	@Override
	@Transactional
	public RspStaffId addStaff(ReqAddStaffInfo reqAddStaffInfo, String enterprise_id, boolean isSyncEntWx) throws IOException {
		Employee employee = new Employee(reqAddStaffInfo);
		String staff_id = UuidUtil.getRandomUuidWithoutSeparator();

		int errcode = 0;
		String QYWX_UserId = null;
		if (isSyncEntWx == true) {
			//需要同步到微信
			//1、调用微信API
			errcode = new JSONObject(insertApi(employee, enterprise_id)).getInt("errcode");
			if (errcode == 48002) {
				throw new BusinessException(ErrCode.LOSE_NUM, "通讯录后台未开启通讯权限");
			}
			if (errcode == 60104) {
				throw new BusinessException(ErrCode.LOSE_NUM, "手机号码已存在");
			}
			if (errcode == 40066) {
				throw new BusinessException(ErrCode.LOSE_NUM, "部门id不存在");
			}

			QYWX_UserId = employee.getUserid();
		} else {
			// 不需要同步微信， 则要通过手机号从库里找一下之前注册过的企业微信ID的
			Deploy platformDeploy = deployMapper.selectAll(reqAddStaffInfo.getEnterprise_id());
			List<String> qywx_WxIdList = staffInfoMapper.getWxIdsByPhone(platformDeploy.getCorpid(), reqAddStaffInfo.getPhone());
			if (qywx_WxIdList != null) {
				for (String tempWXID : qywx_WxIdList) {
					if (StringUtils.isEmpty(tempWXID) == false) {
						QYWX_UserId = tempWXID;
						break;
					}
				}
			}
		}

		if (StringUtils.isEmpty(QYWX_UserId) == true) {
			throw new BusinessException(ErrCode.LOSE_NUM, "企业微信id不存在");
		}

		if (errcode == 0) {
			//2、调用成功后插入本地数据库
			Staff staff = reqAddStaffInfo.toStaff();//new staff(reqAddStaffInfo);
//			Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(staff.getEnterpriseId());
			Deploy deploy = deployMapper.selectAll(staff.getEnterpriseId());
			staff.setId(staff_id);
			//staff.setWxId(employee.getUserid());
			staff.setWxId(QYWX_UserId);
			staff.setDiscount(enterpriseMapper.findDiscount(staff.getEnterpriseId()));
			String code = "";
			if (reqAddStaffInfo.getIsPlatform() == 1) {
				code = smallSoftApi.getMiniUnlimitCode(staff.getId(), deploy.getAppId(), deploy.getSecret());
			} else {
				code = smallSoftApi.code(staff.getId(), staff.getDepartmentId(), staff.getEnterpriseId(), deploy.getAppId(), deploy.getSecret());
			}
			staff.setSoftImgUrl(code);
			if (staffInfoMapper.insertSelective(staff) > 0) {

				Totals total = new Totals();
				total.setStaffId(staff.getId());
				total.setId(UuidUtil.getRandomUuid());
				total.setZanToday(0);
				total.setZanOld(0);
				total.setViewToday(0);
				total.setViewOld(0);
				total.setZhuanOld(0);
				total.setZhuanToday(0);
				totalMapper.insertSelective(total);
			}
			//3 注册im
			tlsUtil.addUser(staff.getId(), staff.getName(), staff.getEnterpriseId());
		} else {
			throw new BusinessException(ErrCode.LOSE_NUM, "企业微信错误: " + errcode);
		}
		RspStaffId rspStaffId = new RspStaffId();
		//rspStaffId.setStaff_id(employee.getUserid());
		rspStaffId.setStaff_id(staff_id);
		rspStaffId.setWx_id(QYWX_UserId);
		return rspStaffId;
	}

	@Override
	public editCard getCard(String staffid, String enid) {
		editCard card = new editCard();
		card = staffInfoMapper.getCard(staffid, enid);
		//图片 mp3 视频等
		card.setRspStaffIntro(staffInfoMapper.intro(staffid, enid));
		return card;
	}

	@Transactional
	@Override
	public void editCard(editCard editCard, HttpServletRequest request) {
		Staff staff = editCard.toStaff();
		staff.setEnterpriseId(UserDataUtil.getEnterpriseId(request));
		//1、调用微信API
		Employee employee = new Employee(staff);
		int errcode = new JSONObject(updateApi(employee, staff.getEnterpriseId())).getInt("errcode");
		if (errcode == 0) {
			//2、调用成功后插入本地数据库
			staffInfoMapper.updateByPrimaryKeySelective(staff);
			//3、将数据插入到个人介绍中
			//先清除原有的介绍
			//staffInfoMapper.delIntro(staff.getId(),staff.getEnterpriseId());
			Integer order = 0;
			if (editCard.getRspStaffIntro() == null) {
				return;
			}
           /* for(RspStaffIntro intro:editCard.getRspStaffIntro()){
                ReqAddIntro reqAddIntro=new ReqAddIntro();
                reqAddIntro.setUrl(intro.getUrl());
                reqAddIntro.setType(intro.getType());
                reqAddIntro.setCreate_time(new Date());
                reqAddIntro.setEnterprise_id(staff.getEnterpriseId());
                reqAddIntro.setStaff_id(staff.getId());
                reqAddIntro.setId(UuidUtil.getRandomUuid());
                reqAddIntro.setOrder(order++);
                staffInfoMapper.insertIntro(reqAddIntro);
            }*/
		}

	}

	@Override
	public Long countUser(Map param) {
		return userStaffRelaMapper.countUser(param);
	}

	/**
	 * 查询名片列表
	 *
	 * @param reqCarList
	 * @return
	 */
	@Override
	public RspPage<RspCarlist> findStaffList(ReqCarList reqCarList) {
		RspPage<RspCarlist> rspPage = new RspPage<>();
		String open_id = reqCarList.getOpen_id();
		String userId = userInfoMapper.findUserId(open_id);
		Page page = PageHelper.startPage(reqCarList.getPage_num(), reqCarList.getPage_size(), true);
		List<RspCarlist> rspCarlist = staffInfoMapper.findCarList(userId);
		rspPage.setData_list(rspCarlist);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		return rspPage;
	}

	@Transactional
	@Override
	/**
	 * updateDB : Add by PLH at 2018-11-28 for 创建多个企业，一个企业微信用户有多个企业微信的部门，此时只要更新企业微信，而不需要更新本地数据库名片信息
	 */
	public void updateStaff(ReqUpdateStaffInfo reqUpdateStaffInfo, String enterprise_id) {
		//1、调用微信API
		Employee employee = new Employee(reqUpdateStaffInfo);
		int errcode = new JSONObject(updateApi(employee, enterprise_id)).getInt("errcode");
		if (errcode == 0) {
			Staff compare = selectByPrimaryKey(reqUpdateStaffInfo.getId());

			//2、调用成功后插入本地数据库
			Staff staff = reqUpdateStaffInfo.toStaff();//new staff(reqUpdateStaffInfo);

			if (staff.getDepartmentId() != null && !"".equals(staff.getDepartmentId())
					&& !compare.getDepartmentId().equals(staff.getDepartmentId())) {
				userStaffRelaMapper.updateDepartment(staff.getId(), staff.getDepartmentId(), compare.getDepartmentId());

				Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(staff.getEnterpriseId());
				Deploy deploy = deployMapper.selectAll(staff.getEnterpriseId());

				String code = null;
				try {
					code = smallSoftApi.code(staff.getId(), staff.getDepartmentId(), staff.getEnterpriseId(), deploy.getAppId(), deploy.getSecret());
					staff.setSoftImgUrl(code);

				} catch (IOException e) {

				}
			}
			staffInfoMapper.updateByPrimaryKeySelective(staff);
		}
	}

	@Transactional
	@Override
	public void delStaff(String id, String wxid, String enterprise_id) {

		//先判断是否有开雷达权限，如果权限有开则无法删除
		if (staffInfoMapper.isRadar(id) > 1) {
			throw new BusinessException(1000, "请先删除该员工的雷达权限！");
		}
		//1、调用微信API
		int errcode = new JSONObject(deleteApi(wxid
				, enterprise_id)).getInt("errcode");
		if (errcode == 301005) {
			throw new BusinessException(1000, "该用户为企业微信的创建人，无法删除，如需删除此人，请登陆企业微信后台转创建人权限！");
		}
		if (errcode == 0) {
			//2、调用成功后插入本地数据库
			//staffInfoMapper.deleteByPrimaryKey(id);
			staffInfoMapper.delStaff(id);
			//userStaffRelaMapper.deleteByStaff(id);
		}
	}

	@Override
	public RspPage<RspStaff> getAll(ReqStaffPage reqStaffPage, String enterpriseid) {
		RspPage<RspStaff> rspPage = new RspPage<RspStaff>();
		com.github.pagehelper.Page page = PageHelper.startPage(reqStaffPage.getPage_num(), reqStaffPage.getPage_size(), true);
       /* List<RspStaff> list=new ArrayList<>();
        //获取当前部门以及子部门
        List<String> dpetids=childDept(reqStaffPage.getDepartment_id(),enterpriseid);
        dpetids.add(reqStaffPage.getDepartment_id());
        for (String deptid:dpetids){
            List<RspStaff> list2=staffInfoMapper.getAll(deptid,enterpriseid);
            for (RspStaff rep:list2){
                list.add(rep);
            }
        }*/
		List<RspStaff> lists = staffInfoMapper.getAll(reqStaffPage.getDepartment_id(), enterpriseid, reqStaffPage.getStaff_name());
		for (RspStaff list : lists) {
			String staffId = list.getId();
			Integer customNum = staffInfoMapper.customCount(staffId);
			list.setCustom_num(customNum);
		}
		rspPage.setData_list(lists);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		return rspPage;
	}

	public List<String> childDept(String deptid, String enid) {

		List<String> ids = staffInfoMapper.childDept(deptid, enid);
		List<String> depts = staffInfoMapper.childDept(deptid, enid);
		BeanUtils.copyProperties(ids, depts);
		for (String id : ids) {
			List<String> ids2 = childDept(id, enid);
			for (String id2 : ids2) {
				depts.add(id2);
			}
		}
		return depts;
	}

	@Override
	public RspPage<RspStaff> staffListByUserId(String id, Integer pageNum, Integer pageSize) {
		RspPage<RspStaff> rspPage = new RspPage<RspStaff>();
		com.github.pagehelper.Page page = PageHelper.startPage(pageNum, pageSize, true);
		List<RspStaff> list = staffInfoMapper.StaffListByUserId(id);
		rspPage.setData_list(list);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		return rspPage;
	}

	//系统后台登陆
	@Override
	public int selectByUserNameAndPassword(ReqSystemLogin reqSystemLogin) {
		int count = staffInfoMapper.selectByUserNameAndPassword(reqSystemLogin);
		return count;
	}

	/**
	 * 更新密码
	 *
	 * @param sta
	 */
	@Override
	public void updatePassword(Staff sta) {
		staffInfoMapper.updateByPrimaryKeySelective(sta);
	}

	/**
	 * 雷达权限列表
	 *
	 * @return
	 */
	@Override
	public List<RspStaff> radar() {
		List<RspStaff> rspStaffs = staffInfoMapper.radar();
		return rspStaffs;
	}

	/**
	 * 条件查询雷达权限
	 *
	 * @return
	 */
	@Override
	public RspRadarPage<RspStaff> findStaffSelect(ReqStaffSelect reqStaffSelect) {
		RspRadarPage<RspStaff> rspPage = new RspRadarPage<>();
		Page page = PageHelper.startPage(reqStaffSelect.getPage_num(), reqStaffSelect.getPage_size(), true);
		List<RspStaff> rspStaffs = staffInfoMapper.findStaffSelect(reqStaffSelect);
//        Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(reqStaffSelect.getEnterprise_id());//查询企业
		rspPage.setData_list(rspStaffs);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
//        rspPage.setSale_card_num(enterprise.getSaleCardNum());
//        rspPage.setUsed_car_num(enterprise.getUsedCarNum());
		return rspPage;
	}

	/**
	 * 账号密码查询对象
	 *
	 * @param reqSystemLogin
	 * @return
	 */
	@Override
	public Staff selectStaff(ReqSystemLogin reqSystemLogin) {
		Staff staff = staffInfoMapper.selectStaff(reqSystemLogin);
		return staff;
	}


	@Override
	public RspCardInfo getById(String enterpriseId, String staffId, String userId) {
		RspCardInfo cardInfo = staffInfoMapper.queryCardInfo(enterpriseId, staffId, userId);
		Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(enterpriseId);
		if (cardInfo == null || "".equals(cardInfo)) {
			throw new BusinessException(1000, "无效名片");
		}
		cardInfo.setUser_icons(staffInfoMapper.userIcons(staffId));//浏览该名片的客户头像
		//cardInfo.setView_num(staffInfoMapper.viewNum(staffId));
		cardInfo.setEnterprise_name(enterprise.getName());
		// D by plh at 20181127 for 20181127000003-新建企业_初次进入员工名片报错, 员工图片表已经不在这里了
		// List<StaffIntro> staffIntros = staffIntroMapper.findStaffIntro(staffId);//根据员工id查询对应的图片
		// cardInfo.setStaffIntros(staffIntros);
//		cardInfo.setRec_offer(offerSpecMapper.recList(staffId));//推荐产品 有单独的接口获取


//		Long viewView = actionLogMapper.queryActionNum(staffId, ActionCode.ACTION_CODE_1001) + clickActionLogMapper.queryActionNum(enterpriseId, staffId, ActionCode.ACTION_CODE_1001);
//
//		Long zanHis = actionLogMapper.queryActionNum(staffId, ActionCode.ACTION_CODE_1002) - actionLogMapper.queryActionNum(staffId, ActionCode.ACTION_CODE_1003);
//
//		Long zanToday = clickActionLogMapper.queryActionNum(enterpriseId, staffId, ActionCode.ACTION_CODE_1002) - clickActionLogMapper.queryActionNum(enterpriseId, staffId, ActionCode.ACTION_CODE_1003);
//
//		Long zhuan_num = actionLogMapper.queryActionNum(staffId, ActionCode.ACTION_CODE_1004) + clickActionLogMapper.queryActionNum(enterpriseId, staffId, ActionCode.ACTION_CODE_1004);

		Totals total = totalMapper.findTotal(staffId);

		if (total == null) {
			total = new Totals();
			total.setId(UuidUtil.getRandomUuid());
			total.setStaffId(staffId);
			total.setViewOld(0);
			total.setViewToday(0);
			total.setZanOld(0);
			total.setZanToday(0);
			total.setZhuanOld(0);
			total.setZhuanToday(0);
			totalMapper.insertSelective(total);
		}

		if (cardInfo != null) {

			cardInfo.setZan_num(total.getZanOld() + total.getZanToday());//点赞

			cardInfo.setView_num(total.getViewOld() + total.getViewToday());//浏览量

			cardInfo.setZhuan_num(total.getZhuanOld() + total.getZhuanToday());//转发量
		}

//        if (cardInfo!=null){
//            cardInfo.setZan_num(zanHis+zanToday);
//
//            cardInfo.setView_num(viewView);
//
//            cardInfo.setZhuan_num(zhuan_num);
//        }


		return cardInfo;
	}

	@Override
	public List<RspStaff> getStaffByDepart(String enterpriseid, String id) {
		return staffInfoMapper.getStaffByDepart(enterpriseid, id);
	}

	@Override
	public List<RspStaffOrder> staffOrderBy(ReqStaffOrder req) {
		switch (req.getQuery_type()) {
			case 1: {
				return staffInfoMapper.staffOrderByOrderForm(req);
			}
			case 2: {
				return staffInfoMapper.staffOrderByUserNumber(req);
			}
			case 3: {
				return staffInfoMapper.staffOrderByInteract(req);
			}
			case 4: {
				req.setLow_close_rate(req.getLow_close_rate());
				req.setHight_close_rate(req.getHight_close_rate());
				return staffInfoMapper.staffOrderByClosedRate(req);
			}
		}
		return null;
	}

	@Override
	public RspStaffOrderPage customOfStaffDetail(ReqCustomList req) {
		RspStaffOrderPage rsp = new RspStaffOrderPage();
		Page page = PageHelper.startPage(req.getPage_num(), req.getPage_size(), true);
		switch (req.getQuery_type()) {
			case 1: {
				rsp.setData_list(userInfoMapper.customOfStaffOrderByUserNumber(req));
				break;
			}
			case 2: {
				rsp.setData_list(userInfoMapper.customOfStaffOrderByInteract(req));
				break;
			}
			case 3: {
				rsp.setData_list(userInfoMapper.customOfStaffOrderByClosedRate(req));
				break;
			}

		}
		rsp.setTotal_num(page.getTotal());
		rsp.setPage_num(page.getPageNum());
		rsp.setPage_size(page.getPageSize());
		rsp.setStaff_id(req.getStaff_id());
		rsp.setStaff_name(staffInfoMapper.staffName(rsp.getStaff_id()));
		return rsp;
	}

	/**
	 * 修改名片接口模版
	 *
	 * @param reqAiUpdateTemp
	 */
	@Override
	public void updateTemplate(ReqAiUpdateTemp reqAiUpdateTemp) {
		Staff staff = new Staff();
		staff.setId(reqAiUpdateTemp.getStaff_id());
		staff.setTemlateId(reqAiUpdateTemp.getTemplate_id());
		staffInfoMapper.updateByPrimaryKeySelective(staff);
	}

	@Override
	public RspCard cardInfo(String staffid, String enid) {
		return staffInfoMapper.getByStaffId(staffid, enid);
	}

	@Override
	public String departname(String enid, String id) {
		return staffInfoMapper.departname(enid, id);
	}

	@Override
	public RspAnalysisData queryUserData(String staffId, String departmentId, String enterpriseId) {
		//1查询饼图数据
		ReqCountAction reqCountAction = new ReqCountAction();
		reqCountAction.setStaffId(staffId);
		reqCountAction.setDepartmentId(departmentId);
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

		List<DataItem> line = clickActionLogMapper.countUserActionByDay(null, staffId, enterpriseId);

		RspAnalysisData rspAnalysisData = new RspAnalysisData();
		rspAnalysisData.setBar(bar);
		rspAnalysisData.setPie(pie);
		rspAnalysisData.setLine(line);

		return rspAnalysisData;
	}

	@Override
	@Transactional
	public List<RspHandover> handover(String fromid, String toid, String enterpriseId) {
		//信息交接清楚后删除用户
		String wx_id = staffInfoMapper.findWxId(fromid);
		staffInfoService.delStaff(fromid, wx_id, enterpriseId);
		List<RspHandover> result = new ArrayList<>();

		//先查询交出方所有客户
		List<RspHandover> userlist = staffInfoMapper.userlist(fromid, enterpriseId);
		for (RspHandover user : userlist) {
			//旁段改用户是否已经是接受方的客户
			if (staffInfoMapper.iscustomer(user.getUser_id(), toid, enterpriseId) > 0) {
				//已经是客户，无需交接
				user.setState("已经是你的客户了，无需交接");
				result.add(user);
				//无需交接的用户需要删除
				staffInfoMapper.delcustomer(user.getUser_id(), fromid, enterpriseId);
			} else {
				//交接
				staffInfoMapper.handover(user.getUser_id(), fromid, toid, enterpriseId);
				user.setState("交接成功！");
				result.add(user);
			}
		}
		clickActionLogMapper.handoverData(toid, fromid);
		//保存交接记录到表？
		return result;
	}

	@Override
	public void intro(List<RspStaffIntro> list) {
		for (RspStaffIntro intro : list) {

		}
	}

	/**
	 * BY PLH注释说明
	 * 根据 企业ID和Staff_ID获取当前用户的OPENID
	 *
	 * @param repOpenId
	 * @return
	 */
	@Override
	public String getopenid(RepOpenId repOpenId) {
		String code = repOpenId.getCode();
		String enterpriseId = repOpenId.getEnterprise_id();
		String staffId = repOpenId.getStaff_id();
		if (enterpriseId == null || "".equals(enterpriseId.trim())) {
			//if (enterpriseId==""){
			Staff staff = staffInfoMapper.selectByPrimaryKey(staffId);
			enterpriseId = staff.getEnterpriseId();
		}
		AllSecret allSecret = enterpriseMapper.AllSecret(enterpriseId);
		String appid = allSecret.getApp_id();
		String secret = allSecret.getSecret();
		return weChatApi.getopenid(appid, secret, code);
	}

	/**
	 * 获取默认员工(BY 小程序搜索操作)
	 * 第一种方式：企业独立部署的小程序        ===> 找这该企业的默认员工
	 * 第二种方式：多个企业共享平台部署小程序  ===> 找平台的默认客服员工
	 * reqOpenIdNew.code => 微信登陆返回的code
	 * reqOpenIdNew.wq_id => siteInfo.uniacid
	 */
	@Override
	public RspDefalutInfo defaultInfo(ReqOpenIdNew reqOpenIdNew) {
		RspDefalutInfo rspDefalutInfo = new RspDefalutInfo();
		//从微擎服务器获取该用户访问的小程序APPID
		//第一种方式：企业独立部署的小程序
		//第二种方式：多个企业共享平台部署小程序
		// M by PLH at 2018-11-26 for 暂时不从微擎获取小程序的APPID和secret，而是固定返回指定的小程序APPID和Secret begin
		// String appid = weChatApi.getAppid(reqOpenIdNew.getWq_id());
		// String secret = weChatApi.getSecret(reqOpenIdNew.getWq_id());
		Deploy deploy = deployMapper.selectAll(PlatformEnterprise.ID);
		String appid = deploy.getAppId();
		String secret = deploy.getSecret();
		// M by PLH at 2018-11-26 for 暂时不从微擎获取小程序的APPID和secret，而是固定返回指定的小程序APPID和Secret end

		// M by PLH at 2018-10-27 for 支持平台或是获取客服员工 Begin
		//  第二种平台模式下，一个小程序APPID可以查询出多家企业
		int enterprise_count = enterpriseMapper.getEnterpriseCount(appid);

		String enterprise_id = null;
		if (enterprise_count <= 0) {
			throw new BusinessException(-1, "未找到小程序对应的企业");
		} else if (enterprise_count == 1) {
			// 属于企业独立部署小程序
			enterprise_id = enterpriseMapper.getenterByAppid(appid);
		} else {
			// 多个企业共享平台小程序, 这是查询平台的客服的员工，所以直接用平台的enterprise_Id
			enterprise_id = PlatformEnterprise.ID;
		}
		//String enterprise_id = enterpriseMapper.getenterByAppid(appid);

		//判断appi.secret.企业id在密钥表中否存在 没有的话默认名片返回客迹的默认名片
		if (enterpriseMapper.checkkey(appid, secret, enterprise_id) == 0) {
			enterprise_id = PlatformEnterprise.ID;
		}
		// M by PLH at 2018-10-27 for 支持平台或是获取客服员工 END
		if (enterprise_id != null) {
			String staff_id = staffInfoMapper.defaultStaff(enterprise_id);
			String department_id = staffInfoMapper.deptByStaff(staff_id);
			rspDefalutInfo.setStaff_id(staff_id);
			rspDefalutInfo.setDepartment_id(department_id);
		}
		String openid = weChatApi.getopenid(appid, secret, reqOpenIdNew.getCode());
		rspDefalutInfo.setEnterprise_id(enterprise_id);
		rspDefalutInfo.setOpen_id(openid);
		return rspDefalutInfo;
	}

	@Override
	public List<RspActionData> actionData(ReqActionData reqActionData) {
		List<RspActionData> lists = clickActionLogMapper.actionDataByStaff(reqActionData);
		//取消点赞数
        /*String cancelZan=clickActionLogMapper.cancelZan(reqActionData);
        for (RspActionData list:lists){
            if (list.getAction_code().equals("1002")){
                //点赞-取消赞   实际点赞数
                list.setCount(String.valueOf((Integer.valueOf(list.getCount())-Integer.valueOf(cancelZan))));
            }
        }*/
		return lists;
	}

	@Override
	public ResPageAction<RspActionDetail> actionDetail(ReqActionCode reqActionCode) {
		ResPageAction<RspActionDetail> resPageAction = new ResPageAction<RspActionDetail>();
//        RspActionDetail rspActionDetail =new RspActionDetail();
		com.github.pagehelper.Page page = PageHelper.startPage(reqActionCode.getPage_num(), reqActionCode.getPage_size(), true);
		resPageAction.setData_list(clickActionLogMapper.actionDetail(reqActionCode));
		resPageAction.setTotal_num(page.getTotal());
		resPageAction.setPage_num(page.getPageNum());
		resPageAction.setPage_size(page.getPageSize());
		return resPageAction;

	}

	@Override
	public RspPage<RspStaffOfferIntro> staffOfferIntro(com.vma.push.business.dto.req.Page pages, String staffid, String enid) {
		RspPage<RspStaffOfferIntro> rspPage = new RspPage<RspStaffOfferIntro>();
		com.github.pagehelper.Page page = PageHelper.startPage(pages.getPage_num(), pages.getPage_size(), true);
		List<RspStaffOfferIntro> list = offerSpecMapper.staffOfferIntro(staffid, enid);
		rspPage.setData_list(list);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		return rspPage;
	}


	/**
	 * 刷新二维码
	 *
	 * @param enterpriseId
	 */
	@Override
	public void getRefreshRader(String enterpriseId) {
		List<Staff> list = staffInfoMapper.findAllStaff(enterpriseId);
		Deploy deploy = deployMapper.selectAll(enterpriseId);
		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);
		for (Staff staff : list) {
			try {
				String fileName = staff.getSoftImgUrl().replace(sysConfig.getQiniuUrl() + "/", "");
				// 删除七牛云上的图片
				boolean bDelete = qiniuUtils.deteteBucketFile(fileName);
				if(bDelete == false) {
					throw new BusinessException(ErrCode.NO_PHONE_EXIST, "七牛云图片删除失败");
				}
				String softUrl = smallSoftApi.getMiniUnlimitCode(staff.getId(), deploy.getAppId(), deploy.getSecret());
				staff.setSoftImgUrl(softUrl);
				staffInfoMapper.updateByPrimaryKeySelective(staff);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public ResPageAction<RspInteract> interactData(ReqInteract reqInteract) {
		ResPageAction<RspInteract> resPageAction = new ResPageAction<RspInteract>();
		RspInteract rspInteract = new RspInteract();
		com.github.pagehelper.Page page = PageHelper.startPage(reqInteract.getPage_num(), reqInteract.getPage_size(), true);
		resPageAction.setData_list(clickActionLogMapper.interactDataByStaff(reqInteract));
		resPageAction.setTotal_num(page.getTotal());
		resPageAction.setPage_num(page.getPageNum());
		resPageAction.setPage_size(page.getPageSize());
		return resPageAction;
	}

	@Override
	public List<RspInteractDetail> interactDetail(ReqInteractDetail reqInteractDetail) {
		return clickActionLogMapper.reqInteractDetail(reqInteractDetail);
	}

	@Override
	public String getSig(String staffid, String enterpriseid) {
		return staffInfoMapper.getSig(staffid, enterpriseid);
	}

	@Override
	public void editSig(String content, String staffid, String enterpriseid) {
		staffInfoMapper.editSig(content, staffid, enterpriseid);
	}

	@Override
	public String getSigVideo(String staffid, String enterpriseid) {
		return staffInfoMapper.getSigVideo(staffid, enterpriseid);
	}

	@Override
	public void editSigVideo(String content, String staffid, String enterpriseid) {
		staffInfoMapper.editSigVideo(content, staffid, enterpriseid);
	}

	@Override
	public RepCardNumber getCallingCardNumber(ReqCardNumber req) {
		RepCardNumber rep = new RepCardNumber();
		req.setDayNumber(0);
		rep.setToday(staffInfoMapper.getCallingCardNumber(req));
		req.setDayNumber(1);
		rep.setYesterday(staffInfoMapper.getCallingCardNumber(req));
		req.setDayNumber(7);
		rep.setSeven_day(staffInfoMapper.getCallingCardNumber(req));
		req.setDayNumber(30);
		rep.setThirty_day(staffInfoMapper.getCallingCardNumber(req));
		req.setDayNumber(90);
		rep.setNinety_day(staffInfoMapper.getCallingCardNumber(req));
		req.setDayNumber(null);
		rep.setAll(staffInfoMapper.getCallingCardNumber(req));
		return rep;
	}

	@Override
	public List<RspDaysCardNumber> getCallingCardNumberByDayNumber(ReqCardNumber req) {
		return staffInfoMapper.getCallingCardNumberByDayNumber(req);

	}

	@Override
	public List<RspStaffIntro> getintro(String staffid, String enid) {
		return staffInfoMapper.intro(staffid, enid);
	}

	@Override
	public void delintro(String id) {
		staffInfoMapper.delintro(id);
	}

	@Override
	public void insertIntro(ReqAddIntro reqAddIntro) {
		staffInfoMapper.insertIntro(reqAddIntro);
	}

	@Override
	public Staff findstaffBywxidAndEnid(String userid, String enterpriseid) {
		return staffInfoMapper.findstaffBywxidAndEnid(userid, enterpriseid);
	}

	@Override
	public RspStaffIcon getByHeadIcon(String id) {
		RspStaffIcon rspStaffIcon = new RspStaffIcon();
		Staff staff = staffInfoMapper.selectByPrimaryKey(id);
		rspStaffIcon.setHead_icon(staff.getHeadIcon());
		rspStaffIcon.setName(staff.getName());
		return rspStaffIcon;
	}

	@Override
	public RspEnterIconAndWxLogo findEnterIconAndWxLogo(String staffId) {
		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);

		RspEnterIconAndWxLogo rspEnterIconAndWxLogo = staffInfoMapper.findEnterIconAndWxLogo(staffId);
		rspEnterIconAndWxLogo.setLocation_logo(sysConfig.getLocationLogo());
		rspEnterIconAndWxLogo.setMobile_logo(sysConfig.getMobileLogo());
		rspEnterIconAndWxLogo.setWeichat_logo(sysConfig.getWechatLogo());
		return rspEnterIconAndWxLogo;
	}

	@Override
	public RspTemplate queryCardTemlate(String id, String enterpriseid) {
		return staffInfoMapper.queryCardTemplateId(enterpriseid, id);
	}

	@Override
	public RspDiscountList<RspDiscount> discountList(ReqDiscount reqDiscount) {
		RspDiscountList<RspDiscount> rspPage = new RspDiscountList<>();
		Page page = PageHelper.startPage(reqDiscount.getPage_num(), reqDiscount.getPage_size());
		List<RspDiscount> lists = staffInfoMapper.discountList(reqDiscount);
		rspPage.setData_list(lists);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		//企业折扣
		rspPage.setDiscount(enterpriseMapper.findDiscount(reqDiscount.getEnterprise_id()));
		rspPage.setEnterprise_name(enterpriseMapper.findEnterpriseName(reqDiscount.getEnterprise_id()));
		return rspPage;
	}

	@Override
	public void setEnterDiscount(String enterpriseId, BigDecimal count) {
		ReqEnterDiscount reqEnterDiscount = new ReqEnterDiscount();
		reqEnterDiscount.setCount(count);
		reqEnterDiscount.setEnterprise_id(enterpriseId);
		staffInfoMapper.setEnterDiscount(reqEnterDiscount);
	}

	@Override
	public void setStaffDiscount(ReqStaffDiscount reqStaffDiscount) {
		ReqEnterDiscount reqEnterDiscount = new ReqEnterDiscount();
		reqEnterDiscount.setCount(reqStaffDiscount.getDiscount());
		reqEnterDiscount.setEnterprise_id(reqStaffDiscount.getEnterprise_id());
		reqEnterDiscount.setId(reqStaffDiscount.getId());
		reqEnterDiscount.setType(reqStaffDiscount.getType());
		staffInfoMapper.setStaffDiscount(reqEnterDiscount);
	}

	@Override
	public RspCustomCount customCount(ReqCustomCount reqCustomCount) {
		RspCustomCount rspCustomCount = new RspCustomCount();
		rspCustomCount.setFrom_custom(staffInfoMapper.customCount(reqCustomCount.getFrom_id()));//交出方用户数
		rspCustomCount.setRepeat_custom(staffInfoMapper.repeatCount(reqCustomCount.getFrom_id(), reqCustomCount.getTo_id()));//重复客户数
		rspCustomCount.setActual_custom(rspCustomCount.getFrom_custom() - rspCustomCount.getRepeat_custom());
		return rspCustomCount;
	}

	@Override
	public Integer staffRadar(String id, String enterpriseid) {
		return staffInfoMapper.staffRadar(id, enterpriseid);
	}

	@Override
	public RspCardInfo getStaffInfoByOpenid(String openid, HttpServletRequest request) {
		logger.info("openid = " + openid);
		// 定义返回值 1:游客身份 -- 直接搜索小程序进入的 ；2:看名片; 3:管名片
		int loginType = 1;

		// 1. 获取小程序用户的信息
		UserInfo loginUserInfo = userInfoMapper.selectByOpenId(openid);

		logger.info("lastStaffId = " + loginUserInfo.getLastStaffId());

		//获取到最后查看名片的员工的对象
		Staff lastViewStaff = staffInfoMapper.selectByPrimaryKey(loginUserInfo.getLastStaffId());
		// A by plh at 2018-12-10 for 20181210000010-确保this.getById能查到用户信息 Begin
		if (lastViewStaff != null && lastViewStaff.getStatus() != 1) {
			//该名片拥有人不属于正常状态(0->已离职等)
			//重新找一个有效名片 -- 根据user_staff_rela.user_id的
			lastViewStaff = staffInfoMapper.getRandomStaffByUserId(loginUserInfo.getId());
		}
		// A by plh at 2018-12-10 for 20181210000010-确保this.getById能查到用户信息 End

		// 2. 查找该小程序登陸用户是否拥有名片
		String isExistCard = staffInfoMapper.isExistCardByOpenId(loginUserInfo.getOpenId());
		logger.info("isExistCard = " + isExistCard);

		if (StringUtils.isEmpty(isExistCard) == true || isExistCard.equals("1") == false) {
			// 2.1 该小程序用户没有自己的名片
			// 那么一定是看别人的名片，或者是搜索小程序进来建名片的
			if (lastViewStaff == null) {
				//For 游客: 搜索小程序进来建名片的
				loginType = 1;
			} else {
				//For 客户：看别人的名片
				loginType = 2;
			}
		} else {
			// 2.2 该小程序用户有自己的名片, 那就要看是不是看自己的名片了
			// 查看当前浏览名片的员工(lastViewStaff)的用户信息(user_info)，用来判断是否是看自己的名片
			if (lastViewStaff == null) {
				// 转换成看自己即可
				// For 管名片: 看自己的名片
				loginType = 3;
				lastViewStaff = staffInfoMapper.getRandomStaffByOpenId(loginUserInfo.getOpenId());
			} else {
				// 判斷lastViewStaff和loginUserInfo是否是同一個人
				UserInfo lastViewStaff_UserInfo = userInfoMapper.selectByOpenId(lastViewStaff.getOpenid());
				if (lastViewStaff_UserInfo.getId().equals(loginUserInfo.getId())) {
					// For 管名片: 看自己的名片
					loginType = 3;

				} else {
					// For 看名片: 看别人的名片
					loginType = 2;
				}
			}
		}

		RspCardInfo rspCardInfo = new RspCardInfo();
		String taken = request.getHeader(Constants.SESSION_ID);
		if (lastViewStaff != null) {
			// 这里对象会被重新执行，注意值覆盖问题
			rspCardInfo = this.getById(lastViewStaff.getEnterpriseId(), lastViewStaff.getId(), loginUserInfo.getId());
			rspCardInfo.setEnterprise_id(lastViewStaff.getEnterpriseId());
			rspCardInfo.setStaffId(lastViewStaff.getId());

			UserDataUtil.setStaffId(taken, lastViewStaff.getId());
			UserDataUtil.setEnterpriseId(taken, lastViewStaff.getEnterpriseId());
			UserDataUtil.setDepartmentId(taken, rspCardInfo.getDepartment_id());
			logger.info("lastViewStaff = " + lastViewStaff.toString());
			if (loginType == 3) {
				UserDataUtil.setMyStaffId(taken, lastViewStaff.getId());
				UserDataUtil.setMyEnterpriseId(taken, lastViewStaff.getEnterpriseId());
				UserDataUtil.setMyDepartmentId(taken, lastViewStaff.getDepartmentId());
			}
		}
		rspCardInfo.setUserId(loginUserInfo.getId());
		UserDataUtil.setUserId(taken, loginUserInfo.getId());
		rspCardInfo.setLoginType(loginType);
		logger.info("loginType = " + loginType);
		return rspCardInfo;
	}

	/**
	 * Created by ljh on 2018/10/24.
	 * begin
	 */

	@Override
	public List<RspUserLabelInfo> getStaffLabelAndReferLabelInfo(String staffId, String userId) {
		return staffInfoMapper.queryStaffLabelAndReferLabelInfo(staffId, userId);
	}

	@Override
	public List<AttachmentImage> getStaffImages(String staffId) {
		return staffInfoMapper.queryStaffImages(staffId);
	}

	@Override
	public AttachmentAudio getStaffAudio(String staffId) {
		return staffInfoMapper.queryStaffAudio(staffId);
	}

	@Override
	public Integer saveResume(CardResumeInfo cardResumeInfo) {
		String staffId = cardResumeInfo.getStaffId();
		String userId = cardResumeInfo.getUserId();
		String signature = cardResumeInfo.getSignature();
		List<RspUserLabelInfo> labels = cardResumeInfo.getLabels();
		List<AttachmentImage> images = cardResumeInfo.getImages();
		AttachmentAudio audio = cardResumeInfo.getAudio();

		// 保存简介
		if (signature != null) {
			this.saveStaffSignature(staffId, signature);
		}

		//保存录音
		if (audio != null) {
			this.saveStaffAudio(staffId, audio);
		}

		//保存标签
		if (labels != null) {
			for (RspUserLabelInfo label : labels) {
				this.saveStaffLabels(staffId, userId, label);
			}
		}

		//保存相册
		if (images != null) {
			this.saveStaffImages(staffId, images);
		}

		return null;
	}

	@Override
	public Integer saveStaffSignature(String staffId, String signature) {
		return staffInfoMapper.updateSignature(staffId, signature);
	}

	@Override
	public Integer saveStaffAudio(String staffId, AttachmentAudio audio) {
		if (audio.getId() == null && audio.getUrl() != null) {
			audio.setId(UuidUtil.removeSeparatorFromUuid(UuidUtil.getRandomUuid()));
			audio.setCreator_id(staffId);
			staffInfoMapper.insertAudio(audio);
			staffInfoMapper.updateAudioId(staffId, audio.getId());
		} else if (audio.getUrl() == null) {
			staffInfoMapper.updateAudioId(staffId, null);
			staffInfoMapper.deleteAudio(audio);
		} else {
			staffInfoMapper.updateAudio(audio);
		}
		return null;
	}

	@Override
	public Integer saveStaffLabels(String staffId, String userId, RspUserLabelInfo userLabelInfo) {
		userLabelInfo.setStaffId(staffId);
		userLabelInfo.setUserId(userId);
		if (userLabelInfo.getState().equals("add") && userLabelInfo.getType() == 1) {
			userLabelInfo.setRealId(UuidUtil.removeSeparatorFromUuid(UuidUtil.getRandomUuid()));
			staffInfoMapper.insertLabelInfo(userLabelInfo);
			staffInfoMapper.insertLabelUserReal(userLabelInfo);
		} else if (userLabelInfo.getState().equals("add") && userLabelInfo.getType() == 0) {
			userLabelInfo.setRealId(UuidUtil.removeSeparatorFromUuid(UuidUtil.getRandomUuid()));
			staffInfoMapper.insertLabelUserReal(userLabelInfo);
		} else if (userLabelInfo.getState().equals("del") && userLabelInfo.getType() == 1) {
			staffInfoMapper.deleteLabelInfo(userLabelInfo);
			staffInfoMapper.deleteLabelUserReal(userLabelInfo);
		} else {
			staffInfoMapper.deleteLabelUserReal(userLabelInfo);
		}
		return 0;
	}

	@Override
	public Integer saveStaffImages(String staffId, List<AttachmentImage> images) {
		for (AttachmentImage image : images) {
			if (image.getState().equals("add")) {
				image.setCreator_id(staffId);
				staffInfoMapper.insertImage(image);
			} else {
				staffInfoMapper.deleteImage(image);
			}
		}
		return 0;
	}

	/**
	 * Created by ljh on 2018/10/24.
	 * end
	 */

	/**
	 * 获取邀请详情页面的信息
	 *
	 * @return
	 */
	@Override
	public ResInvite getInviteInfo(ReqInvite reqInvite) {
		String inviteId = reqInvite.getInviteId();
		String enterpriseId = reqInvite.getEnterpriseId();
		ResInvite resInvite = new ResInvite();
		Staff staff = staffInfoMapper.selectByPrimaryKey(inviteId);
		List<String> headIconList = staffInfoMapper.getHeadIconListByEnterpriseId(enterpriseId);
		Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(enterpriseId);
		if (enterprise.getStatus() == 0) {
			throw new BusinessException(-1, "该企业已解散,请联系邀请人");
		}
		resInvite.setInviteName(staff.getName());
		resInvite.setInviteHeadIcon(staff.getHeadIcon());
		resInvite.setCreateName(enterprise.getManagerName());
		resInvite.setEnterpriseName(enterprise.getName());
		resInvite.setHeadIconList(headIconList);
		return resInvite;
	}

	@Override
	public Integer isExistCard(String openid) {
		String result = staffInfoMapper.isExistCardByOpenId(openid);
		// result 返回的值有可能为空
		if ("1".equals(result)) {
			return 1;
		} else {
			return 0;
		}
	}
}
