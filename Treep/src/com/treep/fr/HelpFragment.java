package com.treep.fr;

import com.google.android.gms.location.LocationListener;
import com.treep.fr.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HelpFragment extends Fragment {

	  
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
		
		
			View v = inflater.inflate(R.layout.fragment_help, container, false);
			
			
			//infobanner.setText(infobannertxt.toString());
			getActivity().setProgressBarIndeterminateVisibility(false); 
			return v;
			
	}
	
}