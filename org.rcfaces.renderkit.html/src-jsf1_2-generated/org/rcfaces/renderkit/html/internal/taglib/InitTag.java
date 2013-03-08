package org.rcfaces.renderkit.html.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.renderkit.html.component.InitComponent;
import javax.faces.context.FacesContext;

public class InitTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(InitTag.class);

	private ValueExpression base;
	private ValueExpression domain;
	private ValueExpression title;
	private ValueExpression favoriteImageURL;
	private ValueExpression disabledScriptPageURL;
	private ValueExpression disabledCookiesPageURL;
	private ValueExpression invalidBrowserPageURL;
	private ValueExpression literalLocale;
	private ValueExpression literalTimeZone;
	private ValueExpression disabledIEImageBar;
	private ValueExpression disableCache;
	private ValueExpression disableContextMenu;
	private ValueExpression renderBaseTag;
	private ValueExpression clientMessageIdFilter;
	private ValueExpression waiRolesNS;
	private ValueExpression clientValidation;
	private ValueExpression userAgentVary;
	public String getComponentType() {
		return InitComponent.COMPONENT_TYPE;
	}

	public void setBase(ValueExpression base) {
		this.base = base;
	}

	public void setDomain(ValueExpression domain) {
		this.domain = domain;
	}

	public void setTitle(ValueExpression title) {
		this.title = title;
	}

	public void setFavoriteImageURL(ValueExpression favoriteImageURL) {
		this.favoriteImageURL = favoriteImageURL;
	}

	public void setDisabledScriptPageURL(ValueExpression disabledScriptPageURL) {
		this.disabledScriptPageURL = disabledScriptPageURL;
	}

	public void setDisabledCookiesPageURL(ValueExpression disabledCookiesPageURL) {
		this.disabledCookiesPageURL = disabledCookiesPageURL;
	}

	public void setInvalidBrowserPageURL(ValueExpression invalidBrowserPageURL) {
		this.invalidBrowserPageURL = invalidBrowserPageURL;
	}

	public void setLiteralLocale(ValueExpression literalLocale) {
		this.literalLocale = literalLocale;
	}

	public void setLiteralTimeZone(ValueExpression literalTimeZone) {
		this.literalTimeZone = literalTimeZone;
	}

	public void setDisabledIEImageBar(ValueExpression disabledIEImageBar) {
		this.disabledIEImageBar = disabledIEImageBar;
	}

	public void setDisableCache(ValueExpression disableCache) {
		this.disableCache = disableCache;
	}

	public void setDisableContextMenu(ValueExpression disableContextMenu) {
		this.disableContextMenu = disableContextMenu;
	}

	public void setRenderBaseTag(ValueExpression renderBaseTag) {
		this.renderBaseTag = renderBaseTag;
	}

	public void setClientMessageIdFilter(ValueExpression clientMessageIdFilter) {
		this.clientMessageIdFilter = clientMessageIdFilter;
	}

	public void setWaiRolesNS(ValueExpression waiRolesNS) {
		this.waiRolesNS = waiRolesNS;
	}

	public void setClientValidation(ValueExpression clientValidation) {
		this.clientValidation = clientValidation;
	}

	public void setUserAgentVary(ValueExpression userAgentVary) {
		this.userAgentVary = userAgentVary;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (InitComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  base='"+base+"'");
			LOG.debug("  domain='"+domain+"'");
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
			LOG.debug("  userAgentVary='"+userAgentVary+"'");
		}
		if ((uiComponent instanceof InitComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'InitComponent'.");
		}

		super.setProperties(uiComponent);

		InitComponent component = (InitComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (base != null) {
			if (base.isLiteralText()==false) {
				component.setValueExpression(Properties.BASE, base);

			} else {
				component.setBase(base.getExpressionString());
			}
		}

		if (domain != null) {
			if (domain.isLiteralText()==false) {
				component.setValueExpression(Properties.DOMAIN, domain);

			} else {
				component.setDomain(domain.getExpressionString());
			}
		}

		if (title != null) {
			if (title.isLiteralText()==false) {
				component.setValueExpression(Properties.TITLE, title);

			} else {
				component.setTitle(title.getExpressionString());
			}
		}

		if (favoriteImageURL != null) {
			if (favoriteImageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.FAVORITE_IMAGE_URL, favoriteImageURL);

			} else {
				component.setFavoriteImageURL(favoriteImageURL.getExpressionString());
			}
		}

		if (disabledScriptPageURL != null) {
			if (disabledScriptPageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLED_SCRIPT_PAGE_URL, disabledScriptPageURL);

			} else {
				component.setDisabledScriptPageURL(disabledScriptPageURL.getExpressionString());
			}
		}

		if (disabledCookiesPageURL != null) {
			if (disabledCookiesPageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLED_COOKIES_PAGE_URL, disabledCookiesPageURL);

			} else {
				component.setDisabledCookiesPageURL(disabledCookiesPageURL.getExpressionString());
			}
		}

		if (invalidBrowserPageURL != null) {
			if (invalidBrowserPageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.INVALID_BROWSER_PAGE_URL, invalidBrowserPageURL);

			} else {
				component.setInvalidBrowserPageURL(invalidBrowserPageURL.getExpressionString());
			}
		}

		if (literalLocale != null) {
			if (literalLocale.isLiteralText()==false) {
				component.setValueExpression(Properties.LITERAL_LOCALE, literalLocale);

			} else {
				component.setLiteralLocale(literalLocale.getExpressionString());
			}
		}

		if (literalTimeZone != null) {
			if (literalTimeZone.isLiteralText()==false) {
				component.setValueExpression(Properties.LITERAL_TIME_ZONE, literalTimeZone);

			} else {
				component.setLiteralTimeZone(literalTimeZone.getExpressionString());
			}
		}

		if (disabledIEImageBar != null) {
			if (disabledIEImageBar.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLED_IEIMAGE_BAR, disabledIEImageBar);

			} else {
				component.setDisabledIEImageBar(getBool(disabledIEImageBar.getExpressionString()));
			}
		}

		if (disableCache != null) {
			if (disableCache.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLE_CACHE, disableCache);

			} else {
				component.setDisableCache(getBool(disableCache.getExpressionString()));
			}
		}

		if (disableContextMenu != null) {
			if (disableContextMenu.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLE_CONTEXT_MENU, disableContextMenu);

			} else {
				component.setDisableContextMenu(getBool(disableContextMenu.getExpressionString()));
			}
		}

		if (renderBaseTag != null) {
			if (renderBaseTag.isLiteralText()==false) {
				component.setValueExpression(Properties.RENDER_BASE_TAG, renderBaseTag);

			} else {
				component.setRenderBaseTag(getBool(renderBaseTag.getExpressionString()));
			}
		}

		if (clientMessageIdFilter != null) {
			if (clientMessageIdFilter.isLiteralText()==false) {
				component.setValueExpression(Properties.CLIENT_MESSAGE_ID_FILTER, clientMessageIdFilter);

			} else {
				component.setClientMessageIdFilter(clientMessageIdFilter.getExpressionString());
			}
		}

		if (waiRolesNS != null) {
			if (waiRolesNS.isLiteralText()==false) {
				component.setValueExpression(Properties.WAI_ROLES_NS, waiRolesNS);

			} else {
				component.setWaiRolesNS(waiRolesNS.getExpressionString());
			}
		}

		if (clientValidation != null) {
			if (clientValidation.isLiteralText()==false) {
				component.setValueExpression(Properties.CLIENT_VALIDATION, clientValidation);

			} else {
				component.setClientValidation(getBool(clientValidation.getExpressionString()));
			}
		}

		if (userAgentVary != null) {
			if (userAgentVary.isLiteralText()==false) {
				component.setValueExpression(Properties.USER_AGENT_VARY, userAgentVary);

			} else {
				component.setUserAgentVary(getBool(userAgentVary.getExpressionString()));
			}
		}
	}

	public void release() {
		base = null;
		domain = null;
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
		userAgentVary = null;

		super.release();
	}

}
