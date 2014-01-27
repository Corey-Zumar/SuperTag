package com.ceazy.lib.SuperTag.Video;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.os.Parcel;
import android.os.Parcelable;

/**<b>class SuperVideo</b>
 * <p>A data class containing information about a video obtained via a {@link SuperVideoList} and the 
 * {@link com.ceazy.lib.SuperTag.SuperTag#getJSON getJSON} method within a "videoMedia" {@link com.ceazy.lib.SuperTag.SuperTag
 * SuperTag}</p>
 * <p>SuperVideo data is obtained using version 3 of the YouTube Data API
 * @see <a href="https://developers.google.com/youtube/v3/">YouTube Data API</a>
 */
public class SuperVideo implements Parcelable {
	
	VideoID id;
	VideoSnippet snippet;
	private String URL, title, date, description, channelTitle, channelURL,
		thumbnailURL;
	
	/**<b>{@link SuperVideo} class constructor</b>*/
	public SuperVideo() {
		
	}
	
	protected SuperVideo(Parcel in) {
		this.URL = in.readString();
		this.title = in.readString();
		this.date = in.readString();
		this.description = in.readString();
		this.channelTitle = in.readString();
		this.channelURL = in.readString();
		this.thumbnailURL = in.readString();
	}
	
	/**<b><i>public <code>String</code> getURL()</i></b>
	 * <br></br>
	 * @return The video's <a href="http://www.youtube.com">YouTube</a> URL
	 */
	public String getURL() {
		if(URL != null) {
			return URL;
		}
		try {
			return "http://www.youtube.com/watch?v=" + 
						URLEncoder.encode(id.getVideoId(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**<b><i>public <code>String</code> getTitle()</i></b>
	 * <br></br>
	 * @return The title of the video
	 */
	public String getTitle() {
		if(title != null) {
			return title;
		}
		return snippet.getTitle();
	}
	
	/**<b><i>public <code>String</code> getDate()</i></b>
	 * <br></br>
	 * @return The date the video was published
	 */
	public String getDate() {
		if(date != null) {
			return date;
		}
		return snippet.getDate();
	}
	
	/**<b><i>public <code>String</code> getDescription()</i></b>
	 * <br></br>
	 * @return A brief description of the video
	 */
	public String getDescription() {
		if(description != null) {
			return description;
		}
		return snippet.getDescription();
	}
	
	/**<b><i>public <code>String</code> getChannelTitle()</i></b>
	 * <br></br>
	 * @return The title of the YouTube channel on which the video was posted
	 */
	public String getChannelTitle() {
		if(channelTitle != null) {
			return channelTitle;
		}
		return snippet.getChannelTitle();
	}
	
	/**<b><i>public <code>String</code> getChannelURL()</i></b>
	 * <br></br>
	 * @return The URL of the <a href="http://www.youtube.com">YouTube</a> channel on which the video was posted
	 */
	public String getChannelURL() {
		if(channelURL != null) {
			return channelURL;
		}
		try {
			return "http://www.youtube.com/channel/" + URLEncoder.encode(
					snippet.getChannelTitle(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**<b><i>public <code>String</code> getThumbnailURL()</i></b>
	 * <br></br>
	 * @return A <a href="http://www.youtube.com">YouTube</a> URL for the video's thumbnail
	 */
	public String getThumbnailURL() {
		if(thumbnailURL != null) {
			return thumbnailURL;
		}
		return snippet.getThumbnailURL();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(getURL());
		out.writeString(getTitle());
		out.writeString(getDate());
		out.writeString(getDescription());
		out.writeString(getChannelTitle());
		out.writeString(getChannelURL());
		out.writeString(getThumbnailURL());
	}
	
	public static final Creator<SuperVideo> CREATOR = new Creator<SuperVideo>() {

		@Override
		public SuperVideo createFromParcel(Parcel in) {
			return new SuperVideo(in);
		}

		@Override
		public SuperVideo[] newArray(int size) {
			return new SuperVideo[size];
		}
		
	};

}
