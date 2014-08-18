package com.treep.fr;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.view.WindowManager.BadTokenException;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.PushService;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MainActivity extends Activity{
	 
	private DrawerLayout mDrawerLayout;
    static ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
 
    
 // XML node keys
    
    static final double TIME_OUT_IN_SECONDS = 30;
    
    
  //userinfo

    static String appVersion = "31";
    
    static int appVersionFromXML = 0;
    
	static final String STEP_USERINFORMATION = "userinformation";
	static final String STEP_HASTOUPDATE = "hastoupdate";
    static final String STEP_HOME = "home";
    static final String STEP_USERTREEPORDERED = "usertreepordered";
    static final String STEP_USERTREEPACCEPTED = "usertreepaccepted";
	static final String STEP_USERCONFIRMONBOARD = "userconfirmonboard";
    static final String STEP_USERONBOARD = "useronboard";
    static final String STEP_USERTREEPFINISHED = "usertreepfinished";
    static final String STEP_USERDRIVERCANCELED = "userdrivercanceled";
    
    
    
    static final String STEP_DRIVERTREEPREQUESTEDNOTREEPER = "drivertreeprequestednotreeper";
    static final String STEP_DRIVERTREEPNOTCONFIRMED = "drivertreepnotconfirmed";
    static final String STEP_DRIVERTREEPREQUESTED = "drivertreeprequested";
    static final String STEP_TREEPREQUEST = "treeprequest";
    static final String STEP_DRIVERTREEPACCEPTEDACK = "drivertreepacceptedack";
    static final String STEP_DRIVERTREEPACCEPTEDTREEPERNOTONBOARD = "drivertreepacceptedtreepernotonboard";
    static final String STEP_DRIVERTREEPACCEPTEDTREEPERONBOARD = "drivertreepacceptedtreeperonboard";
    static final String STEP_DRIVERTREEPACCEPTEDWAITFORTREEPER = "drivertreepacceptedwaitfortreeper";
    static final String STEP_DRIVERTREEPFINISHED = "drivertreepfinished";
    static final String STEP_DRIVERTREEPERCANCELED = "drivertreepercanceled";
    
    static String CURRENT_STEP = STEP_HOME;

	static double myLatitude;
    static double myLongitude;
    
    static Boolean onAppStop = false;
    static final String KEY_USERINFO = "userinfo";
    static final String KEY_USER = "user";
    static final String KEY_USERID = "userid";
    static final String KEY_FIRSTNAME = "firstname";
    static final String KEY_LASTNAME = "lastname";
    static final String KEY_RATING = "rating";
    static final String KEY_NBTREEP = "nbtreep";
    

    static final String KEY_CURRENTPOSITION = "currentposition";
    static final String KEY_CURRENTCOMPANY = "currentcompany";
    
    static final String KEY_USERMAIL = "usermail";
    static final String KEY_USERTEL = "usertel";
    static final String KEY_USERSEXE = "usersexe";
    static final String KEY_ISDRIVER = "isdriver";
    static final String KEY_BECOMEDRIVER = "becomedriver";
    static final String KEY_DRIVERMODE = "drivermode";
    static final String KEY_OILTYPE = "oiltype";
    static final String KEY_ISTREEPORDERED = "istreepordered";
    static final String KEY_TIMEREMAINING = "timeremaining";
    static final String KEY_DETOUR = "detour";
    static final String KEY_ISVALIDATED = "isvalidated";
    
    static final String KEY_DRIVERMODETREEPSTATE = "drivermodetreepstate";
    

    static final String KEY_STATUS = "status";
    
    
    //userdriverinfo (drivermode)
    static final String KEY_USERDRIVERINFO = "userdriverinfo";
    static final String KEY_ISAVAILABLE = "isavailable";
    
    static final String KEY_TREEPNOWCONFIRMEDTIMEREMAINING= "treepnowconfirmedtimeremaining";
    static final String KEY_TREEPORDERIDCONFIRMED= "treeporderidconfirmed";
    
    
    static final String KEY_TREEPCONFIRMEDINFO = "treepconfirmedinfo";
    static final String KEY_ISTREEPNOWACCEPTED = "istreepnowaccepted";
    static final String KEY_ISTREEPNOWARRIVED= "istreepnowarrived";
    
    //userpositionlist (drivermode)
    static final String KEY_USERPOSITIONLIST = "userpositionlist";
    static final String KEY_USERPOSITION = "userposition";
    
    //treeporderinfo
    static final String KEY_TREEPORDERINFO = "treeporderinfo";
    static final String KEY_TREEPNOW = "treepnow";
    static final String KEY_ISTREEPNOW = "istreepnow";
    static final String KEY_ISTREEPNOWCONFIRMED = "istreepnowconfirmed";
    static final String KEY_ISTREEPNOWCANCELEDBYDRIVER = "istreepnowcanceledbydriver";
    static final String KEY_ISTREEPNOWCANCELEDBYTREEPER = "istreepnowcanceledbytreeper";
    static final String KEY_ISTREEPNOWFINISHED = "istreepnowfinished";
    static final String KEY_ISTREEPNOWRATEDBYTREEPER = "istreepnowratedbytreeper";
    static final String KEY_ISTREEPNOWRATEDBYDRIVER = "istreepnowratedbydriver";
    static final String KEY_ISTREEPNOWCANCELED = "istreepnowcanceled";
    static final String KEY_LATDEPNOW = "latdepnow";
    static final String KEY_LNGDEPNOW = "lngdepnow";
    static final String KEY_LATDESTNOW = "latdestnow";
    static final String KEY_LNGDESTNOW = "lngdestnow";
    static final String KEY_ADDRESSDEPNOW = "addressdepnow";
    static final String KEY_ADDRESSDESTNOW = "addressdestnow";
    static final String KEY_ISTREEPERONBOARDFROMTREEPER = "istreeperonboardfromtreeper";
    static final String KEY_ISTREEPERONBOARDFROMDRIVER = "istreeperonboardfromdriver";
    static final String KEY_DRIVERID = "driverid";
    static final String KEY_DRIVERTREEPREQUESTED = "drivertreeprequested";
    static final String KEY_DRIVERTREEPREQUESTID = "drivertreeprequestid";
    static final String KEY_TREEPREQUESTED = "treeprequested";
    static final String KEY_TREEPREQUEST = "treeprequest";
    static final String KEY_ISTREEPREQUESTEDFROMTREEPER = "istreeprequestedfromtreeper";
    
    static final String KEY_TREEPORDERDRIVERINFO = "treeporderdriverinfo";
    static final String KEY_TREEPORDERTIMEREMAINING = "treepordertimeremaining";
    static final String KEY_DISTANCE = "distance";
    static final String KEY_DURATION = "duration";
    
    
    //treepnowconfirmed et treepscheduledconfirmed
    static final String KEY_TREEPNOWCONFIRMED = "treepnowconfirmed";
    static final String KEY_TREEPID = "treepid";
    static final String KEY_REQUESTID = "requestid";
    static final String KEY_CARMODEL = "carmodel";
    static final String KEY_DISTANCETIME = "distancetime";
    static final String KEY_PRICE = "price";
    static final String KEY_LATITUDE = "latitude";
    static final String KEY_LONGITUDE = "longitude";
    static final String KEY_CARPICURL = "carpic_url";
    static final String KEY_TREEPERONBOARD = "treeperonboard";
    static final String KEY_NBSEAT = "nbseat";
    static final String KEY_TREEPRESPONSETIMEREMAINING = "treepresponsetimeremaining";
    
    
    static final String KEY_CONSTANTS = "constants";
    static final String KEY_GAZOLEPRICE = "gazoleprice";
    static final String KEY_GASOLINEPRICE = "gasolineprice";
    
    static String VALUE_GAZOLPRICE;
    static String VALUE_GASOLINEPRICE;
    static double VALUE_CONSUMPTIONPRICEPERKM;
    
    static final String KEY_TREEPRATEKM="treepratekm";
  	
  	static  double VALUE_TREEPRATEKM = 0.45;
  	
  	static boolean VALUE_OILTYPE = false;
    
    static final String KEY_BASEPRICING = "basepricing";
    static final String KEY_NORMALDISTANCEPRICE = "normaldistanceprice";
    static final String KEY_NORMALTIMEPRICE = "normaltimeprice";
    static final String KEY_NORMALAPPROACHPRICE = "normalapproachprice";
    static final String KEY_RUSHDISTANCEPRICE = "rushdistanceprice";
    static final String KEY_RUSHTIMEPRICE = "rushtimeprice";
    static final String KEY_RUSHAPPROACHPRICE = "rushapproachprice";
    static final String KEY_PRICINGNAME = "pricingname";
    
    static String VALUE_NORMALDISTANCEPRICE = "0";
    static String VALUE_NORMALTIMEPRICE = "0";
    static String VALUE_NORMALAPPROACHPRICE = "0";
    static String VALUE_RUSHDISTANCEPRICE = "0";
    static String VALUE_RUSHTIMEPRICE = "0";
    static String VALUE_RUSHAPPROACHPRICE = "0";
    static String VALUE_PRICINGNAME = "0";
    
    //driverpositionlist
    static final String KEY_DRIVERPOSITIONLIST = "driverpositionlist";
    static final String KEY_DRIVERPOSITION = "driverposition";
    static final String KEY_ISBUSY = "isbusy";

  	static final String KEY_ADDRESSDEP = "addressdep";
  	static final String KEY_ADDRESSDEST = "addressdest";
  	static final String KEY_LATLNGMYPOSITION = "latlngmyposition";
  	static final String KEY_LATMYPOSITION = "latmyposition";
  	static final String KEY_LNGMYPOSITION = "lngmyposition";
  	static final String KEY_LATLNGDEP = "latlngdep";
  	static final String KEY_LATLNGDEST = "latlngdest";
  	static final String KEY_LATDEP = "latdep";
  	static final String KEY_LATDEST = "latdest";
  	static final String KEY_LNGDEP = "lngdep";
  	static final String KEY_LNGDEST = "lngdest";
	static final String KEY_PROFILEPIC_URL = "profilepic_url";
	static final String KEY_CARPIC_URL = "carpic_url";  
	static final String KEY_RESPONSEID = "responseid";
    static final String KEY_ORDERID = "orderid";
    static final String KEY_ORDER = "order";	
	static final String KEY_RESPONSE = "response";
	static final String KEY_TREEPORDERID = "treeporderid";
	static final String KEY_MYDRIVERLIST = "mydriverlist";
  	static final String KEY_TREEPRESPONSELIST = "treepresponselist";
  	static final String KEY_TIMEOUT = "timeout";
  	
  	static final String KEY_DRIVERUSERINFO = "driveruserinfo";
  	static final String KEY_ISTREEPNOWACCEPTEDCANCELEDBYDRIVER= "istreepnowacceptedcanceledbydriver";
  	static final String KEY_ISTREEPNOWACCEPTEDCANCELEDBYTREEPER= "istreepnowacceptedcanceledbytreeper";
  	static final String KEY_DRIVERTREEPNOW = "drivertreepnow";
  	static final String KEY_ISTREEPNOWRESPONSEACK = "istreepnowresponseack";


    static final String KEY_DRIVERTREEPRESPONSECOUNT = "drivertreepresponsecount";
    static final String KEY_COUNT = "count";
  	
  	static final String KEY_TREEPRESPONSECOUNT = "treepresponsecount";
  	static final String KEY_DRIVERTREEPNOWCONFIRMEDCOUNT= "drivertreepnowconfirmedcount";
  	
  	static final String KEY_HASTOUPDATE= "hastoupdate";
  	static final String KEY_APPVERSIONCODE= "appversioncode";
  	
  	
  	//promo code
  	static final String KEY_PROMOCODEVERIFICATIONLIST= "promocodeverificationlist";
  	static final String KEY_PROMOCODEUSEDLIST= "promocodeusedlist";
  	static final String KEY_PROMOCODEAVAILABLELIST= "promocodeavailablelist";
  	static final String KEY_PROMOCODEAVAILABLE= "promocodeavailable";
  	static final String KEY_PROMOCODE= "promocode";
  	static final String KEY_PROMOPRICE= "promoprice";
  	static final String KEY_CODE= "code";
    
  	
  	static final String URL_API_PROD = "http://www.treep.fr/api/prod/";
  	static final String URL_API_PPROD = "http://www.treep.fr/api/pprod/";
  	static final String URL_API = URL_API_PPROD;
  	
  	static final String URL_USERINFO = URL_API + "userinfo/userinfo.php";
  	static final String URL_USERPOSITIONLIST = URL_API + "userpositionlist.php";
  	static final String URL_MYDRIVERLIST = URL_API + "mydriverlist.php";
  	static final String URL_TREEPRESPONSELIST = URL_API + "treepresponselist.php";
  	static final String URL_TREEPDRIVERRESPONSELIST = URL_API + "treepdriverresponseslist.php";
  	static final String URL_PROMOCODEVERIFICATION = URL_API + "promocodeverification.php";
  	static final String URL_USERFRIENDLIST = URL_API + "storefriendlist/storefriendlist.php";
  	static final String URL_BOOKTREEP = URL_API + "booktreep.php";
  	static final String URL_SETDRIVERMODEON = URL_API + "setdrivermodeon.php";
  	static final String URL_SETDRIVERMODEOFF = URL_API + "setdrivermodeoff.php";
  	static final String URL_SETMYINFORMATIONS = URL_API + "setmyinformations.php";
  	static final String URL_SETMYPROFILEMAIL = URL_API + "setmyprofilemail.php";
  	static final String URL_SETMYPROFILETEL = URL_API + "setmyprofiletel.php";
  	static final String URL_SETDRIVERAVAILABLE = URL_API + "setdriveravailable.php";
  	static final String URL_SETDRIVERNOTAVAILABLE = URL_API + "setdrivernotavailable.php";
  	static final String URL_SETTREEPERONBOARDFROMTREEPER= URL_API + "settreeperonboardfromtreeper.php";
  	static final String URL_SETTREEPERONBOARDFROMDRIVER= URL_API + "settreeperonboardfromdriver.php";
  	static final String URL_SETTREEPFINISHEDFROMDRIVER= URL_API + "settreepfinishedfromdriver.php";
  	static final String URL_SETTREEPCANCELFROMTREEPER= URL_API + "settreepcancelfromtreeper.php";
  	static final String URL_SETTREEPCANCELFROMDRIVER = URL_API + "settreepcancelfromdriver.php";
  	static final String URL_SETTREEPCANCELEDFROMDRIVER = URL_API + "settreepcanceledfromdriver.php";
  	static final String URL_SETTREEPCANCELEDFROMTREEPER = URL_API + "settreepcanceledfromtreeper.php";
  	static final String URL_SETRAZTREEPFINISHEDRATEDFORTREEPER= URL_API + "setraztreepfinishedratedfortreeper.php";
  	static final String URL_SETRAZTREEPFINISHEDRATEDFORDRIVER = URL_API + "setraztreepfinishedratedfordriver.php";
  	static final String URL_SETTREEPRATINGFROMTREEPER= URL_API + "settreepratingfromtreeper.php";
  	static final String URL_SETTREEPRATINGFROMDRIVER= URL_API + "settreepratingfromdriver.php";
  	static final String URL_SETUSERPOSITION= URL_API + "setuserposition.php";
  	static final String URL_BECOMEDRIVER= URL_API + "becomedriver.php";
  	static final String URL_SETTREEPDRIVERRESPONSECONFIRM =  URL_API + "settreepdriverresponseconfirm.php";
  	static final String URL_SETTREEPREQUESTCONFIRMFROMDRIVER =  URL_API + "settreeprequestconfirmfromdriver.php";
  	static final String URL_SETTREEPREQUESTREFUSEFROMDRIVER =  URL_API + "settreeprequestrefusefromdriver.php";
  	static final String URL_SETDRIVERTREEPNOWRESPONSEACK =  URL_API + "setdrivertreepnowresponseack.php";
  	static final String URL_SETDRIVERINFORMATION =  URL_API + "setdriverinformation.php";
  	static final String URL_STORETREEPREQUEST =  URL_API + "storetreeprequest.php";
  	static final String URL_TREEPDRIVERREQUEST =  URL_API + "treepdriverrequest.php";
  	static final String URL_SETTREEPDRIVERREQUESTEDCANCELFROMDRIVER =  URL_API + "settreepdriverrequestedcancelfromdriver.php";
  	
  	static final String KEY_XML= "xml";
  	static final String KEY_ERRCODE= "errcode";
	
  	static final String KEY_NIGHTHOUR= "nighthour";
  	
  	static final String KEY_SHAREDPREFTREEP = "treepapp";
  	static final String KEY_SHAREDPREFMATCHEDARRAY = "drivermatchedlist";
  	static final String KEY_DRIVERMATCHCOUNT="drivermatchcount";
  	
  	

  	static boolean isTreeperOnBoard = false;
  	static boolean isDriverAccepted = false;
  	
  	static boolean isTreepOrderedByTreeper = false;
  	
  	static final int distanceMax = 50;
  	static Boolean appQuit = true;
  	
 	static int launchTime = 0;
  	
  	static String urlProfilePic(String userId){
  		return  "https://graph.facebook.com/"+userId+"/picture?width=500&height=500";
  	}
  	static String urlThumbPic(String userId){
  		return  "https://graph.facebook.com/"+userId+"/picture?width=100&height=100";
  	}
  	
  	static final String urlFriendList(String userId){
  		return   URL_API + "storefriendlist/lists/"+userId+".js";
  	}
  	
  	static final String urlFacebookProfile(String userId){
  		return  "http://www.facebook.com/"+userId;
  	}
  	static final void displayToast(String str){
  		Toast toast = Toast.makeText(ApplicationContextProvider.getContext(), str, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
  	}
  	
  	static final void displayToast(int integer){
  		Toast toast = Toast.makeText(ApplicationContextProvider.getContext(), integer, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
  	}
  	
  	
	
    public static int initposition = 0;
    int initpos;
    
    static Boolean driverMode = false;
    
    private DialogSMSTreeperOnBoardSent dialogSMSTreeperOnBoardSent;
    
    
    // nav drawer title
    private CharSequence mDrawerTitle;
 
    // used to store app title
    private CharSequence mTitle;
 
    
    // slide menu items
    static String[] navMenuTitles;
    static String[] navMenuSubTitles;
    static TypedArray navMenuIcons;
 
    static ArrayList<NavDrawerItem> navDrawerItems;
    static NavDrawerListAdapter adapter;
    
    static Typeface menuFont;
    
    static public Fragment fragment;
    
    private UIUpdater userInfoUpdater;
    
    private  DialogPleaseWait dialogPleaseWait;
    
    private DialogTimeout dialogTimeout;
   
    private WeakReference<StoreMainUserInfoFromXML> storeMainUserInfoFromXMLContext;
    
    public Object onRetainNonConfigurationInstance() {
        
        WeakReference<StoreMainUserInfoFromXML> weakReference = null;
                
        if (this.storeMainUserInfoFromXMLContext != null
            && this.storeMainUserInfoFromXMLContext.get() != null
            && !this.storeMainUserInfoFromXMLContext.get().getStatus().equals(Status.FINISHED)) {
            weakReference = this.storeMainUserInfoFromXMLContext;
        }
        return weakReference;
    }
    
    
    private class DialogSetDriverModeOn extends Dialog implements
    android.view.View.OnClickListener {
	
	  public Context context;
	  public Button confirmButton;
	  public Button cancelButton;
	  private CheckBox checkBox1;
	  private CheckBox checkBox2;
	  private CheckBox checkBox3;
	
	  private Typeface fb;
	
	  public DialogSetDriverModeOn(Context context) {
	    super(context);
	    // TODO Auto-generated constructor stub
	    this.context = context;
	  }
	  
	  
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.dialog_setdrivermode);
	    
	    
	    fb = Typeface.createFromAsset(context.getAssets(), "fb.ttf");
	    confirmButton = (Button) findViewById(R.id.confirmButton);
	    confirmButton.setOnClickListener(this);
	    confirmButton.setTypeface(fb);
	    cancelButton = (Button) findViewById(R.id.cancelButton);
	    cancelButton.setOnClickListener(this);
	    cancelButton.setTypeface(fb);
	    
	    checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
	    checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
	    checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
	   
	  }
	
	  @Override
	  public void onClick(View v) {
	    switch (v.getId()) {
	    case R.id.confirmButton:
	    	if(!checkBox1.isChecked() || !checkBox2.isChecked()  || !checkBox3.isChecked()){
	    		MainActivity.displayToast("Vous devez cocher les cases afin d'activer le mode driver.");
	    	}
	    	else{
	    		if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
		        	
					showSettingsAlert();
					dismiss();
		    	}
		    	else{
		    		dismiss();
		    		SetDriverModeFromXML setDriverModeFromXML = new SetDriverModeFromXML(MainActivity.this);
			        setDriverModeFromXML.execute();
		    	}
	    	}
	      break;
	    case R.id.cancelButton:
	    	dismiss();
	      break;
	    default:
	      break;
	    }
	  }
	}
 
    @SuppressWarnings({ "unchecked", "deprecation" })
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if(LoginDisplayActivity.userId != null){
        	PushService.subscribe(this.getBaseContext(), "user"+ LoginDisplayActivity.userId, MainActivity.class);
        }
        
        //setProgressBarIndeterminateVisibility(true);
        onAppStop = false;
    
        dialogTimeout = new DialogTimeout(this);
       
        ParseAnalytics.trackAppOpened(getIntent());
        
        
        
        //Hide the ActionBar title display
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(""); 
      //Custom font declaration for listview 
      	//menuFont = Typeface.createFromAsset(getAssets(), "fb.ttf");
           
        mTitle = mDrawerTitle = getTitle();
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuSubTitles = getResources().getStringArray(R.array.nav_drawer_subtitles);
        // nav drawer icons from resources
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        navDrawerItems = new ArrayList<NavDrawerItem>();
 
        // adding nav drawer items to array
        // "réserver un treep"
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0],navMenuSubTitles[0], navMenuIcons.getResourceId(0, -1)));
        // "Mon profil"
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1],navMenuSubTitles[1], navMenuIcons.getResourceId(1, -1)));
        // "Mes conducteurs"
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2],navMenuSubTitles[2], navMenuIcons.getResourceId(2, -1)));
        // "Paramètres"
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3],navMenuSubTitles[3], navMenuIcons.getResourceId(3, -1)));
        // "Aide"
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4],navMenuSubTitles[4], navMenuIcons.getResourceId(4, -1)));
        // "Driver Mode"
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5],navMenuSubTitles[5], navMenuIcons.getResourceId(2, -1)));
        
 
        // Recycle the typed array
        navMenuIcons.recycle();
 

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems,menuFont);
        mDrawerList.setAdapter(adapter);
 
        
        
        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        //Adding a button icon à the right of ActionBar
        //getActionBar().setIcon(R.drawable.ic_action_overflow);
        
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.drawable.ic_drawer, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            @Override
			public void onDrawerClosed(View view) {
                getActionBar().setTitle("");
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
 
            @Override
			public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("");
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        
        
        mDrawerList.setItemChecked(0, true);
        mDrawerList.setSelection(0);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        
        
        //displayToast(LoginDisplayActivity.userBirthdate);
        
    	if(!CheckInternet(this)){
        	
			this.showSettingsAlert();
			
            fragment= new BlankFragment();
			showFragment(0,fragment);
			
    	}
        else{
        	MainActivity.initposition = 0; 
        	/*
        	Runnable storeMainUserInfoRunnable = new Runnable() {
	        	  @Override
	        	  public void run() {
	        		  
	        	  }
        	};
  	
	      	final Handler handler = new Handler();
	      	handler.postDelayed(storeMainUserInfoRunnable, 500);
	      	*/
        	fragment= new BlankAnimFragment();
			showFragment(0,fragment);
			
			
	      	StoreMainUserInfoFromXML storeMainUserInfoFromXML = new StoreMainUserInfoFromXML(MainActivity.this);
            storeMainUserInfoFromXML.execute();
            
            
            userInfoUpdater = new UIUpdater(new Runnable() {
	  	         @Override 
	  	         public void run() {
	  	        	if(initposition == 0){
		  	        	UpdateUserInfoFromXML updateUserInfoFromXML = new UpdateUserInfoFromXML(MainActivity.this);
		  	        	updateUserInfoFromXML.execute();
	  	        	}
	  	         }
	  	    });

		  	// Start updates
            userInfoUpdater.startUpdates();
            
        }
    }
    
    
    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }
 
     /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        
        
       
        switch (position) {
        case 0:
        	
        	setProgressBarIndeterminateVisibility(true);
    		/*if(position == initposition){
                mDrawerLayout.closeDrawer(mDrawerList);
                setProgressBarIndeterminateVisibility(false);
    		}
    		*/
    				if(!CheckInternet(this)){
	    				this.showSettingsAlert();
	    	            fragment= new BlankFragment();
	    	            initposition = 0;
    				}
    				else{
    					
    					
    					mDrawerLayout.closeDrawer(mDrawerList);
        				mDrawerList.setItemChecked(position, true);
        	            mDrawerList.setSelection(position);
        	           // setTitle(navMenuTitles[position]);
        	            setProgressBarIndeterminateVisibility(true);
        	            
        	        	StoreMainUserInfoFromXML storeMainUserInfoFromXML = new StoreMainUserInfoFromXML (this);
        	            storeMainUserInfoFromXML.execute();
        	            if(initposition != 0){
        	            	userInfoUpdater.startUpdates();
        	            }
        	            initposition = 0;
        				
    				}
    			 
            break;
        case 1:
        	
        	setProgressBarIndeterminateVisibility(true);
    		
			if(!CheckInternet(this)){
				setProgressBarIndeterminateVisibility(false);
				this.showSettingsAlert();
	            fragment= new BlankFragment();
	            initposition = 1;
			}
			else{
				initposition = 1;
				mDrawerLayout.closeDrawer(mDrawerList);
				mDrawerList.setItemChecked(position, true);
	            mDrawerList.setSelection(position);
				setProgressBarIndeterminateVisibility(true);
				
				fragment = new MyProfileFragment();
				
			}
    		
            break;
        case 2:
        	setProgressBarIndeterminateVisibility(true);
    		
			if(!CheckInternet(this)){
				
				setProgressBarIndeterminateVisibility(false);
				this.showSettingsAlert();
	            fragment= new BlankFragment();
	            initposition = 2;
			}
			else{
				initposition = 2;
				mDrawerLayout.closeDrawer(mDrawerList);
				mDrawerList.setItemChecked(position, true);
	            mDrawerList.setSelection(position);
				setProgressBarIndeterminateVisibility(true);
				
    	        StoreMyDriverListFromXML storeMyDriverListFromXML = new StoreMyDriverListFromXML(this);
    	        storeMyDriverListFromXML.execute();
			}
            break;
        case 3:
        	mDrawerLayout.closeDrawer(mDrawerList);
        	Intent callIntent = new Intent(Intent.ACTION_DIAL);
			callIntent.setData(Uri.parse("tel:0650312623"));
			startActivity(callIntent);
            
            break;
        case 4:
        	//setProgressBarIndeterminateVisibility(true);
    		/*if(position == initposition){
                mDrawerLayout.closeDrawer(mDrawerList);
                setProgressBarIndeterminateVisibility(false);
    		}
    		*/
    				if(!CheckInternet(this)){
    					setProgressBarIndeterminateVisibility(false);
    					this.showSettingsAlert();
    		            fragment= new BlankFragment();
    		            initposition = 1;
    				}
    				else{
    					
    					initposition = 4;
    					mDrawerLayout.closeDrawer(mDrawerList);
        				mDrawerList.setItemChecked(position, true);
        	            mDrawerList.setSelection(position);


        	            mDrawerLayout.closeDrawer(mDrawerList);
        	            Intent intent = new Intent(MainActivity.this, FragmentsSliderActivity.class);
                        startActivity(intent);
    	    			
    					
    				}
            
            break;
        case 5:
        	if(!CheckInternet(this)){
				setProgressBarIndeterminateVisibility(false);
				this.showSettingsAlert();
	            fragment= new BlankFragment();
	            initposition = 1;
			}
        	else{
		        initposition = 0;
		        
				mDrawerLayout.closeDrawer(mDrawerList);
		       
		        setProgressBarIndeterminateVisibility(true);
		        if(!driverMode){
		        	DialogSetDriverModeOn dialogSetDriverModeOn = new DialogSetDriverModeOn(this);
		        	try{
		        		dialogSetDriverModeOn.show();
		        	}
		        	catch(BadTokenException e){
		        		
		        	}
		        }
		        else{
		        	SetDriverModeFromXML setDriverModeFromXML = new SetDriverModeFromXML(this);
			        setDriverModeFromXML.execute();
		        }
		        
        	}
        break;
 
        default:
            break;
        }
 
        
        if (fragment != null) {
        	
        	getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            showFragment(fragment);
            // update selected item and title, then close the drawer
            if(position !=5){
	            mDrawerList.setItemChecked(position, true);
	            mDrawerList.setSelection(position);
            }
           // setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
        	//Toast.makeText(getApplicationContext(), "fragment", Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Error in creating fragment");
        }
		
    }
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        return super.onCreateOptionsMenu(menu);
    }
    
 
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
 
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
 
   
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
    	
    	
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        else{
        	return false;
        }
		
        
    }

    @Override
    public void onStart() {
        super.onStart();
        onAppStop = false;
    }

   @Override
    public void onResume() {
        super.onResume();
        onAppStop = false;
        if(userInfoUpdater != null){
        	userInfoUpdater.startUpdates();
     	}
    }

    @Override
    public void onPause() {
        super.onPause();
        onAppStop = false;
        if(userInfoUpdater != null){
        	userInfoUpdater.stopUpdates();
     	}
    }

    @Override
    public void onStop() {
        super.onStop();
        onAppStop = true;
        if(userInfoUpdater != null){
        	userInfoUpdater.stopUpdates();
     	}
    }

   @Override
    public void onDestroy() {
        super.onDestroy();
        onAppStop = true;
        if(userInfoUpdater != null){
        	userInfoUpdater.stopUpdates();
     	}
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
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
    	try{
    		dialogSettingInternet.show();
    	}
    	catch(BadTokenException e){
    		
    	}
    }

    private class DialogSettingInternet extends Dialog implements
    android.view.View.OnClickListener {
	
		  public Activity activity;
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
		    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
    
    
    
    private class DialogAppQuit extends Dialog implements
    android.view.View.OnClickListener {
	
		  public Activity activity;
		  public Dialog d;
		  public Button confirmButton;
		  public Button cancelButton;
		
		  public DialogAppQuit(Activity activity) {
		    super(activity);
		    // TODO Auto-generated constructor stub
		    this.activity = activity;
		  }
		
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    setContentView(R.layout.dialog_appquit);
		    confirmButton = (Button) findViewById(R.id.confirmButton);
		    confirmButton.setOnClickListener(this);
		    cancelButton = (Button) findViewById(R.id.cancelButton);
		    cancelButton.setOnClickListener(this);
		     

		  }
		
		  @Override
		  public void onClick(View v) {
		    switch (v.getId()) {
		    case R.id.confirmButton:
		    	dismiss();
		    	MainActivity.this.finish();
		    break;
		    case R.id.cancelButton:
		    	dismiss();
		    break;
		    default:
		      break;
		    }
		  }
	}
    
    @Override
    public void onBackPressed() {
    	if(appQuit){
    			if(initposition != 0){
    				initposition = 0;
    				Intent i = new Intent(MainActivity.this, MainActivity.class);
    	    		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    	    		startActivity(i);
    			
    			}
    			else{
    				DialogAppQuit dialogAppQuit = new DialogAppQuit(this);
    	    		dialogAppQuit.show();
    			}
    	}
    	else{
    		super.onBackPressed();
    	}
    }
    
    private void showFragment(int initpos, Fragment fragment){
    	MainActivity.initposition = initpos;    
    	FragmentManager fragmentManager = getFragmentManager();
    	try{
    		fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container, fragment).commit();
    	}
    	catch(IllegalStateException e){
    		
    	}
    }
    private void showFragment(Fragment fragment){  
    	FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container, fragment).commit();
        
    }
 
 
 private class DialogTreeperOnBoardFromTreeper extends Dialog implements
    android.view.View.OnClickListener {
	
	  public Context context;
	  private HashMap<String, String> mapResultTreepOrderDriverInfo;
	  public Button confirmButton;
	  public Button callButton;
	  private TextView tvTreeperOnBoardConfirm;
	  private ImageView ivResponseDriverProfilePic;
	
	  private Typeface fb;
	
	  public DialogTreeperOnBoardFromTreeper(Context context, HashMap<String, String> mapResultTreepOrderDriverInfo) {
	    super(context);
	    // TODO Auto-generated constructor stub
	    this.context = context;
	    this.mapResultTreepOrderDriverInfo=mapResultTreepOrderDriverInfo;
	  }
	  
	  
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.dialog_treeperonboard);
	    
	    fb = Typeface.createFromAsset(context.getAssets(), "fb.ttf");
	    confirmButton = (Button) findViewById(R.id.confirmButton);
	    confirmButton.setOnClickListener(this);
	    confirmButton.setTypeface(fb);
	    
	    callButton = (Button) findViewById(R.id.callButton);
	    callButton.setOnClickListener(this);
	    callButton.setText("  Appeler " + mapResultTreepOrderDriverInfo.get(MainActivity.KEY_FIRSTNAME) + " " + mapResultTreepOrderDriverInfo.get(MainActivity.KEY_LASTNAME).charAt(0) + ".  ");
	    callButton.setTypeface(fb);
	    
	    
	    tvTreeperOnBoardConfirm = (TextView) findViewById(R.id.tvTreeperOnBoardConfirm);
	    tvTreeperOnBoardConfirm.setText("Veuillez confirmer que vous êtes à bord de la voiture de " + mapResultTreepOrderDriverInfo.get(MainActivity.KEY_FIRSTNAME) + " " + mapResultTreepOrderDriverInfo.get(MainActivity.KEY_LASTNAME).charAt(0) + ".");
	
	    ivResponseDriverProfilePic = (ImageView) findViewById(R.id.ivResponseDriverProfilePic);

	    DownloadImage imageLoaderProfile = new DownloadImage(MainActivity.this,MainActivity.urlProfilePic(mapResultTreepOrderDriverInfo.get(MainActivity.KEY_DRIVERID)),ivResponseDriverProfilePic);
	    imageLoaderProfile.execute();
	    
	    
	    
	  }
	
	  @Override
	  public void onClick(View v) {
	    switch (v.getId()) {
	    case R.id.confirmButton:
			if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
					        	
				showSettingsAlert();
				dismiss();
	    	}
	    	else{
	    		SetTreeperOnBoardFromTreeperFromXML setTreeperOnBoardFromTreeperFromXML = new SetTreeperOnBoardFromTreeperFromXML(MainActivity.this, mapResultTreepOrderDriverInfo.get(MainActivity.KEY_TREEPID), mapResultTreepOrderDriverInfo.get(MainActivity.KEY_DRIVERID));
	    		setTreeperOnBoardFromTreeperFromXML.execute();
	    		dismiss();
	    	}
	      break;
	    case R.id.callButton:
	    	Intent callIntent = new Intent(Intent.ACTION_DIAL);
			callIntent.setData(Uri.parse("tel:"+ mapResultTreepOrderDriverInfo.get(MainActivity.KEY_USERTEL)));
			startActivity(callIntent);
	      break;
	    default:
	      break;
	    }
	  }
	}
 

	
	private class DialogDriverCanceled extends Dialog implements
    android.view.View.OnClickListener {
	
	  public Context context;
	  private HashMap<String, String> mapResultTreepOrderDriverInfo;
	  public Button confirmButton;
	  private TextView tvTreepCanceled;
	
	  private Typeface fb;
	
	  public DialogDriverCanceled(Context context, HashMap<String, String> mapResultTreepOrderDriverInfo) {
	    super(context);
	    // TODO Auto-generated constructor stub
	    this.context = context;
	    this.mapResultTreepOrderDriverInfo=mapResultTreepOrderDriverInfo;
	  }
	  
	  
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.dialog_treepcanceled);
	    fb = Typeface.createFromAsset(context.getAssets(), "fb.ttf");
	    confirmButton = (Button) findViewById(R.id.confirmButton);
	    confirmButton.setOnClickListener(this);
	    confirmButton.setTypeface(fb);
	    
	    tvTreepCanceled = (TextView) findViewById(R.id.tvTreepCanceled);
	    tvTreepCanceled.setText("Désolé, votre driver \n" + mapResultTreepOrderDriverInfo.get(MainActivity.KEY_FIRSTNAME) + " " + mapResultTreepOrderDriverInfo.get(MainActivity.KEY_LASTNAME).charAt(0) + ". a annulé le treep.");
	
	  }
	
	  @Override
	  public void onClick(View v) {
	    switch (v.getId()) {
	    case R.id.confirmButton:
			if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
					        	
				showSettingsAlert();
	    	}
	    	else{
	    		SetTreepCanceledFromDriverFromXML setTreepCanceledFromDriverFromXML = new SetTreepCanceledFromDriverFromXML(MainActivity.this, mapResultTreepOrderDriverInfo.get(MainActivity.KEY_TREEPID));
	    		setTreepCanceledFromDriverFromXML.execute();
          	  dismiss();
	    	}
	      break;
	    default:
	      break;
	    }
	    dismiss();
	  }
	}
	
	private class DialogTreepNotConfirmed extends Dialog implements
    android.view.View.OnClickListener {
	
	  public Context context;
	  public Button confirmButton;
	
	  private Typeface fb;
	
	  public DialogTreepNotConfirmed(Context context) {
	    super(context);
	    // TODO Auto-generated constructor stub
	    this.context = context;
	  }
	  
	  
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.dialog_treepnotconfirmed);
	    fb = Typeface.createFromAsset(context.getAssets(), "fb.ttf");
	    confirmButton = (Button) findViewById(R.id.confirmButton);
	    confirmButton.setOnClickListener(this);
	    confirmButton.setTypeface(fb);
	    
	  }
	
	  @Override
	  public void onClick(View v) {
	    switch (v.getId()) {
	    case R.id.confirmButton:
			if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
					        	
				showSettingsAlert();
	    	}
	    	else{
	    		dismiss();
	    		setProgressBarIndeterminateVisibility(true);
    	        MainActivity.fragment = new HomeFragment();
				getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_righttoleft, R.anim.slide_out_toptobottom,0,0).replace(R.id.frame_container, MainActivity.fragment).commit(); 	
	    	}
	      break;
	    default:
	      break;
	    }
	    dismiss();
	  }
	}
	
	
	private class DialogTreeperCanceled extends Dialog implements
    android.view.View.OnClickListener {
	
	  public Context context;
	  private HashMap<String, String> mapResultDriverTreepNow;
	  public Button confirmButton;
	  private TextView tvTreepCanceled;
	
	  private Typeface fb;
	
	  public DialogTreeperCanceled(Context context, HashMap<String, String> mapResultDriverTreepNow) {
	    super(context);
	    // TODO Auto-generated constructor stub
	    this.context = context;
	    this.mapResultDriverTreepNow=mapResultDriverTreepNow;
	  }
	  
	  
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.dialog_treepcanceled);
	    fb = Typeface.createFromAsset(context.getAssets(), "fb.ttf");
	    confirmButton = (Button) findViewById(R.id.confirmButton);
	    confirmButton.setOnClickListener(this);
	    confirmButton.setTypeface(fb);
	    
	    tvTreepCanceled = (TextView) findViewById(R.id.tvTreepCanceled);
	    tvTreepCanceled.setText("Désolé, " + mapResultDriverTreepNow.get(MainActivity.KEY_FIRSTNAME) + " " + mapResultDriverTreepNow.get(MainActivity.KEY_LASTNAME).charAt(0) + ". a annulé le treep.");
	
	  }
	
	  @Override
	  public void onClick(View v) {
	    switch (v.getId()) {
	    case R.id.confirmButton:
	    	if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
	        	
				showSettingsAlert();
	    	}
	    	else{
	    		SetRazTreepFinishedRatedDriverFromXML setRazTreepFinishedRatedDriverFromXML = new SetRazTreepFinishedRatedDriverFromXML(MainActivity.this,mapResultDriverTreepNow.get(MainActivity.KEY_TREEPID));
	    		setRazTreepFinishedRatedDriverFromXML.execute();
	    		dismiss();
	    	}
	      break;
	    default:
	      break;
	    }
	    dismiss();
	  }
	}
	
	private class DialogEvaluationConfirmedToTreeper extends Dialog implements
    android.view.View.OnClickListener {
	
	  public Context context;
	  private HashMap<String, String> mapResultTreepOrderInfo;
	  public Button confirmButton;
	
	  private Typeface fb;
	
	  public DialogEvaluationConfirmedToTreeper(Context context, HashMap<String, String> mapResultTreepOrderInfo) {
	    super(context);
	    // TODO Auto-generated constructor stub
	    this.context = context;
	    this.mapResultTreepOrderInfo=mapResultTreepOrderInfo;
	  }
	  
	  
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.dialog_evaluationconfirmation);
	    fb = Typeface.createFromAsset(context.getAssets(), "fb.ttf");
	    confirmButton = (Button) findViewById(R.id.confirmButton);
	    confirmButton.setOnClickListener(this);
	    confirmButton.setTypeface(fb);
	    
	  }
	
	  @Override
	  public void onClick(View v) {
	    switch (v.getId()) {
	    case R.id.confirmButton:
			if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
					        	
				showSettingsAlert();
	    	}
	    	else{
	    		SetRazTreepFinishedRatedTreeperFromXML setRazTreepFinishedRatedFromXML = new SetRazTreepFinishedRatedTreeperFromXML(MainActivity.this,mapResultTreepOrderInfo.get(MainActivity.KEY_TREEPID));
	    		setRazTreepFinishedRatedFromXML.execute();
          	  	dismiss();
	    	}
	      break;
	    default:
	      break;
	    }
	    dismiss();
	  }
	}
	
	private class DialogEvaluationConfirmedToDriver extends Dialog implements
    android.view.View.OnClickListener {
	
	  public Context context;
	  private HashMap<String, String> mapResultTreepNow;
	  public Button confirmButton;
	
	  private Typeface fb;
	
	  public DialogEvaluationConfirmedToDriver(Context context, HashMap<String, String> mapResultTreepNow) {
	    super(context);
	    // TODO Auto-generated constructor stub
	    this.context = context;
	    this.mapResultTreepNow=mapResultTreepNow;
	  }
	  
	  
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.dialog_evaluationconfirmation);
	    fb = Typeface.createFromAsset(context.getAssets(), "fb.ttf");
	    confirmButton = (Button) findViewById(R.id.confirmButton);
	    confirmButton.setOnClickListener(this);
	    confirmButton.setTypeface(fb);
	    
	  }
	
	  @Override
	  public void onClick(View v) {
	    switch (v.getId()) {
	    case R.id.confirmButton:
	    	if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
	        	
				showSettingsAlert();
	    	}
	    	else{
	    		SetRazTreepFinishedRatedDriverFromXML setRazTreepFinishedRatedDriverFromXML = new SetRazTreepFinishedRatedDriverFromXML(MainActivity.this,mapResultTreepNow.get(MainActivity.KEY_TREEPID));
	    		setRazTreepFinishedRatedDriverFromXML.execute();
          	  	dismiss();
	    	}
	      break;
	    default:
	      break;
	    }
	  }
	}
	
	
	private class DialogDriverTreepResponseAck extends Dialog implements
    android.view.View.OnClickListener {
	
	  public Context context;
	  private HashMap<String, String> mapResultDriverTreepNow;
	  public Button confirmButton;
	  private ImageView ivTreeperProfilePic;
	  private TextView tvTreeperTitle;
	  private TextView tvDep;
	  private TextView tvDest;
	
	  private Typeface fb;
	
	  public DialogDriverTreepResponseAck(Context context, HashMap<String, String> mapResultDriverTreepNow) {
	    super(context);
	    // TODO Auto-generated constructor stub
	    this.context = context;
	    this.mapResultDriverTreepNow=mapResultDriverTreepNow;
	  }
	  
	  
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.dialog_drivertreepnowresponseack);
	    fb = Typeface.createFromAsset(context.getAssets(), "fb.ttf");
	    
	    tvTreeperTitle = (TextView)findViewById(R.id.tvTreeperTitle);
	    
	    tvTreeperTitle.setText(mapResultDriverTreepNow.get(MainActivity.KEY_FIRSTNAME) + " " + mapResultDriverTreepNow.get(MainActivity.KEY_LASTNAME).charAt(0) + ". a accepté de treeper avec vous.");
	    
	    tvDep = (TextView)findViewById(R.id.tvDep);
	    
	    tvDep.setText(mapResultDriverTreepNow.get(MainActivity.KEY_ADDRESSDEPNOW));
	    tvDest = (TextView)findViewById(R.id.tvDest);
	    tvDest.setText(mapResultDriverTreepNow.get(MainActivity.KEY_ADDRESSDESTNOW));
	    confirmButton = (Button) findViewById(R.id.confirmButton);
	    confirmButton.setOnClickListener(this);
	    confirmButton.setTypeface(fb);
	    
	  }
	
	  @Override
	  public void onClick(View v) {
	    switch (v.getId()) {
	    case R.id.confirmButton:
	    	if(!MainActivity.CheckInternet(ApplicationContextProvider.getContext())){
	        	
				showSettingsAlert();
	    	}
	    	else{
	    		SetDriverTreepNowResponseAckFromXML setDriverTreepNowResponseAckFromXML = new SetDriverTreepNowResponseAckFromXML(MainActivity.this);
	    		setDriverTreepNowResponseAckFromXML.execute();
          	  	dismiss();
	    	}
	      break;
	    default:
	      break;
	    }
	  }
	}
	
	private class DialogSMSTreeperOnBoardSent extends Dialog implements
    android.view.View.OnClickListener {
	
	  public Context context;
	  private HashMap<String, String> mapResultDriverTreepNow;
	  public Button callButton;
	  public TextView tvSmsSentTitle;
	  public ImageView ivTreeperProfilePic;
	  private Typeface fb;
	
	  public DialogSMSTreeperOnBoardSent(Context context, HashMap<String, String> mapResultDriverTreepNow) {
	    super(context);
	    // TODO Auto-generated constructor stub
	    this.context = context;
	    this.mapResultDriverTreepNow=mapResultDriverTreepNow;
	  }
	  
	  
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.dialog_smstreeperonboardsent);
	    fb = Typeface.createFromAsset(context.getAssets(), "fb.ttf");
	    
	    ivTreeperProfilePic = (ImageView) findViewById(R.id.ivTreeperProfilePic);
	    ivTreeperProfilePic = (ImageView) findViewById(R.id.ivTreeperProfilePic);

	    DownloadImage imageLoaderProfile = new DownloadImage(MainActivity.this,MainActivity.urlProfilePic(mapResultDriverTreepNow.get(MainActivity.KEY_USERID)),ivTreeperProfilePic);
	    imageLoaderProfile.execute();
	    
	    tvSmsSentTitle = (TextView)findViewById(R.id.tvSmsSentTitle);
	    tvSmsSentTitle.setText("Un SMS a été envoyé à \n" + mapResultDriverTreepNow.get(MainActivity.KEY_FIRSTNAME) + " " + mapResultDriverTreepNow.get(MainActivity.KEY_LASTNAME).charAt(0) + ". pour prévenir que vous êtes arrivé.");
	    callButton = (Button) findViewById(R.id.callButton);
	    callButton.setOnClickListener(this);
	    callButton.setTypeface(fb);
	    
	    final Animation buttonScaleOnReleaseAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.button_scale_onrelease);
		buttonScaleOnReleaseAnim.setFillAfter(true);
		
		final Animation buttonScaleOnTouchAnim = AnimationUtils.loadAnimation(ApplicationContextProvider.getContext(),R.anim.button_scale_ontouch);
		buttonScaleOnTouchAnim.setFillAfter(true);
		
		OnTouchListener onTouchListenerButtonCall = new View.OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		    	if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
		    		callButton.startAnimation(buttonScaleOnTouchAnim);
				     
			    }
			    else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
			    	callButton.startAnimation(buttonScaleOnReleaseAnim);
			    
			    }
				return false;
		    }

		  };
		  
		  callButton.setOnTouchListener(onTouchListenerButtonCall);
	    
	  }
	
	  @Override
	  public void onClick(View v) {
	    switch (v.getId()) {
	    case R.id.callButton:
	    	Intent callIntent = new Intent(Intent.ACTION_DIAL);
	    	callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			callIntent.setData(Uri.parse("tel:"+ mapResultDriverTreepNow.get(MainActivity.KEY_USERTEL)));
			ApplicationContextProvider.getContext().startActivity(callIntent);
	      break;
	    default:
	      break;
	    }
	    dismiss();
	  }
	}
	
	
	
	private class DialogIceBreaker extends Dialog implements
    android.view.View.OnClickListener {
	
		public Context context;
		public TextView tvIceBreaker;
		public Button pushButton;
		public String[] iceBreakerString;
		public int randomNumber;
		public Typeface fb;
	
	  public DialogIceBreaker(Context context) {
	    super(context);
	    // TODO Auto-generated constructor stub
	    this.context = context;
	  }
	  
	  
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.dialog_icebreaker);
	    fb = Typeface.createFromAsset(context.getAssets(), "fb.ttf");
	    
	    tvIceBreaker = (TextView) findViewById(R.id.tvIceBreaker);
		iceBreakerString = getResources().getStringArray(R.array.icebreaker_items);
		
		pushButton = (Button)findViewById(R.id.pushButton);
	    pushButton.setOnClickListener(this);
	    
	  }
	
	  @Override
	  public void onClick(View v) {
	    switch (v.getId()) {
	    case R.id.pushButton:
	    	Random r = new Random();
	    	randomNumber = r.nextInt(iceBreakerString.length-1)+1;
	    	tvIceBreaker.setText("" +iceBreakerString[randomNumber]);
	    	tvIceBreaker.setGravity(1);
	      break;
	    default:
	      break;
	    }
	  }
	}
	
	
    
    public class StoreMainUserInfoFromXML extends AsyncTask<Void, Integer, HashMap<String, HashMap<String, String>>> {
		
		private MainActivity activity;
		
		private WeakReference<MainActivity> myWeakContext;
		
		private HashMap<String, HashMap<String, String>> mapMainUserInfo = new HashMap<String, HashMap<String, String>>();
		private HashMap<String, String> mapUserInfo = new HashMap<String, String>();
		private HashMap<String, String> mapTreepOrderInfo = new HashMap<String, String>();
		private HashMap<String, String> mapTreepOrderDriverInfo = new HashMap<String, String>();
		private HashMap<String, String> mapDriverUserInfo = new HashMap<String, String>();
		private HashMap<String, String> mapTreepRequest = new HashMap<String, String>();
		private HashMap<String, String> mapDriverTreepNow = new HashMap<String, String>();
		
		private ArrayList<HashMap<String, String>> alMapDriverPosition;
		
		private HashMap<String, String> mapConstants = new HashMap<String, String>();
		
		private HashMap<String, String> mapResultUserInfo = new HashMap<String, String>();
		private HashMap<String, String> mapResultTreepOrderInfo = new HashMap<String, String>();
		private HashMap<String, String> mapResultTreepOrderDriverInfo = new HashMap<String, String>();
		private HashMap<String, String> mapResultDriverUserInfo = new HashMap<String, String>();
		private HashMap<String, String> mapResultTreepRequest = new HashMap<String, String>();
		private HashMap<String, String> mapResultDriverTreepNow = new HashMap<String, String>();
		
		private HashMap<String, String> mapResultConstants = new HashMap<String, String>();
		
		private ArrayList<HashMap<String, String>> alUserPosition = new ArrayList<HashMap<String, String>>();
		
		
		public StoreMainUserInfoFromXML(MainActivity activity){
			this.myWeakContext = new WeakReference<MainActivity>(activity);
		}
		
		
		@SuppressLint("NewApi")
		private void LaunchIceBreakerFragment(){
			MainActivity.fragment = new IceBreakerFragment();
			
			showFragment(0, MainActivity.fragment);
			
		}
		
		@SuppressLint("NewApi")
		private void LaunchMyInformationFragment(){
			MainActivity.fragment = new MyInformationFragment();
			showFragment(0, MainActivity.fragment);
			}
		
		@SuppressLint("NewApi")
		private void LaunchRatingDriverNowFragment(HashMap<String, String> mapResultTreepOrderDriverInfo){
			MainActivity.fragment = new RatingDriverFragment();
			if(MainActivity.fragment == null){
				MainActivity.fragment = new BlankFragment();
				showFragment(0, MainActivity.fragment);
				
			}
			else{
				Bundle data = new Bundle();
				data.putSerializable("mapTreepOrderDriverInfo",mapResultTreepOrderDriverInfo);
				MainActivity.fragment.setArguments(data);
				showFragment(0, MainActivity.fragment);
			}
		}
		
		@SuppressLint("NewApi")
		private void LaunchRatingTreeperNowFragment(HashMap<String, String> mapResultDriverTreepNow){
			MainActivity.fragment = new RatingTreeperFragment();
			if(MainActivity.fragment == null){
				MainActivity.fragment = new BlankFragment();
				showFragment(0, MainActivity.fragment);

			}
			else{
				Bundle data = new Bundle();
				data.putSerializable("mapTreepNow",mapResultDriverTreepNow);
				MainActivity.fragment.setArguments(data);
				showFragment(0, MainActivity.fragment);
				
			}
		}
		
		
		@SuppressLint("NewApi")
		private void LaunchTreepRequestFragment(HashMap<String, String> mapResultTreepRequest){
			MainActivity.fragment = new TreepRequestFragment();
			if(MainActivity.fragment == null){
				MainActivity.fragment = new BlankFragment();
				showFragment(0, MainActivity.fragment);

			}
			else{
				Bundle data = new Bundle();
				data.putSerializable("mapTreepRequest",mapResultTreepRequest);
				MainActivity.fragment.setArguments(data);
				showFragment(0, MainActivity.fragment);
				
			}
		}
		
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			setProgressBarIndeterminateVisibility(true);

			try{
				if(dialogPleaseWait !=null){
					dialogPleaseWait.show();
				}
			}
			catch(BadTokenException e){
				
			}
			
			
		 	
			if(LoginDisplayActivity.userId == null || LoginDisplayActivity.userId == "" ){
				setProgressBarIndeterminateVisibility(false);
				this.cancel(true);
				Intent i = new Intent(MainActivity.this, LoginDisplayActivity.class);
        		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	            startActivity(i);
			}
			else{	
				mapMainUserInfo = new HashMap<String, HashMap<String, String>>();
				mapUserInfo = new HashMap<String, String>();
				mapTreepOrderInfo = new HashMap<String, String>();
				mapTreepOrderDriverInfo = new HashMap<String, String>();
				mapDriverUserInfo = new HashMap<String, String>();
				mapTreepRequest = new HashMap<String, String>();
				mapDriverTreepNow = new HashMap<String, String>();
				mapConstants = new HashMap<String, String>();
				alMapDriverPosition = new ArrayList<HashMap<String, String>>();

			}
			
		}

		@Override
		protected void onProgressUpdate(Integer... values){
			super.onProgressUpdate(values);
			//Toast.makeText(ApplicationContextProvider.getContext(), "traitement asynchrone", Toast.LENGTH_LONG).show();
			
		}

		@Override
		protected HashMap<String, HashMap<String, String>> doInBackground(Void... arg0) {

			HashMap<String,String> mapTimeout = new HashMap<String,String>();
			mapTimeout.put(MainActivity.KEY_TIMEOUT, "1");
			
			if (android.os.Build.VERSION.SDK_INT > 9) 
	    	{ StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }
		
	    	XMLParser parser = new XMLParser();
			String xml = parser.getXmlFromUrl(MainActivity.URL_USERINFO + "?userid=" + LoginDisplayActivity.userId +"&lat=" + myLatitude + "&lng=" + myLongitude);
			
			
			if(xml == null){
				mapMainUserInfo = null;
			}
			else{
				if(xml == "timeout"){
					
					mapUserInfo.put(MainActivity.KEY_TIMEOUT, "1");
					mapTreepOrderInfo.put(MainActivity.KEY_TIMEOUT, "1");	
					mapTreepOrderDriverInfo.put(MainActivity.KEY_TIMEOUT, "1");
					mapDriverUserInfo.put(MainActivity.KEY_TIMEOUT, "1");
					mapTreepRequest.put(MainActivity.KEY_TIMEOUT, "1");
					mapDriverTreepNow.put(MainActivity.KEY_TIMEOUT, "1");	
					mapConstants.put(MainActivity.KEY_TIMEOUT, "1");	
				}
				
				else{
					
					Document doc = null;
					try{
						doc = parser.getDomElement(xml); // getting DOM element
					}
					catch(DOMException e){
						
					}
					
					if(doc == null){
						mapUserInfo = null;
						mapTreepOrderInfo = null;
						mapTreepOrderDriverInfo = null;
						mapDriverUserInfo = null;
						mapTreepRequest = null;
						mapDriverTreepNow = null;
						mapConstants = null;
					}
					else{
						NodeList nl_User = doc.getElementsByTagName(MainActivity.KEY_USERINFO);
						// looping through all xml nodes <KEY_USER>
						for (int i = 0; i < nl_User.getLength(); i++) {
							// creating new HashMap
							
							Element e = (Element) nl_User.item(i);
							// adding each child node to HashMap key => value
							
							mapUserInfo.put(MainActivity.KEY_USERTEL, parser.getValue(e, MainActivity.KEY_USERTEL));
							mapUserInfo.put(MainActivity.KEY_USERSEXE, parser.getValue(e, MainActivity.KEY_USERSEXE));
							mapUserInfo.put(MainActivity.KEY_ISDRIVER, parser.getValue(e, MainActivity.KEY_ISDRIVER));
							mapUserInfo.put(MainActivity.KEY_DRIVERMODE, parser.getValue(e, MainActivity.KEY_DRIVERMODE));
							mapUserInfo.put(MainActivity.KEY_OILTYPE, parser.getValue(e, MainActivity.KEY_OILTYPE));
							mapUserInfo.put(MainActivity.KEY_HASTOUPDATE, parser.getValue(e, MainActivity.KEY_HASTOUPDATE));
							mapTreepRequest.put(MainActivity.KEY_CURRENTPOSITION, parser.getValue(e, MainActivity.KEY_CURRENTPOSITION));
							mapTreepRequest.put(MainActivity.KEY_CURRENTCOMPANY, parser.getValue(e, MainActivity.KEY_CURRENTCOMPANY));
							mapUserInfo.put(MainActivity.KEY_APPVERSIONCODE, parser.getValue(e, MainActivity.KEY_APPVERSIONCODE));
							
							
						}
						
						
						NodeList nl_DriverPositionList = doc.getElementsByTagName(MainActivity.KEY_DRIVERPOSITIONLIST);
						for (int i = 0; i < nl_DriverPositionList.getLength(); i++) {
							Element e = (Element) nl_DriverPositionList.item(i);
							NodeList nl_DriverPosition = e.getElementsByTagName(MainActivity.KEY_DRIVERPOSITION);
							// looping through all xml nodes <KEY_USER>
							for (int j = 0; j < nl_DriverPosition.getLength(); j++) {
								// creating new HashMap
								HashMap<String, String> mapDriverPosition = new HashMap<String, String>();
								Element t = (Element) nl_DriverPosition.item(j);
								// adding each child node to HashMap key => value
								mapDriverPosition.put(MainActivity.KEY_USERID, parser.getValue(t, MainActivity.KEY_USERID));
								mapDriverPosition.put(MainActivity.KEY_FIRSTNAME, parser.getValue(t, MainActivity.KEY_FIRSTNAME));
								mapDriverPosition.put(MainActivity.KEY_LASTNAME, parser.getValue(t, MainActivity.KEY_LASTNAME));
								mapDriverPosition.put(MainActivity.KEY_NBTREEP, parser.getValue(t, MainActivity.KEY_NBTREEP));
								mapDriverPosition.put(MainActivity.KEY_RATING, parser.getValue(t, MainActivity.KEY_RATING));
								mapDriverPosition.put(MainActivity.KEY_LATITUDE, parser.getValue(t, MainActivity.KEY_LATITUDE));
								mapDriverPosition.put(MainActivity.KEY_LONGITUDE, parser.getValue(t, MainActivity.KEY_LONGITUDE));
								mapDriverPosition.put(MainActivity.KEY_ISBUSY, parser.getValue(t, MainActivity.KEY_ISBUSY));
								mapDriverPosition.put(MainActivity.KEY_DRIVERTREEPREQUESTED, parser.getValue(t, MainActivity.KEY_DRIVERTREEPREQUESTED));
								mapDriverPosition.put(MainActivity.KEY_DRIVERTREEPREQUESTID, parser.getValue(t, MainActivity.KEY_DRIVERTREEPREQUESTID));
								mapDriverPosition.put(MainActivity.KEY_LATDEP, parser.getValue(t, MainActivity.KEY_LATDEP));
								mapDriverPosition.put(MainActivity.KEY_LNGDEP, parser.getValue(t, MainActivity.KEY_LNGDEP));
								mapDriverPosition.put(MainActivity.KEY_LATDEST, parser.getValue(t, MainActivity.KEY_LATDEST));
								mapDriverPosition.put(MainActivity.KEY_LNGDEST, parser.getValue(t, MainActivity.KEY_LNGDEST));
								
								// adding HashList to ArrayList
								
								alMapDriverPosition.add(mapDriverPosition);
							}
						}
						
						NodeList nl_TreepOrderInfo = doc.getElementsByTagName(MainActivity.KEY_TREEPORDERINFO);
						// looping through all xml nodes <KEY_USER>
						for (int i = 0; i < nl_TreepOrderInfo.getLength(); i++) {
							// creating new HashMap
							Element e = (Element) nl_TreepOrderInfo.item(i);
							// adding each child node to HashMap key => value
							mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPORDERED, parser.getValue(e, MainActivity.KEY_ISTREEPORDERED));
							mapTreepOrderInfo.put(MainActivity.KEY_TREEPID, parser.getValue(e, MainActivity.KEY_TREEPID));
							mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPNOWCONFIRMED, parser.getValue(e, MainActivity.KEY_ISTREEPNOWCONFIRMED));
							mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPNOWCANCELEDBYDRIVER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWCANCELEDBYDRIVER));
							mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPNOWCANCELEDBYTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWCANCELEDBYTREEPER));
							mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPERONBOARDFROMDRIVER, parser.getValue(e, MainActivity.KEY_ISTREEPERONBOARDFROMDRIVER));
							mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPERONBOARDFROMTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPERONBOARDFROMTREEPER));
							mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPNOWFINISHED, parser.getValue(e, MainActivity.KEY_ISTREEPNOWFINISHED));
							mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPNOWRATEDBYTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWRATEDBYTREEPER));
							mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPNOWRATEDBYDRIVER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWRATEDBYDRIVER));
							mapTreepOrderInfo.put(MainActivity.KEY_LATDEPNOW, parser.getValue(e, MainActivity.KEY_LATDEPNOW));
							mapTreepOrderInfo.put(MainActivity.KEY_LNGDEPNOW, parser.getValue(e, MainActivity.KEY_LNGDEPNOW));
							mapTreepOrderInfo.put(MainActivity.KEY_LATDESTNOW, parser.getValue(e, MainActivity.KEY_LATDESTNOW));
							mapTreepOrderInfo.put(MainActivity.KEY_LNGDESTNOW, parser.getValue(e, MainActivity.KEY_LNGDESTNOW));
							mapTreepOrderInfo.put(MainActivity.KEY_ADDRESSDEPNOW, parser.getValue(e, MainActivity.KEY_ADDRESSDEPNOW));
							mapTreepOrderInfo.put(MainActivity.KEY_ADDRESSDESTNOW, parser.getValue(e, MainActivity.KEY_ADDRESSDESTNOW));
							mapTreepOrderInfo.put(MainActivity.KEY_DRIVERID, parser.getValue(e, MainActivity.KEY_DRIVERID));
							mapTreepOrderInfo.put(MainActivity.KEY_PRICE, parser.getValue(e, MainActivity.KEY_PRICE));
							mapTreepOrderInfo.put(MainActivity.KEY_TREEPORDERTIMEREMAINING, parser.getValue(e, MainActivity.KEY_TREEPORDERTIMEREMAINING));
							mapTreepOrderInfo.put(MainActivity.KEY_TREEPRESPONSECOUNT, parser.getValue(e, MainActivity.KEY_TREEPRESPONSECOUNT));
						}
						
						NodeList nl_TreepOrderDriverInfo = doc.getElementsByTagName(MainActivity.KEY_TREEPORDERDRIVERINFO);
						// looping through all xml nodes <KEY_USER>
						for (int i = 0; i < nl_TreepOrderDriverInfo.getLength(); i++) {
							// creating new HashMap
							
							Element e = (Element) nl_TreepOrderDriverInfo.item(i);
							// adding each child node to HashMap key => value
							mapTreepOrderDriverInfo.put(MainActivity.KEY_TREEPID, parser.getValue(e, MainActivity.KEY_TREEPID));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_DRIVERID, parser.getValue(e, MainActivity.KEY_DRIVERID));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_FIRSTNAME, parser.getValue(e, MainActivity.KEY_FIRSTNAME));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_LASTNAME, parser.getValue(e, MainActivity.KEY_LASTNAME));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_CARMODEL, parser.getValue(e, MainActivity.KEY_CARMODEL));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_DISTANCETIME, parser.getValue(e, MainActivity.KEY_DISTANCETIME));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_USERTEL, parser.getValue(e, MainActivity.KEY_USERTEL));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_RATING, parser.getValue(e, MainActivity.KEY_RATING));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_NBTREEP, parser.getValue(e, MainActivity.KEY_NBTREEP));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_PRICE, parser.getValue(e, MainActivity.KEY_PRICE));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_LATITUDE, parser.getValue(e, MainActivity.KEY_LATITUDE));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_LONGITUDE, parser.getValue(e, MainActivity.KEY_LONGITUDE));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_CARPICURL, parser.getValue(e, MainActivity.KEY_CARPICURL));
						}
						
						NodeList nl_DriverUserInfo = doc.getElementsByTagName(MainActivity.KEY_DRIVERUSERINFO);
						// looping through all xml nodes <KEY_USER>
						for (int i = 0; i < nl_DriverUserInfo.getLength(); i++) {
							// creating new HashMap
							
							Element e = (Element) nl_DriverUserInfo.item(i);
							// adding each child node to HashMap key => valuedrivertreepnowconfirmedcount
							mapDriverUserInfo.put(MainActivity.KEY_DRIVERTREEPREQUESTED, parser.getValue(e, MainActivity.KEY_DRIVERTREEPREQUESTED));
							mapDriverUserInfo.put(MainActivity.KEY_DRIVERTREEPREQUESTID, parser.getValue(e, MainActivity.KEY_DRIVERTREEPREQUESTID));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPREQUESTEDFROMTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPREQUESTEDFROMTREEPER));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWRESPONSEACK, parser.getValue(e, MainActivity.KEY_ISTREEPNOWRESPONSEACK));
							mapDriverUserInfo.put(MainActivity.KEY_DRIVERTREEPNOWCONFIRMEDCOUNT, parser.getValue(e, MainActivity.KEY_DRIVERTREEPNOWCONFIRMEDCOUNT));
							mapDriverUserInfo.put(MainActivity.KEY_TREEPNOWCONFIRMEDTIMEREMAINING, parser.getValue(e, MainActivity.KEY_TREEPNOWCONFIRMEDTIMEREMAINING));
							mapDriverUserInfo.put(MainActivity.KEY_TREEPID, parser.getValue(e, MainActivity.KEY_TREEPID));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWACCEPTED, parser.getValue(e, MainActivity.KEY_ISTREEPNOWACCEPTED));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWFINISHED, parser.getValue(e, MainActivity.KEY_ISTREEPNOWFINISHED));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWRATEDBYDRIVER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWRATEDBYDRIVER));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWRATEDBYTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWRATEDBYTREEPER));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWCANCELEDBYDRIVER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWCANCELEDBYDRIVER));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWCANCELEDBYTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWCANCELEDBYTREEPER));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPERONBOARDFROMDRIVER, parser.getValue(e, MainActivity.KEY_ISTREEPERONBOARDFROMDRIVER));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPERONBOARDFROMTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPERONBOARDFROMTREEPER));
						}
						
						NodeList nl_TreepRequest = doc.getElementsByTagName(MainActivity.KEY_TREEPREQUEST);
						// looping through all xml nodes <KEY_USER>
						for (int i = 0; i < nl_TreepRequest.getLength(); i++) {
							// creating new HashMap
							
							Element e = (Element) nl_TreepRequest.item(i);
							// adding each child node to HashMap key => valuedrivertreepnowconfirmedcount
							mapTreepRequest.put(MainActivity.KEY_REQUESTID, parser.getValue(e, MainActivity.KEY_REQUESTID));
							mapTreepRequest.put(MainActivity.KEY_DRIVERTREEPREQUESTID, parser.getValue(e, MainActivity.KEY_DRIVERTREEPREQUESTID));
							mapTreepRequest.put(MainActivity.KEY_TREEPID, parser.getValue(e, MainActivity.KEY_TREEPID));
							mapTreepRequest.put(MainActivity.KEY_USERID, parser.getValue(e, MainActivity.KEY_USERID));
							mapTreepRequest.put(MainActivity.KEY_FIRSTNAME, parser.getValue(e, MainActivity.KEY_FIRSTNAME));
							mapTreepRequest.put(MainActivity.KEY_LASTNAME, parser.getValue(e, MainActivity.KEY_LASTNAME));
							mapTreepRequest.put(MainActivity.KEY_TIMEREMAINING, parser.getValue(e, MainActivity.KEY_TIMEREMAINING));
							mapTreepRequest.put(MainActivity.KEY_DETOUR, parser.getValue(e, MainActivity.KEY_DETOUR));
							mapTreepRequest.put(MainActivity.KEY_RATING, parser.getValue(e, MainActivity.KEY_RATING));
							mapTreepRequest.put(MainActivity.KEY_CURRENTPOSITION, parser.getValue(e, MainActivity.KEY_CURRENTPOSITION));
							mapTreepRequest.put(MainActivity.KEY_CURRENTCOMPANY, parser.getValue(e, MainActivity.KEY_CURRENTCOMPANY));
							mapTreepRequest.put(MainActivity.KEY_NBTREEP, parser.getValue(e, MainActivity.KEY_NBTREEP));
							mapTreepRequest.put(MainActivity.KEY_PRICE, parser.getValue(e, MainActivity.KEY_PRICE));
							mapTreepRequest.put(MainActivity.KEY_LATDEPNOW, parser.getValue(e, MainActivity.KEY_LATDEPNOW));
							mapTreepRequest.put(MainActivity.KEY_LNGDEPNOW, parser.getValue(e, MainActivity.KEY_LNGDEPNOW));
							mapTreepRequest.put(MainActivity.KEY_LATDESTNOW, parser.getValue(e, MainActivity.KEY_LATDESTNOW));
							mapTreepRequest.put(MainActivity.KEY_LNGDESTNOW, parser.getValue(e, MainActivity.KEY_LNGDESTNOW));
							mapTreepRequest.put(MainActivity.KEY_ADDRESSDEPNOW, parser.getValue(e, MainActivity.KEY_ADDRESSDEPNOW));
							mapTreepRequest.put(MainActivity.KEY_ADDRESSDESTNOW, parser.getValue(e, MainActivity.KEY_ADDRESSDESTNOW));
							
						}
						
						NodeList nl_DriverTreepNow = doc.getElementsByTagName(MainActivity.KEY_DRIVERTREEPNOW);
						// looping through all xml nodes <KEY_USER>
						for (int i = 0; i < nl_DriverTreepNow.getLength(); i++) {
							// creating new HashMap
							
							Element e = (Element) nl_DriverTreepNow.item(i);
							// adding each child node to HashMap key => value
							mapDriverTreepNow.put(MainActivity.KEY_TREEPID, parser.getValue(e, MainActivity.KEY_TREEPID));
							mapDriverTreepNow.put(MainActivity.KEY_USERID, parser.getValue(e, MainActivity.KEY_USERID));
							mapDriverTreepNow.put(MainActivity.KEY_FIRSTNAME, parser.getValue(e, MainActivity.KEY_FIRSTNAME));
							mapDriverTreepNow.put(MainActivity.KEY_LASTNAME, parser.getValue(e, MainActivity.KEY_LASTNAME));
							mapDriverTreepNow.put(MainActivity.KEY_DISTANCETIME, parser.getValue(e, MainActivity.KEY_DISTANCETIME));
							mapDriverTreepNow.put(MainActivity.KEY_USERTEL, parser.getValue(e, MainActivity.KEY_USERTEL));
							mapDriverTreepNow.put(MainActivity.KEY_RATING, parser.getValue(e, MainActivity.KEY_RATING));
							mapDriverTreepNow.put(MainActivity.KEY_CURRENTPOSITION, parser.getValue(e, MainActivity.KEY_CURRENTPOSITION));
							mapDriverTreepNow.put(MainActivity.KEY_CURRENTCOMPANY, parser.getValue(e, MainActivity.KEY_CURRENTCOMPANY));
							mapDriverTreepNow.put(MainActivity.KEY_NBTREEP, parser.getValue(e, MainActivity.KEY_NBTREEP));
							mapDriverTreepNow.put(MainActivity.KEY_PRICE, parser.getValue(e, MainActivity.KEY_PRICE));
							mapDriverTreepNow.put(MainActivity.KEY_LATITUDE, parser.getValue(e, MainActivity.KEY_LATITUDE));
							mapDriverTreepNow.put(MainActivity.KEY_LONGITUDE, parser.getValue(e, MainActivity.KEY_LONGITUDE));
							mapDriverTreepNow.put(MainActivity.KEY_LATDEPNOW, parser.getValue(e, MainActivity.KEY_LATDEPNOW));
							mapDriverTreepNow.put(MainActivity.KEY_LNGDEPNOW, parser.getValue(e, MainActivity.KEY_LNGDEPNOW));
							mapDriverTreepNow.put(MainActivity.KEY_LATDESTNOW, parser.getValue(e, MainActivity.KEY_LATDESTNOW));
							mapDriverTreepNow.put(MainActivity.KEY_LNGDESTNOW, parser.getValue(e, MainActivity.KEY_LNGDESTNOW));
							mapDriverTreepNow.put(MainActivity.KEY_ADDRESSDEPNOW, parser.getValue(e, MainActivity.KEY_ADDRESSDEPNOW));
							mapDriverTreepNow.put(MainActivity.KEY_ADDRESSDESTNOW, parser.getValue(e, MainActivity.KEY_ADDRESSDESTNOW));
							
						}
						
						NodeList nl_UserPositionList = doc.getElementsByTagName(MainActivity.KEY_USERPOSITIONLIST);
						for (int i = 0; i < nl_UserPositionList.getLength(); i++) {
							Element e = (Element) nl_UserPositionList.item(i);
							NodeList nl_UserPosition = e.getElementsByTagName(MainActivity.KEY_USERPOSITION);
							// looping through all xml nodes <KEY_USER>
							for (int j = 0; j < nl_UserPosition.getLength(); j++) {
								// creating new HashMap
								HashMap<String, String> mapUserPosition = new HashMap<String, String>();
								Element t = (Element) nl_UserPosition.item(j);
								// adding each child node to HashMap key => value
								mapUserPosition.put(MainActivity.KEY_USERID, parser.getValue(t, MainActivity.KEY_USERID));
								mapUserPosition.put(MainActivity.KEY_FIRSTNAME, parser.getValue(t, MainActivity.KEY_FIRSTNAME));
								mapUserPosition.put(MainActivity.KEY_LASTNAME, parser.getValue(t, MainActivity.KEY_LASTNAME));
								mapUserPosition.put(MainActivity.KEY_NBTREEP, parser.getValue(t, MainActivity.KEY_NBTREEP));
								mapUserPosition.put(MainActivity.KEY_RATING, parser.getValue(t, MainActivity.KEY_RATING));
								mapUserPosition.put(MainActivity.KEY_LATITUDE, parser.getValue(t, MainActivity.KEY_LATITUDE));
								mapUserPosition.put(MainActivity.KEY_LONGITUDE, parser.getValue(t, MainActivity.KEY_LONGITUDE));
								mapUserPosition.put(MainActivity.KEY_ISBUSY, parser.getValue(t, MainActivity.KEY_ISBUSY));
								
								// adding HashList to ArrayList
								
								alUserPosition.add(mapUserPosition);
							}
						}
						
						NodeList nl_BasePricing = doc.getElementsByTagName(MainActivity.KEY_CONSTANTS);
						// looping through all xml nodes <KEY_USER>
						for (int i = 0; i < nl_BasePricing.getLength(); i++) {
							// creating new HashMap
							
							Element e = (Element) nl_BasePricing.item(i);
							// adding each child node to HashMap key => value
							mapConstants.put(MainActivity.KEY_GAZOLEPRICE, parser.getValue(e, MainActivity.KEY_GAZOLEPRICE));
							mapConstants.put(MainActivity.KEY_GASOLINEPRICE, parser.getValue(e, MainActivity.KEY_GASOLINEPRICE));
							mapConstants.put(MainActivity.KEY_TREEPRATEKM, parser.getValue(e, MainActivity.KEY_TREEPRATEKM));
							
						}
						
						
						mapMainUserInfo.put("mapUserInfo", mapUserInfo);
						mapMainUserInfo.put("mapTreepOrderInfo", mapTreepOrderInfo);
						mapMainUserInfo.put("mapTreepOrderDriverInfo", mapTreepOrderDriverInfo);
						mapMainUserInfo.put("mapDriverUserInfo", mapDriverUserInfo);
						mapMainUserInfo.put("mapTreepRequest", mapTreepRequest);
						mapMainUserInfo.put("mapDriverTreepNow", mapDriverTreepNow);
						mapMainUserInfo.put("mapConstants", mapConstants);
						
						
					}
				}
			}
				
			return mapMainUserInfo;
		}

		@SuppressLint("NewApi")
		protected void onPostExecute(HashMap<String, HashMap<String, String>>  result) {
			
			MainActivity activity = this.myWeakContext.get();
			
			
			if(result == null){
				
				fragment = new BlankFragment();
				showFragment(0, fragment);
				
				try{
					dialogTimeout.show();
				}
				catch(BadTokenException e){
					
				}
			}
			else{
				if(result.containsKey("mapUserInfo")){
					mapResultUserInfo = result.get("mapUserInfo");
					
				}				
				
				if(result.containsKey("mapTreepOrderInfo")){
					mapResultTreepOrderInfo = result.get("mapTreepOrderInfo");
				}
				
				if(result.containsKey("mapTreepOrderDriverInfo")){
					mapResultTreepOrderDriverInfo = result.get("mapTreepOrderDriverInfo");
				}
				
				if(result.containsKey("mapDriverUserInfo")){
					mapResultDriverUserInfo = result.get("mapDriverUserInfo");
				}
				
				if(result.containsKey("mapTreepRequest")){
					mapResultTreepRequest = result.get("mapTreepRequest");
				}
				if(result.containsKey("mapDriverTreepNow")){
					mapResultDriverTreepNow = result.get("mapDriverTreepNow");
				}
				
				if(result.containsKey("mapConstants")){
					mapResultConstants = result.get("mapConstants");
				}
				
				
				if(mapResultUserInfo == null || mapResultUserInfo.containsKey(MainActivity.KEY_TIMEOUT) 
						|| mapResultTreepOrderInfo == null || mapResultTreepOrderInfo.containsKey(MainActivity.KEY_TIMEOUT)
						|| mapResultTreepOrderDriverInfo== null || mapResultTreepOrderDriverInfo.containsKey(MainActivity.KEY_TIMEOUT)
						|| mapResultDriverUserInfo== null || mapResultDriverUserInfo.containsKey(MainActivity.KEY_TIMEOUT)
						|| mapResultDriverTreepNow== null || mapResultDriverTreepNow.containsKey(MainActivity.KEY_TIMEOUT)
						|| mapResultConstants== null || mapResultConstants.containsKey(MainActivity.KEY_TIMEOUT)){
		    		
					fragment = new BlankFragment();
					showFragment(0, fragment);
					DialogTimeout dialogTimeout = new DialogTimeout(MainActivity.this);
					try{
						dialogTimeout.show();
					}
					catch(BadTokenException e){
						
					}		    		
		    	}
				else{
					
					if(mapResultConstants.get(MainActivity.KEY_TREEPRATEKM) != null){
						VALUE_TREEPRATEKM = Double.parseDouble(mapResultConstants.get(MainActivity.KEY_TREEPRATEKM));
					}
					
					if(mapResultUserInfo.get(MainActivity.KEY_OILTYPE) != null && Boolean.parseBoolean(mapResultUserInfo.get(MainActivity.KEY_OILTYPE))){
					    VALUE_CONSUMPTIONPRICEPERKM = Double.parseDouble(mapResultConstants.get(MainActivity.KEY_GAZOLEPRICE));
					}
					else{
						VALUE_CONSUMPTIONPRICEPERKM = Double.parseDouble(mapResultConstants.get(MainActivity.KEY_GASOLINEPRICE));
					}
					
					MainActivity.driverMode=Boolean.parseBoolean(mapResultUserInfo.get(MainActivity.KEY_DRIVERMODE));
					MainActivity.navDrawerItems.remove(MainActivity.navDrawerItems.size()-1);
					MainActivity.navDrawerItems.add(new NavDrawerItem(MainActivity.navMenuTitles[5], MainActivity.navMenuSubTitles[5], R.drawable.drivermodeoff));
					MainActivity.adapter.notifyDataSetChanged();
					if(Boolean.parseBoolean(mapResultUserInfo.get(MainActivity.KEY_DRIVERMODE))){
						MainActivity.navDrawerItems.remove(MainActivity.navDrawerItems.size()-1);
						MainActivity.navDrawerItems.add(new NavDrawerItem(MainActivity.navMenuTitles[5], MainActivity.navMenuSubTitles[5],R.drawable.drivermodeon));
						MainActivity.adapter.notifyDataSetChanged();
					}
					else{
						MainActivity.navDrawerItems.remove(MainActivity.navDrawerItems.size()-1);
						MainActivity.navDrawerItems.add(new NavDrawerItem(MainActivity.navMenuTitles[5], MainActivity.navMenuSubTitles[5], R.drawable.drivermodeoff));
						MainActivity.adapter.notifyDataSetChanged();
					}
					
					try{
						appVersionFromXML = Integer.parseInt(mapResultUserInfo.get(MainActivity.KEY_APPVERSIONCODE));
					}
					catch(NumberFormatException e){
						
					}
					
					if(Integer.parseInt(MainActivity.appVersion) < appVersionFromXML){
						
						CURRENT_STEP = STEP_HASTOUPDATE;

						fragment = new HasToUpdateFragment();
						showFragment(0, fragment);
						
					}
					else{
						if(mapResultUserInfo.get(MainActivity.KEY_USERTEL) != null){
							if(!Boolean.parseBoolean(mapResultUserInfo.get(MainActivity.KEY_DRIVERMODE))){
								
								PushService.unsubscribe(ApplicationContextProvider.getContext(), "drivers");
								
								if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPORDERED))){
									isTreepOrderedByTreeper = true;
									if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPNOWCONFIRMED))){
										if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPNOWCANCELEDBYDRIVER))){
											
											CURRENT_STEP = STEP_USERDRIVERCANCELED;
											
											DialogDriverCanceled dialogDriverCanceled = new DialogDriverCanceled(activity,mapResultTreepOrderDriverInfo);
											dialogDriverCanceled.setCancelable(false);
											try{
												dialogDriverCanceled.show();
											}
											catch(BadTokenException e){
												
											}
										}
										else{
											if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPERONBOARDFROMDRIVER))){
												if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPERONBOARDFROMTREEPER))){
													if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPNOWFINISHED))){
														if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPNOWRATEDBYTREEPER))){					
															CURRENT_STEP = STEP_USERTREEPFINISHED;
															try{
																DialogEvaluationConfirmedToTreeper dialogEvaluationConfirmedToTreeper = new DialogEvaluationConfirmedToTreeper(MainActivity.this,mapResultTreepOrderInfo);
																dialogEvaluationConfirmedToTreeper.setCancelable(false);
																dialogEvaluationConfirmedToTreeper.show();
															}
															catch(BadTokenException e){
																
															}
															
														}
														else{
															
															CURRENT_STEP = STEP_USERTREEPFINISHED;
															
															LaunchRatingDriverNowFragment(mapResultTreepOrderDriverInfo);
														}
													}
													else{
														
														CURRENT_STEP = STEP_USERONBOARD;
														
														fragment = new IceBreakerFragment();
														showFragment(0, fragment);
													}
												}
												else{
													CURRENT_STEP = STEP_USERCONFIRMONBOARD;
													
													fragment = new BlankFragment();
													showFragment(0, fragment);
													try{
														DialogTreeperOnBoardFromTreeper dialogTreeperOnBoardFromTreeper = new DialogTreeperOnBoardFromTreeper(activity,mapResultTreepOrderDriverInfo);
														dialogTreeperOnBoardFromTreeper.show();
														
													}
													catch(BadTokenException e){
														
													}
												}
											}
											else{
												
												CURRENT_STEP = STEP_USERTREEPACCEPTED;
												
												fragment = new MapUserTreepAcceptedFragment();
												Bundle extras = new Bundle();
												extras.putSerializable("mapTreepOrderInfo",mapResultTreepOrderInfo);
												extras.putSerializable("mapTreepOrderDriverInfo",mapResultTreepOrderDriverInfo);
												fragment.setArguments(extras);
												showFragment(0, fragment);
											}
										}
									}
									else{
										
										CURRENT_STEP = STEP_USERTREEPORDERED;
										
										fragment = new TreepRequestedNoMatchFragment();
										Bundle extras = new Bundle();
										extras.putSerializable("alDriverPosition",alMapDriverPosition);
										extras.putSerializable("mapUserInfo",mapResultUserInfo);
										extras.putSerializable("mapTreepOrderInfo",mapResultTreepOrderInfo);
										fragment.setArguments(extras);
										showFragment(0, fragment);
									}
								}
								else{
									
									CURRENT_STEP = STEP_HOME;
									fragment= new HomeFragment();
									Bundle extras = new Bundle();
									//extras.putSerializable("alDriverPosition",alMapDriverPosition);
									
									HomeFragment.alMapDriverPosition = alMapDriverPosition;
									
									extras.putSerializable("mapUserInfo",mapResultUserInfo);
									fragment.setArguments(extras);
									showFragment(0, fragment);
								}
							}
							else{
								
								driverMode = true;
								
								if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_DRIVERTREEPREQUESTED))){
									if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_ISTREEPREQUESTEDFROMTREEPER))){
										if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_ISTREEPNOWACCEPTED))){
											if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_ISTREEPNOWCANCELEDBYTREEPER))){
												CURRENT_STEP = STEP_DRIVERTREEPERCANCELED;
												
												DialogTreeperCanceled dialogTreeperCanceled = new DialogTreeperCanceled(activity,mapResultDriverTreepNow);
												dialogTreeperCanceled.setCancelable(false);
												try {
														dialogTreeperCanceled.show();
											    } 
												catch (BadTokenException e){
											    }
											}
											else{
												if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_ISTREEPERONBOARDFROMDRIVER))){
													if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_ISTREEPERONBOARDFROMTREEPER))){
														isTreeperOnBoard = true;
														isDriverAccepted = false;
														
														if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_ISTREEPNOWFINISHED))){
															if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_ISTREEPNOWRATEDBYDRIVER))){
																CURRENT_STEP = STEP_DRIVERTREEPFINISHED;
																
																DialogEvaluationConfirmedToDriver dialogEvaluationConfirmedToDriver = new DialogEvaluationConfirmedToDriver(activity,mapResultDriverTreepNow);
																dialogEvaluationConfirmedToDriver.setCancelable(false);
																try {
																	dialogEvaluationConfirmedToDriver.show();
															    } 
																catch (BadTokenException e){
																	
															    }
															}
															else{
																CURRENT_STEP = STEP_DRIVERTREEPFINISHED;
																
																LaunchRatingTreeperNowFragment(mapResultDriverTreepNow);
															}
														}
														else{
															CURRENT_STEP = STEP_DRIVERTREEPACCEPTEDTREEPERONBOARD;
															
															fragment= new MapTreepConfirmed();
															Bundle extras = new Bundle();
															extras.putSerializable("mapDriverUserInfo",mapResultDriverUserInfo);
															extras.putSerializable("mapDriverTreepNow",mapResultDriverTreepNow);
															fragment.setArguments(extras);
															showFragment(0, fragment);
														}
													}
													else{
														CURRENT_STEP = STEP_DRIVERTREEPACCEPTEDWAITFORTREEPER;
														
														fragment= new BlankFragment();
														showFragment(0,fragment);
														DialogSMSTreeperOnBoardSent dialogSMSTreeperOnBoardSent = new DialogSMSTreeperOnBoardSent(activity,mapResultDriverTreepNow);
														try {
															dialogSMSTreeperOnBoardSent.show();
													    } 
														catch (BadTokenException e){
													    }
													}
												}
												else{
													isTreeperOnBoard = false;
													isDriverAccepted = false;
													PushService.unsubscribe(ApplicationContextProvider.getContext(), "drivers");
													
													CURRENT_STEP = STEP_DRIVERTREEPACCEPTEDTREEPERNOTONBOARD;
													
													fragment= new MapTreepConfirmed();
													Bundle extras = new Bundle();
													extras.putSerializable("mapDriverUserInfo",mapResultDriverUserInfo);
													extras.putSerializable("mapDriverTreepNow",mapResultDriverTreepNow);
													fragment.setArguments(extras);
													showFragment(0, fragment);
												}
											}
											
										}
										else{
											CURRENT_STEP = STEP_TREEPREQUEST;
											if(mapResultTreepRequest == null || mapResultTreepRequest.containsKey(MainActivity.KEY_TIMEOUT)){
												
											}
											else{
												LaunchTreepRequestFragment(mapResultTreepRequest);
											}
										}
									}
									else{
										CURRENT_STEP = STEP_DRIVERTREEPREQUESTEDNOTREEPER;
										fragment= new TreepRequestedNoMatchFragment();
										Bundle extras = new Bundle();
										extras.putSerializable("alDriverPosition",alMapDriverPosition);
										
										showFragment(0, fragment);
										//DriverTreepRequestedNoTreeperFragment
									}
								}
								else{
									CURRENT_STEP = STEP_HOME;
									fragment= new HomeFragment();
									Bundle extras = new Bundle();
									//extras.putSerializable("alDriverPosition",alMapDriverPosition);
									
									HomeFragment.alMapDriverPosition = alMapDriverPosition;
									
									extras.putSerializable("mapUserInfo",mapResultUserInfo);
									fragment.setArguments(extras);
									showFragment(0, fragment);
								}
							}
						}
						else{
							CURRENT_STEP = STEP_USERINFORMATION;
							LaunchMyInformationFragment();
						}
					}
					
					
				}
			}
			
			if(dialogPleaseWait != null){
				dialogPleaseWait.dismiss();
			}
			
			//storeUserInfo();
			
			setProgressBarIndeterminateVisibility(false);
		}
		
		 public void attach(MainActivity activity) {
		        this.myWeakContext = new WeakReference<MainActivity>(activity);
		    }
	}
    
    
public class UpdateUserInfoFromXML extends AsyncTask<Void, Integer, HashMap<String, HashMap<String, String>>> {
		
		private MainActivity activity;
		
		private WeakReference<MainActivity> myWeakContext;
		
		private HashMap<String, HashMap<String, String>> mapMainUserInfo = new HashMap<String, HashMap<String, String>>();
		private HashMap<String, String> mapUserInfo = new HashMap<String, String>();
		private HashMap<String, String> mapTreepOrderInfo = new HashMap<String, String>();
		private HashMap<String, String> mapTreepOrderDriverInfo = new HashMap<String, String>();
		private HashMap<String, String> mapDriverUserInfo = new HashMap<String, String>();
		private HashMap<String, String> mapTreepRequest = new HashMap<String, String>();
		private HashMap<String, String> mapDriverTreepNow = new HashMap<String, String>();
		
		private HashMap<String, String> mapResultUserInfo = new HashMap<String, String>();
		private HashMap<String, String> mapResultTreepOrderInfo = new HashMap<String, String>();
		private HashMap<String, String> mapResultTreepOrderDriverInfo = new HashMap<String, String>();
		private HashMap<String, String> mapResultDriverUserInfo = new HashMap<String, String>();
		private HashMap<String, String> mapResultTreepRequest = new HashMap<String, String>();
		private HashMap<String, String> mapResultDriverTreepNow = new HashMap<String, String>();
		private ArrayList<HashMap<String, String>> alDriverPosition = new ArrayList<HashMap<String, String>>();
		private ArrayList<HashMap<String, String>> alUserPosition = new ArrayList<HashMap<String, String>>();
		
		
		private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		public UpdateUserInfoFromXML(MainActivity activity){
			this.myWeakContext = new WeakReference<MainActivity>(activity);
		}
		
		
		@SuppressLint("NewApi")
		private void LaunchIceBreakerFragment(){
			MainActivity.fragment = new IceBreakerFragment();
			
			showFragment(0, MainActivity.fragment);
			
		}
		
		@SuppressLint("NewApi")
		private void LaunchMyInformationFragment(){
			MainActivity.fragment = new MyInformationFragment();
			showFragment(0, MainActivity.fragment);
			}
		
	
		@SuppressLint("NewApi")
		private void LaunchRatingDriverNowFragment(HashMap<String, String> mapResultTreepOrderDriverInfo){
			MainActivity.fragment = new RatingDriverFragment();
			if(MainActivity.fragment == null){
				MainActivity.fragment = new BlankFragment();
				showFragment(0, MainActivity.fragment);
				
			}
			else{
				Bundle data = new Bundle();
				data.putSerializable("mapTreepOrderDriverInfo",mapResultTreepOrderDriverInfo);
				MainActivity.fragment.setArguments(data);
				showFragment(0, MainActivity.fragment);
			}
		}
		
		@SuppressLint("NewApi")
		private void LaunchTreepRequestFragment(HashMap<String, String> mapResultTreepRequest){
			MainActivity.fragment = new TreepRequestFragment();
			if(MainActivity.fragment == null){
				MainActivity.fragment = new BlankFragment();
				showFragment(0, MainActivity.fragment);

			}
			else{
				Bundle data = new Bundle();
				data.putSerializable("mapTreepRequest",mapResultTreepRequest);
				MainActivity.fragment.setArguments(data);
				showFragment(0, MainActivity.fragment);
			}
		}
		
		@SuppressLint("NewApi")
		private void LaunchRatingTreeperNowFragment(HashMap<String, String> mapResultDriverTreepNow){
			MainActivity.fragment = new RatingTreeperFragment();
			if(MainActivity.fragment == null){
				MainActivity.fragment = new BlankFragment();
				showFragment(0, MainActivity.fragment);

			}
			else{
				Bundle data = new Bundle();
				data.putSerializable("mapTreepNow",mapResultDriverTreepNow);
				MainActivity.fragment.setArguments(data);
				showFragment(0, MainActivity.fragment);
				
			}
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			setProgressBarIndeterminateVisibility(true);

			try{
				if(dialogPleaseWait !=null){
					dialogPleaseWait.show();
				}
			}
			catch(BadTokenException e){
				
			}
			
			
		 	
			if(LoginDisplayActivity.userId == null || LoginDisplayActivity.userId == "" ){
				setProgressBarIndeterminateVisibility(false);
				this.cancel(true);
				Intent i = new Intent(MainActivity.this, LoginDisplayActivity.class);
        		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	            startActivity(i);
			}
			else{	
				mapMainUserInfo = new HashMap<String, HashMap<String, String>>();
				mapUserInfo = new HashMap<String, String>();
				mapTreepOrderInfo = new HashMap<String, String>();
				mapTreepOrderDriverInfo = new HashMap<String, String>();
				mapDriverUserInfo = new HashMap<String, String>();
				mapTreepRequest = new HashMap<String, String>();
				mapDriverTreepNow = new HashMap<String, String>();

			}
			
		}

		@Override
		protected void onProgressUpdate(Integer... values){
			super.onProgressUpdate(values);
			//Toast.makeText(ApplicationContextProvider.getContext(), "traitement asynchrone", Toast.LENGTH_LONG).show();
			
		}

		@Override
		protected HashMap<String, HashMap<String, String>> doInBackground(Void... arg0) {

			HashMap<String,String> mapTimeout = new HashMap<String,String>();
			mapTimeout.put(MainActivity.KEY_TIMEOUT, "1");
			
			if (android.os.Build.VERSION.SDK_INT > 9) 
	    	{ StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }
		
	    	XMLParser parser = new XMLParser();
			String xml = parser.getXmlFromUrl(MainActivity.URL_USERINFO + "?userid=" + LoginDisplayActivity.userId + "&lat=" + myLatitude + "&lng=" + myLongitude);
			
			
			if(xml == null){
				mapMainUserInfo = null;
			}
			else{
				if(xml == "timeout"){
					
					mapUserInfo.put(MainActivity.KEY_TIMEOUT, "1");
					mapTreepOrderInfo.put(MainActivity.KEY_TIMEOUT, "1");	
					mapTreepOrderDriverInfo.put(MainActivity.KEY_TIMEOUT, "1");
					mapDriverUserInfo.put(MainActivity.KEY_TIMEOUT, "1");
					mapTreepRequest.put(MainActivity.KEY_TIMEOUT, "1");	
					mapDriverTreepNow.put(MainActivity.KEY_TIMEOUT, "1");	
				}
				
				else{
					
					Document doc = null;
					try{
						doc = parser.getDomElement(xml); // getting DOM element
					}
					catch(DOMException e){
						
					}
					
					if(doc == null){
						mapUserInfo = null;
						mapTreepOrderInfo = null;
						mapTreepOrderDriverInfo = null;
						mapDriverUserInfo = null;
						mapTreepRequest = null;
						mapDriverTreepNow = null;
					}
					else{
						NodeList nl_User = doc.getElementsByTagName(MainActivity.KEY_USERINFO);
						// looping through all xml nodes <KEY_USER>
						for (int i = 0; i < nl_User.getLength(); i++) {
							// creating new HashMap
							
							Element e = (Element) nl_User.item(i);
							// adding each child node to HashMap key => value
							
							mapUserInfo.put(MainActivity.KEY_USERTEL, parser.getValue(e, MainActivity.KEY_USERTEL));
							mapUserInfo.put(MainActivity.KEY_USERSEXE, parser.getValue(e, MainActivity.KEY_USERSEXE));
							mapUserInfo.put(MainActivity.KEY_ISDRIVER, parser.getValue(e, MainActivity.KEY_ISDRIVER));
							mapUserInfo.put(MainActivity.KEY_DRIVERMODE, parser.getValue(e, MainActivity.KEY_DRIVERMODE));
							mapUserInfo.put(MainActivity.KEY_OILTYPE, parser.getValue(e, MainActivity.KEY_OILTYPE));
							mapUserInfo.put(MainActivity.KEY_HASTOUPDATE, parser.getValue(e, MainActivity.KEY_HASTOUPDATE));
							mapUserInfo.put(MainActivity.KEY_APPVERSIONCODE, parser.getValue(e, MainActivity.KEY_APPVERSIONCODE));
							mapTreepRequest.put(MainActivity.KEY_CURRENTPOSITION, parser.getValue(e, MainActivity.KEY_CURRENTPOSITION));
							mapTreepRequest.put(MainActivity.KEY_CURRENTCOMPANY, parser.getValue(e, MainActivity.KEY_CURRENTCOMPANY));
							
							
						}
						
						NodeList nl_DriverPositionList = doc.getElementsByTagName(MainActivity.KEY_DRIVERPOSITIONLIST);
						for (int i = 0; i < nl_DriverPositionList.getLength(); i++) {
							Element e = (Element) nl_DriverPositionList.item(i);
							NodeList nl_DriverPosition = e.getElementsByTagName(MainActivity.KEY_DRIVERPOSITION);
							// looping through all xml nodes <KEY_USER>
							for (int j = 0; j < nl_DriverPosition.getLength(); j++) {
								// creating new HashMap
								HashMap<String, String> mapDriverPosition = new HashMap<String, String>();
								Element t = (Element) nl_DriverPosition.item(j);
								// adding each child node to HashMap key => value
								mapDriverPosition.put(MainActivity.KEY_USERID, parser.getValue(t, MainActivity.KEY_USERID));
								mapDriverPosition.put(MainActivity.KEY_FIRSTNAME, parser.getValue(t, MainActivity.KEY_FIRSTNAME));
								mapDriverPosition.put(MainActivity.KEY_LASTNAME, parser.getValue(t, MainActivity.KEY_LASTNAME));
								mapDriverPosition.put(MainActivity.KEY_NBTREEP, parser.getValue(t, MainActivity.KEY_NBTREEP));
								mapDriverPosition.put(MainActivity.KEY_RATING, parser.getValue(t, MainActivity.KEY_RATING));
								mapDriverPosition.put(MainActivity.KEY_LATITUDE, parser.getValue(t, MainActivity.KEY_LATITUDE));
								mapDriverPosition.put(MainActivity.KEY_LONGITUDE, parser.getValue(t, MainActivity.KEY_LONGITUDE));
								mapDriverPosition.put(MainActivity.KEY_ISBUSY, parser.getValue(t, MainActivity.KEY_ISBUSY));
								mapDriverPosition.put(MainActivity.KEY_DRIVERTREEPREQUESTED, parser.getValue(t, MainActivity.KEY_DRIVERTREEPREQUESTED));
								mapDriverPosition.put(MainActivity.KEY_LATDEP, parser.getValue(t, MainActivity.KEY_LATDEP));
								mapDriverPosition.put(MainActivity.KEY_LNGDEP, parser.getValue(t, MainActivity.KEY_LNGDEP));
								mapDriverPosition.put(MainActivity.KEY_LATDEST, parser.getValue(t, MainActivity.KEY_LATDEST));
								mapDriverPosition.put(MainActivity.KEY_LNGDEST, parser.getValue(t, MainActivity.KEY_LNGDEST));
								
								// adding HashList to ArrayList
								
								alDriverPosition.add(mapDriverPosition);
							}
						}
						
						NodeList nl_TreepOrderInfo = doc.getElementsByTagName(MainActivity.KEY_TREEPORDERINFO);
						// looping through all xml nodes <KEY_USER>
						for (int i = 0; i < nl_TreepOrderInfo.getLength(); i++) {
							// creating new HashMap
							Element e = (Element) nl_TreepOrderInfo.item(i);
							// adding each child node to HashMap key => value
							mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPORDERED, parser.getValue(e, MainActivity.KEY_ISTREEPORDERED));
							mapTreepOrderInfo.put(MainActivity.KEY_TREEPID, parser.getValue(e, MainActivity.KEY_TREEPID));
							mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPNOWCONFIRMED, parser.getValue(e, MainActivity.KEY_ISTREEPNOWCONFIRMED));
							mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPNOWCANCELEDBYDRIVER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWCANCELEDBYDRIVER));
							mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPNOWCANCELEDBYTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWCANCELEDBYTREEPER));
							mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPERONBOARDFROMDRIVER, parser.getValue(e, MainActivity.KEY_ISTREEPERONBOARDFROMDRIVER));
							mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPERONBOARDFROMTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPERONBOARDFROMTREEPER));
							mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPNOWFINISHED, parser.getValue(e, MainActivity.KEY_ISTREEPNOWFINISHED));
							mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPNOWRATEDBYTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWRATEDBYTREEPER));
							mapTreepOrderInfo.put(MainActivity.KEY_ISTREEPNOWRATEDBYDRIVER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWRATEDBYDRIVER));
							mapTreepOrderInfo.put(MainActivity.KEY_LATDEPNOW, parser.getValue(e, MainActivity.KEY_LATDEPNOW));
							mapTreepOrderInfo.put(MainActivity.KEY_LNGDEPNOW, parser.getValue(e, MainActivity.KEY_LNGDEPNOW));
							mapTreepOrderInfo.put(MainActivity.KEY_LATDESTNOW, parser.getValue(e, MainActivity.KEY_LATDESTNOW));
							mapTreepOrderInfo.put(MainActivity.KEY_LNGDESTNOW, parser.getValue(e, MainActivity.KEY_LNGDESTNOW));
							mapTreepOrderInfo.put(MainActivity.KEY_ADDRESSDEPNOW, parser.getValue(e, MainActivity.KEY_ADDRESSDEPNOW));
							mapTreepOrderInfo.put(MainActivity.KEY_ADDRESSDESTNOW, parser.getValue(e, MainActivity.KEY_ADDRESSDESTNOW));
							mapTreepOrderInfo.put(MainActivity.KEY_ADDRESSDESTNOW, parser.getValue(e, MainActivity.KEY_ADDRESSDESTNOW));
							mapTreepOrderInfo.put(MainActivity.KEY_DRIVERID, parser.getValue(e, MainActivity.KEY_DRIVERID));
							mapTreepOrderInfo.put(MainActivity.KEY_PRICE, parser.getValue(e, MainActivity.KEY_PRICE));
							mapTreepOrderInfo.put(MainActivity.KEY_TREEPORDERTIMEREMAINING, parser.getValue(e, MainActivity.KEY_TREEPORDERTIMEREMAINING));
							mapTreepOrderInfo.put(MainActivity.KEY_TREEPRESPONSECOUNT, parser.getValue(e, MainActivity.KEY_TREEPRESPONSECOUNT));
						}
						
						NodeList nl_TreepOrderDriverInfo = doc.getElementsByTagName(MainActivity.KEY_TREEPORDERDRIVERINFO);
						// looping through all xml nodes <KEY_USER>
						for (int i = 0; i < nl_TreepOrderDriverInfo.getLength(); i++) {
							// creating new HashMap
							
							Element e = (Element) nl_TreepOrderDriverInfo.item(i);
							// adding each child node to HashMap key => value
							mapTreepOrderDriverInfo.put(MainActivity.KEY_TREEPID, parser.getValue(e, MainActivity.KEY_TREEPID));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_DRIVERID, parser.getValue(e, MainActivity.KEY_DRIVERID));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_FIRSTNAME, parser.getValue(e, MainActivity.KEY_FIRSTNAME));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_LASTNAME, parser.getValue(e, MainActivity.KEY_LASTNAME));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_CARMODEL, parser.getValue(e, MainActivity.KEY_CARMODEL));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_DISTANCETIME, parser.getValue(e, MainActivity.KEY_DISTANCETIME));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_USERTEL, parser.getValue(e, MainActivity.KEY_USERTEL));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_RATING, parser.getValue(e, MainActivity.KEY_RATING));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_NBTREEP, parser.getValue(e, MainActivity.KEY_NBTREEP));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_PRICE, parser.getValue(e, MainActivity.KEY_PRICE));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_LATITUDE, parser.getValue(e, MainActivity.KEY_LATITUDE));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_LONGITUDE, parser.getValue(e, MainActivity.KEY_LONGITUDE));
							mapTreepOrderDriverInfo.put(MainActivity.KEY_CARPICURL, parser.getValue(e, MainActivity.KEY_CARPICURL));
						}
						
						NodeList nl_DriverUserInfo = doc.getElementsByTagName(MainActivity.KEY_DRIVERUSERINFO);
						// looping through all xml nodes <KEY_USER>
						for (int i = 0; i < nl_DriverUserInfo.getLength(); i++) {
							// creating new HashMap
							
							Element e = (Element) nl_DriverUserInfo.item(i);
							// adding each child node to HashMap key => valuedrivertreepnowconfirmedcount
							mapDriverUserInfo.put(MainActivity.KEY_DRIVERTREEPREQUESTED, parser.getValue(e, MainActivity.KEY_DRIVERTREEPREQUESTED));
							mapDriverUserInfo.put(MainActivity.KEY_DRIVERTREEPREQUESTID, parser.getValue(e, MainActivity.KEY_DRIVERTREEPREQUESTID));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPREQUESTEDFROMTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPREQUESTEDFROMTREEPER));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWCONFIRMED, parser.getValue(e, MainActivity.KEY_ISTREEPNOWCONFIRMED));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWRESPONSEACK, parser.getValue(e, MainActivity.KEY_ISTREEPNOWRESPONSEACK));
							mapDriverUserInfo.put(MainActivity.KEY_DRIVERTREEPNOWCONFIRMEDCOUNT, parser.getValue(e, MainActivity.KEY_DRIVERTREEPNOWCONFIRMEDCOUNT));
							mapDriverUserInfo.put(MainActivity.KEY_TREEPNOWCONFIRMEDTIMEREMAINING, parser.getValue(e, MainActivity.KEY_TREEPNOWCONFIRMEDTIMEREMAINING));
							mapDriverUserInfo.put(MainActivity.KEY_TREEPID, parser.getValue(e, MainActivity.KEY_TREEPID));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWACCEPTED, parser.getValue(e, MainActivity.KEY_ISTREEPNOWACCEPTED));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWFINISHED, parser.getValue(e, MainActivity.KEY_ISTREEPNOWFINISHED));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWRATEDBYDRIVER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWRATEDBYDRIVER));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWRATEDBYTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWRATEDBYTREEPER));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWCANCELEDBYDRIVER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWCANCELEDBYDRIVER));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPNOWCANCELEDBYTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPNOWCANCELEDBYTREEPER));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPERONBOARDFROMDRIVER, parser.getValue(e, MainActivity.KEY_ISTREEPERONBOARDFROMDRIVER));
							mapDriverUserInfo.put(MainActivity.KEY_ISTREEPERONBOARDFROMTREEPER, parser.getValue(e, MainActivity.KEY_ISTREEPERONBOARDFROMTREEPER));
						}
						
						NodeList nl_TreepRequest = doc.getElementsByTagName(MainActivity.KEY_TREEPREQUEST);
						// looping through all xml nodes <KEY_USER>
						for (int i = 0; i < nl_TreepRequest.getLength(); i++) {
							// creating new HashMap
							
							Element e = (Element) nl_TreepRequest.item(i);
							// adding each child node to HashMap key => valuedrivertreepnowconfirmedcount
							mapTreepRequest.put(MainActivity.KEY_REQUESTID, parser.getValue(e, MainActivity.KEY_REQUESTID));
							mapTreepRequest.put(MainActivity.KEY_DRIVERTREEPREQUESTID, parser.getValue(e, MainActivity.KEY_DRIVERTREEPREQUESTID));
							mapTreepRequest.put(MainActivity.KEY_TREEPID, parser.getValue(e, MainActivity.KEY_TREEPID));
							mapTreepRequest.put(MainActivity.KEY_USERID, parser.getValue(e, MainActivity.KEY_USERID));
							mapTreepRequest.put(MainActivity.KEY_FIRSTNAME, parser.getValue(e, MainActivity.KEY_FIRSTNAME));
							mapTreepRequest.put(MainActivity.KEY_LASTNAME, parser.getValue(e, MainActivity.KEY_LASTNAME));
							mapTreepRequest.put(MainActivity.KEY_CURRENTPOSITION, parser.getValue(e, MainActivity.KEY_CURRENTPOSITION));
							mapTreepRequest.put(MainActivity.KEY_CURRENTCOMPANY, parser.getValue(e, MainActivity.KEY_CURRENTCOMPANY));
							mapTreepRequest.put(MainActivity.KEY_TIMEREMAINING, parser.getValue(e, MainActivity.KEY_TIMEREMAINING));
							mapTreepRequest.put(MainActivity.KEY_DETOUR, parser.getValue(e, MainActivity.KEY_DETOUR));
							mapTreepRequest.put(MainActivity.KEY_RATING, parser.getValue(e, MainActivity.KEY_RATING));
							mapTreepRequest.put(MainActivity.KEY_NBTREEP, parser.getValue(e, MainActivity.KEY_NBTREEP));
							mapTreepRequest.put(MainActivity.KEY_PRICE, parser.getValue(e, MainActivity.KEY_PRICE));
							mapTreepRequest.put(MainActivity.KEY_LATDEPNOW, parser.getValue(e, MainActivity.KEY_LATDEPNOW));
							mapTreepRequest.put(MainActivity.KEY_LNGDEPNOW, parser.getValue(e, MainActivity.KEY_LNGDEPNOW));
							mapTreepRequest.put(MainActivity.KEY_LATDESTNOW, parser.getValue(e, MainActivity.KEY_LATDESTNOW));
							mapTreepRequest.put(MainActivity.KEY_LNGDESTNOW, parser.getValue(e, MainActivity.KEY_LNGDESTNOW));
							mapTreepRequest.put(MainActivity.KEY_ADDRESSDEPNOW, parser.getValue(e, MainActivity.KEY_ADDRESSDEPNOW));
							mapTreepRequest.put(MainActivity.KEY_ADDRESSDESTNOW, parser.getValue(e, MainActivity.KEY_ADDRESSDESTNOW));
							
						}
						
						NodeList nl_DriverTreepNow = doc.getElementsByTagName(MainActivity.KEY_DRIVERTREEPNOW);
						// looping through all xml nodes <KEY_USER>
						for (int i = 0; i < nl_DriverTreepNow.getLength(); i++) {
							// creating new HashMap
							
							Element e = (Element) nl_DriverTreepNow.item(i);
							// adding each child node to HashMap key => value
							mapDriverTreepNow.put(MainActivity.KEY_TREEPID, parser.getValue(e, MainActivity.KEY_TREEPID));
							mapDriverTreepNow.put(MainActivity.KEY_USERID, parser.getValue(e, MainActivity.KEY_USERID));
							mapDriverTreepNow.put(MainActivity.KEY_FIRSTNAME, parser.getValue(e, MainActivity.KEY_FIRSTNAME));
							mapDriverTreepNow.put(MainActivity.KEY_LASTNAME, parser.getValue(e, MainActivity.KEY_LASTNAME));
							mapDriverTreepNow.put(MainActivity.KEY_DISTANCETIME, parser.getValue(e, MainActivity.KEY_DISTANCETIME));
							mapDriverTreepNow.put(MainActivity.KEY_USERTEL, parser.getValue(e, MainActivity.KEY_USERTEL));
							mapDriverTreepNow.put(MainActivity.KEY_RATING, parser.getValue(e, MainActivity.KEY_RATING));
							mapDriverTreepNow.put(MainActivity.KEY_NBTREEP, parser.getValue(e, MainActivity.KEY_NBTREEP));
							mapDriverTreepNow.put(MainActivity.KEY_CURRENTPOSITION, parser.getValue(e, MainActivity.KEY_CURRENTPOSITION));
							mapDriverTreepNow.put(MainActivity.KEY_CURRENTCOMPANY, parser.getValue(e, MainActivity.KEY_CURRENTCOMPANY));
							mapDriverTreepNow.put(MainActivity.KEY_PRICE, parser.getValue(e, MainActivity.KEY_PRICE));
							mapDriverTreepNow.put(MainActivity.KEY_LATITUDE, parser.getValue(e, MainActivity.KEY_LATITUDE));
							mapDriverTreepNow.put(MainActivity.KEY_LONGITUDE, parser.getValue(e, MainActivity.KEY_LONGITUDE));
							mapDriverTreepNow.put(MainActivity.KEY_LATDEPNOW, parser.getValue(e, MainActivity.KEY_LATDEPNOW));
							mapDriverTreepNow.put(MainActivity.KEY_LNGDEPNOW, parser.getValue(e, MainActivity.KEY_LNGDEPNOW));
							mapDriverTreepNow.put(MainActivity.KEY_LATDESTNOW, parser.getValue(e, MainActivity.KEY_LATDESTNOW));
							mapDriverTreepNow.put(MainActivity.KEY_LNGDESTNOW, parser.getValue(e, MainActivity.KEY_LNGDESTNOW));
							mapDriverTreepNow.put(MainActivity.KEY_ADDRESSDEPNOW, parser.getValue(e, MainActivity.KEY_ADDRESSDEPNOW));
							mapDriverTreepNow.put(MainActivity.KEY_ADDRESSDESTNOW, parser.getValue(e, MainActivity.KEY_ADDRESSDESTNOW));
							
						}
						
						NodeList nl_UserPositionList = doc.getElementsByTagName(MainActivity.KEY_USERPOSITIONLIST);
						for (int i = 0; i < nl_UserPositionList.getLength(); i++) {
							Element e = (Element) nl_UserPositionList.item(i);
							NodeList nl_UserPosition = e.getElementsByTagName(MainActivity.KEY_USERPOSITION);
							// looping through all xml nodes <KEY_USER>
							for (int j = 0; j < nl_UserPosition.getLength(); j++) {
								// creating new HashMap
								HashMap<String, String> mapUserPosition = new HashMap<String, String>();
								Element t = (Element) nl_UserPosition.item(j);
								// adding each child node to HashMap key => value
								mapUserPosition.put(MainActivity.KEY_USERID, parser.getValue(t, MainActivity.KEY_USERID));
								mapUserPosition.put(MainActivity.KEY_FIRSTNAME, parser.getValue(t, MainActivity.KEY_FIRSTNAME));
								mapUserPosition.put(MainActivity.KEY_LASTNAME, parser.getValue(t, MainActivity.KEY_LASTNAME));
								mapUserPosition.put(MainActivity.KEY_NBTREEP, parser.getValue(t, MainActivity.KEY_NBTREEP));
								mapUserPosition.put(MainActivity.KEY_RATING, parser.getValue(t, MainActivity.KEY_RATING));
								mapUserPosition.put(MainActivity.KEY_LATITUDE, parser.getValue(t, MainActivity.KEY_LATITUDE));
								mapUserPosition.put(MainActivity.KEY_LONGITUDE, parser.getValue(t, MainActivity.KEY_LONGITUDE));
								mapUserPosition.put(MainActivity.KEY_ISBUSY, parser.getValue(t, MainActivity.KEY_ISBUSY));
								
								// adding HashList to ArrayList
								
								alUserPosition.add(mapUserPosition);
							}
						}
						
						
						mapMainUserInfo.put("mapUserInfo", mapUserInfo);
						mapMainUserInfo.put("mapTreepOrderInfo", mapTreepOrderInfo);
						mapMainUserInfo.put("mapTreepOrderDriverInfo", mapTreepOrderDriverInfo);
						mapMainUserInfo.put("mapDriverUserInfo", mapDriverUserInfo);
						mapMainUserInfo.put("mapTreepRequest", mapTreepRequest);
						mapMainUserInfo.put("mapDriverTreepNow", mapDriverTreepNow);
						
						
					}
				}
			}
				
			return mapMainUserInfo;
		}

		@SuppressLint("NewApi")
		protected void onPostExecute(HashMap<String, HashMap<String, String>>  result) {
			
			MainActivity activity = this.myWeakContext.get();
			
			if(result == null){
				
			}
			else{
				if(result.containsKey("mapUserInfo")){
					mapResultUserInfo = result.get("mapUserInfo");
				}				
				
				if(result.containsKey("mapTreepOrderInfo")){
					mapResultTreepOrderInfo = result.get("mapTreepOrderInfo");
				}
				
				if(result.containsKey("mapTreepOrderDriverInfo")){
					mapResultTreepOrderDriverInfo = result.get("mapTreepOrderDriverInfo");
				}
				
				if(result.containsKey("mapDriverUserInfo")){
					mapResultDriverUserInfo = result.get("mapDriverUserInfo");
				}
				
				if(result.containsKey("mapTreepRequest")){
					mapResultTreepRequest = result.get("mapTreepRequest");
				}
				
				if(result.containsKey("mapDriverTreepNow")){
					mapResultDriverTreepNow = result.get("mapDriverTreepNow");
				}
				
				
				if(mapResultUserInfo == null || mapResultUserInfo.containsKey(MainActivity.KEY_TIMEOUT) 
						|| mapResultTreepOrderInfo == null || mapResultTreepOrderInfo.containsKey(MainActivity.KEY_TIMEOUT)
						|| mapResultTreepOrderDriverInfo== null || mapResultTreepOrderDriverInfo.containsKey(MainActivity.KEY_TIMEOUT)
						|| mapResultDriverUserInfo== null || mapResultDriverUserInfo.containsKey(MainActivity.KEY_TIMEOUT)
						|| mapResultDriverTreepNow== null || mapResultDriverTreepNow.containsKey(MainActivity.KEY_TIMEOUT)){
		    	
		    		
		    	}
				else{

					MainActivity.driverMode=Boolean.parseBoolean(mapResultUserInfo.get(MainActivity.KEY_DRIVERMODE));
					MainActivity.navDrawerItems.remove(MainActivity.navDrawerItems.size()-1);
					MainActivity.navDrawerItems.add(new NavDrawerItem(MainActivity.navMenuTitles[5], MainActivity.navMenuSubTitles[5], R.drawable.drivermodeoff));
					MainActivity.adapter.notifyDataSetChanged();
					if(Boolean.parseBoolean(mapResultUserInfo.get(MainActivity.KEY_DRIVERMODE))){
						MainActivity.navDrawerItems.remove(MainActivity.navDrawerItems.size()-1);
						MainActivity.navDrawerItems.add(new NavDrawerItem(MainActivity.navMenuTitles[5], MainActivity.navMenuSubTitles[5],R.drawable.drivermodeon));
						MainActivity.adapter.notifyDataSetChanged();
					}
					else{
						MainActivity.navDrawerItems.remove(MainActivity.navDrawerItems.size()-1);
						MainActivity.navDrawerItems.add(new NavDrawerItem(MainActivity.navMenuTitles[5], MainActivity.navMenuSubTitles[5], R.drawable.drivermodeoff));
						MainActivity.adapter.notifyDataSetChanged();
					}
					
					
					try{
						appVersionFromXML = Integer.parseInt(mapResultUserInfo.get(MainActivity.KEY_APPVERSIONCODE));
					}
					catch(NumberFormatException e){
						
					}
					
					if(Integer.parseInt(MainActivity.appVersion) < appVersionFromXML){
						
						CURRENT_STEP = STEP_HASTOUPDATE;

						fragment = new HasToUpdateFragment();
						showFragment(0, fragment);
						
					}
					else{
						if(mapResultUserInfo.get(MainActivity.KEY_USERTEL) != null){
							if(!Boolean.parseBoolean(mapResultUserInfo.get(MainActivity.KEY_DRIVERMODE))){
								
								PushService.unsubscribe(ApplicationContextProvider.getContext(), "drivers");
								
								if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPORDERED))){
									if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPNOWCONFIRMED))){
										if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPNOWCANCELEDBYDRIVER))){
											if(CURRENT_STEP != STEP_USERDRIVERCANCELED){
												
												CURRENT_STEP = STEP_USERDRIVERCANCELED;
												
												DialogDriverCanceled dialogDriverCanceled = new DialogDriverCanceled(activity,mapResultTreepOrderDriverInfo);
												dialogDriverCanceled.setCancelable(false);
												try{
													dialogDriverCanceled.show();
												}
												catch(BadTokenException e){
													
												}
											}
										}
										else{
											if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPERONBOARDFROMDRIVER))){
												if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPERONBOARDFROMTREEPER))){
													if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPNOWFINISHED))){
														if(Boolean.parseBoolean(mapResultTreepOrderInfo.get(MainActivity.KEY_ISTREEPNOWRATEDBYTREEPER))){					
															if(CURRENT_STEP != STEP_USERTREEPFINISHED){
																
																CURRENT_STEP = STEP_USERTREEPFINISHED;
																
																try{
																	DialogEvaluationConfirmedToTreeper dialogEvaluationConfirmedToTreeper = new DialogEvaluationConfirmedToTreeper(MainActivity.this,mapResultTreepOrderInfo);
																	dialogEvaluationConfirmedToTreeper.setCancelable(false);
																	dialogEvaluationConfirmedToTreeper.show();
																}
																catch(BadTokenException e){
																	
																}
															}
														}
														else{
															if(CURRENT_STEP != STEP_USERTREEPFINISHED){
																
																CURRENT_STEP = STEP_USERTREEPFINISHED;
																
																LaunchRatingDriverNowFragment(mapResultTreepOrderDriverInfo);
															}
														}
													}
													else{
														if(CURRENT_STEP != STEP_USERONBOARD){
															
															CURRENT_STEP = STEP_USERONBOARD;
															
															fragment = new IceBreakerFragment();
															showFragment(0, fragment);
														}
														
													}
												}
												else{
													if(CURRENT_STEP != STEP_USERCONFIRMONBOARD){
														
														CURRENT_STEP = STEP_USERCONFIRMONBOARD;
														
														fragment = new BlankFragment();
														showFragment(0, fragment);
														try{
															DialogTreeperOnBoardFromTreeper dialogTreeperOnBoardFromTreeper = new DialogTreeperOnBoardFromTreeper(activity,mapResultTreepOrderDriverInfo);
															dialogTreeperOnBoardFromTreeper.show();
															
														}
														catch(BadTokenException e){
															
														}
													}
												}
											}
											else{
												if(CURRENT_STEP != STEP_USERTREEPACCEPTED){
													
													CURRENT_STEP = STEP_USERTREEPACCEPTED;
													
													fragment = new MapUserTreepAcceptedFragment();
													Bundle extras = new Bundle();
													extras.putSerializable("mapTreepOrderInfo",mapResultTreepOrderInfo);
													extras.putSerializable("mapTreepOrderDriverInfo",mapResultTreepOrderDriverInfo);
													fragment.setArguments(extras);
													showFragment(0, fragment);
												}
											}
										}
									}
									else{
										if(CURRENT_STEP != STEP_USERTREEPORDERED){
											
											CURRENT_STEP = STEP_USERTREEPORDERED;
											
											fragment = new MapUserTreepOrderedFragment();
											Bundle extras = new Bundle();
											extras.putSerializable("alDriverPosition",alDriverPosition);
											extras.putSerializable("mapUserInfo",mapResultUserInfo);
											extras.putSerializable("mapTreepOrderInfo",mapResultTreepOrderInfo);
											fragment.setArguments(extras);
											showFragment(0, fragment);
										}
									}
								}
								else{
									if(CURRENT_STEP != STEP_HOME){
										
										CURRENT_STEP = STEP_HOME;

										fragment= new HomeFragment();
										Bundle extras = new Bundle();
										extras.putSerializable("alDriverPosition",alDriverPosition);
										extras.putSerializable("mapUserInfo",mapResultUserInfo);
										fragment.setArguments(extras);
										showFragment(0, fragment);
									}
								}
							}
							else{
								if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_DRIVERTREEPREQUESTED))){
									if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_ISTREEPREQUESTEDFROMTREEPER))){
										if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_ISTREEPNOWACCEPTED))){
											if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_ISTREEPNOWCANCELEDBYTREEPER))){
												if(CURRENT_STEP != STEP_DRIVERTREEPERCANCELED){
													CURRENT_STEP = STEP_DRIVERTREEPERCANCELED;
													
													DialogTreeperCanceled dialogTreeperCanceled = new DialogTreeperCanceled(activity,mapResultDriverTreepNow);
													dialogTreeperCanceled.setCancelable(false);
													try {
															dialogTreeperCanceled.show();
												    } 
													catch (BadTokenException e){
												    }
												}
											}
											else{
												if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_ISTREEPERONBOARDFROMDRIVER))){
													if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_ISTREEPERONBOARDFROMTREEPER))){
														if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_ISTREEPNOWFINISHED))){
															if(Boolean.parseBoolean(mapResultDriverUserInfo.get(MainActivity.KEY_ISTREEPNOWRATEDBYDRIVER))){
																if(CURRENT_STEP != STEP_DRIVERTREEPFINISHED){
																	CURRENT_STEP = STEP_DRIVERTREEPFINISHED;
																	
																	DialogEvaluationConfirmedToDriver dialogEvaluationConfirmedToDriver = new DialogEvaluationConfirmedToDriver(activity,mapResultDriverTreepNow);
																	dialogEvaluationConfirmedToDriver.setCancelable(false);
																	try {
																		dialogEvaluationConfirmedToDriver.show();
																    } 
																	catch (BadTokenException e){
																		
																    }
																}
															}
															else{
																if(CURRENT_STEP != STEP_DRIVERTREEPFINISHED){
																	CURRENT_STEP = STEP_DRIVERTREEPFINISHED;
																	
																	LaunchRatingTreeperNowFragment(mapResultDriverTreepNow);
																}
															}
														}
														else{
															if(CURRENT_STEP != STEP_DRIVERTREEPACCEPTEDTREEPERONBOARD){
																CURRENT_STEP = STEP_DRIVERTREEPACCEPTEDTREEPERONBOARD;
																
																if(dialogSMSTreeperOnBoardSent != null){
																	dialogSMSTreeperOnBoardSent.dismiss();
																}
																
																isTreeperOnBoard = true;
																isDriverAccepted = false;
																
																fragment= new MapTreepConfirmed();
																Bundle extras = new Bundle();
																extras.putSerializable("mapDriverUserInfo",mapResultDriverUserInfo);
																extras.putSerializable("mapDriverTreepNow",mapResultDriverTreepNow);
																fragment.setArguments(extras);
																showFragment(0, fragment);
															}
														}
													}
													else{
														if(CURRENT_STEP != STEP_DRIVERTREEPACCEPTEDWAITFORTREEPER){
															CURRENT_STEP = STEP_DRIVERTREEPACCEPTEDWAITFORTREEPER;
															
															fragment= new BlankFragment();
															showFragment(0,fragment);
															dialogSMSTreeperOnBoardSent = new DialogSMSTreeperOnBoardSent(activity,mapResultDriverTreepNow);
															try {
																dialogSMSTreeperOnBoardSent.show();
														    } 
															catch (BadTokenException e){
														    }
														}
													}
												}
												else{
													if(CURRENT_STEP != STEP_DRIVERTREEPACCEPTEDTREEPERNOTONBOARD){
														CURRENT_STEP = STEP_DRIVERTREEPACCEPTEDTREEPERNOTONBOARD;
														PushService.unsubscribe(ApplicationContextProvider.getContext(), "drivers");
														isTreeperOnBoard = false;
														isDriverAccepted = false;
														
														
														fragment= new MapTreepConfirmed();
														Bundle extras = new Bundle();
														extras.putSerializable("mapDriverUserInfo",mapResultDriverUserInfo);
														extras.putSerializable("mapDriverTreepNow",mapResultDriverTreepNow);
														fragment.setArguments(extras);
														showFragment(0, fragment);
													}
												}
											}
											
										}
										else{
											if(CURRENT_STEP != STEP_TREEPREQUEST){
												CURRENT_STEP = STEP_TREEPREQUEST;
												if(mapResultTreepRequest == null || mapResultTreepRequest.containsKey(MainActivity.KEY_TIMEOUT)){
													
												}
												else{
													LaunchTreepRequestFragment(mapResultTreepRequest);
												}
												
											}
										}
									}
									else{
										if(CURRENT_STEP != STEP_DRIVERTREEPREQUESTEDNOTREEPER){
											CURRENT_STEP = STEP_DRIVERTREEPREQUESTEDNOTREEPER;
											fragment= new DriverTreepRequestedNoTreeperFragment();
											Bundle extras = new Bundle();
											//extras.putSerializable("alDriverPosition",alMapDriverPosition);
											
											showFragment(0, fragment);
											//DriverTreepRequestedNoTreeperFragment
										}
									}
								}
								else{
									if(CURRENT_STEP != STEP_HOME){
										CURRENT_STEP = STEP_HOME;
										fragment= new HomeFragment();
										Bundle extras = new Bundle();
										extras.putSerializable("alDriverPosition",alDriverPosition);
										extras.putSerializable("mapUserInfo",mapResultUserInfo);
										fragment.setArguments(extras);
										showFragment(0, fragment);
									}
								}
							}
						}
						else{
							if(CURRENT_STEP != STEP_USERINFORMATION){
								CURRENT_STEP = STEP_USERINFORMATION;
								
								LaunchMyInformationFragment();
							}
						}
					}
				}
			}
			
			if(dialogPleaseWait != null){
				dialogPleaseWait.dismiss();
			}
			
			//storeUserInfo();
			setProgressBarIndeterminateVisibility(false);
		}
		
		 public void attach(MainActivity activity) {
		        this.myWeakContext = new WeakReference<MainActivity>(activity);
		    }
	}
}
