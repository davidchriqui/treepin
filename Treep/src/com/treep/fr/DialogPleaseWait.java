package com.treep.fr;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.treep.fr.R;

class DialogPleaseWait extends Dialog {
		
		  public Activity activity;
		  public TextView tvModePink1;
		  private Typeface fb;
		  private ImageView ivRefresh;
		  
		  public DialogPleaseWait(Activity activity) {
		    super(activity);
		    // TODO Auto-generated constructor stub
		    this.activity = activity;
		  }
		
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    setContentView(R.layout.dialog_pleasewait);
		    fb = Typeface.createFromAsset(activity.getAssets(), "fb.ttf");
		    
		    ivRefresh = (ImageView) findViewById(R.id.ivRefresh);
	        Animation rotationCenter = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.rotation_center);
	        ivRefresh.startAnimation(rotationCenter);
		    
		    
		  }
		
		 
}
		