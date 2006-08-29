/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/06/19 17:22:19  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.1  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.1  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 */
package org.rcfaces.core.internal.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class KeyFlagsConverter implements Converter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new KeyFlagsConverter();

    private static final Integer NO_FLAGS = new Integer(0);

    public static final int SHIFT_FLAG = 0x01;

    public static final int CONTROL_FLAG = 0x02;

    public static final int ALT_FLAG = 0x04;

    public static final int META_FLAG = 0x08;

    private static final Map FLAGS = new HashMap(8);
    static {
        FLAGS.put("SHIFT", new Integer(SHIFT_FLAG));
        FLAGS.put("CONTROL", new Integer(CONTROL_FLAG));
        FLAGS.put("CTRL", new Integer(CONTROL_FLAG));
        FLAGS.put("ALT", new Integer(ALT_FLAG));
        FLAGS.put("META", new Integer(META_FLAG));
    }

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        if (value == null) {
            return NO_FLAGS;
        }

        StringTokenizer st = new StringTokenizer(value, ",; ");

        int mask = 0;

        for (; st.hasMoreTokens();) {
            String token = st.nextToken().toUpperCase();

            Integer flags = (Integer) FLAGS.get(token);
            if (flags == null) {
                continue;
            }

            mask |= flags.intValue();
        }

        if (mask == 0) {
            return NO_FLAGS;
        }

        return new Integer(mask);
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        throw new FacesException("Not implemented !");
    }

    public static Integer convertUpperCase(String key) {
        return (Integer) FLAGS.get(key);
    }
}
