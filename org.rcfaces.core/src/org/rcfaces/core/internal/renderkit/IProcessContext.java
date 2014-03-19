/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.renderkit;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.renderkit.designer.IDesignerEngine;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IProcessContext {

    String DEFAULT_TIMEZONE_PARAMETER = Constants.getPackagePrefix()
            + ".DEFAULT_TIMEZONE";

    String FORCED_DATE_TIMEZONE_PARAMETER = Constants.getPackagePrefix()
            + ".FORCED_DATE_TIMEZONE";

    RcfacesContext getRcfacesContext();

    String getNamingSeparator();

    FacesContext getFacesContext();

    Boolean getMultiWindowMode();

    Boolean getDebugMode();

    Boolean getProfilerMode();

    IDesignerInterface getDesignerInterface();

    Locale getUserLocale();

    TimeZone getUserTimeZone();

    Calendar getUserCalendar();

    String getAbsolutePath(String uri, boolean containsContextPath);

    String getRelativePath(String uri);

    void changeBaseHREF(String base);

    String getBaseHREF();

    String getScriptType();

    Locale getDefaultLiteralLocale();

    TimeZone getDefaultTimeZone();

    TimeZone getForcedDateTimeZone();

    Calendar getForcedDateCalendar();

    IDesignerEngine getDesignerEngine();
}
