package com.vma.push.business.dto.req;

import com.vma.push.business.entity.Order;
import com.vma.push.business.util.DateFormatUtils;
import com.vma.push.business.util.UuidUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by huxiangqiang on 2018/5/4.
 */
@Data
public class ReqAddOrder {
    /*@ApiModelProperty(value = "主键",required = true)
    private String id;*/

    /*@ApiModelProperty(value = "订单号",required = true)
    private String orderNbr;*/

    @ApiModelProperty(value = "商品图片",required = true)
    private String offer_img;

    @ApiModelProperty(value = "联系电话",required = true)
    private String link_phone;

    @ApiModelProperty(value = "联系人",required = true)
    private String link_man;

    @ApiModelProperty(value = "商品id",required = true)
    private String offer_id;

    @ApiModelProperty(value = "订单数量",required = true)
    private Integer offer_num;

    /*@ApiModelProperty(value = "下单时间",required = true)
    private Date createTime;*/

    /*@ApiModelProperty(value = "状态  是否付款",required = true)
    private Integer status;*/

    @ApiModelProperty(value = "订单总额",required = true)
    private BigDecimal total_price;

    @ApiModelProperty(value = "客户id")
    private String user_id;

    @ApiModelProperty(value = "销售人员id",required = true)
    private String staff_id;

    /*@ApiModelProperty(value = "付款时间",required = true)
    private Date payTime;*/

    public Order toOrder(){
        Order order =new Order();
        order.setCreateTime(new Date());
        order.setId(UuidUtil.getRandomUuid());
        order.setOfferId(this.getOffer_id());
        order.setOrderNbr(DateFormatUtils.formate(new Date(),DateFormatUtils.TIGHT_PATTERN_DATETIME));
        order.setLinkMan(this.getLink_man());
        order.setStatus(0);//默认给0
        order.setLinkPhone(this.getLink_phone());
        order.setOfferNum(this.getOffer_num());
        order.setStaffId(this.getStaff_id());
        order.setUserId(this.getUser_id());
        order.setTotalPrice(this.getTotal_price());
        order.setOfferImg(this.getOffer_img());
        return order;
    }
}
