package com.vma.push.business.dto.rsp;

import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/6/8.
 */
@Data
public class RspActionDetail {
    private String id;
    private String nick_name;
    private String head_icon;
    private String description;
    private String num;
    private Date create_time;
}
