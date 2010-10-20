package com.knifenomad.anonymous;

import android.app.Application;
import android.content.Context;

public class AnonymousApplication extends Application {
	
	// global states which holds
	
	// user(Dict)
	// group(Dict)
	// and so on...
	
	private static AnonymousApplication instance;
	private Dict Globals; 

    public AnonymousApplication() {
        instance = this;
        Globals  = new Dict();
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