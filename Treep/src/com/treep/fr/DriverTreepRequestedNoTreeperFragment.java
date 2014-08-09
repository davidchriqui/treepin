package com.treep.fr;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;









import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager.BadTokenException;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DriverTreepRequestedNoTreeperFragment extends Fragment {

	private Button cancelOrderButton;
	private TextView title;
	private ImageView refreshBig;
	
	 private OnClickListener clickListenerCancelButton = new View.OnClickListener() {
	    	
		    @Override
		    public void onClick(View v) {
		    	DialogDriverCancelConfirm dialogDriverCancelConfirm = new DialogDriverCancelConfirm(getActivity());
				try{
					dialogDriverCancelConfirm.show();
			    } 
				catch (BadTokenException e){
					
			    }
		    }
		  };
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_drivertreeprequestednotreeper, container, false);
		
		cancelOrderButton = (Button) v.findViewById(R.id.cancelOrderButton);
		title = (TextView) v.findViewById(R.id.title);

		
		cancelOrderButton.setOnClickListener(clickListenerCancelButton);

		refreshBig = (ImageView) v.findViewById(R.id.refreshBig);
		AnimationDrawable frameAnimation = (AnimationDrawable) refreshBig.getBackground();

		 // Start the animation (looped playback by default).
		 frameAnimation.start();
		 
		 Animation rotationCenter = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.rotation_center);
		 refreshBig.startAnimation(rotationCenter);
		
		return v;
	}
	
	private class DialogDriverCancelConfirm extends Dialog implements
    android.view.View.OnClickListener {
	
		  public Dialog d;
		  public Button confirmButton;
		  public Button cancelButton;
		  private Activity activity;
		
		  public DialogDriverCancelConfirm(Activity activity) {
		    super(activity);
		    // TODO Auto-generated constructor stub
		    this.activity = activity;
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
		    		SetDriverTreepRequestedcancel setDriverTreepRequestedcancel = new SetDriverTreepRequestedcancel(getActivity());
			    	setDriverTreepRequestedcancel.execute();
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
  
	
	private class SetDriverTreepRequestedcancel extends AsyncTask<Void, Integer, HashMap<String, String>> {
		
		private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		private HashMap<String,String> mapErrCode = new HashMap<String,String>();
		private Activity activity;
		
		public SetDriverTreepRequestedcancel(Activity activity){
			this.activity=activity;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			activity.setProgressBarIndeterminateVisibility(true);
			nameValuePairs.add(new BasicNameValuePair("userid", LoginDisplayActivity.userId));
			
		}

		@Override
		protected void onProgressUpdate(Integer... values){
			super.onProgressUpdate(values);
			//Toast.makeText(ApplicationContextProvider.getContext(), "traitement asynchrone", Toast.LENGTH_LONG).show();
			
		}

		@Override
		protected HashMap<String, String> doInBackground(Void... arg0) {

			HashMap<String,String> mapTimeout = new HashMap<String,String>();
			mapTimeout.put(MainActivity.KEY_TIMEOUT, "1");
			
			if (android.os.Build.VERSION.SDK_INT > 9) 
	    	{ StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }
		
	    	XMLParser parser = new XMLParser(nameValuePairs);
			String xml = parser.getXmlFromUrl(MainActivity.URL_SETTREEPDRIVERREQUESTEDCANCELFROMDRIVER);
			if(xml == null){
				mapErrCode = null;
			}
			else{
				if(xml == "timeout"){
					
					mapErrCode.put(MainActivity.KEY_TIMEOUT, "1");	
				}
				else{
					
					Document doc = null;
					try{
						doc = parser.getDomElement(xml); // getting DOM element
					}
					catch(DOMException e){
						
					}
					
					if(doc == null){
						mapErrCode = null;
					}
					else{
						NodeList nl_xml = doc.getElementsByTagName(MainActivity.KEY_XML);
						// looping through all xml nodes <KEY_USER>
						for (int i = 0; i < nl_xml.getLength(); i++) {
							// creating new HashMap
							
							Element e = (Element) nl_xml.item(i);
							// adding each child node to HashMap key => value
							mapErrCode.put(MainActivity.KEY_ERRCODE, parser.getValue(e, MainActivity.KEY_ERRCODE));
						}
					}
				}
			}
			
			return mapErrCode;
		}

		@SuppressLint("NewApi")
		protected void onPostExecute(HashMap<String, String>  result) {
			
			if(result == null || result.containsKey(MainActivity.KEY_TIMEOUT)){
	    		
				MainActivity.displayToast(R.string.httpTimeOut);
				activity.setProgressBarIndeterminateVisibility(false);
				
			}
			else{	
				if(result.get(MainActivity.KEY_ERRCODE).equals("000")){
					Intent i = new Intent(ApplicationContextProvider.getContext(), MainActivity.class);
		    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		    		ApplicationContextProvider.getContext().startActivity(i);
				}
				else{
					MainActivity.displayToast("Veuillez r�essayer plus tard : "+result.get(MainActivity.KEY_ERRCODE));
				}
				activity.setProgressBarIndeterminateVisibility(false);
			}
		}
	}

}