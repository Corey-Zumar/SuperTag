package com.ceazy.lib.SuperTag.Dining;

import java.util.ArrayList;
import java.util.Iterator;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**<b>class SuperRestaurantList</b>
 *<p>A data class that stores {@link SuperRestaurant} objects created via the {@link com.ceazy.lib.SuperTag.SuperTag#getJSON getJSON}
 * method in the {@link com.ceazy.lib.SuperTag.SuperTag SuperTag} class for the "dining" 
 * {@link com.ceazy.lib.SuperTag.SuperTag#function function}. See hashtags.xml for a list of supported functions 
 * and their corresponding hashtags</p>
 */
public class SuperRestaurantList implements Parcelable,
		Iterable<SuperRestaurant> {

	ArrayList<SuperRestaurant> businesses;

	/**<b>{@link SuperRestaurantList} class constructor</b>
	 */
	public SuperRestaurantList() {

	}

	protected SuperRestaurantList(Parcel in) {
		businesses = in.readBundle().getParcelableArrayList("businesses");
	}
	
	/**<b><i>public {@link SuperRestaurant} get(<code>int</code> index)</i></b>
	 * <br></br>
	 * @param index
	 * @return A {@link SuperRestaurant} object at the specified index
	 */
	public SuperRestaurant get(int index) {
		return businesses.get(index);
	}
	
	/**<b><i>public <code>int</code> size()</i></b>
	 * <br></br>
	 * @return The size of the {@link SuperRestaurantList}
	 */
	public int size() {
		return businesses.size();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList("businesses", businesses);
		out.writeBundle(bundle);
	}

	public static final Creator<SuperRestaurantList> CREATOR = 
			new Creator<SuperRestaurantList>() {

		@Override
		public SuperRestaurantList createFromParcel(Parcel in) {
			return new SuperRestaurantList(in);
		}

		@Override
		public SuperRestaurantList[] newArray(int size) {
			return new SuperRestaurantList[size];
		}

	};

	@Override
	public Iterator<SuperRestaurant> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	class SuperRestaurantIterator implements Iterator<SuperRestaurant> {

		int index = 0;

		@Override
		public boolean hasNext() {
			if (index >= businesses.size()) {
				return false;
			}
			return true;
		}

		@Override
		public SuperRestaurant next() {
			int tempIndex = index;
			index++;
			return businesses.get(tempIndex);
		}

		@Override
		public void remove() {
			businesses.remove(index);
			index--;
		}

	}

}
