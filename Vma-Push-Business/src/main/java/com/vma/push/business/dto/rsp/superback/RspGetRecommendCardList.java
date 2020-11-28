package com.vma.push.business.dto.rsp.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by huxiangqiang on 2018/6/20.
 */
@Data
public class RspGetRecommendCardList {
    @ApiModelProperty(value = "recommendId")
    private String recommendId;

    @ApiModelProperty(value = "staff_id")
    private String staffId;

    @ApiModelProperty(value = "员工头像")
    private String headIcon;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "企业名字")
    private String enterpriseName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "注册日期")
    private String createTime;

    @ApiModelProperty(value = "浏览次数")
    private String viewNum;
}
