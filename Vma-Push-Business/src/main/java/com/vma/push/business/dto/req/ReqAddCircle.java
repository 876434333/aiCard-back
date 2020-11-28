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
public class ReqAddCircle {

    @ApiModelProperty(value = "企业id",hidden  = true)
    private String enterprise_id;

    @ApiModelProperty(value = "员工id",hidden = true)
    private String employee_id;

    @ApiModelProperty(value = "内容",required = true)
    private String content;

    @ApiModelProperty(value = "标题",required = false)
    private String title;

    @ApiModelProperty(value = "封面图",required = false)
    private String cover;

    @ApiModelProperty(value = "图片路径 数组",required = false)
    private List<String> img_urls;

    @ApiModelProperty(value = "动态类型  0朋友圈 1小图模式 2大图模式",required = true)
    private Integer type;

    @ApiModelProperty(value = "动态权限 0个人动态 1公司动态",required = true)
    private Integer flag;

    public CircleCompany toCircleCompany(){
        CircleCompany circleCompany=new CircleCompany();
        circleCompany.setContent(this.getContent());
        circleCompany.setCreateTime(new Date());
        circleCompany.setModifyTime(new Date());
        circleCompany.setPingNum(0);
        circleCompany.setZanNum(0);
        circleCompany.setId(UuidUtil.getRandomUuid());
        circleCompany.setEmployeeId(this.getEmployee_id());
        circleCompany.setEnterpriseId(this.getEnterprise_id());
        circleCompany.setType(this.getType());
        circleCompany.setCover(this.getCover());
        circleCompany.setTitle(this.getTitle());
        return circleCompany;
    }
    public CircleImg toCircleImg(){
        CircleImg circleImg=new CircleImg();
        circleImg.setId(UuidUtil.getRandomUuid());
        circleImg.setStatus(1);
        return circleImg;
    }

}
