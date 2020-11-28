package com.vma.push.business.dto.rsp.store;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huxiangqiang on 2018/7/24.
 */
@Data
public class RspOrderInfo {
    private String norms_pic;
    private String offer_name;
    private String norms_name;
    private BigDecimal order_price;
    private BigDecimal order_price_discount;
    private Integer offer_num;
}
