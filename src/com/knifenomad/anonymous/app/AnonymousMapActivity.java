package com.knifenomad.anonymous.app;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import android.view.MotionEvent;
import android.os.Bundle;
import com.google.android.maps.Overlay;
import com.knifenomad.anonymous.MapOverlay;
import com.knifenomad.anonymous.R;
import android.graphics.drawable.Drawable;

import android.location.LocationManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.widget.Toast;
import android.util.Log;



public class AnonymousMapActivity extends MapActivity implements LocationListener {
	
	protected LocationManager mLocationManager;
	protected Criteria mCriteria = null;
	
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
        setContentView(R.layout.map);
        
        mLocationManager	= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mapView				= (MapView) findViewById(R.id.mapView);
        mapController		= mapView.getController(); 
        mapController.setZoom(17);
        Location location = mLocationManager.getLastKnownLocation(getBestProvider());
        updateMyLocation(location);
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
	        Drawable marker = this.getResources().getDrawable(R.drawable.map_marker);
	        MapOverlay overlay = new MapOverlay(marker);
	        overlay.addEvent(lat, lng, "I'm here", "My position");
	        List<Overlay> listOfOverlays = mapView.getOverlays();
	        listOfOverlays.clear();
	        listOfOverlays.add(overlay);
	        mapController.animateTo(point);
	        mapView.invalidate();
	        //mLocationManager.requestLocationUpdates(getBestProvider(), 2000, 10, AnonymousMapActivity.this);
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
}