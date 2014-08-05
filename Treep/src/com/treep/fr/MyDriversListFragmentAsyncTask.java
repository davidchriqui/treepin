package com.treep.fr;

import com.treep.fr.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class MyDriversListFragmentAsyncTask extends AsyncTask<Void, Integer, Fragment> {

	private Activity activity;
	private Context context;
	private Fragment fragment;
	
	
	public MyDriversListFragmentAsyncTask(final Activity activity,final Context context){
		
		this.activity=activity;
		this.context=context;
	}
	
	
	//static Boolean asyncOk=false;
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		activity.setProgressBarIndeterminateVisibility(true);
	
		//Toast.makeText(ApplicationContextProvider.getContext(), "Début de DriverPositionList", Toast.LENGTH_LONG).show();
		
	}

	@Override
	protected void onProgressUpdate(Integer... values){
		super.onProgressUpdate(values);
	}

	@Override
	protected Fragment doInBackground(Void... arg0) {
	
		fragment = new MyDriversListFragment();
		
		if(fragment == null){
			
			activity.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			fragment = new IsNotAvailableFragment();
			return fragment;
		}
		else{
			return fragment;
		}
	}

	@SuppressLint("NewApi")
	@Override
	protected void onPostExecute(Fragment  result) {
		//Toast.makeText(ApplicationContextProvider.getContext(), "Le traitement asynchrone MyDriverList est terminé", Toast.LENGTH_LONG).show();
		
		FragmentManager fragmentManager = activity.getFragmentManager();
		fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container,result).commit();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        activity.setProgressBarIndeterminateVisibility(false);
		
	}

}
