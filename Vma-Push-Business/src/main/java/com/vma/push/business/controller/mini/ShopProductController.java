package com.vma.push.business.controller.mini;

import com.vma.push.business.dao.ShopProductMapper;
import com.vma.push.business.dto.shop.ShopProduct;
import com.vma.push.business.service.IShopProductService;
import com.vma.push.business.util.UserDataUtil;
import com.vma.push.business.util.UuidUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ljh
 * @Description: 产品
 * @date 2018-10-29 14:25
 */
@RestController
@RequestMapping("/v2.0")
@Api(value = "微信小程序", description = "小程序--商城--产品api", tags = {"ShopProductController"})
public class ShopProductController {

    @Autowired
    private IShopProductService productService;

    @ApiOperation(value = "查询产品列表", notes = "根据企业ID查询产品列表")
    @RequestMapping(value = "/mini/shop/product/list/{entId}", method = RequestMethod.GET)
    public List<ShopProduct> productList(@PathVariable("entId") String entId) {
        return productService.getProductList(entId,null);
    }

    @ApiOperation(value = "查询产品详情", notes = "根据产品ID查询产品详情")
    @RequestMapping(value = "/mini/shop/product/detail", method = RequestMethod.GET)
    public ShopProduct productetail(String productId) {
        return (ShopProduct) productService.getProductetail(productId);
    }

    @ApiOperation(value = "新建产品", notes = "新建产品")
    @RequestMapping(value = "/mini/shop/product/add", method = RequestMethod.POST)
    public void productAdd(HttpServletRequest request, @RequestBody ShopProduct product) {
        productService.productAdd(request,product);
    }

    @ApiOperation(value = "修改产品", notes = "修改产品")
    @RequestMapping(value = "/mini/shop/product/upd", method = RequestMethod.POST)
    public void productUpd(@RequestBody ShopProduct product) {
        productService.productUpd(product);
    }

    @ApiOperation(value = "删除产品", notes = "删除产品")
    @RequestMapping(value = "/mini/shop/product/del/{productId}", method = RequestMethod.GET)
    public void productDel(@PathVariable("productId") String productId) {
        productService.productDel(productId);
    }
}
