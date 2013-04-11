/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.renderkit;

import java.util.Locale;

import javax.faces.component.StateHolder;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IScriptRenderContext extends StateHolder {

    Locale getUserLocale();

//    String convertSymbol(String className, String memberName);
}
