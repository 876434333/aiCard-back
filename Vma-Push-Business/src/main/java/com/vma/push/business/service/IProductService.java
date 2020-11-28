package com.vma.push.business.service;

import com.vma.push.business.dto.RspProdPage;
import com.vma.push.business.dto.req.*;
import com.vma.push.business.dto.req.store.*;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.rsp.store.*;
import com.vma.push.business.entity.OfferSpec;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/4/23 20:41
 */
public interface IProductService extends IBaseService<OfferSpec,String>{


    RspProductId addProduct(ReqSpecAndImg reqSpecAndImg, HttpServletRequest request);//添加产品


    RspPage<RepAllProduct> findProductBySelect(PageSelect pageSelect);//模糊查询产品列表

    /**
     * 获取staff所在企业发布的所有产品
     * @param reqProductPage
     * @return
     */
    RspProdPage enterpriseProduct(ReqProductPage reqProductPage);

    Integer staffProduct(String enid,String userid);

    void updateProduct(ReqUpdateProduct reqUpdateProduct);//修改指定产品

    RepSpecAndImg selectProductById(String id, String enterprise_id);//根据id查询

    RspPageMiniStore<RspOfferInfo> offerPage(Page page, String eneterpriseId,String staff_id);

    RspPage<RspUserAddress> getAddress(ReqUserAddress reqUserAddress);//收货地址列表

    void DelAddress(ReqDelAddress reqDelAddress);//删除收货地址（修改企业的状态）

    void addAddress(ReqAddAddress addAddress);

    RspDetailAddress Detail(String id);//收货地址详情

    void updateAddress(ReqUpdateAddress reqUpdateAddress);

    void updateDefault(ReqUpdateDefault reqUpdateDefault);

    RspPage<RspOfferList> offerList(ReqOfferList reqOfferList);

    /**
     * 企业后台 商品管理 添加商品
     * @param reqAddOffer
     */
    void offerAdd(ReqAddOffer reqAddOffer);

    ReqEditOffer offerDetail(String id);

    void offerEdit(ReqEditOffer reqEditOffer);

    void changeStatus(List<String> ids, int type);

    RspDefaultAddress getDefaul(String user_id);

    RspOffInfo offerInfo(String id,String staffid);

    RspPage<RspRecOffer> recList(String staffId, Page page);

    void addShopCart(ReqAddCart reqAddCart);

    void updateOrderStatus(String id);
}
