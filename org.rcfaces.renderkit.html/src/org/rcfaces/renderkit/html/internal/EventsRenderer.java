/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/11/09 19:08:57  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.7  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.6  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.5  2006/05/19 20:40:42  oeuillot
 * Ajout de la gestion du disabled pour le treeNode
 * Generalisation du fa_cardinality
 * Ajout de la cardinalit� de selection pour l'arbre
 * Correction des Sets javascript
 * Optimisation importante des perfs du javascript
 *
 * Revision 1.4  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 * Revision 1.3  2006/03/15 13:53:04  oeuillot
 * Stabilisation
 * Ajout des bundles pour le javascript
 * R�organisation de l'arborescence de GridData qui n'herite plus de UIData
 *
 * Revision 1.2  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.1  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Iterator;
import java.util.Map;

import javax.faces.event.FacesListener;

import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.listener.AbstractScriptListener;
import org.rcfaces.core.internal.listener.IScriptListener;
import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class EventsRenderer {
    private static final String REVISION = "$Revision$";

    static final String DEFAULT_SUBMIT = "submit";

    private static final String DEFAULT_SUBMIT_JAVA_SCRIPT = "f_core.Submit()";

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

                encodeEventListeners(js, varName, listenerType, listeners);
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
            String varName, String listenerType, FacesListener facesListeners[])
            throws WriterException {

        boolean first = true;

        for (int i = 0; i < facesListeners.length; i++) {
            FacesListener fl = facesListeners[i];

            if ((fl instanceof IScriptListener) == false) {
                continue;
            }

            IScriptListener scriptListener = (IScriptListener) fl;

            if (IHtmlRenderContext.JAVASCRIPT_TYPE.equals(scriptListener
                    .getScriptType()) == false) {
                continue;
            }

            if (first) {
                first = false;
                js.writeCall(varName, ADD_EVENT_LISTENERS).write(listenerType);
            }

            js.write(',');

            encodeJavaScriptCommmand(js, scriptListener);
        }

        if (first && facesListeners.length > 0) {
            // Des evenements serveurs mais pas Javascript
            // Aussi on ajoute un submit() histoire de soliciter les methodes
            // cot� serveur
            encodeJavaScriptEventSubmit(js, varName, listenerType);
            return;
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

    public static void encodeAttributeEventListeners(StringAppender sb,
            String listenerType, FacesListener[] facesListeners) {

        int cnt = 0;

        boolean clientSubmit = false;
        boolean needSubmit = false;

        for (int i = 0; i < facesListeners.length; i++) {
            FacesListener fl = facesListeners[i];

            if ((fl instanceof IScriptListener) == false) {
                // C'est un listener serveur, il nous faut un submit coté client
                // !

                if (clientSubmit) {
                    // Submit déjà généré : on arrete tout !
                    break;
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
                    .getScriptType()) == false) {
                continue;
            }

            String command = scriptListener.getCommand();

            if (DEFAULT_SUBMIT.equals(command)) {
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
