package com.vma.push.business.dto.rsp.template;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Create By ChenXiAoBin
 * on 2018/6/13
 */
@Data
public class RspTemplatePage {

    @ApiModelProperty(value = "模版id")
    private String id;
    @ApiModelProperty(value = "编号")
    private String code;
    @ApiModelProperty(value = "企业数")
    private Integer enterprise_num;
    @ApiModelProperty(value = "小程序费用")
    private Long small_cost;
    @ApiModelProperty(value = "名片费用")
    private Long card_cost;
    @ApiModelProperty(value = "模版名称")
    private String template_name;
    @ApiModelProperty(value = "创建时间")
    private Date create_time;
    @ApiModelProperty(value = "修改时间")
    private Date Modify_time;
    @ApiModelProperty(value = "状态")
    private Integer status;
}
