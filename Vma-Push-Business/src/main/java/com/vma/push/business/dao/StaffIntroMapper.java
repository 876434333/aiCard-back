package com.vma.push.business.dao;

import com.vma.push.business.entity.StaffIntro;

import java.util.List;

public interface StaffIntroMapper extends BaseDao<StaffIntro,String> {


    List<StaffIntro> findStaffIntro(String staff_id);

}
