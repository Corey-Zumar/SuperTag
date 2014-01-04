package com.ceazy.lib.SuperTag;

import android.content.Context;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;


/**<b>class SuperTag</b>
 * <p>A data class containing a hashtag, its associated phrase and function,
 * and a SuperIntent that can be used to search for its content (optional).</p>*/
public class SuperTag implements Parcelable {
	
	/** The search phrase following the {@link SuperTag#hashTag hashtag} */
	private String hashPhrase;
	/** A recognized hashtag (#location, #video, etc)*/
	private String hashTag;
	/** The function associated with the {@link SuperTag#hashTag hashtag}  
	 * (location, videoMedia, etc)*/
	private String function;
	private int startIndex, endIndex;
	private SuperIntent superIntent;
	
	/**<b><i>{@link SuperTag} class constructor</i></b>
	 * @param hashTag A recognized hashtag (#location, #video, etc) 
	 * @param hashPhrase The search phrase following the hashTag
	 * @param function The function associated with the hashTag (location, videoMedia, etc)
	 * */
	public SuperTag(String hashTag, String hashPhrase, String function) {
		setHashTag(hashTag);
		setHashPhrase(hashPhrase);
		setFunction(function);
	}
	
	protected SuperTag(Parcel in) {
		setFunction(in.readString());
		setHashTag(in.readString());
		setHashPhrase(in.readString());
		if(in.dataAvail() > 0) {
			setIntent((SuperIntent) in.readParcelable(SuperIntent.class.getClassLoader()));
			if(in.dataAvail() > 0) {
				setStartIndex(in.readInt());
				setEndIndex(in.readInt());
			}
		}
	}
	
	/**<b><i> public void setHashTag(<code>String</code> hashTag) </b></i>
	 * <p>Sets the hashtag associated with the SuperTag</p>
	 * @param hashTag See {@link SuperTag#hashTag hashtag}
	 */
	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}
	
	/**<b><i> public void setHashPhrase(<code>String</code> hashPhrase) </b></i>
	 * <p>Sets the hashphrase associated with the SuperTag</p>
	 * @param hashPhrase See {@link SuperTag#hashPhrase hashphrase}
	 */
	public void setHashPhrase(String hashPhrase) {
		this.hashPhrase = hashPhrase;
	}
	
	/**<b><i> public void setFunction(<code>String</code> function) </b></i>
	 * <p>Sets the function associated with the SuperTag</p>
	 * @param function See {@link SuperTag#function function}
	 */
	public void setFunction(String function) {
		this.function = function;
	}
	
	/** <b><i> public void setStartIndex(<code>int</code> startIndex) </b></i>
	 * <p>Sets the index in a string where the {@link SuperTag#hashTag hashtag} is located.
	 * This is useful for creating spannables.</p>
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
	/** <b><i> public void setEndIndex(<code>int</code> endIndex) </i></b>
	 * <p>Sets the index in a string where the {@link SuperTag@hashPhrase hashphrase} ends</p>
	 */
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	
	/**<b><i> public void setIntent({@link SuperIntent} 
	 * superIntent) </b></i>
	 * <p>Sets the SuperIntent that can launch an appropriate application to search for the
	 * SuperTag's {@link SuperTag#hashPhrase hashphrase}</p>
	 * @param superIntent See {@link SuperIntent}
	 */
	public void setIntent(SuperIntent superIntent) {
		this.superIntent = superIntent;
	}
	
	/**<b><i> public String getHashTag()</i></b>
	 * <br></br>
	 * @return The {@link SuperTag#hashTag hashtag} associated with the SuperTag
	 */
	public String getHashTag() {
		return hashTag;
	}
	
	/**<b><i> public String getHashPhrase()</i></b>
	 * <br></br>
	 * @return The {@link SuperTag#hashPhrase hashphrase} associated with the SuperTag
	 */
	public String getHashPhrase() {
		return hashPhrase;
	}
	
	/**<b><i> public String getFunction()</i></b>
	 * <br></br>
	 * @return The {@link SuperTag#function function} associated with the SuperTag
	 */
	public String getFunction() {
		return function;
	}
	
	/** <b><i> public int getStartIndex() </i></b>
	 * <br></br>
	 * @return The index in a string where the {@link SuperTag#hashTag hashtag} is located
	 */
	public int getStartIndex() {
		return startIndex;
	}
	
	/** <b><i> public int getEndIndex() </i></b>
	 * <br></br>
	 * @return The index in a string where the {@link SuperTag#hashPhrase hashphrase} ends
	 */
	public int getEndIndex() {
		return endIndex;
	}
	
	protected SuperIntent getIntent() {
		return superIntent;
	}
	
	/** <b><i> public {@link SuperIntent} getIntent({@link android.content.Context Context}
	 * context) </i></b>
	 * <p> Retrieves the SuperIntent associated with the SuperTag. If it is null, 
	 * a SuperIntent will be created based on the SuperTag's {@link SuperTag#hashPhrase hashPhrase}
	 * and {@link SuperTag#function function} and returned.</p>
	 * @return The {@link SuperIntent} associated with the SuperTag
	 */
	public SuperIntent getIntent(Context context) {
		if(getIntent() != null) {
			return getIntent();
		} else {
			SuperIntentCreator intentCreator = new SuperIntentCreator(context);
			setIntent(new SuperIntent(intentCreator.getIntentsForFunction(getFunction(), 
					getHashPhrase())));
			return getIntent();
		}
		
	}
	
	/**<b><i> public void getJSON({@link android.content.Context Context} context, 
	 * {@link android.os.Messenger Messenger} messenger, <code>boolean</code> returnObj})</i></b>
	 * <p>Queries information from various APIs based on the SuperTag's {@link SuperTag#hashPhrase
	 * hashphrase} and {@link SuperTag#function function} and sends it to a handler through a 
	 * provided Messenger object in the form of a JSON-formatted string or custom data class 
	 * ({@link com.ceazy.lib.SuperTag.Location.SuperLocation SuperLocation}, 
	 * {@link com.ceazy.lib.SuperTag.Video.SuperVideo SuperVideo}, etc)</p>
	 * <p>To be used in place of or in conjunction with the {@link SuperTag#getIntent(Context context) getIntent}
	 * method</p>
	 * @param context
	 * @param messenger The {@link android.os.Messenger messenger} through which the retrieved 
	 * JSON content should be sent
	 * @param returnObj Boolean - If true, the JSON content will be automatically parsed into 
	 * a custom data object. If false, it will be left in string form.
	 */
	public void getJSON(Context context, Messenger messenger, boolean returnObj) {
		
		JSONFetcher fetcher = new JSONFetcher(context, messenger, getFunction(), 
				getHashPhrase(), returnObj);
		fetcher.retrieveJSONInfo();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int arg1) {
		out.writeString(getFunction());
		out.writeString(getHashTag());
		out.writeString(getHashPhrase());
		out.writeParcelable(getIntent(), 0);
		out.writeInt(getStartIndex());
		out.writeInt(getEndIndex());
	}
	
	public static final Creator<SuperTag> CREATOR = new Creator<SuperTag>() {

		@Override
		public SuperTag createFromParcel(Parcel in) {
			return new SuperTag(in);
		}

		@Override
		public SuperTag[] newArray(int size) {
			return new SuperTag[size];
		}
		
	};
	
}
