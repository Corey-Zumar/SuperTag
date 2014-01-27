package com.ceazy.lib.SuperTag.Error;

import java.util.ArrayList;
import java.util.Iterator;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**<b>class SuperErrorList</b>
 *<p>A data class that stores {@link SuperError} objects created via the {@link com.ceazy.lib.SuperTag.SuperTag#getJSON getJSON}
 * method in the {@link com.ceazy.lib.SuperTag.SuperTag SuperTag} class</p>
 */
public class SuperErrorList implements Parcelable, Iterable<SuperError> {

	ArrayList<SuperError> errorList;
	
	/**<b>{@link SuperErrorList} class constructor</b>*/
	public SuperErrorList() {
		errorList = new ArrayList<SuperError>();
	}

	protected SuperErrorList(Parcel in) {
		this.errorList = in.readBundle().getParcelableArrayList("errorList");
	}
	
	/**<b><i>public {@link SuperError} get(<code>int</code> index)</i></b>
	 * <br></br>
	 * @param index
	 * @return A {@link SuperError} object at the specified index
	 */
	public SuperError get(int index) {
		return errorList.get(index);
	}

	/**<b><i>public <code>int</code> size()</i></b>
	 * <br></br>
	 * @return The size of the {@link SuperErrorList}
	 */
	public int size() {
		return errorList.size();
	}

	public void add(SuperError error) {
		errorList.add(error);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList("errorList", errorList);
		out.writeBundle(bundle);
	}

	@Override
	public Iterator<SuperError> iterator() {
		return new SuperErrorIterator();
	}

	public static final Creator<SuperErrorList> CREATOR = new Creator<SuperErrorList>() {

		@Override
		public SuperErrorList createFromParcel(Parcel in) {
			return new SuperErrorList(in);
		}

		@Override
		public SuperErrorList[] newArray(int size) {
			return new SuperErrorList[size];
		}

	};

	class SuperErrorIterator implements Iterator<SuperError> {

		int index = 0;

		@Override
		public boolean hasNext() {
			if (index >= errorList.size()) {
				return false;
			}
			return true;
		}

		@Override
		public SuperError next() {
			int tempIndex = index;
			index++;
			return errorList.get(tempIndex);
		}

		@Override
		public void remove() {
			errorList.remove(index);
			index--;
		}

	}

}
