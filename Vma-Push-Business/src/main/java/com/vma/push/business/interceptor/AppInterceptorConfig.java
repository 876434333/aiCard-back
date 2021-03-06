package com.vma.push.business.interceptor;

import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.util.RedisUtil;
import com.vma.push.business.util.UserDataUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * Created by chenzui on 2018/5/7.
 */
@Component
public class AppInterceptorConfig implements HandlerInterceptor{
    private Logger LOG = Logger.getLogger(this.getClass());

    /**
     * 进入controller层之前拦截请求
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        LOG.info("---------------------开始进入请求地址拦截----------------------------");

        String userId = UserDataUtil.getUserId(httpServletRequest);

        if(userId != null || !"".equals(userId)){
            return true;
        }else {
            throw new BusinessException(ErrCode.LOGIN_TIME_OUT,"登录超时");
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        LOG.info("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        LOG.info("---------------视图渲染之后的操作-------------------------0");
    }
}
