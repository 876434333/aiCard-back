package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by JINzm
 * 2018/6/21
 */
@Data
public class RspEnterpriseHistoryNumber {
    @ApiModelProperty(value = "昨天")
    private Integer yesterday;
    @ApiModelProperty(value = "今天")
    private Integer today;
    @ApiModelProperty(value = "全部")
    private Integer all;
}