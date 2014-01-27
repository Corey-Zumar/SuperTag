package com.ceazy.lib.SuperTag.News;

import java.util.ArrayList;
import java.util.Iterator;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**<b>class SuperArticleList</b>
 *<p>A data class that stores {@link SuperArticle} objects created via the {@link com.ceazy.lib.SuperTag.SuperTag#getJSON getJSON}
 * method in the {@link com.ceazy.lib.SuperTag.SuperTag SuperTag} class for the "newsMedia" 
 * {@link com.ceazy.lib.SuperTag.SuperTag#function function}. See hashtags.xml for a list of supported functions 
 * and their corresponding hashtags</p></p>
 */
public class SuperArticleList implements Parcelable, Iterable<SuperArticle> {
	
	ArrayList<SuperArticle> articles;
	
	/**<b>{@link SuperArticleList} class constructor</b>*/
	public SuperArticleList() {
		
	}
	
	protected SuperArticleList(Parcel in) {
		this.articles = in.readBundle().getParcelableArrayList("articles");
	}
	
	/**<b><i>public {@link SuperArticle} get(<code>int</code> index)</i></b>
	 * <br></br>
	 * @param index
	 * @return A {@link SuperArticle} object at the specified index
	 */
	public SuperArticle get(int index) {
		return articles.get(index);
	}
	
	/**<b><i>public <code>int</code> size()</i></b>
	 * <br></br>
	 * @return The size of the {@link SuperArticleList}
	 */
	public int size() {
		return articles.size();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList("articles", articles);
		out.writeBundle(bundle);
	}
	
	public static final Creator<SuperArticleList> CREATOR = 
			new Creator<SuperArticleList>() {

		@Override
		public SuperArticleList createFromParcel(Parcel in) {
			return new SuperArticleList(in);
		}

		@Override
		public SuperArticleList[] newArray(int size) {
			return new SuperArticleList[size];
		}
		
	};

	@Override
	public Iterator<SuperArticle> iterator() {
		return new SuperArticleIterator();
	}
	
	class SuperArticleIterator implements Iterator<SuperArticle> {

		int index = 0;

		@Override
		public boolean hasNext() {
			if(index >= articles.size()) {
				return false;
			}
			return true;
		}

		@Override
		public SuperArticle next() {
			int tempIndex = index;
			index++;
			return articles.get(tempIndex);
		}

		@Override
		public void remove() {
			articles.remove(index);
			index--;
		}
		
	}

}
