/*
 * $Id$
 * 
 * $Log$
 * Revision 1.5  2006/10/13 18:04:38  oeuillot
 * Ajout de:
 * DateEntry
 * StyledMessage
 * MessageFieldSet
 * xxxxConverter
 * Adapter
 *
 * Revision 1.4  2006/10/04 12:31:42  oeuillot
 * Stabilisation
 *
 * Revision 1.3  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.2  2006/09/05 08:57:14  oeuillot
 * Derni�res corrections pour la migration Rcfaces
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.21  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.20  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.19  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.18  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 * Revision 1.17  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.16  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.15  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.14  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.13  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.12  2004/09/24 14:01:35  oeuillot
 * *** empty log message ***
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

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.renderkit.AbstractRequestContext;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
class HtmlRequestContext extends AbstractRequestContext implements
        IHtmlRequestContext {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(HtmlRequestContext.class);

    private static final String EVENT_SERIAL = "VFC_SERIAL";

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

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractRequestContext#setFacesContext(javax.faces.context.FacesContext)
     */
    public void setFacesContext(FacesContext facesContext) {
        super.setFacesContext(facesContext);

        processContext = HtmlProcessContextImpl
                .getHtmlProcessContext(facesContext);

        parameters = facesContext.getExternalContext()
                .getRequestParameterValuesMap();

        properties = parseProperties(parameters);

        Set keys = new HashSet(parameters.size() + properties.size());
        keys.addAll(parameters.keySet());
        keys.addAll(properties.keySet());

        for (Iterator it = keys.iterator(); it.hasNext();) {
            String key = (String) it.next();

            putComponentData(key, Boolean.FALSE);
        }

        if (parameters != null) {
            eventComponentId = getStringParameter(parameters,
                    EVENT_COMPONENT_ID);

            if (eventComponentId != null) {

                putComponentData(eventComponentId, Boolean.FALSE);
            }

        } else {
            eventComponentId = null;
        }
    }

    protected Map parseProperties(Map parameters) {
        Object facesDatas = this.parameters.get(EVENT_SERIAL);
        if (facesDatas == null) {
            return Collections.EMPTY_MAP;
        }

        return parseRCFacesData(facesDatas);
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
            properties = HtmlTools.decodeParametersToMap(component, values,
                    PROPERTY_SEPARATOR, Boolean.FALSE);

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

            return emptyComponentData();
        }

        HtmlComponentData hcd = new HtmlComponentData();
        hcd.set(parameters, component, componentId, eventComponent, properties);

        return hcd;
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

    private Map parseRCFacesData(Object object) {
        String datas;
        if (object instanceof String) {
            datas = (String) object;

        } else if (object instanceof String[]) {
            String s[] = (String[]) object;

            if (s.length < 1) {
                return Collections.EMPTY_MAP;
            }

            datas = s[0];

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

        public void release() {
            properties = null;
            component = null;
            componentId = null;

            super.release();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.internal.renderkit.IComponentData#getComponentValue()
         */
        public final String getComponentParameter() {
            String key = getComponentId();
            if (key == null) {
                return null;
            }
            return getParameter(key);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.internal.renderkit.IComponentData#getComponentValues()
         */
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
}