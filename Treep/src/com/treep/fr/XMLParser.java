package com.treep.fr;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.treep.fr.R;

import android.util.Log;
import android.widget.Toast;

public class XMLParser {

	
	private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	
	// constructor
		public XMLParser(){
		}
	
	// constructor
	public XMLParser(List<NameValuePair> nameValuePairs) {
		this.nameValuePairs=nameValuePairs;
	}

	/**
	 * Getting XML from URL making HTTP request
	 * @param url string
	 * */
	public String getXmlFromUrl(String url) {
		String xml = null;
		
		
		//Toast.makeText( ApplicationContextProvider.getContext(), Boolean.toString(URLUtil.isValidUrl(url)), Toast.LENGTH_SHORT).show();
    	

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

			
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			httpResponse = httpClient.execute(httpPost);
			
			
			double requestEndTime = new Date().getTime();
			double timeOfRequest = (requestEndTime - requestStartTime) / 1000;
	        if (timeOfRequest > MainActivity.TIME_OUT_IN_SECONDS) {

	        	xml="timeout";
	        }
	        
	        else{
	        	HttpEntity httpEntity = httpResponse.getEntity();
				xml = EntityUtils.toString(httpEntity,HTTP.UTF_8);
				
	        }
			

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return XML
		return xml;
	}
	
	/**
	 * Getting XML DOM element
	 * @param XML string
	 * */
	public Document getDomElement(String xml){
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
			
			if(xml != null){
		        is.setCharacterStream(new StringReader(xml));
		        doc = db.parse(is); 
			}
			
			else{
				doc =null;
				
			}

			} catch (ParserConfigurationException e) {
				Log.e("Error: ", e.getMessage());
				return null;
			} catch (SAXException e) {
				Log.e("Error: ", e.getMessage());
	            return null;
			} catch (IOException e) {
				Log.e("Error: ", e.getMessage());
				return null;
			}

	        return doc;
	}
	
	/** Getting node value
	  * @param elem element
	  */
	 public final String getElementValue( Node elem ) {
	     Node child;
	     if( elem != null){
	         if (elem.hasChildNodes()){
	             for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
	                 if( child.getNodeType() == Node.TEXT_NODE  ){
	                     return child.getNodeValue();
	                 }
	             }
	         }
	     }
	     return "";
	 }
	 
	 /**
	  * Getting node value
	  * @param Element node
	  * @param key string
	  * */
	 public String getValue(Element item, String str) {		
			NodeList n = item.getElementsByTagName(str);		
			return this.getElementValue(n.item(0));
		}
}
