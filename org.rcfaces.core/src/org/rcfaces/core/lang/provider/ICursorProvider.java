/*
 * $Id$
 */
package org.rcfaces.core.lang.provider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICursorProvider {
    Object getCursorValue();

    void setCursorValue(Object cursorValue);
}
