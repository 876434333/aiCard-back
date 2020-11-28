package com.vma.push.business.dto.rsp.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by huxiangqiang on 2018/8/22.
 */
@Data
public class RspStaffOfferIntro {
    @ApiModelProperty(value = "推荐商品id")
    private String recid;
    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品名称")
    private String  offer_name;
    @ApiModelProperty(value = "商品价格")
    private BigDecimal offer_price;
    @ApiModelProperty(value = "创建时间")
    private Date create_time;
    @ApiModelProperty(value = "图片")
    private String norms_pic;
    @ApiModelProperty(value = "是否推荐 1是 0不是")
    private Integer istop;
    @ApiModelProperty(value = "推荐id")
    private String intro_id;
    @ApiModelProperty(value = "语音介绍")
    private String voice_intro;

}

