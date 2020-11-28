package com.vma.push.business.service;

import com.vma.push.business.dto.rsp.RspEnterpriseHistoryNumber;
import com.vma.push.business.dto.rsp.RspEnterpriseNumber;
import com.vma.push.business.dto.rsp.RspMiniOem;
import com.vma.push.business.dto.rsp.RspPointDetail;
import com.vma.push.business.entity.Enterprise;

import java.util.List;

/**
 * Created by JINzm
 * 2018/6/14
 */
public interface IEnterpriseService extends IBaseService<Enterprise,String> {
    /**
     *
     * @param id 企业ID
     * @return
     */
    RspPointDetail pointDetail(String id);
    /**
     * 查询当前企业下的代理商与企业数量
     * @param roleId 企业角色
     * @return
     */
    RspEnterpriseNumber getEnterpriseNumber(Integer roleId, String id);

    /**
     * 查询当前企业下的历史企业数量
     * @param roleId 企业角色
     * @return
     */
    RspEnterpriseHistoryNumber getEnterpriseHistoryNumber(Integer roleId, String id);

    void enterRela(String enid);

    void deptRela(String enid, String dept);

    RspMiniOem oemInfo(String id);

    String findCropId(String enterpriseId);

    List<Enterprise> getEnterpriseListByWxId(String wxId);
    // 修改企业人数
    void updataEnterpriseStaffNum(String enterpriseId, int num);
}
