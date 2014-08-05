package com.treep.fr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
 
public class TreepDriverResponseUserProfileFragment extends Fragment {
     
	
	
	private ImageView ivDriverResponseUserProfilePic;
	private ImageLoader imageLoaderProfile;
	private TextView tvDriverOrderUsername;
	private TextView tvResponseTreepNb;
	private ImageView ivDriverResponseUserRating;
	private TextView tvDriverResponseUserRating;
	private TextView tvResponseDistanceTime;
	private TextView tvReponsePrice;
	private TextView tvResponseAddressDep;
	private TextView tvResponseAddressDest;
	private Button confirmResponseButton;
	private TextView tvfbFriends;
	private TextView tvTreepRepsonseTimeRemaining;
	private LinearLayout inHorizontalScrollView;
	
	private String username;
	
	private Boolean isTreepResponseOutDated = false;
	private Button fbProfileButton;
	private static Bundle bundle;
	
    public TreepDriverResponseUserProfileFragment(){}
     
    
	@SuppressLint("SimpleDateFormat")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		 
		//On récupère les informations transmise par le fragment TreepResponseListFragment
		bundle = getArguments();
		
		MainActivity.appQuit = false;
		
		
        View rootView = null ;
        
        try{
        	rootView= inflater.inflate(R.layout.fragment_treepdriverresponseuserprofile, container, false);
        }
        catch(InflateException e){
        	MainActivity.initposition = 0;
			Intent i = new Intent(ApplicationContextProvider.getContext(), MainActivity.class);
    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    		ApplicationContextProvider.getContext().startActivity(i);
        }

        
        
      //initilize the custom typefont
      	Typeface bello = Typeface.createFromAsset(getActivity().getAssets(), "bello.ttf");
      	Typeface fb = Typeface.createFromAsset(getActivity().getAssets(), "fb.ttf");


      	
      	ivDriverResponseUserProfilePic = (ImageView) rootView.findViewById(R.id.ivDriverResponseUserProfilePic);
  		
  		imageLoaderProfile = new ImageLoader(ApplicationContextProvider.getContext());
  		imageLoaderProfile.DisplayImage(MainActivity.urlProfilePic(bundle.getString(MainActivity.KEY_USERID)),ivDriverResponseUserProfilePic);
  		
  		tvDriverOrderUsername=(TextView)rootView.findViewById(R.id.tvDriverOrderUsername);
        username = bundle.getString(MainActivity.KEY_FIRSTNAME) + " " + bundle.getString(MainActivity.KEY_LASTNAME).charAt(0) + ".";
        
        tvDriverOrderUsername.setText(username);
        
        tvResponseTreepNb = (TextView)rootView.findViewById(R.id.tvResponseTreepNb);
        tvResponseTreepNb.setText(bundle.getString(MainActivity.KEY_NBTREEP) + " treeps");
        
        ivDriverResponseUserRating = (ImageView) rootView.findViewById(R.id.ivDriverResponseUserRating);
       
       
       
       switch (Integer.parseInt(bundle.getString(MainActivity.KEY_RATING))) {
       case 0:
    	   ivDriverResponseUserRating.setImageResource(R.drawable.rating1);       
           break;
       case 1:
    	   ivDriverResponseUserRating.setImageResource(R.drawable.rating1);
             
           break;
       case 2:
    	   ivDriverResponseUserRating.setImageResource(R.drawable.rating2);
             
           break;
       case 3:
    	   ivDriverResponseUserRating.setImageResource(R.drawable.rating3);
             
           break;
       case 4:
    	   ivDriverResponseUserRating.setImageResource(R.drawable.rating4);
             
           break;
       case 5:
    	   ivDriverResponseUserRating.setImageResource(R.drawable.rating5);
             
           break;
		
		}
       
   	
       tvDriverResponseUserRating = (TextView) rootView.findViewById(R.id.tvDriverResponseUserRating);
       tvDriverResponseUserRating.setTypeface(bello);
       
       tvResponseDistanceTime = (TextView) rootView.findViewById(R.id.tvResponseDistanceTime);
       tvReponsePrice = (TextView) rootView.findViewById(R.id.tvResponsePrice);
       tvReponsePrice.setText(bundle.get(MainActivity.KEY_PRICE) + "€");
       
       tvResponseAddressDep = (TextView) rootView.findViewById(R.id.tvResponseAddressDep);
       tvResponseAddressDep.setText(bundle.getString(MainActivity.KEY_ADDRESSDEP));
       
       tvResponseAddressDest = (TextView) rootView.findViewById(R.id.tvResponseAddressDest);
       tvResponseAddressDest.setText(bundle.getString(MainActivity.KEY_ADDRESSDEST));
   
	  
       tvfbFriends = (TextView) rootView.findViewById(R.id.tvResponseFbFriends);
       inHorizontalScrollView  = (LinearLayout) rootView.findViewById(R.id.inhorizontalscrollview);
	 
       (new StoreFriendListFromJSON(this.getActivity(),inHorizontalScrollView, tvfbFriends, bundle.getString(MainActivity.KEY_USERID))).execute();
       
       confirmResponseButton = (Button) rootView.findViewById(R.id.confirmResponseButton);
       confirmResponseButton.setTypeface(fb);
       OnClickListener clickListenerConfirmButton = new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    
		    	if(!isTreepResponseOutDated){
		    		DialogConfirmUserOrder dialogConfirmUserOrder = new DialogConfirmUserOrder(getActivity(), bundle.getString(MainActivity.KEY_TREEPID),bundle.getString(MainActivity.KEY_USERID), bundle.getString(MainActivity.KEY_DISTANCETIME), bundle.getString(MainActivity.KEY_PRICE));
		    		dialogConfirmUserOrder.show();
		    	}
		    	else{
					MainActivity.displayToast("La proposition n'est plus disponible, veuillez rafraichir");
		    	}
		    }
		};
       confirmResponseButton.setOnClickListener(clickListenerConfirmButton);
      
       tvResponseDistanceTime.setText("Se trouve à " + bundle.get(MainActivity.KEY_DISTANCETIME) + " min");
      
       fbProfileButton = (Button) rootView.findViewById(R.id.fbProfileButton);
       
       OnClickListener clickListenerFbProfile = new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    
		    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://facebook.com/profile.php?id="+bundle.get(MainActivity.KEY_USERID)));
		    	startActivity(browserIntent);
		    }
		  };
		  fbProfileButton.setOnClickListener(clickListenerFbProfile);
	  
	  
       tvTreepRepsonseTimeRemaining = (TextView)rootView.findViewById(R.id.tvTreepRepsonseTimeRemaining);
       
       new CountDownTimer(Long.parseLong(bundle.getString(MainActivity.KEY_TREEPRESPONSETIMEREMAINING))*1000, 1000) {

    	   public void onTick(long millisUntilFinished) {
  	    	 long durationSeconds = millisUntilFinished / 1000;
  	    	 tvTreepRepsonseTimeRemaining.setText("Temps restant pour confirmer :\n" + String.format("%02d min %02d sec", (durationSeconds % 3600) / 60, (durationSeconds % 60)));
  	     }

  	     public void onFinish() {
  	    	 tvTreepRepsonseTimeRemaining.setText("La proposition n'est plus disponible");
  	    	 isTreepResponseOutDated = true;
  	     }
  	  }.start();
       
       getActivity().setProgressBarIndeterminateVisibility(false);
        
        return rootView;
    }

	static class DialogConfirmUserOrder extends Dialog implements
    android.view.View.OnClickListener {
	
		 public Activity activity;
		 public Dialog d;
		 public Button confirmButton;
		 public Button cancelButton;
		 public TextView tvDriverCheckoutPrice;
		 String treepId;
		 String distanceTime;
		 String price;
		 String treeperId;
		  
		  public DialogConfirmUserOrder(Activity activity, String treepId, String treeperId, String distanceTime, String price) {
		    super(activity);
		    this.activity=activity;
			this.treepId=treepId;
			this.treeperId=treeperId;
			this.distanceTime=distanceTime;
			this.price=price;
		  }
		
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    setContentView(R.layout.dialog_driverresponseconfirm);
		    confirmButton = (Button) findViewById(R.id.confirmButton);
		    confirmButton.setOnClickListener(this);
		    cancelButton = (Button) findViewById(R.id.cancelButton);
		    cancelButton.setOnClickListener(this);
		    tvDriverCheckoutPrice = (TextView) findViewById(R.id.tvDriverCheckoutPrice);
		    tvDriverCheckoutPrice.setText(bundle.get(MainActivity.KEY_PRICE) + "€");

		  }
		
		  @Override
		  public void onClick(View v) {
		    switch (v.getId()) {
		    case R.id.confirmButton:
		    	//MainActivity.displayToast("treepid = " +treepId + " distancetime = " + distanceTime + " price = " + price);
		    	dismiss();
	    		SetTreepDriverResponseConfirmFromXML setTreepDriverResponseConfirmFromXML = new SetTreepDriverResponseConfirmFromXML(activity, treepId, treeperId, distanceTime, price);
	    		setTreepDriverResponseConfirmFromXML.execute();
		    break;
		    case R.id.cancelButton:
		    	dismiss();
		    break;
		    default:
		      break;
		    }
		  }
	}
}