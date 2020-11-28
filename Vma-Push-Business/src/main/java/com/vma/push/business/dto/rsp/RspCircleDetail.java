package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/4/26.
 */
@Data
public class RspCircleDetail {
    @ApiModelProperty(value = "员工id")//员工id
    private String employee_id;
    @ApiModelProperty(value = "朋友圈ID")//朋友圈
    private String circle_id;
    @ApiModelProperty(value = "员工名称")//员工名称
    private String name;
    @ApiModelProperty(value = "标题")//标题
    private String title;
    @ApiModelProperty(value = "内容")//内容
    private String content;
    @ApiModelProperty(value = "发布时间")//发布时间
    private Date create_time;
    @ApiModelProperty(value = "发布人头像")//发布人头像
    private String head_icon;
    @ApiModelProperty(value = "点赞信息集合")//点赞信息集合
    private List<RspZanInfo> circle_zan_logs;
    @ApiModelProperty(value = "评论信息集合")//评论信息集合
    private List<RspCommentInfo> circle_comment_logs;
    @ApiModelProperty(value = "朋友圈图片集合")//朋友圈图片集合
    private List<String> circle_imgs;
    @ApiModelProperty(value = "朋友圈类型0个人1公司")//朋友圈类型
    private Integer flag;
    @ApiModelProperty(value = "0朋友圈 1小图模式 2大图模式")//类型
    private Integer type;
    @ApiModelProperty(value = "封面")//发布人头像
    private String cover;
    @ApiModelProperty(value = "是否由当前销售人员发布的0不是1是")//发布人头像
    private Integer is_me;
    @ApiModelProperty(value = "1点赞、0未点赞")//发布人头像
    private Integer is_zan;
    


}
