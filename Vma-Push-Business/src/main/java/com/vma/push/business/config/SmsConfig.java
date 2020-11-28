package com.vma.push.business.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix="sms")
public class SmsConfig {
	private int sdk_appid;
	private String sdk_key;
	private Map<String, Integer> template = new HashMap<>();

	public int getSdk_appid() {
		return sdk_appid;
	}

	public void setSdk_appid(int sdk_appid) {
		this.sdk_appid = sdk_appid;
	}

	public String getSdk_key() {
		return sdk_key;
	}

	public void setSdk_key(String sdk_key) {
		this.sdk_key = sdk_key;
	}

	public Map<String, Integer> getTemplate() {
		return template;
	}

	public void setTemplate(Map<String, Integer> template) {
		this.template = template;
	}
}
