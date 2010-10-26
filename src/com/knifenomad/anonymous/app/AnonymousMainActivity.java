package com.knifenomad.anonymous.app;

import com.knifenomad.anonymous.R;
import com.knifenomad.anonymous.R.drawable;
import com.knifenomad.anonymous.R.layout;

import android.app.TabActivity;
import android.content.res.Resources;
import android.util.Log;
import android.widget.TabHost;
import android.content.Intent;
import android.util.Log;
import android.os.Bundle;

public class AnonymousMainActivity extends TabActivity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	
	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab
	    
	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, AnonymousMapActivity.class);
	    
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("map").setIndicator("Map",
	                      res.getDrawable(R.drawable.ic_menu_map))
	                  .setContent(intent);
	    tabHost.addTab(spec);  
	
	    intent = new Intent().setClass(this, AnonymousMapActivity.class);
	    spec = tabHost.newTabSpec("Settings").setIndicator("Settings",
	            res.getDrawable(R.drawable.ic_menu_setting))
	        .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, AnonymousMapActivity.class);
	    spec = tabHost.newTabSpec("message").setIndicator("Messages",
	            res.getDrawable(R.drawable.ic_menu_msg))
	        .setContent(intent);
	    tabHost.addTab(spec);
	    tabHost.setCurrentTab(0);
	    Intent login = new Intent(this, AnonymousLoginActivity.class);
	    startActivityForResult(login, 1);
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		Log.v("onActivityResult", Integer.toString(resultCode));
		if(resultCode==RESULT_OK) {}
	}
}