package com.vma.push.business.util;

import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;

import java.util.*;

/**
 * Created by chenzui on 2018/5/29.
 */
public class SignUtil {

    /**
     * 生成客户端的加密签名
     * @param macKey
     * @return
     */

    public String createSignKey(String macKey){

        String signKey = MD5Helper.getMD5Str(macKey+DateFormatUtils.formate(new Date()));

        UserDataUtil.setSignKey(macKey,signKey);

        return signKey;
    }

    public static boolean sign(Map<String,String> map,String macKey){

        String key = UserDataUtil.getSignKey(macKey);

        if(key==null || "".equals(key)){
            throw new BusinessException(ErrCode.LOGIN_TIME_OUT,"mack超时");
        }

        String sign = createSign(map,key);

        if(map.get("sign").toString().equals(sign)){

            return true;
        }else {

            return false;
        }

    }

    private static String createSign(Map<String, String> paramMap,String macKey) {
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>(paramMap);
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + macKey);
        String sign = MD5Helper.getMD5Str(sb.toString()).toUpperCase();
        return sign;
    }


}
