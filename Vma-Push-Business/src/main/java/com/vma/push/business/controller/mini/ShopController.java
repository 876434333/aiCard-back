package com.vma.push.business.controller.mini;

import com.vma.push.business.dao.ShopMapper;
import com.vma.push.business.dao.ShopProductMapper;
import com.vma.push.business.dto.ContactCard;
import com.vma.push.business.service.IShopService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ljh
 * @Description: 设置商城类型
 * @date 2018-10-29 14:25
 */
@RestController
@RequestMapping("/v2.0")
@Api(value = "微信小程序", description = "小程序--设置商城类型api", tags = {"ShopController"})
public class ShopController {

    @Autowired
    IShopService shopService;

    @ApiOperation(value = "设置商城类型", notes = "设置商城类型")
    @RequestMapping(value = "/mini/shop/startup/{type}/{entId}", method = RequestMethod.GET)
    public void startup(@PathVariable("type") Integer type, @PathVariable("entId") String entId) {
        shopService.startupShop(type, entId);
    }

    @ApiOperation(value = "获取商城类型", notes = "获取商城类型")
    @RequestMapping(value = "/mini/shop/type/{entId}", method = RequestMethod.GET)
    public Integer getShopType(@PathVariable("entId") String entId) {
        return shopService.getShopType(entId);
    }

    @ApiOperation(value = "获取是否开通支付功能", notes = "获取是否开通支付功能")
    @RequestMapping(value = "/mini/shop/isPay/{entId}", method = RequestMethod.GET)
    public Integer getPayType(@PathVariable("entId") String entId) {
        return shopService.getPayType(entId);
    }
}
