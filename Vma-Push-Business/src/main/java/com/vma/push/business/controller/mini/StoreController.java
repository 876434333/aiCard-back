package com.vma.push.business.controller.mini;

import com.vma.push.business.dao.ShoppingCartMapper;
import com.vma.push.business.dto.req.Page;
import com.vma.push.business.dto.req.store.*;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.store.*;
import com.vma.push.business.entity.ShoppingCart;
import com.vma.push.business.service.IOrderService;
import com.vma.push.business.service.IProductService;
import com.vma.push.business.service.IShopCartService;
import com.vma.push.business.service.IUserInfoService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Create By ChenXiAoBin
 * on 2018/7/24
 */

@RestController
@RequestMapping("/v2.0")
@Api(value = "商城管理API", description = "小程序商城管理", tags = {"StoreController"})
public class StoreController {
    private Logger LOG = Logger.getLogger(this.getClass());
    @Autowired
    private IProductService productService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private IShopCartService shopCartService;

    @Autowired
    private IOrderService orderService;


    @ApiOperation(value = "商城产品列表", notes = "商城产品列表")
    @RequestMapping(value = "/mini/offer/list", method = RequestMethod.POST)
    @ApiImplicitParam(name = "page",value = "分页信息",required = true,dataType = "Page")
    public RspPageMiniStore<RspOfferInfo> offerList(@RequestBody Page page,HttpServletRequest request){
        String eneterpriseId=UserDataUtil.getEnterpriseId(request);
        String staff_id=UserDataUtil.getStaffId(request);
        return productService.offerPage(page,eneterpriseId,staff_id);
    }

    @ApiOperation(value = "产品详情", notes = "产品详情")
    @RequestMapping(value = "/mini/offer/{id}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "id", value = "产品详情参数", paramType = "path", required = true, dataType = "String")
    public RspOffInfo getProductById(@PathVariable String id, HttpServletRequest request) {
        LOG.info("产品详情");
        String staffid = UserDataUtil.getStaffId(request);
        return productService.offerInfo(id,staffid);
    }

    @ApiOperation(value = "添加购物车", notes = "添加购物车")
    @RequestMapping(value = "/mini/shop_cart", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqAddCart",value = "加入购物车",required = true,dataType = "ReqAddCart")
    public void addShopCart(@RequestBody ReqAddCart reqAddCart, HttpServletRequest request){
        LOG.info("获取用户购物车");
        reqAddCart.setUser_id(UserDataUtil.getUserId(request));
        reqAddCart.setStaff_id(UserDataUtil.getStaffId(request));
        //获取同规格的商品数量
        productService.addShopCart(reqAddCart);
    }


    @ApiOperation(value = "移除购物车", notes = "移除购物车")
    @RequestMapping(value = "/mini/shop_cart", method = RequestMethod.DELETE)
    @ApiImplicitParam(name = "ids",value = "加入购物车",required = true,dataType = "List<String>")
    public void removeShopcart(@RequestBody List<String> ids,HttpServletRequest request){
        shopCartService.removeShopCart(ids);
    }

    @ApiOperation(value = "编辑购物车中某一商品", notes = "编辑购物车中某一商品")
    @RequestMapping(value = "/mini/shop_cart", method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqEditCart",value = "加入购物车",required = true,dataType = "ReqEditCart")
    public void editShopcart(@RequestBody ReqEditCart reqEditCart, HttpServletRequest request){
        ShoppingCart shoppingCart=new ShoppingCart();
        shoppingCart.setId(reqEditCart.getId());
        shoppingCart.setNormsId(reqEditCart.getNorms_id());
        shoppingCart.setNum(reqEditCart.getNum());
        shoppingCartMapper.updateByPrimaryKeySelective(shoppingCart);
    }

    @ApiOperation(value = "购物车列表", notes = "购物车列表")
    @RequestMapping(value = "/mini/shop_cart/list", method = RequestMethod.POST)
    @ApiImplicitParam(name = "page",value = "分页信息",required = true,dataType = "Page")
    public RspPageWithDiscount<RspUserCartList> shopCartList(@RequestBody Page page, HttpServletRequest request){
        LOG.info("获取用户购物车");
        String userId=UserDataUtil.getUserId(request);
        String staffId=UserDataUtil.getStaffId(request);
        return userInfoService.userShopCartList(userId,page,staffId);
    }

    @ApiOperation(value = "我的订单列表", notes = "我的订单列表")
    @RequestMapping(value = "/mini/order/list", method = RequestMethod.POST)
    @ApiImplicitParam(name = "page",value = "分页信息",required = true,dataType = "ReqMyOrderPage")
    public RspPage<RspOrder> orderList(@RequestBody ReqMyOrderPage page, HttpServletRequest request){
        LOG.info("我的订单列表");
        String userId=UserDataUtil.getUserId(request);
        String staffId=UserDataUtil.getStaffId(request);
        //String userId="df8ba472-d69f-4084-80f7-a9127b8d8546";
        return orderService.myOrderList(page,userId,staffId);
    }

    @ApiOperation(value = "員工订单列表", notes = "員工订单列表")
    @RequestMapping(value = "/mini/order/staffOrder/{staffId}", method = RequestMethod.POST)
    @ApiImplicitParam(name = "page", value = "分页信息 需要传session_id", required = true, dataType = "Page")
    public RspPage<com.vma.push.business.dto.rsp.store.RspOrder> staffOrder(@RequestBody ReqMyOrderPage page, HttpServletRequest request,@PathVariable("staffId") String staffId) {
        LOG.info("员工订单列表");
        String userid = UserDataUtil.getUserId(request);
        return orderService.staffOrderList(page, userid, staffId);
    }

    @ApiOperation(value = "企业订单列表", notes = "企业订单列表")
    @RequestMapping(value = "/mini/order/enterpriseOrder/{enterpriseId}", method = RequestMethod.POST)
    @ApiImplicitParam(name = "page", value = "分页信息 需要传session_id", required = true, dataType = "Page")
    public RspPage<com.vma.push.business.dto.rsp.store.RspOrder> enterpriseOrder(@RequestBody ReqMyOrderPage page, HttpServletRequest request,@PathVariable("enterpriseId") String enterpriseId) {
        LOG.info("企业订单列表");
        String userid = UserDataUtil.getUserId(request);
        return orderService.enterpriseOrderList(page, userid, enterpriseId);
    }

    /**
     * 修改订单状态（已发货）
     */
    @ApiOperation(value = "修改订单状态（已发货）", notes = "修改订单状态（已发货)")
    @RequestMapping(value = "/mini/order/update_status/{id}", method = RequestMethod.PUT)
    @ApiImplicitParam(name = "id",value = "商品id",required = true,dataType = "String",paramType = "path")
    public void changeStatus(@PathVariable String id,HttpServletRequest request){
        LOG.info("修改订单状态为已发货");
        orderService.updateStatus(id);
    }

    @ApiOperation(value = "推荐产品", notes = "推荐产品")
    @RequestMapping(value = "/mini/recommend/list", method = RequestMethod.POST)
    @ApiImplicitParam(name = "page",value = "分页信息",required = true,dataType = "Page")
    public RspPage<RspRecOffer> recList(@RequestBody Page page, HttpServletRequest request){
        LOG.info("推荐产品");
        String staffId=UserDataUtil.getStaffId(request);
        return productService.recList(staffId,page);

    }

    @ApiOperation(value = "我的订单", notes = "我的订单")
    @RequestMapping(value = "/mini/order/order_summary", method = RequestMethod.GET)
    public RspOrderSummary orderSummary(HttpServletRequest request){
        String userId=UserDataUtil.getUserId(request);
        String staffId=UserDataUtil.getStaffId(request);
        return orderService.orderSummary(userId,staffId);
    }
    @ApiOperation(value = "下单未付款", notes = "下单未付款")
    @RequestMapping(value = "/mini/pay", method = RequestMethod.POST)
    public Map addorder(@RequestBody ReqOrderPay reqOrderPay, HttpServletRequest request) throws Exception {
        reqOrderPay.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
        reqOrderPay.setUser_id(UserDataUtil.getUserId(request));
        reqOrderPay.setStaff_id(UserDataUtil.getStaffId(request));
        reqOrderPay.setDepartment_id(UserDataUtil.getDepartmentId(request));
        return orderService.commitNew(reqOrderPay);
    }
    @ApiOperation(value = "付款", notes = "付款")
    @RequestMapping(value = "/mini/pay/{id}", method = RequestMethod.POST)
    @ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "String", paramType = "path")
    public Map order(@PathVariable("id") String id, HttpServletRequest request) throws Exception {
        String userId = UserDataUtil.getUserId(request);
        return orderService.orderPayNew(id, userId);
    }

    @ApiOperation(value = "取消订单", notes = "取消订单")
    @RequestMapping(value = "/mini/order/cancel/{id}", method = RequestMethod.DELETE)
    @ApiImplicitParam(name = "id",value = "订单id",required = true,dataType = "String",paramType = "path")
    public void orderCancel(@PathVariable String id, HttpServletRequest request){
        LOG.info("取消订单");
        String userId=UserDataUtil.getUserId(request);
        orderService.cancelOrder(id);
    }

    @ApiOperation(value = "订单详情", notes = "订单详情")
    @RequestMapping(value = "/mini/order/{id}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "订单id",required = true,dataType = "String",paramType = "path")
    public RspOrderDetails orderDetail(@PathVariable String id, HttpServletRequest request){
        LOG.info("订单详情");
        String userId=UserDataUtil.getUserId(request);
        return orderService.orderDetails(id);
    }
    @ApiOperation(value = "规格详情", notes = "规格详情")
    @RequestMapping(value = "/mini/norms/{id}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "商品id",required = true,dataType = "String",paramType = "path")
    public RspNorms2 offerNorms(@PathVariable String id,HttpServletRequest request){
        LOG.info("规格");
        return orderService.offerNorms(id);
    }


//    /**
//     * 修改订单状态（已发货）
//     */
//    @ApiOperation(value = "修改订单状态（已发货）", notes = "修改订单状态（已发货)")
//    @RequestMapping(value = "/mini/order/update_status/{id}", method = RequestMethod.GET)
//    @ApiImplicitParam(name = "id",value = "商品id",required = true,dataType = "String",paramType = "path")
//    public void changeStatus(@PathVariable String id,HttpServletRequest request){
//        LOG.info("修改订单状态为已发货");
//        orderService.updateStatus(id);
//    }




    /**
     * 收货地址列表
     * @param
     * @return
     */
    @ApiOperation(value = "收货地址列表", notes = "收货地址列表")
    @RequestMapping(value = "/store/address", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqUserAddress",value = "收货地址列表",dataType = "ReqUserAddress")
    public RspPage<RspUserAddress> findProductBySelect(@RequestBody ReqUserAddress reqUserAddress){
        LOG.info("收获地址列表");
        return productService.getAddress(reqUserAddress);
    }

    /**
     * 删除收货地址
     * @param reqDelAddress
     */
    @ApiOperation(value = "删除收货地址",notes = "删除收货地址）")
    @RequestMapping(value = "/store/address",method = RequestMethod.DELETE)
    @ApiImplicitParam(name = "reqDelAddress",required = true,value = "删除收货地址",dataType = "ReqDelAddress")
    public void DelAddress(@RequestBody ReqDelAddress reqDelAddress){
        LOG.info("删除收货地址");
        productService.DelAddress(reqDelAddress);
    }

    /**
     * 添加收货地址
     * @param
     */
    @ApiOperation(value = "添加收货地址", notes = "添加收货地址")
    @RequestMapping(value = "/store/add/address", method = RequestMethod.POST)
    @ApiImplicitParam(name = "addAddress",value = "添加收货地址",required = true,dataType = "ReqAddAddress")
    public void addProduct(@RequestBody ReqAddAddress addAddress){
        LOG.info("添加收货地址");
       productService.addAddress(addAddress);
    }

    /**
     * 根据id查看收货地址详情
     * @param id  产品id
     * @return
     */
    @ApiOperation(value = "收货地址详情", notes = "收货地址详情")
    @RequestMapping(value = "/store/address/{id}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "收货地址详情",paramType = "path",required = true,dataType = "String")
    public RspDetailAddress getAddressById(@PathVariable String id){
        LOG.info("收货地址详情");
        RspDetailAddress rspDetailAddress = productService.Detail(id);
        return rspDetailAddress;
    }


    /**
     * 修改收货地址
     * @param
     */
    @ApiOperation(value = "修改收货地址",notes = "修改收货地址")
    @RequestMapping(value = "/store/address",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateAddress",required = true,value = "修改收货地址",dataType = "ReqUpdateAddress")
    public void updateProduct(@RequestBody ReqUpdateAddress reqUpdateAddress){
        LOG.info("修改收货地址");
        productService.updateAddress(reqUpdateAddress);
    }


    /**
     * 修改收货地址
     * @param
     */
    @ApiOperation(value = "修改默认收货地址",notes = "修改默认收货地址")
    @RequestMapping(value = "/store/update_default",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateDefault",required = true,value = "修改收货地址",dataType = "ReqUpdateDefault")
    public void updateDefault(@RequestBody ReqUpdateDefault reqUpdateDefault){
        LOG.info("修改默认收货地址");
        productService.updateDefault(reqUpdateDefault);
    }

    @ApiOperation(value = "获取默认收货地址", notes = "获取默认收货地址")
    @RequestMapping(value = "/mini/get_default_address/{id}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "用户id",required = true,dataType = "String",paramType = "path")
    public RspDefaultAddress getDefaul(@PathVariable String id, HttpServletRequest request){
        LOG.info("默认收货地址");
        return productService.getDefaul(id);
    }


    /**
     * 修改订单状态为已收货
     * @param
     */
    @ApiOperation(value = "修改订单状态为已收货",notes = "修改订单状态为以收货")
    @RequestMapping(value = "/store/update_order_status/{id}",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "id",required = true,value = "修改订单状态为已收货",dataType = "String",paramType = "path")
    public void updateOrderStatus(@PathVariable String id){
        LOG.info("修改订单状态为已收货");
        productService.updateOrderStatus(id);
    }

    /**
     * 没有开通商城支付的情况下创建订单
     */
    @ApiOperation(value = "没有开通商城支付的情况下创建订单", notes = "没有开通商城支付的情况下创建订单")
    @RequestMapping(value = "/mini/createOrder", method = RequestMethod.POST)
    public boolean createOrder(@RequestBody ReqOrderPay reqOrderPay, HttpServletRequest request) throws Exception {
        reqOrderPay.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
        reqOrderPay.setUser_id(UserDataUtil.getUserId(request));
        reqOrderPay.setStaff_id(UserDataUtil.getStaffId(request));
        reqOrderPay.setDepartment_id(UserDataUtil.getDepartmentId(request));
        return orderService.createOrder(reqOrderPay);
    }
}

