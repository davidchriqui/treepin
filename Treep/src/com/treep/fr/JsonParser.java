package com.treep.fr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonParser {

	    static InputStream is = null;
	    static JSONObject jObj = null;
	    static String json = "";

	    // constructor
	    public JsonParser() {

	    }

	    public JSONObject getJSONFromUrl(String url) {

	    	try {
				
				 HttpResponse httpResponse = null;
				 double requestStartTime = new Date().getTime();
				
				 HttpParams httpParameters = new BasicHttpParams();
				 HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);
				 HttpConnectionParams.setSoTimeout(httpParameters, 3000);

				 
				
				// defaultHttpClient
				
				DefaultHttpClient httpClient = null;
				
				 if(null == httpClient)
					 httpClient = new DefaultHttpClient(httpParameters);
				
				HttpPost httpPost = new HttpPost(url);

				httpResponse = httpClient.execute(httpPost);
				
				
				double requestEndTime = new Date().getTime();
		        //Log.d("requestStratTime", "requestStratTime" + requestStartTime);
		        //Log.d("requestEndTime", "requestEndTime" + requestEndTime);
				double timeOfRequest = (requestEndTime - requestStartTime) / 1000;
		        //Log.d("timeOfRequest", "timeOfRequest" + timeOfRequest);
		        if (timeOfRequest > MainActivity.TIME_OUT_IN_SECONDS) {

		        	json="{ \"timeout\": \"1\"}";
		        	
		        	//Toast.makeText( ApplicationContextProvider.getContext(), "PB DE CONNECTION" + timeOfRequest, Toast.LENGTH_SHORT).show();
		        	
		        }
		        
		        else{
		        	HttpEntity httpEntity = httpResponse.getEntity();
		        	is = httpEntity.getContent();
					
		        }
				

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	
	    	
	        try {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    is, "iso-8859-1"), 8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            is.close();
	            json = sb.toString();
	        } catch (Exception e) {
	            Log.e("Buffer Error", "Error converting result " + e.toString());
	        }

	        // try parse the string to a JSON object
	        try {
	            jObj = new JSONObject(json);
	        } catch (JSONException e) {
	            Log.e("JSON Parser", "Error parsing data " + e.toString());
	        }

	        // return JSON String
	        return jObj;

	    }
	}