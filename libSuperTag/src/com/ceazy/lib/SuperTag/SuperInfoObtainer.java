package com.ceazy.lib.SuperTag;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

/**<b>class SuperInfoObtainer</b>
 *<p>Provides methods to access information about supported functions and hashtags.
 *This includes user-readable terms to describe each function.</p>*/
public class SuperInfoObtainer {
	
	Context context;
	
	/**<b><i>{@link SuperInfoObtainer} class constuctor</i></b>
	 * @param context
	 */
	public SuperInfoObtainer(Context context) {
		this.context = context;
	}
	
	private Context getContext() {
		return context;
	}
	
	/**<b><i>public <code>Map<String, List<String>></code> getFunctionsAndHashTagsDictionary()</i></b>
	 * <p>Creates a dictionary that maps functions to all of their associated hashtags</p>
	 * <p>For reference, see hashtags.xml in res/values for a list of all hashtags and their
	 * corresponding functions.</p>
	 * @return A dictionary mapping supported functions to their associated hashtags
	 */
	public Map<String, List<String>> getFunctionsAndHashTagsDictionary() {
		Map<String, List<String>> functionsAndHashTags = new LinkedHashMap<String, List<String>>();
		DataParser parser = new DataParser(getContext());
		List<String> functionsList = parser.getFunctions();
		for(String function : functionsList) {
			functionsAndHashTags.put(function, parser.getAllHashTagsForFunction(function));
		}
		return functionsAndHashTags;
	}
	
	/**<b><i>public <code>String</code> getReadableFunction(<code>String</code> function, <code>int</code> index)</i></b>
	 * <p>Retrieves a user-readable term describing a specific function from an array of such terms at a specific index.
	 * See readable_functions.xml in res/values for a list of readable term arrays.
	 * @param function The function for which a user-readable term should be retrieved
	 * @param index The index from the term array to retrieve
	 * @return A string describing a function
	 */
	public String getReadableFunction(String function, int index) {
		return getReadableFunctionsArray(function)[index];
	}
	
	/**<b><i>public <code>String[]</code> getReadableFunctionsArray(<code>String</code> function)</i></b>
	 * <p>Retrieves an array of user-readable terms describing a specific function. See readable_functions.xml
	 * in res/values for a list of readable term arrays.
	 * @param function
	 * @return A string array of user-readable terms describing a function
	 */
	public String[] getReadableFunctionsArray(String function) {
		String strIdentifier = "readable_" + function;
		int id = getContext().getResources().
				getIdentifier(strIdentifier, "array", getContext().getPackageName());
		return getContext().getResources().getStringArray(id);
	}
	
	/**<b><i>public <code>Map<String, String></code> createReadableFunctionsDictionary(<code>Integer[]</code> indices)</i></b>
	 * <p>Creates a dictionary that maps functions to user-readable terms that describe them based on a provided
	 * array of indices (one index for each array of readable terms).</p>
	 * <p>Please refer to readable_functions.xml
	 * in /res/values for a list of user-readable term arrays. The indices you provide should correspond with these arrays
	 * from the top of the file downwards</p>
	 * @param indices An integer of indices, each one corresponding to a term in an array of user-readable terms for a function
	 * @return A dictionary mapping functions to user-readable terms
	 */
	public Map<String, String> createReadableFunctionsDictionary(Integer[] indices) {
		Map<String, String> readableDict = new HashMap<String, String>();
		int indicesLength = indices.length;
		DataParser parser = new DataParser(getContext());
		List<String> functionsList = parser.getFunctions();
		for(int i = 0; i < functionsList.size(); i++) {
			String function = functionsList.get(i);
			if(i < indicesLength) {
				readableDict.put(function, 
						getReadableFunctionsArray(function)[indices[i]]);
			} else {
				readableDict.put(function, 
						getReadableFunctionsArray(function)[0]);
			}
		}
		return readableDict;
	}
}
