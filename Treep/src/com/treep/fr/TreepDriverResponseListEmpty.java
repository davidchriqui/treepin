package com.treep.fr;

import com.treep.fr.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager.BadTokenException;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;



public class TreepDriverResponseListEmpty extends Fragment {


	private TextView tvTreepDriverResponseListEmpty;
	private Button cancelOrderButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		
			View v = inflater.inflate(R.layout.fragment_treepdriverresponselistempty, container, false);
			
			tvTreepDriverResponseListEmpty = (TextView) v.findViewById(R.id.tvTreepDriverResponseListEmpty);
			

			Animation blinkAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.blink);
	        
			tvTreepDriverResponseListEmpty.startAnimation(blinkAnim);
			
			cancelOrderButton = (Button) v.findViewById(R.id.cancelOrderButton); 	  
			
			cancelOrderButton.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(ApplicationContextProvider.getContext(), MainActivity.class);
		    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    		ApplicationContextProvider.getContext().startActivity(i);
				}
					
				});
			
			getActivity().setProgressBarIndeterminateVisibility(false);
	 
			return v;
				
	}
	        
				
	
		
		
	
	

	

	
	

 
}