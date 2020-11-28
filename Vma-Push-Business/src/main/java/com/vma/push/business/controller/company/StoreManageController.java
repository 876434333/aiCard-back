package com.vma.push.business.controller.company;

        import com.vma.push.business.dto.req.store.*;
        import com.vma.push.business.dto.req.store.*;
        import com.vma.push.business.dto.req.store.ReqAddOffer;
        import com.vma.push.business.dto.req.store.ReqEditOffer;
        import com.vma.push.business.dto.req.store.ReqOfferList;
        import com.vma.push.business.dto.req.store.ReqOrderPage;
        import com.vma.push.business.dto.rsp.RspPage;
        import com.vma.push.business.dto.rsp.store.*;
        import com.vma.push.business.service.IOrderService;
        import com.vma.push.business.service.IOrderService;
        import com.vma.push.business.service.IProductService;
        import com.vma.push.business.service.IStaffInfoService;
        import com.vma.push.business.util.UserDataUtil;
        import io.swagger.annotations.Api;
        import io.swagger.annotations.ApiImplicitParam;
        import io.swagger.annotations.ApiOperation;
        import org.apache.log4j.Logger;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;

        import javax.servlet.http.HttpServletRequest;
        import java.math.BigDecimal;
        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by huxiangqiang on 2018/7/25.
 */
@RestController(value = "StoreManageController")
@RequestMapping("/V1.0")
@Api(value = "企业后台", description = "商城管理", tags = {"StoreManageController"})
public class StoreManageController {
    private Logger LOG = Logger.getLogger(this.getClass());
    @Autowired
    private IProductService productService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IStaffInfoService staffInfoService;


    @ApiOperation(value = "商城产品列表", notes = "商城产品列表")
    @RequestMapping(value = "/company/store/offer/list", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqOfferList",value = "查询条件",required = true,dataType = "ReqOfferList")
    public RspPage<RspOfferList> offerlist(@RequestBody ReqOfferList reqOfferList, HttpServletRequest request){
        String enterpriseId= UserDataUtil.getCustomId(request);// 11
        reqOfferList.setEnterprise_id(enterpriseId);
        return productService.offerList(reqOfferList);

    }
    @ApiOperation(value = "添加产品", notes = "添加产品")
    @RequestMapping(value = "/company/store/offer", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqAddOffer",value = "查询条件",required = true,dataType = "ReqAddOffer")
    public void addoffer(@RequestBody ReqAddOffer reqAddOffer,HttpServletRequest request){
        String enpterpriseId=UserDataUtil.getCustomId(request);
        reqAddOffer.setEnterprise_id(enpterpriseId);
        productService.offerAdd(reqAddOffer);
    }

    @ApiOperation(value = "产品详情", notes = "产品详情")
    @RequestMapping(value = "/company/store/offer/{id}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "产品id",required = true,dataType = "String",paramType = "path")
    public ReqEditOffer offerDetail(@PathVariable  String id){
        return productService.offerDetail(id);
    }

    @ApiOperation(value = "编辑产品", notes = "编辑产品")
    @RequestMapping(value = "/company/store/offer", method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqEditOffer",value = "查询条件",required = true,dataType = "ReqEditOffer")
    public void editoffer(@RequestBody ReqEditOffer reqEditOffer,HttpServletRequest request){
        reqEditOffer.setEnterpriseId(UserDataUtil.getEnterpriseId(request));
        productService.offerEdit(reqEditOffer);
    }

    @ApiOperation(value = "批量上架", notes = "批量上架")
    @RequestMapping(value = "/company/store/offer/up", method = RequestMethod.PUT)
    @ApiImplicitParam(name = "ids",value = "产品id集合",required = true,dataType = "List<String>")
    public void upoffer(@RequestBody List<String> ids, HttpServletRequest request){
        productService.changeStatus(ids,1);
    }

    @ApiOperation(value = "批量下架", notes = "批量下架")
    @RequestMapping(value = "/company/store/offer/down", method = RequestMethod.PUT)
    @ApiImplicitParam(name = "ids",value = "产品id集合",required = true,dataType = "List<String>")
    public void downoffer(@RequestBody List<String> ids, HttpServletRequest request){
        productService.changeStatus(ids,0);
    }

    @ApiOperation(value = "删除商品", notes = "删除商品")
    @RequestMapping(value = "/company/store/offer/{id}", method = RequestMethod.DELETE)
    @ApiImplicitParam(name = "id",value = "产品id",required = true,dataType = "String",paramType = "path")
    public void offerDel(@PathVariable  String id){
        List<String> ids=new ArrayList<>();
        ids.add(id);
        productService.changeStatus(ids,3);
    }



    @ApiOperation(value = "订单列表", notes = "订单列表")
    @RequestMapping(value = "/company/store/order/list", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqOrderPage",value = "查询条件",required = true,dataType = "ReqOrderPage")
    public RspPage<RspOrderList> orderList(@RequestBody ReqOrderPage reqOrderPage, HttpServletRequest request){
        LOG.info("订单列表");
        String enterpriseId=UserDataUtil.getCustomId(request);
        reqOrderPage.setEnterprise_id(enterpriseId);
        return orderService.orderListPage(reqOrderPage);
    }
    @ApiOperation(value = "员工折扣列表", notes = "员工折扣列表")
    @RequestMapping(value = "/company/discount/list", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqDiscount",value = "查询条件",required = true,dataType = "ReqDiscount")
    public RspDiscountList<RspDiscount> discountList(@RequestBody ReqDiscount reqDiscount, HttpServletRequest request){
        reqDiscount.setEnterprise_id(UserDataUtil.getCustomId(request));
        return staffInfoService.discountList(reqDiscount);
    }
    @ApiOperation(value = "设置企业默认折扣", notes = "设置企业默认折扣")
    @RequestMapping(value = "/company/discount/enterprise", method = RequestMethod.PUT)
    public void enterpriseDiscount(@RequestParam("count") BigDecimal count, HttpServletRequest request){
        String enterpriseId=UserDataUtil.getCustomId(request);
        staffInfoService.setEnterDiscount(enterpriseId,count);
    }
    @ApiOperation(value = "设置个人折扣", notes = "设置个人折扣")
    @RequestMapping(value = "/company/discount/staff", method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqStaffDiscount",value = "查询条件",required = true,dataType = "ReqStaffDiscount")
    public void staffDiscount(@RequestBody ReqStaffDiscount reqStaffDiscount, HttpServletRequest request){
        String enterpriseId=UserDataUtil.getCustomId(request);
        reqStaffDiscount.setEnterprise_id(enterpriseId);
        staffInfoService.setStaffDiscount(reqStaffDiscount);
    }


    /**
     * 员工订单统计
     * @param request
     * @param reqOrderStatistic
     * @return
     */
    @ApiOperation(value = "员工订单统计", notes = "员工订单统计")
    @RequestMapping(value = "/company/store/staff_order/list", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqOrderStatistic",value = "查询条件",required = true,dataType = "ReqOrderStatistic")
    public RspPage<RspOrderStatistic> orderStatisticList(HttpServletRequest request, @RequestBody ReqOrderStatistic reqOrderStatistic){
        LOG.info("员工订单统计列表");
       reqOrderStatistic.setEnterprise_id(UserDataUtil.getCustomId(request));
        //reqOrderStatistic.setEnterprise_id("56c369ec-0c96-40a8-8d9e-a69727f19180");
      return orderService.orderStatisticList(reqOrderStatistic);

    }

    /**
     * 修改订单状态（已发货）
     */
    @ApiOperation(value = "修改订单状态（已发货）", notes = "修改订单状态（已发货)")
    @RequestMapping(value = "/company/store/update_status/{id}", method = RequestMethod.PUT)
    @ApiImplicitParam(name = "id",value = "商品id",required = true,dataType = "String",paramType = "path")
    public void changeStatus(@PathVariable String id,HttpServletRequest request){
        LOG.info("修改订单状态为已发货");
        orderService.updateStatus(id);
    }
}

