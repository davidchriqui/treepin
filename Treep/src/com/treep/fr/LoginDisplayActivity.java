package com.treep.fr;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.BadTokenException;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;


public class LoginDisplayActivity extends Activity {

	private Button buttonLoginLogout;
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	//private TextView textInstructionsOrLink;
	
	
	private Button cguButton = null;
	private Button helpButton = null;
	private ImageView splashscreenLogo = null;
	private ImageView pintreepin;
	private TextView fbwarning = null;
	private ImageView ivRefresh;
	private LinearLayout refreshLayout;
	private LinearLayout cguLayout;
	//private static final String URL_PREFIX_FRIENDS = "https://graph.facebook.com/me/fields=id,name?access_token=";
	
	private Boolean activityLaunched = false ;
	
	static String userId;
	static String userEmail;
	static String userFirstname;
	static String userLastname;
	static String userBirthdate;
	static Session session;
	
	
		  
	  private OnClickListener clickListenerCguButton = new View.OnClickListener() {
	    @Override
	    public void onClick(View v) {
	    	Toast.makeText(getApplicationContext(), "CGU", Toast.LENGTH_SHORT).show();
	    }
	  };
	  
	  private OnClickListener clickListenerHelpButton = new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	Intent intent = new Intent(LoginDisplayActivity.this, FragmentsSliderActivity.class);
                startActivity(intent);
		    }
		  };
	
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logindisplayactivity);
        
        
        Typeface fb = Typeface.createFromAsset(this.getAssets(), "fb.ttf");
        
        
        buttonLoginLogout = (Button)findViewById(R.id.buttonLoginLogout);

        cguLayout = (LinearLayout) findViewById(R.id.cguLayout);
        cguLayout.setVisibility(View.VISIBLE);
        
        refreshLayout = (LinearLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setVisibility(View.GONE);
        ivRefresh = (ImageView) findViewById(R.id.ivRefresh);
        Animation rotationCenter = AnimationUtils.loadAnimation(this,R.anim.rotation_center);
        ivRefresh.startAnimation(rotationCenter);
       
        
        cguButton = (Button)findViewById(R.id.cgubutton);
        helpButton = (Button)findViewById(R.id.helpbutton);     
        splashscreenLogo = (ImageView)findViewById(R.id.splashscreenlogo);
        Animation animationLogo = AnimationUtils.loadAnimation(this,R.anim.splashtranslatelogo);
        splashscreenLogo.startAnimation(animationLogo);
        
        pintreepin = (ImageView)findViewById(R.id.pintreepin);
        pintreepin.startAnimation(animationLogo);
        
        fbwarning = (TextView)findViewById(R.id.fbwarning);
        fbwarning.setTypeface(fb);
        
        
        Animation animationFbconnect = AnimationUtils.loadAnimation(this,R.anim.fbconnectfadein);
        
        buttonLoginLogout.startAnimation(animationFbconnect); 
        buttonLoginLogout.setVisibility(View.GONE);
        fbwarning.startAnimation(animationFbconnect);
        
        
        //buttonLoginLogout.setOnClickListener(clickListenerFbConnectButton);
        cguButton.setOnClickListener(clickListenerCguButton);
        helpButton.setOnClickListener(clickListenerHelpButton);
        
        if(!CheckInternet(this)){
			
			this.showSettingsAlert();
			
        }
        else{
	        Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
	        
	        Session.OpenRequest request = new Session.OpenRequest(this);
	        request.setPermissions(Arrays.asList("user_friends"));
	        request.setCallback(statusCallback );
	        
	        
	        session = Session.getActiveSession();
	        if (session == null) {
	            if (savedInstanceState != null) {
	                session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
	            }
	            if (session == null) {
	                session = new Session(this);
	            }
	            Session.setActiveSession(session);
	            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
	                session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
	            }
	        }
        }
        
        updateView(); 
    }
    
    @Override
    public void onStart() {
        super.onStart();
        if(!CheckInternet(this)){
			MainActivity.displayToast("Problème de connexion");
        }
        else{
        	Session.getActiveSession().addCallback(statusCallback);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //Session.getActiveSession().removeCallback(statusCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
        
        
    }

    @SuppressWarnings("deprecation")
	private void updateView() {
    	
    	if(!CheckInternet(this)){
			
    		buttonLoginLogout.setVisibility(View.VISIBLE);
    		cguLayout.setVisibility(View.VISIBLE);
			//this.showSettingsAlert();
    		buttonLoginLogout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) { 
                	
                	
                	if(!CheckInternet(LoginDisplayActivity.this)){
            			
                		LoginDisplayActivity.this.showSettingsAlert();
                    }
                	else{
                		
                		Intent i = new Intent(LoginDisplayActivity.this, LoginDisplayActivity.class);
                		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			            startActivity(i);
                	}
                } 
            });
			
        }
    	else{
	        session = Session.getActiveSession();
	        buttonLoginLogout.setVisibility(View.GONE);
	        cguLayout.setVisibility(View.GONE);
	        if (session.isOpened()) {
	        	 //Retrieve Facebook userId
	        	
	        	
	        	
	        	Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

	                // callback after Graph API response with user object
	                @Override
	                public void onCompleted(GraphUser user, Response response) {
	                  if (user != null) {
	                	  Log.i("fb", "fb user: "+ user.toString());

	                	 GraphObject responseGraphObject = response.getGraphObject();
      		            //Create the JSON object
      		            JSONObject json = responseGraphObject.getInnerJSONObject();
      		        	
      		            
      		          //MainActivity.displayToast(session.getAccessToken());
      		            
						try {
							 //userId = "601805446";
							userId = json.getString("id");
							userFirstname = json.getString("first_name");
  							userLastname = json.getString("last_name");
  							userEmail = json.getString("email");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                    
						if(userId == "" || userId == null 
								|| userFirstname == "" || userFirstname == null 
								|| userLastname == "" || userLastname == null){
							
							buttonLoginLogout.setVisibility(View.VISIBLE);
				        	cguLayout.setVisibility(View.VISIBLE);
				            buttonLoginLogout.setOnClickListener(new OnClickListener() {
				                public void onClick(View view) { 
				                	
				                	if(!CheckInternet(LoginDisplayActivity.this)){
				            			
				                		LoginDisplayActivity.this.showSettingsAlert();
				            			
				                    }
				                	else{
				                		buttonLoginLogout.setVisibility(View.GONE);
				                		cguLayout.setVisibility(View.GONE);
				                		onClickLogin();
				                	}
				                }
				            });
				            
				            DialogFbConnectUnavailable dialogFbConnectUnavailable = new DialogFbConnectUnavailable(LoginDisplayActivity.this);
				            try{
				            	dialogFbConnectUnavailable.show();
				            }
				            catch(BadTokenException e){
				            	
				            }
						}
						else{
							if(userId != null){
    							StoreFriendList storeFriendList = new StoreFriendList(session.getAccessToken());
    							storeFriendList.execute();
    						}
    						else{

    		        			buttonLoginLogout.setVisibility(View.VISIBLE);
    		        			buttonLoginLogout.setOnClickListener(new OnClickListener() {
    		                        public void onClick(View view) { 
    		                        	
    		                        	
    		                        	if(!CheckInternet(LoginDisplayActivity.this)){
    		                    			
    		                        		LoginDisplayActivity.this.showSettingsAlert();
    		                            }
    		                        	else{
    		                        		
    		                        		Intent i = new Intent(LoginDisplayActivity.this, LoginDisplayActivity.class);
    		                        		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    		        			            startActivity(i);
    		                        	}
    		                        } 
    		                    });
    						}
						}
	                  }
	                  else{
	                	  	buttonLoginLogout.setVisibility(View.VISIBLE);
				        	cguLayout.setVisibility(View.VISIBLE);
				            buttonLoginLogout.setOnClickListener(new OnClickListener() {
				                public void onClick(View view) { 
				                	
				                	if(!CheckInternet(LoginDisplayActivity.this)){
				            			
				                		LoginDisplayActivity.this.showSettingsAlert();
				            			
				                    }
				                	else{
				                		buttonLoginLogout.setVisibility(View.GONE);
				                		cguLayout.setVisibility(View.GONE);
				                		onClickLogin();
				                	}
				                }
				            });
				            
				            DialogFbConnectUnavailable dialogFbConnectUnavailable = new DialogFbConnectUnavailable(LoginDisplayActivity.this);
				            try{
				            	dialogFbConnectUnavailable.show();
				            }
				            catch(BadTokenException e){
				            	
				            }
				            
	                  }
	                }
	              });
	        	
	        	
	        		
		        	
	        }
	        else {
	        	buttonLoginLogout.setVisibility(View.VISIBLE);
	        	cguLayout.setVisibility(View.VISIBLE);
	            buttonLoginLogout.setOnClickListener(new OnClickListener() {
	                public void onClick(View view) { 
	                	
	                	if(!CheckInternet(LoginDisplayActivity.this)){
	            			
	                		LoginDisplayActivity.this.showSettingsAlert();
	            			
	                    }
	                	else{
	                		buttonLoginLogout.setVisibility(View.GONE);
	                		cguLayout.setVisibility(View.GONE);
	                		onClickLogin();
	                	}
	                }
	            });
	        }
    	}
    }

    private void onClickLogin() {
    	
    	try{
    		session.openForRead(new Session.OpenRequest(this).setPermissions(Arrays.asList("user_friends","email")).setCallback(statusCallback));
    	}
    	catch(UnsupportedOperationException e){
    		DialogFbConnectUnavailable dialogFbConnectUnavailable = new DialogFbConnectUnavailable(LoginDisplayActivity.this);
            try{
            	dialogFbConnectUnavailable.show();
            }
            catch(BadTokenException badTokenException){
            	
            }
    	}
    	Session session = Session.getActiveSession();
        buttonLoginLogout.setVisibility(View.GONE);
        cguLayout.setVisibility(View.GONE);
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
        } else {
            Session.openActiveSession(this, true, statusCallback);
        }
    }

    static void onClickLogout() {
        Session session = Session.getActiveSession();
        if (!session.isClosed()) {
            session.closeAndClearTokenInformation();
        }
    }

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
        	
            updateView();
        }
    }
    
    
    public static boolean CheckInternet(Context ctx) {
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        // Check if wifi or mobile network is available or not. If any of them is
        // available or connected then it will return true, otherwise false;
        return wifi.isConnected() || mobile.isConnected();
    }
    
    public void showSettingsAlert(){
    	DialogSettingInternet dialogSettingInternet = new DialogSettingInternet(this);
		 dialogSettingInternet.show();
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
		    	Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
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
    
    
    private class DialogFbConnectUnavailable extends Dialog implements
    android.view.View.OnClickListener {
	
		  public Activity activity;
		  public Dialog d;
		  public Button confirmButton;
		  public Button cancelButton;
		  public Button callHotlineButton;
		
		  public DialogFbConnectUnavailable(Activity activity) {
		    super(activity);
		    // TODO Auto-generated constructor stub
		    this.activity = activity;
		  }
		
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    setContentView(R.layout.dialog_fbconnectunavailable);
		    confirmButton = (Button) findViewById(R.id.confirmButton);
		    confirmButton.setOnClickListener(this);
		    cancelButton = (Button) findViewById(R.id.cancelButton);
		    cancelButton.setOnClickListener(this);
		    callHotlineButton = (Button) findViewById(R.id.callHotlineButton);
		    callHotlineButton.setOnClickListener(this);
		     

		  }
		
		  @Override
		  public void onClick(View v) {
		    switch (v.getId()) {
		    case R.id.confirmButton:
		    	Intent i = new Intent(LoginDisplayActivity.this, LoginDisplayActivity.class);
        		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	            startActivity(i);
	            dismiss();
		    break;
		    case R.id.cancelButton:
		    	LoginDisplayActivity.this.finish();
		    	dismiss();
		    break;
		    case R.id.callHotlineButton:
		    	Intent callIntent = new Intent(Intent.ACTION_DIAL);
				callIntent.setData(Uri.parse("tel:0650312623"));
				startActivity(callIntent);
				dismiss();
		    break;
		    default:
		      break;
		    }
		  }
	}
    
    
    private class StoreFriendList extends AsyncTask<Void, Integer, HashMap<String,String>> {
    	
    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	HashMap<String,String> mapErrCode = new HashMap<String,String>();
    	String fbaccesstoken;
    	
    	
    	public StoreFriendList( String fbaccesstoken){
    		this.fbaccesstoken=fbaccesstoken;
    	}
    	
    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		if(activityLaunched){
				this.cancel(true);
			}
    		buttonLoginLogout.setVisibility(View.GONE);
    		cguLayout.setVisibility(View.GONE);
    		refreshLayout.setVisibility(View.VISIBLE);
    		
    		nameValuePairs.add(new BasicNameValuePair("fbaccesstoken", fbaccesstoken));
    		
    	}

    	@Override
    	protected void onProgressUpdate(Integer... values){
    		super.onProgressUpdate(values);
    		
    	}

    	@Override
    	protected HashMap<String,String> doInBackground(Void... arg0) {
    		HashMap<String,String> mapTimeout = new HashMap<String,String>();
    		mapTimeout.put(MainActivity.KEY_TIMEOUT, "1");
    		
    		if (android.os.Build.VERSION.SDK_INT > 9) 
        	{ StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }
    	
        	XMLParser parser = new XMLParser(nameValuePairs);
        	String xml = null;
			try {
				xml = parser.getXmlFromUrl(MainActivity.URL_USERFRIENDLIST + "?userid=" + userId 
																			+ "&firstname=" + URLEncoder.encode(userFirstname, "UTF-8") 
																			+ "&lastname=" +  URLEncoder.encode(userLastname, "UTF-8") 
																			+ "&usermail=" + userEmail
																			+ "&appversion=" + MainActivity.appVersion 
																			+ "&devicename=" + URLEncoder.encode(getDeviceName(), "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				xml = parser.getXmlFromUrl(MainActivity.URL_USERFRIENDLIST + "?userid=" + userId 
						+ "&firstname=" + userFirstname 
						+ "&lastname=" +  userLastname 
						+ "&usermail=" + userEmail 
						+ "&appversion=" + MainActivity.appVersion 
						+ "&devicename=" + getDeviceName());
			}
        	if(xml == null){
        		mapErrCode = null;
        	}
        	else{
        		if(xml == "timeout"){
        			
        			mapErrCode.put(MainActivity.KEY_TIMEOUT, "1");
        		}
        		
        		else{
        			
        	         // getting XML from URL
        			Document doc = parser.getDomElement(xml); // getting DOM element
        			
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
    	protected void onPostExecute(HashMap<String,String>  result) {
    		
    		
    		if(result == null || result.containsKey(MainActivity.KEY_TIMEOUT)){
    			//MainActivity.displayToast("user id : " + LoginDisplayActivity.userId + "usermail : " + LoginDisplayActivity.userEmail + "firstname= " + LoginDisplayActivity.userFirstname + "lastname = " + LoginDisplayActivity.userLastname);
				
    			MainActivity.displayToast("Problème de connexion, veuillez réessayer plus tard");
    			refreshLayout.setVisibility(View.GONE);
    			buttonLoginLogout.setVisibility(View.VISIBLE);
    			buttonLoginLogout.setOnClickListener(new OnClickListener() {
	                public void onClick(View view) { 
	                	
	                	if(!CheckInternet(LoginDisplayActivity.this)){
	            			
	                		LoginDisplayActivity.this.showSettingsAlert();
	            			
	                    }
	                	else{
	                		buttonLoginLogout.setVisibility(View.GONE);
	                		cguLayout.setVisibility(View.GONE);
	                		onClickLogin();
	                	}
	                }
	            });
        	}
    		else{
    			if(result.get(MainActivity.KEY_ERRCODE) != null){
    				if(result.get(MainActivity.KEY_ERRCODE).equals("000")){
    					if(!activityLaunched){
    						activityLaunched = true;
    						Intent i = new Intent(LoginDisplayActivity.this, MainActivity.class);
    	    				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    			            startActivity(i);
    					}
    					else{
    						
    					}
        			}
        			else{
        				refreshLayout.setVisibility(View.GONE);
	        			  buttonLoginLogout.setVisibility(View.VISIBLE);
	        			  buttonLoginLogout.setOnClickListener(new OnClickListener() {
	      	                public void onClick(View view) {
	      	                	if(!CheckInternet(LoginDisplayActivity.this)){
	      	            			
	      	                		LoginDisplayActivity.this.showSettingsAlert();
	      	            			
	      	                    }
	      	                	else{
	      	                		buttonLoginLogout.setVisibility(View.GONE);
	      	                		cguLayout.setVisibility(View.GONE);
	      	                		updateView();
	      	                	}
	      	                }
	      	            });
	        			  DialogTimeoutLogin dialogTimeoutLogin = new DialogTimeoutLogin(LoginDisplayActivity.this);
	        			  try{
	        				  dialogTimeoutLogin.show();
	        			  }
	        			  catch(BadTokenException e){
	        				  
	        			  }
        			}
    			}
    			else{
    				//MainActivity.displayToast("user id : " + LoginDisplayActivity.userId + "usermail : " + LoginDisplayActivity.userEmail + "firstname= " + LoginDisplayActivity.userFirstname + "lastname = " + LoginDisplayActivity.userLastname);
    				refreshLayout.setVisibility(View.GONE);
        			buttonLoginLogout.setVisibility(View.VISIBLE);
    			}
    			
    		}
    	}
    }
    
    public String getDeviceName() {
    	  String manufacturer = Build.MANUFACTURER;
    	  String model = Build.MODEL;
    	  if (model.startsWith(manufacturer)) {
    	    return capitalize(model);
    	  } else {
    	    return capitalize(manufacturer) + " " + model;
    	  }
    	}
    
    private String capitalize(String s) {
    	  if (s == null || s.length() == 0) {
    	    return "";
    	  }
    	  char first = s.charAt(0);
    	  if (Character.isUpperCase(first)) {
    	    return s;
    	  } else {
    	    return Character.toUpperCase(first) + s.substring(1);
    	  }
    	} 
}
