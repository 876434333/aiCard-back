package com.vma.push.business.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.config.PlatformEnterprise;
import com.vma.push.business.dao.*;
import com.vma.push.business.dto.req.*;
import com.vma.push.business.dto.req.enterprise.ReqEnterpriseAllId;
import com.vma.push.business.dto.req.superback.ReqCardNum;
import com.vma.push.business.dto.req.superback.ReqEditEnterprise;
import com.vma.push.business.dto.req.superback.ReqUpdateEnterStatus;
import com.vma.push.business.dto.req.superback.ReqUpdatePassword;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.rsp.superback.RspEnterpariseList;
import com.vma.push.business.entity.*;
import com.vma.push.business.service.IDepartmentService;
import com.vma.push.business.service.IStaffInfoService;
import com.vma.push.business.service.ISuperEnterpriseService;
import com.vma.push.business.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Created by ChenXiaoBin
 * 2018/5/6 20:58
 */
@Service
public class SuperEnterpriseServiceImpl implements ISuperEnterpriseService {
	private static Logger logger = Logger.getLogger(SuperEnterpriseServiceImpl.class);

	@Autowired
	private EnterpriseMapper enterpriseMapper;
	@Autowired
	private StaffMapper staffInfoMapper;
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private DepartmentMapper departmentMapper;
	@Autowired
	private SmallSoftApi smallSoftApi;
	@Autowired
	private IStaffInfoService staffInfoService;
	@Autowired
	private DeployMapper deployMapper;
	@Autowired
	private KjPointLogMapper kjPointLogMapper;

	@Autowired
	private ShopTemplateMapper shopTemplateMapper;

	@Autowired
	private TlsUtil tlsUtil;

	@Autowired
	private SysConfigMapper sysConfigMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private IDepartmentService departmentService;
	@Autowired
	private UserStaffRelaMapper userStaffRelaMapper;

	// add by plh at 2018-11-01 for 企业微信部门ID控制
	@Autowired
	private IndexCreate indexCreate;

	@Override
	public int deleteByPrimaryKey(String id) {
		return 0;
	}

	@Override
	public int insert(Enterprise record) {
		return 0;
	}

	@Override
	public int insertSelective(Enterprise record) {
		return 0;
	}

	@Override
	public Enterprise selectByPrimaryKey(String id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Enterprise record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Enterprise record) {
		return 0;
	}

	/**
	 * 查看企业信息列表（超级管理后台）
	 *
	 * @param reqEnterpriseSelect
	 * @return
	 */
	@Override
	public RspPage<RspAllEnterprise> findAllEnterprise(ReqEnterpriseSelect reqEnterpriseSelect, String AdminId) {
		// Admin admin = adminMapper.selectByPrimaryKey(AdminId);
//        if (admin.getRole().equals(0)){//代理商
//            reqEnterpriseSelect.setCreate_staff_id(AdminId);
//        }
		RspPage<RspAllEnterprise> rspPage = new RspPage<>();
		Page page = PageHelper.startPage(reqEnterpriseSelect.getPage_num(), reqEnterpriseSelect.getPage_size(), true);
		List<RspAllEnterprise> rspAllEnterpriseList = enterpriseMapper.findAllEnterprise(reqEnterpriseSelect);
		rspPage.setData_list(rspAllEnterpriseList);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		for (RspAllEnterprise R : rspAllEnterpriseList) {
			Date a = R.getExpire_time();
			Date b = new Date();
			if (a.getTime() < b.getTime()) {//到期时间小于当前时间表示已过期
				R.setStatus(2);
				Enterprise enterprise = new Enterprise();
				enterprise.setStatus(2);
				enterprise.setId(R.getId());
//              admin.setStatus(2);
//              admin.setId(AdminId);
				enterpriseMapper.updateByPrimaryKeySelective(enterprise);
				//adminMapper.updateByPrimaryKeySelective(admin);
			}
		}
		return rspPage;
	}

	/**
	 * 根据id查看企业信息
	 *
	 * @return
	 */
	@Override
	public RspAllEnterprise findEnterpriseById(String id) {
		RspAllEnterprise rspAllEnterprise = new RspAllEnterprise();
		Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(id);
		Admin admin = adminMapper.selectByPrimaryKey(enterprise.getCreateStaffId());
		rspAllEnterprise.setAdmin_name(admin.getName());//创建人的姓名
		rspAllEnterprise.setBusiness_license_code(enterprise.getBusinessLicenseCode());
		rspAllEnterprise.setBusiness_license_url(enterprise.getBusinessLicenseUrl());
		rspAllEnterprise.setStatus(enterprise.getStatus());
		rspAllEnterprise.setId(enterprise.getId());//企业ID
		rspAllEnterprise.setName(enterprise.getName());//企业名字
		rspAllEnterprise.setAddress(enterprise.getAddress());//企业地址
		rspAllEnterprise.setPhone(enterprise.getPhone());//企业电话
		rspAllEnterprise.setManager_name(enterprise.getManagerName());//管理员名字
		rspAllEnterprise.setManager_phone(enterprise.getManagerPhone());//管理员电话
		rspAllEnterprise.setSale_card_num(enterprise.getSaleCardNum());//名片数量
		rspAllEnterprise.setCreate_time(enterprise.getCreateTime());//创建时间
		rspAllEnterprise.setExpire_time(enterprise.getExpireTime());//过期时间
		rspAllEnterprise.setAuth_soft(enterprise.getAuthSoft());//小程序
		rspAllEnterprise.setAuth_wei(enterprise.getAuthWei());//企业微信
		rspAllEnterprise.setAddress(enterprise.getAddress());
		rspAllEnterprise.setHead_icon(enterprise.getHeadIcon());
		return rspAllEnterprise;
	}

	/**
	 * 修改企业信息
	 *
	 * @param reqUpdateEnterprise
	 */
	@Override
	public void updateEnterprise(ReqUpdateEnterprise reqUpdateEnterprise) {
		Enterprise enterpriseaById = enterpriseMapper.selectByPrimaryKey(reqUpdateEnterprise.getId());
		Enterprise enterpriseByManager_phone = enterpriseMapper.selectByManagerPhone(reqUpdateEnterprise.getManager_phone());
		if (enterpriseaById.getManagerPhone().equals(reqUpdateEnterprise.getManager_phone())) {
			Enterprise enterprise = new Enterprise();
			enterprise.setId(reqUpdateEnterprise.getId());
			enterprise.setStatus(reqUpdateEnterprise.getStatus());
			enterprise.setName(reqUpdateEnterprise.getName());
			enterprise.setAddress(reqUpdateEnterprise.getAddress());
			enterprise.setPhone(reqUpdateEnterprise.getPhone());
			enterprise.setBusinessLicenseCode(reqUpdateEnterprise.getBusiness_license_code());
//      enterprise.setExpiryTime(reqUpdateEnterprise.getExpiry_time());
			enterprise.setSaleCardNum(reqUpdateEnterprise.getSale_card_num());
			enterprise.setManagerName(reqUpdateEnterprise.getManager_name());
			enterprise.setManagerPhone(reqUpdateEnterprise.getManager_phone());
			enterprise.setExpireTime(reqUpdateEnterprise.getExpire_time());
			enterprise.setAuthSoft(reqUpdateEnterprise.getAuth_soft());//小程序
			enterprise.setAuthWei(reqUpdateEnterprise.getAuth_wei());//企业微信
			enterprise.setModifyTime(new Date());
			enterprise.setIcon(reqUpdateEnterprise.getHead_icon());
			enterpriseMapper.updateByPrimaryKeySelective(enterprise);
		} else if (enterpriseByManager_phone == null) {
			Enterprise enterprise = new Enterprise();
			enterprise.setId(reqUpdateEnterprise.getId());
			enterprise.setStatus(reqUpdateEnterprise.getStatus());
			enterprise.setName(reqUpdateEnterprise.getName());
			enterprise.setAddress(reqUpdateEnterprise.getAddress());
			enterprise.setPhone(reqUpdateEnterprise.getPhone());
			enterprise.setBusinessLicenseCode(reqUpdateEnterprise.getBusiness_license_code());
//      enterprise.setExpiryTime(reqUpdateEnterprise.getExpiry_time());
			enterprise.setSaleCardNum(reqUpdateEnterprise.getSale_card_num());
			enterprise.setManagerName(reqUpdateEnterprise.getManager_name());
			enterprise.setManagerPhone(reqUpdateEnterprise.getManager_phone());
			enterprise.setExpireTime(reqUpdateEnterprise.getExpire_time());
			enterprise.setAuthSoft(reqUpdateEnterprise.getAuth_soft());//小程序
			enterprise.setAuthWei(reqUpdateEnterprise.getAuth_wei());//企业微信
			enterprise.setModifyTime(new Date());
			enterprise.setIcon(reqUpdateEnterprise.getHead_icon());
			enterpriseMapper.updateByPrimaryKeySelective(enterprise);
		} else {
			throw new BusinessException(ErrCode.NO_PHONE_EXIST, "该账号已被注册");
		}

	}

	/**
	 * 添加企业信息
	 *
	 * @param reqAddEnterprise
	 */
	@Override
	@Transactional
	public void addEnterprise(ReqAddEnterprise reqAddEnterprise, String id) {
		Enterprise enterprises = enterpriseMapper.selectByManagerPhone(reqAddEnterprise.getManager_phone());
		if (enterprises == null) {
			Department department = new Department();
			department.setId("1");
			department.setCreateTime(new Date());
			department.setEnterpriseId(UuidUtil.getRandomUuid());
			department.setName(reqAddEnterprise.getName());
			department.setParentId("0");
			department.setOrder(1);
			departmentMapper.insertSelective(department);
			Enterprise enterprise = new Enterprise();
			enterprise.setCreateStaffId(id);
			enterprise.setId(department.getEnterpriseId());
			enterprise.setName(reqAddEnterprise.getName());
			enterprise.setAddress(reqAddEnterprise.getAddress());
			enterprise.setPhone(reqAddEnterprise.getPhone());
			enterprise.setBusinessLicenseCode(reqAddEnterprise.getBusiness_license_code());
			enterprise.setBusinessLicenseUrl(reqAddEnterprise.getBusiness_license_url());//照片地址
			enterprise.setExpireTime(reqAddEnterprise.getExpire_time());
			enterprise.setSaleCardNum(reqAddEnterprise.getSale_card_num());
			enterprise.setManagerName(reqAddEnterprise.getManager_name());
			enterprise.setManagerPhone(reqAddEnterprise.getManager_phone());
			enterprise.setCreateTime(new Date());
			enterprise.setIcon(reqAddEnterprise.getIcon());//q企业头像

			Deploy deploy = new Deploy();
			deploy.setId(UuidUtil.getRandomUuid());
			deploy.setEnterpriseId(enterprise.getId());
			deployMapper.insertSelective(deploy);


			String phone = reqAddEnterprise.getManager_phone();
			String passwrod = String.valueOf(System.currentTimeMillis()).substring(0, 6);
			String url = "http://112.90.92.219:8811/sms.aspx?action=send&userid=1003&account=chenzui&password=chenzui123&mobile=" + phone + "&content=" + createContent(phone, passwrod, enterprise.getName()) + "&sendTime=&extno=";
			HttpUtil.sendGet(url);
			enterprise.setPassword(passwrod);
			enterpriseMapper.insertSelective(enterprise);


		} else {
			throw new BusinessException(ErrCode.NO_PHONE_EXIST, "账号已被注册");
		}


	}

	@Transactional
	@Override
	public void updateEnterseAllId(ReqEnterpriseAllId reqEnterpriseAllId) {
//        Enterprise enterprise = new Enterprise();
//        enterprise.setId(reqEnterpriseAllId.getId());//企业id
//        enterprise.setCorpid(reqEnterpriseAllId.getCorp_id());//公司的CropID
//        enterprise.setContactssecret(reqEnterpriseAllId.getContacts_secret());//通信录密钥
//        enterprise.setBosssecret(reqEnterpriseAllId.getBoss_secret());//boss雷达密钥
//        enterprise.setAisecret(reqEnterpriseAllId.getAi_secret());//ai雷达密钥
//        enterprise.setBossAgentid(reqEnterpriseAllId.getBoss_agent_id());//boss雷达应用id
//        enterprise.setAiAgentid(reqEnterpriseAllId.getAi_agent_id());//ai雷达应用id
//        enterprise.setAppId(reqEnterpriseAllId.getApp_id());//小程序id
//        enterprise.setSecret(reqEnterpriseAllId.getSecret());//小程序密钥
//        enterprise.setMchId(reqEnterpriseAllId.getMch_id());//商户id
//        enterprise.setPayKey(reqEnterpriseAllId.getPay_key());//支付密钥
//        enterpriseMapper.updateByPrimaryKeySelective(enterprise);
		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);

		//记录信息到客迹服务器，方便微擎部署项目
		//A by PLH at 2018-12-01 for 暂时不用集群，代码暂时注册掉，待启用集群机制后再来修改
		//String url = "https://keji-api.h5h5h5.cn/push/V1.0/common/deploy/info?appid="+reqEnterpriseAllId.getApp_id()+"&url="+sysConfig.getBranch()+"&img_url="+sysConfig.getQiniuUrl();
		//HttpUtil.httpGetReq(url).getResponse_str();

		Deploy d = deployMapper.selectAll(reqEnterpriseAllId.getId());//根据企业id查询deploy表，判断是否为空
		Deploy deploy = new Deploy();
		deploy.setCorpid(reqEnterpriseAllId.getCorp_id());//公司的CropID
		deploy.setContactssecret(reqEnterpriseAllId.getContacts_secret());//通信录密钥
		deploy.setBosssecret(reqEnterpriseAllId.getBoss_secret());//boss雷达密钥
		deploy.setAisecret(reqEnterpriseAllId.getAi_secret());//ai雷达密钥
		deploy.setBossAgentid(reqEnterpriseAllId.getBoss_agent_id());//boss雷达应用id
		deploy.setAiAgentid(reqEnterpriseAllId.getAi_agent_id());//ai雷达应用id
		deploy.setAppId(reqEnterpriseAllId.getApp_id());//小程序id
		deploy.setSecret(reqEnterpriseAllId.getSecret());//小程序密钥
		deploy.setMchId(reqEnterpriseAllId.getMch_id());//商户id
		deploy.setPayKey(reqEnterpriseAllId.getPay_key());//支付密钥
		deploy.setMessageTemplate(reqEnterpriseAllId.getMessage_template());//留言回复通知
		deploy.setPayTemplate(reqEnterpriseAllId.getPay_template());//支付成功通知
		deploy.setPublicKey(sysConfig.getImPublick());
		deploy.setPrivateKey(sysConfig.getImPrivate());
		deploy.setSkdAppId(sysConfig.getImSdkApp());
		deploy.setManagerId("admin");
		deploy.setEnterpriseId(reqEnterpriseAllId.getId());
		if (d != null) {
			deploy.setId(d.getId());//id
			deployMapper.updateByPrimaryKeySelective(deploy);
		} else {
			deploy.setId(UuidUtil.getRandomUuid());//id
			deployMapper.insertSelective(deploy);

		}

		Enterprise e = enterpriseMapper.selectByPrimaryKey(reqEnterpriseAllId.getId());
		Staff staff = new Staff();
		List<ReqAddWeiChatUser> weiChatUsers = new ArrayList<>();
		if (reqEnterpriseAllId.isPlatform() == true) {
			//A by lql at 2018-10-23 for 平台模式小程新增企业，自动给该企业在平台企业微信中增加一个顶级部门 Begin

			//A by lql at 2018-10-23 for 平台模式小程新增企业，自动给该企业在平台企业微信中增加一个顶级部门 End
		} else {
			// 非小程序
			weiChatUsers = staffInfoService.wechatUsers(e.getId());

			for (ReqAddWeiChatUser user : weiChatUsers) {
				//Staff s = staffInfoMapper.selectByPrimaryKey(user.getUserid());
				Staff s = staffInfoMapper.selectStaffById(user.getUserid(), reqEnterpriseAllId.getId());
				if (s == null) {
					staff.setId(UuidUtil.getRandomUuidWithoutSeparator());
					staff.setWxId(user.getUserid());
					staff.setName(user.getName());
					staff.setPhone(user.getPhone());
					staff.setCreateTime(new Date());
					staff.setStatus(1);
					staff.setCreateStaffId(e.getId());
					staff.setDepartmentId(user.getDepartment());
					staff.setStation("");
					staff.setHeadIcon(user.getHead_icon());
					staff.setEnterpriseId(e.getId());
					staff.setMail(user.getMail());
					String code = null;

					try {
						code = smallSoftApi.code(staff.getId(), staff.getDepartmentId(), staff.getEnterpriseId(), deploy.getAppId(), deploy.getSecret());
					} catch (IOException e1) {
						e1.printStackTrace();
						code = "";
					} finally {
						staff.setSoftImgUrl(code);
						staffInfoMapper.insertSelective(staff);
						//注册腾讯im
						tlsUtil.addUser(staff.getId(), staff.getName(), staff.getEnterpriseId());
					}
				}
			}
		}
	}

	/**
	 * @param id
	 * @return
	 */
	@Override
	public RspEnterpriseAllId selectEnterpriseId(String id) {
		RspEnterpriseAllId rspEnterpriseAllId = enterpriseMapper.selectEnterpriseId(id);
		if (rspEnterpriseAllId != null) {
//            String ai_uil="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+rspEnterpriseAllId.getCorp_id()+"&redirect_uri=http://sale-v2-dev.h5h5h5.cn/ai?enterpriseid="+id+"&response_type=code&scope=snsapi_base&agentid="+rspEnterpriseAllId.getAi_agent_id()+"&state=STATE#wechat_redirect";
//            String boss_uil="https://open.weixin.qq.com/connect/oauth2/authorize?appid=\"+rspEnterpriseAllId.getCorp_id()+\"&redirect_uri=http://sale-v2-dev.h5h5h5.cn/boss?enterpriseid=\"+id+\"&response_type=code&scope=snsapi_base&agentid=\"+rspEnterpriseAllId.getAi_agent_id()+\"&state=STATE#wechat_redirect";

			String ai_uil = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + rspEnterpriseAllId.getCorp_id() + "&redirect_uri=http://sale.deecard.szrenzhi.com/ai?enterpriseid=" + id + "&response_type=code&scope=snsapi_base&agentid=" + rspEnterpriseAllId.getAi_agent_id() + "&state=STATE#wechat_redirect";
			String boss_uil = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=\"+rspEnterpriseAllId.getCorp_id()+\"&redirect_uri=http://sale.deecard.szrenzhi.com/boss?enterpriseid=\"+id+\"&response_type=code&scope=snsapi_base&agentid=\"+rspEnterpriseAllId.getAi_agent_id()+\"&state=STATE#wechat_redirect";

			rspEnterpriseAllId.setAi_uil(ai_uil);
			rspEnterpriseAllId.setBoss_uil(boss_uil);
		}

		return rspEnterpriseAllId;
	}

	/**
	 * 列表
	 *
	 * @param adminId
	 * @return
	 */
	@Override
	public RspPage<RspEnterpriseAllId> selectEnterpriseIdList(String adminId) {
		return null;
	}

	private String createContent(String phone, String password, String name) {
		//String code = String.valueOf(System.currentTimeMillis()).substring(0,6);
		UserDataUtil.setMsgCode(phone, password);
		//return "【客迹】您的登录密码为:"+password;
		return "【" + name + "】您的登录密码为:" + password;
	}

	/**
	 * 获取腾讯Im数据对象
	 *
	 * @param enterprise_id
	 * @return
	 */
	public RspIm findIm(String enterprise_id) {

		return enterpriseMapper.findIm(enterprise_id);

	}

	@Override
	public RspPage<RspEnterList> enterList(ReqEnterList reqEnterList, HttpServletRequest request) {
		enterpriseMapper.proChildEnterpriseId(reqEnterList.getParent_id());
		RspPage<RspEnterList> rspPage = new RspPage<RspEnterList>();
		com.github.pagehelper.Page page = PageHelper.startPage(reqEnterList.getPage_num(), reqEnterList.getPage_size(), true);
		List<RspEnterList> list = enterpriseMapper.enterList(reqEnterList);
		for (RspEnterList enterInfo : list) {
			if (UserDataUtil.getCustomId(request) != null && UserDataUtil.getCustomId(request).equals(enterInfo.getParent_id())) {
				enterInfo.setIs_power(1);
			} else {
				enterInfo.setIs_power(0);
			}
		}

		rspPage.setData_list(list);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		return rspPage;
	}

	@Override
	public RspPage<RspEnterDetailList> enterDetailList(ReqEnterList reqEnterList) {
		enterpriseMapper.proChildEnterpriseId(reqEnterList.getParent_id());
		RspPage<RspEnterDetailList> rspPage = new RspPage<RspEnterDetailList>();
		com.github.pagehelper.Page page = PageHelper.startPage(reqEnterList.getPage_num(), reqEnterList.getPage_size(), true);
		List<RspEnterDetailList> list = enterpriseMapper.enterDetailList(reqEnterList);
		rspPage.setData_list(list);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		return rspPage;
	}

	@Override
	public RspPage<RspEnterpariseList> enterpariseList(ReqEnterList reqEnterList) {
		enterpriseMapper.proChildEnterpriseId(reqEnterList.getParent_id());
		RspPage<RspEnterpariseList> rspPage = new RspPage<RspEnterpariseList>();
		com.github.pagehelper.Page page = PageHelper.startPage(reqEnterList.getPage_num(), reqEnterList.getPage_size(), true);
		List<RspEnterpariseList> list = enterpriseMapper.enterpriseList(reqEnterList);
		rspPage.setData_list(list);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		return rspPage;
	}

	@Override
	public RspOemInfo oemInfo(String id) {
		RspOemInfo oemInfo = new RspOemInfo();
		oemInfo = enterpriseMapper.oemInfo(id);

		if (oemInfo != null) {
			oemInfo.setArea_count(enterpriseMapper.areacount(id));
			oemInfo.setAgent_count(enterpriseMapper.agentcount(id));

			oemInfo.setProvince_name(enterpriseMapper.provinceName(oemInfo.getProvince_code()));
			oemInfo.setCity_name(enterpriseMapper.cityName(oemInfo.getProvince_code(), oemInfo.getCity_code()));
			oemInfo.setArea_name(enterpriseMapper.areaName(oemInfo.getProvince_code(), oemInfo.getCity_code(), oemInfo.getArea_code()));
		}
		return oemInfo;

	}

	@Override
	public RspAreaInfo areaInfo(String id) {
		RspAreaInfo areaInfo = new RspAreaInfo();
		areaInfo = enterpriseMapper.areaInfo(id);
		if (areaInfo != null) {
			areaInfo.setAgent_count(enterpriseMapper.agentcount(id));
			areaInfo.setProvince_name(enterpriseMapper.provinceName(areaInfo.getProvince_code()));
			areaInfo.setCity_name(enterpriseMapper.cityName(areaInfo.getProvince_code(), areaInfo.getCity_code()));
			areaInfo.setArea_name(enterpriseMapper.areaName(areaInfo.getProvince_code(), areaInfo.getCity_code(), areaInfo.getArea_code()));
		}
		return areaInfo;
	}

	@Override
	public RspAgentInfo agentInfo(String id, HttpServletRequest request) {
		RspAgentInfo enterInfo = new RspAgentInfo();

		enterInfo = enterpriseMapper.agentInfo(id);
		if (UserDataUtil.getCustomId(request).equals(enterInfo.getParent_id())) {
			enterInfo.setIs_power(1);
		} else {
			enterInfo.setIs_power(0);
		}
		if (enterInfo != null) {
			enterInfo.setEnterprise_count(enterpriseMapper.entercount(id));
			enterInfo.setProvince_name(enterpriseMapper.provinceName(enterInfo.getProvince_code()));
			enterInfo.setCity_name(enterpriseMapper.cityName(enterInfo.getProvince_code(), enterInfo.getCity_code()));
			enterInfo.setArea_name(enterpriseMapper.areaName(enterInfo.getProvince_code(), enterInfo.getCity_code(), enterInfo.getArea_code()));
		}
		return enterInfo;
	}

	@Override
	public RspEnterInfo enterInfo(String id) {
		RspEnterInfo enterInfo = enterpriseMapper.enterInfo(id);

		enterInfo.setProvince_name(enterpriseMapper.provinceName(enterInfo.getProvince_code()));
		enterInfo.setCity_name(enterpriseMapper.cityName(enterInfo.getProvince_code(), enterInfo.getCity_code()));
		enterInfo.setArea_name(enterpriseMapper.areaName(enterInfo.getProvince_code(), enterInfo.getCity_code(), enterInfo.getArea_code()));
		return enterInfo;
	}

	@Override
	@Transactional
	public void addEnterAndAdmin(com.vma.push.business.dto.req.superback.ReqAddEnterprise reqEnterList) {
		Admin admin = new Admin();
		admin.setLogin(reqEnterList.getLogin());
		admin.setType(reqEnterList.getType());
		admin.setRole(1);
		//判断账号是否存在(非微信创建企业则要验证重复账号)
		if (reqEnterList.getCreate_scene() != 1 && adminMapper.isExist(admin) > 0) {
			throw new BusinessException(ErrCode.NO_PHONE_EXIST, "账号已被注册");
		} else {
			//添加企业
			Enterprise enterprise = new Enterprise();
			enterprise.setId(reqEnterList.getId()); //企业id
			enterprise.setIsDeploy(0); //是否部署  默认未部署
			enterprise.setTemplateId(reqEnterList.getTemplate_id()); //模板id
			enterprise.setHeadIcon(sysConfigMapper.selectByPrimaryKey(1).getImgUrl()+"/dc_me_ent_logo.png");
			enterprise.setName(reqEnterList.getName()); //企业名称
			enterprise.setPhone(reqEnterList.getPhone()); //电话
			enterprise.setManagerPhone(reqEnterList.getPhone());//管理员电话
			enterprise.setMoneyInit(reqEnterList.getMoney_init());//初始迹点
			enterprise.setMoneySum(reqEnterList.getMoney_init());//累计迹点/名片
			enterprise.setMoneyLeave(reqEnterList.getMoney_init());//剩余迹点
			enterprise.setCreateStaffId(reqEnterList.getParent_id());//创建人
			enterprise.setProvinceCode(reqEnterList.getProvince_code());//省
			enterprise.setCityCode(reqEnterList.getCity_code());//市
			enterprise.setAreaCode(reqEnterList.getArea_code());//区
			// a by lql for 迎合小程序的三级联动
			if (reqEnterList.getCreate_scene() == 1) {
				String[] code = this.getProvinceCityAreaCode(reqEnterList.getProvince_code(), reqEnterList.getCity_code(), reqEnterList.getArea_code());
				enterprise.setProvinceCode(code[0]);//省
				enterprise.setCityCode(code[1]);//市
				enterprise.setAreaCode(code[2]);//区
				enterprise.setManagerName(reqEnterList.getPeople_name());// 管理员名字
			}

			// A by PLH at 2018-11-27 for 平台模式创建的企业默认迹点数为 10(10个名片)
			if (reqEnterList.getCreate_scene() == 1) {
				enterprise.setMoneyInit(10);		// 初始迹点
				enterprise.setMoneySum(10);			// 累计迹点/名片
				enterprise.setMoneyLeave(9);		// 剩余迹点
				enterprise.setCardSum(1);			// 设置累计名片数量
				enterprise.setSaleCardNum(1);		// 设置名片数量
				enterprise.setUsedCarNum(1);		// 已经使用的名片数量
			}
			enterprise.setAddress(reqEnterList.getAddress());//地址
			enterprise.setRemark(reqEnterList.getRemark());//备注
			enterprise.setCreateTime(new Date());//创建时间
			enterprise.setEnterpriseNo(reqEnterList.getEnterprise_no());//企业编号
			enterprise.setParentId(StringUtils.isEmpty(reqEnterList.getParent_id()) ? "0" : reqEnterList.getParent_id());//上级
			enterprise.setRole(reqEnterList.getRole());//角色
			enterprise.setExpireTime(reqEnterList.getExpire_time());
			Enterprise parentEnterprise = enterpriseMapper.selectByPrimaryKey(reqEnterList.getParent_id());
			//通过模板id获取 授权企业价格以及单个名片的价格
			ShopTemplate shopTemplate = shopTemplateMapper.selectByCode(reqEnterList.getTemplate_id());

			if (parentEnterprise != null && !"".equals(parentEnterprise)) { //parentEnterprise 是null代表是超级后台  不用扣迹点
				if (parentEnterprise.getRole() != 2) {//角色不是代理商
					if (parentEnterprise.getMoneyLeave() < reqEnterList.getMoney_init() && !reqEnterList.getParent_id().equals("0")) {
						throw new BusinessException(ErrCode.NO_PHONE_EXIST, "迹点不足");
					} else {
						parentEnterprise.setMoneyLeave(parentEnterprise.getMoneyLeave() - reqEnterList.getMoney_init());
					}
				} else { //角色是代理商 扣除对应模板的费用以及名片费用
					if (parentEnterprise.getMoneyLeave() < shopTemplate.getSmallCost() + reqEnterList.getMoney_init() * shopTemplate.getCardCost() && !reqEnterList.getParent_id().equals("0")) {
						throw new BusinessException(ErrCode.NO_PHONE_EXIST, "迹点不足");
					} else {
						parentEnterprise.setMoneyLeave(parentEnterprise.getMoneyLeave() - shopTemplate.getSmallCost() - reqEnterList.getMoney_init() * shopTemplate.getCardCost());
					}
				}
			}

			if (enterpriseMapper.insertSelective(enterprise) > 0) { //添加企业成功后操作
				if (parentEnterprise != null && !"".equals(parentEnterprise)) {
					//更新上级的迹点数
					enterpriseMapper.updateByPrimaryKeySelective(parentEnterprise);
					//当新增企业的时候 往部门表插一条数据
					if (parentEnterprise.getRole() == 2) {
						Department department = new Department();
						department.setId("1");
						department.setCreateTime(new Date());
						department.setEnterpriseId(enterprise.getId());
						department.setName(reqEnterList.getName());
						department.setParentId("0");
						department.setOrder(1);
						departmentMapper.insertSelective(department);
					} else if (reqEnterList.getCreate_scene() == 1) {
						//A by lql at 2018-10-13 10:00 for 平台模式小程序新增企业, Begin
						// 1、将系统的配置复制一份到企业, 调用企业配置的接口
						Deploy platformDeploy = deployMapper.selectAll(reqEnterList.getParent_id());
						ReqEnterpriseAllId reqEnterpriseAllId = new ReqEnterpriseAllId();
						reqEnterpriseAllId.setAi_agent_id(platformDeploy.getAiAgentid());
						reqEnterpriseAllId.setAi_secret(platformDeploy.getAisecret());
						reqEnterpriseAllId.setApp_id(platformDeploy.getAppId());
						reqEnterpriseAllId.setBoss_agent_id(platformDeploy.getBossAgentid());
						reqEnterpriseAllId.setBoss_secret(platformDeploy.getBosssecret());
						reqEnterpriseAllId.setContacts_secret(platformDeploy.getContactssecret());
						reqEnterpriseAllId.setCorp_id(platformDeploy.getCorpid());
						reqEnterpriseAllId.setMch_id(platformDeploy.getMchId());
						reqEnterpriseAllId.setMessage_template(platformDeploy.getMessageTemplate());
						reqEnterpriseAllId.setPay_key(platformDeploy.getPayKey());
						reqEnterpriseAllId.setPay_template(platformDeploy.getPayTemplate());
						reqEnterpriseAllId.setId(reqEnterList.getId());
						reqEnterpriseAllId.setSecret(platformDeploy.getSecret());
						reqEnterpriseAllId.setPlatform(true);
						this.updateEnterseAllId(reqEnterpriseAllId);

						// 本地数据库：创建部门
						// int maxId = departmentMapper.getMaxIdByEnterpriseWxId(reqEnterpriseAllId.getCorp_id(), platformDeploy.getEnterpriseId());
						int maxDeptId = indexCreate.getDeptIdIndex();
						Department department = new Department();
						department.setCreateTime(new Date());
						department.setEnterpriseId(enterprise.getId());
						//department.setId(Integer.toString(maxId + 1));
						department.setId(Integer.toString(maxDeptId));
						department.setName(reqEnterList.getName());
						department.setParentId("0");
						department.setOrder(1);
						departmentMapper.insertSelective(department);

						// 2、企业微信: 创建一个部门         不增加部门，统一在企业微信牧歌目录下增加员工
						WechatDepartment enterpriseWxDept = new WechatDepartment();
						enterpriseWxDept.setName(reqEnterList.getEnterprise_no() + "-" + reqEnterList.getName());
						enterpriseWxDept.setParentid("1"); 		// 父id为平台根目录1
						enterpriseWxDept.setId(department.getId());
						// 调用企业微信接口加入部门
						String result = departmentService.insertApi(enterpriseWxDept, reqEnterList.getParent_id());
						int errCode = new JSONObject(result).getInt("errcode");
						if (errCode != 0) {
							logger.info( "创建企业失败, errCode="+errCode + " by id = " + enterpriseWxDept.getId() + "  Corp_id = " + reqEnterpriseAllId.getCorp_id() + "  EnterpriseId = " +  platformDeploy.getEnterpriseId());
							throw new BusinessException(errCode, "创建企业失败, errCode="+errCode);
						}

						// 3、企业微信: 增加员工
						ReqAddStaffInfo reqAddStaffInfo = new ReqAddStaffInfo();
						reqAddStaffInfo.setEnterprise_id(enterprise.getId());
						reqAddStaffInfo.setDepartment_id(Integer.parseInt(sysConfigMapper.selectByPrimaryKey(1).getPlatformDefaultDeptId()));
						reqAddStaffInfo.setName(reqEnterList.getPeople_name());
						reqAddStaffInfo.setPhone(reqEnterList.getPhone());
						reqAddStaffInfo.setIsPlatform(1); // 平台模式 用于区分生成限量的二维码还是不限量的二维码

						try {
							// 在企业微信添加人员成功，在staff里面完善记录
							Staff staff = new Staff();
							//staff.setId(UuidUtil.getRandomUuidWithoutSeparator());
							staff.setEnterpriseId(reqEnterList.getId());
							staff.setWeixin(reqEnterList.getWeixin());                   // 微信号
							staff.setMail(reqEnterList.getEmail());                      // 邮箱
							staff.setAddress(reqEnterList.getAddress());                 // 地址
							//staff.setWxId(UuidUtil.getRandomUuidWithoutSeparator());
							staff.setStation(reqEnterList.getPosition());
							staff.setOpenid(reqEnterList.getOpenid());
							staff.setSignature(reqEnterList.getSelf_introduction());    // 个人简介
							staff.setRole("0");                                         // 系统角色-- 此时默认为管理员 0管理员 2运营者 3员工
							staff.setCreateTime(new Date());                            // 创建时间
							staff.setStatus(1);                                         // 状态 1正常 0暂停 2解散
							staff.setPhone(reqEnterList.getPhone());
							staff.setCreateStaffId(enterprise.getParentId());           //创建人的企业id
							staff.setTemlateId(1);
							staff.setDepartmentId(department.getId());
							staff.setName(reqEnterList.getPeople_name());
							staff.setFirstLetter(ChineseFCUtil.cn2py(reqEnterList.getPeople_name())); // 获取名字首字母
							staff.setOpenAi(1);			//默认开通AI雷达（普通员工是否要开通待定）
							staff.setOpenBoss(1);		//默认开通BOSS雷达（普通员工是否要开通待定）

							String headIcon = "";
							if (StringUtils.isEmpty(reqEnterList.getHead_icon()) == true) {
								headIcon = sysConfigMapper.selectByPrimaryKey(1).getImgUrl()+ "/dc_user_default_logo.png";
							} else {
								headIcon = reqEnterList.getHead_icon();
							}
							staff.setHeadIcon(headIcon);

							// Add by PLH at 2018-11-01 for 平台模式下, 手机号可以重复，(每个企业微信只能有一个企业微信用户)
							// 该手机号在这个企业微信下是否已经存在员工
							String isExistStaff = staffInfoMapper.isExistStaffByEntWxAndPhone(reqEnterpriseAllId.getCorp_id(), reqEnterList.getPhone());
							RspStaffId staffId = null;
							if (StringUtils.isEmpty(isExistStaff) == true || isExistStaff.equals("1") == false) {
								//本地数据库中还没有这个员工
								staffId = staffInfoService.addStaff(reqAddStaffInfo, reqEnterList.getParent_id(), true);
							} else {
								//本地数据库中已经有这个员工了
								staffId = staffInfoService.addStaff(reqAddStaffInfo, reqEnterList.getParent_id(), false);
							}

							//Add by PLH at 2018-11-28 for 一个企业微信用户对应多个名片(多个部门) Begin
							//同时要设置这个员工多个部门权限
							List<String> QYWXDeptIdList = staffInfoMapper.getWxDeptIdsByPhone(reqEnterpriseAllId.getCorp_id(), staff.getPhone());
							int[] department_Ids = new int[QYWXDeptIdList.size()+1];
							for(int i = 0; i < QYWXDeptIdList.size(); i++) {
								String loopQYWXID = QYWXDeptIdList.get(i);
								department_Ids[i] = Integer.parseInt(loopQYWXID);
							}
							// 补上新部门的权限
							department_Ids[QYWXDeptIdList.size()] =  Integer.parseInt(department.getId());

							ReqUpdateStaffInfo reqUpdateStaffInfo = new ReqUpdateStaffInfo();
							reqUpdateStaffInfo.setPhone(reqAddStaffInfo.getPhone());
							reqUpdateStaffInfo.setDepartment_id(department_Ids);
							reqUpdateStaffInfo.setName(reqAddStaffInfo.getName());
							reqUpdateStaffInfo.setWx_id(staffId.getWx_id());

							Employee employee = new Employee(reqUpdateStaffInfo);
							int QYWX_RETCODE = new JSONObject(staffInfoService.updateApi(employee, reqEnterList.getParent_id())).getInt("errcode");
							if(QYWX_RETCODE != 0) {
								throw new BusinessException(ErrCode.LOSE_NUM, "微信错误: " + QYWX_RETCODE);
							}
							//Add by PLH at 2018-11-28 for 一个企业微信用户对应多个名片(多个部门) End

							// 补全staffInfoService.addStaff中insert staff缺少的字段
							staff.setId(staffId.getStaff_id());
							staff.setWxId(staffId.getWx_id());

							staffInfoMapper.updateByPrimaryKeySelective(staff);

							// 更新enterprise表中创建人id
							enterprise.setCreateStaffId(staff.getId());
							enterpriseMapper.updateByPrimaryKeySelective(enterprise);

							// 添加user_staff_rela关系 -- 自己微信用户和自己名片
							UserInfo userInfo = userInfoMapper.selectByOpenId(reqEnterList.getOpenid());
							UserStaffRela userStaffRela = new UserStaffRela();
							userStaffRela.setId(UuidUtil.getRandomUuid());
							userStaffRela.setCreateTime(new Date());
							userStaffRela.setEnterpriseId(reqEnterList.getId());//添加企业ID
							userStaffRela.setModifyTime(new Date());
							userStaffRela.setFroms(0);//名片来源
							userStaffRela.setStaffId(staff.getId());//员工id
							userStaffRela.setUserId(userInfo.getId());//用户id
							userStaffRela.setDepartmentId(staff.getDepartmentId());
							userStaffRelaMapper.insertSelective(userStaffRela);

							//  在user-info中添加last_staff_id
							userInfo.setLastStaffId(staff.getId());
							//Add by PLH at 2018-11-27 06:40 for 设置用户最后一个企业为新创建的企业
							userInfo.setLastEnterpriseId(enterprise.getId());
							
							userInfoMapper.updateByPrimaryKeySelective(userInfo);

							// Add BY PLH at 2018-11-29 for 深卡小蜜的名片夹也要加入这个新用户的名片 Begin
							// 其实前面getDefaultInfo中已经推送一个小秘给他了，这里没必要再做了
							List<UserStaffRela> xiaoMiStaffRelaList = userStaffRelaMapper.getUserRelaListByUserId(userInfo.getId());
							if(xiaoMiStaffRelaList != null && xiaoMiStaffRelaList.size() > 0) {
								//关系里面查询看是否有平台的企业名片（已经关联过小秘了）,就不要再重复推荐小秘的名片给他了
								UserStaffRela xiaoMiRela = null;
								for(UserStaffRela platformStaff : xiaoMiStaffRelaList) {
									//这个微信用户第一次建企业，才会给他推小秘给他，之后就不会再推小秘给他了
									if(PlatformEnterprise.ID.equals(platformStaff.getEnterpriseId()) == true) {
										xiaoMiRela = platformStaff;
										break;
									}
								}
								if(xiaoMiRela != null) {
									// 如果有多个的话，选择第一个就好
									Staff xiaoMiStaff = staffInfoMapper.selectByPrimaryKey(xiaoMiRela.getStaffId());
									UserInfo xiaoMiUserInfo = userInfoMapper.selectByOpenId(xiaoMiStaff.getOpenid());

									UserStaffRela xiaoMiStaffRela = new UserStaffRela();
									xiaoMiStaffRela.setId(UuidUtil.getRandomUuid());
									xiaoMiStaffRela.setUserId(xiaoMiUserInfo.getId());		//小秘微信用户id
									xiaoMiStaffRela.setEnterpriseId(enterprise.getId());	//添加企业ID--新企业的ID
									xiaoMiStaffRela.setDepartmentId(department.getId());
									xiaoMiStaffRela.setStaffId(staff.getId());				//员工id
									xiaoMiStaffRela.setCreateTime(new Date());
									xiaoMiStaffRela.setModifyTime(new Date());
									xiaoMiStaffRela.setFroms(4);//名片来源
									userStaffRelaMapper.insertSelective(xiaoMiStaffRela);
								}
							}
							// Add BY PLH at 2018-11-29 for 深卡小蜜的名片夹也要加入这个新用户的名片 End
						} catch (IOException e) {
							e.printStackTrace();
							System.out.println("----------------------------------" + e.getMessage());
						}
						//A by lql at 2018-10-13 10:00 for 平台模式小程序新增企业, End
					}
				}
				//添加账号密码
				admin.setId(UuidUtil.getRandomUuid());
				admin.setCreateId(enterprise.getParentId());
				admin.setCreateTime(new Date());
				admin.setName(enterprise.getName());
				admin.setPassWord(reqEnterList.getPassword());
				admin.setPhone(reqEnterList.getPhone());
				admin.setCustomId(reqEnterList.getId());
				admin.setStatus(1);
				// V2新增
				if (reqEnterList.getCreate_scene() == 1) {
					admin.setType(5);
				}
				admin.setOpenid(reqEnterList.getOpenid());

				//Add by PLH at 2018-11-27 for 20181127000007-创建企业_默认用手机号创建后台账号 begin
				if (reqEnterList.getCreate_scene() == 1) {
					// 判断该手机号是否修改过密码 add by lql
					List<Admin> adminList = adminMapper.selectAdminListByPhone(reqEnterList.getPhone());
					String passWord = "";
					if(adminList.size() > 0){
						// 如果有账号，取它的密码
						passWord = adminList.get(0).getPassWord();
					} else {
						// 没有账号，用手机号做密码
						passWord = admin.getPhone();
					}
					admin.setLogin(admin.getPhone());
					admin.setPassWord(passWord);
				}
				//Add by PLH at 2018-11-27 for 20181127000007-创建企业_默认用手机号创建后台账号 End
				adminMapper.insertSelective(admin);

				//为用户增加菜单权限  默认全加
				ReqUserMenu reqUserMenu = new ReqUserMenu();
				reqUserMenu.setType(admin.getType());
				reqUserMenu.setUser_id(admin.getId());
				adminMapper.setMenuRole(reqUserMenu);

				//添加迹点操作记录
				KjPointLog kjPointLog = new KjPointLog();
				kjPointLog.setCreateBy(reqEnterList.getCreate_by());//操作人
				kjPointLog.setOperation("1");//操作绩点加、减
				kjPointLog.setCreateTime(new Date());//操作时间3
				kjPointLog.setCustomId(reqEnterList.getId());//企业id
				kjPointLog.setId(UuidUtil.getRandomUuid());//ID
				if (parentEnterprise != null && !"".equals(parentEnterprise)) {
					kjPointLog.setTargetId(parentEnterprise.getId());
				} else {
					kjPointLog.setTargetId("0");
				}
				//判断是添加企业还是还是添加代理商、总代等
				if (reqEnterList.getRole() == 1) { //企业
					kjPointLog.setOperation("2");//操作绩点加、减
					kjPointLog.setCreateTime(new Date());//操作时间3
					kjPointLog.setCustomId(parentEnterprise.getId());//企业id
					kjPointLog.setContent("授权小程序以及开通" + reqEnterList.getMoney_init().floatValue() + "张名片");
					kjPointLog.setOperationPoint(reqEnterList.getMoney_init().floatValue() * shopTemplate.getCardCost() + shopTemplate.getSmallCost());//操作绩点数 分配名片数
					kjPointLog.setRemainPoint(parentEnterprise.getMoneyLeave().floatValue());
					kjPointLog.setTargetId(reqEnterList.getId());
					kjPointLogMapper.insertSelective(kjPointLog);
					kjPointLog.setId(UuidUtil.getRandomUuid());//ID
					kjPointLog.setOperation("1");//操作绩点加、减
					kjPointLog.setCreateTime(new Date());//操作时间3
					kjPointLog.setCustomId(reqEnterList.getId());//企业id
					kjPointLog.setContent("授权小程序以及开通" + reqEnterList.getMoney_init().floatValue() + "张名片");
					kjPointLog.setOperationPoint(reqEnterList.getMoney_init().floatValue() * shopTemplate.getCardCost() + shopTemplate.getSmallCost());//操作绩点数 分配名片数
					kjPointLog.setRemainPoint(parentEnterprise.getMoneyLeave().floatValue());
					kjPointLog.setTargetId(parentEnterprise.getId());
					kjPointLogMapper.insertSelective(kjPointLog);
				} else {//非企业
					kjPointLog.setContent("初始迹点分配");
					kjPointLog.setOperationPoint(reqEnterList.getMoney_init().floatValue());//操作绩点数为给下级分配的迹点数
					kjPointLog.setRemainPoint(reqEnterList.getMoney_init().floatValue());
					kjPointLogMapper.insertSelective(kjPointLog);
					kjPointLog.setId(UuidUtil.getRandomUuid());//ID
					kjPointLog.setContent("开通下级");
					kjPointLog.setOperation("2");//操作绩点加、减
					kjPointLog.setCustomId(reqEnterList.getParent_id());//企业id
					kjPointLog.setId(UuidUtil.getRandomUuid());//ID
					if (parentEnterprise != null && !"".equals(parentEnterprise)) {
						kjPointLog.setRemainPoint(parentEnterprise.getMoneyLeave().floatValue());
					}
					kjPointLog.setTargetId(reqEnterList.getId());
					kjPointLogMapper.insertSelective(kjPointLog);
				}
			}
		}
	}

	@Override
	public void editEnterAndAdmin(ReqEditEnterprise reqEnterList) {
		//修改登陆账号
		Admin admin = new Admin();
		admin.setType(reqEnterList.getType());
		admin.setPhone(reqEnterList.getPhone());
		admin.setName(reqEnterList.getName());
		admin.setLogin(reqEnterList.getLogin());
		admin.setId(reqEnterList.getId2());
       /* if(adminMapper.isExist(admin) > 0){
            throw new BusinessException(ErrCode.NO_PHONE_EXIST, "账号已被注册");
        }*/
		if (adminMapper.updateLogin(admin) > 0) {
			//修改企业
			Enterprise enterprise = new Enterprise();
			enterprise.setId(reqEnterList.getId());
			enterprise.setHeadIcon(reqEnterList.getHead_icon());
			enterprise.setName(reqEnterList.getName());
			enterprise.setPhone(reqEnterList.getPhone());
			enterprise.setMoneyInit(reqEnterList.getMoney_init());
			enterprise.setProvinceCode(reqEnterList.getProvince_code());
			enterprise.setCityCode(reqEnterList.getCity_code());
			enterprise.setAreaCode(reqEnterList.getArea_code());
			enterprise.setAddress(reqEnterList.getAddress());
			enterprise.setRemark(reqEnterList.getRemark());
			enterprise.setModifyTime(new Date());
			enterprise.setExpireTime(reqEnterList.getExpire_time());
			enterpriseMapper.updateByPrimaryKeySelective(enterprise);
			//修改企业的时候同时修改默认部门的名字
			enterpriseMapper.uodateDepartName(enterprise.getName(), enterprise.getId());
		}
	}

	/**
	 * 添加名片数量
	 *
	 * @param reqCardNum
	 */
	@Override
	public void addCard(ReqCardNum reqCardNum, HttpServletRequest request) {


		Enterprise topEnterprise = enterpriseMapper.selectByPrimaryKey(UserDataUtil.getCustomId(request));
		Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(reqCardNum.getId());
		ShopTemplate shopTemplate = shopTemplateMapper.selectByCode(enterprise.getTemplateId());

		Float used = shopTemplate.getCardCost() * reqCardNum.getCar_num().floatValue();
		if (topEnterprise.getMoneyLeave().floatValue() < used) {
			throw new BusinessException(-1, "绩点不足");
		}
		//操作方迹点记录
		KjPointLog topKjLointLog = new KjPointLog();
		//被操作方迹点记录
		KjPointLog kjPointLog = new KjPointLog();
		if (topEnterprise != null && !"".equals(topEnterprise)) { //parentEnterprise 是null代表是超级后台  不用扣迹点
			topEnterprise.setMoneyLeave(topEnterprise.getMoneyLeave() - shopTemplate.getCardCost() * reqCardNum.getCar_num());
			enterprise.setMoneyLeave(enterprise.getMoneyLeave() + reqCardNum.getCar_num());
			kjPointLog.setOperation("2");//操作绩点加、减
			kjPointLog.setCreateTime(new Date());//操作时间3
			kjPointLog.setCustomId(topEnterprise.getId());//企业id
			kjPointLog.setContent("给下属企业新增" + reqCardNum.getCar_num() + "张名片");
			kjPointLog.setOperationPoint(shopTemplate.getCardCost() * reqCardNum.getCar_num().floatValue());//操作绩点数 分配名片数
			kjPointLog.setRemainPoint(topEnterprise.getMoneyLeave().floatValue());
			kjPointLog.setTargetId(reqCardNum.getId());
			kjPointLog.setId(UuidUtil.getRandomUuid());//ID
			topKjLointLog.setOperation("1");//操作绩点加、减
			topKjLointLog.setCreateTime(new Date());//操作时间3
			topKjLointLog.setCustomId(reqCardNum.getId());//企业id
			topKjLointLog.setContent("新增" + reqCardNum.getCar_num() + "张名片");
			topKjLointLog.setOperationPoint(shopTemplate.getCardCost() * reqCardNum.getCar_num().floatValue());//操作绩点数 分配名片数
			topKjLointLog.setRemainPoint(shopTemplate.getCardCost() * reqCardNum.getCar_num().floatValue());
			topKjLointLog.setTargetId(topEnterprise.getId());
			topKjLointLog.setId(UuidUtil.getRandomUuid());//ID
		}
		Integer carNum = enterprise.getSaleCardNum() + enterprise.getCardSum() + reqCardNum.getCar_num();//累计名片数
		enterprise.setCardSum(carNum);
		enterprise.setMoneySum(enterprise.getMoneySum() + reqCardNum.getCar_num());
		kjPointLogMapper.insertSelective(kjPointLog);
		kjPointLogMapper.insertSelective(topKjLointLog);
		enterpriseMapper.updateByPrimaryKeySelective(topEnterprise);
		enterpriseMapper.updateByPrimaryKeySelective(enterprise);

	}

	/**
	 * 重置密码
	 *
	 * @param reqUpdatePassword
	 */
	@Override
	public void updatePassword(ReqUpdatePassword reqUpdatePassword) {
//        Enterprise enterprise = new Enterprise();
//        enterprise.setId(reqUpdatePassword.getId());
//        enterprise.setPassword(reqUpdatePassword.getPassword());
//        enterpriseMapper.updateByPrimaryKeySelective(enterprise);

		Admin admin = adminMapper.queryEnterAdmin(reqUpdatePassword.getId());
		admin.setPassWord(reqUpdatePassword.getPassword());
		adminMapper.updateByPrimaryKeySelective(admin);
	}

	/**
	 * 修改状态
	 *
	 * @param reqUpdateEnterStatus
	 */
	@Transactional
	@Override
	public void updateStatus(ReqUpdateEnterStatus reqUpdateEnterStatus) {
		Enterprise enterprise = new Enterprise();
		enterprise.setStatus(reqUpdateEnterStatus.getStatus());
		enterprise.setId(reqUpdateEnterStatus.getId());
		Admin admin = new Admin();
		admin.setStatus(reqUpdateEnterStatus.getStatus());
		admin.setCustomId(reqUpdateEnterStatus.getId());
		adminMapper.updateStatus(admin);
		enterpriseMapper.updateByPrimaryKeySelective(enterprise);
	}

	@Override
	public List<RspTemplate> findTemplate() {
		return enterpriseMapper.findTemplate();
	}

	/**
	 * 修改部署状态
	 *
	 * @param reqUpdateDeploy
	 */
	@Override
	public void updateDeploy(ReqUpdateDeploy reqUpdateDeploy) {
		Enterprise enterprise = new Enterprise();
		enterprise.setIsDeploy(reqUpdateDeploy.getIs_deploy());
		enterprise.setId(reqUpdateDeploy.getEnterprise_id());
		enterpriseMapper.updateByPrimaryKeySelective(enterprise);
	}

	@Autowired
	private AreaInfoMapper areaInfoMapper;
	@Autowired
	private CityInfoMapper cityInfoMapper;
	@Autowired
	private UserAddressMapper userAddressMapper;

	private String[] getProvinceCityAreaCode(String provinceName, String cityName, String areaName) {
		String province_code = userAddressMapper.findProvinceCode(provinceName);
		String province_id = userAddressMapper.findProvinceId(province_code);//获取省级的id

		String city_code = userAddressMapper.findCityCode(cityName, province_id);//城市code
		if (StringUtils.isEmpty(city_code)) {
			CityInfo compare = cityInfoMapper.selectCityByPID(Integer.valueOf(province_id));
			int compareId = 0;
			if(StringUtils.isEmpty(compare.getCityCode())){
				compareId = 0;
			}else{
				compareId = Integer.valueOf(compare.getCityCode());
			}
			CityInfo cityInfo = new CityInfo();
			cityInfo.setCreateTime(new Date());
			cityInfo.setCityCode(String.valueOf(compareId + 100));
			cityInfo.setCityName(cityName);
			cityInfo.setProvinceId(Integer.valueOf(province_id));
			cityInfo.setStatus(1);
			cityInfoMapper.insertSelective(cityInfo);
			city_code = cityInfo.getCityCode();
		}
		String city_id = userAddressMapper.findCityId(cityName, province_id);//城市id
		String area_code = userAddressMapper.findAreaCode(areaName, city_id);

		if (StringUtils.isEmpty(area_code)) {
			AreaInfo compare = areaInfoMapper.selectAreaByPID(Integer.valueOf(province_id));
			int compareId = 0;
			if(StringUtils.isEmpty(compare.getAreaCode())){
				compareId = 0;
			}else{
				compareId = Integer.valueOf(compare.getAreaCode());
			}
			AreaInfo areaInfo = new AreaInfo();
			areaInfo.setCreateTime(new Date());
			areaInfo.setAreaCode(String.valueOf(compareId + 1));
			areaInfo.setAreaName(areaName);
			areaInfo.setProvinceId(Integer.valueOf(province_id));
			areaInfo.setCityId(Integer.valueOf(city_id));
			areaInfoMapper.insertSelective(areaInfo);
			area_code = areaInfo.getAreaCode();
		}
		String[] code = {
				province_code, city_code, area_code
		};
		return code;
	}
}
