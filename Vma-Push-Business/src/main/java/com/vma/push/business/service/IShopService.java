package com.vma.push.business.service;

public interface IShopService {

    /**
     * 开启商城
     * @param type
     */
    void startupShop(Integer type, String entId);

    /**
     * 获取商城类型
     * @return
     */
    Integer getShopType(String entId);

    /**
     * 获取商城是否开通支付
     * @return
     */
    Integer getPayType(String entId);
}
