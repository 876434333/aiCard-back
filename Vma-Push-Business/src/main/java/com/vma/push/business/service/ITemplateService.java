package com.vma.push.business.service;

import com.vma.push.business.dto.req.template.ReqTemplateSelect;
import com.vma.push.business.dto.req.template.ReqUpdateTem;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.template.RspTemplatePage;
import com.vma.push.business.entity.ShopTemplate;

/**
 * Create By ChenXiAoBin
 * on 2018/6/13
 */
public interface ITemplateService extends IBaseService<ShopTemplate,String> {


    RspPage<RspTemplatePage> findTemplateSerlect(ReqTemplateSelect reqTemplateSelect);

    RspTemplatePage findTemplateById(String id);

    void updateTem(ReqUpdateTem reqUpdateTem);
}
