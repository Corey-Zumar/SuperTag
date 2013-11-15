package com.ceazy.SuperTag.News;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewsFragment extends DialogFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		int theme = 0;
		if(android.os.Build.VERSION.SDK_INT >= 11) {
			theme = android.R.style.Theme_Holo_Light_Dialog;
		} else {
			theme = android.R.style.Theme_Dialog;
		}
		setStyle(DialogFragment.STYLE_NO_TITLE, theme);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	
	
	
	
	

}
