package com.treep.fr;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class pushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            String message = extras != null ? extras.getString("com.parse.Data") : "";
            JSONObject jObject;
            try {
                    jObject = new JSONObject(message);
                    
                    
            } catch (JSONException e) {
                    e.printStackTrace();
            }
    }
 
 
}