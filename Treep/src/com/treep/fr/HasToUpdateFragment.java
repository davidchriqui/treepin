package com.treep.fr;

import com.google.android.gms.location.LocationListener;
import com.treep.fr.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;


public class HasToUpdateFragment extends Fragment {


	  private Button confirmButton;
	  private Button cancelButton;
	  
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
		
		
			View v = inflater.inflate(R.layout.dialog_hastoupdate, container, false);
			
			OnClickListener clickListenerButton = new View.OnClickListener() {
			    @SuppressLint("NewApi")
				@Override
				public void onClick(View v) {
			    	switch (v.getId()) {
				    case R.id.confirmButton:
				    	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.treep.fr"));
		                startActivity(intent);
				    break;
				    case R.id.cancelButton:
				    	getActivity().finish();
				    break;
				    default:
				      break;
				    }
			    }
		  };
			
			confirmButton = (Button) v.findViewById(R.id.confirmButton);
		    confirmButton.setOnClickListener(clickListenerButton);
		    cancelButton = (Button) v.findViewById(R.id.cancelButton);
		    cancelButton.setOnClickListener(clickListenerButton);
		     
			//infobanner.setText(infobannertxt.toString());
			getActivity().setProgressBarIndeterminateVisibility(false); 
			return v;
			
	}
	
}