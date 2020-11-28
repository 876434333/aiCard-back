package com.vma.push.business.dto.rsp.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by huxiangqiang on 2018/5/6.
 */
@Data
public class RspStaffPage {
    @ApiModelProperty(value = "数据")
    List<RspStaff> data_list;
    @ApiModelProperty(value = "总记录数")
    Long total_num;
    @ApiModelProperty(value = "当前分页数")
    Integer page_num;
    @ApiModelProperty(value = "分页大小")
    Integer page_size;
//    private String id;
//
//    private String name;
//
//    private String phone;
//
//    private Integer status;
//
//    private String create_staff_id;
//
//    private String department_id;
//
//    private String station;
//
//    private String head_icon;
//
//    private Integer open_ai;
//
//    private Integer open_boss;
//    public RspStaffPage(){}
//
//    public RspStaffPage(Staff staff){
//        this.setId(staff.getId());
//        this.setName(staff.getName());
//        this.setPhone(staff.getPhone());
//        this.setStation(staff.getStation());
//        this.setCreate_staff_id(staff.getCreateStaffId());
//        this.setDepartment_id(staff.getDepartmentId());
//        this.setStatus(staff.getStatus());
//        this.setHead_icon(staff.getHeadIcon());
//        this.setOpen_ai(staff.getOpenAi());
//        this.setOpen_boss(staff.getOpenBoss());
//    }
}
