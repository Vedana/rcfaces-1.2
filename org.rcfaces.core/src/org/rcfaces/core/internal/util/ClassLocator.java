/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/10/13 18:04:51  oeuillot
 * Ajout de:
 * DateEntry
 * StyledMessage
 * MessageFieldSet
 * xxxxConverter
 * Adapter
 *
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
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

    public static final URL getResource(String resourceLocation,
            Object fallback, Object context) {

        IOException thOrigin[] = new IOException[1];

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl != null) {
            URL url = cl.getResource(resourceLocation);

            if (testURL(url, thOrigin)) {
                return url;
            }
        }

        if (fallback != null) {
            URL url = fallback.getClass().getClassLoader().getResource(
                    resourceLocation);
            if (testURL(url, thOrigin)) {
                return url;
            }
        }

        URL url = ClassLocator.class.getClassLoader().getResource(
                resourceLocation);
        if (testURL(url, thOrigin)) {
            return url;
        }

        if (context instanceof FacesContext) {
            context = ((FacesContext) context).getExternalContext()
                    .getContext();
        }

        if (context instanceof ServletContext) {
            context = ((ServletContext) context).getClass().getClassLoader();
        }

        if (context instanceof ClassLoader) {
            url = ((ClassLoader) context).getResource(resourceLocation);

            if (testURL(url, thOrigin)) {
                return url;
            }
        }

        if (thOrigin[0] == null) {
            thOrigin[0] = new IOException("Can not find resource '"
                    + resourceLocation + "'.");
        }

        return null;
    }

    protected static boolean testURL(URL url, IOException exs[]) {
        if (url == null) {
            return false;
        }
        try {
            InputStream ins = url.openStream();

            ins.close();

            return true;

        } catch (IOException ex) {
            if (exs != null && exs[0] == null) {
                exs[0] = ex;
            }
        }

        return false;
    }
}
