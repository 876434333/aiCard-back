package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/5/3 23:21
 */
@Data
public class ReqUpdateAdvStatus {
    @ApiModelProperty(value = "广告ID",required = true)
    String id;
    @ApiModelProperty(value = "广告状态",required = true)
    int status;
}
