package com.hse.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2017-12-28
 * @version 1.00.00
 * @history:
 */
public class DateUtil {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	
	public final static String YMD = "yyyy-MM-dd";
	public final static String YMDH = "yyyy-MM-dd HH";
	public final static String YMDHM = "yyyy-MM-dd HH:mm";
	public final static String YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public final static String DATETIME_YMDHMSS_S = "yyyy-MM-dd HH:mm:ss.S";
	public final static String TIMESTAMP = "yyyyMMddHHmmssSSS";
	public final static String TIMESTAMP_YMDHMS = "yyyyMMddHHmmss";
	public final static String TIMESTAMP_YMDH = "yyyyMMddhh";
	public final static String TIMESTAMP_YMD = "yyyyMMdd";
	public final static String HMS = "HH:mm:ss";

	private static final DateFormat[] ACCEPT_DATE_FORMATS = {
		new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
		new SimpleDateFormat("yyyy-MM-dd"),
		new SimpleDateFormat("dd/MM/yyyy"),
		new SimpleDateFormat("yyyy/MM/dd")
	};  //支持转换的日期格式

	/**
	 * 把当前毫秒的时间转换为秒
	 * @return
	 */
	public static String coverCurrentTimeMillisToSecond() {

		long timestamp = System.currentTimeMillis();
		return Long.valueOf(timestamp/1000).toString();
	}

	/**
	 * 通过时间秒毫秒数判断两个时间的间隔
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDaysByMillisecond(Date date1, Date date2) {
		
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
		return Math.abs(days);
	}
	
	
	public static Timestamp formatTimestamp(String format){
		Date date = new Date();
		SimpleDateFormat df1 = new SimpleDateFormat(format);
		String time = df1.format(date);
		Timestamp CreateDate = Timestamp.valueOf(time);
		return CreateDate;
	}
	
	/**
	 * 
	 * @description:将毫秒数转换为日期
	 * @author: 
	 * @param millSec
	 * @return
	 */
	public static String formatMillSecToDateStr(String millSec) {

		if (millSec == null || "0".equals(millSec)) {
			return null;
		} else {
			long tempL = Long.parseLong(millSec);
			Date tempDate = new Date(tempL);
			return sdf.format(tempDate);
		}
	}
	
	/**
	 * 
	 * @description:将指定格式的原时间字符串转换成标准格式
	 * @author: 
	 * @param origTimeStr
	 * @param origPattern
	 * @param destPattern
	 * @return
	 */
	public static String formatByPattern(String origTimeStr,
			String origPattern, String destPattern) {
		if (origTimeStr == null || "".equals(origTimeStr)) {
			return null;
		} else {
			SimpleDateFormat origSdf = new SimpleDateFormat(origPattern);
			SimpleDateFormat destSdf = new SimpleDateFormat(destPattern);
			try {
				Date date = origSdf.parse(origTimeStr);
				return destSdf.format(date);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	
	public static String formatDate(Date currentDate, String pattern) {
		if (currentDate == null || pattern == null) {
			throw new NullPointerException("The arguments are null !");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(currentDate);
	}
	
	
	/**
	 * DateתString
	 * @param date
	 * @param format
	 * @return
	 */
	public static String toString(Date date, String format) {
		
		if(date == null){
			return "";
		}
		
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
	        return formatter.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
    }
	/**
	 * DateתString
	 * @param date
	 * @return
	 */
	public static String toString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(YMDHMS);
        return formatter.format(date);
    }
	public static String toStringYMD(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(YMD);
        return formatter.format(date);
    }
	/**
	 * DateתString
	 * @param date
	 * @return
	 */
	public static String toStringYNDHM(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(YMDHM);
        return formatter.format(date);
    }
	/**
	 * StringתDate
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date toDate(String str, String format){
        Date date = null;
		try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            date = formatter.parse(str);
        } catch (Exception e) {
			//时间转换异常不打印了
            //e.printStackTrace();
        }
        return date;
    }
	public static Date toDate(Date str, String format){
        Date date = null;
		try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            date = formatter.parse(toString(str,format));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
	
	public static String toStringYMDHMS(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP_YMDHMS);
        return formatter.format(date);
    }
	
	/**
	 * StringתDate
	 * @param str
	 * @return
	 */
	public static Date toDate(String str){
		for (DateFormat format : ACCEPT_DATE_FORMATS) { 
			try { 
				return format.parse(str);//遍历日期支持格式，进行转换 
			}catch(Exception e) { 
				continue; 
			}
		} 
        return null;
    }
	
	public static Date toDateForYMD(String str){
		DateFormat format = new SimpleDateFormat(YMD);
		try {
			return format.parse(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static int getAgeByBirthday(Date birthday) {
			
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthday)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth 
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth 
				age--;
			}
		}
		return age;
	}

	public static boolean isSameDay(Date day1,Date day2){
		
		if(day1 == null || day2 == null) return false;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day1);
		int day1Year = calendar.get(Calendar.YEAR);
		int day1Month = calendar.get(Calendar.MONTH);
		int day1Day = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.setTime(day2);
		int day2Year = calendar.get(Calendar.YEAR);
		int day2Month = calendar.get(Calendar.MONTH);
		int day2Day = calendar.get(Calendar.DAY_OF_MONTH);
		
		return (day1Day == day2Day && day2Month == day1Month && day1Year == day2Year);
	}
	/**
	 * 获取当月第一天
	 * @param isTimestamp true返回yyyy-MM-dd HH:mm:ss，false返回yyyy-MM-dd
	 * @return
	 */
	public static String getFirstDayOfMonth(boolean isTimestamp){
		SimpleDateFormat dateFormatShort = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
		String ret = dateFormatShort.format(calendar.getTime());
		if(isTimestamp){
			return ret + " 00:00:00";
		}
		return ret;
	}
	/**
	 * 获取当月最后一天
	 * @param isTimestamp true返回yyyy-MM-dd HH:mm:ss，false返回yyyy-MM-dd
	 * @return
	 */
	public static String getLastDayOfMonth(boolean isTimestamp){
		SimpleDateFormat dateFormatShort = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		String ret = dateFormatShort.format(calendar.getTime());
		if(isTimestamp){
			return ret + " 23:59:59";
		}
		return ret;
	}
	
}
