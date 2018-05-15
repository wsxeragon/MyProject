package com.inphase.sparrow.base.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.inphase.sparrow.base.handler.BusinessException;

public class FileUtil {
	
	private static final Logger logger = Logger.getLogger("FileUtil");
	
	private FileUtil() {}
	
	/**
	 * 
	 * @Title upload
	 * @Description 上传保存文件
	 * @author zuoyc
	 * @date 2017年5月24日
	 * @param directory 文件保存文件夹名
	 * @param dirRole 子文件夹创建规则,0:不创建子文件夹,
	 * 1:按天创建文件,2:按月创建文件夹,3:按年创建文件夹
	 * @param files 文件对象
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public static String[] upload( String directory, int dirRole, CommonsMultipartFile... files){
		//获取request
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		//获取WEB根目录文件夹路径
		String realPath = request.getServletContext().getRealPath(directory);
		String path = getSavePath(realPath,dirRole);
		String[] fileName = new String[files.length];
		int i = 0;
		for (CommonsMultipartFile file : files) {
			FileItem item = file.getFileItem();
			if(item.getSize() <= 0)
				continue;
			String newName = generateName(item.getName());
			String savePath = path + "/" + newName; 
			try(				
				BufferedInputStream inputStream = new BufferedInputStream(item.getInputStream());
				BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(savePath)))
			){
				
				Streams.copy(inputStream, outputStream, true);
				fileName[i] = savePath.substring(savePath.indexOf(directory));
				i++;
			}
			catch (IOException e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		}
		return fileName;
	}
	/**
	 * 
	 * @Title getSavePath
	 * @Description 创建文件存储路径
	 * @author zuoyc
	 * @date 2017年5月24日
	 * @param directory
	 * @param dirRole
	 * @return
	 * @throws Exception 
	 */
	private static String getSavePath(String directory,int dirRole){
		String childDir;
		if(0 == dirRole) {
			childDir = "";
		} else if (1 == dirRole) {
			//按天创建文件夹
			childDir = DateUtils.getDate("yyyyMMdd");
		} else if (2 == dirRole) {
			//按月创建文件夹
			childDir = DateUtils.getDate("yyyyMM");
		} else if (3 == dirRole) {
			childDir = DateUtils.getDate("yyyy");
		} else {
			throw new BusinessException(500000,"不支持的创建类型");
		}
		String newDirectory = directory +"/"+childDir;
		File file = new File(newDirectory);
		if(!file.exists()||!file.isDirectory())
			file.mkdirs();
		return newDirectory;
	}
	
	/**
	 * 
	 * @Title generateName
	 * @Description 获取随机文件名
	 * @author zuoyc
	 * @date 2017年5月24日
	 * @param fileName
	 * @return
	 */
	private static String generateName(String fileName) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(DateUtils.getDate("yyyyMMddHHmmss"))
		.append(CommonUtils.getRandom(4))
		.append(fileName.substring(fileName.lastIndexOf('.')));
		return buffer.toString();
	}
}
