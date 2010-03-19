/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.renderkit;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IEventData {

    String getEventName();

    String getEventValue();

    String getEventItem();

    int getEventDetail();

    Object getEventObject(IDecoderContext decoderContext);
}
