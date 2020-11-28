package com.vma.push.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/5/16.
 */
@Data
public class ReqMiniCirclePage {
    @ApiModelProperty(value = "单前页",required = true)
    int page_num;
    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size;
    @ApiModelProperty(value = "销售人员id",hidden = true)
    String  staff_id;
    @ApiModelProperty(value = "客户id",hidden = true)
    String  user_id;
}
