package com.treep.fr;

import com.treep.fr.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class SplashScreen extends Activity{
	
	 private ImageView pintreepin;
	 private LinearLayout logoLayout;
	 private static int SPLASH_TIME_OUT = 1000;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	       
	        setContentView(R.layout.splashscreen);
	       
	        pintreepin = (ImageView) findViewById(R.id.pintreepin);
	        logoLayout = (LinearLayout) findViewById(R.id.logoLayout);
	        Animation animationPin = AnimationUtils.loadAnimation(this,R.anim.splashtranslatepin);
	        pintreepin.startAnimation(animationPin);
	        
	        animationPin.setAnimationListener(new Animation.AnimationListener(){
			    @Override
			    public void onAnimationStart(Animation arg0) {

			    }           
			    @Override
			    public void onAnimationRepeat(Animation arg0) {
			    }           
			    @Override
			    public void onAnimationEnd(Animation arg0) {
			    	Animation animationLogo = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.splashtranslatelogolayout);
			        logoLayout.startAnimation(animationLogo);
			        animationLogo.setAnimationListener(new Animation.AnimationListener(){
					    @Override
					    public void onAnimationStart(Animation arg0) {

					    }           
					    @Override
					    public void onAnimationRepeat(Animation arg0) {
					    }           
					    @Override
					    public void onAnimationEnd(Animation arg0) {
					    	Intent i = new Intent(SplashScreen.this, LoginDisplayActivity.class);
			                startActivity(i);
			                
			                //transition between splashscreen and login display activity
			                overridePendingTransition(R.anim.logindisplayactivityfadein,R.anim.splashfadeout);
			 
			                // close this activity
			                finish();
					    }
					});
			    }
			});
	    }
}
