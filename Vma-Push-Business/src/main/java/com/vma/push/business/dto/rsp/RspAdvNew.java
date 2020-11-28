package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
/**
 * Created by JINzm
 * 2018/8/6
 */
@Data
public class RspAdvNew {
    @ApiModelProperty(value = "广告ID")
    private String id;
    @ApiModelProperty(value = "广告名称")
    private String title;//广告名称
    @ApiModelProperty(value = "广告链接")
    private String href;//链接
    @ApiModelProperty(value = "广告图片")
    private String img_url;//链接
    @ApiModelProperty(value = "广告地址")
    private int location;//位置
    @ApiModelProperty(value = "顺序")
    private int a_order;//顺序
    @ApiModelProperty(value = "创建时间")
    private Date create_time;//创建时间
    @ApiModelProperty(value = "创建人")
    private String name;//创建人
    @ApiModelProperty(value = "状态")
    private int status; //状态
    @ApiModelProperty(value = "开始时间")
    private Date begin_time;//创建时间
    @ApiModelProperty(value = "到期时间")
    private Date finish_time;//创建时间
}
