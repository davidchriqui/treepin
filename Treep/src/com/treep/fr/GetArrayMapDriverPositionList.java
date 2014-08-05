package com.treep.fr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;

import com.google.android.gms.maps.model.Marker;

public class GetArrayMapDriverPositionList extends AsyncTask<Void, Integer, ArrayList<HashMap<String, String>>> {
	
	private Activity activity;
	private ArrayList<HashMap<String, String>> alMapDriverPosition = new ArrayList<HashMap<String, String>>();
	
	public GetArrayMapDriverPositionList(Activity activity){
		this.activity=activity;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	 	
		
	}

	@Override
	protected void onProgressUpdate(Integer... values){
		super.onProgressUpdate(values);
		
	}

	@Override
	protected ArrayList<HashMap<String, String>> doInBackground(Void... arg0) {
		HashMap<String,String> mapTimeout = new HashMap<String,String>();
		mapTimeout.put(MainActivity.KEY_TIMEOUT, "1");
		
		if (android.os.Build.VERSION.SDK_INT > 9) 
    	{ StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }
	
    	XMLParser parser = new XMLParser();
    	String xml = parser.getXmlFromUrl(MainActivity.URL_USERINFO + "?userid=" + LoginDisplayActivity.userId +"&lat=" + MainActivity.myLatitude + "&lng=" + MainActivity.myLongitude);
		
		if(xml == null){
			alMapDriverPosition = null;
		}
		else{
			if(xml == "timeout"){
				alMapDriverPosition.add(mapTimeout);
			}
			
			else{
				
				Document doc = null;
				try{
					doc = parser.getDomElement(xml); // getting DOM element
				}
				catch(DOMException e){
					
				}
				
				if(doc == null){
					alMapDriverPosition = null;
				}
				else{
					
					
					NodeList nl_DriverPositionList = doc.getElementsByTagName(MainActivity.KEY_DRIVERPOSITIONLIST);
					for (int i = 0; i < nl_DriverPositionList.getLength(); i++) {
						Element e = (Element) nl_DriverPositionList.item(i);
						NodeList nl_DriverPosition = e.getElementsByTagName(MainActivity.KEY_DRIVERPOSITION);
						// looping through all xml nodes <KEY_USER>
						for (int j = 0; j < nl_DriverPosition.getLength(); j++) {
							// creating new HashMap
							HashMap<String, String> mapDriverPosition = new HashMap<String, String>();
							Element t = (Element) nl_DriverPosition.item(j);
							// adding each child node to HashMap key => value
							mapDriverPosition.put(MainActivity.KEY_USERID, parser.getValue(t, MainActivity.KEY_USERID));
							mapDriverPosition.put(MainActivity.KEY_FIRSTNAME, parser.getValue(t, MainActivity.KEY_FIRSTNAME));
							mapDriverPosition.put(MainActivity.KEY_LASTNAME, parser.getValue(t, MainActivity.KEY_LASTNAME));
							mapDriverPosition.put(MainActivity.KEY_NBTREEP, parser.getValue(t, MainActivity.KEY_NBTREEP));
							mapDriverPosition.put(MainActivity.KEY_RATING, parser.getValue(t, MainActivity.KEY_RATING));
							mapDriverPosition.put(MainActivity.KEY_LATITUDE, parser.getValue(t, MainActivity.KEY_LATITUDE));
							mapDriverPosition.put(MainActivity.KEY_LONGITUDE, parser.getValue(t, MainActivity.KEY_LONGITUDE));
							mapDriverPosition.put(MainActivity.KEY_ISBUSY, parser.getValue(t, MainActivity.KEY_ISBUSY));
							
							// adding HashList to ArrayList
							
							alMapDriverPosition.add(mapDriverPosition);
						}
					}
				}
			}
		}
		
		return alMapDriverPosition;
		
	}

	
	@SuppressLint("NewApi")
	protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
		
		if(result == null){
			MainActivity.displayToast(R.string.httpTimeOut);
		}
		else{
		}
	}
}