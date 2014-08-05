package com.treep.fr;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class DownloadImage  extends AsyncTask<Void, Integer, Bitmap> {

	private Activity activity;
	private String url;
	private ImageView iv;
	
	public DownloadImage(Activity activity, String url, ImageView iv){
		this.activity=activity;
		this.url=url;
		this.iv=iv;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		activity.setProgressBarIndeterminateVisibility(true);
		
	}
	
	
	@Override
	protected Bitmap doInBackground(Void... params) {
		Bitmap image = downloadImage(url);
		return image;
	}
	
	protected void onPostExecute(Bitmap  result) {
		if(result == null){
			
		}
		else{
			iv.setImageBitmap(result);
		}
		
		activity.setProgressBarIndeterminateVisibility(false);
	}
	
	
	private Bitmap downloadImage(String urlImage) {
        Bitmap bitmap = null;
        try {
        	URL url = new URL(urlImage);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return bitmap;
    }

	
}
