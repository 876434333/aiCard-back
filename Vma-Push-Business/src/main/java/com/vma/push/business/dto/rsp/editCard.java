package com.vma.push.business.dto.rsp;

import com.vma.push.business.entity.Staff;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/5/14.
 */
@Data
public class editCard {

    @ApiModelProperty(value = "ID")
    String id;

    @ApiModelProperty(value = "微信id")
    String wx_id;

    @ApiModelProperty(value = "头像")
    String head_icon;

    @ApiModelProperty(value = "销售人员姓名")
    String name;

    @ApiModelProperty(value = "岗位")
    String station;

    @ApiModelProperty(value = "部门id")
    String department_id;

    @ApiModelProperty(value = "部门名称")
    String department_name;

    @ApiModelProperty(value = "座机")
    String mobile;

    @ApiModelProperty(value = "邮箱")
    String mail;

    @ApiModelProperty(value = "微信")
    String weixin;

    @ApiModelProperty(value = "二维码")
    String soft_img_url;

    @ApiModelProperty(value = "手机号")
    String phone;
    @ApiModelProperty(value = "名片模板ID")
    Integer temlateId;
    @ApiModelProperty(value = "地址")
    String address;
    @ApiModelProperty(value = "个性签名")
    String signature;

    @ApiModelProperty(value = "图片/视频/音频介绍")
    List<RspStaffIntro> rspStaffIntro;



    public Staff toStaff(){
        Staff staff=new Staff();
        staff.setId(this.id);
        staff.setHeadIcon(this.getHead_icon());
        staff.setName(this.getName());
        staff.setStation(this.getStation());
        staff.setDepartmentId(this.getDepartment_id());
        staff.setMobile(this.getMobile());
        staff.setMail(this.getMail());
        staff.setWeixin(this.getWeixin());
        staff.setAddress(this.getAddress());
        staff.setPhone(this.getPhone());
        staff.setModifyTime(new Date());
        staff.setSignature(this.getSignature());
        staff.setWxId(this.getWx_id());
        staff.setTemlateId(this.getTemlateId());
        return staff;
    }

}
