package com.inphase.sparrow.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.log4j.Logger;

/**
 * 共用辅助方法
 * 
 * 包含一些零散的便捷方法。日期操作
 * 
 * 
 */
public class DateUtils {
	
	private static final Logger logger = Logger.getLogger("errorfile");
	
	public static final String PATTERN_YYYY_MM_DD  = "yyyy-MM-dd";
	
	public static final String PATTERN_YYYY = "yyyy";
	
	public static final String PATTERN_YYYYMM = "yyyyMM";
	
	public static final String PATTERN_YYYYMMDD = "yyyyMMdd";
	
	public static final String PATTERN_CN = "yyyy年MM月dd日";
	
	public static final String PATTERN_WHOLE = "yyyy-MM-dd HH:mm:ss";
	
	public static final String PATTERN_WHOLE_YMDS = "yyyyMMddHHmmss";
	
	public static final String PATTERN_WHOLE_CN = "yyyy年MM月dd日 HH:mm:ss";
	
	private DateUtils(){}
	
	/**
	 * 
	 * @Title getDate
	 * @Description 获取当前时间
	 * @author zuoyc
	 * @date 2017年5月4日
	 * @return
	 */
	public static Date getDate() {
		return Calendar.getInstance().getTime();
	}
	/**
	 * 
	 * @Title getDate
	 * @Description 获取当前时间并格式化
	 * @author zuoyc
	 * @date 2017年5月11日
	 * @param pattern
	 * @return
	 */
	public static String getDate(String pattern) {
		return formatDate(getDate(),pattern);
	}
	/**
	 * 
	 * @Title formatDate
	 * @Description 按指定类型格式化时间
	 * @author zuoyc
	 * @date 2017年5月4日
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if(date!=null) {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.format(date);
		} else {
			return "";
		}
	}

	/**
	 * 
	 * @Title stringToDate
	 * @Description 将字符串类型时间转换为系统时间
	 * @author zuoyc
	 * @date 2017年5月4日
	 * @param stringDate
	 * @param pattern
	 * @return
	 */
	public static Date stringToDate(String stringDate, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = format.parse(stringDate);
		} catch (ParseException e) {
			logger.error(e);
		}
		return date;
	}
	/**
	 * 
	 * @Title getDateBeforeAfter
	 * @Description 得到加减后的时间
	 * @author zuoyc
	 * @date 2017年5月4日
	 * @param date 需加减的时间基数
	 * @param units 加减单位,1:年,2:月,3:周,6:天,10:小时
	 * @param amount 加减数量,为负数表示减
	 * @return
	 */
	public static Date getDateBeforeAfter(Date date, int units, int amount) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		switch(units) {
			case Calendar.YEAR:
				gc.add(Calendar.YEAR, amount);
				break;
			case Calendar.MONTH:
				gc.add(Calendar.MONTH, amount);
				break;
			case Calendar.WEEK_OF_YEAR:
				gc.add(Calendar.WEEK_OF_YEAR, amount);
				break;
			case Calendar.DAY_OF_YEAR:
				gc.add(Calendar.DAY_OF_YEAR, amount);
				break;
			case Calendar.HOUR:
				gc.add(Calendar.HOUR, amount);
				break;
			default:
				break;
		}
		return gc.getTime();
	}
	/**
	 * 
	 * @Title getTimestamp
	 * @Description 获取当前时间的时间戳
	 * @author zuoyc
	 * @date 2017年5月4日
	 * @return
	 */
	public static long getTimestamp() {
		return getTimestamp(getDate());
	}
	/**
	 * 
	 * @Title getTimestamp
	 * @Description 将时间转换为时间戳
	 * @author zuoyc
	 * @date 2017年5月4日
	 * @param date
	 * @return
	 */
	public static long getTimestamp(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}
	
	/**
	 * 
	 * @Title getTimestamp
	 * @Description 将字符串类型时间转换为时间戳
	 * @author zuoyc
	 * @date 2017年5月4日
	 * @param stringDate
	 * @param pattern
	 * @return
	 */
	public static long getTimestamp(String stringDate, String pattern) {
		return getTimestamp(stringToDate(stringDate, pattern));
	}
	/**
	 * 
	 * @Title getTwoDateDays
	 * @Description 计算两个日期的天数差值
	 * @author zuoyc
	 * @date 2017年5月4日
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static long getTwoDateDays(Date beginDate, Date endDate) {
		long beginTime = beginDate.getTime();
		long endTime = endDate.getTime();
		return (long) ((endTime - beginTime) / (1000 * 60 * 60 * 24) + 0.5);
	}
	
	
	/**
	 * @Description 获取当前年、当前月、当前日
	 * @param timeUnit 使用Calendar的枚举来
	 * @return
	 */
	public static int getTimeUnit(int timeUnit){
		Calendar cal = Calendar.getInstance();
		return cal.get(timeUnit);
	}
}