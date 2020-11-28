package com.vma.push.business.util;


import com.vma.push.business.common.Constants;
import com.vma.push.business.entity.Admin;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取用户缓存数据
 * Created by zhangshilin on 2018/2/6.
 */
public class UserDataUtil {


	//获取部门ID
	public static String getDepartmentId(HttpServletRequest request) {
		String sessionId = request.getHeader(Constants.SESSION_ID);
		return RedisUtil.get(sessionId + "_department_id");

	}

	//当前用户编号
	public static String getStaffId(HttpServletRequest request) {
		String sessionId = request.getHeader(Constants.SESSION_ID);
		return RedisUtil.get(sessionId + "_staff_id");
	}

	//当前用户对应的enterpriseId
	public static String getEnterpriseId(HttpServletRequest request) {
		String sessionId = request.getHeader(Constants.SESSION_ID);
		return RedisUtil.get(sessionId + "_enterprise_id");
	}

	public static String getAccessToken(HttpServletRequest request) {
		String sessionId = request.getHeader(Constants.SESSION_ID);
		return RedisUtil.get(sessionId + "_access_token");
	}


	//当前号码的验证码
	public static String getMsg(String phone) {
		return RedisUtil.get(phone);
	}

	//通过手机号码存 验证码  有效时间 3分钟
	public static void setMsg(String phone, String Msg) {
		RedisUtil.set(phone, Msg, Constants.MSG_KEEP_TIME);
	}

	public static void setStaffId(String sessionId, String staffId) {
		RedisUtil.set(sessionId + "_staff_id", staffId, Constants.LOGIN_KEEP_TIME);
	}

	public static void setSessionKey(String openid, String key) {
		RedisUtil.set(openid, key, Constants.LOGIN_KEEP_TIME);
	}

	public static String getSessionKey(String openid) {
		return RedisUtil.get(openid);
	}

	public static void setEnterpriseId(String sessionId, String enterpriseId) {
		RedisUtil.set(sessionId + "_enterprise_id", enterpriseId, Constants.LOGIN_KEEP_TIME);
	}

	public static void setDepartmentId(String sessionId, String departmentId) {
		RedisUtil.set(sessionId + "_department_id", departmentId, Constants.LOGIN_KEEP_TIME);
	}

	/**
	 * 获取小程序客户ID
	 *
	 * @param request
	 * @return
	 */
	public static String getUserId(HttpServletRequest request) {
		String sessionId = request.getHeader(Constants.SESSION_ID);
		return RedisUtil.get(sessionId + "_user_id");
	}

	/**
	 * 设置小程序用户ID进缓存
	 *
	 * @param sessionId,userId
	 * @return
	 */
	public static void setUserId(String sessionId, String userId) {
		RedisUtil.set(sessionId + "_user_id", userId, Constants.LOGIN_KEEP_TIME);

	}


	/**
	 * 获取超级后台，贴牌商、地区总代理、代理商的adminId
	 *
	 * @param request
	 * @return
	 */
	public static String getAdminId(HttpServletRequest request) {
		String sessionId = request.getHeader(Constants.SESSION_ID);
		return RedisUtil.get(sessionId + "_admin_id");
	}

	/**
	 * 设置超级后台，贴牌商、地区总代理、代理商的token进缓存
	 *
	 * @param sessionId,userId
	 * @return
	 */
	public static void setAdminId(String sessionId, String userId) {
		RedisUtil.set(sessionId + "_admin_id", userId, Constants.LOGIN_KEEP_TIME);

	}

	/**
	 * 获取超级后台，贴牌商、地区总代理、代理商登录人所属客户id
	 *
	 * @param request
	 * @return
	 */
	public static String getCustomId(HttpServletRequest request) {
		String sessionId = request.getHeader(Constants.SESSION_ID);
		return RedisUtil.get(sessionId + "_custom_id");
	}

	/**
	 * 设置超级后台，贴牌商、地区总代理、代理商登录人所属客户id缓存
	 *
	 * @param sessionId,userId
	 * @return
	 */
	public static void setCustomId(String sessionId, String customId) {
		RedisUtil.set(sessionId + "_custom_id", customId, Constants.LOGIN_KEEP_TIME);
	}

	/**
	 * 获取超级后台，贴牌商、地区总代理、代理商登录人所属客户id
	 *
	 * @param request
	 * @return
	 */
	public static String getUserType(HttpServletRequest request) {
		String sessionId = request.getHeader(Constants.SESSION_ID);
		return RedisUtil.get(sessionId + "_user_type");
	}

	/**
	 * 设置设置当前用户的类型缓存
	 *
	 * @param sessionId,userId
	 * @return
	 */
	public static void setUserType(String sessionId, String type) {
		RedisUtil.set(sessionId + "_user_type", type, Constants.LOGIN_KEEP_TIME);

	}

	public static String getSuperId(HttpServletRequest request) {
		String sessionId = request.getHeader(Constants.SESSION_ID);
		return RedisUtil.get(sessionId + "_super_id");
	}

	//存储微信token
	public static void setWxToken(String token, String enid) {
		RedisUtil.set("wx_token_" + enid, token, 7000);
	}

	public static String getWxToken(String enid) {
		return RedisUtil.get("wx_token_" + enid);
	}

	public static void setMsgCode(String phone, String code) {
		RedisUtil.set(phone, code, 60 * 5);
	}

	public static String getMsgCode(String phone) {
		return RedisUtil.get(phone);
	}


	public static void setSignKey(String macKey, String signKey) {
		RedisUtil.set(macKey, signKey, 60 * 60 * 24 * 7);
	}

	public static String getSignKey(String macKey) {
		return RedisUtil.get(macKey);
	}

	public static void setUuid(HttpServletRequest request) {
		String uuid = request.getHeader(Constants.CLIENT_UUID);
		String sessionId = request.getHeader(Constants.SESSION_ID);
		RedisUtil.set(sessionId, uuid, 60 * 5);
	}

	public static String getUuid(HttpServletRequest request) {
		String sessionId = request.getHeader(Constants.SESSION_ID);
		return RedisUtil.get(sessionId);

	}


	public static void removeAdminInfo(HttpServletRequest request) {
		String sessionId = request.getHeader(Constants.SESSION_ID);
		RedisUtil.delKey(sessionId + "_admin_id");
		RedisUtil.delKey(sessionId + "_user_type");
		RedisUtil.delKey(sessionId + "_custom_id");
	}
	// add by lql -- start
	/**
	 * 设置自己的staffId
	 * @param sessionId,userId
	 * @return
	 */
	public static void setMyStaffId(String sessionId, String type) {
		RedisUtil.set(sessionId + "my_staffid", type, Constants.LOGIN_KEEP_TIME);

	}

	public static String getMyStaffId(HttpServletRequest request) {
		String sessionId = request.getHeader(Constants.SESSION_ID);
		return RedisUtil.get(sessionId + "my_staffid");
	}
	/**
	 * 设置自己的enterpriseId
	 * @param sessionId,userId
	 * @return
	 */
	public static void setMyEnterpriseId(String sessionId, String type) {
		RedisUtil.set(sessionId + "my_enterpriseid", type, Constants.LOGIN_KEEP_TIME);

	}

	public static String getMyEnterpriseId(HttpServletRequest request) {
		String sessionId = request.getHeader(Constants.SESSION_ID);
		return RedisUtil.get(sessionId + "my_enterpriseid");
	}
	/**
	 * 设置自己的enterpriseId
	 * @param sessionId,userId
	 * @return
	 */
	public static void setMyDepartmentId(String sessionId, String type) {
		RedisUtil.set(sessionId + "my_departmentid", type, Constants.LOGIN_KEEP_TIME);

	}

	public static String getMyDepartmentId(HttpServletRequest request) {
		String sessionId = request.getHeader(Constants.SESSION_ID);
		return RedisUtil.get(sessionId + "my_departmentid");
	}
	// add by lql -- end
}
