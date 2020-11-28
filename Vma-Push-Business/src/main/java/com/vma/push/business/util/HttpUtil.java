package com.vma.push.business.util;

import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.*;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * <p>HttpUtil</p>
 */
public abstract class HttpUtil {
	private HttpUtil() {
		throw new RuntimeException("HttpUtil.class can't be instantiated");
	}

	private static final Logger LOG = Logger.getLogger(HttpUtil.class);

	/**
	 * 支持HTTP_CLIENT的多线程使用
	 */
	private static final MultiThreadedHttpConnectionManager
			HTTP_CONNECTION_MANAGER = new MultiThreadedHttpConnectionManager();
	private static final HttpClient HTTP_CLIENT = new HttpClient(HTTP_CONNECTION_MANAGER);

	/**
	 * httpGetReq
	 *
	 * @param url 服务路径
	 * @return
	 * @throws IOException
	 */
	public static HttpRspResult httpGetReq(String url) {
		GetMethod method = null;
		HttpRspResult httpRspResult = new HttpRspResult();
		try {
			LOG.info("get url : " + url);
			method = new GetMethod(url);
			int httpStatus = HTTP_CLIENT.executeMethod(method);
			String rspJson = new String(method.getResponseBody(), "UTF-8");
			LOG.info("rsp data : " + rspJson);

			httpRspResult.setHttp_status(httpStatus);
			httpRspResult.setResponse_str(rspJson);
			return httpRspResult;
		} catch (IOException e) {
			LOG.error("HttpGet失败:", e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return null;
	}


	/**
	 * httpGetReq
	 *
	 * @param url 服务路径
	 * @return
	 * @throws IOException
	 */
	public static HttpRspResult httpGetReq(String url, Map<String, Object> params) {
		GetMethod method = null;
		HttpRspResult httpRspResult = new HttpRspResult();
		try {
			String reqParam = buildQuery(params, "UTF-8");
			LOG.info("get url : " + url + "?" + reqParam);
			method = new GetMethod(url + "?" + reqParam);
			int httpStatus = HTTP_CLIENT.executeMethod(method);
			String rspJson = new String(method.getResponseBody(), "UTF-8");
			LOG.info("rsp data : " + rspJson);

			httpRspResult.setHttp_status(httpStatus);
			httpRspResult.setResponse_str(rspJson);
			return httpRspResult;
		} catch (IOException e) {
			LOG.error("HttpGet失败:", e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return null;
	}

	/**
	 * httpGetReq
	 *
	 * @param url 服务路径
	 * @return
	 * @throws IOException
	 */
	public static byte[] httpGetReqForBytes(String url, Map<String, Object> params) {
		GetMethod method = null;
		try {
			String reqParam = buildQuery(params, "UTF-8");
			LOG.info("get url : " + url + "?" + reqParam);
			method = new GetMethod(url + "?" + reqParam);
			HTTP_CLIENT.executeMethod(method);
			return method.getResponseBody();
		} catch (IOException e) {
			LOG.error("HttpGet失败:", e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return null;
	}

	public static String postWithHeader(String url, Map<String, String> paramMap, Map<String, String> headers)
			throws Exception {

		PostMethod method = null;
		try {
			LOG.info("post url : " + url);
			method = new PostMethod(url);
			Gson gson = new Gson();
			String json = gson.toJson(paramMap);
			LOG.info("req post data : " + json);
			for (String key : paramMap.keySet()) {
				method.addParameter(key, paramMap.get(key));
			}
			for (String key : headers.keySet()) {
				method.addRequestHeader(key, headers.get(key));
			}
			HTTP_CLIENT.executeMethod(method);
			String rspJson = new String(method.getResponseBody(), "UTF-8");
			LOG.info("rsp data : " + rspJson);
			return rspJson;
		} catch (IOException e) {
			LOG.error("HttpPost失败:", e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return null;
	}

	/**
	 * httpDeleteReq
	 *
	 * @param url 服务路径
	 * @return
	 * @throws IOException
	 */
	public static HttpRspResult httpDeleteReq(String url) {
		DeleteMethod method = null;
		try {
			HttpRspResult httpRspResult = new HttpRspResult();
			LOG.info("delete url : " + url);
			method = new DeleteMethod(url);
			int httpStatus = HTTP_CLIENT.executeMethod(method);
			String rspJson = new String(method.getResponseBody(), "UTF-8");
			LOG.info("rsp data : " + rspJson);
			httpRspResult.setHttp_status(httpStatus);
			httpRspResult.setResponse_str(rspJson);
			return httpRspResult;
		} catch (IOException e) {
			LOG.error("HttpDelete失败:", e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return null;
	}

	/**
	 * httpPutReq
	 *
	 * @param url    服务路径
	 * @param params 请求参数
	 * @return http应答内容
	 * @throws IOException
	 */
	public static String httpPutReq(String url, Object params) {
		PutMethod method = null;
		try {
			LOG.info("put url : " + url);
			method = new PutMethod(url);
			Gson gson = new Gson();
			String json = gson.toJson(params);
			LOG.info("req data : " + json);
			RequestEntity entity = new StringRequestEntity(json, "application/json", "UTF-8");
			method.setRequestEntity(entity);
			HTTP_CLIENT.executeMethod(method);
			String rspJson = new String(method.getResponseBody(), "UTF-8");
			LOG.info("rsp data : " + rspJson);
			return rspJson;
		} catch (IOException e) {
			LOG.error("HttpPut失败:", e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return null;
	}

	/**
	 * httpPostReq
	 *
	 * @param url    服务路径
	 * @param params 请求参数
	 * @return http应答内容
	 * @throws IOException
	 */
	public static String httpPostReq(String url, Object params) {
		PostMethod method = null;
		try {
			LOG.info("post url : " + url);
			method = new PostMethod(url);
			Gson gson = new Gson();
			String json = gson.toJson(params);
			LOG.info("req post data : " + json);
			RequestEntity entity = new StringRequestEntity(json, "application/json", "UTF-8");
			method.setRequestEntity(entity);
			HTTP_CLIENT.executeMethod(method);
			String rspJson = new String(method.getResponseBody(), "UTF-8");
			LOG.info("rsp data : " + rspJson);
			return rspJson;
		} catch (IOException e) {
			LOG.error("HttpPost失败:", e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return null;
	}

	/**
	 * httpPostReq
	 *
	 * @param uri    服务路径
	 * @param params 请求参数
	 * @return http应答内容
	 * @throws IOException
	 */
	public static HttpRspResult httpPostReq(String uri, Map<String, String> params) {
		PostMethod method = null;
		HttpRspResult httpRspResult = new HttpRspResult();
		try {
			method = new PostMethod(uri);
			Gson gson = new Gson();
			String json = gson.toJson(params);
			LOG.info("req data : " + json);
			RequestEntity entity = new StringRequestEntity(json, "application/json", "UTF-8");
			method.setRequestEntity(entity);
			Integer status = HTTP_CLIENT.executeMethod(method);
			String rspJson = new String(method.getResponseBody(), "UTF-8");
			LOG.info("rsp data : " + rspJson);
			httpRspResult.setHttp_status(status);
			httpRspResult.setResponse_str(rspJson);
			return httpRspResult;
		} catch (IOException e) {
			LOG.error("HttpPost失败:", e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return null;
	}


	/**
	 * httpPutReq
	 *
	 * @param uri    服务路径
	 * @param params 请求参数
	 * @return http应答内容
	 * @throws IOException
	 */
	public static HttpRspResult httpPutReq(String uri, Map<String, String> params) {
		PutMethod method = null;
		HttpRspResult httpRspResult = new HttpRspResult();
		try {
			method = new PutMethod(uri);
			Gson gson = new Gson();
			String json = gson.toJson(params);
			LOG.info("req data : " + json);
			RequestEntity entity = new StringRequestEntity(json, "application/json", "UTF-8");
			method.setRequestEntity(entity);
			Integer status = HTTP_CLIENT.executeMethod(method);
			String rspJson = new String(method.getResponseBody(), "UTF-8");
			LOG.info("rsp data : " + rspJson);
			httpRspResult.setHttp_status(status);
			httpRspResult.setResponse_str(rspJson);
			return httpRspResult;
		} catch (IOException e) {
			LOG.error("HttpPut失败:", e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return null;
	}


	/**
	 * httpPostReq
	 *
	 * @param url     服务路径
	 * @param content 请求内容
	 * @return http应答内容
	 * @throws IOException
	 */
	public static String httpPostReq(String url, String content) {
		PostMethod method = null;
		try {
			method = new PostMethod(url);
			LOG.info("url :" + url + "; method :post; req data : " + content);
			RequestEntity entity = new StringRequestEntity(content, "application/json", "UTF-8");
			method.setRequestEntity(entity);
			HTTP_CLIENT.executeMethod(method);
			String rspJson = new String(method.getResponseBody(), "UTF-8");
			LOG.info("rsp data : " + rspJson);
			return rspJson;
		} catch (IOException e) {
			LOG.error("HttpPost失败:", e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return null;
	}

	/**
	 * 发送POST 请求
	 *
	 * @param url     请求地址
	 * @param charset 编码格式
	 * @param params  请求参数
	 * @return 响应
	 * @throws IOException
	 */
	public static String post(String url, String charset, Map params) {
		HttpURLConnection conn = null;
		OutputStreamWriter out = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer result = new StringBuffer();
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Accept-Charset", charset);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			out = new OutputStreamWriter(conn.getOutputStream(), charset);
			out.write(buildQuery(params, charset));
			out.flush();
			inputStream = conn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);
			String tempLine = null;
			while ((tempLine = reader.readLine()) != null) {
				result.append(tempLine);
			}

		} catch (IOException e) {
			LOG.error("Post失败:", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (reader != null) {
					reader.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				LOG.error("请求关闭失败:", e);
			}

		}
		return result.toString();
	}

	/**
	 * 将map转换为请求字符串
	 * <p>data=xxx&msg_type=xxx</p>
	 *
	 * @param params
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String buildQuery(Map<String, Object> params, String charset) throws IOException {
		if (params == null || params.isEmpty()) {
			return null;
		}

		StringBuffer data = new StringBuffer();
		boolean flag = false;

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			if (flag) {
				data.append("&");
			} else {
				flag = true;
			}
			data.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(), charset));
		}

		return data.toString();

	}

	public static String sendGet(String url) {
		System.out.println("发送请求：" + url);
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			connection.setConnectTimeout(6000);
			connection.setReadTimeout(6000);
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
//	            for (String key : map.keySet()) {
//	                System.out.println(key + "--->" + map.get(key));
//	            }
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
//	            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}
