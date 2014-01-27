package com.ceazy.lib.SuperTag.Music;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**<b>class SuperTrack</b>
 * <p>A data class containing information about a music track obtained via a {@link SuperTrackList} and the 
 * {@link com.ceazy.lib.SuperTag.SuperTag#getJSON getJSON} method within a "musicMedia" {@link com.ceazy.lib.SuperTag.SuperTag
 * SuperTag} with a "musicMediaTrack" subfunction</p>
 * <p>SuperTrack data is obtained using the Spotify Metadata API (Search)
 * @see <a href="https://developer.spotify.com/technologies/web-api/search/">Spotify Metadata API</a>
 */
public class SuperTrack implements Parcelable {
	
	SuperAlbum album;
	String name;
	double popularity;
	double length;
	@SerializedName("href")
	String url;
	SuperArtist[] artists;
	
	public SuperTrack() {
		
	}
	
	protected SuperTrack(Parcel in) {
		this.album = in.readParcelable(SuperAlbumList.class.getClassLoader());
		this.name = in.readString();
		this.popularity = in.readDouble();
		this.length = in.readDouble();
		this.url = in.readString();
		this.artists = (SuperArtist[]) in.readParcelableArray(SuperArtist.class.getClassLoader());
	}
	
	/**<b><i>public {@link SuperAlbum} getAlbum()</i></b>
	 * <br></br>
	 * @return The album associated with the track
	 */
	public SuperAlbum getAlbum() {
		return album;
	}
	
	/**<b><i>public <code>String</code> getName()</i></b>
	 * <br></br>
	 * @return The name of the track
	 */
	public String getName() {
		return name;
	}
	
	/**<b><i>public <code>double</code> getPopularity()</i></b>
	 * <br></br>
	 * @return The popularity of the track on a scale of 0.0 to 1.0
	 */
	public double getPopularity() {
		return popularity;
	}
	
	/**<b><i>public <code>double</code> getLength()</i></b>
	 * <br></br>
	 * @return The length of the track in seconds
	 */
	public double getLength() {
		return length;
	}
	
	/**<b><i>public <code>String</code> getURL()</i></b>
	 * <p>Retrieves a Spotify-formatted URL that can be used to access the track via the Spotify mobile application.</p>
	 * <p>This URL can also be used in conjunction with the <a href="https://developer.spotify.com/technologies/web-api/lookup/">
	 * Spotify Lookup API</a> to obtain detailed information about the track</p>
	 * @return A Spotify-formatted URL
	 */
	public String getURL() {
		return url;
	}
	
	/**<b><i>public {@link SuperArtistList} getArtists()</i></b>
	 * <br></br>
	 * @return A list of the track's artists
	 */
	public SuperArtistList getArtists() {
		return new SuperArtistList(artists);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int arg1) {
		out.writeParcelable(getAlbum(), 0);
		out.writeString(getName());
		out.writeDouble(getPopularity());
		out.writeDouble(getLength());
		out.writeString(getURL());
		out.writeParcelableArray(artists, 0);
	}
	
	public Creator<SuperTrack> CREATOR = new Creator<SuperTrack>() {

		@Override
		public SuperTrack createFromParcel(Parcel in) {
			return new SuperTrack(in);
		}

		@Override
		public SuperTrack[] newArray(int size) {
			return new SuperTrack[size];
		}
		
	};

}
