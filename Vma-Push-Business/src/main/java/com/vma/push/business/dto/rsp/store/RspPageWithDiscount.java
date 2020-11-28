package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/7/30.
 */
@Data
public class RspPageWithDiscount<T> {
    @ApiModelProperty(value = "数据")
    List<T> data_list;
    @ApiModelProperty(value = "总记录数")
    Long total_num;
    @ApiModelProperty(value = "当前分页数")
    Integer page_num;
    @ApiModelProperty(value = "分页大小")
    Integer page_size;
    @ApiModelProperty(value = "折扣")
    BigDecimal discount;
}
