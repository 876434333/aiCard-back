package com.vma.push.business.dao;

import com.vma.push.business.dto.shop.Shop;
import com.vma.push.business.dto.shop.ShopProduct;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ShopMapper {

    @Select("select type from shop where id = (select shop_id from enterprise where id = #{enterpriseId})")
    Integer selectShopType(String enterpriseId);

    @Update("update enterprise set shop_id = #{shopId} where id = #{enterpriseId}")
    Integer updateEntShopId(@Param("shopId") String shopId, @Param("enterpriseId")String enterpriseId);

    @Update("insert into shop(id,enterprise_id,name,type,create_time) " +
                "values(#{id},#{enterprise_id},#{name},#{type},NOW())")
    Integer insertShop(Shop shop);

    @Select("select id from shop where enterprise_id = #{enterpriseId} and type = #{type}")
    String selectShopId(@Param("enterpriseId") String enterpriseId, @Param("type") Integer type);

    @Select("select paytype from shop where enterprise_id = #{enterpriseId} and type = 2")
    Integer selectPayType(@Param("enterpriseId") String enterpriseId);

}
