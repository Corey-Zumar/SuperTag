package com.ceazy.lib.SuperTag;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.ceazy.lib.SuperTag.R;
import com.ceazy.lib.SuperTag.Dining.YelpApi2;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

class JSONQueryBuilder {

	Context context;
	JSONErrorLogger errorLogger;

	protected JSONQueryBuilder(Context context, JSONErrorLogger errorLogger) {
		this.context = context;
		this.errorLogger = errorLogger;
	}

	private Context getContext() {
		return context;
	}
	
	private JSONErrorLogger getErrorLogger() {
		return errorLogger;
	}

	public String getKeyForId(int id) {
		return getContext().getResources().getString(id);
	}

	private Location getCurrentLocation() {
		LocationManager locationManager = (LocationManager) getContext()
				.getSystemService(Context.LOCATION_SERVICE);
		for (String provider : locationManager.getAllProviders()) {
			Location currentLocation = locationManager
					.getLastKnownLocation(provider);
			if (currentLocation != null) {
				return currentLocation;
			}
		}
		return null;
	}

	protected String buildPlacesQuery(String searchPhrase, String searchType,
			String inputType, String distanceAttrib, String type) {
		Location currentLocation = getCurrentLocation();
		StringBuilder builder = new StringBuilder();
		builder.append("https://maps.googleapis.com/maps/api/place/");
		builder.append(searchType + "/json?");
		builder.append(inputType + "=" + searchPhrase);
		if (type != null) {
			try {
				builder.append("&types=" + URLEncoder.encode(type, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		builder.append("&" + distanceAttrib);
		if (currentLocation != null) {
			builder.append("&location=" + currentLocation.getLatitude() + ","
					+ currentLocation.getLongitude());
		}
		builder.append("&sensor=false&key="
				+ getKeyForId(R.string.GoogleAPIKey));
		return builder.toString();
	}
	
	protected String buildPlacesPhotoQuery(String photoReference) {
		StringBuilder builder = new StringBuilder(
				"https://maps.googleapis.com/maps/api/place/photo?");
		builder.append("maxwidth=400&photoreference=" + photoReference);
		builder.append("&sensor=true&key=" + getKeyForId(R.string.GoogleAPIKey));
		return builder.toString();
	}

	protected OAuthRequest buildYelpQuery(String searchPhrase) {
		Location currentLocation = getCurrentLocation();
		OAuthRequest request = new OAuthRequest(Verb.GET,
				"http://api.yelp.com/v2/search");
		request.addQuerystringParameter("term", searchPhrase);
		if (currentLocation != null) {
			request.addQuerystringParameter("ll", currentLocation.getLatitude()
					+ "," + currentLocation.getLongitude());
		}
		Token accessToken = new Token(getKeyForId(R.string.YelpToken),
				getKeyForId(R.string.YelpSecretToken));
		OAuthService service = new ServiceBuilder().provider(YelpApi2.class)
				.apiKey(getKeyForId(R.string.YelpConsumerKey))
				.apiSecret(getKeyForId(R.string.YelpConsumerSecretKey)).build();
		service.signRequest(accessToken, request);
		return request;
	}

	protected String buildFeedzillaQuery(String searchPhrase) {
		return "http://api.feedzilla.com/v1/articles/search.json?q="
				+ searchPhrase;
	}

	protected String buildRottenTomatoesQuery(String searchPhrase) {
		StringBuilder builder = new StringBuilder(
				"http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=");
		builder.append(getKeyForId(R.string.RottenTomatoesAPIKey));
		builder.append("&q=" + searchPhrase + "&page_limit=1");
		return builder.toString();
	}
	
	protected String buildYouTubeQuery(String searchPhrase) {
		StringBuilder builder = new StringBuilder(
				"https://www.googleapis.com/youtube/v3");
		builder.append("/search?part=snippet&q=" + searchPhrase);
		builder.append("&type=video&key=" + getKeyForId(R.string.GoogleAPIKey));
		return builder.toString();
	}
	
	protected String buildStocksTickerQuery(String searchPhrase) {
		try {
			StringBuilder builder = new StringBuilder(
					"http://d.yimg.com/autoc.finance.yahoo.com/autoc?query=");
			builder.append(URLEncoder.encode(searchPhrase, "UTF-8"));
			builder.append("&callback=YAHOO.Finance.SymbolSuggest.ssCallback");
			return builder.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected String buildStocksQuoteQuery(String searchPhrase) {
		try {
			searchPhrase = searchPhrase.substring(searchPhrase.indexOf("(") + 1,
					searchPhrase.length() - 2);
			JSONObject obj = new JSONObject(searchPhrase).getJSONObject("ResultSet")
					.getJSONArray("Result").getJSONObject(0);
			String tickerName = obj.getString("symbol");
			StringBuilder builder = new StringBuilder(
					"http://query.yahooapis.com/v1/public/yql?q=");
			builder.append(URLEncoder.encode("select " + "* from yahoo.finance"
					+ ".quote where symbol in ('" + tickerName + "')", "UTF-8"));
			builder.append("&format=json&env=store://datatables.org/alltableswithkeys");
			return builder.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			String explanation = "No stocks ticker symbol found for query";
			getErrorLogger().log(explanation, e.toString());
		}
		return null;
	}
	
	protected String buildSpotifyTrackQuery(String searchPhrase) {
		return "http://ws.spotify.com/search/1/track.json?q=" + searchPhrase;
	}
	
	protected String buildSpotifyAlbumQuery(String searchPhrase) {
		return "http://ws.spotify.com/search/1/album.json?q=" + searchPhrase;
	}
	
	protected String buildSpotifyArtistQuery(String searchPhrase) {
		return "http://ws.spotify.com/search/1/artist.json?q=" + searchPhrase;
	}

}
