package com.vma.push.business.dto.req;

import com.vma.push.business.entity.Staff;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by chenzui on 2018/4/23.
 */
@Data
public class ReqAddStaffInfo {

    @ApiModelProperty(value = "姓名",required = true)
    String name;

    @ApiModelProperty(value = "部门ID",required = true)
    int department_id;

    @ApiModelProperty(value = "岗位",required = true)
    String station;

    @ApiModelProperty(value = "电话",required = true)
    String phone;

    @ApiModelProperty(value = "头像",required = true)
    String head_icon;

    @ApiModelProperty(value = "操作员工ID",hidden = true)
    String staff_id;
    @ApiModelProperty(value = "企业id",hidden = true)
    String enterprise_id;
    @ApiModelProperty(value = "名片模板ID",required = true)
    Integer temlate_id;
    @ApiModelProperty(value = "openid")
    String openid;
    @ApiModelProperty(value = "isPlatform")
    int isPlatform;
    public Staff toStaff(){
        Staff staff=new Staff();
        //this.setId(UuidUtil.getRandomUuid());
        staff.setCreateTime(new Date());
        staff.setModifyTime(new Date());
        staff.setCreateStaffId(this.getStaff_id());
        staff.setName(this.getName());
        staff.setPhone(this.getPhone());
        staff.setDepartmentId(String.valueOf(this.getDepartment_id()));
        staff.setStation(this.getStation());
        staff.setStatus(1);
        staff.setTemlateId(this.getTemlate_id());
        if(this.getHead_icon()!=null) {
            staff.setHeadIcon(this.getHead_icon());
        } else {
            //staff.setHeadIcon("https://keji-res.h5h5h5.cn/imgdd01338adc0c41978016a1faf16185f38png");
            staff.setHeadIcon("https://res.deecard.net/dc_user_default_logo.png");
        }
        staff.setEnterpriseId(this.enterprise_id);
        staff.setOpenid(this.openid);
        return staff;
    }
}
