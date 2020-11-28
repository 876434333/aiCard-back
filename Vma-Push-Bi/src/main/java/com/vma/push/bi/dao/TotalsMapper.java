package com.vma.push.bi.dao;

import com.vma.push.bi.entity.Totals;

import java.util.List;

public interface TotalsMapper extends BaseDao<Totals,String> {
    void change();

    List<Totals> findAll();
}
