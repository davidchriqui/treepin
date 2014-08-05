package com.treep.fr;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class DisplayDriverOrderedPin extends AsyncTask<Void, Void, Integer> {
	Marker driverOrderedMarker;
	String lat1;
	String lng1;
	Double lat2;
	Double lng2;
	String username;
	TextView tvspOrderedDistanceTime;
	TextView infobanner;
	StringBuilder infobannertxt = new StringBuilder();
	
	public DisplayDriverOrderedPin(Marker driverOrderedMarker,
			String lat1,
			String lng1,
			Double lat2,
			Double lng2, 
			String username, 
			TextView tvspOrderedDistanceTime, 
			TextView infobanner) {
	    super();
	    this.driverOrderedMarker=driverOrderedMarker;
	    this.lat1=lat1;
	    this.lng1=lng1;
	    this.lat2=lat2;
	    this.lng2=lng2;
	    this.username=username;
	    this.tvspOrderedDistanceTime=tvspOrderedDistanceTime;
	    this.infobanner=infobanner;
	}
	
	
	private int getDurationInfo(String lat1, String lng1, Double lat2, Double lng2) {
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
		
		 return getDurationInfo(lat1, lng1, lat2, lng2);
	   
	}
	
	
	 @SuppressLint("SimpleDateFormat")
	protected void onPostExecute(Integer result) { 
		 
		 //MainActivity.displayToast("lat1 : " + lat1 + "lng1 : " + lng1 + "lat2 : " + lat2 + "lng2 : " + lng2);
		 
		 if(result == null ){
			
		 }
		 else{
			driverOrderedMarker.setSnippet("Se trouve à " + result + " min");
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
	 		Calendar cal = Calendar.getInstance();
	 		cal.add(Calendar.MINUTE, result);
			 
			infobannertxt.append(username).append(" arrive à ").append(dateFormat.format(cal.getTime()));
	 		infobanner.setText(infobannertxt.toString());
			
	 		tvspOrderedDistanceTime.setText("Rendez-vous à " + dateFormat.format(cal.getTime()));
	 		
	 		
		 }
	 }
}

	