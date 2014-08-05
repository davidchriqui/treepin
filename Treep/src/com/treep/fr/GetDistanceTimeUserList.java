package com.treep.fr;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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

public class GetDistanceTimeUserList extends AsyncTask<Void, Void, HashMap<String, String>> {
	Context mContext;
	Double lat1;
	Double lng1;
	Double lat2;
	Double lng2;
	String distanceTime = "";
	HashMap<String, String> mapDistanceTime = new HashMap<String, String>();
	String username;
	String userId;
	ArrayList<Integer> alDistanceTime;
	StringBuilder infobannertxt;
	TextView infobanner;
	
	public GetDistanceTimeUserList(Double lat1, 
			Double lng1,
			String distanceTime,
			String userId, 
			String username,
			ArrayList<Integer> alDistanceTime,
			StringBuilder infobannertxt,
			TextView infobanner) {
	    super();
	    mContext = ApplicationContextProvider.getContext();
	    this.lat1 = lat1;
	    this.lng1= lng1;
	    this.distanceTime=distanceTime;
	    this.username = username;
	    this.userId=userId;
	    this.alDistanceTime=alDistanceTime;
	    this.infobannertxt=infobannertxt;
	    this.infobanner=infobanner;
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
		 	
		alDistanceTime.add(Integer.parseInt(mapDistanceTime.get(userId)));
		infobannertxt = new StringBuilder();
		infobannertxt.append("treeper le plus proche à ").append(Collections.min(alDistanceTime)).append(" min");
		infobanner.setText(infobannertxt.toString());
	    }
}
	