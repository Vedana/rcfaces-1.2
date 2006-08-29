/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.4  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.3  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.2  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.1  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.2  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.1  2004/08/20 13:32:07  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2004/08/16 13:30:00  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/16 08:00:08  oeuillot
 * Gestion des listeners
 *
 */
package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesListener;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class SelectionEvent extends ActionEvent implements ITypedEvent {
    private static final String REVISION = "$Revision$";

    public static final int SELECTED_EVENT_TYPE = 0x100;

    public static final int DEFAULT_SELECTED_EVENT_TYPE = 0x102;

    public static final int UNKNOWN_POSITION = -1;

    public static final int UNKNOWN_KEY = -1;

    public static final int UNKNOWN_BUTTONS = 0x8000;

    public static final int UNKNOWN_MODIFIERS = 0x8000;

    public static final int BUTTON_1 = 0x01;

    public static final int BUTTON_2 = 0x02;

    public static final int BUTTON_3 = 0x04;

    public static final int MODIFIER_ALT = 0x01;

    public static final int MODIFIER_CTRL = 0x02;

    public static final int MODIFIER_SHIFT = 0x04;

    private final int type;

    private final int keyCode;

    private final int mouseX;

    private final int mouseY;

    private final int buttonsMask;

    private final int modifiersMask;

    private String value;

    private String item;

    public SelectionEvent(UIComponent component, String value, String item,
            int detail) {
        this(component, computeTypeFromDetail(detail), UNKNOWN_POSITION,
                UNKNOWN_POSITION, UNKNOWN_BUTTONS, UNKNOWN_MODIFIERS,
                UNKNOWN_KEY);

        this.value = value;
        this.item = item;
    }

    private static int computeTypeFromDetail(int detail) {
        // XXX A terminer !
        return 0;
    }

    public SelectionEvent(UIComponent component, int type, int mouseX,
            int mouseY, int buttonsMask, int modifiersMask) {
        this(component, type, mouseX, mouseY, buttonsMask, modifiersMask,
                UNKNOWN_KEY);
    }

    public SelectionEvent(UIComponent component, int type, int mouseX,
            int mouseY, int buttonsMask, int modifiersMask, int keyCode) {
        super(component);

        this.type = type;

        this.mouseX = mouseX;
        this.mouseY = mouseY;

        this.buttonsMask = buttonsMask;
        this.modifiersMask = modifiersMask;
        this.keyCode = keyCode;
    }

    public int getType() {
        return type;
    }

    public String getItem() {
        return item;
    }

    public String getValue() {
        return value;
    }

    public int getButtonsMask() {
        return buttonsMask;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public int getModifiersMask() {
        return modifiersMask;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.event.FacesEvent#isAppropriateListener(javax.faces.event.FacesListener)
     */
    public boolean isAppropriateListener(FacesListener listener) {
        return (listener instanceof ISelectionListener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.event.FacesEvent#processListener(javax.faces.event.FacesListener)
     */
    public void processListener(FacesListener listener) {
        ((ISelectionListener) listener).componentSelected(this);
    }

}