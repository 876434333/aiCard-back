package com.vma.push.business.dao;

import com.vma.push.business.entity.EnterpriseFile;

import java.util.List;

/**
 * @author lql
 * @Description: TODO
 * @date 2019-2-19 10:48
 */
public interface EnterpriseFileMapper extends BaseDao<EnterpriseFile, String> {
	Integer isExistDirFile(String dirId);

	List<EnterpriseFile> getFileList(String dirId);

}
