package com.treep.fr;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.treep.fr.R;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

public class GetDistanceTimeUserPinList extends AsyncTask<Void, Void, HashMap<String, String>> {
	Context mContext;
	Double lat1;
	Double lng1;
	GoogleMap mMap;
	String duration = "";
	String distanceTime = "";
	HashMap<String, String> mapDistanceTime = new HashMap<String, String>();
	String username;
	String userId;
	
	public GetDistanceTimeUserPinList(Double lat1,
										Double lng1, 
										String distanceTime,
										String userId, 
										String username,
										GoogleMap mMap) {
	    super();
	    mContext = ApplicationContextProvider.getContext();
	    this.lat1 = lat1;
	    this.lng1= lng1;
	    this.distanceTime=distanceTime;
	    this.username = username;
	    this.userId=userId;
	    this.mMap=mMap;
	}
	
	/**
	 * Get a Geocoder instance, get the latitude and longitude
	 * look up the address, and return it
	 * @return 
	 *
	 * @params params One or more Location objects
	 */
	@Override
	protected HashMap<String, String> doInBackground(Void... params) {
	   
		 	mapDistanceTime.put(userId, distanceTime);
			return mapDistanceTime;
		
	}
	
	
	 protected void onPostExecute(HashMap<String, String> result) {   
		 	if(result.get(userId) == "null" ){
		 		
		 		mMap.addMarker(new MarkerOptions().position(new LatLng(lat1,lng1))
						.title(username)
				        .snippet("Distance non disponible")
				        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pinuserposition)));
	            
		 	}
		 	
		 	else{
		 		
		 			mMap.addMarker(new MarkerOptions().position(new LatLng(lat1,lng1))
							.title(username)
					        .snippet("Se trouve à " + distanceTime + " min")
					        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pinuserposition)));
		 	}
	            
	        
	    }
}
	