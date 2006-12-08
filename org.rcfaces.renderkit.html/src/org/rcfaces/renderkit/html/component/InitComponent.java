package org.rcfaces.renderkit.html.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import java.util.Locale;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.core.internal.component.IPageConfigurator;
import org.rcfaces.core.internal.converter.LocaleConverter;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import java.util.Set;
import java.util.HashSet;

public class InitComponent extends CameliaBaseComponent implements 
	IPageConfigurator {

	public static final String COMPONENT_TYPE="org.rcfaces.html.init";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"base","renderBaseTag","disabledCookiesPageURL","disableContextMenu","title","invalidBrowserPageURL","disabledScriptPageURL","favoriteImageURL","disabledIEImageBar","attributesLocale","disableCache"}));
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

	public final void setAttributesLocale(String locale) {


		setAttributesLocale((Locale)LocaleConverter.SINGLETON.getAsObject(null, this, locale));
		
	}

	public final String getBase() {
		return getBase(null);
	}

	public final String getBase(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BASE, facesContext);
	}

	public final void setBase(String base) {
		engine.setProperty(Properties.BASE, base);
	}

	public final void setBase(ValueBinding base) {
		engine.setProperty(Properties.BASE, base);
	}

	public final boolean isBaseSetted() {
		return engine.isPropertySetted(Properties.BASE);
	}

	public final String getTitle() {
		return getTitle(null);
	}

	public final String getTitle(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TITLE, facesContext);
	}

	public final void setTitle(String title) {
		engine.setProperty(Properties.TITLE, title);
	}

	public final void setTitle(ValueBinding title) {
		engine.setProperty(Properties.TITLE, title);
	}

	public final boolean isTitleSetted() {
		return engine.isPropertySetted(Properties.TITLE);
	}

	public final String getFavoriteImageURL() {
		return getFavoriteImageURL(null);
	}

	public final String getFavoriteImageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FAVORITE_IMAGE_URL, facesContext);
	}

	public final void setFavoriteImageURL(String favoriteImageURL) {
		engine.setProperty(Properties.FAVORITE_IMAGE_URL, favoriteImageURL);
	}

	public final void setFavoriteImageURL(ValueBinding favoriteImageURL) {
		engine.setProperty(Properties.FAVORITE_IMAGE_URL, favoriteImageURL);
	}

	public final boolean isFavoriteImageURLSetted() {
		return engine.isPropertySetted(Properties.FAVORITE_IMAGE_URL);
	}

	public final String getDisabledScriptPageURL() {
		return getDisabledScriptPageURL(null);
	}

	public final String getDisabledScriptPageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DISABLED_SCRIPT_PAGE_URL, facesContext);
	}

	public final void setDisabledScriptPageURL(String disabledScriptPageURL) {
		engine.setProperty(Properties.DISABLED_SCRIPT_PAGE_URL, disabledScriptPageURL);
	}

	public final void setDisabledScriptPageURL(ValueBinding disabledScriptPageURL) {
		engine.setProperty(Properties.DISABLED_SCRIPT_PAGE_URL, disabledScriptPageURL);
	}

	public final boolean isDisabledScriptPageURLSetted() {
		return engine.isPropertySetted(Properties.DISABLED_SCRIPT_PAGE_URL);
	}

	public final String getDisabledCookiesPageURL() {
		return getDisabledCookiesPageURL(null);
	}

	public final String getDisabledCookiesPageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.DISABLED_COOKIES_PAGE_URL, facesContext);
	}

	public final void setDisabledCookiesPageURL(String disabledCookiesPageURL) {
		engine.setProperty(Properties.DISABLED_COOKIES_PAGE_URL, disabledCookiesPageURL);
	}

	public final void setDisabledCookiesPageURL(ValueBinding disabledCookiesPageURL) {
		engine.setProperty(Properties.DISABLED_COOKIES_PAGE_URL, disabledCookiesPageURL);
	}

	public final boolean isDisabledCookiesPageURLSetted() {
		return engine.isPropertySetted(Properties.DISABLED_COOKIES_PAGE_URL);
	}

	public final String getInvalidBrowserPageURL() {
		return getInvalidBrowserPageURL(null);
	}

	public final String getInvalidBrowserPageURL(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.INVALID_BROWSER_PAGE_URL, facesContext);
	}

	public final void setInvalidBrowserPageURL(String invalidBrowserPageURL) {
		engine.setProperty(Properties.INVALID_BROWSER_PAGE_URL, invalidBrowserPageURL);
	}

	public final void setInvalidBrowserPageURL(ValueBinding invalidBrowserPageURL) {
		engine.setProperty(Properties.INVALID_BROWSER_PAGE_URL, invalidBrowserPageURL);
	}

	public final boolean isInvalidBrowserPageURLSetted() {
		return engine.isPropertySetted(Properties.INVALID_BROWSER_PAGE_URL);
	}

	public final java.util.Locale getAttributesLocale() {
		return getAttributesLocale(null);
	}

	public final java.util.Locale getAttributesLocale(javax.faces.context.FacesContext facesContext) {
		return (java.util.Locale)engine.getValue(Properties.ATTRIBUTES_LOCALE, facesContext);
	}

	public final void setAttributesLocale(java.util.Locale attributesLocale) {
		engine.setProperty(Properties.ATTRIBUTES_LOCALE, attributesLocale);
	}

	public final void setAttributesLocale(ValueBinding attributesLocale) {
		engine.setProperty(Properties.ATTRIBUTES_LOCALE, attributesLocale);
	}

	public final boolean isAttributesLocaleSetted() {
		return engine.isPropertySetted(Properties.ATTRIBUTES_LOCALE);
	}

	public final boolean isDisabledIEImageBar() {
		return isDisabledIEImageBar(null);
	}

	public final boolean isDisabledIEImageBar(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.DISABLED_IEIMAGE_BAR, false, facesContext);
	}

	public final void setDisabledIEImageBar(boolean disabledIEImageBar) {
		engine.setProperty(Properties.DISABLED_IEIMAGE_BAR, disabledIEImageBar);
	}

	public final void setDisabledIEImageBar(ValueBinding disabledIEImageBar) {
		engine.setProperty(Properties.DISABLED_IEIMAGE_BAR, disabledIEImageBar);
	}

	public final boolean isDisabledIEImageBarSetted() {
		return engine.isPropertySetted(Properties.DISABLED_IEIMAGE_BAR);
	}

	public final boolean isDisableCache() {
		return isDisableCache(null);
	}

	public final boolean isDisableCache(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.DISABLE_CACHE, false, facesContext);
	}

	public final void setDisableCache(boolean disableCache) {
		engine.setProperty(Properties.DISABLE_CACHE, disableCache);
	}

	public final void setDisableCache(ValueBinding disableCache) {
		engine.setProperty(Properties.DISABLE_CACHE, disableCache);
	}

	public final boolean isDisableCacheSetted() {
		return engine.isPropertySetted(Properties.DISABLE_CACHE);
	}

	public final boolean isDisableContextMenu() {
		return isDisableContextMenu(null);
	}

	public final boolean isDisableContextMenu(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.DISABLE_CONTEXT_MENU, false, facesContext);
	}

	public final void setDisableContextMenu(boolean disableContextMenu) {
		engine.setProperty(Properties.DISABLE_CONTEXT_MENU, disableContextMenu);
	}

	public final void setDisableContextMenu(ValueBinding disableContextMenu) {
		engine.setProperty(Properties.DISABLE_CONTEXT_MENU, disableContextMenu);
	}

	public final boolean isDisableContextMenuSetted() {
		return engine.isPropertySetted(Properties.DISABLE_CONTEXT_MENU);
	}

	public final boolean isRenderBaseTag() {
		return isRenderBaseTag(null);
	}

	public final boolean isRenderBaseTag(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.RENDER_BASE_TAG, false, facesContext);
	}

	public final void setRenderBaseTag(boolean renderBaseTag) {
		engine.setProperty(Properties.RENDER_BASE_TAG, renderBaseTag);
	}

	public final void setRenderBaseTag(ValueBinding renderBaseTag) {
		engine.setProperty(Properties.RENDER_BASE_TAG, renderBaseTag);
	}

	public final boolean isRenderBaseTagSetted() {
		return engine.isPropertySetted(Properties.RENDER_BASE_TAG);
	}

	public void release() {
		super.release();
	}
	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
