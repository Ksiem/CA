package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	//"2017-10-26T15:57:12"
	
	public static Date parseDate(String str){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		Date date = null ;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return date;
	}
	
	
//	public static void main(String[] args) {
//		System.out.println(DateUtil.parseDate("2017-10-27"));
//		
//	}

}
