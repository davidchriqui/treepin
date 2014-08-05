package com.treep.fr;
import java.util.ArrayList;
import java.util.HashMap;

import com.treep.fr.R;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class TreepResponseAdapter extends BaseAdapter {

	private TreepResponseListFragment activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	private TextView tvUsername;
	private TextView tvCarModel;
	private TextView tvDistanceTime;
	private TextView tvPrice;
	private ImageView thumb_image;
	private ImageView ivRating;
	private HashMap<String, String> treepResponse = new HashMap<String, String>();
	
	private String username;

	public TreepResponseAdapter(TreepResponseListFragment a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getActivity());
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null){
			vi = inflater.inflate(R.layout.list_row_treepresponse, null);
		}

		treepResponse = data.get(position);
	
		
		tvUsername = (TextView) vi.findViewById(R.id.username); // user name = firstname + 1st letter of lastname
		username = treepResponse.get(MainActivity.KEY_FIRSTNAME) + " " + treepResponse.get(MainActivity.KEY_LASTNAME).charAt(0) + ".";
		tvUsername.setText(username);
		
		tvCarModel = (TextView) vi.findViewById(R.id.carmodel); 
		tvCarModel.setText(treepResponse.get(MainActivity.KEY_CARMODEL) +" " + treepResponse.get(MainActivity.KEY_NBSEAT) +" places dispo.");
		
		tvPrice = (TextView) vi.findViewById(R.id.treepresponseprice);
		tvPrice.setText("Prix : " + treepResponse.get(MainActivity.KEY_PRICE) + "€");
		
		thumb_image = (ImageView) vi.findViewById(R.id.list_image); // thumb
		imageLoader.DisplayImage(MainActivity.urlThumbPic(treepResponse.get(MainActivity.KEY_USERID)),
				thumb_image);
				
		ivRating = (ImageView) vi.findViewById(R.id.list_rating); // User rating
		int rating = Integer.parseInt(treepResponse.get(MainActivity.KEY_RATING));
		switch (rating) {
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
		tvDistanceTime = (TextView) vi.findViewById(R.id.distancetime);
		
		tvDistanceTime.setText("Se trouve à "+ treepResponse.get(MainActivity.KEY_DISTANCETIME) + " min");
		
		
		return vi;
	}
}