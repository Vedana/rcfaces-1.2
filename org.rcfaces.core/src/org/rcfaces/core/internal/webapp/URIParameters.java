/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.webapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.rcfaces.core.internal.lang.StringAppender;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class URIParameters {
    public static final String PARAMETER_URI_SEPARATOR = "__";

    public static final char VERSION_PARAMETER = 'V';

    public static final char LOCALE_PARAMETER = 'L';

    public static final char AGENT_PARAMETER = 'A';

    private static final int MAX_COMMANDS = 8;

    private final String uri;

    private char[] commands;

    private String[] parameters;

    private URIParameters(String uri, char[] commands, String[] parameters) {
        this.uri = uri;
        this.commands = commands;
        this.parameters = parameters;
    }

    public URIParameters(String uri) {
        this.uri = uri;
        this.commands = new char[MAX_COMMANDS];
        this.parameters = new String[this.commands.length];
    }

    public static URIParameters parseURI(String uri) {
        int separatorPos = uri.indexOf(PARAMETER_URI_SEPARATOR);
        if (separatorPos < 0) {
            return null;
        }

        StringAppender sb = new StringAppender(uri.length());
        sb.append(uri.substring(0, separatorPos));

        int lastDot = uri.lastIndexOf('.');

        List<String> parameters = new ArrayList<String>(8);

        int last = 0;
        for (; last < uri.length();) {
            int pos = uri.indexOf(PARAMETER_URI_SEPARATOR, last);
            if (pos < 0) {
                sb.append(uri.substring(last));
                break;
            }

            pos += PARAMETER_URI_SEPARATOR.length();
            last = uri.indexOf(PARAMETER_URI_SEPARATOR, pos);
            if (lastDot >= 0 && (last < 0 || lastDot < last)) {
                last = lastDot;
            }

            if (last < 0) {
                last = uri.length();
            }

            parameters.add(uri.substring(pos, last));
        }

        char chs[] = new char[parameters.size()];
        String commands[] = new String[chs.length];

        int i = 0;
        for (Iterator<String> it = parameters.iterator(); it.hasNext(); i++) {
            String s = it.next();

            chs[i] = s.charAt(0);
            commands[i] = s.substring(1);
        }

        uri = sb.toString();

        return new URIParameters(uri, chs, commands);
    }

    public String getURI() {
        return uri;
    }

    public char getCommand(int index) {
        return commands[index];
    }

    public String getParameter(int index) {
        return parameters[index];
    }

    public int countParameters() {
        return commands.length;
    }

    public String computeParametredURI() {
        StringAppender sb = new StringAppender(uri.length() + commands.length
                * 8);

        int idx = uri.lastIndexOf('.');

        if (idx < 0) {
            sb.append(uri);
        } else {
            sb.append(uri.substring(0, idx));
        }

        for (int i = 0; i < commands.length; i++) {
            if (commands[i] == 0) {
                break;
            }

            sb.append(PARAMETER_URI_SEPARATOR);
            sb.append(commands[i]);
            String param = parameters[i];
            if (param != null) {
                sb.append(param);
            }
        }

        if (idx >= 0) {
            sb.append(uri.substring(idx));
        }

        return sb.toString();
    }

    public void append(char command, String parameter) {
        for (int i = 0; i < commands.length; i++) {
            if (commands[i] != 0) {
                continue;
            }

            commands[i] = command;
            parameters[i] = parameter;
            return;
        }

    }

    public void appendLocale(Locale locale) {
        append(LOCALE_PARAMETER, locale.toString());
    }

    public void appendVersion(String version) {
        append(VERSION_PARAMETER, version);
    }

    public void appendAgent(String version) {
        append(AGENT_PARAMETER, version);
    }

    public String getVersion() {
        return getParameterByCommand(VERSION_PARAMETER);
    }

    public String getLocaleName() {
        return getParameterByCommand(LOCALE_PARAMETER);
    }

    public String getAgent() {
        return getParameterByCommand(AGENT_PARAMETER);
    }

    private String getParameterByCommand(char command) {
        for (int i = 0; i < commands.length; i++) {
            if (commands[i] != command) {
                continue;
            }

            return parameters[i];
        }

        return null;
    }
}
