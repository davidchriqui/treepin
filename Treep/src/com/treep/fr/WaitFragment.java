package com.treep.fr;

import com.treep.fr.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WaitFragment extends Fragment{

	
	private static TextView tvListEmpty;
	private static String strListEmpty;
	
	

	public WaitFragment() {}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_wait, container, false);
		
		tvListEmpty = (TextView)rootView.findViewById(R.id.listEmpty);
		
		strListEmpty="Chargement...\nVeuillez patienter";
		
		tvListEmpty.setText(strListEmpty);
		
		
		
		return rootView;
		
		
		
	}




	public TextView getTvListEmpty() {
		return tvListEmpty;
	}




	public void setTvListEmpty(TextView tvListEmpty) {
		WaitFragment.tvListEmpty = tvListEmpty;
	}




	public String getStrListEmpty() {
		return strListEmpty;
	}




	public void setStrListEmpty(String strListEmpty) {
		WaitFragment.strListEmpty = strListEmpty;
	}


	
	
	
	
}
