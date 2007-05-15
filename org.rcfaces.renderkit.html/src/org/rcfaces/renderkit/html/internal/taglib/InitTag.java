package org.rcfaces.renderkit.html.internal.taglib;

import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import org.rcfaces.renderkit.html.component.InitComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class InitTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(InitTag.class);

	private String base;
	private String title;
	private String favoriteImageURL;
	private String disabledScriptPageURL;
	private String disabledCookiesPageURL;
	private String invalidBrowserPageURL;
	private String literalLocale;
	private String literalTimeZone;
	private String disabledIEImageBar;
	private String disableCache;
	private String disableContextMenu;
	private String renderBaseTag;
	private String clientMessageIdFilter;
	private String waiRolesNS;
	private String clientValidation;
	public String getComponentType() {
		return InitComponent.COMPONENT_TYPE;
	}

	public final String getBase() {
		return base;
	}

	public final void setBase(String base) {
		this.base = base;
	}

	public final String getTitle() {
		return title;
	}

	public final void setTitle(String title) {
		this.title = title;
	}

	public final String getFavoriteImageURL() {
		return favoriteImageURL;
	}

	public final void setFavoriteImageURL(String favoriteImageURL) {
		this.favoriteImageURL = favoriteImageURL;
	}

	public final String getDisabledScriptPageURL() {
		return disabledScriptPageURL;
	}

	public final void setDisabledScriptPageURL(String disabledScriptPageURL) {
		this.disabledScriptPageURL = disabledScriptPageURL;
	}

	public final String getDisabledCookiesPageURL() {
		return disabledCookiesPageURL;
	}

	public final void setDisabledCookiesPageURL(String disabledCookiesPageURL) {
		this.disabledCookiesPageURL = disabledCookiesPageURL;
	}

	public final String getInvalidBrowserPageURL() {
		return invalidBrowserPageURL;
	}

	public final void setInvalidBrowserPageURL(String invalidBrowserPageURL) {
		this.invalidBrowserPageURL = invalidBrowserPageURL;
	}

	public final String getLiteralLocale() {
		return literalLocale;
	}

	public final void setLiteralLocale(String literalLocale) {
		this.literalLocale = literalLocale;
	}

	public final String getLiteralTimeZone() {
		return literalTimeZone;
	}

	public final void setLiteralTimeZone(String literalTimeZone) {
		this.literalTimeZone = literalTimeZone;
	}

	public final String getDisabledIEImageBar() {
		return disabledIEImageBar;
	}

	public final void setDisabledIEImageBar(String disabledIEImageBar) {
		this.disabledIEImageBar = disabledIEImageBar;
	}

	public final String getDisableCache() {
		return disableCache;
	}

	public final void setDisableCache(String disableCache) {
		this.disableCache = disableCache;
	}

	public final String getDisableContextMenu() {
		return disableContextMenu;
	}

	public final void setDisableContextMenu(String disableContextMenu) {
		this.disableContextMenu = disableContextMenu;
	}

	public final String getRenderBaseTag() {
		return renderBaseTag;
	}

	public final void setRenderBaseTag(String renderBaseTag) {
		this.renderBaseTag = renderBaseTag;
	}

	public final String getClientMessageIdFilter() {
		return clientMessageIdFilter;
	}

	public final void setClientMessageIdFilter(String clientMessageIdFilter) {
		this.clientMessageIdFilter = clientMessageIdFilter;
	}

	public final String getWaiRolesNS() {
		return waiRolesNS;
	}

	public final void setWaiRolesNS(String waiRolesNS) {
		this.waiRolesNS = waiRolesNS;
	}

	public final String getClientValidation() {
		return clientValidation;
	}

	public final void setClientValidation(String clientValidation) {
		this.clientValidation = clientValidation;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (InitComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  base='"+base+"'");
			LOG.debug("  title='"+title+"'");
			LOG.debug("  favoriteImageURL='"+favoriteImageURL+"'");
			LOG.debug("  disabledScriptPageURL='"+disabledScriptPageURL+"'");
			LOG.debug("  disabledCookiesPageURL='"+disabledCookiesPageURL+"'");
			LOG.debug("  invalidBrowserPageURL='"+invalidBrowserPageURL+"'");
			LOG.debug("  literalLocale='"+literalLocale+"'");
			LOG.debug("  literalTimeZone='"+literalTimeZone+"'");
			LOG.debug("  disabledIEImageBar='"+disabledIEImageBar+"'");
			LOG.debug("  disableCache='"+disableCache+"'");
			LOG.debug("  disableContextMenu='"+disableContextMenu+"'");
			LOG.debug("  renderBaseTag='"+renderBaseTag+"'");
			LOG.debug("  clientMessageIdFilter='"+clientMessageIdFilter+"'");
			LOG.debug("  waiRolesNS='"+waiRolesNS+"'");
			LOG.debug("  clientValidation='"+clientValidation+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof InitComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'InitComponent'.");
		}

		InitComponent component = (InitComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (base != null) {
			if (isValueReference(base)) {
				ValueBinding vb = application.createValueBinding(base);
				component.setBase(vb);
			} else {
				component.setBase(base);
			}
		}

		if (title != null) {
			if (isValueReference(title)) {
				ValueBinding vb = application.createValueBinding(title);
				component.setTitle(vb);
			} else {
				component.setTitle(title);
			}
		}

		if (favoriteImageURL != null) {
			if (isValueReference(favoriteImageURL)) {
				ValueBinding vb = application.createValueBinding(favoriteImageURL);
				component.setFavoriteImageURL(vb);
			} else {
				component.setFavoriteImageURL(favoriteImageURL);
			}
		}

		if (disabledScriptPageURL != null) {
			if (isValueReference(disabledScriptPageURL)) {
				ValueBinding vb = application.createValueBinding(disabledScriptPageURL);
				component.setDisabledScriptPageURL(vb);
			} else {
				component.setDisabledScriptPageURL(disabledScriptPageURL);
			}
		}

		if (disabledCookiesPageURL != null) {
			if (isValueReference(disabledCookiesPageURL)) {
				ValueBinding vb = application.createValueBinding(disabledCookiesPageURL);
				component.setDisabledCookiesPageURL(vb);
			} else {
				component.setDisabledCookiesPageURL(disabledCookiesPageURL);
			}
		}

		if (invalidBrowserPageURL != null) {
			if (isValueReference(invalidBrowserPageURL)) {
				ValueBinding vb = application.createValueBinding(invalidBrowserPageURL);
				component.setInvalidBrowserPageURL(vb);
			} else {
				component.setInvalidBrowserPageURL(invalidBrowserPageURL);
			}
		}

		if (literalLocale != null) {
			if (isValueReference(literalLocale)) {
				ValueBinding vb = application.createValueBinding(literalLocale);
				component.setLiteralLocale(vb);
			} else {
				component.setLiteralLocale(literalLocale);
			}
		}

		if (literalTimeZone != null) {
			if (isValueReference(literalTimeZone)) {
				ValueBinding vb = application.createValueBinding(literalTimeZone);
				component.setLiteralTimeZone(vb);
			} else {
				component.setLiteralTimeZone(literalTimeZone);
			}
		}

		if (disabledIEImageBar != null) {
			if (isValueReference(disabledIEImageBar)) {
				ValueBinding vb = application.createValueBinding(disabledIEImageBar);
				component.setDisabledIEImageBar(vb);
			} else {
				component.setDisabledIEImageBar(getBool(disabledIEImageBar));
			}
		}

		if (disableCache != null) {
			if (isValueReference(disableCache)) {
				ValueBinding vb = application.createValueBinding(disableCache);
				component.setDisableCache(vb);
			} else {
				component.setDisableCache(getBool(disableCache));
			}
		}

		if (disableContextMenu != null) {
			if (isValueReference(disableContextMenu)) {
				ValueBinding vb = application.createValueBinding(disableContextMenu);
				component.setDisableContextMenu(vb);
			} else {
				component.setDisableContextMenu(getBool(disableContextMenu));
			}
		}

		if (renderBaseTag != null) {
			if (isValueReference(renderBaseTag)) {
				ValueBinding vb = application.createValueBinding(renderBaseTag);
				component.setRenderBaseTag(vb);
			} else {
				component.setRenderBaseTag(getBool(renderBaseTag));
			}
		}

		if (clientMessageIdFilter != null) {
			if (isValueReference(clientMessageIdFilter)) {
				ValueBinding vb = application.createValueBinding(clientMessageIdFilter);
				component.setClientMessageIdFilter(vb);
			} else {
				component.setClientMessageIdFilter(clientMessageIdFilter);
			}
		}

		if (waiRolesNS != null) {
			if (isValueReference(waiRolesNS)) {
				ValueBinding vb = application.createValueBinding(waiRolesNS);
				component.setWaiRolesNS(vb);
			} else {
				component.setWaiRolesNS(waiRolesNS);
			}
		}

		if (clientValidation != null) {
			if (isValueReference(clientValidation)) {
				ValueBinding vb = application.createValueBinding(clientValidation);
				component.setClientValidation(vb);
			} else {
				component.setClientValidation(getBool(clientValidation));
			}
		}
	}

	public void release() {
		base = null;
		title = null;
		favoriteImageURL = null;
		disabledScriptPageURL = null;
		disabledCookiesPageURL = null;
		invalidBrowserPageURL = null;
		literalLocale = null;
		literalTimeZone = null;
		disabledIEImageBar = null;
		disableCache = null;
		disableContextMenu = null;
		renderBaseTag = null;
		clientMessageIdFilter = null;
		waiRolesNS = null;
		clientValidation = null;

		super.release();
	}

}
