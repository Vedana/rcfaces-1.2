package org.rcfaces.core.internal.util;

public class URLResourceNormalizer {

	private static int jdkVersion = 0;

	public static String computeResourceURL(String url) {

		if (getJdkVersion() < 8) {
			return url;
		}

		if (url.length() == 0 || url.charAt(0) == '/') {
			return url;
		}

		return '/' + url;
	}

	private static int getJdkVersion() {
		if (jdkVersion > 0) {
			return jdkVersion;
		}
		String version = System.getProperty("java.version");
		if (version.startsWith("1.")) {
			version = version.substring(2);
		}
		// Allow these formats:
		// 1.8.0_72-ea
		// 9-ea
		// 9
		// 9.0.1
		int dotPos = version.indexOf('.');
		int dashPos = version.indexOf('-');
		jdkVersion = Integer.parseInt(version.substring(0, dotPos > -1 ? dotPos : dashPos > -1 ? dashPos : 1));
		return jdkVersion;
	}
}
