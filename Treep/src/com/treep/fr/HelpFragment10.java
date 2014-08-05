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

public class HelpFragment10 extends Fragment {

	private ImageView ivHelpPic;
	private ImageView ivGoRight;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_help_layout, container, false);
		
		ivGoRight = (ImageView) v.findViewById(R.id.ivGoRight);
		ivGoRight.setVisibility(View.GONE);
		
		ImageView ivHelpPic = (ImageView) v.findViewById(R.id.ivHelpPic);
		ivHelpPic.setImageResource(R.drawable.help9);
		
		Typeface fb = Typeface.createFromAsset(getActivity().getAssets(), "fb.ttf");
		TextView tvHelpTitle;
		TextView tvHelp;
		
		tvHelpTitle = (TextView) v.findViewById(R.id.tvHelpTitle);
		tvHelpTitle.setTypeface(fb);
		tvHelpTitle.setText("Trajet terminé");
		
		tvHelp = (TextView) v.findViewById(R.id.tvHelp);
		tvHelp.setTypeface(fb);
		tvHelp.setText("Une fois arrivé à destination, vous payez le montant du trajet en espèces (paiement intégré à l'application dans la prochaine version).\nAttribuez une note sur 5 à votre nouvel ami.");
		
		return v;
	}
}