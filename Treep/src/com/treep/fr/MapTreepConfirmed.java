package com.treep.fr;



import java.net.URL;
import java.util.HashMap;

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
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.BadTokenException;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
public class MapTreepConfirmed extends Fragment implements LocationListener {
     
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
 	
 	private LinearLayout slidingPanelLayout;
 	
 	
 	private RelativeLayout spHeadLayout;
 	private ImageView spHeadListImage;
 	private TextView spHeadUsername;
 	private TextView spHeadCarModel;
 	private ImageView spHeadRating;
 	
 	private ImageView ivspOrderedDriverProfilePic;
 	
 	private SlidingUpPanelLayout slidingUpPanellayout;
 	private Marker userConfirmedMarker;
 	
 	private TextView tvspOrderedDistanceTime;
 	
 	private TextView tvspOrderedAddressDep;
 	private TextView tvspOrderedAddressDest;
 	

 	private TextView spHeadCurrentPosition;
 	
 	private Button spCallButton;
 	private Button spCancelButton;
 	private Button spTreepFinishedButton;
 	
 	private Button spTreeperOnBoardButton;
 	
 	
 	private double latdep;
 	private double lngdep;
 	private double latdest;
 	private double lngdest;
 	

 	private HashMap<String, String> mapDriverUserInfo = new HashMap<String, String>();
 	private HashMap<String, String> mapDriverTreepNow = new HashMap<String, String>();
 	
 	
 	
 	private UIUpdater setUserPositionUpdater;
 	private UIUpdater treeperAcceptedNotOnBoardPositionUpdater;
 	
 	
 	
 	
 	private LatLng latLngMyPosition;
 	private LatLng latLngDep;
 	private LatLng latLngDest;
 	
 
 	
 	private DialogPleaseWait dialogPleaseWait;
 	
 	
 	static int indexDriverList=0;
	static int indexUserList=0;
 	//DRIVER MODE
 	
 	private Button gpsNavButton;
 	
 	
	  
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
	        if(treeperAcceptedNotOnBoardPositionUpdater != null){
	        	treeperAcceptedNotOnBoardPositionUpdater.stopUpdates();
	     	}
	    }

	    @Override
	    public void onStop() {
	        super.onStop();
	        if(setUserPositionUpdater != null){
	     		setUserPositionUpdater.stopUpdates();
	     	}
	        if(treeperAcceptedNotOnBoardPositionUpdater != null){
	        	treeperAcceptedNotOnBoardPositionUpdater.stopUpdates();
	     	}
	    }

	    
	    private class DialogTreepFinishedFromDriver extends Dialog implements
	    android.view.View.OnClickListener {
		
			  public Activity activity;
			  public Dialog d;
			  public String treepId;
			  public Button confirmButton;
			  public Button cancelButton;
			  private Typeface fb;
			  private String treeperId;
			
			  public DialogTreepFinishedFromDriver(Activity activity, String treepId, String treeperId) {
			    super(activity);
			    // TODO Auto-generated constructor stub
			    this.activity = activity;
			    this.treepId=treepId;
			    this.treeperId=treeperId;
			  }
			
			  @Override
			  protected void onCreate(Bundle savedInstanceState) {
			    super.onCreate(savedInstanceState);
			    requestWindowFeature(Window.FEATURE_NO_TITLE);
			    fb = Typeface.createFromAsset(activity.getAssets(), "fb.ttf");
			    setContentView(R.layout.dialog_treepfinishedfromdriver);
			    confirmButton = (Button) findViewById(R.id.confirmButton);
			    confirmButton.setTypeface(fb);
			    confirmButton.setOnClickListener(this);
			    cancelButton = (Button) findViewById(R.id.cancelButton);
			    cancelButton.setTypeface(fb);
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
			    		SetTreepFinishedFromDriverFromXML setTreepFinishedFromDriverFromXML = new SetTreepFinishedFromDriverFromXML(getActivity(), treepId, treeperId);
						setTreepFinishedFromDriverFromXML.execute();
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
			View v = inflater.inflate(R.layout.fragment_maptreepconfirmed, container, false);
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
				
				
				spTreeperOnBoardButton = (Button)v.findViewById(R.id.spTreeperOnBoardButton);
				spTreeperOnBoardButton.setVisibility(View.GONE);
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
				
				//DRIVER MODE AFFECTATION
				
				if(setUserPositionUpdater != null){
			 		setUserPositionUpdater.stopUpdates();
			 	}
			    if(treeperAcceptedNotOnBoardPositionUpdater != null){
			    	treeperAcceptedNotOnBoardPositionUpdater.stopUpdates();
			 	}
				
				if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
		        	
					this.showSettingsAlert();
		            
				}
				else{
					Bundle bundle = this.getArguments();
					try{
					mapDriverUserInfo = (HashMap<String, String>) bundle.getSerializable("mapDriverUserInfo");
					mapDriverTreepNow = (HashMap<String, String>) bundle.getSerializable("mapDriverTreepNow");
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
					
					gpsNavButton = (Button) v.findViewById(R.id.gpsNavButton);
					gpsNavButton.setTypeface(fb);
					gpsNavButton.setVisibility(View.GONE);
					
					myLocationButton = (Button) v.findViewById(R.id.mypositionbutton);
					myLocationButton.setOnClickListener(clickListenerMyLocationButton);
					
					tvspOrderedDistanceTime  = (TextView)v.findViewById(R.id.tvspOrderedDistanceTime);
					spHeadListImage = (ImageView) v.findViewById(R.id.spHeadListImage);
				 	spHeadUsername = (TextView)v.findViewById(R.id.spHeadUsername);
				 	spHeadCarModel = (TextView)v.findViewById(R.id.spHeadCarModel);
				 	spHeadRating = (ImageView)v.findViewById(R.id.spHeadRating);
				 	spHeadLayout = (RelativeLayout) v.findViewById(R.id.spHeadLayout);
				 	ivspOrderedDriverProfilePic = (ImageView) v.findViewById(R.id.spOrderedDriverProfilePic);
				 	tvspOrderedAddressDep  = (TextView)v.findViewById(R.id.tvspOrderedAddressDep);
				 	tvspOrderedAddressDest  = (TextView)v.findViewById(R.id.tvspOrderedAddressDest);
				 	
				 	spCallButton = (Button) v.findViewById(R.id.spCallButton);
				 	spCancelButton = (Button) v.findViewById(R.id.spCancelButton);
				 	spTreepFinishedButton = (Button) v.findViewById(R.id.spTreepFinishedButton);
				 	spTreepFinishedButton.setVisibility(View.GONE);
			 
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
			        lngdep= MainActivity.myLongitude;
			        MarkerOptions a = new MarkerOptions().position(latLngMyPosition);
				    myMarker = mMap.addMarker(a.title("Moi").icon(BitmapDescriptorFactory.fromResource(R.drawable.pinmyposition)));
				   
					        
			        
					(new GetAddressTask(getActivity(),MainActivity.myLatitude,MainActivity.myLongitude,latLngMyPosition, myMarker,null)).execute();
						
			        //Move the camera instantly to my position with a zoom of 15.
			  		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngMyPosition, 45));
			  		// Zoom in, animating the camera.
			  		mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null); 
			  		
			  		
					slidingPanelLayout.setVisibility(View.VISIBLE);
					
					infobanner.setVisibility(View.VISIBLE);
		  			String username = mapDriverTreepNow.get(MainActivity.KEY_FIRSTNAME) + " " + mapDriverTreepNow.get(MainActivity.KEY_LASTNAME).charAt(0) + ".";
				 								
		  			spCallButton.setTypeface(fb);
				 	spCancelButton.setTypeface(fb);
					
					ImageLoader imageLoaderThumb = new ImageLoader(ApplicationContextProvider.getContext());
					imageLoaderThumb.DisplayImage(MainActivity.urlThumbPic(mapDriverTreepNow.get(MainActivity.KEY_USERID)),spHeadListImage);
					
				 	spHeadUsername.setText(username);
				 	
				 	switch (Integer.parseInt(mapDriverTreepNow.get(MainActivity.KEY_RATING))) {
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

				 	spTreeperOnBoardButton.setVisibility(View.GONE);
				 	
				 	if(!MainActivity.isDriverAccepted){
				 		gpsNavButton.setVisibility(View.VISIBLE);
				 		if(!MainActivity.isTreeperOnBoard){
				 			gpsNavButton.setOnClickListener( new OnClickListener(){

								@Override
								public void onClick(View v) {
									Intent i = new Intent(Intent.ACTION_VIEW);
									i.setData(Uri.parse("http://maps.google.com/maps?saddr=" + MainActivity.myLatitude + "," + MainActivity.myLongitude + "&daddr=" + mapDriverTreepNow.get(MainActivity.KEY_LATDEPNOW) + "," + mapDriverTreepNow.get(MainActivity.KEY_LNGDEPNOW)));
									startActivity(i);
									
								}
								
							});
				 			spTreeperOnBoardButton.setVisibility(View.VISIBLE);
							
							spTreeperOnBoardButton.setOnClickListener( new OnClickListener(){

								@Override
								public void onClick(View v) {
									SetTreeperOnBoardFromDriverFromXML setTreeperOnBoardFromDriverFromXML = new SetTreeperOnBoardFromDriverFromXML(getActivity(), mapDriverUserInfo.get(MainActivity.KEY_TREEPID), mapDriverTreepNow.get(MainActivity.KEY_USERID));
									setTreeperOnBoardFromDriverFromXML.execute();
								}
								
							});
				 		}
				 		else{
				 			spCancelButton.setVisibility(View.GONE);
				 			spCallButton.setVisibility(View.GONE);
				 			spTreepFinishedButton.setVisibility(View.VISIBLE);
				 			
				 			spTreepFinishedButton.setOnClickListener( new OnClickListener(){

								@Override
								public void onClick(View v) {
									DialogTreepFinishedFromDriver dialogTreepFinishedFromDriver = new DialogTreepFinishedFromDriver(getActivity(),mapDriverUserInfo.get(MainActivity.KEY_TREEPID), mapDriverTreepNow.get(MainActivity.KEY_USERID));
									try{
										dialogTreepFinishedFromDriver.show();
									}
									catch(BadTokenException e){
										
									}
								}
								
							});
				 			
				 			gpsNavButton.setOnClickListener( new OnClickListener(){

								@Override
								public void onClick(View v) {
									Intent i = new Intent(Intent.ACTION_VIEW);
									i.setData(Uri.parse("http://maps.google.com/maps?saddr=" + MainActivity.myLatitude + "," + MainActivity.myLongitude + "&daddr=" + mapDriverTreepNow.get(MainActivity.KEY_LATDESTNOW) + "," + mapDriverTreepNow.get(MainActivity.KEY_LNGDESTNOW)));
									startActivity(i);
									
								}
								
							});
				 		}
				 	}
				 	
				 	slidingUpPanellayout.setDragView(spHeadLayout);
				 	
				 	ImageLoader imageLoaderProfilePic = new ImageLoader(ApplicationContextProvider.getContext());
				 	imageLoaderProfilePic.DisplayImage(MainActivity.urlProfilePic(mapDriverTreepNow.get(MainActivity.KEY_USERID)),ivspOrderedDriverProfilePic);
				 	
				 	spHeadCurrentPosition = (TextView) v.findViewById(R.id.spHeadCurrentPosition);
				 	spHeadCurrentPosition.setText(Html.fromHtml("<b>" + mapDriverTreepNow.get(MainActivity.KEY_CURRENTPOSITION) + "</b> chez <b> " + mapDriverTreepNow.get(MainActivity.KEY_CURRENTCOMPANY) + "</b>"));
				 	
				 	if(!MainActivity.isDriverAccepted){
				 		tvspOrderedAddressDep.setText(mapDriverTreepNow.get(MainActivity.KEY_ADDRESSDEPNOW));
						tvspOrderedAddressDest.setText(mapDriverTreepNow.get(MainActivity.KEY_ADDRESSDESTNOW));
				 	}else{
				 		
				 	}
					
					final Animation buttonScaleOnReleaseAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.button_scale_onrelease);
					buttonScaleOnReleaseAnim.setFillAfter(true);
					
					final Animation buttonScaleOnTouchAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.button_scale_ontouch);
					buttonScaleOnTouchAnim.setFillAfter(true);
					
					OnTouchListener onTouchListenerButtonCall = new View.OnTouchListener() {
					    @Override
					    public boolean onTouch(View v, MotionEvent event) {
					    	if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
					    		spCallButton.startAnimation(buttonScaleOnTouchAnim);
							     
						    }
						    else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
						    	spCallButton.startAnimation(buttonScaleOnReleaseAnim);
						    
						    }
							return false;
					    }

					  };
					  
					spCallButton.setOnTouchListener(onTouchListenerButtonCall);
					
					spCallButton.setOnClickListener( new OnClickListener(){

						@Override
						public void onClick(View v) {
							if(!MainActivity.isDriverAccepted){
								Intent callIntent = new Intent(Intent.ACTION_DIAL);
								callIntent.setData(Uri.parse("tel:"+ mapDriverTreepNow.get(MainActivity.KEY_USERTEL)));
								startActivity(callIntent);
							}
							else{
								
							}
						}
						
					});
					spCancelButton.setOnClickListener( new OnClickListener(){

						@Override
						public void onClick(View v) {
							DialogDriverCancel dialogDriverCancel = new DialogDriverCancel(getActivity(),mapDriverTreepNow.get(MainActivity.KEY_TREEPID), mapDriverTreepNow.get(MainActivity.KEY_USERID));
							
							dialogDriverCancel.show();
							
						}
						
					});
				 	  
					if(!MainActivity.isDriverAccepted){
				 		if(!MainActivity.isTreeperOnBoard){
				 			userConfirmedMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(mapDriverTreepNow.get(MainActivity.KEY_LATDEPNOW)),Double.parseDouble(mapDriverTreepNow.get(MainActivity.KEY_LNGDEPNOW))))
									.title(username)
							        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pinuserposition)));
							
							infobannertxt = new StringBuilder();
							infobannertxt.append("Calcul en cours...");
							infobanner.setText(infobannertxt.toString());
							
							DisplayUserOrderedPin displayUserOrderedPin = new DisplayUserOrderedPin(userConfirmedMarker,mapDriverTreepNow.get(MainActivity.KEY_LATDEPNOW),mapDriverTreepNow.get(MainActivity.KEY_LNGDEPNOW),MainActivity.myLatitude, MainActivity.myLongitude,username,tvspOrderedDistanceTime,infobanner);
							displayUserOrderedPin.execute();
							
							new ItineraireTask(ApplicationContextProvider.getContext(), mMap, Double.parseDouble(mapDriverTreepNow.get(MainActivity.KEY_LATDEPNOW)), Double.parseDouble(mapDriverTreepNow.get(MainActivity.KEY_LNGDEPNOW)), MainActivity.myLatitude, MainActivity.myLongitude, getActivity()).execute();
							
				 		}
				 		else{
				 			userConfirmedMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(mapDriverTreepNow.get(MainActivity.KEY_LATDESTNOW)),Double.parseDouble(mapDriverTreepNow.get(MainActivity.KEY_LNGDESTNOW))))
									.title("Destination")
							        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pinfinish)));
							
							infobannertxt = new StringBuilder();
							infobannertxt.append("Calcul en cours...");
							infobanner.setText(infobannertxt.toString());
							
							DisplayUserOrderedPin displayUserOrderedPin = new DisplayUserOrderedPin(userConfirmedMarker,mapDriverTreepNow.get(MainActivity.KEY_LATDESTNOW),mapDriverTreepNow.get(MainActivity.KEY_LNGDESTNOW),MainActivity.myLatitude, MainActivity.myLongitude,username,tvspOrderedDistanceTime,infobanner);
							displayUserOrderedPin.execute();
							
							new ItineraireTask(ApplicationContextProvider.getContext(), mMap, Double.parseDouble(mapDriverTreepNow.get(MainActivity.KEY_LATDESTNOW)), Double.parseDouble(mapDriverTreepNow.get(MainActivity.KEY_LNGDESTNOW)), MainActivity.myLatitude, MainActivity.myLongitude, getActivity()).execute();
							
				 		}
					}
					else{
						
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
			  		
			  		treeperAcceptedNotOnBoardPositionUpdater = new UIUpdater(new Runnable() {
			  	         @Override 
			  	         public void run() {
			  	        	UpdateUserConfirmedPositionFromXML updateUserConfirmedPositionFromXML = new UpdateUserConfirmedPositionFromXML(getActivity());
			  	        	updateUserConfirmedPositionFromXML.execute();
			  	         }
			  	    });
	
				  	// Start updates
			  		treeperAcceptedNotOnBoardPositionUpdater.startUpdates();
						  		
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
		if(setUserPositionUpdater != null){
	 		setUserPositionUpdater.stopUpdates();
	 	}
	    if(treeperAcceptedNotOnBoardPositionUpdater != null){
	    	treeperAcceptedNotOnBoardPositionUpdater.stopUpdates();
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
	 
	
	 
	 
	 
	 private class DialogDriverCancel extends Dialog implements
	    android.view.View.OnClickListener {
		
			  public Activity activity;
			  public Button confirmButton;
			  public Button cancelButton;
			  public String treepId;
			  public String treeperId;
			
			  public DialogDriverCancel(Activity activity, String treepId, String treeperId) {
			    super(activity);
			    this.activity = activity;
			    this.treepId=treepId;
			    this.treeperId=treeperId;
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
			    	DialogDriverCancelConfirm dialogDriverCancelConfirm = new DialogDriverCancelConfirm(activity, treepId, treeperId);
			    	try{
			    		dialogDriverCancelConfirm.show();
			    	}
			    	catch(BadTokenException e){
			    		
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
	 
	 private class DialogDriverCancelConfirm extends Dialog implements
	    android.view.View.OnClickListener {
		
			  public Activity activity;
			  public Dialog d;
			  public Button confirmButton;
			  public Button cancelButton;
			  public String treepId;
			  public String treeperId;
			
			  public DialogDriverCancelConfirm(Activity activity, String treepId, String treeperId) {
			    super(activity);
			    // TODO Auto-generated constructor stub
			    this.activity = activity;
			    this.treepId=treepId;
			    this.treeperId=treeperId;
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
			    		SetTreepCancelFromDriverFromXML setTreepCancelFromDriverFromXML = new SetTreepCancelFromDriverFromXML(activity, treepId, treeperId);
			    		setTreepCancelFromDriverFromXML.execute();
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
	 
		private class UpdateUserConfirmedPositionFromXML extends AsyncTask<Void, Integer, HashMap<String,String>> {
			
			private Activity activity;
			private HashMap<String, String> mapDriverTreepNow = new HashMap<String, String>();
			
			
			public UpdateUserConfirmedPositionFromXML(Activity activity){
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
		    	String xml = parser.getXmlFromUrl(MainActivity.URL_USERINFO + "?userid=" + LoginDisplayActivity.userId +"&lat=" + MainActivity.myLatitude + "&lng=" + MainActivity.myLongitude);
				
				
				if(xml ==null){
					mapDriverTreepNow = null;
				}
				else{
					if(xml == "timeout"){
						
						mapDriverTreepNow.put(MainActivity.KEY_TIMEOUT, "1");
					}
					
					else{
						
						Document doc = null;
						try{
							doc = parser.getDomElement(xml); // getting DOM element
						}
						catch(DOMException e){
							
						}
						
						if(doc == null){
							mapDriverTreepNow = null;
						}
						else{
							NodeList nl_DriverTreepNow = doc.getElementsByTagName(MainActivity.KEY_DRIVERTREEPNOW);
							// looping through all xml nodes <KEY_USER>
							for (int i = 0; i < nl_DriverTreepNow.getLength(); i++) {
								// creating new HashMap
								
								Element e = (Element) nl_DriverTreepNow.item(i);
								// adding each child node to HashMap key => value
								mapDriverTreepNow.put(MainActivity.KEY_USERID, parser.getValue(e, MainActivity.KEY_USERID));
								mapDriverTreepNow.put(MainActivity.KEY_FIRSTNAME, parser.getValue(e, MainActivity.KEY_FIRSTNAME));
								mapDriverTreepNow.put(MainActivity.KEY_LASTNAME, parser.getValue(e, MainActivity.KEY_LASTNAME));
								mapDriverTreepNow.put(MainActivity.KEY_DISTANCETIME, parser.getValue(e, MainActivity.KEY_DISTANCETIME));
								mapDriverTreepNow.put(MainActivity.KEY_USERTEL, parser.getValue(e, MainActivity.KEY_USERTEL));
								mapDriverTreepNow.put(MainActivity.KEY_RATING, parser.getValue(e, MainActivity.KEY_RATING));
								mapDriverTreepNow.put(MainActivity.KEY_CURRENTPOSITION, parser.getValue(e, MainActivity.KEY_CURRENTPOSITION));
								mapDriverTreepNow.put(MainActivity.KEY_CURRENTCOMPANY, parser.getValue(e, MainActivity.KEY_CURRENTCOMPANY));
								mapDriverTreepNow.put(MainActivity.KEY_NBTREEP, parser.getValue(e, MainActivity.KEY_NBTREEP));
								mapDriverTreepNow.put(MainActivity.KEY_PRICE, parser.getValue(e, MainActivity.KEY_PRICE));
								mapDriverTreepNow.put(MainActivity.KEY_LATITUDE, parser.getValue(e, MainActivity.KEY_LATITUDE));
								mapDriverTreepNow.put(MainActivity.KEY_LONGITUDE, parser.getValue(e, MainActivity.KEY_LONGITUDE));
								mapDriverTreepNow.put(MainActivity.KEY_LATDEPNOW, parser.getValue(e, MainActivity.KEY_LATDEPNOW));
								mapDriverTreepNow.put(MainActivity.KEY_LNGDEPNOW, parser.getValue(e, MainActivity.KEY_LNGDEPNOW));
								mapDriverTreepNow.put(MainActivity.KEY_LATDESTNOW, parser.getValue(e, MainActivity.KEY_LATDESTNOW));
								mapDriverTreepNow.put(MainActivity.KEY_LNGDESTNOW, parser.getValue(e, MainActivity.KEY_LNGDESTNOW));
								mapDriverTreepNow.put(MainActivity.KEY_ADDRESSDEPNOW, parser.getValue(e, MainActivity.KEY_ADDRESSDEPNOW));
								mapDriverTreepNow.put(MainActivity.KEY_ADDRESSDESTNOW, parser.getValue(e, MainActivity.KEY_ADDRESSDESTNOW));
								
							}
							
						}
					}
				}
				
				return mapDriverTreepNow;
				
				
			}

			
			@SuppressLint("NewApi")
			protected void onPostExecute(HashMap<String,String> result) {
				
				
				
				if(result== null || result.containsKey(MainActivity.KEY_TIMEOUT)){
		    		
					
		    	}
				else{
					// create class object
			        gps = new GPSTracker(getActivity(),ApplicationContextProvider.getContext() );
				        // Getting latitude of the current location
				        // Getting longitude of the current location
			        MainActivity.myLatitude = gps.getLatitude();
			        MainActivity.myLongitude = gps.getLongitude();
			        // Creating a LatLng object for the current location
			        latLngMyPosition = new LatLng(MainActivity.myLatitude, MainActivity.myLongitude);
			        String username = result.get(MainActivity.KEY_FIRSTNAME) + " " + result.get(MainActivity.KEY_LASTNAME).charAt(0) + ".";
				 	
			        
			        if(ItineraireTask.polylineItinerary != null){
			        	ItineraireTask.polylineItinerary.remove();
			        }
			        
			        if(!MainActivity.isDriverAccepted){
				 		if(!MainActivity.isTreeperOnBoard){
				 			userConfirmedMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(mapDriverTreepNow.get(MainActivity.KEY_LATDEPNOW)),Double.parseDouble(mapDriverTreepNow.get(MainActivity.KEY_LNGDEPNOW))))
									.title(username)
							        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pinuserposition)));
							
							infobannertxt = new StringBuilder();
							infobannertxt.append("Calcul en cours...");
							infobanner.setText(infobannertxt.toString());
							
							DisplayUserOrderedPin displayUserOrderedPin = new DisplayUserOrderedPin(userConfirmedMarker,mapDriverTreepNow.get(MainActivity.KEY_LATDEPNOW),mapDriverTreepNow.get(MainActivity.KEY_LNGDEPNOW),MainActivity.myLatitude, MainActivity.myLongitude,username,tvspOrderedDistanceTime,infobanner);
							displayUserOrderedPin.execute();
							
							new ItineraireTask(ApplicationContextProvider.getContext(), mMap, Double.parseDouble(mapDriverTreepNow.get(MainActivity.KEY_LATDEPNOW)), Double.parseDouble(mapDriverTreepNow.get(MainActivity.KEY_LNGDEPNOW)), MainActivity.myLatitude, MainActivity.myLongitude, getActivity()).execute();
							
				 		}
				 		else{
				 			userConfirmedMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(mapDriverTreepNow.get(MainActivity.KEY_LATDESTNOW)),Double.parseDouble(mapDriverTreepNow.get(MainActivity.KEY_LNGDESTNOW))))
									.title("Destination")
							        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pinfinish)));
							
							infobannertxt = new StringBuilder();
							infobannertxt.append("Calcul en cours...");
							infobanner.setText(infobannertxt.toString());
							
							DisplayUserOrderedPin displayUserOrderedPin = new DisplayUserOrderedPin(userConfirmedMarker,mapDriverTreepNow.get(MainActivity.KEY_LATDESTNOW),mapDriverTreepNow.get(MainActivity.KEY_LNGDESTNOW),MainActivity.myLatitude, MainActivity.myLongitude,username,tvspOrderedDistanceTime,infobanner);
							displayUserOrderedPin.execute();
							
							new ItineraireTask(ApplicationContextProvider.getContext(), mMap, Double.parseDouble(mapDriverTreepNow.get(MainActivity.KEY_LATDESTNOW)), Double.parseDouble(mapDriverTreepNow.get(MainActivity.KEY_LNGDESTNOW)), MainActivity.myLatitude, MainActivity.myLongitude, getActivity()).execute();
							
				 		}
					}
					else{
						
					}
				}
			}
		}
		
		
}