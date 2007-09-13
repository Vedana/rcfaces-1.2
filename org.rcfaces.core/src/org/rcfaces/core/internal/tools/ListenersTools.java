/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IAdditionalInformationEventCapability;
import org.rcfaces.core.component.capability.ICheckEventCapability;
import org.rcfaces.core.component.capability.ICloseEventCapability;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.component.capability.IErrorEventCapability;
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
import org.rcfaces.core.component.capability.IValidationEventCapability;
import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import org.rcfaces.core.internal.listener.AdditionalInformationActionListener;
import org.rcfaces.core.internal.listener.AdditionalInformationScriptListener;
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
import org.rcfaces.core.internal.listener.ErrorActionListener;
import org.rcfaces.core.internal.listener.ErrorScriptListener;
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
import org.rcfaces.core.internal.listener.ValidationActionListener;
import org.rcfaces.core.internal.listener.ValidationScriptListener;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ListenersTools {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ListenersTools.class);

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public interface IListenerType {

        void addActionListener(UIComponent component, Application application,
                String expression);

        void addScriptListener(UIComponent component, String scriptType,
                String expression);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static abstract class AbstractListenerType implements IListenerType {
        private static final String REVISION = "$Revision$";
    }

    public static final IListenerType BLUR_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType FOCUS_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType LOAD_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType ERROR_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            IErrorEventCapability errorEventCapability = (IErrorEventCapability) component;

            errorEventCapability.addErrorListener(new ErrorScriptListener(
                    scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            IErrorEventCapability errorEventCapability = (IErrorEventCapability) component;

            errorEventCapability.addErrorListener(new ErrorActionListener(
                    expression));
        }
    };

    public static final IListenerType DOUBLE_CLICK_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType SELECTION_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType ADDITIONAL_INFORMATION_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            IAdditionalInformationEventCapability additionalInformationEventCapability = (IAdditionalInformationEventCapability) component;

            additionalInformationEventCapability
                    .addAdditionalInformationListener(new AdditionalInformationScriptListener(
                            scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            IAdditionalInformationEventCapability additionalInformationEventCapability = (IAdditionalInformationEventCapability) component;

            additionalInformationEventCapability
                    .addAdditionalInformationListener(new AdditionalInformationActionListener(
                            expression));
        }
    };

    public static final IListenerType CHECK_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType CLOSE_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType VALUE_CHANGE_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType SUGGESTION_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType PROPERTY_CHANGE_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType KEY_PRESS_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType KEY_DOWN_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType KEY_UP_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType INIT_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType MOUSE_OUT_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType MOUSE_OVER_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType SORT_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType RESET_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType VALIDATION_LISTENER_TYPE = new AbstractListenerType() {
        private static final String REVISION = "$Revision$";

        public void addScriptListener(UIComponent component, String scriptType,
                String command) {
            IValidationEventCapability validationEventCapability = (IValidationEventCapability) component;

            validationEventCapability
                    .addValidationListener(new ValidationScriptListener(
                            scriptType, command));
        }

        public void addActionListener(UIComponent component,
                Application application, String expression) {
            IValidationEventCapability validationEventCapability = (IValidationEventCapability) component;

            validationEventCapability
                    .addValidationListener(new ValidationActionListener(
                            expression));
        }
    };

    public static final IListenerType MENU_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType USER_EVENT_LISTENER_TYPE = new AbstractListenerType() {
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

    public static final IListenerType SERVICE_EVENT_LISTENER_TYPE = new AbstractListenerType() {
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

    public static void parseListener(FacesContext facesContext,
            UIComponent component, IListenerType listenerType, String expression) {
        parseListener(facesContext, component, listenerType, expression, false);
    }

    public static void parseListener(FacesContext facesContext,
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
        String scriptType = getScriptType(facesContext);

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

            parseFunction(chs, lastStart, offset - 1, expression, facesContext,
                    component, listenerType, scriptType);
            lastStart = offset + 1;
        }

        if (lastStart < offset) {
            parseFunction(chs, lastStart, offset - 1, expression, facesContext,
                    component, listenerType, scriptType);
        }
    }

    private static void parseFunction(char[] chs, int start, int end,
            String listeners, FacesContext facesContext, UIComponent component,
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
            if (BindingTools.isBindingExpression(s) || isForwardReference(s)) {
                // Value reference � ajouter !
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Add server listener to component '"
                            + component.getId() + "' : " + s);
                }

                listenerType.addActionListener(component, facesContext
                        .getApplication(), s);
                return;
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Add script listener (type=" + scriptType
                    + ") to component '" + component.getId() + "' : " + s);
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

    public static final String getScriptType(FacesContext facesContext) {
        return PageConfiguration.getScriptType(facesContext);
    }
}
