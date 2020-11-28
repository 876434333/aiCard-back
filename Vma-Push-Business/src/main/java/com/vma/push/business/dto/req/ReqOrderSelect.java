package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ChenXiaoBin
 * 2018/5/2 23:21
 */
@Data
public class ReqOrderSelect {
    @ApiModelProperty(value = "当前页",required = true)
    int page_num;//当前页
    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size; //每页的记录数
    @ApiModelProperty(value = "订单编号",required = true)
    private String order_nbr;//订单编号
    @ApiModelProperty(value = "状态",required = true)
    private Integer status;//状态
    @ApiModelProperty(value = "购买人",required = true)
    private String nick_name;//购买人
    @ApiModelProperty(value = "开始时间",required = true)
    private Date begin_time;
    @ApiModelProperty(value = "结束时间",required = true)
    private Date end_time;
    @ApiModelProperty(value = "电话",required = true)
    private String link_phone;//电话
    @ApiModelProperty(value = "企业id" ,hidden = true)
    private String enterprise_id;
}
