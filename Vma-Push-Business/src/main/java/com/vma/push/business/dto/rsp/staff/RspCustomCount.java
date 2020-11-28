package com.vma.push.business.dto.rsp.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/8/14.
 */
@Data
public class RspCustomCount {
    @ApiModelProperty(value = "目标客户数")
    private Integer from_custom;
    @ApiModelProperty(value = "重复客户数")
    private Integer repeat_custom;
    @ApiModelProperty(value = "实际交接客户数")
    private Integer actual_custom;

}
