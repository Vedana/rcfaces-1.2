package org.rcfaces.renderkit.html.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import org.rcfaces.renderkit.html.component.InitComponent;

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

	public final void setBase(String base) {
		this.base = base;
	}

	public final void setTitle(String title) {
		this.title = title;
	}

	public final void setFavoriteImageURL(String favoriteImageURL) {
		this.favoriteImageURL = favoriteImageURL;
	}

	public final void setDisabledScriptPageURL(String disabledScriptPageURL) {
		this.disabledScriptPageURL = disabledScriptPageURL;
	}

	public final void setDisabledCookiesPageURL(String disabledCookiesPageURL) {
		this.disabledCookiesPageURL = disabledCookiesPageURL;
	}

	public final void setInvalidBrowserPageURL(String invalidBrowserPageURL) {
		this.invalidBrowserPageURL = invalidBrowserPageURL;
	}

	public final void setLiteralLocale(String literalLocale) {
		this.literalLocale = literalLocale;
	}

	public final void setLiteralTimeZone(String literalTimeZone) {
		this.literalTimeZone = literalTimeZone;
	}

	public final void setDisabledIEImageBar(String disabledIEImageBar) {
		this.disabledIEImageBar = disabledIEImageBar;
	}

	public final void setDisableCache(String disableCache) {
		this.disableCache = disableCache;
	}

	public final void setDisableContextMenu(String disableContextMenu) {
		this.disableContextMenu = disableContextMenu;
	}

	public final void setRenderBaseTag(String renderBaseTag) {
		this.renderBaseTag = renderBaseTag;
	}

	public final void setClientMessageIdFilter(String clientMessageIdFilter) {
		this.clientMessageIdFilter = clientMessageIdFilter;
	}

	public final void setWaiRolesNS(String waiRolesNS) {
		this.waiRolesNS = waiRolesNS;
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
				component.setValueBinding(Properties.BASE, vb);

			} else {
				component.setBase(base);
			}
		}

		if (title != null) {
			if (isValueReference(title)) {
				ValueBinding vb = application.createValueBinding(title);
				component.setValueBinding(Properties.TITLE, vb);

			} else {
				component.setTitle(title);
			}
		}

		if (favoriteImageURL != null) {
			if (isValueReference(favoriteImageURL)) {
				ValueBinding vb = application.createValueBinding(favoriteImageURL);
				component.setValueBinding(Properties.FAVORITE_IMAGE_URL, vb);

			} else {
				component.setFavoriteImageURL(favoriteImageURL);
			}
		}

		if (disabledScriptPageURL != null) {
			if (isValueReference(disabledScriptPageURL)) {
				ValueBinding vb = application.createValueBinding(disabledScriptPageURL);
				component.setValueBinding(Properties.DISABLED_SCRIPT_PAGE_URL, vb);

			} else {
				component.setDisabledScriptPageURL(disabledScriptPageURL);
			}
		}

		if (disabledCookiesPageURL != null) {
			if (isValueReference(disabledCookiesPageURL)) {
				ValueBinding vb = application.createValueBinding(disabledCookiesPageURL);
				component.setValueBinding(Properties.DISABLED_COOKIES_PAGE_URL, vb);

			} else {
				component.setDisabledCookiesPageURL(disabledCookiesPageURL);
			}
		}

		if (invalidBrowserPageURL != null) {
			if (isValueReference(invalidBrowserPageURL)) {
				ValueBinding vb = application.createValueBinding(invalidBrowserPageURL);
				component.setValueBinding(Properties.INVALID_BROWSER_PAGE_URL, vb);

			} else {
				component.setInvalidBrowserPageURL(invalidBrowserPageURL);
			}
		}

		if (literalLocale != null) {
			if (isValueReference(literalLocale)) {
				ValueBinding vb = application.createValueBinding(literalLocale);
				component.setValueBinding(Properties.LITERAL_LOCALE, vb);

			} else {
				component.setLiteralLocale(literalLocale);
			}
		}

		if (literalTimeZone != null) {
			if (isValueReference(literalTimeZone)) {
				ValueBinding vb = application.createValueBinding(literalTimeZone);
				component.setValueBinding(Properties.LITERAL_TIME_ZONE, vb);

			} else {
				component.setLiteralTimeZone(literalTimeZone);
			}
		}

		if (disabledIEImageBar != null) {
			if (isValueReference(disabledIEImageBar)) {
				ValueBinding vb = application.createValueBinding(disabledIEImageBar);
				component.setValueBinding(Properties.DISABLED_IEIMAGE_BAR, vb);

			} else {
				component.setDisabledIEImageBar(getBool(disabledIEImageBar));
			}
		}

		if (disableCache != null) {
			if (isValueReference(disableCache)) {
				ValueBinding vb = application.createValueBinding(disableCache);
				component.setValueBinding(Properties.DISABLE_CACHE, vb);

			} else {
				component.setDisableCache(getBool(disableCache));
			}
		}

		if (disableContextMenu != null) {
			if (isValueReference(disableContextMenu)) {
				ValueBinding vb = application.createValueBinding(disableContextMenu);
				component.setValueBinding(Properties.DISABLE_CONTEXT_MENU, vb);

			} else {
				component.setDisableContextMenu(getBool(disableContextMenu));
			}
		}

		if (renderBaseTag != null) {
			if (isValueReference(renderBaseTag)) {
				ValueBinding vb = application.createValueBinding(renderBaseTag);
				component.setValueBinding(Properties.RENDER_BASE_TAG, vb);

			} else {
				component.setRenderBaseTag(getBool(renderBaseTag));
			}
		}

		if (clientMessageIdFilter != null) {
			if (isValueReference(clientMessageIdFilter)) {
				ValueBinding vb = application.createValueBinding(clientMessageIdFilter);
				component.setValueBinding(Properties.CLIENT_MESSAGE_ID_FILTER, vb);

			} else {
				component.setClientMessageIdFilter(clientMessageIdFilter);
			}
		}

		if (waiRolesNS != null) {
			if (isValueReference(waiRolesNS)) {
				ValueBinding vb = application.createValueBinding(waiRolesNS);
				component.setValueBinding(Properties.WAI_ROLES_NS, vb);

			} else {
				component.setWaiRolesNS(waiRolesNS);
			}
		}

		if (clientValidation != null) {
			if (isValueReference(clientValidation)) {
				ValueBinding vb = application.createValueBinding(clientValidation);
				component.setValueBinding(Properties.CLIENT_VALIDATION, vb);

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
