/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.javascript;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractFacesImplementation {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractFacesImplementation.class);

    private static AbstractFacesImplementation singleton;

    private static final AbstractFacesImplementation UNKNOWN = new AbstractFacesImplementation() {
        private static final String REVISION = "$Revision$";

        public String getJavaScriptModuleName() {
            return null;
        }

    };

    static synchronized AbstractFacesImplementation get() {
        if (singleton != null) {
            return singleton;
        }

        try {
            Class clazz = AbstractFacesImplementation.class.getClassLoader()
                    .loadClass("com.sun.faces.RIConstants");

            String version = clazz.getPackage().getImplementationVersion();

            if (version != null) {
                LOG.info("Faces RI version '" + version + "' detected !");
            }

            /*
             * Désormais on envoie tous les inputs Hidden lors de requetes AJAX
             * Aussi singleton = new AbstractFacesImplementation() { private
             * static final String REVISION = "$Revision$";
             * 
             * public String getJavaScriptModuleName() { return
             * "com.sun.faces.RI_1_1_2"; } };
             */
        } catch (Throwable th) {
            // Pas de RI !
        }

        if (singleton == null) {
            singleton = UNKNOWN;
        }

        return singleton;
    }

    public abstract String getJavaScriptModuleName();

}
