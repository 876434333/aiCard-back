package com.vma.push.business.dao;

import com.vma.push.business.entity.Deploy;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * Create By ChenXiAoBin
 * on 2018/6/18
 */
@Service
public interface DeployMapper extends BaseDao<Deploy,String> {
    Deploy selectAll(@Param("id") String id);
}
