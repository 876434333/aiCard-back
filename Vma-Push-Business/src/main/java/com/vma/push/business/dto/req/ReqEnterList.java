package com.vma.push.business.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/6/14.
 */
@Data
public class ReqEnterList {
    @ApiModelProperty(value = "角色", hidden = true)
    private Integer role;

    @ApiModelProperty(value = "上级id", hidden = true)
    private String parent_id;

    @ApiModelProperty(value = "电话或者名称")
    private String name_or_phone;

    @ApiModelProperty(value = "开始时间")
    private Date begin_time;

    @ApiModelProperty(value = "结束时间")
    private Date end_time;

    @ApiModelProperty(value = "省份代码")
    private String province_code;

    @ApiModelProperty(value = "市代码")
    private String city_code;

    @ApiModelProperty(value = "当前页")
    private Integer page_num;

    @ApiModelProperty(value = "页面大小")
    private Integer page_size;

    @ApiModelProperty(value = "状态  是否部署",hidden = true)
    private Integer is_deploy;

    @ApiModelProperty(value = "状态  是否启用  1启用 0禁用")
    private Integer status;

}
