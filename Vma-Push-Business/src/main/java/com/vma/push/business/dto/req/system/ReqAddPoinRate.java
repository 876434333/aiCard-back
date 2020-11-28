package com.vma.push.business.dto.req.system;

import com.vma.push.business.entity.PointRate;
import com.vma.push.business.util.UuidUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Create By ChenXiAoBin
 * on 2018/6/14
 */
@Data
public class ReqAddPoinRate {
    @ApiModelProperty(value = "贴牌商汇率(1迹点与元换算)")
    private Float oemRate;
    @ApiModelProperty(value = "地区总代理")
    private Float regionalRate;
    @ApiModelProperty(value = "代理商")
    private Float agentRate;

    public PointRate toPoinRate(){
        PointRate pointRate = new PointRate();
        pointRate.setId(UuidUtil.getRandomUuid());
        pointRate.setOemRate(this.getOemRate());
        pointRate.setAgentRate(this.getAgentRate());
        pointRate.setRegionalRate(this.getRegionalRate());
        return pointRate;

    }
}
