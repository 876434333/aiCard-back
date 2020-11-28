package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by huxaingqiang on 2018/4/19.
 */
public class WechatDepartment {
    @ApiModelProperty(value = "部门id，整型。指定时必须大于1，不指定时则自动生成")
    private String id;

    @ApiModelProperty(value = "在父部门中的次序值。order值小的排序靠前。")
    private int order;

    @ApiModelProperty(value = "上级部门id 公司id为1")
    private String parentid;

    @ApiModelProperty(value = "部门名称。长度限制为32个字（汉字或英文字母），字符不能包括\\:*?\"<>｜"    )
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parent_id) {
        this.parentid = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WechatDepartment(){};
    public WechatDepartment(ReqAddDepartInfo reqAddDeaprtInfo){
        //this.setId(UuidUtil.getRandomUuid());
        this.setParentid(reqAddDeaprtInfo.getParent_id());
        this.setName(reqAddDeaprtInfo.getName());
        this.setOrder(1);
    }
    public WechatDepartment(ReqUpdateDepartInfo reqUpdateDepartInfo){
        this.setName(reqUpdateDepartInfo.getName());
        this.setId(reqUpdateDepartInfo.getId());
        this.setParentid(reqUpdateDepartInfo.getParent_id());
    }

}
