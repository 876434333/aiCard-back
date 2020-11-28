package com.vma.push.business.interceptor;

import com.google.gson.Gson;
import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.util.SignUtil;
import com.vma.push.business.util.UserDataUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.crypto.Mac;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.Map;

/**
 * Created by chenzui on 2018/5/7.
 * 企业管理后台登录拦截器
 */
@Component
public class SaleInterceptorConfig implements HandlerInterceptor{
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
        BufferedReader br = httpServletRequest.getReader();

        String str, wholeStr = "";
        while((str = br.readLine()) != null){
            wholeStr += str;
        }
        System.out.println(wholeStr);


        SignUtil.sign(new Gson().fromJson(wholeStr, Map.class),"macKey");

        String staffId = UserDataUtil.getStaffId(httpServletRequest);

        if(staffId != null || !"".equals(staffId)){
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
