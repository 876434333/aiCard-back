package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ChenXiaoBin
 * 2018/5/6 21:38
 */
@Data
public class ReqEnterpriseSelect {
    @ApiModelProperty(value = "当前页")
    int page_num;//当前页
    @ApiModelProperty(value = "每页记录数")
    int page_size; //每页的记录数
    @ApiModelProperty(value = "企业名称")
    private String name;//企业名称
    @ApiModelProperty(value = "到期时间（开始）")
    private Date begin_time;//到期时间 开始
    @ApiModelProperty(value = "到期时间（结束）")
    private Date end_time;//到期时间  结束
    @ApiModelProperty(value = "状态")
    private Integer status;//状态
    @ApiModelProperty(value = "代理商")
    private String agent;
    @ApiModelProperty(value = "创建人id", hidden = true)
    private String create_staff_id;//
}
