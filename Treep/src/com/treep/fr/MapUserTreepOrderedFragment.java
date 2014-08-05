package com.treep.fr;


import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.BadTokenException;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParsePush;
import com.parse.SendCallback;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class MapUserTreepOrderedFragment extends Fragment implements LocationListener {
     
	private Typeface bellofont;
	private Typeface fb;
	
	private MapView mapView;
	static GoogleMap mMap;
	private TextView infobanner ;
	private StringBuilder infobannertxt;
	private GPSTracker gps;
	static double myLatitude;
    static double myLongitude;
    
 // All static variables
    URL url;
    
    
    private TextView infobannerordered;
    private TextView infobannercount;
 	

 	private LinearLayout llWaitOrderLayout;
 	private TextView tvAddressDep;
 	private TextView tvAddressDest;
 	private Button cancelOrderButton;
 	
 	
 	private double latdep;
 	private double lngdep;
 	private double latdest;
 	private double lngdest;
 	
 	private  Marker myMarker;

 	ArrayList<HashMap<String,String>> alDriverPosition;
	HashMap<String, String> mapTreepOrderInfo;
 	
 	private CountDownTimer treepOrderedTimer;

 	private UIUpdater driverPositionListUpdater;
 	private UIUpdater setUserPositionUpdater;
 	
 	
 	private LatLng latLngMyPosition;
 	private LatLng latLngDep;
 	private LatLng latLngDest;
 	
 	
 	private DialogPleaseWait dialogPleaseWait;
 	
 	
 	static int indexDriverList=0;
	static int indexUserList=0;

 	static ArrayList<HashMap<String, String>> alMapDistanceTime = new ArrayList<HashMap<String, String>>();
 	static ArrayList<Integer>alDistanceTime = new ArrayList<Integer>();
 	static ArrayList<Marker> driverMarkerList = new ArrayList<Marker>();
 	
 	private UpdateDriverPositionFromXML updateDriverPositionFromXML;
	  
	private Button myLocationButton;
    private OnClickListener clickListenerMyLocationButton = new View.OnClickListener() {
    	
	    @Override
	    public void onClick(View v) {
	                // Creating a LatLng object for the current location
	    			latLngMyPosition = new LatLng(gps.getLatitude(), gps.getLongitude());
	    	      //Move the camera instantly to hamburg with a zoom of 15.
	    			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngMyPosition, 15));
	    }
	  };
    

	    @Override
	    public void onPause() {
	        super.onPause();
	        if(treepOrderedTimer != null){
	        	treepOrderedTimer.cancel();
	        	treepOrderedTimer = null;
		 	}
	        
	        if(driverPositionListUpdater != null){
	        	driverPositionListUpdater.stopUpdates();
		 	}
		 	if(setUserPositionUpdater != null){
		 		setUserPositionUpdater.stopUpdates();
		 	}
	    }

	    @Override
	    public void onStop() {
	        super.onStop();
	        if(treepOrderedTimer != null){
	        	treepOrderedTimer.cancel();
		 		treepOrderedTimer = null;
		 	}
	        if(driverPositionListUpdater != null){
	        	driverPositionListUpdater.stopUpdates();
		 	}
		 	
		 	if(setUserPositionUpdater != null){
		 		setUserPositionUpdater.stopUpdates();
		 	}
	    }

	    private class DialogTreeperCancelConfirm extends Dialog implements
	    android.view.View.OnClickListener {
		
			  public Activity activity;
			  public Dialog d;
			  public Button confirmButton;
			  public Button cancelButton;
			  public String treepId;
			
			  public DialogTreeperCancelConfirm(Activity activity, String treepId) {
			    super(activity);
			    // TODO Auto-generated constructor stub
			    this.activity = activity;
			    this.treepId=treepId;
			  }
			
			  @Override
			  protected void onCreate(Bundle savedInstanceState) {
			    super.onCreate(savedInstanceState);
			    requestWindowFeature(Window.FEATURE_NO_TITLE);
			    setContentView(R.layout.dialog_treepercancelconfirm);
			    confirmButton = (Button) findViewById(R.id.confirmButton);
			    confirmButton.setOnClickListener(this);
			    cancelButton = (Button) findViewById(R.id.cancelButton);
			    cancelButton.setOnClickListener(this);
			     

			  }
			
			  @Override
			  public void onClick(View v) {
			    switch (v.getId()) {
			    case R.id.confirmButton:
			    	if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
			        	
						MainActivity.displayToast(R.string.httpTimeOut);
			    	}
			    	else{
			    		if(driverPositionListUpdater != null){
			            	driverPositionListUpdater.stopUpdates();
			    	 	}
			    		if(updateDriverPositionFromXML != null){
			    			updateDriverPositionFromXML.cancel(true);
			    		}
			    		SetTreepCancelFromTreeperFromXML setTreepCancelFromTreeperFromXML = new SetTreepCancelFromTreeperFromXML(activity, treepId);
			    		setTreepCancelFromTreeperFromXML.execute();
		          	  	dismiss();
			    	}
			    break;
			    case R.id.cancelButton:
			    	dismiss();
			    break;
			    default:
			      break;
			    }
			    dismiss();
			  }
		}
	  
	  
	@SuppressWarnings("unchecked")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			View v = inflater.inflate(R.layout.fragment_mapusertreepordered, container, false);
			if(LoginDisplayActivity.userId == null || LoginDisplayActivity.userId == "" ){
				Intent i = new Intent(getActivity(), LoginDisplayActivity.class);
        		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	            startActivity(i);
			}
			else{
				
				dialogPleaseWait = new DialogPleaseWait(getActivity());
				//initilize the custom typefont
				bellofont = Typeface.createFromAsset(getActivity().getAssets(), "bello.ttf");
				fb = Typeface.createFromAsset(getActivity().getAssets(), "fb.ttf");
				
				
				llWaitOrderLayout = (LinearLayout) v.findViewById(R.id.waitOrderLayout);
				
				
				
			 	tvAddressDep = (TextView) v.findViewById(R.id.tvAddressDep);
			 	tvAddressDest= (TextView) v.findViewById(R.id.tvAddressDest);
			 	
				
				cancelOrderButton = (Button)v.findViewById(R.id.cancelOrderButton);
				cancelOrderButton.setTypeface(fb);
				
				
			 	
			 	if(treepOrderedTimer != null){
			 		treepOrderedTimer.cancel();
			 		treepOrderedTimer = null;
			 	}
				
				if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
		        	
					this.showSettingsAlert();
		           
				}
				else{
					
					Bundle bundle = this.getArguments();
					alDriverPosition  = (ArrayList<HashMap<String,String>>) bundle.getSerializable("alDriverPosition");
					mapTreepOrderInfo = (HashMap<String, String>) bundle.getSerializable("mapTreepOrderInfo");
					
			  		
					mapView = (MapView) v.findViewById(R.id.map);
					mapView.onCreate(savedInstanceState);
					
					// Gets to GoogleMap from the MapView and does initialization stuff
					mMap = mapView.getMap();
					mMap.clear();
					mMap.getUiSettings().setZoomControlsEnabled(false);
					
					
					infobanner = (TextView) v.findViewById(R.id.infobanner);
					infobanner.setTypeface(fb);
					
					infobannerordered = (TextView) v.findViewById(R.id.infobannerordered);
					
					infobannercount = (TextView) v.findViewById(R.id.infobannercount);
					infobannercount.setText("Drivers contactés : 0");
					
					Animation blinkAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.blink);
			        
					infobannerordered.startAnimation(blinkAnim);
					
					llWaitOrderLayout.startAnimation(blinkAnim);
					
					myLocationButton = (Button) v.findViewById(R.id.mypositionbutton);
					myLocationButton.setOnClickListener(clickListenerMyLocationButton);
					
					cancelOrderButton.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View arg0) {
							DialogTreeperCancelConfirm dialogTreeperCancelConfirm = new DialogTreeperCancelConfirm(getActivity(), mapTreepOrderInfo.get(MainActivity.KEY_TREEPID));
							try{
								dialogTreeperCancelConfirm.show();
						    } 
							catch (BadTokenException e){
								
						    }
						}
							
						});
					
			 
					// Needs to call MapsInitializer before doing any CameraUpdateFactory calls
					try {
						MapsInitializer.initialize(ApplicationContextProvider.getContext());
					} catch (GooglePlayServicesNotAvailableException e) {
						e.printStackTrace();
					}
					
					mMap.clear();
					// create class object
			        gps = new GPSTracker(getActivity(),ApplicationContextProvider.getContext() );
				        // Getting latitude of the current location
				        // Getting longitude of the current location
			        myLatitude = gps.getLatitude();
			        myLongitude = gps.getLongitude();
			        // Creating a LatLng object for the current location
			        latLngMyPosition = new LatLng(myLatitude, myLongitude);
			        
			        MarkerOptions a = new MarkerOptions().position(latLngMyPosition);
				    myMarker = mMap.addMarker(a.icon(BitmapDescriptorFactory.fromResource(R.drawable.pinmyposition)));
				    
					(new GetAddressTask(getActivity(),myLatitude,myLongitude,latLngMyPosition, myMarker)).execute();
						
			        //Move the camera instantly to my position with a zoom of 15.
			  		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngMyPosition, 45));
			  		// Zoom in, animating the camera.
			  		mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null); 
			  		
			  	
		  			tvAddressDep.setText(mapTreepOrderInfo.get(MainActivity.KEY_ADDRESSDEPNOW));
		  			tvAddressDest.setText(mapTreepOrderInfo.get(MainActivity.KEY_ADDRESSDESTNOW));
		  			
		  	       if(Long.parseLong(mapTreepOrderInfo.get(MainActivity.KEY_TREEPORDERTIMEREMAINING)) <= 0){
		  	    	 Intent i = new Intent(getActivity(), LoginDisplayActivity.class);
		  	    	 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		             startActivity(i);
		  	       }
		  	       else{
		  	    	 treepOrderedTimer = new CountDownTimer(Long.parseLong(mapTreepOrderInfo.get(MainActivity.KEY_TREEPORDERTIMEREMAINING))*1000, 1000) {

		  	    	     public void onTick(long millisUntilFinished) {
		  	    	    	 long durationSeconds = millisUntilFinished / 1000;
		  	    	    	
		  	    	     }

		  	    	     public void onFinish() {
		  	    	    	if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
		  	  	        	
		  	  				showSettingsAlert();
		  	  	            
			  	  			}
			  	  			else{				  	  			
				  	  		JSONObject object = new JSONObject();
					        try {
			                    object.put("alert", "Veuillez faire une nouvelle demande");
			                    object.put("title", "Treep : votre demande a expirée.");
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
				  	  			
				  	  			llWaitOrderLayout.setVisibility(View.GONE);
				  	  			DialogTreepNotConfirmed dialogTreepNotConfirmed = new DialogTreepNotConfirmed(getActivity());
				  	  			
					  	  		try {
									dialogTreepNotConfirmed.show();
							    } 
								catch (BadTokenException e){
							    }
			  	  			 }
		  	    	     }
		  	    	  }.start();
		  	       }
		  	       
		  	       for(int i = 0; i< driverMarkerList.size(); i++){
						driverMarkerList.get(i).remove();
					
		  	       }
		  			
		  	       if(alDriverPosition.size() == 0){
						
						infobannertxt = new StringBuilder();
						infobannertxt.append("En attente de drivers...");
						infobanner.setText(infobannertxt.toString());
						//Toast.makeText( ApplicationContextProvider.getContext(), "Aucun conducteur à proximité", Toast.LENGTH_SHORT).show();
						
					}
					else{
						if(alDriverPosition.get(0).containsKey(MainActivity.KEY_TIMEOUT)){
			        		
							infobannertxt = new StringBuilder();
							infobannertxt.append("En attente de drivers...");
			        	}
						else{
							alDistanceTime = new ArrayList<Integer>();
							alMapDistanceTime = new ArrayList<HashMap<String, String>>();
							driverMarkerList = new ArrayList<Marker>();
							
							
							for(HashMap<String, String> map : alDriverPosition){
								
								String username = map.get(MainActivity.KEY_FIRSTNAME) + " " + map.get(MainActivity.KEY_LASTNAME).charAt(0) + ".";
								
								DisplayDriverPinList getDistance = new DisplayDriverPinList(myLatitude,myLongitude,username,map,mMap,alDistanceTime,driverMarkerList);
								getDistance.execute();
								
						    }
						}
						infobannertxt = new StringBuilder();
						infobannertxt.append("Calcul en cours...");
						infobanner.setText(infobannertxt.toString());
						 
						Handler handler = new Handler(); 
					    handler.postDelayed(new Runnable() { 
					         public void run() { 
					        	 if(alDistanceTime.size() != 0){
						        	 infobannertxt = new StringBuilder();
									 infobannertxt.append("Driver le plus proche à ").append(Collections.min(alDistanceTime)).append(" min");
									 infobanner.setText(infobannertxt.toString());
					        	 }
					         } 
					    }, 3000);
					}  	
		  	       
		  	    	SetUserPositionFromXML setUserPositionFromXML = new SetUserPositionFromXML(getActivity(), myLatitude, myLongitude);
			  		setUserPositionFromXML.execute();
			  		
			  		setUserPositionUpdater = new UIUpdater(new Runnable() {
			  	         @Override 
			  	         public void run() {
			  	        	SetUserPositionFromXML setUserPositionFromXML = new SetUserPositionFromXML(getActivity(), MainActivity.myLatitude, MainActivity.myLongitude);
					  		setUserPositionFromXML.execute();
			  	         }
			  	    });
	
				  	// Start updates
			  		setUserPositionUpdater.startUpdates();
			  		
			  		driverPositionListUpdater = new UIUpdater(new Runnable() {
			  	         @Override 
			  	         public void run() {
			  	        	updateDriverPositionFromXML = new UpdateDriverPositionFromXML(getActivity(),mapTreepOrderInfo);
					  		updateDriverPositionFromXML.execute();
			  	         }
			  	    }, 15000);
	
				  	// Start updates
			  		driverPositionListUpdater.startUpdates();
			  		
			  		
				}
				
				getActivity().setProgressBarIndeterminateVisibility(false);
			}
			return v;
	}

			
 
	@Override
	public void onResume() {
		super.onResume();
		if(LoginDisplayActivity.userId == null || LoginDisplayActivity.userId == "" ){
			Intent i = new Intent(getActivity(), LoginDisplayActivity.class);
    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
		}
		else{
			mapView.onResume();
			if(treepOrderedTimer != null){
				treepOrderedTimer.cancel();
				treepOrderedTimer = null;
		 	}
			
			if(driverPositionListUpdater != null){
	        	driverPositionListUpdater.startUpdates();
		 	}
		 	if(setUserPositionUpdater != null){
		 		setUserPositionUpdater.startUpdates();
		 	}
		}		
	}
	
	
 
	@Override
	public void onDestroy() {
		super.onDestroy();
		//mapView.onDestroy();
		if(treepOrderedTimer != null){
			treepOrderedTimer.cancel();
			treepOrderedTimer = null;
	 	}
		
		if(driverPositionListUpdater != null){
        	driverPositionListUpdater.stopUpdates();
	 	}
	 	if(setUserPositionUpdater != null){
	 		setUserPositionUpdater.stopUpdates();
	 	}
	}
 
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}



	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	
	 public void showSettingsAlert(){
		 DialogSettingInternet dialogSettingInternet = new DialogSettingInternet(getActivity());
		 try{

			 dialogSettingInternet.show();
		 }
		 catch(BadTokenException e){
			 
		 }
	    
	 }
	 
	 private class DialogSettingInternet extends Dialog implements
	    android.view.View.OnClickListener {
		
			  public Activity activity;
			  public Dialog d;
			  public Button confirmButton;
			  public Button cancelButton;
			
			  public DialogSettingInternet(Activity activity) {
			    super(activity);
			    // TODO Auto-generated constructor stub
			    this.activity = activity;
			  }
			
			  @Override
			  protected void onCreate(Bundle savedInstanceState) {
			    super.onCreate(savedInstanceState);
			    requestWindowFeature(Window.FEATURE_NO_TITLE);
			    setContentView(R.layout.dialog_settinginternet);
			    confirmButton = (Button) findViewById(R.id.confirmButton);
			    confirmButton.setOnClickListener(this);
			    cancelButton = (Button) findViewById(R.id.cancelButton);
			    cancelButton.setOnClickListener(this);
			     

			  }
			
			  @Override
			  public void onClick(View v) {
			    switch (v.getId()) {
			    case R.id.confirmButton:
			    	Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
			    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                startActivity(intent);
			    break;
			    case R.id.cancelButton:
			    	dismiss();
			    break;
			    default:
			      break;
			    }
			  }
		}
	 
	
	 private class DialogTreepNotConfirmed extends Dialog implements
	    android.view.View.OnClickListener {
		
		  public Context context;
		  public Button confirmButton;
		
		  private Typeface fb;
		
		  public DialogTreepNotConfirmed(Context context) {
		    super(context);
		    // TODO Auto-generated constructor stub
		    this.context = context;
		  }
		  
		  
		
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    setContentView(R.layout.dialog_treepnotconfirmed);
		    fb = Typeface.createFromAsset(context.getAssets(), "fb.ttf");
		    confirmButton = (Button) findViewById(R.id.confirmButton);
		    confirmButton.setOnClickListener(this);
		    confirmButton.setTypeface(fb);
		    
		  }
		
		  @Override
		  public void onClick(View v) {
		    switch (v.getId()) {
		    case R.id.confirmButton:
				if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
						        	
					showSettingsAlert();
		    	}
		    	else{
		    		dismiss();
		    		Intent i = new Intent(getActivity(), MainActivity.class);
		    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		            startActivity(i);
		    		}
		      break;
		    default:
		      break;
		    }
		    dismiss();
		  }
		}
		
	 
	 
	 
		

		private class UpdateDriverPositionFromXML extends AsyncTask<Void, Integer, ArrayList<HashMap<String, String>>> {
			
			private Activity activity;
			private ArrayList<HashMap<String, String>> alDriverPosition = new ArrayList<HashMap<String, String>>();
			private HashMap<String,String> mapTreepOrderInfo = new HashMap<String,String>();
			
			public UpdateDriverPositionFromXML(Activity activity, HashMap<String,String> mapTreepOrderInfo){
				this.activity=activity;
				this.mapTreepOrderInfo=mapTreepOrderInfo;
			}
			
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			 	
				
			}

			@Override
			protected void onProgressUpdate(Integer... values){
				super.onProgressUpdate(values);
				
			}

			@Override
			protected ArrayList<HashMap<String, String>> doInBackground(Void... arg0) {
				HashMap<String,String> mapTimeout = new HashMap<String,String>();
				mapTimeout.put(MainActivity.KEY_TIMEOUT, "1");
				
				if (android.os.Build.VERSION.SDK_INT > 9) 
		    	{ StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }
			
		    	XMLParser parser = new XMLParser();
		    	String xml = parser.getXmlFromUrl(MainActivity.URL_USERINFO + "?userid=" + LoginDisplayActivity.userId +"&lat=" + myLatitude + "&lng=" + myLongitude);
				
				if(xml == null){
					alDriverPosition = null;
				}
				else{
					if(xml == "timeout"){
						alDriverPosition.add(mapTimeout);
					}
					
					else{
						
				         // getting XML from URL
						Document doc = parser.getDomElement(xml); // getting DOM element
						
						if(doc == null){
							alDriverPosition = null;
						}
						else{
							
							
							NodeList nl_DriverPositionList = doc.getElementsByTagName(MainActivity.KEY_DRIVERPOSITIONLIST);
							for (int i = 0; i < nl_DriverPositionList.getLength(); i++) {
								Element e = (Element) nl_DriverPositionList.item(i);
								NodeList nl_DriverPosition = e.getElementsByTagName(MainActivity.KEY_DRIVERPOSITION);
								// looping through all xml nodes <KEY_USER>
								for (int j = 0; j < nl_DriverPosition.getLength(); j++) {
									// creating new HashMap
									HashMap<String, String> mapDriverPosition = new HashMap<String, String>();
									Element t = (Element) nl_DriverPosition.item(j);
									// adding each child node to HashMap key => value
									mapDriverPosition.put(MainActivity.KEY_USERID, parser.getValue(t, MainActivity.KEY_USERID));
									mapDriverPosition.put(MainActivity.KEY_FIRSTNAME, parser.getValue(t, MainActivity.KEY_FIRSTNAME));
									mapDriverPosition.put(MainActivity.KEY_LASTNAME, parser.getValue(t, MainActivity.KEY_LASTNAME));
									mapDriverPosition.put(MainActivity.KEY_NBTREEP, parser.getValue(t, MainActivity.KEY_NBTREEP));
									mapDriverPosition.put(MainActivity.KEY_RATING, parser.getValue(t, MainActivity.KEY_RATING));
									mapDriverPosition.put(MainActivity.KEY_LATITUDE, parser.getValue(t, MainActivity.KEY_LATITUDE));
									mapDriverPosition.put(MainActivity.KEY_LONGITUDE, parser.getValue(t, MainActivity.KEY_LONGITUDE));
									mapDriverPosition.put(MainActivity.KEY_ISBUSY, parser.getValue(t, MainActivity.KEY_ISBUSY));
									mapDriverPosition.put(MainActivity.KEY_DRIVERTREEPREQUESTED, parser.getValue(t, MainActivity.KEY_DRIVERTREEPREQUESTED));
									mapDriverPosition.put(MainActivity.KEY_DRIVERTREEPREQUESTID, parser.getValue(t, MainActivity.KEY_DRIVERTREEPREQUESTID));
									mapDriverPosition.put(MainActivity.KEY_LATDEP, parser.getValue(t, MainActivity.KEY_LATDEP));
									mapDriverPosition.put(MainActivity.KEY_LNGDEP, parser.getValue(t, MainActivity.KEY_LNGDEP));
									mapDriverPosition.put(MainActivity.KEY_LATDEST, parser.getValue(t, MainActivity.KEY_LATDEST));
									mapDriverPosition.put(MainActivity.KEY_LNGDEST, parser.getValue(t, MainActivity.KEY_LNGDEST));
									
									// adding HashList to ArrayList
									
									alDriverPosition.add(mapDriverPosition);
								}
							}
						}
					}
				}
				
				return alDriverPosition;
				
			}

			
			@SuppressLint("NewApi")
			protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
				
				if(result == null){
					
				}
				else{
					if(result.size() == 0){
						for(int i = 0; i< driverMarkerList.size(); i++){
							driverMarkerList.get(i).remove();
						}
						
						// create class object
				        gps = new GPSTracker(getActivity(),ApplicationContextProvider.getContext() );
					        // Getting latitude of the current location
					        // Getting longitude of the current location
				        myLatitude = gps.getLatitude();
				        myLongitude = gps.getLongitude();
				        // Creating a LatLng object for the current location
				        latLngMyPosition = new LatLng(myLatitude, myLongitude);
				        
						(new GetAddressTask(getActivity(),myLatitude,myLongitude,latLngMyPosition, myMarker)).execute();
							
				        //Move the camera instantly to my position with a zoom of 15.
				  		//mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngMyPosition,mMap.getCameraPosition().zoom));
				  		// Zoom in, animating the camera.
				  		
						infobannertxt = new StringBuilder();
						infobannertxt.append("En attente de drivers...");
						infobanner.setText(infobannertxt.toString());
						
						
					}
					else{
						if(result.get(0).containsKey(MainActivity.KEY_TIMEOUT)){
							
			        	}
						else{
							if(driverMarkerList != null){
								for(int i = 0; i< driverMarkerList.size(); i++){
									driverMarkerList.get(i).remove();
								}
							}
							
							
							// create class object
					        gps = new GPSTracker(getActivity(),ApplicationContextProvider.getContext() );
						        // Getting latitude of the current location
						        // Getting longitude of the current location
					        myLatitude = gps.getLatitude();
					        myLongitude = gps.getLongitude();
					        // Creating a LatLng object for the current location
					        latLngMyPosition = new LatLng(myLatitude, myLongitude);
					        
					        
							(new GetAddressTask(getActivity(),myLatitude,myLongitude,latLngMyPosition,myMarker)).execute();
								
					        //Move the camera instantly to my position with a zoom of 15.
					  		//mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngMyPosition, 45));
					  		// Zoom in, animating the camera.
							
							alDistanceTime = new ArrayList<Integer>();
							alMapDistanceTime = new ArrayList<HashMap<String, String>>();
							
							
							for(HashMap<String, String> map : alDriverPosition){
								
								String username = map.get(MainActivity.KEY_FIRSTNAME) + " " + map.get(MainActivity.KEY_LASTNAME).charAt(0) + ".";
								DisplayDriverPinList getDistance = new DisplayDriverPinList(myLatitude,myLongitude,username,map,mMap,alDistanceTime,driverMarkerList);
								getDistance.execute();
						    }
							
							GetMatchedDrivers getMatchedDrivers = new GetMatchedDrivers(getActivity(),
										mapTreepOrderInfo.get(MainActivity.KEY_LATDEPNOW), 
										mapTreepOrderInfo.get(MainActivity.KEY_LNGDEPNOW), 
										mapTreepOrderInfo.get(MainActivity.KEY_LATDESTNOW), 
										mapTreepOrderInfo.get(MainActivity.KEY_LNGDESTNOW),
										mapTreepOrderInfo.get(MainActivity.KEY_ADDRESSDEPNOW), 
										mapTreepOrderInfo.get(MainActivity.KEY_ADDRESSDESTNOW),
										false, 
										result,
										infobannercount);
							getMatchedDrivers.execute();

							Handler handler = new Handler(); 
						    handler.postDelayed(new Runnable() { 
						         public void run() {
						        	 if(alDistanceTime.size() != 0){
							        	 infobannertxt = new StringBuilder();
										 infobannertxt.append("Driver le plus proche à ").append(Collections.min(alDistanceTime)).append(" min");
										 infobanner.setText(infobannertxt.toString());
						        	 }
						         } 
						    }, 3000);
						}
					}
				}
			}
		}
		
}