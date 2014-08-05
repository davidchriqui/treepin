package com.treep.fr;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.treep.fr.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class RatingFragment extends Fragment {

	
	private TextView tvUsername;
	private ImageView ivProfilePic;
	private RatingBar ratingBar;
	private TextView tvRating;
	private Button confirmRatingButton;
	private TextView tvRatingTitle;
	private ImageLoader imageLoaderProfilePic;
	private HashMap<String, String> mapTreep = new HashMap<String, String>();
    
    
	@SuppressWarnings("unchecked")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_rating, container, false);
        
        Typeface bello = Typeface.createFromAsset(getActivity().getAssets(), "bello.ttf");
        Typeface fb = Typeface.createFromAsset(getActivity().getAssets(), "fb.ttf");
        Bundle bundle = getArguments();
        mapTreep = (HashMap<String, String>) bundle.getSerializable("mapTreepNow");
        
        tvUsername = (TextView) rootView.findViewById(R.id.tvUsername);
        ivProfilePic = (ImageView) rootView.findViewById(R.id.ivProfilePic);
        ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
        tvRating = (TextView) rootView.findViewById(R.id.tvRating);
        tvRatingTitle = (TextView) rootView.findViewById(R.id.tvRatingTitle);
        confirmRatingButton = (Button) rootView.findViewById(R.id.confirmRating);
        
        tvUsername.setTypeface(bello);
        tvRatingTitle.setTypeface(bello);
        confirmRatingButton.setTypeface(fb);
        
     
        String username = mapTreep.get(MainActivity.KEY_FIRSTNAME) + " " + mapTreep.get(MainActivity.KEY_LASTNAME).charAt(0) + ".";
		tvUsername.setText(username);
		
		imageLoaderProfilePic = new ImageLoader(ApplicationContextProvider.getContext());
        imageLoaderProfilePic.DisplayImage(MainActivity.urlProfilePic(mapTreep.get(MainActivity.KEY_USERID)),ivProfilePic);
		
    
        ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar rateBar, float rating,
                    boolean fromUser) {
               
            	if(rating == (int) rating)
            		tvRating.setText(String.format("%d",(int)rating) + " /5");
                else{
            	 tvRating.setText(rating + "/5");
            	 }
            }
           });
        
        OnClickListener clickListenerConfirmButton = new View.OnClickListener() {
    	    @SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
    	    	if(!CheckInternet(ApplicationContextProvider.getContext())){
	    			
     				getFragmentManager().popBackStack();
     				FragmentManager fragmentManager = getFragmentManager();
    		        fragmentManager.beginTransaction().replace(R.id.frame_container,  new IsNotAvailableFragment()).commit();
     			}
	    		else{
    	    		MainActivity.initposition = 0;
    	    		getActivity().setProgressBarIndeterminateVisibility(true);
    	    		MainActivity.fragment= new HomeFragment();
    				FragmentManager fragmentManager = getFragmentManager();
    				fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container, MainActivity.fragment).commit();
    				
	    		}
    	    }
	  };
	  confirmRatingButton.setOnClickListener(clickListenerConfirmButton);
        
        return rootView;
	
	
	}
	
	public static boolean CheckInternet(Context ctx) {
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        // Check if wifi or mobile network is available or not. If any of them is
        // available or connected then it will return true, otherwise false;
        return wifi.isConnected() || mobile.isConnected();
    }
    
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ApplicationContextProvider.getContext());
      
        // Setting Dialog Title
        alertDialog.setTitle("Paramétrage Internet");
  
        // Setting Dialog Message
        alertDialog.setMessage("Pas de connexion internet. Voulez-vous l'activer?");
  
        // On pressing Settings button
        alertDialog.setPositiveButton("Réglages", new DialogInterface.OnClickListener() {
            @Override
			public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
            }
        });
  
        // on pressing cancel button
        alertDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
			public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
  
        // Showing Alert Message
        alertDialog.show();
    }
	
	
}

