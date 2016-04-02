package com.zk.common.util;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
   public static  Time StringToTime(String timeFormat,String str){
       SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
       Time time=null;
		try {
			time = new Time(sdf.parse(str).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	       return time; 
	   }
}
