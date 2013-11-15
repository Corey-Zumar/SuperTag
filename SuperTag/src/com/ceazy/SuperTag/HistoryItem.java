package com.ceazy.SuperTag;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class HistoryItem {
	
	private long date;
	private String pkgName, hashTag, hashPhrase, action;
	private Drawable appIcon;
	
	public HistoryItem(long date, String pkgName, String hashTag, String hashPhrase) {
		this.date = date;
		this.pkgName = pkgName;
		this.hashTag = hashTag;
		this.hashPhrase = hashPhrase;
	}
	
	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public long getDate() {
		return date;
	}
	
	public String getPkgName() {
		return pkgName;
	}
	
	public String getHashTag() {
		return hashTag;
	}
	
	public String getHashPhrase() {
		return hashPhrase;
	}
	
	public Drawable getAppIcon() {
		return appIcon;
	}
	
	public String getAction() {
		return action;
	}

}
