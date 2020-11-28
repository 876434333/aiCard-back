package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/7/16.
 */
@Data
public class ReqOpenIdNew {
    @ApiModelProperty(value = "微信code",required = true)
    private String code;
    @ApiModelProperty(value = "微擎id",required = true)
    private String wq_id;
}
