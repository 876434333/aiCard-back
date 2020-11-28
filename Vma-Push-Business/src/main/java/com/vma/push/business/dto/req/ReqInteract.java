package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/6/8.
 */
@Data
public class ReqInteract {
    @ApiModelProperty(value = "单前页",required = true)
    int page_num;
    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size;
    @ApiModelProperty(value = "员工",hidden = true)
    String staff_id;
    @ApiModelProperty(value = "开始时间",required = true)
    private Date begin_time;
    @ApiModelProperty(value = "结束时间",required = true)
    private Date end_time;
}
