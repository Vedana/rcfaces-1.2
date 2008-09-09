package org.rcfaces.renderkit.html.component;

import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.component.PropertiesRepository;

public class Properties {
	public static final String BASE = "base";
	public static final String BASE_NAME = "baseName";
	public static final String BUNDLE_NAME = "bundleName";
	public static final String CLIENT_MESSAGE_ID_FILTER = "clientMessageIdFilter";
	public static final String CLIENT_VALIDATION = "clientValidation";
	public static final String DISABLED_COOKIES_PAGE_URL = "disabledCookiesPageURL";
	public static final String DISABLED_IEIMAGE_BAR = "disabledIEImageBar";
	public static final String DISABLED_SCRIPT_PAGE_URL = "disabledScriptPageURL";
	public static final String DISABLE_CACHE = "disableCache";
	public static final String DISABLE_CONTEXT_MENU = "disableContextMenu";
	public static final String FAVORITE_IMAGE_URL = "favoriteImageURL";
	public static final String INVALID_BROWSER_PAGE_URL = "invalidBrowserPageURL";
	public static final String LITERAL_LOCALE = "literalLocale";
	public static final String LITERAL_TIME_ZONE = "literalTimeZone";
	public static final String MERGE_SCRIPTS = "mergeScripts";
	public static final String OVERRIDE = "override";
	public static final String PREFIX = "prefix";
	public static final String RENDER_BASE_TAG = "renderBaseTag";
	public static final String REQUIRED_CLASSES = "requiredClasses";
	public static final String REQUIRED_FILES = "requiredFiles";
	public static final String REQUIRED_MODULES = "requiredModules";
	public static final String REQUIRED_SETS = "requiredSets";
	public static final String SERVER_SCOPE = "serverScope";
	public static final String SIDE = "side";
	public static final String SRC = "src";
	public static final String SRC_CHAR_SET = "srcCharSet";
	public static final String TEXT = "text";
	public static final String TITLE = "title";
	public static final String URI = "uri";
	public static final String WAI_ROLES_NS = "waiRolesNS";
	static {
		if (Constants.COMPACTED_PROPERTY_NAME) {
			PropertiesRepository.declareProperties(new String[] {BASE,BASE_NAME,BUNDLE_NAME,CLIENT_MESSAGE_ID_FILTER,CLIENT_VALIDATION,DISABLED_COOKIES_PAGE_URL,DISABLED_IEIMAGE_BAR,DISABLED_SCRIPT_PAGE_URL,DISABLE_CACHE,DISABLE_CONTEXT_MENU,FAVORITE_IMAGE_URL,INVALID_BROWSER_PAGE_URL,LITERAL_LOCALE,LITERAL_TIME_ZONE,MERGE_SCRIPTS,OVERRIDE,PREFIX,RENDER_BASE_TAG,REQUIRED_CLASSES,REQUIRED_FILES,REQUIRED_MODULES,REQUIRED_SETS,SERVER_SCOPE,SIDE,SRC,SRC_CHAR_SET,TEXT,TITLE,URI,WAI_ROLES_NS});
		}
	}
}
