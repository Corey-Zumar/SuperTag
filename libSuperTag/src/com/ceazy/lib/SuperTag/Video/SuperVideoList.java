package com.ceazy.lib.SuperTag.Video;

import java.util.ArrayList;
import java.util.Iterator;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**<b>class SuperVideoList</b>
 *<p>A data class that stores {@link SuperVideo} objects created via the {@link com.ceazy.lib.SuperTag.SuperTag#getJSON getJSON}
 * method in the {@link com.ceazy.lib.SuperTag.SuperTag SuperTag} class for the "videoMedia" 
 * {@link com.ceazy.lib.SuperTag.SuperTag#function function}. See hashtags.xml for a list of supported functions 
 * and their corresponding hashtags</p></p>
 */
public class SuperVideoList implements Parcelable, Iterable<SuperVideo> {
	
	ArrayList<SuperVideo> items;
	
	/**<b>{@link SuperVideoList} class constructor</b>*/
	public SuperVideoList() {
		
	}
	
	protected SuperVideoList(Parcel in) {
		items = in.readBundle().getParcelableArrayList("items");
	}
	
	/**<b><i>public {@link SuperVideo} get(<code>int</code> index)</i></b>
	 * <br></br>
	 * @param index
	 * @return A {@link SuperVideo} object at the specified index
	 */
	public SuperVideo get(int index) {
		return items.get(index);
	}
	
	/**<b><i>public <code>int</code> size()</i></b>
	 * <br></br>
	 * @return The size of the {@link SuperVideoList}
	 */
	public int size() {
		return items.size();
	}

	@Override
	public Iterator<SuperVideo> iterator() {
		return new VideoIterator();
	}
	
	class VideoIterator implements Iterator<SuperVideo> {
		
		int index = 0;

		@Override
		public boolean hasNext() {
			if(index >= items.size()) {
				return false;
			}
			return true;
		}

		@Override
		public SuperVideo next() {
			int tempIndex = index;
			index++;
			return items.get(tempIndex);
		}

		@Override
		public void remove() {
			items.remove(index);
			index--;
		}
		
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList("items", items);
		out.writeBundle(bundle);
	}
	
	public static final Creator<SuperVideoList> CREATOR = new Creator<SuperVideoList>() {

		@Override
		public SuperVideoList createFromParcel(Parcel in) {
			return new SuperVideoList(in);
		}

		@Override
		public SuperVideoList[] newArray(int size) {
			return new SuperVideoList[size];
		}
		
	};

}
