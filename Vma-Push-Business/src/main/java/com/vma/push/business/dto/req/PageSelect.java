package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/4/25 21:33
 */
@Data
public class PageSelect {
    @ApiModelProperty(value = "商品名称",required = true)
    String offer_name;//商品名称
    @ApiModelProperty(value = "状态")
    Integer status; //状态
    @ApiModelProperty(value = "当前页",required = true)
    int page_num;//当前页
    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size; //每页的记录数

    @ApiModelProperty(value = "不需要传")
    String enterprise_id;
}
