package com.vma.push.business.service;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-12-17 9:30
 */
public interface ISendSMS {
	// 发送短信
	int sendSMS(String phone, String[] comment, Integer template);

	// 校验短信验证码
	String cherckSMS(String phone, String VerificationCode);
}
