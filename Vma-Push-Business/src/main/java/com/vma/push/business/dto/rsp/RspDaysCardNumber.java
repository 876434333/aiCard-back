package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by JINzm on 2018/6/14.
 */
@Data
public class RspDaysCardNumber {
    @ApiModelProperty(value = "日期")
    private String create_time;

    @ApiModelProperty(value = "名片数")
    private Long card_number;
}
