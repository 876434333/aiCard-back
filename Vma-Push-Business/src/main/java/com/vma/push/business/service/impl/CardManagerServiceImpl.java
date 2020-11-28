package com.vma.push.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.vma.push.business.common.Constants;
import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.config.PlatformEnterprise;
import com.vma.push.business.config.SmsConfig;
import com.vma.push.business.dao.*;
import com.vma.push.business.dto.Website;
import com.vma.push.business.dto.WebsiteTemplate;
import com.vma.push.business.dto.req.*;
import com.vma.push.business.dto.req.mini.*;
import com.vma.push.business.dto.req.mini.ReqAddDepartment;
import com.vma.push.business.dto.req.mini.ReqCardBaseInfo;
import com.vma.push.business.dto.req.mini.ReqCompanyNameLogo;
import com.vma.push.business.dto.req.mini.ReqGetPhone;
import com.vma.push.business.dto.req.mini.ReqAddDepartment;
import com.vma.push.business.dto.req.mini.ReqCardBaseInfo;
import com.vma.push.business.dto.req.mini.ReqCompanyNameLogo;
import com.vma.push.business.dto.req.mini.ReqGetPhone;
import com.vma.push.business.dto.req.mini.ReqHandover;
import com.vma.push.business.dto.req.superback.ReqAddEnterprise;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.RspStaffId;
import com.vma.push.business.dto.rsp.mini.*;
import com.vma.push.business.dto.rsp.staff.RspCardInfo;
import com.vma.push.business.entity.*;
import com.vma.push.business.service.*;
import com.vma.push.business.util.*;
import org.apache.log4j.Logger;
import org.codehaus.xfire.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-10-23 14:24
 */
@Service
public class CardManagerServiceImpl implements ICardManageService {
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private IStaffInfoService staffInfoService;
	@Autowired
	private StaffMapper staffInfoMapper;
	@Autowired
	private EnterpriseMapper enterpriseMapper;
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private DepartmentMapper departmentMapper;
	// add by plh at 2018-11-01 for 企业微信部门ID控制
	@Autowired
	private IndexCreate indexCreate;
	// add by plh at 2018-11-01 for 企业微信部门ID控制
	@Autowired
	private IDepartmentService departmentService;

	@Autowired
	private StaffQuitMapper staffQuitMapper;
	@Autowired
	private UserStaffRelaMapper userStaffRelaMapper;

	@Autowired
	AdminMapper adminMapper;

	@Autowired
	private DeployMapper deployMapper;
	@Autowired
	private SysConfigMapper sysConfigMapper;

	@Autowired
	private SmsConfig smsConfig;

	@Autowired
	private QiniuUtils qiniuUtils;
	@Autowired
	private ISendSMS sendSMSService;
	@Autowired
	private IEnterpriseService enterpriseService;

	@Override
	public String sendMsg(String phone) {
		String msgCode = RandomValidateCodeUtil.getMsgValidateCode();
		System.out.println("验证码：-------------------" + msgCode);
		String[] params = {msgCode};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
		Integer template = smsConfig.getTemplate().get("user_register");
		Integer result = sendSMSService.sendSMS(phone, params, template);
		if (result == 0) {
			// 存入Redis
			UserDataUtil.setMsg(phone, msgCode);
			// 短信发送成功
			return "ok";
		} else {
			return "-1";
		}
	}

	/**
	 * 校验验证码
	 *
	 * @param phone
	 * @param msgCode
	 * @return
	 */
	@Override
	public String checkMsg(String phone, String msgCode) {
		return sendSMSService.cherckSMS(phone, msgCode);
	}

	/**
	 * 更新名片基本信息
	 *
	 * @param reqCard
	 * @return
	 */
	@Override
	public String updataCardBaseInfo(ReqCardBaseInfo reqCard) {
		Staff staff = reqCard.toStaff();
		// 修改企业微信里面的名字电话
		String staffId = reqCard.getStaffId();
		Staff oldStaff = staffInfoMapper.selectByPrimaryKey(staffId);
		staff.setId(staffId);
		staff.setEnterpriseId(oldStaff.getEnterpriseId());
		staff.setDepartmentId(oldStaff.getDepartmentId());
		staff.setWxId(oldStaff.getWxId());

		staffInfoMapper.updateByPrimaryKeySelective(staff);
		return "";
		// 不修改企业微信里面的信息
//		Employee employee = new Employee(staff);
//		int errcode = new JSONObject(staffInfoService.updateApi(employee, staff.getEnterpriseId())).getInt("errcode");
//		if (errcode == 0) {
//			staffInfoMapper.updateByPrimaryKeySelective(staff);
//			return "ok";
//		} else {
//			throw new BusinessException(-1, "修改信息失败");
//		}
	}

	/**
	 * 获取企业列表
	 *
	 * @param openid
	 * @return
	 */
	@Override
	public List<RspEnterpriseList> getEnterpriseList(String openid) {
		List<RspEnterpriseList> enterpriseLists = enterpriseMapper.getEnterpriseListByOpenid(openid);
		UserInfo userInfo = userInfoMapper.selectByOpenId(openid);
		Staff staff = staffInfoMapper.selectByPrimaryKey(userInfo.getLastStaffId());
		List<RspEnterpriseList> repList = new ArrayList<>();
		for (RspEnterpriseList enterprise : enterpriseLists) {
			if (enterprise.getId().equals(staff.getEnterpriseId())) {
				enterprise.setIs_curent(1);
			} else {
				enterprise.setIs_curent(0);
			}
			repList.add(enterprise);
		}
		return repList;
	}

	/**
	 * 解密手机号码
	 *
	 * @param reqGetPhone
	 * @return
	 */
	@Override
	public String getPhone(ReqGetPhone reqGetPhone, HttpServletRequest request) {
		String openid = reqGetPhone.getOpenid();
		String iv = reqGetPhone.getIv();
		String phone = reqGetPhone.getPhone();
		String sessionKey = UserDataUtil.getSessionKey(openid);
		try {
			byte[] result = this.decrypt(Base64.decode(sessionKey), Base64.decode(iv), Base64.decode(phone));
			String phoneObject = new String(result);
			String mingwenPhone = new JSONObject(phoneObject).getString("phoneNumber");

			UserInfo userInfo = userInfoMapper.selectByOpenId(openid);
			userInfo.setPhone(mingwenPhone);
			userInfoMapper.updateByPrimaryKeySelective(userInfo);
			return mingwenPhone;
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密手机号
	 *
	 * @param key
	 * @param iv
	 * @param encData
	 * @return
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public byte[] decrypt(byte[] key, byte[] iv, byte[] encData)
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		return cipher.doFinal(encData);
	}

	/**
	 * 解散企业
	 *
	 * @param enterpriseId
	 * @return
	 */
	@Override
	@Transactional
	public int dissolveCompany(String enterpriseId, HttpServletRequest request) {
		Enterprise enterprise = enterpriseMapper.getEntpById(enterpriseId);
		enterprise.setStatus(0); // 将状态设置为0 为解散状态
		enterprise.setModifyTime(new Date());
		enterpriseMapper.updateByPrimaryKeySelective(enterprise);

		// 获取该解散企业所有员工
		List<String> staffIdList = staffInfoMapper.getStaffListByEnterpriseId(enterpriseId);
		// 找到userInfo中lastStaffId为解散企业的员工
		List<String> userIdList = userInfoMapper.getUserInfoListByLastStaffId(staffIdList);

		for (String userInfoId : userIdList) {
			UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
			List<com.vma.push.business.dto.rsp.mini.RspEnterpriseList> enterpriseList = enterpriseMapper.getEnterpriseListByOpenid(userInfo.getOpenId());
			if (enterpriseList.size() == 0) {
				// 解散最后一个企业
				userInfo.setLastStaffId("");
			} else {
				// 不是最后一个
				String eId = enterpriseList.get(0).getId();
				Staff staff = staffInfoMapper.getStaffByEnterpriseIdAndOpenid(eId, userInfo.getOpenId(), "1");
				userInfo.setLastStaffId(staff.getId());
			}
			userInfoMapper.updateByPrimaryKeySelective(userInfo);
		}
		return 1;
	}

	@Override
	public Enterprise getCurrentEnterpriseInfo(String enterpriseId) {
//		String enterpriseId = UserDataUtil.getEnterpriseIdByOpenid(openid);
		return enterpriseMapper.getEntpById(enterpriseId);
	}

	@Override
	public List<RepDepartmentList> getDepartmentList(String enterpriseId, String parentId) {
		return departmentMapper.getDeparentListAndPerson(enterpriseId, parentId);
	}

	@Override
	public void switchCompany(String enterpriseId, String openid, HttpServletRequest request) {
		// 切换后的企业员工
		Staff staff = staffInfoMapper.getStaffByEnterpriseIdAndOpenid(enterpriseId, openid, "1");
		UserInfo userInfo = userInfoMapper.selectByOpenId(openid);
		// 切换企业把last_staff_id 换一下
		userInfo.setLastStaffId(staff.getId());
		userInfoMapper.updateByPrimaryKeySelective(userInfo);
		UserDataUtil.setMyStaffId(staff.getId(),request.getHeader(Constants.SESSION_ID));
		UserDataUtil.setMyEnterpriseId(staff.getEnterpriseId(),request.getHeader(Constants.SESSION_ID));
		UserDataUtil.setEnterpriseId(staff.getEnterpriseId(),request.getHeader(Constants.SESSION_ID));
	}

	@Override
	public List<ResDepartmentPerson> getDepartmentPerson(String enterpriseId, String departmentId, String status) {
		// 获取该部门下人员
		List<ResDepartmentPerson> list = staffInfoMapper.getDepartmentPersionList(enterpriseId, departmentId, status, null);
		return list;
	}

	@Override
	public List<ResDepartmentPerson> getDepartmentPersonByName(String enterpriseId, String name) {
		return staffInfoMapper.getDepartmentPersionList(enterpriseId, "null", "1", name);
	}

	@Override
	@Transactional
	public String addDepartment(ReqAddDepartment reqAddDepartment) {
		String enterpriseId = reqAddDepartment.getEnterpriseId();
		String parentId = reqAddDepartment.getParentId();
		String departmentName = reqAddDepartment.getDepartmentName();
		// 获取最大的id
		// M by plh at 2018-11-01 for 企业微信部门ID控制
		// int maxId = departmentMapper.getMaxIdByParenId(enterpriseId);
		int maxDeptId = indexCreate.getDeptIdIndex();

		Department department = new Department();
		department.setId(Integer.toString(maxDeptId));
		department.setCreateTime(new Date());
		department.setName(departmentName);
		department.setParentId(parentId);
		department.setEnterpriseId(enterpriseId);
		department.setOrder(1);
		departmentMapper.insertSelective(department);

		//Add by PLH at 2018-11-01 for 同时要新增到企业微信中 Begin
		WechatDepartment wechatDept = new WechatDepartment();
		wechatDept.setId(department.getId());
		wechatDept.setParentid(department.getParentId());
		wechatDept.setName(department.getName());

		String result = departmentService.insertApi(wechatDept, PlatformEnterprise.ID);
		int errCode = new JSONObject(result).getInt("errcode");
		if (errCode != 0) {
			logger.info("创建部门失败, errCode=" + errCode + " by id = " + wechatDept.getId() + "  Corp_id = ?  EnterpriseId = " + PlatformEnterprise.ID);
			throw new BusinessException(errCode, "创建部门失败, errCode=" + errCode);
		}
		//Add by PLH at 2018-11-01 for 同时要新增到企业微信中 End

		return Integer.toString(maxDeptId);
	}

	/**
	 * 删除部门
	 *
	 * @param reqdeleteDepartment
	 */
	@Override
	public void deleteDepartment(ReqAddDepartment reqdeleteDepartment) {
		String enterpriseId = reqdeleteDepartment.getEnterpriseId();
		// 当前需要删除的部门Id
		String departmentId = reqdeleteDepartment.getParentId();
		List<ResDepartmentPerson> list = staffInfoMapper.getDepartmentPersionList(enterpriseId, departmentId, "1", null);
		List<ResDepartmentPerson> list1 = staffInfoMapper.getDepartmentPersionList(enterpriseId, departmentId, "2", null);
		if (list.size() > 0) {
			throw new BusinessException(-1, "该部门存在员工,不允许删除");
		}
		if (list1.size() > 0) {
			throw new BusinessException(-1, "该部门存在未认领的员工,不允许删除");
		}
		// 获取子部门列表
		List<RepDepartmentList> list2 = departmentMapper.getDeparentListAndPerson(enterpriseId, departmentId);
		if (list2.size() > 0) {
			throw new BusinessException(-1, "该部门存在子部门,不允许删除");
		}
		// 从企业微信中删除
		departmentService.deleteApi(departmentId, enterpriseId);
		// 重数据库总删除
		departmentMapper.delDept(departmentId, enterpriseId);
	}

	@Override
	public void updataDepartment(ReqAddDepartment reqUpdataDepartment) {
		String enterpriseId = reqUpdataDepartment.getEnterpriseId();
		// 当前需要更新的部门Id
		String departmentId = reqUpdataDepartment.getParentId();
		String departmentName = reqUpdataDepartment.getDepartmentName();
		ReqUpdateDepartInfo reqUpdateDepartInfo = new ReqUpdateDepartInfo();
		reqUpdateDepartInfo.setEnterprise_id(enterpriseId);
		reqUpdateDepartInfo.setId(departmentId);
		reqUpdateDepartInfo.setName(departmentName);
		departmentMapper.updateDeptOnlyName(reqUpdateDepartInfo);
	}

	@Override
	public void updataCompanyNameOrLogo(ReqCompanyNameLogo companyNameLogo) {
		Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(companyNameLogo.getEnterpriseId());
		enterprise.setName(companyNameLogo.getEnterpriseName());
		enterprise.setHeadIcon(companyNameLogo.getIconUrl());
		enterpriseMapper.updateByPrimaryKeySelective(enterprise);
	}

	@Override
	public String getUserRelo(String enterpriseId, String openid) {
		Staff staff = staffInfoMapper.getStaffByEnterpriseIdAndOpenid(enterpriseId, openid, "1");
		if (staff == null) {
			return "2"; // 没找到，存在该员工是申请中的状态
		} else {
			return staff.getRole();
		}
	}

	public boolean addVideo(Map<String, Object> map) {
		staffInfoMapper.delVideoByStaffId(map.get("staffId").toString());
		int rs = staffInfoMapper.addVideo(map);
		staffInfoMapper.updStaffVideo(map);
		return rs > 0 ? true : false;
	}

	@Override
	public Map<String, Object> getVideo(String staffId) {
		return staffInfoMapper.getVideo(staffId);
	}

	@Override
	public boolean updCardStyle(Map<String, Object> map) {
		return staffInfoMapper.updCardStyle(map) > 0 ? true : false;
	}

	@Override
	public Map<String, Object> getCardStyle(String staffId) {
		return staffInfoMapper.getCardStyle(staffId);
	}

	@Override
	public boolean updShareSetup(Map<String, Object> map) {
		System.out.println(map);
		return staffInfoMapper.updShareSetup(map) > 0 ? true : false;
	}

	/**
	 * 修改员工基本信息
	 *
	 * @param reqUpdataDptPerson
	 */
	@Override
	@Transactional
	public void updataDptPerson(ReqUpdataDptPerson reqUpdataDptPerson) {
		Staff staff = new Staff();
		staff.setId(reqUpdataDptPerson.getId());
		staff.setName(reqUpdataDptPerson.getName());
		staff.setPhone(reqUpdataDptPerson.getPhone());
		staff.setStation(reqUpdataDptPerson.getStation());
		staff.setDepartmentId(reqUpdataDptPerson.getDepartment_id());
		int result = staffInfoMapper.updateByPrimaryKeySelective(staff);
		if (result <= 0) {
			throw new BusinessException(-1, "跟新员工资料失败");
		}
		// 修改企业微信资料，更改微信部门
		// 获取wx_id
		String wx_id = staffInfoMapper.getWxIdByStaffId(reqUpdataDptPerson.getId());
		//Add by lql at 2018-11-28 for 一个企业微信用户对应多个名片(多个部门) Begin
		//同时要设置这个员工多个部门权限
		List<String> QYWXDeptIdList = staffInfoMapper.getWxDeptIdsByPhone(sysConfigMapper.selectByPrimaryKey(1).getPlatformCorpId(), staff.getPhone());
		int[] department_Ids = new int[QYWXDeptIdList.size() + 1];
		for (int i = 0; i < QYWXDeptIdList.size(); i++) {
			String loopQYWXID = QYWXDeptIdList.get(i);
			department_Ids[i] = Integer.parseInt(loopQYWXID);
		}
		// 补上新部门的权限
		// 此处部门id为平台统一建的部门,一个定值
		department_Ids[QYWXDeptIdList.size()] = Integer.parseInt(sysConfigMapper.selectByPrimaryKey(1).getPlatformDefaultDeptId());
		ReqUpdateStaffInfo reqUpdateStaffInfo = new ReqUpdateStaffInfo();
		reqUpdateStaffInfo.setPhone(staff.getPhone());
		reqUpdateStaffInfo.setDepartment_id(department_Ids);
		reqUpdateStaffInfo.setName(staff.getName());
		reqUpdateStaffInfo.setWx_id(wx_id);

		Employee employee = new Employee(reqUpdateStaffInfo);
		int QYWX_RETCODE = new JSONObject(staffInfoService.updateApi(employee, reqUpdataDptPerson.getEnterprise_id())).getInt("errcode");
		if (QYWX_RETCODE != 0) {
			throw new BusinessException(ErrCode.LOSE_NUM, "微信错误: " + QYWX_RETCODE);
		}

	}

	@Override
	@Transactional
	public void handover(ReqHandover reqHandover) {
		String leaveStaffId = reqHandover.getLeaveStaffId();
		String handoverStaffId = reqHandover.getHandoverStaffId();
		Staff leaveStaff = staffInfoMapper.selectByPrimaryKey(leaveStaffId);
		// 更新离职状态
		leaveStaff.setStatus(0);
		staffInfoMapper.updateByPrimaryKeySelective(leaveStaff);

		//在staff_quit表中新增一条记录
		StaffQuit staffQuit = new StaffQuit();
		staffQuit.setId(UuidUtil.getRandomUuidWithoutSeparator());
		staffQuit.setQuitStaffId(leaveStaffId);
		staffQuit.setReceiverStaffId(handoverStaffId);
		staffQuit.setCreateTime(new Date());
		staffQuitMapper.insert(staffQuit);
		//修改所有user_staff_rela和离职人员建立关系的的状态为0
		List<UserStaffRela> relaList = userStaffRelaMapper.getUserRelaListByStaffid(leaveStaffId);
		for (UserStaffRela rela : relaList) {
			rela.setRelaStatus(0);
			rela.setModifyTime(new Date());
			userStaffRelaMapper.updateByPrimaryKeySelective(rela);
		}
		// t_staff_id修改离职人员最后一次l

		// 找到userInfo中lastStaffId为解散企业的员工
		List<String> staffIdList = new ArrayList<>();
		staffIdList.add(leaveStaffId);
		List<String> userIdList = userInfoMapper.getUserInfoListByLastStaffId(staffIdList);
		for (String userInfoId : userIdList) {
			UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userInfoId);
			List<com.vma.push.business.dto.rsp.mini.RspEnterpriseList> enterpriseList = enterpriseMapper.getEnterpriseListByOpenid(userInfo.getOpenId());
			if (enterpriseList.size() == 0) {
				// 最后一个企业
				userInfo.setLastStaffId("");
			} else {
				// 不是最后一个
				String eId = enterpriseList.get(0).getId();
				Staff staff = staffInfoMapper.getStaffByEnterpriseIdAndOpenid(eId, userInfo.getOpenId(), "1");
				userInfo.setLastStaffId(staff.getId());
			}
			userInfoMapper.updateByPrimaryKeySelective(userInfo);
		}
		// 修改企业名片数-1
		enterpriseService.updataEnterpriseStaffNum(leaveStaff.getEnterpriseId(),-1);

		//Add by PLH at 2018-11-28 for 一个企业微信用户对应多个名片(多个部门) Begin
		//同时要设置这个员工多个部门权限
		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);
		List<String> QYWXDeptIdList = staffInfoMapper.getWxDeptIdsByPhone(sysConfig.getPlatformCorpId(), leaveStaff.getPhone());
		int[] department_Ids = new int[QYWXDeptIdList.size() + 1];
		for (int i = 0; i < QYWXDeptIdList.size(); i++) {
			String loopQYWXID = QYWXDeptIdList.get(i);
			department_Ids[i] = Integer.parseInt(loopQYWXID);
		}
		// 补上新部门的权限
		// 此处部门id为平台统一建的部门,一个定值
		department_Ids[QYWXDeptIdList.size()] = Integer.parseInt(sysConfigMapper.selectByPrimaryKey(1).getPlatformDefaultDeptId());

		ReqUpdateStaffInfo reqUpdateStaffInfo = new ReqUpdateStaffInfo();
		reqUpdateStaffInfo.setPhone(leaveStaff.getPhone());
		reqUpdateStaffInfo.setDepartment_id(department_Ids);
		reqUpdateStaffInfo.setName(leaveStaff.getName());
		reqUpdateStaffInfo.setWx_id(leaveStaff.getWxId());

		Employee employee = new Employee(reqUpdateStaffInfo);
		int QYWX_RETCODE = new JSONObject(staffInfoService.updateApi(employee, leaveStaff.getEnterpriseId())).getInt("errcode");
		if (QYWX_RETCODE != 0) {
			throw new BusinessException(ErrCode.LOSE_NUM, "微信错误: " + QYWX_RETCODE);
		}
		//Add by PLH at 2018-11-28 for 一个企业微信用户对应多个名片(多个部门) End

	}

	/**
	 * 复职
	 *
	 * @param staffId
	 */
	@Override
	@Transactional
	public void reentry(String staffId) {
		// 1、修改staff表中的状态
		Staff staff = staffInfoMapper.selectByPrimaryKey(staffId);
		staff.setStatus(1);
		staff.setModifyTime(new Date());
		staffInfoMapper.updateByPrimaryKeySelective(staff);
		// 2、更改user_staff_rela表中的客户关系状态 如果rela_status为2(客户与交接人交接完成)、0（客户与交接人待交接） 则修改为1,
		List<UserStaffRela> relaList = userStaffRelaMapper.getUserRelaListByStaffid(staffId);
		for (UserStaffRela rela : relaList) {
			rela.setRelaStatus(1);
			rela.setModifyTime(new Date());
			userStaffRelaMapper.updateByPrimaryKeySelective(rela);
		}
		// 修改名片数量+1
		enterpriseService.updataEnterpriseStaffNum(staff.getEnterpriseId(),1);

		//Add by PLH at 2018-11-28 for 一个企业微信用户对应多个名片(多个部门) Begin
		//同时要设置这个员工多个部门权限
		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);
		List<String> QYWXDeptIdList = staffInfoMapper.getWxDeptIdsByPhone(sysConfig.getPlatformCorpId(), staff.getPhone());
		int[] department_Ids = new int[QYWXDeptIdList.size() + 1];
		for (int i = 0; i < QYWXDeptIdList.size(); i++) {
			String loopQYWXID = QYWXDeptIdList.get(i);
			department_Ids[i] = Integer.parseInt(loopQYWXID);
		}
		// 补上新部门的权限
		// 此处部门id为平台统一建的部门,一个定值
		department_Ids[QYWXDeptIdList.size()] = Integer.parseInt(sysConfigMapper.selectByPrimaryKey(1).getPlatformDefaultDeptId());

		ReqUpdateStaffInfo reqUpdateStaffInfo = new ReqUpdateStaffInfo();
		reqUpdateStaffInfo.setPhone(staff.getPhone());
		reqUpdateStaffInfo.setDepartment_id(department_Ids);
		reqUpdateStaffInfo.setName(staff.getName());
		reqUpdateStaffInfo.setWx_id(staff.getWxId());

		Employee employee = new Employee(reqUpdateStaffInfo);
		int QYWX_RETCODE = new JSONObject(staffInfoService.updateApi(employee, staff.getEnterpriseId())).getInt("errcode");
		if (QYWX_RETCODE != 0) {
			throw new BusinessException(ErrCode.LOSE_NUM, "微信错误: " + QYWX_RETCODE);
		}
		//Add by PLH at 2018-11-28 for 一个企业微信用户对应多个名片(多个部门) End

	}

	/**
	 * 获取管理员或者运营者列表
	 *
	 * @param enterpriseId
	 * @return
	 */
	@Override
	public List<ResDepartmentPerson> getManager(String enterpriseId, String role) {
		return staffInfoMapper.getManagerList(enterpriseId, role);
	}

	/**
	 * 转让管理员
	 *
	 * @param reqHandover
	 */
	@Override
	@Transactional
	public void transferManager(ReqHandover reqHandover) {
		String oldStaffId = reqHandover.getLeaveStaffId();
		String newStaffId = reqHandover.getHandoverStaffId();
		// 1、修改两个员工的角色
		Staff oldStaff = staffInfoMapper.selectByPrimaryKey(oldStaffId);
		Staff newStaff = staffInfoMapper.selectByPrimaryKey(newStaffId);
		if (newStaff.getStatus() == 0) {
			throw new BusinessException(-1, "该员工已离职");
		}
		if (newStaff.getRole().equals("0")) {
			throw new BusinessException(-1, "该员工已是管理员");
		}
		newStaff.setRole("0");
		newStaff.setOpenBoss(1);
		newStaff.setModifyTime(new Date());
		staffInfoMapper.updateByPrimaryKeySelective(newStaff);

		oldStaff.setRole("2");
		newStaff.setOpenBoss(0);
		oldStaff.setModifyTime(new Date());
		staffInfoMapper.updateByPrimaryKeySelective(oldStaff);

		// 2、修改admin表中的该企业的管理员信息
		Admin admin = adminMapper.queryEnterAdmin(oldStaff.getEnterpriseId());
		admin.setOpenid(newStaff.getOpenid());
		admin.setRemark(oldStaffId + "--转让管理员给--" + newStaffId);
		adminMapper.updateByPrimaryKeySelective(admin);
	}

	/**
	 * 增加运营者
	 *
	 * @param staffId
	 */
	@Override
	public void addOperator(String staffId) {
		Staff staff = staffInfoMapper.selectByPrimaryKey(staffId);
		staff.setModifyTime(new Date());
		staff.setRole("1"); // 0:管理员，1:运营者, 2:员工
		staffInfoMapper.updateByPrimaryKeySelective(staff);
	}

	/**
	 * 移除运营者
	 *
	 * @param operatorList
	 */
	@Override
	public void removeOperator(ReqRemoveOperators operatorList) {
		for (String staffId : operatorList.getOperatorList()) {
			//挨个变更状态
			Staff staff = staffInfoMapper.selectByPrimaryKey(staffId);
			staff.setModifyTime(new Date());
			staff.setRole("2"); // 0:管理员，1:运营者, 2:员工
			staffInfoMapper.updateByPrimaryKeySelective(staff);
		}
	}

	/**
	 * 手动输入添加员工
	 *
	 * @param reqUpdataDptPerson
	 */
	@Override
	public void addDptStaff(ReqUpdataDptPerson reqUpdataDptPerson) {
		String name = reqUpdataDptPerson.getName();
		String phone = reqUpdataDptPerson.getPhone();
		String station = reqUpdataDptPerson.getStation();
		String department_id = reqUpdataDptPerson.getDepartment_id();
		String enterprise_id = reqUpdataDptPerson.getEnterprise_id();
		String role = reqUpdataDptPerson.getRole();

		// 判断手机号是否存在该企业中
		Staff staff1 = staffInfoMapper.getStaffByEnterpriseIdAndPhone(enterprise_id, phone);
		if (staff1 != null) {
			throw new BusinessException(-1, "企业中存在相同的手机号");
		}
		Deploy deploy = deployMapper.selectAll(enterprise_id);
		String isExistStaff = staffInfoMapper.isExistStaffByEntWxAndPhone(deploy.getCorpid(), phone);
		ReqAddStaffInfo reqAddStaffInfo = new ReqAddStaffInfo();
		reqAddStaffInfo.setEnterprise_id(enterprise_id);
		reqAddStaffInfo.setDepartment_id(Integer.parseInt(sysConfigMapper.selectByPrimaryKey(1).getPlatformDefaultDeptId())); // 此处部门id为平台统一建的部门,一个定值
		reqAddStaffInfo.setName(name);
		reqAddStaffInfo.setPhone(phone);
		reqAddStaffInfo.setIsPlatform(1); // 平台模式 用于区分生成限量的二维码还是不限量的二维码
		RspStaffId staffId = null;
		try {
			if (StringUtils.isEmpty(isExistStaff) == true || isExistStaff.equals("1") == false) {
				//本地数据库中还没有这个员工
				// 企业微信: 增加员工
				staffId = staffInfoService.addStaff(reqAddStaffInfo, PlatformEnterprise.ID, true);
			} else {
				// 本地数据库存在相同手机号，此时不需要在企业微信中添加员工
				staffId = staffInfoService.addStaff(reqAddStaffInfo, PlatformEnterprise.ID, false);
			}
			Staff staff = new Staff();
			// 如果没传部门id默认根Id
			if (StringUtils.isEmpty(department_id)) {
				int departmentId = this.getDepartmentList(enterprise_id, "0").get(0).getId();
				department_id = Integer.toString(departmentId);
			}
			staff.setId(staffId.getStaff_id());
			staff.setWxId(staffId.getWx_id());
			staff.setDepartmentId(department_id);
			staff.setStation(station);
			staff.setRole(role);
			staff.setStatus(2); // 表示暂停状态 -- 未领取
			staff.setModifyTime(new Date());
			staff.setCreateTime(new Date());
			staff.setFirstLetter(ChineseFCUtil.cn2py(name));
			staffInfoMapper.updateByPrimaryKeySelective(staff);

			// 人数在后续的认领中添加

		} catch (Exception e) {

		}
	}

	@Override
	public Integer cardOn(String relaId) {
		return staffInfoMapper.updateRelaStatus("1", relaId);
	}

	@Override
	public Integer cardOff(String relaId) {
		return staffInfoMapper.updateRelaStatus("0", relaId);
	}

	/**
	 * 通过分享加员工
	 */
	@Override
	@Transactional
	public void addStaffByShare(ReqAddStaffByShare reqAddStaffByShare) {
		String openid = reqAddStaffByShare.getOpenid();

		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);

		UserInfo userInfo = userInfoMapper.selectByOpenId(openid);
		// 1、user_info中添加记录
		if (userInfo == null) {
			userInfo = new UserInfo();
			userInfo.setId(UuidUtil.getRandomUuidWithoutSeparator());
			userInfo.setOpenId(openid);
			userInfo.setHeadIcon(reqAddStaffByShare.getAvatarUrl());
			userInfo.setNickName(reqAddStaffByShare.getNickName());
			userInfo.setWxSoftId(sysConfig.getPlatformMiniAppId());

			Staff shareStaff = staffInfoMapper.selectByPrimaryKey(reqAddStaffByShare.getStaffId());// 查询分享分的userid
			UserInfo shareUserInfo = userInfoMapper.selectByOpenId(shareStaff.getOpenid());

			userInfo.setFromUserId(shareUserInfo.getId());
			userInfo.setCreateTime(new Date());
			userInfo.setModifyTime(new Date());
			userInfoMapper.insertSelective(userInfo);
		}

		// 2、判断邀请者是否在该企业中
		Staff staff1 = staffInfoMapper.getStaffByEnterpriseIdAndOpenid(reqAddStaffByShare.getEnterpriseId(), openid, "1");
		if (staff1 != null) {
			throw new BusinessException(-1, "你已经是该企业员工,请勿重复添加！");
		}
		// 3、判断邀请者是否离职过
		Staff staff2 = staffInfoMapper.getStaffByEnterpriseIdAndOpenid(reqAddStaffByShare.getEnterpriseId(), openid, "0");
		if (staff2 != null) {
			throw new BusinessException(-1, "你曾经离职过,请联系管理员！");
		}
		// 3、判断邀请者是否离职过
		Staff staff3 = staffInfoMapper.getStaffByEnterpriseIdAndOpenid(reqAddStaffByShare.getEnterpriseId(), openid, "3");
		if (staff3 != null) {
			throw new BusinessException(-1, "你申请加入过该公司,请联系管理员审核！");
		}

		// 4、员工表中添加
		try {
			String nickName = userInfo.getNickName();
			String headIcon = "";
			if (StringUtils.isEmpty(userInfo.getHeadIcon()) == true) {
				headIcon = sysConfigMapper.selectByPrimaryKey(1).getImgUrl() + "/dc_user_default_logo.png";
			} else {
				headIcon = userInfo.getHeadIcon();
			}

			// 5、企业微信: 增加员工
			ReqAddStaffInfo reqAddStaffInfo = new ReqAddStaffInfo();
			reqAddStaffInfo.setEnterprise_id(reqAddStaffByShare.getEnterpriseId());
			reqAddStaffInfo.setDepartment_id(Integer.parseInt(reqAddStaffByShare.getDepartmentId()));
			reqAddStaffInfo.setName(nickName);
			reqAddStaffInfo.setPhone(reqAddStaffByShare.getPhone());
			reqAddStaffInfo.setIsPlatform(1); // 平台模式 用于区分生成限量的二维码还是不限量的二维码
			// Add by PLH at 2018-11-01 for 平台模式下, 手机号可以重复，(每个企业微信只能有一个企业微信用户)
			// 该手机号在这个企业微信下是否已经存在员工
			String isExistStaff = staffInfoMapper.isExistStaffByEntWxAndPhone(sysConfig.getPlatformCorpId(), reqAddStaffByShare.getPhone());
			RspStaffId staffId = null;
			if (StringUtils.isEmpty(isExistStaff) == true || isExistStaff.equals("1") == false) {
				//本地数据库中还没有这个员工
				staffId = staffInfoService.addStaff(reqAddStaffInfo, PlatformEnterprise.ID, true);
			} else {
				staffId = staffInfoService.addStaff(reqAddStaffInfo, PlatformEnterprise.ID, false);
			}

			//Add by PLH at 2018-11-28 for 一个企业微信用户对应多个名片(多个部门) Begin
			//同时要设置这个员工多个部门权限
			List<String> QYWXDeptIdList = staffInfoMapper.getWxDeptIdsByPhone(sysConfig.getPlatformCorpId(), reqAddStaffByShare.getPhone());
			int[] department_Ids = new int[QYWXDeptIdList.size() + 1];
			for (int i = 0; i < QYWXDeptIdList.size(); i++) {
				String loopQYWXID = QYWXDeptIdList.get(i);
				department_Ids[i] = Integer.parseInt(loopQYWXID);
			}
			// 补上新部门的权限
			// 此处部门id为平台统一建的部门,一个定值
			department_Ids[QYWXDeptIdList.size()] = Integer.parseInt(sysConfigMapper.selectByPrimaryKey(1).getPlatformDefaultDeptId());

			ReqUpdateStaffInfo reqUpdateStaffInfo = new ReqUpdateStaffInfo();
			reqUpdateStaffInfo.setPhone(reqAddStaffInfo.getPhone());
			reqUpdateStaffInfo.setDepartment_id(department_Ids);
			reqUpdateStaffInfo.setName(reqAddStaffInfo.getName());
			reqUpdateStaffInfo.setWx_id(staffId.getWx_id());

			Employee employee = new Employee(reqUpdateStaffInfo);
			int QYWX_RETCODE = new JSONObject(staffInfoService.updateApi(employee, reqAddStaffByShare.getEnterpriseId())).getInt("errcode");
			if (QYWX_RETCODE != 0) {
				throw new BusinessException(ErrCode.LOSE_NUM, "微信错误: " + QYWX_RETCODE);
			}
			//Add by PLH at 2018-11-28 for 一个企业微信用户对应多个名片(多个部门) End


			// 补全staffInfoService.addStaff中insert staff缺少的字段
			// 在企业微信添加人员成功，在staff里面完善记录
			Staff staff = new Staff();
			staff.setEnterpriseId(reqAddStaffByShare.getEnterpriseId());
			staff.setStation("");
			staff.setOpenid(openid);
			staff.setRole("2");                                         // 系统角色-- 此时默认为管理员 0管理员 2运营者 3员工
			staff.setCreateTime(new Date());                            // 创建时间
			staff.setStatus(1);                                         // 状态 1正常 0暂停 2解散
			staff.setPhone(reqAddStaffByShare.getPhone());
			staff.setTemlateId(1);
			staff.setName(nickName);
			staff.setFirstLetter(ChineseFCUtil.cn2py(nickName)); // 获取名字首字母
			staff.setHeadIcon(headIcon);
			staff.setId(staffId.getStaff_id());
			staff.setWxId(staffId.getWx_id());
			staff.setDepartmentId(reqAddStaffByShare.getDepartmentId());
			staff.setOpenAi(1);
			staffInfoMapper.updateByPrimaryKeySelective(staff);

			// 6、为自己添加user_staff_rela关系
			UserStaffRela userStaffRela = new UserStaffRela();
			userStaffRela.setId(UuidUtil.getRandomUuid());
			userStaffRela.setCreateTime(new Date());
			userStaffRela.setEnterpriseId(reqAddStaffByShare.getEnterpriseId());//添加企业ID
			userStaffRela.setModifyTime(new Date());
			userStaffRela.setFroms(2);//名片来源
			userStaffRela.setStaffId(staff.getId());//自己员工id
			userStaffRela.setUserId(userInfo.getId());//自己用户id
			userStaffRela.setDepartmentId(staff.getDepartmentId());
			userStaffRelaMapper.insertSelective(userStaffRela);

			userInfo.setLastStaffId(staff.getId()); // 把最后查看的名片设为自己
			userInfoMapper.updateByPrimaryKeySelective(userInfo);

			// 7、修改企业名片数量+1
			enterpriseService.updataEnterpriseStaffNum(reqAddStaffByShare.getEnterpriseId(), 1);

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("----------------------------------" + e.getMessage());
		}
	}

	/**
	 * 获取认领界面的内容
	 *
	 * @param staffId
	 * @return
	 */
	@Override
	public ResClaimInfo getClaimInfo(String staffId) {
		Staff staff = staffInfoMapper.selectByPrimaryKey(staffId);
		Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(staff.getEnterpriseId());
		ResClaimInfo resClaimInfo = new ResClaimInfo();
		resClaimInfo.setInviteName(staff.getName());
		resClaimInfo.setPhone(staff.getPhone());
		resClaimInfo.setStation(staff.getStation());
		resClaimInfo.setInviteHeadIcon(staff.getHeadIcon());
		resClaimInfo.setEnterpriseName(enterprise.getName());
		return resClaimInfo;
	}

	@Override
	@Transactional
	public void claimStaffByShare(ReqAddStaffByShare reqAddStaffByShare) {
		String inviteStaffId = reqAddStaffByShare.getInviteStaffId();
		String openid = reqAddStaffByShare.getOpenid();

		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);

		// 1、判断openid是否存在数据库
		UserInfo userInfo = userInfoMapper.selectByOpenId(openid);
		if (userInfo == null) {
			userInfo = new UserInfo();
			userInfo.setId(UuidUtil.getRandomUuidWithoutSeparator());
			userInfo.setOpenId(openid);
			userInfo.setHeadIcon(reqAddStaffByShare.getAvatarUrl());
			userInfo.setNickName(reqAddStaffByShare.getNickName());
			userInfo.setWxSoftId(sysConfig.getPlatformMiniAppId());
			Staff shareStaff = staffInfoMapper.selectByPrimaryKey(reqAddStaffByShare.getStaffId());// 查询分享分的userid
			UserInfo shareUserInfo = userInfoMapper.selectByOpenId(shareStaff.getOpenid());
			userInfo.setFromUserId(shareUserInfo.getId());
			userInfo.setCreateTime(new Date());
			userInfo.setModifyTime(new Date());
			userInfoMapper.insertSelective(userInfo);
		}
		// 2、判断手机号是否和邀请人输入的手机号相同
		Staff inviteStaff = staffInfoMapper.selectByPrimaryKey(inviteStaffId);
		if (inviteStaff == null) {
			throw new BusinessException(-1, "没有记录,请与邀请人联系");
		}
		if (!inviteStaff.getPhone().equals(reqAddStaffByShare.getPhone())) {
			throw new BusinessException(-1, "与邀请人填的手机号不同,请与邀请人联系");
		}
		if (inviteStaff.getStatus() == 1) {
			throw new BusinessException(-1, "该名片已经被认领,请与邀请人联系");
		}

		// 3、修改数据库状态
		inviteStaff.setStatus(1);// 修改为正常状态
		inviteStaff.setHeadIcon(reqAddStaffByShare.getAvatarUrl());
		inviteStaff.setOpenid(openid);
		inviteStaff.setHeadIcon(userInfo.getHeadIcon());
		inviteStaff.setModifyTime(new Date());
		inviteStaff.setOpenAi(1);
		staffInfoMapper.updateByPrimaryKeySelective(inviteStaff);

		//Add by PLH at 2018-11-28 for 一个企业微信用户对应多个名片(多个部门) Begin
		//同时要设置这个员工多个部门权限
		List<String> QYWXDeptIdList = staffInfoMapper.getWxDeptIdsByPhone(sysConfig.getPlatformCorpId(), reqAddStaffByShare.getPhone());
		int[] department_Ids = new int[QYWXDeptIdList.size() + 1];
		for (int i = 0; i < QYWXDeptIdList.size(); i++) {
			String loopQYWXID = QYWXDeptIdList.get(i);
			department_Ids[i] = Integer.parseInt(loopQYWXID);
		}
		// 补上新部门的权限
		department_Ids[QYWXDeptIdList.size()] = Integer.parseInt(inviteStaff.getDepartmentId());

		ReqUpdateStaffInfo reqUpdateStaffInfo = new ReqUpdateStaffInfo();
		reqUpdateStaffInfo.setPhone(reqAddStaffByShare.getPhone());
		reqUpdateStaffInfo.setDepartment_id(department_Ids);
		reqUpdateStaffInfo.setName(inviteStaff.getName());
		reqUpdateStaffInfo.setWx_id(inviteStaff.getWxId());

		Employee employee = new Employee(reqUpdateStaffInfo);
		int QYWX_RETCODE = new JSONObject(staffInfoService.updateApi(employee, reqAddStaffByShare.getEnterpriseId())).getInt("errcode");
		if (QYWX_RETCODE != 0) {
			throw new BusinessException(ErrCode.LOSE_NUM, "微信错误: " + QYWX_RETCODE);
		}
		//Add by PLH at 2018-11-28 for 一个企业微信用户对应多个名片(多个部门) End

		// 4、为自己添加user_staff_rela关系
		UserStaffRela userStaffRela = new UserStaffRela();
		userStaffRela.setId(UuidUtil.getRandomUuid());
		userStaffRela.setCreateTime(new Date());
		userStaffRela.setEnterpriseId(reqAddStaffByShare.getEnterpriseId());//添加企业ID
		userStaffRela.setModifyTime(new Date());
		userStaffRela.setFroms(2);//名片来源
		userStaffRela.setStaffId(inviteStaff.getId());//自己员工id
		userStaffRela.setUserId(userInfo.getId());//自己用户id
		userStaffRela.setDepartmentId(inviteStaff.getDepartmentId());
		userStaffRelaMapper.insertSelective(userStaffRela);

		userInfo.setLastStaffId(inviteStaff.getId()); // 把最后查看的名片设为自己
		userInfoMapper.updateByPrimaryKeySelective(userInfo);

		// 7、修改企业名片数量+1
		enterpriseService.updataEnterpriseStaffNum(reqAddStaffByShare.getEnterpriseId(),1);
	}

	@Override
	public boolean addWebsite(Website website) {
		int rs = staffInfoMapper.addWebsite(website);
		if (rs > 0) {
			System.out.println(website.getId());
			System.out.println(website.getEnterprise_id());
			staffInfoMapper.updEnterpriseWebsite(website);
		}
		return rs > 0 ? true : false;
	}

	@Override
	public List<Website> getWebsiteById(String enter) {
		return staffInfoMapper.getWebsiteByEnterpriseId(enter);
	}

	@Override
	public List<WebsiteTemplate> getWebsiteComponentById(String id) {
		return staffInfoMapper.getWebsiteComponentById(id);
	}

	@Override
	public boolean updWebsiteComponent(List<WebsiteTemplate> list, String id) {
		staffInfoMapper.delWebsiteComponentById(id);
		int rs = 0;
		if (list != null && list.size() > 0)
			rs = staffInfoMapper.insWebsiteComponent(list);
		else
			rs = 1;
		return rs > 0 ? true : false;
	}

	/**
	 * 获取相同名字的企业列表
	 *
	 * @param enterpriseName
	 * @return
	 */
	@Override
	public List<ResGetSameCompanyNameList> getSameCompanyNameList(String enterpriseName) {
		return enterpriseMapper.getSameCompanyNameList(enterpriseName);
	}

	/**
	 * 小程序用户通过搜索加入企业
	 *
	 * @param reqEnterList
	 */
	@Override
	@Transactional
	public void addStaffBySrearch(ReqAddEnterprise reqEnterList) {
		String enterpriseId = reqEnterList.getId();
		String phone = reqEnterList.getPhone();
		// 判断手机号是否存在该企业中
		Staff staff1 = staffInfoMapper.getStaffByEnterpriseIdAndPhone(enterpriseId, phone);
		if (staff1 != null && staff1.getStatus() == 4) {
			// 反正不让加入，只是告诉用户拒绝原因
			throw new BusinessException(-1, "您曾经被管理员拒绝,请联系管理员");
		}
		if (staff1 != null) {
			throw new BusinessException(-1, "您已经加入过该企业,请勿重新加入");
		}
		Staff staff2 = staffInfoMapper.getStaffByEnterpriseIdAndOpenid(enterpriseId, reqEnterList.getOpenid(), "1");
		if (staff2 != null) {
			throw new BusinessException(-1, "您已经加入过该企业,请勿重新加入");
		}
		Deploy deploy = deployMapper.selectAll(enterpriseId);
		String isExistStaff = staffInfoMapper.isExistStaffByEntWxAndPhone(deploy.getCorpid(), phone);
		ReqAddStaffInfo reqAddStaffInfo = new ReqAddStaffInfo();
		reqAddStaffInfo.setEnterprise_id(enterpriseId);
		reqAddStaffInfo.setDepartment_id(Integer.parseInt(sysConfigMapper.selectByPrimaryKey(1).getPlatformDefaultDeptId())); // 此处部门id为平台统一建的部门,一个定值
		reqAddStaffInfo.setName(reqEnterList.getPeople_name());
		reqAddStaffInfo.setPhone(phone);
		reqAddStaffInfo.setIsPlatform(1); // 平台模式 用于区分生成限量的二维码还是不限量的二维码
		RspStaffId staffId = null;
		try {
			if (StringUtils.isEmpty(isExistStaff) == true || isExistStaff.equals("1") == false) {
				//本地数据库中还没有这个员工
				// 企业微信: 增加员工
				staffId = staffInfoService.addStaff(reqAddStaffInfo, PlatformEnterprise.ID, true);
			} else {
				// 本地数据库存在相同手机号，此时不需要在企业微信中添加员工
				staffId = staffInfoService.addStaff(reqAddStaffInfo, PlatformEnterprise.ID, false);
			}
			Staff staff = new Staff();
			staff.setId(staffId.getStaff_id());
			staff.setWxId(staffId.getWx_id());
			// 获取企业根部门id
			int departmentId = this.getDepartmentList(enterpriseId, "0").get(0).getId();
			staff.setDepartmentId(String.valueOf(departmentId));
			staff.setStation(reqEnterList.getPosition() == null ? "" : reqEnterList.getPosition());
			staff.setHeadIcon(reqEnterList.getHead_icon());
			staff.setWeixin(reqEnterList.getWeixin());
			staff.setOpenid(reqEnterList.getOpenid());
			staff.setSignature(reqEnterList.getSelf_introduction());
			staff.setMail(reqEnterList.getEmail());
			staff.setAddress(reqEnterList.getAddress());

			staff.setRole("2"); // 员工角色
			staff.setStatus(3); // 表示暂停状态 -- 待审核
			staff.setModifyTime(new Date());
			staff.setCreateTime(new Date());
			staff.setFirstLetter(ChineseFCUtil.cn2py(reqEnterList.getPeople_name()));
			staffInfoMapper.updateByPrimaryKeySelective(staff);

			// 为自己添加user_staff_rela关系
			UserInfo userInfo = userInfoMapper.selectByOpenId(reqEnterList.getOpenid());
			UserStaffRela userStaffRela = new UserStaffRela();
			userStaffRela.setId(UuidUtil.getRandomUuid());
			userStaffRela.setCreateTime(new Date());
			userStaffRela.setEnterpriseId(enterpriseId);//添加企业ID
			userStaffRela.setModifyTime(new Date());

			userStaffRela.setFroms(2);//名片来源
			userStaffRela.setStaffId(staff.getId());//自己员工id
			userStaffRela.setUserId(userInfo.getId());//自己用户id
			userStaffRela.setDepartmentId(staff.getDepartmentId());
			userStaffRelaMapper.insertSelective(userStaffRela);

			// 给管理员发送短信
			// 获取企业管理员和运营者
			List<String> phoneList = staffInfoMapper.getManagerPhoneByEnterpriseId(enterpriseId);
			// 获取企业名字
			Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(enterpriseId);
			String[] param = {enterprise.getName()};
			Integer templateId = smsConfig.getTemplate().get("check_pending");
			for (String sendPhone : phoneList) {
				sendSMSService.sendSMS(sendPhone, param, templateId);
			}
		} catch (Exception e) {
		}
	}

	@Override
	public RspApproveList getApproveList(String enterpriseId) {
		RspApproveList list = new RspApproveList();
		list.setPendingStaffList(staffInfoMapper.getPendingStaffList(enterpriseId));
		list.setApproveStaffList(staffInfoMapper.getApproveStaffList(enterpriseId));
		return list;
	}

	/**
	 * 处理审核结果
	 *
	 * @param approveList
	 */
	@Override
	@Transactional
	public void checkApprove(ReqCheckApprove approveList) {
		String result = approveList.getResult();
		String handlerStaffId = approveList.getHandleStaffId();
		List<String> staffIdList = approveList.getStaffIdList();
		String enterpriseName = staffInfoMapper.getEnterpriseName(handlerStaffId);
		Integer templateId = smsConfig.getTemplate().get("check_result");
		Integer status;
		if ("1".equals(result)) {
			// 同意加入
			status = 1;
		} else {
			// 不同意加入
			status = 4;
		}
		for (String staffId : staffIdList) {
			Staff staff = staffInfoMapper.selectByPrimaryKey(staffId);
			staff.setStatus(status);
			staff.setAuditStaffId(handlerStaffId);
			staff.setAuditTime(new Date());
			String[] param = new String[2];
			if (status == 1) {
				// 加入企业微信
				//Add by PLH at 2018-11-28 for 一个企业微信用户对应多个名片(多个部门) Begin
				//同时要设置这个员工多个部门权限
				SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);
				List<String> QYWXDeptIdList = staffInfoMapper.getWxDeptIdsByPhone(sysConfig.getPlatformCorpId(), staff.getPhone());
				int[] department_Ids = new int[QYWXDeptIdList.size() + 1];
				for (int i = 0; i < QYWXDeptIdList.size(); i++) {
					String loopQYWXID = QYWXDeptIdList.get(i);
					department_Ids[i] = Integer.parseInt(loopQYWXID);
				}
				// 补上新部门的权限
				department_Ids[QYWXDeptIdList.size()] = Integer.parseInt(staff.getDepartmentId());

				ReqUpdateStaffInfo reqUpdateStaffInfo = new ReqUpdateStaffInfo();
				reqUpdateStaffInfo.setPhone(staff.getPhone());
				reqUpdateStaffInfo.setDepartment_id(department_Ids);
				reqUpdateStaffInfo.setName(staff.getName());
				reqUpdateStaffInfo.setWx_id(staff.getWxId());

				Employee employee = new Employee(reqUpdateStaffInfo);
				int QYWX_RETCODE = new JSONObject(staffInfoService.updateApi(employee, staff.getEnterpriseId())).getInt("errcode");
				if (QYWX_RETCODE != 0) {
					throw new BusinessException(ErrCode.LOSE_NUM, "微信错误: " + QYWX_RETCODE);
				}
				//Add by PLH at 2018-11-28 for 一个企业微信用户对应多个名片(多个部门) End
				staff.setOpenAi(1);
				param[0] = "同意";
			} else if (status == 4) {
				// 判断该员工最后查看名片是不是自己
				UserInfo userInfo = userInfoMapper.selectByOpenId(staff.getOpenid());
				if (userInfo.getLastStaffId().equals(staffId)) {
					// 最后一次查看是自己，修改成其他企业的名片
					Staff staff1 = staffInfoMapper.getRandomStaffByOpenId(staff.getOpenid());
					userInfo.setLastStaffId(staff1.getId());
					userInfoMapper.updateByPrimaryKeySelective(userInfo);
				}
				// 删除user_staff_rela表中所有自己的关系
				userStaffRelaMapper.deleteByStaff(staffId);
				param[0] = "拒绝";
			}
			staffInfoMapper.updateByPrimaryKeySelective(staff);
			// 发送审核结果给用户
			param[1] = enterpriseName;
			sendSMSService.sendSMS(staff.getPhone(), param, templateId);
		}
		// 修改企业名片数量
		if (status == 1) {
			Staff staff = staffInfoMapper.selectByPrimaryKey(handlerStaffId);
			enterpriseService.updataEnterpriseStaffNum(staff.getEnterpriseId(),staffIdList.size());
		}
	}

	/**
	 * 获取StaffId
	 *
	 * @param enterprise
	 * @param openid
	 * @return
	 */
	@Override
	public String getStaffId(String enterprise, String openid) {
		Staff staff = staffInfoMapper.getStaffByEnterpriseIdAndOpenid(enterprise, openid, "1");
		return staff.getId();
	}

	/**
	 * 获取StaffId
	 *
	 * @param openid
	 * @return
	 */
	@Override
	public RepMyCardInfo getMyselfCardInfo(String openid) {
		RepMyCardInfo repMyCardInfo = staffInfoMapper.getMyselfInfo(openid);
		return repMyCardInfo;
	}

	/**
	 * 获取StaffId
	 *
	 * @param openid
	 * @return
	 */
	@Override
	public List<RepMyselfCardList> getMyselfCardList(String openid, HttpServletRequest request) {
		List<RepMyselfCardList> list = staffInfoMapper.getMyselfListByOpenId(openid);
		String userId = userInfoMapper.findUserId(openid);
		// 将第一张有效名片的信息保存在Redis中
		for (RepMyselfCardList mycard : list) {
			if (mycard.getEnterprise_status() == 1 && mycard.getStaff_status() == 1 && mycard.getId().length() > 10) {
				RspCardInfo resp = staffInfoService.getById(mycard.getEnterprise_id(), mycard.getId(), userId);
				String taken = request.getHeader(Constants.SESSION_ID);
				UserDataUtil.setMyStaffId(taken,resp.getStaffId());
				UserDataUtil.setMyDepartmentId(taken, resp.getDepartment_id());
				UserDataUtil.setMyEnterpriseId(taken,resp.getEnterprise_id());
				UserDataUtil.setEnterpriseId(taken,resp.getEnterprise_id());
				break;
			}
		}
		return list;
	}

	@Override
	public int getPendingPerson(String enterpriseId) {

		return staffInfoMapper.getPendingPerson(enterpriseId);
	}

	/**
	 * 删除七牛云的资源
	 *
	 * @param resourceName
	 * @return
	 */
	@Override
	public boolean deleteResources(String resourceName) {
		// 从Url中提取文件名
		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);
		String fileName = resourceName.replace(sysConfig.getQiniuUrl() + "/", "");
		return qiniuUtils.deteteBucketFile(fileName);
	}

	@Override
	public RepEnterprise getEnterprise(String staffId) {
		Staff staff = staffInfoMapper.selectByPrimaryKey(staffId);
		RepEnterprise rep = new RepEnterprise();
		rep.setEnterpriseId(staff.getEnterpriseId());
		rep.setDepartmentId(staff.getDepartmentId());
		return rep;
	}

	@Override
	public Integer isExitMoreStaff(String enterpriseId) {
		Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(enterpriseId);
		return enterprise.getSaleCardNum();
	}

	@Override
	public Integer updateCardImgUrl(String staffId, String cardImgUrl) {
		return staffInfoMapper.updataCardImgUrl(staffId, cardImgUrl);
	}

	/**
	 * 获取用户的雷达权限（ai雷达，boss雷达）
	 * @param staffId
	 * @return
	 */
	@Override
	public RepRadarPermissions getRadarPermission(String staffId) {
		return staffInfoMapper.getRadarPermission(staffId);
	}


	@Override
	public RspPage<RepRecommendCard> getRecommendCardList(Page page, String openId) {
		RspPage<RepRecommendCard> repPage = new RspPage<RepRecommendCard>();
		PageHelper.startPage(page.getPage_num(), page.getPage_size(), true);
		List<RepRecommendCard> list = staffInfoMapper.getRecommendCardToMainPage(openId);
		PageInfo<RepRecommendCard> gitPage = new PageInfo<>(list);
		repPage.setData_list(list);
		repPage.setPage_num(gitPage.getPageNum());
		repPage.setPage_size(gitPage.getPageSize());
		repPage.setTotal_num(gitPage.getTotal());
		repPage.setHaveNextPage(gitPage.isHasNextPage());
		return repPage;
	}
}
