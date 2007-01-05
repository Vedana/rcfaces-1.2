package org.rcfaces.renderkit.html.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import javax.faces.el.ValueBinding;

import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.rcfaces.core.internal.component.IPageConfigurator;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.LocaleConverter;
import org.rcfaces.core.internal.converter.TimeZoneConverter;
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
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"disableContextMenu","title","clientMessageIdFilter","base","literalTimeZone","renderBaseTag","disabledCookiesPageURL","literalLocale","disabledScriptPageURL","invalidBrowserPageURL","favoriteImageURL","disabledIEImageBar","disableCache"}));
	}

	public InitComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public InitComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final String getPageScriptType() {


			return IHtmlRenderContext.JAVASCRIPT_TYPE;
			
	}

	public final void setLiteralLocale(String locale) {


		setLiteralLocale((Locale)LocaleConverter.SINGLETON.getAsObject(null, this, locale));
		
	}

	public final void setLiteralTimeZone(String timeZone) {


		setLiteralTimeZone((TimeZone)TimeZoneConverter.SINGLETON.getAsObject(null, this, timeZone));
		
	}

	/**
	 * Returns a string value indicating the base HRef. If the key word "context" is used the base HRef is calculated from the context.
	 * @return base HRef
	 */
	public final String getBase() {
		return getBase(null);
	}

	/**
	 * Returns a string value indicating the base HRef. If the key word "context" is used the base HRef is calculated from the context.
	 * @return base HRef
	 */
	public final String getBase(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BASE, facesContext);
	}

	/**
	 * Sets a string value indicating the base HRef. If the key word "context" is used the base HRef is calculated from the context.
	 * @param base base HRef
	 */
	public final void setBase(String base) {
		engine.setProperty(Properties.BASE, base);
	}

	/**
	 * Sets a string value indicating the base HRef. If the key word "context" is used the base HRef is calculated from the context.
	 * @param base base HRef
	 */
	public final void setBase(ValueBinding base) {
		engine.setProperty(Properties.BASE, base);
	}

	/**
	 * Returns <code>true</code> if the attribute "base" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBaseSetted() {
		return engine.isPropertySetted(Properties.BASE);
	}

	/**
	 * Returns a string value specifying the title for the document.
	 * @return title
	 */
	public final String getTitle() {
		return getTitle(null);
	}

	/**
	 * Returns a string value specifying the title for the document.
	 * @return title
	 */
	public final String getTitle(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TITLE, facesContext);
	}

	/**
	 * Sets a string value specifying the title for the document.
	 * @param title title
	 */
	public final void setTitle(String title) {
		engine.setProperty(Properties.TITLE, title);
	}

	/**
	 * Sets a string value specifying the title for the document.
	 * @param title title
	 */
	public final void setTitle(ValueBinding title) {
		engine.setProperty(Properties.TITLE, title);
	}

	/**
	 * Returns <code>true</code> if the attribute "title" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTitleSetted() {
		return engine.isPropertySetted(Properties.TITLE);
	}

	/**
	 * Returns an url string pointing to the image used for a bookmark.
	 * @return favorite image url
	 */
	public final String getFavoriteImageURL() {
		return getFavoriteImageURL(null);
	}

	/**
	 * Returns an url string pointing to the image used for a bookmark.
	 * @return favorite image url
	 */
	public final String getFavoriteImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FAVORITE_IMAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to the image used for a bookmark.
	 * @param favoriteImageURL favorite image url
	 */
	public final void setFavoriteImageURL(String favoriteImageURL) {
		engine.setProperty(Properties.FAVORITE_IMAGE_URL, favoriteImageURL);
	}

	/**
	 * Sets an url string pointing to the image used for a bookmark.
	 * @param favoriteImageURL favorite image url
	 */
	public final void setFavoriteImageURL(ValueBinding favoriteImageURL) {
		engine.setProperty(Properties.FAVORITE_IMAGE_URL, favoriteImageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "favoriteImageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFavoriteImageURLSetted() {
		return engine.isPropertySetted(Properties.FAVORITE_IMAGE_URL);
	}

	/**
	 * Returns an url string pointing to a page shown if scripts are not enabled on the browser.
	 * @return url
	 */
	public final String getDisabledScriptPageURL() {
		return getDisabledScriptPageURL(null);
	}

	/**
	 * Returns an url string pointing to a page shown if scripts are not enabled on the browser.
	 * @return url
	 */
	public final String getDisabledScriptPageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DISABLED_SCRIPT_PAGE_URL, facesContext);
	}

	/**
	 * Sets an url string pointing to a page shown if scripts are not enabled on the browser.
	 * @param disabledScriptPageURL url
	 */
	public final void setDisabledScriptPageURL(String disabledScriptPageURL) {
		engine.setProperty(Properties.DISABLED_SCRIPT_PAGE_URL, disabledScriptPageURL);
	}

	/**
	 * Sets an url string pointing to a page shown if scripts are not enabled on the browser.
	 * @param disabledScriptPageURL url
	 */
	public final void setDisabledScriptPageURL(ValueBinding disabledScriptPageURL) {
		engine.setProperty(Properties.DISABLED_SCRIPT_PAGE_URL, disabledScriptPageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "disabledScriptPageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisabledScriptPageURLSetted() {
		return engine.isPropertySetted(Properties.DISABLED_SCRIPT_PAGE_URL);
	}

	/**
	 * Returns a boolean value that inhibits cookies for the page or not.
	 * @return true if cookies are disabled
	 */
	public final String getDisabledCookiesPageURL() {
		return getDisabledCookiesPageURL(null);
	}

	/**
	 * Returns a boolean value that inhibits cookies for the page or not.
	 * @return true if cookies are disabled
	 */
	public final String getDisabledCookiesPageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DISABLED_COOKIES_PAGE_URL, facesContext);
	}

	/**
	 * Sets a boolean value that inhibits cookies for the page or not.
	 * @param disabledCookiesPageURL true if cookies are to be disabled
	 */
	public final void setDisabledCookiesPageURL(String disabledCookiesPageURL) {
		engine.setProperty(Properties.DISABLED_COOKIES_PAGE_URL, disabledCookiesPageURL);
	}

	/**
	 * Sets a boolean value that inhibits cookies for the page or not.
	 * @param disabledCookiesPageURL true if cookies are to be disabled
	 */
	public final void setDisabledCookiesPageURL(ValueBinding disabledCookiesPageURL) {
		engine.setProperty(Properties.DISABLED_COOKIES_PAGE_URL, disabledCookiesPageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "disabledCookiesPageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisabledCookiesPageURLSetted() {
		return engine.isPropertySetted(Properties.DISABLED_COOKIES_PAGE_URL);
	}

	/**
	 * Returns an url pointing to the page to show if the browser is not supported.
	 * @return url
	 */
	public final String getInvalidBrowserPageURL() {
		return getInvalidBrowserPageURL(null);
	}

	/**
	 * Returns an url pointing to the page to show if the browser is not supported.
	 * @return url
	 */
	public final String getInvalidBrowserPageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.INVALID_BROWSER_PAGE_URL, facesContext);
	}

	/**
	 * Sets an url pointing to the page to show if the browser is not supported.
	 * @param invalidBrowserPageURL url
	 */
	public final void setInvalidBrowserPageURL(String invalidBrowserPageURL) {
		engine.setProperty(Properties.INVALID_BROWSER_PAGE_URL, invalidBrowserPageURL);
	}

	/**
	 * Sets an url pointing to the page to show if the browser is not supported.
	 * @param invalidBrowserPageURL url
	 */
	public final void setInvalidBrowserPageURL(ValueBinding invalidBrowserPageURL) {
		engine.setProperty(Properties.INVALID_BROWSER_PAGE_URL, invalidBrowserPageURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "invalidBrowserPageURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isInvalidBrowserPageURLSetted() {
		return engine.isPropertySetted(Properties.INVALID_BROWSER_PAGE_URL);
	}

	public final java.util.Locale getLiteralLocale() {
		return getLiteralLocale(null);
	}

	public final java.util.Locale getLiteralLocale(javax.faces.context.FacesContext facesContext) {
		return (java.util.Locale)engine.getValue(Properties.LITERAL_LOCALE, facesContext);
	}

	public final void setLiteralLocale(java.util.Locale literalLocale) {
		engine.setProperty(Properties.LITERAL_LOCALE, literalLocale);
	}

	public final void setLiteralLocale(ValueBinding literalLocale) {
		engine.setProperty(Properties.LITERAL_LOCALE, literalLocale);
	}

	/**
	 * Returns <code>true</code> if the attribute "literalLocale" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isLiteralLocaleSetted() {
		return engine.isPropertySetted(Properties.LITERAL_LOCALE);
	}

	public final java.util.TimeZone getLiteralTimeZone() {
		return getLiteralTimeZone(null);
	}

	public final java.util.TimeZone getLiteralTimeZone(javax.faces.context.FacesContext facesContext) {
		return (java.util.TimeZone)engine.getValue(Properties.LITERAL_TIME_ZONE, facesContext);
	}

	public final void setLiteralTimeZone(java.util.TimeZone literalTimeZone) {
		engine.setProperty(Properties.LITERAL_TIME_ZONE, literalTimeZone);
	}

	public final void setLiteralTimeZone(ValueBinding literalTimeZone) {
		engine.setProperty(Properties.LITERAL_TIME_ZONE, literalTimeZone);
	}

	/**
	 * Returns <code>true</code> if the attribute "literalTimeZone" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isLiteralTimeZoneSetted() {
		return engine.isPropertySetted(Properties.LITERAL_TIME_ZONE);
	}

	/**
	 * Returns a boolean value specifying wether the "image Bar" (contextual menu for images) in Internet Explorer should be hidden.
	 * @return true if the image bar is disabled
	 */
	public final boolean isDisabledIEImageBar() {
		return isDisabledIEImageBar(null);
	}

	/**
	 * Returns a boolean value specifying wether the "image Bar" (contextual menu for images) in Internet Explorer should be hidden.
	 * @return true if the image bar is disabled
	 */
	public final boolean isDisabledIEImageBar(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.DISABLED_IEIMAGE_BAR, false, facesContext);
	}

	/**
	 * Sets a boolean value specifying wether the "image Bar" (contextual menu for images) in Internet Explorer should be hidden.
	 * @param disabledIEImageBar true if the image bar is to be disabled
	 */
	public final void setDisabledIEImageBar(boolean disabledIEImageBar) {
		engine.setProperty(Properties.DISABLED_IEIMAGE_BAR, disabledIEImageBar);
	}

	/**
	 * Sets a boolean value specifying wether the "image Bar" (contextual menu for images) in Internet Explorer should be hidden.
	 * @param disabledIEImageBar true if the image bar is to be disabled
	 */
	public final void setDisabledIEImageBar(ValueBinding disabledIEImageBar) {
		engine.setProperty(Properties.DISABLED_IEIMAGE_BAR, disabledIEImageBar);
	}

	/**
	 * Returns <code>true</code> if the attribute "disabledIEImageBar" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisabledIEImageBarSetted() {
		return engine.isPropertySetted(Properties.DISABLED_IEIMAGE_BAR);
	}

	/**
	 * Returns a boolean value that force disabled cache for the page or not.
	 * @return true if the cache is disabled
	 */
	public final boolean isDisableCache() {
		return isDisableCache(null);
	}

	/**
	 * Returns a boolean value that force disabled cache for the page or not.
	 * @return true if the cache is disabled
	 */
	public final boolean isDisableCache(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.DISABLE_CACHE, false, facesContext);
	}

	/**
	 * Sets a boolean value that force disabled cache for the page or not.
	 * @param disableCache true if the cache is to be disabled
	 */
	public final void setDisableCache(boolean disableCache) {
		engine.setProperty(Properties.DISABLE_CACHE, disableCache);
	}

	/**
	 * Sets a boolean value that force disabled cache for the page or not.
	 * @param disableCache true if the cache is to be disabled
	 */
	public final void setDisableCache(ValueBinding disableCache) {
		engine.setProperty(Properties.DISABLE_CACHE, disableCache);
	}

	/**
	 * Returns <code>true</code> if the attribute "disableCache" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisableCacheSetted() {
		return engine.isPropertySetted(Properties.DISABLE_CACHE);
	}

	/**
	 * Returns a boolean value that hide context menu for the page or not.
	 * @return true if the browser context menu is hidden
	 */
	public final boolean isDisableContextMenu() {
		return isDisableContextMenu(null);
	}

	/**
	 * Returns a boolean value that hide context menu for the page or not.
	 * @return true if the browser context menu is hidden
	 */
	public final boolean isDisableContextMenu(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.DISABLE_CONTEXT_MENU, false, facesContext);
	}

	/**
	 * Sets a boolean value that hide context menu for the page or not.
	 * @param disableContextMenu true if the browser context menu is to be hidden
	 */
	public final void setDisableContextMenu(boolean disableContextMenu) {
		engine.setProperty(Properties.DISABLE_CONTEXT_MENU, disableContextMenu);
	}

	/**
	 * Sets a boolean value that hide context menu for the page or not.
	 * @param disableContextMenu true if the browser context menu is to be hidden
	 */
	public final void setDisableContextMenu(ValueBinding disableContextMenu) {
		engine.setProperty(Properties.DISABLE_CONTEXT_MENU, disableContextMenu);
	}

	/**
	 * Returns <code>true</code> if the attribute "disableContextMenu" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isDisableContextMenuSetted() {
		return engine.isPropertySetted(Properties.DISABLE_CONTEXT_MENU);
	}

	/**
	 * Returns a string value specifying the HTML base.
	 * @return HTML base
	 */
	public final boolean isRenderBaseTag() {
		return isRenderBaseTag(null);
	}

	/**
	 * Returns a string value specifying the HTML base.
	 * @return HTML base
	 */
	public final boolean isRenderBaseTag(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.RENDER_BASE_TAG, false, facesContext);
	}

	/**
	 * Sets a string value specifying the HTML base.
	 * @param renderBaseTag HTML base
	 */
	public final void setRenderBaseTag(boolean renderBaseTag) {
		engine.setProperty(Properties.RENDER_BASE_TAG, renderBaseTag);
	}

	/**
	 * Sets a string value specifying the HTML base.
	 * @param renderBaseTag HTML base
	 */
	public final void setRenderBaseTag(ValueBinding renderBaseTag) {
		engine.setProperty(Properties.RENDER_BASE_TAG, renderBaseTag);
	}

	/**
	 * Returns <code>true</code> if the attribute "renderBaseTag" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRenderBaseTagSetted() {
		return engine.isPropertySetted(Properties.RENDER_BASE_TAG);
	}

	public final String getClientMessageIdFilter() {
		return getClientMessageIdFilter(null);
	}

	public final String getClientMessageIdFilter(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.CLIENT_MESSAGE_ID_FILTER, facesContext);
	}

	public final void setClientMessageIdFilter(String clientMessageIdFilter) {
		engine.setProperty(Properties.CLIENT_MESSAGE_ID_FILTER, clientMessageIdFilter);
	}

	public final void setClientMessageIdFilter(ValueBinding clientMessageIdFilter) {
		engine.setProperty(Properties.CLIENT_MESSAGE_ID_FILTER, clientMessageIdFilter);
	}

	/**
	 * Returns <code>true</code> if the attribute "clientMessageIdFilter" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isClientMessageIdFilterSetted() {
		return engine.isPropertySetted(Properties.CLIENT_MESSAGE_ID_FILTER);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
