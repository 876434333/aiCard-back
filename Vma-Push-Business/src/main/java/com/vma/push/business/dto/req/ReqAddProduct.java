package com.vma.push.business.dto.req;

import com.vma.push.business.entity.OfferSpec;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ChenXiaoBin
 * 2018/4/24 20:24
 */
@Data
public class ReqAddProduct {
   @ApiModelProperty(value = "产品ID",required = true)
   private String id;
   @ApiModelProperty(value = "产品名称",required = true)
   private String offerName;
   @ApiModelProperty(value = "产品价格",required = true)
   private BigDecimal offerPrice;
    @ApiModelProperty(value = "创建时间",required = true)
   private Date createSpecTime;
   @ApiModelProperty(value = "产品价格",required = true)
   private  Date updateTime;
    @ApiModelProperty(value = "商品编码",required = true)
   private String code;
    @ApiModelProperty(value = "创建人的id",required = true)
   private String staffId;


    @ApiModelProperty(value = "图片d",required = true)
   private  String imgId;
    @ApiModelProperty(value = "产品规格id",required = true)
   private String offerSpecId;
    @ApiModelProperty(value = "图片路径",required = true)
   private String imgUrl;
    @ApiModelProperty(value = "图片创建时间",required = true)
   private Date createTime;
    @ApiModelProperty(value = "图片修改时间",required = true)
   private Date modifyTime;
    @ApiModelProperty(value = "图片状态",required = true)
   private int status;
    @ApiModelProperty(value = "图片参数",required = true)
   private  int type;

}
