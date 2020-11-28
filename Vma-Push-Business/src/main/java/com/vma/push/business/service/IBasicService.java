package com.vma.push.business.service;

import com.vma.push.business.dto.req.ReqSystemLogin;
import com.vma.push.business.dto.req.enterprise.ReqModifyPhone;
import com.vma.push.business.dto.rsp.RspAdminLogin;
import com.vma.push.business.dto.rsp.RspEnterprise;
import com.vma.push.business.entity.Enterprise;
import com.vma.push.business.entity.Staff;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/5/4 5:52
 */
public interface IBasicService extends IBaseService<Enterprise,String> {
    RspEnterprise selectEnterprisetById(String enterpriseId);//查看企业基本信息

    void modifyPhone(ReqModifyPhone reqModifyPhone, HttpServletRequest request);

    RspAdminLogin login(ReqSystemLogin reqSystemLogin);

    void updateCardUsedInfo(String enterpriseId,Staff staff);

    /**
     * 查看用户所在企业列表
     * Created by ljh
     * 2018/10/23 10:20
     */
    List<Enterprise> selectEnterprisetListByOpenId(String openId);
}
