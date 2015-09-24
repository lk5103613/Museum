package com.shmuseum.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 有关日期的操作用此类完成
 * 
 * @author 水季
 * @version 1.0, 2005/4/24
 */
public class DateUtil {

	/**
	 * <p>
	 * 获取服务器当前时间，格式为"yyyy-mm-dd"
	 * </p>
	 * 
	 * @return 返回一个String
	 */
	public static String getDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/**
	 * 把带时间的日期取出，如果是1900-01-01日的，转换成空
	 * 
	 * @param date
	 *            传入一个字符串
	 * @return 返回一个String
	 */
	public static String getDate(String date) {
		if (date.indexOf(" ") != -1) {
			date = date.substring(0, date.indexOf(" "));
		}
		if (date.equals("1900-01-01")) {
			date = "";
		}
		return date;
	}

	public static Date conversionDate(String date) {
		if (date == null)
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date conversionDate(String date, String format) {
		if (date == null)
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date conversionDate(Date date, String format) {
		return conversionDate(DateUtil.getDateTime(date), format);
	}

	public static Date conversionDateYMD(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		// System.out.println(getDate());
		long l = 1230880341000L;
		Date d = new Date(l);
		System.out.println(new SimpleDateFormat("MM-dd HH:mm").format(d));
	}

	// 毫秒数换为 01-02 15:30
	public static String systimeToDate(String ci) {
		long l = Long.parseLong(ci + "000");
		Date d = new Date(l);
		return (String.valueOf(new SimpleDateFormat("MM-dd HH:mm").format(d)));
	}

	// 转换时间格式的方法，如：09/12/07 变为2007-09-12
	public static String changetime(String sdate) {
		String[] ydate = sdate.split("/");
		String cdate = "20" + ydate[2] + "-" + ydate[0] + "-" + ydate[1];

		return cdate;
	}

	/**
	 * 获取服务器当前时间，格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 返回一个String
	 */
	public static String getDateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());
	}
	
	/**
	 * 获取服务器当前时间，格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 返回一个String
	 */
	public static String getDateTime(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * 获取服务器当前时间，格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 返回一个String
	 */
	public static String getDateTime(String date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * 获取服务器当前时间，格式为2005年01月02日 22点04分05秒
	 * 
	 * @return 返回一个String
	 */
	public static String getChineseDateTime() {
		String string = "";
		try {
			string = (new SimpleDateFormat("yyyy年MM月dd日 HH点mm分ss秒")).format(new Date());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return string;
	}

	/**
	 * 获取以服务器当前时间为基准的代码，格式为20050102
	 * 
	 * @return 返回一个String
	 */
	public static String getDateCode() {
		String string = "";
		Date date = new Date();
		int year, month, day;

		year = date.getYear() + 1900;
		month = date.getMonth() + 1;
		day = date.getDate();
		string = String.valueOf(year);

		if (month < 10) {
			string += "0" + String.valueOf(month);
		} else {
			string += String.valueOf(month);
		}

		if (day < 10) {
			string += "0" + String.valueOf(day);
		} else {
			string += String.valueOf(day);
		}

		return string;
	}

	/**
	 * 获取以服务器当前时间为基准的代码，格式为20050102
	 * 
	 * @return 返回一个String
	 */
	public static String getDateCode(String dt) {
		String string = "";
		Date date = conversionDate(dt);
		int year, month, day;

		year = date.getYear() + 1900;
		month = date.getMonth() + 1;
		day = date.getDate();
		string = String.valueOf(year);

		if (month < 10) {
			string += "0" + String.valueOf(month);
		} else {
			string += String.valueOf(month);
		}

		if (day < 10) {
			string += "0" + String.valueOf(day);
		} else {
			string += String.valueOf(day);
		}

		return string;
	}

	/**
	 * 获取以服务器当前时间为基准的代码，格式为200501020101
	 * 
	 * @return 返回一个String
	 */
	public static String getDateTimeCode(String dt) {
		String string = "";
		Date date = conversionDate(dt);
		int year, month, day, hour, min;

		year = date.getYear() + 1900;
		month = date.getMonth() + 1;
		day = date.getDate();
		hour = date.getHours();
		min = date.getMinutes();

		string = String.valueOf(year);

		if (month < 10) {
			string += "0" + String.valueOf(month);
		} else {
			string += String.valueOf(month);
		}

		if (day < 10) {
			string += "0" + String.valueOf(day);
		} else {
			string += String.valueOf(day);
		}
		if (hour < 10) {
			string += "0" + String.valueOf(hour);
		} else {
			string += String.valueOf(hour);
		}
		if (min < 10) {
			string += "0" + String.valueOf(min);
		} else {
			string += String.valueOf(min);
		}

		return string;
	}

	/**
	 * 获取以服务器当前时间为基准的代码，格式为20050102
	 * 
	 * @return 返回一个String
	 */
	public static String getDateCode(Date date) {
		String string = "";
		int year, month, day;

		year = date.getYear() + 1900;
		month = date.getMonth() + 1;
		day = date.getDate();
		string = String.valueOf(year);

		if (month < 10) {
			string += "0" + String.valueOf(month);
		} else {
			string += String.valueOf(month);
		}

		if (day < 10) {
			string += "0" + String.valueOf(day);
		} else {
			string += String.valueOf(day);
		}

		return string;
	}

	/**
	 * 获取以服务器当前时间为基准的代码，格式为200501020101
	 * 
	 * @return 返回一个String
	 */
	public static String getDateTimeCode(Date date) {
		String string = "";
		int year, month, day, hour, min;

		year = date.getYear() + 1900;
		month = date.getMonth() + 1;
		day = date.getDate();
		hour = date.getHours();
		min = date.getMinutes();

		string = String.valueOf(year);

		if (month < 10) {
			string += "0" + String.valueOf(month);
		} else {
			string += String.valueOf(month);
		}

		if (day < 10) {
			string += "0" + String.valueOf(day);
		} else {
			string += String.valueOf(day);
		}
		if (hour < 10) {
			string += "0" + String.valueOf(hour);
		} else {
			string += String.valueOf(hour);
		}
		if (min < 10) {
			string += "0" + String.valueOf(min);
		} else {
			string += String.valueOf(min);
		}

		return string;
	}

	/**
	 * 获取上月日期和去年日期
	 * 
	 * @param value
	 *            = 0, tag = 0 //获得上个月的日期，如果在1月份则返回0
	 * @param value
	 *            = 0, tag = 1 //获得本月数字
	 * @param value
	 *            = 1, tag = 0 //获得本年数字
	 * @param value
	 *            = 1, tag = 1 //获得去年数字
	 * 
	 * @return 返回一个int
	 */
	public static int getBefDate(int value, int tag) {
		int str = 0;
		try {
			String date = getDate();
			date = date.replaceAll("-", "/");
			Date d = new Date(date);
			if (tag == 0) {
				if (value == 1) {
					str = d.getYear() + 1900;
				} else {
					str = d.getMonth();
				}
			} else {
				if (value == 1) {
					str = d.getYear() + 1900 - 1;
				} else {
					str = d.getMonth() + 1;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return str;
	}

	/**
	 * 获取下个季度的日期段(起始日期) type: 1 月 2 季 3 旬
	 * 
	 * @param strDate
	 *            = "2005-01-01", type = 1 //获取下个月的开始日期
	 * @param strDate
	 *            = "2005-01-01", type = 2 //获取下个季的开始日期
	 * @param strDate
	 *            = "2005-01-01", type = 3 //旬没有做
	 * 
	 * @return 返回一个String
	 */
	public static String getDate(String strDate, int type) {
		String[] str = new String[2];
		strDate = strDate.replaceAll("-", "/");
		Date d = new Date(strDate);
		int year, month, day;
		year = d.getYear() + 1900;
		month = d.getMonth() + 1;
		day = d.getDate();

		switch (type) {
		case 1:
			str[0] = String.valueOf(year) + "-" + String.valueOf(month) + "-01";
			if (month == 12) {
				str[1] = String.valueOf(year + 1) + "-01-01";
			} else {
				str[1] = String.valueOf(year) + "-" + String.valueOf(month + 1)
						+ "-01";
			}
			break;
		case 2:
			if (month == 1 || month == 2 || month == 3) {
				str[0] = String.valueOf(year) + "-01-01";
				str[1] = String.valueOf(year) + "-04-01";
			} else if (month == 4 || month == 5 || month == 6) {
				str[0] = String.valueOf(year) + "-04-01";
				str[1] = String.valueOf(year) + "-07-01";
			} else if (month == 7 || month == 8 || month == 9) {
				str[0] = String.valueOf(year) + "-07-01";
				str[1] = String.valueOf(year) + "-10-01";
			} else if (month == 10 || month == 11 || month == 12) {
				str[0] = String.valueOf(year) + "-10-01";
				str[1] = String.valueOf(year + 1) + "-01-01";
			}
			break;
		case 3:
			break;
		}

		return str[1];
	}

	/**
	 * 获取年月，如:2005-01
	 * 
	 * @param strDate
	 *            传入一个字符串
	 * 
	 * @return 返回一个String
	 */
	public static String getYM(String strDate) {
		String s = "";
		if (strDate.trim().length() > 0 && strDate.indexOf("-") != -1) {
			String str[] = strDate.split("-");
			s = str[0] + "-" + str[1];
			if (strDate.equals("1900-01-01")) {
				s = "";
			}
		}
		if (strDate.trim().length() > 0 && strDate.indexOf("/") != -1) {
			String str[] = strDate.split("/");
			s = str[0] + "-" + str[1];
			if (strDate.equals("1900/01/01")) {
				s = "";
			}
		}
		return s;
	}

	/**
	 * 获取中文格式的年月，如:2005年01月
	 * 
	 * @param strDate
	 *            传入一个字符串
	 * 
	 * @return 返回一个String
	 */
	public static String getChineseYM(String strDate) {
		String s = "";
		if (strDate.trim().length() > 0 && strDate.indexOf("-") != -1) {
			String str[] = strDate.split("-");
			s = str[0] + "年" + str[1] + "月";
			if (strDate.equals("1900-01-01")) {
				s = "";
			}
		}
		if (strDate.trim().length() > 0 && strDate.indexOf("/") != -1) {
			String str[] = strDate.split("/");
			s = str[0] + "年" + str[1] + "月";
			if (strDate.equals("1900/01/01")) {
				s = "";
			}
		}
		return s;
	}

	/**
	 * 获取本周末的日期或是本周的星期一
	 * 
	 * @param tag
	 *            = 0 //获取本周末的日期，如果今天的日期为周末，则返回下个星期的周末
	 * @param tag
	 *            = 1 //获取本周的星期一
	 * 
	 * @return 返回一个String
	 */
	public static String getWeekDate(int tag) {
		String str = "";
		String str1 = "";
		try {
			Date d = new Date();
			Calendar cal = Calendar.getInstance();
			cal.clear();

			cal.set((d.getYear() + 1900), (d.getMonth()), d.getDate(),
					d.getHours(), d.getMinutes(), d.getSeconds());
			if (tag == 1) {
				cal.add(Calendar.DATE, -(d.getDay() - 1));
			} else if (tag == 0) {
				cal.add(Calendar.DATE, (7 - d.getDay()));
			} else {
				cal.add(Calendar.DATE, -d.getDay());
			}
			d = cal.getTime();
			str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
			str = getDate(str);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return str;
	}

	/**
	 * 获取所在月
	 * 
	 * @return 返回一个int
	 */
	public static int getMonth() {
		int month = 1;

		try {
			month = (new Date()).getMonth() + 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return month;
	}

	/**
	 * 获取所在月
	 * 
	 * @return 返回一个int
	 */
	public static int getMonth(String date) {
		int month = 1;

		try {
			month = (conversionDate(date)).getMonth() + 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return month;
	}

	/**
	 * 获取所在月
	 * 
	 * @return 返回一个int
	 */
	public static int getMonth(Date date) {
		int month = 1;

		try {
			month = date.getMonth() + 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return month;
	}

	/**
	 * 获取往前的日期
	 * 
	 * @param strDate
	 *            一个字符串, index 向前几天
	 * 
	 * @return 返回一个String
	 */
	public static String getAlongDate(String strDate, int index) {
		String str = "";
		String str1 = "";
		try {
			strDate = strDate.replaceAll("-", "/");
			Date d = new Date(strDate);
			Calendar cal = Calendar.getInstance();
			cal.clear();

			cal.set((d.getYear() + 1900), (d.getMonth()), d.getDate(),
					d.getHours(), d.getMinutes(), d.getSeconds());
			cal.add(Calendar.DATE, index);

			d = cal.getTime();
			str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
//			str = getDate(str);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return str;
	}

	/**
	 * 获取往前的日期
	 * 
	 * @param date
	 *            一个字符串, index 向前几天
	 * 
	 * @return 返回一个String
	 */
	public static String getAlongDate(Date date, int index) {
		return getAlongDate(DateUtil.formatDate(date, "yyyy-MM-dd HH:mm:ss"), index);
	}

	/**
	 * 获取大于这个日期
	 * 
	 * @param strDate
	 *            指定日期
	 * @return 返回一个String
	 */
	public static String getAfterDate(String strDate) {
		String str = "";
		String str1 = "";
		try {
			strDate = strDate.replaceAll("-", "/");
			Date d = new Date(strDate);
			Calendar cal = Calendar.getInstance();
			cal.clear();

			cal.set((d.getYear() + 1900), (d.getMonth()), d.getDate(),
					d.getHours(), d.getMinutes(), d.getSeconds() + 60);

			d = cal.getTime();
			str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return str;
	}

	/**
	 * 计算两个日期差
	 * 
	 * @param s1
	 *            , s2 //s1 - s2
	 * 
	 * @return 返回一个int
	 */
	public static int getSubDate(String s1, String s2) {
		int intDays = 0;
		try {
			s1 = s1.replaceAll("-", "/");
			s2 = s2.replaceAll("-", "/");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			Date dt1 = formatter.parse(s1);
			Date dt2 = formatter.parse(s2);

			long l = dt1.getTime() - dt2.getTime();
			intDays = (int) (l / (24 * 3600 * 1000));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return intDays;
	}

	/**
	 * 获取当前的小时
	 * 
	 * @return 返回一个int
	 */
	public static int getHour() {
		return new Date().getHours();
	}

	/**
	 * 获取当前的小时
	 * 
	 * @return 返回一个int
	 */
	public static int getHour(String date) {
		return conversionDate(date).getHours();
	}

	/**
	 * 获取当前的小时
	 * 
	 * @return 返回一个int
	 */
	public static int getHour(Date date) {
		return date.getHours();
	}

	/**
	 * 获取当前的分
	 * 
	 * @return 返回一个int
	 */
	public static int getMinute() {
		return new Date().getMinutes();
	}

	/**
	 * 获取当前的分
	 * 
	 * @return 返回一个int
	 */
	public static int getMinute(String date) {
		return conversionDate(date).getMinutes();
	}

	/**
	 * 获取当前的分
	 * 
	 * @return 返回一个int
	 */
	public static int getMinute(Date date) {
		return date.getMinutes();
	}

	/**
	 * 获取当前的秒
	 * 
	 * @return 返回一个int
	 */
	public static int getSeconds() {
		return new Date().getSeconds();
	}

	/**
	 * 获取当前的秒
	 * 
	 * @return 返回一个int
	 */
	public static int getSeconds(String date) {
		return conversionDate(date).getSeconds();
	}

	/**
	 * 获取当前的秒
	 * 
	 * @return 返回一个int
	 */
	public static int getSeconds(Date date) {
		return date.getSeconds();
	}

	/**
	 * 获取当前所在年
	 * 
	 * @return 返回一个int
	 */
	public static int getYear() {
		return new Date().getYear() + 1900;
	}

	public static int getYear(Date date) {
		return date.getYear() + 1900;
	}

	public static int getYear(String date) {
		return (conversionDate(date)).getYear() + 1900;
	}

	/**
	 * 获取当前的日
	 * 
	 * @return 返回一个int
	 */
	public static int getDay() {
		return new Date().getDate();
	}

	public static int getDay(String date) {
		return (conversionDate(date)).getDate();
	}

	public static int getDay(Date date) {
		return date.getDate();
	}

	/**
	 * 间隔时间的计算
	 * 
	 * @param date
	 *            日期
	 * @param type
	 *            为1时，返回当前日期加val的日期<br>
	 *            为2时，返回当前周加val的周的星期一<br>
	 *            为3时，返回当前月加val的月的日期<br>
	 * @param val
	 *            间隔数
	 * 
	 * @return 返回一个String
	 */
	public static String getCalDate(String date, int type, int val) {
		String str = "";
		try {
			date = date.replaceAll("-", "/");
			Date d = new Date(date);
			Calendar cal = Calendar.getInstance();
			cal.clear();

			cal.set((d.getYear() + 1900), (d.getMonth()), d.getDate(),
					d.getHours(), d.getMinutes(), d.getSeconds());
			switch (type) {
			case 1: // 日
				cal.add(Calendar.DATE, val);
				break;
			case 2: // 周
				cal.add(Calendar.DATE, 7 * val);
				break;
			case 3: // 月
				cal.add(Calendar.MONDAY, val);
				break;
			case 4: // 年
				cal.add(Calendar.YEAR, val);
				break;
			}

			d = cal.getTime();
			str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
			str = getDate(str);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return str;
	}

	/**
	 * 返回星期几
	 * 
	 * @return String 返回星期几
	 */
	public static String getWeek() {
		String s = "";
		try {
			SimpleDateFormat simpledateformat = new SimpleDateFormat("E");
			s = simpledateformat.format(new Date());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}

	/**
	 * 修改日期：2005-10-4 将型如 2005-01-01 00:00:00.0 格式的日期时间字符串 格式为 2005-01-01 格式的字符串
	 * 
	 * @param date
	 *            String
	 * @return String
	 */
	public static String formatDateTime(String date) {
		String dateStr = date;
		if (dateStr == null || dateStr.length() < 10) {
			dateStr = "0000-00-00";
		} else {
			dateStr = dateStr.substring(0, 10);
		}
		return dateStr;
	}

	/**
	 * 方法 formatDateTime,提供几种全格式的日期格式化
	 * 
	 * @param date
	 * @param formattype
	 *            <li>formattype = 1 -- yyyy-MM-dd hh:mm:ss</li> <li>formattype
	 *            = 2 -- yyyy年MM月dd日 hh:mm:ss</li>
	 * @return
	 */
	public static String formatDateTime(Date date, int formattype) {
		if (date == null)
			return null;
		switch (formattype) {
		case 2:
			return (new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss")).format(date);
		default:
			return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
		}
	}

	/**
	 * 方法 formatDate， 提供几种日期格式化
	 * 
	 * @param date
	 * @param formattype
	 *            <li>formattype = 1 -- yyyy年MM月DD日</li> <li>formattype = 2 --
	 *            yyyy-MM-DD</li> <li>formattype = 3 -- yyyy/MM/DD</li>
	 * @return
	 */
	public static String formatDate(Date date, int formattype) {
		switch (formattype) {
		case 2:
			return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
		case 3:
			return (new SimpleDateFormat("yyyy/MM/dd")).format(date);
		default:
			return (new SimpleDateFormat("yyyy年MM月dd日")).format(date);
		}
	}

	/**
	 * 方法 formatDate， 提供几种日期格式化
	 * 
	 * @param date
	 * @param formattype
	 *            格式
	 * @return
	 */
	public static String formatDate(Date date, String formattype) {
		return (new SimpleDateFormat(formattype)).format(date);
	}

	/**
	 * 处理POI控制EXCEL乱码问题
	 * 
	 * @param src
	 *            String
	 * @return String
	 */
	public String getXlsString(String src) {
		String rightStr = "";
		try {
			rightStr = new String(src.getBytes("UTF-16BE"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rightStr;
	}

	/**
	 * 文件名时间格式化函数
	 * 
	 * @return
	 */
	public static String getDateTimefile() {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		String s = simpledateformat.format(new Date());
		return s;
	}

	/**
	 * 文件名时间格式化函数
	 * 
	 * @return
	 */
	public static String getDateTimefile(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		String s = simpledateformat.format(date);
		return s;
	}
	
	/**
	 * 获取周几，周日为每周第一天
	 * @param date
	 * @return
	 */
	public static int getWeekDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

}