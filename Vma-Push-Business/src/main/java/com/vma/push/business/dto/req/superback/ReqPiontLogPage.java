package com.vma.push.business.dto.req.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * created by linzh on 2018/6/15
 */
@Data
public class ReqPiontLogPage {
    @ApiModelProperty(value = "当前页面",required = true)
    int page_num;//当前页
    @ApiModelProperty(value = "每页记录数",required = true)
    int page_size; //每页的记录数
    @ApiModelProperty(value = "操作开始时间")
    Date create_time_start;
    @ApiModelProperty(value = "操作结束时间")
    Date create_time_end;
    @ApiModelProperty(value = "父级节点",hidden = true)
    String parent_id;
    @ApiModelProperty(value = "操作",notes = "1 增加，2 扣减")
    String operation;
    @ApiModelProperty(value = "贴牌商、代理商、地区总代ID",required = false)
    String enterprise_id;
    @ApiModelProperty(value = "1企业  2代理商  3地区总代理  4贴牌商",required = false)
    Integer role;
    @ApiModelProperty(value = "0下级迹点1我的迹点",hidden = true)
    Integer is_me;
}
