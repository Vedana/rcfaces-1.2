/*
 * $Id$
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
        if (l >= Level.FATAL.toInt()) {
            return 0;
        }
        if (l >= Level.ERROR.toInt()) {
            return 1;
        }
        if (l >= Level.WARN.toInt()) {
            return 2;
        }
        if (l >= Level.INFO.toInt()) {
            return 3;
        }
        if (l >= Level.DEBUG.toInt()) {
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
