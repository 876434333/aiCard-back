package com.vma.push.business.dto.rsp;

import com.vma.push.business.entity.Department;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/5/5.
 */
@Data
public class RspDepartPage {
    //部门id
    private String id;

    //上级部门id
    private String parent_id;

    //部门名称
    private String name;

    //排序
    private Integer order;

    //企业id
    private String enterprise_id;

    public RspDepartPage(){}

    public RspDepartPage(Department department){
        this.setEnterprise_id(department.getEnterpriseId());
        this.setId(department.getId());
        this.setParent_id(department.getParentId());
        this.setOrder(department.getOrder());
        this.setName(department.getName());
    }

}
