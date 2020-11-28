package com.vma.push.business.dao;

import com.vma.push.business.dto.req.ReqUserFormAdd;
import com.vma.push.business.entity.Admin;
import com.vma.push.business.entity.UserFormRela;

/**
 * Created by huxiangqiang on 2018/6/5.
 */
public interface UserFormRelaMapper extends BaseDao<UserFormRela,String>{

    void userformAdd(ReqUserFormAdd reqUserFormAdd);
    Integer isexist(ReqUserFormAdd reqUserFormAdd);
    void userformDel(ReqUserFormAdd reqUserFormAdd);
    String getForm(String userid);
}
