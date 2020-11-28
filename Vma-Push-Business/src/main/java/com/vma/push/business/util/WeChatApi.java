package com.vma.push.business.util;

import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dao.EnterpriseMapper;
import com.vma.push.business.dao.StaffMapper;
import com.vma.push.business.dto.req.AllSecret;
import com.vma.push.business.dto.req.RepOpenId;
import com.vma.push.business.entity.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huxiangqiang on 2018/4/24.
 */
@Service
public class WeChatApi {
@Autowired
 private EnterpriseMapper enterpriseMapper;

@Autowired
private StaffMapper staffMapper;
    //公司的CorpID
    //private static final String CorpID = "wwc49a7c5b05d65aa9";

    //通讯录的secret
    //private static final String contactssecret =  "6VH-11nNzYA8JlMNBXXD57d7hon90uEpcjHjIfD3-pc";

    //boss雷达的secret
   // private static final String bosssecret =  "84FIVcyN0JJU1D-Pkar4VtVxqV845UXV4XmJzcMXvTc";

    //ai雷达的secret

   // private static final String aisecret =  "0VMYzX2xcWVP68A5fJ2k1DSvfkqny9IrclY0VM0GBG4";


//    public static final String boss_AgentId="1000004";
//
//    public static  final String ai_AgentId="1000005";

    public String findBoosAgentId(String enterprise_id){
        return enterpriseMapper.findBoosAgentId(enterprise_id);
    }
    public String findAiAgentId(String enterprise_id){
        return enterpriseMapper.findAiAgentId(enterprise_id);
    }
    private  String getToken(String secret,String id){

        String CorpID = enterpriseMapper.findCropID(id);
        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + CorpID + "&corpsecret=" + secret;
        String message=HttpUtil.httpGetReq(url).getResponse_str();
        String result="";
        try{
            if (new JSONObject(message).getString("access_token") !=null){

                result = new JSONObject(message).getString("access_token");
                //return result;
            }
        }
        catch (Exception e){
            throw new BusinessException(10001,"密钥配置错误");
        }
        return  result;
    }
    //获取微信小程序appid 微擎
    public  String getAppid(String unacid){
//         String url = "https://keji-publish.h5h5h5.cn/app/kejiindex.php?i=" + unacid;
        String url = "https://publish.deecard.szrenzhi.com/app/kejiindex.php?i=" + unacid;
        String message=HttpUtil.httpGetReq(url).getResponse_str();
        Integer errno=new org.json.JSONObject(message).getInt("errno");
        String result="";
        if (errno==0){
            result=new org.json.JSONObject(message).getJSONObject("data").getString("key");
        }else{
            throw new BusinessException(1000,"小程序unacid参数错误！");
        }
        return  result;
    }

    //获取微信小程序 secret 微擎
    public  String getSecret(String unacid){
        //String url = "https://keji-publish.h5h5h5.cn/app/kejiindex.php?i=" + unacid;
        String url = "https://publish.deecard.szrenzhi.com/app/kejiindex.php?i=" + unacid;
        String message=HttpUtil.httpGetReq(url).getResponse_str();
        Integer errno=new org.json.JSONObject(message).getInt("errno");
        String result="";
        if (errno==0){
            result=new org.json.JSONObject(message).getJSONObject("data").getString("secret");
        }else{
            throw new BusinessException(1000,"小程序unacid参数错误！");
        }
        return  result;
    }
    public String getopenid(String appid,String secret,String code){
/*        String code=repOpenId.getCode();
        String enterpriseId=repOpenId.getEnterprise_id();
        String staffId=repOpenId.getStaff_id();
        if(enterpriseId == null || "".equals(enterpriseId.trim())){
        //if (enterpriseId==""){
            Staff staff = staffMapper.selectByPrimaryKey(staffId);
            enterpriseId=staff.getEnterpriseId();
        }
        AllSecret allSecret=enterpriseMapper.AllSecret(enterpriseId);*/
  /*      String appid=allSecret.getApp_id();
        String secret=allSecret.getSecret();*/
        String url="https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
        String result=HttpUtil.httpGetReq(url).getResponse_str();
        String openid = new JSONObject(result).getString("openid");
        String sessionkey = new JSONObject(result).getString("session_key");
        UserDataUtil.setSessionKey(openid,sessionkey);
        //System.out.print("session_key-------------------------------"+session_key);
        return openid;
    }

    /**
     * 获取boss雷达的token
     * @return
     */
    public  String boss_token(String id){
      String bosssecret =  enterpriseMapper.getBosssecret(id);
        return getToken(bosssecret,id);
    }



    /**
     * 获取ai雷达的token
     * @return
     */
    public  String ai_token(String id){
        String aisecret = enterpriseMapper.findAisecret(id);
        String token = UserDataUtil.getWxToken(id);
        if(token != null && !"".equals(token)){
            return token;
        }else {
            token = getToken(aisecret,id);
            UserDataUtil.setWxToken(token,id);
            return token;
        }

    }

    /**
     * 获取通讯录的token
     * @return
     */
    public  String Contacts_token(String id){
        String contactssecret = enterpriseMapper.getContactssecret(id);
        return getToken(contactssecret,id);
    }

    /**
     * jsapi_ticket
     * @return
     */
    public  String jsapi_ticket(String id){
        String secret=enterpriseMapper.getContactssecret(id);
        String token=getToken(secret,id);
        String url="https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token="+token;
        String result= HttpUtil.httpGetReq(url).getResponse_str();
        return new JSONObject(result).getString("ticket");
    }

    public String findAppId(String id){
        return enterpriseMapper.findAppId(id);
    }

    /**
     * 通过code获取ai雷达的用户idh
     * @param code
     * @return
     */
    public  String aiUserIdByCode(String code,String id){
        String url="https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token="+ ai_token(id)+"&code="+code;
        String result=HttpUtil.httpGetReq(url).getResponse_str();
        if (new JSONObject(result).getInt("errcode")!=0)
        {
            return "";
        }
        else{
            String userid=new JSONObject(result).getString("UserId");
            return userid;
        }
    }

    /**
     * 通过code获取boss雷达的用户id
     * @param code
     * @return
     */
    public  String bossUserIdByCode(String code,String id){
        String url="https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token="+ boss_token(id)+"&code="+code;
        String result=HttpUtil.httpGetReq(url).getResponse_str();
        if (new JSONObject(result).getInt("errcode")!=0)
        {
            return "";
        }
        else{
            String userid=new JSONObject(result).getString("UserId");
            return userid;
        }

    }
    /**
     * 对象转map
     * @param obj
     * @return
     */
    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    if(value != null && !"".equals(value)){
                        map.put(key, value);
                    }
                }

            }
        } catch (Exception e) {
            //LOGGER.error("transBean2Map Error {}" ,e);
        }
        return map;

    }
}
