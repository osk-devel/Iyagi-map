package com.knifenomad.anonymous;

import org.json.JSONObject;
import org.json.JSONException;

public class Dict extends JSONObject{
	
	public Dict()
	{
		super();
	}
	
	public Dict(String json) throws JSONException
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
	
	public String getString(String name)
	{
		return (String) get(name);
	}
	
	public Dict put(String name, Object value)
	{
		try{
			super.put(name, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public Dict set(String name, Object value)
	{
		return put(name, value);
	}
	
	public static String encode(Dict dict)
	{
		String encoded=null;
		encoded = dict.toString();
		return encoded;
	}
	
	public static Dict decode(String json)
	{
		Dict decoded = null;
		try {
			decoded = new Dict(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return decoded;
	}	
}