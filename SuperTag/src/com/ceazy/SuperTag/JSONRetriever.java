package com.ceazy.SuperTag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class JSONRetriever {
	
	String function;
	String searchPhrase;
	Context context;
	Messenger messenger;
	LocationManager lManager;
	
	public JSONRetriever(Context context, Messenger messenger, String function, String searchPhrase) {
		setContext(context);
		setMessenger(messenger);
		setFunction(function);
		setSearchPhrase(searchPhrase.replaceAll("\\s", "+"));
	}
	
	
	private void setMessenger(Messenger messenger) {
		this.messenger = messenger;
	}
	
	private void setContext(Context context) {
		this.context = context;
	}
	
	private void setFunction(String function) {
		this.function = function;
	}
	
	private void setSearchPhrase(String searchPhrase) {
		this.searchPhrase = searchPhrase;
	}
	
	private Context getContext() {
		return context;
	}
	
	private Messenger getMessenger() {
		return messenger;
	}
	
	private String getFunction() {
		return function;
	}
	
	private String getSearchPhrase() {
		return searchPhrase;
	}
	
	private String getAPIKey(int resID) {
		try {
			return URLEncoder.encode(getContext().getResources().getString(resID), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void retrieveJSONInfo() {
		String function = getFunction();
		if(function.equals("location")) {
			getLocationJSON();
		} else if(function.equals("newsMedia")) {
			getNewsJSON();
		} else if(function.equals("movieMedia")) {
			getMovieJSON();
		}
	}
	
	private void fetchJSON(final Messenger messenger, final String request_url, final String key) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				String result = null;
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet request = new HttpGet(request_url);
				try {
					HttpResponse httpResponse = httpClient.execute(request);
					HttpEntity httpEntity = httpResponse.getEntity();
					InputStream inputStream = httpEntity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
					StringBuilder strBuilder = new StringBuilder();
					String line = null;
					while((line = reader.readLine()) != null) {
						strBuilder.append(line + "\n");
					}
					inputStream.close();
					result = strBuilder.toString();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(result != null) {
					Log.d("result", result);
					Message msg = Message.obtain();
					Bundle data = new Bundle();
					data.putString("result", result);
					data.putString("key", key);
					msg.setData(data);
					try {
						messenger.send(msg);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
		});
		thread.start();
	}
	
	//LOCATION
	
	private Location getCurrentLocation() {
		if(lManager == null) {
			lManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
		}
		return lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	}
	
	private String createPlacesQuery(String searchPhrase, String searchType, String inputType, String distanceAttrib, String type) {
		Location currentLocation = getCurrentLocation();
		StringBuilder builder = new StringBuilder();
		builder.append("https://maps.googleapis.com/maps/api/place/");
		builder.append(searchType + "/json?");
		builder.append(inputType + "=" + searchPhrase);
		if(type != null) {
			try {
				builder.append("&types=" + URLEncoder.encode(type, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		builder.append("&"+distanceAttrib);
		builder.append("&location=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude());
		builder.append("&sensor=false&key=" + getAPIKey(R.string.maps_api_key));
		return builder.toString();
	}
	
	
	private void getLocationJSON() {
		final String places_url = createPlacesQuery(getSearchPhrase(), "search", "name", "rankby=distance", null);
		Log.d("PLACES", places_url);
		final String transportation_url = createPlacesQuery("public+transportation", "textsearch", "query", "radius=5000",
				"bus_station|subway_station|taxi_stand");
		fetchJSON(new Messenger(new MapsDetailsHandler()), places_url, "general_place");
		fetchJSON(getMessenger(), transportation_url, "transportation");
	}
	
	class MapsDetailsHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			String result = msg.getData().getString("result");
			try {
				JSONObject obj = new JSONObject(result).getJSONArray("results").getJSONObject(0);
				String reference = obj.get("reference").toString();
				String details_url = "https://maps.googleapis.com/maps/api/place/details/json?sensor=false&key="
						+getAPIKey(R.string.maps_api_key) + "&reference=" + URLEncoder.encode(reference, "utf-8");
				fetchJSON(getMessenger(), details_url, "detailed_place");
			} catch (JSONException e) {
				Log.d("INVALID", "OR NO RESULTS");
				//INVOKE GOOGLE MAPS API
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			super.handleMessage(msg);
		}
		
	}
	
	//NEWS
	private void getNewsJSON() {
		final String news_url = "http://api.feedzilla.com/v1/articles/search.json?q=" + getSearchPhrase();
		fetchJSON(getMessenger(), news_url, "news");
	}
	
	//MOVIES
	private void getMovieJSON() {
		final String rt_url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=" + getAPIKey(R.string.rt_api_key) + 
				"&q=" + getSearchPhrase() +"&page_limit=1";
		Log.d("URL", rt_url);
		fetchJSON(getMessenger(), rt_url, "movie_rt");
	}
	
	

}
