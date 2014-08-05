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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
 
public class TreepResponseDriverProfileFragment extends Fragment {
     
	
	
	private ImageView ivResponseDriverProfilePic;
	private ImageView ivResponseDriverCarPic;
	private ImageLoader imageLoaderProfile;
	private ImageLoader imageLoaderCar;
	private TextView tvDriverUsername;
	private TextView tvResponseCarModel;
	private TextView tvResponseTreepNb;
	private ImageView ivResponseDriverRating;
	private TextView tvResponseDriverRating;
	private TextView tvResponseDistanceTime;
	private TextView tvReponsePrice;
	private TextView tvResponseAddressDep;
	private TextView tvResponseAddressDest;
	private Button confirmResponseButton;
	private TextView tvfbFriends;
	private TextView tvTreepRepsonseTimeRemaining;
	private LinearLayout inHorizontalScrollView;
	
	private String username;
	private String driverId;
	private static String treepId;
	private static String responseId;
	
	private Boolean isTreepResponseOutDated = false;
	private Button fbProfileButton;
	private static Bundle bundle;
	
    public TreepResponseDriverProfileFragment(){}
     
    
	@SuppressLint("SimpleDateFormat")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		 
		//On récupère les informations transmise par le fragment TreepResponseListFragment
		bundle = getArguments();
		MainActivity.appQuit = false;
			
        View rootView = inflater.inflate(R.layout.fragment_treepresponsedriverprofile, container, false);

        
        
      //initilize the custom typefont
      	Typeface bello = Typeface.createFromAsset(getActivity().getAssets(), "bello.ttf");
      	Typeface fb = Typeface.createFromAsset(getActivity().getAssets(), "fb.ttf");


      	
  		ivResponseDriverProfilePic = (ImageView) rootView.findViewById(R.id.ivResponseDriverProfilePic);
  		ivResponseDriverCarPic = (ImageView) rootView.findViewById(R.id.ivResponseDriverCarPic);
  		
  		imageLoaderProfile = new ImageLoader(ApplicationContextProvider.getContext());
  		imageLoaderProfile.DisplayImage(MainActivity.urlProfilePic(bundle.getString(MainActivity.KEY_USERID)),ivResponseDriverProfilePic);
  		
  		imageLoaderCar = new ImageLoader(ApplicationContextProvider.getContext());
  		imageLoaderCar.DisplayImage(bundle.getString(MainActivity.KEY_CARPIC_URL),ivResponseDriverCarPic);
  		
        tvDriverUsername=(TextView)rootView.findViewById(R.id.tvResponseDriverUsername);
        username = bundle.getString(MainActivity.KEY_FIRSTNAME) + " " + bundle.getString(MainActivity.KEY_LASTNAME).charAt(0) + ".";
        
        tvDriverUsername.setText(username);
        
        tvResponseCarModel = (TextView)rootView.findViewById(R.id.tvResponseCarModel);
        tvResponseCarModel.setText(bundle.getString(MainActivity.KEY_CARMODEL)+" " + bundle.get(MainActivity.KEY_NBSEAT) +" place(s) dispo.");
        
        tvResponseTreepNb = (TextView)rootView.findViewById(R.id.tvResponseTreepNb);
        tvResponseTreepNb.setText(bundle.getString(MainActivity.KEY_NBTREEP) + " treeps");
        
        ivResponseDriverRating = (ImageView) rootView.findViewById(R.id.ivResponseDriverRating);
       
       
       
       switch (Integer.parseInt(bundle.getString(MainActivity.KEY_RATING))) {
       case 0:
    	   ivResponseDriverRating.setImageResource(R.drawable.rating1);    
           break;
       case 1:
    	   ivResponseDriverRating.setImageResource(R.drawable.rating1);
             
           break;
       case 2:
    	   ivResponseDriverRating.setImageResource(R.drawable.rating2);
             
           break;
       case 3:
    	   ivResponseDriverRating.setImageResource(R.drawable.rating3);
             
           break;
       case 4:
    	   ivResponseDriverRating.setImageResource(R.drawable.rating4);
             
           break;
       case 5:
    	   ivResponseDriverRating.setImageResource(R.drawable.rating5);
             
           break;
		
		}
       
   	
       tvResponseDriverRating = (TextView) rootView.findViewById(R.id.tvResponseDriverRating);
       tvResponseDriverRating.setTypeface(bello);
       
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
		    		DialogCheckout dialogCheckout = new DialogCheckout(getActivity());
		    		dialogCheckout.show();
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

	static class DialogCheckout extends Dialog implements
    android.view.View.OnClickListener {
	
		  public Activity activity;
		  public Dialog d;
		  public Button confirmButton;
		  public Button cancelButton;
		  public TextView tvCheckoutPrice;
		  public EditText etPromoCode;
		  
		  public DialogCheckout(Activity activity) {
		    super(activity);
		    // TODO Auto-generated constructor stub
		    this.activity = activity;
		  }
		
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    setContentView(R.layout.dialog_checkout);
		    confirmButton = (Button) findViewById(R.id.confirmButton);
		    confirmButton.setOnClickListener(this);
		    cancelButton = (Button) findViewById(R.id.cancelButton);
		    cancelButton.setOnClickListener(this);
		    tvCheckoutPrice = (TextView) findViewById(R.id.tvCheckoutPrice);
		    tvCheckoutPrice.setText(bundle.get(MainActivity.KEY_PRICE) + "€");
		    
		    etPromoCode = (EditText) findViewById(R.id.etPromoCode);

		  }
		
		  @Override
		  public void onClick(View v) {
		    switch (v.getId()) {
		    case R.id.confirmButton:
		    	if(etPromoCode.getText().toString().length() != 0){
		    		CheckoutPromoCodeVerifFromXML checkoutPromoCodeVerifFromXML = new CheckoutPromoCodeVerifFromXML(activity,bundle.getString(MainActivity.KEY_TREEPID), bundle.getString(MainActivity.KEY_RESPONSEID), bundle.getString(MainActivity.KEY_USERID), bundle.getString(MainActivity.KEY_PRICE),etPromoCode.getText().toString());
			    	checkoutPromoCodeVerifFromXML.execute();
			    	dismiss();
		    	}
		    	else{
		    		SetTreepResponseConfirmFromXML setTreepResponseConfirmFromXML = new SetTreepResponseConfirmFromXML(activity, bundle.getString(MainActivity.KEY_TREEPID), bundle.getString(MainActivity.KEY_RESPONSEID), bundle.getString(MainActivity.KEY_USERID), bundle.getString(MainActivity.KEY_PRICE));
		    		setTreepResponseConfirmFromXML.execute();
		    		dismiss();
		    	}
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