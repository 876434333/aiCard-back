package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/5/10.
 */
@Data
public class RspActionLog {

    @ApiModelProperty(value = "用户id")
    private String user_id;
    @ApiModelProperty(value = "用户")
    private String name;
    @ApiModelProperty(value = "动作描述")
    private String description;

    @ApiModelProperty(value = "动作id")
    private String action_code;

    @ApiModelProperty(value = "创建时间")
    private Date create_time;
    @ApiModelProperty(value = "次数")
    private String num;

    String head_icon;



}
