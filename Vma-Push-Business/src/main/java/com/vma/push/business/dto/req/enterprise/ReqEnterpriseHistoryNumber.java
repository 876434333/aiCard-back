package com.vma.push.business.dto.req.enterprise;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by JINzm
 * 2018/6/21
 */
@Data
public class ReqEnterpriseHistoryNumber {
    @ApiModelProperty(value = "企业id")
    private String id;
    @ApiModelProperty(value = "天数")
    private Integer dayNumber;
}
