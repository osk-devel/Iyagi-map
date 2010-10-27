package com.knifenomad.anonymous.app;

import com.knifenomad.anonymous.*;

import android.app.Application;

public class AnonymousApplication extends Application {
	
	// global states which holds
	
	// user(Dict)
	// group(Dict)
	// and so on...
	
	private static AnonymousApplication instance;
	private JDict Globals; 

    public AnonymousApplication() {
        instance = this;
        Globals  = new JDict();
    }

    public static AnonymousApplication getContext() {
        return instance;
    }

	public Object getGlobal(String name){
		return Globals.get(name);
	  }
	  
	public void setGlobal(String name, Object obj){
			Globals.set(name, obj);
	}
}