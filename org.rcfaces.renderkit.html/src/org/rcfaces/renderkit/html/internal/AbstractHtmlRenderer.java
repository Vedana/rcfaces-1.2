/*
 * $Id$
 * 
 * $Log$
 * Revision 1.7  2006/11/10 14:00:03  oeuillot
 * *** empty log message ***
 *
 * Revision 1.6  2006/11/09 19:08:57  oeuillot
 * *** empty log message ***
 *
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
 * Revision 1.3  2006/09/20 17:55:24  oeuillot
 * Tri multiple des tables
 * Dialogue modale en JS
 *
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.50  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.49  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.48  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.47  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 * Revision 1.46  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.45  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.44  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.43  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.42  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.41  2006/01/04 18:54:31  oeuillot
 * Mise au point des popup menus
 *
 * Revision 1.40  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.39  2005/12/27 16:08:16  oeuillot
 * Gestion imageButtonWriter
 * Ajout de fa_images
 * Preparation de f_imageCombo
 *
 * Revision 1.38  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.37  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.36  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.35  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.34  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.33  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.32  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.31  2005/02/18 14:46:07  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.30  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.29  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.28  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 */

package org.rcfaces.renderkit.html.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.component.capability.IClientDataCapability;
import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.component.capability.IHelpCapability;
import org.rcfaces.core.component.capability.IImmediateCapability;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import org.rcfaces.core.component.capability.IPositionCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.IScrollableCapability;
import org.rcfaces.core.component.capability.ISelectedCapability;
import org.rcfaces.core.component.capability.ISeverityStyleClassCapability;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.component.capability.ITabIndexCapability;
import org.rcfaces.core.component.capability.IToolTipCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
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
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.service.AsyncRenderService;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
abstract class AbstractHtmlRenderer extends AbstractCameliaRenderer implements
        IJavaScriptComponent {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractHtmlRenderer.class);

    private static final String JAVASCRIPT_LISTENERS = "camelia.html.javascript.listeners";

    private static final String COMPONENT_DECORATOR = "camelia.component.decorator";

    private static final Map EVENT_DECODERS;

    static {
        EVENT_DECODERS = new HashMap(32);

        EVENT_DECODERS.put(JavaScriptClasses.EVENT_SELECTION,
                new AbstractEventDecoder() {
                    private static final String REVISION = "$Revision$";

                    public void decodeEvent(UIComponent component,
                            IEventData eventData) {
                        FacesEvent event = new SelectionEvent(component,
                                eventData.getEventValue(), eventData
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
                        FacesEvent event = new KeyPressEvent(component);
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
                        // @XXX A Completer avec les noms des propriétés ...

                        FacesEvent event = new UserEvent(component, eventData
                                .getEventValue(), eventData.getEventDetail());
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

    protected static IHtmlRenderContext getHtmlRenderContext(IHtmlWriter writer) {
        return writer.getHtmlComponentRenderContext().getHtmlRenderContext();
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
            writer.writeAttribute("v:required", "true");
        }

        return writer;
    }

    protected final IHtmlWriter writeImmediate(IHtmlWriter writer,
            IImmediateCapability component) throws WriterException {
        if (component.isImmediate()) {
            writer.writeAttribute("v:immediate", "true");
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

        return writer;
    }

    protected final IHtmlWriter writeScroll(IHtmlWriter writer,
            IScrollableCapability scrollableComponent) throws WriterException {

        String horizontalScrollPosition = scrollableComponent
                .getHorizontalScrollPosition();
        if (horizontalScrollPosition != null) {
            writer.writeAttribute("v:hsp", horizontalScrollPosition);
        }

        String verticalScrollPosition = scrollableComponent
                .getVerticalScrollPosition();
        if (verticalScrollPosition != null) {
            writer.writeAttribute("v:vsp", verticalScrollPosition);
        }

        return writer;
    }

    protected IHtmlWriter writeHelp(IHtmlWriter writer,
            IHelpCapability helpComponent) throws WriterException {
        String helpURL = helpComponent.getHelpURL();
        if (helpURL != null) {

            IContentAccessor contentAccessor = ContentAccessorFactory
                    .createFromWebResource(helpURL, IContentType.HELP);

            String resolvedHelpURL = contentAccessor.resolveURL(writer
                    .getComponentRenderContext().getFacesContext(), null, null);

            if (resolvedHelpURL != null) {
                writer.writeAttribute("v:helpURL", resolvedHelpURL);
            }
        }

        String helpMessage = helpComponent.getHelpMessage();
        if (helpMessage != null) {
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

                Boolean old = visibilityCapability.getVisible();

                if (hp.equals(old) == false) {
                    visibilityCapability.setVisible(hp);

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

            String horizontalScrollPosition = componentData
                    .getStringProperty("horizontalScrollPosition");
            if (horizontalScrollPosition != null) {
                try {
                    String old = scrollableCapability
                            .getHorizontalScrollPosition();

                    if (horizontalScrollPosition.equals(old) == false) {
                        scrollableCapability
                                .setHorizontalScrollPosition(horizontalScrollPosition);

                        component.queueEvent(new PropertyChangeEvent(component,
                                Properties.HORIZONTAL_SCROLL_POSITION, old,
                                horizontalScrollPosition));
                    }

                } catch (NumberFormatException ex) {
                    LOG.error("Bad number '" + horizontalScrollPosition
                            + "' for horizontalScrollPosition.", ex);
                }
            }

            String verticalScrollPosition = componentData
                    .getStringProperty("verticalScrollPosition");
            if (verticalScrollPosition != null) {
                try {
                    String old = scrollableCapability
                            .getVerticalScrollPosition();

                    if (verticalScrollPosition.equals(old) == false) {
                        scrollableCapability
                                .setVerticalScrollPosition(verticalScrollPosition);

                        component.queueEvent(new PropertyChangeEvent(component,
                                Properties.VERTICAL_SCROLL_POSITION, old,
                                verticalScrollPosition));
                    }

                } catch (NumberFormatException ex) {
                    LOG.error("Bad number '" + verticalScrollPosition
                            + "' for verticalScrollPosition.", ex);
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
        eventDecoder.decodeEvent(component, eventData);
    }

    protected final IRenderContext getRenderContext(FacesContext context) {
        return HtmlRenderContext.getRenderContext(context);
    }

    protected final IRequestContext getRequestContext(FacesContext context) {
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
            UIComponent component, boolean enableAsyncRender) {

        IHtmlRenderContext renderContext = (IHtmlRenderContext) writer
                .getComponentRenderContext().getRenderContext();

        if (renderContext.isAsyncRenderEnable() == false) {
            return;
        }

        AsyncRenderService.setAsyncRenderer(component, enableAsyncRender);
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

    protected static boolean isEquals(Object object1, Object object2) {
        if (object1 == object2) {
            return true;
        }
        if (object1 != null && object1.equals(object2)) {
            return true;
        }

        return false;
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
}
