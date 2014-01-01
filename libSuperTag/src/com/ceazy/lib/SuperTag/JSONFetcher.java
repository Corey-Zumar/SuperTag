package com.ceazy.lib.SuperTag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.exceptions.OAuthConnectionException;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;

public class JSONFetcher {
	
	String function;
	String searchPhrase;
	Context context;
	Messenger messenger;
	LocationManager lManager;
	boolean returnObj;
	
	public JSONFetcher(Context context, Messenger messenger, String function, 
			String searchPhrase, boolean returnObj) {
		this.context = context;
		this.messenger = messenger;
		this.function = function;
		this.returnObj = returnObj;
		try {
			setSearchPhrase(URLEncoder.encode(searchPhrase, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	private boolean returnObject() {
		return returnObj;
	}
	
	public String getKeyForId(int id) {
		return getContext().getResources().getString(id);
	}
	
	public void retrieveJSONInfo() {
		String function = getFunction();
		if(function.equals("location")) {
			getLocationJSON();
		} else if(function.equals("newsMedia")) {
			getNewsJSON();
		} else if(function.equals("movieMedia")) {
			getMovieJSON();
		} else if(function.equals("food")) {
			getFoodJSON();
		} else if(function.equals("videoMedia")) {
			getVideoJSON();
		}
	}
	
	private void fetchJSON(final Messenger messenger, final String request_url, 
			final String key, final boolean returnObj) {
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
					Message msg = Message.obtain();
					Bundle data = new Bundle();
					if(returnObj) {
						JSONParser parser = new JSONParser(getContext());
						data.putParcelableArrayList("result", 
								(ArrayList<? extends Parcelable>) parser.
								parseJSONForKey(key, result));
					} else  {
						data.putString("result", result);
					}
					data.putString("key", key);
					msg.setData(data);
					try {
						messenger.send(msg);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
				
			}
			
		});
		thread.start();
	}
	
	private void fetchOAuthJSON(final Messenger messenger, final OAuthRequest request, 
			final String key, final boolean returnObj) {
		Thread thread = new Thread(new Runnable () {

			@Override
			public void run() {
				Response response = null;
				String result = null;
				try {
					response = request.send();
					result = response.getBody();
				} catch (OAuthConnectionException e) {
					e.printStackTrace();
				}
				if(result != null) {
					Message msg = Message.obtain();
					Bundle data = new Bundle();
					if(returnObj) {
						JSONParser parser = new JSONParser(getContext());
						data.putParcelableArrayList("result", 
								(ArrayList<? extends Parcelable>) parser.
								parseJSONForKey(key, result));
					} else {
						data.putString("result", result);
					}
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
		for(String provider : lManager.getAllProviders()) {
			Location currentLocation = lManager.getLastKnownLocation(provider);
			if(currentLocation != null) {
				return currentLocation;
			}
		}
		return null;
	}
	
	private void getLocationJSON() {
		final String places_url = createPlacesQuery(getSearchPhrase(), "search", "name", "rankby=distance", null);
		fetchJSON(getMessenger(), places_url, "location", returnObject());
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
		if(currentLocation != null) {
			builder.append("&location=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude());
		}
		builder.append("&sensor=false&key=" + getKeyForId(R.string.GoogleAPIKey));
		return builder.toString();
	}
	
	class MapsDetailsHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			String result = msg.getData().getString("result");
			try {
				JSONObject obj = new JSONObject(result).getJSONArray("results").getJSONObject(0);
				String reference = obj.get("reference").toString();
				String details_url = "https://maps.googleapis.com/maps/api/place/details/json?sensor=false&key="
						+ getKeyForId(R.string.GoogleAPIKey) + "&reference=" + URLEncoder.encode(reference, "utf-8");
				fetchJSON(getMessenger(), details_url, "detailed_place", false);
			} catch (JSONException e) {
				//INVOKE GOOGLE MAPS API
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			super.handleMessage(msg);
		}
		
	}
	//FOOD
	private void getFoodJSON() {
		Location currentLocation = getCurrentLocation();
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
		request.addQuerystringParameter("term", getSearchPhrase());
		if(currentLocation != null) {
			request.addQuerystringParameter("ll", currentLocation.getLatitude() + "," +
					currentLocation.getLongitude());
		}
		Token accessToken = new Token(getKeyForId(R.string.YelpToken),
				getKeyForId(R.string.YelpSecretToken));
		OAuthService service = new ServiceBuilder().provider(YelpApi2.class)
				.apiKey(getKeyForId(R.string.YelpConsumerKey))
				.apiSecret(getKeyForId(R.string.YelpConsumerSecretKey)).build();
		service.signRequest(accessToken, request);
		fetchOAuthJSON(getMessenger(), request, "food", returnObject());
	}
	
	//NEWS
	private void getNewsJSON() {
		final String news_url = "http://api.feedzilla.com/v1/articles/search.json?q=" + getSearchPhrase();
		fetchJSON(getMessenger(), news_url, "newsMedia", returnObject());
	}
	
	//MOVIES
	private void getMovieJSON() {
		final String rt_url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=" 
				+ getKeyForId(R.string.RottenTomatoesAPIKey) + "&q=" + getSearchPhrase() +"&page_limit=1";
		fetchJSON(getMessenger(), rt_url, "movieMedia", returnObject());
	}
	//VIDEOS
	private void getVideoJSON() {
		StringBuilder builder = new StringBuilder("https://www.googleapis.com/youtube/v3");
		builder.append("/search?part=snippet&q=" + getSearchPhrase());
		builder.append("&type=video&key=" + getKeyForId(R.string.GoogleAPIKey));
		fetchJSON(getMessenger(), builder.toString(), "videoMedia", returnObject());
	}
	

}
