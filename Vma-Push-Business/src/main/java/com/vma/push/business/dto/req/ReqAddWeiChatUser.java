package com.vma.push.business.dto.req;

import lombok.Data;

/**
 * Created by huxiangqiang on 2018/6/26.
 */
@Data
public class ReqAddWeiChatUser {
    private String name;
    private String userid;
    private String department;
    private String phone;
    private String head_icon;
    private String mail;
    private String position;
}
