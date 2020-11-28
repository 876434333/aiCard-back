package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by JINzm
 * 2018/5/25 14:37
 */
@Data
public class RspEnterpriseNumber {
    @ApiModelProperty(value = "贴牌商数量")
    private Integer first_number;
    @ApiModelProperty(value = "总代理商数量")
    private Integer second_number;
    @ApiModelProperty(value = "代理商数量")
    private Integer third_number;
    @ApiModelProperty(value = "企业数量")
    private Integer enterprise_number;
}
