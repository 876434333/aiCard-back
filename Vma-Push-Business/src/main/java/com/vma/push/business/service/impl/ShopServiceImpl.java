package com.vma.push.business.service.impl;

import com.vma.push.business.dao.ShopMapper;
import com.vma.push.business.dto.shop.Shop;
import com.vma.push.business.service.IShopService;
import com.vma.push.business.util.UserDataUtil;
import com.vma.push.business.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ShopServiceImpl implements IShopService {

	@Autowired
	ShopMapper shopMapper;

	@Override
	public void startupShop(Integer type, String entId) {
		String shopId = shopMapper.selectShopId(entId, type);
		if (shopId == null) {
			shopId = UuidUtil.getRandomUuid();
			Shop shop = new Shop();
			shop.setId(shopId);
			shop.setEnterprise_id(entId);
			shop.setName("商城类型：" + type);
			shop.setType(type);
			shopMapper.insertShop(shop);
		}
		shopMapper.updateEntShopId(shopId, entId);
	}

	@Override
	public Integer getShopType(String entId) {
		return shopMapper.selectShopType(entId);
	}

	@Override
	public Integer getPayType(String entId) {
		Integer payType = shopMapper.selectPayType(entId);
		// 防止返回空值
		if (payType == 1) {
			return 1;
		} else {
			return 0;
		}
	}
}
