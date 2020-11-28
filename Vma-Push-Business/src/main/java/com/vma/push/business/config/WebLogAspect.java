package com.vma.push.business.config;

import com.vma.push.business.util.GsonUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 统一日志处理
 * Created by zhangshilin on 2017/9/7.
 */
@Aspect
@Component
public class WebLogAspect {

	private Logger LOG = Logger.getLogger(this.getClass());

	@Pointcut("execution(* com.vma.push.business.controller..*.*(..))")
	public void webLog() {
	}


	@Before("webLog()")
	public void before(JoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		MDC.put("requestId", System.currentTimeMillis());
		for (Object arg : args) {
			if (arg == null || arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
				continue;
			}
			try {
				// 防止上传图片时会打印图片的base64码
				if (GsonUtil.toJson(arg).length() > 500) {
					LOG.info("请求参数为:-----------------------请求参数过长，有可能是上传图片或视屏——————————————");
				} else {
					LOG.info("请求参数为:" + GsonUtil.toJson(arg));
				}
			} catch (Exception e) {
				continue;
			}

		}
	}

	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void afterReturn(Object ret) throws Throwable {
		LOG.info("响应参数为:" + GsonUtil.toJson(ret));
		MDC.clear();
	}
}
