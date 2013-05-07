package com.stealthyone.bukkit.simplepromoter.utils;

public final class StringUtils {

	public final static boolean containsMultiple(String main, String... comparisons) {
		for (String cur : comparisons) {
			if (main.contains(cur)) {
				return true;
			}
		}
		return false;
	}
	
	public final static boolean equalsIgnoreCaseMultiple(String main, String... comparisons) {
		for (String cur : comparisons) {
			if (cur.equalsIgnoreCase(main)) {
				return true;
			}
		}
		return false;
	}
	
	public final static boolean startsWithVowel(String input) {
		char c = input.charAt(0);
		if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
			return true;
		}
		return false;
	}
}