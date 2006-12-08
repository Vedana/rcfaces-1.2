/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.tools.ListenersTools.IListenerType;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.tag.MetaRule;
import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.Metadata;
import com.sun.facelets.tag.MetadataTarget;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TextHandler;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.ComponentHandler;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CameliaComponentHandler extends ComponentHandler {
    private static final String REVISION = "$Revision$";

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static interface IAttributeMetaData {
        Metadata processAttribute(String expression,
                IListenerType defaultListenerType, String attributeName);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class ListenerAttributeMetaData implements
            IAttributeMetaData {
        private static final String REVISION = "$Revision$";

        private final IListenerType listenerType;

        private ListenerAttributeMetaData(IListenerType listenerType) {
            this.listenerType = listenerType;
        }

        public Metadata processAttribute(final String expression,
                final IListenerType defaultListenerType, String attributeName) {
            return new Metadata() {
                public void applyMetadata(FaceletContext ctx, Object instance) {
                    ListenersTools.parseListener(ctx.getFacesContext(),
                            (UIComponent) instance, listenerType, expression,
                            listenerType == defaultListenerType);
                }
            };
        }
    }

    private static final Class[] ENUMERATION_PARAMETERS = new Class[] { String.class };

    private static IAttributeMetaData ENUMERATION_ATTRIBUTE_METADATA = new IAttributeMetaData() {
        private static final String REVISION = "$Revision$";

        public Metadata processAttribute(final String expression,
                IListenerType defaultListenerType, final String attributeName) {

            return new Metadata() {
                public void applyMetadata(FaceletContext ctx, Object instance) {
                    try {
                        String setterMethodName = "set"
                                + Character
                                        .toUpperCase(attributeName.charAt(0))
                                + attributeName.substring(1);

                        Method method = instance.getClass().getMethod(
                                setterMethodName, ENUMERATION_PARAMETERS);

                        method.invoke(instance, new Object[] { expression });

                    } catch (Throwable th) {
                        throw new FaceletException("Can not set enumeration '"
                                + attributeName + "'", th);
                    }
                }

            };
        }
    };

    private static final Map ATTRIBUTES_METADATA = new HashMap(32);
    static {
        ATTRIBUTES_METADATA.put("blurListener", new ListenerAttributeMetaData(
                ListenersTools.BLUR_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("checkListener", new ListenerAttributeMetaData(
                ListenersTools.CHECK_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("closeListener", new ListenerAttributeMetaData(
                ListenersTools.CLOSE_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("focusListener", new ListenerAttributeMetaData(
                ListenersTools.FOCUS_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("initListener", new ListenerAttributeMetaData(
                ListenersTools.INIT_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("keyDownListener",
                new ListenerAttributeMetaData(
                        ListenersTools.KEY_DOWN_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("keyPressListener",
                new ListenerAttributeMetaData(
                        ListenersTools.KEY_PRESS_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("keyUpListener", new ListenerAttributeMetaData(
                ListenersTools.KEY_UP_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("loadListener", new ListenerAttributeMetaData(
                ListenersTools.LOAD_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("menuListener", new ListenerAttributeMetaData(
                ListenersTools.MENU_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("mouseOutListener",
                new ListenerAttributeMetaData(
                        ListenersTools.MOUSE_OUT_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("mouseOverListener",
                new ListenerAttributeMetaData(
                        ListenersTools.MOUSE_OVER_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("propertyChangeListener",
                new ListenerAttributeMetaData(
                        ListenersTools.PROPERTY_CHANGE_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("resetListener", new ListenerAttributeMetaData(
                ListenersTools.RESET_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("selectionListener",
                new ListenerAttributeMetaData(
                        ListenersTools.SELECTION_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("serviceListener",
                new ListenerAttributeMetaData(
                        ListenersTools.SERVICE_EVENT_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("sortListener", new ListenerAttributeMetaData(
                ListenersTools.SORT_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("suggestionListener",
                new ListenerAttributeMetaData(
                        ListenersTools.SUGGESTION_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("userEventListener",
                new ListenerAttributeMetaData(
                        ListenersTools.USER_EVENT_LISTENER_TYPE));
        ATTRIBUTES_METADATA.put("valueChangeListener",
                new ListenerAttributeMetaData(
                        ListenersTools.VALUE_CHANGE_LISTENER_TYPE));

        ATTRIBUTES_METADATA.put("action", new IAttributeMetaData() {
            private static final String REVISION = "$Revision$";

            public Metadata processAttribute(final String expression,
                    final IListenerType defaultListenerType,
                    String attributeName) {
                return new Metadata() {
                    public void applyMetadata(FaceletContext ctx,
                            Object instance) {
                        ListenersTools.parseAction(ctx.getFacesContext(),
                                (UIComponent) instance, defaultListenerType,
                                expression);
                    }
                };
            }
        });

        ATTRIBUTES_METADATA.put("hiddenMode", ENUMERATION_ATTRIBUTE_METADATA);
        ATTRIBUTES_METADATA.put("asyncRenderMode",
                ENUMERATION_ATTRIBUTE_METADATA);
        ATTRIBUTES_METADATA.put("checkCardinality",
                ENUMERATION_ATTRIBUTE_METADATA);
        ATTRIBUTES_METADATA.put("selectionCardinality",
                ENUMERATION_ATTRIBUTE_METADATA);
        ATTRIBUTES_METADATA.put("textPosition", ENUMERATION_ATTRIBUTE_METADATA);
        ATTRIBUTES_METADATA.put("order", ENUMERATION_ATTRIBUTE_METADATA);
        ATTRIBUTES_METADATA.put("mode", ENUMERATION_ATTRIBUTE_METADATA);
        ATTRIBUTES_METADATA.put("attributesLocale",
                ENUMERATION_ATTRIBUTE_METADATA);
        ATTRIBUTES_METADATA.put("clientDatesStrategy",
                ENUMERATION_ATTRIBUTE_METADATA);
        ATTRIBUTES_METADATA.put("numberFormatType",
                ENUMERATION_ATTRIBUTE_METADATA);
        ATTRIBUTES_METADATA.put("disabledWeekDays",
                ENUMERATION_ATTRIBUTE_METADATA);

        ATTRIBUTES_METADATA.put("date", ENUMERATION_ATTRIBUTE_METADATA);
        ATTRIBUTES_METADATA.put("maxDate", ENUMERATION_ATTRIBUTE_METADATA);
        ATTRIBUTES_METADATA.put("minDate", ENUMERATION_ATTRIBUTE_METADATA);
        ATTRIBUTES_METADATA.put("defaultDate", ENUMERATION_ATTRIBUTE_METADATA);

        ATTRIBUTES_METADATA.put("time", ENUMERATION_ATTRIBUTE_METADATA);
        ATTRIBUTES_METADATA.put("maxTime", ENUMERATION_ATTRIBUTE_METADATA);
        ATTRIBUTES_METADATA.put("minTime", ENUMERATION_ATTRIBUTE_METADATA);
        ATTRIBUTES_METADATA.put("defaultTime", ENUMERATION_ATTRIBUTE_METADATA);
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

    protected final void setTextBody(FaceletContext ctx, UIComponent c) {
        StringAppender content = null;
        Iterator iter = findNextByType(TextHandler.class);
        while (iter.hasNext()) {
            TextHandler text = (TextHandler) iter.next();

            if (content == null) {
                content = new StringAppender();
            }
            content.append(text.getText(ctx));
        }

        if (content != null && content.length() > 0) {
            c.getAttributes().put("text", content.toString());
        }
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
            if (expression == null) {
                return null;
            }

            IAttributeMetaData attributeMetaData = (IAttributeMetaData) ATTRIBUTES_METADATA
                    .get(name);

            if (attributeMetaData == null) {
                return null;
            }

            return attributeMetaData.processAttribute(expression,
                    defaultListenerType, name);
        }
    }

}
