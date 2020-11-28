package com.vma.push.business.service.impl;

import com.vma.push.business.dao.BaseDao;
import com.vma.push.business.dao.LabelInfoMapper;
import com.vma.push.business.dto.rsp.userInfo.RspUserLabelInfo;
import com.vma.push.business.entity.LabelInfo;
import com.vma.push.business.service.ILabelInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenzui on 2018/5/14.
 */
@Service
public class LabelInfoServiceImpl extends AbstractServiceImpl<LabelInfo,String> implements ILabelInfoService {

    private LabelInfoMapper mapper;

    public LabelInfoServiceImpl(LabelInfoMapper mapper) {
        super(mapper);
        this.mapper = mapper;
    }

}
