package com.treep.fr;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.treep.fr.R;

class DialogTimeout extends Dialog implements
	    android.view.View.OnClickListener {
			public Activity activity;
			public Button okButton;
			public Button cancelButton;
			public TextView tvModePink1;
			private Typeface fb;
			private Typeface bello;
		  
		  public DialogTimeout(Activity activity) {
		    super(activity);
		    // TODO Auto-generated constructor stub
		    this.activity=activity;
		  }
		
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    setContentView(R.layout.dialog_timeout);
		    fb = Typeface.createFromAsset(activity.getAssets(), "fb.ttf");
		    
		    okButton = (Button)findViewById(R.id.okButton);
		    okButton.setOnClickListener(this);
		    okButton.setTypeface(fb);
		    
		  }
		
		  @SuppressLint("NewApi")
		@Override
	  public void onClick(View v) {
	    switch (v.getId()) {
		    case R.id.okButton:
		    	FragmentManager fragmentManager = activity.getFragmentManager();
       		 	fragmentManager.beginTransaction().remove(MainActivity.fragment).commit();
			    	switch (MainActivity.initposition) {
		                case 0:
			                	MainActivity.initposition = 0;
			    	    		activity.setProgressBarIndeterminateVisibility(true);
			    	    		Intent i = new Intent(ApplicationContextProvider.getContext(), MainActivity.class);
			    	    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			    	    		ApplicationContextProvider.getContext().startActivity(i);
			       		 break;
		                case 1 :
			                	activity.setProgressBarIndeterminateVisibility(true);
			        			MainActivity.fragment = new MyProfileFragment();
			       				fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container, MainActivity.fragment).commit();
			       				
			       		 break;
			       		 case 2 :
		        				activity.setProgressBarIndeterminateVisibility(true);
			           	        StoreMyDriverListFromXML storeMyDriverListFromXML = new StoreMyDriverListFromXML(activity);
			           	        storeMyDriverListFromXML.execute();
			       		 break;
			       		 case 3 :
			       			 activity.setProgressBarIndeterminateVisibility(true);
			           	        MainActivity.fragment = new HomeFragment();
			       				fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container, MainActivity.fragment).commit();
			        			
			       		 break;
			       		 case 4 :  
		                    fragmentManager.beginTransaction().replace(R.id.frame_container, new HelpFragment()).commit(); 
			        		activity.setProgressBarIndeterminateVisibility(false);
			        			
			       			 
			       		 break;
			       		 case 5 :
			       			activity.setProgressBarIndeterminateVisibility(true);
		           	        MainActivity.fragment = new HomeFragment();
		       				fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container, MainActivity.fragment).commit();
		        			
			       			 
			       		 break;
			       		default:
			  		    break;
			    	}
			    	dismiss();
		    case R.id.cancelButton:
		    	dismiss();
		    	break;
		    default:
		      break;
		      }
		  }
		 
}
		