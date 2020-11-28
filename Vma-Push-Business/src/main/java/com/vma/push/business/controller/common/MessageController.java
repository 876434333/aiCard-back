package com.vma.push.business.controller.common;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.util.HttpUtil;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * Created by chenzui on 2018/5/10.
 */

@RestController
@RequestMapping("/v4.0")
@Api(value = "短信API", description = "短信API", tags = {"MessageController"})
public class MessageController  extends ControllerExceptionHandler {


    @ApiOperation(value = "获取短信验证码")
    @RequestMapping(value = "/common/message/{phone}",method = RequestMethod.GET)
    public void send(@ApiParam(required=true, name="phone", value="电话") @PathVariable("phone") String phone){
        //String id= UuidUtil.getRandomUuid();
        String code = String.valueOf(System.currentTimeMillis()).substring(0,6);
        String url =  "http://112.90.92.219:8811/sms.aspx?action=send&userid=1003&account=chenzui&password=chenzui123&mobile="+phone+"&content="+createContent(phone,code)+"&sendTime=&extno=";
        HttpUtil.sendGet(url);
        //return id;

    }

    @ApiOperation(value = "校验短信验证码")
    @RequestMapping(value = "/common/message/check",method = RequestMethod.GET)
    public void send(@ApiParam(required=true, name="phone", value="电话") @RequestParam(value = "phone") String phone,
                       @ApiParam(required=true, name="code", value="code") @RequestParam(value = "code") String code){

        String ridisCode=UserDataUtil.getMsgCode(phone);
        if (code!=null&&!"".equals(code)){
            if (ridisCode==null&&"".equals(ridisCode)){
                throw new BusinessException(ErrCode.ERROR_PASSWORD,"验证码过期，请重新发送验证码");
            }
            if (!code.equals(ridisCode)){
                throw new BusinessException(ErrCode.ERROR_PASSWORD,"验证码错误，请重新输入");
            }
        }else{
            throw new BusinessException(ErrCode.ERROR_PASSWORD,"请输入验证码");
        }

    }

    private String createContent(String phone,String code){
        //String code = String.valueOf(System.currentTimeMillis()).substring(0,6);
        UserDataUtil.setMsgCode(phone,code);
        return "【客迹】您的验证码为:"+code;
    }

    public static void main(String[] args){
//        String code = String.valueOf(System.currentTimeMillis()).substring(0,5);
        String url =  "http://112.90.92.219:8811/sms.aspx?action=send&userid=1003&account=chenzui&password=chenzui123&mobile="+"15220089930"+"&content="+"【客迹】您的验证码:1234"+"&sendTime=&extno=";
        HttpUtil.sendGet(url);
    }
}
