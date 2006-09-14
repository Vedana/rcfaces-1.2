/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:50  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:12  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.27  2006/08/28 16:03:55  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.26  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.25  2006/05/11 16:34:18  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 * Revision 1.24  2006/04/27 13:49:46  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.23  2006/03/02 15:31:55  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.22  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.21  2005/12/22 11:48:06  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.20  2005/11/08 12:16:26  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.19  2005/10/28 14:41:49  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.18  2005/09/16 09:54:41  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.17  2005/03/07 10:47:02  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.16  2005/02/21 17:33:06  oeuillot
 * Reorganisation du JAVASCRIPT
 * Reorganisation des ImageXxxxButton
 * Reorganise le ComponentTools => Converters
 *
 * Revision 1.15  2005/02/18 14:46:05  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.14  2004/12/24 15:10:03  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.13  2004/12/22 12:16:14  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 */
package org.rcfaces.core.internal.taglib;

import java.io.IOException;
import java.io.Writer;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.MethodBinding;
import javax.faces.webapp.UIComponentBodyTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ICheckEventCapability;
import org.rcfaces.core.component.capability.ICloseEventCapability;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.component.capability.IFocusBlurEventCapability;
import org.rcfaces.core.component.capability.IInitEventCapability;
import org.rcfaces.core.component.capability.IKeyDownEventCapability;
import org.rcfaces.core.component.capability.IKeyPressEventCapability;
import org.rcfaces.core.component.capability.IKeyUpEventCapability;
import org.rcfaces.core.component.capability.ILoadEventCapability;
import org.rcfaces.core.component.capability.IMenuEventCapability;
import org.rcfaces.core.component.capability.IMouseEventCapability;
import org.rcfaces.core.component.capability.IPropertyChangeEventCapability;
import org.rcfaces.core.component.capability.IResetEventCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.IServiceEventCapability;
import org.rcfaces.core.component.capability.ISortEventCapability;
import org.rcfaces.core.component.capability.ISuggestionEventCapability;
import org.rcfaces.core.component.capability.IUserEventCapability;
import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.component.IAsyncRenderComponent;
import org.rcfaces.core.internal.listener.BlurActionListener;
import org.rcfaces.core.internal.listener.BlurScriptListener;
import org.rcfaces.core.internal.listener.ChangeActionListener;
import org.rcfaces.core.internal.listener.ChangeScriptListener;
import org.rcfaces.core.internal.listener.CheckActionListener;
import org.rcfaces.core.internal.listener.CheckScriptListener;
import org.rcfaces.core.internal.listener.CloseActionListener;
import org.rcfaces.core.internal.listener.CloseScriptListener;
import org.rcfaces.core.internal.listener.DoubleClickActionListener;
import org.rcfaces.core.internal.listener.DoubleClickScriptListener;
import org.rcfaces.core.internal.listener.FocusScriptListener;
import org.rcfaces.core.internal.listener.InitScriptListener;
import org.rcfaces.core.internal.listener.KeyDownScriptListener;
import org.rcfaces.core.internal.listener.KeyPressScriptListener;
import org.rcfaces.core.internal.listener.KeyUpScriptListener;
import org.rcfaces.core.internal.listener.LoadScriptListener;
import org.rcfaces.core.internal.listener.MenuScriptListener;
import org.rcfaces.core.internal.listener.MouseOutScriptListener;
import org.rcfaces.core.internal.listener.MouseOverScriptListener;
import org.rcfaces.core.internal.listener.PropertyChangeActionListener;
import org.rcfaces.core.internal.listener.PropertyChangeScriptListener;
import org.rcfaces.core.internal.listener.ResetActionListener;
import org.rcfaces.core.internal.listener.ResetScriptListener;
import org.rcfaces.core.internal.listener.SelectionActionListener;
import org.rcfaces.core.internal.listener.SelectionScriptListener;
import org.rcfaces.core.internal.listener.ServiceEventActionListener;
import org.rcfaces.core.internal.listener.ServiceEventScriptListener;
import org.rcfaces.core.internal.listener.SortActionListener;
import org.rcfaces.core.internal.listener.SortScriptListener;
import org.rcfaces.core.internal.listener.SuggestionActionListener;
import org.rcfaces.core.internal.listener.SuggestionScriptListener;
import org.rcfaces.core.internal.listener.UnsupportedListenerTypeException;
import org.rcfaces.core.internal.listener.UserEventActionListener;
import org.rcfaces.core.internal.listener.UserEventScriptListener;
import org.rcfaces.core.internal.renderkit.IAsyncRenderer;
import org.rcfaces.core.internal.service.AbstractAsyncRenderService;
import org.rcfaces.core.internal.util.ForwardMethodBinding;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
abstract class CameliaTag extends UIComponentBodyTag {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(CameliaTag.class);

    private static final String RESPONSE_WRITER_PATCH_PARAMETER = Constants
            .getPackagePrefix()
            + ".RESPONSE_WRITER_PATCH";

    private AbstractAsyncRenderService asyncRenderServer = null;

    private IAsyncRenderer asyncRender = null;

    private boolean ignoreBody;

    private boolean setupWriter;

    public void release() {
        asyncRender = null;
        asyncRenderServer = null;
        ignoreBody = false;
        setupWriter = false;
        super.release();
    }

    protected int getDoStartValue() {
        UIComponent component = getComponentInstance();

        if (component instanceof IAsyncRenderComponent) {
            FacesContext facesContext = getFacesContext();

            asyncRenderServer = AbstractAsyncRenderService
                    .getInstance(facesContext);
            if (asyncRenderServer != null
                    && asyncRenderServer.isAsyncRenderEnable()) {
                asyncRender = ((IAsyncRenderComponent) component)
                        .getAsyncRenderer(facesContext);

                if (asyncRender != null) {
                    if (asyncRenderServer.isAsyncRendererEnabled(facesContext,
                            component)) {
                        return EVAL_BODY_BUFFERED;
                    }

                    asyncRender = null;
                }
            }
        }

        if (component.isRendered() == false) {
            ignoreBody = true;
            return EVAL_BODY_BUFFERED;
        }

        return (EVAL_BODY_INCLUDE);
    }

    protected void setupResponseWriter() {

        if (setupWriter) {
            if (getFacesContext().getResponseWriter() == null) {
                // C'est le fichier principal !
                setupWriter = false;
            }
        }

        super.setupResponseWriter();

        if (setupWriter == false) {
            return;
        }

        if ("true".equalsIgnoreCase(pageContext.getServletContext()
                .getInitParameter(RESPONSE_WRITER_PATCH_PARAMETER)) == false) {
            return;
        }

        installNewResponseWriter();
    }

    private void installNewResponseWriter() {
        FacesContext facesContext = getFacesContext();
        ResponseWriter responseWriter = facesContext.getResponseWriter();

        responseWriter = responseWriter.cloneWithWriter(new Writer() {
            public void close() throws IOException {
                pageContext.getOut().close();
            }

            public void flush() {
            }

            public void write(char cbuf) throws IOException {
                pageContext.getOut().write(cbuf);
            }

            public void write(char[] cbuf, int off, int len) throws IOException {
                pageContext.getOut().write(cbuf, off, len);
            }

            public void write(int c) throws IOException {
                pageContext.getOut().write(c);
            }

            public void write(String str) throws IOException {
                pageContext.getOut().write(str);
            }

            public void write(String str, int off, int len) throws IOException {
                pageContext.getOut().write(str, off, len);
            }
        });

        facesContext.setResponseWriter(responseWriter);
    }

    public void setParent(Tag parent) {
        super.setParent(parent);

        if (parent == null) {
            setupWriter = true;
        }
    }

    public int doEndTag() throws JspException {
        if (asyncRender == null || ignoreBody) {
            return super.doEndTag();
        }

        bodyContent = getBodyContent();

        asyncRenderServer.setContent(getFacesContext(), getComponentInstance(),
                bodyContent);

        return super.doEndTag();
    }

    public final String getRendererType() {
        return null;
    }

    protected static final boolean getBool(String value) {
        return Boolean.valueOf(value).booleanValue();
    }

    protected static final Boolean getBoolean(String value) {
        if (value == null) {
            return null;
        }

        return Boolean.valueOf(value);
    }

    protected static final Integer getInteger(String value) {
        if (value == null) {
            return null;
        }

        return Integer.valueOf(value);
    }

    protected static final int getInt(String value) {
        return Integer.parseInt(value);
    }

    protected static final double getDouble(String value) {
        return Double.parseDouble(value);
    }

    protected final void parseActionListener(Application application,
            UIComponent component, IListenerType listenerType, String expression) {
        parseActionListener(application, component, listenerType, expression,
                false);
    }

    protected final void parseActionListener(Application application,
            UIComponent component, IListenerType listenerType,
            String expression, boolean defaultAction) {
        expression = expression.trim();
        if (expression.length() < 1) {
            return;
        }
        /*
         * if (defaultAction && (component instanceof UICommand)) { UICommand
         * command = (UICommand) component;
         * 
         * MethodBinding vb; if (isValueReference(expression)) { vb =
         * application.createMethodBinding(expression, null); } else { vb = new
         * ForwardMethodBinding(expression); }
         * 
         * command.setActionListener(vb); return; }
         */
        String scriptType = null;

        char chs[] = expression.toCharArray();
        int par = 0;
        int acco = 0;
        int brakets = 0;
        int lastStart = 0;
        int offset;
        nextChar: for (offset = 0; offset < chs.length; offset++) {
            char c = chs[offset];

            if (c == '\"' || c == '\'') {
                for (offset++; offset < chs.length; offset++) {
                    char c2 = chs[offset];

                    if (c == c2) {
                        continue nextChar;
                    }

                    if (c2 == '\\' && offset + 1 < chs.length) {
                        offset++;
                    }
                }

                // Mauvaise syntaxe
                throw new FacesException(
                        "Syntax error on javascript expression='" + expression
                                + "': quote or double-quote are not balanced.");
            }
            if (c == '(') {
                par++;
                continue;
            }
            if (c == ')') {
                if (par < 1) {
                    throw new FacesException(
                            "Syntax error on javascript expression='"
                                    + expression
                                    + "': parentheses are not balanced");
                }
                par--;
                continue;
            }
            if (c == '{') {
                acco++;
                continue;
            }
            if (c == '}') {
                if (acco < 1) {
                    throw new FacesException(
                            "Syntax error on javascript expression='"
                                    + expression
                                    + "': braces are not balanced.");
                }
                acco--;
                continue;
            }
            if (c == '[') {
                brakets++;
                continue;
            }
            if (c == ']') {
                if (brakets < 1) {
                    throw new FacesException(
                            "Syntax error on javascript expression='"
                                    + expression
                                    + "': brackets are not balanced.");
                }
                brakets--;
                continue;
            }
            if (c != ';') {
                continue;
            }

            if (brakets > 0 || acco > 0 || par > 0) {
                continue;
            }

            if (scriptType == null) {
                scriptType = AbstractInitializeTag.getScriptType(pageContext);
            }
            parseFunction(chs, lastStart, offset - 1, expression, application,
                    component, listenerType, scriptType);
            lastStart = offset + 1;
        }

        if (lastStart < offset) {
            if (scriptType == null) {
                scriptType = AbstractInitializeTag.getScriptType(pageContext);
            }
            parseFunction(chs, lastStart, offset - 1, expression, application,
                    component, listenerType, scriptType);
        }
    }

    protected static final void parseAction(Application application,
            UIComponent component, IListenerType listenerType,
            String expression, boolean defaultAction) {
        expression = expression.trim();
        if (expression.length() < 1) {
            return;
        }

        if (defaultAction && (component instanceof UICommand)) {
            UICommand command = (UICommand) component;

            MethodBinding vb;
            if (isValueReference(expression)) {
                vb = application.createMethodBinding(expression, null);

            } else {
                vb = new ForwardMethodBinding(expression);
            }

            command.setAction(vb);
            return;
        }

        if (isValueReference(expression) == false) {
            expression = "#[" + expression + "]";
        }

        listenerType.addActionListener(component, application, expression);
    }

    private static void parseFunction(char[] chs, int start, int end,
            String listeners, Application application, UIComponent component,
            IListenerType listenerType, String scriptType) {
        for (; start < end; start++) {
            char c = chs[start];
            if (Character.isWhitespace(c) == false) {
                break;
            }
        }

        for (; end > start; end--) {
            char c = chs[end];
            if (Character.isWhitespace(c) == false) {
                break;
            }
        }

        if (start >= end) {
            // Que du blanc !
            return;
        }

        String s = listeners.substring(start, end + 1);

        if (start + 4 < end) {
            if (isValueReference(s) || isForwardReference(s)) {
                // Value reference � ajouter !
                listenerType.addActionListener(component, application, s);
                return;
            }
        }

        listenerType.addScriptListener(component, scriptType, s);
    }

    protected static boolean isForwardReference(String s) {
        if (s.startsWith("#[") == false) {
            return false;
        }

        if (s.endsWith("]") == false) {
            return false;
        }

        return true;
    }

    public boolean enableLazyDownload() {
        return false;
    }

    public void setBody(String content) {
    }

    private interface IListenerType {

        void addActionListener(UIComponent component, Application application,
                String expression);

        void addScriptListener(UIComponent component, String scriptType,
                String expression);
    }

    private static abstract class AbstractListenerType implements IListenerType {
    }

    protected static final IListenerType BLUR_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            IFocusBlurEventCapability focusBlurEventCapability = (IFocusBlurEventCapability) component;

            focusBlurEventCapability.addBlurListener(new BlurScriptListener(
                    scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            IFocusBlurEventCapability focusBlurEventCapability = (IFocusBlurEventCapability) component;

            focusBlurEventCapability.addBlurListener(new BlurActionListener(
                    expression));
        }
    };

    protected static final IListenerType FOCUS_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            IFocusBlurEventCapability focusBlurEventCapability = (IFocusBlurEventCapability) component;

            focusBlurEventCapability.addFocusListener(new FocusScriptListener(
                    scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            throw new UnsupportedListenerTypeException("focus");
        }
    };

    protected static final IListenerType LOAD_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            ILoadEventCapability loadEventCapability = (ILoadEventCapability) component;

            loadEventCapability.addLoadListener(new LoadScriptListener(
                    scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            throw new UnsupportedListenerTypeException("Load");
        }
    };

    protected static final IListenerType DOUBLE_CLICK_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            IDoubleClickEventCapability doubleClickEventCapability = (IDoubleClickEventCapability) component;

            doubleClickEventCapability
                    .addDoubleClickListener(new DoubleClickScriptListener(
                            scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            IDoubleClickEventCapability doubleClickEventCapability = (IDoubleClickEventCapability) component;

            doubleClickEventCapability
                    .addDoubleClickListener(new DoubleClickActionListener(
                            expression));
        }
    };

    protected static final IListenerType SELECTION_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            ISelectionEventCapability selectEventCapability = (ISelectionEventCapability) component;

            selectEventCapability
                    .addSelectionListener(new SelectionScriptListener(
                            scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            ISelectionEventCapability selectEventCapability = (ISelectionEventCapability) component;

            selectEventCapability
                    .addSelectionListener(new SelectionActionListener(
                            expression));
        }
    };

    protected static final IListenerType CHECK_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            ICheckEventCapability checkEventCapability = (ICheckEventCapability) component;

            checkEventCapability.addCheckListener(new CheckScriptListener(
                    scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            ICheckEventCapability checkEventCapability = (ICheckEventCapability) component;

            checkEventCapability.addCheckListener(new CheckActionListener(
                    expression));
        }
    };

    protected static final IListenerType CLOSE_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            ICloseEventCapability closeEventCapability = (ICloseEventCapability) component;

            closeEventCapability.addCloseListener(new CloseScriptListener(
                    scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            ICloseEventCapability closeEventCapability = (ICloseEventCapability) component;

            closeEventCapability.addCloseListener(new CloseActionListener(
                    expression));
        }
    };

    protected static final IListenerType VALUE_CHANGE_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            IValueChangeEventCapability changeEventCapability = (IValueChangeEventCapability) component;

            changeEventCapability
                    .addValueChangeListener(new ChangeScriptListener(
                            scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            IValueChangeEventCapability changeEventCapability = (IValueChangeEventCapability) component;

            changeEventCapability
                    .addValueChangeListener(new ChangeActionListener(expression));
        }
    };

    protected static final IListenerType SUGGESTION_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            ISuggestionEventCapability prepareEventCapability = (ISuggestionEventCapability) component;

            prepareEventCapability
                    .addSuggestionListener(new SuggestionScriptListener(
                            scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            ISuggestionEventCapability prepareEventCapability = (ISuggestionEventCapability) component;

            prepareEventCapability
                    .addSuggestionListener(new SuggestionActionListener(
                            expression));
        }
    };

    protected static final IListenerType PROPERTY_CHANGE_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            IPropertyChangeEventCapability changeEventCapability = (IPropertyChangeEventCapability) component;

            changeEventCapability
                    .addPropertyChangeListener(new PropertyChangeScriptListener(
                            scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            IPropertyChangeEventCapability propertyChangeEventCapability = (IPropertyChangeEventCapability) component;

            propertyChangeEventCapability
                    .addPropertyChangeListener(new PropertyChangeActionListener(
                            expression));
        }
    };

    protected static final IListenerType KEY_PRESS_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            IKeyPressEventCapability keyEventCapability = (IKeyPressEventCapability) component;

            keyEventCapability.addKeyPressListener(new KeyPressScriptListener(
                    scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            throw new UnsupportedListenerTypeException("keyPress");
        }
    };

    protected static final IListenerType KEY_DOWN_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            IKeyDownEventCapability keyEventCapability = (IKeyDownEventCapability) component;

            keyEventCapability.addKeyDownListener(new KeyDownScriptListener(
                    scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            throw new UnsupportedListenerTypeException("keyDown");
        }
    };

    protected static final IListenerType KEY_UP_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            IKeyUpEventCapability keyEventCapability = (IKeyUpEventCapability) component;

            keyEventCapability.addKeyUpListener(new KeyUpScriptListener(
                    scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            throw new UnsupportedListenerTypeException("keyUp");
        }
    };

    protected static final IListenerType INIT_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            IInitEventCapability initEventCapability = (IInitEventCapability) component;

            initEventCapability.addInitListener(new InitScriptListener(
                    scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            throw new UnsupportedListenerTypeException("init");
        }
    };

    protected static final IListenerType MOUSE_OUT_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            IMouseEventCapability mouseEventCapability = (IMouseEventCapability) component;

            mouseEventCapability
                    .addMouseOutListener(new MouseOutScriptListener(scriptType,
                            command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            throw new UnsupportedListenerTypeException("mouseOut");
        }
    };

    protected static final IListenerType MOUSE_OVER_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            IMouseEventCapability mouseEventCapability = (IMouseEventCapability) component;

            mouseEventCapability
                    .addMouseOverListener(new MouseOverScriptListener(
                            scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            throw new UnsupportedListenerTypeException("mouseOver");
        }
    };

    protected static final IListenerType SORT_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            ISortEventCapability sortEventCapability = (ISortEventCapability) component;

            sortEventCapability.addSortListener(new SortScriptListener(
                    scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {

            ISortEventCapability sortEventCapability = (ISortEventCapability) component;

            sortEventCapability.addSortListener(new SortActionListener(
                    expression));
        }
    };

    protected static final IListenerType RESET_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            IResetEventCapability resetEventCapability = (IResetEventCapability) component;

            resetEventCapability.addResetListener(new ResetScriptListener(
                    scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {

            IResetEventCapability sortEventCapability = (IResetEventCapability) component;

            sortEventCapability.addResetListener(new ResetActionListener(
                    expression));
        }
    };

    protected static final IListenerType MENU_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            IMenuEventCapability menuEventCapability = (IMenuEventCapability) component;

            menuEventCapability.addMenuListener(new MenuScriptListener(
                    scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            throw new UnsupportedListenerTypeException("menuListener");
        }
    };

    protected static final IListenerType USER_EVENT_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            IUserEventCapability userEventCapability = (IUserEventCapability) component;

            userEventCapability
                    .addUserEventListener(new UserEventScriptListener(
                            scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {

            IUserEventCapability userEventCapability = (IUserEventCapability) component;

            userEventCapability
                    .addUserEventListener(new UserEventActionListener(
                            expression));
        }
    };

    protected static final IListenerType SERVICE_EVENT_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            IServiceEventCapability userEventCapability = (IServiceEventCapability) component;

            userEventCapability
                    .addServiceEventListener(new ServiceEventScriptListener(
                            scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {

            IServiceEventCapability userEventCapability = (IServiceEventCapability) component;

            userEventCapability
                    .addServiceEventListener(new ServiceEventActionListener(
                            expression));
        }
    };
}