package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/5/24 14:22
 */
@Data
public class RspOrderPage<T> {
    @ApiModelProperty(value = "数据")
    List<T> data_list;
    @ApiModelProperty(value = "总记录数")
    Long total_num;
    @ApiModelProperty(value = "当前分页数")
    Integer page_num;
    @ApiModelProperty(value = "分页大小")
    Integer page_size;
    @ApiModelProperty(value = "未付款订单数")
    private Integer orderTotal;
}
