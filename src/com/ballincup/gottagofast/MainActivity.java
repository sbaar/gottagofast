	package com.ballincup.gottagofast;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity {
public GoogleMap mMap;
public String apiURL = "http://api.amp.active.com/v2/search?query=running&api_key=vdzxkzhe75bbheu2642y5tts&radius=25&per_page=10&near=San%20Diego,CA,US";
LatLng here;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		LocationManager lm =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
		String provider = lm.getBestProvider(new Criteria(),false);
		Location location = lm.getLastKnownLocation(provider);
		here = new LatLng(location.getLatitude(), location.getLongitude());
		
		
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		mMap.setMyLocationEnabled(true);
		mMap.setIndoorEnabled(true);
		mMap.addMarker(new MarkerOptions().position(here).title("ME"));
		mMap.moveCamera(CameraUpdateFactory.newLatLng(here));
		mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
		RunningRequest();
	}
    private void RunningRequest() {
    	RequestQueue queue = Volley.newRequestQueue(this);
    	String reqURL = apiURL.toString();
    	reqURL += "&lat_lon=" + here.latitude + "," + here.longitude;
    	
    	JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, reqURL, null, 
    			new Response.Listener<JSONObject>() {
    			public void onResponse(JSONObject response) {
    				String s = "";
    				s.toString();
    			}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
						}
				});
 		queue.add(jsObjRequest);
	}
	protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();

    }
    
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
