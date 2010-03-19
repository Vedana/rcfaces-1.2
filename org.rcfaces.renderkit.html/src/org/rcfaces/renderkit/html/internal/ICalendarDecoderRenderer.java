/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Calendar;

import org.rcfaces.core.internal.renderkit.IDecoderContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICalendarDecoderRenderer {

    Calendar getCalendar(IDecoderContext decoderContext, String attributeName);

}
