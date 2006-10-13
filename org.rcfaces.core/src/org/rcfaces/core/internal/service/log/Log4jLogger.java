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
 * Revision 1.1  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 */
package org.rcfaces.core.internal.service.log;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.rcfaces.core.internal.service.log.LogService.IFilter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
class Log4jLogger implements LogService.ILogger {

    private static final String REVISION = "$Revision$";

    private IFilter filters[];

    public void logException(FacesContext facesContext, UIViewRoot viewRoot,
            String name, long date, String message, int level,
            Throwable exception) {

        Logger logger = Logger.getLogger(name);

        if (logger.getLoggerRepository().isDisabled(level) == false) {
            return;
        }

        Level levelObject = convertIntToLevel(level);

        logger.callAppenders(new LoggingEvent(name, logger, date, levelObject,
                message, exception));
    }

    public synchronized IFilter[] listFilters(FacesContext facesContext) {
        if (filters != null) {
            return filters;
        }

        List l = null;

        Enumeration loggers = LogManager.getCurrentLoggers();

        for (; loggers.hasMoreElements();) {
            Logger logger = (Logger) loggers.nextElement();

            String name = logger.getName();
            if (name.startsWith(LogService.PREFIX_LOGGER_NAME) == false) {
                continue;
            }
            name = name.substring(LogService.PREFIX_LOGGER_NAME.length());
            if (name.length() > 0 && name.charAt(0) == '.') {
                name = name.substring(1);
            }

            int level = convertLevelToInt(logger.getEffectiveLevel());

            if (l == null) {
                l = new ArrayList();
            }

            l.add(new LogService.Filter(level, name));
        }

        if (l == null) {
            return LogService.EMPTY_FILTERS;
        }

        return (LogService.Filter[]) l.toArray(new LogService.Filter[l.size()]);
    }

    private static final int convertLevelToInt(Level level) {
        int l = level.toInt();
        if (l >= Level.FATAL_INT) {
            return 0;
        }
        if (l >= Level.ERROR_INT) {
            return 1;
        }
        if (l >= Level.WARN_INT) {
            return 2;
        }
        if (l >= Level.INFO_INT) {
            return 3;
        }
        if (l >= Level.DEBUG_INT) {
            return 4;
        }

        return 5;
    }

    private static final Level convertIntToLevel(int level) {
        switch (level) {
        case 0:
            return Level.FATAL;

        case 1:
            return Level.ERROR;

        case 2:
            return Level.WARN;

        case 3:
            return Level.INFO;

        default:
            return Level.DEBUG;
        }
    }
}
