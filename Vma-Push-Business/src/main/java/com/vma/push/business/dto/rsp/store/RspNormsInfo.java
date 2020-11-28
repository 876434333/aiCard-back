package com.vma.push.business.dto.rsp.store;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huxiangqiang on 2018/7/26.
 */
@Data
public class RspNormsInfo {
    private String norms_pic;
    private BigDecimal offer_price;
    private Integer offer_leave;
    private Integer is_default;
}
