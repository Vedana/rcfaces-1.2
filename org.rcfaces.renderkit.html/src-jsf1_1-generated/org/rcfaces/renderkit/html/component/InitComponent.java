package org.rcfaces.renderkit.html.component;

import java.util.TimeZone;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.capability.IPageConfigurator;
import java.lang.String;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.converter.TimeZoneConverter;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import java.util.HashSet;
import org.rcfaces.core.internal.converter.LocaleConverter;
import java.util.Locale;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;

/**
 * <p><b>Mandatory</b>.</p>
 * <p>It holds the included javascript and css files. It is responsible for initializing RCFaces javascript engine.</p>
 * <p>It <b>must</b> be the first tag for a page.</p>
 */
public class InitComponent extends CameliaBaseComponent implements 
	IPageConfigurator {

	public static final String COMPONENT_TYPE="org.rcfaces.html.init";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"clientMessageIdFilter","disabledCookiesPageURL","invalidBrowserPageURL","waiRolesNS","clientValidation","title","literalLocale","favoriteImageURL","disabledScriptPageURL","literalTimeZone","base","disableCache","disabledIEImageBar","renderBaseTag","disableContextMenu"}));
	}

	public InitComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public InitComponent(String componentId) {
		this();
		setId(componentId);
	}

	public String getPageScriptType() {


			return IHtmlRenderContext.JAVASCRIPT_TYPE;
			
	}

	public void setLiteralLocale(String locale) {


		setLiteralLocale((Locale)LocaleConverter.SINGLETON.getAsObject(null, this, locale));
		
	}

	public void setLiteralTimeZone(String timeZone) {


		setLiteralTimeZone((TimeZone)TimeZoneConverter.SINGLETON.getAsObject(null, this, timeZone));
		
	}

	/**
	 * Returns a string value indicating the base HRef. If the key word "context" is used the base HRef is calculated from the context.
	 * @return base HRef
	 */
	public String getBase() {
		return getBase(null);
	}

	/**
	 * Returns a string value indicating the base HRef. If the key word "context" is used the base HRef is calculated from the context.
	 * @return base HRef
	 */
	public String getBase(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BASE, facesContext);
	}

	/**
	 * Sets a string value indicating the base HRef. If the key word "context" is used the base HRef is calculated from the context.
	 * @param base base HRef
	 */
	public void setBase(String base) {
		engine.setProperty(Properties.BASE, base);
	}

	/**
	 * Sets a string value indicating the base HRef. If the key word "context" is used the base HRef is calculated from the context.
	 * @param base base HRef
	 */
	/**
	 * Returns <code>true</code> if the attribute "base" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isBaseSetted() {
		return engine.isPropertySetted(Properties.BASE);
	}

	/**
	 * Returns a string value specifying the title for the document.
	 * @return title
	 */
	public String getTitle() {
		return getTitle(null);
	}

	/**
	 * Returns a string value specifying the title for the document.
	 * @return title
	 */
	public String getTitle(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TITLE, facesContext);
	}

	/**
	 * Sets a string value specifying the title for the document.
	 * @param title title
	 */
	public void setTitle(String title) {
		engine.setProperty(Properties.TITLE, title);
	}

	/**
	 * Sets a string value specifying the title for the document.
	 * @param title title
	 */
	/**
	 * Returns <code>true</code> if the attribute "title" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isTitleSetted() {
		return engine.isPropertySetted(Properties.TITLE);
	}

	/**
	 * Returns an url string pointing to the image used for a bookmark.
	 * @return favorite image url
	 */
	public String getFavoriteImageURL() {
		return getFavoriteImageURL(null);
	}

	/**
	 * Returns an url string pointing to the image used for a bookmark.
	 * @return favorite image url
	 */
	public String getFavoriteImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FAVORITE_IMAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to the image used for a bookmark.
	 * @param favoriteImageURL favorite image url
	 */
	public void setFavoriteImageURL(String favoriteImageURL) {
		engine.setProperty(Properties.FAVORITE_IMAGE_URL, favoriteImageURL);
	}

	/**
	 * Sets an url string pointing to the image used for a bookmark.
	 * @param favoriteImageURL favorite image url
	 */
	/**
	 * Returns <code>true</code> if the attribute "favoriteImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFavoriteImageURLSetted() {
		return engine.isPropertySetted(Properties.FAVORITE_IMAGE_URL);
	}

	/**
	 * Returns an url string pointing to a page shown if scripts are not enabled on the browser.
	 * @return url
	 */
	public String getDisabledScriptPageURL() {
		return getDisabledScriptPageURL(null);
	}

	/**
	 * Returns an url string pointing to a page shown if scripts are not enabled on the browser.
	 * @return url
	 */
	public String getDisabledScriptPageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DISABLED_SCRIPT_PAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to a page shown if scripts are not enabled on the browser.
	 * @param disabledScriptPageURL url
	 */
	public void setDisabledScriptPageURL(String disabledScriptPageURL) {
		engine.setProperty(Properties.DISABLED_SCRIPT_PAGE_URL, disabledScriptPageURL);
	}

	/**
	 * Sets an url string pointing to a page shown if scripts are not enabled on the browser.
	 * @param disabledScriptPageURL url
	 */
	/**
	 * Returns <code>true</code> if the attribute "disabledScriptPageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDisabledScriptPageURLSetted() {
		return engine.isPropertySetted(Properties.DISABLED_SCRIPT_PAGE_URL);
	}

	/**
	 * Returns a boolean value that inhibits cookies for the page or not.
	 * @return true if cookies are disabled
	 */
	public String getDisabledCookiesPageURL() {
		return getDisabledCookiesPageURL(null);
	}

	/**
	 * Returns a boolean value that inhibits cookies for the page or not.
	 * @return true if cookies are disabled
	 */
	public String getDisabledCookiesPageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DISABLED_COOKIES_PAGE_URL, facesContext);
	}

	/**
	 * Sets a boolean value that inhibits cookies for the page or not.
	 * @param disabledCookiesPageURL true if cookies are to be disabled
	 */
	public void setDisabledCookiesPageURL(String disabledCookiesPageURL) {
		engine.setProperty(Properties.DISABLED_COOKIES_PAGE_URL, disabledCookiesPageURL);
	}

	/**
	 * Sets a boolean value that inhibits cookies for the page or not.
	 * @param disabledCookiesPageURL true if cookies are to be disabled
	 */
	/**
	 * Returns <code>true</code> if the attribute "disabledCookiesPageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDisabledCookiesPageURLSetted() {
		return engine.isPropertySetted(Properties.DISABLED_COOKIES_PAGE_URL);
	}

	/**
	 * Returns an url pointing to the page to show if the browser is not supported.
	 * @return url
	 */
	public String getInvalidBrowserPageURL() {
		return getInvalidBrowserPageURL(null);
	}

	/**
	 * Returns an url pointing to the page to show if the browser is not supported.
	 * @return url
	 */
	public String getInvalidBrowserPageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.INVALID_BROWSER_PAGE_URL, facesContext);
	}

	/**
	 * Sets an url pointing to the page to show if the browser is not supported.
	 * @param invalidBrowserPageURL url
	 */
	public void setInvalidBrowserPageURL(String invalidBrowserPageURL) {
		engine.setProperty(Properties.INVALID_BROWSER_PAGE_URL, invalidBrowserPageURL);
	}

	/**
	 * Sets an url pointing to the page to show if the browser is not supported.
	 * @param invalidBrowserPageURL url
	 */
	/**
	 * Returns <code>true</code> if the attribute "invalidBrowserPageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isInvalidBrowserPageURLSetted() {
		return engine.isPropertySetted(Properties.INVALID_BROWSER_PAGE_URL);
	}

	public java.util.Locale getLiteralLocale() {
		return getLiteralLocale(null);
	}

	public java.util.Locale getLiteralLocale(javax.faces.context.FacesContext facesContext) {
		return (java.util.Locale)engine.getValue(Properties.LITERAL_LOCALE, facesContext);
	}

	public void setLiteralLocale(java.util.Locale literalLocale) {
		engine.setProperty(Properties.LITERAL_LOCALE, literalLocale);
	}

	/**
	 * Returns <code>true</code> if the attribute "literalLocale" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isLiteralLocaleSetted() {
		return engine.isPropertySetted(Properties.LITERAL_LOCALE);
	}

	public java.util.TimeZone getLiteralTimeZone() {
		return getLiteralTimeZone(null);
	}

	public java.util.TimeZone getLiteralTimeZone(javax.faces.context.FacesContext facesContext) {
		return (java.util.TimeZone)engine.getValue(Properties.LITERAL_TIME_ZONE, facesContext);
	}

	public void setLiteralTimeZone(java.util.TimeZone literalTimeZone) {
		engine.setProperty(Properties.LITERAL_TIME_ZONE, literalTimeZone);
	}

	/**
	 * Returns <code>true</code> if the attribute "literalTimeZone" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isLiteralTimeZoneSetted() {
		return engine.isPropertySetted(Properties.LITERAL_TIME_ZONE);
	}

	/**
	 * Returns a boolean value specifying wether the "image Bar" (contextual menu for images) in Internet Explorer should be hidden.
	 * @return true if the image bar is disabled
	 */
	public boolean isDisabledIEImageBar() {
		return isDisabledIEImageBar(null);
	}

	/**
	 * Returns a boolean value specifying wether the "image Bar" (contextual menu for images) in Internet Explorer should be hidden.
	 * @return true if the image bar is disabled
	 */
	public boolean isDisabledIEImageBar(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.DISABLED_IEIMAGE_BAR, false, facesContext);
	}

	/**
	 * Sets a boolean value specifying wether the "image Bar" (contextual menu for images) in Internet Explorer should be hidden.
	 * @param disabledIEImageBar true if the image bar is to be disabled
	 */
	public void setDisabledIEImageBar(boolean disabledIEImageBar) {
		engine.setProperty(Properties.DISABLED_IEIMAGE_BAR, disabledIEImageBar);
	}

	/**
	 * Sets a boolean value specifying wether the "image Bar" (contextual menu for images) in Internet Explorer should be hidden.
	 * @param disabledIEImageBar true if the image bar is to be disabled
	 */
	/**
	 * Returns <code>true</code> if the attribute "disabledIEImageBar" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDisabledIEImageBarSetted() {
		return engine.isPropertySetted(Properties.DISABLED_IEIMAGE_BAR);
	}

	/**
	 * Returns a boolean value that force disabled cache for the page or not.
	 * @return true if the cache is disabled
	 */
	public boolean isDisableCache() {
		return isDisableCache(null);
	}

	/**
	 * Returns a boolean value that force disabled cache for the page or not.
	 * @return true if the cache is disabled
	 */
	public boolean isDisableCache(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.DISABLE_CACHE, false, facesContext);
	}

	/**
	 * Sets a boolean value that force disabled cache for the page or not.
	 * @param disableCache true if the cache is to be disabled
	 */
	public void setDisableCache(boolean disableCache) {
		engine.setProperty(Properties.DISABLE_CACHE, disableCache);
	}

	/**
	 * Sets a boolean value that force disabled cache for the page or not.
	 * @param disableCache true if the cache is to be disabled
	 */
	/**
	 * Returns <code>true</code> if the attribute "disableCache" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDisableCacheSetted() {
		return engine.isPropertySetted(Properties.DISABLE_CACHE);
	}

	/**
	 * Returns a boolean value that hide context menu for the page or not.
	 * @return true if the browser context menu is hidden
	 */
	public boolean isDisableContextMenu() {
		return isDisableContextMenu(null);
	}

	/**
	 * Returns a boolean value that hide context menu for the page or not.
	 * @return true if the browser context menu is hidden
	 */
	public boolean isDisableContextMenu(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.DISABLE_CONTEXT_MENU, false, facesContext);
	}

	/**
	 * Sets a boolean value that hide context menu for the page or not.
	 * @param disableContextMenu true if the browser context menu is to be hidden
	 */
	public void setDisableContextMenu(boolean disableContextMenu) {
		engine.setProperty(Properties.DISABLE_CONTEXT_MENU, disableContextMenu);
	}

	/**
	 * Sets a boolean value that hide context menu for the page or not.
	 * @param disableContextMenu true if the browser context menu is to be hidden
	 */
	/**
	 * Returns <code>true</code> if the attribute "disableContextMenu" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDisableContextMenuSetted() {
		return engine.isPropertySetted(Properties.DISABLE_CONTEXT_MENU);
	}

	/**
	 * Returns a string value specifying the HTML base.
	 * @return HTML base
	 */
	public boolean isRenderBaseTag() {
		return isRenderBaseTag(null);
	}

	/**
	 * Returns a string value specifying the HTML base.
	 * @return HTML base
	 */
	public boolean isRenderBaseTag(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.RENDER_BASE_TAG, true, facesContext);
	}

	/**
	 * Sets a string value specifying the HTML base.
	 * @param renderBaseTag HTML base
	 */
	public void setRenderBaseTag(boolean renderBaseTag) {
		engine.setProperty(Properties.RENDER_BASE_TAG, renderBaseTag);
	}

	/**
	 * Sets a string value specifying the HTML base.
	 * @param renderBaseTag HTML base
	 */
	/**
	 * Returns <code>true</code> if the attribute "renderBaseTag" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRenderBaseTagSetted() {
		return engine.isPropertySetted(Properties.RENDER_BASE_TAG);
	}

	public String getClientMessageIdFilter() {
		return getClientMessageIdFilter(null);
	}

	public String getClientMessageIdFilter(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CLIENT_MESSAGE_ID_FILTER, facesContext);
	}

	public void setClientMessageIdFilter(String clientMessageIdFilter) {
		engine.setProperty(Properties.CLIENT_MESSAGE_ID_FILTER, clientMessageIdFilter);
	}

	/**
	 * Returns <code>true</code> if the attribute "clientMessageIdFilter" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isClientMessageIdFilterSetted() {
		return engine.isPropertySetted(Properties.CLIENT_MESSAGE_ID_FILTER);
	}

	public String getWaiRolesNS() {
		return getWaiRolesNS(null);
	}

	public String getWaiRolesNS(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.WAI_ROLES_NS, facesContext);
	}

	public void setWaiRolesNS(String waiRolesNS) {
		engine.setProperty(Properties.WAI_ROLES_NS, waiRolesNS);
	}

	/**
	 * Returns <code>true</code> if the attribute "waiRolesNS" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isWaiRolesNSSetted() {
		return engine.isPropertySetted(Properties.WAI_ROLES_NS);
	}

	public boolean isClientValidation() {
		return isClientValidation(null);
	}

	public boolean isClientValidation(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CLIENT_VALIDATION, true, facesContext);
	}

	public void setClientValidation(boolean clientValidation) {
		engine.setProperty(Properties.CLIENT_VALIDATION, clientValidation);
	}

	/**
	 * Returns <code>true</code> if the attribute "clientValidation" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isClientValidationSetted() {
		return engine.isPropertySetted(Properties.CLIENT_VALIDATION);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
