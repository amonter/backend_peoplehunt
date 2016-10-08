package com.myownmotivator.utils;

public class PermalinkUtil {

	private static final String CHARS_TO_REPLACE = "[ \\(\\?\\,\\!\\)\\*]";

	public static String prepareForPermalink(String input) {
		return input.replaceAll(CHARS_TO_REPLACE, "-").toLowerCase();
	}
	
}
