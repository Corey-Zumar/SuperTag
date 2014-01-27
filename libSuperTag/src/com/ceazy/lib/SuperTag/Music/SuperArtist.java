package com.ceazy.lib.SuperTag.Music;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**<b>class SuperArtist</b>
 * <p>A data class containing information about a music artist obtained via a {@link SuperArtistList} and the 
 * {@link com.ceazy.lib.SuperTag.SuperTag#getJSON getJSON} method within a "musicMedia" {@link com.ceazy.lib.SuperTag.SuperTag
 * SuperTag} with a "musicMediaArtist" subfunction</p>
 * <p>SuperArtist data is obtained using the Spotify Metadata API (Search)
 * @see <a href="https://developer.spotify.com/technologies/web-api/search/">Spotify Metadata API</a>
 */
public class SuperArtist implements Parcelable {
	
	@SerializedName("href")
	String url;
	String name;
	double popularity;
	
	/**<b>{@link SuperArtist} class constructor</b>*/
	public SuperArtist() {
		
	}
	
	protected SuperArtist(Parcel in) {
		this.url = in.readString();
		this.name = in.readString();
		this.popularity = in.readDouble();
	}
	
	/**<b><i>public <code>String</code> getURL()</i></b>
	 * <p>Retrieves a Spotify-formatted URL that can be used to access the artist via the Spotify mobile application.</p>
	 * <p>This URL can also be used in conjunction with the <a href="https://developer.spotify.com/technologies/web-api/lookup/">
	 * Spotify Lookup API</a> to obtain detailed information about the artist</p>
	 * @return A Spotify-formatted URL
	 */
	public String getURL() {
		return url;
	}
	
	/**<b><i>public <code>String</code> getName()</i></b>
	 * <br></br>
	 * @return The name of the artist
	 */
	public String getName() {
		return name;
	}
	
	/**<b><i>public <code>double</code> getPopularity()</i></b>
	 * <br></br>
	 * @return The popularity of the artist on a scale of 0.0 to 1.0
	 */
	public double getPopularity() {
		return popularity;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(getURL());
		out.writeString(getName());
		out.writeDouble(getPopularity());
	}
	
	public Creator<SuperArtist> CREATOR = new Creator<SuperArtist>() {

		@Override
		public SuperArtist createFromParcel(Parcel in) {
			return new SuperArtist(in);
		}

		@Override
		public SuperArtist[] newArray(int size) {
			return new SuperArtist[size];
		}
		
	};

}
