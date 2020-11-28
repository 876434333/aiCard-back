package com.vma.push.business.service.impl;

import com.mysql.jdbc.StringUtils;
import com.vma.push.business.service.IMessageService;
import com.vma.push.business.util.HttpUtil;
import com.vma.push.business.util.WeChatApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huxiangqiang on 2018/4/24.
 */
@Service
public class MessageServiceImpl implements IMessageService {
    @Autowired
    private WeChatApi weChatApi;

    @Override
    public String sendMsg_ai(String[] userid,String content,String enterprise_id) {
        String url="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+ weChatApi.ai_token(enterprise_id);
        Map param = new HashMap();
        Map cont=new HashMap();
        param.put("agentid",weChatApi.findAiAgentId(enterprise_id));
        param.put("msgtype","text");
        param.put("touser",array2string(userid));
        cont.put("content",content);
        param.put("text",cont);
        return HttpUtil.httpPostReq(url,param).getResponse_str();
    }

    @Override
    public String sendMsg_boss(String[] userid, String content,String enterprise_id) {
        String url="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+ weChatApi.boss_token(enterprise_id);
        Map param = new HashMap();
        Map cont=new HashMap();
        param.put("agentid",weChatApi.findBoosAgentId(enterprise_id));
        param.put("msgtype","text");
        param.put("touser",array2string(userid));
        cont.put("content",content);
        param.put("text",cont);
        return HttpUtil.httpPostReq(url,param).getResponse_str();
    }

    /**
     * 数组转string   [1,2,3]--->1|2|3
     * @param users 用户组
     * @return
     */
    private String array2string(String[] users){
        String s=Arrays.toString(users).replace(" ","");
        return s.substring(1, s.length()-1).replace(",","|");
    }
}
