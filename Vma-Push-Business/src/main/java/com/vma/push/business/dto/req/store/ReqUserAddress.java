package com.vma.push.business.dto.req.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Create By ChenXiAoBin
 * on 2018/7/24
 */
@Data
public class ReqUserAddress {
    @ApiModelProperty(value = "当前页",required = true)
    int page_num;//当前页
    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size; //每页的记录数
    @ApiModelProperty(value = "用户id")
    private String user_id;
}
