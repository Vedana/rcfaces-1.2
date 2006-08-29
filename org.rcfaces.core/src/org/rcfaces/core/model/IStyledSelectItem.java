/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/01/03 15:21:39  oeuillot
 * Refonte du systeme de menuPopup !
 *
 */
package org.rcfaces.core.model;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IStyledSelectItem {

    int AS_PUSH_BUTTON = 1;

    int AS_CHECK_BOX = 2;

    int AS_RADIO_BUTTON = 8;

    int AS_DROP_DOWN_MENU = 4;

    int getStyle();
}
