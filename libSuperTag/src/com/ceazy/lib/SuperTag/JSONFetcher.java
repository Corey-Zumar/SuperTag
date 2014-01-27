package com.ceazy.lib.SuperTag;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.scribe.exceptions.OAuthConnectionException;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

class JSONFetcher {

	String hashTag, hashPhrase, function;
	Context context;
	Messenger messenger;
	boolean returnObj;
	JSONQueryBuilder queryBuilder;
	JSONErrorLogger errorLogger;

	protected JSONFetcher(Context context, Messenger messenger, String hashTag, 
		String hashPhrase, String function, boolean returnObj) {
		this.context = context;
		this.messenger = messenger;
		this.function = function;
		this.hashTag = hashTag;
		this.returnObj = returnObj;
		this.errorLogger = new JSONErrorLogger();
		this.queryBuilder = new JSONQueryBuilder(context, errorLogger);
		setHashPhrase(hashPhrase, function);
	}

	private void setHashPhrase(String hashPhrase, String function) {
		if (function.equals("stocks")) {
			hashPhrase = parseStocksInput(hashPhrase);
		}
		try {
			this.hashPhrase = URLEncoder.encode(hashPhrase, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private Context getContext() {
		return context;
	}

	private JSONQueryBuilder getQueryBuilder() {
		return queryBuilder;
	}

	private Messenger getMessenger() {
		return messenger;
	}
	
	private String getHashTag() {
		return hashTag;
	}

	private String getFunction() {
		return function;
	}

	private String getHashPhrase() {
		return hashPhrase;
	}

	private boolean returnObject() {
		return returnObj;
	}
	
	private JSONErrorLogger getErrorLogger() {
		return errorLogger;
	}

	public void retrieveJSONInfo() {
		String function = getFunction();
		if (function.equals("location")) {
			getLocationJSON();
		} else if (function.equals("newsMedia")) {
			getNewsJSON();
		} else if (function.equals("movieMedia")) {
			getMovieJSON();
		} else if (function.equals("dining")) {
			getDiningJSON();
		} else if (function.equals("videoMedia")) {
			getVideoJSON();
		} else if (function.equals("stocks")) {
			getStocksJSON();
		} else if(function.equals("musicMedia")) {
			getMusicJSON();
		}
	}

	private void fetchJSON(final Messenger messenger, final String requestURL,
			final String key, final boolean returnObj) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				String result = "None";
				try {
					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpGet request = new HttpGet(requestURL);
					HttpResponse httpResponse = httpClient.execute(request);
					HttpEntity httpEntity = httpResponse.getEntity();
					InputStream inputStream = httpEntity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(inputStream, "iso-8859-1"), 8);
					StringBuilder strBuilder = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						strBuilder.append(line + "\n");
					}
					inputStream.close();
					result = strBuilder.toString();
				} catch (Exception e) {
					String explanation = "Error creating HTTP request for" +
							" function '" + key + "' and URL '" + requestURL + "'";
					getErrorLogger().log(explanation, e.toString());
				}
				sendResult(messenger, key, result, returnObj);
			}

		});
		thread.start();
	}

	private void fetchOAuthJSON(final Messenger messenger,
			final OAuthRequest request, final String key,
			final boolean returnObj) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				Response response = null;
				String result = "None";
				try {
					response = request.send();
					result = response.getBody();
				} catch (OAuthConnectionException e) {
					String explanation = "Error creating OAuth request for" +
							" function '" + key + ".'";
					getErrorLogger().log(explanation, e.toString());
				}
				sendResult(messenger, key, result, returnObj);
			}

		});
		thread.start();
	}

	private void sendResult(Messenger messenger, String key, String result,
			boolean returnObj) {
			Message msg = Message.obtain();
			Bundle data = new Bundle();
			data.putString("key", key);
			if (returnObj) {
				JSONParser parser = new JSONParser(getErrorLogger());
				data.putParcelable("result", parser.parseJSONForKey(key, result));
			} else {
				data.putString("result", result);
			}
			data.putParcelable("errors", getErrorLogger().getSuperErrorList());
			msg.setData(data);
			try {
				messenger.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	}

	// LOCATION

	private void getLocationJSON() {
		final String placesURL = getQueryBuilder().buildPlacesQuery(
				getHashPhrase(), "search", "name", "rankby=distance", null);
		fetchJSON(getMessenger(), placesURL, "location", returnObject());
	}

	// DINING
	private void getDiningJSON() {
		OAuthRequest diningRequest = getQueryBuilder().buildYelpQuery(getHashPhrase());
		fetchOAuthJSON(getMessenger(), diningRequest, "dining", returnObject());
	}

	// NEWS
	private void getNewsJSON() {
		String newsURL = getQueryBuilder().buildFeedzillaQuery(getHashPhrase());
		fetchJSON(getMessenger(), newsURL, "newsMedia", returnObject());
	}

	// MOVIES
	private void getMovieJSON() {
		String movieURL = getQueryBuilder().buildRottenTomatoesQuery(hashPhrase);
		fetchJSON(getMessenger(), movieURL, "movieMedia", returnObject());
	}

	// VIDEOS
	private void getVideoJSON() {
		String videoURL = getQueryBuilder().buildYouTubeQuery(hashPhrase);
		fetchJSON(getMessenger(), videoURL, "videoMedia",
				returnObject());
	}

	// STOCKS
	private String parseStocksInput(String input) {
		if (input.contains("'")) {
			input = input.substring(0, input.indexOf("'"));
		}
		return input.toLowerCase();
	}

	private void getStocksJSON() {
		Handler tickerHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				String result = msg.getData().getString("result");
				String queryURL = getQueryBuilder().buildStocksQuoteQuery(result);
				fetchJSON(getMessenger(), queryURL, "stocks", returnObject());
				super.handleMessage(msg);
			}

		};
		String hashPhrase = parseStocksInput(getHashPhrase());
		String tickerURL = getQueryBuilder().buildStocksTickerQuery(hashPhrase);
		fetchJSON(new Messenger(tickerHandler), tickerURL,
				"stocks", false);
	}
	// MUSIC
	private void getMusicJSON() {
		String subtag = new DataParser(getContext()).getMusicSubFunction(getHashTag());
		if(subtag.equals("musicMediaTrack")) {
			getTrackJSON();
		} else if(subtag.equals("musicMediaAlbum")) {
			getAlbumJSON();
		} else {
			getArtistJSON();
		}
	}
	
	private void getTrackJSON() {
		String trackURL = getQueryBuilder().buildSpotifyTrackQuery(getHashPhrase());
		fetchJSON(getMessenger(), trackURL, "musicMediaTrack", returnObject());
	}
	
	private void getAlbumJSON() {
		String albumURL = getQueryBuilder().buildSpotifyAlbumQuery(getHashPhrase());
		fetchJSON(getMessenger(), albumURL, "musicMediaAlbum", returnObject());
	}
	
	private void getArtistJSON() {
		String artistURL = getQueryBuilder().buildSpotifyArtistQuery(getHashPhrase());
		fetchJSON(getMessenger(), artistURL, "musicMediaArtist", returnObject());
	}
}
