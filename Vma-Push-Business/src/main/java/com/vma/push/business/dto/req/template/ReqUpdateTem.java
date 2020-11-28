package com.vma.push.business.dto.req.template;

import com.vma.push.business.entity.ShopTemplate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Create By ChenXiAoBin
 * on 2018/6/13
 */
@Data
public class ReqUpdateTem {
    @ApiModelProperty(value = "模版id")
    private String id;
    @ApiModelProperty(value = "小程序费用")
    private Integer small_cost;
    @ApiModelProperty(value = "名片费用")
    private Integer card_cost;
    @ApiModelProperty(value = "模版名称")
    private String template_name;

    public ShopTemplate toShopTemplate(){
        ShopTemplate shopTemplate = new ShopTemplate();
        shopTemplate.setId(this.id);
        shopTemplate.setSmallCost(this.getSmall_cost());
        shopTemplate.setCardCost(this.getCard_cost());
        shopTemplate.setTemplateName(this.template_name);
        return shopTemplate;
    }
}
