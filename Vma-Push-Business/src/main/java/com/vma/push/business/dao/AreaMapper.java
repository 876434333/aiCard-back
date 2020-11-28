package com.vma.push.business.dao;

import com.vma.push.business.dto.rsp.area.AreaInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by chenzui on 2018/6/14.
 */
public interface AreaMapper {
    public List<AreaInfo> selectProvince();

    public List<AreaInfo> selectCity(@Param("code") String code);

    public List<AreaInfo> selectArea(@Param("code") String code);
}
