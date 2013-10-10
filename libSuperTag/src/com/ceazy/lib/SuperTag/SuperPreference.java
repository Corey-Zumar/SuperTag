package com.ceazy.lib.SuperTag;
public class SuperPreference {
	
	private String primaryPkg, primaryName, secondaryPkg, secondaryName, function;
	
	public SuperPreference(String function, String primaryPkg, String primaryName) {
		setPrimaryPkg(primaryPkg);
		setPrimaryName(primaryName);
		setFunction(function);
	}
	
	public void setFunction(String function) {
		this.function = function;
	}
	
	public void setPrimaryPkg(String primaryPkg) {
		this.primaryPkg = primaryPkg;
	}
	
	public void setSecondaryPkg(String secondaryPkg) {
		this.secondaryPkg = secondaryPkg;
	}
	
	public void setPrimaryName(String primaryName) {
		this.primaryName = primaryName;
	}
	
	public void setSecondaryName(String secondaryName) {
		this.secondaryName = secondaryName;
	}
	
	public String getFunction() {
		return function;
	}
	
	public String getPrimaryPkg() {
		return primaryPkg;
	}
	
	public String getSecondaryPkg() {
		if(secondaryPkg == null) {
			return "none";
		} else {
			return secondaryPkg;
		}
	}
	
	public String getPrimaryName() {
		return primaryName;
	}
	
	public String getSecondaryName() {
		return secondaryName;
	}

}
