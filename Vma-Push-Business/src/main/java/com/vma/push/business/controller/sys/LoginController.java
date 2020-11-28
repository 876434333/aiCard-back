package com.vma.push.business.controller.sys;

import com.vma.push.business.common.Constants;
import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.dao.EnterpriseMapper;
import com.vma.push.business.dao.SysConfigMapper;
import com.vma.push.business.dto.req.ReqModifyPwd;
import com.vma.push.business.dto.req.ReqSystemLogin;
import com.vma.push.business.dto.req.ReqUpdatePwd;
import com.vma.push.business.dto.rsp.RspApiAndImgUrl;
import com.vma.push.business.dto.rsp.RspMiniOem;
import com.vma.push.business.dto.rsp.superback.RspLoginAdmin;
import com.vma.push.business.dto.rsp.superback.RspLoginInfo;
import com.vma.push.business.dto.rsp.userInfo.RspWebInfo;
import com.vma.push.business.entity.Admin;
import com.vma.push.business.entity.SysConfig;
import com.vma.push.business.service.*;
import com.vma.push.business.util.MD5Helper;
import com.vma.push.business.util.RandomValidateCodeUtil;
import com.vma.push.business.util.UserDataUtil;
import com.vma.push.business.util.WeChatApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * created by linzh on 2018/6/14
 */
@RestController(value = "CommonLoginController")
@RequestMapping("/V1.0")
@Api(value = "登录API", description = "登录API", tags = {"LoginController"})
public class LoginController extends ControllerExceptionHandler {
	private Logger LOG = Logger.getLogger(this.getClass());
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IMenuResourceService menuResourceService;
	@Autowired
	private IUserInfoService userInfoService;

	@Autowired
	private IAdminService adminService;
	@Autowired
	private WeChatApi weChatApi;
	@Autowired
	private EnterpriseMapper enterpriseMapper;
	@Autowired
	private IEnterpriseService enterpriseService;
	@Autowired
	private SysConfigMapper sysConfigMapper;

	@ApiOperation(value = "后台登陆", notes = "后台登陆")
	@RequestMapping(value = "/common/account/login", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqSystemLogin", value = "后台系统登陆", dataType = "ReqSystemLogin")
	public RspLoginAdmin login(@RequestBody ReqSystemLogin reqSystemLogin, HttpServletRequest request) {
		Map<String, String> ret = new HashMap<String, String>();

		Admin admin = loginService.login(reqSystemLogin, request);

		String token = MD5Helper.getMD5Str(admin.getId() + System.currentTimeMillis());
		UserDataUtil.setAdminId(token, admin.getId());
		if ("1".equals(reqSystemLogin.getType())) {
			//超级后台,设置为客户id为0
			UserDataUtil.setCustomId(token, "0");
		} else {
			UserDataUtil.setCustomId(token, admin.getCustomId());
		}
		UserDataUtil.setUserType(token, reqSystemLogin.getType());

		//根据用户的登陆权限type获取 web_name wen_icon
		RspWebInfo webInfo = loginService.findWebInfo(admin.getType());

		String userId = admin.getId();
		Integer role = admin.getRole();
		RspLoginAdmin rspLoginAdmin = new RspLoginAdmin();
		rspLoginAdmin.setId(userId);
		rspLoginAdmin.setName(admin.getName());
		rspLoginAdmin.setMenu(menuResourceService.getMenuByUserId(userId));
		rspLoginAdmin.setToken(token);
		rspLoginAdmin.setHead_icon(admin.getHeadIcon());
		if (webInfo != null) {
			rspLoginAdmin.setWeb_icon(webInfo.getWeb_icon());
			rspLoginAdmin.setWeb_title(webInfo.getWeb_title());
		}

		RspMiniOem rspMiniOem = enterpriseService.oemInfo(admin.getCustomId());
		if (rspMiniOem != null) {
			rspLoginAdmin.setOem_icon(rspMiniOem.getOem_icon());
			rspLoginAdmin.setOem_name(rspMiniOem.getOem_name());
		}
		return rspLoginAdmin;

	}

	@ApiOperation(value = "后台登出", notes = "后台登出")
	@RequestMapping(value = "/common/account/logout", method = RequestMethod.POST)
	public void logOut(HttpServletRequest request) {
		UserDataUtil.removeAdminInfo(request);
	}


	@ApiOperation(value = "获取用户信息", notes = "获取用户信息")
	@RequestMapping(value = "/common/account/user", method = RequestMethod.GET)
	public RspLoginInfo login(HttpServletRequest request) {
		String userId = UserDataUtil.getAdminId(request);
		Admin admin = adminService.selectByPrimaryKey(userId);
		if (admin == null) {
			return new RspLoginInfo();
		} else {
			RspLoginInfo rspLoginInfo = new RspLoginInfo();
			rspLoginInfo.setId(admin.getId());
			rspLoginInfo.setName(admin.getName());
			rspLoginInfo.setPhone(admin.getPhone());
			rspLoginInfo.setHead_icon("");
			rspLoginInfo.setMenu(menuResourceService.getMenuByUserId(userId));
			RspMiniOem rspMiniOem = enterpriseService.oemInfo(admin.getCustomId());
			if (rspMiniOem != null) {
				rspLoginInfo.setOem_icon(rspMiniOem.getOem_icon());
				rspLoginInfo.setOem_name(rspMiniOem.getOem_name());
			}
			return rspLoginInfo;
		}

	}

	@ApiOperation(value = "找回密码", notes = "找回密码")
	@RequestMapping(value = "/common/account/pwd/find", method = RequestMethod.PUT)
	public String updatePwd(@RequestBody ReqUpdatePwd reqUpdatePwd) {
		return loginService.updatePwd(reqUpdatePwd);
	}

	@ApiOperation(value = "修改密码", notes = "修改密码")
	@RequestMapping(value = "/common/account/pwd", method = RequestMethod.PUT)
	public void modifyPwd(@RequestBody ReqModifyPwd reqModifyPwd, HttpServletRequest request) {
		loginService.modifyPwd(reqModifyPwd, request);
	}

	@ApiOperation(value = "通过微擎获取api路径", notes = "通过微擎获取api路径")
	@RequestMapping(value = "/common/api_url/{wq_id}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "wq_id", value = "微擎id", dataType = "String", paramType = "path")
	public String findApiUrl(@PathVariable("wq_id") String wq_id, HttpServletRequest request) {
		//M by PLH at 2018-11-26 for 暂时不适用微擎, 关于集群问题待后续修正 begin
		//LOG.info("wq_id:"+wq_id);
		//String appid=weChatApi.getAppid(wq_id);
		//LOG.info("appid:"+appid);
		//String re =  enterpriseMapper.apiUrlByAppid(appid);
		String re = null;
		if (StringUtils.isEmpty(re)) {
			// re = "https://keji-api.deecard.szrenzhi.com";
			//request.getRequestURL() = http://192.168.0.100/push/V1.0/common/api_url/2
			//request.getRequestURL() = http://dev.deecard.net/push/V1.0/common/api_url/2
			// http 此时在小程序中会校验HTTPS
			// re = "http://keji-api.deecard.szrenzhi.com";
			String requestURL = request.getRequestURL().toString();
			re = requestURL.substring(0, requestURL.indexOf("/push/"));

			LOG.info("re_befor:" + re);
			//http://192是开发调试代码
			if (re.indexOf("https://") < 0 && re.indexOf("http://192") < 0) {
				re = re.replace("http://", "https://");
			}
			LOG.info("re_after:" + re);
		}
		//M by PLH at 2018-11-26 for 暂时不适用微擎, 关于集群问题待后续修正 end
		return re;
	}

	@ApiOperation(value = "通过微擎获取api路径和图片路径", notes = "通过微擎获取api路径和图片路径")
	@RequestMapping(value = "/common/api_img_url/{wq_id}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "wq_id", value = "微擎id", dataType = "String", paramType = "path")
	public RspApiAndImgUrl findApiImgUrl(@PathVariable("wq_id") String wq_id, HttpServletRequest request) {
		LOG.info("wq_id:" + wq_id);
		String appid = weChatApi.getAppid(wq_id);
		LOG.info("appid:" + appid);
		RspApiAndImgUrl re = enterpriseMapper.apiImgUrlByAppid(appid);
		LOG.info("re:" + re);
		if (re == null) {
			RspApiAndImgUrl url = new RspApiAndImgUrl();
//            url.setApi_url("https://keji-api.h5h5h5.cn");
//            url.setImg_url("https://keji-res.h5h5h5.cn");

			//A by plh at 2018-12-01 for 防止出错 BEGIN
			//Image URL
			SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);
			url.setImg_url(sysConfig.getImgUrl());

			//API URL
			String requestURL = request.getRequestURL().toString();
			requestURL = requestURL.substring(0, requestURL.indexOf("/push/"));

			LOG.info("re_befor:" + re);
			//http://192是开发调试代码
			if (requestURL.indexOf("https://") < 0 && requestURL.indexOf("http://192") < 0) {
				requestURL = requestURL.replace("http://", "https://");
			}
			url.setApi_url(requestURL);
			//A by plh at 2018-12-01 for 防止出错 END

			re = url;
		}
		return re;
	}


	/**
	 * 生成验证码
	 */
	@ApiOperation(value = "生成验证码", notes = "生成验证码")
	@RequestMapping(value = "/common/account/verify", method = RequestMethod.GET)
	public void getVerify(HttpServletRequest request, HttpServletResponse response) {
		try {
//            HttpServletResponse response= request.get
			response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
			response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expire", 0);
			RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
			randomValidateCode.getRandcode(request, response);//输出验证码图片方法
		} catch (Exception e) {
			LOG.error("获取验证码失败>>>>   ", e);
		}
	}

	@ApiOperation(value = "后台选择企业登录", notes = "后台选择企业登录")
	@RequestMapping(value = "/common/account/chooseLogin/{enterpriseId}", method = RequestMethod.GET)
	public RspLoginAdmin chooseLogin(@PathVariable(value = "enterpriseId") String enterpriseId, HttpServletRequest request) {
		String token = request.getHeader(Constants.SESSION_ID);
		UserDataUtil.setCustomId(token, enterpriseId);
		// 登录时的存入Redis中的adminId
		Admin admin = adminService.getAdmin(enterpriseId);
		//根据用户的登陆权限type获取 web_name wen_icon
		RspWebInfo webInfo = loginService.findWebInfo(admin.getType());

		RspLoginAdmin rspLoginAdmin = new RspLoginAdmin();
		rspLoginAdmin.setId(admin.getId());
		rspLoginAdmin.setName(admin.getName());
		rspLoginAdmin.setMenu(menuResourceService.getMenuByUserId(admin.getId()));
		rspLoginAdmin.setHead_icon(admin.getHeadIcon());
		if (webInfo != null) {
			rspLoginAdmin.setWeb_icon(webInfo.getWeb_icon());
			rspLoginAdmin.setWeb_title(webInfo.getWeb_title());
		}

		RspMiniOem rspMiniOem = enterpriseService.oemInfo(admin.getCustomId());
		if (rspMiniOem != null) {
			rspLoginAdmin.setOem_icon(rspMiniOem.getOem_icon());
			rspLoginAdmin.setOem_name(rspMiniOem.getOem_name());
		}
		return rspLoginAdmin;
	}
}
