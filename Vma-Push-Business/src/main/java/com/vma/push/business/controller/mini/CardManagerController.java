package com.vma.push.business.controller.mini;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.config.PlatformEnterprise;
import com.vma.push.business.dto.AttachmentImage;
import com.vma.push.business.dto.CardResumeInfo;
import com.vma.push.business.dto.Website;
import com.vma.push.business.dto.WebsiteTemplate;
import com.vma.push.business.dto.req.Page;
import com.vma.push.business.dto.req.ReqResouceName;
import com.vma.push.business.dto.req.mini.*;
import com.vma.push.business.dto.req.superback.ReqAddEnterprise;
import com.vma.push.business.dto.rsp.RspDepartTreeNew;
import com.vma.push.business.dto.rsp.RspEnterprise;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.mini.*;
import com.vma.push.business.dto.rsp.staff.RspCardInfo;
import com.vma.push.business.dto.rsp.userInfo.RspUserLabelInfo;
import com.vma.push.business.entity.Enterprise;
import com.vma.push.business.entity.EnterpriseFile;
import com.vma.push.business.entity.EnterpriseFileDir;
import com.vma.push.business.service.*;
import com.vma.push.business.util.IndexCreate;
import com.vma.push.business.util.TlsUtil;
import com.vma.push.business.util.UserDataUtil;
import com.vma.push.business.util.UuidUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * @author lql
 * @Description: 管名片的相关api
 * @date 2018-10-18 16:10
 */
@RestController
@RequestMapping("/v2.0")
@Api(value = "微信小程序", description = "小程序--管名片相关api", tags = {"CardManagerController"})
public class CardManagerController extends ControllerExceptionHandler {
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private IndexCreate indexCreate;
	@Autowired
	private ISuperEnterpriseService enterpriseService;
	@Autowired
	private IStaffInfoService staffInfoService;
	@Autowired
	private ICardManageService cardManageService;
	@Autowired
	private IDepartmentService departmentService;
	@Autowired
	private TlsUtil tlsUtil;
	@Autowired
	private IFileManageService fileManageService;

	@ApiOperation(value = "微信用户创建企业", notes = "微信用户创建企业")
	@ApiImplicitParam(name = "reqEnterList", value = "微信用户创建企业", required = true, dataType = "ReqAddEnterprise")
	@RequestMapping(value = "/mini/card/addCard", method = RequestMethod.POST)
	public void addCompany(@RequestBody ReqAddEnterprise reqEnterList) {
		logger.info("新增企业");
		reqEnterList.setType(5);
		reqEnterList.setRole(1);
		String id = UuidUtil.getRandomUuid();
		String enterprise_no = indexCreate.getIndex("AE", "AE");
		reqEnterList.setEnterprise_no(enterprise_no);
		// 微信创建企业默认平台为代理商
		reqEnterList.setParent_id(PlatformEnterprise.ID);
		reqEnterList.setId(id);
		reqEnterList.setTemplate_id("1001");
		// 迹点操作者设置为平台
		reqEnterList.setCreate_by("1");
		reqEnterList.setMoney_init(1);
		reqEnterList.setCreate_scene(1);
		reqEnterList.setName(reqEnterList.getEnterprise_name()); // 在V1.0版本中name为公司名字; V2.0中小程序需从前台传人名和公司名
		logger.info(reqEnterList);
		enterpriseService.addEnterAndAdmin(reqEnterList);
	}

	@ApiOperation(value = "获取用户详情", notes = "获取用户详情")
	@ApiImplicitParam(name = "RspCardInfo", value = "获取用户详情", required = true, dataType = "ReqAddEnterprise")
	@RequestMapping(value = "/mini/card/getStaffInfo/{openid}", method = RequestMethod.GET)
	public RspCardInfo getStaffInfo(@PathVariable("openid") String openid, HttpServletRequest request) {
		return staffInfoService.getStaffInfoByOpenid(openid, request);
	}

	/**
	 * 销售人员向用户发送消息
	 *
	 * @param
	 */
	@ApiOperation(value = "发送短信", notes = "发送短信")
	@RequestMapping(value = "/mini/sendMsg/{phone}", method = RequestMethod.GET)
	public String sendMsg(@PathVariable("phone") String phone) {
		return cardManageService.sendMsg(phone);
	}

	/**
	 * 销售人员向用户发送消息
	 *
	 * @param
	 */
	@ApiOperation(value = "验证短信", notes = "验证短信")
	@RequestMapping(value = "/mini/checkMsg/{phone}/{msgCode}", method = RequestMethod.GET)
	public String checkMsg(@PathVariable("phone") String phone, @PathVariable("msgCode") String msgCode) {
		return cardManageService.checkMsg(phone, msgCode);
	}

	@ApiOperation(value = "解密手机号", notes = "解密手机号")
	@RequestMapping(value = "/mini/card/getPhone", method = RequestMethod.POST)
	public String getPhone(@RequestBody ReqGetPhone reqGetPhone, HttpServletRequest request) {
		String phone = cardManageService.getPhone(reqGetPhone, request);
		return phone;
	}

	@ApiOperation(value = "更新名片基本信息", notes = "更新名片基本信息")
	@RequestMapping(value = "/mini/card/updataCardBaseInfo", method = RequestMethod.POST)
	public String updataCardBaseInfo(@RequestBody ReqCardBaseInfo reqCard) {
		return cardManageService.updataCardBaseInfo(reqCard);
	}

	@ApiOperation(value = "获取企业列表", notes = "获取企业列表")
	@RequestMapping(value = "/mini/card/getEnterpriseList/{openid}", method = RequestMethod.GET)
	public List<RspEnterpriseList> getEnterpriseList(@PathVariable("openid") String openid) {
		return cardManageService.getEnterpriseList(openid);
	}

	@ApiOperation(value = "解散企业", notes = "解散企业")
	@RequestMapping(value = "/mini/closeEnterprise/{enterpriseId}", method = RequestMethod.GET)
	public String dissolveCompany(@PathVariable("enterpriseId") String enterpriseId, HttpServletRequest request) {
		int result = cardManageService.dissolveCompany(enterpriseId, request);
		if (result > 0) {
			return "ok";
		} else {
			throw new BusinessException(000, "解散企业失败");
		}
	}

	/**
	 * 获取当前企业
	 *
	 * @return
	 */
	@ApiOperation(value = "获取企业详情", notes = "获取企业详情")
	@RequestMapping(value = "/mini/card/getCurrentEnterpriseInfo/{enterprisId}", method = RequestMethod.GET)
	public RspEnterprise getCurrentEnterpriseInfo(@PathVariable("enterprisId") String enterprisId) {
		RspEnterprise rspEnterprise = new RspEnterprise();
		Enterprise enterprise = cardManageService.getCurrentEnterpriseInfo(enterprisId);
		rspEnterprise.setId(enterprise.getId());
		rspEnterprise.setName(enterprise.getName());
		rspEnterprise.setHead_icon(enterprise.getHeadIcon());
		rspEnterprise.setSale_card_num(enterprise.getSaleCardNum());
		rspEnterprise.setManager_name(enterprise.getManagerName());
		System.out.println(enterprise.getMoneyInit());
		rspEnterprise.setMoney_init(enterprise.getMoneyInit());
		return rspEnterprise;
	}

	/**
	 * 获取部门列表及人数
	 *
	 * @return
	 */
	@ApiOperation(value = "获取公司在部门表的id", notes = "获取公司在部门表的id")
	@RequestMapping(value = "/mini/card/getRootDptId/{enterpriseId}", method = RequestMethod.GET)
	public Integer getRootDptId(@PathVariable("enterpriseId") String enterpriseId) {
		return cardManageService.getDepartmentList(enterpriseId, "0").get(0).getId();
	}

	/**
	 * 获取部门列表及人数
	 *
	 * @return
	 */
	@ApiOperation(value = "获取部门列表及人数", notes = "获取部门列表及人数")
	@RequestMapping(value = "/mini/card/getDepartmentList/{enterpriseId}/{parentId}", method = RequestMethod.GET)
	public List<RepDepartmentList> getDepartmentList(@PathVariable("enterpriseId") String enterpriseId, @PathVariable("parentId") String parentId) {
		return cardManageService.getDepartmentList(enterpriseId, parentId);
	}

	/**
	 * 获取部门人员列表
	 *
	 * @param enterpriseId
	 * @param parentId
	 * @return
	 */
	@ApiOperation(value = "获取部门人员列表", notes = "获取部门人员列表")
	@RequestMapping(value = "/mini/card/getDepartmentPerson/{enterpriseId}/{parentId}", method = RequestMethod.GET)
	public RspDepartmentPersonList getDepartmentPerson(@PathVariable("enterpriseId") String enterpriseId, @PathVariable("parentId") String parentId) {
		RspDepartmentPersonList personList = new RspDepartmentPersonList();
		// 在职员工
		personList.setOnJobPersonList(cardManageService.getDepartmentPerson(enterpriseId, parentId, "1"));
		if (!"-1".equals(parentId)) {
			// 离职员工
			personList.setDeparturePersonList(cardManageService.getDepartmentPerson(enterpriseId, parentId, "0"));
			// 临时申请员工
			personList.setTemporaryPersonList(cardManageService.getDepartmentPerson(enterpriseId, parentId, "2"));
		}
		return personList;
	}

	@ApiOperation(value = "模糊查询部门人员", notes = "模糊查询部门人员")
	@RequestMapping(value = "/mini/card/getDepartmentPersonByName/{enterpriseId}", method = RequestMethod.GET)
	public RspDepartmentPersonList getDepartmentPersonByName(@PathVariable("enterpriseId") String enterpriseId, @RequestParam String name) {
		RspDepartmentPersonList personList = new RspDepartmentPersonList();
		personList.setOnJobPersonList(cardManageService.getDepartmentPersonByName(enterpriseId, name));
		return personList;
	}

	@ApiOperation(value = "获取企业离职人员列表", notes = "获取企业离职人员列表")
	@RequestMapping(value = "/mini/card/getDeparturePersonList/{enterpriseId}", method = RequestMethod.GET)
	public RspDepartmentPersonList getDeparturePersonList(@PathVariable("enterpriseId") String enterpriseId) {
		RspDepartmentPersonList personList = new RspDepartmentPersonList();
		personList.setDeparturePersonList(cardManageService.getDepartmentPerson(enterpriseId, "null", "0"));
		return personList;
	}

	/**
	 * 获取部门列表及人数
	 *
	 * @return
	 */
	@ApiOperation(value = "切换企业", notes = "切换企业")
	@RequestMapping(value = "/mini/card/switchCompany/{enterpriseId}/{openid}", method = RequestMethod.GET)
	public RspCardInfo switchCompany(@PathVariable("enterpriseId") String enterpriseId, @PathVariable("openid") String openid, HttpServletRequest request) {
		cardManageService.switchCompany(enterpriseId, openid, request);
		return staffInfoService.getStaffInfoByOpenid(openid, request);
	}

	/**
	 * 新增部门
	 *
	 * @return
	 */
	@ApiOperation(value = "新增部门", notes = "新增部门")
	@RequestMapping(value = "/mini/card/addDepartment", method = RequestMethod.POST)
	public String addDepartment(@RequestBody ReqAddDepartment eqAddDepartment) {
		return cardManageService.addDepartment(eqAddDepartment);
	}

	/**
	 * 新增部门
	 *
	 * @return
	 */
	@ApiOperation(value = "删除部门", notes = "删除部门")
	@RequestMapping(value = "/mini/card/deleteDepartment", method = RequestMethod.POST)
	public void deleteDepartment(@RequestBody ReqAddDepartment deleteDepartment) {
		cardManageService.deleteDepartment(deleteDepartment);
	}

	/**
	 * 新增部门
	 *
	 * @return
	 */
	@ApiOperation(value = "更新部门", notes = "更新部门")
	@RequestMapping(value = "/mini/card/updataDepartment", method = RequestMethod.POST)
	public void updataDepartment(@RequestBody ReqAddDepartment updataDepartment) {
		cardManageService.updataDepartment(updataDepartment);
	}

	/**
	 * 修改企业名字和logo
	 */
	@ApiOperation(value = "修改公司名字和logo", notes = "修改公司名字和logo")
	@RequestMapping(value = "/mini/card/updataCompanyNameOrLogo", method = RequestMethod.POST)
	public void updataCompanyNameOrLogo(@RequestBody ReqCompanyNameLogo companyNameLogo) {
		cardManageService.updataCompanyNameOrLogo(companyNameLogo);
	}

	/**
	 * 获取小程序用户身份
	 */
	@ApiOperation(value = "获取小程序用户身份", notes = "获取小程序用户身份")
	@RequestMapping(value = "/mini/card/getUserRole/{enterpriseId}/{openid}", method = RequestMethod.GET)
	public String getUserRole(@PathVariable("enterpriseId") String enterpriseId, @PathVariable("openid") String openid) {
		return cardManageService.getUserRelo(enterpriseId, openid);
	}

	/**
	 * 更新部门员工信息
	 */
	@ApiOperation(value = "更新部门员工信息", notes = "更新部门员工信息")
	@RequestMapping(value = "/mini/card/dept/updataDptPerson", method = RequestMethod.POST)
	public void updataDptPerson(@RequestBody ReqUpdataDptPerson reqUpdataDptPerson) {
		cardManageService.updataDptPerson(reqUpdataDptPerson);
	}

	/**
	 * 工作交接
	 */
	@ApiOperation(value = "工作交接", notes = "工作交接")
	@RequestMapping(value = "/mini/card/handover", method = RequestMethod.POST)
	public void handover(@RequestBody ReqHandover reqHandover) {
		cardManageService.handover(reqHandover);
	}

	/**
	 * 离职员工复职
	 */
	@ApiOperation(value = "离职员工复职", notes = "离职员工复职")
	@RequestMapping(value = "/mini/card/reentry/{staffId}", method = RequestMethod.GET)
	public void reentry(@PathVariable("staffId") String staffId) {
		cardManageService.reentry(staffId);
	}

	/**
	 * Created by ljh on 2018/10/24.
	 * begin
	 */

	@ApiOperation(value = "获取个人简介、语音、标签、照片", notes = "取个人简介、语音、标签、照片")
	@RequestMapping(value = "/mini/card/resume/{staffId}/{userId}", method = RequestMethod.GET)
	public CardResumeInfo resume(@PathVariable("staffId") String staffId, @PathVariable("userId") String userId) {
		CardResumeInfo rspcri = new CardResumeInfo();
		rspcri.setStaffId(staffId);
		rspcri.setUserId(userId);
		rspcri.setLabels(staffInfoService.getStaffLabelAndReferLabelInfo(staffId, userId));
		rspcri.setImages(staffInfoService.getStaffImages(staffId));
		rspcri.setAudio(staffInfoService.getStaffAudio(staffId));
		return rspcri;
	}

	@ApiOperation(value = "保存个人简介、语音、标签、照片", notes = "保存个人简介、语音、标签、照片")
	@RequestMapping(value = "/mini/card/resume/save", method = RequestMethod.POST)
	public Integer saveResume(@RequestBody CardResumeInfo cardResumeInfo) {
		return staffInfoService.saveResume(cardResumeInfo);
	}

	@ApiOperation(value = "获取员工标签", notes = "获取个人标签")
	@RequestMapping(value = "/mini/card/resume/label/{staffId}/{userId}", method = RequestMethod.GET)
	public List<RspUserLabelInfo> resumeLabel(@PathVariable("staffId") String staffId, @PathVariable("userId") String userId) {
		return staffInfoService.getStaffLabelAndReferLabelInfo(staffId, userId);
	}

	@ApiOperation(value = "获取员工图片", notes = "获取员工图片")
	@RequestMapping(value = "/mini/card/resume/images/{staffId}", method = RequestMethod.GET)
	public List<AttachmentImage> resumeImages(@PathVariable("staffId") String staffId) {
		return staffInfoService.getStaffImages(staffId);
	}

	@ApiOperation(value = "获取部门树", notes = "获取部门树")
	@RequestMapping(value = "/mini/card/dept/tree/{enterpriseId}", method = RequestMethod.GET)
	public List<RspDepartTreeNew> getDeptTree(@PathVariable("enterpriseId") String enterpriseId) {
		return departmentService.departTreeNew(enterpriseId, "0");
	}

	@ApiOperation(value = "卡片启用", notes = "卡片启用")
	@RequestMapping(value = "/mini/card/on/{staffId}", method = RequestMethod.GET)
	public Integer cardOn(@PathVariable("staffId") String staffId) {
		return cardManageService.cardOn(staffId);
	}

	@ApiOperation(value = "卡片屏蔽", notes = "卡片屏蔽")
	@RequestMapping(value = "/mini/card/off/{staffId}", method = RequestMethod.GET)
	public Integer cardOff(@PathVariable("staffId") String staffId) {
		return cardManageService.cardOff(staffId);
	}
	/**
	 * Created by ljh on 2018/10/24.
	 * end
	 */

	/**
	 * 添加视频
	 *
	 * @param map
	 */
	@ApiOperation(value = "添加视频", notes = "添加视频")
	@RequestMapping(value = "/mini/video/add", method = RequestMethod.POST)
	public boolean addVideo(@RequestBody Map<String, Object> map) {
		String obj = map.get("duration").toString();
		if (!StringUtils.isEmpty(obj))
			map.put("duration", new Double(obj).intValue());
		map.put("id", UuidUtil.removeSeparatorFromUuid(UuidUtil.getRandomUuid()));
		return cardManageService.addVideo(map);
	}

	@ApiOperation(value = "获取视频信息", notes = "获取视频信息")
	@RequestMapping(value = "/mini/video/get", method = RequestMethod.POST)
	public Object getVideo(@RequestBody Map<String, Object> map) {
		System.out.println(map.get("staffId").toString());
		return cardManageService.getVideo(map.get("staffId").toString());
	}

	/**
	 * 更新卡片样式
	 *
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "更新卡片样式", notes = "更新卡片样式")
	@RequestMapping(value = "/mini/card/style/upd", method = RequestMethod.POST)
	public boolean updCardStyle(@RequestBody Map<String, Object> map) {
		System.out.println(map);
		return cardManageService.updCardStyle(map);
	}

	/**
	 * 获取卡片样式
	 *
	 * @param staffId
	 * @return
	 */
	@ApiOperation(value = "获取卡片样式", notes = "获取卡片样式")
	@RequestMapping(value = "/mini/card/style/get")
	public Object getCardStyle(@RequestParam String staffId) {
		return cardManageService.getCardStyle(staffId);
	}

	@ApiOperation(value = "更新名片分享编辑文本", notes = "更新名片分享编辑文本")
	@RequestMapping(value = "/mini/card/share/setup", method = RequestMethod.POST)
	public boolean updShareSetup(@RequestBody Map<String, Object> map) {
		return cardManageService.updShareSetup(map);
	}

	/**
	 * 获取企业管理员和运营者
	 *
	 * @param enterpriseId
	 * @return
	 */
	@ApiOperation(value = "获取企业所有员工", notes = "获取企业所有员工")
	@RequestMapping(value = "/mini/card/getEnterpriseStaffList/{enterpriseId}", method = RequestMethod.GET)
	public List<ResDepartmentPerson> getEnterpriseStaffList(@PathVariable("enterpriseId") String enterpriseId) {
		return cardManageService.getManager(enterpriseId, null);
	}

	/**
	 * 获取企业员工(排除管理员和运营者)
	 *
	 * @param enterpriseId
	 * @return
	 */
	@ApiOperation(value = "获取企业员工(排除管理员和运营者)", notes = "排除管理员和运营者")
	@RequestMapping(value = "/mini/card/getEnterpriseOnlyStaffList/{enterpriseId}", method = RequestMethod.GET)
	public List<ResDepartmentPerson> getEnterpriseOnlyStaffList(@PathVariable("enterpriseId") String enterpriseId) {
		return cardManageService.getManager(enterpriseId, "2");
	}

	/**
	 * 获取企业管理员和运营者
	 *
	 * @param enterpriseId
	 * @return
	 */
	@ApiOperation(value = "获取企业管理员和运营者", notes = "取企业管理员和运营者")
	@RequestMapping(value = "/mini/card/getMamager/{enterpriseId}", method = RequestMethod.GET)
	public RspEnterpriseManager getManager(@PathVariable("enterpriseId") String enterpriseId) {
		RspEnterpriseManager manager = new RspEnterpriseManager();
		manager.setManagerList(cardManageService.getManager(enterpriseId, "0"));
		manager.setOperatorsList(cardManageService.getManager(enterpriseId, "1"));
		return manager;
	}

	/**
	 * 转让管理员
	 *
	 * @return
	 */
	@ApiOperation(value = "转让管理员", notes = "转让管理员")
	@RequestMapping(value = "/mini/card/transferManager", method = RequestMethod.POST)
	public void transferManager(@RequestBody ReqHandover reqHandover) {
		cardManageService.transferManager(reqHandover);
	}

	/**
	 * 增加运营者
	 *
	 * @return
	 */
	@ApiOperation(value = "增加运营者", notes = "增加运营者")
	@RequestMapping(value = "/mini/card/addOperator/{staffId}", method = RequestMethod.GET)
	public void addOperator(@PathVariable("staffId") String staffId) {
		cardManageService.addOperator(staffId);
	}

	/**
	 * 增加运营者
	 *
	 * @return
	 */
	@ApiOperation(value = "移除运营者", notes = "移除运营者")
	@RequestMapping(value = "/mini/card/removeOperator", method = RequestMethod.POST)
	public void removeperator(@RequestBody ReqRemoveOperators operatorList) {
		cardManageService.removeOperator(operatorList);
	}

	/**
	 * 手动增加员工
	 *
	 * @return
	 */
	@ApiOperation(value = "手动增加员工", notes = "手动增加员工")
	@RequestMapping(value = "/mini/card/dept/addstaff", method = RequestMethod.POST)
	public void addDptStaff(@RequestBody ReqUpdataDptPerson reqUpdataDptPerson) {
		cardManageService.addDptStaff(reqUpdataDptPerson);
	}

	/**
	 * 获取邀请详情页面所需内容
	 *
	 * @return
	 */
	@ApiOperation(value = "获取邀请详情页面所需内容", notes = "获取邀请详情页面所需内容")
	@RequestMapping(value = "/mini/card/getInviteInfo", method = RequestMethod.POST)
	public ResInvite getInviteInfo(@RequestBody ReqInvite reqInvite) {
		return staffInfoService.getInviteInfo(reqInvite);
	}

	/**
	 * 获取邀请详情页面所需内容
	 *
	 * @return
	 */
	@ApiOperation(value = "通过分享加入员工", notes = "通过分享加入员工")
	@RequestMapping(value = "/mini/card/addStaffByShare", method = RequestMethod.POST)
	public void addStaffByShare(@RequestBody ReqAddStaffByShare reqAddStaffByShare) {
		cardManageService.addStaffByShare(reqAddStaffByShare);
	}

	/**
	 * 获取员工员工认领页面信息
	 *
	 * @return
	 */
	@ApiOperation(value = "获取员工员工认领页面信息", notes = "获取员工员工认领页面信息")
	@RequestMapping(value = "/mini/card/getClaimInfo/{staffId}", method = RequestMethod.GET)
	public ResClaimInfo getClaimInfo(@PathVariable("staffId") String staffId) {
		return cardManageService.getClaimInfo(staffId);
	}

	/**
	 * 获取邀请详情页面所需内容
	 *
	 * @return
	 */
	@ApiOperation(value = "通过分享认领员工", notes = "通过分享认领员工")
	@RequestMapping(value = "/mini/card/claimStaffByShare", method = RequestMethod.POST)
	public void claimStaffByShare(@RequestBody ReqAddStaffByShare reqAddStaffByShare) {
		cardManageService.claimStaffByShare(reqAddStaffByShare);
	}

	/**
	 * 获取网站列表
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据ID获取网站列表", notes = "根据ID获取网站列表")
	@RequestMapping(value = "/mini/website/get/{id}", method = RequestMethod.GET)
	public Object getWebsiteById(@PathVariable String id) {
		return cardManageService.getWebsiteById(id);
	}

	/**
	 * 添加网站
	 *
	 * @param website
	 * @return
	 */
	@ApiOperation(value = "添加网站")
	@RequestMapping(value = "/mini/website/add", method = RequestMethod.POST)
	public Object addWebsite(@RequestBody Website website) {
		website.setId(UuidUtil.getRandomUuid());
		boolean flag = cardManageService.addWebsite(website);
		return flag ? website : flag;
	}

	@ApiOperation(value = "根据ID获取网站组件", notes = "根据ID获取网站组件")
	@RequestMapping(value = "/mini/website/component/{id}", method = RequestMethod.GET)
	public Object getWebsiteComponentsById(@PathVariable String id) {
		return cardManageService.getWebsiteComponentById(id);
	}

	@ApiOperation(value = "根据ID更新网站组件", notes = "根据ID更新网站组件")
	@RequestMapping(value = "/mini/website/component/upd/{id}", method = RequestMethod.POST)
	public Object updWebsiteComponentsById(@PathVariable String id, @RequestBody List<WebsiteTemplate> list) {
		System.out.println(id);
		int i = 1;
		for (WebsiteTemplate tmp : list) {
			tmp.setWebsite_id(id);
			tmp.setOrder_num(i);
			i++;
			if (StringUtils.isEmpty(tmp.getId()))
				tmp.setId(UuidUtil.getRandomUuid());
		}
		return cardManageService.updWebsiteComponent(list, id);
	}

	/**
	 * 获取相同名字的企业列表
	 *
	 * @return
	 */
	@ApiOperation(value = "获取相同名字的企业列表", notes = "获取相同名字的企业列表")
	@RequestMapping(value = "/mini/card/getSameCompanyName", method = RequestMethod.POST)
	public List<ResGetSameCompanyNameList> getSameCompanyNameList(@RequestBody ReqSomeCompany reqSomeCompany) {
		String enterpriseName = reqSomeCompany.getEnterpriseName();
		return cardManageService.getSameCompanyNameList(enterpriseName);
	}

	/**
	 * 小程序用户通过搜索加入公司
	 *
	 * @return
	 */
	@ApiOperation(value = "小程序用户通过搜索加入公司", notes = "小程序用户通过搜索加入公司")
	@RequestMapping(value = "/mini/card/addStaffBySearch", method = RequestMethod.POST)
	public void addStaffBySearch(@RequestBody ReqAddEnterprise reqEnterList) {
		cardManageService.addStaffBySrearch(reqEnterList);
	}

	/**
	 * 获取申请人员和已审批列表列表
	 *
	 * @return
	 */
	@ApiOperation(value = "获取申请人员和已审批列表列表", notes = "获取申请人员和已审批列表列表")
	@RequestMapping(value = "/mini/card/getApproveList/{enterpriseId}", method = RequestMethod.GET)
	public RspApproveList getApproveList(@PathVariable(value = "enterpriseId") String enterpriseId) {
		return cardManageService.getApproveList(enterpriseId);
	}

	/**
	 * 小程序用户通过搜索加入公司
	 *
	 * @return
	 */
	@ApiOperation(value = "管理员审核申请", notes = "管理员审核申请")
	@RequestMapping(value = "/mini/card/updataApprove", method = RequestMethod.POST)
	public void checkApprove(@RequestBody ReqCheckApprove approveList) {
		cardManageService.checkApprove(approveList);
	}

	/**
	 * 获取申请人员和已审批列表列表
	 *
	 * @return
	 */
	@ApiOperation(value = "获取申请人员和已审批列表列表", notes = "获取申请人员和已审批列表列表")
	@RequestMapping(value = "/mini/card/getStaffByEnterpriseIdAndOpenId/{enterpriseId}/{openid}", method = RequestMethod.GET)
	public String getStaffByEnterpriseIdAndOpenId(@PathVariable(value = "enterpriseId") String enterpriseId, @PathVariable(value = "openid") String openid) {
		return cardManageService.getStaffId(enterpriseId, openid);
	}

	/**
	 * 获取自己的名片列表
	 *
	 * @return
	 */
	@ApiOperation(value = "获取自己名片数量", notes = "获取自己名片数量")
	@RequestMapping(value = "/mini/card/getMyselfInfo/{openid}", method = RequestMethod.GET)
	public RepMyCardInfo getMyselfCardInfo(@PathVariable(value = "openid") String openid) {
		return cardManageService.getMyselfCardInfo(openid);
	}

	/**
	 * 获取自己的名片列表
	 *
	 * @return
	 */
	@ApiOperation(value = "获取自己的名片列表", notes = "获取自己的名片列表")
	@RequestMapping(value = "/mini/card/getMyselfList/{openid}", method = RequestMethod.GET)
	public List<RepMyselfCardList> getMyselfCardList(@PathVariable(value = "openid") String openid, HttpServletRequest request) {
		return cardManageService.getMyselfCardList(openid, request);
	}

	/**
	 * 获取自己的名片列表
	 *
	 * @return
	 */
	@ApiOperation(value = "获取待审批人数", notes = "获取待审批人数")
	@RequestMapping(value = "/mini/card/getPendingPerson/{enterpriseId}", method = RequestMethod.GET)
	public int getPendingPerson(@PathVariable(value = "enterpriseId") String enterpriseId) {
		return cardManageService.getPendingPerson(enterpriseId);
	}

	/**
	 * 删除七牛云上的资源
	 */
	@ApiOperation(value = "删除七牛云上的资源", notes = "删除七牛云上的资源")
	@RequestMapping(value = "/mini/deleteResources", method = RequestMethod.POST)
	public boolean deleteResources(@RequestBody ReqResouceName resouceName) {
		return cardManageService.deleteResources(resouceName.getFileName());
	}

	/**
	 * 删除七牛云上的资源
	 */
	@ApiOperation(value = "获取企业Id和departmentId", notes = "获取企业Id和departmentId")
	@RequestMapping(value = "/mini/getEnterprise/{staffId}", method = RequestMethod.GET)
	public RepEnterprise getEnterprise(@PathVariable("staffId") String staffId) {
		return cardManageService.getEnterprise(staffId);
	}

	/**
	 * 获取员工数量
	 */
	@ApiOperation(value = "获取员工数量", notes = "获取员工数量")
	@RequestMapping(value = "/mini/getStaffNum/{enterpriseId}", method = RequestMethod.GET)
	public Integer getStaffNum(@PathVariable("enterpriseId") String enterpriseId) {
		return cardManageService.isExitMoreStaff(enterpriseId);
	}

	/**
	 * 更新分享名片时的背景图
	 *
	 * @return
	 */

	@ApiOperation(value = "更新分享名片时的背景图", notes = "更新分享名片时的背景图")
	@RequestMapping(value = "/mini/updCardImg", method = RequestMethod.POST)
	public Integer updCardImg(@RequestBody ReqResouceName resouceName, HttpServletRequest request) {
		String staffId = UserDataUtil.getStaffId(request);
		return cardManageService.updateCardImgUrl(staffId, resouceName.getFileName());
	}

	/**
	 * 获取员工的雷达权限
	 *
	 * @param staffId
	 * @return
	 */
	@ApiOperation(value = "获取雷达权限", notes = "获取雷达权限")
	@RequestMapping(value = "/mini/getRadarPermissions/{staffId}", method = RequestMethod.GET)
	public RepRadarPermissions getRadarPermissions(@PathVariable("staffId") String staffId) {
		return cardManageService.getRadarPermission(staffId);
	}

	/**
	 * 获取企业上传的文件目录
	 */
	@ApiOperation(value = "获取企业文件目录", notes = "获取企业文件目录")
	@RequestMapping(value = "/mini/getFileDir/{enterpriseId}", method = RequestMethod.GET)
	public List<EnterpriseFileDir> getFileDir(@PathVariable("enterpriseId") String enterpriseId, HttpServletRequest request) {
		return fileManageService.getFileDirList(enterpriseId);
	}

	/**
	 * 获取文件列表
	 */
	@ApiOperation(value = "获取文件列表", notes = "获取文件列表")
	@RequestMapping(value = "/mini/getFileList", method = RequestMethod.POST)
	public RspPage<EnterpriseFile> getFileList(@RequestParam("dir_id") String dir_id, @RequestBody Page page) {
		return fileManageService.getFileList(page, dir_id);
	}

	/**
	 * 获取推荐名片(用于主页)
	 */
	@ApiOperation(value = "获取推荐名片(用于主页)", notes = "获取推荐名片(用于主页)")
	@RequestMapping(value = "/mini/getRecommendListToMainPage", method = RequestMethod.POST)
	public RspPage<RepRecommendCard> getRecommendCardList(@RequestParam("open_id") String open_id, @RequestBody Page page) {
		return cardManageService.getRecommendCardList(page, open_id);
	}
}
