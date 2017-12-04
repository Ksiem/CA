package com.util;


/**
 * 日志描述字段  超过3800个字符就截取子都
 * @author sddm.chenxia
 *
 */
public final class LogUtil {
	
	public static String LogDescribe_s(String params) { // 
		String logDescribe = params;
	    //假设我某个打印域的长度不能超过3800个字节的长度
	    String print = null;
	    byte[] bytes = logDescribe.getBytes();
	    System.out.println(bytes.length);
	    if(bytes.length>3800){
	        String tmp1 = new String(bytes,0,3800);
	        String tmp2 = new String(bytes,0,3801);
	        if(tmp1.length() == tmp2.length()){
	        //当tmp1域tmp2的String长度相同时，说明3800和3801位组成的是一个字符，因此取到3799位长度就不会出现半个汉字了
	        	String tmp3 = new String(bytes,0,3799);
	            print = tmp3;
	        }else{
	            print = tmp1;
	        }
	    }else{
	        print = logDescribe;
	    }
	    return print;
	}
	
	public static String LogDescribe(String params) { // 
		String logDescribe = params;
		  //假设我某个打印域的长度不能超过2000个字节的长度
        String print = null;
        byte[] bytes = logDescribe.getBytes();
        System.out.println(bytes.length);
        if(bytes.length>2000){
            String tmp1 = new String(bytes,0,2000);
            String tmp2 = new String(bytes,0,2001);
            if(tmp1.length() == tmp2.length()){
            //当tmp1域tmp2的String长度相同时，说明2000和2001位组成的是一个字符，因此取到1999位长度就不会出现半个汉字了
            	String tmp3 = new String(bytes,0,1999);
                print = tmp3;
            }else{
                print = tmp1;
            }
        }else{
            print = logDescribe;
        }
        return print;
	}
}
