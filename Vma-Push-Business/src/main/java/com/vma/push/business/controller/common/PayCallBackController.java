package com.vma.push.business.controller.common;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by chenzui on 2018/5/23.
 */

@RestController
@RequestMapping("/v4.0")
@Api(value = "PayCallBackController",description = "支付回调",tags = {"FileController"})
public class PayCallBackController extends ControllerExceptionHandler {

    @Autowired
    private IOrderService orderService;

    @ApiOperation(value = "获取短信验证码")
    @RequestMapping(value = "/paycallback/weixinCallback",method = RequestMethod.POST)
    public void weixinCallback(HttpServletRequest request, HttpServletResponse response) {
        try {
            boolean result = orderService.callbackPay(request);
            if (result) {
                // 返回成功信息

                response.getWriter()
                        .write(" <xml> <return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
            } else {
                // 返回失败信息
                response
                        .getWriter()
                        .write(" <xml> <return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[fail info]]></return_msg></xml>");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
