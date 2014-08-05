package com.treep.fr;


import com.treep.fr.R;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HelpFragment1 extends Fragment {

	private ImageView ivHelpPic;
	private ImageView ivGoLeft;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_help_layout, container, false);
		
		ivGoLeft = (ImageView) v.findViewById(R.id.ivGoLeft);
		ivGoLeft.setVisibility(View.GONE);
		
		ivHelpPic = (ImageView) v.findViewById(R.id.ivHelpPic);
		ivHelpPic.setImageResource(R.drawable.help1);
		
		Typeface fb = Typeface.createFromAsset(getActivity().getAssets(), "fb.ttf");
		TextView tvHelpTitle;
		TextView tvHelp;
		
		tvHelpTitle = (TextView) v.findViewById(R.id.tvHelpTitle);
		tvHelpTitle.setTypeface(fb);
		tvHelpTitle.setText("Réservez votre treep en 5 sec !");
		
		tvHelp = (TextView) v.findViewById(R.id.tvHelp);
		tvHelp.setTypeface(fb);
		tvHelp.setText("Vous êtes géolocalisé. \nEntrez votre adresse de destination.");
		
		return v;
	}
}