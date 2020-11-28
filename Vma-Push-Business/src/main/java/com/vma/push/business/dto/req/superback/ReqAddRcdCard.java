package com.vma.push.business.dto.req.superback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReqAddRcdCard {
    @ApiModelProperty(value = "员工ID列表")
    private List<String> staff_id_list;

}
