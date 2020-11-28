package com.vma.push.business.dto.req.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/8/14.
 */
@Data
public class ReqCustomCount {
    @ApiModelProperty(value = "交出方id")
    private String from_id;
    @ApiModelProperty(value = "接受方id")
    private String to_id;
}
