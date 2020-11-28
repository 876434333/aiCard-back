package com.vma.push.business.service;

import com.vma.push.business.dto.req.customer.ReqApplyPage;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.customer.RspApplyEnterprise;
import com.vma.push.business.dto.rsp.customer.RspApplyList;
import com.vma.push.business.dto.rsp.template.RspTemplatePage;
import com.vma.push.business.entity.Enterprise;

/**
 * Create By ChenXiAoBin
 * on 2018/6/14
 */
public interface ICustomerService extends IBaseService<Enterprise,String> {
    RspPage<RspApplyList> applyList(ReqApplyPage reqApplyPage);

    RspApplyEnterprise detailById(String id);//申请列表-企业详情
}
