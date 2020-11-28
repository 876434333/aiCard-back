package com.vma.push.business.dto.req.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/7/24.
 */
@Data
public class ReqMyOrderPage {
    @ApiModelProperty(value = "单前页",required = true)
    int page_num;
    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size;
    @ApiModelProperty(value = "类型 0未支付 1已支付 2用户取消 3超时取消 4未发货 5已发货 6完成",required = true)
    String type;
}
