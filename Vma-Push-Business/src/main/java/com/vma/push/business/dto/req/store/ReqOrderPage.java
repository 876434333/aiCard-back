package com.vma.push.business.dto.req.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/7/26.
 */
@Data
public class ReqOrderPage {
    @ApiModelProperty(value = "订单编号")
    private String order_nbr;
    @ApiModelProperty(value = "收货人姓名")
    private String link_man;
    @ApiModelProperty(value = "收货人手机")
    private String link_phone;
    @ApiModelProperty(value = "下单时间 开始")
    private Date create_time_begin;
    @ApiModelProperty(value = "下单时间 结束")
    private Date create_time_end;
    @ApiModelProperty(value = "完成时间 开始")
    private Date finish_time_begin;
    @ApiModelProperty(value = "完成时间 结束")
    private Date finish_time_end;
    @ApiModelProperty(value = "商品名称")
    private String offer_name;
    @ApiModelProperty(value = "订单状态 0全部 1待支付 2待发货 3待收货 4已完成")
    private Integer status;
    @ApiModelProperty(value = "业务员")
    private String staff_name;
    @ApiModelProperty(value = "企业id",hidden = true)
    private String enterprise_id;

    private Integer page_size;
    private Integer page_num;
}
