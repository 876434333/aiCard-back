package com.vma.push.business.service.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.pagehelper.PageHelper;
import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dao.DepartmentMapper;
import com.vma.push.business.dto.req.Page;
import com.vma.push.business.dto.req.ReqAddDepartInfo;
import com.vma.push.business.dto.req.ReqUpdateDepartInfo;
import com.vma.push.business.dto.req.WechatDepartment;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.rsp.staff.RspStaff;
import com.vma.push.business.entity.Department;
import com.vma.push.business.service.IDepartmentService;
import com.vma.push.business.util.HttpUtil;
import com.vma.push.business.util.JSONObject;
import com.vma.push.business.util.WeChatApi;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huxiangqiang on 2018/4/20.
 */
@Service
public class DepartmentServiceImpl implements IDepartmentService{



    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private WeChatApi weChatApi;


    @Override
    public int deleteByPrimaryKey(String id) {
        return departmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Department record) {
        return departmentMapper.insert(record);
    }

    @Override
    public int insertSelective(Department record) {
        return departmentMapper.insertSelective(record);
    }

    @Override
    public Department selectByPrimaryKey(String id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Department record) {

        return departmentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Department record) {
        return departmentMapper.updateByPrimaryKey(record);
    }


    @Override
    public String insertApi(WechatDepartment dt,String enterprise_id) {
        String result="";
        String token= weChatApi.Contacts_token(enterprise_id);
        String url="https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token="+token;
        Map param = WeChatApi.transBean2Map(dt);
        result=HttpUtil.httpPostReq(url,param).getResponse_str();
        return result;
    }

    @Override
    public String updateApi(WechatDepartment dt,String enterprise_id) {
        String result="";
        String token=weChatApi.Contacts_token(enterprise_id);
        String url="https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token="+token;
        Map param = WeChatApi.transBean2Map(dt);
        result=HttpUtil.httpPostReq(url,param).getResponse_str();
        return result;
    }



    @Override
    public String deleteApi(String id,String enterprise_id) {
        String result="";
        String token=weChatApi.Contacts_token(enterprise_id);
        String url="https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token="+token+"&id="+id;
        result=HttpUtil.httpGetReq(url).getResponse_str();
        return result;
    }

    @Override
    public void delDepart(String id,String enterprise_id) {
        //删除微信中的部门
        String result=deleteApi(id,enterprise_id);
        int errcode=new JSONObject(result).getInt("errcode");
        if (errcode==0){
            //删除数据库中的部门
            //departmentMapper.deleteByPrimaryKey(id);
            departmentMapper.delDept(id,enterprise_id);
        }else{
            throw new BusinessException(errcode,"部门下存在员工");
        }

    }

    @Override
    public RspPage<RspDepartPage> getAll(Page pages,String enId) {
        RspPage<RspDepartPage> rspPage=new RspPage<RspDepartPage>();
        com.github.pagehelper.Page page = PageHelper.startPage(pages.getPage_num(),pages.getPage_size(),true);
        List<RspDepartPage> list =departmentMapper.getAll(enId);
        rspPage.setData_list(list);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return rspPage;
    }

    @Override
    public List<RspDepartTree> departTree(String enid,String pid) {
        //第一级的部门的父id为0
        List<RspDepartTree> rspDepartTrees=departmentMapper.DepartTree(enid,pid);
        for (RspDepartTree t:rspDepartTrees){
            List<RspDepartTree> children=departTree(enid,t.getId());
            t.setChildren(children);
        }
        return rspDepartTrees;
    }

    @Override
    public List<RspDepartTreeNew> departTreeNew(String enid, String pid) {
        //第一级的部门的父id为0
        List<RspDepartTreeNew> rspDepartTrees=departmentMapper.DepartTreeNew(enid,pid);
        for (RspDepartTreeNew t:rspDepartTrees){
            List<RspDepartTreeNew> children=departTreeNew(enid,t.getId());
            t.setChildren(children);
        }
        return rspDepartTrees;
    }

    @Override
    public void updateDepart(ReqUpdateDepartInfo reqUpdateDepartInfo,String enterprise_id) {
        //修改部门到微信
        WechatDepartment wechatDepartment=new WechatDepartment(reqUpdateDepartInfo);
        String result=updateApi(wechatDepartment,enterprise_id);
        int errcode=new JSONObject(result).getInt("errcode");
        if (errcode==0){
            //Department department=reqUpdateDepartInfo.toDepartment();//new Department(reqUpdateDepartInfo);
            //departmentMapper.updateByPrimaryKeySelective(department);
            departmentMapper.updateDeptName(reqUpdateDepartInfo);
        }
        if (errcode==60008){
            throw new BusinessException(1000,"上级部门不能选择子部门");
        }
        if (errcode==60010){
            throw new BusinessException(1000,"不能将上级部门设为自己");
        }
    }

    @Override
    public void addDepart(ReqAddDepartInfo reqAddDeaprtInfo,String enterprise_id) {

        //添加部门到微信
        WechatDepartment wechatDepartment=new WechatDepartment(reqAddDeaprtInfo);
        String rerult=insertApi(wechatDepartment,enterprise_id);
        int errcode=new JSONObject(rerult).getInt("errcode");
        if (errcode==0){
            //添加部门到数据库
            Department department=reqAddDeaprtInfo.toDepartment();//new Department(reqAddDeaprtInfo);
            department.setId(new JSONObject(rerult).getInt("id")+"");
            departmentMapper.insertSelective(department);
        }
    }

    @Override
    public List<RspDepartInfo> departinfo(String enid) {
        return departmentMapper.departinfo(enid);
    }
}
