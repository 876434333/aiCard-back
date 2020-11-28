package com.vma.push.business.dto.req.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/7/24.
 */
@Data
public class ReqAddCart {
    @ApiModelProperty(value = "商品id",required = true)
    private String offer_id;

    @ApiModelProperty(value = "规格id",required = true)
    private String norms_id;

    @ApiModelProperty(value="数量",required = true)
    private Integer offer_num;

    @ApiModelProperty(value = "用户id",hidden = true)
    private String user_id;

    @ApiModelProperty(value = "销售id",hidden = true)
    private String staff_id;

}
