package com.treep.fr;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.text.Html;
import android.widget.TextView;

import com.parse.ParsePush;
import com.parse.SendCallback;



public class SendTreepRequest extends AsyncTask<Void, Integer, HashMap<String, String>> {
	
	private Activity activity;
	private String latDep;
	private String lngDep;
	private String latDest;
	private String lngDest;
	private String addressDep;
	private String addressDest;
	private ArrayList<String> alMatchedDrivers;
	private ArrayList<String> alStoredMatchedDrivers;
	private ArrayList<String> alNewMatchedDrivers = new ArrayList<String>();
	private HashMap<String,String> mapResultXML = new HashMap<String,String>();
	
	private ArrayList<String> alMatchedDetourList = new ArrayList<String>();
	private ArrayList<String> alMatchedDriverTreepRequestIdList = new ArrayList<String>();
	
	
	private TextView infobannercount;
	
	private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	
	
	public SendTreepRequest(Activity activity, 
			String latDep, 
			String lngDep,
			String latDest, 
			String lngDest,
			String addressDep, 
			String addressDest,
			ArrayList<String> alMatchedDrivers,
			ArrayList<String> alMatchedDriverTreepRequestIdList,
			ArrayList<String> alMatchedDetourList,
			TextView infobannercount){
		this.activity=activity;
		this.latDep=latDep;
		this.lngDep=lngDep;
		this.latDest=latDest;
		this.lngDest=lngDest;
		this.addressDep=addressDep;
		this.addressDest=addressDest;
		this.alMatchedDrivers=alMatchedDrivers;
		this.alMatchedDriverTreepRequestIdList=alMatchedDriverTreepRequestIdList;
		this.alMatchedDetourList=alMatchedDetourList;
		this.infobannercount=infobannercount;
	}
	
	String ArrayToString (ArrayList<String> array){
		StringBuilder arrayToString = new StringBuilder();
		
		if(array.size() != 0){
			for(int i=0 ; i < array.size() ; i++ ){
				if(i != array.size()-1){
					arrayToString.append(array.get(i)).append(",");
				}
				else{
					arrayToString.append(array.get(i));
				}
			}
		}
		return arrayToString.toString();
		
	}
	
	boolean saveSharedPrefArray(Activity activity, String arrayName, ArrayList<String> array) {  
		
		StringBuilder arrayToString = new StringBuilder();
		
		if(array.size() != 0){
			for(int i=0 ; i < array.size() ; i++ ){
				if(i != array.size()-1){
					arrayToString.append(array.get(i)).append(",");
				}
				else{
					arrayToString.append(array.get(i));
				}
			}
		}
		try{
			SharedPreferences prefs =  activity.getSharedPreferences(MainActivity.KEY_SHAREDPREFTREEP, 0);  
		    SharedPreferences.Editor editor = prefs.edit();  
		    editor.putString(MainActivity.KEY_SHAREDPREFMATCHEDARRAY, ArrayToString(array));  
		    return editor.commit(); 
		}
		catch(NullPointerException e){
			return false;
		}
	     
	} 
	
	ArrayList<String> loadSharedPrefArray(Activity activity, String arrayName) { 
		ArrayList<String> stringToArray = new ArrayList<String>();
		List<String> list = new ArrayList<String>();
		try{
			SharedPreferences prefs = activity.getSharedPreferences(MainActivity.KEY_SHAREDPREFTREEP, 0);  
		    String sharedPrefString = prefs.getString(arrayName, "");
		    
		    String [] stringToList= sharedPrefString.split(",");
		    list = Arrays.asList(stringToList); 
		    for(int i = 0 ; i < list.size() ; i ++){
		    	stringToArray.add(list.get(i));
		    }
		}
		catch(NullPointerException e){
		}
	    
	    return stringToArray;  
	}
	
	boolean removeTreepAppSharedPref(String name, Activity activity){
		try{
			SharedPreferences preferences = activity.getSharedPreferences(MainActivity.KEY_SHAREDPREFTREEP, 0);
			return preferences.edit().remove(name).commit();
        }
        catch(NullPointerException e){
        	return false;
        }
		
		
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(LoginDisplayActivity.userId == null || LoginDisplayActivity.userId == "" ){
			this.cancel(true);
			Intent i = new Intent(activity, LoginDisplayActivity.class);
    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    		activity.startActivity(i);
		}
		else{
			alStoredMatchedDrivers = loadSharedPrefArray(activity, MainActivity.KEY_SHAREDPREFMATCHEDARRAY);
			if(alMatchedDrivers != null){
				for(int i=0 ; i < alMatchedDrivers.size() ; i++){
					if(!alStoredMatchedDrivers.contains(alMatchedDrivers.get(i))){
						alNewMatchedDrivers.add(alMatchedDrivers.get(i));
					}
				}
				
				/*
				for(int i = 0 ; i < alMatchedDrivers.size(); i++){
					MainActivity.displayToast(alMatchedDrivers.get(i) + " : " + alMatchedDriverTreepRequestIdList.get(i) );
				}
				*/
				
				
				
				nameValuePairs.add(new BasicNameValuePair("matchedlist", ArrayToString(alMatchedDrivers)));
				nameValuePairs.add(new BasicNameValuePair("matcheddrivertreeprequestidlist", ArrayToString(alMatchedDriverTreepRequestIdList)));
				
				//MainActivity.displayToast(ArrayToString(alMatchedDriverTreepRequestIdList));
			}
			
			if(alMatchedDetourList != null){
				nameValuePairs.add(new BasicNameValuePair("matcheddetourlist", ArrayToString(alMatchedDetourList)));
			}
			
			/*
			MainActivity.displayToast("before saving : " + loadSharedPrefArray(activity, MainActivity.KEY_SHAREDPREFMATCHEDARRAY));
			ArrayList<String> test = new ArrayList<String>();
			test.add("1");
			test.add("2");
			test.add("3");
			test.add("4");
			saveSharedPrefArray(activity, MainActivity.KEY_SHAREDPREFMATCHEDARRAY, test);
			MainActivity.displayToast("after saving : " + loadSharedPrefArray(activity,MainActivity.KEY_SHAREDPREFMATCHEDARRAY));
			removeTreepAppSharedPref(MainActivity.KEY_SHAREDPREFMATCHEDARRAY, activity);
			MainActivity.displayToast("after remove : " + loadSharedPrefArray(activity,MainActivity.KEY_SHAREDPREFMATCHEDARRAY));
			*/
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values){
		super.onProgressUpdate(values);
		//Toast.makeText(ApplicationContextProvider.getContext(), "traitement asynchrone", Toast.LENGTH_LONG).show();
		
	}

	@Override
	protected HashMap<String, String> doInBackground(Void... arg0) {

		HashMap<String,String> mapTimeout = new HashMap<String,String>();
		mapTimeout.put(MainActivity.KEY_TIMEOUT, "1");
		
		if (android.os.Build.VERSION.SDK_INT > 9) 
    	{ StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }
	
    	XMLParser parser = new XMLParser(nameValuePairs);
    	String xml;
		try {
			xml = parser.getXmlFromUrl(MainActivity.URL_STORETREEPREQUEST +"?userid=" + LoginDisplayActivity.userId
																	+ "&latdep=" + latDep 
																	+ "&lngdep=" + lngDep 
																	+ "&latdest=" + latDest 
																	+ "&lngdest=" + lngDest 
																	+ "&addressdep=" + URLEncoder.encode(addressDep, "UTF-8") 
																	+ "&addressdest=" + URLEncoder.encode(addressDest, "UTF-8")
																	);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			xml = null;
		}
		
    	if(xml == null){
    		mapResultXML = null;
    	}
    	else{
    		if(xml == "timeout"){
    			
    			mapResultXML.put(MainActivity.KEY_TIMEOUT, "1");
    		}
    		
    		else{
    			
    			Document doc = null;
				try{
					doc = parser.getDomElement(xml); // getting DOM element
				}
				catch(DOMException e){
					
				}
				
    			if(doc == null){
    				mapResultXML = null;
    			}
    			else{
    				NodeList nl_xml = doc.getElementsByTagName(MainActivity.KEY_XML);
					// looping through all xml nodes <KEY_USER>
					for (int i = 0; i < nl_xml.getLength(); i++) {
						// creating new HashMap
						
						Element e = (Element) nl_xml.item(i);
						// adding each child node to HashMap key => value
						mapResultXML.put(MainActivity.KEY_ERRCODE, parser.getValue(e, MainActivity.KEY_ERRCODE));
						mapResultXML.put(MainActivity.KEY_DRIVERMATCHCOUNT, parser.getValue(e, MainActivity.KEY_DRIVERMATCHCOUNT));
						
					}
    			}
    		}
    	}
		
		return mapResultXML;
	}
	@SuppressLint("NewApi")
	protected void onPostExecute(HashMap<String, String>  result) {
		
		
		//MainActivity.displayToast("dep : " +addressDep +"\ndest : " + addressDest);
		
		if(result == null || result.containsKey(MainActivity.KEY_TIMEOUT)){
    		
			//MainActivity.displayToast("Veuillez réessayer plus tard : " + result.get(MainActivity.KEY_ERRCODE));
			//MainActivity.displayToast(R.string.httpTimeOut);
			MainActivity.displayToast(R.string.httpTimeOut);
			
			activity.setProgressBarIndeterminateVisibility(false);
			
			
		}
		else{
			if(result.get(MainActivity.KEY_ERRCODE).equals("000")){
				
				removeTreepAppSharedPref(MainActivity.KEY_SHAREDPREFMATCHEDARRAY, activity);
				
				if(alMatchedDrivers != null){
					saveSharedPrefArray(activity, MainActivity.KEY_SHAREDPREFMATCHEDARRAY, alMatchedDrivers);
					
					
					
					ArrayList <String> alMatchedDriverChannels = new ArrayList<String>();
					
					for(int i=0;i < alMatchedDrivers.size(); i++){
						alMatchedDriverChannels.add("user" + alMatchedDrivers.get(i));
					}
					
					JSONObject object = new JSONObject();
			        try {
	                    object.put("alert", "Cliquez ici pour voir sa demande");
	                    object.put("title", LoginDisplayActivity.userFirstname + " " + LoginDisplayActivity.userLastname.charAt(0) + ". demande un treep");
	                    object.put("action", "Notif");                   
	                    ParsePush pushMessageClient = new ParsePush();
	                    pushMessageClient.setData(object);
	                    pushMessageClient.setChannels(alMatchedDriverChannels);
	                    pushMessageClient.sendInBackground(new SendCallback() {
		                    	

							@Override
							public void done(com.parse.ParseException e) {
								// TODO Auto-generated method stub
							}
	                    });
			        } 
			        catch (JSONException e){
			        	 e.printStackTrace();
			        }
				}
				
				
	        	Intent i = new Intent(ApplicationContextProvider.getContext(), MainActivity.class);
	    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	    		ApplicationContextProvider.getContext().startActivity(i);
			}
			else{
				if(result.get(MainActivity.KEY_ERRCODE).equals("100")){

					ArrayList <String> alMatchedDriverChannels = new ArrayList<String>();
					
					if(alNewMatchedDrivers.size() != 0){
						for(int i=0;i < alNewMatchedDrivers.size(); i++){
							alMatchedDriverChannels.add("user" + alNewMatchedDrivers.get(i));
						}
						JSONObject object = new JSONObject();
				        try {
		                    object.put("alert", "Cliquez ici pour voir sa demande");
		                    object.put("title", LoginDisplayActivity.userFirstname + " " + LoginDisplayActivity.userLastname.charAt(0) + ". demande un treep");
		                    object.put("action", "Notif");                   
		                    ParsePush pushMessageClient = new ParsePush();
		                    pushMessageClient.setData(object);
		                    pushMessageClient.setChannels(alMatchedDriverChannels);
		                    pushMessageClient.sendInBackground(new SendCallback() {
			                    	

								@Override
								public void done(com.parse.ParseException e) {
									// TODO Auto-generated method stub
								}
		                    });
				        } 
				        catch (JSONException e){
				        	 e.printStackTrace();
				        }
				        
				        try{
				        	removeTreepAppSharedPref(MainActivity.KEY_SHAREDPREFMATCHEDARRAY, activity);
				        }
				        catch(NullPointerException e){
				        	
				        }
				        saveSharedPrefArray(activity, MainActivity.KEY_SHAREDPREFMATCHEDARRAY, alMatchedDrivers);
					}
				}
				else{
					MainActivity.displayToast("Veuillez réessayer plus tard : " + result.get(MainActivity.KEY_ERRCODE));
				}
			}
			if(infobannercount != null){
				infobannercount.setText(Html.fromHtml("Drivers contactés : <b>" + result.get(MainActivity.KEY_DRIVERMATCHCOUNT) + "</b>"));
			}
			try{
				activity.setProgressBarIndeterminateVisibility(false);
			}
			catch(NullPointerException e){
				
			}
		}
	}
}
