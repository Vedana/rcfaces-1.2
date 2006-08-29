/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/07/18 17:06:29  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 */
package org.rcfaces.renderkit.html.internal.javascript;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
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
             * Aussi 
            singleton = new AbstractFacesImplementation() {
                private static final String REVISION = "$Revision$";

                public String getJavaScriptModuleName() {
                    return "com.sun.faces.RI_1_1_2";
                }
            };
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
