/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesListener;

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
import org.rcfaces.core.component.capability.ISortEventCapability;
import org.rcfaces.core.component.capability.ISuggestionEventCapability;
import org.rcfaces.core.component.capability.IUserEventCapability;
import org.rcfaces.core.component.capability.IValidationEventCapability;
import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public final class ListenerTools {

    private static final String REVISION = "$Revision$";

    /**
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static final INameSpace JAVASCRIPT_NAME_SPACE = new INameSpace() {
        private static final String REVISION = "$Revision$";

        public String getSelectionEventName() {
            return JavaScriptClasses.EVENT_SELECTION_CST;
        }

        public String getCheckEventName() {
            return JavaScriptClasses.EVENT_CHECK_CST;
        }

        public String getErrorEventName() {
            return JavaScriptClasses.EVENT_ERROR_CST;
        }

        public String getMenuEventName() {
            return JavaScriptClasses.EVENT_MENU_CST;
        }

        public String getValueChangeEventName() {
            return JavaScriptClasses.EVENT_VALUE_CHANGE_CST;
        }

        public String getDoubleClickEventName() {
            return JavaScriptClasses.EVENT_DBLCLICK_CST;
        }

        public String getBlurEventName() {
            return JavaScriptClasses.EVENT_BLUR_CST;
        }

        public String getFocusEventName() {
            return JavaScriptClasses.EVENT_FOCUS_CST;
        }

        public String getMouseOutEventName() {
            return JavaScriptClasses.EVENT_MOUSEOUT_CST;
        }

        public String getMouseOverEventName() {
            return JavaScriptClasses.EVENT_MOUSEOVER_CST;
        }

        public String getKeyDownEventName() {
            return JavaScriptClasses.EVENT_KEYDOWN_CST;
        }

        public String getKeyUpEventName() {
            return JavaScriptClasses.EVENT_KEYUP_CST;
        }

        public String getKeyPressEventName() {
            return JavaScriptClasses.EVENT_KEYPRESS_CST;
        }

        public String getSortEventName() {
            return JavaScriptClasses.EVENT_SORT_CST;
        }

        public String getSuggestionEventName() {
            return JavaScriptClasses.EVENT_SUGGESTION_CST;
        }

        public String getPropertyChangeEventName() {
            return JavaScriptClasses.EVENT_PROPERTY_CHANGE_CST;
        }

        public String getResetEventName() {
            return JavaScriptClasses.EVENT_RESET_CST;
        }

        public String getUserEventName() {
            return JavaScriptClasses.EVENT_USER_CST;
        }

        public String getInitEventName() {
            return JavaScriptClasses.EVENT_INIT_CST;
        }

        public String getLoadEventName() {
            return JavaScriptClasses.EVENT_LOAD_CST;
        }

        public String getValidationEventName() {
            return JavaScriptClasses.EVENT_VALIDATION_CST;
        }

        public String getCloseEventName() {
            return JavaScriptClasses.EVENT_CLOSE_CST;
        }

    };

    /**
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static final INameSpace ATTRIBUTE_NAME_SPACE = new INameSpace() {
        private static final String REVISION = "$Revision$";

        public String getSelectionEventName() {
            return JavaScriptClasses.EVENT_SELECTION_ATTRIBUTE;
        }

        public String getCheckEventName() {
            return JavaScriptClasses.EVENT_CHECK_ATTRIBUTE;
        }

        public String getErrorEventName() {
            return JavaScriptClasses.EVENT_ERROR_ATTRIBUTE;
        }

        public String getMenuEventName() {
            return JavaScriptClasses.EVENT_MENU_ATTRIBUTE;
        }

        public String getValueChangeEventName() {
            return JavaScriptClasses.EVENT_VALUE_CHANGE_ATTRIBUTE;
        }

        public String getDoubleClickEventName() {
            return JavaScriptClasses.EVENT_DBLCLICK_ATTRIBUTE;
        }

        public String getBlurEventName() {
            return JavaScriptClasses.EVENT_BLUR_ATTRIBUTE;
        }

        public String getFocusEventName() {
            return JavaScriptClasses.EVENT_FOCUS_ATTRIBUTE;
        }

        public String getMouseOutEventName() {
            return JavaScriptClasses.EVENT_MOUSEOUT_ATTRIBUTE;
        }

        public String getMouseOverEventName() {
            return JavaScriptClasses.EVENT_MOUSEOVER_ATTRIBUTE;
        }

        public String getKeyDownEventName() {
            return JavaScriptClasses.EVENT_KEYDOWN_ATTRIBUTE;
        }

        public String getKeyUpEventName() {
            return JavaScriptClasses.EVENT_KEYUP_ATTRIBUTE;
        }

        public String getKeyPressEventName() {
            return JavaScriptClasses.EVENT_KEYPRESS_ATTRIBUTE;
        }

        public String getSortEventName() {
            return JavaScriptClasses.EVENT_SORT_ATTRIBUTE;
        }

        public String getSuggestionEventName() {
            return JavaScriptClasses.EVENT_SUGGESTION_ATTRIBUTE;
        }

        public String getPropertyChangeEventName() {
            return JavaScriptClasses.EVENT_PROPERTY_CHANGE_ATTRIBUTE;
        }

        public String getResetEventName() {
            return JavaScriptClasses.EVENT_RESET_ATTRIBUTE;
        }

        public String getUserEventName() {
            return JavaScriptClasses.EVENT_USER_ATTRIBUTE;
        }

        public String getInitEventName() {
            return JavaScriptClasses.EVENT_INIT_ATTRIBUTE;
        }

        public String getLoadEventName() {
            return JavaScriptClasses.EVENT_LOAD_ATTRIBUTE;
        }

        public String getValidationEventName() {
            return JavaScriptClasses.EVENT_VALIDATION_ATTRIBUTE;
        }

        public String getCloseEventName() {
            return JavaScriptClasses.EVENT_CLOSE_ATTRIBUTE;
        }

    };

    public static final Map getListenersByType(INameSpace nameSpace,
            UIComponent component) {
        Map map = null;

        if (component instanceof ISelectionEventCapability) {
            ISelectionEventCapability selectEventCapability = (ISelectionEventCapability) component;

            FacesListener fls[] = selectEventCapability
                    .listSelectionListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getSelectionEventName(), fls);
            }
        }

        if (component instanceof ICheckEventCapability) {
            ICheckEventCapability checkEventCapability = (ICheckEventCapability) component;

            FacesListener fls[] = checkEventCapability.listCheckListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getCheckEventName(), fls);
            }
        }

        if (component instanceof IValueChangeEventCapability) {
            IValueChangeEventCapability changeEventCapability = (IValueChangeEventCapability) component;

            FacesListener fls[] = changeEventCapability
                    .listValueChangeListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getValueChangeEventName(), fls);
            }
        }

        if (component instanceof IDoubleClickEventCapability) {
            IDoubleClickEventCapability doubleClickEventCapability = (IDoubleClickEventCapability) component;

            FacesListener fls[] = doubleClickEventCapability
                    .listDoubleClickListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getDoubleClickEventName(), fls);
            }
        }

        if (component instanceof IFocusBlurEventCapability) {
            IFocusBlurEventCapability focusBlurEventCapability = (IFocusBlurEventCapability) component;

            FacesListener fls[] = focusBlurEventCapability.listBlurListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getBlurEventName(), fls);
            }

            fls = focusBlurEventCapability.listFocusListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getFocusEventName(), fls);
            }
        }

        if (component instanceof IMouseEventCapability) {
            IMouseEventCapability mouseEventCapability = (IMouseEventCapability) component;

            FacesListener fls[] = mouseEventCapability.listMouseOutListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getMouseOutEventName(), fls);
            }

            fls = mouseEventCapability.listMouseOverListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getMouseOverEventName(), fls);
            }
        }

        if (component instanceof IKeyDownEventCapability) {
            IKeyDownEventCapability keyEventCapability = (IKeyDownEventCapability) component;

            FacesListener fls[] = keyEventCapability.listKeyDownListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getKeyDownEventName(), fls);
            }
        }

        if (component instanceof IKeyPressEventCapability) {
            IKeyPressEventCapability keyEventCapability = (IKeyPressEventCapability) component;
            FacesListener fls[] = keyEventCapability.listKeyPressListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getKeyPressEventName(), fls);
            }
        }

        if (component instanceof IKeyUpEventCapability) {
            IKeyUpEventCapability keyEventCapability = (IKeyUpEventCapability) component;
            FacesListener fls[] = keyEventCapability.listKeyUpListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getKeyUpEventName(), fls);
            }
        }

        if (component instanceof IResetEventCapability) {
            IResetEventCapability resetEventCapability = (IResetEventCapability) component;

            FacesListener fls[] = resetEventCapability.listResetListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getResetEventName(), fls);
            }
        }

        if (component instanceof IMenuEventCapability) {
            IMenuEventCapability menuEventCapability = (IMenuEventCapability) component;

            FacesListener fls[] = menuEventCapability.listMenuListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getMenuEventName(), fls);
            }
        }

        if (component instanceof IUserEventCapability) {
            IUserEventCapability userEventCapability = (IUserEventCapability) component;

            FacesListener fls[] = userEventCapability.listUserEventListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getUserEventName(), fls);
            }
        }

        if (component instanceof IInitEventCapability) {
            IInitEventCapability initEventCapability = (IInitEventCapability) component;

            FacesListener fls[] = initEventCapability.listInitListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getInitEventName(), fls);
            }
        }

        if (component instanceof IPropertyChangeEventCapability) {
            IPropertyChangeEventCapability propertyChangeEventCapability = (IPropertyChangeEventCapability) component;

            FacesListener fls[] = propertyChangeEventCapability
                    .listPropertyChangeListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getPropertyChangeEventName(), fls);
            }
        }

        if (component instanceof ISuggestionEventCapability) {
            ISuggestionEventCapability suggestionEventCapability = (ISuggestionEventCapability) component;

            FacesListener fls[] = suggestionEventCapability
                    .listSuggestionListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getSuggestionEventName(), fls);
            }
        }

        if (component instanceof ILoadEventCapability) {
            ILoadEventCapability loadEventCapability = (ILoadEventCapability) component;

            FacesListener fls[] = loadEventCapability.listLoadListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getLoadEventName(), fls);
            }
        }

        if (component instanceof IErrorEventCapability) {
            IErrorEventCapability errorListenerCapability = (IErrorEventCapability) component;

            FacesListener fls[] = errorListenerCapability.listErrorListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getErrorEventName(), fls);
            }
        }

        if (component instanceof ISortEventCapability) {
            ISortEventCapability sortListenerCapability = (ISortEventCapability) component;

            FacesListener fls[] = sortListenerCapability.listSortListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getSortEventName(), fls);
            }
        }

        if (component instanceof ICloseEventCapability) {
            ICloseEventCapability closeListenerCapability = (ICloseEventCapability) component;

            FacesListener fls[] = closeListenerCapability.listCloseListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getCloseEventName(), fls);
            }
        }

        if (component instanceof IValidationEventCapability) {
            IValidationEventCapability clientValidationListenerCapability = (IValidationEventCapability) component;

            FacesListener fls[] = clientValidationListenerCapability
                    .listValidationListeners();
            if (fls.length > 0) {
                if (map == null) {
                    map = new HashMap(4);
                }

                map.put(nameSpace.getValidationEventName(), fls);
            }
        }

        if (map == null) {
            return Collections.EMPTY_MAP;
        }
        return map;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public interface INameSpace {
        String getBlurEventName();

        String getValidationEventName();

        String getCheckEventName();

        String getDoubleClickEventName();

        String getErrorEventName();

        String getFocusEventName();

        String getInitEventName();

        String getKeyDownEventName();

        String getKeyPressEventName();

        String getKeyUpEventName();

        String getLoadEventName();

        String getMenuEventName();

        String getMouseOutEventName();

        String getMouseOverEventName();

        String getSuggestionEventName();

        String getPropertyChangeEventName();

        String getResetEventName();

        String getSelectionEventName();

        String getSortEventName();

        String getUserEventName();

        String getValueChangeEventName();

        String getCloseEventName();
    }

}
