package com.vma.push.business.dao;

import com.vma.push.business.dto.req.store.ReqUserAddress;
import com.vma.push.business.dto.rsp.store.RspDefaultAddress;
import com.vma.push.business.dto.rsp.store.RspDetailAddress;
import com.vma.push.business.dto.rsp.store.RspUserAddress;
import com.vma.push.business.entity.UserAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Create By ChenXiAoBin
 * on 2018/7/24
 */
public interface UserAddressMapper extends BaseDao<UserAddress,String> {
    List<RspUserAddress> getAddress(ReqUserAddress reqUserAddress);//

    RspDetailAddress detail(String id);//收货地址详情

    void updateAllDefault(String user_id);


    String findProvinceCode(String province_name);

    String findCityCode( @Param("city_name") String city_name,@Param("province_id") String province_id);//获取city的code

    String findAreaCode(@Param("area_name") String area_name,@Param("city_id") String city_id);

    String findProvinceId(String province_code);//获取省级的id

    String findCityId(@Param("city_name") String city_name, @Param("province_id")String province_id);

    RspDefaultAddress getDefaul(String user_id);

    List<UserAddress> findByUserId(String user_id);//跟进userid查询
}
