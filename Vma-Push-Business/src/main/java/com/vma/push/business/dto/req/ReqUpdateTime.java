package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/6/13.
 */
@Data
public class ReqUpdateTime {
    @ApiModelProperty(value = "用户id",required = true)
    private String user_id;
    @ApiModelProperty(value = "时间",required = true)
    private Date times;
    @ApiModelProperty(value = "员工id",hidden = true)
    private String staff_id;
}
