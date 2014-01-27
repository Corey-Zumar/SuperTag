package com.ceazy.lib.SuperTag.Movie;

import java.util.ArrayList;
import java.util.Iterator;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**<b>class SuperMovieList</b>
 *<p>A data class that stores {@link SuperMovie} objects created via the {@link com.ceazy.lib.SuperTag.SuperTag#getJSON getJSON}
 * method in the {@link com.ceazy.lib.SuperTag.SuperTag SuperTag} class for the "movieMedia" 
 * {@link com.ceazy.lib.SuperTag.SuperTag#function function}. See hashtags.xml for a list of supported functions 
 * and their corresponding hashtags</p></p>
 */
public class SuperMovieList implements Parcelable, Iterable<SuperMovie> {
	
	ArrayList<SuperMovie> movies;

	/**<b>{@link SuperMovieList} class constructor</b>*/
	public SuperMovieList() {
		
	}
	
	/**<b><i>public {@link SuperMovie} get(<code>int</code> index)</i></b>
	 * <br></br>
	 * @param index
	 * @return A {@link SuperMovie} object at the specified index
	 */
	public SuperMovie get(int index) {
		return movies.get(index);
	}
	
	/**<b><i>public <code>int</code> size()</i></b>
	 * <br></br>
	 * @return The size of the {@link SuperMovieList}
	 */
	public int size() {
		return movies.size();
	}
	
	protected SuperMovieList(Parcel in) {
		this.movies = in.readBundle().getParcelableArrayList("movies");
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList("movies", movies);
		out.writeBundle(bundle);
	}
	
	public static final Creator<SuperMovieList> CREATOR = new Creator<SuperMovieList>() {

		@Override
		public SuperMovieList createFromParcel(Parcel in) {
			return new SuperMovieList(in);
		}

		@Override
		public SuperMovieList[] newArray(int size) {
			return new SuperMovieList[size];
		}
		
	};
	
	@Override
	public Iterator<SuperMovie> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	class SuperMovieIterator implements Iterator<SuperMovie> {

		int index = 0;

		@Override
		public boolean hasNext() {
			if(index >= movies.size()) {
				return false;
			}
			return true;
		}

		@Override
		public SuperMovie next() {
			int tempIndex = index;
			index++;
			return movies.get(tempIndex);
		}

		@Override
		public void remove() {
			movies.remove(index);
			index--;
		}
		
	}

}
