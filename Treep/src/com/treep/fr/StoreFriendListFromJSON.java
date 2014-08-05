package com.treep.fr;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StoreFriendListFromJSON extends AsyncTask<Void, Integer, ArrayList<String>> {
	
	
	private Activity activity;
	private String userId;
	private LinearLayout inHorizontalScrollView;
	private TextView tvfbFriends;
	
	private int sizeMyFriends;
	private int sizeYourFriends;
	
	public StoreFriendListFromJSON(final Activity activity, final LinearLayout inHorizontalScrollView, final TextView tvfbFriends, final String userId){
		
		this.activity=activity;
		this.inHorizontalScrollView=inHorizontalScrollView;
		this.tvfbFriends=tvfbFriends;
		this.userId=userId;
		
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		activity.setProgressBarIndeterminateVisibility(true);
		//Toast.makeText(ApplicationContextProvider.getContext(), "Début de MyDriverList", Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onProgressUpdate(Integer... values){
		super.onProgressUpdate(values);
		
		// Mise à jour de la ProgressBar
		//mProgressBar.setProgress(values[0]);
	}

	@Override
	protected ArrayList<String> doInBackground(Void... arg0) {

		if (android.os.Build.VERSION.SDK_INT > 9) 
    	{ StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); }
		
    	JsonParser parser = new JsonParser();
    	JSONObject json = parser.getJSONFromUrl(MainActivity.urlFriendList(userId));
    	//Toast.makeText(ApplicationContextProvider.getContext(), MainActivity.urlFriendList(Integer.parseInt(userId)), Toast.LENGTH_LONG).show();
		
		ArrayList<String> idFriendList = new ArrayList<String>();
		

		if(json == null){
			idFriendList = null;
			
		}
		else{
			try {
				JSONArray jArray = json.getJSONArray("data");
				
				for (int i=0; i < jArray.length(); i++)
				{
				    
				        JSONObject jObject = jArray.getJSONObject(i);
				        // Pulling items from the array
				        
				        idFriendList.add(jObject.getString("id"));
				        
				    } 
				sizeYourFriends=idFriendList.size();
				
			} catch (JSONException e) {
				idFriendList.add("error");
				e.printStackTrace();
			}
		}
		
		return idFriendList;
	}

	@Override
	protected void onPostExecute(final ArrayList<String>  result) {
		//Toast.makeText(ApplicationContextProvider.getContext(), "Le traitement asynchrone MyDriverList est terminé", Toast.LENGTH_LONG).show();
		activity.setProgressBarIndeterminateVisibility(true);
		final ArrayList<String> myFriends = new ArrayList<String>();
		
		if(result == null || result.size() == 0){
			tvfbFriends.setText("Amis Facebook en commun (0)");
			//Toast.makeText(ApplicationContextProvider.getContext(), "My FRIEND : " + sizeMyFriends, Toast.LENGTH_LONG).show();
			activity.setProgressBarIndeterminateVisibility(false);
		}
		else{
			if (LoginDisplayActivity.session.isOpened()) {
		       	 //Retrieve Facebook userId
		       	new Request(
		       			LoginDisplayActivity.session,
		       		    "/me/friends",
		       		    null,
		       		    HttpMethod.GET,
		       		    new Request.Callback() {
		       		        public void onCompleted(Response response) {
		       		        	
		       		        	
		       		        	GraphObject responseGraphObject = response.getGraphObject();
		       		            //Create the JSON object
		       		            JSONObject json = responseGraphObject.getInnerJSONObject();
		       		            
		       		            if(json ==null){
		       		            	tvfbFriends.setText("");
		       		            }
		       		            else{
			       		            try {
										JSONArray jArray = json.getJSONArray("data");
										
										for (int i=0; i < jArray.length(); i++)
										{
										        JSONObject jObject = jArray.getJSONObject(i);
										        // Pulling items from the array
										        
										        myFriends.add(jObject.getString("id"));
										}
										
										myFriends.retainAll(result);
										
										if(myFriends.size() <= 40){
											for(int i = 0 ; i < myFriends.size(); i++){
											     addImageView(inHorizontalScrollView,MainActivity.urlThumbPic(myFriends.get(i)));
											}
										}
										else{
											for(int i = 0 ; i < 40; i++){
											     addImageView(inHorizontalScrollView,MainActivity.urlThumbPic(myFriends.get(i)));
											}
										}
											
										tvfbFriends.setText("Amis Facebook en commun (" + myFriends.size() + ")");
										//Toast.makeText(ApplicationContextProvider.getContext(), "My FRIEND : " + sizeMyFriends, Toast.LENGTH_LONG).show();
										activity.setProgressBarIndeterminateVisibility(false);
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
		       		            }
		       		            
		       		            
		   						
		       		        }
		       		    }
		       		).executeAsync();
				}
			}
		}
	
	
	
	private void addImageView(LinearLayout layout, String url){
	     ImageView imageView = new ImageView(activity);
	     ImageLoader imageLoader = new ImageLoader(activity);
	     imageLoader.DisplayImage(url,imageView);
	     imageView.setPadding(10,10,10,10);
	     imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
	     layout.addView(imageView);
	    }

}