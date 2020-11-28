package com.vma.push.business.dto.rsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/5/12.
 */
@Data
public class RspAllCircle {

    @ApiModelProperty(value="朋友圈id")
    private String id;
    @ApiModelProperty(value="员工名字")
    private String name;
    @ApiModelProperty(value="内容")
    private String content;
    @ApiModelProperty(value="发布时间")
    private Date create_time;
    @ApiModelProperty(value = "发布人头像")
    private String head_icon;
    @ApiModelProperty(value = "点赞数")
    private Integer zan_num;
    @ApiModelProperty(value = "评论数")
    private Integer ping_num;
    @ApiModelProperty(value = "朋友圈类型 0朋友圈 1小图 2大图")
    private Integer flag;
    @ApiModelProperty(value = "封面")
    private String cover;
    @ApiModelProperty(value = "朋友圈图片集合")
    private List<String> circle_imgs;
    @ApiModelProperty(value="标题")
    private String title;
}
