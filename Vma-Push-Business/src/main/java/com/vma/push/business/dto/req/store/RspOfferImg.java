package com.vma.push.business.dto.req.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/7/25.
 */
@Data
public class RspOfferImg {
    @ApiModelProperty(value="图片路径")
    private String url;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "类型 1封面图2详情图")
    private Integer type;
}
