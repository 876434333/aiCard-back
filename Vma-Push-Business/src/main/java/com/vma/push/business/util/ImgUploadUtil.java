package com.vma.push.business.util;

import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class ImgUploadUtil {
	/**
	 * 根据时间生成文件夹目录
	 * @return
	 */
	public static String getPath(String fileType) {
		// 生成uid
		Date nowTime = new Date(System.currentTimeMillis());
		SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String retStrFormatNowDate = sdFormatter.format(nowTime);
		Calendar calendar = Calendar.getInstance();
		// 显示年份
		int year = calendar.get(Calendar.YEAR);
		// System.out.println("显示年份==>YEAR is = " + String.valueOf(year));
		// 显示月份 (从0开始, 实际显示要加一)
		int MONTH = calendar.get(Calendar.MONTH);
		int trueMONTH = MONTH + 1;
		// System.out.println("显示月份==>MONTH is = " + (MONTH + 1));

		File file1 = null;
		File file2 = null;
		String savePath = "img";
		if(fileType != null && !"".equals(fileType)){
			savePath = fileType;
		}
		String outputFile1 = savePath + "/" + year;
		String outputFile2 = savePath + "/" + year + File.separator + trueMONTH;
		File picPath = new File(savePath);
		if (!picPath.exists()) {
			picPath.mkdirs();
		}
		file1 = new File(outputFile1);
		if (!file1.exists()) {
			file1.mkdir();
		}
		file2 = new File(outputFile2);
		if (!file2.exists()) {
			file2.mkdir();
		}
		return outputFile2;
	}

	public static boolean generateImage(String imgStr,String imgFile)throws Exception {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) { // 图像数据为空
			return false;

		} else {
			BASE64Decoder decoder = new BASE64Decoder();

			// Base64解码
//			String t = imgStr.replace("data:image/jpeg;base64,", "");
			String t = imgStr;


			byte[] b = decoder.decodeBuffer(t);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(imgFile);
			out.write(b);
			out.flush();
			out.close();
			return true;

		}
	}

	public static String generateWord() {
		String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7",
				"8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };
		List<String> list = Arrays.asList(beforeShuffle);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		String result = afterShuffle.substring(5, 15);
		return result;
	}
}
