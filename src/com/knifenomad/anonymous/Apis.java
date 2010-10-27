package com.knifenomad.anonymous;

import java.util.HashMap;
import java.util.Iterator;

public class Apis {
	
	public final static String BASEURL = "http://175.122.253.4:33333/cgi-bin/";
	
	public static String call(String url, JDict params) {
		Iterator it = params.keys();
		
		if (it.hasNext()) url += "?";
		
		while(it.hasNext()) {	
			String key = (String) it.next();
			String val = (String) params.get(key);	
			url += String.format("%s=%s&", key, val);
		}
		HttpData data = HttpRequest.get(BASEURL + url);
		return data.content;
	}
	
	public static String call(String url, String json) {
		JDict params = JDict.decode(json);
		return call(url, params);
	}
}
