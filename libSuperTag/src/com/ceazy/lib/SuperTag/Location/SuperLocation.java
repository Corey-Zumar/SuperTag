package com.ceazy.lib.SuperTag.Location;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**<b>class SuperLocation</b>
  * <p>A data class containing information about a location obtained via a {@link SuperLocationList} and the 
 * {@link com.ceazy.lib.SuperTag.SuperTag#getJSON getJSON} method within a "location" {@link com.ceazy.lib.SuperTag.SuperTag
 * SuperTag}</p>
 * <p>SuperLocation data is obtained using the Google Places API (Place Search specifically)
 * @see <a href="https://developers.google.com/places/documentation/search">Google Place Search API</a>
 *
 */
public class SuperLocation implements Parcelable {
	
	String name, reference;
	double rating = -1;
	@SerializedName("price_level")
	int priceLevel;
	ArrayList<LocationPhoto> photos;
	
	/**<b>{@link SuperLocation} class constructor</b>*/
	public SuperLocation() {
		
	}
	
	protected SuperLocation(Parcel in) {
		this.name = in.readString();
		this.reference = in.readString();
		this.rating = in.readDouble();
		this.priceLevel = in.readInt();
		this.photos = in.readBundle().getParcelableArrayList("photos");
	}
	
	/**<b><i>public <code>String</code> getName()</i></b>
	 * <br></br>
	 * @return The name of the location
	 */
	public String getName() {
		return name;
	}
	
	/**<b><i>public <code>String</code> getDetailsReference()</i></b>
	 * <p>Retrieves a reference string that can be used to obtain more information about a location
	 * via JSON and the Google Places API</p>
	 * @see <a href="https://developers.google.com/places/documentation/details">Google Places Details API</a>
	 * @return A details reference string
	 */
	public String getDetailsReference() {
		return reference;
	}
	
	/**<b><i>public <code>double</code> getRating()</i></b>
	 * <br></br>
	 * @return The location's rating on a scale of 1.0 to 5.0 (may be -1 if not applicable)
	 */
	public double getRating() {
		return rating;
	}
	
	/**<b><i>public <code>int</code> getPriceLevel()</i></b>
	 * <br></br>
	 * @return The location's price level on a scale of 0 to 4 (may be -1 if not applicable)
	 */
	public int getPriceLevel() {
		return priceLevel;
	}
	
	/**<b><i>public <code>String</code> getPhotoReference()</i></b>
	 * <p>Retrieves a reference string that can be used to obtain a photo for a location
	 * via JSON and the Google Places API</p>
	 * @see <a href="https://developers.google.com/places/documentation/photos">Google Places Photos API</a>
	 * @return A photo reference string
	 */
	public String getPhotoReference() {
		if(photos != null && photos.size() > 0) {
			return photos.get(0).getPhotoReference();
		}
		return null;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(name);
		out.writeString(reference);
		out.writeDouble(rating);
		out.writeInt(priceLevel);
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList("photos", photos);
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
	

}
