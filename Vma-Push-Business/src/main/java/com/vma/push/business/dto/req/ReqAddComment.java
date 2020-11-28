package com.vma.push.business.dto.req;

import com.vma.push.business.entity.CircleCommentLog;
import com.vma.push.business.util.UuidUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/4/25.
 */
@Data
public class ReqAddComment {
    @ApiModelProperty(value = "评论内容",required = true)
    private String content;

    @ApiModelProperty(value = "评论人id",hidden = true)
    private String userId;

    @ApiModelProperty(value = "朋友圈id",required = true)
    private String circle_id;

    public CircleCommentLog toCircleCommentLog(){
        CircleCommentLog circleCommentLog=new CircleCommentLog();
        circleCommentLog.setCircleId(this.getCircle_id());
        circleCommentLog.setContent(this.getContent());
        circleCommentLog.setUserId(this.getUserId());
        circleCommentLog.setCreateTime(new Date());
        circleCommentLog.setModifyTime(new Date());
        circleCommentLog.setId(UuidUtil.getRandomUuid());
        circleCommentLog.setStatus(1);
        return circleCommentLog;
    }
}
