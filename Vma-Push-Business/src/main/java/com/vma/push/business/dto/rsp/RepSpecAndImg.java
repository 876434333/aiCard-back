package com.vma.push.business.dto.rsp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/4/25 12:05
 */
@Data
public class RepSpecAndImg {
    @ApiModelProperty(value = "ID")
    private String id;
    @ApiModelProperty(value = "产品名称")
    private String offer_name;
    @ApiModelProperty(value = "产品价格")
    private BigDecimal offer_price;
    @ApiModelProperty(value = "企业状态")
    private Integer status;
    @ApiModelProperty(value = "图片链接")
    List<RspImage> img_url;
    @ApiModelProperty(value = "封面图或者详情图")
    private  int type;
    @ApiModelProperty(value = "销售数量")
    private Integer sale_num ;
    @ApiModelProperty(value = "浏览次数")
    private Integer view_num ;



}
