package com.treep.fr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;

import com.parse.ParsePush;
import com.parse.SendCallback;



public class SetTreepBookFromXML extends AsyncTask<Void, Integer, HashMap<String, String>> {
	
	private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	private HashMap<String,String> mapErrCode = new HashMap<String,String>();
	private Activity activity;
	private double latdep;
	private double lngdep;
	private double latdest;
	private double lngdest;
	private Boolean pinkMode;
	
	public SetTreepBookFromXML(Activity activity, double latdep, double lngdep, double latdest, double lngdest, Boolean pinkMode){
		this.activity=activity;
		this.latdep=latdep;
		this.lngdep=lngdep;
		this.latdest=latdest;
		this.lngdest=lngdest;
		this.pinkMode=pinkMode;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		/*
		nameValuePairs.add(new BasicNameValuePair("userid", LoginDisplayActivity.userId));
		nameValuePairs.add(new BasicNameValuePair("latdep", ""+latdep));
		nameValuePairs.add(new BasicNameValuePair("lngdep", ""+lngdep));
		nameValuePairs.add(new BasicNameValuePair("latdest", ""+latdest));
		nameValuePairs.add(new BasicNameValuePair("lngdest", ""+lngdest));
		*/
		activity.setProgressBarIndeterminateVisibility(true);
		
		
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
    	//String xml = parser.getXmlFromUrl(MainActivity.URL_BOOKTREEP);
		String xml = parser.getXmlFromUrl(MainActivity.URL_BOOKTREEP + "?userid=" + LoginDisplayActivity.userId + "&latdep=" + latdep +"&lngdep="+ lngdep+"&latdest="+latdest+"&lngdest="+lngdest+"&pinkmode="+pinkMode);
		//debug = xml;
		if(xml == null){
			mapErrCode =null;
		}
		else{
			if(xml == "timeout"){
				
				mapErrCode.put(MainActivity.KEY_TIMEOUT, "1");	
			}
			else{
				
		         // getting XML from URL
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
		
		//MainActivity.displayToast(" latdep : " + Double.toString(latdep) +" lngdep : " + Double.toString(lngdep) + " latdest : " + Double.toString(latdest) + " lngdest : " + Double.toString(lngdest));
		
		if(result == null || result.containsKey(MainActivity.KEY_TIMEOUT)){
    		
			//MainActivity.displayToast("Veuillez réessayer plus tard : " + result.get(MainActivity.KEY_ERRCODE));
			MainActivity.displayToast(R.string.httpTimeOut);
			activity.setProgressBarIndeterminateVisibility(false);
			
			
		}
		else{	
			if(result.get(MainActivity.KEY_ERRCODE).equals("000")){
				
				JSONObject object = new JSONObject();
		        try {
                    object.put("alert", "Appuyez sur \"Liste des Treeps\"");
                    object.put("title", LoginDisplayActivity.userFirstname + " " + LoginDisplayActivity.userLastname.charAt(0) + ". demande un treep");
                    object.put("action", "Notif");                   
                    ParsePush pushMessageClient1 = new ParsePush();
                    pushMessageClient1.setData(object);
                    pushMessageClient1.setChannel("drivers");
                    pushMessageClient1.sendInBackground(new SendCallback() {
	                    	

						@Override
						public void done(com.parse.ParseException e) {
							// TODO Auto-generated method stub
							
						}
                    });
		        } 
		        catch (JSONException e){
		        	 e.printStackTrace();
		        }
				
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
