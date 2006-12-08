/*
 * $Id$
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
            Class cls;
            if (fallback instanceof Class) {
                cls = (Class) fallback;
            } else {
                cls = fallback.getClass();
            }
            
            try {
                return cls.getClassLoader().loadClass(className);

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
