package com.treep.fr;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class BlankAnimFragment extends Fragment {

	private ImageView ivRefresh;
	private LinearLayout refreshBigLayout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_blankanim, container, false);
		
		ivRefresh = (ImageView) v.findViewById(R.id.ivRefresh);
		
		
		Animation rotationCenter = AnimationUtils.loadAnimation(getActivity(),R.anim.rotation_center);
		ivRefresh.startAnimation(rotationCenter);
		
		return v;
	}
}