package com.vma.push.business.dto.rsp.staff;

import com.vma.push.business.entity.Staff;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ChenXiaoBin
 * 2018/5/4 11:38
 */
@Data
public class RspStaff {
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
    @ApiModelProperty(value = "名片模板ID")
    private Integer temlate_id;
    @ApiModelProperty(value = "客户数")
    private  Integer custom_num;

    Long user_num;
    public RspStaff(){
        user_num = 0L;
    }

    public RspStaff(Staff staff){
        this.setId(staff.getId());
        this.setName(staff.getName());
        this.setPhone(staff.getPhone());
        this.setStation(staff.getStation());
        this.setHead_icon(staff.getHeadIcon());
        this.setOpen_ai(staff.getOpenAi());
        this.setOpen_boss(staff.getOpenBoss());
    }
}
