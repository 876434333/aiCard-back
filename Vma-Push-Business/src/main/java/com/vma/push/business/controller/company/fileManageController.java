package com.vma.push.business.controller.company;

import com.vma.push.business.dto.req.Page;
import com.vma.push.business.dto.req.enterprise.ReqEnterpriseFileDir;
import com.vma.push.business.dto.req.enterprise.ReqUpdateFileDirOrder;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.entity.EnterpriseFile;
import com.vma.push.business.entity.EnterpriseFileDir;
import com.vma.push.business.service.IFileManageService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author lql
 * @Description: TODO
 * @date 2019-2-19 10:03
 */
@RestController(value = "fileManageController")
@RequestMapping("/V1.0")
@Api(value = "企业后台", description = "文件管理", tags = {"fileManageController"})
public class fileManageController {
	@Autowired
	IFileManageService fileManageService;


	/**
	 * 增加目录
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "新增目录", notes = "新增目录")
	@RequestMapping(value = "/company/file/addFileDir", method = RequestMethod.POST)
	public EnterpriseFileDir addFileDir(@RequestBody ReqEnterpriseFileDir reqEnterpriseFileDir, HttpServletRequest request) {
		return fileManageService.addFileDir(request, reqEnterpriseFileDir);
	}

	/**
	 * 获取企业的所有目录
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "获取企业目录列表", notes = "获取企业目录列表")
	@RequestMapping(value = "/company/file/getFileDirList", method = RequestMethod.GET)
	public List<EnterpriseFileDir> getFileDirList(HttpServletRequest request) {
		String enterpriseId = UserDataUtil.getCustomId(request);
		return fileManageService.getFileDirList(enterpriseId);
	}

	/**
	 * 修改目录名字
	 *
	 * @param reqEnterpriseFileDir
	 * @param request
	 */
	@ApiOperation(value = "更新目录名字", notes = "更新目录名字")
	@RequestMapping(value = "/company/file/updateFileDir", method = RequestMethod.POST)
	public void updateFileDir(@RequestBody ReqEnterpriseFileDir reqEnterpriseFileDir, HttpServletRequest request) {
		fileManageService.updateFileDir(reqEnterpriseFileDir, request);
	}

	/**
	 * 删除目录
	 *
	 * @param id
	 * @param request
	 */
	@ApiOperation(value = "删除目录", notes = "删除目录")
	@RequestMapping(value = "/company/file/deleteFileDir/{id}", method = RequestMethod.GET)
	public void deleteFileDir(@PathVariable("id") String id, HttpServletRequest request) {
		fileManageService.deleteFileDir(request, id);
	}

	/**
	 * 企业管理上传文件
	 *
	 * @param file
	 * @param dirId
	 * @return
	 * @throws IOException
	 */
	@ApiOperation(value = "上传文件", notes = "上传文件")
	@RequestMapping(value = "/company/file/upload", method = RequestMethod.POST)
	public EnterpriseFile upload(@RequestParam("file") MultipartFile file, @RequestParam("dirId") String dirId) throws IOException {
		ReqEnterpriseFileDir reqEnterpriseFileDir = new ReqEnterpriseFileDir();
		reqEnterpriseFileDir.setId(dirId);
		return fileManageService.uploadFile(file, reqEnterpriseFileDir);
	}

	/**
	 * 删除文件
	 *
	 * @param id
	 */
	@ApiOperation(value = "删除文件", notes = "删除文件")
	@RequestMapping(value = "/company/file/deleteFile", method = RequestMethod.GET)
	public void deleteFile(@RequestParam("id") String id) {
		fileManageService.deleteFile(id);
	}

	/**
	 * 获取文件列表
	 */
	@ApiOperation(value = "获取文件列表", notes = "获取文件列表")
	@RequestMapping(value = "/company/file/getFileList", method = RequestMethod.POST)
	public RspPage<EnterpriseFile> getFileList(@RequestParam("dir_id") String dir_id, @RequestBody Page page) {
		return fileManageService.getFileList(page, dir_id);
	}

	/**
	 * 获取文件列表
	 */
	@ApiOperation(value = "修改文件夹排序", notes = "修改文件夹排序")
	@RequestMapping(value = "/company/file/updateDirOrder", method = RequestMethod.POST)
	public void updateDirOrder(@RequestBody ReqUpdateFileDirOrder dirOrder, HttpServletRequest request) {
		String enterpriseId = UserDataUtil.getCustomId(request);
//		String enterpriseId ="2b4a629c-d45f-4b2b-ab69-fa8354c765e4";
		fileManageService.updateFileDirOrder(enterpriseId, dirOrder.getId(), dirOrder.getIs_move_up());
	}
}
