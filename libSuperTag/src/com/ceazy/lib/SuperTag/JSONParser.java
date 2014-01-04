package com.ceazy.lib.SuperTag;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ceazy.lib.SuperTag.Food.SuperRestaurant;
import com.ceazy.lib.SuperTag.Location.SuperLocation;
import com.ceazy.lib.SuperTag.Movie.SuperMovie;
import com.ceazy.lib.SuperTag.News.SuperArticle;
import com.ceazy.lib.SuperTag.Stocks.SuperStock;
import com.ceazy.lib.SuperTag.Video.SuperVideo;

import android.content.Context;
import android.util.Log;

public class JSONParser { //TO DO: USE GSON. Note: isNull may be useful for checking
	
	private Context context;
	
	protected JSONParser(Context context) {
		this.context = context;
	}
	
	private Context getContext() {
		return context;
	}
	
	protected ArrayList<?> parseJSONForKey(String key, String jsonString) {
		if(key.equals("location")) {
			return parseLocationJSON(jsonString);
		} else if(key.equals("newsMedia")) {
			return parseNewsJSON(jsonString);
		} else if(key.equals("movieMedia")) {
			return parseMovieJSON(jsonString);
		} else if(key.equals("videoMedia")) {
			return parseVideoJSON(jsonString);
		} else if(key.equals("food")) {
			return parseFoodJSON(jsonString);
		} else if(key.equals("stocks")) {
			return parseStocksJSON(jsonString);
		}
		return null;
	}
	
	protected ArrayList<SuperLocation> parseLocationJSON(String input) {
		ArrayList<SuperLocation> locationList = new ArrayList<SuperLocation>();
		JSONArray results;
		try {
			results = new JSONObject(input).getJSONArray("results");
			for(int i = 0; i < results.length(); i++) {
				JSONObject object = results.getJSONObject(i);
				String name = object.getString("name");
				String reference = object.getString("reference");
				long rating = object.getLong("rating");
				int priceLevel = object.getInt("price_level");
				SuperLocation location = new SuperLocation(name);
				location.setRating(rating);
				location.setPriceLevel(priceLevel);
				location.setReference(reference);
				String photoReference = object.getJSONArray("photos")
						.getJSONObject(0).getString("photo_reference");
				location.setPhotoReference(photoReference);
				locationList.add(location);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return locationList;
	}
	
	protected ArrayList<SuperMovie> parseMovieJSON(String input) {
		ArrayList<SuperMovie> moviesList = new ArrayList<SuperMovie>();
		try {
			JSONArray movies = new JSONObject(input).getJSONArray("movies");
			for(int i = 0; i < movies.length(); i++) {
					JSONObject movie = movies.getJSONObject(i);
					String title = movie.getString("title");
					int year = movie.getInt("year");
					String rating = movie.getString("mpaa_rating");
					JSONObject scores = movie.getJSONObject("ratings");
					int criticScore = scores.getInt("critics_score");
					int audienceScore = scores.getInt("audience_score");
					int runtime = movie.getInt("runtime");
					String posterURL = movie.getJSONObject("posters")
							.getString("original");
					String rtURL = movie.getJSONObject("links")
							.getString("alternate");
					String synopsis = movie.getString("synopsis");
					JSONArray castArray = movie.getJSONArray("abridged_cast");
					int castLength = castArray.length();
					String[] cast = new String[castLength];
					for(int x = 0; x < castLength; x++) {
						JSONObject castObj = castArray.getJSONObject(x);
						cast[x] = castObj.getString("name");
					}
					SuperMovie superMovie = new SuperMovie(title, year);
					superMovie.setRating(rating);
					superMovie.setCriticScore(criticScore);
					superMovie.setAudienceScore(audienceScore);
					superMovie.setRuntime(runtime);
					superMovie.setPosterURL(posterURL);
					superMovie.setRottenTomatoesURL(rtURL);
					superMovie.setSynopsis(synopsis);
					superMovie.setCast(cast);
					moviesList.add(superMovie);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return moviesList;
	}
	
	protected ArrayList<SuperArticle> parseNewsJSON(String input) {
		ArrayList<SuperArticle> articleList = new ArrayList<SuperArticle>();
		try {
			JSONArray articles = new JSONObject(input).getJSONArray("articles");
			for(int i = 0; i < articles.length(); i++) {
				JSONObject article = articles.getJSONObject(i);
				String title = article.getString("title");
				String date = article.getString("publish_date");
				String sourceURL = article.getString("source_url");
				String summary = article.getString("summary");
				SuperArticle superArticle = new SuperArticle(title, date);
				String author = article.getString("author");
				superArticle.setAuthor(author);
				superArticle.setSourceURL(sourceURL);
				superArticle.setSummary(summary);
				articleList.add(superArticle);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return articleList;
	}
	
	protected ArrayList<SuperVideo> parseVideoJSON(String input) {
		ArrayList<SuperVideo> videoList = new ArrayList<SuperVideo>();
		try {
			JSONArray videos = new JSONObject(input).getJSONArray("items");
			for(int i = 0; i < videos.length(); i++) {
				JSONObject video = videos.getJSONObject(i);
				JSONObject id = video.getJSONObject("id");
				if(id.getString("kind").equals("youtube#video")) {
					String videoId = id.getString("videoId");
					JSONObject snippet = video.getJSONObject("snippet");
					String date = snippet.getString("publishedAt");
					String channelId = snippet.getString("channelId");
					String title = snippet.getString("title");
					String description = snippet.getString("description");
					JSONObject thumbnails = snippet.getJSONObject("thumbnails");
					String thumbnailURL = thumbnails.getJSONObject("medium")
							.getString("url");
					String channelTitle = snippet.getString("channelTitle");
					SuperVideo superVideo = new SuperVideo(title, date);
					superVideo.setVideoURL(videoId);
					superVideo.setChannelURL(channelId);
					superVideo.setDescription(description);
					superVideo.setThumbnailURL(thumbnailURL);
					superVideo.setChannelTitle(channelTitle);
					videoList.add(superVideo);
				}
			}
		} catch(JSONException e) {
			e.printStackTrace();
		}
		return videoList;
	}
	
	public ArrayList<SuperRestaurant> parseFoodJSON(String input) {
		ArrayList<SuperRestaurant> restaurantList = new ArrayList<SuperRestaurant>();
		try {
			JSONArray restaurants = new JSONObject(input).getJSONArray("businesses");
			for(int i = 0; i < restaurants.length(); i++) {
				JSONObject business = restaurants.getJSONObject(i);
				String name = business.getString("name");
				long rating = business.getLong("rating");
				String url = business.getString("mobile_url");
				String photoURL = null;
				if(!business.isNull("image_url")) {
					photoURL = business.getString("image_url");
				}
				String internationalNumber = business.getString("phone");
				String phoneNumber = business.getString("display_phone");
				long distance = business.getLong("distance");
				boolean isClosed = business.getBoolean("is_closed");
				JSONArray addressInfoArray = business.getJSONObject("location")
						.getJSONArray("display_address");
				int length = addressInfoArray.length();
				String[] addressInfo = new String[length];
				for(int x = 0; x < length; x++) {
					addressInfo[x] = addressInfoArray.getString(x);
				}
				JSONArray categoriesArray = business.getJSONArray("categories");
				int categoriesLength = categoriesArray.length();
				String[] categories = new String[categoriesLength];
				for(int y = 0; y < categoriesLength; y++) {
					JSONArray categoryArray = categoriesArray.getJSONArray(y);
					categories[y] = categoryArray.getString(0);
				}
				SuperRestaurant superRestaurant = new SuperRestaurant(name);
				superRestaurant.setRating(rating);
				superRestaurant.setURL(url);
				superRestaurant.setPhotoURL(photoURL);
				superRestaurant.setInternationalPhoneNumber(internationalNumber);
				superRestaurant.setPhoneNumber(phoneNumber);
				superRestaurant.setDistance(distance);
				superRestaurant.setClosedStatus(isClosed);
				superRestaurant.setAddressInfo(addressInfo);
				superRestaurant.setCategories(categories);
				restaurantList.add(superRestaurant);
			}
		} catch(JSONException e) {
			e.printStackTrace();
		}
		return restaurantList;
	}
	
	public ArrayList<SuperStock> parseStocksJSON(String input) {
		ArrayList<SuperStock> stocksList = new ArrayList<SuperStock>();
		try {
			JSONArray stocks = new JSONObject(input).getJSONArray("SOMESHIT");
		} catch(JSONException e) {
			e.printStackTrace();
		}
		return stocksList;
	}
	
	

}
