package com.vma.push.business.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件工具类
 */
public class ConfigUtil {
	private static Properties properties;

	static {
		properties = new Properties();
		InputStream in = ConfigUtil.class.getClassLoader().getResourceAsStream(
				"common.properties");
		try {
			properties.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取String类型数据
	 * @param key key
	 * @return value
	 */
	public static String getStringValue(String key) {
		try {
			return properties.getProperty(key).trim();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 获取Integer类型数据
	 * @param key key
	 * @return value
	 */
	public static Integer getIntgerValue(String key){
		String value = ConfigUtil.getStringValue(key);
		if (null == value || value.length() == 0) {
			return null;
		}
		return Integer.parseInt(value);
	}

	public static void main(String[] args){
		System.out.println(getStringValue("host"));
	}
}
