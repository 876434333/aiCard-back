package com.vma.push.business.dto.req.store;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huxiangqiang on 2018/7/26.
 */
@Data
public class ReqEnterDiscount {
    private String enterprise_id;
    private BigDecimal count;
    private String id;
    private Integer type;
}
