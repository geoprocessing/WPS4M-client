package geoprocessing.wps4m.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TimeConverter {
	private static Calendar modifiedJulianDateZero = new GregorianCalendar(1858, 10, 17);
    private static long modifiedJulianDateZeroMsec = modifiedJulianDateZero.getTimeInMillis();
    private static double msecDay = 24 * 60 * 60 * 1000;
    public static String TimeFormat = "yyyy-MM-dd hh:mm:ss";

    /**
     * Converts a Gregorian calendar date to a Modified Julian Date.
     *
     * @param gregorianDate The Gregorian date
     * @return The Modified Julian date as double
     */
    public static double gregorian2ModifiedJulian(Calendar gregorianDate) {
        long msec = gregorianDate.getTimeInMillis() - modifiedJulianDateZeroMsec;
        return ((double) msec) / msecDay;
    }

    /**
     * Converts a Modified Julian date to a Gregorian date.
     *
     * @param modifiedJulianDate to convert
     * @return Gregorian date as a Calendar
     */
    public static Calendar modifiedJulian2Gregorian(double modifiedJulianDate) {
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(modifiedJulianDateZeroMsec + Math.round(modifiedJulianDate * msecDay));
        return c;
    }

	
	public static double str2JulianDate(String datestr,String format){
		SimpleDateFormat sdf  =   new  SimpleDateFormat( format );  
		Calendar calendar = Calendar.getInstance();
		Date date=null;
		try {
			date = sdf.parse(datestr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.setTime(date);
		return gregorian2ModifiedJulian(calendar);
	}
	
	public static Calendar str2Calendar(String time,String format) {
		Date date = str2Date(time, format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	/**
	 * 
	 * @param time the string of time
	 * @param format, time format, yyyy-MM-dd HH:mm:ss for example
	 * @return
	 */
	public static Date str2Date(String time,String format){
		SimpleDateFormat sdf  =   new  SimpleDateFormat( format );  
		Date date=null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public static double date2MJulianDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return gregorian2ModifiedJulian(calendar);
	}
	
	
	public static boolean isEmpty(String value){
		if (value !=null && !value.trim().equals("")) {
			return false;
		}
		return true;
	}

	
	public static double round(double d,int num){
		double multi = Math.pow(10, num);
		return (Math.round(d*multi)/multi);
	}
	
	public static double sum(double[] values){
		double res = 0.0;
		for(double val:values){
			res+= val;
		}
		return res;
	}
	
	public static String calendar2Str(Calendar calendar,String format){
		return (new SimpleDateFormat(format)).format(calendar.getTime()); 
	}
	public static void main(String[] args){
		System.out.println(str2JulianDate("2000/6/14 0:00", "yyyy/MM/dd HH:mm"));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		System.out.println(calendar.getTime().toString());
		System.out.println(calendar2Str(calendar,"yyyy-MM-dd hh:mm:ss"));
		System.out.println(str2JulianDate("2005-01-01 00:00", "yyyy-MM-dd HH:mm"));
		System.out.println(str2JulianDate("2005-01-01 03:00", "yyyy-MM-dd HH:mm"));
		System.out.println(str2JulianDate("2005-01-01 24:00", "yyyy-MM-dd HH:mm"));
		
	}
}
