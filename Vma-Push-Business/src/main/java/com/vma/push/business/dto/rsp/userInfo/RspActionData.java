package com.vma.push.business.dto.rsp.userInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * Created by huxiangqiang on 2018/6/6.
 */
@Data
public class RspActionData {
    @ApiModelProperty(value = "动作")
    private String action_code;

    @ApiModelProperty(value = "次数")
    private String count;

    @ApiModelProperty(value = "描述")
    private String description;


}
