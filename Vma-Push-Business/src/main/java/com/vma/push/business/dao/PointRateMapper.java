package com.vma.push.business.dao;

import com.vma.push.business.dto.rsp.system.RspPointRate;
import com.vma.push.business.entity.PointRate;

import java.util.List;

/**
 * Create By ChenXiAoBin
 * on 2018/6/15
 */
public interface PointRateMapper extends BaseDao<PointRate,String>{
    RspPointRate selectPoint();
}
