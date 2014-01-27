package com.ceazy.lib.SuperTag.Music;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**<b>class SuperAlbum</b>
 * <p>A data class containing information about a music album obtained via a {@link SuperAlbumList} and the 
 * {@link com.ceazy.lib.SuperTag.SuperTag#getJSON getJSON} method within a "musicMedia" {@link com.ceazy.lib.SuperTag.SuperTag
 * SuperTag} with a "musicMediaAlbum" subfunction</p>
 * <p>SuperAlbum data is obtained using the Spotify Metadata API (Search)
 * @see <a href="https://developer.spotify.com/technologies/web-api/search/">Spotify Metadata API</a>
 */
public class SuperAlbum implements Parcelable {
	
	String name;
	double popularity;
	@SerializedName("href")
	String url;
	SuperArtist[] artists;
	
	public SuperAlbum() {
		
	}
	
	protected SuperAlbum(Parcel in) {
		this.name = in.readString();
		this.popularity = in.readDouble();
		this.url = in.readString();
		this.artists = (SuperArtist[]) in.readParcelableArray(SuperArtistList.class.getClassLoader());
	}
	
	/**<b><i>public <code>String</code> getName()</i></b>
	 * <br></br>
	 * @return The name of the album
	 */
	public String getName() {
		return name;
	}
	
	/**<b><i>public <code>double</code> getPopularity()</i></b>
	 * <br></br>
	 * @return The popularity of the album on a scale of 0.0 to 1.0
	 */
	public double getPopularity() {
		return popularity;
	}
	
	/**<b><i>public <code>String</code> getURL()</i></b>
	 * <p>Retrieves a Spotify-formatted URL that can be used to access the album via the Spotify mobile application.</p>
	 * <p>This URL can also be used in conjunction with the <a href="https://developer.spotify.com/technologies/web-api/lookup/">
	 * Spotify Lookup API</a> to obtain detailed information about the album</p>
	 * @return A Spotify-formatted URL
	 */
	public String getURL() {
		return url;
	}
	
	/**<b><i>public {@link SuperArtistList} getArtists()</i></b>
	 * <br></br>
	 * @return A list of the album's artists
	 */
	public SuperArtistList getArtists() {
		return new SuperArtistList(artists);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(getName());
		out.writeDouble(getPopularity());
		out.writeString(getURL());
		out.writeParcelableArray(artists, 0);
	}
	
	public Creator<SuperAlbum> CREATOR = new Creator<SuperAlbum>() {

		@Override
		public SuperAlbum createFromParcel(Parcel in) {
			return new SuperAlbum(in);
		}

		@Override
		public SuperAlbum[] newArray(int size) {
			return new SuperAlbum[size];
		}
		
	};

}
