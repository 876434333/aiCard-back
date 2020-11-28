package com.vma.push.business.dto.req.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Create By ChenXiAoBin
 * on 2018/6/14
 */
@Data
public class ReqApplyPage {

    @ApiModelProperty(value = "当前页面",required = true)
    int page_num;//当前页
    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size; //每页的记录数
    @ApiModelProperty(value = "企业名称")
    private String enterprise_name;
    @ApiModelProperty(value = "是否部署")
    private Integer is_deploy;
    @ApiModelProperty(value = "时间开始")
    private Date template_time;
    @ApiModelProperty(value = "时间结束")
    private Date template_time_end;

}
