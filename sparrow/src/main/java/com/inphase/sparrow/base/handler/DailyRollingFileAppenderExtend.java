package com.inphase.sparrow.base.handler;

import com.inphase.sparrow.base.util.CommonUtils;
import com.inphase.sparrow.base.util.DateUtils;

import java.io.IOException;
import java.util.Calendar;
import org.apache.log4j.DailyRollingFileAppender;
import org.springframework.util.StringUtils;



/**      
 * @Description:对log4j进行扩展，支持文件夹按条件自动生成.
 * @author: sunchao
 */
public class DailyRollingFileAppenderExtend extends DailyRollingFileAppender {

	static final String DIRECTORY_EXTEND = "log4j.DirectoryPattern";

	@Override
	public void setFile(String file) {
		String newPath = getPath(file,1);
		super.setFile(newPath);
	}

	@Override
	public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize)
			throws IOException {
		String newPath = getPath(fileName, 2);
		super.setFile(newPath, append, bufferedIO, bufferSize);
	}

	/**
	 * @Description 根据自定义配置按年生成文件夹
	 * @return
	 */
	private String getPath(String oldPath, int type){
		String directoryPattern = CommonUtils.getProperty(DIRECTORY_EXTEND, "log4j.properties");
		String path = "";
		if(!StringUtils.isEmpty(directoryPattern)){
			if(directoryPattern.contains("/yyyy/")){
				path += directoryPattern.replace("yyyy", String.valueOf(DateUtils.getTimeUnit(Calendar.YEAR)));
			}
			if(type==1){
				path += oldPath;
			}else{
				path += oldPath.substring(directoryPattern.length(), oldPath.length());
			}
		}else{
			path = oldPath;
		}
		return path;
	}
	
	
}
