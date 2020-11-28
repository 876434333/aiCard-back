package com.vma.push.business.dao;

import com.vma.push.business.dto.rsp.RspWebsite;
import com.vma.push.business.entity.WebsiteTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by chenzui on 2018/5/15.
 */
public interface WebsiteTemplateMapper extends BaseDao<WebsiteTemplate,String> {
    void delete(@Param("enterpriseId") String enterpriseId);

    List<RspWebsite> findWebsiteByEnterprise(String id);
}
