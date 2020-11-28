package com.vma.push.business.service;

import com.vma.push.business.dto.req.*;
import com.vma.push.business.dto.rsp.RspAdv;
import com.vma.push.business.dto.rsp.RspAdvNew;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.entity.Adv;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/5/3 11:17
 */
public interface IAdvService extends IBaseService<Adv,String>{

    List<RspAdv> findAdvPage();//分页查询广告

    RspPage<RspAdv> findAdvBySelect(ReqAdvSelect reqAdvSelect,HttpServletRequest request);//条件查询

    void addAdv(ReqAddAdv reqAddAdv, HttpServletRequest request);//添加广告

    void newAddAdv(ReqAddAdvNew req, HttpServletRequest request);//添加广告

    void updateAdv(ReqUpdateAdv reqUpdateAdv, HttpServletRequest request);//更新广告

    void updateAdvNew(ReqUpdateAdvNew req ,HttpServletRequest request);//更新广告

    List<RspAdv> miniFindAdv(HttpServletRequest request);

    RspPage<RspAdvNew> findAdvBySelectNew(ReqAdvSelectNew req, HttpServletRequest request);//条件查询

    void toBeNo1(String id,String enterpriseId);

    void orderAdv(HttpServletRequest request);

}
