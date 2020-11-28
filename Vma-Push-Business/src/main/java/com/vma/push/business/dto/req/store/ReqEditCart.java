package com.vma.push.business.dto.req.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/7/24.
 */
@Data
public class ReqEditCart {
    @ApiModelProperty(value = "id",required =  true)
    private String id;
    @ApiModelProperty(value = "规格id",required = true)
    private String norms_id;
    @ApiModelProperty(value = "购买数量",required = true)
    private Integer num;
}
