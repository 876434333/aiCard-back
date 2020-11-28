package com.vma.push.business.dao;

import com.vma.push.business.dto.shop.ShopProduct;
import com.vma.push.business.dto.shop.ShopProductImg;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ShopProductMapper {

    @Select("select * from shop_product where enterprise_id = #{enterpriseId}")
    @Results({
        @Result(id=true, property="id", column="id"),
        @Result(property="productImgs", column="id",javaType=List.class,
            many=@Many(select="com.vma.push.business.dao.ShopProductMapper.selectProductImgListByProductId"))
    })
    List<ShopProduct> selectProductListByEntId(String enterpriseId);

    @Select("select * from shop_product_img where product_id = #{productId} order by type")
    List<ShopProductImg> selectProductImgListByProductId(@Param("productId") String productId);

    @Select("select * from shop_product where id = #{id}")
    ShopProduct selectProductById(@Param("id") String id);

    @Insert("insert into shop_product(" +
                "id,name,salePrice,code,descript,create_staff_id,enterprise_id,create_time) " +
            "values(" +
                "#{id},#{name},#{salePrice},#{code},#{descript},#{create_staff_id},#{enterprise_id},NOW()" +
            ")")
    Integer insertProduct(ShopProduct product);

    @Update("update shop_product set " +
                "name=#{name},salePrice=#{salePrice},descript=#{descript},modify_time=NOW() " +
            "where id = #{id}")
    Integer updateProduct(ShopProduct product);

    @Delete("delete from shop_product where id = #{id}")
    Integer deleteProduct(@Param("id") String id);

    @Insert("insert into shop_product_img(id,product_id,img_url,descript,type) " +
                "values(#{id},#{product_id},#{img_url},#{descript},#{type})")
    Integer insertProductImg(ShopProductImg productImg);

    @Delete("delete from shop_product_img where product_id = #{productId}")
    Integer deleteProductImg(@Param("productId") String productId);

}
