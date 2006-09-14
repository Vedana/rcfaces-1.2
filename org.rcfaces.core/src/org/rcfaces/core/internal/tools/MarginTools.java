/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/03/23 19:12:40  oeuillot
 * Ajout des marges
 * Ajout des processors
 * Amelioration des menus
 *
 */
package org.rcfaces.core.internal.tools;

import java.util.StringTokenizer;

import javax.faces.FacesException;

import org.rcfaces.core.component.capability.IMarginCapability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MarginTools {
    private static final String REVISION = "$Revision$";

    public static void setMargins(IMarginCapability marginCapability,
            String margins) {
        if (margins == null) {
            return;
        }
        margins = margins.trim();
        if (margins.length() < 1) {
            return;
        }

        StringTokenizer st = new StringTokenizer(margins, " ");
        String tok1 = st.nextToken();
        if (st.hasMoreTokens() == false) {
            marginCapability.setMarginTop(tok1);
            marginCapability.setMarginRight(tok1);
            marginCapability.setMarginBottom(tok1);
            marginCapability.setMarginLeft(tok1);
            return;
        }
        String tok2 = st.nextToken();
        if (st.hasMoreTokens() == false) {
            marginCapability.setMarginTop(tok1);
            marginCapability.setMarginRight(tok2);
            marginCapability.setMarginBottom(tok1);
            marginCapability.setMarginLeft(tok2);
            return;
        }
        String tok3 = st.nextToken();
        if (st.hasMoreTokens() == false) {
            marginCapability.setMarginTop(tok1);
            marginCapability.setMarginRight(tok2);
            marginCapability.setMarginBottom(tok3);
            marginCapability.setMarginLeft(tok2);
            return;
        }

        String tok4 = st.nextToken();
        if (st.hasMoreTokens()) {
            throw new FacesException("Invalid margins form '" + margins + "'.");
        }

        marginCapability.setMarginTop(tok1);
        marginCapability.setMarginRight(tok2);
        marginCapability.setMarginBottom(tok3);
        marginCapability.setMarginLeft(tok4);
    }
}
