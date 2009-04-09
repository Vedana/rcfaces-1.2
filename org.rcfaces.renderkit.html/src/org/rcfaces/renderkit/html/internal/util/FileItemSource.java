package org.rcfaces.renderkit.html.internal.util;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.contentAccessor.IResourceKeyParticipant;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.item.ICharSetItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FileItemSource implements StateHolder, IResourceKeyParticipant {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(FileItemSource.class);

    private String source;

    private String charSet;

    private boolean transientState;

    public FileItemSource(SelectItem selectItem) {
        source = (String) selectItem.getValue();

        if (selectItem instanceof ICharSetItem) {
            charSet = ((ICharSetItem) selectItem).getCharSet();
        }
    }

    public FileItemSource(String source, String userAgentVary, String charSet) {
        this.source = source;

        this.charSet = charSet;
    }

    public FileItemSource() {
    }

    public String getSource() {
        return source;
    }

    public String getCharSet() {
        return charSet;
    }

    public boolean isTransient() {
        return transientState;
    }

    public void setTransient(boolean newTransientValue) {
        this.transientState = newTransientValue;
    }

    public void restoreState(FacesContext context, Object state) {
        Object ret[] = (Object[]) state;

        source = (String) ret[0];
        // userAgentVary = (String) ret[1];
        charSet = (String) ret[2];
    }

    public Object saveState(FacesContext context) {
        return new Object[] { source, null, charSet };
    }

    public void participeKey(StringAppender sa) {
        if (source != null) {
            sa.append(source);
        }
        /*
         * if (userAgentVary != null) {
         * sa.append(IResourceKeyParticipant.RESOURCE_KEY_SEPARATOR).append(
         * userAgentVary); }
         */
        if (charSet != null) {
            sa.append(IResourceKeyParticipant.RESOURCE_KEY_SEPARATOR).append(
                    charSet);
        }
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((charSet == null) ? 0 : charSet.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FileItemSource other = (FileItemSource) obj;
        if (charSet == null) {
            if (other.charSet != null)
                return false;
        } else if (!charSet.equals(other.charSet))
            return false;
        return true;
    }
}