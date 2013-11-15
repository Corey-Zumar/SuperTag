package com.ceazy.SuperTag;


import org.json.JSONException;
import org.json.JSONObject;

import com.ceazy.SuperTag.Location.Location;
import com.ceazy.SuperTag.Movie.Movie;

import android.content.Context;
import android.util.Log;

public class JSONParser {
	
	private Context context;
	
	public JSONParser(Context context) {
		this.context = context;
	}
	
	private Context getContext() {
		return context;
	}
	
	public Object parseJSONForKey(String key, String jsonString) {
		if(key.equals("detailed_place")) {
			return parseLocationJSON(jsonString);
		} else if(key.equals("news")) {
			Log.d("TEST", jsonString);
		} else if(key.equals("movie_rt")) {
			return parseMovieJSON(jsonString);
		}
		return null;
	}
	
	public Location parseLocationJSON(String input) {
		JSONObject results;
		try {
			results = new JSONObject(input).getJSONObject("result");
			String name = results.getString("name");
			String address = results.getString("formatted_address");
			String phone = results.getString("formatted_phone_number");
			Location location = new Location(name, address);
			location.setPhone(phone);
			return location;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Movie parseMovieJSON(String input) {
		try {
			JSONObject obj = new JSONObject(input);
			JSONObject jsonMovie = obj.getJSONArray("movies").getJSONObject(0);
			String title = jsonMovie.getString("title");
			String year = jsonMovie.getString("year");
			String mpaaRating = jsonMovie.getString("mpaa_rating");
			int criticRating = jsonMovie.getJSONObject("ratings").getInt("critics_score");
			final String poster_url = jsonMovie.getJSONObject("posters").getString("thumbnail");
			Movie movie = new Movie(title, year, mpaaRating, criticRating);
			movie.setPoster(poster_url);
			return movie;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

}
