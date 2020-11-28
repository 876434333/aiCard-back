package com.vma.push.business.service.impl;

import com.vma.push.business.dao.ShoppingCartMapper;
import com.vma.push.business.entity.ShoppingCart;
import com.vma.push.business.service.IShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huxiangqiang on 2018/7/24.
 */
@Service
public class ShopCartServiceImpl implements IShopCartService{
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return shoppingCartMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ShoppingCart record) {
        return shoppingCartMapper.insert(record);
    }

    @Override
    public int insertSelective(ShoppingCart record) {
        return shoppingCartMapper.insertSelective(record);
    }

    @Override
    public ShoppingCart selectByPrimaryKey(String id) {
        return shoppingCartMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ShoppingCart record) {
        return shoppingCartMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ShoppingCart record) {
        return shoppingCartMapper.updateByPrimaryKey(record);
    }

    @Override
    public void removeShopCart(List<String> ids) {
        for (String id:ids){
            shoppingCartMapper.deleteByPrimaryKey(id);
        }
    }
}
