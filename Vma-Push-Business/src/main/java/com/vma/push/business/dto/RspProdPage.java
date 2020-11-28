package com.vma.push.business.dto;

import com.vma.push.business.dto.rsp.RspStaffProduct;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by chenzui on 2018/6/15.
 */
@Data
public class RspProdPage {
    @ApiModelProperty(value = "数据")
    List<RspStaffProduct> data_list;
    @ApiModelProperty(value = "总记录数")
    Long total_num;
    @ApiModelProperty(value = "当前分页数")
    Integer page_num;
    @ApiModelProperty(value = "分页大小")
    Integer page_size;
    @ApiModelProperty(value = "未付款订单数")
    private Integer orderTotal;
}
