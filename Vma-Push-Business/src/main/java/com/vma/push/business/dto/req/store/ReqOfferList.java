package com.vma.push.business.dto.req.store;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/7/25.
 */
@Data
public class ReqOfferList {
    @ApiModelProperty(value = "页数")
    private Integer page_num;
    @ApiModelProperty(value = "页面大小")
    private Integer page_size;
    @ApiModelProperty(value = "查询类型 1已上架 2已售罄 0未上架")
    private Integer type;
    @ApiModelProperty(value="商品名称")
    private String offer_name;
    @ApiModelProperty(value = "企业id",hidden = true)
    private String enterprise_id;
}
