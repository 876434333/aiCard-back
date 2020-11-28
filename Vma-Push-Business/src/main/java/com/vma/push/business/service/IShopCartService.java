package com.vma.push.business.service;

import com.vma.push.business.entity.ShoppingCart;

import java.util.List;

/**
 * Created by huxiangqiang on 2018/7/24.
 */
public interface IShopCartService extends IBaseService<ShoppingCart,String> {
    void removeShopCart(List<String> ids);
}
