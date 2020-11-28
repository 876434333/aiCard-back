package com.vma.push.business.dto.rsp.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by JINzm on 2018/7/17.
 */
@Data
public class RspStaffOrderPage {
    @ApiModelProperty(value = "数据")
    List<RspCustomList> data_list;
    @ApiModelProperty(value = "总记录数")
    Long total_num;
    @ApiModelProperty(value = "当前分页数")
    Integer page_num;
    @ApiModelProperty(value = "分页大小")
    Integer page_size;
    @ApiModelProperty(value = "用户ID")
    private String staff_id;
    @ApiModelProperty(value = "用户名称")
    private String staff_name;
}
