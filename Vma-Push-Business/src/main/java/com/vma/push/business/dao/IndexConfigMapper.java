package com.vma.push.business.dao;

import com.vma.push.business.entity.IndexConfig;
import org.apache.ibatis.annotations.Param;

/**
 * Created by huxiangqiang on 2018/6/14.
 */
public interface IndexConfigMapper extends BaseDao<IndexConfig,String>  {
    IndexConfig getNo(@Param("enterpriseId") String enterpriseId, @Param("pri")String pri);
}
