package com.vma.push.business.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dao.CircleCommentLogMapper;
import com.vma.push.business.dao.CircleCompanyMapper;
import com.vma.push.business.dao.CircleImgMapper;
import com.vma.push.business.dao.CircleZanLogMapper;
import com.vma.push.business.dto.ReqMiniCirclePage;
import com.vma.push.business.dto.req.ReqAddCircle;
import com.vma.push.business.dto.req.ReqCircleByStaffAndEnterprise;
import com.vma.push.business.dto.req.ReqSelectCircle;
import com.vma.push.business.dto.req.ReqUpdateCircle;
import com.vma.push.business.dto.rsp.RspAllCircle;
import com.vma.push.business.dto.rsp.RspCircleDetail;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.entity.CircleCommentLog;
import com.vma.push.business.entity.CircleCompany;
import com.vma.push.business.entity.CircleImg;
import com.vma.push.business.entity.CircleZanLog;
import com.vma.push.business.service.ICircleCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/4/25.
 */
@Service
public class CircleCompanyServiceImpl implements ICircleCompanyService {

    @Autowired
    private CircleCompanyMapper circleCompanyMapper;

    @Autowired
    private CircleImgMapper circleImgMapper;

    @Autowired
    private CircleZanLogMapper circleZanLogMapper;

    @Autowired
    private CircleCommentLogMapper circleCommentLogMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return circleCompanyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CircleCompany record) {
        return 0;
    }

    @Override
    public int insertSelective(CircleCompany record) {
        return circleCompanyMapper.insertSelective(record);
    }

    @Override
    public CircleCompany selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(CircleCompany record) {
        return circleCompanyMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CircleCompany record) {
        return 0;
    }

    @Override
    public RspPage<RspCircleDetail> miniCircle(ReqMiniCirclePage reqMiniCirclePage) {
        RspPage<RspCircleDetail> rspPage =new RspPage<RspCircleDetail>();
        Page page= PageHelper.startPage(reqMiniCirclePage.getPage_num(),reqMiniCirclePage.getPage_size(),true);
        List<String> listid=circleCompanyMapper.miniCircle(reqMiniCirclePage.getStaff_id());
        List<RspCircleDetail> rspCircleDetails=new ArrayList<RspCircleDetail>();
        for (String id:listid)
        {
            rspCircleDetails.add(circleDetail2(id,reqMiniCirclePage.getUser_id()));
        }

        rspPage.setData_list(rspCircleDetails);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return rspPage;
    }

    @Override
    public RspPage<RspCircleDetail> getAllByStaffId(Integer pageNum,Integer pageSize,ReqCircleByStaffAndEnterprise req){
        RspPage<RspCircleDetail> rspPage =new RspPage<RspCircleDetail>();
        Page page= PageHelper.startPage(pageNum,pageSize,true);
        //获取所有朋友圈的id
        List<String> listid=circleCompanyMapper.getAllIdByStaffId(req);
        List<RspCircleDetail> rspCircleDetails = new ArrayList<RspCircleDetail>();
        for (String id:listid)
        {
            RspCircleDetail rspCircleDetail=circleDetail(id);
            rspCircleDetail.setIs_zan(circleZanLogMapper.isZansale(id,req.getEmployee_id()));
            rspCircleDetails.add(rspCircleDetail);
        }
        for (RspCircleDetail rsp:rspCircleDetails)
        {
            if(rsp.getEmployee_id().equals(req.getEmployee_id())){
                rsp.setIs_me(1);
            }else{
                rsp.setIs_me(0);
            }
        }
        rspPage.setData_list(rspCircleDetails);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return rspPage;
    }

    @Override
    public Integer pageCount(String id) {
        return circleCompanyMapper.pageCount(id);
    }

    @Override
    public RspPage<RspCircleDetail> getAllByStaff(Integer pageNum,Integer pageSize,ReqCircleByStaffAndEnterprise req){
        RspPage<RspCircleDetail> rspPage =new RspPage<RspCircleDetail>();
        Page page= PageHelper.startPage(pageNum,pageSize,true);
        //获取所有朋友圈的id
        List<String> listid=circleCompanyMapper.getAllIdByStaff(req);
        List<RspCircleDetail> rspCircleDetails = new ArrayList<RspCircleDetail>();
        for (String id:listid)
        {
            RspCircleDetail rspCircleDetail = circleDetail(id);
            rspCircleDetail.setIs_zan(circleZanLogMapper.isZansale(id,req.getEmployee_id()));
            rspCircleDetails.add(rspCircleDetail);
        }
        for (RspCircleDetail rsp:rspCircleDetails)
        {
            if(rsp.getEmployee_id().equals(req.getEmployee_id())){
                rsp.setIs_me(1);
            }else{
                rsp.setIs_me(0);
            }
        }
        rspPage.setData_list(rspCircleDetails);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return rspPage;
    }

    @Override
    public Integer pageCountByStaff(String id) {
        return circleCompanyMapper.pageCountByStaff(id);
    }

    @Override
    public RspPage<RspCircleDetail> getAll(Integer pageNum,Integer pageSize) {
        RspPage<RspCircleDetail> rspPage =new RspPage<RspCircleDetail>();
        Page page= PageHelper.startPage(pageNum,pageSize,true);
        //获取所有朋友圈的
        List<String> listid=circleCompanyMapper.getAllId();
        List<RspCircleDetail> rspCircleDetails=new ArrayList<RspCircleDetail>();
        for (String id:listid)
        {
            rspCircleDetails.add(circleDetail(id));
        }
        rspPage.setData_list(rspCircleDetails);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return rspPage;

    }

    @Override
    public RspPage<RspAllCircle> circlePage(ReqSelectCircle reqSelectCircle) {
        RspPage<RspAllCircle> rspPage =new RspPage<RspAllCircle>();
        Page page= PageHelper.startPage(reqSelectCircle.getPage_num(),reqSelectCircle.getPage_size(),true);
        //获取所有朋友圈的
        System.out.println(reqSelectCircle);
        List<String> listid=circleCompanyMapper.getAllIdByItem(reqSelectCircle);
        List<RspAllCircle> rspCircleDetails=new ArrayList<RspAllCircle>();
        for (String id:listid)
        {
            rspCircleDetails.add(circlebrief(id));
        }
        rspPage.setData_list(rspCircleDetails);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return rspPage;
    }
    @Override
    public RspAllCircle circlebrief(String id){
        RspAllCircle rspAllCircle=circleCompanyMapper.rspAllCircle(id);
        rspAllCircle.setCircle_imgs(circleImgMapper.getCircleImg(id));
        return rspAllCircle;
    }
    @Override
    public List<RspCircleDetail> getAllByid(String eid) {
        List<String> listid=circleCompanyMapper.getAllIdbyenid(eid);
        List<RspCircleDetail> rspCircleDetails=new ArrayList<RspCircleDetail>();
        for (String id:listid)
        {
            rspCircleDetails.add(circleDetail(id));
        }
        return rspCircleDetails;
    }

    @Override
    public void sendCircle(ReqAddCircle reqAddCircle,Integer flag) {
        /**
         * 将信息添加到朋友圈表
         */
        if(reqAddCircle.getType() == null){
            reqAddCircle.setType(1);
        }
        CircleCompany circleCompany=reqAddCircle.toCircleCompany();//new CircleCompany(reqAddCircle);
        circleCompany.setFlag(flag);
        circleCompanyMapper.insertSelective(circleCompany);

        /**
         * 循环图片数组，将图片一一增加到朋友圈图片表
         */
        if (reqAddCircle.getImg_urls()!=null){
            for(int i=0;i<reqAddCircle.getImg_urls().size();i++){
                CircleImg circleImg=reqAddCircle.toCircleImg();//new CircleImg(reqAddCircle);
                circleImg.setCircleId(circleCompany.getId());
                circleImg.setCreateTime(circleCompany.getCreateTime());
                circleImg.setModifyTime(circleCompany.getModifyTime());
                circleImg.setImgUrl(reqAddCircle.getImg_urls().get(i));
                circleImg.setiOrder(i);
                circleImgMapper.insertSelective(circleImg);
            }
        }

    }

    @Override
    public void editCircle(ReqUpdateCircle reqUpdateCircle) {
        /**
         * 修改朋友圈记录
         */
        CircleCompany circleCompany=reqUpdateCircle.toCircleCompany();//new CircleCompany(reqUpdateCircle);
        circleCompanyMapper.updateByPrimaryKeySelective(circleCompany);
        /**
         * 删除原有该朋友圈对应的所有图片
         */
        String circleid=reqUpdateCircle.getId();
       circleImgMapper.deleteByCircleId(circleid);

        /**
         * 重新添加信息到朋友圈图片表
         */
        CircleCompany circleCompany1=circleCompanyMapper.selectByPrimaryKey(circleCompany.getId());
        for(String url:reqUpdateCircle.getImg_urls()){
            CircleImg circleImg=reqUpdateCircle.toCircleImg();//new CircleImg(reqUpdateCircle);
            circleImg.setCircleId(circleCompany1.getId());
            circleImg.setCreateTime(circleCompany1.getCreateTime());
            circleImg.setModifyTime(circleCompany1.getModifyTime());
            circleImg.setImgUrl(url);
            circleImgMapper.insertSelective(circleImg);
        }

    }

    @Override
    public void delCircle(String id) {
        //删除朋友圈内容
        circleCompanyMapper.deleteByPrimaryKey(id);
        //删除对应的图片
        circleImgMapper.deleteByCircleId(id);
        //删除对应的评论
        circleCommentLogMapper.deleteCommentByCircleId(id);
        //删除对应的点赞记录
        circleZanLogMapper.deleteZanByCircleId(id);
    }

    @Override
    public RspCircleDetail circleDetail(String id) {
        /**
         * 获取朋友圈部分内容
         */
        RspCircleDetail repCircleDetail= circleCompanyMapper.getCircleInfoById(id);

         if (repCircleDetail==null){
            throw new BusinessException(1000,"该朋友圈已删除！");
        }
        /**
         * 获取点赞信息集合
         */
        repCircleDetail.setCircle_zan_logs(circleZanLogMapper.getZanInfo(id));

        /**
         * 获取评论集合
         */
        repCircleDetail.setCircle_comment_logs(circleCommentLogMapper.getCommentInfo(id));
        /**
         * 获取朋友圈图片集合
         */
        repCircleDetail.setCircle_imgs(circleImgMapper.getCircleImg(id));

        return  repCircleDetail;
    }

    public RspCircleDetail circleDetail2(String id,String circleUserid) {
        RspCircleDetail rspCircleDetail= circleDetail(id);

        //判断小程序用户是否点赞
        if (circleZanLogMapper.isZanmini(id,circleUserid)>0){
            rspCircleDetail.setIs_zan(1);
        }else {
            rspCircleDetail.setIs_zan(0);
        }
        return rspCircleDetail;

    }

    @Override
    public RspCircleDetail minicircleDetail(String id, String userid) {
        RspCircleDetail rspCircleDetail= circleDetail(id);
        //判断小程序用户是否点赞
        if (circleZanLogMapper.isZanmini(id,userid)>0){
            rspCircleDetail.setIs_zan(1);
        }else {
            rspCircleDetail.setIs_zan(0);
        }
        return rspCircleDetail;
    }

    @Override
    public RspCircleDetail salecircleDetail(String id, String staffid) {
        RspCircleDetail rspCircleDetail= circleDetail(id);

        //判断小程序用户是否点赞
        if (circleZanLogMapper.isZansale(id,staffid)>0){
            rspCircleDetail.setIs_zan(1);
        }else {
            rspCircleDetail.setIs_zan(0);
        }
        return rspCircleDetail;
    }
}
