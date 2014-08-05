package com.treep.fr;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.parse.ParsePush;
import com.parse.SendCallback;

public class SendDocumentFragment extends Fragment{
	
	private Button saveButton;
	private Typeface fb;
	
	public static void hideSoftKeyboard(Activity activity) {
 	    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
 	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
 	}
	
	
	  
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_senddocument, container, false);
		fb = Typeface.createFromAsset(getActivity().getAssets(), "fb.ttf");
		
		saveButton = (Button)rootView.findViewById(R.id.saveButton);
		saveButton.setTypeface(fb);
		
		
		
		saveButton.setOnClickListener(new View.OnClickListener()
	      {
	         public void onClick(View v)
	         {
	        	Intent i = new Intent(ApplicationContextProvider.getContext(), MainActivity.class);
	    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	    		ApplicationContextProvider.getContext().startActivity(i);
	 	    	
	          }
	      });
	   

		return rootView;
		
		
	}
	

	
	
}
