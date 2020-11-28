package com.vma.push.business.controller.common;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dao.SysConfigMapper;
import com.vma.push.business.dto.req.img.ReqImgUpload;
import com.vma.push.business.entity.SysConfig;
import com.vma.push.business.util.ImgUploadUtil;
import com.vma.push.business.util.QiniuUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenzui on 2017/3/28.
 */

@RestController
@RequestMapping("/v1.0")
@Api(value = "fileController", description = "文件上传", tags = {"FileController"})
public class FileController extends ControllerExceptionHandler {
	private Logger LOG = Logger.getLogger(this.getClass());


	private static final String FILE_SEPARATOR = "/";

	@Autowired
	private QiniuUtils qiniuUtils;

	@Autowired
	private SysConfigMapper sysConfigMapper;

	/**
	 * upload 图片上传方法
	 *
	 * @throws IOException
	 */
	@ApiOperation(value = "文件上传", notes = "文件上传")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Map upload(@RequestParam("editormd-image-file") MultipartFile multipartFile) throws IOException {
		//根据时间生成文件夹目录
		if (multipartFile.isEmpty() || StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
			throw new BusinessException(ErrCode.PARAM_NOT_RIGHT, "");
		}
		String contentType = multipartFile.getContentType();
		if (!contentType.contains("")) {
			throw new BusinessException(ErrCode.IMG_FORMAT_ERROR, "");
		}
		String rootFileName = multipartFile.getOriginalFilename();
		LOG.info("上传图片:name={" + rootFileName + "},type={" + contentType + "}");

		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);

		String upToken = qiniuUtils.getQiNiuToken(sysConfig.getQiniuBucket());

		//M by PLH at 2018-10-23 for 深卡名片的服务器路径被修改为/u01/app/deecard了
//        String path = "/Users/chenzui/my-study/";
		//String path = "/alidata01/chenz/tmp";
		String path = "/u01/app/deecard/tmp";
		File dest = new File(path + "/" + rootFileName.substring(0, rootFileName.lastIndexOf(".")) + ".png");
		if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
			//dest.getParentFile().mkdir();
			dest.getParentFile().mkdirs();
		}
		//multipartFile.transferTo(dest);
		multipartFile.transferTo(dest.getCanonicalFile());

//         如果文件路径所对应的文件存在，并且是一个文件，则直接删除

		String res = qiniuUtils.upFileToQiNiuZone(upToken, dest.getAbsolutePath(), ".png");
		dest.delete();
		Map result = new HashMap();
		result.put("url", sysConfig.getQiniuUrl() + FILE_SEPARATOR + res);
		return result;
	}

	/**
	 * upload 图片上传方法
	 *
	 * @throws IOException
	 */
	@ApiOperation(value = "上传文件 图片/音频/视频等", notes = "上传文件 图片/音频/视频等")
	@RequestMapping(value = "/upload_files", method = RequestMethod.POST)
	public Map uploadmp3(@RequestParam("editormd-image-file") MultipartFile multipartFile) throws IOException {
		//根据时间生成文件夹目录
		if (multipartFile.isEmpty() || StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
			throw new BusinessException(ErrCode.PARAM_NOT_RIGHT, "");
		}
		String contentType = multipartFile.getContentType();
		if (!contentType.contains("")) {
			throw new BusinessException(ErrCode.IMG_FORMAT_ERROR, "");
		}
		String rootFileName = multipartFile.getOriginalFilename();
		LOG.info("上传文件:name={" + rootFileName + "},type={" + contentType + "}");
		System.out.println(rootFileName);
		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);

		String upToken = qiniuUtils.getQiNiuToken(sysConfig.getQiniuBucket());

		//M by PLH at 2018-10-23 for 深卡名片的服务器路径被修改为/u01/app/deecard了
//        String path = "/Users/chenzui/my-study/";
		//String path = "/alidata01/home/chenz/tmp";
		String path = "/u01/app/deecard/tmp";
		File dest = new File(path + "/" + rootFileName);
		if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
			//dest.getParentFile().mkdir();
			dest.getParentFile().mkdirs();
		}
		//multipartFile.transferTo(dest);
		multipartFile.transferTo(dest.getCanonicalFile());

//         如果文件路径所对应的文件存在，并且是一个文件，则直接删除

		String res = qiniuUtils.upFileToQiNiuZone(upToken, dest.getAbsolutePath(), dest.getName().substring(dest.getName().lastIndexOf(".")));
		dest.delete();
		Map result = new HashMap();
		result.put("url", sysConfig.getQiniuUrl() + FILE_SEPARATOR + res);
		return result;
	}

	@ApiOperation(value = "文件上传", notes = "文件上传")
	@RequestMapping(value = "/uploads", method = RequestMethod.POST)
	public Map uploads(@RequestParam("editormd-image-file") MultipartFile[] multipartFiles) throws IOException {
		MultipartFile multipartFile = null;
		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);

		for (int i = 0; i < multipartFiles.length; i++) {
			//根据时间生成文件夹目录
			if (multipartFile.isEmpty() || StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
				throw new BusinessException(ErrCode.PARAM_NOT_RIGHT, "");
			}
			String contentType = multipartFile.getContentType();
			if (!contentType.contains("")) {
				throw new BusinessException(ErrCode.IMG_FORMAT_ERROR, "");
			}
			String rootFileName = multipartFile.getOriginalFilename();
			LOG.info("上传图片:name={" + rootFileName + "},type={" + contentType + "}");

			String upToken = qiniuUtils.getQiNiuToken(sysConfig.getQiniuBucket());

			//M by PLH at 2018-10-23 for 深卡名片的服务器路径被修改为/u01/app/deecard了
//        String path = "/Users/chenzui/my-study/";
			//String path = "/alidata01/chenz/tmp";
			String path = "/u01/app/deecard/tmp";
			File dest = new File(path + "/" + rootFileName);
			if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
				dest.getParentFile().mkdir();
			}
			multipartFile.transferTo(dest);
//         如果文件路径所对应的文件存在，并且是一个文件，则直接删除

			String res = qiniuUtils.upFileToQiNiuZone(upToken, dest.getAbsolutePath(), rootFileName.substring(rootFileName.lastIndexOf(".")));
			dest.delete();
			Map result = new HashMap();
			result.put("url", sysConfig.getQiniuUrl() + FILE_SEPARATOR + res);
		}

		return null;


	}

	@ApiOperation(value = "base64图片上传", notes = "base64图片上传")
	@RequestMapping(value = "/file/base64", method = RequestMethod.POST)
	@ApiImplicitParam(value = "图片参数", name = "reqImgUpload", required = true, dataType = "ReqImgUpload")
	public Map upload(@RequestBody ReqImgUpload reqImgUpload) throws Exception {
		//M by PLH at 2018-10-23 for 深卡名片的服务器路径被修改为/u01/app/deecard了
		//String path = "/alidata01/chenz/tmp";
		String path = "/u01/app/deecard/tmp";
		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);

		String savePath = String.valueOf(System.currentTimeMillis()) + ".jpg";
		//M by PLH at 2018-10-23 for 深卡名片的服务器路径被修改为/u01/app/deecard了
		//ImgUploadUtil.generateImage(reqImgUpload.getImgdata(), "/alidata01/chenz/tmp/" + savePath);
		ImgUploadUtil.generateImage(reqImgUpload.getImgdata(), "/u01/app/deecard/tmp/" + savePath);
		//File dest = new File("/alidata01/chenz/tmp/" + savePath);
		File dest = new File("/u01/app/deecard/tmp/" + savePath);
		if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
			//dest.getParentFile().mkdir();
			dest.getParentFile().mkdirs();
		}

//        ImgUploadUtil.generateImage(reqImgUpload.getImgdata(), "/Users/chenzui/Downloads/" + savePath);
//        File dest = new File("/Users/chenzui/Downloads/" + savePath);
		String upToken = qiniuUtils.getQiNiuToken(sysConfig.getQiniuBucket());
		String res = qiniuUtils.upFileToQiNiuZone(upToken, dest.getAbsolutePath(), ".jpg");
		dest.delete();
		Map result = new HashMap();
		result.put("url", sysConfig.getQiniuUrl() + FILE_SEPARATOR + res);
		return result;

	}

	@ApiOperation(value = "将web图片转存到七牛", notes = "将web图片转存到七牛")
	@RequestMapping(value = "/web_to_qiniu", method = RequestMethod.POST)
	public String web2qiniu(String weburl) throws IOException {
		String newurl = qiniuUtils.newUlr(weburl);
		return newurl;
	}


	public static void main(String[] args) {
		String path = "1D40A64D-1C3B-4250-AB5C-8B35F2C572CB.png";

		System.out.println(path.substring(0, path.lastIndexOf(".")));

	}

	/**
	 * upload 富文本文件上传
	 *
	 * @throws IOException
	 */
	@ApiOperation(value = "富文本文件上传", notes = "富文本文件上传")
	@RequestMapping(value = "/ckeditorUpload", method = RequestMethod.POST)
	public void ckeditorUpload(MultipartHttpServletRequest multipartRequest,
							   HttpServletResponse resp,
							   @RequestParam("CKEditorFuncNum") String callback,
							   @RequestParam(value = "backUrl", required = false) String backUrl) throws IOException {
		MultipartFile multipartFile = multipartRequest.getFile("upload");
		//根据时间生成文件夹目录
		if (multipartFile.isEmpty() || StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
			throw new BusinessException(ErrCode.PARAM_NOT_RIGHT, "");
		}
		String contentType = multipartFile.getContentType();
		if (!contentType.contains("")) {
			throw new BusinessException(ErrCode.IMG_FORMAT_ERROR, "");
		}
		String rootFileName = multipartFile.getOriginalFilename();
		LOG.info("上传图片:name={" + rootFileName + "},type={" + contentType + "}");


		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);
		String upToken = qiniuUtils.getQiNiuToken(sysConfig.getQiniuBucket());

		//M by PLH at 2018-10-23 for 深卡名片的服务器路径被修改为/u01/app/deecard了
//        String path = "/Users/chenzui/my-study/";
		//String path = "/alidata01/home/chenz/tmp";
		String path = "/u01/app/deecard/tmp";
		File dest = new File(path + "/" + rootFileName.substring(0, rootFileName.lastIndexOf(".")) + ".png");
		if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
			//dest.getParentFile().mkdir();
			dest.getParentFile().mkdirs();
		}
		//multipartFile.transferTo(dest);
		multipartFile.transferTo(dest.getCanonicalFile());

//         如果文件路径所对应的文件存在，并且是一个文件，则直接删除

		String res = qiniuUtils.upFileToQiNiuZone(upToken, dest.getAbsolutePath(), ".png");
		dest.delete();
		Map result = new HashMap();
		result.put("url", sysConfig.getQiniuUrl() + FILE_SEPARATOR + res);
		PrintWriter out = resp.getWriter();
		if (StringUtils.isEmpty(backUrl)) {
			resp.setContentType("text/html; charset=utf-8");
			out.println("<script type=\"text/javascript\">");
			out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + result.get("url") + "','')'");
			out.println("</script>");
		} else {
			if (backUrl.indexOf("?") == -1) {
				backUrl += "?";
			} else {
				backUrl += "&";
			}
			resp.sendRedirect(backUrl + "imgUrl=" + result.get("url") + "&callback=" + callback);
		}


	}

}
