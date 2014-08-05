package com.treep.fr;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.treep.fr.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.WindowManager.BadTokenException;

public class StoreTreepResponseListFromXML extends AsyncTask<Void, Integer, ArrayList<HashMap<String, String>>> {
	
	
	private Activity activity;
	private String treepId;
	private ArrayList<HashMap<String, String>> alTreepResponse = new ArrayList<HashMap<String, String>>() ;
	//private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	
	public StoreTreepResponseListFromXML( Activity activity, String treepId){
		
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
		// Mise à jour de la ProgressBar
		//mProgressBar.setProgress(values[0]);
	}

	@Override
	protected ArrayList<HashMap<String, String>> doInBackground(Void... arg0) {

		if (android.os.Build.VERSION.SDK_INT > 9) 
    	{ StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }
		
    	XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(MainActivity.URL_TREEPRESPONSELIST + "?userid=" + LoginDisplayActivity.userId);
		
		ArrayList<HashMap<String, String>> treepResponseList = new ArrayList<HashMap<String, String>>();
		HashMap<String,String> mapEmpty = new HashMap<String,String>();
		mapEmpty.put("empty", "1");
		
		if(xml == null){
			alTreepResponse = null;
		}
		else{
			if(xml == "timeout"){
				HashMap<String,String> mapTimeout = new HashMap<String,String>();
				mapTimeout.put(MainActivity.KEY_TIMEOUT, "1");
				treepResponseList.add(mapTimeout);
			}
			else{
				
				if(xml.length() == 0){
					alTreepResponse.add(mapEmpty);
				}
				
				else{
					Document doc = null;
					try{
						doc = parser.getDomElement(xml); // getting DOM element
					}
					catch(DOMException e){
						
					}
					
					if(doc == null){
						alTreepResponse = null;
					}
					else{
						NodeList nl = doc.getElementsByTagName(MainActivity.KEY_RESPONSE);
						// looping through all xml nodes <KEY_USER>
						for (int i = 0; i < nl.getLength(); i++) {
							// creating new HashMap
							HashMap<String, String> mapDriverResponseinfo = new HashMap<String, String>();
							Element e = (Element) nl.item(i);
							// adding each child node to HashMap key => value
							mapDriverResponseinfo.put(MainActivity.KEY_RESPONSEID, parser.getValue(e, MainActivity.KEY_RESPONSEID));
							mapDriverResponseinfo.put(MainActivity.KEY_TREEPID, parser.getValue(e, MainActivity.KEY_TREEPID));
							mapDriverResponseinfo.put(MainActivity.KEY_USERID, parser.getValue(e, MainActivity.KEY_USERID));
							mapDriverResponseinfo.put(MainActivity.KEY_LATITUDE, parser.getValue(e, MainActivity.KEY_LATITUDE));
							mapDriverResponseinfo.put(MainActivity.KEY_LONGITUDE, parser.getValue(e, MainActivity.KEY_LONGITUDE));
							mapDriverResponseinfo.put(MainActivity.KEY_FIRSTNAME, parser.getValue(e, MainActivity.KEY_FIRSTNAME));
							mapDriverResponseinfo.put(MainActivity.KEY_LASTNAME, parser.getValue(e, MainActivity.KEY_LASTNAME));
							mapDriverResponseinfo.put(MainActivity.KEY_CARMODEL, parser.getValue(e, MainActivity.KEY_CARMODEL));
							mapDriverResponseinfo.put(MainActivity.KEY_NBSEAT, parser.getValue(e, MainActivity.KEY_NBSEAT));
							mapDriverResponseinfo.put(MainActivity.KEY_RATING, parser.getValue(e, MainActivity.KEY_RATING));
							mapDriverResponseinfo.put(MainActivity.KEY_NBTREEP, parser.getValue(e, MainActivity.KEY_NBTREEP));
							mapDriverResponseinfo.put(MainActivity.KEY_PRICE, parser.getValue(e, MainActivity.KEY_PRICE));
							mapDriverResponseinfo.put(MainActivity.KEY_DISTANCETIME, parser.getValue(e, MainActivity.KEY_DISTANCETIME));
							mapDriverResponseinfo.put(MainActivity.KEY_TREEPRESPONSETIMEREMAINING, parser.getValue(e, MainActivity.KEY_TREEPRESPONSETIMEREMAINING));
							mapDriverResponseinfo.put(MainActivity.KEY_ADDRESSDEP, parser.getValue(e, MainActivity.KEY_ADDRESSDEP));
							mapDriverResponseinfo.put(MainActivity.KEY_ADDRESSDEST, parser.getValue(e, MainActivity.KEY_ADDRESSDEST));
							mapDriverResponseinfo.put(MainActivity.KEY_CARPIC_URL, parser.getValue(e, MainActivity.KEY_CARPIC_URL));

							alTreepResponse.add(mapDriverResponseinfo);
						}
					}
				}
			}
		}
		
		return alTreepResponse;
	}

	@SuppressLint("NewApi")
	@Override
	protected void onPostExecute(final ArrayList<HashMap<String, String>>  result) {

		if(result == null){
			MainActivity.displayToast(R.string.httpTimeOut);
		}
		else{
			if(result.size() ==0){
				
				FragmentManager fragmentManager = activity.getFragmentManager();
	            try{
	            	MainActivity.fragment = new TreepResponseListEmpty();
					Bundle data = new Bundle();
					data.putString("treepid",treepId);
					MainActivity.fragment .setArguments(data);
		            fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_toptobottom, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container,MainActivity.fragment).commit();
				}
	            catch(IllegalStateException e){
	            	MainActivity.displayToast("Treep vous recherche un conducteur. Merci de patienter...");
	            }
				
			}
			else{
				
				if(result.get(0).containsKey(MainActivity.KEY_TIMEOUT) || result == null){
					DialogTimeout dialogTimeout = new DialogTimeout(activity);
					try{
						dialogTimeout.show();
					}
					catch(BadTokenException e){
						
					}
				}
				
				else{
					if(result.get(0).containsKey("empty")){
						MainActivity.fragment = new TreepResponseListEmpty();
						Bundle data = new Bundle();
						FragmentManager fragmentManager = activity.getFragmentManager();
						data.putString("treepid",treepId);
						MainActivity.fragment .setArguments(data);
			            fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_toptobottom, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container,MainActivity.fragment).commit();
					}
					else{
						MainActivity.fragment = new TreepResponseListFragment();
						Bundle data = new Bundle();
						FragmentManager fragmentManager = activity.getFragmentManager();
						data.putSerializable("alTreepResponseList",result);
						data.putString("treepid",treepId);
						MainActivity.fragment .setArguments(data);
				        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_toptobottom, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container,MainActivity.fragment).commit();
						
					}
				}
		        
			}
		}
		
		activity.setProgressBarIndeterminateVisibility(false);
	}

}
