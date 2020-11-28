package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ChenXiaoBin
 * 2018/5/4 23:51
 */
@Data
public class RspImage {
    @ApiModelProperty(value = "图片id")
    private String id;
    @ApiModelProperty(value = "产品规格ID")
    private String offer_spec_id;
    @ApiModelProperty(value = "图片链接")
    private String img_url;
    @ApiModelProperty(value = "创建时间")
    private Date create_time;
    @ApiModelProperty(value = "修改时间")
    private Date modify_time;
    @ApiModelProperty(value = "图片是否有效")
    private Integer status;
    @ApiModelProperty(value = "封面图或详情图")
    private Integer type;
    @ApiModelProperty(value = "排序")
    private Integer order;

}
