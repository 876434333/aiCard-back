package com.vma.push.business.dto.rsp.userInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by chenzui on 2018/5/14.
 */
@Data
public class RspUserSimpleInfo {

    @ApiModelProperty(value = "头像url")
    String head_icon;
    @ApiModelProperty(value = "昵称")
    String nick_name;
    @ApiModelProperty(value = "姓名")
    String name;
    @ApiModelProperty(value = "预计成交率")
    Double rate;
    @ApiModelProperty(value = "环信id")
    String hx_im_login;
    @ApiModelProperty(value = "电话")
    String phone;
    @ApiModelProperty(value = "预计交易时间")
    Date clinch_time;
    @ApiModelProperty(value = "微信绑定手机号")
    String bind_phone;
}
