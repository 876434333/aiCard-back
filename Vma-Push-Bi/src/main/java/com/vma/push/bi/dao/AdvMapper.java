package com.vma.push.bi.dao;


import com.vma.push.bi.entity.Adv;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/5/3 11:01
 */
public interface AdvMapper extends BaseDao<Adv,String>{
    public void changeStatus();
}
