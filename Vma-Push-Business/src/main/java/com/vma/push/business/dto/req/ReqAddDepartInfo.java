package com.vma.push.business.dto.req;

import com.vma.push.business.entity.Department;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/4/25.
 */
@Data
public class ReqAddDepartInfo {

    @ApiModelProperty(value = "上级部门Id",required = true)
    private String parent_id;

    @ApiModelProperty(value = "部门名称",required = true)
    private String name;

    /*@ApiModelProperty(value = "创建时间",required = true)
    private Date create_time;*/

  /*  @ApiModelProperty(value = "修改时间",required = true)
    private Date modify_time;*/

    /*@ApiModelProperty(value = "排序",required = true)
    private Integer order;*/

    @ApiModelProperty(value = "所属企业id",hidden = true)
    private String enterprise_id;

    public Department toDepartment(){
        Department department=new Department();
        department.setModifyTime(new Date());
        department.setCreateTime(new Date());
        department.setEnterpriseId(this.getEnterprise_id());
        department.setName(this.getName());
        department.setOrder(1);
        department.setParentId(this.getParent_id());
        return department;
    }
}
