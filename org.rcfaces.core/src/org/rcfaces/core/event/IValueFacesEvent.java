/*
 * $Id$
 */
package org.rcfaces.core.event;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IValueFacesEvent extends IFacesEvent {

    String getValue();

    Object getValueObject();
}
