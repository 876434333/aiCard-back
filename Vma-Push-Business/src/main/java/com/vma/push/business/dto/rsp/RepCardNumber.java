package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by JINzm
 * 2018/6/14
 */
@Data
public class RepCardNumber {
    @ApiModelProperty(value = "昨天")
    private Integer yesterday;
    @ApiModelProperty(value = "今天")
    private Integer today;
    @ApiModelProperty(value = "七天内")
    private Integer seven_day;
    @ApiModelProperty(value = "三十天内")
    private Integer thirty_day;
    @ApiModelProperty(value = "九十天内")
    private Integer ninety_day;
    @ApiModelProperty(value = "全部")
    private Integer all;
}
