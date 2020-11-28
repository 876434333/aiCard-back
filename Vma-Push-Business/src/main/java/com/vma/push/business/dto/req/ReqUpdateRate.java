package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Created by huxiangqiang on 2018/6/13.
 */
@Data
public class ReqUpdateRate {
    @ApiModelProperty(value = "用户id",required = true)
    private String user_id;
    @ApiModelProperty(value = "成交率",required = true)
    private Integer rate;
    @ApiModelProperty(value = "员工id",hidden = true)
    private String staff_id;
}
