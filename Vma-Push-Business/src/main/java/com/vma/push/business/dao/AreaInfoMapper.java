package com.vma.push.business.dao;

import com.vma.push.business.entity.AreaInfo;
import org.apache.ibatis.annotations.Param;

/**
 * Created by chenzui on 2018/8/26.
 */
public interface AreaInfoMapper extends BaseDao<AreaInfo,Integer> {
    public AreaInfo selectAreaByPID(@Param("provinceId") Integer provinceId);
}
