package com.org.fashtag.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
/**
* @author(name="vipin c p") 
*/
public final class DateUtils {

	private static final long MILLIS_PER_HOUR = 1000 * 60 * 60;

	private static final long MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;

	private static final long MILLIS_PER_MONTH = MILLIS_PER_DAY * 30;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy");

	/**
	 * @return Today's Date
	 */

	public static Date removeTimeFromDate(Date aDate)
	{
		Calendar cal = Calendar.getInstance(); // locale-specific
		cal.setTime(aDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
		
	}
	public  static Date subtractMinutesToDate(int minutes)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentDate());
		cal.add(Calendar.MINUTE, -minutes);
		Date retDate = cal.getTime();
		return retDate;
	}
	
	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}
	
	

	/**
	 * @param aNoOfDays
	 * @return Date , n days after the current date (in yyyy-MM-dd format)
	 */
	public static Date getFutureDate(int aNoOfDays) {
		return DateUtils.getPastDate(-1 * aNoOfDays);
	}

	/**
	 * 
	 * Get the last day of this month, for eg: for February ,2004 last date will
	 * be 29, because of leap year.
	 * 
	 * @param month
	 * @return Date
	 * 
	 */
	public static Date getLastDate(int month, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 
	 * Get the last day of this month, for eg: for February ,2004 last date will
	 * be 29, because of leap year.
	 * 
	 * @param month
	 * @return Date
	 * 
	 */
	public static Date getLastDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 
	 * Get the first day of this month.
	 * 
	 * @param month
	 * @return Date
	 * 
	 */
	public static Date getFirstDate(int month, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 
	 * Get the first day of this month.
	 * 
	 * @param month
	 * @return Date
	 * 
	 */
	public static Date getFirstDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * @param aNoOfDays
	 * @return Date , n days before the current date (in dd/MM/yyyy format)
	 */
	public static Date getPastDate(int aNoOfDays) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -1 * aNoOfDays);
		return cal.getTime();
	}

	/**
	 * @param date
	 * @param aNoOfDays
	 * @return Date , n days after the given date (in dd/MM/yyyy format) without time
	 */
	public static Date getFutureDate(Date dt, int aNoOfDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE,0);
	    cal.set(Calendar.SECOND,0);
	    cal.set(Calendar.MILLISECOND,0);
		cal.add(Calendar.DATE, aNoOfDays);
		return cal.getTime();
	}

	/**.common
	 * Get a future date by adding 'n' months to a specified data
	 * 
	 * @param currentDate
	 * @param month
	 * @return a future date
	 */
	public static Date getFutureDateAfterMonth(Date currentDate, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}

	/**
	 * @param aNoOfDays
	 * @return Date , n days before the current date
	 */
	public static Date getPastDate(Date dt, int aNoOfDays) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		calendar.add(Calendar.DAY_OF_MONTH, (aNoOfDays * -1));

		return calendar.getTime();

	}

	/**
	 * @param l
	 * @return
	 */
	public static double differenceInHours(Date startTime, Date endTime) {
		long hours = 0, minutes = 0, total;
		String str = "";
		Double d;
		if (startTime == null || endTime == null) {
			return 0;
		}
		total = endTime.getTime() - startTime.getTime();
		if (total < 0) {
			return -1;
		}

		hours = hours + (total / MILLIS_PER_HOUR);
		minutes = minutes + ((total % MILLIS_PER_HOUR) / (60 * 1000));
		if (minutes < 10) {
			str = hours + ".0" + minutes;
			d = new Double(str);
			return d.doubleValue();
		}
		str = hours + "." + minutes;
		d = new Double(str);
		return d.doubleValue();
	}

	/**
	 * @param l
	 * @return
	 */
	public static int differenceInDays(Date startTime, Date endTime) {
		if (startTime == null || endTime == null) {
			return 0;
		}
		return (int) ((endTime.getTime() - startTime.getTime()) / MILLIS_PER_DAY);
	}

	/**
	 * @return current timestamp in yyyy-MM-dd hh:mm:ss.ms format (eg 2004-04-28
	 *         10:43:26.962 )
	 */
	public static Timestamp getCurrentTimeStamp() {
		return (new Timestamp(System.currentTimeMillis()));
	}

	/**
	 * 
	 * Compare two dates.
	 * 
	 * @return <code>-1</code> startDate <code> < </code> endDate,
	 *         <code>0</code> for startDate equals endDate, <code>1</code>
	 *         for startDate <code> > </code>endDate
	 * 
	 * 
	 * 
	 */
	public static int compareDate(java.util.Date startDate,
			java.util.Date endDate) {

		if (startDate == null || endDate == null) {
			throw new NullPointerException("Compare Date");
		}

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startDate);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(endDate);

		return cal1.compareTo(cal2);
	}

	/**
	 * 
	 * 
	 * 
	 */
	
	
	 public static int isToday(Date date) {
		 return isSameDay(date, getCurrentDate());
	    }
	 
	 
	public static int isSameDay(java.util.Date date1, java.util.Date date2) {

		if (date2 == null || date1 == null) {
			throw new NullPointerException("Compare Time");
		}

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		Calendar day1 = Calendar.getInstance();
		day1.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DATE));

		Calendar day2 = Calendar.getInstance();
		day2.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DATE));
		
		return day1.compareTo(day2);
	}

	public static String formatDate(Date aDate) 
	{
		return dateFormat.format(aDate);
	}

	/**
	 * @param aDate
	 * @param format
	 * @return
	 */
	public static String formatDate(Date aDate, String format) {
		String formatedDate = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			formatedDate = df.format(aDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formatedDate;
	}

	/**
	 * 
	 * @param dateString
	 *            in the format dd/MM/yyyy
	 * @return
	 */
	public static Date getDate(String dateString) {
		if (CommonUtils.isEmpty(dateString)) {
			return null;
		}
		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getDate(String dateString, String datePattern) {
		if (CommonUtils.isEmpty(dateString)) {
			return null;
		}
		try {
			SimpleDateFormat df = new SimpleDateFormat(datePattern);
			return df.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isDateBetween(Date dateToCheck, Date startDate,
			Date endDate) {

		if (dateToCheck == null || startDate == null || endDate == null) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateToCheck);
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		if (cal.compareTo(startCal) >= 0 && cal.compareTo(endCal) <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * Check wheather the given date is sunday
	 * 
	 * @param dt
	 * 
	 * @return boolean, true if the give date is Sunday
	 * 
	 */
	public static boolean isSunday(Date dt) {
		if (dt == null)
			return false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return true;
		}
		return false;
	}

	/**
	 * Check wheather the given date is saturday
	 * 
	 * @param dt
	 * 
	 * @return boolean, true if the give date is Saturday
	 * 
	 */
	public static boolean isSaturday(Date dt) {
		if (dt == null)
			return false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return true;
		}
		return false;
	}

	/**
	 * Get collection of all dates between startDt and endDt.
	 * 
	 * @param startDt
	 * @param endDt
	 * @return
	 * @return Collection
	 * 
	 */
	public static Collection getDatesBetween(Date startDt, Date endDt) {
		
			if (startDt == null || endDt == null)
			return Collections.EMPTY_LIST;

		int totalDays = differenceInDays(startDt, endDt);
		Collection list = new ArrayList(totalDays);
		list.add(startDt);
		Date tmp = startDt;

		for (int i = 0; i < totalDays; i++) {
			tmp = getFutureDate(tmp, 1);
			list.add(tmp);
		}
		return list;
	}

	/**
	 * Check wheather the start2 & end2 is overlapping start1 & end1 For eg:
	 * 10.00 - 11.00 is overlapping 9.30 and 10.30
	 * 
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 * @return boolean
	 * 
	 */
	public static boolean isDateBetween(Calendar start1, Calendar end1,
			Calendar start2, Calendar end2) {

		if (start2.compareTo(start1) >= 0 && start2.compareTo(end1) <= 0) {
			return true;
		}
		if (end2.compareTo(start1) >= 0 && start2.compareTo(end1) <= 0) {
			return true;
		}

		return false;
	}

	public static Date getMidnigtDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Date(cal.getTimeInMillis());

	}

	public static Date getMidnigtDate(Date aDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(aDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Date(cal.getTimeInMillis());

	}

	public static Date getEndMidnightDate(Date aDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(aDate);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return new Date(cal.getTimeInMillis());

	}

	
	public static String monthofDate(Date d) {
		String month = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int mon = cal.get(Calendar.MONTH);
		switch (mon) {
		case 0:
			month = "Jan";
			break;
		case 1:
			month = "Feb";
			break;
		case 2:
			month = "Mar";
			break;
		case 3:
			month = "Apr";
			break;
		case 4:
			month = "May";
			break;
		case 5:
			month = "Jun";
			break;
		case 6:
			month = "July";
			break;
		case 7:
			month = "Aug";
			break;
		case 8:
			month = "Sept";
			break;
		case 9:
			month = "Oct";
			break;
		case 10:
			month = "Nov";
			break;
		case 11:
			month = "Dec";
			break;
		}
		return month;
	}

	public static String getMonthYear(Date d) {
		String month = "";
		String monthYear;
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int mon = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		switch (mon) {
		case 0:
			month = "Jan";
			break;
		case 1:
			month = "Feb";
			break;
		case 2:
			month = "Mar";
			break;
		case 3:
			month = "Apr";
			break;
		case 4:
			month = "May";
			break;
		case 5:
			month = "Jun";
			break;
		case 6:
			month = "July";
			break;
		case 7:
			month = "Aug";
			break;
		case 8:
			month = "Sept";
			break;
		case 9:
			month = "Oct";
			break;
		case 10:
			month = "Nov";
			break;
		case 11:
			month = "Dec";
			break;
		}
		monthYear = month + " " + year;
		return monthYear;
	}

	/**
	 *         Method to find the corresponding month value against an index
	 *         Eg:. [0-Jan, 10-Nov, 11-Dec]
	 * 
	 * @param index
	 */
	public static String getMonthByIndex(int index) {
		String month = "";
		index = index % 12;
		switch (index) {
		case 0:
			month = "Jan";
			break;
		case 1:
			month = "Feb";
			break;
		case 2:
			month = "Mar";
			break;
		case 3:
			month = "Apr";
			break;
		case 4:
			month = "May";
			break;
		case 5:
			month = "Jun";
			break;
		case 6:
			month = "July";
			break;
		case 7:
			month = "Aug";
			break;
		case 8:
			month = "Sept";
			break;
		case 9:
			month = "Oct";
			break;
		case 10:
			month = "Nov";
			break;
		case 11:
			month = "Dec";
			break;
		}
		return month;
	}

	/**
	 *         Method to Find the Difference in months between two dates
	 */
	public static int differenceInMonths(Date startTime, Date endTime) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startTime);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endTime);
		int differenceInMonths = endCal.get(Calendar.MONTH)
				- startCal.get(Calendar.MONTH)
				+ (endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR))
				* 12;
		return differenceInMonths;
	}

	/**
	 *         Method to find months between two given dates. Eg. From Date :
	 *         12/04/2008 - To Date : 30/07/2008 -> Res: [Apr, May, Jun, Jul]
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static Collection monthsBetweenDates(Date fromDate, Date toDate) {
		Collection monthColl = new ArrayList();
		int differenceInMonths = differenceInMonths(fromDate, toDate);
		System.out.println(differenceInMonths);
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		int firstMonth = cal.get(Calendar.MONTH);
		for (int i = firstMonth; i <= (firstMonth + differenceInMonths); i++) {
			monthColl.add(getMonthByIndex(i));
		}
		return monthColl;
	}

	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.YEAR);
	}

	public static int getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.MONTH);
	}

	public static int getCurrentDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.DATE);
	}

	public static void main(String[] args) {
		System.out.println(getTotalDaysInCurrentMonth());
	}

	/**
	 * @param aDate
	 * @return
	 */
	public static int getDayOfMonth(Date aDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(aDate);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int getTotalDaysInMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static int getTotalDaysInCurrentMonth() {
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static String dayOfDate(Date d) {
		String day = "";
		
		Calendar cal = Calendar.getInstance();
		
		
		cal.setTime(d);
		int aDay = cal.get(Calendar.DAY_OF_WEEK);
		switch (aDay) {
		case 1:
			day = "Sunday";
			break;
		case 2:
			day = "Monday";
			break;
		case 3:
			day = "Tuesday";
			break;
		case 4:
			day = "Wednesday";
			break;
		case 5:
			day = "Thursday";
			break;
		case 6:
			day = "Friday";
			break;
		case 7:
			day = "Saturday";
			break;
		}
		
		return day;
	}

	/**
	 * This method checks whether the day given is valid for the month or not.
	 * @param month
	 * @param year
	 * @return
	 */
	public static boolean checkDayValidity(int month, int year, int day) {
		boolean result = true;
		Date date = DateUtils.getFirstDate(month, year);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (day <= 0 || day > cal.getActualMaximum(Calendar.DATE)) {
			result = false;
		}
		return result;
	}

	
}
