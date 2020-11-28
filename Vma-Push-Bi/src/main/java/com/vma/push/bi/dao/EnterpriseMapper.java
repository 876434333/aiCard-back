package com.vma.push.bi.dao;

import com.vma.push.bi.entity.Enterprise;

import java.util.List;

/**
 * Created by chenzui on 2018/5/19.
 */
public interface EnterpriseMapper extends BaseDao<Enterprise,String> {
    public List<Enterprise> findAll();
}
