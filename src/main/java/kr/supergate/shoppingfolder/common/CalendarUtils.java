package kr.supergate.shoppingfolder.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalendarUtils {
	
	private final static String yyyyMMdd = "yyyyMMdd";
	private final static String yyyyMMddDBFormat = "yyyy-MM-dd";

	private final static String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
	
	private final static String yyyyMMdd_SMS = "yyyy년 MM월 dd일";

			
	public static Date parse(String source) {
		try {
			return new SimpleDateFormat(yyyyMMdd).parse(source);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date parseTime(String source) {
		try {
			return new SimpleDateFormat(yyyyMMddHHmmss).parse(source);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String parseTime(Date source) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMddHHmmss);
		return simpleDateFormat.format(source);
	}

	public static String parseDBFormat(Date source) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMddDBFormat);
		return simpleDateFormat.format(source);
	}



	public static DateFormat getISO8601Format() {
		TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
		DateFormat df = new SimpleDateFormat(yyyyMMddHHmmss);
		df.setTimeZone(tz);

		return df;
	}

	public static Date getNow() {
		return new Date();
	}
	
	public static String after(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		c.add(Calendar.DAY_OF_MONTH, amount);
		
		return new SimpleDateFormat(yyyyMMdd_SMS).format(c.getTime());
	}
}
