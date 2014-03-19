/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.javascript;

import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IJavaScriptSymbolsConverter {
    String SERVICE_ID = "org.rcfaces.javaScript.SYMBOLS_CONVERTER";

    String convertSymbols(String identifier, String code,
            Map<String, String> symbols,
            Map<String, Object> applicatioParameters);
}
