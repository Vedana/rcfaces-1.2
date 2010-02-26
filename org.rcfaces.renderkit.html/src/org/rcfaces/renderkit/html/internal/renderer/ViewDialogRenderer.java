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

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ViewDialogRenderer extends AbstractJavaScriptRenderer {
    private static final String REVISION = "$Revision$";

    public ViewDialogRenderer() {
        super();
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();
        ViewDialogComponent component = (ViewDialogComponent) componentRenderContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        if (component.isVisible(facesContext)) {
            htmlWriter.getJavaScriptEnableMode().enableOnInit();
        }

        htmlWriter.startElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);

        String width = component.getWidth(facesContext);
        if (width != null) {
            htmlWriter.writeAttribute("v:width", width);
        }
        String height = component.getHeight(facesContext);
        if (height != null) {
            htmlWriter.writeAttribute("v:height", height);
        }
        String text = component.getText(facesContext);
        if (text != null) {
            htmlWriter.writeAttribute("v:title", text);
        }
        String src = component.getViewURL(facesContext);
        if (src != null) {
            IContentAccessor contentAccessor = ContentAccessorFactory
                    .createFromWebResource(facesContext, src,
                            IContentFamily.JSP);

            src = contentAccessor.resolveURL(facesContext, null, null);
            if (src != null) {
                htmlWriter.writeAttribute("v:viewURL", src);
            }
        }
        List<UISelectItem> children = component.getChildren();
		int i = 0;
		Map<String, String> values = new HashMap<String, String>(); 
		UISelectItem selectItem;
		while (i < children.size()) {
			if (children.get(i) instanceof UISelectItem) {
				selectItem = (UISelectItem) children.get(i);
				values.put(selectItem.getItemLabel(), selectItem.getItemValue().toString());
			}
			i++;
		}
		
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
        
        
        
        htmlWriter.writeAttribute("v:parameter", datas.toString());
        System.out.println(datas.toString());
        
        String shellDecorator = component.getShellDecoratorName(facesContext);
        if (shellDecorator != null) {
        	htmlWriter.writeAttribute("v:shellDecorator", shellDecorator);
        }
        
        if (!component.isVisible(facesContext)) {
            htmlWriter.writeAttribute("v:visible", false);
        }
        if (component.isDialogPrioritySetted()) {
            htmlWriter.writeAttribute("v:dialogPriority", component
                    .getDialogPriority(facesContext));
        }
        htmlWriter.endElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);

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

}
