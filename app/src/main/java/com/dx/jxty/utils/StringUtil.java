package com.dx.jxty.utils;

public class StringUtil {
	 public static Boolean isEmpty(Object obj) {
	    	if(null==obj){
	    		return true;
	    	}
	    	if(null== String.valueOf(obj)||"".equals(obj)){
	    		return true;
	    	}
	    	return false;
	    }

}
