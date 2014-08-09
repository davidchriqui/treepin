package com.treep.fr;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.BadTokenException;
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

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class MapDriverTreepNotConfirmedFragment extends Fragment implements LocationListener {
     
	private Typeface bellofont;
	private Typeface fb;
	
	private MapView mapView;
	static GoogleMap mMap;
	private TextView infobanner ;
	private StringBuilder infobannertxt;
	private GPSTracker gps;
    
 // All static variables
    URL url;
    
    private Marker myMarker;
 	
    ArrayList<HashMap<String,String>> alUserPosition  = new ArrayList<HashMap<String,String>>();
 	
 	private double latdep;
 	private double lngdep;
 	private double latdest;
 	private double lngdest;
 	
 	
 	
 	private UIUpdater userPositionListUpdater;
 	private UIUpdater setUserPositionUpdater;
 	
 	private LatLng latLngMyPosition;
 	
 	
 	private DialogPleaseWait dialogPleaseWait;
 	
 	
 	static int indexDriverList=0;
	static int indexUserList=0;
 	//DRIVER MODE
 	
 	private LinearLayout idleDriverLayout;
 	private Button treepListButton;
 	private TextView tvtreepDriverResponseSentCount;
 	

 	private ArrayList<HashMap<String, String>> alMapDistanceTime = new ArrayList<HashMap<String, String>>();
 	private ArrayList<Integer>alDistanceTime = new ArrayList<Integer>();
 	private ArrayList<Marker> userMarkerList = new ArrayList<Marker>();
 	
 	
	  
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
	        if(userPositionListUpdater != null){
	     		userPositionListUpdater.stopUpdates();
	     	}
	     	if(setUserPositionUpdater != null){
	     		setUserPositionUpdater.stopUpdates();
	     	}
		 	
	    }

	    @Override
	    public void onStop() {
	        super.onStop();
	        if(userPositionListUpdater != null){
	     		userPositionListUpdater.stopUpdates();
	     	}
	     	if(setUserPositionUpdater != null){
	     		setUserPositionUpdater.stopUpdates();
	     	}
	    }

	  
	  
	@SuppressWarnings("unchecked")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			View v = inflater.inflate(R.layout.fragment_mapdrivertreepnotconfirmed, container, false);
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
				
				
				
				idleDriverLayout = (LinearLayout)v.findViewById(R.id.idleDriverLayout);
				idleDriverLayout.setVisibility(View.GONE);
				
				tvtreepDriverResponseSentCount = (TextView)v.findViewById(R.id.tvtreepDriverResponseSentCount);
				tvtreepDriverResponseSentCount.setVisibility(View.GONE);
						
				treepListButton = (Button)v.findViewById(R.id.treepListButton);
				treepListButton.setTypeface(fb);
				treepListButton.setOnClickListener(new View.OnClickListener(){
	
					@Override
					public void onClick(View v) {
						getActivity().setProgressBarIndeterminateVisibility(true);
		         		
						if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
				        	
							MainActivity.displayToast("Problème de connexion internet. Veuillez réessayer.");
							MapDriverTreepNotConfirmedFragment.this.showSettingsAlert();
						}
		     			else{
		     				StoreMainUserInfoNotifFromXML atUserInfoNotifFromXml = new StoreMainUserInfoNotifFromXML(getActivity());
		     				atUserInfoNotifFromXml.execute();
		     			}
						
					}
					
				});
				
				if(userPositionListUpdater != null){
			 		userPositionListUpdater.stopUpdates();
			 	}
			 	if(setUserPositionUpdater != null){
			 		setUserPositionUpdater.stopUpdates();
			 	}
				
				if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
		        	
					this.showSettingsAlert();
		           
				}
				else{
					Bundle bundle = this.getArguments();
					try{
					alUserPosition  = (ArrayList<HashMap<String,String>>) bundle.getSerializable("alUserPosition");
					}
					catch(NullPointerException e){
						
					}
			  		
					mapView = (MapView) v.findViewById(R.id.map);
					mapView.onCreate(savedInstanceState);
					
					// Gets to GoogleMap from the MapView and does initialization stuff
					
					
					mMap = mapView.getMap();
					
					mMap.clear();
					
					mMap.getUiSettings().setZoomControlsEnabled(false);
					
					
					infobanner = (TextView) v.findViewById(R.id.infobanner);
					infobanner.setTypeface(fb);
					infobanner.setVisibility(View.GONE);
					
					myLocationButton = (Button) v.findViewById(R.id.mypositionbutton);
					myLocationButton.setOnClickListener(clickListenerMyLocationButton);
					
			 
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
			        MainActivity.myLatitude = gps.getLatitude();
			        MainActivity.myLongitude = gps.getLongitude();
			        // Creating a LatLng object for the current location
			        latLngMyPosition = new LatLng(MainActivity.myLatitude, MainActivity.myLongitude);
			        
			        latdep = MainActivity.myLatitude;
			        lngdep=MainActivity.myLongitude;
			        MarkerOptions a = new MarkerOptions().position(latLngMyPosition);
				    myMarker = mMap.addMarker(a.title("Moi").icon(BitmapDescriptorFactory.fromResource(R.drawable.pinmyposition)));
				   
					        
			        
					(new GetAddressTask(getActivity(),MainActivity.myLatitude,MainActivity.myLongitude,latLngMyPosition, myMarker, null)).execute();
						
			        //Move the camera instantly to my position with a zoom of 15.
			  		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngMyPosition, 45));
			  		// Zoom in, animating the camera.
			  		mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null); 
			  		
					infobanner.setVisibility(View.VISIBLE);
					idleDriverLayout.setVisibility(View.VISIBLE);
					
		  			
					if(alUserPosition.size() == 0){
						
						infobannertxt = new StringBuilder();
						infobannertxt.append("En attente de treepers...");
						infobanner.setText(infobannertxt);
						//Toast.makeText( ApplicationContextProvider.getContext(), "Aucun conducteur à proximité", Toast.LENGTH_SHORT).show();
						
					}
					else{
						if(alUserPosition.get(0).containsKey(MainActivity.KEY_TIMEOUT)){
			        		
							DialogTimeout dialogTimeout = new DialogTimeout(getActivity());
							try {
								dialogTimeout.show();
						    } 
							catch (BadTokenException e){
						    }
							if(dialogPleaseWait != null){
								dialogPleaseWait.dismiss();
							}
			        		
			        	}
			        	
						else{
							alDistanceTime = new ArrayList<Integer>();
							alMapDistanceTime = new ArrayList<HashMap<String, String>>();
							userMarkerList = new ArrayList<Marker>();
							
							for(HashMap<String, String> map : alUserPosition){
								
								String username = map.get(MainActivity.KEY_FIRSTNAME) + " " + map.get(MainActivity.KEY_LASTNAME).charAt(0) + ".";
								
								DisplayUserPinList displayUserPinList = new DisplayUserPinList(MainActivity.myLatitude,MainActivity.myLongitude,username,map,mMap,alDistanceTime,userMarkerList);
								displayUserPinList.execute();
								
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
									 infobannertxt.append("treeper le plus proche à ").append(Collections.min(alDistanceTime)).append(" min");
									 infobanner.setText(infobannertxt.toString());
					        	 }
					         } 
					    }, 3000);
					}
						  	
					SetUserPositionFromXML setUserPositionFromXML = new SetUserPositionFromXML(getActivity(), MainActivity.myLatitude, MainActivity.myLongitude);
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
			  		
			  		userPositionListUpdater = new UIUpdater(new Runnable() {
			  	         @Override 
			  	         public void run() {
			  	        	UpdateUserPositionFromXML updateUserPositionFromXML = new UpdateUserPositionFromXML(getActivity());
			    	    	updateUserPositionFromXML.execute();
			  	         }
			  	    }, 15000);
	
				  	// Start updates
			  		userPositionListUpdater.startUpdates();
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
		}		
	}
	
	
 
	@Override
	public void onDestroy() {
		super.onDestroy();
		//mapView.onDestroy();
		if(userPositionListUpdater != null){
	 		userPositionListUpdater.stopUpdates();
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
		// create class object
        gps = new GPSTracker(getActivity(),ApplicationContextProvider.getContext() );
	        // Getting latitude of the current location
	        // Getting longitude of the current location
        MainActivity.myLatitude = gps.getLatitude();
        MainActivity.myLongitude = gps.getLongitude();
        // Creating a LatLng object for the current location
        latLngMyPosition = new LatLng(MainActivity.myLatitude, MainActivity.myLongitude);
        
		(new GetAddressTask(getActivity(),MainActivity.myLatitude,MainActivity.myLongitude,latLngMyPosition, myMarker, null)).execute();
			
        //Move the camera instantly to my position with a zoom of 15.
  		//mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngMyPosition,mMap.getCameraPosition().zoom));
  		// Zoom in, animating the camera.
		
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
	 
	

		private class UpdateUserPositionFromXML extends AsyncTask<Void, Integer, ArrayList<HashMap<String, String>>> {
			
			private Activity activity;
			private ArrayList<HashMap<String, String>> alUserPosition = new ArrayList<HashMap<String, String>>();
			
			public UpdateUserPositionFromXML(Activity activity){
				this.activity=activity;
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
				String xml = parser.getXmlFromUrl(MainActivity.URL_USERPOSITIONLIST + "?userid=" + LoginDisplayActivity.userId);
				
				if(xml == null){
					alUserPosition = null;
				}
				else{
					if(xml == "timeout"){
						alUserPosition.add(mapTimeout);
					}
					
					else{
						
						Document doc = null;
						try{
							doc = parser.getDomElement(xml); // getting DOM element
						}
						catch(DOMException e){
							
						}
						
						if(doc == null){
							alUserPosition = null;
						}
						else{
							
							
							NodeList nl_UserPositionList = doc.getElementsByTagName(MainActivity.KEY_USERPOSITIONLIST);
							for (int i = 0; i < nl_UserPositionList.getLength(); i++) {
								Element e = (Element) nl_UserPositionList.item(i);
								NodeList nl_UserPosition = e.getElementsByTagName(MainActivity.KEY_USERPOSITION);
								// looping through all xml nodes <KEY_USER>
								for (int j = 0; j < nl_UserPosition.getLength(); j++) {
									// creating new HashMap
									HashMap<String, String> mapDriverPosition = new HashMap<String, String>();
									Element t = (Element) nl_UserPosition.item(j);
									// adding each child node to HashMap key => value
									mapDriverPosition.put(MainActivity.KEY_USERID, parser.getValue(t, MainActivity.KEY_USERID));
									mapDriverPosition.put(MainActivity.KEY_FIRSTNAME, parser.getValue(t, MainActivity.KEY_FIRSTNAME));
									mapDriverPosition.put(MainActivity.KEY_LASTNAME, parser.getValue(t, MainActivity.KEY_LASTNAME));
									mapDriverPosition.put(MainActivity.KEY_NBTREEP, parser.getValue(t, MainActivity.KEY_NBTREEP));
									mapDriverPosition.put(MainActivity.KEY_RATING, parser.getValue(t, MainActivity.KEY_RATING));
									mapDriverPosition.put(MainActivity.KEY_LATITUDE, parser.getValue(t, MainActivity.KEY_LATITUDE));
									mapDriverPosition.put(MainActivity.KEY_LONGITUDE, parser.getValue(t, MainActivity.KEY_LONGITUDE));
									mapDriverPosition.put(MainActivity.KEY_ISBUSY, parser.getValue(t, MainActivity.KEY_ISBUSY));
									
									// adding HashList to ArrayList
									
									alUserPosition.add(mapDriverPosition);
								}
							}
						}
					}
				}
				
				return alUserPosition;
				
			}

			
			@SuppressLint("NewApi")
			protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
				
				if(result == null){
					
				}
				else{
					if(result.size() == 0){
						
						for(int i = 0; i< userMarkerList.size(); i++){
							userMarkerList.get(i).remove();
						}
						// create class object
				        gps = new GPSTracker(getActivity(),ApplicationContextProvider.getContext() );
					        // Getting latitude of the current location
					        // Getting longitude of the current location
				        MainActivity.myLatitude = gps.getLatitude();
				        MainActivity.myLongitude = gps.getLongitude();
				        // Creating a LatLng object for the current location
				        latLngMyPosition = new LatLng(MainActivity.myLatitude, MainActivity.myLongitude);
				        
						(new GetAddressTask(getActivity(),MainActivity.myLatitude,MainActivity.myLongitude,latLngMyPosition, myMarker, null)).execute();
							
				        //Move the camera instantly to my position with a zoom of 15.
				  		//mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngMyPosition,mMap.getCameraPosition().zoom));
				  		// Zoom in, animating the camera.
				  		
						infobannertxt = new StringBuilder();
						infobannertxt.append("En attente de treepers...");
						infobanner.setText(infobannertxt.toString());
						
						
					}
					else{
						if(result.get(0).containsKey(MainActivity.KEY_TIMEOUT)){
							
			        	}
						else{
							for(int i = 0; i< userMarkerList.size(); i++){
								userMarkerList.get(i).remove();
							}
							
							
							// create class object
					        gps = new GPSTracker(getActivity(),ApplicationContextProvider.getContext() );
						        // Getting latitude of the current location
						        // Getting longitude of the current location
					        MainActivity.myLatitude = gps.getLatitude();
					        MainActivity.myLongitude = gps.getLongitude();
					        // Creating a LatLng object for the current location
					        latLngMyPosition = new LatLng(MainActivity.myLatitude, MainActivity.myLongitude);
					        
					        
							(new GetAddressTask(getActivity(),MainActivity.myLatitude,MainActivity.myLongitude,latLngMyPosition,myMarker, null)).execute();
								
					        //Move the camera instantly to my position with a zoom of 15.
					  		//mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngMyPosition, 45));
					  		// Zoom in, animating the camera.
							
							alDistanceTime = new ArrayList<Integer>();
							alMapDistanceTime = new ArrayList<HashMap<String, String>>();
							userMarkerList = new ArrayList<Marker>();
							
							
							
							for(HashMap<String, String> map : alUserPosition){
								
								String username = map.get(MainActivity.KEY_FIRSTNAME) + " " + map.get(MainActivity.KEY_LASTNAME).charAt(0) + ".";
								DisplayUserPinList displayUserPinList = new DisplayUserPinList(MainActivity.myLatitude,MainActivity.myLongitude,username,map,mMap,alDistanceTime,userMarkerList);
								displayUserPinList.execute();
						    }

							Handler handler = new Handler(); 
						    handler.postDelayed(new Runnable() { 
						         public void run() {
						        	 if(alDistanceTime.size() != 0){
							        	 infobannertxt = new StringBuilder();
										 infobannertxt.append("treeper le plus proche à ").append(Collections.min(alDistanceTime)).append(" min");
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