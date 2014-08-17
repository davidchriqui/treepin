package com.treep.fr;



import java.util.HashMap;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;



public class SendTreepDriverRequest extends AsyncTask<Void, Integer, HashMap<String, String>> {
	
	private Activity activity;
	private String latDep;
	private String lngDep;
	private String latDest;
	private String lngDest;
	private Boolean pinkMode;
	private HashMap<String,String> mapResultXML = new HashMap<String,String>();
	
	
	
	
	public SendTreepDriverRequest(Activity activity, 
			String latDep, 
			String lngDep,
			String latDest, 
			String lngDest){
		this.activity=activity;
		this.latDep=latDep;
		this.lngDep=lngDep;
		this.latDest=latDest;
		this.lngDest=lngDest;
		this.pinkMode=pinkMode;
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
	
    	XMLParser parser = new XMLParser();
    	String xml;
		
		xml = parser.getXmlFromUrl(MainActivity.URL_TREEPDRIVERREQUEST +"?userid=" + LoginDisplayActivity.userId
																+ "&latdep=" + latDep 
																+ "&lngdep=" + lngDep 
																+ "&latdest=" + latDest 
																+ "&lngdest=" + lngDest
																+ "&pinkmode=" + String.valueOf(pinkMode)
																);
			
		
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
    		
			MainActivity.displayToast("Veuillez réessayer plus tard : " + result.get(MainActivity.KEY_ERRCODE));
			//MainActivity.displayToast(R.string.httpTimeOut);
			//MainActivity.displayToast(R.string.httpTimeOut);
			
			activity.setProgressBarIndeterminateVisibility(false);
			
			
		}
		else{
			if(result.get(MainActivity.KEY_ERRCODE).equals("000")){
				
				
	        	Intent i = new Intent(ApplicationContextProvider.getContext(), MainActivity.class);
	    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	    		ApplicationContextProvider.getContext().startActivity(i);
			}
			else{
				MainActivity.displayToast("Veuillez réessayer plus tard : " + result.get(MainActivity.KEY_ERRCODE));
			}
			activity.setProgressBarIndeterminateVisibility(false);
		}
	}
}
