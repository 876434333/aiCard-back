package com.vma.push.business.dto.req.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Create By ChenXiAoBin
 * on 2018/6/21
 */
@Data
public class ReqCardNum {
    @ApiModelProperty(value = "卡片数量")
    private Integer car_num;
    @ApiModelProperty(value = "企业ID")
    private String id;

}
