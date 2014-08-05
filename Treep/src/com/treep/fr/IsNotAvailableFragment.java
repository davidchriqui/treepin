package com.treep.fr;

import com.treep.fr.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class IsNotAvailableFragment extends Fragment{

	
	private static TextView tvListEmpty;
	private static String strListEmpty;
	
	

	public IsNotAvailableFragment() {}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_listempty, container, false);
		
		tvListEmpty = (TextView)rootView.findViewById(R.id.listEmpty);
		
		strListEmpty="Vous n'êtes pas connecté à internet";
		
		tvListEmpty.setText(strListEmpty);
		
		
		
		return rootView;
		
		
		
	}




	public TextView getTvListEmpty() {
		return tvListEmpty;
	}




	public void setTvListEmpty(TextView tvListEmpty) {
		IsNotAvailableFragment.tvListEmpty = tvListEmpty;
	}




	public String getStrListEmpty() {
		return strListEmpty;
	}




	public void setStrListEmpty(String strListEmpty) {
		IsNotAvailableFragment.strListEmpty = strListEmpty;
	}


	
	
	
	
}
