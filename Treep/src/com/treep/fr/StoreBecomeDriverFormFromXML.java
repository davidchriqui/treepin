package com.treep.fr;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.treep.fr.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;



public class StoreBecomeDriverFormFromXML extends AsyncTask<Void, Integer, HashMap<String, String>> {
	

	private HashMap<String,String> mapErrCode = new HashMap<String,String>();
	private Activity activity;
	private String licenceYear;
	private String carModel;
	private String city;
	
	public StoreBecomeDriverFormFromXML(Activity activity,
										String licenceYear,
										String carModel,
										String city){
		this.activity=activity;
		this.licenceYear=licenceYear;
		this.carModel=carModel;
		this.city=city;
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
		String xml;
		try {
			xml = parser.getXmlFromUrl(MainActivity.URL_BECOMEDRIVER + "?userid=" + LoginDisplayActivity.userId + "&licenceyear=" + licenceYear + "&carmodel=" + URLEncoder.encode(carModel, "UTF-8") + "&city=" + URLEncoder.encode(city, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			xml = parser.getXmlFromUrl(MainActivity.URL_BECOMEDRIVER + "?userid=" + LoginDisplayActivity.userId + "&licenceyear=" + licenceYear + "&carmodel=UNKNOWN&city=UNKNOWN");
			
			e1.printStackTrace();
		}
		
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
	protected void onPostExecute(HashMap<String, String> result) {
				
		if(result == null || result.containsKey(MainActivity.KEY_TIMEOUT)){
		    		
			MainActivity.displayToast(R.string.httpTimeOut);
			activity.setProgressBarIndeterminateVisibility(false);
			
		}
		else{	
			if(result.get(MainActivity.KEY_ERRCODE).equals("000")){
				DialogBecomeFormSent dialogBecomeFormSent = new DialogBecomeFormSent(activity);
				dialogBecomeFormSent.setCancelable(false);
				dialogBecomeFormSent.setCanceledOnTouchOutside(false);
				dialogBecomeFormSent.show();
			}
			else{
				MainActivity.displayToast("Veuillez réessayer plus tard : " + result.get(MainActivity.KEY_ERRCODE));
			}
		}
		activity.setProgressBarIndeterminateVisibility(false);
	}
	
	private class DialogBecomeFormSent extends Dialog implements
    android.view.View.OnClickListener {
	
	  public Activity activity;
	  public Button okButton;
	  private Typeface fb;
	
	  public DialogBecomeFormSent(Activity activity) {
	    super(activity);
	    this.activity = activity;
	  }
	  
	  
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.dialog_becomedriverformsent);
	    fb = Typeface.createFromAsset(activity.getAssets(), "fb.ttf");
	    
	    okButton = (Button) findViewById(R.id.okButton);
	    okButton.setTypeface(fb);
	    okButton.setOnClickListener(this);
	  }
	
	  @SuppressLint("NewApi")
	@Override
	  public void onClick(View v) {
	    switch (v.getId()) {
	    case R.id.okButton:
	    	activity.setProgressBarIndeterminateVisibility(true);
    		
			if(!MainActivity.CheckInternet(activity)){
				MainActivity.displayToast(R.string.httpTimeOut);
	            MainActivity.initposition = 0;
			}
			else{
				
				MainActivity.initposition = 0;
	            activity.setProgressBarIndeterminateVisibility(true);
	            MainActivity.fragment= new HomeFragment();
	            FragmentManager fragmentManager = activity.getFragmentManager();
	            fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container, MainActivity.fragment).commit();
			}
			dismiss();
	      break;
	    default:
	      break;
	    }
	    dismiss();
	  }
	}
}
