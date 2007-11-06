/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Set;

import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.internal.capability.IAsyncRenderComponent;
import org.rcfaces.core.internal.renderkit.IRenderContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IHtmlRenderContext extends IRenderContext {

    String JAVASCRIPT_TYPE = "text/javascript";

    String JAVASCRIPT_CHARSET = "UTF-8";

    String JAVASCRIPT_CDATA_BEGIN = "//<![CDATA[";

    String JAVASCRIPT_CDATA_END = "//]]>";

    String CSS_TYPE = "text/css";

    String HTML_TYPE = "text/html";

    String NO_CLIENT_MESSAGES = "none";

    String ALL_CLIENT_MESSAGES = "all";

    IHtmlProcessContext getHtmlProcessContext();

    void pushInteractiveRenderComponent(IHtmlWriter writer);

    void popInteractiveRenderComponent();

    IJavaScriptRenderContext getJavaScriptRenderContext();

    IAsyncRenderComponent getCurrentInteractiveRenderComponent();

    String getCurrentInteractiveRenderComponentClientId();

    Object saveRenderContextState();

    boolean canUseLazyTag();

    boolean isAsyncRenderEnable();

    boolean isDisabledContextMenu();

    // void setDisabledContextMenu(boolean state);

    String getInvalidBrowserURL();

    // void setInvalidBrowserURL(String invalidBrowserURL);

    Set getClientMessageIdFilters();

    String getWaiRolesNS();

    boolean isClientValidation();

    int getAsyncRenderMode(IAsyncRenderModeCapability asyncRenderModeCapability);
}