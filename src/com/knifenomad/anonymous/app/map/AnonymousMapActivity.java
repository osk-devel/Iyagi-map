package com.knifenomad.anonymous.app.map;

import java.util.List;

import com.knifenomad.anonymous.*;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.os.Bundle;
import com.google.android.maps.Overlay;
import com.knifenomad.anonymous.Apis;
import com.knifenomad.anonymous.MapOverlay;
import com.knifenomad.anonymous.R;
import android.graphics.drawable.Drawable;

import android.location.LocationManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;


import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.util.Log;
import android.view.View;



public class AnonymousMapActivity extends MapActivity implements LocationListener {
	
	protected LocationManager mLocationManager;
	protected Criteria mCriteria = null;
	protected JList group_list; 
	
	MapView mapView;
	MapController mapController;
	
    public Criteria getCriteria() {
    	if(mCriteria == null)
    	{
	        mCriteria = new Criteria();
	        mCriteria.setAccuracy(Criteria.ACCURACY_FINE);
	        mCriteria.setAltitudeRequired(false);
	        mCriteria.setBearingRequired(false);
	        mCriteria.setCostAllowed(true);
	        mCriteria.setPowerRequirement(Criteria.POWER_LOW);
    	}
    	return mCriteria;
    }
    
    public String getBestProvider() {
    	String provider = mLocationManager.getBestProvider(getCriteria(), true);
    	Log.v("MapActivitiy", provider);
    	return provider;
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        RelativeLayout container = new RelativeLayout(this);
        mapView = new MapView(this, "0ccaMaVkGgzHQwihdk4ojHsOhDOU7viWAxLTVEQ");
        mapView.isClickable();
        mapView.setEnabled(true);
        
        ImageView addEventBtn = new ImageView(this);
        addEventBtn.setImageResource(R.drawable.btn_event_plus);
        
        LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        
        container.addView(mapView);
        container.addView(addEventBtn);

        addEventBtn.setOnClickListener(
        		new View.OnClickListener() {
        			public void onClick(View v) {
        				onClickAddEvent(v);
        	        }
        	    }
        );
        
        addEventBtn.setLayoutParams(param);
        
        mLocationManager	= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mapController		= mapView.getController(); 
        mapController.setZoom(14);
        
        Location location = mLocationManager.getLastKnownLocation(getBestProvider());
        updateMyLocation(location);
        
        setContentView(container);
        registerForContextMenu(container);
    }

    @Override
    public void onCreateContextMenu( ContextMenu menu,
    		View v, ContextMenu.ContextMenuInfo menuInfo ) {
    	group_list = Apis.getMyGroupList(AnonymousMapActivity.this);
    	menu.setHeaderTitle("Group list");
    	
    	for(int i=0; i< group_list.length(); i++) {
    		JDict group = group_list.getDict(i);
    		menu.add(0, i, 0, group.getString("name"));
    	}
    }
    
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int idx = item.getItemId();
        
        JDict group = group_list.getDict(idx);
        JDict user  = Global.getDict("user");
        
        user.set("gid"  , group.getString("gid"));
        user.set("gname", group.getString("name"));
        
        Global.set("user" , user);
        Global.set("group", group);
        
        JList event_list = Apis.getMyEventList(AnonymousMapActivity.this, mapView);
        
        Drawable marker = this.getResources().getDrawable(R.drawable.pin);
        
        List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        
        MapOverlay overlay = new MapOverlay(marker, AnonymousMapActivity.this);
        
        for(int i=0;i<event_list.length();i++) {
        	JDict event = event_list.getDict(i);
        	Log.v("MapActivitiy", event.toString());
        	overlay.addEvent(event);
        }
        listOfOverlays.add(overlay);
        return false;
    }
    
    public void updateMyLocation(Location location) {
    	if(location != null)
    	{
	    	Log.v("MapActivitiy", "update");
	    	double lat = location.getLatitude();
	    	double lng = location.getLongitude();
	    	double alt = location.getAltitude();
	 
	        GeoPoint point = new GeoPoint(
	            (int) (lat * 1E6), 
	            (int) (lng * 1E6));
	
	        //---Add a location marker---
	        mapController.animateTo(point);
	        mapView.postInvalidate();
	        //mLocationManager.requestLocationUpdates(getBestProvider(), 2000, 0, AnonymousMapActivity.this);
    	}
    }
    
    // LocationListener implementation start
    public void onLocationChanged(Location location) {
    	updateMyLocation(location);
    }
    
    public void onProviderEnabled(String provider) {
    	Log.v("MapActivitiy", "GPS enabled");
    }
    
    public void onProviderDisabled(String provider) {
    	Log.v("MapActivitiy", "GPS disabled");
    }
    
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}
	// LocationListener implementation ends
	
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	Log.v("MapActivitiy", "Pause");
    	mLocationManager.removeUpdates(AnonymousMapActivity.this);
    	
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	Log.v("MapActivitiy", "Resume");
    	mLocationManager.requestLocationUpdates(getBestProvider(), 2000, 10, AnonymousMapActivity.this);
    }
    
    public void onClickAddEvent(View v) {
    	Log.v("MapActivitiy", "AddEvent Clicked!");
    }
}