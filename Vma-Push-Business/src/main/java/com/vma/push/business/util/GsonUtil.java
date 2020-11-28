package com.vma.push.business.util;


import com.google.gson.Gson;

/**
 * <p>Title:GsonUtil</p>
 * <p>Description:使用Gson解决json字符串相关操作</p>
 */
public class GsonUtil {
	private static final Gson gson = new Gson();

	/**
	 * 将任意实体类转换成json字符串
	 * @param obj 实体类
	 * @return json字符串
	 */
	public static String toJson(Object obj){
		return gson.toJson(obj);
	}


	/**
	 * 将任意实体类转换成json字符串
	 * @param str json字符串
	 * @param type 类型
	 * @return 实体对象
	 */
	public static <T> T fromJson(String str, Class<T> type) {
		Gson gson = new Gson();
		return gson.fromJson(str, type);
	}
}
