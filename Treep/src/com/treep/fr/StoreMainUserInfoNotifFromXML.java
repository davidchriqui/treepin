package com.treep.fr;



import java.util.HashMap;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.WindowManager.BadTokenException;



public class StoreMainUserInfoNotifFromXML extends AsyncTask<Void, Integer, HashMap<String, HashMap<String, String>>> {
	
	private Activity activity;
	private HashMap<String, HashMap<String, String>> mapMainUserInfo = new HashMap<String, HashMap<String, String>>();
	private HashMap<String, String> mapUserInfo = new HashMap<String, String>();
	private HashMap<String, String> mapTreepOrderInfo = new HashMap<String, String>();
	private HashMap<String, String> mapDriverUserInfo = new HashMap<String, String>();
	
	private HashMap<String, String> mapResultUserInfo = new HashMap<String, String>();
	private HashMap<String, String> mapResultTreepOrderInfo = new HashMap<String, String>();
	private HashMap<String, String> mapResultDriverUserInfo = new HashMap<String, String>();
	
	
	static Boolean driverMode = false;
	
	public StoreMainUserInfoNotifFromXML(Activity activity){
		this.activity=activity;
	}
	
	
	@SuppressLint("NewApi")
	private void LaunchMyMapFragment(){
		MainActivity.fragment = new HomeFragment();
		
		Bundle data = new Bundle();
		
		FragmentManager fragmentManager = activity.getFragmentManager();
		data.putSerializable("mapUserInfo",mapResultUserInfo);
		data.putSerializable("mapTreepOrderInfo",mapResultTreepOrderInfo);
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
			mapTreepOrderInfo = new HashMap<String, String>();
			mapDriverUserInfo = new HashMap<String, String>();
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
    	String xml = parser.getXmlFromUrl(MainActivity.URL_USERINFO + "?userid=" + LoginDisplayActivity.userId + "&latitude=1&longitude=1");
		
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
    				mapTreepOrderInfo = null;
    				mapDriverUserInfo = null;
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
    					mapUserInfo.put(MainActivity.KEY_DRIVERMODE, parser.getValue(e, MainActivity.KEY_DRIVERMODE));
    				}
    				
    				
    				
    				NodeList nl_DriverUserInfo = doc.getElementsByTagName(MainActivity.KEY_DRIVERUSERINFO);
    				// looping through all xml nodes <KEY_USER>
    				for (int i = 0; i < nl_DriverUserInfo.getLength(); i++) {
    					// creating new HashMap
    					
    					Element e = (Element) nl_DriverUserInfo.item(i);
    					// adding each child node to HashMap key => value
    					mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWCONFIRMED, parser.getValue(e, MainActivity.KEY_ISTREEPNOWCONFIRMED));
    					mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWACCEPTED, parser.getValue(e, MainActivity.KEY_ISTREEPNOWACCEPTED));
    					mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWRESPONSEACK, parser.getValue(e, MainActivity.KEY_ISTREEPNOWRESPONSEACK));
    					mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWFINISHED, parser.getValue(e, MainActivity.KEY_ISTREEPNOWFINISHED));
    					mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWACCEPTEDCANCELEDBYDRIVER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWACCEPTEDCANCELEDBYDRIVER));
    					mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWACCEPTEDCANCELEDBYTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWACCEPTEDCANCELEDBYTREEPER));
    					mapDriverUserInfo.put(MainActivity.KEY_ISTREEPERONBOARDFROMDRIVER, parser.getValue(e, MainActivity.KEY_ISTREEPERONBOARDFROMDRIVER));
    					mapDriverUserInfo.put(MainActivity.KEY_ISTREEPERONBOARDFROMTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPERONBOARDFROMTREEPER));
    				}
    				
    				
    				
    				NodeList nl_TreepOrderInfo = doc.getElementsByTagName(MainActivity.KEY_TREEPORDERINFO);
    				// looping through all xml nodes <KEY_USER>
    				for (int i = 0; i < nl_TreepOrderInfo.getLength(); i++) {
    					// creating new HashMap
    					Element e = (Element) nl_TreepOrderInfo.item(i);
    					// adding each child node to HashMap key => value
    					mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPORDERED, parser.getValue(e, MainActivity.KEY_ISTREEPORDERED));
    					mapTreepOrderInfo.put(MainActivity.KEY_TREEPID, parser.getValue(e, MainActivity.KEY_TREEPID));
    					mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPNOWCONFIRMED, parser.getValue(e, MainActivity.KEY_ISTREEPNOWCONFIRMED));
    					mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPNOWCANCELEDBYDRIVER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWCANCELEDBYDRIVER));
    					mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPNOWCANCELEDBYTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWCANCELEDBYTREEPER));
    					mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPERONBOARDFROMDRIVER, parser.getValue(e, MainActivity.KEY_ISTREEPERONBOARDFROMDRIVER));
    					mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPERONBOARDFROMTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPERONBOARDFROMTREEPER));
    					mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPNOWFINISHED, parser.getValue(e, MainActivity.KEY_ISTREEPNOWFINISHED));
    					mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPNOWRATEDBYTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWRATEDBYTREEPER));
    					mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPNOWRATEDBYDRIVER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWRATEDBYDRIVER));
    					mapTreepOrderInfo.put(MainActivity.KEY_LATDEPNOW, parser.getValue(e, MainActivity.KEY_LATDEPNOW));
    					mapTreepOrderInfo.put(MainActivity.KEY_LNGDEPNOW, parser.getValue(e, MainActivity.KEY_LNGDEPNOW));
    					mapTreepOrderInfo.put(MainActivity.KEY_LATDESTNOW, parser.getValue(e, MainActivity.KEY_LATDESTNOW));
    					mapTreepOrderInfo.put(MainActivity.KEY_LNGDESTNOW, parser.getValue(e, MainActivity.KEY_LNGDESTNOW));
    					mapTreepOrderInfo.put(MainActivity.KEY_ADDRESSDEPNOW, parser.getValue(e, MainActivity.KEY_ADDRESSDEPNOW));
    					mapTreepOrderInfo.put(MainActivity.KEY_ADDRESSDESTNOW, parser.getValue(e, MainActivity.KEY_ADDRESSDESTNOW));
    					mapTreepOrderInfo.put(MainActivity.KEY_ADDRESSDESTNOW, parser.getValue(e, MainActivity.KEY_ADDRESSDESTNOW));
    					mapTreepOrderInfo.put(MainActivity.KEY_DRIVERID, parser.getValue(e, MainActivity.KEY_DRIVERID));
    					mapTreepOrderInfo.put(MainActivity.KEY_PRICE, parser.getValue(e, MainActivity.KEY_PRICE));
    					mapTreepOrderInfo.put(MainActivity.KEY_TREEPORDERTIMEREMAINING, parser.getValue(e, MainActivity.KEY_TREEPORDERTIMEREMAINING));
    					
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
			try{
				dialogTimeout.show();
			}
			catch(BadTokenException e){
				MainActivity.displayToast(R.string.httpTimeOut);
			}
			
		}
		else{
			mapResultUserInfo = result.get("mapUserInfo");
			mapResultTreepOrderInfo = result.get("mapTreepOrderInfo");
			mapResultDriverUserInfo = result.get("mapDriverUserInfo");
			
			
			if(mapResultUserInfo == null || mapResultUserInfo.containsKey(MainActivity.KEY_TIMEOUT) 
					|| mapResultTreepOrderInfo == null || mapResultTreepOrderInfo.containsKey(MainActivity.KEY_TIMEOUT)
					|| mapResultDriverUserInfo== null || mapResultDriverUserInfo.containsKey(MainActivity.KEY_TIMEOUT)){
	    		
				DialogTimeout dialogTimeout = new DialogTimeout(activity);
				try{
					dialogTimeout.show();
				}
				catch(BadTokenException e){
					MainActivity.displayToast(R.string.httpTimeOut);
				}
	    	}
			else{
				MainActivity.driverMode=Boolean.parseBoolean(mapResultUserInfo.get(MainActivity.KEY_DRIVERMODE));
				if(Boolean.parseBoolean(mapResultUserInfo.get(MainActivity.KEY_DRIVERMODE))){
					MainActivity.navDrawerItems.remove(MainActivity.navDrawerItems.size()-1);
					MainActivity.navDrawerItems.add(new NavDrawerItem(MainActivity.navMenuTitles[5], MainActivity.navMenuSubTitles[5],R.drawable.drivermodeon));
					MainActivity.adapter.notifyDataSetChanged();
				}
				else{
					MainActivity.navDrawerItems.remove(MainActivity.navDrawerItems.size()-1);
					MainActivity.navDrawerItems.add(new NavDrawerItem(MainActivity.navMenuTitles[5], MainActivity.navMenuSubTitles[5], R.drawable.drivermodeoff));
					MainActivity.adapter.notifyDataSetChanged();
				}
				
				if(mapResultUserInfo.get(MainActivity.KEY_USERTEL).length() > 0){
					if(!Boolean.parseBoolean(mapResultUserInfo.get(MainActivity.KEY_DRIVERMODE))){
						if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPORDERED))){
							if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPNOWCONFIRMED))){
								if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPNOWCANCELEDBYDRIVER))){
									MainActivity.displayToast("Vous avez une réservation immédiate en cours...");
								}
								else{
									if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPERONBOARDFROMDRIVER))){
										if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPERONBOARDFROMTREEPER))){
											if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPNOWFINISHED))){
												if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPNOWRATEDBYDRIVER))){
													MainActivity.displayToast("Vous devez appuyer sur le bouton OK pour continuer");
												}
												else{
													MainActivity.displayToast("Vous devez évaluer votre conducteur pour continuer");
												}
											}
											else{
												MainActivity.displayToast("Vous vous ennuyez ?");
											}
										}
										else{
											MainActivity.displayToast("Veuillez confirmer que vous vous trouvez bien à bord du véhicule");
										}
									}
									else{
										MainActivity.displayToast("Vous avez une réservation immédiate en cours...");
									}
								}
							}
							else{
								activity.setProgressBarIndeterminateVisibility(true);
		         				StoreTreepResponseListFromXML storeTreepResponseListFromXML = new StoreTreepResponseListFromXML(activity, mapResultTreepOrderInfo.get(MainActivity.KEY_TREEPID));
		         				storeTreepResponseListFromXML.execute();
		            	        MainActivity.initposition = 6;
							}
						}
						else{
							MainActivity.displayToast("Vous n'avez aucune réservation immédiate en cours...");
						}
					}
					else{
						if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_ISTREEPNOWCONFIRMED))){
							if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_ISTREEPNOWACCEPTED))){
								MainActivity.displayToast("Vous avez un trajet ou une réponse en cours.");
							}
							else{
								activity.setProgressBarIndeterminateVisibility(true);
								driverMode = true;
		         				StoreTreepDriverResponseListFromXML storeTreepDriverResponseListFromXML = new StoreTreepDriverResponseListFromXML(activity);
		         				storeTreepDriverResponseListFromXML.execute();
		         				MainActivity.initposition = 6;
							}
						}
						else{
							activity.setProgressBarIndeterminateVisibility(true);
							driverMode = true;
	         				StoreTreepDriverResponseListFromXML storeTreepDriverResponseListFromXML = new StoreTreepDriverResponseListFromXML(activity);
	         				storeTreepDriverResponseListFromXML.execute();
	         				MainActivity.initposition = 6;
						}
					}
				}
				else{
					MainActivity.displayToast("Vous devez entrer vos informations pour continuer");
				}
				
				activity.setProgressBarIndeterminateVisibility(false);
				
			}
		}
	}
}
