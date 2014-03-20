/*
 * $Id: IProcessContext.java,v 1.12.6.1 2012/04/20 12:01:07 oeuillot Exp $
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
 * @author Olivier Oeuillot (latest modification by $Author: oeuillot $)
 * @version $Revision: 1.12.6.1 $ $Date: 2012/04/20 12:01:07 $
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

    TimeZone getDefaultTimeZone();

    TimeZone getForcedDateTimeZone();

    Calendar getForcedDateCalendar();

    IDesignerEngine getDesignerEngine();
}
