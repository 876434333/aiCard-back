package com.vma.push.business.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vma.push.business.common.ActionCode;
import com.vma.push.business.common.Constants;
import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dao.*;
import com.vma.push.business.dto.req.ReqAddOrder;
import com.vma.push.business.dto.req.ReqOrderSelect;
import com.vma.push.business.dto.req.store.*;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.rsp.RspOrder;
import com.vma.push.business.dto.rsp.RspOrderDetail;
import com.vma.push.business.dto.rsp.store.*;
import com.vma.push.business.entity.*;
import com.vma.push.business.service.IOrderService;
import com.vma.push.business.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by huxiangqiang on 2018/5/4.
 */
@Service
public class OrderServiceImpl implements IOrderService {
	private Logger LOG = Logger.getLogger(this.getClass());

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderDetailMapper orderDetailMapper;
	@Autowired
	private ClickActionLogMapper clickActionLogMapper;

	@Autowired
	private StaffMapper staffMapper;

	@Autowired
	private EnterpriseMapper enterpriseMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private DeployMapper deployMapper;

	@Autowired
	private OfferSpecMapper offerSpecMapper;

	@Autowired
	private ShoppingCartMapper shoppingCartMapper;

	@Autowired
	private SysConfigMapper sysConfigMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		return orderMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Order record) {
		return orderMapper.insert(record);
	}

	@Override
	public int insertSelective(Order record) {
		return orderMapper.insertSelective(record);
	}

	@Override
	public Order selectByPrimaryKey(String id) {
		return orderMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Order record) {
		return orderMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public RspOrderDetail orderDetail(String id,String orderDetailId, String enterpriseId) {

		RspOrderDetail orderDetail = orderMapper.orderDetail(id); // 此处得到数量为订单数量，需从商品详情列表得到数量

		orderDetail.setOffer_num(orderDetailMapper.selectByPrimaryKey(orderDetailId).getOfferNum().toString());

		orderDetail.setSale_num(orderMapper.saleCount(orderDetail.getOffer_id()));//获取购买量
		Long num = clickActionLogMapper.findNum(enterpriseId, ActionCode.ACTION_CODE_1016, orderDetail.getOffer_id());//获取浏览量
		orderDetail.setView_num(num.intValue());//添加浏览量
		return orderDetail;
	}
	@Override
	public RspOrderDetail orderDetail(String id, String enterpriseId) {

		RspOrderDetail orderDetail = orderMapper.orderDetail(id);
		orderDetail.setSale_num(orderMapper.saleCount(orderDetail.getOffer_id()));//获取购买量
		Long num = clickActionLogMapper.findNum(enterpriseId, ActionCode.ACTION_CODE_1016, orderDetail.getOffer_id());//获取浏览量
		orderDetail.setView_num(num.intValue());//添加浏览量
		return orderDetail;
	}

	@Override
	public Map commit(ReqAddOrder reqAddOrder) throws Exception {
		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);

		Order order = reqAddOrder.toOrder();
		String staffId = reqAddOrder.getStaff_id();
		Staff staff = staffMapper.selectByPrimaryKey(staffId);
		//Enterprise enterprise =enterpriseMapper.selectByPrimaryKey(staff.getEnterpriseId());
		Deploy deploy = deployMapper.selectAll(staff.getEnterpriseId());
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(reqAddOrder.getUser_id());
		Map map = WeiXinHelper.payInfo(order.getOrderNbr(), order.getOfferId(), order.getTotalPrice().movePointRight(2).toString(), sysConfig.getWxPayCallbackUrl(), deploy.getAppId(), deploy.getMchId(), "JSAPI", deploy.getPayKey(), userInfo.getOpenId());
		//提交订单未付款
		order.setDepartmentId(staff.getDepartmentId());
		order.setEnterpriseId(staff.getEnterpriseId());
		orderMapper.insertSelective(order);
		LOG.info("=================================下单=============旧");
		return WeiXinHelper.wxPay((HashMap<String, String>) map, deploy.getPayKey());
	}

	@Override
	public Map commitNew(ReqOrderPay reqOrderPay) throws Exception {
		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);
		Order order = new Order();
		order.setId(UuidUtil.getRandomUuid());//订单id
		order.setOrderNbr(DateFormatUtils.formate(new Date(), DateFormatUtils.TIGHT_PATTERN_DATETIME));//订单编号
		order.setLinkMan(reqOrderPay.getLink_man());//;联系人
		order.setLinkPhone(reqOrderPay.getLink_phone());//联系人电话
		order.setAddress(reqOrderPay.getAddress());//联系地址
		order.setCreateTime(new Date());//下单时间
		order.setStatus(0);//状态  未付款
		order.setUserId(reqOrderPay.getUser_id());//客户id
		order.setStaffId(reqOrderPay.getStaff_id());//员工id
		order.setExpressFee(reqOrderPay.getExpress_fee());//快递费
		order.setTotalPrice(reqOrderPay.getTotal_price());//商品总费用
		order.setTotalPriceDiscount(reqOrderPay.getTotal_price_discount());//实付金额
		order.setEnterpriseId(reqOrderPay.getEnterprise_id());
		order.setDepartmentId(reqOrderPay.getDepartment_id());
		Deploy deploy = deployMapper.selectAll(reqOrderPay.getEnterprise_id());//获取密钥
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(reqOrderPay.getUser_id());
		Map map = WeiXinHelper.payInfo(order.getOrderNbr(), order.getId(), order.getTotalPriceDiscount().movePointRight(2).toString(), sysConfig.getWxPayCallbackUrl(), deploy.getAppId(), deploy.getMchId(), "JSAPI", deploy.getPayKey(), userInfo.getOpenId());
		Integer offerLevel = 0;
		for (ReqOrder2 detail : reqOrderPay.getOrder_detail()) {
			offerLevel = orderMapper.findOfferLevel(detail.getNorm_id());//查询商品的库存
			OfferSpec offerSpec = offerSpecMapper.selectByPrimaryKey(detail.getOffer_id());//查询商品名称
			if (offerLevel < detail.getNum()) {//如果库存小于等于购买数量
				throw new BusinessException(ErrCode.NO_OFFERLEVEL, offerSpec.getOfferName() + "库存不足");
			}
		}
		//订单佣金
		BigDecimal extractValue = BigDecimal.ZERO;
		for (ReqOrder2 detail : reqOrderPay.getOrder_detail()) {
			OfferSpec offerSpec = offerSpecMapper.selectByPrimaryKey(detail.getOffer_id());
			if (offerSpec.getExtractType() == 1) {
				offerSpec.getExtractValue();//单个商品的佣金
				BigDecimal num = new BigDecimal(detail.getNum());//数量
				extractValue = extractValue.add(offerSpec.getExtractValue().multiply(num));

			} else {
				detail.getOffer_price_discount();//售价
				offerSpec.getExtractPer();//比例
				BigDecimal num = new BigDecimal(detail.getNum());//数量
				extractValue = extractValue.add(offerSpec.getExtractPer().multiply(detail.getOffer_price_discount()).multiply(num));
				extractValue = extractValue.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
			}
		}
		order.setExtractValue(extractValue);
		if (orderMapper.insertSelective(order) > 0) {//生成订单数
			for (ReqOrder2 detail : reqOrderPay.getOrder_detail()) {
				OrderDetail orderDetail = new OrderDetail();
				// orderMapper.updateOfferLevel(offerLevel-detail.getNum(),detail.getNorm_id());//扣除库存量
				orderDetail.setId(UuidUtil.getRandomUuid());//订单明细id
				orderDetail.setOrderId(order.getId());//订单id
				orderDetail.setOfferId(detail.getOffer_id());//商品id
				orderDetail.setNormsId(detail.getNorm_id());//规格id
				orderDetail.setOfferNum(detail.getNum());//购买数量
				orderDetail.setOfferPrice(detail.getOffer_price());//单价
				orderDetail.setOfferPriceDiscount(detail.getOffer_price_discount());//折扣价
				orderDetail.setTotalPrice(detail.getOffer_price().multiply(BigDecimal.valueOf(detail.getNum())));//总价
				orderDetail.setTotalPriceDiscount(detail.getOffer_price_discount().multiply(BigDecimal.valueOf(detail.getNum()))); //打完折之后的总价
				orderDetailMapper.insertSelective(orderDetail);
				ShoppingCart shoppingCart = shoppingCartMapper.getcount(detail.getOffer_id(), detail.getNorm_id(), reqOrderPay.getUser_id());//根据规格id和商品id查看购物车是否有东西
				if (shoppingCart != null) {
					shoppingCartMapper.deleteByPrimaryKey(shoppingCart.getId());//有的话就直接删除
				}
			}
		}
//        if (orderMapper.insertSelective(order)>0){//生成订单数据
//            for (ReqOrder2 detail:reqOrderPay.getOrder_detail()){
//                Integer offerLevel = orderMapper.findOfferLevel(detail.getOffer_id());
//                if (offerLevel >= detail.getNum()){
//                    orderMapper.updateOfferLevel(offerLevel-detail.getNum(),detail.getOffer_id());//扣除库存量
//                    OrderDetail orderDetail=new OrderDetail();
//                    orderDetail.setId(UuidUtil.getRandomUuid());//订单明细id
//                    orderDetail.setOrderId(order.getId());//订单id
//                    orderDetail.setOfferId(detail.getOffer_id());//商品id
//                    orderDetail.setNormsId(detail.getNorm_id());//规格id
//                    orderDetail.setOfferNum(detail.getNum());//购买数量
//                    orderDetail.setOfferPrice(detail.getOffer_price());//单价
//                    orderDetail.setOfferPriceDiscount(detail.getOffer_price_discount());//折扣价
//                    orderDetailMapper.insertSelective(orderDetail);
//                }else{
//                    throw new BusinessException(ErrCode. NO_OFFERLEVEL,"库存不足");
//                }
//
//            }
//        }

		LOG.info("=================================下单=============新");
		System.out.println(map);
		return WeiXinHelper.wxPay((HashMap<String, String>) map, deploy.getPayKey());
	}

	/**
	 * 订单支付
	 *
	 * @param id
	 * @param userId
	 * @return
	 */
	@Override
	@Transactional
	public Map orderPay(String id, String userId) throws Exception {
		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);

		Order order = orderMapper.selectByPrimaryKey(id);
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
		//Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(order.getEnterpriseId());
		Deploy deploy = deployMapper.selectAll(order.getEnterpriseId());
		Map map = WeiXinHelper.payInfo(order.getOrderNbr(), order.getOfferId(), order.getTotalPrice().movePointRight(2).toString(), sysConfig.getWxPayCallbackUrl(), deploy.getAppId(), deploy.getMchId(), "JSAPI", deploy.getPayKey(), userInfo.getOpenId());
		System.out.println("=================================支付==============旧");
		return WeiXinHelper.wxPay((HashMap<String, String>) map, deploy.getPayKey());
	}

	@Override
	public Map orderPayNew(String id, String userId) throws Exception {
		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);
		List<RspNormAndNum> list = orderMapper.findNormAndNumByOrderId(id);//根据订单id查询对应的规格id以及数量
		for (RspNormAndNum normAndNum : list) {
			Integer offerLevel = orderMapper.findOfferLevel(normAndNum.getNorms_id());//根据规格id查询库存
			if (offerLevel < normAndNum.getOffer_num()) {//如果库存小于购买数量
				OfferSpec offerSpec = offerSpecMapper.selectByPrimaryKey(normAndNum.getOffer_id());//查询商品名称
				String name = orderMapper.findOfferNormsName(normAndNum.getNorms_id());//商号品的型名字
				throw new BusinessException(ErrCode.NO_OFFERLEVEL, offerSpec.getOfferName() + name + "库存不足");
			}

		}
		Order order = orderMapper.selectByPrimaryKey(id);
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
		//Enterprise enterprise = enterpriseMapper.selectByPrimaryKey(order.getEnterpriseId());
		Deploy deploy = deployMapper.selectAll(order.getEnterpriseId());
		Map map = WeiXinHelper.payInfo(order.getOrderNbr(), order.getId(), order.getTotalPriceDiscount().movePointRight(2).toString(), sysConfig.getWxPayCallbackUrl(), deploy.getAppId(), deploy.getMchId(), "JSAPI", deploy.getPayKey(), userInfo.getOpenId());
		System.out.println("=================================支付==============新");
		System.out.println(map);
		return WeiXinHelper.wxPay((HashMap<String, String>) map, deploy.getPayKey());
	}

	@Override
	public boolean callbackPay(HttpServletRequest request) {

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			LOG.info("wxPayListener===========" + sb.toString());
			PayResult payResult = XMLUtils.getPayResult(sb.toString());
			String olNbr = payResult.getOutTradeNo();
			if ("SUCCESS".equals(payResult.getResultCode())) {
				//此处解析阿里回调接口中的参数，全部组装成map,丢给各子业务模块进行独立处理。
				Map<String, String> map = new HashMap<String, String>();
				map.put("outTradeNo", payResult.getOutTradeNo());

				Order order = orderMapper.getByNbr(payResult.getOutTradeNo());

				if (order == null) {
					//System.out.println("in AliBasePayService.callbackPay, payOrder={} find null payOrder. ");
					return false;
				}

				// 如果该订单已经支付成功了，则无需再次处理。(内部逻辑由各自业务自行处理)

				if (order.getStatus().equals(1)) {
					return true;
				} else {
					order.setStatus(1);
					orderMapper.updateByPrimaryKeySelective(order);
				}

				List<RspNormAndNum> list = orderMapper.findNormAndNumByOrderId(order.getId());//根据订单id查询对应的规格id以及数量以及商品ID
				for (RspNormAndNum normAndNum : list) {
					Integer offerLevel = orderMapper.findOfferLevel(normAndNum.getNorms_id());//根据规格id查询库存
//					orderMapper.updateOfferLevel(offerLevel - normAndNum.getOffer_num(), normAndNum.getNorms_id());
					orderMapper.updateOfferLevel(offerLevel - normAndNum.getOffer_num(), normAndNum.getNorms_id());//修改库存
					OfferSpec offerSpec = offerSpecMapper.selectByPrimaryKey(normAndNum.getOffer_id());//根据商品id查询销量添加销量
					if (offerSpec.getOfferSale() == null) {
						offerSpec.setOfferSale(0);
					}
					offerSpec.setOfferSale(normAndNum.getOffer_num() + offerSpec.getOfferSale());//添加销量
					offerSpec.setId(normAndNum.getOffer_id());
					if (offerLevel - normAndNum.getOffer_num() == 0) {//如果库存为0设置状态为已售罄
						orderMapper.updateOfferNormStatus(normAndNum.getNorms_id());
						// 设置订单表的状态为已售罄 --A lql 20180919000004
						offerSpec.setStatus(2);
					}
					offerSpecMapper.updateByPrimaryKeySelective(offerSpec);
				}
			} else {
				//记录LOG
				LOG.error("in WeixinCallback failure. sb=" + sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}


	@Override
	public int updateByPrimaryKey(Order record) {
		return orderMapper.updateByPrimaryKey(record);
	}


	/**
	 * 条件查询订单
	 *
	 * @param reqOrderSelect
	 * @return
	 */
	@Override
	public RspPage<RspOrder> findOrderBySelect(ReqOrderSelect reqOrderSelect) {
		RspPage<RspOrder> rspPage = new RspPage<>();
		Page page = PageHelper.startPage(reqOrderSelect.getPage_num(), reqOrderSelect.getPage_size(), true);
		List<RspOrder> orders = orderMapper.findOrderBySelect(reqOrderSelect);
		rspPage.setData_list(orders);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		return rspPage;
	}

	@Override
	public List<RspOrder> findAllOrder() {
		List<RspOrder> list = orderMapper.findAllOrder();
		return list;
	}

	@Override
	public RspPage<RspStaffOrder> orderByStaffid(com.vma.push.business.dto.req.Page pages, String staffid, String enid) {
		RspPage<RspStaffOrder> rspPage = new RspPage<RspStaffOrder>();
		Page page = PageHelper.startPage(pages.getPage_num(), pages.getPage_size(), true);
		List<RspStaffOrder> rspStaffOrders = orderMapper.orderByStaffid(staffid, enid);
		rspPage.setData_list(rspStaffOrders);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		return rspPage;

	}

	@Override
	public Integer OrderCount(String id, String enid) {
		return orderMapper.OrderCount(id, enid);
	}

	/**
	 * 订单列表
	 *
	 * @param pages
	 * @param userid
	 * @return
	 */
	@Override
	public RspPage<RspUserOrder> orderByUserid(com.vma.push.business.dto.req.Page pages, String userid, String enterpriseId) {
		RspPage<RspUserOrder> rspPage = new RspPage<RspUserOrder>();
		Page page = PageHelper.startPage(pages.getPage_num(), pages.getPage_size(), true);
		List<RspUserOrder> rspStaffOrders = orderMapper.orderByUserid(userid);
		for (RspUserOrder rspUserOrder : rspStaffOrders) {
			rspUserOrder.setSale_num(orderMapper.saleCount(rspUserOrder.getOffer_id()));//获取订单产品的
			Long num = clickActionLogMapper.findNum(enterpriseId, ActionCode.ACTION_CODE_1016, rspUserOrder.getOffer_id());//获取浏览量
			rspUserOrder.setView_num(num.intValue());//添加浏览量
		}
		rspPage.setData_list(rspStaffOrders);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		return rspPage;
	}

	@Override
	public RspPage<com.vma.push.business.dto.rsp.store.RspOrder> myOrderList(ReqMyOrderPage pages, String userId, String staffid) {
		RspPage<com.vma.push.business.dto.rsp.store.RspOrder> rspPage = new RspPage<com.vma.push.business.dto.rsp.store.RspOrder>();
		Page page = PageHelper.startPage(pages.getPage_num(), pages.getPage_size(), true);
		List<com.vma.push.business.dto.rsp.store.RspOrder> lists = orderMapper.userOrder(userId, pages.getType(),staffid);
		for (com.vma.push.business.dto.rsp.store.RspOrder list : lists) {
			list.setOrderDetails(orderMapper.orderDetailnew(list.getId()));
			Date now = new Date();
			long num = now.getTime() - list.getCreate_time().getTime();//判断下订单的时间是否超过24小时
			double result = num * 1.0 / (1000 * 60 * 60);
			if (result > 24 && list.getStatus().equals("0")) {
				list.setStatus("3");
				Order order = new Order();
				order.setId(list.getId());
				order.setStatus(3);
				orderMapper.updateByPrimaryKeySelective(order);
			}
		}
		rspPage.setData_list(lists);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		return rspPage;
	}

    @Override
    public RspPage<com.vma.push.business.dto.rsp.store.RspOrder> staffOrderList(ReqMyOrderPage pages, String userId, String staffid) {
        RspPage<com.vma.push.business.dto.rsp.store.RspOrder> rspPage = new RspPage<com.vma.push.business.dto.rsp.store.RspOrder>();
        Page page = PageHelper.startPage(pages.getPage_num(), pages.getPage_size(), true);
        List<com.vma.push.business.dto.rsp.store.RspOrder> lists = orderMapper.staffOrder(userId, pages.getType(),staffid);
        for (com.vma.push.business.dto.rsp.store.RspOrder list : lists) {
            list.setOrderDetails(orderMapper.orderDetailnew(list.getId()));
            Date now = new Date();
            long num = now.getTime() - list.getCreate_time().getTime();//判断下订单的时间是否超过24小时
            double result = num * 1.0 / (1000 * 60 * 60);
            if (result > 24 && list.getStatus().equals("0")) {
                list.setStatus("3");
                Order order = new Order();
                order.setId(list.getId());
                order.setStatus(3);
                orderMapper.updateByPrimaryKeySelective(order);
            }
        }
        rspPage.setData_list(lists);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return rspPage;
    }

    @Override
    public RspPage<com.vma.push.business.dto.rsp.store.RspOrder> enterpriseOrderList(ReqMyOrderPage pages, String userId, String enterpriseId) {
        RspPage<com.vma.push.business.dto.rsp.store.RspOrder> rspPage = new RspPage<com.vma.push.business.dto.rsp.store.RspOrder>();
        Page page = PageHelper.startPage(pages.getPage_num(), pages.getPage_size(), true);
        List<com.vma.push.business.dto.rsp.store.RspOrder> lists = orderMapper.enterpriseOrder(userId, pages.getType(),enterpriseId);
        for (com.vma.push.business.dto.rsp.store.RspOrder list : lists) {
            list.setOrderDetails(orderMapper.orderDetailnew(list.getId()));
            Date now = new Date();
            long num = now.getTime() - list.getCreate_time().getTime();//判断下订单的时间是否超过24小时
            double result = num * 1.0 / (1000 * 60 * 60);
            if (result > 24 && list.getStatus().equals("0")) {
                list.setStatus("3");
                Order order = new Order();
                order.setId(list.getId());
                order.setStatus(3);
                orderMapper.updateByPrimaryKeySelective(order);
            }
        }
        rspPage.setData_list(lists);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return rspPage;
    }

    @Override
	public RspOrderSummary orderSummary(String userId,String staffId) {
		RspOrderSummary rspOrderSummary = new RspOrderSummary();
		//"类型  全部  待支付  待发货  待收货  已完成"
		//0未支付 1已支付 2用户取消 3超时取消 4未发货 5已发货 6完成
		ReqUserType reqUserType = new ReqUserType();
		reqUserType.setUser_id(userId);
        reqUserType.setStaff_id(staffId);
		reqUserType.setType(0);
		rspOrderSummary.setUnpay(orderMapper.orderCount(reqUserType));
		reqUserType.setType(4);
		rspOrderSummary.setUnsend(orderMapper.orderCount(reqUserType));
		reqUserType.setType(5);
		rspOrderSummary.setUnreceive(orderMapper.orderCount(reqUserType));
		reqUserType.setType(6);
		rspOrderSummary.setDone(orderMapper.orderCount(reqUserType));
		return rspOrderSummary;
	}

	@Override
	public void cancelOrder(String id) {
		orderMapper.cancelOrder(id);
	}

	@Override
	public RspOrderDetails orderDetails(String id) {

		RspOrderDetails orderDetails = orderMapper.orderDetails(id);
		if (orderDetails != null) {
			orderDetails.setOrder_list(orderMapper.orderInfo(id));
		}
		return orderDetails;
	}

	@Override
	public RspNorms2 offerNorms(String id) {
		RspNorms2 norm = new RspNorms2();
		norm.setNorms(orderMapper.offerNorms(id));
		RspPriceRange priceRange = orderMapper.priceRange(id);
		norm.setMax_price(priceRange.getMax_price());
		norm.setMin_price(priceRange.getMin_price());
		return norm;
	}

	@Override
	public RspPage<RspOrderList> orderListPage(ReqOrderPage reqOrderPage) {
		RspPage<RspOrderList> rspPage = new RspPage<>();
		Page page = PageHelper.startPage(reqOrderPage.getPage_num(), reqOrderPage.getPage_size(), true);
		List<RspOrderList> lists = orderMapper.orderList(reqOrderPage);
		for (RspOrderList list : lists) {//获取订单明细
			String orderId = list.getId();
			list.setDetails(orderMapper.findDetailById(orderId));

		}
		rspPage.setData_list(lists);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		return rspPage;
	}

	/**
	 * 修改订单状态为已发货
	 *
	 * @param id
	 */
	@Override
	public void updateStatus(String id) {
		Order order = new Order();
		order.setStatus(5);
		order.setId(id);
		orderMapper.updateByPrimaryKeySelective(order);
	}

	/**
	 * 员工订单统计
	 *
	 * @param reqOrderStatistic
	 * @return
	 */
	@Override
	public RspPage<RspOrderStatistic> orderStatisticList(ReqOrderStatistic reqOrderStatistic) {
		//RspPage<RspOrder> rspPage = new RspPage<>();
//        Page page = PageHelper.startPage(reqOrderSelect.getPage_num(),reqOrderSelect.getPage_size(),true);
//        List<RspOrder> orders = orderMapper.findOrderBySelect(reqOrderSelect);
//        rspPage.setData_list(orders);
//        rspPage.setTotal_num(page.getTotal());
//        rspPage.setPage_num(page.getPageNum());
//        rspPage.setPage_size(page.getPageSize());
//        return rspPage;

		RspPage<RspOrderStatistic> rspPage = new RspPage<>();
		Page page = PageHelper.startPage(reqOrderStatistic.getPage_num(), reqOrderStatistic.getPage_size(), true);
		List<RspOrderStatistic> list = orderMapper.orderStatisticList(reqOrderStatistic);
		rspPage.setData_list(list);
		rspPage.setTotal_num(page.getTotal());
		rspPage.setPage_num(page.getPageNum());
		rspPage.setPage_size(page.getPageSize());
		return rspPage;

	}

	@Override
	public boolean createOrder(ReqOrderPay reqOrderPay) throws Exception {
		Order order = new Order();
		order.setId(UuidUtil.getRandomUuid());//订单id
		order.setOrderNbr(DateFormatUtils.formate(new Date(), DateFormatUtils.TIGHT_PATTERN_DATETIME));//订单编号
		order.setLinkMan(reqOrderPay.getLink_man());//;联系人
		order.setLinkPhone(reqOrderPay.getLink_phone());//联系人电话
		order.setAddress(reqOrderPay.getAddress());//联系地址
		order.setCreateTime(new Date());//下单时间
		order.setStatus(0);//状态  未付款
		order.setUserId(reqOrderPay.getUser_id());//客户id
		order.setStaffId(reqOrderPay.getStaff_id());//员工id
		order.setExpressFee(reqOrderPay.getExpress_fee());//快递费
		order.setTotalPrice(reqOrderPay.getTotal_price());//商品总费用
		order.setTotalPriceDiscount(reqOrderPay.getTotal_price_discount());//实付金额
		order.setEnterpriseId(reqOrderPay.getEnterprise_id());
		order.setDepartmentId(reqOrderPay.getDepartment_id());
		Integer offerLevel = 0;
		for (ReqOrder2 detail : reqOrderPay.getOrder_detail()) {
			offerLevel = orderMapper.findOfferLevel(detail.getNorm_id());//查询商品的库存
			OfferSpec offerSpec = offerSpecMapper.selectByPrimaryKey(detail.getOffer_id());//查询商品名称
			if (offerLevel < detail.getNum()) {//如果库存小于等于购买数量
				throw new BusinessException(ErrCode.NO_OFFERLEVEL, offerSpec.getOfferName() + "库存不足");
			}
		}
		//订单佣金
		BigDecimal extractValue = BigDecimal.ZERO;
		for (ReqOrder2 detail : reqOrderPay.getOrder_detail()) {
			OfferSpec offerSpec = offerSpecMapper.selectByPrimaryKey(detail.getOffer_id());
			if (offerSpec.getExtractType() == 1) {
				offerSpec.getExtractValue();//单个商品的佣金
				BigDecimal num = new BigDecimal(detail.getNum());//数量
				extractValue = extractValue.add(offerSpec.getExtractValue().multiply(num));
			} else {
				detail.getOffer_price_discount();//售价
				offerSpec.getExtractPer();//比例
				BigDecimal num = new BigDecimal(detail.getNum());//数量
				extractValue = extractValue.add(offerSpec.getExtractPer().multiply(detail.getOffer_price_discount()).multiply(num));
				extractValue = extractValue.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
			}
		}
		order.setExtractValue(extractValue);
		if (orderMapper.insertSelective(order) > 0) {//生成订单数
			for (ReqOrder2 detail : reqOrderPay.getOrder_detail()) {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setId(UuidUtil.getRandomUuid());//订单明细id
				orderDetail.setOrderId(order.getId());//订单id
				orderDetail.setOfferId(detail.getOffer_id());//商品id
				orderDetail.setNormsId(detail.getNorm_id());//规格id
				orderDetail.setOfferNum(detail.getNum());//购买数量
				orderDetail.setOfferPrice(detail.getOffer_price());//单价
				orderDetail.setOfferPriceDiscount(detail.getOffer_price_discount());//折扣价
				orderDetail.setTotalPrice(detail.getOffer_price().multiply(BigDecimal.valueOf(detail.getNum())));//总价
				orderDetail.setTotalPriceDiscount(detail.getOffer_price_discount().multiply(BigDecimal.valueOf(detail.getNum()))); //打完折之后的总价
				orderDetailMapper.insertSelective(orderDetail);
				ShoppingCart shoppingCart = shoppingCartMapper.getcount(detail.getOffer_id(), detail.getNorm_id(), reqOrderPay.getUser_id());//根据规格id和商品id查看购物车是否有东西
				if (shoppingCart != null) {
					shoppingCartMapper.deleteByPrimaryKey(shoppingCart.getId());//有的话就直接删除
				}
			}
		}

		// 修改订单状态
		if (order.getStatus().equals(1)) {
			return true;
		} else {
			order.setStatus(1);
			orderMapper.updateByPrimaryKeySelective(order);
		}

		List<RspNormAndNum> list = orderMapper.findNormAndNumByOrderId(order.getId());//根据订单id查询对应的规格id以及数量以及商品ID
		for (RspNormAndNum normAndNum : list) {
			Integer level = orderMapper.findOfferLevel(normAndNum.getNorms_id());//根据规格id查询库存
			orderMapper.updateOfferLevel(level - normAndNum.getOffer_num(), normAndNum.getNorms_id());//修改库存
			OfferSpec offerSpec = offerSpecMapper.selectByPrimaryKey(normAndNum.getOffer_id());//根据商品id查询销量添加销量
			if (offerSpec.getOfferSale() == null) {
				offerSpec.setOfferSale(0);
			}
			offerSpec.setOfferSale(normAndNum.getOffer_num() + offerSpec.getOfferSale());//添加销量
			offerSpec.setId(normAndNum.getOffer_id());
			if (offerLevel - normAndNum.getOffer_num() == 0) {//如果库存为0设置状态为已售罄
				orderMapper.updateOfferNormStatus(normAndNum.getNorms_id());
				// 设置订单表的状态为已售罄 --A lql 20180919000004
				offerSpec.setStatus(2);
			}
			offerSpecMapper.updateByPrimaryKeySelective(offerSpec);
		}
		return true;
	}
}
