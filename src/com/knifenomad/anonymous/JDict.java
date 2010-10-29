package com.knifenomad.anonymous;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class JDict extends JSONObject{
	
	public JDict()
	{
		super();
	}
	
	public JDict(String json) throws JSONException
	{
		super(json);
	}
	
	public Object get(String name)
	{
		Object obj = null;
		try{
			obj = super.get(name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public double getDouble(String name)
	{
		double number = -1.0;
		try {
			number = super.getDouble(name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return number;
	}
	
	public JDict getDict(String name)
	{
		JSONObject obj = null;
		obj = (JSONObject) get(name);
		return JDict.decode(obj.toString());
	}
	
	public JList getList(String name)
	{
		JSONArray obj = null;
		obj = (JSONArray) get(name);
		return JList.decode(obj.toString());
	}
	
	public String getString(String name)
	{
		String str = (String) get(name);
		try {
			str = URLDecoder.decode(str, "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public JDict put(String name, Object value)
	{
		try{
			super.put(name, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public JDict set(String name, Object value)
	{
		return put(name, value);
	}
	
	public static String encode(JDict dict)
	{
		String encoded=null;
		encoded = dict.toString();
		return encoded;
	}
	
	public static JDict decode(String json)
	{
		JDict decoded = null;
		try {
			decoded = new JDict(json);
		} catch (JSONException e) {
			e.printStackTrace();
			decoded = new JDict();
		}
		return decoded;
	}	
}