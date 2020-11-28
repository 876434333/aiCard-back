package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huxiangqiang on 2018/7/26.
 */
@Data
public class RspStaffDiscount {
    @ApiModelProperty(value = "企业名字")
    private String enterprise_name;
    @ApiModelProperty(value="员工名字")
    private String staff_name;
    @ApiModelProperty(value ="专属折扣 0-10")
    private BigDecimal discount;
    @ApiModelProperty(value="头像")
    private String head_icon;
}
