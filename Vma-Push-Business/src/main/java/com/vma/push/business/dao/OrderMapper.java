package com.vma.push.business.dao;

import com.vma.push.business.dto.req.ReqOrderSelect;
import com.vma.push.business.dto.req.store.ReqOrderStatistic;
import com.vma.push.business.dto.req.store.ReqOrderPage;
import com.vma.push.business.dto.req.store.ReqUserType;
import com.vma.push.business.dto.rsp.RspOrder;
import com.vma.push.business.dto.rsp.RspOrderDetail;
import com.vma.push.business.dto.rsp.RspStaffOrder;
import com.vma.push.business.dto.rsp.RspUserOrder;
import com.vma.push.business.dto.rsp.store.*;
import com.vma.push.business.entity.Order;
import com.vma.push.business.entity.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/5/2 21:31
 */
public interface OrderMapper extends BaseDao<Order,String> {
    List<RspOrder> findOrderBySelect(ReqOrderSelect reqOrderSelect);//条件查询订单

    List<RspOrder> findAllOrder();

    List<RspStaffOrder> orderByStaffid(@Param("staffid") String staffid,@Param("enid") String enid);

    Integer OrderCount(@Param("staffid") String staffid,@Param("enid") String enid);
    List<RspUserOrder> orderByUserid(@Param("userid") String userid);

    RspOrderDetail orderDetail(String id);

    /**
     * 销售数量
     * @param id 商品编号
     * @return
     */
    int saleCount(String id);

    Integer findCount(String userid);//查看未支付订单数

    Order getByNbr(@Param("orderNbr") String orderNbr);

    List<com.vma.push.business.dto.rsp.store.RspOrder> userOrder(@Param("userId") String userId,@Param("type") String type, @Param("staffId") String staffId);
    List<com.vma.push.business.dto.rsp.store.RspOrder> staffOrder(@Param("userId") String userId,@Param("type") String type, @Param("staffId") String staffId);
    List<com.vma.push.business.dto.rsp.store.RspOrder> enterpriseOrder(@Param("userId") String userId,@Param("type") String type, @Param("enterprise_id") String enterprise_id);

    List<com.vma.push.business.dto.rsp.store.RspOrderDetail2> orderDetailnew(String orderid);
    Integer orderCount(ReqUserType reqUserType);

    void cancelOrder(@Param("id") String id);

    RspOrderDetails orderDetails(String id);

    List<RspOrderInfo> orderInfo(String id);

    List<RspNorms> offerNorms(String id);

    RspPriceRange priceRange(String id);

    List<RspOrderStatistic> orderStatisticList(ReqOrderStatistic reqOrderStatistic);//员工订单统计

    List<RspOrderList> orderList(ReqOrderPage reqOrderPage);

    List<RspOrderListDetail> findDetailById(String orderId);

    Integer findOfferLevel( @Param("norm_id") String norm_id);//查询商品库存

    void updateOfferLevel(@Param("num") int num, @Param("norm_id") String norm_id);//扣除库存量

    List<RspNormAndNum> findNormAndNumByOrderId(String id);

    String findOfferNormsName(String norms_id);//版本名称

    void updateOfferNormStatus(String norms_id);
}
