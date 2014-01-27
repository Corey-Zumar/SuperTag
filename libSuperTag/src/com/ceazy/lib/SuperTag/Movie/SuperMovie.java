package com.ceazy.lib.SuperTag.Movie;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**<b>class SuperMovie</b>
 * <p>A data class containing information about a movie obtained via a {@link SuperMovieList} and the 
* {@link com.ceazy.lib.SuperTag.SuperTag#getJSON getJSON} method within a "movieMedia" {@link com.ceazy.lib.SuperTag.SuperTag
* SuperTag}</p>
* <p>SuperMovie data is obtained using Rotten Tomatoes API
* @see <a href="http://developer.rottentomatoes.com/docs">Rotten Tomatoes API</a>
*/
public class SuperMovie implements Parcelable {
	
	String title, synopsis;
	int year, runtime;
	@SerializedName("mpaa_rating")
	String rating;
	MovieScores scores;
	MoviePosters posters;
	MovieLinks links;
	List<MovieCast> abridged_cast;
	private int criticScore = -1, audienceScore = -1;
	private String posterURL, rtURL;
	private String[] cast;
	
	/**<b>{@link SuperMovie} class constructor</b>*/
	public SuperMovie() {
		
	}
	
	protected SuperMovie(Parcel in) {
		this.title = in.readString();
		this.year = in.readInt();
		this.rating = in.readString();
		this.criticScore = in.readInt();
		this.audienceScore = in.readInt();
		this.runtime = in.readInt();
		this.posterURL = in.readString();
		this.rtURL = in.readString();
		this.synopsis = in.readString();
		this.cast = in.createStringArray();
	}
	
	/**<b><i>public <code>String</code> getTitle()</i></b>
	 * <br></br>
	 * @return The title of the movie
	 */
	public String getTitle() {
		return title;
	}
	
	/**<b><i>public <code>int</code> getYear()</i></b>
	 * <br></br>
	 * @return The year the movie was released
	 */
	public int getYear() {
		return year;
	}
	
	/**<b><i>public <code>String</code> getRating()</i></b>
	 * <br></br>
	 * @return The MPAA rating of the movie (G, PG-13, R, etc)
	 */
	public String getRating() {
		return rating;
	}
	
	/**<b><i>public <code>int</code> getCriticScore()</i></b>
	 * <br></br>
	 * @return The critics' rating of the movie on a scale of 1 to 100
	 */
	public int getCriticScore() {
		if(criticScore != -1) {
			return criticScore;
		}
		return scores.getCriticScore();
	}
	
	/**<b><i>public <code>int</code> getAudienceScore()</i></b>
	 * <br></br>
	 * @return The general audience's rating of the movie on a scale of 1 to 100
	 */
	public int getAudienceScore() {
		if(audienceScore != -1) {
			return audienceScore;
		}
		return scores.getAudienceScore();
	}
	
	/**<b><i>public <code>int</code> getRuntime()</i></b>
	 * <br></br>
	 * @return The runtime of the movie in minutes
	 */
	public int getRuntime() {
		return runtime;
	}
	
	/**<b><i>public <code>String</code> getPosterURL()</i></b>
	 * <br></br>
	 * @return A URL from which an image of the movie's poster can be obtained
	 */
	public String getPosterURL() {
		if(posterURL != null) {
			return posterURL;
		}
		return posters.getPosterURL();
	}
	
	/**<b><i>public <code>String</code> getRottenTomatoesURL()</i></b>
	 * <br></br>
	 * @return A URL providing more information about the movie from <a href="http://www.rottentomatoes.com">
	 * Rotten Tomatoes</a>
	 */
	public String getRottenTomatoesURL() {
		if(rtURL != null) {
			return rtURL;
		}
		return links.getURL();
	}
	
	/**<b><i>public <code>String</code> getSynopsis()</i></b>
	 * <br></br>
	 * @return A synopsis of the movie
	 */
	public String getSynopsis() {
		return synopsis;
	}
	
	/**<b><i>public <code>String[]</code> getCast()</i></b>
	 * <br></br>
	 * @return The cast members of the movie
	 */
	public String[] getCast() {
		if(cast != null) {
			return cast;
		}
		int size = abridged_cast.size();
		String[] castArray = new String[size];
		for(int i = 0; i < size; i++) {
			castArray[i] = abridged_cast.get(i).getName();
		}
		return castArray;
	}
	
	class MovieScores {
		@SerializedName("critic_score")
		int criticScore;
		@SerializedName("audience_score")
		int audienceScore;
		
		protected int getCriticScore() {
			return criticScore;
		}
		
		protected int getAudienceScore() {
			return audienceScore;
		}
	}
	
	class MoviePosters {
		@SerializedName("original")
		String posterURL;
		
		protected String getPosterURL() {
			return posterURL;
		}
	}
	
	class MovieLinks {
		@SerializedName("alternate")
		String URL;
		
		protected String getURL() {
			return URL;
		}
	}
	
	class MovieCast {
		String name;
		
		protected String getName() {
			return name;
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(getTitle());
		out.writeInt(getYear());
		out.writeString(getRating());
		out.writeInt(getCriticScore());
		out.writeInt(getAudienceScore());
		out.writeInt(getRuntime());
		out.writeString(getPosterURL());
		out.writeString(getRottenTomatoesURL());
		out.writeString(getSynopsis());
		out.writeStringArray(getCast());
	}
	
	public static final Creator<SuperMovie> CREATOR = new Creator<SuperMovie>() {

		@Override
		public SuperMovie createFromParcel(Parcel in) {
			return new SuperMovie(in);
		}

		@Override
		public SuperMovie[] newArray(int size) {
			return new SuperMovie[size];
		}
		
	};

}
