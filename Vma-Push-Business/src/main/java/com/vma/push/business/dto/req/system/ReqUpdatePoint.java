package com.vma.push.business.dto.req.system;

import com.vma.push.business.entity.PointRate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Create By ChenXiAoBin
 * on 2018/6/15
 */
@Data
public class ReqUpdatePoint {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "贴牌商汇率(1迹点与元换算)")
    private Float oem_rate;
    @ApiModelProperty(value = "地区总代理")
    private Float regional_rate;
    @ApiModelProperty(value = "代理商")
    private Float agent_rate;

    public PointRate toPointRate(){
        PointRate pointRate = new PointRate();
        pointRate.setId(this.getId());
        pointRate.setAgentRate(this.getAgent_rate());
        pointRate.setOemRate(this.getOem_rate());
        pointRate.setRegionalRate(this.getRegional_rate());
        pointRate.setModifyTime(new Date());
        return pointRate;
    }
}
