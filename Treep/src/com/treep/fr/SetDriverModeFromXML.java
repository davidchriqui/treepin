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

import com.treep.fr.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.BadTokenException;
import android.widget.Button;
import android.widget.Toast;



public class SetDriverModeFromXML extends AsyncTask<Void, Integer, HashMap<String, HashMap<String, String>>> {
	
	private Activity activity;
	private HashMap<String, HashMap<String, String>> mapMainUserInfo = new HashMap<String, HashMap<String, String>>();
	private HashMap<String, String> mapUserInfo = new HashMap<String, String>();
	private HashMap<String, String> mapDriverUserInfo = new HashMap<String, String>();
	private HashMap<String, String> mapTreepOrderInfo = new HashMap<String, String>();
	
	private HashMap<String, String> mapResultUserInfo = new HashMap<String, String>();
	private HashMap<String, String> mapResultDriverUserInfo = new HashMap<String, String>();
	private HashMap<String, String> mapResultTreepOrderInfo = new HashMap<String, String>();
	
	
	public SetDriverModeFromXML(Activity activity){
		this.activity=activity;
	}
	
	private void setDriverModeOn(){
		SetDriverModeOnFromXML setDriverModeOnFromXML = new SetDriverModeOnFromXML(activity);
		setDriverModeOnFromXML.execute();
	}
	private void setDriverModeOff(){
		SetDriverModeOffFromXML setDriverModeOffFromXML = new SetDriverModeOffFromXML(activity);
		setDriverModeOffFromXML.execute();
	}
	
	@SuppressLint("NewApi")
		private void launchSetDriverInformationFragment(){
		MainActivity.fragment = new SetDriverInformationFragment();
		FragmentManager fragmentManager = activity.getFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container,MainActivity.fragment ).commit();
	
	}

	@SuppressLint("NewApi")
	private void launchSendDocumentFragment(){
	MainActivity.fragment = new SendDocumentFragment();
	FragmentManager fragmentManager = activity.getFragmentManager();
    fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container,MainActivity.fragment ).commit();

}
	
	@SuppressLint("NewApi")
	private void LaunchMyInformationFragment(){
		MainActivity.fragment = new MyInformationFragment();
		
		Bundle data = new Bundle();
		
		FragmentManager fragmentManager = activity.getFragmentManager();
		data.putSerializable("mapUserInfo",mapResultUserInfo);
		MainActivity.fragment.setArguments(data);
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container,MainActivity.fragment ).commit();
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
			activity.setProgressBarIndeterminateVisibility(true);
			mapMainUserInfo = new HashMap<String, HashMap<String, String>>();
			mapUserInfo = new HashMap<String, String>();
			mapDriverUserInfo = new HashMap<String, String>();
			mapTreepOrderInfo = new HashMap<String, String>();
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values){
		super.onProgressUpdate(values);
		//Toast.makeText(ApplicationContextProvider.getContext(), "traitement asynchrone", Toast.LENGTH_LONG).show();
		
	}

	@Override
	protected HashMap<String, HashMap<String, String>> doInBackground(Void... arg0) {

		HashMap<String,String> mapTimeout = new HashMap<String,String>();
		mapTimeout.put(MainActivity.KEY_TIMEOUT, "1");
		
		if (android.os.Build.VERSION.SDK_INT > 9) 
    	{ StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }
	
    	XMLParser parser = new XMLParser();
    	String xml = parser.getXmlFromUrl(MainActivity.URL_USERINFO + "?userid=" + LoginDisplayActivity.userId + "&lat=" + MainActivity.myLatitude + "&lng=" + MainActivity.myLongitude);
		
    	if(xml == null){
    		mapMainUserInfo = null;
    	}
    	else{
    		if(xml == "timeout"){
    			
    			mapUserInfo.put(MainActivity.KEY_TIMEOUT, "1");
    			mapTreepOrderInfo.put(MainActivity.KEY_TIMEOUT, "1");
    			mapDriverUserInfo.put(MainActivity.KEY_TIMEOUT, "1");	
    		}
    		
    		else{
    			
    			Document doc = null;
				try{
					doc = parser.getDomElement(xml); // getting DOM element
				}
				catch(DOMException e){
					
				}
    			
    			if(doc == null){
    				mapUserInfo = null;
    				mapDriverUserInfo = null;
    				mapTreepOrderInfo = null;
    			}
    			else{
    				NodeList nl_User = doc.getElementsByTagName(MainActivity.KEY_USER);
    				// looping through all xml nodes <KEY_USER>
    				for (int i = 0; i < nl_User.getLength(); i++) {
    					// creating new HashMap
    					
    					Element e = (Element) nl_User.item(i);
    					// adding each child node to HashMap key => value
    					mapUserInfo.put(MainActivity.KEY_USERTEL, parser.getValue(e, MainActivity.KEY_USERTEL));
    					mapUserInfo.put(MainActivity.KEY_USERSEXE, parser.getValue(e, MainActivity.KEY_USERSEXE));
    					mapUserInfo.put(MainActivity.KEY_ISDRIVER, parser.getValue(e, MainActivity.KEY_ISDRIVER));
    					mapUserInfo.put(MainActivity.KEY_ISVALIDATED, parser.getValue(e, MainActivity.KEY_ISVALIDATED));
    					mapUserInfo.put(MainActivity.KEY_DRIVERMODE, parser.getValue(e, MainActivity.KEY_DRIVERMODE));
    				}
    				
    				NodeList nl_DriverUserInfo = doc.getElementsByTagName(MainActivity.KEY_DRIVERUSERINFO);
					// looping through all xml nodes <KEY_USER>
					for (int i = 0; i < nl_DriverUserInfo.getLength(); i++) {
						// creating new HashMap
						
						Element e = (Element) nl_DriverUserInfo.item(i);
						// adding each child node to HashMap key => valuedrivertreepnowconfirmedcount
						mapDriverUserInfo.put(MainActivity.KEY_DRIVERTREEPREQUESTED, parser.getValue(e, MainActivity.KEY_DRIVERTREEPREQUESTED));
					}
    				
    				NodeList nl_TreepOrderInfo = doc.getElementsByTagName(MainActivity.KEY_TREEPORDERINFO);
    				// looping through all xml nodes <KEY_USER>
    				for (int i = 0; i < nl_TreepOrderInfo.getLength(); i++) {
    					// creating new HashMap
    					Element e = (Element) nl_TreepOrderInfo.item(i);
    					// adding each child node to HashMap key => value
    					mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPORDERED, parser.getValue(e, MainActivity.KEY_ISTREEPORDERED));
    					
    				}
    				
    				mapMainUserInfo.put("mapUserInfo", mapUserInfo);
    				mapMainUserInfo.put("mapTreepOrderInfo", mapTreepOrderInfo);
    				mapMainUserInfo.put("mapDriverUserInfo", mapDriverUserInfo);
    			}
    		}
    	}
		return mapMainUserInfo;
	}

	@SuppressLint("NewApi")
	protected void onPostExecute(HashMap<String, HashMap<String, String>>  result) {
		
		if(result == null){
			DialogTimeout dialogTimeout = new DialogTimeout(activity);
			dialogTimeout.setCancelable(false);
			dialogTimeout.setCanceledOnTouchOutside(false);
			dialogTimeout.show();
		}
		else{
			mapResultUserInfo = result.get("mapUserInfo");
			mapResultDriverUserInfo = result.get("mapDriverUserInfo");
			mapResultTreepOrderInfo = result.get("mapTreepOrderInfo");
			
			if(mapResultUserInfo == null || mapResultUserInfo.containsKey(MainActivity.KEY_TIMEOUT)
					|| mapResultTreepOrderInfo == null || mapResultTreepOrderInfo.containsKey(MainActivity.KEY_TIMEOUT)
					|| mapResultDriverUserInfo== null || mapResultDriverUserInfo.containsKey(MainActivity.KEY_TIMEOUT)){
	    		
				DialogTimeout dialogTimeout = new DialogTimeout(activity);
				dialogTimeout.setCancelable(false);
				dialogTimeout.setCanceledOnTouchOutside(false);
				dialogTimeout.show();
	    		
	    	}
			else{
				if(mapResultUserInfo.get(MainActivity.KEY_USERTEL) != null){
					if(Boolean.parseBoolean(mapResultUserInfo.get(MainActivity.KEY_DRIVERMODE))){
						if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_DRIVERTREEPREQUESTED))){
							MainActivity.displayToast("Vous avez un Treep en cours.\nLe mode driver n'est pas désactivable pour le moment");
						}
						else{
							setDriverModeOff();
						}
					}
					else{
						if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPORDERED))){
							MainActivity.displayToast("Vous avez un Treep en cours.\nLe mode driver n'est pas activable pour le moment");
						}
						else{
							if(Boolean.parseBoolean(mapResultUserInfo.get(MainActivity.KEY_ISDRIVER))){
								if(Boolean.parseBoolean(mapResultUserInfo.get(MainActivity.KEY_ISVALIDATED))){
									setDriverModeOn();
								}
								else{
									launchSendDocumentFragment();
								}
							}
							else{
								launchSetDriverInformationFragment();
							}
						}
					}
				}
				else{
					LaunchMyInformationFragment();
				}
				activity.setProgressBarIndeterminateVisibility(false);
			}
		}	
	}
}
