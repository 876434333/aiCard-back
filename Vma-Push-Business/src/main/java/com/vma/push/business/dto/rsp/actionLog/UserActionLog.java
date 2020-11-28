package com.vma.push.business.dto.rsp.actionLog;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by chenzui on 2018/5/14.
 */
@Data
public class UserActionLog {

    @ApiModelProperty(value = "头像")
    String head_icon;
    @ApiModelProperty(value = "昵称")
    String nick_name;
    @ApiModelProperty(value = "姓名")
    String name;
    @ApiModelProperty(value = "动作描述")
    String description;
    @ApiModelProperty(value = "动作次数")
    String num;
    @ApiModelProperty(value = "动作编码")
    String action_code;
    @ApiModelProperty(value = "时间")
    Date create_time;
}
