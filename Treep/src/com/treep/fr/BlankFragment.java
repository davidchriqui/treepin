package com.treep.fr;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class BlankFragment extends Fragment {

	private Button refreshBigButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_blank, container, false);
		
		refreshBigButton = (Button) v.findViewById(R.id.refreshBigButton);
		
		OnClickListener clickListenerRefreshButton = new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	Intent i = new Intent(getActivity(), MainActivity.class);
        		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	            startActivity(i);
		    }
		  };
		
		  refreshBigButton.setOnClickListener(clickListenerRefreshButton);
		
		
		return v;
	}
}