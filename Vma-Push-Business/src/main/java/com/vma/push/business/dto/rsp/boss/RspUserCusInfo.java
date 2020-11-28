package com.vma.push.business.dto.rsp.boss;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/8/22.
 */
@Data
public class RspUserCusInfo {
    @ApiModelProperty(value = "客户数")
    private Integer customer;
    @ApiModelProperty(value = "跟进用户数")
    private Integer attach;
    @ApiModelProperty(value = "咨询数")
    private Integer consult;
}
