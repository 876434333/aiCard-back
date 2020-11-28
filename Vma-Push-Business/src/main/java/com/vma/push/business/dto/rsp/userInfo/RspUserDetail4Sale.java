package com.vma.push.business.dto.rsp.userInfo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by chenzui on 2018/5/14.
 * 销售端用户明细
 */
@Data
public class RspUserDetail4Sale {

    //昵称
    String nick_name;
    //名字
    String name;
    //来源
    Integer froms;
    //电话
    String phone;
    //微信绑定的号码
    String wx_phone;
    //邮箱
    String mail;
    //公司
    String company;
    //职位
    String position;
    //生日
    Date birthday;
    //备注
    String remark;
    //标签
    List<String> label;
    //成为用户日期
    Date create_time;
    //头像
    String head_icon;
    //成为用户日期
    Date modify_time;
    //id
    String id;

}