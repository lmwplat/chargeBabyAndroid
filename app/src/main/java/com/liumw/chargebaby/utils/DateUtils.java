package com.liumw.chargebaby.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 * 
 * @author SUN.Eudemon
 * @下午02:59:06
 */
public final class DateUtils {
	

	private DateUtils() {

	}

	/**
	 * 获取当前时间 格式yyyy-MM-dd HH:mm:ss
	 */
	public static String getNow() {
		Date d = new Date(System.currentTimeMillis());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINA);
		return df.format(d);
	}
	
	/**
	 * 获取当前时间 格式yyyyMMddHHmmss
	 */
	public static String getNowStr() {
		Date d = new Date(System.currentTimeMillis());
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss",
				Locale.CHINA);
		return df.format(d);
	}

	/**
	 * 格式化时间 格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Object date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINA);
		return df.format(date);
	}
	
	/**
	 * 格式化时间 格式yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateSimple(Object date) {
		SimpleDateFormat df = new SimpleDateFormat("MM-dd",
				Locale.CHINA);
		return df.format(date);
	}

	/**
	 * 获取本周一
	 * 
	 * @return
	 */
	public static String Monday() {

		Calendar c = Calendar.getInstance();
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(c.getTime());
		System.out.println();
		return sdf.format(c.getTime());
	}

	/**
	 * 获取本周日
	 * 
	 * @return
	 */
	public static String Sunday() {

		Calendar c = Calendar.getInstance();
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 7);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sdf.format(c.getTime()));
		return sdf.format(c.getTime());
	}

	/**
	 * 获取本月最后一天
	 * 
	 * @return
	 */
	public static String monthLastDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date();

		if (dt == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, days);
		String result = format.format(cal.getTime());
		System.out.println("一个月最后一天" + result);
		return result;
	}

	/**
	 * 获取本月第一天
	 * 
	 * @return
	 */
	public static String monthFirstDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date();

		if (dt == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int days = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, days);
		String result = format.format(cal.getTime());
		System.out.println("一个月第一天" + result);
		return result;
	}

	/**
	 * 字符串转Date对象
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateForString(String date) throws ParseException{
		/*try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date d = format.parse(date);
			return d;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;*/
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date d = format.parse(date);
		return d;
		
	}
	
	/**
	 * 字符串转Date对象
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDatetimeForString(String date) throws ParseException{
		/*try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date d = format.parse(date);
			return d;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;*/
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d = format.parse(date);
		return d;
		
	}

	public static String getFirstDayOfMonth(Date d) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		if (d == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int days = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, days);
		String result = format.format(cal.getTime());
		System.out.println("一个月第一天" + result);
		return result;
	}

	public static String getLastDayOfMonth(Date d) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

		if (d == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, days);
		String result = format.format(cal.getTime());
		System.out.println("一个月最后一天" + result);
		return result;
	}
	
/*	public static Date addTime(int second)
	{
		Date date = new Date(System.currentTimeMillis());
		System.out.println("date:" + formatDate(date));
		Date date1 = new Date(System.currentTimeMillis() + 7200 * 1000);
		System.out.println("date1:" + formatDate(date1));
		return null;
	}*/
	
	public static String getNowDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String str = df.format(new Date());
		return str;
	}
	
	/**
	 * 时间加减，单位为小时
	 * @param count 小时数
	 */
	public static Date addTime(int count) {
		
		Calendar cal= Calendar.getInstance();
		Date d = new Date(System.currentTimeMillis());
		cal.setTime(d);
		cal.add(Calendar.HOUR_OF_DAY,count);
		
		return cal.getTime();
	}

}
