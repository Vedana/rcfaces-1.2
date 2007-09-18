/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.event.FacesListener;

import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.listener.AbstractScriptListener;
import org.rcfaces.core.internal.listener.IScriptListener;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.util.ListenerTools;

/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class EventsRenderer {
    private static final String REVISION = "$Revision$";

    static final String DEFAULT_SUBMIT = "submit";

    // On conserve le mot clef coté client !
    private static final String DEFAULT_SUBMIT_JAVA_SCRIPT = "submit";

    private static final String ADD_EVENT_LISTENERS = "f_addEventListener";

    private static final FacesListener DEFAULT_SUBMIT_FACES_LISTENER = new AbstractScriptListener(
            IHtmlRenderContext.JAVASCRIPT_TYPE, DEFAULT_SUBMIT) {
        private static final String REVISION = "$Revision$";

    };

    private static final FacesListener[] DEFAULT_SUBMIT_FACES_LISTENERS = new FacesListener[] { DEFAULT_SUBMIT_FACES_LISTENER };

    public static void encodeEventListeners(IJavaScriptWriter js,
            String varName, Map listenersByType, String actionListenerType)
            throws WriterException {

        if (listenersByType.isEmpty() == false) {
            for (Iterator it = listenersByType.entrySet().iterator(); it
                    .hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();

                String listenerType = (String) entry.getKey();
                FacesListener listeners[] = (FacesListener[]) entry.getValue();

                boolean submitSupport = true;
                if (ListenerTools.ATTRIBUTE_NAME_SPACE.getValidationEventName()
                        .equals(listenerType)) {
                    submitSupport = false;
                }

                encodeEventListeners(js, varName, listenerType, listeners,
                        submitSupport);
            }
        }

        if (actionListenerType != null) {
            if (listenersByType.containsKey(actionListenerType) == false) {
                // Il faut provoquer un Submit alors ...
                encodeJavaScriptEventSubmit(js, varName, actionListenerType);
            }
        }
    }

    private static void encodeEventListeners(IJavaScriptWriter js,
            String varName, String listenerType,
            FacesListener facesListeners[], boolean submitSupport)
            throws WriterException {

        boolean first = true;

        boolean clientSubmit = false;
        boolean needSubmit = false;

        IProcessContext processContext = js.getHtmlRenderContext()
                .getProcessContext();

        for (int i = 0; i < facesListeners.length; i++) {
            FacesListener fl = facesListeners[i];

            if ((fl instanceof IScriptListener) == false) {
                // C'est un listener serveur, il nous faut un submit coté client
                // !

                if (clientSubmit) {
                    // Submit déjà généré : on arrete tout !
                    break;
                }

                if (submitSupport == false) {
                    continue;
                }
                // Il faut générer un submit en javascript !
                needSubmit = true;
                continue;
            }

            if (clientSubmit) {
                // Le client a déjà généré un submit : on ignore la suite !
                continue;
            }

            IScriptListener scriptListener = (IScriptListener) fl;

            if (IHtmlRenderContext.JAVASCRIPT_TYPE.equals(scriptListener
                    .getScriptType(processContext)) == false) {
                continue;
            }

            if (first) {
                first = false;
                js.writeCall(varName, ADD_EVENT_LISTENERS).write(listenerType);
            }

            js.write(',');

            String command = scriptListener.getCommand();

            if (DEFAULT_SUBMIT.equals(command)) {
                if (submitSupport == false) {
                    throw new FacesException("'" + DEFAULT_SUBMIT
                            + "' keyword is not supported byte listener type '"
                            + listenerType + "'.");
                }

                command = DEFAULT_SUBMIT_JAVA_SCRIPT;
                clientSubmit = true;
                needSubmit = false;
            }

            js.writeString(command);
        }

        if (needSubmit) {

            if (first) {
                first = false;
                js.writeCall(varName, ADD_EVENT_LISTENERS).write(listenerType);
            }

            js.write(',');
            js.writeString(DEFAULT_SUBMIT_JAVA_SCRIPT);
        }

        if (first == false) {
            js.writeln(");");
        }
    }

    public static final void encodeJavaScriptEventSubmit(IJavaScriptWriter js,
            String varName, String listenerType) throws WriterException {
        js.writeCall(varName, ADD_EVENT_LISTENERS).write(listenerType);
        js.write(',').writeString(DEFAULT_SUBMIT_JAVA_SCRIPT);
        js.writeln(");");
    }

    public static void encodeJavaScriptCommmand(IJavaScriptWriter js,
            IScriptListener scriptListener) throws WriterException {
        String command = scriptListener.getCommand();

        if (DEFAULT_SUBMIT.equals(command)) {
            command = DEFAULT_SUBMIT_JAVA_SCRIPT;
        }

        js.writeString(command);
    }

    public static void encodeAttributeEventListeners(
            IRenderContext renderContext, StringAppender sb,
            String listenerType, FacesListener[] facesListeners,
            boolean submitSupport) {

        int cnt = 0;

        boolean clientSubmit = false;
        boolean needSubmit = false;

        IProcessContext processContext = renderContext.getProcessContext();

        for (int i = 0; i < facesListeners.length; i++) {
            FacesListener fl = facesListeners[i];

            if ((fl instanceof IScriptListener) == false) {
                // C'est un listener serveur, il nous faut un submit coté client
                // !

                if (clientSubmit) {
                    // Submit déjà généré : on arrete tout !
                    break;
                }

                if (submitSupport == false) {
                    continue;
                }
                // Il faut générer un submit en javascript !
                needSubmit = true;
                continue;
            }

            if (clientSubmit) {
                // Le client a déjà généré un submit : on ignore la suite !
                continue;
            }

            IScriptListener scriptListener = (IScriptListener) fl;

            if (IHtmlRenderContext.JAVASCRIPT_TYPE.equals(scriptListener
                    .getScriptType(processContext)) == false) {
                continue;
            }

            String command = scriptListener.getCommand();

            if (DEFAULT_SUBMIT.equals(command)) {
                if (submitSupport == false) {
                    throw new FacesException("'" + DEFAULT_SUBMIT
                            + "' keyword is not supported byte listener type '"
                            + listenerType + "'.");
                }

                command = DEFAULT_SUBMIT_JAVA_SCRIPT;
                clientSubmit = true;
                needSubmit = false;
            }

            if (command.length() < 1) {
                continue;
            }

            if (cnt > 0) {
                sb.append(':');

            } else {
                if (sb.length() > 0) {
                    sb.append('|');
                }
                sb.append(listenerType);
                sb.append(':');
            }

            appendCommand(sb, command);

            cnt++;
        }

        if (needSubmit) {
            // Des evenements serveurs mais pas Javascript
            // Aussi on ajoute un submit() histoire de soliciter les methodes
            // coté serveur
            if (sb.length() > 0) {
                sb.append('|');
            }
            sb.append(listenerType);
            sb.append(':');
            sb.append(DEFAULT_SUBMIT_JAVA_SCRIPT);
        }
    }

    public static void appendCommand(StringAppender sa, String command) {
        char chs[] = command.toCharArray();

        int last = 0;
        for (int i = 0; i < chs.length; i++) {
            char c = chs[i];
            if (c != '|' && c != ':' && c != '%') {
                continue;
            }

            if (last < i) {
                sa.append(chs, last, i);
                last = i + 1;
            }

            switch (c) {
            case '|':
                sa.append("%7C");
                break;

            case ':':
                sa.append("%3A");
                break;

            case '%':
                sa.append("%25");
                break;
            }
        }
        if (last < chs.length) {
            sa.append(chs, last, chs.length - last);
        }
    }

    public static FacesListener[] getSubmitJavaScriptListeners() {
        return DEFAULT_SUBMIT_FACES_LISTENERS;
    }
}
