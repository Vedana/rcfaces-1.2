/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.taglib.Listeners;
import org.rcfaces.core.internal.taglib.Listeners.IListenerType;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.MetaRule;
import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.Metadata;
import com.sun.facelets.tag.MetadataTarget;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.ComponentHandler;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CameliaComponentHandler extends ComponentHandler {
    private static final String REVISION = "$Revision$";

    private static final IListenerType FAKE_ACTION_LISTENER_TYPE = new IListenerType() {
        private static final String REVISION = "$Revision$";

        public void addActionListener(UIComponent component,
                Application application, String expression) {
        }

        public void addScriptListener(UIComponent component, String scriptType,
                String expression) {
        }

    };

    private static final Map LISTENERS_METADATA = new HashMap(32);
    {
        LISTENERS_METADATA.put("blurListener", Listeners.BLUR_LISTENER_TYPE);
        LISTENERS_METADATA.put("checkListener", Listeners.CHECK_LISTENER_TYPE);
        LISTENERS_METADATA.put("closeListener", Listeners.CLOSE_LISTENER_TYPE);
        LISTENERS_METADATA.put("focusListener", Listeners.FOCUS_LISTENER_TYPE);
        LISTENERS_METADATA.put("initListener", Listeners.INIT_LISTENER_TYPE);
        LISTENERS_METADATA.put("keyDownListener",
                Listeners.KEY_DOWN_LISTENER_TYPE);
        LISTENERS_METADATA.put("keyPressListener",
                Listeners.KEY_PRESS_LISTENER_TYPE);
        LISTENERS_METADATA.put("keyUpListener", Listeners.KEY_UP_LISTENER_TYPE);
        LISTENERS_METADATA.put("loadListener", Listeners.LOAD_LISTENER_TYPE);
        LISTENERS_METADATA.put("menuListener", Listeners.MENU_LISTENER_TYPE);
        LISTENERS_METADATA.put("mouseOutListener",
                Listeners.MOUSE_OUT_LISTENER_TYPE);
        LISTENERS_METADATA.put("mouseOverListener",
                Listeners.MOUSE_OVER_LISTENER_TYPE);
        LISTENERS_METADATA.put("propertyChangeListener",
                Listeners.PROPERTY_CHANGE_LISTENER_TYPE);
        LISTENERS_METADATA.put("resetListener", Listeners.RESET_LISTENER_TYPE);
        LISTENERS_METADATA.put("selectionListener",
                Listeners.SELECTION_LISTENER_TYPE);
        LISTENERS_METADATA.put("serviceListener",
                Listeners.SERVICE_EVENT_LISTENER_TYPE);
        LISTENERS_METADATA.put("sortListener", Listeners.SORT_LISTENER_TYPE);
        LISTENERS_METADATA.put("suggestionListener",
                Listeners.SUGGESTION_LISTENER_TYPE);
        LISTENERS_METADATA.put("userEventListener",
                Listeners.USER_EVENT_LISTENER_TYPE);
        LISTENERS_METADATA.put("valueChangeListener",
                Listeners.VALUE_CHANGE_LISTENER_TYPE);

        LISTENERS_METADATA.put("action", FAKE_ACTION_LISTENER_TYPE);
    }

    public CameliaComponentHandler(ComponentConfig config) {
        super(config);
    }

    protected MetaRuleset createMetaRuleset(Class type) {
        MetaRuleset metaRuleset = super.createMetaRuleset(type);

        metaRuleset.addRule(new ComponentMetaRule(getDefaultListenerType()));

        return metaRuleset;
    }

    protected IListenerType getDefaultListenerType() {
        return null;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class ComponentMetaRule extends MetaRule {
        private static final String REVISION = "$Revision$";

        private final IListenerType defaultListenerType;

        protected ComponentMetaRule(IListenerType defaultListenerType) {
            this.defaultListenerType = defaultListenerType;
        }

        public Metadata applyRule(String name, TagAttribute attribute,
                MetadataTarget meta) {

            final String expression = attribute.getValue();
            if (expression == null || expression.trim().length() < 1) {
                return null;
            }

            final IListenerType listenerType = (IListenerType) LISTENERS_METADATA
                    .get(name);

            if (listenerType == null) {
                return null;
            }

            if (listenerType == FAKE_ACTION_LISTENER_TYPE) {
                return new Metadata() {
                    public void applyMetadata(FaceletContext ctx,
                            Object instance) {
                        Listeners.parseAction(ctx.getFacesContext(),
                                (UIComponent) instance, defaultListenerType,
                                expression);
                    }
                };
            }

            return new Metadata() {
                public void applyMetadata(FaceletContext ctx, Object instance) {
                    Listeners.parseListener(ctx.getFacesContext(),
                            (UIComponent) instance, listenerType, expression,
                            listenerType == defaultListenerType);
                }
            };
        }
    }

}
