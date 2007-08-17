/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.renderkit;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.RcfacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IProcessContext {

    RcfacesContext getRcfacesContext();

    String getNamingSeparator();

    FacesContext getFacesContext();

    Boolean getDebugMode();

    Boolean getProfilerMode();

    boolean isDesignerMode();

    Locale getUserLocale();

    TimeZone getUserTimeZone();

    Calendar getUserCalendar();

    String getAbsolutePath(String uri, boolean containsContextPath);

    String getRelativePath(String uri);

    void changeBaseHREF(String base);

    String getBaseHREF();

    String getScriptType();

    Locale getDefaultLiteralLocale();
}
