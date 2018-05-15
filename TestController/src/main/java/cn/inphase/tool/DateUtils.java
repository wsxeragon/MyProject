package cn.inphase.tool;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	private static Calendar calendar = Calendar.getInstance();

	public static String getNowDate() {
		calendar.setTime(new Date());
		int year = calendar.get(calendar.YEAR);
		int month = calendar.get(calendar.MONTH) + 1;
		int day = calendar.get(calendar.DAY_OF_MONTH);
		int hour = calendar.get(calendar.HOUR_OF_DAY);
		int minute = calendar.get(calendar.MINUTE);
		int millisSecond = calendar.get(calendar.MILLISECOND);
		return "" + year + month + day + hour + minute + millisSecond;
	}

}
