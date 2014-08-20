package com.treep.fr;



import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.view.WindowManager.BadTokenException;
import android.widget.TextView;



public class GetMatchedDrivers extends AsyncTask<Void, Integer, ArrayList<String>> {
	
	private Activity activity;
	private String latDep;
	private String lngDep;
	private String latDest;
	private String lngDest;
	private String addressDep;
	private String addressDest;
	private String status = "";
	
	

	String jsondep;
	String jsondest;
	
	private ArrayList<HashMap<String,String>> alMapDriverPosition = new ArrayList<HashMap<String,String>>();
	
	private ArrayList<HashMap<String,String>> alMapDriverPositionFiltered = new ArrayList<HashMap<String,String>>();
	
	
	private ArrayList<String> alMatchedDriverList  = new ArrayList<String>();
	private ArrayList<String> alMatchedDetourList  = new ArrayList<String>();
	private ArrayList<String> alMatchedDriverTreepRequestIdList  = new ArrayList<String>();
	
	private StringBuilder url = new StringBuilder();
	private TextView infobannercount;
	
	public GetMatchedDrivers(Activity activity, 
								String latDep, 
								String lngDep,
								String latDest, 
								String lngDest, 
								String addressDep, 
								String addressDest,
								ArrayList<HashMap<String,String>> alMapDriverPosition){
		this.activity=activity;
		this.latDep=latDep;
		this.lngDep=lngDep;
		this.latDest=latDest;
		this.lngDest=lngDest;
		this.addressDep=addressDep;
		this.addressDest=addressDest;
		this.alMapDriverPosition=alMapDriverPosition;
	}
	
	public GetMatchedDrivers(Activity activity, 
			String latDep, 
			String lngDep,
			String latDest, 
			String lngDest, 
			String addressDep, 
			String addressDest,
			ArrayList<HashMap<String,String>> alMapDriverPosition,
			TextView infobannercount){
		this.activity=activity;
		this.latDep=latDep;
		this.lngDep=lngDep;
		this.latDest=latDest;
		this.lngDest=lngDest;
		this.addressDep=addressDep;
		this.addressDest=addressDest;
		this.alMapDriverPosition=alMapDriverPosition;
		this.infobannercount=infobannercount;
	}
		
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
//		StringBuilder test = new StringBuilder();
//		for(int i=0; i <alMapDriverPosition.size(); i++){
//			test.append(alMapDriverPosition.get(i).get(MainActivity.KEY_USERID)).append(" - ");
//		}
//		MainActivity.displayToast(test.toString());
		
		if(LoginDisplayActivity.userId == null || LoginDisplayActivity.userId == "" ){
			this.cancel(true);
			Intent i = new Intent(activity, LoginDisplayActivity.class);
    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    		activity.startActivity(i);
		}
		else{	
			try{
			activity.setProgressBarIndeterminateVisibility(true);
			}
			catch(NullPointerException e){
				
			}
			
			url.append("http://maps.googleapis.com/maps/api/distancematrix/json?origins=").append(latDep).append(",").append(lngDep).append("&destinations=");
			
			// + lat2  +","+ lng2 + URLEncoder.encode("|", "UTF-8") + lat3 + "," + lng3 + "&mode=driving&sensor=false";
			
			if(alMapDriverPosition != null){
				if(alMapDriverPosition.size() != 0){
					for (HashMap<String, String> map : alMapDriverPosition){
						if(!Boolean.parseBoolean(map.get(MainActivity.KEY_ISBUSY))){
							if(Boolean.parseBoolean(map.get(MainActivity.KEY_DRIVERTREEPREQUESTED))){
								alMapDriverPositionFiltered.add(map);
								
								try {
									url.append(map.get(MainActivity.KEY_LATDEP)).append(",").append(map.get(MainActivity.KEY_LNGDEP)).append(URLEncoder.encode("|", "UTF-8"));
								}
								catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									url.append(map.get(MainActivity.KEY_LATDEP)).append(",").append(map.get(MainActivity.KEY_LNGDEP)).append("|");
									
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
			
			
			url.append("&mode=driving&sensor=false");
			
			//MainActivity.displayToast(url.toString());
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values){
		super.onProgressUpdate(values);
		//Toast.makeText(ApplicationContextProvider.getContext(), "traitement asynchrone", Toast.LENGTH_LONG).show();
		
	}

	
	private ArrayList<String> getDistanceMatrix(String latDep, String lngDep, ArrayList<HashMap<String,String>> alMapDriverPositionFiltered ){
		
		
		ArrayList<HashMap<String,String>> alMapMatchedDrivers  = new ArrayList<HashMap<String,String>>();
		
		//Liste contenant les userid des driver dont le point de départ match avec la request
		ArrayList<String> alMatchedDriversDepDest = new ArrayList<String>();
		
		StringBuilder stringBuilder = new StringBuilder();
		
		if(alMapDriverPositionFiltered.size() != 0){
			try {
		        
		        HttpPost httppost = new HttpPost(url.toString());
		
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

				jsondep = stringBuilder.toString();
				
		        status = jsonObject.getString("status");
		        
		        if(!status.contains("OK")){
		        	
				}
				else{
					JSONArray JSONArrayRowsDep = jsonObject.getJSONArray("rows");
					
			         JSONObject JSONelementsDep = JSONArrayRowsDep.getJSONObject(0);
			         
			         JSONArray JSONArrayElementsDep = JSONelementsDep.getJSONArray("elements");
			         
			         for(int i=0; i< JSONArrayElementsDep.length(); i++){
			        	 
			        	 JSONObject JSONObjectdistanceDurationDep = JSONArrayElementsDep.getJSONObject(i);
			             
			             JSONObject JSONObjectDurationDep = JSONObjectdistanceDurationDep.getJSONObject("duration");
			             
			             HashMap<String,String> map = new HashMap<String,String>();
			             
			             map.put(MainActivity.KEY_USERID, alMapDriverPositionFiltered.get(i).get(MainActivity.KEY_USERID));
			             map.put(MainActivity.KEY_LATDEP, alMapDriverPositionFiltered.get(i).get(MainActivity.KEY_LATDEP));
			             map.put(MainActivity.KEY_LNGDEP, alMapDriverPositionFiltered.get(i).get(MainActivity.KEY_LNGDEP));
			             map.put(MainActivity.KEY_LATDEST, alMapDriverPositionFiltered.get(i).get(MainActivity.KEY_LATDEST));
			             map.put(MainActivity.KEY_LNGDEST, alMapDriverPositionFiltered.get(i).get(MainActivity.KEY_LNGDEST));
			             map.put(MainActivity.KEY_DRIVERTREEPREQUESTID, alMapDriverPositionFiltered.get(i).get(MainActivity.KEY_DRIVERTREEPREQUESTID));
			             map.put(MainActivity.KEY_DURATION, JSONObjectDurationDep.getString("value"));
			             
			             
			             alMapMatchedDrivers.add(map);
			             
			             /*if(Double.parseDouble(JSONObjectDuration.getString("value"))/ 60 < 50){
			            	 alMatchedDrivers.add(alMapDriverPositionFiltered.get(i).get(MainActivity.KEY_USERID));
			             }*/
			         }
			         
			         url = new StringBuilder();
			         url.append("http://maps.googleapis.com/maps/api/distancematrix/json?origins=").append(latDest).append(",").append(lngDest).append("&destinations=");
						
			         for (HashMap<String, String> map : alMapMatchedDrivers){
							
							try {
								url.append(map.get(MainActivity.KEY_LATDEST)).append(",").append(map.get(MainActivity.KEY_LNGDEST)).append(URLEncoder.encode("|", "UTF-8"));
							}
							catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								url.append(map.get(MainActivity.KEY_LATDEST)).append(",").append(map.get(MainActivity.KEY_LNGDEST)).append("|");
								
								e.printStackTrace();
							}
			         }
			         
			         try {
				        HttpPost httppost = new HttpPost(url.toString());
				
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
			         
				        
					jsonObject = new JSONObject(stringBuilder.toString());

					jsondest = stringBuilder.toString();
					
			        status = jsonObject.getString("status");
			        if(!status.contains("OK")){
			        	alMatchedDriversDepDest.add("PBDISTANCEDEST");
					}
					else{
						JSONArray JSONArrayRowsDest = jsonObject.getJSONArray("rows");
						
				         JSONObject JSONelementsDest = JSONArrayRowsDest.getJSONObject(0);
				         
				         JSONArray JSONArrayElementsDest = JSONelementsDest.getJSONArray("elements");
				         
				         for(int i=0; i< JSONArrayElementsDest.length(); i++){
				        	 
				        	 JSONObject JSONObjectdistanceDurationDest = JSONArrayElementsDest.getJSONObject(i);
				             
				             JSONObject JSONObjectDurationDest = JSONObjectdistanceDurationDest.getJSONObject("duration");
				             
				             
				             if((Double.parseDouble(JSONObjectDurationDest.getString("value")) + Double.parseDouble(alMapMatchedDrivers.get(i).get(MainActivity.KEY_DURATION)))/60 < MainActivity.distanceMax){
				            	 alMatchedDriversDepDest.add(alMapMatchedDrivers.get(i).get(MainActivity.KEY_USERID));
				            	 alMatchedDriversDepDest.add(String.format("%.1f", (Double.parseDouble(JSONObjectDurationDest.getString("value")) + Double.parseDouble(alMapMatchedDrivers.get(i).get(MainActivity.KEY_DURATION)))/60));
				            	 alMatchedDriversDepDest.add(alMapMatchedDrivers.get(i).get(MainActivity.KEY_DRIVERTREEPREQUESTID));								
				             }
				         }
					}
				}
			}
	        catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         
	         if(alMatchedDriversDepDest.size() == 0){
	        	 alMatchedDriversDepDest.add("NODRIVERMATCHED");
	         }
		}
		else{
			alMatchedDriversDepDest.add("NODRIVERMATCHED");
		}
		
		return alMatchedDriversDepDest;
	}
	
	@Override
	protected ArrayList<String> doInBackground(Void... arg0) {
		
		return getDistanceMatrix(latDep, lngDep,alMapDriverPositionFiltered);
		
	}

	@SuppressLint("NewApi")
	protected void onPostExecute(ArrayList<String>  result) {
		
		//MainActivity.displayToast(status);
		
		
		
		if(result == null){
			//DO NOTHING
		}
		else{
			/*
			StringBuilder getmatched = new StringBuilder();
			getmatched.append("getmatched : \n");
			for(int i=0; i < result.size() ; i ++){
				getmatched.append(result.get(i) + "\n");
				
			}
			MainActivity.displayToast(getmatched.toString());
			
			
			*/
			
			if(result.size() != 0){				
				if(result.get(0) == "NODRIVERMATCHED"){
					SendTreepRequest sendTreepRequest = new SendTreepRequest(activity, latDep, lngDep, latDest, lngDest,addressDep, addressDest, null, null, null, infobannercount);
					sendTreepRequest.execute();
				}
				else{
					if(result.get(0) == "PBDISTANCEDEP"){
						//MainActivity.displayToast("PB DISTANCE DEP");
					}
					else{
						if(result.get(0) == "PBDISTANCEDEST"){
							//MainActivity.displayToast("PB DISTANCE DEST");
						}
						else{
							for(int i = 0 ; i < result.size(); i++){
								alMatchedDriverList.add(result.get(i));
								alMatchedDetourList.add(result.get(i+1));
								alMatchedDriverTreepRequestIdList.add(result.get(i+2));
								i = i+2;
							}
							
							/*
							for(int i = 0 ; i < alMatchedDriverList.size(); i++){
								MainActivity.displayToast(alMatchedDriverList.get(i) + " - " + alMatchedDetourList.get(i) + " - " + alMatchedDriverTreepRequestIdList.get(i));
							}
							*/
							SendTreepRequest sendTreepRequest = new SendTreepRequest(activity, latDep, lngDep, latDest, lngDest,addressDep, addressDest, alMatchedDriverList,alMatchedDriverTreepRequestIdList,alMatchedDetourList,infobannercount);
							sendTreepRequest.execute();
						}
					}
				}
			}
			else{
				MainActivity.displayToast(R.string.httpTimeOut);
			}
			
		}
		try{
			activity.setProgressBarIndeterminateVisibility(false);
		}
		catch(NullPointerException e){
			
		}
	}
}
