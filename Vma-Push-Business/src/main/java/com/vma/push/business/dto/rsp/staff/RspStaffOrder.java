package com.vma.push.business.dto.rsp.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by JINzm
 * 2018/7/17
 */
@Data
public class RspStaffOrder {
    @ApiModelProperty(value = "ID")
    private String id;
    @ApiModelProperty(value = "wx_id")
    private String wx_id;
    @ApiModelProperty(value = "头像")
    private String head_icon;//头像
    @ApiModelProperty(value = "姓名")
    private String name;//姓名
    @ApiModelProperty(value = "岗位")
    private String station;//岗位
    @ApiModelProperty(value = "手机")
    private String phone;//手机
    @ApiModelProperty(value = "部门")
    private String department_name;//部门名字
    @ApiModelProperty(value = "部门ID")
    private String department_id;
    @ApiModelProperty(value = "AI雷达开关")
    private Integer open_ai;
    @ApiModelProperty(value = "boss雷达开关")
    private Integer open_boss;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "二维码")
    private String soft_img_url;
    @ApiModelProperty(value = "订单数、客户数、跟进客户数/咨询客户数、完成订单数")
    private Integer count;

    Long user_num;
    public RspStaffOrder(){
        user_num = 0L;
    }
}
