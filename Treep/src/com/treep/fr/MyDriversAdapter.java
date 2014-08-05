package com.treep.fr;
import java.util.ArrayList;
import java.util.HashMap;

import com.treep.fr.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyDriversAdapter extends BaseAdapter {

	private MyDriversListFragment activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	private String username;

	public MyDriversAdapter(MyDriversListFragment a, ArrayList<HashMap<String, String>> d) {
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
		if (convertView == null)
			vi = inflater.inflate(R.layout.list_row_mydrivers, null);

		TextView tvUsername = (TextView) vi.findViewById(R.id.username); // user name = firstname + 1st letter of lastname
		TextView tvCarModel = (TextView) vi.findViewById(R.id.carmodel); // artist
																	// name
		ImageView list_rating = (ImageView) vi.findViewById(R.id.list_rating); // User rating
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.list_image); // thumb
																				// image

		HashMap<String, String> user = new HashMap<String, String>();
		user = data.get(position);
	

		// Setting all values in listview
		
		username = user.get(MainActivity.KEY_FIRSTNAME) + " " + user.get(MainActivity.KEY_LASTNAME).charAt(0) + ".";
		
		tvUsername.setText(username);
		tvCarModel.setText(user.get(MainActivity.KEY_CARMODEL));
		
		int rating = Integer.parseInt(user.get(MainActivity.KEY_RATING));
		
		
		switch (rating) {
        case 0:
        	//list_rating.setImageResource(R.drawable.rating1);       
            break;
        case 1:
        	list_rating.setImageResource(R.drawable.rating1);
              
            break;
        case 2:
        	list_rating.setImageResource(R.drawable.rating2);
              
            break;
        case 3:
        	list_rating.setImageResource(R.drawable.rating3);
              
            break;
        case 4:
        	list_rating.setImageResource(R.drawable.rating4);
              
            break;
        case 5:
        	list_rating.setImageResource(R.drawable.rating5);
              
            break;
		
		}
		
		imageLoader.DisplayImage(MainActivity.urlThumbPic(user.get(MainActivity.KEY_USERID)),
				thumb_image);
		return vi;
	}
}