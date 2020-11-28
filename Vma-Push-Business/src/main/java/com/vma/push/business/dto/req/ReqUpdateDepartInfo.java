package com.vma.push.business.dto.req;

import com.vma.push.business.entity.Department;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/4/25.
 */
@Data
public class ReqUpdateDepartInfo {

    @ApiModelProperty(value = "部门ID",required = true)
    private String id;

    @ApiModelProperty(value = "上级部门ID",required = true)
    private String parent_id;

    @ApiModelProperty(value = "所属企业id",hidden = true)
    private String enterprise_id;

    @ApiModelProperty(value = "部门名称",required = true)
    private String name;

   /* @ApiModelProperty(value = "修改时间",required = true)
    private Date modify_time;*/


    public Department toDepartment(){
        Department department=new Department();
        department.setModifyTime(new Date());
        department.setParentId(this.getParent_id());
        department.setId(this.getId());
        department.setName(this.getName());
        department.setEnterpriseId(this.getEnterprise_id());
        return department;
    }

}
