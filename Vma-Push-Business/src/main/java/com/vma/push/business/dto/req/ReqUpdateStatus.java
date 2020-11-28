package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/4/26 21:22
 */
@Data
public class ReqUpdateStatus {
    @ApiModelProperty(value = "产品ID",required = true)
    String id;
    @ApiModelProperty(value = "产品状态",required = true)
    int status;
}
