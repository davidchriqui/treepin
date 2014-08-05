package com.treep.fr;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.treep.fr.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
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
import android.widget.LinearLayout;
import android.widget.TextView;
 
public class MyDriverProfileFragment extends Fragment {
     
	
	
	private ImageView ivDriverProfilePicture;
	private ImageView ivCarPicture;
	private ImageLoader imageLoaderProfile;
	private ImageLoader imageLoaderCar;
	private ImageView ivDriverRating;
	private TextView tvDriverUsername;
	private TextView tvDriverRating;
	private TextView tvCarModel;
	private TextView tvTreepNb;
	private TextView tvfbFriends;
	private String username;
	private Button fbProfileButton;
	private LinearLayout inHorizontalScrollView;
	
    public MyDriverProfileFragment(){}
     
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
		//On récupère les informations transmise par le fragment MyDriversListFragment
		final Bundle bundle = getArguments();
		
		MainActivity.appQuit = false;
		//here is your list array 
        //String myStrings=bundle.getString(MyDriversListFragment.KEY_PROFILEPIC_URL);
		
		
        View rootView = inflater.inflate(R.layout.fragment_mydriverprofile, container, false);

        
      //initilize the custom typefont
      		Typeface bellofont = Typeface.createFromAsset(getActivity().getAssets(), "bello.ttf");
      		
  		tvDriverRating = (TextView) rootView.findViewById(R.id.tvDriverRating);
  		tvDriverRating.setTypeface(bellofont);
  		
  		tvfbFriends = (TextView) rootView.findViewById(R.id.tvfbFriends);
  		
  		
  		ivDriverProfilePicture = (ImageView) rootView.findViewById(R.id.driverprofilepicture);
  		ivCarPicture = (ImageView) rootView.findViewById(R.id.drivercarpicture);
  		
  		imageLoaderProfile = new ImageLoader(ApplicationContextProvider.getContext());
  		imageLoaderProfile.DisplayImage(MainActivity.urlProfilePic(bundle.getString(MainActivity.KEY_USERID)),ivDriverProfilePicture);
  		
  		if(bundle.getString(MainActivity.KEY_CARPIC_URL) == null || bundle.getString(MainActivity.KEY_CARPIC_URL) == "" ){
  			ivCarPicture.setVisibility(View.GONE);
  		}
  		else{
	  		imageLoaderCar = new ImageLoader(ApplicationContextProvider.getContext());
	  		imageLoaderCar.DisplayImage(bundle.getString(MainActivity.KEY_CARPIC_URL),ivCarPicture);
  		}
  		
        tvDriverUsername=(TextView)rootView.findViewById(R.id.tvDriverUsername);
        username = bundle.getString(MainActivity.KEY_FIRSTNAME) + " " + bundle.getString(MainActivity.KEY_LASTNAME).charAt(0) + ".";
        Typeface bello = Typeface.createFromAsset(getActivity().getAssets(), "bello.ttf");
        tvDriverUsername.setText(username);
        
        tvCarModel = (TextView)rootView.findViewById(R.id.tvCarModel);
        tvCarModel.setText(bundle.getString(MainActivity.KEY_CARMODEL));
        
        tvTreepNb = (TextView)rootView.findViewById(R.id.tvTreepNb);
        tvTreepNb.setText(bundle.getString(MainActivity.KEY_NBTREEP) + " treeps");
        
       	//Toast.makeText( ApplicationContextProvider.getContext(), bundle.getString(MyDriversListFragment.KEY_RATING) , Toast.LENGTH_SHORT).show();
        
        
       ivDriverRating = (ImageView) rootView.findViewById(R.id.ivDriverRating);
       
       
       
       switch (Integer.parseInt(bundle.getString(MainActivity.KEY_RATING))) {
       case 0:
       	//list_rating.setImageResource(R.drawable.rating1);       
           break;
       case 1:
    	   ivDriverRating.setImageResource(R.drawable.rating1);
             
           break;
       case 2:
    	   ivDriverRating.setImageResource(R.drawable.rating2);
             
           break;
       case 3:
    	   ivDriverRating.setImageResource(R.drawable.rating3);
             
           break;
       case 4:
    	   ivDriverRating.setImageResource(R.drawable.rating4);
             
           break;
       case 5:
    	   ivDriverRating.setImageResource(R.drawable.rating5);
             
           break;
		
		}
       inHorizontalScrollView = (LinearLayout)rootView.findViewById(R.id.inhorizontalscrollview);
       
       
       //ArrayList<String> friendList;
       
       (new StoreFriendListFromJSON(this.getActivity(),inHorizontalScrollView, tvfbFriends, bundle.getString(MainActivity.KEY_USERID))).execute();
       
       //Toast.makeText(ApplicationContextProvider.getContext(), bundle.getString(MainActivity.KEY_USERID), Toast.LENGTH_LONG).show();
       fbProfileButton = (Button) rootView.findViewById(R.id.fbProfileButton);
       
       OnClickListener clickListenerFbProfile = new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    
		    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://facebook.com/profile.php?id="+bundle.get(MainActivity.KEY_USERID)));
		    	startActivity(browserIntent);
		    }
		  };
		  fbProfileButton.setOnClickListener(clickListenerFbProfile);
       	
        
        return rootView;
    }
    
}