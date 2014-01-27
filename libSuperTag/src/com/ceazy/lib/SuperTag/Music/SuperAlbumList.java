package com.ceazy.lib.SuperTag.Music;

import java.util.Iterator;
import android.os.Parcel;
import android.os.Parcelable;

/**<b>class SuperAlbumList</b>
 *<p>A data class that stores {@link SuperAlbum} objects created via the {@link com.ceazy.lib.SuperTag.SuperTag#getJSON getJSON}
 * method in the {@link com.ceazy.lib.SuperTag.SuperTag SuperTag} class for the "musicMediaAlbum" subfunction. 
 * See subtags.xml for a list of Music subfunctions and their corresponding hashtags</p>
 */
public class SuperAlbumList implements Parcelable, Iterable<SuperAlbum> {
	
	SuperAlbum[] albums;

	@Override
	public Iterator<SuperAlbum> iterator() {
		return new SuperAlbumIterator();
	}
	
	/**<b>{@link SuperAlbumList} class constructor</b>*/
	public SuperAlbumList() {
		
	}
	
	protected SuperAlbumList(Parcel in) {
		this.albums = (SuperAlbum[]) in.readParcelableArray(SuperAlbum.class.getClassLoader());
	}
	
	/**<b><i>public {@link SuperAlbum} get(<code>int</code> index)</i></b>
	 * <br></br>
	 * @param index
	 * @return A {@link SuperAlbum} object at the specified index
	 */
	public SuperAlbum get(int index) {
		return albums[index];
	}
	
	/**<b><i>public <code>int</code> size()</i></b>
	 * <br></br>
	 * @return The size of the {@link SuperAlbumList}
	 */
	public int size() {
		return albums.length;
	}
	
	class SuperAlbumIterator implements Iterator<SuperAlbum> {

		int index = 0;

		@Override
		public boolean hasNext() {
			if(index >= albums.length) {
				return false;
			}
			return true;
		}

		@Override
		public SuperAlbum next() {
			int tempIndex = index;
			index++;
			return albums[tempIndex];
		}

		@Override
		public void remove() {
			albums[index] = null;
			index--;
		}
		
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeParcelableArray(albums, 0);
	}
	
	public Creator<SuperAlbumList> CREATOR = new Creator<SuperAlbumList>() {

		@Override
		public SuperAlbumList createFromParcel(Parcel out) {
			return new SuperAlbumList(out);
		}

		@Override
		public SuperAlbumList[] newArray(int size) {
			return new SuperAlbumList[size];
		}
		
	};

}
