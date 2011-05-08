package me.sabaku.web.controller.impl;

/**
 * Utility class for formatting json strings to a jsonp response
 */
public class JsonpFormatter {
	private static final String JSONP_FORMAT = "%s(%s)";
	
	/**
	 * Given the input json, return a jsonp formatted response
	 * @param json
	 * @return
	 */
	public static String format(String json, String callback) {
		return String.format(JSONP_FORMAT, callback, json);
	}
}
