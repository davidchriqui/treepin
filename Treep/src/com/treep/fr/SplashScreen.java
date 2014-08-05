package com.treep.fr;

import com.treep.fr.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class SplashScreen extends Activity{
	
	 private ImageView pintreepin;
	 private static int SPLASH_TIME_OUT = 1000;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	       
	        setContentView(R.layout.splashscreen);
	       
	        pintreepin = (ImageView) findViewById(R.id.pintreepin);
	        
	        Animation animationPin = AnimationUtils.loadAnimation(this,R.anim.splashtranslatepin);
	        pintreepin.startAnimation(animationPin);
	        
	        new Handler().postDelayed(new Runnable() {
	        	 
	            /*
	             * Showing splash screen with a timer. This will be useful when you
	             * want to show case your app logo / company
	             */
	 
	            @Override
	            public void run() {
	                // This method will be executed once the timer is over
	                // Start your app main activity
	                Intent i = new Intent(SplashScreen.this, LoginDisplayActivity.class);
	                startActivity(i);
	                
	                //transition between splashscreen and login display activity
	                overridePendingTransition(R.anim.logindisplayactivityfadein,R.anim.splashfadeout);
	 
	                // close this activity
	                finish();
	            }
	        }, SPLASH_TIME_OUT);
	    }
   
}
