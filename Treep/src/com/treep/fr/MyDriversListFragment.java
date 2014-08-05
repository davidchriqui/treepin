package com.treep.fr;


import java.util.ArrayList;
import java.util.HashMap;

import com.treep.fr.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

 
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class MyDriversListFragment extends Fragment {
	
	private ListView list;
	private MyDriversAdapter adapter;
	private ArrayList<HashMap<String, String>> alMyDriverList = new ArrayList<HashMap<String, String>>() ;
    private View rootView;

    
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_mydriverslist, container, false);
			MainActivity.appQuit = true;
			Bundle bundle = getArguments();
			alMyDriverList = (ArrayList<HashMap<String, String>>) bundle.getSerializable("alMyDriverList");
			
			adapter = new MyDriversAdapter(this, alMyDriverList);
	        
	    	list=(ListView)rootView.findViewById(R.id.list);
	    	
	        list.setAdapter(adapter);
	        
	        list.setOnItemClickListener(new OnItemClickListener() {
	            @SuppressLint("NewApi")
				@Override
	            public void onItemClick(AdapterView<?> parent, View view, int position,
	                    long id) {
	            	
	           getActivity().setProgressBarIndeterminateVisibility(true); 
	           //On Sauvegarde les données dans un objet "bundle"
	           Bundle data = new Bundle();   
	           
	           //On place les données liées à une ligne de la liste dans un objet HASHMAP
	           HashMap<String, String> map = alMyDriverList.get(position);          
	           data.putString(MainActivity.KEY_USERID,map.get(MainActivity.KEY_USERID));
	           data.putString(MainActivity.KEY_FIRSTNAME,map.get(MainActivity.KEY_FIRSTNAME));
	           data.putString(MainActivity.KEY_LASTNAME,map.get(MainActivity.KEY_LASTNAME));
	           data.putString(MainActivity.KEY_CARMODEL,map.get(MainActivity.KEY_CARMODEL));
	           data.putString(MainActivity.KEY_NBTREEP,map.get(MainActivity.KEY_NBTREEP));
	           data.putString(MainActivity.KEY_RATING,map.get(MainActivity.KEY_RATING));
	           data.putString(MainActivity.KEY_PROFILEPIC_URL,map.get(MainActivity.KEY_PROFILEPIC_URL));
	           data.putString(MainActivity.KEY_CARPIC_URL,map.get(MainActivity.KEY_CARPIC_URL));
	           
	           Fragment fragment = new MyDriverProfileFragment();
	           fragment.setArguments(data);
	          
	           FragmentManager fragmentManager = getActivity().getFragmentManager();
	           fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_righttoleft,0,0).replace(R.id.frame_container, fragment).addToBackStack("mydriverslist").commit();
	            //Toast.makeText( ApplicationContextProvider.getContext(), "PB DE CONNECTION" , Toast.LENGTH_SHORT).show();
		            
	           
	            }
	        });
			
		return rootView;	
		
    }
}
