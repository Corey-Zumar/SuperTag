package com.ceazy.lib.SuperTag;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class ChooserAdapter extends ArrayAdapter<ChooserItem> {

	private List<ChooserItem> chooserItems;
	private Context context;
	private TextView tvLabel;
	private ImageView ivIcon;
	
	protected ChooserAdapter(Context context, int textViewResourceId,
			List<ChooserItem> chooserItems) {
		super(context, textViewResourceId, chooserItems);
		// TODO Auto-generated constructor stub
		this.chooserItems = chooserItems;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		if(v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.chooser_layout, parent, false);
		}
		ChooserItem item = chooserItems.get(position);
		if(item != null) {
			ivIcon = (ImageView) v.findViewById(R.id.ivIcon);
			tvLabel = (TextView) v.findViewById(R.id.tvLabel);
			ivIcon.setImageDrawable(item.getIcon());
			tvLabel.setText(item.getLabel());
		}
		return v;
	}
}
