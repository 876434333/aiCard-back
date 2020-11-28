package com.vma.push.business.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vma.push.business.dao.AdvMapper;
import com.vma.push.business.dao.EnterpriseMapper;
import com.vma.push.business.dto.req.*;
import com.vma.push.business.dto.rsp.RspAdv;
import com.vma.push.business.dto.rsp.RspAdvNew;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.entity.Adv;
import com.vma.push.business.service.IAdvService;
import com.vma.push.business.util.UserDataUtil;
import com.vma.push.business.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/5/3 11:17
 */
@Service
public class AdvServiceImpl implements IAdvService {
    @Autowired
    private AdvMapper advMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return advMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Adv record) {
        return advMapper.insert(record);
    }

    @Override
    public int insertSelective(Adv record) {
        return advMapper.insertSelective(record);
    }

    @Override
    public Adv selectByPrimaryKey(String id) {
        return advMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Adv record) {
        return advMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Adv record) {
        return advMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<RspAdv> findAdvPage() {
        List<RspAdv> rspAdvs = advMapper.findAdvPage();
        return rspAdvs;
    }


    /**
     * 条件模糊查询
     * @param reqAdvSelect
     * @return
     */
    @Override
    public RspPage<RspAdv> findAdvBySelect(ReqAdvSelect reqAdvSelect,HttpServletRequest request) {
        RspPage<RspAdv> rspPage = new RspPage<>();
        Page page = PageHelper.startPage(reqAdvSelect.getPage_num(),reqAdvSelect.getPage_size(),true);
        List<RspAdv> rspAdvs = advMapper.findAdvBySelect(reqAdvSelect);
        rspPage.setData_list(rspAdvs);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return rspPage;
    }

    /**
     * 条件模糊查询
     * @param req
     * @return
     */
    @Override
    public RspPage<RspAdvNew> findAdvBySelectNew(ReqAdvSelectNew req, HttpServletRequest request) {
        RspPage<RspAdvNew> rspPage = new RspPage<>();
        Page page = PageHelper.startPage(req.getPage_num(),req.getPage_size(),true);
        List<RspAdvNew> rspAdvs = advMapper.findAdvBySelectNew(req);
        rspPage.setData_list(rspAdvs);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return rspPage;
    }

    /**
     * 添加广告
     * @param reqAddAdv
     */
    @Override
    public void addAdv(ReqAddAdv reqAddAdv,HttpServletRequest request) {
       Adv adv = new Adv();
       String staffId = UserDataUtil.getStaffId(request);//获取员工ID
       String enterpriseId = UserDataUtil.getEnterpriseId(request);//获取企业的ID
       adv.setaOrder(reqAddAdv.getA_order());//排序
       adv.setCreateStaffId(staffId);//创建人id
       adv.setCreateTime(new Date());//创建时间
       adv.setId(UuidUtil.getRandomUuid());//主键id
       adv.setHref(reqAddAdv.getHref());//链接
       adv.setImgUrl(reqAddAdv.getImg_url());
       adv.setLocation(reqAddAdv.getLocation()); //位置
       adv.setTitle(reqAddAdv.getTitle()); //广告名
       adv.setEnterpriseId(enterpriseId); //企业id
       advMapper.insertSelective(adv);
    }

    /**
     * 添加广告
     * @param req
     */
    @Override
    public void newAddAdv(ReqAddAdvNew req, HttpServletRequest request) {
        Adv adv = new Adv();
        String staffId = UserDataUtil.getAdminId(request);//获取员工ID
        String enterpriseId = UserDataUtil.getCustomId(request);//获取企业的ID
        adv.setaOrder(req.getA_order());//排序
        adv.setCreateStaffId(staffId);//创建人id
        adv.setCreateTime(new Date());//创建时间
        adv.setId(UuidUtil.getRandomUuid());//主键id
        adv.setHref(req.getHref());//链接
        adv.setImgUrl(req.getImg_url());
        adv.setLocation(req.getLocation()); //位置
        adv.setTitle(req.getTitle()); //广告名
        adv.setEnterpriseId(enterpriseId); //企业id
        adv.setFinishTime(req.getFinish_time());
        adv.setBeginTime(req.getBegin_time());
        adv.setStatus(req.getStatus());
        advMapper.insertSelective(adv);

    }

    /**
     * 更新广告
     * @param reqUpdateAdv
     */
    @Override
    public void updateAdv(ReqUpdateAdv reqUpdateAdv,HttpServletRequest request) {
        Adv adv = new Adv();
        adv.setTitle(reqUpdateAdv.getTitle());//广告名
        adv.setLocation(reqUpdateAdv.getLocation());//位置
        adv.setHref(reqUpdateAdv.getHref());//链接
        adv.setImgUrl(reqUpdateAdv.getImg_url());
        adv.setaOrder(reqUpdateAdv.getA_order());//展示顺序
       // adv.setCreateStaffId(reqUpdateAdv.getStaff_id());//创建人id
        adv.setModifyTime(new Date());//更新时间
        adv.setStatus(reqUpdateAdv.getStatus());//状态
        adv.setId(reqUpdateAdv.getId());//广告id
        advMapper.updateByPrimaryKeySelective(adv);

    }

    /**
     * 更新广告
     * @param req
     */
    @Override
    public void updateAdvNew(ReqUpdateAdvNew req ,HttpServletRequest request) {
        Adv adv = new Adv();
        adv.setTitle(req.getTitle());//广告名
        adv.setLocation(req.getLocation());//位置
        adv.setHref(req.getHref());//链接
        adv.setImgUrl(req.getImg_url());
        adv.setaOrder(req.getA_order());//展示顺序
        // adv.setCreateStaffId(reqUpdateAdv.getStaff_id());//创建人id
        adv.setModifyTime(new Date());//更新时间
        adv.setStatus(req.getStatus());//状态
        adv.setId(req.getId());//广告id
        adv.setBeginTime(req.getBegin_time());
        adv.setFinishTime(req.getFinish_time());
        advMapper.updateByPrimaryKeySelective(adv);
    }

    /**
     * 小程序查询对应企业下的广告
     * @param request
     * @return
     */
    @Override
    public List<RspAdv> miniFindAdv(HttpServletRequest request) {
        String enterpriseId = UserDataUtil.getEnterpriseId(request);//获取企业的ID
        RspPage<RspAdv> rspPage = new RspPage<>();
        List<RspAdv> rspAdvs = advMapper.miniFindAdv(enterpriseId);
        return rspAdvs;
    }

    /**
     * 广告置顶
     * @param id
     */
    @Override
    public void toBeNo1(String id,String enterpriseId){
        advMapper.advIOrderAddOne(id,enterpriseId);
        advMapper.toBeNo1(id);

    }

    /**
     * 广告排序
     * adv 新增广告或者修改顺序的广告
     * list 企业的所有广告
     **/
    @Override
    public void orderAdv(HttpServletRequest request){
        String enterpriseId = UserDataUtil.getCustomId(request);//获取企业的ID
        List<Adv> listAdv = advMapper.getListAdv(enterpriseId);
        int i = 1;
        for(Adv adv: listAdv){
            adv.setaOrder(i);
            advMapper.updateByPrimaryKey(adv);
            i++;
        }
    }
}
