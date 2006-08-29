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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.service.log.LogService.IFilter;
import org.rcfaces.core.internal.service.log.LogService.ILogger;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class CommonsLogger implements ILogger {
    private static final String REVISION = "$Revision$";

    private static final String DEFAULT_LOG_LEVEL_PARAMETER = Constants
            .getPackagePrefix()
            + ".client.DEFAULT_LOG_LEVEL";

    private static final String LOG_LEVELS_PARAMETER = Constants
            .getPackagePrefix()
            + ".client.LOG_LEVELS";

    private static final Map LOG_LEVELS = new HashMap(8);

    static {
        LOG_LEVELS.put("FATAL", new Integer(0));
        LOG_LEVELS.put("ERROR", new Integer(3));
        LOG_LEVELS.put("WARN", new Integer(4));
        LOG_LEVELS.put("INFO", new Integer(6));
        LOG_LEVELS.put("DEBUG", new Integer(7));
        LOG_LEVELS.put("TRACE", new Integer(8));
    }

    private IFilter filters[];

    public void logException(FacesContext facesContext, UIViewRoot viewRoot,
            String name, long date, String message, int level, Throwable ex) {

        if (name != null) {
            try {
                Log log = LogFactory.getLog(LogService.PREFIX_LOGGER_NAME
                        + name);
                if (log != null) {
                    switch (level) {
                    case 0:
                        log.fatal(message, ex);
                        return;

                    case 3:
                        log.error(message, ex);
                        return;

                    case 4:
                        log.warn(message, ex);
                        return;

                    case 6:
                        log.info(message, ex);
                        return;

                    case 7:
                        log.debug(message, ex);
                        return;
                    }
                }

            } catch (LogConfigurationException logException) {
                LogService.LOG.error("Can not log '" + name + "'.",
                        logException);
            }
        }

        System.out.println("Name=" + name);
        System.out.println("  level=" + level);
        System.out.println("  message=" + message);
        System.out.println("  exception=" + ex);
    }

    public synchronized IFilter[] listFilters(FacesContext facesContext) {
        if (verifyFilters(facesContext)) {
            return filters;
        }

        filters = loadFilters(facesContext);

        return filters;
    }

    protected boolean verifyFilters(FacesContext facesContext) {
        return (filters != null);
    }

    protected IFilter[] loadFilters(FacesContext facesContext) {

        Map initParameters = facesContext.getExternalContext()
                .getInitParameterMap();

        List l = new ArrayList();

        String level = (String) initParameters.get(DEFAULT_LOG_LEVEL_PARAMETER);
        if (level != null) {
            Integer i = (Integer) LOG_LEVELS.get(level.toUpperCase());
            if (i != null) {
                l.add(new LogService.Filter(i.intValue(), ""));
            }
        }

        String levels = (String) initParameters.get(LOG_LEVELS_PARAMETER);
        if (levels != null) {
            for (StringTokenizer st = new StringTokenizer(levels, ", "); st
                    .hasMoreTokens();) {
                String token = st.nextToken();

                int idx = token.indexOf('=');
                if (idx < 0) {
                    continue;
                }

                String name = token.substring(0, idx);
                level = token.substring(idx + 1);
                Integer i = (Integer) LOG_LEVELS.get(level.toUpperCase());
                if (i == null) {
                    continue;
                }

                l.add(new LogService.Filter(i.intValue(), name));
            }
        }

        if (l.isEmpty()) {
            return LogService.EMPTY_FILTERS;
        }

        return (IFilter[]) l.toArray(new IFilter[l.size()]);
    }
}