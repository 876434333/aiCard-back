package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ChenXiaoBin
 * 2018/4/25 20:08
 */
@Data
public class RepAllProduct {
    @ApiModelProperty(value = "ID")
    private String id;
    @ApiModelProperty(value = "产品名称")
    private String offer_name;
    @ApiModelProperty(value = "产品价格")
    private BigDecimal offer_price;
    @ApiModelProperty(value = "创建时间")
    private Date create_time;
    @ApiModelProperty(value = "创建人")
    private String name;
    @ApiModelProperty(value = "状态")
    private int status;
}
