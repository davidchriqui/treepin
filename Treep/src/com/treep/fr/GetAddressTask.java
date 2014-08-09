package com.treep.fr;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class GetAddressTask extends AsyncTask<Void, Void, String> {
	Activity activity;
	Context mContext;
	Double lat;
	Double lng;
	LatLng latLngMyPosition;
	Marker myMarker;
	AutoCompleteTextView acAddressdep;
	
	
	public GetAddressTask(Activity activity,Double lat, Double lng,LatLng latLngMyPosition,Marker myMarker,AutoCompleteTextView acAddressdep) {
	    super();
	    this.activity=activity;
	    mContext = ApplicationContextProvider.getContext();
	    this.lat = lat;
	    this.lng = lng;
	    this.latLngMyPosition=latLngMyPosition;
	    this.myMarker=myMarker;
	    this.acAddressdep=acAddressdep;
	}
	
	
	
	/**
	 * Get a Geocoder instance, get the latitude and longitude
	 * look up the address, and return it
	 *
	 * @params params One or more Location objects
	 * @return A string containing the address of the current
	 * location, or an empty string if no address can be found,
	 * or an error message
	 */
	@Override
	protected String doInBackground(Void... params) {
	    Geocoder geocoder =  new Geocoder(mContext, Locale.getDefault());
	    // Get the current location from the input parameter list
	    
	    // Create a list to contain the result address
	    List<Address> addresses = null;
	    try {
	        /*
	         * Return 1 address.
	         */
	        addresses = geocoder.getFromLocation(lat,lng, 1);
	    } catch (IOException e1) {
	    Log.e("LocationSampleActivity",
	            "IO Exception in getFromLocation()");
	    e1.printStackTrace();
	    return ("IO Exception trying to get address");
	    } catch (IllegalArgumentException e2) {
	    // Error message to post in the log
	    String errorString = "Illegal arguments " +
	            Double.toString(lat) +
	            " , " +
	            Double.toString(lng) +
	            " passed to address service";
	    Log.e("LocationSampleActivity", errorString);
	    e2.printStackTrace();
	    return errorString;
	    }
	    // If the reverse geocode returned an address
	    if (addresses != null && addresses.size() > 0) {
	        // Get the first address
	        Address address = addresses.get(0);
	        /*
	         * Format the first line of address (if available),
	         * city, and country name.
	         */
	        String addressText = String.format(
	                "%s, %s, %s",
	                // If there's a street address, add it
	                address.getMaxAddressLineIndex() > 0 ?
	                        address.getAddressLine(0) : "",
	                // Locality is usually a city
	                address.getLocality(),
	                // The country of the address
	                address.getPostalCode());
	        // Return the text
	        return addressText;
	    } 
	    else {
	        return "Ma position";
	    }
	}
	
	@Override
	protected void onPostExecute(String  result) {
		//Toast.makeText(ApplicationContextProvider.getContext(), "Le traitement asynchrone est terminé", Toast.LENGTH_LONG).show();
		
		if(latLngMyPosition != null){
			myMarker.setPosition(latLngMyPosition);
			myMarker.setSnippet(result);
			if(acAddressdep !=null){
				acAddressdep.setHint(result);
			}
			HomeFragment.addressDep = result;
			//MainActivity.displayToast(HomeFragment.addressDep);
		}
		
	}
}