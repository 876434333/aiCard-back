package com.vma.push.business.service.impl;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.config.SmsConfig;
import com.vma.push.business.service.ISendSMS;
import com.vma.push.business.util.JSONException;
import com.vma.push.business.util.UserDataUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-12-17 9:33
 */
@Service
public class SendSMSServiceImpl implements ISendSMS {
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private SmsConfig smsConfig;

	@Override
	public int sendSMS(String phone, String[] comment, Integer template) {
		try {
			SmsSingleSender ssender = new SmsSingleSender(smsConfig.getSdk_appid(), smsConfig.getSdk_key());
			SmsSingleSenderResult result = ssender.sendWithParam("86", phone, template, comment, "", "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
			if (result.result == 0) {
				return result.result;
			} else if (result.result == 1016) {
				//手机号格式错误
				throw new BusinessException(ErrCode.ERROR_PHONE, "手机号格式不正确,请重新输入手机号");
			} else if (result.result == 1033) {
				// 短信服务欠费，前往腾讯云充值
				logger.info("。。。。。。。。。。。。。。。。。。短信服务欠费，前往腾讯云充值");
				return -1;
			} else if (result.result == 1025) {
				throw new BusinessException(ErrCode.ERROR_PHONE, result.errMsg);
			} else {
				logger.info("。。。。。。。。。。。。。。。。。。"+ result.errMsg);
				return -1;
			}
		} catch (Exception e) {
			// 网络IO错误
			e.printStackTrace();
			throw new BusinessException(-1, "网络异常");
		}
	}

	@Override
	public String cherckSMS(String phone, String verificationCode) {
		String sysCode = UserDataUtil.getMsg(phone);
		if (sysCode == null) {
			throw new BusinessException(ErrCode.ERROR_PHONE, "验证码已过期,请重新获取");
		} else if (sysCode.equals(verificationCode)) {
			return "ok";
		} else if (sysCode.equals(verificationCode) == false) {
			throw new BusinessException(ErrCode.ERROR_PHONE, "验证码错误,请重新输入");
		}
		return "";
	}
}
