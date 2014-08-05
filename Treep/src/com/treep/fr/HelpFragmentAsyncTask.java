package com.treep.fr;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.treep.fr.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Window;
import android.widget.Toast;

public class HelpFragmentAsyncTask extends AsyncTask<Void, Integer, Fragment> {

	private Activity activity;
	private Context context;
	ProgressDialog prog;
	
	
	public HelpFragmentAsyncTask(final Activity activity, final Context context){
		
		this.activity=activity;
		this.context=context;
	}
	
	
	//static Boolean asyncOk=false;
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		activity.setProgressBarIndeterminateVisibility(true);
		
		activity.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		 
		 prog = new ProgressDialog(context);
		 prog.setCanceledOnTouchOutside (false);
		 prog.setMessage("Chargement...Veuillez patienter");
		 prog.setIndeterminate(true);
		 prog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		 prog.show();
		//Toast.makeText(ApplicationContextProvider.getContext(), "Début de DriverPositionList", Toast.LENGTH_LONG).show();
		
	}

	@Override
	protected void onProgressUpdate(Integer... values){
		super.onProgressUpdate(values);
	}

	@Override
	protected Fragment doInBackground(Void... arg0) {
	
		MainActivity.fragment = new HelpFragment();

		testWait();
		if(MainActivity.fragment == null){
			
			activity.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			MainActivity.fragment = new IsNotAvailableFragment();
			return MainActivity.fragment;
		}
		else{
			return MainActivity.fragment;
		}
	}

	@Override
	protected void onPostExecute(Fragment  result) {
		//Toast.makeText(ApplicationContextProvider.getContext(), "Le traitement asynchrone MyDriverList est terminé", Toast.LENGTH_LONG).show();
		activity.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		FragmentManager fragmentManager = activity.getFragmentManager();
		prog.dismiss();
        fragmentManager.beginTransaction().replace(R.id.frame_container,result).commit();
		activity.setProgressBarIndeterminateVisibility(false);
		
	}

	
	public void testWait(){
	    final double INTERVAL = 1000000000;
	    long start = System.nanoTime();
	    long end=0;
	    do{
	        end = System.nanoTime();
	    }while(start + INTERVAL >= end);
	}
	
}
