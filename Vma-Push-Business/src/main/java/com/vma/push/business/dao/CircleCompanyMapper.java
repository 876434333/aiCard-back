package com.vma.push.business.dao;

import com.vma.push.business.dto.req.ReqCircleByStaffAndEnterprise;
import com.vma.push.business.dto.req.ReqSelectCircle;
import com.vma.push.business.dto.rsp.RspAllCircle;
import com.vma.push.business.dto.rsp.RspCircleDetail;
import com.vma.push.business.entity.CircleCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by huxiangqiang on 2018/4/25.
 */
public interface CircleCompanyMapper extends BaseDao<CircleCompany,String> {

    /**
     * 获取所有的朋友圈id
     * @return
     */
    List<String> getAllId();

    /**
     * 获取销售人员以及公司的朋友圈
     * @param staffid
     * @return
     */
    List<String> miniCircle(@Param("staffid") String staffid);

    /**
     * 通过多个条件查询朋友圈id
     * @param reqSelectCircle
     * @return
     */
    List<String> getAllIdByItem(ReqSelectCircle reqSelectCircle);
    /**
     * 获取某个员工的所有朋友圈id
     * @return
     */
    List<String> getAllIdByStaffId(ReqCircleByStaffAndEnterprise req);

    Integer pageCount(String id);

    /**
     * 获取某个员工的所有朋友圈id
     * @return
     */
    List<String> getAllIdByStaff(ReqCircleByStaffAndEnterprise req);

    Integer pageCountByStaff(String id);
    /**
     * 通过公司id获取该公司的所有朋友圈id
     * @param id
     * @return
     */
    List<String> getAllIdbyenid(String id);
    /**
     * 获取朋友圈详情
     * @param id
     * @return
     */
    RspCircleDetail getCircleInfoById(String id);

    /**
     * 获取朋友圈信息
     * @param id
     * @return
     */
    RspAllCircle rspAllCircle(String id);

}
