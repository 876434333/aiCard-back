package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/8/2.
 */
@Data
public class RspDiscountList<T> {
    @ApiModelProperty(value = "数据")
    List<T> data_list;
    @ApiModelProperty(value = "总记录数")
    Long total_num;
    @ApiModelProperty(value = "当前分页数")
    Integer page_num;
    @ApiModelProperty(value = "分页大小")
    Integer page_size;
    @ApiModelProperty(value = "企业折扣")
    BigDecimal discount;
    @ApiModelProperty(value = "企业名称")
    String enterprise_name;
}
