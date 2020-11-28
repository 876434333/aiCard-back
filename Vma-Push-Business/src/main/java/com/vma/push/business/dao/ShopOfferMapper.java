package com.vma.push.business.dao;

import com.vma.push.business.dto.shop.Category;
import com.vma.push.business.dto.shop.ShopOfferImg;
import com.vma.push.business.dto.shop.ShopOfferSpec;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ShopOfferMapper {

    @Select("select os.id,os.offer_name,os.offer_price,os.category_id,cg.name category_name,os.descript,os.status,os.id,os.enterprise_id,os.create_staff_id,norm.offer_leave,norm.id norms_id " +
        "from shop_offer_spec os " +
        "left join shop_offer_norms norm on os.id = norm.offer_id " +
        "left join shop_category cg on os.category_id = cg.id " +
        "where os.enterprise_id = #{enterpriseId} and os.category_id like #{categoryId} and os.status != 3")
    @Results({
        @Result(id=true, property="id", column="id"),
        @Result(property="offerImgs", column="id",javaType=List.class,
            many=@Many(select="com.vma.push.business.dao.ShopOfferMapper.selectOfferImgListByOfferId"))
    })
    List<ShopOfferSpec> selectOfferListByEntId(@Param("enterpriseId") String enterpriseId, @Param("categoryId") String categoryId);

    @Select("select os.id,os.offer_name,os.offer_price,os.category_id,cg.name category_name,os.descript,os.status,os.id,os.enterprise_id,os.create_staff_id,norm.offer_leave,norm.id norms_id " +
        "from shop_offer_spec os " +
        "left join shop_offer_norms norm on os.id = norm.offer_id " +
        "left join shop_category cg on os.category_id = cg.id " +
        // "where os.enterprise_id = #{enterpriseId} and os.status = #{stauts} and os.category_id like #{categoryId} and os.status != 3")
        "where os.enterprise_id = #{enterpriseId} and (os.status = 1 or os.status = 2) and os.category_id like #{categoryId}")
    @Results({
        @Result(id=true, property="id", column="id"),
        @Result(property="offerImgs", column="id",javaType=List.class,
            many=@Many(select="com.vma.push.business.dao.ShopOfferMapper.selectOfferImgListByOfferId"))
    })
    List<ShopOfferSpec> selectOfferListByEntIdAndStatus(@Param("enterpriseId") String enterpriseId,@Param("stauts") Integer stauts, @Param("categoryId") String categoryId);

    @Select("select * from shop_offer_img where offer_spec_id = #{offerId} order by type")
    List<ShopOfferImg> selectOfferImgListByOfferId(String offerId);

    @Select("select * from shop_offer_spec where id = #{id}")
    ShopOfferSpec selectOfferById(@Param("id") String id);

    @Insert("insert into shop_offer_spec(" +
                "id,offer_name,offer_price,descript,create_staff_id,enterprise_id,status,create_time,onsale_time,is_pay_online,code,category_id) " +
            "values(" +
                "#{id},#{offer_name},#{offer_price},#{descript},#{create_staff_id},#{enterprise_id},1,NOW(),NOW(),1,uuid(),#{category_id}" +
            ")")
    Integer insertOffer(ShopOfferSpec offer);

    @Insert("insert into shop_offer_norms(" +
        "id,name,offer_id,offer_price,offer_leave,is_default,norms_type,status,norms_pic,create_time) " +
        "values(" +
        "uuid(),#{name},#{offer_id},#{offer_price},#{offer_leave},1,0,1,#{norms_pic},NOW()" +
        ")")
    Integer insertOfferNorms(@Param("name") String name,
                @Param("offer_id") String offer_id,
                @Param("offer_price") double offer_price,
                @Param("norms_pic") String norms_pic,
                @Param("offer_leave") Integer offer_leave);

    @Update("update shop_offer_norms set offer_leave = #{offer_leave},offer_price = #{offer_price},name = #{offer_name},norms_pic = #{norms_pic} where offer_id = #{offer_id}")
    Integer updateOfferNorms(@Param("offer_leave") Integer offer_leave,
                             @Param("offer_id") String offer_id,
                             @Param("offer_price") double offer_price,
                             @Param("norms_pic") String norms_pic,
                             @Param("offer_name") String offer_name);

    @Update("update shop_offer_spec set " +
        "offer_name=#{offer_name},offer_price=#{offer_price},descript=#{descript},category_id=#{category_id},status=#{status} " +
        "where id = #{id}")
    Integer updateOffer(ShopOfferSpec offer);

    @Update("update shop_offer_spec set status=#{status},onsale_time=NOW() where id = #{id}")
    Integer updateOfferStatus(@Param("status") Integer status,@Param("id") String id);

    @Delete("delete from shop_offer_spec where id = #{id}")
    Integer deleteOffer(@Param("id") String id);

    @Insert("insert into shop_offer_img(id,offer_spec_id,img_url,descript,type) " +
                "values(#{id},#{offer_spec_id},#{img_url},#{descript},#{type})")
    Integer insertOfferImg(ShopOfferImg offerImg);

    @Delete("delete from shop_offer_img where offer_spec_id = #{offerId}")
    Integer deleteOfferImg(@Param("offerId") String offerId);

    @Select("select id,name from shop_category where enterprise_id = #{entId}")
    List<Category> selectCategoryList(@Param("entId") String entId);

    @Insert("insert into shop_category(id,name,create_staff_id,enterprise_id,create_time) " +
        "values(#{id},#{name},#{create_staff_id},#{enterprise_id},NOW())")
    Integer insertCategory(Category category);

    @Update("update shop_category set name=#{name} where id = #{id}")
    Integer updateCategory(@Param("name") String name, @Param("id") String id);

    @Update("delete from shop_category where id = #{id}")
    Integer deleteCategory(@Param("id") String id);

}
