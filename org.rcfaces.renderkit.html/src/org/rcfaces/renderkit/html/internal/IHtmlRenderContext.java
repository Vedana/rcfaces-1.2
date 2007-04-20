/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Set;

import org.rcfaces.core.internal.capability.IAsyncRenderComponent;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;

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

    IJavaScriptWriter removeJavaScriptWriter(IHtmlWriter writer);

    IJavaScriptWriter getJavaScriptWriter(IHtmlWriter writer,
            IJavaScriptComponent javaScriptComponent) throws WriterException;

    void pushInteractiveRenderComponent(IHtmlWriter writer);

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
}