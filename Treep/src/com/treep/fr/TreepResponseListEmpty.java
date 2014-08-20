package com.treep.fr;

import com.treep.fr.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager.BadTokenException;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;



public class TreepResponseListEmpty extends Fragment {


	private TextView etTreepResponseListEmpty;
	private Button cancelOrderButton;
    private String treepId;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		
			View v = inflater.inflate(R.layout.fragment_treepresponselistempty, container, false);
			
			Bundle bundle = getArguments();
			
			treepId = (String) bundle.getSerializable("treepid");
			
			etTreepResponseListEmpty = (TextView) v.findViewById(R.id.etTreepResponseListEmpty);
			
			Animation blinkAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.blink);
	        
			etTreepResponseListEmpty.startAnimation(blinkAnim);
			
			cancelOrderButton = (Button) v.findViewById(R.id.cancelOrderButton); 	  
			
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
			
			getActivity().setProgressBarIndeterminateVisibility(false);
	 
			return v;
				
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