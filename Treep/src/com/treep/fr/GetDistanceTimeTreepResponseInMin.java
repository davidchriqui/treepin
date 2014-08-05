package com.treep.fr;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

public class GetDistanceTimeTreepResponseInMin extends AsyncTask<Void, Void, String> {
	Context mContext;
	Double lat1;
	Double lng1;
	Double lat2;
	Double lng2;
	TextView tv1;
	String duration = null;
	String distanceTime = null;
	
	public GetDistanceTimeTreepResponseInMin(Double lat1, Double lng1,Double lat2, Double lng2, TextView tv1) {
	    super();
	    mContext = ApplicationContextProvider.getContext();
	    this.lat1 = lat1;
	    this.lng1= lng1;
	    this.lat2 = lat2;
	    this.lng2 = lng2;
	    this.tv1=tv1;
	}
	
	
	public Document getDocument(Double lat1,Double lng1,Double lat2,Double lng2) {
        String url = "http://maps.googleapis.com/maps/api/directions/xml?" 
                + "origin=" + lat1 + "," + lng1  
                + "&destination=" + lat2 + "," + lng2
                + "&sensor=false&units=metric&mode=driving";

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            InputStream in = response.getEntity().getContent();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in);
            return doc;
        } catch (Exception e) {
        	Document doc = null;
            e.printStackTrace();
        }
        return null;
    }

    public String getDurationText (Document doc) {
    	NodeList nl_status = doc.getElementsByTagName("status");
    	if(nl_status.item(0).toString() == "OK"){
	        NodeList nl1 = doc.getElementsByTagName("duration");
	        Node node1 = nl1.item(nl1.getLength() - 1);
	        NodeList nl2 = node1.getChildNodes();
	        Node node2 = nl2.item(3);
	        return node2.getTextContent();
    	}
    	else{
    		return "0";
    	}
    }
	
	/**
	 * Get a Geocoder instance, get the latitude and longitude
	 * look up the address, and return it
	 * @return 
	 *
	 * @params params One or more Location objects
	 */
	@Override
	protected String doInBackground(Void... params) {
	   
		
		Document doc = getDocument (lat1,lng1,lat2,lng2);
		if(doc != null){
			duration = getDurationText(doc);
			
			if(duration.length() > 0){
				
				StringBuilder myNumbers = new StringBuilder();
		 	    for (int i = 0; i < duration.length(); i++) {
		 	        if (Character.isDigit(duration.charAt(i))) {
		 	            myNumbers.append(duration.charAt(i));
		 	            
		 	        }
		 	    }
		 	    
		 	   duration = myNumbers.toString();
			}
			else{
				duration = "";
			}
			return duration;
		}
		else{
			duration = "";
			return duration;
		}
		
	}
	
	
	 protected void onPostExecute(String result) {   
		 	if(distanceTime == null ){
	            tv1.setText("");
		 	}
		 	
		 	else{
		 		tv1.setText("Distance de vous : " + result + " min"); 
		 	}
	            
	        
	    }
}
	