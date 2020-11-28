package com.vma.push.business.service;

import com.vma.push.business.dto.req.Page;
import com.vma.push.business.dto.req.ReqAddOrder;
import com.vma.push.business.dto.req.ReqOrderSelect;
import com.vma.push.business.dto.req.store.ReqMyOrderPage;
import com.vma.push.business.dto.req.store.ReqOrderPay;
import com.vma.push.business.dto.req.store.ReqOrderStatistic;
import com.vma.push.business.dto.req.store.ReqOrderPage;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.rsp.RspOrder;
import com.vma.push.business.dto.rsp.RspOrderDetail;
import com.vma.push.business.dto.rsp.store.*;
import com.vma.push.business.entity.Order;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenXiaoBin
 * 2018/5/2 21:27
 */
public interface IOrderService extends IBaseService<Order,String> {
    RspPage<RspOrder> findOrderBySelect(ReqOrderSelect reqOrderSelect); //条件查询订单

    List<RspOrder> findAllOrder();

    /**
     * 获取销售人员订单
     * @param page
     * @param staffid
     * @return
     */
    RspPage<RspStaffOrder> orderByStaffid(Page page,String staffid,String endi);

    Integer OrderCount(String id,String enid);
    /**
     * 获取客户订单列表
     * @param page
     * @param userid
     * @return
     */
    RspPage<RspUserOrder> orderByUserid(Page page, String userid,String enterpriseId);
    /**
     * 通过订单id获取订单详情
     * @param id
     * @return
     */
    RspOrderDetail orderDetail(String id,String enterpriseId);

    RspOrderDetail orderDetail(String id,String orderDetailId,String enterpriseId);

    public Map commit(ReqAddOrder reqAddOrder) throws Exception;

    public Map commitNew(ReqOrderPay reqOrderPay) throws Exception;

    public boolean callbackPay(HttpServletRequest request);

    Map orderPay(String id, String userId) throws Exception;//支付
    Map orderPayNew(String id, String userId) throws Exception;//支付
    RspPage<com.vma.push.business.dto.rsp.store.RspOrder> myOrderList(ReqMyOrderPage page,String userId,String staffId);
    RspPage<com.vma.push.business.dto.rsp.store.RspOrder> staffOrderList(ReqMyOrderPage page,String userId,String staffId);
    RspPage<com.vma.push.business.dto.rsp.store.RspOrder> enterpriseOrderList(ReqMyOrderPage page,String userId,String enterprise_id);

    RspOrderSummary orderSummary(String userId,String staffId);

    void cancelOrder( String id);

    RspOrderDetails orderDetails(String id);

    RspNorms2 offerNorms(String id);

    RspPage<RspOrderStatistic> orderStatisticList(ReqOrderStatistic reqOrderStatistic);//员工订单统计

    RspPage<RspOrderList> orderListPage(ReqOrderPage reqOrderPage);

    void updateStatus(String id);//修改订单状态为已发货

    //创建未开通商城支付的订单
    boolean createOrder(ReqOrderPay reqOrderPay) throws Exception;
}
