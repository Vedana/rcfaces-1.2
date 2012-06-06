/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.FacesException;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.renderkit.IProcessContext;

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
        if (command == null) {
            throw new NullPointerException(
                    "Can not create AbstractScriptListener: parameter is null.");
        }

        this.scriptType = scriptType;
        this.command = command;
        this.hashCode = computeHashCode();
    }

    public AbstractScriptListener() {
    }

    public final String getScriptType(IProcessContext processContext) {
        if (scriptType == null) {
            scriptType = processContext.getScriptType();

            if (scriptType == null) {
                throw new FacesException(
                        "No script type defined ! (You may use an init tag to resolve this problem)");
            }

        }
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
    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
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
     * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.
     * FacesContext, java.lang.Object)
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
     * @see
     * javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext
     * )
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
