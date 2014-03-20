package org.rcfaces.core.component;

import java.util.LinkedList;
import javax.faces.event.FacesEvent;
import org.rcfaces.core.internal.component.Properties;
import java.util.Map;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import java.util.List;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import javax.faces.context.FacesContext;

public class EventsCollectorComponent extends CameliaBaseComponent {

	private static final Log LOG = LogFactory.getLog(EventsCollectorComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.eventsCollector";

	 private static final String EVENTS_LIST_PROPERTY_NAME = "org.rcfaces.EVENTS_LIST";

	public EventsCollectorComponent() {
		setRendererType(null);
	}

	public EventsCollectorComponent(String componentId) {
		this();
		setId(componentId);
	}

	@Override
	public void queueEvent(FacesEvent event) {


		

        if (LOG.isDebugEnabled()) {
            LOG.debug("Queue event '" + event + "'");
        }

        List<FacesEvent> events = getEventsList(null);

        events.add(event);

        super.queueEvent(event);
  
    
	}

	public static List<FacesEvent> getEventsList(FacesContext facesContext) {


		

        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        Map<String, Object> requestMap = facesContext.getExternalContext()
                .getRequestMap();

        List<FacesEvent> list = (List<FacesEvent>) requestMap
                .get(EVENTS_LIST_PROPERTY_NAME);
        if (list == null) {
            list = new LinkedList<FacesEvent>();
            requestMap.put(EVENTS_LIST_PROPERTY_NAME, list);
        }

        return list;

    
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
