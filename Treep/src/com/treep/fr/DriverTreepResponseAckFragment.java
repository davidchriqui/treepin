package com.treep.fr;

import java.util.HashMap;

import com.google.android.gms.location.LocationListener;
import com.treep.fr.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class DriverTreepResponseAckFragment extends Fragment {



	  public Context context;
	  public Button confirmButton;
	  private ImageView ivTreeperProfilePic;
	  private TextView tvTreeperTitle;
	  private TextView tvDep;
	  private TextView tvDest;
	  private TextView tvPrice;
	  private ImageLoader imageLoaderProfilePic;
	  private HashMap<String, String> mapDriverTreepNow = new HashMap<String, String>();
	  
	  private Typeface fb;
	  
	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
		
		
			View v = inflater.inflate(R.layout.fragment_drivertreepnowresponseack, container, false);
			
			Bundle bundle = getArguments();
			try{
			mapDriverTreepNow = (HashMap<String, String>) bundle.getSerializable("mapDriverTreepNow");
			}
			catch(NullPointerException e){
				
			}
			
			try{
				fb = Typeface.createFromAsset(context.getAssets(), "fb.ttf");
			}
			catch(NullPointerException e){
				
			}
			
			    
		    tvTreeperTitle = (TextView)v.findViewById(R.id.tvTreeperTitle);
		    
		    tvTreeperTitle.setText(mapDriverTreepNow.get(MainActivity.KEY_FIRSTNAME) + " " + mapDriverTreepNow.get(MainActivity.KEY_LASTNAME).charAt(0) + ". a accepté de treeper avec vous.");
		    
		    ivTreeperProfilePic = (ImageView)v.findViewById(R.id.ivTreeperProfilePic);
		    
		    imageLoaderProfilePic = new ImageLoader(ApplicationContextProvider.getContext());
	        imageLoaderProfilePic.DisplayImage(MainActivity.urlProfilePic(mapDriverTreepNow.get(MainActivity.KEY_USERID)),ivTreeperProfilePic);
		    
	        tvPrice = (TextView)v.findViewById(R.id.tvPrice);
	        tvPrice.setText("Prix : " + mapDriverTreepNow.get(MainActivity.KEY_PRICE) + " €");
	        
		    tvDep = (TextView)v.findViewById(R.id.tvDep);
		    
		    tvDep.setText(mapDriverTreepNow.get(MainActivity.KEY_ADDRESSDEPNOW));
		    tvDest = (TextView)v.findViewById(R.id.tvDest);
		    tvDest.setText(mapDriverTreepNow.get(MainActivity.KEY_ADDRESSDESTNOW));
		    confirmButton = (Button) v.findViewById(R.id.confirmButton);
		   
		    confirmButton.setTypeface(fb);
			
		    OnClickListener clickListenerButton = new View.OnClickListener() {
			    @SuppressLint("NewApi")
				@Override
				public void onClick(View v) {
			    	switch (v.getId()) {
				    case R.id.confirmButton:
				    	if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
				        	
							MainActivity.displayToast(R.string.httpTimeOut);
				    	}
				    	else{
				    		SetDriverTreepNowResponseAckFromXML setDriverTreepNowResponseAckFromXML = new SetDriverTreepNowResponseAckFromXML(getActivity());
				    		setDriverTreepNowResponseAckFromXML.execute();
				    	}
				      break;
				    default:
				      break;
				    }
			    }
		  };
		    
		  confirmButton.setOnClickListener(clickListenerButton);
		  
			getActivity().setProgressBarIndeterminateVisibility(false); 
			return v;
			
	}
	
}