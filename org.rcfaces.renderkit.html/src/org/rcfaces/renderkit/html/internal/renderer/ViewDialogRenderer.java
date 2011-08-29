/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.ViewDialogComponent;
import org.rcfaces.core.internal.codec.URLFormCodec;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.lang.IContentFamily;
import org.rcfaces.renderkit.html.internal.AbstractJavaScriptRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.ns.INamespaceConfiguration;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ViewDialogRenderer extends AbstractJavaScriptRenderer {

    public ViewDialogRenderer() {
        super();
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();
        ViewDialogComponent component = (ViewDialogComponent) componentRenderContext
                .getComponent();

        boolean designMode = componentRenderContext.getRenderContext()
                .getProcessContext().isDesignerMode();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        if (component.isVisible(facesContext) && designMode == false) {
            htmlWriter.getJavaScriptEnableMode().enableOnInit();
        }

        htmlWriter.startElementNS(LAZY_INIT_TAG);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);

        String width = component.getWidth(facesContext);
        if (width != null) {
            htmlWriter.writeAttributeNS("width", width);
        }

        String height = component.getHeight(facesContext);
        if (height != null) {
            htmlWriter.writeAttributeNS("height", height);
        }

        String text = component.getText(facesContext);
        if (text != null) {
            htmlWriter.writeAttributeNS("title", text);
        }

        boolean closable = component.isClosable(facesContext);
        if (closable == true) {
            htmlWriter.writeAttributeNS("closable", closable);
        }

        String src = component.getViewURL(facesContext);
        if (src != null) {
            IContentAccessor contentAccessor = ContentAccessorFactory
                    .createFromWebResource(facesContext, src,
                            IContentFamily.JSP);

            src = contentAccessor.resolveURL(facesContext, null, null);
            if (src != null) {
                htmlWriter.writeAttributeNS("viewURL", src);
            }
        }

        // TODO A refaire en décorator !
        List children = component.getChildren();
        Map values = null;
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i) instanceof UISelectItem) {
                UISelectItem selectItem = (UISelectItem) children.get(i);

                Object value = selectItem.getItemValue();
                if (value == null) {
                    continue;
                }

                if (values == null) {
                    values = new HashMap(8);
                }
                values.put(selectItem.getItemLabel(), value);
            }
        }

        if (values != null) {
            StringAppender datas = new StringAppender(values.size() * 64);
            for (Iterator it = values.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();

                String key = (String) entry.getKey();
                if (key == null || key.length() < 1) {
                    continue;
                }

                String value = (String) entry.getValue();
                if (value == null) {
                    continue;
                }

                if (datas.length() > 0) {
                    datas.append(',');
                }

                appendData(datas, key, value);
            }

            if (datas.length() > 0) {
                htmlWriter.writeAttributeNS("parameter", datas.toString());
            }
        }

        String shellDecorator = component.getShellDecoratorName(facesContext);
        if (shellDecorator != null) {
            htmlWriter.writeAttributeNS("shellDecorator", shellDecorator);
        }

        if (!component.isVisible(facesContext)) {
            htmlWriter.writeAttributeNS("visible", false);
        }
        if (component.isDialogPrioritySetted()) {
            htmlWriter.writeAttributeNS("dialogPriority",
                    component.getDialogPriority(facesContext));
        }
        htmlWriter.endElementNS(LAZY_INIT_TAG);

        declareLazyJavaScriptRenderer(htmlWriter);

        super.encodeEnd(htmlWriter);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.VIEW_DIALOG;
    }

    protected boolean sendCompleteComponent(
            IHtmlComponentRenderContext htmlComponentContext) {
        return false;
    }

    private void appendData(StringAppender datas, String key, String value) {
        URLFormCodec.encode(datas, key);
        datas.append('=');
        URLFormCodec.encode(datas, value);
    }

    public void declare(INamespaceConfiguration nameSpaceProperties) {
        super.declare(nameSpaceProperties);

        nameSpaceProperties.addComponent(LAZY_INIT_TAG);

        nameSpaceProperties.addAttributes(null, new String[] { "width",
                "height", "title", "closable", "viewURL", "parameter",
                "shellDecorator", "visible", "dialogPriority" });
    }
}
