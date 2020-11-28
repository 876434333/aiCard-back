package com.vma.push.business.service;

import com.vma.push.business.dto.req.ReqSuperLogin;
import com.vma.push.business.entity.Admin;

/**
 * Created by ChenXiaoBin
 * 2018/5/13 15:50
 */
public interface ISuperLoginService extends IBaseService<Admin,String> {
    String login(ReqSuperLogin reqSuperLogin);
}
