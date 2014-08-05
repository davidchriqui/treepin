package com.treep.fr;

import java.util.ArrayList;
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
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.parse.ParsePush;
import com.parse.SendCallback;

public class SetDriverInformationFragment extends Fragment{
	
	private Button saveButton;
	private EditText etCarBrand;
	private EditText etCarModel;
	private EditText etCarColor;
	private EditText etCarSeat;
	private CheckBox driverInfoCheckBox;
	private Typeface fb;
	private DialogPleaseWait dialogPleaseWait;
	
	public static void hideSoftKeyboard(Activity activity) {
 	    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
 	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
 	}
	
	
	  
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_setdriverinformations, container, false);
		fb = Typeface.createFromAsset(getActivity().getAssets(), "fb.ttf");
		
		saveButton = (Button)rootView.findViewById(R.id.saveButton);
		saveButton.setTypeface(fb);
		
		dialogPleaseWait = new DialogPleaseWait(getActivity());
		
		etCarBrand = (EditText) rootView.findViewById(R.id.etCarBrand);
		etCarModel = (EditText) rootView.findViewById(R.id.etCarModel);
		etCarColor = (EditText) rootView.findViewById(R.id.etCarColor);
		etCarSeat = (EditText) rootView.findViewById(R.id.etCarSeat);
		

		driverInfoCheckBox = (CheckBox) rootView.findViewById(R.id.driverInfoCheckBox);
		
		
		saveButton.setOnClickListener(new View.OnClickListener()
	      {
	         public void onClick(View v)
	         {
	        	 hideSoftKeyboard(getActivity());
	        	 
	        	 if(!driverInfoCheckBox.isChecked()){
	 	    		MainActivity.displayToast("Vous devez cocher la cases et certifier être en possession d'un permis de conduire et d'une assurance en cours de validité.");
	 	    	}
	 	    	else{
	 	    		StoreDriverInformations storeDriverInformations = new StoreDriverInformations(getActivity(),etCarBrand.getText().toString(),etCarModel.getText().toString(),etCarColor.getText().toString(),etCarSeat.getText().toString());
					storeDriverInformations.execute();
	 	    	}
	          }
	      });
	   

		return rootView;
		
		
	}
	
	

private class StoreDriverInformations extends AsyncTask<Void, Integer, HashMap<String, String>> {
	
	//private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	private HashMap<String,String> mapErrCode = new HashMap<String,String>();
	private Activity activity;
	private String carBrand;
	private String carModel;
	private String carColor;
	private String carSeat;
	private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	
	public StoreDriverInformations(Activity activity, String carBrand, String carModel, String carColor, String carSeat){
		this.activity=activity;
		this.carBrand=carBrand;
		this.carModel=carModel;
		this.carColor=carColor;
		this.carSeat=carSeat;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		activity.setProgressBarIndeterminateVisibility(true);
		nameValuePairs.add(new BasicNameValuePair("userid", LoginDisplayActivity.userId));
		nameValuePairs.add(new BasicNameValuePair("carbrand", carBrand));
		nameValuePairs.add(new BasicNameValuePair("carmodel", carModel));
		nameValuePairs.add(new BasicNameValuePair("carcolor", carColor));
		nameValuePairs.add(new BasicNameValuePair("carseat", carSeat));
		
	}

	@Override
	protected void onProgressUpdate(Integer... values){
		super.onProgressUpdate(values);
		
	}

	@Override
	protected HashMap<String, String> doInBackground(Void... arg0) {

		HashMap<String,String> mapTimeout = new HashMap<String,String>();
		mapTimeout.put(MainActivity.KEY_TIMEOUT, "1");
		
		if (android.os.Build.VERSION.SDK_INT > 9) 
    	{ StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }
	
    	XMLParser parser = new XMLParser(nameValuePairs);
		String xml = parser.getXmlFromUrl(MainActivity.URL_SETDRIVERINFORMATION);
		
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
				
				JSONObject object = new JSONObject();
		        try {
                    object.put("alert", "Vous pouvez maintenant prendre des treepers. Bonne route.");
                    object.put("title", "Treep : Mode Driver Activé");
                    object.put("action", "Notif");                   
                    ParsePush pushMessageClient1 = new ParsePush();
                    pushMessageClient1.setData(object);
                    pushMessageClient1.setChannel("user" + LoginDisplayActivity.userId);
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

	
	
}
