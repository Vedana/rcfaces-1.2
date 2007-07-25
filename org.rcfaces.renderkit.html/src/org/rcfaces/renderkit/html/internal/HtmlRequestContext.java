/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IUnlockedClientAttributesCapability;
import org.rcfaces.core.internal.renderkit.AbstractRequestContext;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlRequestContext extends AbstractRequestContext implements
        IHtmlRequestContext {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(HtmlRequestContext.class);

    public static final String EVENT_SERIAL = "VFC_SERIAL";

    private static final String EVENT_NAME = "VFC_EVENT";

    private static final String EVENT_COMPONENT_ID = "VFC_COMPONENT";

    private static final String EVENT_VALUE = "VFC_VALUE";

    private static final String EVENT_ITEM = "VFC_ITEM";

    private static final String EVENT_DETAIL = "VFC_DETAIL";

    private static final String REQUEST_CONTEXT = "camelia.request.context";

    private static final char PROPERTY_SEPARATOR = ',';

    private Map parameters;

    private Map properties;

    private String eventComponentId;

    private IHtmlProcessContext processContext;

    public void setFacesContext(FacesContext facesContext) {
        super.setFacesContext(facesContext);

        processContext = HtmlProcessContextImpl
                .getHtmlProcessContext(facesContext);

        parameters = facesContext.getExternalContext()
                .getRequestParameterValuesMap();

        properties = parseProperties(parameters);
        if (LOG.isTraceEnabled()) {
            LOG.trace("Parsed camelia properties => " + properties);
        }

        Set keys = new HashSet(parameters.size() + properties.size());
        keys.addAll(parameters.keySet());
        keys.addAll(properties.keySet());

        for (Iterator it = keys.iterator(); it.hasNext();) {
            String key = (String) it.next();

            putComponentData(key, Boolean.FALSE);
        }

        eventComponentId = getStringParameter(parameters, EVENT_COMPONENT_ID);
        if (eventComponentId != null) {
            putComponentData(eventComponentId, Boolean.FALSE);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Event component id detected: " + eventComponentId);
            }

        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("No event component id detected");
            }
        }
    }

    protected Map parseProperties(Map parameters) {
        Object facesDatas = parameters.get(EVENT_SERIAL);
        if (facesDatas == null) {
            return Collections.EMPTY_MAP;
        }

        return parseCameliaData(facesDatas);
    }

    public String getComponentId(FacesContext facesContext,
            UIComponent component) {
        if (processContext.isFlatIdentifierEnabled() == false) {
            return component.getClientId(facesContext);
        }

        String id = component.getId();

        if (id == null || id.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
            return component.getClientId(facesContext);
        }

        return id;
    }

    protected IComponentData getComponentData(UIComponent component,
            String componentId, Object data) {

        Map properties = Collections.EMPTY_MAP;
        Map parameters = this.parameters;

        String values = (String) this.properties.get(componentId);
        if (values != null) {
            // Il faut transformer la valeur serialisée en Map
            properties = HtmlTools.decodeParametersToMap(getProcessContext(),
                    component, values, PROPERTY_SEPARATOR, "");

            if (LOG.isDebugEnabled()) {
                LOG.debug("Decode component data of '" + componentId + "' to "
                        + properties);
            }
        }

        Object parameterValues = null;
        if (parameters != null) {
            parameterValues = parameters.get(componentId);

            if (parameterValues != null) {
                if (parameterValues.getClass().isArray()) {
                    if (Array.getLength(parameterValues) == 0) {
                        parameterValues = null;
                    }
                }
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Parameters values of component '" + componentId
                        + "'=" + parameterValues);
            }
        }

        boolean eventComponent = false;

        if (eventComponentId != null && eventComponentId.equals(componentId)) {
            eventComponent = true;

            if (LOG.isDebugEnabled()) {
                LOG
                        .debug("Event detected for component '" + componentId
                                + "'.");
            }

        } else if ((properties == null || properties.isEmpty())
                && (parameterValues == null)) {

            if (LOG.isDebugEnabled()) {
                LOG.debug("No properites, no parameters for component '"
                        + componentId + "'.");
            }

            return emptyComponentData();
        }

        if (isLockedClientAttributes()) {
            if (component instanceof IUnlockedClientAttributesCapability) {
                properties = filterProperties(
                        (IUnlockedClientAttributesCapability) component,
                        properties);

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Filtred properties => '" + properties + "'.");
                }

            } else {
                properties = Collections.EMPTY_MAP;
            }
        }

        HtmlComponentData hcd = new HtmlComponentData();
        hcd.set(parameters, component, componentId, eventComponent, properties);

        return hcd;
    }

    private Map filterProperties(IUnlockedClientAttributesCapability component,
            Map properties) {
        String unlockedAttributes = component.getUnlockedClientAttributeNames();
        if (unlockedAttributes == null) {
            return Collections.EMPTY_MAP;
        }
        unlockedAttributes = unlockedAttributes.trim();
        if ("*".equals(unlockedAttributes)) {
            return properties;
        }

        Map ret = null;

        StringTokenizer st = new StringTokenizer(unlockedAttributes, ", ");
        for (; st.hasMoreTokens();) {
            String attributeName = st.nextToken();

            Object value = properties.get(attributeName);
            if (value == null) {
                continue;
            }

            if (ret == null) {
                ret = new HashMap(properties.size());
            }
            ret.put(attributeName, value);
        }

        if (ret == null) {
            return Collections.EMPTY_MAP;
        }

        return ret;
    }

    private static final String getStringParameter(Map parameters, String name) {
        Object value = parameters.get(name);
        if (value == null) {
            return null;
        }

        if (value instanceof String[]) {
            String array[] = (String[]) value;

            if (array.length < 1) {
                return null;
            }

            return array[0];
        }

        return value.toString();
    }

    private Map parseCameliaData(Object object) {
        String datas;
        if (object instanceof String) {
            datas = (String) object;

        } else if (object.getClass().isArray()) {
            if (Array.getLength(object) == 0) {
                return Collections.EMPTY_MAP;
            }

            return parseCameliaData(Array.get(object, 0));

        } else {
            return Collections.EMPTY_MAP;
        }

        char cs[] = datas.toCharArray();
        Map properties = new HashMap((cs.length / 16) + 1);

        for (int i = 0; i < cs.length;) {
            int nameStart = i;
            int nameEnd = 0;
            char c;

            for (; i < cs.length; i++) {
                c = cs[i];

                if (c != '=') {
                    continue;
                }

                nameEnd = i;
                break;
            }
            if (i == cs.length) {
                throwFormatException("EOF", i, datas);
            }

            i++;
            if (i == cs.length) {
                throwFormatException("EOF", i, datas);
            }

            if (cs[i++] != '[') {
                throwFormatException("Bad Char ", i, datas);
            }

            int valueStart = i;
            int valueEnd = 0;
            for (; i < cs.length; i++) {
                c = cs[i];

                if (c != ']') {
                    continue;
                }

                valueEnd = i;
                break;

            }

            if (i == cs.length) {
                throwFormatException("EOF", i, datas);
            }

            String componentId = datas.substring(nameStart, nameEnd);

            properties.put(componentId, datas.substring(valueStart, valueEnd));

            i++;
            if (i == cs.length) {
                break;
            }

            if (cs[i] != ',') {
                throwFormatException("Bad Char ", i, datas);
            }
            i++;
        }

        if (properties.size() < 1) {
            return Collections.EMPTY_MAP;
        }

        return properties;
    }

    private static void throwFormatException(String message, int i, String datas) {
        throw new FacesException("Bad format of rcfaces serialized datas ! ("
                + message + ": pos=" + i + " data='" + datas + "')");
    }

    protected IComponentData createEmptyComponentData() {
        AbstractHtmlComponentData componentData = new AbstractHtmlComponentData() {
            private static final String REVISION = "$Revision$";

            public Object getProperty(String name) {
                return null;
            }

            public String getComponentParameter() {
                return null;
            }

            public String[] getComponentParameters() {
                return null;
            }

            public boolean isEventComponent() {
                return false;
            }

            public boolean containsKey(String name) {
                return false;
            }

        };

        componentData.set(parameters);

        return componentData;
    }

    public IProcessContext getProcessContext() {
        return processContext;
    }

    public IHtmlProcessContext getHtmlProcessContext() {
        return processContext;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static abstract class AbstractHtmlComponentData extends
            AbstractComponentData {
        private static final String REVISION = "$Revision$";

        private Map parameters;

        private boolean detailInitialized;

        private int detail;

        public final void set(Map parameters) {
            this.parameters = parameters;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.internal.renderkit.IComponentData#getParameter(java.lang.String)
         */
        public final String getParameter(String parameterName) {
            return getStringParameter(parameters, parameterName);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.internal.renderkit.IComponentData#getParameters(java.lang.String)
         */
        public final String[] getParameters(String parameterName) {
            Object value = parameters.get(parameterName);
            if (value == null) {
                return null;
            }

            if (value instanceof String[]) {
                return (String[]) value;
            }

            return new String[] { value.toString() };
        }

        public void release() {
            parameters = null;
        }

        public final String getEventName() {
            return getParameter(EVENT_NAME);
        }

        public final String getEventValue() {
            return getParameter(EVENT_VALUE);
        }

        public final String getEventItem() {
            return getParameter(EVENT_ITEM);
        }

        public final int getEventDetail() {
            if (detailInitialized == false) {
                detailInitialized = true;

                String detailString = getParameter(EVENT_DETAIL);
                if (detailString != null && detailString.length() > 0) {
                    try {
                        detail = Integer.parseInt(detailString);

                    } catch (NumberFormatException ex) {
                        throw new FacesException("Invalid event detail '"
                                + detail + "', it is not an integer !", ex);
                    }
                }
            }

            return detail;
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static class HtmlComponentData extends AbstractHtmlComponentData {
        private static final String REVISION = "$Revision$";

        private String componentId;

        private UIComponent component;

        private Map properties;

        private boolean eventComponent;

        public void set(Map parameters, UIComponent component,
                String componentId, boolean eventComponent, Map properties) {
            super.set(parameters);

            this.component = component;
            this.componentId = componentId;
            this.properties = properties;
            this.eventComponent = eventComponent;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.internal.renderkit.IComponentData#getProperty(java.lang.String)
         */
        public Object getProperty(String name) {
            return properties.get(name);
        }

        public boolean containsKey(String name) {
            return false;
        }

        /*
         * public void release() { properties = null; component = null;
         * componentId = null;
         * 
         * super.release(); }
         */

        public final String getComponentParameter() {
            String key = getComponentId();
            if (key == null) {
                return null;
            }
            return getParameter(key);
        }

        public final String[] getComponentParameters() {
            String key = getComponentId();
            if (key == null) {
                return null;
            }
            return getParameters(key);
        }

        protected String getComponentId() {
            return componentId;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.internal.renderkit.IComponentData#isEventComponent()
         */
        public boolean isEventComponent() {
            return eventComponent;
        }
    }

    static IRequestContext getRequestContext(FacesContext context) {
        Map requestMap = context.getExternalContext().getRequestMap();

        IRequestContext requestContext = (IRequestContext) requestMap
                .get(REQUEST_CONTEXT);
        if (requestContext != null) {
            return requestContext;
        }

        requestContext = createRequestContext(context);
        requestMap.put(REQUEST_CONTEXT, requestContext);

        return requestContext;
    }

    static IRequestContext createRequestContext(FacesContext context) {
        HtmlRequestContext hrc = new HtmlRequestContext();
        hrc.setFacesContext(context);

        return hrc;
    }

    public String getEventComponentId() {
        return eventComponentId;
    }
}
