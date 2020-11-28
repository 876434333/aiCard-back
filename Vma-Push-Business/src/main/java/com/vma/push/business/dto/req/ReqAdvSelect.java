package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/5/3 13:54
 */
@Data
public class ReqAdvSelect {
    @ApiModelProperty(value = "当前页面",required = true)
    int page_num;//当前页
    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size; //每页的记录数
    @ApiModelProperty(value = "广告名称",required = true)
    private String title;//广告名称
    @ApiModelProperty(value = "位置",required = true)
    private Integer location;//位置
    @ApiModelProperty(value = "状态",required = true)
    private Integer status;//状态
    @ApiModelProperty(value = "企业ID",hidden = true)
    private String enterprise_id;//企业ID

}
