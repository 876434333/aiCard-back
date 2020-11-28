package com.vma.push.business.service;

import com.vma.push.business.dto.shop.Category;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IShopProductService<T> {

    List<T> getProductList(String entId, String categoryId);

    List<T> getProductList(String entId, Integer status, String categoryId);

    T getProductetail(String productId);

    void productAdd(HttpServletRequest request, T product);

    void productUpd(T product);

    void productDel(String productId);

    void productStatusUpd(Integer status, String id);

    List<Category> getSelectCategoryList(String entId);

    void categoryAdd(HttpServletRequest request, Category category);

    void categoryUpd(String name, String id);

    void categoryDel(String id);
}
