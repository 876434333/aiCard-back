package com.vma.push.business.dto.req.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/6/15.
 */
@Data
public class ReqFindParam {
    @ApiModelProperty(value = "上级id")
    private String parent_id;

    @ApiModelProperty(value = "名称或者电话")
    private String name_or_phone;

    @ApiModelProperty(value = "当前页")
    private Integer page_num;

    @ApiModelProperty(value = "页面大小")
    private Integer page_size;
}
