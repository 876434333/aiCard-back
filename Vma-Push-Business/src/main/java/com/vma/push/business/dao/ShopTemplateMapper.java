package com.vma.push.business.dao;

import com.vma.push.business.dto.req.template.ReqTemplateSelect;
import com.vma.push.business.dto.rsp.template.RspTemplatePage;
import com.vma.push.business.entity.ShopTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Create By ChenXiAoBin
 * on 2018/6/13
 */
public interface ShopTemplateMapper extends BaseDao<ShopTemplate,String> {
    List<RspTemplatePage> findTemplateSerlect(ReqTemplateSelect reqTemplateSelect);


    RspTemplatePage findTemplateById(@Param("id") String id);

    ShopTemplate selectByCode(String templateId);
}
