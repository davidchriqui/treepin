package com.treep.fr;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.treep.fr.R;

class DialogTimeoutLogin extends Dialog implements
	    android.view.View.OnClickListener {
		
		  public Activity activity;
		  public Button okButton;
		  public Button cancelButton;
		  public TextView tvModePink1;
		  private Typeface fb;
		  private Typeface bello;
		  
		  public DialogTimeoutLogin(Activity activity) {
		    super(activity);
		    // TODO Auto-generated constructor stub
		    this.activity = activity;
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
		    cancelButton = (Button)findViewById(R.id.cancelButton);
		    cancelButton.setOnClickListener(this);
		    cancelButton.setTypeface(fb);
		    
		  }
		
		  @SuppressLint("NewApi")
		@Override
		  public void onClick(View v) {
		    switch (v.getId()) {
		    case R.id.okButton:

		    	Intent intent = activity.getIntent();
		    	activity.finish();
		    	activity.startActivity(intent);
		    	break;
		    case R.id.cancelButton:
		    	dismiss();
		    	break;	
		    default:
		      break;
		    }
		    
		  }
		}