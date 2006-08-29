/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.2  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.1  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 */
package org.rcfaces.core.internal.util;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ClassLocator {
    private static final String REVISION = "$Revision$";

    public static final Class load(String className, Object fallback,
            Object context) throws ClassNotFoundException {

        ClassNotFoundException thOrigin = null;

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl != null) {
            try {
                return cl.loadClass(className);

            } catch (ClassNotFoundException ex) {
                if (thOrigin == null) {
                    thOrigin = ex;
                }
            }
        }

        if (fallback != null) {
            try {
                return fallback.getClass().getClassLoader()
                        .loadClass(className);

            } catch (ClassNotFoundException ex) {
                if (thOrigin == null) {
                    thOrigin = ex;
                }
            }
        }

        try {
            return ClassLocator.class.getClassLoader().loadClass(className);

        } catch (ClassNotFoundException ex) {
            if (thOrigin == null) {
                thOrigin = ex;
            }
        }

        if (context instanceof FacesContext) {
            context = ((FacesContext) context).getExternalContext()
                    .getContext();
        }

        if (context instanceof ServletContext) {
            context = ((ServletContext) context).getClass().getClassLoader();
        }

        if (context instanceof ClassLoader) {
            try {
                return ((ClassLoader) context).loadClass(className);

            } catch (ClassNotFoundException ex) {
                if (thOrigin == null) {
                    thOrigin = ex;
                }
            }
        }

        if (thOrigin == null) {
            thOrigin = new ClassNotFoundException("Can not find class '"
                    + className + "'.");
        }

        throw thOrigin;
    }
}
