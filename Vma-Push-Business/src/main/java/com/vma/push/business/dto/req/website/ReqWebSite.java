package com.vma.push.business.dto.req.website;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by chenzui on 2018/5/15.
 */
@Data
public class ReqWebSite {
    @ApiModelProperty(value = "类型(1企业简介2电话3地址4联系我们5标题6文本7图片8轮播图9视频10占位符)",required = true)
    Integer type;
    @ApiModelProperty(value = "对应内容",required = true)
    String text_content;
    @ApiModelProperty(value = "对应组件的备注,例如图片的模板单图还是双图、视频的封面图、占位符的高度",required = true)
    String config;
}
