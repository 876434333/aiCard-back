package com.vma.push.business.dto.req;

import com.vma.push.business.entity.CircleZanLog;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/4/25.
 */
@Data
public class ReqZan {
    @ApiModelProperty(value = "朋友圈id",required = true)
    private String circle_id;
    @ApiModelProperty(value = "用户id",hidden = true)
    private String userId;

    public CircleZanLog toCircleZanLog(){
        CircleZanLog circleZanLog=new CircleZanLog();
        circleZanLog.setCircleId(this.getCircle_id());
        circleZanLog.setUserId(this.getUserId());
        return circleZanLog;
    }
}
