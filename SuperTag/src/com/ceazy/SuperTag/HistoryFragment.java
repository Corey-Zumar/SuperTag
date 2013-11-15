package com.ceazy.SuperTag;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HistoryFragment extends ListFragment {
	
	public static HistoryFragment newInstance() {
		return null;
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.history_layout, null);
	}
	
	public void update(HistoryItem item) {
		
	}
	
	

}
