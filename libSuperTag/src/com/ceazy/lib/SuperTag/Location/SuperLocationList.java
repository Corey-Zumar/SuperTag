package com.ceazy.lib.SuperTag.Location;

import java.util.ArrayList;
import java.util.Iterator;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**<b>class SuperLocationList</b>
 *<p>A data class that stores {@link SuperLocation} objects created via the {@link com.ceazy.lib.SuperTag.SuperTag#getJSON getJSON}
 * method in the {@link com.ceazy.lib.SuperTag.SuperTag SuperTag} class for the "location" 
 * {@link com.ceazy.lib.SuperTag.SuperTag#function function}. See hashtags.xml for a list of supported functions 
 * and their corresponding hashtags</p></p>
 */
public class SuperLocationList implements Parcelable, Iterable<SuperLocation> {
	
	ArrayList<SuperLocation> results;
	
	/**<b>{@link SuperLocationList} class constructor</b>
	 */
	public SuperLocationList() {
		
	}
	
	protected SuperLocationList(Parcel in) {
		this.results = in.readBundle().getParcelableArrayList("results");
	}
	
	/**<b><i>public {@link SuperLocation} get(<code>int</code> index)</i></b>
	 * <br></br>
	 * @param index
	 * @return A {@link SuperLocation} object at the specified index
	 */
	public SuperLocation get(int index) {
		return results.get(index);
	}
	
	/**<b><i>public <code>int</code> size()</i></b>
	 * <br></br>
	 * @return The size of the {@link SuperLocationList}
	 */
	public int size() {
		return results.size();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList("results", results);
		out.writeBundle(bundle);
	}
	
	public static final Creator<SuperLocation> CREATOR = new Creator<SuperLocation>() {

		@Override
		public SuperLocation createFromParcel(Parcel in) {
			return new SuperLocation(in);
		}

		@Override
		public SuperLocation[] newArray(int size) {
			return new SuperLocation[size];
		}
		
	};

	@Override
	public Iterator<SuperLocation> iterator() {
		return new SuperLocationIterator();
	}
	
	class SuperLocationIterator implements Iterator<SuperLocation> {

		int index = 0;

		@Override
		public boolean hasNext() {
			if(index >= results.size()) {
				return false;
			}
			return true;
		}

		@Override
		public SuperLocation next() {
			int tempIndex = index;
			index++;
			return results.get(tempIndex);
		}

		@Override
		public void remove() {
			results.remove(index);
			index--;
		}
		
	}

}
