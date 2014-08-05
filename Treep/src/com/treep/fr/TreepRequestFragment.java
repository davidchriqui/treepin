package com.treep.fr;


import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.parse.ParsePush;
import com.parse.SendCallback;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TreepRequestFragment extends Fragment {

	private TextView tvInfobanner;
	private TextView tvUsername;
	private Button buttonRefuse;
	private Button buttonAccept;
	private Button buttonItinerary;
	private ImageView ivUserProfilePicture;
	private ImageLoader imageLoaderProfilePicture;
	private ImageView ivUserRating;
	private LinearLayout detourLayout;
	private TextView tvDetour;
	private TextView tvAddressDep;
	private TextView tvAddressDest;
	private TextView tvPositionCompany;
	private String username;
	private String treepId;
	private String treepRequestId;
	
	private HashMap<String, String> mapTreepRequest = new HashMap<String, String>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_treeprequest, container, false);
		
		final Bundle bundle = getArguments();
		mapTreepRequest = (HashMap<String, String>) bundle.getSerializable("mapTreepRequest");
		
		tvInfobanner = (TextView) v.findViewById(R.id.tvInfobanner);
		Animation blinkAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.blink);
        
		tvInfobanner.startAnimation(blinkAnim);
		tvInfobanner.setText("Temps restant : " + mapTreepRequest.get(MainActivity.KEY_TIMEREMAINING));
		
		tvUsername = (TextView) v.findViewById(R.id.tvUsername);
		username = mapTreepRequest.get(MainActivity.KEY_FIRSTNAME) + " " + mapTreepRequest.get(MainActivity.KEY_LASTNAME).charAt(0) + ".";
        tvUsername.setText(username);
        

		buttonItinerary = (Button) v.findViewById(R.id.buttonItinerary);
		
		OnClickListener clickListenerItinerary = new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    
		    	Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
	    		    Uri.parse("http://maps.google.com/maps?saddr="+ 
	    		    		mapTreepRequest.get(MainActivity.KEY_LATDEPNOW) + "," + mapTreepRequest.get(MainActivity.KEY_LNGDEPNOW)
	    		    			+ "&daddr=" + mapTreepRequest.get(MainActivity.KEY_LATDESTNOW) + "," + mapTreepRequest.get(MainActivity.KEY_LNGDESTNOW)));
		    		startActivity(intent);
		    }
		  };
		  buttonItinerary.setOnClickListener(clickListenerItinerary);
       	
		

		ivUserProfilePicture = (ImageView) v.findViewById(R.id.ivUserProfilePicture);
		
		imageLoaderProfilePicture = new ImageLoader(ApplicationContextProvider.getContext());
		imageLoaderProfilePicture.DisplayImage(MainActivity.urlProfilePic(mapTreepRequest.get(MainActivity.KEY_USERID)),ivUserProfilePicture);
  		
  		
		ivUserRating = (ImageView) v.findViewById(R.id.ivUserRating);
		
		switch (Integer.parseInt(mapTreepRequest.get(MainActivity.KEY_RATING))) {
	       case 0:
	       	//list_rating.setImageResource(R.drawable.rating1);       
	           break;
	       case 1:
	    	   ivUserRating.setImageResource(R.drawable.rating1);
	             
	           break;
	       case 2:
	    	   ivUserRating.setImageResource(R.drawable.rating2);
	             
	           break;
	       case 3:
	    	   ivUserRating.setImageResource(R.drawable.rating3);
	             
	           break;
	       case 4:
	    	   ivUserRating.setImageResource(R.drawable.rating4);
	             
	           break;
	       case 5:
	    	   ivUserRating.setImageResource(R.drawable.rating5);
	             
	           break;
			
			}
		
		tvAddressDep = (TextView) v.findViewById(R.id.tvAddressDep);
		tvAddressDep.setText(mapTreepRequest.get(MainActivity.KEY_ADDRESSDEPNOW));
		
		tvAddressDest = (TextView) v.findViewById(R.id.tvAddressDest);
		tvAddressDest.setText(mapTreepRequest.get(MainActivity.KEY_ADDRESSDESTNOW));
		
		tvPositionCompany = (TextView) v.findViewById(R.id.tvPositionCompany);
		tvPositionCompany.setText(Html.fromHtml("<b>" + (mapTreepRequest.get(MainActivity.KEY_CURRENTPOSITION) + "</b> chez <b>" + mapTreepRequest.get(MainActivity.KEY_CURRENTCOMPANY) + "</b>")));
		
		tvDetour = (TextView) v.findViewById(R.id.tvDetour);
		tvDetour.setText(Html.fromHtml("Détour : <b>moins de " + (mapTreepRequest.get(MainActivity.KEY_DETOUR) + " min </b>")));
		
		detourLayout = (LinearLayout) v.findViewById(R.id.detourLayout);
		detourLayout.startAnimation(blinkAnim);
		
		buttonRefuse = (Button) v.findViewById(R.id.buttonRefuse);
		buttonAccept = (Button) v.findViewById(R.id.buttonAccept);
		
		final Animation buttonScaleOnReleaseAcceptAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.button_scale_onrelease);
		buttonScaleOnReleaseAcceptAnim.setFillAfter(true);
		
		final Animation buttonScaleOnTouchAcceptAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.button_scale_ontouch);
		buttonScaleOnTouchAcceptAnim.setFillAfter(true);
		
		
		
		
		
		OnTouchListener onTouchListenerButtonAccept = new View.OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		    	if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
		    		buttonAccept.startAnimation(buttonScaleOnTouchAcceptAnim);
				     
			    }
			    else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
			    	buttonAccept.startAnimation(buttonScaleOnReleaseAcceptAnim);
			    
			    }
				return false;
		    }

		  };
		  
		  buttonAccept.setOnTouchListener(onTouchListenerButtonAccept);
		  
		  OnClickListener clickListenerButtonAccept = new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
			    	SetTreepRequestConfirmFromXML setTreepRequestConfirmFromXML = new SetTreepRequestConfirmFromXML(getActivity() ,mapTreepRequest.get(MainActivity.KEY_TREEPID), mapTreepRequest.get(MainActivity.KEY_REQUESTID), mapTreepRequest.get(MainActivity.KEY_DRIVERTREEPREQUESTID));
			    	setTreepRequestConfirmFromXML.execute();
			    }
		  };
		  buttonAccept.setOnClickListener(clickListenerButtonAccept);
			  
		  
		  final Animation buttonScaleOnTouchRefuseAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.button_scale_ontouch);
		  buttonScaleOnTouchRefuseAnim.setFillAfter(true);
			
		  final Animation buttonScaleOnReleaseRefuseAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.button_scale_onrelease);
			buttonScaleOnReleaseRefuseAnim.setFillAfter(true);
		  
		  OnTouchListener onTouchListenerButtonRefuse = new View.OnTouchListener() {
			    @Override
			    public boolean onTouch(View v, MotionEvent event) {
			    	if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
			    		buttonRefuse.startAnimation(buttonScaleOnTouchRefuseAnim);
					     
				    }
				    else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
				    	buttonRefuse.startAnimation(buttonScaleOnReleaseRefuseAnim);
				    
				    }
			    	
					return false;
			    }    
		  };
			  
		buttonRefuse.setOnTouchListener(onTouchListenerButtonRefuse);
		OnClickListener clickListenerButtonRefuse = new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	SetTreepRequestRefuseFromXML setTreepRequestRefuseFromXML = new SetTreepRequestRefuseFromXML(getActivity(), mapTreepRequest.get(MainActivity.KEY_REQUESTID));
		    	setTreepRequestRefuseFromXML.execute();
		    }
		  };
		  buttonRefuse.setOnClickListener(clickListenerButtonRefuse);
			
		
		return v;
	}
	
	private class SetTreepRequestConfirmFromXML extends AsyncTask<Void, Integer, HashMap<String, String>> {
		
		private Activity activity;
		private HashMap<String,String> mapErrCode = new HashMap<String,String>();
		private String treepId;
		private String requestId;
		private String driverTreepRequestId;
		
		
		public SetTreepRequestConfirmFromXML(Activity activity,String treepId, String requestId, String driverTreepRequestId){
			this.activity=activity;
			this.treepId=treepId;
			this.requestId=requestId;
			this.driverTreepRequestId=driverTreepRequestId;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			activity.setProgressBarIndeterminateVisibility(true);
			MainActivity.displayToast(LoginDisplayActivity.userId);
			
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
		
	    	XMLParser parser = new XMLParser();
			String xml = parser.getXmlFromUrl(MainActivity.URL_SETTREEPREQUESTCONFIRMFROMDRIVER + "?userid=" + LoginDisplayActivity.userId + "&treepid=" + treepId + "&requestid=" + requestId + "&drivertreeprequestid=" + driverTreepRequestId);
			
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
					
					JSONObject object = new JSONObject();
			        try {
	                    object.put("alert", "Votre driver arrive.");
	                    object.put("title", "Treep : Confirmation de " +LoginDisplayActivity.userFirstname + " " + LoginDisplayActivity.userLastname.charAt(0) + ".");
	                    object.put("action", "Notif");                   
	                    ParsePush pushMessageClient1 = new ParsePush();
	                    pushMessageClient1.setData(object);
	                    pushMessageClient1.setChannel("user" + mapTreepRequest.get(MainActivity.KEY_USERID));
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
					
					MainActivity.initposition = 0;
					Intent i = new Intent(ApplicationContextProvider.getContext(), MainActivity.class);
		    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    		getActivity().overridePendingTransition(R.anim.slide_in_righttoleft, R.anim.slide_out_lefttoright);
		    		ApplicationContextProvider.getContext().startActivity(i);
				}
				else{
					if(result.get(MainActivity.KEY_ERRCODE).equals("100")){
						MainActivity.displayToast("Cette demande de Treep n'est plus disponible");
					}
					else{
						MainActivity.displayToast("Veuillez réessayer plus tard : " + result.get(MainActivity.KEY_ERRCODE));
					}
				}
			}
			activity.setProgressBarIndeterminateVisibility(false);
		}
	}
	
private class SetTreepRequestRefuseFromXML extends AsyncTask<Void, Integer, HashMap<String, String>> {
		
		private Activity activity;
		private HashMap<String,String> mapErrCode = new HashMap<String,String>();
		private String requestId;
		
		
		public SetTreepRequestRefuseFromXML(Activity activity, String requestId){
			this.activity=activity;
			this.requestId=requestId;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			activity.setProgressBarIndeterminateVisibility(true);
			
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
		
	    	XMLParser parser = new XMLParser();
			String xml = parser.getXmlFromUrl(MainActivity.URL_SETTREEPREQUESTREFUSEFROMDRIVER + "?userid=" + LoginDisplayActivity.userId + "&requestid=" + requestId);
			
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
					
					MainActivity.initposition = 0;
					Intent i = new Intent(ApplicationContextProvider.getContext(), MainActivity.class);
		    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    		ApplicationContextProvider.getContext().startActivity(i);
				}
				else{
					MainActivity.displayToast("Veuillez réessayer plus tard : " + result.get(MainActivity.KEY_ERRCODE));
				}
			}
			activity.setProgressBarIndeterminateVisibility(false);
		}
	}
}