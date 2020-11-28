package com.vma.push.bi.dao;

import com.vma.push.bi.entity.ClickAction;

import java.util.List;

/**
 * Created by chenzui on 2018/5/19.
 */
public interface ClickActionMapper extends BaseDao<ClickAction,String> {

    public List<ClickAction> findAll();

    void moveClickData();

}
