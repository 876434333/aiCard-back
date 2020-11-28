package com.vma.push.business.dto.req;

import com.vma.push.business.entity.Staff;
import com.vma.push.business.util.UuidUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by kk on 2018/4/19.
 */
@Data
public class Employee {
    @ApiModelProperty(value = "用2户id", required = true)
    private String userid;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "部门编号")
    private int[] department;

    @ApiModelProperty(value = "职称信息")
    private String position;

    @ApiModelProperty(value = "电话号码 企业内必须唯一，mobile/weixinid/email三者不能同时为空")
    private String mobile;

    @ApiModelProperty(value = "性别。1表示男性，2表示女性")
    private String gender;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "微信id")
    private String weixinid;

    @ApiModelProperty(value = "成员头像的mediaid，通过多媒体接口上传图片获得的mediaid")
    private String avatar_mediaid;

    @ApiModelProperty(value = "扩展属性。扩展属性需要在WEB管理端创建后才生效，否则忽略未知属性的赋值")
    private String extattr;

    public Employee() {

    }

    public Employee(ReqAddStaffInfo reqAddStaffInfo) {
        this.setUserid(UuidUtil.getRandomUuidWithoutSeparator());
        this.setMobile(reqAddStaffInfo.getPhone());
        this.setName(reqAddStaffInfo.getName());
        // M by PLH at 2018-11-28 for支持企业微信多组织
        //this.setDepartment(reqAddStaffInfo.getDepartment_id());
        int[] deptIds = new int[1];
        deptIds[0] = reqAddStaffInfo.getDepartment_id();
        this.setDepartment(deptIds);
    }

    public Employee(ReqUpdateStaffInfo reqUpdateStaffInfo){
        //this.setUserid(reqUpdateStaffInfo.getStaff_id());
        this.setMobile(reqUpdateStaffInfo.getPhone());
        this.setName(reqUpdateStaffInfo.getName());
        this.setUserid(reqUpdateStaffInfo.getWx_id());

        this.setDepartment(reqUpdateStaffInfo.getDepartment_id());
    }

    public Employee(Staff staff){
        //this.setUserid(staff.getId());
        this.setMobile(staff.getPhone());
        this.setName(staff.getName());
        this.setUserid(staff.getWxId());

        // M by PLH at 2018-11-28 for支持企业微信多组织
        //this.setDepartment(staff.getDepartmentId());
        int[] deptIds = new int[1];
        deptIds[0] = Integer.valueOf(staff.getDepartmentId());
        this.setDepartment(deptIds);
    }


}
