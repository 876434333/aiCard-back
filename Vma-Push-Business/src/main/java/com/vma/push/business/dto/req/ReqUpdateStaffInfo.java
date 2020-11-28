package com.vma.push.business.dto.req;

import com.vma.push.business.entity.Staff;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/4/24.
 */
@Data
public class ReqUpdateStaffInfo {

    @ApiModelProperty(value = "人员id",required = true)
    String id;

    @ApiModelProperty(value = "微信id",required = true)
    String wx_id;

    @ApiModelProperty(value = "姓名",required = true)
    String name;

    @ApiModelProperty(value = "部门ID",required = true)
    int[] department_id;

    @ApiModelProperty(value = "岗位",required = true)
    String station;

    @ApiModelProperty(value = "电话",required = true)
    String phone;

    @ApiModelProperty(value = "头像",required = true)
    String head_icon;

    @ApiModelProperty(value = "操作员工ID",required = true)
    String staff_id;

    @ApiModelProperty(value = "企业id",hidden = true)
    String enterprise_id;

    @ApiModelProperty(value = "名片模板ID")
    Integer temlate_id;

    @ApiModelProperty(value = "微信")
    String weixin;

    public Staff toStaff(){
        Staff staff=new Staff();
        staff.setModifyTime(new Date());
        staff.setCreateStaffId(this.getStaff_id());
        staff.setName(this.getName());
        staff.setPhone(this.getPhone());
        staff.setDepartmentId(String.valueOf(this.getDepartment_id()[0]));
        staff.setStation(this.getStation());
        staff.setHeadIcon(this.getHead_icon());
        staff.setId(this.getId());
        staff.setEnterpriseId(this.enterprise_id);
        staff.setTemlateId(this.getTemlate_id());
        staff.setWeixin(this.getWeixin());
        return staff;
    }
}
