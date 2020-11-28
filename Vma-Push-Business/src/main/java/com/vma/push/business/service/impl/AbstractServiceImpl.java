package com.vma.push.business.service.impl;

import com.vma.push.business.dao.BaseDao;
import com.vma.push.business.service.IBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象服务实现类
 *
 * @param <T>
 * @param <PK>
 * @author Xuerong Xue
 */
public class AbstractServiceImpl<T, PK> implements IBaseService<T, PK> {
    protected Logger LOG = LoggerFactory.getLogger(AbstractServiceImpl.class);
    private BaseDao<T, PK> base;

    public AbstractServiceImpl(BaseDao<T, PK> base) {
        this.base = base;
    }

    @Override
    public int deleteByPrimaryKey(PK id) {
        return base.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T record) {
        return base.insert(record);
    }

    @Override
    public int insertSelective(T record) {
        return base.insertSelective(record);
    }

    @Override
    public T selectByPrimaryKey(PK id) {
        return base.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        return base.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return base.updateByPrimaryKey(record);
    }
}
