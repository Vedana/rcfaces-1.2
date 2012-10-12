/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.agent;

/** 
 */
public interface IUserAgent {

    public enum BrowserType {
        UNKNOWN("unknown"), MICROSOFT_INTERNET_EXPLORER("ie"), FIREFOX("fx"), SAFARI(
                "safari"), OPERA("opera"), CHROME("chrome"), IOS("ios"), ANDROID(
                "android");

        private final String shortName;

        BrowserType(String shortName) {
            this.shortName = shortName;
        }

        public String shortName() {
            return this.shortName;
        }
    }

    BrowserType getBrowserType();

    Integer getMajorVersion();

    Integer getMinorVersion();

    Integer getReleaseVersion();

    void textForm(StringBuilder sb);

}
