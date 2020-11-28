package com.vma.push.business.dto.req.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huxiangqiang on 2018/8/6.
 */
@Data
public class ReqStaffDiscount {
    @ApiModelProperty(value = "员工id")
    private String  id;
    @ApiModelProperty(value = "折扣")
    private BigDecimal discount;
    @ApiModelProperty(value = "类型 1 个人特权 0企业特权")
    private Integer type;
    @ApiModelProperty(value = "企业id",hidden = true)
    private String enterprise_id;
}
