package com.ceazy.SuperTag.Location;

import android.graphics.drawable.Drawable;

public class Location {
	
	private String name, address, phone;
	private boolean open;
	private Drawable photo;
	
	public Location(String name, String address) {
		setName(name);
		setAddress(address);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public void setPhoto(Drawable photo) {
		this.photo = photo;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getOpen() {
		return open;
	}
	
	public Drawable getPhoto() {
		return photo;
	}
	
	public String getPhone() {
		return phone;
	}

}
