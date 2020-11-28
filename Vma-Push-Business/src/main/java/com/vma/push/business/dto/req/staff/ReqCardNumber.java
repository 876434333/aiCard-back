package com.vma.push.business.dto.req.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by JINzm
 * 2018/6/14
 */
@Data
public class ReqCardNumber {
    @ApiModelProperty(value = "企业id")
    private String enterprise_id;
    @ApiModelProperty(value = "天数")
    private Integer dayNumber;
}
