package com.vma.push.business.service.impl;

import com.vma.push.business.dao.ShopOfferMapper;
import com.vma.push.business.dto.shop.Category;
import com.vma.push.business.dto.shop.ShopOfferImg;
import com.vma.push.business.dto.shop.ShopOfferSpec;
import com.vma.push.business.service.IShopProductService;
import com.vma.push.business.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service("offerService")
public class ShopOfferServiceImpl implements IShopProductService<ShopOfferSpec> {

    @Autowired
    ShopOfferMapper som;

    @Override
    public List<ShopOfferSpec> getProductList(String entId, String categoryId) {
        if(categoryId.equals("*")){
            categoryId = "%";
        }
        return som.selectOfferListByEntId(entId, categoryId);
    }

    @Override
    public List<ShopOfferSpec> getProductList(String entId, Integer status, String categoryId) {
        if(categoryId.equals("*")){
            categoryId = "%";
        }
        return som.selectOfferListByEntIdAndStatus(entId, status, categoryId);
    }

    @Override
    public ShopOfferSpec getProductetail(String offerId) {
        return som.selectOfferById(offerId);
    }

    @Override
    @Transactional
    public void productAdd(HttpServletRequest request, ShopOfferSpec offer) {
        String productId = UuidUtil.getRandomUuid();
        offer.setId(productId);
        som.insertOffer(offer);
        String coverImg = null;
        List<ShopOfferImg> offerImgs = offer.getOfferImgs();
        for (ShopOfferImg offerImg : offerImgs) {
            if (offerImg.getType().equals("1")) {
                coverImg = offerImg.getImg_url();
                break;
            }
        }
        som.insertOfferNorms(offer.getOffer_name(),productId,offer.getOffer_price(),coverImg,offer.getOffer_leave());
        if (offerImgs != null) {
            for (ShopOfferImg offerImg : offerImgs) {
                offerImg.setId(UuidUtil.getRandomUuid());
                offerImg.setOffer_spec_id(productId);
                som.insertOfferImg(offerImg);
            }
        }
    }

    @Override
    @Transactional
    public void productUpd(ShopOfferSpec offer){
        if (offer.getOffer_leave() > 0) {
            offer.setStatus(1);
        }
        som.updateOffer(offer);
        String coverImg = null;
        List<ShopOfferImg> offerImgs = offer.getOfferImgs();
        for (ShopOfferImg offerImg : offerImgs) {
            if (offerImg.getType().equals("1")) {
                coverImg = offerImg.getImg_url();
                break;
            }
        }
        som.updateOfferNorms(offer.getOffer_leave(), offer.getId(),offer.getOffer_price(),coverImg,offer.getOffer_name());
        if (offerImgs != null) {
            // 先清除商品原本图片，再进行插入
            som.deleteOfferImg(offer.getId());
            for (ShopOfferImg offerImg : offerImgs) {
                offerImg.setId(UuidUtil.getRandomUuid());
                offerImg.setOffer_spec_id(offer.getId());
                som.insertOfferImg(offerImg);
            }
        }
    }

    @Override
    @Transactional
    public void productDel(String offerId) {
        som.deleteOffer(offerId);
        som.deleteOfferImg(offerId);
    }

    @Override
    public void productStatusUpd(Integer status, String id) {
        som.updateOfferStatus(status, id);
    }

    @Override
    public List<Category> getSelectCategoryList(String entId) {
        return som.selectCategoryList(entId);
    }

    @Override
    public void categoryAdd(HttpServletRequest request, Category category) {
        category.setId(UuidUtil.getRandomUuid());
        som.insertCategory(category);
    }

    @Override
    public void categoryUpd(String name, String id) {
        som.updateCategory(name, id);
    }

    @Override
    public void categoryDel(String id) {
        som.deleteCategory(id);
    }

}
