package cn.inphase.tool;

import org.apache.log4j.Logger;

public class Log4jTool {
	private static Logger logger = Logger.getLogger("rootLogger");

	public static void logInfo(String msg) {
		logger.info(msg);
	}

	public static void logDebug(String msg) {
		logger.debug(msg);
	}

	public static void logErrror(String msg) {
		logger.error(msg);
	}

	public static String getExceptionDetail(Exception e) {
		StringBuffer stringBuffer = new StringBuffer(e.toString() + "\n");
		StackTraceElement[] messages = e.getStackTrace();
		int length = messages.length;
		for (int i = 0; i < length; i++) {
			stringBuffer.append("\t" + messages[i].toString() + "\n");
		}
		return stringBuffer.toString();
	}
}
