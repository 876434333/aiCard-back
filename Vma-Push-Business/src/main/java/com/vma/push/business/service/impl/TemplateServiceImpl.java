package com.vma.push.business.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vma.push.business.dao.ShopTemplateMapper;
import com.vma.push.business.dto.req.template.ReqTemplateSelect;
import com.vma.push.business.dto.req.template.ReqUpdateTem;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.template.RspTemplatePage;
import com.vma.push.business.entity.ShopTemplate;
import com.vma.push.business.service.ITemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create By ChenXiAoBin
 * on 2018/6/13
 */
@Service
public class TemplateServiceImpl implements ITemplateService {


    @Autowired
    private ShopTemplateMapper shopTemplateMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(ShopTemplate record) {
        return 0;
    }

    @Override
    public int insertSelective(ShopTemplate record) {
        return 0;
    }

    @Override
    public ShopTemplate selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(ShopTemplate record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(ShopTemplate record) {
        return 0;
    }

    @Override
    public RspPage<RspTemplatePage> findTemplateSerlect(ReqTemplateSelect reqTemplateSelect) {
        RspPage<RspTemplatePage> rspPage = new RspPage<>();
        Page page = PageHelper.startPage(reqTemplateSelect.getPage_num(),reqTemplateSelect.getPage_size(),true);
        List<RspTemplatePage> list = shopTemplateMapper.findTemplateSerlect(reqTemplateSelect);
        rspPage.setData_list(list);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return  rspPage;
    }

    /**
     * 根据ID查看模版
     * @return
     */
    @Override
    public RspTemplatePage findTemplateById(String id) {
        return shopTemplateMapper.findTemplateById(id);
    }

    /**
     * 编辑
     * @param reqUpdateTem
     */
    @Override
    public void updateTem(ReqUpdateTem reqUpdateTem) {
       ShopTemplate shopTemplate = reqUpdateTem.toShopTemplate();
       shopTemplateMapper.updateByPrimaryKeySelective(shopTemplate);
    }
}
