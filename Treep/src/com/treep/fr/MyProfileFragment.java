package com.treep.fr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.BadTokenException;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
 
public class MyProfileFragment extends Fragment {
     
	private HashMap<String, String> mapUserInfo = new HashMap<String, String>();
	private TextView tvUsername;
	private ImageView ivProfilePic;
	private ImageView ivRating;
	private TextView tvTreepNb;
	private TextView tvRatingTitle;
	private Button bDisconnect;
	private Button bTel;
	private Button bMail;
	private ImageLoader imageLoaderProfilePic;
	private Button bBecomeDriver;
	private LinearLayout becomelayout;
	private DialogPleaseWait dialogPleaseWait;
	
	private String phoneNumber;
	private HashMap<String, String> smsResponse = new HashMap<String, String>();
	
	
    public MyProfileFragment(){}
     
    public void showSettingsAlert(){
		 DialogSettingInternet dialogSettingInternet = new DialogSettingInternet(getActivity());
		 dialogSettingInternet.show();
	    }
    
	@SuppressLint("NewApi")
	@SuppressWarnings("unchecked")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_myprofile, container, false);
        Typeface bello = Typeface.createFromAsset(getActivity().getAssets(), "bello.ttf");
        Typeface fb = Typeface.createFromAsset(getActivity().getAssets(), "fb.ttf");
        
        dialogPleaseWait = new DialogPleaseWait(getActivity());
        
        tvUsername=(TextView)rootView.findViewById(R.id.tvUsername);
        
        ivProfilePic = (ImageView) rootView.findViewById(R.id.ivProfilePic);
        
        tvRatingTitle = (TextView) rootView.findViewById(R.id.tvRatingTitle);
  		tvRatingTitle.setTypeface(bello);
    
  		tvTreepNb = (TextView) rootView.findViewById(R.id.tvTreepNb);
  		
        bTel = (Button)rootView.findViewById(R.id.bTel);
        
        bTel.setOnClickListener(new OnClickListener() {
            public void onClick(View view) { 
            
            	CustomDialogTel customDialogTel = new CustomDialogTel(getActivity());
            	customDialogTel.setCancelable(false);
            	customDialogTel.setCanceledOnTouchOutside(false);
            	customDialogTel.show();
            }
        });
        
        
        bMail = (Button)rootView.findViewById(R.id.bMail);
        
        bMail.setOnClickListener(new OnClickListener() {
            public void onClick(View view) { 
            
            	CustomDialogMail customDialogMail = new CustomDialogMail(getActivity());
            	customDialogMail.setCancelable(false);
            	customDialogMail.setCanceledOnTouchOutside(false);
            	customDialogMail.show();
            }
        });
        
        ivRating = (ImageView) rootView.findViewById(R.id.ivRating);
        
        bBecomeDriver = (Button)rootView.findViewById(R.id.bBecomeDriver);
        becomelayout = (LinearLayout)rootView.findViewById(R.id.becomelayout);
        bBecomeDriver.setOnClickListener(new OnClickListener() {
            public void onClick(View view) { 
            
            	getActivity().setProgressBarIndeterminateVisibility(true);
                SetDriverModeFromXML setDriverModeFromXML = new SetDriverModeFromXML(getActivity());
                setDriverModeFromXML.execute();
            }
        });
        
        
        bDisconnect = (Button)rootView.findViewById(R.id.bDisconnect);
        bDisconnect.setTypeface(fb);
        bDisconnect.setOnClickListener(new OnClickListener() {
            public void onClick(View view) { 
            
            	CustomDialogDisconnect customDialogDisconnect = new CustomDialogDisconnect(getActivity());
            	customDialogDisconnect.setCancelable(false);
            	customDialogDisconnect.setCanceledOnTouchOutside(false);
            	customDialogDisconnect.show();
            }
        });
        
        StoreMyProfileInfoFromXML storeMyProfileInfoFromXML = new StoreMyProfileInfoFromXML(getActivity());
        storeMyProfileInfoFromXML.execute();
        
        getActivity().setProgressBarIndeterminateVisibility(false);
        return rootView;
    }
	
	
	
	private class DialogSettingInternet extends Dialog implements
    android.view.View.OnClickListener {
	
		  public Activity activity;
		  public Dialog d;
		  public Button confirmButton;
		  public Button cancelButton;
		
		  public DialogSettingInternet(Activity activity) {
		    super(activity);
		    // TODO Auto-generated constructor stub
		    this.activity = activity;
		  }
		
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    setContentView(R.layout.dialog_settinginternet);
		    confirmButton = (Button) findViewById(R.id.confirmButton);
		    confirmButton.setOnClickListener(this);
		    cancelButton = (Button) findViewById(R.id.cancelButton);
		    cancelButton.setOnClickListener(this);
		     

		  }
		
		  @Override
		  public void onClick(View v) {
		    switch (v.getId()) {
		    case R.id.confirmButton:
		    	Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
		    break;
		    case R.id.cancelButton:
		    	dismiss();
		    break;
		    default:
		      break;
		    }
		  }
	}
	
	
	private class CustomDialogTel extends Dialog implements
    android.view.View.OnClickListener {
	
		  public Activity activity;
		  public Dialog d;
		  public Button confirmButton;
		  public Button cancelButton;
		  public EditText etTel;
		
		  public CustomDialogTel(Activity activity) {
		    super(activity);
		    // TODO Auto-generated constructor stub
		    this.activity = activity;
		  }
		
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    setContentView(R.layout.dialog_telmodif);
		    confirmButton = (Button) findViewById(R.id.confirmButton);
		    confirmButton.setOnClickListener(this);
		    cancelButton = (Button) findViewById(R.id.cancelButton);
		    cancelButton.setOnClickListener(this);
		    etTel = (EditText) findViewById(R.id.etTel);
		     

		  }
		
		  @Override
		  public void onClick(View v) {
		    switch (v.getId()) {
		    case R.id.confirmButton:
		    	phoneNumber = etTel.getText().toString();
		    	Pattern pattern = Pattern.compile("^[0-9]{10,13}$");
				Matcher matcher = pattern.matcher(etTel.getText().toString());
				if (matcher.matches()) {
					SendSMS sendSMS = new SendSMS(getActivity());
					sendSMS.execute();
				}
				else{
					MainActivity.displayToast("Veuillez entrer un numéro valide");
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
	
	private class CustomDialogMail extends Dialog implements
    android.view.View.OnClickListener {
	
		  public Activity activity;
		  public Dialog d;
		  public Button confirmButton;
		  public Button cancelButton;
		  public EditText etEmail;
		
		  public CustomDialogMail(Activity activity) {
		    super(activity);
		    // TODO Auto-generated constructor stub
		    this.activity = activity;
		  }
		
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    setContentView(R.layout.dialog_mailmodif);
		    confirmButton = (Button) findViewById(R.id.confirmButton);
		    confirmButton.setOnClickListener(this);
		    cancelButton = (Button) findViewById(R.id.cancelButton);
		    cancelButton.setOnClickListener(this);
		    etEmail = (EditText) findViewById(R.id.etEmail);
		     

		  }
		
		  @Override
		  public void onClick(View v) {
		    switch (v.getId()) {
		    case R.id.confirmButton:
		    	Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
				Matcher matcher = pattern.matcher(etEmail.getText().toString());
				if (matcher.matches()) {
					SetMyProfileNewMailFromXML setMyProfileNewMailFromXML = new SetMyProfileNewMailFromXML(getActivity(),etEmail.getText().toString());
					setMyProfileNewMailFromXML.execute();
				}
				else{
					MainActivity.displayToast("Veuillez entrer une adresse email valide");
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
	
	private class CustomDialogDisconnect extends Dialog implements
    android.view.View.OnClickListener {
	
		  public Activity activity;
		  public Dialog d;
		  public Button confirmButton;
		  public Button cancelButton;
		
		  public CustomDialogDisconnect(Activity activity) {
		    super(activity);
		    // TODO Auto-generated constructor stub
		    this.activity = activity;
		  }
		
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    setContentView(R.layout.dialog_disconnect);
		    confirmButton = (Button) findViewById(R.id.confirmButton);
		    confirmButton.setOnClickListener(this);
		    cancelButton = (Button) findViewById(R.id.cancelButton);
		    cancelButton.setOnClickListener(this);
		     

		  }
		
		  @Override
		  public void onClick(View v) {
		    switch (v.getId()) {
		    case R.id.confirmButton:
		    	LoginDisplayActivity.onClickLogout();
  	   			Intent i = new Intent(ApplicationContextProvider.getContext(), LoginDisplayActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
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
	
	private class SendSMS extends AsyncTask<Void, Integer, HashMap<String, String>> {

		private Activity activity;
		
		public SendSMS(Activity activity){
			this.activity=activity;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			activity.setProgressBarIndeterminateVisibility(true);
			if(dialogPleaseWait != null){
				dialogPleaseWait.show();
			}
			else{
				dialogPleaseWait = new DialogPleaseWait(getActivity());
				dialogPleaseWait.show();
			}
			//urlBuilder.append(url).append("&sms_recipients=").append(etPhoneNumber.getText().toString()).append("&sms_text=Bienvenue sur treep. Votre code d'identification est : ").append(keyCode(etPhoneNumber.getText().toString()));
			
		}
		
		
		@Override
		protected HashMap<String, String> doInBackground(Void... params) {

			XMLParser parser = new XMLParser();
			String xml = parser.getXmlFromUrl("http://www.octopush-dm.com/api/sms/?user_login=david%40treep.fr&api_key=sVDI4wiHnR1wr3mMQ9vP5BqIlB2REVv2&sms_type=FR&sms_sender=Test+SMS&transactional=1&request_mode=simu&sms_recipients="+phoneNumber+"&sms_text=Bienvenue+sur+treep.+Votre+code+didentification+est+:+"+keyCode(phoneNumber));
			
			if(xml == "timeout"){
				smsResponse.put(MainActivity.KEY_TIMEOUT, "1");
			}
			
			else{
				
		         // getting XML from URL
				Document doc = parser.getDomElement(xml); // getting DOM element
				
				if(doc == null){
					smsResponse = null;
				}
				else{
					NodeList nl_smsResponse = doc.getElementsByTagName("octopush");
					// looping through all xml nodes <KEY_USER>
					for (int i = 0; i < nl_smsResponse.getLength(); i++) {
						// creating new HashMap
						Element e = (Element) nl_smsResponse.item(i);
						// adding each child node to HashMap key => value
						smsResponse.put("error_code", parser.getValue(e, "error_code"));
					}
				}
			}
			return smsResponse;
		}
		
		protected void onPostExecute(HashMap<String,String>  result) {
						
			if(result == null || result.containsKey(MainActivity.KEY_TIMEOUT)){
				MainActivity.displayToast("Problème lors de l'envoi du SMS. Veuillez réessayer");
	    	}
			else{
				if(result.get("error_code").equals("000")){
					MainActivity.displayToast("Vous allez recevoir un SMS contenant un code de vérification");
					CustomDialogSMS cdd = new CustomDialogSMS(getActivity());
					cdd.setCancelable(false);
					cdd.setCanceledOnTouchOutside(false);
					try{
						cdd.show();
					}
					catch(BadTokenException e){
						
					}
				}
				else{
					MainActivity.displayToast("Problème lors de l'envoi du SMS. Veuillez réessayer");
				}
				
				
			}
			
			dialogPleaseWait.dismiss();
			activity.setProgressBarIndeterminateVisibility(false);
		}
		
	}
	
	
	private class CustomDialogSMS extends Dialog implements
    android.view.View.OnClickListener {
	
		  public Activity activity;
		  public Button confirmButton;
		  private EditText etKeyCode;
		
		  public CustomDialogSMS(Activity activity) {
		    super(activity);
		    // TODO Auto-generated constructor stub
		    this.activity = activity;
		  }
		
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    setContentView(R.layout.alertdialog_smsconfirm);
		    confirmButton = (Button) findViewById(R.id.confirmButton);
		    confirmButton.setOnClickListener(this);
		    etKeyCode = (EditText)findViewById(R.id.etKeyCode);
		
		  }
		
		  @Override
		  public void onClick(View v) {
		    switch (v.getId()) {
		    case R.id.confirmButton:
		    	if (etKeyCode.getText().toString().equals(keyCode(phoneNumber))) {
					  //Toast.makeText( ApplicationContextProvider.getContext(), "Numéro OK", Toast.LENGTH_SHORT).show();
		    		SetMyProfileNewPhoneFromXML setMyProfileNewPhoneFromXML = new SetMyProfileNewPhoneFromXML(getActivity(),phoneNumber);
		    		setMyProfileNewPhoneFromXML.execute();
				  }
				  else{
					  MainActivity.displayToast("Le code de vérification n'est pas correct. Veuillez réessayer.");
				  }
		      break;
		    default:
		      break;
		    }
		    dismiss();
		  }
	}
	
	public String keyCode(String phoneNumber){
		
		
		StringBuilder keyCodeBuilder = new StringBuilder();
		int keyCode1;
		int keyCode2;
		int keyCode3;
		int keyCode4;
		
		ArrayList<Character> phoneNumberCharacter = new ArrayList<Character>();
		ArrayList<String> phoneNumberString = new ArrayList<String>();
		ArrayList<Integer> phoneNumberInteger = new ArrayList<Integer>();
		
		for(int i = 0; i<phoneNumber.length(); i++){
			phoneNumberCharacter.add(phoneNumber.charAt(i));
			phoneNumberString.add(phoneNumberCharacter.get(i).toString());
			phoneNumberInteger.add(Integer.parseInt(phoneNumberString.get(i)));
			
		}
		
		keyCode1 = phoneNumberInteger.get(0)+phoneNumberInteger.get(1)+phoneNumberInteger.get(2);
		keyCode2 = phoneNumberInteger.get(3)+phoneNumberInteger.get(4);
		keyCode3 = phoneNumberInteger.get(5)+phoneNumberInteger.get(6);
		keyCode4 = +phoneNumberInteger.get(7)+phoneNumberInteger.get(8)+phoneNumberInteger.get(9);
		
		
		keyCodeBuilder.append(keyCode1).append(keyCode2).append(keyCode3).append(keyCode4);
		
		
		return keyCodeBuilder.toString();
		
	}
	
	private class StoreMyProfileInfoFromXML extends AsyncTask<Void, Integer, HashMap<String,String>> {
		
		private Activity activity;
		private HashMap<String, String> mapUserInfo = new HashMap<String, String>();
		private HashMap<String, String> mapErrCode = new HashMap<String, String>();
		
		public StoreMyProfileInfoFromXML(Activity activity){
			this.activity=activity;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(LoginDisplayActivity.userId == null || LoginDisplayActivity.userId == "" ){
				this.cancel(true);
				Intent i = new Intent(getActivity(), LoginDisplayActivity.class);
        		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	            startActivity(i);
			}
			else{
				activity.setProgressBarIndeterminateVisibility(true);
				if(dialogPleaseWait != null){
					dialogPleaseWait.show();
				}
				else{
					dialogPleaseWait = new DialogPleaseWait(getActivity());
					dialogPleaseWait.show();
				}
			}
		}

		@Override
		protected void onProgressUpdate(Integer... values){
			super.onProgressUpdate(values);
			
		}

		@Override
		protected HashMap<String,String> doInBackground(Void... arg0) {

			if (android.os.Build.VERSION.SDK_INT > 9) 
	    	{ StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }
		
			XMLParser parser = new XMLParser();
			String xml = parser.getXmlFromUrl(MainActivity.URL_USERINFO + "?userid=" + LoginDisplayActivity.userId + "&lat=" + MainActivity.myLatitude + "&lng=" + MainActivity.myLongitude);
			
			if(xml == "timeout"){
				mapUserInfo.put(MainActivity.KEY_TIMEOUT, "1");
			}
			else{
				// getting XML from URL
				Document doc = parser.getDomElement(xml); //getting DOM element
				
				if(doc == null){
					mapUserInfo = null;
				}
				else{
					NodeList nl_User = doc.getElementsByTagName(MainActivity.KEY_USERINFO);
					// looping through all xml nodes <KEY_USER>
					for (int i = 0; i < nl_User.getLength(); i++) {
						// creating new HashMap
						
						Element e = (Element) nl_User.item(i);
						// adding each child node to HashMap key => value
						mapUserInfo.put(MainActivity.KEY_FIRSTNAME, parser.getValue(e, MainActivity.KEY_FIRSTNAME));
						mapUserInfo.put(MainActivity.KEY_LASTNAME, parser.getValue(e, MainActivity.KEY_LASTNAME));
						mapUserInfo.put(MainActivity.KEY_RATING, parser.getValue(e, MainActivity.KEY_RATING));
						mapUserInfo.put(MainActivity.KEY_NBTREEP, parser.getValue(e, MainActivity.KEY_NBTREEP));
						mapUserInfo.put(MainActivity.KEY_USERMAIL, parser.getValue(e, MainActivity.KEY_USERMAIL));
						mapUserInfo.put(MainActivity.KEY_USERTEL, parser.getValue(e, MainActivity.KEY_USERTEL));
						mapUserInfo.put(MainActivity.KEY_USERSEXE, parser.getValue(e, MainActivity.KEY_USERSEXE));
						mapUserInfo.put(MainActivity.KEY_ISTREEPORDERED, parser.getValue(e, MainActivity.KEY_ISTREEPORDERED));
						mapUserInfo.put(MainActivity.KEY_ISDRIVER, parser.getValue(e, MainActivity.KEY_ISDRIVER));
						mapUserInfo.put(MainActivity.KEY_BECOMEDRIVER, parser.getValue(e, MainActivity.KEY_BECOMEDRIVER));
						mapUserInfo.put(MainActivity.KEY_DRIVERMODE, parser.getValue(e, MainActivity.KEY_DRIVERMODE));
					}
					
					NodeList nl_ErrCode = doc.getElementsByTagName(MainActivity.KEY_USERINFO);
					// looping through all xml nodes <KEY_USER>
					for (int i = 0; i < nl_ErrCode.getLength(); i++) {
						// creating new HashMap
						Element e = (Element) nl_User.item(i);
						// adding each child node to HashMap key => value
						mapErrCode.put(MainActivity.KEY_ERRCODE, parser.getValue(e, MainActivity.KEY_ERRCODE));
					}
				}
			}
			return mapUserInfo;
		}

		
		@SuppressLint("NewApi")
		protected void onPostExecute(HashMap<String,String> result) {
			
			
			if(result == null || result.containsKey(MainActivity.KEY_TIMEOUT)){
				if(mapErrCode == null){
					DialogTimeout dialogTimeout = new DialogTimeout(activity);
					dialogTimeout.show();
					dialogPleaseWait.dismiss();
		            activity.setProgressBarIndeterminateVisibility(false);
				}
				else{
					dialogPleaseWait.dismiss();
					activity.setProgressBarIndeterminateVisibility(false);
					MainActivity.displayToast("Veuillez réessayer :  " + mapErrCode.get(MainActivity.KEY_ERRCODE));
				}
			}
			else{
				if(result.get(MainActivity.KEY_USERTEL).length() > 0){
					tvUsername.setText(result.get(MainActivity.KEY_FIRSTNAME) + " " + result.get(MainActivity.KEY_LASTNAME));
					bMail.setText(result.get(MainActivity.KEY_USERMAIL));
					bTel.setText(result.get(MainActivity.KEY_USERTEL));
					imageLoaderProfilePic = new ImageLoader(ApplicationContextProvider.getContext());
			        imageLoaderProfilePic.DisplayImage(MainActivity.urlProfilePic(LoginDisplayActivity.userId),ivProfilePic);
			        tvTreepNb.setText(result.get(MainActivity.KEY_NBTREEP) + " treeps");
			        if(Boolean.parseBoolean(result.get(MainActivity.KEY_ISDRIVER))){
			        	becomelayout.setVisibility(View.GONE);
			        }
			        else{
			        	if(Boolean.parseBoolean(result.get(MainActivity.KEY_BECOMEDRIVER))){
			        		becomelayout.setVisibility(View.GONE);
			        	}
			        	else{
			        		
			        	}
			        }
			        switch (Integer.parseInt(result.get(MainActivity.KEY_RATING))) {
				        case 0:
				        	ivRating.setImageResource(R.drawable.rating1);  
				            break;
				        case 1:
				        	ivRating.setImageResource(R.drawable.rating1);
				            break;
				        case 2:
				        	ivRating.setImageResource(R.drawable.rating2);
				            break;
				        case 3:
				        	ivRating.setImageResource(R.drawable.rating3);
				            break;
				        case 4:
				        	ivRating.setImageResource(R.drawable.rating4);
				            break;
				        case 5:
				        	ivRating.setImageResource(R.drawable.rating5);
				            break;
			 		}
				}
				else{
					LaunchMyInformationFragment();
				}
				dialogPleaseWait.dismiss();
				activity.setProgressBarIndeterminateVisibility(false);
			}
		}
		@SuppressLint("NewApi")
		private void LaunchMyInformationFragment(){
			MainActivity.fragment = new MyInformationFragment();
			
			FragmentManager fragmentManager = activity.getFragmentManager();
	        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container,MainActivity.fragment ).commit();
		}
	}
	
}