package com.vma.push.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dao.AdminMapper;
import com.vma.push.business.dao.EnterpriseFileDirMapper;
import com.vma.push.business.dao.EnterpriseFileMapper;
import com.vma.push.business.dao.SysConfigMapper;
import com.vma.push.business.dto.req.Page;
import com.vma.push.business.dto.req.enterprise.ReqEnterpriseFileDir;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.entity.EnterpriseFile;
import com.vma.push.business.entity.EnterpriseFileDir;
import com.vma.push.business.entity.SysConfig;
import com.vma.push.business.service.IFileManageService;
import com.vma.push.business.util.QiniuUtils;
import com.vma.push.business.util.UserDataUtil;
import com.vma.push.business.util.UuidUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lql
 * @Description: TODO
 * @date 2019-2-19 10:47
 */
@Service
public class FileManageServiceImpl implements IFileManageService {
	@Autowired
	EnterpriseFileDirMapper fileDirMapper;
	@Autowired
	EnterpriseFileMapper fileMapper;

	@Autowired
	private QiniuUtils qiniuUtils;

	@Autowired
	private SysConfigMapper sysConfigMapper;
	private Logger LOG = Logger.getLogger(this.getClass());

	@Override
	public EnterpriseFileDir addFileDir(HttpServletRequest request, ReqEnterpriseFileDir reqEnterpriseFileDir) {
		String dirName = reqEnterpriseFileDir.getDir_name();
		String dirPassword = reqEnterpriseFileDir.getDir_password();
		if (StringUtils.isEmpty(dirPassword) == false) {
			// 非空,验证是否为6位数字
			if (!dirPassword.matches("[0-9]{6}")) {
				throw new BusinessException(-1, "密码只能为6位数字");
			}
		}
		if (StringUtils.isEmpty(dirName)) {
			throw new BusinessException(-1, "目录名不能为空");
		}
		String enterpriseId = UserDataUtil.getCustomId(request);
		Integer isExist = fileDirMapper.isExistSameDir(enterpriseId, dirName);
		if (isExist == 1) {
			throw new BusinessException(-1, "存在同名目录");
		}
		EnterpriseFileDir fileDir = new EnterpriseFileDir();
		fileDir.setId(UuidUtil.getRandomUuidWithoutSeparator());
		fileDir.setDirName(dirName);
		fileDir.setEnterpriseId(enterpriseId);
		fileDir.setCreateTime(new Date());
		fileDir.setDirPassword(dirPassword);
		fileDirMapper.insertSelective(fileDir);
		return fileDir;
	}

	@Override
	public List<EnterpriseFileDir> getFileDirList(String enterpriseId) {
		return fileDirMapper.getFileDirList(enterpriseId);
	}

	@Override
	public void updateFileDir(ReqEnterpriseFileDir fileDir, HttpServletRequest request) {
		if (StringUtils.isEmpty(fileDir.getDir_name())) {
			throw new BusinessException(-1, "目录名不能为空");
		}
		String dirPassword = fileDir.getDir_password();
		if (StringUtils.isEmpty(dirPassword) == false) {
			// 非空,验证是否为6位数字
			if (!dirPassword.matches("[0-9]{6}")) {
				throw new BusinessException(-1, "密码只能为6位数字");
			}
		}
		EnterpriseFileDir enterpriseFileDir = fileDirMapper.selectByPrimaryKey(fileDir.getId());
		if (!fileDir.getDir_name().equals(enterpriseFileDir.getDirName())) {
			// 名字不相同，判断是否存在同名目录
			String enterpriseId = UserDataUtil.getCustomId(request);
			Integer isExist = fileDirMapper.isExistSameDir(enterpriseId, fileDir.getDir_name());
			if (isExist == 1) {
				throw new BusinessException(-1, "存在同名目录");
			}
		}

		EnterpriseFileDir newFileDir = new EnterpriseFileDir();
		newFileDir.setDirName(fileDir.getDir_name());
		newFileDir.setId(fileDir.getId());
		newFileDir.setDirPassword(dirPassword);
		fileDirMapper.updateByPrimaryKeySelective(newFileDir);
	}

	@Override
	public void deleteFileDir(HttpServletRequest request, String dirId) {
		Integer isExist = fileMapper.isExistDirFile(dirId);
		if (isExist == 1) {
			throw new BusinessException(-1, "该目录存在文件,不允许删除");
		}
		fileDirMapper.deleteByPrimaryKey(dirId);
	}

	@Override
	public EnterpriseFile uploadFile(MultipartFile file, ReqEnterpriseFileDir reqEnterpriseFileDir) throws IOException {
		String FILE_SEPARATOR = "/";
		//根据时间生成文件夹目录
		if (file.isEmpty() || StringUtils.isEmpty(file.getOriginalFilename())) {
			throw new BusinessException(ErrCode.PARAM_NOT_RIGHT, "");
		}
		String contentType = file.getContentType();
		if (!contentType.contains("")) {
			throw new BusinessException(ErrCode.IMG_FORMAT_ERROR, "");
		}
		if (file.getSize() > 2 * 1024 * 1024) {
			// 文件大于2M 不让上传
			throw new BusinessException(-1, "只允许上传小于2M的文件");
		}
		String rootFileName = file.getOriginalFilename();
		String fileType = rootFileName.substring(rootFileName.lastIndexOf("."));
		String[] allowFileType = {".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".pdf"};
		Map tep = new HashMap<String, Integer>();
		tep.put(allowFileType[0], 1);
		tep.put(allowFileType[1], 1);
		tep.put(allowFileType[2], 1);
		tep.put(allowFileType[3], 1);
		tep.put(allowFileType[4], 1);
		tep.put(allowFileType[5], 1);
		tep.put(allowFileType[6], 1);
		if (tep.get(fileType) == null) {
			throw new BusinessException(-1, "只允许上传doc、xls、ppt、pdf格式");
		}

		String fileSize = String.valueOf(file.getSize());
		LOG.info("上传文件:name={" + rootFileName + "},type={" + fileType + "}");

		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);

		String upToken = qiniuUtils.getQiNiuToken(sysConfig.getQiniuBucket());

		String path = "/u01/app/deecard/tmp";
		File dest = new File(path + "/" + rootFileName);
		if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
			dest.getParentFile().mkdirs();
		}
		file.transferTo(dest.getCanonicalFile());
		String res = qiniuUtils.upFileToQiNiuZone(upToken, dest.getAbsolutePath(), fileType);
		dest.delete();

		EnterpriseFile fileValue = new EnterpriseFile();
		fileValue.setFileUrl(sysConfig.getQiniuUrl() + FILE_SEPARATOR + res);
		fileValue.setCreateTime(new Date());
		fileValue.setId(UuidUtil.getRandomUuid());
		fileValue.setFileSize(fileSize);
		fileValue.setDirId(reqEnterpriseFileDir.getId());
		fileValue.setFileName(rootFileName);
		fileMapper.insert(fileValue);
		return fileValue;
	}

	@Override
	public void deleteFile(String id) {
		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);
		EnterpriseFile enterpriseFile = fileMapper.selectByPrimaryKey(id);
		String fileName = enterpriseFile.getFileUrl().replace(sysConfig.getQiniuUrl() + "/", "");
		Boolean isSuccess = qiniuUtils.deteteBucketFile(fileName);
		if (isSuccess) {
			fileMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public RspPage<EnterpriseFile> getFileList(Page page, String dirId) {
		RspPage<EnterpriseFile> repPage = new RspPage<EnterpriseFile>();
		PageHelper.startPage(page.getPage_num(), page.getPage_size(), true);
		List<EnterpriseFile> list = fileMapper.getFileList(dirId);
		PageInfo<EnterpriseFile> gitPage = new PageInfo<>(list);
		repPage.setData_list(list);
		repPage.setPage_num(gitPage.getPageNum());
		repPage.setPage_size(gitPage.getPageSize());
		repPage.setTotal_num(gitPage.getTotal());
		repPage.setHaveNextPage(gitPage.isHasNextPage());
		return repPage;
	}

	@Override
	public void updateFileDirOrder(String enterpriseId, String dirId, int isMoveUp) {
		List<EnterpriseFileDir> dirList = this.getFileDirList(enterpriseId);
		int order = 1;
		for (EnterpriseFileDir dir : dirList) {
			dir.setShowOrder(order);
			fileDirMapper.updateByPrimaryKeySelective(dir);
			order++;
		}
		EnterpriseFileDir oldDir1 = null; // 拍在需要修改目录的前一条目录
		EnterpriseFileDir oldDir2 = null; // 需要修改移动的目录
		EnterpriseFileDir oldDir3 = null; // 拍在需要修改目录的后一条目录
		for (int i = 0; i < dirList.size(); i++) {
			if (dirList.get(i).getId().equals(dirId)) {
				if (isMoveUp == 1 && i == 0) {
					// 已经是第一位了，不需要上移
					throw new BusinessException(-1,"已经是第一位了，不需要上移");
				}
				if (isMoveUp == 0 && i == dirList.size() - 1) {
					// 已经是最后一位了，不需要下移
					throw new BusinessException(-1,"已经是最后一位了，不需要下移");
				}
				if (isMoveUp == 1) {
					oldDir1 = dirList.get(i - 1);
				} else {
					oldDir3 = dirList.get(i + 1);
				}
				oldDir2 = dirList.get(i);
				break;
			}
		}
		if (isMoveUp == 1) {
			// 上移 只需将两个的showOrder换个位置
			oldDir2.setShowOrder(oldDir2.getShowOrder() - 1);
			oldDir1.setShowOrder(oldDir1.getShowOrder() + 1);
			fileDirMapper.updateByPrimaryKey(oldDir1);
		} else {
			// 下移 只需将两个的showOrder换个位置
			oldDir2.setShowOrder(oldDir2.getShowOrder() + 1);
			oldDir3.setShowOrder(oldDir3.getShowOrder() - 1);
			fileDirMapper.updateByPrimaryKey(oldDir3);
		}
		fileDirMapper.updateByPrimaryKey(oldDir2);
	}
}
