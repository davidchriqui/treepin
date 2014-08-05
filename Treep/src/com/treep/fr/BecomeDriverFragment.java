package com.treep.fr;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.treep.fr.R;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class BecomeDriverFragment extends Fragment {


	private View rootView;
	private Button confirmButton;
	private TextView tvBecomeDriverSubTitle2;
	
	private EditText etLicenceYear;
	private EditText etCarModel;
	private EditText etCity;
	
	private Drawable editTextRounded;
	
	
	
	  private TextWatcher textWatcherLicenceYear = new TextWatcher() {

		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	etLicenceYear.setBackground(editTextRounded);
		    	etLicenceYear.setTextColor(0xff444444);
		    }
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count,
		      int after) {}
		  
		    @Override
		    public void afterTextChanged(Editable s) {}
		  };
	  
	  private TextWatcher textWatcherCarModel = new TextWatcher() {

		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	etCarModel.setBackground(editTextRounded);
		    	etCarModel.setTextColor(0xff444444);
		    }
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count,
		      int after) {}
		  
		    @Override
		    public void afterTextChanged(Editable s) {}
	  };
	  
	  private TextWatcher textWatcherCity = new TextWatcher() {

		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	etCity.setBackground(editTextRounded);
		    	etCity.setTextColor(0xff444444);
		    }
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count,
		      int after) {}
		  
		    @Override
		    public void afterTextChanged(Editable s) {}
	  };
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.fragment_becomedriver, container, false);
		Typeface bello = Typeface.createFromAsset(getActivity().getAssets(), "bello.ttf");
        Typeface fb = Typeface.createFromAsset(getActivity().getAssets(), "fb.ttf");
        
        editTextRounded = getResources().getDrawable( R.drawable.rounded_edittext);
        
        confirmButton = (Button)rootView.findViewById(R.id.confirmButton);
        confirmButton.setTypeface(fb);
        
        
        tvBecomeDriverSubTitle2 = (TextView)rootView.findViewById(R.id.tvBecomeDriverSubTitle2);
        tvBecomeDriverSubTitle2.setTypeface(bello);
		
        etLicenceYear = (EditText)rootView.findViewById(R.id.etLicenceYear);
        etCarModel = (EditText)rootView.findViewById(R.id.etCarModel);
        etCity = (EditText)rootView.findViewById(R.id.etCity);
        
        OnClickListener clickListenerRequestTreepButton = new View.OnClickListener() {
    	    @Override
    	    public void onClick(View v) {
    	    	if(etLicenceYear.getText().toString().length() == 0){
    	    		etLicenceYear.setBackgroundColor(0xAAf00000);
    	    		etLicenceYear.setTextColor(0xffffffff);
    	    		etLicenceYear.setHintTextColor(0xff000000);
	    			MainActivity.displayToast("Veuillez indiquer l'année d'obtention de votre permis de conduire");
    	    	}
    	    	else{
    	    		Pattern patternLicenceYear = Pattern.compile("^[0-9]{4}$");
					Matcher matcherLicenceYear = patternLicenceYear.matcher(etLicenceYear.getText().toString());
					if (!matcherLicenceYear.matches()) {
						etLicenceYear.setBackgroundColor(0xAAf00000);
						etLicenceYear.setTextColor(0xffffffff);
						etLicenceYear.setHintTextColor(0xff000000);
						MainActivity.displayToast("Veuillez indiquer l'année d'obtention de votre permis de conduire");
					}
					else{
						if(etCarModel.getText().toString().length() == 0){
							etCarModel.setBackgroundColor(0xAAf00000);
							etCarModel.setTextColor(0xffffffff);
    						etCarModel.setHintTextColor(0xff000000);
        	    			MainActivity.displayToast("Veuillez entrer le modèle de votre voiture (Autolib' acceptées !)");
						}
						else{
							if(etCity.getText().toString().length() == 0){
								etCity.setBackgroundColor(0xAAf00000);
								etCity.setTextColor(0xffffffff);
								etCity.setHintTextColor(0xff000000);
	        	    			MainActivity.displayToast("Veuillez entrer votre ville");
							}
							else{
								StoreBecomeDriverFormFromXML StoreBecomeDriverFormFromXML = new StoreBecomeDriverFormFromXML(getActivity(),etLicenceYear.getText().toString(),etCarModel.getText().toString(),etCity.getText().toString());
				    	    	StoreBecomeDriverFormFromXML.execute();
							}
						}
					}	
    	    	}
    	    }
    	  };
    	  etLicenceYear.addTextChangedListener(textWatcherLicenceYear);
    	  etCarModel.addTextChangedListener(textWatcherCarModel);
    	  etCity.addTextChangedListener(textWatcherCity);
    	  
    	  confirmButton.setOnClickListener(clickListenerRequestTreepButton);
        
        
        
        
		return rootView;
		
	}

	
	
	
}
