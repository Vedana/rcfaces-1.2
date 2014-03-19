/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.css;

import org.rcfaces.renderkit.html.internal.agent.IClientBrowser;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICssConfig {

    String getDefaultStyleSheetURI();

    String getStyleSheetFileName(IClientBrowser clientBrowser);

    String getCharSet();

}
