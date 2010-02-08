/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.event.BlurEvent;
import org.rcfaces.core.event.CheckEvent;
import org.rcfaces.core.event.ClientValueChangeEvent;
import org.rcfaces.core.event.CloseEvent;
import org.rcfaces.core.event.DoubleClickEvent;
import org.rcfaces.core.event.FocusEvent;
import org.rcfaces.core.event.KeyDownEvent;
import org.rcfaces.core.event.KeyPressEvent;
import org.rcfaces.core.event.KeyUpEvent;
import org.rcfaces.core.event.LoadEvent;
import org.rcfaces.core.event.MouseOutEvent;
import org.rcfaces.core.event.MouseOverEvent;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.event.ResetEvent;
import org.rcfaces.core.event.SelectionEvent;
import org.rcfaces.core.event.SortEvent;
import org.rcfaces.core.event.SuggestionEvent;
import org.rcfaces.core.event.UserEvent;
import org.rcfaces.core.internal.renderkit.IEventData;
import org.rcfaces.core.internal.renderkit.IRequestContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class EventDecoders {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(EventDecoders.class);

    public interface IEventDecoder {
        void decodeEvent(IRequestContext requestContext, UIComponent component,
                IEventData eventData, IEventObjectDecoder eventObjectDecoder);
    }

    public interface IEventObjectDecoder {
        Object decodeEventObject(IRequestContext requestContext,
                UIComponent component, IEventData eventData);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static abstract class AbstractEventDecoder implements IEventDecoder {
        private static final String REVISION = "$Revision$";

        protected final void queueEvent(UIComponent component, FacesEvent event) {
            PhaseId phaseId = PhaseId.INVOKE_APPLICATION;

            event.setPhaseId(phaseId);
            component.queueEvent(event);
        }

    }

    private static final Map EVENT_DECODERS;

    static {
        EVENT_DECODERS = new HashMap(32);

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_SELECTION,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(IRequestContext requestContext,
                            UIComponent component, IEventData eventData,
                            IEventObjectDecoder eventObjectDecoder) {

                        Object eventObject = null;

                        if (eventObjectDecoder != null) {
                            eventObject = eventObjectDecoder.decodeEventObject(
                                    requestContext, component, eventData);
                        }

                        FacesEvent event = new SelectionEvent(component,
                                eventData.getEventValue(), eventObject,
                                eventData.getEventItem(), eventData
                                        .getEventDetail());
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_VALUE_CHANGE,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(IRequestContext requestContext,
                            UIComponent component, IEventData eventData,
                            IEventObjectDecoder eventObjectDecoder) {
                        FacesEvent event = new ClientValueChangeEvent(
                                component, null, eventData.getEventValue());

                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_DBLCLICK,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(IRequestContext requestContext,
                            UIComponent component, IEventData eventData,
                            IEventObjectDecoder eventObjectDecoder) {

                        Object eventObject = null;

                        if (eventObjectDecoder != null) {
                            eventObject = eventObjectDecoder.decodeEventObject(
                                    requestContext, component, eventData);
                        }

                        FacesEvent event = new DoubleClickEvent(component,
                                eventData.getEventValue(), eventObject,
                                eventData.getEventItem(), eventData
                                        .getEventDetail());
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_CHECK,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(IRequestContext requestContext,
                            UIComponent component, IEventData eventData,
                            IEventObjectDecoder eventObjectDecoder) {
                        FacesEvent event = new CheckEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_BLUR,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(IRequestContext requestContext,
                            UIComponent component, IEventData eventData,
                            IEventObjectDecoder eventObjectDecoder) {
                        FacesEvent event = new BlurEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_FOCUS,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(IRequestContext requestContext,
                            UIComponent component, IEventData eventData,
                            IEventObjectDecoder eventObjectDecoder) {
                        FacesEvent event = new FocusEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_KEYDOWN,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(IRequestContext requestContext,
                            UIComponent component, IEventData eventData,
                            IEventObjectDecoder eventObjectDecoder) {
                        FacesEvent event = new KeyDownEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_KEYUP,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(IRequestContext requestContext,
                            UIComponent component, IEventData eventData,
                            IEventObjectDecoder eventObjectDecoder) {
                        FacesEvent event = new KeyUpEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_KEYPRESS,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(IRequestContext requestContext,
                            UIComponent component, IEventData eventData,
                            IEventObjectDecoder eventObjectDecoder) {

                        int charCode = -1;
                        try {
                            charCode = Integer.parseInt(eventData
                                    .getEventValue());

                        } catch (NumberFormatException ex) {
                            LOG.debug("Invalid charCode from eventValue '"
                                    + eventData.getEventValue() + "'", ex);
                        }

                        int modifiers = eventData.getEventDetail();

                        FacesEvent event = new KeyPressEvent(component,
                                charCode, modifiers);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_MOUSEOUT,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(IRequestContext requestContext,
                            UIComponent component, IEventData eventData,
                            IEventObjectDecoder eventObjectDecoder) {
                        FacesEvent event = new MouseOutEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_MOUSEOVER,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(IRequestContext requestContext,
                            UIComponent component, IEventData eventData,
                            IEventObjectDecoder eventObjectDecoder) {
                        FacesEvent event = new MouseOverEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_CLOSE,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(IRequestContext requestContext,
                            UIComponent component, IEventData eventData,
                            IEventObjectDecoder eventObjectDecoder) {
                        FacesEvent event = new CloseEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_RESET,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(IRequestContext requestContext,
                            UIComponent component, IEventData eventData,
                            IEventObjectDecoder eventObjectDecoder) {
                        FacesEvent event = new ResetEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_SORT,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(IRequestContext requestContext,
                            UIComponent component, IEventData eventData,
                            IEventObjectDecoder eventObjectDecoder) {

                        FacesEvent event = new SortEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_SUGGESTION,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(IRequestContext requestContext,
                            UIComponent component, IEventData eventData,
                            IEventObjectDecoder eventObjectDecoder) {
                        // @XXX A Completer avec les noms des propri�t�s ...

                        FacesEvent event = new SuggestionEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_PROPERTY_CHANGE,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(IRequestContext requestContext,
                            UIComponent component, IEventData eventData,
                            IEventObjectDecoder eventObjectDecoder) {
                        // @XXX A Completer avec les noms des propri�t�s ...

                        FacesEvent event = new PropertyChangeEvent(component,
                                null, null, eventData.getEventValue());
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_USER,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(IRequestContext requestContext,
                            UIComponent component, IEventData eventData,
                            IEventObjectDecoder eventObjectDecoder) {
                        FacesEvent event = new UserEvent(component, eventData
                                .getEventValue(), eventData.getEventItem(),
                                eventData.getEventDetail());
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_LOAD,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(IRequestContext requestContext,
                            UIComponent component, IEventData eventData,
                            IEventObjectDecoder eventObjectDecoder) {
                        // @XXX A Completer avec les noms des propri�t�s ...

                        FacesEvent event = new LoadEvent(component);
                        queueEvent(component, event);
                    }
                });
    }

    public static IEventDecoder get(String eventName) {
        return (IEventDecoder) EVENT_DECODERS.get(eventName);
    }
}