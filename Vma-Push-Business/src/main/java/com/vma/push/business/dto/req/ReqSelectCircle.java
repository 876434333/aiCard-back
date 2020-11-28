package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/5/12.
 */
@Data
public class ReqSelectCircle {

    @ApiModelProperty(value = "发布人名称",required = true)
    private String name;

    @ApiModelProperty(value = "类型 不传所有 1企业 0个人",required = true)
    private Integer flag;

    @ApiModelProperty(value = "企业",hidden = true)
    private String enterprise_id;

    @ApiModelProperty(value = "开始时间",required = true)
    private Date begin_time;

    @ApiModelProperty(value = "结束时间",required = true)
    private Date end_time;

    @ApiModelProperty(value = "页数",required = true)
    private Integer page_num;

    @ApiModelProperty(value = "每页条数",required = true)
    private Integer page_size;
}
