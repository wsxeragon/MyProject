package com.inphase.sparrow.service.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inphase.sparrow.base.util.CommonUtils;
import com.inphase.sparrow.base.util.DateUtils;
import com.inphase.sparrow.dao.system.LogDao;
import com.inphase.sparrow.entity.TableMessage;
import com.inphase.sparrow.entity.TableParam;
import com.inphase.sparrow.entity.system.SystemLog;

/**      
 * @Description:系统操作日志以及文件日志操作
 * @author: sunchao
 */
@Service
public class LogService {

	private static Logger logger = Logger.getLogger(LogService.class);

	@Autowired
	private LogDao logDao;

	public TableMessage<List<SystemLog>> listLog(TableParam tableParam) {
		return logDao.listLog(tableParam);
	}

	/**
	 * @Description 获取文本日志
	 * @param 1：普通信息日志；2：自定义日志；3：全局异常日志；
	 * @param date
	 * @return
	 * @throws Exception 
	 */
	public String getTextLog(int type, String date) {
		String logRootPath = CommonUtils.getProperty("log4j.DirectoryPattern", "log4j.properties");
		logRootPath = logRootPath.replace("yyyy", String.valueOf(DateUtils.getTimeUnit(Calendar.YEAR)));
		String filePath = getValueByType(type);
		if (StringUtils.isEmpty(date) || date.equals(DateUtils.getDate("yyyy-MM-dd"))) {
			logRootPath += filePath;
		} else {
			logRootPath += filePath + "_" + date;
		}
		File file = new File(logRootPath);
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
				sb.append("\r\n");
			}
			br.close();
		} catch (IOException ex) {
			logger.error(ExceptionUtils.getFullStackTrace(ex));
		}
		return sb.toString();
	}

	/**
	 * @Description 根据type获取不同类型日志根目录
	 * @param type
	 * @return
	 */
	private String getValueByType(int type) {
		String value;
		if (type == 1) {
			value = CommonUtils.getProperty("log4j.appender.infofile.File", "log4j.properties");
		} else if (type == 2) {
			value = CommonUtils.getProperty("log4j.appender.errorfile.File", "log4j.properties");
		} else {
			value = CommonUtils.getProperty("log4j.appender.globeinfo.File", "log4j.properties");
		}
		return value;
	}

	/**
	 * @Description 获取给定目录下的最新文件
	 * @param path
	 * @return
	 */
	public File getLastFile(String path) {
		File file = new File(path);
		File[] files = file.listFiles();
		List<File> fileList = new ArrayList<File>();
		for (File f : files) {
			fileList.add(f);
		}
		Collections.sort(fileList, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				if (o1.lastModified() > o2.lastModified())
					return -1;
				if (o1.lastModified() < o2.lastModified())
					return 1;
				return 0;
			}
		});
		return fileList.get(0);
	}

	/**
	 * @Description 记录日志
	 * @param systemLog
	 * @return
	 */
	public void saveLog(SystemLog systemLog) {
		logDao.saveLog(systemLog);
	}
}
