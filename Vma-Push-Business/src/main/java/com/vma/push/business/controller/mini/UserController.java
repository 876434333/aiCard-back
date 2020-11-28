package com.vma.push.business.controller.mini;

import com.vma.push.business.common.Constants;
import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.dao.StaffMapper;
import com.vma.push.business.dao.UserInfoMapper;
import com.vma.push.business.dto.ReqMiniCirclePage;
import com.vma.push.business.dto.RspProdPage;
import com.vma.push.business.dto.req.*;
import com.vma.push.business.dto.req.staff.ReqCarAction;
import com.vma.push.business.dto.req.store.ReqMyOrderPage;
import com.vma.push.business.dto.req.userInfo.ReqAddUser;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.rsp.mini.RepRecommendCard;
import com.vma.push.business.dto.rsp.mini.ResOpenId;
import com.vma.push.business.dto.rsp.staff.*;
import com.vma.push.business.dto.rsp.staff.RspTemplate;
import com.vma.push.business.dto.rsp.userInfo.DataItem;
import com.vma.push.business.dto.rsp.userInfo.RspUserInfo;
import com.vma.push.business.entity.*;
import com.vma.push.business.service.*;
import com.vma.push.business.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;

import com.vma.push.business.dto.req.ReqCarList;

import java.util.*;

/**
 * Created by chenzui on 2018/5/2.
 */
@RestController
@RequestMapping("/v2.0")
@Api(value = "微信小程序", description = "小程序--相关API", tags = {"UserController"})
public class UserController extends ControllerExceptionHandler {
	private Logger LOG = Logger.getLogger(this.getClass());
	@Autowired
	private IWebsiteService websiteService;
	@Autowired
	private IBossService bossService;
	@Autowired
	private IStaffInfoService staffInfoService;
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IProductService productService;
	@Autowired
	private ICircleCompanyService circleCompanyService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IAdvService advService;
	@Autowired
	private IEnterpriseService enterpriseService;

	@Autowired
	ICircleZanLogService circleZanLogService;

	@Autowired
	ICircleCommentLogService circleCommentLogService;

	@Autowired
	TlsUtil tlsUtil;
	@Autowired
	StaffMapper staffMapper;
	@Autowired
	UserInfoMapper userInfoMapper;
	@Autowired
	IUserStaffService userStaffService;

	@Autowired
	JavaMailSender mailSender;//自动注入
//
//    @ApiOperation(value = "名片列表", notes = "名片列表")
//    @RequestMapping(value = "/mini/card/list", method = RequestMethod.POST)
//    public RspPage<RspStaff> CarList(@RequestBody Page page, HttpServletRequest request){
//        LOG.info("获取名片列表");
////        PageHelper.startPage(page.getPage_num(),page.getPage_size());
////        RspPage<RspStaff> rspStaffPage= staffInfoService.staffListByUserId("1",page.getPage_num(),page.getPage_size());
//        //return rspStaffPage;
//    }

	@ApiOperation(value = "名片列表", notes = "名片列表")
	@RequestMapping(value = "/mini/card/list", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqCarList", value = "查看名片列表", dataType = "ReqCarList")
	public RspPage<RspCarlist> findCarList(@RequestBody ReqCarList reqCarList) {
		return staffInfoService.findStaffList(reqCarList);
	}

	@ApiOperation(value = "员工头像与企业相关信息", notes = "员工头像与企业相关信息")
	@RequestMapping(value = "/mini/enterprise/{staffId}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "staffId", value = "员工ID", required = true, dataType = "String", paramType = "path")
	public RspEnterIconAndWxLogo staffDetail(@PathVariable("staffId") String staffId, HttpServletRequest request) {
		LOG.info("查询员工头像与企业相关信息");
		RspEnterIconAndWxLogo rsp = staffInfoService.findEnterIconAndWxLogo(staffId);
		return rsp;
	}

	@ApiOperation(value = "获取openid", notes = "获取openid")
	@RequestMapping(value = "/mini/getopenid", method = RequestMethod.POST)
	@ApiImplicitParam(name = "repOpenId", value = "获取openid", dataType = "RepOpenId")
	public String getopenid(@RequestBody RepOpenId repOpenId) {
		return staffInfoService.getopenid(repOpenId);
	}


	@ApiOperation(value = "获取openid、默认员工", notes = "获取openid、默认员工")
	@RequestMapping(value = "/mini/default_info", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqOpenIdNew", value = "获取openid", dataType = "ReqOpenIdNew")
	public RspDefalutInfo getopenid(@RequestBody ReqOpenIdNew reqOpenIdNew) {
		return staffInfoService.defaultInfo(reqOpenIdNew);
	}

	//    @ApiOperation(value = "开启屏蔽名片",notes = "开启屏蔽名片")
//    @RequestMapping(value = "/mini/card/action",method = RequestMethod.PUT)
//    public void action(@RequestParam(value = "id",required = true) @ApiParam(value = "id") String id){
//        LOG.info("开启屏蔽名片");
//        String a = id;
//        System.out.println(id);
//        // TODO: 2018/5/8
//    }
	@ApiOperation(value = "开启屏蔽名片", notes = "开启屏蔽名片")
	@RequestMapping(value = "/mini/card/action", method = RequestMethod.PUT)
	@ApiImplicitParam(name = "reqCarAction", required = true, value = "修改广告", dataType = "ReqCarAction")
	public void action(@RequestBody ReqCarAction reqCarAction) {
		userInfoService.updateCarAction(reqCarAction);

	}

	@ApiOperation(value = "员工头像", notes = "员工头像")
	@RequestMapping(value = "/mini/staff/{id}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "id", value = "员工ID", required = true, dataType = "String", paramType = "path")
	public RspStaffIcon getStaffHeadIcon(@PathVariable("id") String id, HttpServletRequest request) {
		LOG.info("查询员工头像");

		RspStaffIcon rsp = staffInfoService.getByHeadIcon(id);

		return rsp;
	}

	@ApiOperation(value = "贴牌商信息", notes = "贴牌商信息")
	@RequestMapping(value = "/mini/oem_info", method = RequestMethod.GET)
	public RspMiniOem oemInfo(HttpServletRequest request) {
		String id = UserDataUtil.getEnterpriseId(request);
		return enterpriseService.oemInfo(id);
	}

	@ApiOperation(value = "名片详情", notes = "名片详情")
	@RequestMapping(value = "/mini/card/{id}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "id", value = "员工ID 需要传session_id", required = true, dataType = "String", paramType = "path")
	public RspCardInfo getStaff(@PathVariable("id") String id, @RequestParam(value = "enterprise_id", required = false) String enterprise_id, HttpServletRequest request) {
		LOG.info("查询名片详情");
		Long start = System.currentTimeMillis();
		LOG.info("开始时间:" + start);
		String enterpriseid;
		if (UserDataUtil.getEnterpriseId(request) == null) {
			enterpriseid = enterprise_id;
		} else {
			enterpriseid = UserDataUtil.getEnterpriseId(request);
		}
		RspCardInfo rspCardInfo = staffInfoService.getById(enterpriseid, id, UserDataUtil.getUserId(request));
		Long end = System.currentTimeMillis();
		LOG.info("结束时间:" + end);

		LOG.info("耗时:" + (end - start));

		return rspCardInfo;
	}

	@ApiOperation(value = "名片详情", notes = "名片详情")
	@RequestMapping(value = "/mini/card/{staffId}/{openid}", method = RequestMethod.GET)
	public RspCardInfo getCardInfo(@PathVariable("staffId") String staffId, @PathVariable("openid") String openid, HttpServletRequest request) {
		Staff staff = staffMapper.selectByPrimaryKey(staffId);
		UserInfo userInfo = userInfoMapper.selectByOpenId(openid);

		RspCardInfo rspCardInfo = staffInfoService.getById(staff.getEnterpriseId(), staffId, userInfo.getId());
		String taken = request.getHeader(Constants.SESSION_ID);
		UserDataUtil.setStaffId(taken, rspCardInfo.getStaffId());
		UserDataUtil.setEnterpriseId(taken, rspCardInfo.getEnterprise_id());
		UserDataUtil.setDepartmentId(taken, rspCardInfo.getDepartment_id());
		int isMyself = userInfoService.updataLastStaffId(staff.getEnterpriseId(), staffId, openid);
		rspCardInfo.setIsMyself(isMyself);
		return rspCardInfo;
	}

	@ApiOperation(value = "名片模板ID", notes = "名片模板ID")
	@RequestMapping(value = "/mini/card/temlate/{id}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "id", value = "员工ID 无需传session_id", required = true, dataType = "String", paramType = "path")
	public RspTemplate getStaffTemlate(@PathVariable("id") String id, HttpServletRequest request) {
		return staffInfoService.queryCardTemlate(id, staffInfoService.selectByPrimaryKey(id).getEnterpriseId());
	}

	@ApiOperation(value = "获取用户的所在企业发布的产品列表", notes = "获取用户的所在发布的产品列表")
	@RequestMapping(value = "/mini/productList", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqProductPage", value = "分页信息 需要传session_id", dataType = "ReqProductPage")
	public RspProdPage staffProduct(@RequestBody ReqProductPage reqProductPage, HttpServletRequest request) {
		String enterid = UserDataUtil.getEnterpriseId(request);
		String userid = UserDataUtil.getUserId(request);
		reqProductPage.setUser_id(userid);
		reqProductPage.setEnterprise_id(enterid);
		RspProdPage repAllProductRspPage = productService.enterpriseProduct(reqProductPage);
		return repAllProductRspPage;
	}

	@ApiOperation(value = "产品详情", notes = "产品详情")
	@RequestMapping(value = "/mini/product/{id}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "id", value = "产品详情参数", paramType = "path", required = true, dataType = "String")
	public RepSpecAndImg getProductById(@PathVariable String id, HttpServletRequest request) {
		LOG.info("根据id查询产品数量");
		String enterprise_id = UserDataUtil.getEnterpriseId(request);
		RepSpecAndImg product = productService.selectProductById(id, enterprise_id);
		return product;
	}

	@ApiOperation(value = "获取所有朋友圈", notes = "获取销售人员以及所属企业的朋友圈")
	@RequestMapping(value = "/mini/Circle", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqMiniCirclePage", value = "分页信息 需要传session_id", required = true, dataType = "ReqMiniCirclePage")
	public RspPage<RspCircleDetail> CirclePage(@RequestBody ReqMiniCirclePage reqMiniCirclePage, HttpServletRequest request) {
		LOG.info("获取销售人员以及所属企业的朋友圈");
		reqMiniCirclePage.setUser_id(UserDataUtil.getUserId(request));
		reqMiniCirclePage.setStaff_id(UserDataUtil.getStaffId(request));
		return circleCompanyService.miniCircle(reqMiniCirclePage);
	}

	@ApiOperation(value = "获取朋友圈详情", notes = "获取朋友圈详情")
	@RequestMapping(value = "/mini/CircleDetail/{id}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "id", value = "朋友圈id", required = true, dataType = "String", paramType = "path")
	public RspCircleDetail circleDetail(@PathVariable("id") String id, HttpServletRequest request) {
		LOG.info("获取朋友圈详情");
		String userId = UserDataUtil.getUserId(request);
		return circleCompanyService.minicircleDetail(id, userId);
	}

	@ApiOperation(value = "小程序用户登录", notes = "不存在则注册")
	@RequestMapping(value = "/mini/login", method = RequestMethod.POST)
	public RspMiniInfo addUser(@RequestBody ReqAdduser reqAdduser) {
		LOG.info("小程序用户登录");

		Staff staff = staffInfoService.selectByPrimaryKey(reqAdduser.getEmployee_id());
		reqAdduser.setDepartmentId(staff.getDepartmentId());
		if (reqAdduser.getEnterprise_id() == null || "".equals(reqAdduser.getEnterprise_id().trim())) {
			// Staff staff= staffInfoService.selectByPrimaryKey(reqAdduser.getEmployee_id());
			reqAdduser.setEnterprise_id(staff.getEnterpriseId());
			//reqAdduser.setDepartmentId(staff.getDepartmentId());
		}
		return userInfoService.login(reqAdduser);
	}

	@ApiOperation(value = "下单未付款", notes = "下单未付款")
	@RequestMapping(value = "/mini/order", method = RequestMethod.POST)
	public Map addorder(@RequestBody ReqAddOrder reqAddOrder, HttpServletRequest request) throws Exception {
//        Order order=reqAddOrder.toOrder();
//        //提交订单未付款
//        orderService.insertSelective(order);
		//付款-调用微信支付接口
		// some code...
		String userId = UserDataUtil.getUserId(request);

		reqAddOrder.setUser_id(userId);

		return orderService.commit(reqAddOrder);

	}


	@ApiOperation(value = "付款", notes = "付款")
	@RequestMapping(value = "/mini/order/{id}", method = RequestMethod.POST)
	@ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "String", paramType = "path")
	public Map order(@PathVariable("id") String id, HttpServletRequest request) throws Exception {
		String userId = UserDataUtil.getUserId(request);
		return orderService.orderPay(id, userId);
	}


	@ApiOperation(value = "删除订单", notes = "删除订单")
	@RequestMapping(value = "/mini/order/{id}", method = RequestMethod.DELETE)
	@ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "String", paramType = "path")
	public void delorder(@PathVariable("id") String id) {
		Order order = new Order();
		order.setId(id);
		order.setStatus(0);
		orderService.updateByPrimaryKeySelective(order);
	}

//    @ApiOperation(value = "付款",notes = "付款")
//    @RequestMapping(value = "/mini/order/pay/{id}",method = RequestMethod.DELETE)
//    @ApiImplicitParam(name = "id",value = "订单id",required = true,dataType = "String",paramType = "path")
//    public void payorder(@PathVariable("id") String id){
//        Order order=new Order();
//        order.setId(id);
//        order.setPayTime(new Date());
//        orderService.updateByPrimaryKeySelective(order);
//    }

	@ApiOperation(value = "点赞或取消赞", notes = "点赞或取消赞")
	@RequestMapping(value = "/mini/Zan", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqZan", value = "朋友圈id 需要传session_id", required = true, dataType = "ReqZan")
	public void zan(@RequestBody ReqZan reqZan, HttpServletRequest request) {
		LOG.info("点赞或取消赞");
		CircleZanLog circleZanLog = reqZan.toCircleZanLog();//new CircleZanLog(reqZan);
		circleZanLog.setUserId(UserDataUtil.getUserId(request));
		circleZanLog.setFlag(2);//1公司点赞 0销售点赞 2客户点赞
		circleZanLogService.zan(circleZanLog);
	}

	@ApiOperation(value = "客户评论朋友圈", notes = "客户评论朋友圈")
	@RequestMapping(value = "/mini/Comment", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqAddComment", value = "评论内容 需要传session_id", required = true, dataType = "ReqAddComment")
	public void commentCircle(@RequestBody ReqAddComment reqAddComment, HttpServletRequest request) {
		LOG.info("客户评论朋友圈");
		CircleCommentLog circleCommentLog = reqAddComment.toCircleCommentLog();
		circleCommentLog.setUserId(UserDataUtil.getUserId(request));
		circleCommentLog.setFlag(2);
		circleCommentLogService.circleComment(circleCommentLog);
	}

	@ApiOperation(value = "小程序用户订单列表", notes = "小程序用户订单列表")
	@RequestMapping(value = "/mini/Order", method = RequestMethod.POST)
	@ApiImplicitParam(name = "page", value = "分页信息 需要传session_id", required = true, dataType = "Page")
	public RspPage<RspUserOrder> orderByStaffid(@RequestBody Page page, HttpServletRequest request) {
		LOG.info("员工订单列表");
		String userid = UserDataUtil.getUserId(request);
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		return orderService.orderByUserid(page, userid, enterpriseId);
	}

	@ApiOperation(value = "订单详情", notes = "订单详情")
	@RequestMapping(value = "/mini/OrderDetail/{id}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "String", paramType = "path")
	public RspOrderDetail orderDetail(@PathVariable("id") String id, HttpServletRequest request) {
		LOG.info("订单详情");
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		return orderService.orderDetail(id, enterpriseId);

	}

	@ApiOperation(value = "查询用户新增情况", notes = "查询用户新增情况")
	@RequestMapping(value = "/mini/index/user", method = RequestMethod.GET)
	public List<DataItem> countUserAdd(@ApiParam(value = "7,15,30", name = "day") @RequestParam(value = "day") Integer day, HttpServletRequest request) {
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		String departmentId = UserDataUtil.getDepartmentId(request);
		Map param = new HashMap();
		param.put("day", day);
		param.put("departmentId", departmentId);
		param.put("enterpriseId", enterpriseId);
		return bossService.countUserAdd(param);
	}


	@ApiOperation(value = "根据enterprise_id查询", notes = "查询")
	@RequestMapping(value = "/mini/findWebsite", method = RequestMethod.GET)
	public List<RspWebsite> findWebsiteByEnterprise(HttpServletRequest request) {
		String id = UserDataUtil.getEnterpriseId(request);
		List<RspWebsite> websiteList = websiteService.findWebsiteByEnterprise(id);
		for (int i = 0; i < websiteList.size(); i++) {
			if (websiteList.get(i).getType() == 4) {
				Map<String, Object> textContent = GsonUtil.fromJson(websiteList.get(i).getText_content(), Map.class);
				Map<String, Object> dataContent = GsonUtil.fromJson(GsonUtil.toJson(textContent.get("data")), Map.class);
				Map<String, Object> employeeContent = GsonUtil.fromJson(GsonUtil.toJson(dataContent.get("employee")), Map.class);
				List valueContent = GsonUtil.fromJson(GsonUtil.toJson(employeeContent.get("value")), List.class);
				List<Staff> staffList = new ArrayList<Staff>();
				for (int j = 0; j < valueContent.size(); j++) {
					Staff staff = staffInfoService.selectByPrimaryKey(GsonUtil.fromJson(GsonUtil.toJson(valueContent.get(j)), Staff.class).getId());
					staffList.add(staff);
				}
				employeeContent.put("value", staffList);
				dataContent.put("employee", employeeContent);
				textContent.put("data", dataContent);
				websiteList.get(i).setText_content(GsonUtil.toJson(textContent));
			}
		}
		return websiteList;
	}

	@ApiOperation(value = "根据open_id查看用户", notes = "根据open_id查看用户")
	@RequestMapping(value = "/mini/findUserInfo", method = RequestMethod.POST)
	@ApiImplicitParam(name = "rspGetSig", value = "rspGetSig", required = true, dataType = "RspGetSig")
	public RspUserInfo findUserInfoByOpenId(@RequestBody RspGetSig rspGetSig) {
		//String enterpriseId=UserDataUtil.getEnterpriseId(request);
		if (rspGetSig.getEnterparise_id() == null || "".equals(rspGetSig.getEnterparise_id().trim())) {
			Staff staff = staffInfoService.selectByPrimaryKey(rspGetSig.getStaff_id());
			rspGetSig.setEnterparise_id(staff.getEnterpriseId());
		}
		return userInfoService.findUserInfoByOpenId(rspGetSig.getOpen_id(), rspGetSig.getEnterparise_id());
	}

	/**
	 * 添加用户
	 *
	 * @param
	 */
	@ApiOperation(value = "添加用户", notes = "添加用户")
	@RequestMapping(value = "/mini/addUserInfo", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqAddUser", value = "添加用户", required = true, dataType = "ReqAddUser")
	public void addUserInfo(@RequestBody ReqAddUser reqAddUser, HttpServletRequest request) {
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		userInfoService.addUserInfo(reqAddUser, enterpriseId);
	}

	@ApiOperation(value = "根据staff_id查看用户", notes = "根据staff_id查看用户")
	@RequestMapping(value = "/mini/findStaffById/{id}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "id", value = "id", required = true, dataType = "String", paramType = "path")
	public RspStaffLogin findStaffById(@PathVariable("id") String id) {
		return userInfoService.findStaffByOpenId(id);
	}

	@ApiOperation(value = "添加用户form关系", notes = "添加用户form关系")
	@RequestMapping(value = "/user_form_add", method = RequestMethod.POST)
	public void UserFormAdd(@RequestParam(value = "formid") String formid, HttpServletRequest request) {
		LOG.info(formid + ",长度为:" + formid.length());

		LOG.info("添加用户form关系");
		String userid = UserDataUtil.getUserId(request);
		ReqUserFormAdd reqUserFormAdd = new ReqUserFormAdd();
		reqUserFormAdd.setId(UuidUtil.getRandomUuid());
		reqUserFormAdd.setFormId(formid);
		reqUserFormAdd.setUserId(userid);
		reqUserFormAdd.setTimes(new Date());
		LOG.info(reqUserFormAdd);
		userInfoService.userformAdd(reqUserFormAdd);

	}

	@ApiOperation(value = "获取企业下的所有广告", notes = "获取企业下的所有广告")
	@RequestMapping(value = "/mini/Adv", method = RequestMethod.GET)
	public List<RspAdv> adv(HttpServletRequest request) {
		LOG.info("获取企业下的所有广告");
		return advService.miniFindAdv(request);
	}

	@ApiOperation(value = "查询是否手机号授权", notes = "查询是否手机号授权")
	@RequestMapping(value = "/mini/user/phone/auth", method = RequestMethod.GET)
	public String isPhone(HttpServletRequest request) {
		String userid = UserDataUtil.getUserId(request);

		return userInfoService.isPhone(userid);
	}

	@ApiOperation(value = "设置手机号", notes = "设置手机号")
	@RequestMapping(value = "/mini/user/phone", method = RequestMethod.PUT)
	public void setPhone(@RequestBody ReqGetMiniUserInfo reqGetMiniUserInfo, HttpServletRequest request) {
       /* String userid=UserDataUtil.getUserId(request);
        UserInfo userInfo = userInfoService.selectByPrimaryKey(userid);
        userInfo.setPhone(phone);
        userInfoService.updateByPrimaryKeySelective(userInfo);*/
		String id = UserDataUtil.getUserId(request);
		String openid = reqGetMiniUserInfo.getOpen_id();
		String encryptedData = reqGetMiniUserInfo.getEncrypted_data();
		String iv = reqGetMiniUserInfo.getIv();
		//通过用户openid获取sessionkey
		String sessionkey = UserDataUtil.getSessionKey(openid);
		String userInfo = this.decryptPhone(encryptedData, sessionkey, iv);
		String phone = new JSONObject(userInfo).getString("phoneNumber");

		//通过用户id将电话号码存到数据库 用户表 主表
		UserInfo userInfo1 = new UserInfo();
		userInfo1.setId(id);
		userInfo1.setPhone(phone);
		userInfoService.updateByPrimaryKeySelective(userInfo1);

	}
	/**
	 * 销售人员向用户发送消息
	 *
	 * @param
	 */
	@ApiOperation(value = "销售人员向用户发送消息", notes = "销售人员向用户发送消息")
	@RequestMapping(value = "/mini/sendMsg", method = RequestMethod.POST)
	public Integer sendMsg(@RequestBody ReqSendMsg reqSendMsg, HttpServletRequest request) {
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		String userId = UserDataUtil.getUserId(request);
		return tlsUtil.sendMsg(reqSendMsg.getStaffId(), userId, enterpriseId);
	}

	public String decryptPhone(String encryptedData, String sessionkey, String iv) {
		// 被加密的数据
		byte[] dataByte = org.codehaus.xfire.util.Base64.decode(encryptedData);
		// 加密秘钥
		byte[] keyByte = org.codehaus.xfire.util.Base64.decode(sessionkey);
		// 偏移量
		byte[] ivByte = org.codehaus.xfire.util.Base64.decode(iv);
		try {
			// 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
			int base = 16;
			if (keyByte.length % base != 0) {
				int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
				byte[] temp = new byte[groups * base];
				Arrays.fill(temp, (byte) 0);
				System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
				keyByte = temp;
			}
			// 初始化
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				String result = new String(resultByte, "UTF-8");
				return result;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidParameterSpecException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		return null;
	}

	@ApiOperation(value = "发送邮件", notes = "发送邮件")
	@RequestMapping(value = "/mini/sendEmail", method = RequestMethod.POST)
	public String sendEmail(@RequestBody Map<String, Object> map) {
		String content = "<!DOCTYPE html>"
				+ "<html>"
				+ "<head>"
				+ "<title>名片小程序码，请查收</title>"
				+ "<meta name=\"content-type\" content=\"text/html; charset=UTF-8\">"
				+ "</head>"
				+ "<body>"
				+ "<p>深卡小程序名片二维码~</p>"
				+ "<img src='" + map.get("qrCode").toString() + "' style='width:200px;height:200px'/>"
				+ "</body>"
				+ "</html>";

		try {
			MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
			message.setFrom("876434333@qq.com");//设置发信人，发信人需要和spring.mail.username配置的一样否则报错
			//补全地址
			message.setTo(map.get("email").toString());                //设置收信人
			message.setSubject("名片小程序码，请查收");    //设置主题
			message.setText(content, true);    //第二个参数true表示使用HTML语言来编写邮件

//            FileSystemResource file = new FileSystemResource(new File());
//            message.addAttachment("picture.jpg", file);//添加带附件的邮件  
//            helper.addInline("picture",file);//添加带静态资源的邮件
			this.mailSender.send(mimeMessage);

			return "success";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "error";
		}
	}

	@ApiOperation(value = "获取openid并且校验是否老用户", notes = "获取openid并且校验是否老用户")
	@RequestMapping(value = "/mini/getOpenidAndCheck", method = RequestMethod.POST)
	@ApiImplicitParam(name = "repOpenId", value = "获取openid", dataType = "RepOpenId")
	public ResOpenId getOpenidAndCheck(@RequestBody RepOpenId repOpenId) {

		String openid = staffInfoService.getopenid(repOpenId);
		ResOpenId resOpenId = new ResOpenId();
		resOpenId.setOpenid(openid);
		UserInfo userInfo = userInfoMapper.selectByOpenId(openid);
		if (userInfo == null) {
			resOpenId.setStatus("0");
		} else {
			resOpenId.setStatus("1");
			resOpenId.setNickName(userInfo.getNickName());
			resOpenId.setHeadIcon(userInfo.getHeadIcon());
		}
		return resOpenId;
	}

	@ApiOperation(value = "平台模式看名片登录", notes = "平台模式看名片登录")
	@RequestMapping(value = "/mini/card/userLogin", method = RequestMethod.POST)
	public String createRela(@RequestBody ReqAdduser reqAdduser) {
		return userInfoService.createRela(reqAdduser);
	}

	@ApiOperation(value = "用户是否有名片", notes = "用户是否有名片")
	@RequestMapping(value = "/mini/isExistCard/{openid}", method = RequestMethod.GET)
	public Integer isExistCard(@PathVariable("openid") String openid) {
		return staffInfoService.isExistCard(openid);
	}

	@ApiOperation(value = "获取推荐名片列表", notes = "获取推荐名片列表")
	@RequestMapping(value = "/mini/getRecommendCard", method = RequestMethod.GET)
	public List<RepRecommendCard> getRecommendCard() {
		return userInfoService.getRecommendCardList();
	}

	@ApiOperation(value = "用户点击推荐名片", notes = "用户点击推荐名片")
	@RequestMapping(value = "/mini/viewRecommendCard/{staffId}", method = RequestMethod.GET)
	public void viewRecommendCard(@PathVariable("staffId") String staffId) {
		userInfoService.viewRecommendCard(staffId);
	}


	@ApiOperation(value = "判断是否交换联系方式", notes = "判断是否交换联系方式")
	@RequestMapping(value = "/mini/isExchangeContact/{staffId}", method = RequestMethod.GET)
	public Boolean isExchangeContact(@PathVariable("staffId") String staffId,HttpServletRequest request) {
		String userId = UserDataUtil.getUserId(request);
		return userInfoService.isExchangContact(userId, staffId);
	}

	@ApiOperation(value = "交换联系方式", notes = "交换联系方式")
	@RequestMapping(value = "/mini/exchangeContact/{phone}", method = RequestMethod.GET)
	public int exchangeContact(@PathVariable("phone") String phone, HttpServletRequest request) {
		String staffId = UserDataUtil.getStaffId(request);
		String userId = UserDataUtil.getUserId(request);
		UserStaffRela userStaffRela = new UserStaffRela();
		userStaffRela.setPhone(phone);
		userStaffRela.setStaffId(staffId);
		userStaffRela.setUserId(userId);
		return userStaffService.updataPhone(userStaffRela);
	}
}
