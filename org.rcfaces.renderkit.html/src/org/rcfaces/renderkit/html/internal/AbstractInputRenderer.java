/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;

import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractInputRenderer extends AbstractCssRenderer {

    private static final String REVISION = "$Revision$";

    private static final String INPUT_MESSAGES_PROPERTY = "org.rcfaces.html.INPUT_MESSAGES";

    public static final String BUTTON_TYPE = "button";

    public static final String RADIO_TYPE = "radio";

    public static final String CHECKBOX_TYPE = "checkbox";

    public static final String TEXT_TYPE = "text";

    public static final String PASSWORD_TYPE = "password";

    public static final String RESET_TYPE = "reset";

    public static final String SUBMIT_TYPE = "submit";

    public static final String IMAGE_TYPE = "image";

    protected abstract String getInputType(UIComponent component);

    /*
    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        if (htmlWriter.isJavaScriptEnabled() == false) {
            Iterator it = writer.getComponentRenderContext().getFacesContext()
                    .getMessages(
                            writer.getComponentRenderContext()
                                    .getComponentClientId());
            if (it.hasNext()) {
                ((IHtmlWriter) writer).enableJavaScript();

                writer.getComponentRenderContext().setAttribute(
                        INPUT_MESSAGES_PROPERTY, Boolean.TRUE);
            }
        }

        super.encodeEnd(writer);
    }

    protected void encodeJavaScript(IJavaScriptWriter js)
            throws WriterException {
        super.encodeJavaScript(js);

        if (JavaScriptTools.writeMessages(js)) {
            js.getHtmlComponentRenderContext().setAttribute(
                    INPUT_MESSAGES_PROPERTY, Boolean.TRUE);

        }
    }
    */

    protected IHtmlWriter writeInputAttributes(IHtmlWriter writer)
            throws WriterException {
        UIComponent component = writer.getComponentRenderContext()
                .getComponent();

        return writeInputAttributes(writer, component.getId());
    }

    /*   
    protected void addRequiredJavaScriptClassNames(IHtmlWriter writer,
            Set classes) {
        super.addRequiredJavaScriptClassNames(writer, classes);

        if (writer.getComponentRenderContext().containsAttribute(
                INPUT_MESSAGES_PROPERTY)) {

            IJavaScriptRenderContext javaScriptRenderContext = writer
                    .getHtmlComponentRenderContext().getHtmlRenderContext()
                    .getJavaScriptRenderContext();

            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.INPUT, "message");
        }
    }
    */

    protected final IHtmlWriter writeInputAttributes(IHtmlWriter writer,
            String id) throws WriterException {

        IHtmlComponentRenderContext componentRenderContext = writer
                .getHtmlComponentRenderContext();

        UIComponent component = componentRenderContext.getComponent();

        String name = getInputName(componentRenderContext, id);
        if (name != null) {
            writer.writeAttribute("name", name);
        }

        String type = getInputType(component);
        if (type != null) {
            writer.writeAttribute("type", type);
        }

        if (component instanceof IReadOnlyCapability) {
            writeReadOnly(writer, (IReadOnlyCapability) component);
        }

        if (component instanceof IDisabledCapability) {
            writeEnabled(writer, (IDisabledCapability) component);
        }

        return writer;
    }

    /*
     * Il faut ecrire l'ID de toute facon, car il peut y avoir des regles CSS !
     * protected IWriter writeIdAttribute(IWriter writer) throws WriterException {
     * if (isNameEqualsId()) { return writer; }
     * 
     * return super.writeIdAttribute(writer); }
     */

    protected boolean isNameEqualsId() {
        return false;
    }

    protected String getInputName(
            IHtmlComponentRenderContext componentRenderContext, String id) {
        return componentRenderContext.getComponentClientId();
    }
}