package com.ceazy.lib.SuperTag.Music;

public class SuperTrack {
	
	String title, artist, album;
	
	public SuperTrack(String title, String artist) {
		setTitle(title);
		setArtist(artist);
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public void setAlbum(String album) {
		this.album = album;
	}

}
