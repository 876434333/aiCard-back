package com.vma.push.business.service;

/**
 * Created by huxiangqiang on 2018/4/24.
 */
public interface IMessageService {
    /**
     * 推送ai雷达消息
     * @param userid 用户id
     * @param content 消息内容
     * @return
     */
    String sendMsg_ai(String[] userid,String content,String enterprise_id);

    /**
     * 推送boss雷达消息
     * @param userid 用户id
     * @param content 消息美容
     * @return
     */
    String sendMsg_boss(String[] userid,String content,String enterprise_id);

}
