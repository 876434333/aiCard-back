package com.vma.push.business.dao;


import com.vma.push.business.entity.Totals;

public interface TotalsMapper extends BaseDao<Totals,String> {

    Totals findTotal(String staffId);//
}
