package com.vma.push.business.dto.rsp;

import com.vma.push.business.entity.Staff;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/5/6.
 */
@Data
public class RspRadarStaffInfo {

    private String id;

    private String name;

    private String phone;

    private Integer status;

    private String create_staff_id;

    private String department_id;

    private String station;

    private String head_icon;

    private Integer open_ai;

    private Integer open_boss;

    public RspRadarStaffInfo(){}

    public RspRadarStaffInfo(Staff staff){
        this.setId(staff.getId());
        this.setName(staff.getName());
        this.setPhone(staff.getPhone());
        this.setStation(staff.getStation());
        this.setCreate_staff_id(staff.getCreateStaffId());
        this.setDepartment_id(staff.getDepartmentId());
        this.setStatus(staff.getStatus());
        this.setHead_icon(staff.getHeadIcon());
        this.setOpen_ai(staff.getOpenAi());
        this.setOpen_boss(staff.getOpenBoss());
    }
}
