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
            jdkVersion = Integer.parseInt(version.substring(2));
            return jdkVersion;
        }

        int dot = version.indexOf('.');
        if (dot < 0) {
            jdkVersion = Integer.parseInt(version.substring(0, dot));
            return jdkVersion;
        }

        jdkVersion = Integer.parseInt(version);
        return jdkVersion;
    }
}
