package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/6/8.
 */
@Data
public class ReqInteractDetail {

    @ApiModelProperty(value = "员工",hidden = true)
    String staff_id;
    @ApiModelProperty(value = "开始时间",required = true)
    private Date begin_time;
    @ApiModelProperty(value = "结束时间",required = true)
    private Date end_time;
    @ApiModelProperty(value = "用户id",required = true)
    String user_id;
}
