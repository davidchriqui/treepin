package com.treep.fr;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.FloatMath;

public class GetDistancePrice extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {
	Activity activity;
	ArrayList<HashMap<String, String>> alTreepDriverResponse = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> alTreepDriverResponseResult = new ArrayList<HashMap<String, String>>();
	
	String status;
	String duration = "";
	double price = 0;
	
	public GetDistancePrice(Activity activity,
			ArrayList<HashMap<String, String>> alTreepDriverResponse) {
	    super();
	    this.activity = activity;
	    this.alTreepDriverResponse=alTreepDriverResponse;
	}
	
	
	private ArrayList<HashMap<String, String>> getDistanceDurationInfo(String lat1, String lng1, String lat2, String lng2, String lat3, String lng3) {
        StringBuilder stringBuilder = new StringBuilder();
    	ArrayList<HashMap<String, String>> alDistanceDuration = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> distanceDuration1 = new HashMap<String, String> ();
        HashMap<String, String> distanceDuration2 = new HashMap<String, String> ();
        HashMap<String, String> distanceDuration3 = new HashMap<String, String> ();
        HashMap<String, String> distanceDuration4 = new HashMap<String, String> ();
        
        try {
	        String url = "http://maps.googleapis.com/maps/api/distancematrix/json?origins=" + lat1 + "," + lng1  + URLEncoder.encode("|", "UTF-8") + lat2 + "," + lng2 + "&destinations=" + lat2  +","+ lng2 + URLEncoder.encode("|", "UTF-8") + lat3 + "," + lng3 + "&mode=driving&sensor=false";
	
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
            
           // JSONObject JSONstatus = jsonObject.getJSONObject("status");
            //status = JSONstatus.getString("status");

            JSONArray JSONArrayRows = jsonObject.getJSONArray("rows");

            JSONObject JSONelements1 = JSONArrayRows.getJSONObject(0);
            
            JSONArray JSONArrayElements1 = JSONelements1.getJSONArray("elements");
            
            JSONObject JSONObjectdistanceDuration1 = JSONArrayElements1.getJSONObject(0);
            
            JSONObject JSONObjectDistance1 = JSONObjectdistanceDuration1.getJSONObject("distance");
            JSONObject JSONObjectDuration1 = JSONObjectdistanceDuration1.getJSONObject("duration");
            
            distanceDuration1.put(MainActivity.KEY_DISTANCE, JSONObjectDistance1.getString("value"));
            distanceDuration1.put(MainActivity.KEY_DURATION, JSONObjectDuration1.getString("value"));
            
            
            JSONObject JSONObjectdistanceDuration2 = JSONArrayElements1.getJSONObject(1);
            
            JSONObject JSONObjectDistance2 = JSONObjectdistanceDuration2.getJSONObject("distance");
            JSONObject JSONObjectDuration2 = JSONObjectdistanceDuration2.getJSONObject("duration");
            
            distanceDuration2.put(MainActivity.KEY_DISTANCE, JSONObjectDistance2.getString("value"));
            distanceDuration2.put(MainActivity.KEY_DURATION, JSONObjectDuration2.getString("value"));
            
            
            
            
            JSONObject JSONelements2 = JSONArrayRows.getJSONObject(1);
            
            JSONArray JSONArrayElements2 = JSONelements2.getJSONArray("elements");
            
            JSONObject JSONObjectdistanceDuration3 = JSONArrayElements2.getJSONObject(0);
            
            JSONObject JSONObjectDistance3 = JSONObjectdistanceDuration3.getJSONObject("distance");
            JSONObject JSONObjectDuration3 = JSONObjectdistanceDuration3.getJSONObject("duration");
            
            distanceDuration3.put(MainActivity.KEY_DISTANCE, JSONObjectDistance3.getString("value"));
            distanceDuration3.put(MainActivity.KEY_DURATION, JSONObjectDuration3.getString("value"));
            
            
            JSONObject JSONObjectdistanceDuration4 = JSONArrayElements2.getJSONObject(1);
            
            JSONObject JSONObjectDistance4 = JSONObjectdistanceDuration4.getJSONObject("distance");
            JSONObject JSONObjectDuration4 = JSONObjectdistanceDuration4.getJSONObject("duration");
            
            distanceDuration4.put(MainActivity.KEY_DISTANCE, JSONObjectDistance4.getString("value"));
            distanceDuration4.put(MainActivity.KEY_DURATION, JSONObjectDuration4.getString("value"));

            alDistanceDuration.add(distanceDuration1);
            alDistanceDuration.add(distanceDuration2);
            alDistanceDuration.add(distanceDuration3);
            alDistanceDuration.add(distanceDuration4);
            

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //  Math.round(duration/60);
        return alDistanceDuration;
    }
	
	
	public static float distance_between(double lat1, double lng1, double lat2, double lng2) {
		
		float[] results = new float[1];
		
		Location.distanceBetween(lat1, lng1, lat2,lng2, results);
		
		return results[0];
	    }
	
	private ArrayList<HashMap<String, String>> getDistanceDurationInfoCalcul(String lat1, String lng1, String lat2, String lng2, String lat3, String lng3) {
		
		ArrayList<HashMap<String, String>> alDistanceDuration = new ArrayList<HashMap<String, String>>();
		
		HashMap<String, String> distanceDuration1 = new HashMap<String, String> ();
        HashMap<String, String> distanceDuration2 = new HashMap<String, String> ();
        HashMap<String, String> distanceDuration3 = new HashMap<String, String> ();
        HashMap<String, String> distanceDuration4 = new HashMap<String, String> ();
        
        distanceDuration1.put(MainActivity.KEY_DISTANCE, Double.toString(Math.ceil(distance_between(Double.parseDouble(lat1), Double.parseDouble(lng1), Double.parseDouble(lat2),Double.parseDouble(lng2)))));
        distanceDuration1.put(MainActivity.KEY_DURATION, Double.toString(Math.ceil(distance_between(Double.parseDouble(lat1), Double.parseDouble(lng1), Double.parseDouble(lat2),Double.parseDouble(lng2))*(9/100))));
        
        distanceDuration2.put(MainActivity.KEY_DISTANCE, Double.toString(Math.ceil(distance_between(Double.parseDouble(lat1), Double.parseDouble(lng1), Double.parseDouble(lat3),Double.parseDouble(lng3)))));
        distanceDuration2.put(MainActivity.KEY_DURATION, Double.toString(Math.ceil(distance_between(Double.parseDouble(lat1), Double.parseDouble(lng1), Double.parseDouble(lat3),Double.parseDouble(lng3))*(9/100))));
		
        distanceDuration3.put(MainActivity.KEY_DISTANCE, Double.toString(Math.ceil(distance_between(Double.parseDouble(lat2), Double.parseDouble(lng2), Double.parseDouble(lat2),Double.parseDouble(lng2)))));
        distanceDuration3.put(MainActivity.KEY_DURATION, Double.toString(Math.ceil(distance_between(Double.parseDouble(lat2), Double.parseDouble(lng2), Double.parseDouble(lat2),Double.parseDouble(lng2))*(9/100))));
        
        distanceDuration4.put(MainActivity.KEY_DISTANCE, Double.toString(Math.ceil(distance_between(Double.parseDouble(lat2), Double.parseDouble(lng2), Double.parseDouble(lat3),Double.parseDouble(lng3)))));
        distanceDuration4.put(MainActivity.KEY_DURATION, Double.toString(Math.ceil(distance_between(Double.parseDouble(lat2), Double.parseDouble(lng2), Double.parseDouble(lat3),Double.parseDouble(lng3))*(9/100))));
		
        alDistanceDuration.add(distanceDuration1);
        alDistanceDuration.add(distanceDuration2);
        alDistanceDuration.add(distanceDuration3);
        alDistanceDuration.add(distanceDuration4);
        
		return alDistanceDuration;
		
	}
	
	@Override
	protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
		for(HashMap<String, String> map : alTreepDriverResponse){
			
			HashMap<String, String> mapResponseItem = new HashMap<String, String>();
			
			mapResponseItem = map;
			ArrayList<HashMap<String, String>> alMapDistanceDuration = new ArrayList<HashMap<String, String>>();
			
			alMapDistanceDuration = getDistanceDurationInfo(Double.toString(MainActivity.myLatitude),Double.toString(MainActivity.myLongitude),mapResponseItem.get(MainActivity.KEY_LATDEPNOW), mapResponseItem.get(MainActivity.KEY_LNGDEPNOW), mapResponseItem.get(MainActivity.KEY_LATDESTNOW), mapResponseItem.get(MainActivity.KEY_LNGDESTNOW));
			
			if(alMapDistanceDuration == null){
				alMapDistanceDuration = getDistanceDurationInfoCalcul(Double.toString(MainActivity.myLatitude),Double.toString(MainActivity.myLongitude),mapResponseItem.get(MainActivity.KEY_LATDEPNOW), mapResponseItem.get(MainActivity.KEY_LNGDEPNOW), mapResponseItem.get(MainActivity.KEY_LATDESTNOW), mapResponseItem.get(MainActivity.KEY_LNGDESTNOW));
				
			}
			
	    	mapResponseItem.put(MainActivity.KEY_DISTANCETIME, Integer.toString((int)Math.ceil(Double.parseDouble(alMapDistanceDuration.get(0).get(MainActivity.KEY_DURATION))/60)));

	    	if(!Boolean.parseBoolean(map.get(MainActivity.KEY_NIGHTHOUR))){
	    		if(Double.parseDouble(alMapDistanceDuration.get(0).get(MainActivity.KEY_DISTANCE))/1000 >= 5){
	    			price = (Double.parseDouble(alMapDistanceDuration.get(3).get(MainActivity.KEY_DISTANCE))/1000) * Double.parseDouble(MainActivity.VALUE_NORMALDISTANCEPRICE) +(Double.parseDouble(alMapDistanceDuration.get(3).get(MainActivity.KEY_DURATION))/60)* Double.parseDouble(MainActivity.VALUE_NORMALTIMEPRICE) + Double.parseDouble(MainActivity.VALUE_NORMALAPPROACHPRICE);
	    			//price = Math.ceil(price);
		    	}
		    	else{
		    		price = (Double.parseDouble(alMapDistanceDuration.get(3).get(MainActivity.KEY_DISTANCE))/1000) * Double.parseDouble(MainActivity.VALUE_NORMALDISTANCEPRICE) +(Double.parseDouble(alMapDistanceDuration.get(3).get(MainActivity.KEY_DURATION))/60)* Double.parseDouble(MainActivity.VALUE_NORMALTIMEPRICE);
					//price = Math.ceil(price);
		    	}
	    	}
	    	else{
	    		if(Double.parseDouble(alMapDistanceDuration.get(0).get(MainActivity.KEY_DISTANCE))/1000 >= 5){
	    			price = (Double.parseDouble(alMapDistanceDuration.get(3).get(MainActivity.KEY_DISTANCE))/1000) * Double.parseDouble(MainActivity.VALUE_RUSHDISTANCEPRICE) +(Double.parseDouble(alMapDistanceDuration.get(3).get(MainActivity.KEY_DURATION))/60)* Double.parseDouble(MainActivity.VALUE_RUSHTIMEPRICE) + Double.parseDouble(MainActivity.VALUE_RUSHAPPROACHPRICE);
	    			//price = Math.ceil(price);
		    	}
		    	else{
		    		price = (Double.parseDouble(alMapDistanceDuration.get(3).get(MainActivity.KEY_DISTANCE))/1000) * Double.parseDouble(MainActivity.VALUE_RUSHDISTANCEPRICE) +(Double.parseDouble(alMapDistanceDuration.get(3).get(MainActivity.KEY_DURATION))/60)* Double.parseDouble(MainActivity.VALUE_RUSHTIMEPRICE);
					//price = Math.ceil(price);
		    	}
	    	}
			if(price <= 5){
				price = 5;
			}
			
			mapResponseItem.put(MainActivity.KEY_PRICE, Integer.toString((int)Math.ceil(price)));
			
			alTreepDriverResponseResult.add(mapResponseItem);
	    }
		
		 return alTreepDriverResponseResult;
	}
	
	
	 @SuppressLint("NewApi")
	protected void onPostExecute(ArrayList<HashMap<String, String>> result) { 
		 
		 if(result == null){
			 MainActivity.displayToast(R.string.httpTimeOut);
		 }
		 else{
			 if(result.size() ==0){
					
					FragmentManager fragmentManager = activity.getFragmentManager();
		            fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_toptobottom, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container,new TreepDriverResponseListEmpty()).commit();
				   
			 }
			 else{
				
				if(result.get(0).containsKey(MainActivity.KEY_TIMEOUT)){
					MainActivity.displayToast("Problème de connexion, veuillez réessayer");
					
				}
				
				else{
					
					MainActivity.fragment = new TreepDriverResponseListFragment();
					Bundle data = new Bundle();
					FragmentManager fragmentManager = activity.getFragmentManager();
					data.putSerializable("alTreepDriverResponseList",result);
					MainActivity.fragment .setArguments(data);
			        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_toptobottom, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container,MainActivity.fragment).commit();
					
				}
			 }
		 }
			activity.setProgressBarIndeterminateVisibility(false);	
	 }
}

	