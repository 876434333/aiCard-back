package com.vma.push.business.dto.req.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/7/25.
 */
@Data
public class ReqAddOffer {
    @ApiModelProperty(value="企业id",hidden = true)
    private String enterprise_id;
    @ApiModelProperty(value="商品名称",required = true)
    private String name;
    @ApiModelProperty(value="是否支持线上支付",required = true)
    private Integer is_pay_online;
    @ApiModelProperty(value = "图片")
    private List<RspOfferImg> imgs;
    @ApiModelProperty(value = "是否上架 1上架 0未上架")
    private Integer status;

    @ApiModelProperty(value="提成方式 1固定提成 0百分比提成")
    private Integer extract_type;
    @ApiModelProperty(value="提成额度")
    private BigDecimal extract_value;
    @ApiModelProperty(value="提成比例")
    private BigDecimal extract_per;

    @ApiModelProperty(value="规格类型 0单规格 1多规格")
    private Integer type;
    @ApiModelProperty(value="规格")
    private List<RspOfferNorms> norms;



}
