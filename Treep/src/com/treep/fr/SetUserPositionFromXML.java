package com.treep.fr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.StrictMode;

public class SetUserPositionFromXML extends AsyncTask<Void, Integer, HashMap<String, String>> {
	
	private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	private HashMap<String,String> mapErrCode = new HashMap<String,String>();
	private Activity activity;
	private double lat;
	private double lng;
	
	public SetUserPositionFromXML(Activity activity, double lat, double lng){
		this.activity=activity;
		this.lat=lat;
		this.lng=lng;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(lat < 48.5 || lat > 49.4 ||  lng < 1.5 || lng > 3.1){
			this.cancel(true);
		}
		else{
			nameValuePairs.add(new BasicNameValuePair("userid", LoginDisplayActivity.userId));
			nameValuePairs.add(new BasicNameValuePair("lat", Double.toString(lat)));
			nameValuePairs.add(new BasicNameValuePair("lng", Double.toString(lng)));
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
		String xml = parser.getXmlFromUrl(MainActivity.URL_SETUSERPOSITION);
		//debug = xml;
		
		if(xml == null){
			mapErrCode = null;
		}
		else{
			if(xml == "timeout"){
				
				mapErrCode.put(MainActivity.KEY_TIMEOUT, "1");	
			}
			
			else{
				
				Document doc = null;
				try{
					doc = parser.getDomElement(xml); // getting DOM element
				}
				catch(DOMException e){
					
				}
				
				if(doc == null){
					mapErrCode = null;
				}
				else{
					NodeList nl_xml = doc.getElementsByTagName(MainActivity.KEY_XML);
					// looping through all xml nodes <KEY_USER>
					for (int i = 0; i < nl_xml.getLength(); i++) {
						// creating new HashMap
						
						Element e = (Element) nl_xml.item(i);
						// adding each child node to HashMap key => value
						mapErrCode.put(MainActivity.KEY_ERRCODE, parser.getValue(e, MainActivity.KEY_ERRCODE));
					}
				}
			}
		}
		
		return mapErrCode;
	}

	@SuppressLint("NewApi")
	protected void onPostExecute(HashMap<String, String>  result) {
		
	}
}