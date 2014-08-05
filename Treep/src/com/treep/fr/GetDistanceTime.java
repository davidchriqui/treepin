package com.treep.fr;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

public class GetDistanceTime extends AsyncTask<Void, Void, String> {
	Context mContext;
	Double lat1;
	Double lng1;
	Double lat2;
	Double lng2;
	String distanceTime = "";
	String username;
	StringBuilder infobannertxt = new StringBuilder();
	TextView infobanner;
	TextView tvspOrderedDistanceTime;
	
	public GetDistanceTime(String distanceTime, 
			String username,
			TextView tvspOrderedDistanceTime,
			TextView infobanner) {
	    super();
	    mContext = ApplicationContextProvider.getContext();
	    this.distanceTime=distanceTime;
	    this.username=username;
	    this.tvspOrderedDistanceTime=tvspOrderedDistanceTime;
	    this.infobanner=infobanner;
	}
	
	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected String doInBackground(Void... params) {
	   
		
	 	    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
	 		Calendar cal = Calendar.getInstance();
	 		cal.add(Calendar.MINUTE, Integer.parseInt(distanceTime));
	 	    distanceTime =dateFormat.format(cal.getTime());
	 	    
			return distanceTime;
	}
	
	
	 protected void onPostExecute(String result) {   
		 	if(result == null ){
		 		tvspOrderedDistanceTime.setText("Veuillez patienter");
		 		infobannertxt.append(username).append(" arrive");
		 		infobanner.setText(infobannertxt.toString());
		 	}
		 	
		 	else{
		 		tvspOrderedDistanceTime.setText("Rendez-vous à " + result);
		 		infobannertxt.append(username).append(" arrive à ").append(distanceTime);
		 		infobanner.setText(infobannertxt.toString());
		 	}
	            
	        
	    }
}
	