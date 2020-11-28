package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * Created by huxiangqiang on 2018/6/5.
 */
@Data
public class ReqMiniSendMsg {




    //private String openId;
    @ApiModelProperty(value = "客户id",required = true)
    private String user_id;

    @ApiModelProperty(value = "form_id",required = true)
    private String form_id;

    @ApiModelProperty(value = "消息内容",hidden = true)
    private String content;


    //private String secure;


    @ApiModelProperty(value = "员工id",hidden = true)
    private String staff_id;

    @ApiModelProperty(value = "部门id",hidden = true)
    private String department_id;

    @ApiModelProperty(value = "企业id",hidden = true)
    private String enterprise_id;

    @ApiModelProperty(value = "推送时间",hidden = true)
    private String times;

    @ApiModelProperty(value = "员工名称",hidden = true)
    private String staff_name;

    @ApiModelProperty(value = "模板id",hidden = true)
    private String template_id;
}
