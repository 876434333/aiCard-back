package com.vma.push.business.dao;

import com.vma.push.business.dto.req.ReqAddAdv;
import com.vma.push.business.dto.req.ReqAdvSelect;
import com.vma.push.business.dto.req.ReqAdvSelectNew;
import com.vma.push.business.dto.rsp.RspAdv;
import com.vma.push.business.dto.rsp.RspAdvNew;
import com.vma.push.business.entity.Adv;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/5/3 11:01
 */
public interface AdvMapper extends BaseDao<Adv,String>{
    List<RspAdv> findAdvPage();

    List<RspAdv> findAdvBySelect(ReqAdvSelect reqAdvSelect);//条件查询广告

    void addAdv(ReqAddAdv reqAddAdv);//添加广告

    List<RspAdv> miniFindAdv(String enterprise_id);

    List<RspAdvNew> findAdvBySelectNew(ReqAdvSelectNew reqAdvSelect);//条件查询广告

    void advIOrderAddOne(@Param("id")String id, @Param("enterpriseId")String enterpriseId);

    void toBeNo1(@Param("id")String id);

    List<Adv> getListAdv(@Param("enterpriseId")String enterpriseId);
}
