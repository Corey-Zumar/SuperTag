package com.ceazy.lib.SuperTag;

import android.graphics.drawable.Drawable;

public class ChooserItem {
	
	private String label;
	private Drawable icon;
	
	protected ChooserItem() {
		
	}
	
	protected void setLabel(String label) {
		this.label = label;
	}
	
	protected void setIcon(Drawable icon) {
		this.icon = icon;
	}
	
	protected String getLabel() {
		return label;
	}
	
	protected Drawable getIcon() {
		return icon;
	}

}
