package com.treep.fr;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.treep.fr.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager.BadTokenException;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

 
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class TreepDriverResponseListFragment extends Fragment {
     
	
	private ListView list;
    private View rootView;
    private ArrayList<HashMap<String, String>> alTreepDriverResponseList = new ArrayList<HashMap<String, String>>() ;
    private TreepDriverResponseAdapter adapter;
    private Button cancelOrderButton;
     
  
	@SuppressWarnings("unchecked")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
		rootView = inflater.inflate(R.layout.fragment_treepdriverresponselist, container, false);	
		Bundle bundle = getArguments();
		MainActivity.appQuit = true;
		
		cancelOrderButton = (Button) rootView.findViewById(R.id.cancelOrderButton); 	  
		
		cancelOrderButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(ApplicationContextProvider.getContext(), MainActivity.class);
	    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	    		ApplicationContextProvider.getContext().startActivity(i);
			}
				
			});
		
		alTreepDriverResponseList = (ArrayList<HashMap<String, String>>) bundle.getSerializable("alTreepDriverResponseList");
		
		 //Bundle TreepResponseList = getArguments();
    	
    	
    	adapter = new TreepDriverResponseAdapter(this, alTreepDriverResponseList);
        
    	list=(ListView)rootView.findViewById(R.id.listresponse);
    	
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
           HashMap<String, String> map = alTreepDriverResponseList.get(position); 
           
           data.putString(MainActivity.KEY_TREEPID,map.get(MainActivity.KEY_TREEPID));
           data.putString(MainActivity.KEY_USERID,map.get(MainActivity.KEY_USERID));
           data.putString(MainActivity.KEY_FIRSTNAME,map.get(MainActivity.KEY_FIRSTNAME));
           data.putString(MainActivity.KEY_LASTNAME,map.get(MainActivity.KEY_LASTNAME));
           data.putString(MainActivity.KEY_LATITUDE,map.get(MainActivity.KEY_LATITUDE));
           data.putString(MainActivity.KEY_LONGITUDE,map.get(MainActivity.KEY_LONGITUDE));
           data.putString(MainActivity.KEY_NBTREEP,map.get(MainActivity.KEY_NBTREEP));
           data.putString(MainActivity.KEY_DISTANCETIME,map.get(MainActivity.KEY_DISTANCETIME));
           data.putString(MainActivity.KEY_TREEPRESPONSETIMEREMAINING,map.get(MainActivity.KEY_TREEPRESPONSETIMEREMAINING));
           data.putString(MainActivity.KEY_RATING,map.get(MainActivity.KEY_RATING));
           data.putString(MainActivity.KEY_PRICE,map.get(MainActivity.KEY_PRICE));
           data.putString(MainActivity.KEY_ADDRESSDEP,map.get(MainActivity.KEY_ADDRESSDEP));
           data.putString(MainActivity.KEY_ADDRESSDEST,map.get(MainActivity.KEY_ADDRESSDEST));
           data.putString(MainActivity.KEY_PROFILEPIC_URL,map.get(MainActivity.KEY_PROFILEPIC_URL));
           
           
           Fragment fragment = new TreepDriverResponseUserProfileFragment();
           fragment.setArguments(data);
          
           FragmentManager fragmentManager = getActivity().getFragmentManager();
           fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_righttoleft,0,0).replace(R.id.frame_container, fragment).addToBackStack("treepdriverresponselist").commit();
            //Toast.makeText( ApplicationContextProvider.getContext(), "PB DE CONNECTION" , Toast.LENGTH_SHORT).show();
	            
            }
        });
	
    	return rootView;
		
    }
    
    
 
}