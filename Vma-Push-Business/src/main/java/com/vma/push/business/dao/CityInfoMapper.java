package com.vma.push.business.dao;

import com.vma.push.business.entity.CityInfo;
import org.apache.ibatis.annotations.Param;

/**
 * Created by chenzui on 2018/8/26.
 */
public interface CityInfoMapper extends BaseDao<CityInfo,Integer> {
    public CityInfo selectCityByPID(@Param("provinceId") Integer provinceId);
}
