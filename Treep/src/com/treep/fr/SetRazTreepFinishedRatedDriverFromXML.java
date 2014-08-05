package com.treep.fr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.treep.fr.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;



public class SetRazTreepFinishedRatedDriverFromXML extends AsyncTask<Void, Integer, HashMap<String, String>> {
	
	private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	private HashMap<String,String> mapErrCode = new HashMap<String,String>();
	private Activity activity;
	private String treepId;
	
	public SetRazTreepFinishedRatedDriverFromXML(Activity activity, String treepId){
		this.activity=activity;
		this.treepId=treepId;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
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
		String xml = parser.getXmlFromUrl(MainActivity.URL_SETRAZTREEPFINISHEDRATEDFORDRIVER + "?userid=" + LoginDisplayActivity.userId + "&treepid=" + treepId);
		
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
		
		if(result == null || result.containsKey(MainActivity.KEY_TIMEOUT)){
    		
			MainActivity.displayToast(R.string.httpTimeOut);
			activity.setProgressBarIndeterminateVisibility(false);
			
		}
		else{	
			if(result.get(MainActivity.KEY_ERRCODE).equals("000")){
				Intent i = new Intent(ApplicationContextProvider.getContext(), MainActivity.class);
	    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	    		ApplicationContextProvider.getContext().startActivity(i);
			}
			else{
				MainActivity.displayToast("Veuillez réessayer : " + result.get(MainActivity.KEY_ERRCODE));
			}
			activity.setProgressBarIndeterminateVisibility(false);
		}
	}
}
