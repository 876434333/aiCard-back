package com.vma.push.business.dto.req.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * created by linzh on 2018/6/15
 */
@Data
public class ReqAdminPage {
    @ApiModelProperty(value = "当前页面",required = true)
    int page_num;               //当前页
    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size;              //每页的记录数
    @ApiModelProperty(value = "账号/姓名")
    private String login_name;  //账号/姓名
    @ApiModelProperty(value = "所属客户id")
    private String custom_id;
}
