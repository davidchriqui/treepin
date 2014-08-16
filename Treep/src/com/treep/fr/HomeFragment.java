package com.treep.fr;



import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.DOMException;
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
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class HomeFragment extends Fragment implements LocationListener {
     
	
	private Typeface fb;
	
	private Location location;
	
	private MapView mapView;
	private GoogleMap mMap;
	private TextView infobanner ;
	private TextView infobannermodedriver;
	private TextView tvPrice ;
	private StringBuilder infobannertxt;
	private GPSTracker gps;
	
	
	private TextView treepinPrice;
	private TextView treepintime;
	private TextView ptPrice;
	private TextView ptTime;
	private TextView taxiPrice;
	private TextView taxiTime;
	
	
	private ImageView ivMyPosition;
    
 // All static variables
    URL url;
    
    static Marker myMarkerDep;
    static Marker myMarkerDest;
    private LinearLayout addressDepLayout;
 	private LinearLayout addressDestLayout;
 	private AutoCompleteTextView acAddressdep;
 	static String addressDep;
 	static String addressDest;
 	private AutoCompleteTextView acAddressdest;
 	static boolean departIsOk=false;
 	private boolean destIsOk=false;
 	
 	private boolean nightHour = false;
 	
 	public static ArrayList<HashMap<String,String>> alMapDriverPosition;
	HashMap<String, String> mapUserInfo = new HashMap<String, String> ();
 	
 	static String price;
 	
 	static double latdep;
 	static double lngdep;
 	static double latdest;
 	static double lngdest;
 	
 	
 	private boolean isSetDep = false;
 	//private CountDownTimer updateDriverPositionListTimer;

 	private UIUpdater driverPositionListUpdater;
 	private UIUpdater setUserPositionUpdater;
 	
 	static LatLng latLngMyPosition;
 	static LatLng latLngDep;
 	static LatLng latLngDest;
 	
 
 	private static int acAddressdepLength = 0;
 	private static int acAddressdestLength = 0;
 			
 			
 	static int treepResponseCount = 0;
 	
  	static Boolean hasToUpdate = false;
 	
 	private Button pinkModeButton;
 	private Boolean pinkMode = false;
 	
 	
 	
 	static int indexDriverList=0;
	static int indexUserList=0;
 	//DRIVER MODE
 	
 	
 	 private OnClickListener clickListenerPinkModeButton = new View.OnClickListener() {
 	    @Override
 	    public void onClick(View v) {
 	    	if(!pinkMode){
 	    		pinkMode = true;
 	    		pinkModeButton.setBackgroundResource(R.drawable.pinkmodeon);
 	    		DialogPinkModeOn dialogPinkModeOn = new DialogPinkModeOn(getActivity());
 	    		dialogPinkModeOn.show();
 	    	}
 	    	else{
 	    		pinkMode = false;
 	    		pinkModeButton.setBackgroundResource(R.drawable.pinkmodeoff);
 	    		MainActivity.displayToast("Mode Pink désactivé");
 	    	}
 	    }
 	    	
 	  };

 	private ArrayList<HashMap<String, String>> alMapDistanceTime = new ArrayList<HashMap<String, String>>();
 	private ArrayList<Integer>alDistanceTime = new ArrayList<Integer>();
 	private ArrayList<Marker> driverMarkerList = new ArrayList<Marker>();
 	
 	
 	public static void hideSoftKeyboard(Activity activity) {
 	    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
 	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
 	}
 	
	
 	private Button buttonRequestDep;
 	private Button buttonRequestDest;
 	private Button buttonBackToDep;
 	
 	private boolean compareLayoutIsOpened = false;

 	private Animation buttonCompareScaleAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.button_compare_scale);
 	private Animation buttonCompareFadeOutAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.fadeout);
 	
 	private Animation tableLayoutInAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.slide_in_compare_layout);
 	private Animation tableLayoutOutAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.slide_out_compare_layout);
 	
 	private Animation layoutSlideOutAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.slide_out_layout_toptobottom);
 	private Animation layoutSlideInAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.slide_in_layout_bottomtotop);
 	
 	private TableLayout tableLayout;
 	
 	private Button compareButton;
 	
 	Animation buttonScaleOnReleaseCompareAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.button_scale_onrelease);
	
	Animation buttonScaleOnTouchCompareAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.button_scale_ontouch);
	
	
	OnTouchListener onTouchListenerCompareButton = new View.OnTouchListener() {
	    @Override
	    public boolean onTouch(View v, MotionEvent event) {
	    	if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
	    		compareButton.startAnimation(buttonScaleOnTouchCompareAnim);
			     
		    }
		    else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
		    	compareButton.startAnimation(buttonScaleOnReleaseCompareAnim);
		    
		    }
			return false;
	    }

	  };
	  
 	private OnClickListener clickListenerCompareButton = new View.OnClickListener() {
    	
	    @Override
	    public void onClick(View v) {
	    	
	    	
	    	
	    	if(!compareLayoutIsOpened){
	    		// Creating a LatLng object for the current location
		    	tableLayoutInAnim.setAnimationListener(new Animation.AnimationListener(){
				    @Override
				    public void onAnimationStart(Animation arg0) {
				    	tableLayout.setVisibility(View.VISIBLE);
				    	compareButton.bringToFront();
				    }           
				    @Override
				    public void onAnimationRepeat(Animation arg0) {
				    }           
				    @Override
				    public void onAnimationEnd(Animation arg0) {
				    	
				    }
				});
		    	tableLayout.startAnimation(tableLayoutInAnim);
		    	compareLayoutIsOpened = true;
		    	
		    	CompareDurationForPublicTransportWithGoogle compareDurationForPublicTransport = new CompareDurationForPublicTransportWithGoogle();
		    	compareDurationForPublicTransport.execute();
	    	}
	    	else{
	    		tableLayoutOutAnim.setAnimationListener(new Animation.AnimationListener(){
				    @Override
				    public void onAnimationStart(Animation arg0) {
				    	
				    }           
				    @Override
				    public void onAnimationRepeat(Animation arg0) {
				    }           
				    @Override
				    public void onAnimationEnd(Animation arg0) {
				    	tableLayout.setVisibility(View.GONE);
				    }
				});
		    	tableLayout.startAnimation(tableLayoutOutAnim);
		    	compareLayoutIsOpened = false;
	    	}		
	    }
	  };
 	
 	//OnClickListener for the "Request a Treep" button
    private OnClickListener clickListenerRequestTreepDepButton = new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {
	    	
		    if(Double.toString(latdep) != "0" && Double.toString(lngdep) != "0" && Double.toString(latdep) != "0.0" && Double.toString(lngdep) != "0.0"){
		    	if(addressDep != null){
		    		acAddressdep.setTextColor(0xff444444);
	    			departIsOk=true;
		    	}
		    	else{
		    		departIsOk = false;
		    		acAddressdep.setText("");
		    		onCameraChangeDep();
		    		MainActivity.displayToast(R.string.warningaddressdep);
		    	}
		    }
		    else{
		    	departIsOk = false;
	    		acAddressdep.setText("");
	    		onCameraChangeDep();
	    		MainActivity.displayToast(R.string.warningaddressdep);
		    }
	    	if(departIsOk){
				MainActivity.initposition = 0;
	    		
				isSetDep = true;
				
				if(myMarkerDep != null){
					myMarkerDep.remove();
				}
				
				MarkerOptions a = new MarkerOptions().position(latLngDep);
				myMarkerDep = mMap.addMarker(a.title("Adresse de départ").snippet(addressDep).icon(BitmapDescriptorFactory.fromResource(R.drawable.pinmyposition)));
				
				ivMyPosition.setImageDrawable(getResources().getDrawable(R.drawable.pindestination));
				mMap.animateCamera(CameraUpdateFactory.zoomOut());
				
	    		//hideSoftKeyboard(getActivity());
				layoutSlideInAnim.setAnimationListener(new Animation.AnimationListener(){
				    @Override
				    public void onAnimationStart(Animation arg0) {
				    }           
				    @Override
				    public void onAnimationRepeat(Animation arg0) {
				    }           
				    @Override
				    public void onAnimationEnd(Animation arg0) {
				    	addressDepLayout.clearAnimation();
				    	addressDepLayout.setVisibility(View.GONE);
				    }
				});
				layoutSlideOutAnim.setAnimationListener(new Animation.AnimationListener(){
				    @Override
				    public void onAnimationStart(Animation arg0) {
				    }           
				    @Override
				    public void onAnimationRepeat(Animation arg0) {
				    }           
				    @Override
				    public void onAnimationEnd(Animation arg0) {
				    	addressDestLayout.clearAnimation();
				    	
				    }
				});
				addressDestLayout.setVisibility(View.VISIBLE);
				addressDepLayout.startAnimation(layoutSlideOutAnim);
				 
				addressDestLayout.startAnimation(layoutSlideInAnim);
				
				
				buttonCompareScaleAnim.setAnimationListener(new Animation.AnimationListener(){
				    @Override
				    public void onAnimationStart(Animation arg0) {
				    	compareButton.setVisibility(View.VISIBLE);
				    }           
				    @Override
				    public void onAnimationRepeat(Animation arg0) {
				    }           
				    @Override
				    public void onAnimationEnd(Animation arg0) {
				    	addressDestLayout.clearAnimation();
				    	
				    }
				});
				compareButton.startAnimation(buttonCompareScaleAnim);
	    	}
	    }
	  };
	  
	  
	  
	  
	//OnClickListener for the "Request a Treep" button
    private OnClickListener clickListenerRequestBackToDepButton = new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {
	    	
	    	isSetDep = false;
	    	
	    	if(myMarkerDep != null){
				myMarkerDep.remove();
			}
			
			ivMyPosition.setImageDrawable(getResources().getDrawable(R.drawable.pinmyposition));
	    	
	    	layoutSlideInAnim.setAnimationListener(new Animation.AnimationListener(){
			    @Override
			    public void onAnimationStart(Animation arg0) {

				 	addressDepLayout.setVisibility(View.VISIBLE);
			    }           
			    @Override
			    public void onAnimationRepeat(Animation arg0) {
			    }           
			    @Override
			    public void onAnimationEnd(Animation arg0) {
			    	addressDepLayout.clearAnimation();
			    }
			});
	    	addressDestLayout.startAnimation(layoutSlideOutAnim);
		 	addressDepLayout.startAnimation(layoutSlideInAnim);
		 	addressDestLayout.setVisibility(View.GONE);
		 	
		 	buttonCompareFadeOutAnim.setAnimationListener(new Animation.AnimationListener(){
			    @Override
			    public void onAnimationStart(Animation arg0) {

				 	addressDepLayout.setVisibility(View.VISIBLE);
			    }           
			    @Override
			    public void onAnimationRepeat(Animation arg0) {
			    }           
			    @Override
			    public void onAnimationEnd(Animation arg0) {
			    	compareButton.setVisibility(View.GONE);
			    }
			});
		 	compareButton.startAnimation(buttonCompareFadeOutAnim);
		 	addressDepLayout.setVisibility(View.VISIBLE);
		 	
	    }
	  };
 	
    //OnClickListener for the "Request a Treep" button
    private OnClickListener clickListenerRequestTreepDestButton = new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {
	    	
	    	if(latdest != 0 && lngdest != 0){
		    	if(addressDest != null){
		    		acAddressdest.setTextColor(0xff444444);
		    		destIsOk=true;
		    	}
		    	else{
		    		destIsOk = false;
		    		acAddressdest.setText("");
		    		onCameraChangeDest();
		    		MainActivity.displayToast(R.string.warningaddressdest);
		    	}
		    }
		    else{
		    	destIsOk = false;
	    		acAddressdest.setText("");
	    		onCameraChangeDest();
	    		MainActivity.displayToast(R.string.warningaddressdest);
		    }
	    	
	    	if(departIsOk && destIsOk){
				MainActivity.initposition = 0;
	    		
	    		hideSoftKeyboard(getActivity());
	    		if(alMapDriverPosition == null){
	    			MainActivity.displayToast("Mise à jour de la position des drivers.\nVeuillez appuyer à nouveau pour réserver votre Treep");
	    		}
	    		else{
	    			//SetTreepBookFromXML setTreepBookFromXML = new SetTreepBookFromXML(getActivity(), latdep, lngdep, latdest, lngdest, pinkMode);
		    		//setTreepBookFromXML.execute();
	    			if(!MainActivity.driverMode){
	    				GetMatchedDrivers getMatchedDrivers = new GetMatchedDrivers(getActivity(),Double.toString(latdep), Double.toString(lngdep), Double.toString(latdest), Double.toString(lngdest), addressDep, addressDest, pinkMode, alMapDriverPosition);
			    		getMatchedDrivers.execute();
	    			}
	    			else{
	    				SendTreepDriverRequest sendTreepDriverRequest = new SendTreepDriverRequest(getActivity(),Double.toString(latdep), Double.toString(lngdep), Double.toString(latdest), Double.toString(lngdest),pinkMode);
	    				sendTreepDriverRequest.execute();
	    			}
	    		}
	    	}
	    }
	  };
		  
	  private TextWatcher textWatcherDep = new TextWatcher() {

		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	if(s.length() == 0){
		    		//latdep = MainActivity.myLatitude;
			        //lngdep = MainActivity.myLongitude;
			        acAddressdep.setHintTextColor(0xff4cc3ef);
			    	//acAddressdep.setBackgroundResource(R.drawable.edittextstroke);
	    			//acAddressdep.setTextColor(0xff444444);
	    			
	    			
		    	}
		    	else{
			        addressDep = null;
		    		latdep = 0;
			        lngdep=0;
		    	}
		    	tvPrice.setVisibility(View.GONE);
		    }
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count,
		      int after) {}
		    @Override
		    public void afterTextChanged(Editable s) {}
		  };
		  
	  private TextWatcher textWatcherDest = new TextWatcher() {

		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	if(s.length() == 0){
		    		//latdep = MainActivity.myLatitude;
			        //lngdep = MainActivity.myLongitude;
			        acAddressdest.setHintTextColor(0xff4cc3ef);
			    	//acAddressdep.setBackgroundResource(R.drawable.edittextstroke);
	    			//acAddressdep.setTextColor(0xff444444);
	    			
	    			
		    	}
		    	else{
			        addressDest = null;
		    		latdest = 0;
			        lngdest=0;
		    	}
		    }
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count,
		      int after) {}
		  
		    @Override
		    public void afterTextChanged(Editable s) {}
		  };
	  
	  
	  private OnItemClickListener itemClickListenerAddressdep = new OnItemClickListener() {
	    	
		    @Override
		    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		    	addressDep = (String) adapterView.getItemAtPosition(position);
		    	acAddressdepLength = acAddressdep.getText().toString().length();
		        Geocoder gcDep = new Geocoder(ApplicationContextProvider.getContext());
		        try {
					
					//une liste déroulante d'adresse possible pour qu'il sélectionne la bonne adresse
		        	//On vérifie que cette liste n'est pas vide et associe bien un LATLNG
		        	if(gcDep.getFromLocationName(addressDep, 5).size() > 0){
			        	List<Address> addresses = gcDep.getFromLocationName(addressDep, 5);
			        	Address x = addresses.get(0);
						//on récupère la latitude et longitude correspondante à l'adresse
						latdep  = x.getLatitude();
						lngdep = x.getLongitude();
						latLngDep = new LatLng(latdep,lngdep);
						
						CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLngDep, 15);
						mMap.animateCamera(cameraUpdate);
						
		        	}
		        	else{
		        		departIsOk = false;
			    		acAddressdep.setBackgroundColor(0xAAf00000);
			    		acAddressdep.setTextColor(0xffffffff);
			    		acAddressdep.setHintTextColor(0xff000000);
		    			MainActivity.displayToast(R.string.warningaddressdep);
		        	}
		        }
		        catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			//MainActivity.displayToast(addressDep);
		    }
		  };
			  
			  
	  //listeners sur l'autocompletion pour enregistrer dans une string l'adresse sélectionnée
	  private OnItemClickListener itemClickListenerAddressdest = new OnItemClickListener() {
	    	
		    @Override
		    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		    	addressDest = (String) adapterView.getItemAtPosition(position);
		    	acAddressdestLength = acAddressdest.getText().toString().length();
		        Geocoder gcDest = new Geocoder(ApplicationContextProvider.getContext());
		        try {
					//une liste déroulante d'adresse possible pour qu'il sélectionne labonneadresse
		        	//On vérifie que cette liste n'est pas vide et associe bien un LATLNG
		        	if(gcDest.getFromLocationName(addressDest, 5).size() > 0){
			        	List<Address> addresses = gcDest.getFromLocationName(addressDest, 5);
			        	Address x = addresses.get(0);
						//on récupère la latitude et longitude correspondante à l'adresse
			        	latdest  = x.getLatitude();
						lngdest = x.getLongitude();
						latLngDest = new LatLng(latdest,lngdest);
						
						CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLngDest, 15);
						mMap.animateCamera(cameraUpdate);
						
						GetDistancePriceEstimation getDistancePriceEstimation = new GetDistancePriceEstimation(getActivity(),Double.toString(latdep), Double.toString(lngdep), Double.toString(latdest), Double.toString(lngdest), tvPrice);
						getDistancePriceEstimation.execute();
		        	}
		        	else{
		        		destIsOk = false;
			    		acAddressdest.setBackgroundColor(0xAAf00000);
			    		acAddressdest.setTextColor(0xffffffff);
			    		acAddressdest.setHintTextColor(0xff000000);
		    			MainActivity.displayToast(R.string.warningaddressdest);
		        	}
		        }
		        catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        MainActivity.displayToast(addressDest);
		    }
		  };
	  
	private Button myLocationButton;
    private OnClickListener clickListenerMyLocationButton = new View.OnClickListener() {
    	
	    @Override
	    public void onClick(View v) {
            // Creating a LatLng object for the current location
	    	location = mMap.getMyLocation();
	    	if(location != null){
	    		latLngMyPosition = new LatLng(location.getLatitude(), location.getLongitude());
				CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLngMyPosition, 15);
				mMap.animateCamera(cameraUpdate);
	    	}
	    	else{
	    		gps = new GPSTracker(getActivity(),ApplicationContextProvider.getContext() );
				latLngMyPosition = new LatLng(gps.getLatitude(), gps.getLongitude());
				CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLngMyPosition, 15);
				mMap.animateCamera(cameraUpdate);
	    	}		
	    }
	  };
    
    
	  
	  private static final String LOG_TAG = "TREEP";

	  private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	  private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	  private static final String OUT_JSON = "/json";
	  private static final String API_KEY = "AIzaSyB2q-QqL8kskemF_ZgFrMSuCR0n-WlIsyY";

	  private ArrayList<String> autocomplete(String input) {
	      ArrayList<String> resultList = null;

	      HttpURLConnection conn = null;
	      StringBuilder jsonResults = new StringBuilder();
	      try {
	          StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
	          sb.append("?sensor=false&key=" + API_KEY);
	          sb.append("&components=country:fr");
	          sb.append("&input=" + URLEncoder.encode(input, "utf8"));

	          url = new URL(sb.toString());
	          conn = (HttpURLConnection) url.openConnection();
	          InputStreamReader in = new InputStreamReader(conn.getInputStream());

	          // Load the results into a StringBuilder
	          int read;
	          char[] buff = new char[1024];
	          while ((read = in.read(buff)) != -1) {
	              jsonResults.append(buff, 0, read);
	          }
	      } catch (MalformedURLException e) {
	          Log.e(LOG_TAG, "Error processing Places API URL", e);
	          return resultList;
	      } catch (IOException e) {
	          Log.e(LOG_TAG, "Error connecting to Places API", e);
	          return resultList;
	      } finally {
	          if (conn != null) {
	              conn.disconnect();
	          }
	      }

	      try {
	          // Create a JSON object hierarchy from the results
	          JSONObject jsonObj = new JSONObject(jsonResults.toString());
	          JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

	          // Extract the Place descriptions from the results
	          resultList = new ArrayList<String>(predsJsonArray.length());
	          for (int i = 0; i < predsJsonArray.length(); i++) {
	              resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
	          }
	      } catch (JSONException e) {
	          Log.e(LOG_TAG, "Cannot process JSON results", e);
	      }

	      return resultList;
	  }
	  
	  
	  
	  private class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
		    private ArrayList<String> resultList;

		    public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
		        super(context, textViewResourceId);
		    }

		    @Override
		    public int getCount() {
		        return resultList.size();
		    }

		    @Override
		    public String getItem(int index) {
		        return resultList.get(index);
		    }

		    @Override
		    public Filter getFilter() {
		        Filter filter = new Filter() {
		            @Override
		            protected FilterResults performFiltering(CharSequence constraint) {
		                FilterResults filterResults = new FilterResults();
		                if (constraint != null) {
		                    // Retrieve the autocomplete results.
		                    resultList = autocomplete(constraint.toString());

		                    // Assign the data to the FilterResults
		                    filterResults.values = resultList;
		                    filterResults.count = resultList.size();
		                }
		                return filterResults;
		            }

		            @Override
		            protected void publishResults(CharSequence constraint, FilterResults results) {
		                if (results != null && results.count > 0) {
		                    notifyDataSetChanged();
		                }
		                else {
		                    notifyDataSetInvalidated();
		                }
		            }};
		        return filter;
		    }
		}
	  

	    @Override
	    public void onPause() {
	        super.onPause();
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
	        if(driverPositionListUpdater != null){
		 		driverPositionListUpdater.stopUpdates();
		 	}
		 	if(setUserPositionUpdater != null){
		 		setUserPositionUpdater.stopUpdates();
		 	}
	    }

	  
	  
	@SuppressWarnings("unchecked")
	public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			View v = inflater.inflate(R.layout.fragment_home, container, false);
			if(LoginDisplayActivity.userId == null || LoginDisplayActivity.userId == "" ){
				Intent i = new Intent(getActivity(), LoginDisplayActivity.class);
        		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	            startActivity(i);
			}
			else{
				
				//initilize the custom typefont
				fb = Typeface.createFromAsset(getActivity().getAssets(), "fb.ttf");
				
				tableLayout = (TableLayout)v.findViewById(R.id.tableLayout);
				
				pinkModeButton = (Button) v.findViewById(R.id.pinkModeButton);
				infobanner = (TextView) v.findViewById(R.id.infobanner);
				infobannermodedriver =  (TextView) v.findViewById(R.id.infobannermodedriver);
				if(!MainActivity.driverMode){
					infobannermodedriver.setVisibility(View.GONE);
				}
				tvPrice = (TextView) v.findViewById(R.id.tvPrice);
				tvPrice.setVisibility(View.GONE);
				addressDepLayout = (LinearLayout)v.findViewById(R.id.addressDepLayout);
				addressDestLayout = (LinearLayout)v.findViewById(R.id.addressDestLayout);
				addressDestLayout.setVisibility(View.GONE);
				
				ivMyPosition = (ImageView) v.findViewById(R.id.ivMyPosition);
				
				if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
					
					pinkModeButton.setVisibility(View.GONE);
					infobanner.setVisibility(View.GONE);
					addressDepLayout.setVisibility(View.GONE);
					addressDestLayout.setVisibility(View.GONE);
					getActivity().setProgressBarIndeterminateVisibility(false);
					this.showSettingsAlert();
				}
				else{
					Bundle bundle = getArguments();
					try{
						//alMapDriverPosition  = (ArrayList<HashMap<String,String>>) bundle.get("alDriverPosition");
					
						mapUserInfo = (HashMap<String, String>) bundle.getSerializable("mapUserInfo");
					}
					catch(NullPointerException e){
						pinkModeButton.setVisibility(View.GONE);
						infobanner.setVisibility(View.GONE);
						addressDepLayout.setVisibility(View.GONE);
						addressDestLayout.setVisibility(View.GONE);
						getActivity().setProgressBarIndeterminateVisibility(false);
					}
					mapView = (MapView) v.findViewById(R.id.map);
					mapView.onCreate(savedInstanceState);
					
					// Gets to GoogleMap from the MapView and does initialization stuff
					
					
					mMap = mapView.getMap();
					
					mMap.clear();
					
					mMap.getUiSettings().setZoomControlsEnabled(false);
					mMap.setMyLocationEnabled(true);
					mMap.getUiSettings().setMyLocationButtonEnabled(false);
					
					//nightHour = Boolean.parseBoolean(mapUserInfo.get(MainActivity.KEY_NIGHTHOUR));
					
					
					if(!Boolean.parseBoolean(mapUserInfo.get(MainActivity.KEY_USERSEXE))){
						pinkModeButton.setVisibility(View.VISIBLE);
						pinkModeButton.setOnClickListener(clickListenerPinkModeButton);
					}
					else{
						pinkModeButton.setVisibility(View.GONE);
					}
					
					
					//infobanner.setTypeface(fb);
					
					
					acAddressdep = (AutoCompleteTextView) v.findViewById(R.id.acAddressdep);
					acAddressdep.setAdapter(new PlacesAutoCompleteAdapter(ApplicationContextProvider.getContext(), R.layout.list_item));
					acAddressdep.setOnItemClickListener(itemClickListenerAddressdep);
					acAddressdep.addTextChangedListener(textWatcherDep);
					
					acAddressdest = (AutoCompleteTextView) v.findViewById(R.id.acAddressdest);
					
					acAddressdest.setAdapter(new PlacesAutoCompleteAdapter(ApplicationContextProvider.getContext(), R.layout.list_item));
					acAddressdest.setOnItemClickListener(itemClickListenerAddressdest);
					acAddressdest.addTextChangedListener(textWatcherDest);
					
					buttonRequestDep = (Button) v.findViewById(R.id.buttonRequestDep);
					buttonRequestDest = (Button) v.findViewById(R.id.buttonRequestDest);
					buttonBackToDep = (Button) v.findViewById(R.id.buttonBackToDep);
					
					buttonRequestDep.setOnClickListener(clickListenerRequestTreepDepButton);
					buttonRequestDest.setOnClickListener(clickListenerRequestTreepDestButton);
					buttonBackToDep.setOnClickListener(clickListenerRequestBackToDepButton);
					
					myLocationButton = (Button) v.findViewById(R.id.mypositionbutton);
					myLocationButton.setOnClickListener(clickListenerMyLocationButton);
					
					compareButton = (Button) v.findViewById(R.id.compareButton);
					compareButton.setOnClickListener(clickListenerCompareButton);
					compareButton.setOnTouchListener(onTouchListenerCompareButton);
					buttonScaleOnTouchCompareAnim.setFillAfter(true);
					buttonScaleOnReleaseCompareAnim.setFillAfter(true);
					
					compareButton.setVisibility(View.GONE);
					
					layoutSlideOutAnim.setFillAfter(true);
					layoutSlideInAnim.setFillAfter(true);
					
					treepinPrice  = (TextView) v.findViewById(R.id.treepinPrice);
					treepintime = (TextView) v.findViewById(R.id.treepintime);
					ptPrice = (TextView) v.findViewById(R.id.ptPrice);
					ptTime = (TextView) v.findViewById(R.id.ptTime);
					taxiPrice = (TextView) v.findViewById(R.id.taxiPrice);
					taxiTime = (TextView) v.findViewById(R.id.taxiTime);
					
					// Needs to call MapsInitializer before doing any CameraUpdateFactory calls
					try {
						MapsInitializer.initialize(ApplicationContextProvider.getContext());
					} catch (GooglePlayServicesNotAvailableException e) {
						e.printStackTrace();
					}
					
					mMap.clear();
					// create class object
					
					location = mMap.getMyLocation();
					
					

			        if (location != null) {
			        	
						latLngMyPosition = new LatLng(location.getLatitude(), location.getLongitude());
						
						
						mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngMyPosition, 45));
				  		// Zoom in, animating the camera.
				  		mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null); 
					}
			        else{
			        	gps = new GPSTracker(getActivity(),ApplicationContextProvider.getContext() );
						latLngMyPosition = new LatLng(gps.getLatitude(), gps.getLongitude());
						CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLngMyPosition, 15);
						mMap.animateCamera(cameraUpdate);
						
						mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngMyPosition, 45));
				  		// Zoom in, animating the camera.
				  		mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null); 
			        }
			        //gps = new GPSTracker(getActivity(),ApplicationContextProvider.getContext() );
				        // Getting latitude of the current location
				        // Getting longitude of the current location
			        //MainActivity.myLatitude = gps.getLatitude();
			        //MainActivity.myLongitude = gps.getLongitude();
			        // Creating a LatLng object for the current location
			       
			  		
			  		
			  		
		  			mMap.setOnCameraChangeListener(new OnCameraChangeListener() {
				  	       
						@Override
						public void onCameraChange(CameraPosition arg0) {
							if(!isSetDep){
								onCameraChangeDep();
							}
							else{
								onCameraChangeDest();
							}
							
						}
		  			});
			  		
					infobanner.setVisibility(View.VISIBLE);
					
					if(driverMarkerList != null){
						for(int i = 0; i< driverMarkerList.size(); i++){
							driverMarkerList.get(i).remove();
						}
					}
					
					if(alMapDriverPosition != null){
						if(alMapDriverPosition.size() == 0){
							
							infobannertxt = new StringBuilder();
							 if(!MainActivity.driverMode){
					            	infobannertxt.append("En attente de drivers...");
					            }
					            else{
					            	infobannertxt.append("En attente de treepers...");
					            }
								
							infobanner.setText(infobannertxt.toString());
							
						}
						else{
							if(alMapDriverPosition.get(0).containsKey(MainActivity.KEY_TIMEOUT)){
				        		
					            infobannertxt = new StringBuilder();
					            if(!MainActivity.driverMode){
					            	infobannertxt.append("En attente de drivers...");
					            }
					            else{
					            	infobannertxt.append("En attente de treepers...");
					            }
								
								infobanner.setText(infobannertxt.toString());
				        		
				        	}
				        	
							else{
								//alDistanceTime = new ArrayList<Integer>();
								alMapDistanceTime = new ArrayList<HashMap<String, String>>();
								driverMarkerList = new ArrayList<Marker>();
								alDistanceTime = new ArrayList<Integer>();
								
								
								
								for(HashMap<String, String> map : alMapDriverPosition){
									
									String username = map.get(MainActivity.KEY_FIRSTNAME) + " " + map.get(MainActivity.KEY_LASTNAME).charAt(0) + ".";
									
									DisplayDriverPinList getDistance = new DisplayDriverPinList(MainActivity.myLatitude,MainActivity.myLongitude,username,map,mMap,alDistanceTime,driverMarkerList);
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
							        	 if(!MainActivity.driverMode){
							        		 	infobannertxt.append("Driver le plus proche à ");
								            }
								            else{
								            	infobannertxt.append("Treeper le plus proche à ");
								            }
										 infobannertxt.append(Collections.min(alDistanceTime)).append(" min");
										 infobanner.setText(infobannertxt.toString());
						        	 }
						         } 
						    }, 3000);
							//updateDriverPosition();
						}
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
			  		
			  	
			  		driverPositionListUpdater = new UIUpdater(new Runnable() {
			  	         @Override 
			  	         public void run() {
			  	        	UpdateDriverPositionFromXML updateDriverPositionFromXML = new UpdateDriverPositionFromXML(getActivity());
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
		
		//latLngMyPosition = new LatLng(gps.getLatitude(), gps.getLongitude());
		//CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLngMyPosition, 15);
		//mMap.animateCamera(cameraUpdate);
		
		
		
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
		 dialogSettingInternet.setCancelable(false);
		 
		 try{
			 dialogSettingInternet.show();
		 }
		 catch(BadTokenException e){
			 
		 }
		 
	 }
	
	 
	 
	public void onCameraChangeDep(){

		// TODO Auto-generated method stub
		getActivity().setProgressBarIndeterminateVisibility(true);
		
		//myMarker.setPosition(center);
		Geocoder geocoderDep;
		List<Address> addresses;
		geocoderDep = new Geocoder(ApplicationContextProvider.getContext(), Locale.getDefault());
		
		try {
			addresses = geocoderDep.getFromLocation(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude, 1);
			
			if(addresses != null){
				try{
					String address = addresses.get(0).getAddressLine(0);
					String city = addresses.get(0).getAddressLine(1);
					String country = addresses.get(0).getAddressLine(2);
					
					latLngDep = mMap.getCameraPosition().target;
					
					latdep = latLngDep.latitude;
					lngdep = latLngDep.longitude;
					acAddressdep.setText("");
					//acAddressdep.setFocusableInTouchMode(false);
					
					acAddressdep.setHint(address + ", " + city + ", " + country);
					addressDep = address + ", " + city + ", " + country;
					//MainActivity.displayToast("DEP : " + latdep + ", " + lngdep +"\nDEST : " + latdest + ", " + lngdest);
						
				}
				catch(IndexOutOfBoundsException e){
					
				}
				
			}
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	public void onCameraChangeDest(){
		getActivity().setProgressBarIndeterminateVisibility(true);
		
		//myMarker.setPosition(center);
		Geocoder geocoderDest;
		List<Address> addresses;
		geocoderDest = new Geocoder(ApplicationContextProvider.getContext(), Locale.getDefault());
		try {
			addresses = geocoderDest.getFromLocation(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude, 1);
			
			if(addresses != null){
				
				try{
					String address = addresses.get(0).getAddressLine(0);
					String city = addresses.get(0).getAddressLine(1);
					String country = addresses.get(0).getAddressLine(2);
					
					latdest = mMap.getCameraPosition().target.latitude;
					lngdest = mMap.getCameraPosition().target.longitude;
					acAddressdest.setText("");
					//acAddressdep.setFocusableInTouchMode(false);
					
					acAddressdest.setHint(address + ", " + city + ", " + country);
					addressDest = address + ", " + city + ", " + country;
					//MainActivity.displayToast("DEP : " + latdep + ", " + lngdep +"\nDEST : " + latdest + ", " + lngdest);
	    			
				}
				catch(IndexOutOfBoundsException e){
					
				}
				
				if(addressDest != null){
					GetDistancePriceEstimation getDistancePriceEstimation = new GetDistancePriceEstimation(getActivity(),Double.toString(latdep), Double.toString(lngdep), Double.toString(latdest), Double.toString(lngdest), tvPrice);
					getDistancePriceEstimation.execute();
				}
				
			}
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 
	 
	 
	 private class DialogPinkModeOn extends Dialog implements
	    android.view.View.OnClickListener {
		
		  public Activity activity;
		  public Button okButton;
		  public TextView tvModePink1;
		  private Typeface fb;
		  private Typeface bello;
		  
		  public DialogPinkModeOn(Activity activity) {
		    super(activity);
		    // TODO Auto-generated constructor stub
		    this.activity = activity;
		  }
		
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    setContentView(R.layout.dialog_pinkmodeon);
		    fb = Typeface.createFromAsset(activity.getAssets(), "fb.ttf");
		    
		    okButton = (Button)findViewById(R.id.okButton);
		    okButton.setOnClickListener(this);
		    okButton.setTypeface(fb);
		    
		  }
		
		  @Override
		  public void onClick(View v) {
		    switch (v.getId()) {
		    case R.id.okButton:
		    	dismiss();
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
			
			public UpdateDriverPositionFromXML(Activity activity){
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
		    	String xml = parser.getXmlFromUrl(MainActivity.URL_USERINFO + "?userid=" + LoginDisplayActivity.userId +"&lat=" + MainActivity.myLatitude + "&lng=" + MainActivity.myLongitude);
				
				if(xml == null){
					alDriverPosition = null;
				}
				else{
					if(xml == "timeout"){
						alDriverPosition.add(mapTimeout);
					}
					
					else{
						
						Document doc = null;
						try{
							doc = parser.getDomElement(xml); // getting DOM element
						}
						catch(DOMException e){
							
						}
						
						if(doc == null){
							alDriverPosition = null;
						}
						else{
							
							if(!MainActivity.driverMode){
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
							else{
								NodeList nl_DriverPositionList = doc.getElementsByTagName(MainActivity.KEY_USERPOSITIONLIST);
								for (int i = 0; i < nl_DriverPositionList.getLength(); i++) {
									Element e = (Element) nl_DriverPositionList.item(i);
									NodeList nl_DriverPosition = e.getElementsByTagName(MainActivity.KEY_USERPOSITION);
									// looping through all xml nodes <KEY_USER>
									for (int j = 0; j < nl_DriverPosition.getLength(); j++) {
										// creating new HashMap
										HashMap<String, String> mapDriverPosition = new HashMap<String, String>();
										Element t = (Element) nl_DriverPosition.item(j);
										// adding each child node to HashMap key => value
										mapDriverPosition.put(MainActivity.KEY_USERID, parser.getValue(t, MainActivity.KEY_USERID));
										mapDriverPosition.put(MainActivity.KEY_FIRSTNAME, parser.getValue(t, MainActivity.KEY_FIRSTNAME));
										mapDriverPosition.put(MainActivity.KEY_LASTNAME, parser.getValue(t, MainActivity.KEY_LASTNAME));
										mapDriverPosition.put(MainActivity.KEY_RATING, parser.getValue(t, MainActivity.KEY_RATING));
										mapDriverPosition.put(MainActivity.KEY_LATITUDE, parser.getValue(t, MainActivity.KEY_LATITUDE));
										mapDriverPosition.put(MainActivity.KEY_LONGITUDE, parser.getValue(t, MainActivity.KEY_LONGITUDE));
										mapDriverPosition.put(MainActivity.KEY_TREEPREQUESTED, parser.getValue(t, MainActivity.KEY_TREEPREQUESTED));
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
				}
				
				return alDriverPosition;
				
			}

			
			@SuppressLint("NewApi")
			protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
				
				if(result == null){
					alMapDriverPosition = result;
				}
				else{
					alMapDriverPosition = result;
					if(result.size() == 0){
						for(int i = 0; i< driverMarkerList.size(); i++){
							driverMarkerList.get(i).remove();
						}
						
						infobannertxt = new StringBuilder();
						if(!MainActivity.driverMode){
							infobannertxt.append("En attente de drivers...");
						}
						else{
							infobannertxt.append("En attente de treepers...");
						}
						
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
							
							alDistanceTime = new ArrayList<Integer>();
							alMapDistanceTime = new ArrayList<HashMap<String, String>>();
							//driverMarkerList = new ArrayList<Marker>();
							
							
							for(HashMap<String, String> map : result){
								
								String username = map.get(MainActivity.KEY_FIRSTNAME) + " " + map.get(MainActivity.KEY_LASTNAME).charAt(0) + ".";
								DisplayDriverPinList getDistance = new DisplayDriverPinList(MainActivity.myLatitude,MainActivity.myLongitude,username,map,mMap,alDistanceTime,driverMarkerList);
								getDistance.execute();
								
								//MainActivity.displayToast(map.get(MainActivity.KEY_USERID));
						    }

							Handler handler = new Handler(); 
						    handler.postDelayed(new Runnable() { 
						         public void run() {
						        	 if(alDistanceTime.size() != 0){
							        	 infobannertxt = new StringBuilder();
							        	 if(!MainActivity.driverMode){
												infobannertxt.append("Driver le plus proche à ").append(Collections.min(alDistanceTime)).append(" min");
											}
											else{
												infobannertxt.append("Treeper le plus proche à ").append(Collections.min(alDistanceTime)).append(" min");
											}
										 
										 infobanner.setText(infobannertxt.toString());
						        	 }
						        	 else{
						        		 infobannertxt = new StringBuilder();
						        		 
						        		 if(!MainActivity.driverMode){
												infobannertxt.append("En attente de drivers...");
											}
											else{
												infobannertxt.append("En attente de treepers...");
											}
										infobanner.setText(infobannertxt.toString());
						        	 }
						         } 
						    }, 3000);
							//updateDriverPosition();
						}
					}
				}
			}
		}
		
		
		private class CompareDurationForPublicTransportWithGoogle extends AsyncTask<Void, Integer, HashMap<String,String>> {
			
			String json;
			
			public CompareDurationForPublicTransportWithGoogle(){
				
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
				
				HashMap<String,String> mapDistanceDuration = new HashMap<String,String>();
				
				StringBuilder stringBuilder = new StringBuilder();
				
				
				try {
			        String url = "http://maps.googleapis.com/maps/api/directions/json?origin=" + latdep + "," + lngdep + "&destination=" + latdest  +","+ lngdest + "&sensor=false&region=fr&departure_time=" + System.currentTimeMillis()/1000 +"&mode=transit";
			        //String url = "http://maps.googleapis.com/maps/api/directions/json?origin=1%20rue%20vidal%20de%20la%20blache%2075020%20paris&destination=2%20rue%20brochant%2075017%20paris&sensor=false&region=fr&departure_time=1408143996&mode=transit";	
			
			        HttpPost httppost = new HttpPost(url);
			
			        HttpClient client = new DefaultHttpClient();
			        HttpResponse response;
			        stringBuilder = new StringBuilder();
			
			
			        response = client.execute(httppost);
			        HttpEntity entity = response.getEntity();
			        InputStream stream = entity.getContent();
			        int b;
			        while ((b = stream.read()) != -1) {
			            stringBuilder.append((char) b);
			        }
		        }
		        catch (ClientProtocolException e) {
		        } 
		        catch (IOException e) {
		        }
				
				JSONObject jsonObject = new JSONObject();
		        try {

		            jsonObject = new JSONObject(stringBuilder.toString());
		            
		            json = stringBuilder.toString();
		            
		           // JSONObject JSONstatus = jsonObject.getJSONObject("status");
		            mapDistanceDuration.put(MainActivity.KEY_STATUS, jsonObject.getString("status"));

		            JSONArray JSONArrayRoutes = jsonObject.getJSONArray("routes");

		            JSONObject JSONObjectRoutesElement = JSONArrayRoutes.getJSONObject(0);
		            
		            JSONArray JSONArrayLegs = JSONObjectRoutesElement.getJSONArray("legs");

		            JSONObject JSONObjectLegsElement = JSONArrayLegs.getJSONObject(0);
		            
		            JSONObject JSONObjectDuration = JSONObjectLegsElement.getJSONObject("duration");
		            
		            mapDistanceDuration.put(MainActivity.KEY_DURATION, JSONObjectDuration.getString("value"));
		            

		        } catch (JSONException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		        catch(NullPointerException e){
		        	
		        }
		        catch(RuntimeException e){
		        	
		        }
				
				
				
				return mapDistanceDuration;
				
			}

			
			@SuppressLint("NewApi")
			protected void onPostExecute(HashMap<String,String> result) {
				
				if(result == null){
					ptTime.setTextColor(Color.parseColor("#CDCDCD"));
					ptTime.setText("Indisp. status");
					
					//MainActivity.displayToast(json);
				}
				else{
					if(!result.get(MainActivity.KEY_STATUS).contains("OK")){
						ptTime.setTextColor(Color.parseColor("#CDCDCD"));
						ptTime.setText("Indisp.");
						
						//MainActivity.displayToast(json);
					}
					else{
						ptTime.setTextColor(Color.parseColor("#888888"));
						long durationInMillis = Long.parseLong(result.get(MainActivity.KEY_DURATION))*1000;
					    if(Double.parseDouble(result.get(MainActivity.KEY_DURATION))<3600){
					    	ptTime.setText(String.format("%02d min",TimeUnit.MILLISECONDS.toMinutes(durationInMillis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(durationInMillis))));
						
					    }
					    else{
					    	ptTime.setText(String.format("%02dh%02dmin", TimeUnit.MILLISECONDS.toHours(durationInMillis),
						            TimeUnit.MILLISECONDS.toMinutes(durationInMillis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(durationInMillis))));
						
					    }    
					}
				}
			}
		}
		
		
		private class CompareDistanceDuration extends AsyncTask<Void, Integer, HashMap<String, String>> {
			
			private Activity activity;
			private String distanceForPublicTransport;
			
			
			public CompareDistanceDuration(Activity activity, String distanceForPublicTransport){
				this.activity=activity;
				this.distanceForPublicTransport=distanceForPublicTransport;
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
			protected HashMap<String, String> doInBackground(Void... arg0) {
				HashMap<String, String> mapDistanceDuration = new HashMap<String, String>();
				
				StringBuilder stringBuilder = new StringBuilder();
				
				try {
			        String url = "http://maps.googleapis.com/maps/api/distancematrix/json?origins=" + latdep + "," + lngdep + "&destinations=" + latdest  +","+ lngdest + "&mode=driving&sensor=false";
			
			        HttpPost httppost = new HttpPost(url);
			
			        HttpClient client = new DefaultHttpClient();
			        HttpResponse response;
			        stringBuilder = new StringBuilder();
			
			
			        response = client.execute(httppost);
			        HttpEntity entity = response.getEntity();
			        InputStream stream = entity.getContent();
			        int b;
			        while ((b = stream.read()) != -1) {
			            stringBuilder.append((char) b);
			        }
		        }
		        catch (ClientProtocolException e) {
		        } 
		        catch (IOException e) {
		        }
				
				JSONObject jsonObject = new JSONObject();
		        try {

		            jsonObject = new JSONObject(stringBuilder.toString());
		            
		           // JSONObject JSONstatus = jsonObject.getJSONObject("status");
		            mapDistanceDuration.put(MainActivity.KEY_STATUS, jsonObject.getString("status"));

		            JSONArray JSONArrayRows = jsonObject.getJSONArray("rows");

		            JSONObject JSONelements = JSONArrayRows.getJSONObject(0);
		            
		            JSONArray JSONArrayElements = JSONelements.getJSONArray("elements");
		            
		            JSONObject JSONObjectdistanceDuration = JSONArrayElements.getJSONObject(0);
		            
		            JSONObject JSONObjectDistance1 = JSONObjectdistanceDuration.getJSONObject("distance");
		            JSONObject JSONObjectDuration1 = JSONObjectdistanceDuration.getJSONObject("duration");
		            
		            mapDistanceDuration.put(MainActivity.KEY_DISTANCE, JSONObjectDistance1.getString("value"));
		            mapDistanceDuration.put(MainActivity.KEY_DURATION, JSONObjectDuration1.getString("value"));
		            

		        } catch (JSONException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
				
				return mapDistanceDuration;
			}

			
			@SuppressLint("NewApi")
			protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
				
				if(result == null){
					
				}
				else{
				}
			}
		}
		
}