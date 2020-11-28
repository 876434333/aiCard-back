package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by JINzm
 * 2018/8/6
 */
@Data
public class ReqAdvSelectNew {
    @ApiModelProperty(value = "当前页面",required = true)
    int page_num;//当前页
    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size; //每页的记录数
    @ApiModelProperty(value = "广告名称",required = false)
    private String title;//广告名称
    @ApiModelProperty(value = "企业ID",hidden = true)
    private String enterprise_id;//企业ID
    @ApiModelProperty(value = "开始时间",required = false)
    private Date finish_begin_time;
    @ApiModelProperty(value = "结束时间",required = false)
    private Date finish_end_time;
}
