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

/**<b>class SuperIntentCreator</b>
 *<p>Creates {@link SuperIntent SuperIntents} for supported functions based on a 
 *supplied {@link SuperTag#hashPhrase hashphrase}
 */
public class SuperIntentCreator {
	
	Context context;
	
	/**<b>SuperIntentCreator class constructor</b>
	 * @param context
	 */
	public SuperIntentCreator(Context context) {
		this.context = context;
	}
	
	private Context getContext() {
		return context;
	}
	
	/**<b><i>public {@link SuperTag} updateTagWithIntent({@link SuperTag} tag, <code>Integer[]</code> flagsArray)
	 * </i></b>
	 * <p>Updates a supplied SuperTag object with a {@link SuperIntent} created
	 * based on the SuperTag's {@link SuperTag#function function} and {@link
	 * SuperTag#hashPhrase hashphrase}
	 * @param tag The SuperTag to be updated with a SuperIntent
	 * @param flagsArray Flags to be added to each {@link Intent} within the SuperIntent 
	 * (Intent.FLAG_ACTIVITY_NEW_TASK, etc)
	 * @return The updated SuperTag with SuperIntent
	 */
	public SuperTag updateTagWithIntent(SuperTag tag, Integer[] flagsArray) {
		List<Intent> intentsList = getIntentsForFunction(tag.getFunction(), 
				tag.getHashPhrase(), flagsArray);
		SuperIntent superIntent = new SuperIntent(intentsList);
		tag.setIntent(superIntent);
		return tag;
	}
	
	/**<b><i>public {@link SuperIntent} createGenericSearchIntent(<code>String</code>
	 * hashPhrase, <code>Integer[]</code> flagsArray)</i></b>
	 * <p>Creates a SuperIntent based on the "genericSearch" {@link SuperTag#function
	 * function} and a supplied {@link SuperTag#hashPhrase hashphrase}
	 * @param hashPhrase The hashphrase with which the SuperIntent should be created
	 * @param flagsArray Flags to be added to each {@link Intent} within the SuperIntent 
	 * (Intent.FLAG_ACTIVITY_NEW_TASK, etc)
	 * @return A {@link SuperIntent} object
	 */
	public SuperIntent createGenericSearchIntent(String hashPhrase, Integer[] flagsArray) {
		return new SuperIntent(getIntentsForFunction("genericSearch", hashPhrase, flagsArray));
	}
	
	/**<b><i>public {@link SuperIntent} createGoogleSearchIntent(<code>String</code>
	 * hashPhrase, <code>Integer[]</code> flagsArray)</i></b>
	 * <p>Creates a SuperIntent based on the "googleSearch" {@link SuperTag#function
	 * function} and a supplied {@link SuperTag#hashPhrase hashphrase}
	 * @param hashPhrase The hashphrase with which the SuperIntent should be created
	 * @param flagsArray Flags to be added to each {@link Intent} within the SuperIntent 
	 * (Intent.FLAG_ACTIVITY_NEW_TASK, etc)
	 * @return A {@link SuperIntent} object
	 */
	public SuperIntent createGoogleSearchIntent(String hashPhrase, Integer[] flagsArray) {
		return new SuperIntent(getIntentsForFunction("googleSearch", hashPhrase, flagsArray));
	}
	
	/**<b><i>public {@link SuperIntent} createTwitterSearchIntent(<code>String</code>
	 * hashPhrase, <code>Integer[]</code> flagsArray)</i></b>
	 * <p>Creates a SuperIntent based on the "twitterSearch" {@link SuperTag#function
	 * function} and a supplied {@link SuperTag#hashPhrase hashphrase}
	 * @param hashPhrase The hashphrase with which the SuperIntent should be created
	 * @param flagsArray Flags to be added to each {@link Intent} within the SuperIntent 
	 * (Intent.FLAG_ACTIVITY_NEW_TASK, etc)
	 * @return A {@link SuperIntent} object
	 */
	public SuperIntent createTwitterSearchIntent(String hashPhrase, Integer[] flagsArray) {
		return new SuperIntent(getIntentsForFunction("twitterSearch", hashPhrase, flagsArray));
	}
	
	/**<b><i>public {@link SuperIntent} createLocationIntent(<code>String</code>
	 * hashPhrase, <code>Integer[]</code> flagsArray)</i></b>
	 * <p>Creates a SuperIntent based on the "location" {@link SuperTag#function
	 * function} and a supplied {@link SuperTag#hashPhrase hashphrase}
	 * @param hashPhrase The hashphrase with which the SuperIntent should be created
	 * @param flagsArray Flags to be added to each {@link Intent} within the SuperIntent 
	 * (Intent.FLAG_ACTIVITY_NEW_TASK, etc)
	 * @return A {@link SuperIntent} object
	 */
	public SuperIntent createLocationIntent(String hashPhrase, Integer[] flagsArray) {
		return new SuperIntent(getIntentsForFunction("location", hashPhrase, flagsArray));
	}
	
	/**<b><i>public {@link SuperIntent} createDiningIntent(<code>String</code>
	 * hashPhrase, <code>Integer[]</code> flagsArray)</i></b>
	 * <p>Creates a SuperIntent based on the "dining" {@link SuperTag#function
	 * function} and a supplied {@link SuperTag#hashPhrase hashphrase}
	 * @param hashPhrase The hashphrase with which the SuperIntent should be created
	 * @param flagsArray Flags to be added to each {@link Intent} within the SuperIntent 
	 * (Intent.FLAG_ACTIVITY_NEW_TASK, etc)
	 * @return A {@link SuperIntent} object
	 */
	public SuperIntent createDiningIntent(String hashPhrase, Integer[] flagsArray) {
		return new SuperIntent(getIntentsForFunction("dining", hashPhrase, flagsArray));
	}
	
	/**<b><i>public {@link SuperIntent} createVideoIntent(<code>String</code>
	 * hashPhrase, <code>Integer[]</code> flagsArray)</i></b>
	 * <p>Creates a SuperIntent based on the "video" {@link SuperTag#function
	 * function} and a supplied {@link SuperTag#hashPhrase hashphrase}
	 * @param hashPhrase The hashphrase with which the SuperIntent should be created
	 * @param flagsArray Flags to be added to each {@link Intent} within the SuperIntent 
	 * (Intent.FLAG_ACTIVITY_NEW_TASK, etc)
	 * @return A {@link SuperIntent} object
	 */
	public SuperIntent createVideoIntent(String hashPhrase, Integer[] flagsArray) {
		return new SuperIntent(getIntentsForFunction("videoMedia", hashPhrase, flagsArray));
	}
	
	/**<b><i>public {@link SuperIntent} createMusicIntent(<code>String</code>
	 * hashPhrase, <code>Integer[]</code> flagsArray)</i></b>
	 * <p>Creates a SuperIntent based on the "music" {@link SuperTag#function
	 * function} and a supplied {@link SuperTag#hashPhrase hashphrase}
	 * @param hashPhrase The hashphrase with which the SuperIntent should be created
	 * @param flagsArray Flags to be added to each {@link Intent} within the SuperIntent 
	 * (Intent.FLAG_ACTIVITY_NEW_TASK, etc)
	 * @return A {@link SuperIntent} object
	 */
	public SuperIntent createMusicIntent(String hashPhrase, Integer[] flagsArray) {
		return new SuperIntent(getIntentsForFunction("musicMedia", hashPhrase, flagsArray));
	}
	
	/**<b><i>public {@link SuperIntent} createNewsIntent(<code>String</code>
	 * hashPhrase, <code>Integer[]</code> flagsArray)</i></b>
	 * <p>Creates a SuperIntent based on the "news" {@link SuperTag#function
	 * function} and a supplied {@link SuperTag#hashPhrase hashphrase}
	 * @param hashPhrase The hashphrase with which the SuperIntent should be created
	 * @param flagsArray Flags to be added to each {@link Intent} within the SuperIntent 
	 * (Intent.FLAG_ACTIVITY_NEW_TASK, etc)
	 * @return A {@link SuperIntent} object
	 */
	public SuperIntent createNewsIntent(String hashPhrase, Integer[] flagsArray) {
		return new SuperIntent(getIntentsForFunction("newsMedia", hashPhrase, flagsArray));
	}
	
	/**<b><i>public {@link SuperIntent} createShoppingIntent(<code>String</code>
	 * hashPhrase, <code>Integer[]</code> flagsArray)</i></b>
	 * <p>Creates a SuperIntent based on the "shopping" {@link SuperTag#function
	 * function} and a supplied {@link SuperTag#hashPhrase hashphrase}
	 * @param hashPhrase The hashphrase with which the SuperIntent should be created
	 * @param flagsArray Flags to be added to each {@link Intent} within the SuperIntent 
	 * (Intent.FLAG_ACTIVITY_NEW_TASK, etc)
	 * @return A {@link SuperIntent} object
	 */
	public SuperIntent createShoppingIntent(String hashPhrase, Integer[] flagsArray) {
		return new SuperIntent(getIntentsForFunction("shopping", hashPhrase, flagsArray));
	}
	
	/**<b><i>public {@link SuperIntent} createStocksIntent(<code>String</code>
	 * hashPhrase, <code>Integer[]</code> flagsArray)</i></b>
	 * <p>Creates a SuperIntent based on the "stocks" {@link SuperTag#function
	 * function} and a supplied {@link SuperTag#hashPhrase hashphrase}
	 * @param hashPhrase The hashphrase with which the SuperIntent should be created
	 * @param flagsArray Flags to be added to each {@link Intent} within the SuperIntent 
	 * (Intent.FLAG_ACTIVITY_NEW_TASK, etc)
	 * @return A {@link SuperIntent} object
	 */
	public SuperIntent createStocksIntent(String hashPhrase, Integer[] flagsArray) {
		return new SuperIntent(getIntentsForFunction("stocks", hashPhrase, flagsArray));
	}
	
	/**<b><i>public {@link SuperIntent} createApplicationSearchIntent(<code>String</code>
	 * hashPhrase, <code>Integer[]</code> flagsArray)</i></b>
	 * <p>Creates a SuperIntent based on the "application" {@link SuperTag#function
	 * function} and a supplied {@link SuperTag#hashPhrase hashphrase}
	 * @param hashPhrase The hashphrase with which the SuperIntent should be created
	 * @param flagsArray Flags to be added to each {@link Intent} within the SuperIntent 
	 * (Intent.FLAG_ACTIVITY_NEW_TASK, etc)
	 * @return A {@link SuperIntent} object
	 */
	public SuperIntent createApplicationSearchIntent(String hashPhrase, Integer[] flagsArray) {
		return new SuperIntent(getIntentsForFunction("application", hashPhrase, flagsArray));
	}
	
	protected List<Intent> getIntentsForFunction(String function, String hashPhrase, Integer[] flagsArray) {
		if(function.equals("genericSearch")) {
			return createGenericSearchIntents(hashPhrase, flagsArray);
		} else if(function.equals("googleSearch")) {
			return createGoogleSearchIntents(hashPhrase, flagsArray);
		} else if(function.equals("twitterSearch")) {
			return createTwitterSearchIntents(hashPhrase, flagsArray);
		} else if(function.equals("location")) {
			return createLocationIntents(hashPhrase, flagsArray);
		} else if(function.equals("dining")) {
			return createDiningIntents(hashPhrase, flagsArray);
		} else if(function.equals("videoMedia")) {
			return createVideoIntents(hashPhrase, flagsArray);
		} else if(function.equals("musicMedia")) {
			return createMusicIntents(hashPhrase, flagsArray);
		} else if(function.equals("movieMedia")) {
			return createMovieIntents(hashPhrase, flagsArray);
		} else if(function.equals("newsMedia")) {
			return createNewsIntents(hashPhrase, flagsArray);
		} else if(function.equals("shopping")) {
			return createShoppingIntents(hashPhrase, flagsArray);
		} else if(function.equals("stocks")) {
			return createStocksIntents(hashPhrase, flagsArray);
		} else if(function.equals("application")) {
			return createApplicationIntents(hashPhrase, flagsArray);
		}
		return new ArrayList<Intent>();
	}
	
	protected List<Intent> createGenericSearchIntents(String hashPhrase, Integer[] flagsArray) {
		List<Intent> intentsList = new ArrayList<Intent>(
				createGoogleSearchIntents(hashPhrase, flagsArray));
		intentsList.addAll(createTwitterSearchIntents(hashPhrase, flagsArray));
		return intentsList;
	}
	
	protected List<Intent> createGoogleSearchIntents(String hashPhrase, Integer[] flagsArray) {
		return createIntentsFromUris("googleSearch", hashPhrase, flagsArray);
	}
	
	protected List<Intent> createTwitterSearchIntents(String hashPhrase, Integer[] flagsArray) {
		if(!hashPhrase.contains(" ")) {
			hashPhrase = "#" + hashPhrase;
		}
		return createIntentsFromUris("twitterSearch", hashPhrase, flagsArray);
	}
	
	protected List<Intent> createLocationIntents(String hashPhrase, Integer[] flagsArray) {
		return createIntentsFromUris("location", hashPhrase, flagsArray);
	}
	
	protected List<Intent> createDiningIntents(String hashPhrase, Integer[] flagsArray) {
		return createIntentsFromUris("dining", hashPhrase, flagsArray);
	}
	
	protected List<Intent> createVideoIntents(String hashPhrase, Integer[] flagsArray) {
		List<Intent> intentsList = createIntentsFromUris("videoMedia", hashPhrase, flagsArray);
		intentsList.add(YouTubeIntents.createSearchIntent(getContext(), hashPhrase));
		return intentsList;
	}
	
	protected List<Intent> createMusicIntents(String hashPhrase, Integer[] flagsArray) {
		List<Intent> intentsList =  createIntentsFromUris("musicMedia", hashPhrase, flagsArray);
		intentsList.add(YouTubeIntents.createSearchIntent(getContext(), hashPhrase));
		Intent iGooglePlayMusic = createIntent(Intent.ACTION_SEARCH, "com.google.android.music", 
				null, hashPhrase, flagsArray);
		intentsList.add(iGooglePlayMusic);
		return intentsList;
	}
	
	protected List<Intent> createApplicationIntents(String hashPhrase, Integer[] flagsArray) {
		return createIntentsFromUris("application", hashPhrase, flagsArray);
	}
	
	protected List<Intent> createMovieIntents(String hashPhrase, Integer[] flagsArray) {
		List<Intent> intentsList = createIntentsFromUris("movieMedia", hashPhrase, flagsArray);
		Intent iFandango = createIntent(Intent.ACTION_SEARCH, "com.fandango",
				"com.fandango.activities.SearchListActivity", hashPhrase, flagsArray);
		intentsList.add(iFandango);
		return intentsList;
	}
	
	protected List<Intent> createNewsIntents(String hashPhrase, Integer[] flagsArray) {
		List<Intent> intentsList =  createIntentsFromUris("newsMedia", hashPhrase, flagsArray);
		Intent iHuffingtonPost = createIntent(Intent.ACTION_SEARCH, "com.huffingtonpost.android",
				"com.huffingtonpost.android.section.SectionActivity", hashPhrase, flagsArray);
		intentsList.add(iHuffingtonPost);
		Intent iNPR = createIntent(Intent.ACTION_SEARCH, "org.npr.android.news", 
				"org.npr.android.news.SearchResultsActivity", hashPhrase, flagsArray);
		intentsList.add(iNPR);
		return intentsList;
	}
	
	protected List<Intent> createStocksIntents(String hashPhrase, Integer[] flagsArray) {
		return createIntentsFromUris("stocks", hashPhrase, flagsArray);
	}
	
	protected List<Intent> createShoppingIntents(String hashPhrase, Integer[] flagsArray) {
		List<Intent> intentsList = createIntentsFromUris("shopping", hashPhrase, flagsArray);
		Intent iAmazonMobile = createIntent(Intent.ACTION_SEARCH,
				"com.amazon.mShop.android", "com.amazon.mShop.search.SearchActivity", hashPhrase, flagsArray);
		intentsList.add(iAmazonMobile);
		Intent iEbay = createIntent(Intent.ACTION_SEARCH, "com.ebay.mobile",
				"com.ebay.mobile.activities.QuickSearchHandler", hashPhrase, flagsArray);
		intentsList.add(iEbay);
		return intentsList;
	}
	
	protected Intent createIntent(String action, String pkgName, String className, 
			String hashPhrase, Integer[] flagsArray) {
		Intent intent = new Intent(action);
		intent.setPackage(pkgName);
		if(className != null) {
			intent.setClassName(pkgName, className);
		}
		intent.putExtra(SearchManager.QUERY, hashPhrase);
		addFlagsFromArray(intent, flagsArray);
		return intent;
	}
	
	protected List<Intent> createIntentsFromUris(String function, String hashPhrase, Integer[] flagsArray) {
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
				addFlagsFromArray(intent, flagsArray);
				intentsList.add(intent);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return intentsList;
	}
	
	private void addFlagsFromArray(Intent intent, Integer[] flagsArray) {
		for(int flag : flagsArray) {
			intent.addFlags(flag);
		}
	}

}
