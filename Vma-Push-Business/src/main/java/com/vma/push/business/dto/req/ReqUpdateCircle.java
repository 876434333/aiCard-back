package com.vma.push.business.dto.req;

import com.vma.push.business.entity.CircleCompany;
import com.vma.push.business.entity.CircleImg;
import com.vma.push.business.util.UuidUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/4/25.
 */
@Data
public class ReqUpdateCircle {
    @ApiModelProperty(value = "朋友圈编号",required = true)
    private String id;

    @ApiModelProperty(value = "内容",required = true)
    private String content;

    @ApiModelProperty(value = "图片路径",required = true)
    private List<String> img_urls;

    public CircleCompany toCircleCompany(){
        CircleCompany circleCompany=new CircleCompany();
        circleCompany.setId(this.getId());
        circleCompany.setModifyTime(new Date());
        circleCompany.setContent(this.getContent());
        return circleCompany;
    }

    public CircleImg toCircleImg(){
        CircleImg circleImg=new CircleImg();
        circleImg.setId(UuidUtil.getRandomUuid());
        circleImg.setStatus(1);
        circleImg.setiOrder(1);
        return circleImg;
    }
}
