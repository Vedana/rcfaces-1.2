/*
 * $Id$
 */
package org.rcfaces.core.model;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IStyledSelectItem {

    int AS_PUSH_BUTTON = 1;

    int AS_CHECK_BOX = 2;

    int AS_RADIO_BUTTON = 8;

    int AS_DROP_DOWN_MENU = 4;

    int getStyle();
}
