package com.knifenomad.anonymous;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class JList extends JSONArray{
	
	public JList()
	{
		super();
	}
	
	public JList(String json) throws JSONException
	{
		super(json);
	}
	
	public Object get(int idx)
	{
		Object obj = null;
		try{
			obj = super.get(idx);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public JDict getDict(int idx)
	{
		JDict obj = null;
		obj = (JDict) get(idx);
		return JDict.decode(obj.toString());
	}
	
	public String getString(int idx)
	{
		String str = (String) get(idx);
		try {
			str = URLDecoder.decode(str, "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public JList put(Object value)
	{
		super.put(value);
		return this;
	}
	
	public JList put(int idx, Object value)
	{
		try{
			super.put(idx, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public JList set(int idx, Object value)
	{
		return put(idx, value);
	}
	
	public static String encode(JList list)
	{
		String encoded=null;
		encoded = list.toString();
		return encoded;
	}
	
	public static JList decode(String json)
	{
		JList decoded = null;
		try {
			decoded = new JList(json);
		} catch (JSONException e) {
			e.printStackTrace();
			decoded = new JList();
		}
		return decoded;
	}
}