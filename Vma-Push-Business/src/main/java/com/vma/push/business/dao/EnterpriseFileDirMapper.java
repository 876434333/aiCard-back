package com.vma.push.business.dao;

import com.vma.push.business.entity.EnterpriseFileDir;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lql
 * @Description: TODO
 * @date 2019-2-19 10:58
 */
public interface EnterpriseFileDirMapper extends BaseDao<EnterpriseFileDir, String> {
	Integer isExistSameDir(@Param("enterpriseId") String enterpriseId, @Param("dirName") String dirName);

	List<EnterpriseFileDir> getFileDirList(String enterpriseId);
}
