package com.dropbyke

class ParseUtils {
	public static int strToInt(String val, int dflt = 0) {

		try {
			return Integer.parseInt(val)
		}
		catch(e) {
			return dflt
		}
	}
	
	public static long strToLong(String val, long dflt = 0L) {
		
		try {
			return Long.parseLong(val)
		}
		catch(e) {
			return dflt
		}
	}

	public static  double strToNumber(String val, double dflt = 0.0) {

		try {
			return Double.valueOf(val)
		}
		catch(e) {
			return dflt
		}
	}
}
