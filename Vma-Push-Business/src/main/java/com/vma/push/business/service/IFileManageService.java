package com.vma.push.business.service;

import com.vma.push.business.dto.req.Page;
import com.vma.push.business.dto.req.enterprise.ReqEnterpriseFileDir;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.mini.RepRecommendCard;
import com.vma.push.business.entity.EnterpriseFile;
import com.vma.push.business.entity.EnterpriseFileDir;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author lql
 * @Description: TODO
 * @date 2019-2-19 10:46
 */
public interface IFileManageService {
	EnterpriseFileDir addFileDir(HttpServletRequest request, ReqEnterpriseFileDir reqEnterpriseFileDir);

	List<EnterpriseFileDir> getFileDirList(String enterpriseId);

	void updateFileDir(ReqEnterpriseFileDir fileDir, HttpServletRequest request);

	void deleteFileDir(HttpServletRequest request, String dirId);

	EnterpriseFile uploadFile(MultipartFile file, ReqEnterpriseFileDir reqEnterpriseFileDir) throws IOException;

	void deleteFile(String id);

	RspPage<EnterpriseFile> getFileList(Page page, String dirId);

	void updateFileDirOrder(String enterpriseId, String dirId, int isMoveUp);
}
