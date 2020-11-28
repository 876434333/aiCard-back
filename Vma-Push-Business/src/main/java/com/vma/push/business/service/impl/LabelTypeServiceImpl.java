package com.vma.push.business.service.impl;

import com.vma.push.business.dao.LabelTypeMapper;
import com.vma.push.business.entity.LabelType;
import com.vma.push.business.service.ILabelTypeService;
import org.springframework.stereotype.Service;

/**
 * Created by chenzui on 2018/5/14.
 */
@Service
public class LabelTypeServiceImpl extends AbstractServiceImpl<LabelType,String> implements ILabelTypeService {

    private LabelTypeMapper mapper;

    public LabelTypeServiceImpl(LabelTypeMapper mapper) {
        super(mapper);
        this.mapper = mapper;
    }
}
