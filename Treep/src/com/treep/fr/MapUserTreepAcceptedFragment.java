package com.treep.fr;



import java.net.URL;
import java.util.ArrayList;
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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.BadTokenException;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.treep.fr.SlidingUpPanelLayout.PanelSlideListener;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class MapUserTreepAcceptedFragment extends Fragment implements LocationListener {
     
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
    
    private Marker myMarker;
 	
 	private LinearLayout slidingPanelLayout;
 	
 	
 	
 	private RelativeLayout spHeadLayout;
 	private ImageView spHeadListImage;
 	private TextView spHeadUsername;
 	private TextView spHeadCarModel;
 	private ImageView spHeadRating;
 	
 	private ImageView ivspOrderedDriverProfilePic;
 	private ImageView ivspOrderedDriverCarPic;
 	
 	private SlidingUpPanelLayout slidingUpPanellayout;
 	private Marker driverOrderedMarker;
 	
 	private TextView tvspOrderedDistanceTime;
 	
 	private TextView tvspOrderedAddressDep;
 	private TextView tvspOrderedAddressDest;
 	
 	private Button spCallOrderedDriverButton;
 	private Button spCancelOrderedDriverButton;
 	
 	
 	private UIUpdater setUserPositionUpdater;
 	private UIUpdater driverOrderedPositionUpdater;
 	
 	
 	private LatLng latLngMyPosition;
 	
 	private DialogPleaseWait dialogPleaseWait;
 	
 	
 	static int indexDriverList=0;
	static int indexUserList=0;
 	//DRIVER MODE
 	
 	

 	static ArrayList<HashMap<String, String>> alMapDistanceTime = new ArrayList<HashMap<String, String>>();
 	static ArrayList<Integer>alDistanceTime = new ArrayList<Integer>();
 	static ArrayList<Marker> driverMarkerList = new ArrayList<Marker>();
 	
 	
	  
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
	        if(setUserPositionUpdater != null){
		 		setUserPositionUpdater.stopUpdates();
		 	}
	        if(driverOrderedPositionUpdater != null){
	        	driverOrderedPositionUpdater.stopUpdates();
		 	}
	    }

	    @Override
	    public void onStop() {
	        super.onStop();
	        if(setUserPositionUpdater != null){
		 		setUserPositionUpdater.stopUpdates();
		 	}
	        if(driverOrderedPositionUpdater != null){
	        	driverOrderedPositionUpdater.stopUpdates();
		 	}
	    }

	  
	  
	@SuppressWarnings("unchecked")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			View v = inflater.inflate(R.layout.fragment_mapusertreepaccepted, container, false);
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
				
				slidingUpPanellayout = (SlidingUpPanelLayout) v.findViewById(R.id.sliding_layout);
				slidingPanelLayout = (LinearLayout) v.findViewById(R.id.slidingPanelLayout);
				
				
				slidingUpPanellayout.setShadowDrawable(getResources().getDrawable(R.drawable.above_shadow));
				slidingUpPanellayout.setAnchorPoint(1f);
				
				slidingUpPanellayout.setPanelSlideListener(new PanelSlideListener() {
		            @Override
		            public void onPanelExpanded(View panel) {}
		            @Override
		            public void onPanelCollapsed(View panel) {}
		            @Override
		            public void onPanelAnchored(View panel) {}
					@Override
					public void onPanelSlide(View panel, float slideOffset) {}
		        });
				
			 	
				
				if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
		        	
					this.showSettingsAlert();
		           
				}
				else{
					Bundle bundle = this.getArguments();
					final HashMap<String, String> mapTreepOrderInfo = (HashMap<String, String>) bundle.getSerializable("mapTreepOrderInfo");
					final HashMap<String, String> mapTreepOrderDriverInfo = (HashMap<String, String>) bundle.getSerializable("mapTreepOrderDriverInfo");
					
			  		
					mapView = (MapView) v.findViewById(R.id.map);
					mapView.onCreate(savedInstanceState);
					
					// Gets to GoogleMap from the MapView and does initialization stuff
					
					
					mMap = mapView.getMap();
					
					mMap.clear();
					
					mMap.getUiSettings().setZoomControlsEnabled(false);
					
					
					
					infobanner = (TextView) v.findViewById(R.id.infobanner);
					infobanner.setTypeface(fb);
					
					myLocationButton = (Button) v.findViewById(R.id.mypositionbutton);
					myLocationButton.setOnClickListener(clickListenerMyLocationButton);
					
					
					
					tvspOrderedDistanceTime  = (TextView)v.findViewById(R.id.tvspOrderedDistanceTime);
					spHeadListImage = (ImageView) v.findViewById(R.id.spHeadListImage);
				 	spHeadUsername = (TextView)v.findViewById(R.id.spHeadUsername);
				 	spHeadCarModel = (TextView)v.findViewById(R.id.spHeadCarModel);
				 	spHeadRating = (ImageView)v.findViewById(R.id.spHeadRating);
				 	spHeadLayout = (RelativeLayout) v.findViewById(R.id.spHeadLayout);
				 	ivspOrderedDriverProfilePic = (ImageView) v.findViewById(R.id.spOrderedDriverProfilePic);
				 	ivspOrderedDriverCarPic = (ImageView) v.findViewById(R.id.spOrderedDriverCarPic);
				 	tvspOrderedAddressDep  = (TextView)v.findViewById(R.id.tvspOrderedAddressDep);
				 	tvspOrderedAddressDest  = (TextView)v.findViewById(R.id.tvspOrderedAddressDest);
				 	
				 	spCallOrderedDriverButton = (Button) v.findViewById(R.id.spCallOrderedDriverButton);
				 	spCancelOrderedDriverButton = (Button) v.findViewById(R.id.spCancelOrderedDriverButton);
			 
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
				    myMarker = mMap.addMarker(a.title("Moi").icon(BitmapDescriptorFactory.fromResource(R.drawable.pinmyposition)));
				   
					        
			        
					(new GetAddressTask(getActivity(),myLatitude,myLongitude,latLngMyPosition, myMarker)).execute();
						
			        //Move the camera instantly to my position with a zoom of 15.
			  		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngMyPosition, 45));
			  		// Zoom in, animating the camera.
			  		mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null); 

					slidingPanelLayout.setVisibility(View.VISIBLE);
					infobanner.setVisibility(View.VISIBLE);
		  			final String username = mapTreepOrderDriverInfo.get(MainActivity.KEY_FIRSTNAME) + " " + mapTreepOrderDriverInfo.get(MainActivity.KEY_LASTNAME).charAt(0) + ".";
				 									
				 	
				 	spCallOrderedDriverButton.setTypeface(fb);
				 	spCancelOrderedDriverButton.setTypeface(fb);
					
					ImageLoader imageLoaderThumb = new ImageLoader(ApplicationContextProvider.getContext());
					imageLoaderThumb.DisplayImage(MainActivity.urlThumbPic(mapTreepOrderDriverInfo.get(MainActivity.KEY_DRIVERID)),spHeadListImage);
					
				 	spHeadUsername.setText(username);
				 	spHeadCarModel.setText(mapTreepOrderDriverInfo.get(MainActivity.KEY_CARMODEL));
				 	
				 	switch (Integer.parseInt(mapTreepOrderDriverInfo.get(MainActivity.KEY_RATING))) {
				       case 0:
				       	//list_rating.setImageResource(R.drawable.rating1);       
				           break;
				       case 1:
				    	   spHeadRating.setImageResource(R.drawable.rating1);
				             
				           break;
				       case 2:
				    	   spHeadRating.setImageResource(R.drawable.rating2);
				             
				           break;
				       case 3:
				    	   spHeadRating.setImageResource(R.drawable.rating3);
				             
				           break;
				       case 4:
				    	   spHeadRating.setImageResource(R.drawable.rating4);
				             
				           break;
				       case 5:
				    	   spHeadRating.setImageResource(R.drawable.rating5);
				             
				           break;
						}
					
				 	slidingUpPanellayout.setDragView(spHeadLayout);
				 	
				 	ImageLoader imageLoaderProfilePic = new ImageLoader(ApplicationContextProvider.getContext());
				 	imageLoaderProfilePic.DisplayImage(MainActivity.urlProfilePic(mapTreepOrderDriverInfo.get(MainActivity.KEY_DRIVERID)),ivspOrderedDriverProfilePic);
				 	
				 	ImageLoader imageLoaderCarPic = new ImageLoader(ApplicationContextProvider.getContext());
				 	imageLoaderCarPic.DisplayImage(mapTreepOrderDriverInfo.get(MainActivity.KEY_CARPICURL),ivspOrderedDriverCarPic);
				 	
					tvspOrderedAddressDep.setText(mapTreepOrderInfo.get(MainActivity.KEY_ADDRESSDEPNOW));
					tvspOrderedAddressDest.setText(mapTreepOrderInfo.get(MainActivity.KEY_ADDRESSDESTNOW));
				 	
					spCallOrderedDriverButton.setOnClickListener( new OnClickListener(){

						@Override
						public void onClick(View v) {
							Intent callIntent = new Intent(Intent.ACTION_DIAL);
							callIntent.setData(Uri.parse("tel:"+ mapTreepOrderDriverInfo.get(MainActivity.KEY_USERTEL)));
							startActivity(callIntent);
							
						}
						
					});
					spCancelOrderedDriverButton.setOnClickListener( new OnClickListener(){

						@Override
						public void onClick(View v) {
							DialogTreeperCancel dialogTreeperCancel = new DialogTreeperCancel(getActivity(), mapTreepOrderInfo.get(MainActivity.KEY_TREEPID),mapTreepOrderDriverInfo.get(MainActivity.KEY_DRIVERID));
							
							try {
									dialogTreeperCancel.show();
						    } 
							catch (BadTokenException e){
								MainActivity.displayToast("Veuillez appuyer sur le bouton rafraichir");
								
						    }
							
						}
						
					});
				 	
					driverOrderedMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(mapTreepOrderDriverInfo.get(MainActivity.KEY_LATITUDE)),Double.parseDouble(mapTreepOrderDriverInfo.get(MainActivity.KEY_LONGITUDE))))
							.title(username)
					        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pindriverlocation)));
					
					infobannertxt = new StringBuilder();
					infobannertxt.append("Calcul en cours...");
					infobanner.setText(infobannertxt.toString());
					
					DisplayDriverOrderedPin displayDriverOrderedPin = new DisplayDriverOrderedPin(driverOrderedMarker,mapTreepOrderDriverInfo.get(MainActivity.KEY_LATITUDE),mapTreepOrderDriverInfo.get(MainActivity.KEY_LONGITUDE),myLatitude, myLongitude,username,tvspOrderedDistanceTime,infobanner);
					displayDriverOrderedPin.execute();
					
					new ItineraireTask(ApplicationContextProvider.getContext(), mMap, Double.parseDouble(mapTreepOrderDriverInfo.get(MainActivity.KEY_LATITUDE)), Double.parseDouble(mapTreepOrderDriverInfo.get(MainActivity.KEY_LONGITUDE)), myLatitude, myLongitude, getActivity()).execute();
					
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
			  		
			  		driverOrderedPositionUpdater = new UIUpdater(new Runnable() {
			  	         @Override 
			  	         public void run() {
			  	        	UpdateDriverOrderedPositionFromXML updateDriverOrderedPositionFromXML = new UpdateDriverOrderedPositionFromXML(getActivity());
			  	        	updateDriverOrderedPositionFromXML.execute();
			  	         }
			  	    });
	
				  	// Start updates
			  		driverOrderedPositionUpdater.startUpdates();
						  		
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
		if(setUserPositionUpdater != null){
	 		setUserPositionUpdater.stopUpdates();
	 	}
        if(driverOrderedPositionUpdater != null){
        	driverOrderedPositionUpdater.stopUpdates();
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
	 
	
	 
	 private class DialogTreeperCancel extends Dialog implements
	    android.view.View.OnClickListener {
		
			  public Activity activity;
			  public Button confirmButton;
			  public Button cancelButton;
			  public String treepId;
			  public String driverId;
			
			  public DialogTreeperCancel(Activity activity, String treepId,String driverId) {
			    super(activity);
			    this.activity = activity;
			    this.treepId=treepId;
			    this.driverId=driverId;
			  }
			
			  @Override
			  protected void onCreate(Bundle savedInstanceState) {
			    super.onCreate(savedInstanceState);
			    requestWindowFeature(Window.FEATURE_NO_TITLE);
			    setContentView(R.layout.dialog_treepercancel);
			    confirmButton = (Button) findViewById(R.id.confirmButton);
			    confirmButton.setOnClickListener(this);
			    cancelButton = (Button) findViewById(R.id.cancelButton);
			    cancelButton.setOnClickListener(this);
			     

			  }
			
			  @Override
			  public void onClick(View v) {
			    switch (v.getId()) {
			    case R.id.confirmButton:
			    	DialogTreeperCancelConfirm dialogTreeperCancelConfirm = new DialogTreeperCancelConfirm(activity, treepId, driverId);
			    	dialogTreeperCancelConfirm.show();
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
	 
	 private class DialogTreeperCancelConfirm extends Dialog implements
	    android.view.View.OnClickListener {
		
			  public Activity activity;
			  public Button confirmButton;
			  public Button cancelButton;
			  public String treepId;
			  public String driverId;
			
			  public DialogTreeperCancelConfirm(Activity activity, String treepId,String driverId) {
			    super(activity);
			    // TODO Auto-generated constructor stub
			    this.activity = activity;
			    this.treepId=treepId;
			    this.driverId=driverId;
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
			        	
						showSettingsAlert();
			    	}
			    	else{
			    		SetTreepCancelFromTreeperFromXML setTreepCancelFromTreeperFromXML = new SetTreepCancelFromTreeperFromXML(activity, treepId, driverId);
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
	 
		
		
		
		private class UpdateDriverOrderedPositionFromXML extends AsyncTask<Void, Integer, HashMap<String,String>> {
			
			private Activity activity;
			private HashMap<String, String> mapTreepOrderDriverInfo = new HashMap<String, String>();
			private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			
			public UpdateDriverOrderedPositionFromXML(Activity activity){
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
			protected HashMap<String,String> doInBackground(Void... arg0) {
				HashMap<String,String> mapTimeout = new HashMap<String,String>();
				mapTimeout.put(MainActivity.KEY_TIMEOUT, "1");
				
				if (android.os.Build.VERSION.SDK_INT > 9) 
		    	{ StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }
			
		    	XMLParser parser = new XMLParser();
		    	String xml = parser.getXmlFromUrl(MainActivity.URL_USERINFO + "?userid=" + LoginDisplayActivity.userId + "&latitude=" + myLatitude +"&longitude="+ myLongitude);
				
		    	if(xml == null){
		    		mapTreepOrderDriverInfo = null;
		    	}
		    	else{
		    		if(xml == "timeout"){
						
						mapTreepOrderDriverInfo.put(MainActivity.KEY_TIMEOUT, "1");
					}
					
					else{
						
						Document doc = null;
						try{
							doc = parser.getDomElement(xml); // getting DOM element
						}
						catch(DOMException e){
							
						}
						
						if(doc == null){
							mapTreepOrderDriverInfo = null;
						}
						else{
							NodeList nl_TreepOrderDriverInfo = doc.getElementsByTagName(MainActivity.KEY_TREEPORDERDRIVERINFO);
							// looping through all xml nodes <KEY_USER>
							for (int i = 0; i < nl_TreepOrderDriverInfo.getLength(); i++) {
								// creating new HashMap
								
								Element e = (Element) nl_TreepOrderDriverInfo.item(i);
								// adding each child node to HashMap key => value
								mapTreepOrderDriverInfo.put(MainActivity.KEY_TREEPID, parser.getValue(e, MainActivity.KEY_TREEPID));
								mapTreepOrderDriverInfo.put(MainActivity.KEY_DRIVERID, parser.getValue(e, MainActivity.KEY_DRIVERID));
								mapTreepOrderDriverInfo.put(MainActivity.KEY_FIRSTNAME, parser.getValue(e, MainActivity.KEY_FIRSTNAME));
								mapTreepOrderDriverInfo.put(MainActivity.KEY_LASTNAME, parser.getValue(e, MainActivity.KEY_LASTNAME));
								mapTreepOrderDriverInfo.put(MainActivity.KEY_CARMODEL, parser.getValue(e, MainActivity.KEY_CARMODEL));
								mapTreepOrderDriverInfo.put(MainActivity.KEY_DISTANCETIME, parser.getValue(e, MainActivity.KEY_DISTANCETIME));
								mapTreepOrderDriverInfo.put(MainActivity.KEY_USERTEL, parser.getValue(e, MainActivity.KEY_USERTEL));
								mapTreepOrderDriverInfo.put(MainActivity.KEY_RATING, parser.getValue(e, MainActivity.KEY_RATING));
								mapTreepOrderDriverInfo.put(MainActivity.KEY_NBTREEP, parser.getValue(e, MainActivity.KEY_NBTREEP));
								mapTreepOrderDriverInfo.put(MainActivity.KEY_PRICE, parser.getValue(e, MainActivity.KEY_PRICE));
								mapTreepOrderDriverInfo.put(MainActivity.KEY_LATITUDE, parser.getValue(e, MainActivity.KEY_LATITUDE));
								mapTreepOrderDriverInfo.put(MainActivity.KEY_LONGITUDE, parser.getValue(e, MainActivity.KEY_LONGITUDE));
								mapTreepOrderDriverInfo.put(MainActivity.KEY_CARPICURL, parser.getValue(e, MainActivity.KEY_CARPICURL));
							}
							
						}
					}
		    	}
				
				return mapTreepOrderDriverInfo;
				
				
			}

			
			@SuppressLint("NewApi")
			protected void onPostExecute(HashMap<String,String> result) {
				
				
				
				if(result== null || result.containsKey(MainActivity.KEY_TIMEOUT) || result.size() == 0){
		    		
					
		    	}
				else{
					// create class object
					if(driverMarkerList != null){
						for(int i = 0; i< driverMarkerList.size(); i++){
							driverMarkerList.get(i).remove();
						}
					}
					
			        gps = new GPSTracker(getActivity(),ApplicationContextProvider.getContext() );
				        // Getting latitude of the current location
				        // Getting longitude of the current location
			        myLatitude = gps.getLatitude();
			        myLongitude = gps.getLongitude();
			        // Creating a LatLng object for the current location
			        latLngMyPosition = new LatLng(myLatitude, myLongitude);
			        if(ItineraireTask.polylineItinerary != null){
			        	ItineraireTask.polylineItinerary.remove();
			        }
			        
					(new GetAddressTask(getActivity(),myLatitude,myLongitude,latLngMyPosition,myMarker)).execute();
					
					driverOrderedMarker.setPosition(new LatLng(Double.parseDouble(result.get(MainActivity.KEY_LATITUDE)),Double.parseDouble(result.get(MainActivity.KEY_LONGITUDE))));
					driverOrderedMarker.setSnippet("Se trouve à " + result.get(MainActivity.KEY_DISTANCETIME) + " min"); 
					
					new ItineraireTask(ApplicationContextProvider.getContext(), mMap, Double.parseDouble(result.get(MainActivity.KEY_LATITUDE)), Double.parseDouble(result.get(MainActivity.KEY_LONGITUDE)), myLatitude, myLongitude, getActivity()).execute();
					
					String username = result.get(MainActivity.KEY_FIRSTNAME) + " " + result.get(MainActivity.KEY_LASTNAME).charAt(0) + ".";
				 	
					GetDistanceTime distanceTime = new GetDistanceTime(result.get(MainActivity.KEY_DISTANCETIME),username,tvspOrderedDistanceTime,infobanner);
				 	distanceTime.execute();	
					
				}
			}
		}
		
}