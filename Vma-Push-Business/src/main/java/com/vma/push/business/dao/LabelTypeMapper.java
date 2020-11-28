package com.vma.push.business.dao;

import com.vma.push.business.dto.rsp.userInfo.RspLabelType;
import com.vma.push.business.entity.LabelType;

import java.util.List;

/**
 * Created by chenzui on 2018/5/14.
 */
public interface LabelTypeMapper extends BaseDao<LabelType,String> {
    public List<RspLabelType> queryAll();
}
