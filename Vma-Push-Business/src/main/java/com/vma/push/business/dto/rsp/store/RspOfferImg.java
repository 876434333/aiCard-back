package com.vma.push.business.dto.rsp.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/7/30.
 */
@Data
public class RspOfferImg {
    @ApiModelProperty(value="图片路径")
    private String img_url;
    @ApiModelProperty(value="图片类型 1封面图2详情图")
    private Integer type;
}