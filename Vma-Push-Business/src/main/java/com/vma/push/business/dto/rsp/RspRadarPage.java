package com.vma.push.business.dto.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/5/10 20:37
 */
@Data
public class RspRadarPage <T>{
    @ApiModelProperty(value = "数据")
    List<T> data_list;
    @ApiModelProperty(value = "总记录数")
    Long total_num;
    @ApiModelProperty(value = "当前分页数")
    Integer page_num;
    @ApiModelProperty(value = "分页大小")
    Integer page_size;
    @ApiModelProperty(value = "名片数量")
     Integer sale_card_num;
    @ApiModelProperty(value = "已使用名片")
     Integer used_car_num;
}
