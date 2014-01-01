package com.ceazy.lib.SuperTag;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.ceazy.lib.SuperTag.Location.SuperLocation;
import com.ceazy.lib.SuperTag.Movie.SuperMovie;
import com.ceazy.lib.SuperTag.News.SuperArticle;

import android.content.Context;

public class JSONParser {
	
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
	
	

}
