package com.vma.push.business.service.impl;

import com.vma.push.business.dao.ShopProductMapper;
import com.vma.push.business.dto.shop.Category;
import com.vma.push.business.dto.shop.ShopProduct;
import com.vma.push.business.dto.shop.ShopProductImg;
import com.vma.push.business.service.IShopProductService;
import com.vma.push.business.util.UserDataUtil;
import com.vma.push.business.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service("productService")
public class ShopProductServiceImpl implements IShopProductService<ShopProduct>{

    @Autowired
    private ShopProductMapper spm;

    @Override
    public List<ShopProduct> getProductList(String entId, String categoryId) {
        return spm.selectProductListByEntId(entId);
    }

    @Override
    public List<ShopProduct> getProductList(String entId, Integer status, String categoryId) {
        return null;
    }

    @Override
    public ShopProduct getProductetail(String productId) {
        return spm.selectProductById(productId);
    }

    @Override
    @Transactional
    public void productAdd(HttpServletRequest request, ShopProduct product) {
        String productId = UuidUtil.getRandomUuid();
        product.setId(productId);
        List<ShopProductImg> productImgs = product.getProductImgs();
        spm.insertProduct(product);
        if (productImgs != null) {
            for (ShopProductImg productImg : productImgs) {
                productImg.setId(UuidUtil.getRandomUuid());
                productImg.setProduct_id(productId);
                spm.insertProductImg(productImg);
            }
        }
    }

    @Override
    @Transactional
    public void productUpd(ShopProduct product) {
        spm.updateProduct(product);
        List<ShopProductImg> productImgs = product.getProductImgs();
        if (productImgs != null) {
            // 先清除商品原本图片，再进行插入
            spm.deleteProductImg(product.getId());
            for (ShopProductImg productImg : productImgs) {
                productImg.setId(UuidUtil.getRandomUuid());
                productImg.setProduct_id(product.getId());
                spm.insertProductImg(productImg);
            }
        }
    }

    @Override
    @Transactional
    public void productDel(String productId) {
        spm.deleteProduct(productId);
        spm.deleteProductImg(productId);
    }

    @Override
    public void productStatusUpd(Integer status, String id) {

    }

    @Override
    public List<Category> getSelectCategoryList(String entId) {
        return null;
    }

    @Override
    public void categoryAdd(HttpServletRequest request, Category category) {

    }

    @Override
    public void categoryUpd(String name, String id) {

    }

    @Override
    public void categoryDel(String id) {

    }
}
