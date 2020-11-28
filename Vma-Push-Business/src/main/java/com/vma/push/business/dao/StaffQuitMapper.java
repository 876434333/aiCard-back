package com.vma.push.business.dao;

import com.vma.push.business.entity.StaffQuit;

/**
 * @author lql
 * @Description: TODO
 * @date 2018-11-2 9:51
 */
public interface StaffQuitMapper extends BaseDao<StaffQuit,String> {
	String getRecevierStaffId(String staffId);

}
