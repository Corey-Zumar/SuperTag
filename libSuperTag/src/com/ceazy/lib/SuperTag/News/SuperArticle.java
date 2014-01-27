package com.ceazy.lib.SuperTag.News;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**<b>class SuperArticle</b>
 * <p>A data class containing information about a news article obtained via a {@link SuperArticleList} and the 
 * {@link com.ceazy.lib.SuperTag.SuperTag#getJSON getJSON} method within a "newsMedia" {@link com.ceazy.lib.SuperTag.SuperTag
 * SuperTag}</p>
 * <p>SuperArticle data is obtained using the Feedzilla News API
 * @see <a href="https://code.google.com/p/feedzilla-api/wiki/RestApi">Feedzilla News API</a>
 */
public class SuperArticle implements Parcelable {
	
	String title, summary, author;
	@SerializedName("publish_date")
	String date;
	@SerializedName("source_url")
	String sourceURL;
	
	/**<b>{@link SuperArticle} class constructor</b>*/
	public SuperArticle() {
		
	}
	
	protected SuperArticle(Parcel in) {
		this.title = in.readString();
		this.summary = in.readString();
		this.author = in.readString();
		this.date = in.readString();
		this.sourceURL = in.readString();
	}
	
	/**<b><i>public <code>String</code> getTitle()</i></b>
	 * <br></br>
	 * @return The title of the article
	 */
	public String getTitle() {
		return title;
	}
	
	/**<b><i>public <code>String</code> getSummary()</i></b>
	 * <br></br>
	 * @return A brief summary of the article
	 */
	public String getSummary() {
		return summary;
	}
	
	/**<b><i>public <code>String</code> getAuthor()</i></b>
	 * <br></br>
	 * @return The author of the article
	 */
	public String getAuthor() {
		return author;
	}
	
	/**<b><i>public <code>String</code> getDate()</i></b>
	 * <br></br>
	 * @return The date the article was published
	 */
	public String getDate() {
		return date;
	}
	
	/**<b><i>public <code>String</code> getFeedURL()</i></b>
	 * <br></br>
	 * @return A URL associated with the RSS feed from which the article was obtained
	 */
	public String getFeedURL() {
		return sourceURL;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(title);
		out.writeString(summary);
		out.writeString(author);
		out.writeString(date);
		out.writeString(sourceURL);
	}
	
	public static final Creator<SuperArticle> CREATOR = new Creator<SuperArticle>() {

		@Override
		public SuperArticle createFromParcel(Parcel in) {
			return new SuperArticle(in);
		}

		@Override
		public SuperArticle[] newArray(int size) {
			return new SuperArticle[size];
		}
		
	};

}
