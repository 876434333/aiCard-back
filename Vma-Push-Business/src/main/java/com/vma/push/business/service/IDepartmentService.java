package com.vma.push.business.service;

import com.vma.push.business.dto.req.Page;
import com.vma.push.business.dto.req.ReqAddDepartInfo;
import com.vma.push.business.dto.req.ReqUpdateDepartInfo;
import com.vma.push.business.dto.req.WechatDepartment;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.entity.Department;

import java.util.List;

/**
 * Created by huxiangqiang on 2018/4/20.
 */
public interface IDepartmentService extends IBaseService<Department,String> {

    /**
     * 新增部门（微信api）
     * @param dt 部门对象
     * @return
     */
    String insertApi(WechatDepartment dt,String enterprise_id);

    /**
     * 修改部门（微信api）
     * @param dt 部门对象
     * @return
     */
    String updateApi(WechatDepartment dt,String enterprise_id);

    /**
     * 通过id删除部门（微信api）
     * @return
     */
    String deleteApi(String id,String enterprise_id);

    /**
     * 同时添加部门到微信和数据库
     * @param reqAddDeaprtInfo
     */
    void addDepart(ReqAddDepartInfo reqAddDeaprtInfo,String enterprise_id);

    /**
     * 同时修改部门到微信和数据库
     * @param reqUpdateDepartInfo
     */
    void updateDepart(ReqUpdateDepartInfo reqUpdateDepartInfo,String enterprise_id);

    /**
     * 删除部门
     * @param id
     */
    void delDepart(String id,String enterprise_id);

    /**
     * 获取所有的部门
     * @return
     */
    RspPage<RspDepartPage> getAll(Page page,String enid);

    /**
     * 获取部门树
     * @param enid  企业编号
     * @return
     */
    List<RspDepartTree> departTree(String enid,String pid);

    /**
     * 获取部门树
     * @param enid  企业编号
     * @return
     */
    List<RspDepartTreeNew> departTreeNew(String enid, String pid);

    /**
     * 获取部门列表  部门名字 id
     * @param enid
     * @return
     */
    List<RspDepartInfo> departinfo(String enid);
}
