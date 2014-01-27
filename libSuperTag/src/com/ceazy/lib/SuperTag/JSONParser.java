package com.ceazy.lib.SuperTag;

import android.os.Parcelable;
import android.util.Log;

import com.ceazy.lib.SuperTag.Dining.SuperRestaurantList;
import com.ceazy.lib.SuperTag.Location.SuperLocationList;
import com.ceazy.lib.SuperTag.Movie.SuperMovieList;
import com.ceazy.lib.SuperTag.Music.SuperAlbumList;
import com.ceazy.lib.SuperTag.Music.SuperArtistList;
import com.ceazy.lib.SuperTag.Music.SuperTrackList;
import com.ceazy.lib.SuperTag.News.SuperArticleList;
import com.ceazy.lib.SuperTag.Stocks.SuperStock;
import com.ceazy.lib.SuperTag.Video.SuperVideoList;
import com.google.gson.Gson;

class JSONParser {

	JSONErrorLogger errorLogger;

	protected JSONParser(JSONErrorLogger errorLogger) {
		this.errorLogger = errorLogger;
	}

	protected JSONErrorLogger getErrorLogger() {
		if(errorLogger.getErrorMap().size() > 0) {
			return errorLogger;
		}
		return null;
	}

	protected Parcelable parseJSONForKey(String key, String jsonString) {
		try {
			Log.d("KEY", key);
			if (key.equals("location")) {
				return parseLocationJSON(jsonString);
			} else if (key.equals("newsMedia")) {
				return parseNewsJSON(jsonString);
			} else if (key.equals("movieMedia")) {
				return parseMovieJSON(jsonString);
			} else if (key.equals("videoMedia")) {
				return parseVideoJSON(jsonString);
			} else if (key.equals("dining")) {
				return parseRestaurantJSON(jsonString);
			} else if (key.equals("stocks")) {
				return parseStocksJSON(jsonString);
			} else if (key.equals("musicMediaTrack")) {
				return parseTrackJSON(jsonString);
			} else if (key.equals("musicMediaAlbum")) {
				return parseAlbumJSON(jsonString);
			} else if (key.equals("musicMediaArtist")) {
				return parseArtistJSON(jsonString);
			}
		} catch (Exception e) {
			errorLogger.log("Error parsing JSON", e.toString());
		}
		return null;
	}

	protected SuperLocationList parseLocationJSON(String input) {
		Gson gson = new Gson();
		return gson.fromJson(input, SuperLocationList.class);
	}

	protected SuperRestaurantList parseRestaurantJSON(String input) {
		Gson gson = new Gson();
		return gson.fromJson(input, SuperRestaurantList.class);
	}

	protected SuperArticleList parseNewsJSON(String input) {
		Gson gson = new Gson();
		return gson.fromJson(input, SuperArticleList.class);
	}

	protected SuperVideoList parseVideoJSON(String input) {
		Gson gson = new Gson();
		return gson.fromJson(input, SuperVideoList.class);
	}

	protected SuperMovieList parseMovieJSON(String input) {
		Gson gson = new Gson();
		return gson.fromJson(input, SuperMovieList.class);
	}

	protected SuperStock parseStocksJSON(String input) {
		Gson gson = new Gson();
		return gson.fromJson(input, SuperStock.class);
	}
	
	protected Parcelable parseTrackJSON(String input) {
		Gson gson = new Gson();
		return gson.fromJson(input, SuperTrackList.class);
	}
	
	protected Parcelable parseAlbumJSON(String input) {
		Gson gson = new Gson();
		return gson.fromJson(input, SuperAlbumList.class);
	}
	
	protected Parcelable parseArtistJSON(String input) {
		Gson gson = new Gson();
		return gson.fromJson(input, SuperArtistList.class);
	}

}
