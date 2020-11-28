package com.vma.push.business.dto.req.template;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Create By ChenXiAoBin
 * on 2018/6/13
 */
@Data
public class ReqTemplateSelect {
    @ApiModelProperty(value = "模版名称")
    private String template_name;
    @ApiModelProperty(value = "当前页面",required = true)
    int page_num;//当前页
    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size; //每页的记录数
}
