package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/7/30.
 */
@Data
public class RspOffInfo {
    @ApiModelProperty(value = "商品id")
    private String offer_id;
    @ApiModelProperty(value = "商品名称")
    private String offer_name;
    @ApiModelProperty(value="快递费")
    private BigDecimal express_fee;
    @ApiModelProperty(value="单价")
    private BigDecimal offer_price;
    @ApiModelProperty(value = "员工专属折扣")
    private BigDecimal discount;
    @ApiModelProperty(value = "员工名称")
    private String staff_name;
    @ApiModelProperty(value = "员工头像")
    private String head_icon;
    @ApiModelProperty(value = "图片集合")
    private List<RspOfferImg> imgs;
    @ApiModelProperty(value = "是否支持线上支付 1是 0否")
    private Integer is_pay_online;

    @ApiModelProperty(value = "商品语音介绍")
    private String voice_intro;
    @ApiModelProperty(value = "0未上架 1上架 2售罄")
    private Integer status;
}
