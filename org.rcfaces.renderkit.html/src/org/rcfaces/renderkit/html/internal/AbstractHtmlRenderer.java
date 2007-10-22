/*
 * $Id$
 * 
 */

package org.rcfaces.renderkit.html.internal;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.component.capability.IAlternateTextCapability;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.component.capability.IClientDataCapability;
import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.component.capability.IHelpCapability;
import org.rcfaces.core.component.capability.IImmediateCapability;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import org.rcfaces.core.component.capability.IPagerMessageCapability;
import org.rcfaces.core.component.capability.IPositionCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.IScrollableCapability;
import org.rcfaces.core.component.capability.ISelectedCapability;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.component.capability.ITabIndexCapability;
import org.rcfaces.core.component.capability.ITextDirectionCapability;
import org.rcfaces.core.component.capability.IToolTipCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.component.capability.IWAIRoleCapability;
import org.rcfaces.core.event.BlurEvent;
import org.rcfaces.core.event.CheckEvent;
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
import org.rcfaces.core.event.SuggestionEvent;
import org.rcfaces.core.event.UserEvent;
import org.rcfaces.core.internal.component.ISeverityImageAccessors;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentType;
import org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IEventData;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.ParamUtils;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.service.AsyncRenderService;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlRenderer extends AbstractCameliaRenderer {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractHtmlRenderer.class);

    private static final String JAVASCRIPT_LISTENERS = "camelia.html.javascript.listeners";

    private static final String COMPONENT_DECORATOR = "camelia.component.decorator";

    private static final Map EVENT_DECODERS;

    private static final String RIGHT_TO_LEFT = "RTL";

    static {
        EVENT_DECODERS = new HashMap(32);

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_SELECTION,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(UIComponent component,
                            IEventData eventData) {
                        FacesEvent event = new SelectionEvent(component,
                                eventData.getEventValue(), null, eventData
                                        .getEventItem(), eventData
                                        .getEventDetail());
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_VALUE_CHANGE,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(UIComponent component,
                            IEventData eventData) {
                        FacesEvent event = new ValueChangeEvent(component,
                                null, eventData.getEventValue());

                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_DBLCLICK,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(UIComponent component,
                            IEventData eventData) {
                        FacesEvent event = new DoubleClickEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_CHECK,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(UIComponent component,
                            IEventData eventData) {
                        FacesEvent event = new CheckEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_BLUR,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(UIComponent component,
                            IEventData eventData) {
                        FacesEvent event = new BlurEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_FOCUS,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(UIComponent component,
                            IEventData eventData) {
                        FacesEvent event = new FocusEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_KEYDOWN,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(UIComponent component,
                            IEventData eventData) {
                        FacesEvent event = new KeyDownEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_KEYUP,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(UIComponent component,
                            IEventData eventData) {
                        FacesEvent event = new KeyUpEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_KEYPRESS,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(UIComponent component,
                            IEventData eventData) {

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

                    public void decodeEvent(UIComponent component,
                            IEventData eventData) {
                        FacesEvent event = new MouseOutEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_MOUSEOVER,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(UIComponent component,
                            IEventData eventData) {
                        FacesEvent event = new MouseOverEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_CLOSE,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(UIComponent component,
                            IEventData eventData) {
                        FacesEvent event = new CloseEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_RESET,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(UIComponent component,
                            IEventData eventData) {
                        FacesEvent event = new ResetEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_SUGGESTION,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(UIComponent component,
                            IEventData eventData) {
                        // @XXX A Completer avec les noms des propri�t�s ...

                        FacesEvent event = new SuggestionEvent(component);
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_PROPERTY_CHANGE,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(UIComponent component,
                            IEventData eventData) {
                        // @XXX A Completer avec les noms des propri�t�s ...

                        FacesEvent event = new PropertyChangeEvent(component,
                                null, null, eventData.getEventValue());
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_USER,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(UIComponent component,
                            IEventData eventData) {
                        FacesEvent event = new UserEvent(component, eventData
                                .getEventValue(), eventData.getEventItem(),
                                eventData.getEventDetail());
                        queueEvent(component, event);
                    }
                });

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_LOAD,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(UIComponent component,
                            IEventData eventData) {
                        // @XXX A Completer avec les noms des propri�t�s ...

                        FacesEvent event = new LoadEvent(component);
                        queueEvent(component, event);
                    }
                });
    }

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        if (hasComponenDecoratorSupport()) {
            IComponentDecorator componentDecorator = getComponentDecorator(writer
                    .getComponentRenderContext());
            encodeBeforeDecorator((IHtmlWriter) writer, componentDecorator);

            if (componentDecorator != null) {
                componentDecorator.encodeContainer((IHtmlWriter) writer, this);
            }
        }
    }

    protected void encodeBeforeDecorator(IHtmlWriter writer,
            IComponentDecorator componentDecorator) throws WriterException {
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        if (hasComponenDecoratorSupport()) {
            IComponentDecorator componentDecorator = getComponentDecorator(writer
                    .getComponentRenderContext());
            if (componentDecorator != null) {
                componentDecorator.encodeContainerEnd((IHtmlWriter) writer,
                        this);
            }

            encodeAfterDecorator((IHtmlWriter) writer, componentDecorator);
        }

        super.encodeEnd(writer);
    }

    protected void encodeAfterDecorator(IHtmlWriter writer,
            IComponentDecorator componentDecorator) throws WriterException {
    }

    protected final IHtmlWriter writeTitle(IHtmlWriter writer,
            IToolTipCapability element) throws WriterException {
        String title = element.getToolTipText();

        if (title == null) {
            return writer;
        }

        title = ParamUtils.formatMessage((UIComponent) element, title);

        writer.writeTitle(title);

        return writer;
    }

    protected final IHtmlWriter writeTabIndex(IHtmlWriter writer,
            ITabIndexCapability tabIndexCapability) throws WriterException {
        Integer index = tabIndexCapability.getTabIndex();
        if (index == null) {
            return writer;
        }

        int idx = index.intValue();

        if (idx < 0 || idx > 32767) {
            throw new WriterException(
                    "Bad tabindex attribute value ! (Value must be a number between 0 and 32767)",
                    null, (UIComponent) tabIndexCapability);
        }

        writer.writeTabIndex(idx);

        return writer;
    }

    protected final IHtmlWriter writeRequired(IHtmlWriter writer,
            IRequiredCapability component) throws WriterException {
        if (component.isRequired()) {
            writer.writeAttribute("v:required", true);
        }

        return writer;
    }

    protected final IHtmlWriter writeImmediate(IHtmlWriter writer,
            IImmediateCapability component) throws WriterException {
        if (component.isImmediate()) {
            writer.writeAttribute("v:immediate", true);
        }

        return writer;
    }

    protected IHtmlWriter writeFocusStyleClass(IHtmlWriter writer,
            IFocusStyleClassCapability component) throws WriterException {
        String focusStyleClass = component.getFocusStyleClass();
        if (focusStyleClass == null) {
            return writer;
        }

        writer.writeAttribute("v:focusStyleClass", focusStyleClass);

        return writer;
    }

    protected IHtmlWriter writeAccessKey(IHtmlWriter writer,
            IAccessKeyCapability accessKeyCapability) throws WriterException {
        String ak = accessKeyCapability.getAccessKey();

        if (ak != null && ak.length() > 0) {
            // L'API IE spécifie une majuscule à Key !
            writer.writeAccessKey(ak);

            writer.enableJavaScript();
        }

        return writer;
    }

    protected void writePagerMessage(IHtmlWriter htmlWriter,
            IPagerMessageCapability pagerMessageCapability)
            throws WriterException {

        String message = pagerMessageCapability.getMessage();
        if (message != null) {
            message = ParamUtils.formatMessage(
                    (UIComponent) pagerMessageCapability, message);

            htmlWriter.writeAttribute("v:message", message);

            String zeroResultMessage = pagerMessageCapability
                    .getZeroResultMessage();
            if (zeroResultMessage != null) {
                zeroResultMessage = ParamUtils
                        .formatMessage((UIComponent) pagerMessageCapability,
                                zeroResultMessage);

                htmlWriter.writeAttribute("v:zeroResultMessage",
                        zeroResultMessage);
            }

            String oneResultMessage = pagerMessageCapability
                    .getOneResultMessage();
            if (oneResultMessage != null) {
                oneResultMessage = ParamUtils.formatMessage(
                        (UIComponent) pagerMessageCapability, oneResultMessage);

                htmlWriter.writeAttribute("v:oneResultMessage",
                        oneResultMessage);
            }

            String manyResultsMessage = pagerMessageCapability
                    .getManyResultsMessage();
            if (manyResultsMessage != null) {
                manyResultsMessage = ParamUtils.formatMessage(
                        (UIComponent) pagerMessageCapability,
                        manyResultsMessage);

                htmlWriter.writeAttribute("v:manyResultMessage",
                        manyResultsMessage);
            }
        }

    }

    protected IHtmlWriter writeCoreAttributes(IHtmlWriter writer)
            throws WriterException {

        writeIdAttribute(writer);

        IComponentRenderContext componentContext = writer
                .getComponentRenderContext();

        UIComponent component = componentContext.getComponent();

        if (component instanceof ILookAndFeelCapability) {
            ILookAndFeelCapability lookAndFeelCapability = (ILookAndFeelCapability) component;

            String lookId = lookAndFeelCapability.getLookId();
            if (lookId != null) {
                writer.writeAttribute("v:lookid", lookId);
            }
        }

        return writer;
    }

    protected IHtmlWriter writeIdAttribute(IHtmlWriter writer)
            throws WriterException {
        String id = writer.getComponentRenderContext().getComponentClientId();
        if (id != null) {
            writer.writeId(id);
        }

        return writer;
    }

    protected final IHtmlWriter writeHtmlAttributes(IHtmlWriter _writer)
            throws WriterException {
        IHtmlWriter writer = writeCoreAttributes(_writer);

        UIComponent component = writer.getComponentRenderContext()
                .getComponent();

        if (component instanceof IToolTipCapability) {
            writeTitle(writer, (IToolTipCapability) component);
        }

        if (component instanceof ITabIndexCapability) {
            writeTabIndex(writer, (ITabIndexCapability) component);
        }

        if (component instanceof IHelpCapability) {
            writeHelp(writer, (IHelpCapability) component);
        }

        if (component instanceof IAccessKeyCapability) {
            writeAccessKey(writer, (IAccessKeyCapability) component);
        }

        if (component instanceof IRequiredCapability) {
            writeRequired(writer, (IRequiredCapability) component);
        }

        if (component instanceof IImmediateCapability) {
            writeImmediate(writer, (IImmediateCapability) component);
        }

        if (component instanceof IClientDataCapability) {
            writeClientData(writer, (IClientDataCapability) component);
        }

        if (component instanceof IScrollableCapability) {
            writeScroll(writer, (IScrollableCapability) component);
        }

        if (component instanceof IFocusStyleClassCapability) {
            writeFocusStyleClass(writer, (IFocusStyleClassCapability) component);
        }

        if (component instanceof NamingContainer) {
            writeNamingContainer(writer, (NamingContainer) component);
        }

        writeRole(writer, component);

        return writer;
    }

    protected void writeAlternateText(IHtmlWriter htmlWriter,
            IAlternateTextCapability alternateTextCapability)
            throws WriterException {

        String alternateText = alternateTextCapability.getAlternateText();
        if (alternateText != null) {
            htmlWriter.writeAlt(alternateText);
        }
    }

    public static final IHtmlWriter writeTextDirection(IHtmlWriter htmlWriter,
            ITextDirectionCapability textDirectionCapability)
            throws WriterException {

        int textDirection = textDirectionCapability.getTextDirection();
        if (textDirection != ITextDirectionCapability.DEFAULT_TEXT_DIRECTION) {
            switch (textDirection) {
            case ITextDirectionCapability.RIGHT_LEFT_TEXT_DIRECTION:
                htmlWriter.writeDir(RIGHT_TO_LEFT);
            }
        }

        return htmlWriter;
    }

    protected final IHtmlWriter writeScroll(IHtmlWriter writer,
            IScrollableCapability scrollableComponent) throws WriterException {

        int horizontalScrollPosition = scrollableComponent
                .getHorizontalScrollPosition();
        if (horizontalScrollPosition > 0) {
            writer.writeAttribute("v:hsp", horizontalScrollPosition);
        }

        int verticalScrollPosition = scrollableComponent
                .getVerticalScrollPosition();
        if (verticalScrollPosition > 0) {
            writer.writeAttribute("v:vsp", verticalScrollPosition);
        }

        return writer;
    }

    protected IHtmlWriter writeHelp(IHtmlWriter writer,
            IHelpCapability helpComponent) throws WriterException {
        String helpURL = helpComponent.getHelpURL();
        if (helpURL != null) {

            FacesContext facesContext = writer.getComponentRenderContext()
                    .getFacesContext();

            IContentAccessor contentAccessor = ContentAccessorFactory
                    .createFromWebResource(facesContext, helpURL,
                            IContentType.HELP);

            String resolvedHelpURL = contentAccessor.resolveURL(facesContext,
                    null, null);

            if (resolvedHelpURL != null) {
                writer.writeAttribute("v:helpURL", resolvedHelpURL);
            }
        }

        String helpMessage = helpComponent.getHelpMessage();
        if (helpMessage != null) {
            helpMessage = ParamUtils.formatMessage((UIComponent) helpComponent,
                    helpMessage);

            writer.writeAttribute("v:helpMessage", helpMessage);
        }

        return writer;
    }

    protected IHtmlWriter writeClientData(IHtmlWriter writer,
            IClientDataCapability clientDataCapability) throws WriterException {
        Map values = clientDataCapability.getClientDataMap();
        if (values.isEmpty()) {
            return writer;
        }

        return HtmlTools.writeClientData(writer, values);
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {

        if (hasComponenDecoratorSupport()) {
            IComponentDecorator componentDecorator = getComponentDecorator(
                    context.getFacesContext(), component);
            if (componentDecorator != null) {
                componentDecorator.decode(context, component, componentData);
            }
        }

        if (component instanceof IToolTipCapability) {
            String hp = componentData.getStringProperty("toolTip");
            if (hp != null) {
                IToolTipCapability toolTipCapability = (IToolTipCapability) component;

                String old = toolTipCapability.getToolTipText();

                if (hp.equals(old) == false) {
                    toolTipCapability.setToolTipText(hp);

                    component.queueEvent(new PropertyChangeEvent(component,
                            Properties.TOOL_TIP_TEXT, old, hp));
                }
            }
        }

        if (component instanceof IDisabledCapability) {
            Boolean hp = componentData.getBooleanProperty("disabled");
            if (hp != null) {
                IDisabledCapability enabledCapability = (IDisabledCapability) component;

                Boolean old = Boolean.valueOf(enabledCapability.isDisabled());

                if (hp.equals(old) == false) {
                    enabledCapability.setDisabled(hp.booleanValue());

                    component.queueEvent(new PropertyChangeEvent(component,
                            Properties.DISABLED, old, hp));
                }
            }
        }

        if (component instanceof IReadOnlyCapability) {
            Boolean hp = componentData.getBooleanProperty("readOnly");
            if (hp != null) {
                IReadOnlyCapability readOnlyCapability = (IReadOnlyCapability) component;

                Boolean old = Boolean.valueOf(readOnlyCapability.isReadOnly());

                if (hp.equals(old) == false) {
                    readOnlyCapability.setReadOnly(hp.booleanValue());

                    component.queueEvent(new PropertyChangeEvent(component,
                            Properties.READ_ONLY, old, hp));
                }
            }
        }

        if (component instanceof IImmediateCapability) {
            Boolean hp = componentData.getBooleanProperty("immediate");
            if (hp != null) {
                IImmediateCapability immediateCapability = (IImmediateCapability) component;

                Boolean old = Boolean
                        .valueOf(immediateCapability.isImmediate());

                if (hp.equals(old) == false) {
                    immediateCapability.setImmediate(hp.booleanValue());

                    component.queueEvent(new PropertyChangeEvent(component,
                            Properties.IMMEDIATE, old, hp));
                }
            }
        }

        if (component instanceof IVisibilityCapability) {
            Boolean hp = componentData.getBooleanProperty("visible");
            if (hp != null) {
                IVisibilityCapability visibilityCapability = (IVisibilityCapability) component;

                Boolean old = visibilityCapability.getVisibleState();

                if (hp.equals(old) == false) {
                    visibilityCapability.setVisible(hp.booleanValue());

                    component.queueEvent(new PropertyChangeEvent(component,
                            Properties.VISIBLE, old, hp));
                }
            }
        }

        if (component instanceof IPositionCapability) {
            IPositionCapability positionCapability = (IPositionCapability) component;

            String x = componentData.getStringProperty("x");
            if (x != null) {
                String old = positionCapability.getX();

                if (x.equals(old) == false) {
                    positionCapability.setX(x);

                    component.queueEvent(new PropertyChangeEvent(component,
                            Properties.X, old, x));
                }
            }

            String y = componentData.getStringProperty("y");
            if (y != null) {
                String old = positionCapability.getY();

                if (y.equals(old) == false) {
                    positionCapability.setY(y);

                    component.queueEvent(new PropertyChangeEvent(component,
                            Properties.Y, old, y));
                }
            }
        }

        if (component instanceof IScrollableCapability) {
            IScrollableCapability scrollableCapability = (IScrollableCapability) component;

            int horizontalScrollPosition = componentData.getIntProperty(
                    "horizontalScrollPosition", -1);
            if (horizontalScrollPosition >= 0) {
                int old = scrollableCapability.getHorizontalScrollPosition();

                if (horizontalScrollPosition != old) {
                    scrollableCapability
                            .setHorizontalScrollPosition(horizontalScrollPosition);

                    component
                            .queueEvent(new PropertyChangeEvent(component,
                                    Properties.HORIZONTAL_SCROLL_POSITION,
                                    new Integer(old), new Integer(
                                            horizontalScrollPosition)));
                }
            }

            int verticalScrollPosition = componentData.getIntProperty(
                    "verticalScrollPosition", -1);
            if (verticalScrollPosition >= 0) {
                int old = scrollableCapability.getVerticalScrollPosition();

                if (verticalScrollPosition != old) {
                    scrollableCapability
                            .setVerticalScrollPosition(verticalScrollPosition);

                    component.queueEvent(new PropertyChangeEvent(component,
                            Properties.VERTICAL_SCROLL_POSITION, new Integer(
                                    old), new Integer(verticalScrollPosition)));
                }
            }
        }

        if (component instanceof ISizeCapability) {
            ISizeCapability sizeCapability = (ISizeCapability) component;

            String width = componentData.getStringProperty("width");
            if (width != null) {
                String old = sizeCapability.getWidth();

                if (width.equals(old) == false) {
                    sizeCapability.setWidth(width);

                    component.queueEvent(new PropertyChangeEvent(component,
                            Properties.WIDTH, old, width));
                }
            }

            String height = componentData.getStringProperty("height");
            if (height != null) {
                String old = sizeCapability.getHeight();

                if (height.equals(old) == false) {
                    sizeCapability.setHeight(height);

                    component.queueEvent(new PropertyChangeEvent(component,
                            Properties.HEIGHT, old, height));
                }
            }
        }

        if (component instanceof IClientDataCapability) {
            String hp = componentData.getStringProperty("data");
            if (hp != null) {
                IClientDataCapability clientDataCapability = (IClientDataCapability) component;

                StringTokenizer st = new StringTokenizer(hp,
                        HtmlTools.LIST_SEPARATORS);
                for (; st.hasMoreTokens();) {
                    String cmd = st.nextToken();
                    String name = st.nextToken();
                    String value = null;

                    if ("S".equals(cmd)) {
                        value = st.nextToken();
                    }

                    clientDataCapability.setClientData(name, value);
                }
            }
        }

        super.decode(context, component, componentData);

        if (componentData.isEventComponent()) {
            IEventData eventData = null;
            if (componentData.getEventName() != null) {
                eventData = componentData;
            }

            decodeEvent(context, component, eventData);
        }
    }

    protected void decodeEvent(IRequestContext context, UIComponent component,
            IEventData eventData) {
        super.decodeEvent(context, component, eventData);

        if (eventData == null) {
            return;
        }

        IEventDecoder eventDecoder = (IEventDecoder) EVENT_DECODERS
                .get(eventData.getEventName());

        if (eventDecoder == null) {
            LOG.error("Unknown decoder for event name '"
                    + eventData.getEventName() + "'.");
            return;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Decode event type='" + eventData.getEventName()
                    + "' for component='" + component.getId() + "'.");
        }

        eventDecoder.decodeEvent(component, eventData);
    }

    protected IRenderContext getRenderContext(FacesContext context) {
        return getHtmlRenderContext(context);
    }

    protected IHtmlRenderContext getHtmlRenderContext(FacesContext context) {
        return HtmlRenderContext.getRenderContext(context);
    }

    protected IRequestContext getRequestContext(FacesContext context) {
        return HtmlRequestContext.getRequestContext(context);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private interface IEventDecoder {
        void decodeEvent(UIComponent component, IEventData eventData);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static abstract class AbstractEventDecoder implements IEventDecoder {
        private static final String REVISION = "$Revision$";

        protected final void queueEvent(UIComponent component, FacesEvent event) {
            PhaseId phaseId = PhaseId.INVOKE_APPLICATION;

            event.setPhaseId(phaseId);
            component.queueEvent(event);
        }
    }

    protected final void setAsyncRenderer(IHtmlWriter writer,
            UIComponent component, int asyncRenderMode) {

        IHtmlRenderContext renderContext = (IHtmlRenderContext) writer
                .getComponentRenderContext().getRenderContext();

        if (renderContext.isAsyncRenderEnable() == false) {
            return;
        }

        AsyncRenderService.setAsyncRenderer(writer
                .getHtmlComponentRenderContext(), asyncRenderMode);
        if (asyncRenderMode == IAsyncRenderModeCapability.TREE_ASYNC_RENDER_MODE) {
            hideChildren(writer.getComponentRenderContext());
        }
    }

    protected final IComponentDecorator getComponentDecorator(
            FacesContext facesContext, UIComponent component) {
        return createComponentDecorator(facesContext, component);
    }

    protected final IComponentDecorator getComponentDecorator(
            IComponentRenderContext componentRenderContext) {
        IComponentDecorator componentDecorator = (IComponentDecorator) componentRenderContext
                .getAttribute(COMPONENT_DECORATOR);
        if (componentDecorator != null) {
            return componentDecorator;
        }

        componentDecorator = createComponentDecorator(componentRenderContext
                .getFacesContext(), componentRenderContext.getComponent());
        componentRenderContext.setAttribute(COMPONENT_DECORATOR,
                componentDecorator);
        return componentDecorator;

    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {
        throw new FacesException(
                "This component does not support component-decorator !");
    }

    protected boolean hasComponenDecoratorSupport() {
        return false;
    }

    protected final IHtmlWriter writeReadOnly(IHtmlWriter writer,
            IReadOnlyCapability readOnlyCapability) throws WriterException {
        if (readOnlyCapability.isReadOnly()) {
            writer.writeReadOnly();
        }

        return writer;
    }

    protected final IHtmlWriter writeEnabled(IHtmlWriter writer,
            IDisabledCapability enabledCapability) throws WriterException {
        if (enabledCapability.isDisabled()) {
            writer.writeDisabled();
        }

        return writer;
    }

    protected IHtmlWriter writeChecked(IHtmlWriter writer,
            ISelectedCapability selectedCapability) throws WriterException {
        if (selectedCapability.isSelected()) {
            writer.writeChecked();
        }

        return writer;
    }

    protected IHtmlWriter writeNamingContainer(IHtmlWriter writer,
            NamingContainer namingContainer) throws WriterException {

        writer.writeAttribute("v:nc", true);

        return writer;
    }

    protected static boolean isEquals(Object object1, Object object2) {
        if (object1 == object2) {
            return true;
        }

        if (object1 == null || object2 == null) {
            return false;
        }

        if (object1.getClass().isArray() == false) {
            return object1.equals(object2);
        }

        if (object2.getClass().isArray() == false) {
            return false;
        }

        int l = Array.getLength(object1);
        if (l != Array.getLength(object2)) {
            return false;
        }

        for (int i = 0; i < l; i++) {
            if (isEquals(Array.get(object1, i), Array.get(object2, i)) == false) {
                return false;
            }
        }

        return true;
    }

    public String convertClientId(FacesContext context, String clientId) {
        if (Constants.CLIENT_NAMING_SEPARATOR_SUPPORT == false) {
            return super.convertClientId(context, clientId);
        }

        String namingSeparator = HtmlRenderContext.getRenderContext(context)
                .getProcessContext().getNamingSeparator();

        if (namingSeparator == null) {
            return super.convertClientId(context, clientId);
        }

        return HtmlTools.replaceSeparator(clientId, namingSeparator);
    }

    protected static void writeSeverityStyleClasses(IHtmlWriter htmlWriter,
            ISeverityStyleClassCapability severityStyleClassCapability)
            throws WriterException {

        String infoStyleClass = severityStyleClassCapability
                .getInfoStyleClass();
        if (infoStyleClass != null) {
            htmlWriter.writeAttribute("v:infoStyleClass", infoStyleClass);
        }

        String warnStyleClass = severityStyleClassCapability
                .getWarnStyleClass();
        if (warnStyleClass != null) {
            htmlWriter.writeAttribute("v:warnStyleClass", warnStyleClass);
        }

        String errorStyleClass = severityStyleClassCapability
                .getErrorStyleClass();
        if (errorStyleClass != null) {
            htmlWriter.writeAttribute("v:errorStyleClass", errorStyleClass);
        }

        String fatalStyleClass = severityStyleClassCapability
                .getFatalStyleClass();
        if (fatalStyleClass != null) {
            htmlWriter.writeAttribute("v:fatalStyleClass", fatalStyleClass);
        }
    }

    protected static boolean writeSeverityImages(IHtmlWriter htmlWriter,
            ISeverityImageAccessors severityImageAccessors)
            throws WriterException {

        IContentAccessor imageAccessor = severityImageAccessors
                .getImageAccessor();
        IContentAccessor infoImageAccessor = severityImageAccessors
                .getInfoImageAccessor();
        IContentAccessor warnImageAccessor = severityImageAccessors
                .getWarnImageAccessor();
        IContentAccessor errorImageAccessor = severityImageAccessors
                .getErrorImageAccessor();
        IContentAccessor fatalImageAccessor = severityImageAccessors
                .getFatalImageAccessor();
        if (imageAccessor == null && infoImageAccessor == null
                && warnImageAccessor == null && errorImageAccessor == null
                && fatalImageAccessor == null) {
            return false;
        }

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        if (infoImageAccessor != null) {
            String infoImageURL = infoImageAccessor.resolveURL(facesContext,
                    null, null);
            if (infoImageURL != null) {
                htmlWriter.writeAttribute("v:infoImageURL", infoImageURL);
            }
        }

        if (warnImageAccessor != null) {
            String warnImageURL = warnImageAccessor.resolveURL(facesContext,
                    null, null);
            if (warnImageURL != null) {
                htmlWriter.writeAttribute("v:warnImageURL", warnImageURL);
            }
        }

        if (errorImageAccessor != null) {
            String errorImageURL = errorImageAccessor.resolveURL(facesContext,
                    null, null);
            if (errorImageURL != null) {
                htmlWriter.writeAttribute("v:errorImageURL", errorImageURL);
            }
        }

        if (fatalImageAccessor != null) {
            String fatalImageURL = fatalImageAccessor.resolveURL(facesContext,
                    null, null);
            if (fatalImageURL != null) {
                htmlWriter.writeAttribute("v:fatalImageURL", fatalImageURL);
            }
        }

        return true;
    }

    protected void writeRole(IHtmlWriter writer, UIComponent component)
            throws WriterException {

        String waiRole = null;
        if (component instanceof IWAIRoleCapability) {
            waiRole = ((IWAIRoleCapability) component).getWaiRole();
        }

        if (waiRole == null) {
            waiRole = getWAIRole();
        }

        if (waiRole != null) {
            writer.writeRole(waiRole);
        }
    }

    protected String getWAIRole() {
        return null;
    }
}
