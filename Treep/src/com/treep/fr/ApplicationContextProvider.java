package com.treep.fr;

import android.app.Application;
import android.content.Context;
import android.net.ParseException;

import org.acra.*;
import org.acra.annotation.*;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;


@ReportsCrashes(
	    formKey = "", // This is required for backward compatibility but not used
	    formUri = "http://www.treep.fr/api/bugreport.php"
	)



public class ApplicationContextProvider extends Application {
	 
    /**
     * Keeps a reference of the application context
     */
    private static Context sContext;
 
    @Override
    public void onCreate() {
    	
	    	Parse.initialize(this, "Tlmd4VHQeJ8aLAY1iVigII1Z3x4eNTq4I1Ugv4aP", "5Mj7HGe2UhDpq6JC5OBW2kIEUNznoG74yXNPTXks");
	        
	        PushService.setDefaultPushCallback(this, MainActivity.class);
	     
        super.onCreate();
        ACRA.init(this);
        sContext = getApplicationContext();
        
        
 
    }
 
    /**
     * Returns the application context
     *
     * @return application context
     */
    public static Context getContext() {
        return sContext;
    }
 
}