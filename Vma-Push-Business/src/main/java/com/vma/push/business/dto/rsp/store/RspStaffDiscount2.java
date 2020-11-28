package com.vma.push.business.dto.rsp.store;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by huxiangqiang on 2018/7/30.
 */
@Data
public class RspStaffDiscount2 {
    private String staff_name;
    private BigDecimal discount;
    private String head_icon;
}
