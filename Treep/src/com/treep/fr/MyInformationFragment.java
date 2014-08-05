package com.treep.fr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.treep.fr.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MyInformationFragment extends Fragment{
	
	private Button saveButton;
	private EditText etPhoneNumber;
	private EditText etMail;
	private RadioGroup rgSexe;
	private HashMap<String, String> smsResponse = new HashMap<String, String>();
	private Typeface fb;
	private Boolean isMale = true;
	private DialogPleaseWait dialogPleaseWait;
	
	public static void hideSoftKeyboard(Activity activity) {
 	    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
 	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
 	}
	
	private OnCheckedChangeListener OnCheckedChangeListenerRg = new OnCheckedChangeListener() {
		   
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			
						    
		    switch (group.getCheckedRadioButtonId()) {
	        case R.id.rbMan:
	        	//Toast.makeText(getActivity(),"NOW", Toast.LENGTH_LONG).show();
	        	isMale =true;
	                     
	            break;
	        case R.id.rbWoman:
	        	//Toast.makeText(getActivity(),"NOT NOW", Toast.LENGTH_LONG).show();
	        	isMale =false;
	                     
	            break;
	        }
		    
			
		}
	  };
	  
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_myinformations, container, false);
		fb = Typeface.createFromAsset(getActivity().getAssets(), "fb.ttf");
		
		saveButton = (Button)rootView.findViewById(R.id.saveButton);
		saveButton.setTypeface(fb);
		etPhoneNumber = (EditText)rootView.findViewById(R.id.etPhoneNumber);
		
		dialogPleaseWait = new DialogPleaseWait(getActivity());
		
		etMail = (EditText)rootView.findViewById(R.id.etMail);
		if(LoginDisplayActivity.userEmail != null){
			etMail.setText(LoginDisplayActivity.userEmail);
		}
		
		saveButton.setOnClickListener(new View.OnClickListener()
	      {
	         public void onClick(View v)
	         {
	        	Editable phoneValue = etPhoneNumber.getText();
				Pattern phonePattern = Pattern.compile("^[0-9]{10}$");
				Matcher phoneMatcher = phonePattern.matcher(phoneValue);
				
				Editable mailValue = etMail.getText();
				Pattern mailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
				Matcher mailMatcher = mailPattern.matcher(mailValue);
				
				if (mailMatcher.matches()) {
					if (phoneMatcher.matches()) {
						 // SendSMS sendSMS = new SendSMS(getActivity());
						  //sendSMS.execute();
						hideSoftKeyboard(getActivity());
						SetMyInformationsFromXML setMyInformationsFromXML = new SetMyInformationsFromXML(getActivity(),etPhoneNumber.getText().toString(),etMail.getText().toString(), isMale);
			    		setMyInformationsFromXML.execute();
					}
					else{
						MainActivity.displayToast("Veuillez entrer votre numéro de téléphone mobile");
					 }
				}
				else{
					MainActivity.displayToast("Veuillez entrer une adresse mail valide");
				 }
	          }
	      });
	   

		rgSexe=(RadioGroup) rootView.findViewById(R.id.rgSexe);
		rgSexe.setOnCheckedChangeListener(OnCheckedChangeListenerRg);
		return rootView;
		
		
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
	
	private class SendSMS extends AsyncTask<Void, Integer, HashMap<String, String>> {

		private Activity activity;
		private ProgressDialog pd;
		
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
			String xml = parser.getXmlFromUrl("http://www.octopush-dm.com/api/sms/?user_login=david%40treep.fr&api_key=sVDI4wiHnR1wr3mMQ9vP5BqIlB2REVv2&sms_type=FR&sms_sender=Test+SMS&transactional=1&request_mode=simu&sms_recipients="+etPhoneNumber.getText().toString()+"&sms_text=Bienvenue+sur+treep.+Votre+code+didentification+est+:+"+keyCode(etPhoneNumber.getText().toString()));
			
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
					CustomDialogClass cdd = new CustomDialogClass(getActivity());
					cdd.show();
				}
				else{
					MainActivity.displayToast("Problème lors de l'envoi du SMS. Veuillez réessayer");
				}
				
				
			}
			
			dialogPleaseWait.dismiss();
			activity.setProgressBarIndeterminateVisibility(false);
		}
		
	}
	
	private class CustomDialogClass extends Dialog implements
    android.view.View.OnClickListener {
	
	  public Activity activity;
	  public Dialog d;
	  public Button confirmButton;
	  private EditText etKeyCode;
	
	  public CustomDialogClass(Activity activity) {
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
	
	  @SuppressLint("NewApi")
	@Override
	  public void onClick(View v) {
	    switch (v.getId()) {
	    case R.id.confirmButton:
	    	if (etKeyCode.getText().toString().equals(keyCode(etPhoneNumber.getText().toString()))) {
				  //Toast.makeText( ApplicationContextProvider.getContext(), "Numéro OK", Toast.LENGTH_SHORT).show();
	    		SetMyInformationsFromXML setMyInformationsFromXML = new SetMyInformationsFromXML(activity,etPhoneNumber.getText().toString(),etMail.getText().toString(), isMale);
	    		setMyInformationsFromXML.execute();
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
	
}
