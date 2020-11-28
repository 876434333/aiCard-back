package com.vma.push.business.dto.rsp;

import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/4/26.
 */
@Data
public class RspCommentInfo {
    //评论人id
    private String user_id;

    //评论人名字
    private String user_name;

    //评论时间
    private Date modify_time;

    //评论内容
    private String content;

    //用户头像
    private String head_icon;

    //评论id
    private String id;

    // 1公司 0 销售人员 2客户
    private Integer flag;
}
