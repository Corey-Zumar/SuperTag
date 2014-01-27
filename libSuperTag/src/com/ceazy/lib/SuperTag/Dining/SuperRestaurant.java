package com.ceazy.lib.SuperTag.Dining;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**<b>class SuperRestaurant</b>
 * <p>A data class containing information about a restaurant obtained via a {@link SuperRestaurantList} and the 
 * {@link com.ceazy.lib.SuperTag.SuperTag#getJSON getJSON} method within a "dining" {@link com.ceazy.lib.SuperTag.SuperTag
 * SuperTag}</p>
 * <p>SuperRestaurant data is obtained using version 2 of the Yelp Search API
 * @see <a href="http://www.yelp.com/developers/documentation/v2/search_api">Yelp Search API v2</a>
 */
public class SuperRestaurant implements Parcelable {
	
	String name;
	double rating = 1;
	@SerializedName("mobile_url")
	String URL;
	@SerializedName("image_url")
	String photoURL;
	@SerializedName("display_phone")
	String internationalNumber;
	@SerializedName("phone")
	String domesticNumber;
	@SerializedName("is_closed")
	boolean isClosed;
	RestaurantLocation location;
	List<String[]> categories;
	
	/**<b>{@link SuperRestaurant} class constructor</b>*/
	public SuperRestaurant() {
		
	}
	
	@SuppressWarnings("unchecked")
	protected SuperRestaurant(Parcel in) {
		this.name = in.readString();
		this.rating = in.readDouble();
		this.URL = in.readString();
		this.photoURL = in.readString();
		this.internationalNumber = in.readString();
		this.domesticNumber = in.readString();
		this.isClosed = (in.readByte() != 0);
		this.location = in.readParcelable(RestaurantLocation.class.getClassLoader());
		this.categories = in.readArrayList(String[].class.getClassLoader());
	}
	
	/**<b><i>public <code>String</code> getName()</i></b>
	 * <br></br>
	 * @return The name of the restaurant
	 */
	public String getName() {
		return name;
	}
	
	/**<b><i>public <code>double</code> getRating()</i></b>
	 * <br></br>
	 * @return The restaurant's rating on a scale of 1.0 to 5.0
	 */
	public double getRating() {
		return rating;
	}
	
	/**<b><i>public <code>String</code> getURL()</i></b>
	 * <br></br>
	 * @return A URL for accessing information on this website via <a href="http://www.yelp.com">Yelp</a>
	 */
	public String getURL() {
		return URL;
	}
	
	/**<b><i>public <code>String</code> getPhotoURL()</i></b>
	 * <br></br>
	 * @return A URL for accessing a photo of the restaurant via <a href="http://www.yelp.com">Yelp</a>
	 */
	public String getPhotoURL() {
		return photoURL;
	}
	
	/**<b><i>public <code>String</code> getInternationalNumber()</i></b>
	 * <br></br>
	 * @return The restaurant's phone number formatted for international use (includes country code)
	 */
	public String getInternationalNumber() {
		return internationalNumber;
	}
	
	/**<b><i>public <code>String</code> getDomesticNumber()</i></b>
	 * <br></br>
	 * @return The restaurant's phone number formatted for domestic use
	 */
	public String getDomesticNumber() {
		return domesticNumber;
	}
	
	/**<b><i>public <code>boolean</code> isOpen()</i></b>
	 * <br></br>
	 * @return Whether or not the restaurant is open
	 */
	public boolean isOpen() {
		return !isClosed;
	}
	
	/**<b><i>public <code>String</code> getAddress()</i></b>
	 * <br></br>
	 * @return The restaurant's full address (including state and zip code if applicable)
	 */
	public String getAddress() {
		return location.getAddress();
	}
	
	/**<b><i>public <code>String[]</code> getCategories()</i></b>
	 * <p>Retrieves a list of categories describing the restaurant (i.e Chinese, Casual, etc)
	 * @return An array of categories
	 */
	public String[] getCategories() {
		int size = categories.size();
		String[] conciseCategories = new String[size];
		for(int i = 0; i < size; i++) {
			String[] category = categories.get(i);
			conciseCategories[i] = category[0];
		}
		return conciseCategories;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(name);
		out.writeDouble(rating);
		out.writeString(URL);
		out.writeString(photoURL);
		out.writeString(internationalNumber);
		out.writeString(domesticNumber);
		out.writeByte((byte) (isClosed ? 1 : 0));
		out.writeParcelable(location, 0);
		out.writeList(categories);
	}
	
	public static final Creator<SuperRestaurant> CREATOR = 
			new Creator<SuperRestaurant>() {

		@Override
		public SuperRestaurant createFromParcel(Parcel in) {
			return new SuperRestaurant(in);
		}

		@Override
		public SuperRestaurant[] newArray(int size) {
			return new SuperRestaurant[size];
		}
		
	};

}
