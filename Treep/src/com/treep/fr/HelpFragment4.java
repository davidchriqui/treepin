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

public class HelpFragment4 extends Fragment {

	private ImageView ivHelpPic;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_help_layout, container, false);
		
		
		ImageView ivHelpPic = (ImageView) v.findViewById(R.id.ivHelpPic);
		ivHelpPic.setImageResource(R.drawable.help3);
		
		Typeface fb = Typeface.createFromAsset(getActivity().getAssets(), "fb.ttf");
		TextView tvHelpTitle;
		TextView tvHelp;
		
		tvHelpTitle = (TextView) v.findViewById(R.id.tvHelpTitle);
		tvHelpTitle.setTypeface(fb);
		tvHelpTitle.setText("Vous avez le choix");
		
		tvHelp = (TextView) v.findViewById(R.id.tvHelp);
		tvHelp.setTypeface(fb);
		tvHelp.setText("Sélectionnez le conducteur de votre choix dans la liste des réponses à votre demande de treep.");
		return v;
	}
}