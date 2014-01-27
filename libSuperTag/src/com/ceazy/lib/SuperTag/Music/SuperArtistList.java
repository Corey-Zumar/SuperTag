package com.ceazy.lib.SuperTag.Music;

import java.util.Iterator;
import android.os.Parcel;
import android.os.Parcelable;

/**<b>class SuperTrackList</b>
 *<p>A data class that stores {@link SuperArtist} objects created via the {@link com.ceazy.lib.SuperTag.SuperTag#getJSON getJSON}
 * method in the {@link com.ceazy.lib.SuperTag.SuperTag SuperTag} class for the "musicMediaArtist" subfunction. 
 * See subtags.xml for a list of Music subfunctions and their corresponding hashtags</p>
 */
public class SuperArtistList implements Parcelable, Iterable<SuperArtist> {
	
	SuperArtist[] artists;

	@Override
	public Iterator<SuperArtist> iterator() {
		return new SuperArtistIterator();
	}
	
	/**<b>SuperArtistList class constructor</b>*/
	public SuperArtistList() {
		
	}
	
	protected SuperArtistList(SuperArtist[] artists) {
		this.artists = artists;
	}
	
	protected SuperArtistList(Parcel in) {
		this.artists = (SuperArtist[]) in.readParcelableArray(SuperArtist.class.getClassLoader());
	}
	
	/**<b><i>public {@link SuperArtist} get(<code>int</code> index)</i></b>
	 * <br></br>
	 * @param index
	 * @return A {@link SuperArtist} object at the specified index
	 */
	public SuperArtist get(int index) {
		return artists[index];
	}
	
	/**<b><i>public <code>int</code> size()</i></b>
	 * <br></br>
	 * @return The size of the {@link SuperArtistList}
	 */
	public int size() {
		return artists.length;
	}
	
	class SuperArtistIterator implements Iterator<SuperArtist> {

		int index = 0;

		@Override
		public boolean hasNext() {
			if(index >= artists.length) {
				return false;
			}
			return true;
		}

		@Override
		public SuperArtist next() {
			int tempIndex = index;
			index++;
			return artists[tempIndex];
		}

		@Override
		public void remove() {
			artists[index] = null;
			index--;
		}
		
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeParcelableArray(artists, 0);
	}
	
	public Creator<SuperArtistList> CREATOR = new Creator<SuperArtistList>() {

		@Override
		public SuperArtistList createFromParcel(Parcel out) {
			return new SuperArtistList(out);
		}

		@Override
		public SuperArtistList[] newArray(int size) {
			return new SuperArtistList[size];
		}
		
	};

}
