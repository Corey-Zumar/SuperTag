package com.ceazy.lib.SuperTag;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.android.youtube.player.YouTubeIntents;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class SuperIntentCreator {
	
	Context context;
	
	public SuperIntentCreator(Context context) {
		this.context = context;
	}
	
	private Context getContext() {
		return context;
	}
	
	public SuperTag updateTagWithIntents(SuperTag tag) {
		List<Intent> intentsList = getIntentsForFunction(tag.getFunction(), 
				tag.getHashPhrase());
		SuperIntent superIntent = new SuperIntent(intentsList);
		tag.setIntent(superIntent);
		return tag;
	}
	
	public SuperIntent createGoogleSearchIntent(String hashPhrase) {
		return new SuperIntent(getIntentsForFunction("googleSearch", hashPhrase));
	}
	
	public SuperIntent createTwitterSearchIntent(String hashPhrase) {
		return new SuperIntent(getIntentsForFunction("twitterSearch", hashPhrase));
	}
	
	public SuperIntent createLocationIntent(String hashPhrase) {
		return new SuperIntent(getIntentsForFunction("location", hashPhrase));
	}
	
	public SuperIntent createFoodIntent(String hashPhrase) {
		return new SuperIntent(getIntentsForFunction("food", hashPhrase));
	}
	
	public SuperIntent createVideoIntent(String hashPhrase) {
		return new SuperIntent(getIntentsForFunction("videoMedia", hashPhrase));
	}
	
	public SuperIntent createMusicIntent(String hashPhrase) {
		return new SuperIntent(getIntentsForFunction("musicMedia", hashPhrase));
	}
	
	public SuperIntent createNewsIntent(String hashPhrase) {
		return new SuperIntent(getIntentsForFunction("newsMedia", hashPhrase));
	}
	
	public SuperIntent createShoppingIntent(String hashPhrase) {
		return new SuperIntent(getIntentsForFunction("shopping", hashPhrase));
	}
	
	public SuperIntent createStocksIntent(String hashPhrase) {
		return new SuperIntent(getIntentsForFunction("stocks", hashPhrase));
	}
	
	public SuperIntent createApplicationSearchIntent(String hashPhrase) {
		return new SuperIntent(getIntentsForFunction("application", hashPhrase));
	}
	
	protected List<Intent> getIntentsForFunction(String function, String hashPhrase) {
		if(function.equals("googleSearch")) {
			return createGoogleSearchIntents(hashPhrase);
		} else if(function.equals("twitterSearch")) {
			return createTwitterSearchIntents(hashPhrase);
		} else if(function.equals("location")) {
			return createLocationIntents(hashPhrase);
		} else if(function.equals("food")) {
			return createFoodIntents(hashPhrase);
		} else if(function.equals("videoMedia")) {
			return createVideoIntents(hashPhrase);
		} else if(function.equals("musicMedia")) {
			return createMusicIntents(hashPhrase);
		} else if(function.equals("movieMedia")) {
			return createMovieIntents(hashPhrase);
		} else if(function.equals("newsMedia")) {
			return createNewsIntents(hashPhrase);
		} else if(function.equals("shopping")) {
			return createShoppingIntents(hashPhrase);
		} else if(function.equals("stocks")) {
			return createStocksIntents(hashPhrase);
		} else if(function.equals("application")) {
			return createApplicationIntents(hashPhrase);
		}
		return new ArrayList<Intent>();
	}
	
	protected List<Intent> createGoogleSearchIntents(String hashPhrase) {
		return createIntentsFromUris("googleSearch", hashPhrase);
	}
	
	protected List<Intent> createTwitterSearchIntents(String hashPhrase) {
		return createIntentsFromUris("twitterSearch", hashPhrase);
	}
	
	protected List<Intent> createLocationIntents(String hashPhrase) {
		return createIntentsFromUris("location", hashPhrase);
	}
	
	protected List<Intent> createFoodIntents(String hashPhrase) {
		return createIntentsFromUris("food", hashPhrase);
	}
	
	protected List<Intent> createVideoIntents(String hashPhrase) {
		List<Intent> intentsList = createIntentsFromUris("videoMedia", hashPhrase);
		intentsList.add(YouTubeIntents.createSearchIntent(getContext(), hashPhrase));
		return intentsList;
	}
	
	protected List<Intent> createMusicIntents(String hashPhrase) {
		List<Intent> intentsList =  createIntentsFromUris("musicMedia", hashPhrase);
		intentsList.add(YouTubeIntents.createSearchIntent(getContext(), hashPhrase));
		Intent iGooglePlayMusic = createIntent(Intent.ACTION_SEARCH, "com.google.android.music", 
				null, 0, hashPhrase);
		intentsList.add(iGooglePlayMusic);
		return intentsList;
	}
	
	protected List<Intent> createApplicationIntents(String hashPhrase) {
		return createIntentsFromUris("application", hashPhrase);
	}
	
	protected List<Intent> createMovieIntents(String hashPhrase) {
		List<Intent> intentsList = createIntentsFromUris("movieMedia", hashPhrase);
		Intent iFandango = createIntent(Intent.ACTION_SEARCH, "com.fandango",
				"com.fandango.activities.SearchListActivity", 0, hashPhrase);
		intentsList.add(iFandango);
		return intentsList;
	}
	
	protected List<Intent> createNewsIntents(String hashPhrase) {
		List<Intent> intentsList =  createIntentsFromUris("newsMedia", hashPhrase);
		Intent iHuffingtonPost = createIntent(Intent.ACTION_SEARCH, "com.huffingtonpost.android",
				"com.huffingtonpost.android.section.SectionActivity", 0, hashPhrase);
		intentsList.add(iHuffingtonPost);
		Intent iNPR = createIntent(Intent.ACTION_SEARCH, "org.npr.android.news", 
				"org.npr.android.news.SearchResultsActivity", 0, hashPhrase);
		intentsList.add(iNPR);
		return intentsList;
	}
	
	protected List<Intent> createStocksIntents(String hashPhrase) {
		return createIntentsFromUris("stocks", hashPhrase);
	}
	
	protected List<Intent> createShoppingIntents(String hashPhrase) {
		List<Intent> intentsList = createIntentsFromUris("shopping", hashPhrase);
		Intent iAmazonMobile = createIntent(Intent.ACTION_SEARCH,
				"com.amazon.mShop.android", "com.amazon.mShop.search.SearchActivity",
				0, hashPhrase);
		intentsList.add(iAmazonMobile);
		Intent iEbay = createIntent(Intent.ACTION_SEARCH, "com.ebay.mobile",
				"com.ebay.mobile.activities.QuickSearchHandler", 0, hashPhrase);
		intentsList.add(iEbay);
		return intentsList;
	}
	
	protected Intent createIntent(String action, String pkgName, String className,
			int flags, String hashPhrase) {
		Intent intent = new Intent(action);
		intent.setPackage(pkgName);
		if(className != null) {
			intent.setClassName(pkgName, className);
		}
		intent.putExtra(SearchManager.QUERY, hashPhrase);
		intent.setFlags(flags);
		return intent;
	}
	
	protected List<Intent> createIntentsFromUris(String function, String hashPhrase) {
		List<Intent> intentsList = new ArrayList<Intent>();
		int id = getContext().getResources()
				.getIdentifier(function, "array", getContext().getPackageName());
		for(String uriInfo : getContext().getResources().getStringArray(id)) {
			try {
				String uriString;
				String type = null;
				String include = null;
				if(uriInfo.contains(",")) {
					int commaIndex = uriInfo.indexOf(",");
					uriString = uriInfo.substring(0, commaIndex);
					String typeInfo = uriInfo.substring(commaIndex + 1);
					if(typeInfo.contains(",")) {
						int commaIndex2 = typeInfo.indexOf(",");
						type = typeInfo.substring(0, commaIndex2);
						include = typeInfo.substring(commaIndex2 + 1);
					} else {
						type = typeInfo;
					}
				} else {
					uriString = uriInfo;
				}
				Intent intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse(uriString + URLEncoder.encode(hashPhrase, "UTF-8")));
				if(type != null) {
					intent.putExtra("type", type);
					if(include != null) {
						intent.putExtra("include", include);
					}
				}
				intentsList.add(intent);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return intentsList;
	}

}
