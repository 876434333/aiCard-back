package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/6/21.
 */
@Data
public class RspEnterDetailList {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "剩余迹点")
    private Integer money_leave;



    @ApiModelProperty(value = "编号")
    private String enterprise_no;

    @ApiModelProperty(value = "logo")
    private String head_icon;

    @ApiModelProperty(value = "创建时间")
    private Date create_time;

    @ApiModelProperty(value = "累计迹点")
    private Integer money_sum;
}
