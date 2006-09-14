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
 * Revision 1.2  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.1  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 */
package org.rcfaces.core.internal.service.log;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;
import org.rcfaces.core.internal.service.AbstractService;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class LogService extends AbstractService {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(LogService.class);

    public static final String PREFIX_LOGGER_NAME = "client.";

    protected static final IFilter[] EMPTY_FILTERS = new IFilter[0];

    private final Object LOGGER_LOCK = new Object();

    private ILogger logger;

    protected ILogger getLogger() {
        synchronized (LOGGER_LOCK) {
            if (logger != null) {
                return logger;
            }

            try {
                if (LOG instanceof Log4JLogger) {
                    logger = new Log4jLogger();
                }

            } catch (Throwable th) {
                // en cas de debuggage, on peut avoir un ClassCastException !
            }

            if (logger == null) {
                logger = new CommonsLogger();
            }
        }
        return logger;
    }

    public interface IFilter {
        String getName();

        int getLevel();
    }

    public interface ILogger {
        void logException(FacesContext facesContext, UIViewRoot viewRoot,
                String name, long date, String message, int level,
                Throwable exception);

        IFilter[] listFilters(FacesContext facesContext);
    }

    public final IFilter[] listFilters(FacesContext facesContext) {
        return getLogger().listFilters(facesContext);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class Filter implements IFilter {
        private static final String REVISION = "$Revision$";

        private final int level;

        private final String name;

        public Filter(int level, String name) {
            this.level = level;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }
    }
}
