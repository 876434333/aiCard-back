package com.vma.push.business.service;

import com.vma.push.business.dto.ReqMiniCirclePage;
import com.vma.push.business.dto.req.ReqAddCircle;
import com.vma.push.business.dto.req.ReqCircleByStaffAndEnterprise;
import com.vma.push.business.dto.req.ReqSelectCircle;
import com.vma.push.business.dto.req.ReqUpdateCircle;
import com.vma.push.business.dto.rsp.RspAllCircle;
import com.vma.push.business.dto.rsp.RspCircleDetail;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.entity.CircleCompany;

import java.util.List;

/**
 * Created by huxiangqiang on 2018/4/25.
 */
public interface ICircleCompanyService extends IBaseService<CircleCompany,String> {
    /**
     * 获取所有朋友圈信息
     * @return
     */
    RspPage<RspCircleDetail> getAll(Integer pageNum,Integer pageSize);

    /**
     * 获取小程序端 所有朋友圈
     * @param reqMiniCirclePage
     * @return
     */
    RspPage<RspCircleDetail> miniCircle(ReqMiniCirclePage reqMiniCirclePage);
    /**
     * 通过条件查询朋友圈
     * @param reqSelectCircle
     * @return
     */
    RspPage<RspAllCircle> circlePage(ReqSelectCircle reqSelectCircle);
    /**
     * 获取用户所有的朋友圈信息
     * @return
     */
    RspPage<RspCircleDetail> getAllByStaffId(Integer pageNum,Integer pageSize,ReqCircleByStaffAndEnterprise req);
    /**
     * 通过公司获取该公司的所有朋友圈
     * @param id
     * @return
     */

    Integer pageCount(String id);
    /**
     * 获取用户所有的朋友圈信息
     * @return
     */
    RspPage<RspCircleDetail> getAllByStaff(Integer pageNum,Integer pageSize,ReqCircleByStaffAndEnterprise req);
    /**
     * 通过公司获取该公司的所有朋友圈
     * @param id
     * @return
     */

    Integer pageCountByStaff(String id);
    List<RspCircleDetail> getAllByid(String id);
    /**
     * 发布朋友圈
     * @param reqAddCircle
     * @param flag 0个人  1 公司
     */
    void sendCircle(ReqAddCircle reqAddCircle,Integer flag);

    /**
     * 编辑朋友圈
     * @param reqUpdateCircle
     */
    void editCircle(ReqUpdateCircle reqUpdateCircle);

    /**
     * 删除朋友圈
     * @param id 朋友圈id
     */
    void delCircle(String id);
    /**
     * 获取朋友圈详情
     * @param id
     * @return
     */
    RspCircleDetail circleDetail(String id);

    /**
     * 小程序朋友圈详情
     * @param id
     * @param userid
     * @return
     */
    RspCircleDetail minicircleDetail(String id,String userid);
    /**
     * 销售端朋友圈详情
     * @param id
     * @param userid
     * @return
     */
    RspCircleDetail salecircleDetail(String id, String userid);

    /**
     * 获取朋友圈简要的信息  点赞数评论数 不获取点赞评论明细
     * @param id
     * @return
     */
    RspAllCircle circlebrief(String id);
}
