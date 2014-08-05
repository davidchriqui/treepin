package com.treep.fr;


import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.treep.fr.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;



public class CheckoutPromoCodeVerifFromXML extends AsyncTask<Void, Integer, HashMap<String,ArrayList<String>>> {
	
	private Activity activity;
	private String treepId;
	private String responseId;
	private String driverId;
	private String price;
	private String promoCodeEntered;
	private int promoPrice;
	private int finalPrice = -1;
	private int size;
	
	//Hashmap contenant les 2 arraylist<String> "promoCodeUsedList" et "promoCodeAvailableList"
	private HashMap<String,ArrayList<String>> mapPromoCode = new HashMap<String,ArrayList<String>>();
	
	//Arraylist contenant les codes promo utilisés
	private ArrayList<String> promoCodeUsedList = new ArrayList<String>();
	//Arraylist contenant les codes promo disponibles
	private ArrayList<String> promoCodeAvailableList = new ArrayList<String>();
	//Arraylist de hashmap contenant le code promo et le prix asocié
	private ArrayList<HashMap<String,String>> promoCodePriceAvailableList = new ArrayList<HashMap<String,String>>();
	
	//arraylists retournées par l'async
	private ArrayList<String> promoCodeUsedResultList = new ArrayList<String>();
	private ArrayList<String> promoCodeAvailableResultList = new ArrayList<String>();
	
	public CheckoutPromoCodeVerifFromXML(Activity activity, String treepId, String responseId, String driverId, String price, String promoCodeEntered){
		this.activity=activity;
		this.treepId=treepId;
		this.responseId=responseId;
		this.driverId=driverId;
		this.price=price;
		this.promoCodeEntered=promoCodeEntered;
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
	protected HashMap<String,ArrayList<String>> doInBackground(Void... arg0) {
		
		
		if (android.os.Build.VERSION.SDK_INT > 9) 
    	{ StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }
	
    	XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(MainActivity.URL_PROMOCODEVERIFICATION + "?userid=" + LoginDisplayActivity.userId);
		
		if(xml == "timeout"){
			promoCodeUsedList.add("timeout");
			promoCodeAvailableList.add("timeout");
		}
		else{
			 // getting XML from URL
			Document doc = parser.getDomElement(xml); // getting DOM element
			
			if(doc == null){
				promoCodeUsedList = null;
				promoCodeAvailableList = null;
			}
			else{
				NodeList nl_PromoCodeUsedList = doc.getElementsByTagName(MainActivity.KEY_PROMOCODEUSEDLIST);
				
				size = nl_PromoCodeUsedList.getLength();
				
				for (int i = 0; i < nl_PromoCodeUsedList.getLength(); i++) {
					
					Element e = (Element) nl_PromoCodeUsedList.item(i);
					
					NodeList nl_PromoCode = e.getElementsByTagName(MainActivity.KEY_PROMOCODE);
					
					//size = nl_PromoCode.getLength();
					
					for (int j = 0; j < nl_PromoCode.getLength(); j++) {
						// creating new HashMap
						Element r = (Element) nl_PromoCode.item(j);
						promoCodeUsedList.add(parser.getValue(r, MainActivity.KEY_CODE));
					}
				}
				
				NodeList nl_PromoCodeAvailableList = doc.getElementsByTagName(MainActivity.KEY_PROMOCODEAVAILABLELIST);
				for (int i = 0; i < nl_PromoCodeAvailableList.getLength(); i++) {
					Element r = (Element) nl_PromoCodeAvailableList.item(i);
					NodeList nl_PromoCodeAvailable = r.getElementsByTagName(MainActivity.KEY_PROMOCODEAVAILABLE);
					
					for (int j = 0; j < nl_PromoCodeAvailable.getLength(); j++) {
						// creating new HashMap
						HashMap<String, String> mapPromoCodeAvailable = new HashMap<String, String>();
						Element t = (Element) nl_PromoCodeAvailable.item(j);
						// adding each child node to HashMap key => value
						mapPromoCodeAvailable.put(MainActivity.KEY_CODE, parser.getValue(t, MainActivity.KEY_CODE));
						mapPromoCodeAvailable.put(MainActivity.KEY_PROMOPRICE, parser.getValue(t, MainActivity.KEY_PROMOPRICE));
						
						
						promoCodeAvailableList.add(parser.getValue(t, MainActivity.KEY_CODE));
						
						promoCodePriceAvailableList.add(mapPromoCodeAvailable);
					}
				}
				
				mapPromoCode.put("promoCodeUsedList",promoCodeUsedList);
				mapPromoCode.put("promoCodeAvailableList",promoCodeAvailableList);
			}
		}
		return mapPromoCode;
	}

	
	@SuppressLint("NewApi")
	protected void onPostExecute(HashMap<String,ArrayList<String>>  result) {
		
		promoCodeUsedResultList = result.get("promoCodeUsedList");
		promoCodeAvailableResultList = result.get("promoCodeAvailableList");
		
		if(promoCodeUsedResultList.contains(MainActivity.KEY_TIMEOUT) || promoCodeAvailableResultList.contains(MainActivity.KEY_TIMEOUT)){
			
			if(MainActivity.launchTime <=3){
				CheckoutPromoCodeVerifFromXML checkoutPromoCodeVerifFromXML = new CheckoutPromoCodeVerifFromXML(activity, treepId, responseId, driverId, price,promoCodeEntered);
				checkoutPromoCodeVerifFromXML.execute();
				MainActivity.launchTime++ ;
			}
			else{
				MainActivity.displayToast("Problème de connexion internet. Veuillez réessayer.");
			}
		}
		else{
			if(promoCodeAvailableResultList == null){
				MainActivity.displayToast("Problème de connexion internet, veuillez réessayer");
			}
			else{
				if(promoCodeAvailableResultList.contains(promoCodeEntered)){
					if(promoCodeUsedResultList.contains(promoCodeEntered)){
						MainActivity.displayToast("Vous avez déjà utilisé ce code promo");
					}
					else{
						for(HashMap<String,String> mapPromoCodePrice : promoCodePriceAvailableList){
							if(mapPromoCodePrice.get(MainActivity.KEY_CODE).equals(promoCodeEntered)){
							//promoPrice = Integer.parseInt(mapPromoCodePrice.get(promoCodeEntered));
							promoPrice = Integer.parseInt(mapPromoCodePrice.get(MainActivity.KEY_PROMOPRICE));
							}
						}
						finalPrice = Integer.parseInt(price) - promoPrice;
						
						if(finalPrice < 0){
							finalPrice = 0;
							DialogCheckoutWithPromoCode dialogCheckoutWithPromoCode = new DialogCheckoutWithPromoCode(activity, finalPrice);
							dialogCheckoutWithPromoCode.show();
						}
						else{
							DialogCheckoutWithPromoCode dialogCheckoutWithPromoCode = new DialogCheckoutWithPromoCode(activity, finalPrice);
							dialogCheckoutWithPromoCode.show();
						}
					}
				}
				else{
					MainActivity.displayToast("Le code promo entré n'est pas valide");
				}
			}
		}
		activity.setProgressBarIndeterminateVisibility(false);
	}
	
	
	private class DialogCheckoutWithPromoCode extends Dialog implements
    android.view.View.OnClickListener {
	
		  public Activity activity;
		  public Button confirmButton;
		  public Button cancelButton;
		  public TextView tvCheckoutPromoCodePrice;
		  public int finalPrice;
		  
		  public DialogCheckoutWithPromoCode(Activity activity,int finalPrice) {
		    super(activity);
		    // TODO Auto-generated constructor stub
		    this.activity = activity;
		    this.finalPrice=finalPrice;
		  }
		
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    setContentView(R.layout.dialog_checkoutwithpromocode);
		    confirmButton = (Button) findViewById(R.id.confirmButton);
		    confirmButton.setOnClickListener(this);
		    cancelButton = (Button) findViewById(R.id.cancelButton);
		    cancelButton.setOnClickListener(this);
		    tvCheckoutPromoCodePrice = (TextView) findViewById(R.id.tvCheckoutPromoCodePrice);
		    tvCheckoutPromoCodePrice.setText(finalPrice + "€");
		  }
		
		  @Override
		  public void onClick(View v) {
		    switch (v.getId()) {
		    case R.id.confirmButton:
		    	if(finalPrice != -1){
		    		if(finalPrice == 0){
		    			SetTreepResponseConfirmWithPromoCodeFromXML setTreepResponseConfirmWithPromoCodeFromXML = new SetTreepResponseConfirmWithPromoCodeFromXML(activity, finalPrice);
		    			setTreepResponseConfirmWithPromoCodeFromXML.execute();
		    		}
		    		else{
		    			SetTreepResponseConfirmWithPromoCodeFromXML setTreepResponseConfirmWithPromoCodeFromXML = new SetTreepResponseConfirmWithPromoCodeFromXML(activity, finalPrice);
		    			setTreepResponseConfirmWithPromoCodeFromXML.execute();
		    		}
		    	}
		    	else{
		    		
		    	}
		    	dismiss();
		    break;
		    case R.id.cancelButton:
		    	dismiss();
		    break;
		    default:
		      break;
		    }
		  }
	}
	
	
	
	
	
	private class SetTreepResponseConfirmWithPromoCodeFromXML extends AsyncTask<Void, Integer, HashMap<String, String>> {
		
		private Activity activity;
		private HashMap<String,String> mapErrCode = new HashMap<String,String>();
		private int finalPrice;
		private String strFinalPrice;
		
		
		public SetTreepResponseConfirmWithPromoCodeFromXML(Activity activity, int finalPrice){
			this.activity=activity;
			this.finalPrice=finalPrice;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(finalPrice == 0){
				strFinalPrice = "00";
			}
			else{
				strFinalPrice = Integer.toString(finalPrice);
			}
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
			String xml = parser.getXmlFromUrl(MainActivity.URL_SETTREEPREQUESTCONFIRMFROMDRIVER + "?userid=" + LoginDisplayActivity.userId + "&treepid=" + treepId + "&responseid=" + responseId + "&driverid=" + driverId + "&price=" + strFinalPrice +"&promocode=" + promoCodeEntered);
			
			if(xml == "timeout"){
				
				mapErrCode.put(MainActivity.KEY_TIMEOUT, "1");	
			}
			
			else{
				
		         // getting XML from URL
				Document doc = parser.getDomElement(xml); // getting DOM element
				
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

			return mapErrCode;
			
		}

		
		@SuppressLint("NewApi")
		protected void onPostExecute(HashMap<String, String> result) {
			
			if(result == null || result.containsKey(MainActivity.KEY_TIMEOUT)){
	    		
				MainActivity.displayToast("Veuillez réessayer plus tard");
				activity.setProgressBarIndeterminateVisibility(false);
				
			}
			else{	
				if(result.get(MainActivity.KEY_ERRCODE).equals("000")){

	    			MainActivity.displayToast("code= " + promoCodeEntered);
					MainActivity.initposition = 0;
					MainActivity.fragment= new HomeFragment();
					FragmentManager fragmentManager = activity.getFragmentManager();
					fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container, MainActivity.fragment).commit();
					
				}
				else{
					MainActivity.displayToast("Veuillez réessayer plus tard : " + result.get(MainActivity.KEY_ERRCODE));
				}
			}
			activity.setProgressBarIndeterminateVisibility(false);
		}
	}
}
