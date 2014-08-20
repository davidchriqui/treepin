package com.treep.fr;

import java.util.ArrayList;
import java.util.HashMap;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager.BadTokenException;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

 
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class TreepResponseListFragment extends Fragment {
     
	
	private ListView list;
    private View rootView;
    private String treepId;
    private ArrayList<HashMap<String, String>> alTreepResponseList = new ArrayList<HashMap<String, String>>() ;
    private TreepResponseAdapter adapter;
    private Button cancelOrderButton;
  
    
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
		rootView = inflater.inflate(R.layout.fragment_treepresponselist, container, false);	
		Bundle bundle = getArguments();
		
		treepId = (String) bundle.getSerializable("treepid");
		cancelOrderButton = (Button) rootView.findViewById(R.id.cancelOrderButton); 	  
		
		cancelOrderButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				DialogTreeperCancel dialogTreeperCancel = new DialogTreeperCancel(getActivity(), treepId);
				try{
						dialogTreeperCancel.show();
			    } 
				catch (BadTokenException e){
					
			    }
			}
				
			});
		
		alTreepResponseList = (ArrayList<HashMap<String, String>>) bundle.getSerializable("alTreepResponseList");
		
		 //Bundle TreepResponseList = getArguments();
		MainActivity.appQuit = true;
    	
    	adapter = new TreepResponseAdapter(this, alTreepResponseList);
        
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
           HashMap<String, String> map = alTreepResponseList.get(position);          
           data.putString(MainActivity.KEY_RESPONSEID,map.get(MainActivity.KEY_RESPONSEID));
           data.putString(MainActivity.KEY_TREEPID,map.get(MainActivity.KEY_TREEPID));
           data.putString(MainActivity.KEY_USERID,map.get(MainActivity.KEY_USERID));
           data.putString(MainActivity.KEY_LATITUDE,map.get(MainActivity.KEY_LATITUDE));
           data.putString(MainActivity.KEY_LONGITUDE,map.get(MainActivity.KEY_LONGITUDE));
           data.putString(MainActivity.KEY_FIRSTNAME,map.get(MainActivity.KEY_FIRSTNAME));
           data.putString(MainActivity.KEY_LASTNAME,map.get(MainActivity.KEY_LASTNAME));
           data.putString(MainActivity.KEY_CARMODEL,map.get(MainActivity.KEY_CARMODEL));
           data.putString(MainActivity.KEY_NBSEAT,map.get(MainActivity.KEY_NBSEAT));
           data.putString(MainActivity.KEY_RATING,map.get(MainActivity.KEY_RATING));
           data.putString(MainActivity.KEY_NBTREEP,map.get(MainActivity.KEY_NBTREEP));
           data.putString(MainActivity.KEY_PRICE,map.get(MainActivity.KEY_PRICE));
           data.putString(MainActivity.KEY_DISTANCETIME,map.get(MainActivity.KEY_DISTANCETIME));
           data.putString(MainActivity.KEY_TREEPRESPONSETIMEREMAINING,map.get(MainActivity.KEY_TREEPRESPONSETIMEREMAINING));
           data.putString(MainActivity.KEY_ADDRESSDEP,map.get(MainActivity.KEY_ADDRESSDEP));
           data.putString(MainActivity.KEY_ADDRESSDEST,map.get(MainActivity.KEY_ADDRESSDEST));
           data.putString(MainActivity.KEY_CARPIC_URL,map.get(MainActivity.KEY_CARPIC_URL));
           
           
           Fragment fragment = new TreepResponseDriverProfileFragment();
           fragment.setArguments(data);
          
           FragmentManager fragmentManager = getActivity().getFragmentManager();
           fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_righttoleft,0,0).replace(R.id.frame_container, fragment).addToBackStack("treepresponselist").commit();
            //Toast.makeText( ApplicationContextProvider.getContext(), "PB DE CONNECTION" , Toast.LENGTH_SHORT).show();
	            
            }
        });
        
        
    	return rootView;
		
    }
    
	private class DialogTreeperCancel extends Dialog implements
    android.view.View.OnClickListener {
	
		  public Activity activity;
		  public Button confirmButton;
		  public Button cancelButton;
		  public String treepId;
		
		  public DialogTreeperCancel(Activity activity, String treepId) {
		    super(activity);
		    this.activity = activity;
		    this.treepId=treepId;
		  }
		
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    setContentView(R.layout.dialog_treepercancel);
		    confirmButton = (Button) findViewById(R.id.confirmButton);
		    confirmButton.setOnClickListener(this);
		    cancelButton = (Button) findViewById(R.id.cancelButton);
		    cancelButton.setOnClickListener(this);
		     

		  }
		
		  @Override
		  public void onClick(View v) {
		    switch (v.getId()) {
		    case R.id.confirmButton:
		    	DialogTreeperCancelConfirm dialogTreeperCancelConfirm = new DialogTreeperCancelConfirm(activity, treepId);
		    	dialogTreeperCancelConfirm.show();
		    break;
		    case R.id.cancelButton:
		    	dismiss();
		    break;
		    default:
		      break;
		    }
		    dismiss();
		  }
	}
 
 private class DialogTreeperCancelConfirm extends Dialog implements
    android.view.View.OnClickListener {
	
		  public Activity activity;
		  public Dialog d;
		  public Button confirmButton;
		  public Button cancelButton;
		  public String treepId;
		
		  public DialogTreeperCancelConfirm(Activity activity, String treepId) {
		    super(activity);
		    // TODO Auto-generated constructor stub
		    this.activity = activity;
		    this.treepId=treepId;
		  }
		
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    setContentView(R.layout.dialog_treepercancelconfirm);
		    confirmButton = (Button) findViewById(R.id.confirmButton);
		    confirmButton.setOnClickListener(this);
		    cancelButton = (Button) findViewById(R.id.cancelButton);
		    cancelButton.setOnClickListener(this);
		     

		  }
		
		  @Override
		  public void onClick(View v) {
		    switch (v.getId()) {
		    case R.id.confirmButton:
		    	if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
		        	
					MainActivity.displayToast(R.string.httpTimeOut);
		    	}
		    	else{
		    		SetTreepCancelFromTreeperFromXML setTreepCancelFromTreeperFromXML = new SetTreepCancelFromTreeperFromXML(activity, treepId);
		    		setTreepCancelFromTreeperFromXML.execute();
	          	  	dismiss();
		    	}
		    break;
		    case R.id.cancelButton:
		    	dismiss();
		    break;
		    default:
		      break;
		    }
		    dismiss();
		  }
	}
 
}