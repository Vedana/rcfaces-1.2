/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.designtime;

import java.util.Locale;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDesignTimePoint {
    String getStyleSheetContent(Locale locale);

    String getJavaScriptContent(Locale locale);
}
