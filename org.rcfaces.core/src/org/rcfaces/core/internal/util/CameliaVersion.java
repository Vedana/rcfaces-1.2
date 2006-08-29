/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.4  2006/06/19 17:22:19  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.3  2006/02/06 16:47:05  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.2  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.1  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 */
package org.rcfaces.core.internal.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class CameliaVersion {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(CameliaVersion.class);

    private static final String CAMELIA_VERSION_PROPERTY = "camelia.version";

    private static String version;

    public static String getVersion() {

        synchronized (CAMELIA_VERSION_PROPERTY) {

            if (version != null) {
                return version;
            }

            try {
                version = System.getProperty(CAMELIA_VERSION_PROPERTY);
                if (version != null) {
                    if ("now".equals(version)) {
                        version = "0."
                                + String.valueOf(System.currentTimeMillis());
                        System.setProperty(CAMELIA_VERSION_PROPERTY, version);

                        LOG
                                .info("Camelia version: DEVELOPMENT MODE (Use fake version: "
                                        + version + ")");

                    } else {
                        LOG.info("Camelia version setted by property: "
                                + version);
                    }
                }

            } catch (Throwable th) {
                // Un probleme de sécurité peut-etre !
                LOG.debug(th);
            }

            if (version == null) {
                try {
                    version = CameliaVersion.class.getPackage()
                            .getImplementationVersion();
                    if (version != null) {
                        LOG.info("Camelia version: " + version);
                    }
                } catch (Throwable th) {
                    LOG.error("Can not get Camelia version by package API !",
                            th);
                }
            }

            if (version == null) {
                LOG.error("Version of Camelia can not be determined !");
                version = "0";
            }

            return version;
        }
    }
}
