package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/6/8.
 */
@Data
public class ReqActionCode {
    @ApiModelProperty(value = "页面大小",required = true)
    private Integer page_size;
    @ApiModelProperty(value = "当前页",required = true)
    private Integer page_num;
    @ApiModelProperty(value = "行为code",required = true)
    private String code;
    @ApiModelProperty(value = "开始时间",required = true)
    private Date begin_time;
    @ApiModelProperty(value = "结束时间",required = true)
    private Date end_time;
    @ApiModelProperty(value = "员工id",hidden = true)
    private String staff_id;
    @ApiModelProperty(value = "用户id")
    private String user_id;
}
