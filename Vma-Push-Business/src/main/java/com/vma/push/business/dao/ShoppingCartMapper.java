package com.vma.push.business.dao;

import com.vma.push.business.entity.ShoppingCart;
import org.apache.ibatis.annotations.Param;

/**
 * Created by huxiangqiang on 2018/7/24.
 */
public interface ShoppingCartMapper extends BaseDao<ShoppingCart,String> {

    ShoppingCart getcount(@Param("offerid") String offer_id, @Param("normid")String norms_id, @Param("userid") String id);

}
