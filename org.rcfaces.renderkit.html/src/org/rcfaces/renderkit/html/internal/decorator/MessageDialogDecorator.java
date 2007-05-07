package org.rcfaces.renderkit.html.internal.decorator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.rcfaces.core.component.MessageDialogComponent;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.internal.component.IImageAccessors;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;

public class MessageDialogDecorator extends AbstractSelectItemsDecorator {

    // private String newVar;
    private String defaultValue;

    /**
     * 
     * @return String the base css name for all the classes
     */
    protected String getCssClassBase(MessageDialogComponent component,
            FacesContext facesContext) {
        return component.getCssClassBase(facesContext);
    }

    public MessageDialogDecorator(UIComponent component) {
        super(component, null);

    }

    protected SelectItemsContext createHtmlContext() {
        return null;
    }

    protected void preEncodeContainer() throws WriterException {
        super.preEncodeContainer();
        writer.enableJavaScript();
    }

    protected SelectItemsContext createJavaScriptContext()
            throws WriterException {

        IComponentRenderContext componentRenderContext = javaScriptWriter
                .getHtmlComponentRenderContext();

        return new SelectItemsJsContext(this, componentRenderContext,
                getComponent(), null);
    }

    protected void encodeComponentsBegin() throws WriterException {
        super.encodeComponentsBegin();

        MessageDialogComponent component = (MessageDialogComponent) getComponent();
        FacesContext facesContext = javaScriptWriter.getFacesContext();

        String styleClass = getCssClassBase(component, facesContext);
        if (styleClass != null) {
            // titi.setCssClassBase(cssClassBase);
            javaScriptWriter.writeMethodCall("f_setCssClassBase").writeString(
                    styleClass).writeln(");");
        }
        styleClass = component.getStyleClass(facesContext);
        if (styleClass != null) {
            // titi.f_setStyleClass(styleClass);
            javaScriptWriter.writeMethodCall("f_setStyleClass").writeString(
                    styleClass).writeln(");");
        }

        int priority = component.getPriority(facesContext);
        if (priority != 0) {
            javaScriptWriter.writeMethodCall("f_setPriority")
                    .writeInt(priority).writeln(");");
        }

        IContentAccessors contentAccessors = component
                .getImageAccessors(facesContext);
        if (contentAccessors instanceof IImageAccessors) {
            IImageAccessors imageAccessors = (IImageAccessors) contentAccessors;
            IContentAccessor imageAccessor = imageAccessors.getImageAccessor();
            if (imageAccessor != null) {
                String imageSrc = imageAccessor.resolveURL(facesContext, null,
                        null);
                if (imageSrc != null) {
                    javaScriptWriter.writeMethodCall("f_setImageURL")
                            .writeString(imageSrc).writeln(");");
                }
            }
        }

        defaultValue = component.getDefaultValue();

    }

    protected void encodeComponentsEnd() throws WriterException {

        MessageDialogComponent component = (MessageDialogComponent) getComponent();
        FacesContext facesContext = javaScriptWriter.getFacesContext();

        // titi.open(callback)

        javaScriptWriter.writeMethodCall("f_open");

        String jsCallBack = component.getCallback(facesContext);
        if (jsCallBack != null) {
            javaScriptWriter.write(jsCallBack);
        }

        javaScriptWriter.writeln(");");

        super.encodeComponentsEnd();
    }

    public int encodeNodeBegin(UIComponent component, SelectItem selectItem,
            boolean hasChildren, boolean isVisible) throws WriterException {
        IComponentRenderContext componentRenderContext = getComponentRenderContext();

        Object selectItemValue = selectItem.getValue();
        String value = convertItemValue(componentRenderContext, selectItemValue);
        if (value == null) {
            return EVAL_NODE;
        }

        if (hasChildren) {
            if (getContext().getDepth() > 1) {
                throw new WriterException(
                        "Optgroup does not support more 1 level !", null,
                        component);
            }
        }

        String text = selectItem.getLabel();

        javaScriptWriter.writeMethodCall("f_addAction").writeString(value)
                .write(',').writeString(text).write(',').writeBoolean(
                        selectItem.isDisabled()).write(',').writeBoolean(
                        value == defaultValue).writeln(");");

        return EVAL_NODE;

    }

    public void encodeNodeEnd(UIComponent component, SelectItem selectItem,
            boolean hasChild, boolean isVisible) throws WriterException {

    }

}
