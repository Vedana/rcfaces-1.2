/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.2  2004/08/16 10:20:00  oeuillot
 * Ajout de immediate
 *
 * Revision 1.1  2004/08/13 13:04:58  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.internal.listener;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractScriptListener implements IScriptListener,
        StateHolder {

    private String scriptType;

    private String command;

    private int hashCode = 0;

    public AbstractScriptListener(String scriptType, String command) {
        if (scriptType == null || command == null) {
            throw new NullPointerException(
                    "Can not create AbstractScriptListener: parameter is null.");
        }

        this.scriptType = scriptType;
        this.command = command;
        this.hashCode = computeHashCode();
    }

    public AbstractScriptListener() {
    }

    public final String getScriptType() {
        return scriptType;
    }

    public final String getCommand() {
        return command;
    }

    protected int computeHashCode() {
        int hashCode = 0;

        if (scriptType != null) {
            hashCode ^= scriptType.hashCode();
        }

        if (command != null) {
            hashCode ^= command.hashCode();
        }

        return hashCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return hashCode;
    }

    public boolean equals(Object object) {
        if (object == null
                || (object instanceof AbstractScriptListener) == false) {
            return false;
        }

        AbstractScriptListener s = (AbstractScriptListener) object;

        if (scriptType != s.scriptType) {
            if (scriptType == null || scriptType.equals(s.scriptType) == false) {
                return false;
            }
        }

        if (command != s.command) {
            if (command == null || command.equals(s.command) == false) {
                return false;
            }
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext,
     *      java.lang.Object)
     */
    public final void restoreState(FacesContext context, Object state) {
        String s[] = (String[]) state;

        scriptType = s[0];
        command = s[1];
        hashCode = computeHashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
     */
    public final Object saveState(FacesContext context) {
        return new String[] { scriptType, command };
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.component.StateHolder#isTransient()
     */
    public boolean isTransient() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.component.StateHolder#setTransient(boolean)
     */
    public void setTransient(boolean newTransientValue) {
    }
}