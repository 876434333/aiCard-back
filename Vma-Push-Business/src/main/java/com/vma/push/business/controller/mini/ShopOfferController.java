package com.vma.push.business.controller.mini;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.dto.shop.Category;
import com.vma.push.business.dto.shop.ShopOfferSpec;
import com.vma.push.business.service.IShopProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ljh
 * @Description: 商品
 * @date 2018-10-29 14:25
 */
@RestController
@RequestMapping("/v2.0")
@Api(value = "微信小程序", description = "小程序--商城--商品api", tags = {"ShopOfferController"})
public class ShopOfferController{

    @Autowired
    private IShopProductService offerService;

    @ApiOperation(value = "查询商品列表", notes = "根据企业ID查询商品列表")
    @RequestMapping(value = "/mini/shop/offer/list/{entId}/{categoryId}", method = RequestMethod.GET)
    public List<ShopOfferSpec> offerList(@PathVariable("entId") String entId,@PathVariable("categoryId") String categoryId) {
        return offerService.getProductList(entId,categoryId);
    }

    @ApiOperation(value = "查询商品列表,按状态", notes = "根据企业ID、状态查询商品列表")
    @RequestMapping(value = "/mini/shop/offer/list/{entId}/{status}/{categoryId}", method = RequestMethod.GET)
    public List<ShopOfferSpec> offerList(@PathVariable("entId") String entId,@PathVariable("status") Integer status,@PathVariable("categoryId") String categoryId) {
        return offerService.getProductList(entId, status, categoryId);
    }

    @ApiOperation(value = "查询商品详情", notes = "根据商品ID查询商品详情")
    @RequestMapping(value = "/mini/shop/offer/detail", method = RequestMethod.GET)
    public ShopOfferSpec offertetail(String offerId) {
        return (ShopOfferSpec) offerService.getProductetail(offerId);
    }

    @ApiOperation(value = "新建商品", notes = "新建商品")
    @RequestMapping(value = "/mini/shop/offer/add", method = RequestMethod.POST)
    public void offerAdd(HttpServletRequest request, @RequestBody ShopOfferSpec offer) {
        offerService.productAdd(request,offer);
    }

    @ApiOperation(value = "修改商品", notes = "修改商品")
    @RequestMapping(value = "/mini/shop/offer/upd", method = RequestMethod.POST)
    public void offerUpd(@RequestBody ShopOfferSpec offer) {
        offerService.productUpd(offer);
    }

    @ApiOperation(value = "删除商品", notes = "删除商品")
    @RequestMapping(value = "/mini/shop/offer/del/{offerId}", method = RequestMethod.GET)
    public void offerDel(@PathVariable("offerId") String offerId) {
        offerService.productDel(offerId);
    }

    @ApiOperation(value = "上架、下架", notes = "上架、下架")
    @RequestMapping(value = "/mini/shop/offer/updOfferStatus/{status}/{id}", method = RequestMethod.GET)
    public void updOfferStatus(@PathVariable("status") Integer status, @PathVariable("id") String id){
        offerService.productStatusUpd(status, id);
    }

    @ApiOperation(value = "查询商品分类", notes = "根据企业ID查询商品列表")
    @RequestMapping(value = "/mini/shop/offer/category/list/{entId}", method = RequestMethod.GET)
    public List<Category> categoryList(@PathVariable("entId") String entId) {
        return offerService.getSelectCategoryList(entId);
    }

    @ApiOperation(value = "新增商品分类", notes = "新增商品分类")
    @RequestMapping(value = "/mini/shop/offer/category/add", method = RequestMethod.POST)
    public void categoryAdd(HttpServletRequest request,@RequestBody Category category) {
        offerService.categoryAdd(request, category);
    }

    @ApiOperation(value = "修改商品分类", notes = "修改商品分类")
    @RequestMapping(value = "/mini/shop/offer/category/upd", method = RequestMethod.GET)
    public void categoryUpd(String name, String id) {
        offerService.categoryUpd(name, id);
    }

    @ApiOperation(value = "删除商品分类", notes = "删除商品分类")
    @RequestMapping(value = "/mini/shop/offer/category/del/{id}", method = RequestMethod.GET)
    public void categoryDel(@PathVariable("id")String id) {
        offerService.categoryDel(id);
    }
}
