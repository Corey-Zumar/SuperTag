package com.ceazy.lib.SuperTag;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.ceazy.lib.SuperTag.Error.SuperError;
import com.ceazy.lib.SuperTag.Error.SuperErrorList;

class JSONErrorLogger {

	Map<String, String> errorMap;

	protected JSONErrorLogger() {
		this.errorMap = new HashMap<String, String>();
	}

	protected Map<String, String> getErrorMap() {
		return errorMap;
	}

	protected void log(String explanation, String exceptionString) {
		getErrorMap().put(explanation, exceptionString);
	}

	protected SuperErrorList getSuperErrorList() {
		SuperErrorList errorList = new SuperErrorList();
		for (Entry<String, String> entry : errorMap.entrySet()) {
			SuperError error = new SuperError(entry.getKey(),
					entry.getValue());
			errorList.add(error);
		}
		return errorList;
	}

}
