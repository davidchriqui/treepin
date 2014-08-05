package com.treep.fr;

import java.util.Random;

import com.treep.fr.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;



public class IceBreakerFragment extends Fragment {


	private TextView tvIceBreaker;
	private Button pushButton;
	private String[] iceBreakerString;
	private int randomNumber;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		
			View rootView = inflater.inflate(R.layout.fragment_icebreaker, container, false);
			
			tvIceBreaker = (TextView) rootView.findViewById(R.id.tvIceBreaker);
			iceBreakerString = getResources().getStringArray(R.array.icebreaker_items);
			
			pushButton = (Button)rootView.findViewById(R.id.pushButton);
			
			OnClickListener clickListenerPushButton = new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
			    	Random r = new Random();
			    	randomNumber = r.nextInt(iceBreakerString.length);
			    	tvIceBreaker.setText("" +iceBreakerString[randomNumber]);
			    	tvIceBreaker.setGravity(1);
			    }
			  };
			
			  pushButton.setOnClickListener(clickListenerPushButton);
			
			
			
			getActivity().setProgressBarIndeterminateVisibility(false);
	 
			return rootView;
				
	}
	        
				
	
		
		
	
	

	

	
	

 
}