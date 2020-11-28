package com.vma.push.business.service;

import com.vma.push.business.dto.req.ReqAccountSelect;
import com.vma.push.business.dto.req.ReqAddAccount;
import com.vma.push.business.dto.req.ReqUpdateAccount;
import com.vma.push.business.dto.rsp.RspAccount;
import com.vma.push.business.dto.rsp.RspAccountOne;
import com.vma.push.business.dto.rsp.RspAgent;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.entity.Admin;
import com.vma.push.business.entity.Enterprise;

import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/5/8 16:09
 */
public interface ISuperStaffService extends IBaseService<Enterprise,String> {
   // RspPage<RspAccount> findAllAccount(ReqAccountSelect reqAccountSelect);//账号管理列表

    void addAccount(ReqAddAccount reqAddAccount,String adminId);

    void updateAccount(ReqUpdateAccount reqUpdateAccount);//修改账号信息

    RspPage<RspAccount> findAllAccount(ReqAccountSelect reqAccountSelect,String adminId);

    Admin findAdmin(String adminId);//

    RspAccountOne findAccountById(String id);//根据id查询详情

    List<RspAgent> findAllAgent();//查询所有代理商
}
