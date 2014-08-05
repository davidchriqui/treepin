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
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;

public class StoreMyDriverListFromXML extends AsyncTask<Void, Integer, ArrayList<HashMap<String, String>>> {
	
	
	private Activity activity;
	private ArrayList<HashMap<String, String>> alMyDriverList = new ArrayList<HashMap<String, String>>() ;
	private DialogPleaseWait dialogPleaseWait;
	
	public StoreMyDriverListFromXML(final Activity activity){
		
		this.activity=activity;
		dialogPleaseWait = new DialogPleaseWait(activity);
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
			if(dialogPleaseWait != null){
				dialogPleaseWait.show();
			}
			else{
				dialogPleaseWait = new DialogPleaseWait(activity);
				dialogPleaseWait.show();
			}
		}
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
		String xml = parser.getXmlFromUrl(MainActivity.URL_MYDRIVERLIST + "?userid=" + LoginDisplayActivity.userId);
		
		if(xml == null){
			alMyDriverList = null;
		}
		else{
			if(xml == "timeout"){
				HashMap<String,String> mapTimeout = new HashMap<String,String>();
				mapTimeout.put(MainActivity.KEY_TIMEOUT, "1");
				alMyDriverList.add(mapTimeout);
			}
			
			else{
				
				if(xml.length() == 0){
					
					HashMap<String,String> mapEmpty = new HashMap<String,String>();
					mapEmpty.put("empty", "1");
					alMyDriverList.add(mapEmpty);
					
				}
				else{
					Document doc = null;
					try{
						doc = parser.getDomElement(xml); // getting DOM element
					}
					catch(DOMException e){
						
					}
					
					if(doc == null){
						alMyDriverList = null;
					}
					else{
						NodeList nl = doc.getElementsByTagName(MainActivity.KEY_USER);
						// looping through all xml nodes <KEY_USER>
						for (int i = 0; i < nl.getLength(); i++) {
							// creating new HashMap
							HashMap<String, String> mapDriverinfo = new HashMap<String, String>();
							Element e = (Element) nl.item(i);
							// adding each child node to HashMap key => value
							mapDriverinfo.put(MainActivity.KEY_USERID, parser.getValue(e, MainActivity.KEY_USERID));
							mapDriverinfo.put(MainActivity.KEY_FIRSTNAME, parser.getValue(e, MainActivity.KEY_FIRSTNAME));
							mapDriverinfo.put(MainActivity.KEY_LASTNAME, parser.getValue(e, MainActivity.KEY_LASTNAME));
							mapDriverinfo.put(MainActivity.KEY_NBTREEP, parser.getValue(e, MainActivity.KEY_NBTREEP));
							mapDriverinfo.put(MainActivity.KEY_CARMODEL, parser.getValue(e, MainActivity.KEY_CARMODEL));
							mapDriverinfo.put(MainActivity.KEY_RATING, parser.getValue(e, MainActivity.KEY_RATING));
							mapDriverinfo.put(MainActivity.KEY_CARPIC_URL, parser.getValue(e, MainActivity.KEY_CARPIC_URL));
							
							// adding HashList to ArrayList
							alMyDriverList.add(mapDriverinfo);
						}
					}
				}
			}
		}
		
		return alMyDriverList;
        
	}

	@SuppressLint("NewApi")
	@Override
	protected void onPostExecute(final ArrayList<HashMap<String, String>>  result) {
		
		
		activity.setProgressBarIndeterminateVisibility(true);
		
		if(result ==null){
			DialogTimeout dialogTimeout = new DialogTimeout(activity);
			dialogTimeout.show();
			
		}
		else{
			if(result.size() == 0){
				FragmentManager fragmentManager = activity.getFragmentManager();
	            fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container,new EmptyList()).commit();
			}
			else{
				if(result.get(0).containsKey(MainActivity.KEY_TIMEOUT)){
					DialogTimeout dialogTimeout = new DialogTimeout(activity);
					dialogTimeout.show();
				}
				else{
					MainActivity.fragment = new MyDriversListFragment();
					
					Bundle data = new Bundle();
					
					FragmentManager fragmentManager = activity.getFragmentManager();
					data.putSerializable("alMyDriverList",result);
					MainActivity.fragment.setArguments(data);
			        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container,MainActivity.fragment ).commit();
				}
			}
	        
		}
		
		dialogPleaseWait.dismiss();
		activity.setProgressBarIndeterminateVisibility(false);
	}

}
