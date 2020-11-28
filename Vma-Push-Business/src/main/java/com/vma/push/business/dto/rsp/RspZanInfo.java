package com.vma.push.business.dto.rsp;

import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/4/26.
 */
@Data
public class RspZanInfo {
    //点赞人id
    private String user_id;

    //点赞人名字
    private String user_name;

    //点赞时间
    private Date modify_time;

    //标志位 1公司 0 销售人员 2客户
    private Integer flag;

}
