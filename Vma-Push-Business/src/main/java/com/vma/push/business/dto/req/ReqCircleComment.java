package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ReqCircleComment {
    @ApiModelProperty(value = "当前页",required = true)
    int page_num;
    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size;
    @ApiModelProperty(value = "企业ID",hidden = true)
    String employee_id;
    @ApiModelProperty(value = "开始时间",required = false)
    Date begin_time;
    @ApiModelProperty(value = "结束时间",required = false)
    Date end_time;
    @ApiModelProperty(value = "内容关键字",required = false)
    String related;
    @ApiModelProperty(value = "0所有 1企业 2个人",required = true)
    Integer query_type;
}
