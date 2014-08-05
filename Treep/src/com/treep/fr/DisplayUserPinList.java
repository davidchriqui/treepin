package com.treep.fr;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.treep.fr.R;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class DisplayUserPinList extends AsyncTask<Void, Void, Integer> {
	Context mContext;
	Double myLatitude;
	Double myLongitude;
	GoogleMap mMap;
	HashMap<String, String> map = new HashMap<String, String>();
	String username;
	ArrayList<Integer> alDistanceTime = new ArrayList<Integer>();
 	ArrayList<Marker> userMarkerList = new ArrayList<Marker>();
 	
 	
	public DisplayUserPinList(Double myLatitude, Double myLongitude,
			String username,
			HashMap<String, String> map,
			GoogleMap mMap,
			ArrayList<Integer> alDistanceTime,
			ArrayList<Marker> userMarkerList) {
	    super();
	    mContext = ApplicationContextProvider.getContext();
	    this.myLatitude=myLatitude;
	    this.myLongitude=myLongitude;
	    this.username=username;
	    this.map=map;
	    this.mMap=mMap;
	    this.alDistanceTime=alDistanceTime;
	    this.userMarkerList=userMarkerList;
	}
	
	
	private int getDurationInfo(Double lat1, Double lng1, Double lat2, Double lng2) {
        StringBuilder stringBuilder = new StringBuilder();
        int duration = 0;
        try {
   
	        String url = "http://maps.googleapis.com/maps/api/directions/json?origin=" + lat1 + "," + lng1 + "&destination=" + lat2 +","+ lng2 + "&mode=driving&sensor=false";
	
	        HttpPost httppost = new HttpPost(url);
	
	        HttpClient client = new DefaultHttpClient();
	        HttpResponse response;
	        stringBuilder = new StringBuilder();
	
	
	        response = client.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        InputStream stream = entity.getContent();
	        int b;
	        while ((b = stream.read()) != -1) {
	            stringBuilder.append((char) b);
	        }
        }
        catch (ClientProtocolException e) {
        } 
        catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject = new JSONObject(stringBuilder.toString());

            JSONArray array = jsonObject.getJSONArray("routes");

            JSONObject routes = array.getJSONObject(0);

            JSONArray legs = routes.getJSONArray("legs");

            JSONObject steps = legs.getJSONObject(0);

            JSONObject distance = steps.getJSONObject("duration");

            Log.i("Duration", distance.toString());
            duration = Integer.parseInt(distance.getString("value"));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return  Math.round(duration/60);
    }
	@Override
	protected Integer doInBackground(Void... params) {
		
		 return getDurationInfo(Double.parseDouble(map.get(MainActivity.KEY_LATITUDE)),Double.parseDouble(map.get(MainActivity.KEY_LONGITUDE)), myLatitude, myLongitude);
	   
	}
	
	
	 protected void onPostExecute(Integer result) { 
		 
		 
		 if(result == null ){
			MarkerOptions markerOptions;

				markerOptions = new MarkerOptions().position(new LatLng(Double.parseDouble(map.get(MainActivity.KEY_LATITUDE)),Double.parseDouble(map.get(MainActivity.KEY_LONGITUDE))))
							.title(username)
					        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pinuserposition));
		

			userMarkerList.add(mMap.addMarker(markerOptions));
		 }
		 else{
			MarkerOptions markerOptions;

				markerOptions = new MarkerOptions().position(new LatLng(Double.parseDouble(map.get(MainActivity.KEY_LATITUDE)),Double.parseDouble(map.get(MainActivity.KEY_LONGITUDE))))
							.title(username)
					        .snippet("Se trouve à " + result + " min")
					        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pinuserposition));
			

			userMarkerList.add(mMap.addMarker(markerOptions));
														
			alDistanceTime.add(result);
			
			
		 }
	 }
}

	