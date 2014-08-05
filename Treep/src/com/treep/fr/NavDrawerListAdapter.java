package com.treep.fr;
import java.util.ArrayList;
 



import com.treep.fr.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class NavDrawerListAdapter extends BaseAdapter {
     
    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private Typeface font;
    
    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems, Typeface font){
        this.context = context;
        this.navDrawerItems = navDrawerItems;
        this.font = font;
    }
 
    @Override
    public int getCount() {
        return navDrawerItems.size();
    }
 
    @Override
    public Object getItem(int position) {       
        return navDrawerItems.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	
  
    	
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }
          
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtSubTitle = (TextView) convertView.findViewById(R.id.subtitle);
       
        
          
        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());        
        txtTitle.setText(navDrawerItems.get(position).getTitle());
        txtSubTitle.setText(navDrawerItems.get(position).getSubTitle());
         
        //setting a custom "bello" font for listview items
        txtTitle.setTypeface(font);

         
        return convertView;
    }
 
}