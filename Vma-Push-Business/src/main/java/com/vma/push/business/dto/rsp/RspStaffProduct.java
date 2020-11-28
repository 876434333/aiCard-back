package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/5/11.
 */
@Data
public class RspStaffProduct {
    @ApiModelProperty(value = "ID")
    private String id;
    @ApiModelProperty(value = "产品名称")
    private String offer_name;
    @ApiModelProperty(value = "产品价格")
    private BigDecimal offer_price;
    @ApiModelProperty(value = "创建时间")
    private Date create_time;
    @ApiModelProperty(value = "状态")
    private int status;
    @ApiModelProperty(value = "图片")
    private List<RspImage> imgs ;


    @ApiModelProperty(value = "销售数量")
    private Integer sale_num ;

    @ApiModelProperty(value = "浏览次数")
    private Integer view_num ;
    
    @ApiModelProperty(value = "打折")
    private Integer discount;


}
