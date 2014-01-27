package com.ceazy.lib.SuperTag.Music;

import java.util.Iterator;

import android.os.Parcel;
import android.os.Parcelable;

/**<b>class SuperTrackList</b>
 *<p>A data class that stores {@link SuperTrack} objects created via the {@link com.ceazy.lib.SuperTag.SuperTag#getJSON getJSON}
 * method in the {@link com.ceazy.lib.SuperTag.SuperTag SuperTag} class for the "musicMediaTrack" subfunction. 
 * See subtags.xml for a list of Music subfunctions and their corresponding hashtags</p>
 */
public class SuperTrackList implements Parcelable, Iterable<SuperTrack> {

	SuperTrack[] tracks;

	@Override
	public Iterator<SuperTrack> iterator() {
		return new SuperTrackIterator();
	}
	
	/**<b>{@link SuperTrackList} class constructor</b>*/
	public SuperTrackList() {
		
	}
	
	protected SuperTrackList(SuperTrack[] tracks) {
		this.tracks = tracks;
	}
	
	protected SuperTrackList(Parcel in) {
		this.tracks = (SuperTrack[]) in.readParcelableArray(SuperTrack.class.getClassLoader());
	}
	
	/**<b><i>public {@link SuperTrack} get(<code>int</code> index)</i></b>
	 * <br></br>
	 * @param index
	 * @return A {@link SuperTrack} object at the specified index
	 */
	public SuperTrack get(int index) {
		return tracks[index];
	}
	
	/**<b><i>public <code>int</code> size()</i></b>
	 * <br></br>
	 * @return The size of the {@link SuperTrackList}
	 */
	public int size() {
		return tracks.length;
	}
	
	class SuperTrackIterator implements Iterator<SuperTrack> {

		int index = 0;

		@Override
		public boolean hasNext() {
			if(index >= tracks.length) {
				return false;
			}
			return true;
		}

		@Override
		public SuperTrack next() {
			int tempIndex = index;
			index++;
			return tracks[tempIndex];
		}

		@Override
		public void remove() {
			tracks[index] = null;
			index--;
		}
		
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeParcelableArray(tracks, 0);
	}
	
	public Creator<SuperTrackList> CREATOR = new Creator<SuperTrackList>() {

		@Override
		public SuperTrackList createFromParcel(Parcel out) {
			return new SuperTrackList(out);
		}

		@Override
		public SuperTrackList[] newArray(int size) {
			return new SuperTrackList[size];
		}
		
	};

}
