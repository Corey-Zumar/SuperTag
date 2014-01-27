package com.ceazy.lib.SuperTag.Error;

import android.os.Parcel;
import android.os.Parcelable;

/**<b>class SuperError</b>*/
public class SuperError implements Parcelable {
	
	String explanation, exceptionString;
	
	/**<b>{@link SuperError} class constructor</b>
	 * 
	 * @param explanation An explanation of the error
	 * @param exceptionString The thrown exception in string form
	 */
	public SuperError(String explanation, String exceptionString) {
		this.explanation = explanation;
		this.exceptionString = exceptionString;
	}
	
	protected SuperError(Parcel in) {
		this.explanation = in.readString();
		this.exceptionString = in.readString();
	}
	
	/**<b><i>public <code>String</code> getExplanation()</i></b>
	 * 
	 * @return A readable explanation of the error
	 */
	public String getExplanation() {
		return explanation;
	}
	
	/**<b><i>public <code>String</code> getExceptionString()</i></b>
	 * 
	 * @return The thrown exception in string form
	 */
	public String getExceptionString() {
		return exceptionString;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(getExplanation());
		out.writeString(getExceptionString());
	}
	
	public static final Creator<SuperError> CREATOR = new Creator<SuperError>() {

		@Override
		public SuperError createFromParcel(Parcel in) {
			return new SuperError(in);
		}

		@Override
		public SuperError[] newArray(int size) {
			return new SuperError[size];
		}
		
	};

}
