package com.vma.push.business.dto.rsp.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by JINzm
 * 2018/7/19
 */
@Data
public class RspCustomList {
    @ApiModelProperty(value = "用户ID")
    private String id;
    @ApiModelProperty(value = "昵称")
    private String nick_name;
    @ApiModelProperty(value = "头像")
    private String head_icon;
    @ApiModelProperty(value = "最后一次跟进时间")
    private Date last_attach_time;//最后一次跟进时间
    @ApiModelProperty(value = "成交率")
    private Float close_rate;//成交率
    @ApiModelProperty(value = "预计成交时间")
    private Date clinch_time;//预计成交时间
    @ApiModelProperty(value = "用户ID")
    private String staff_id;
}
