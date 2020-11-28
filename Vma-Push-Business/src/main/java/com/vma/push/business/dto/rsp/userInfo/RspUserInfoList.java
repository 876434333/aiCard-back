package com.vma.push.business.dto.rsp.userInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by chenzui on 2018/5/11.
 */
@Data
public class RspUserInfoList {

    @ApiModelProperty(value = "昵称")
    String nick_name;
    @ApiModelProperty(value = "最后跟进时间")
    Date last_attach_time;
    @ApiModelProperty(value = "最后动作时间")
    Date last_action_time;
    @ApiModelProperty(value = "头像url")
    String head_icon;
    @ApiModelProperty(value = "姓名")
    String name;
    @ApiModelProperty(value = "来源")
    String froms;
    @ApiModelProperty(value = "ID")
    String id;
    @ApiModelProperty(value = "AI成交率")
    BigDecimal rate;



}
