package com.treep.fr;

import com.treep.fr.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class EmptyList extends Fragment {


	private TextView listEmpty;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		
			View v = inflater.inflate(R.layout.fragment_listempty, container, false);
			listEmpty = (TextView) v.findViewById(R.id.listEmpty);
			listEmpty.setText("Vous n'avez effectué aucun treep jusqu'à présent");
			
			getActivity().setProgressBarIndeterminateVisibility(false);
	 
			return v;
				
	}
	        
				
	
		
		
	
	

	

	
	

 
}