/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.AcceleratorComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.KeyTools;
import org.rcfaces.renderkit.html.internal.AbstractJavaScriptRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.util.ListenerTools.INameSpace;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AcceleratorRenderer extends AbstractJavaScriptRenderer {
    private static final String REVISION = "$Revision$";

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        AcceleratorComponent acceleratorComponent = (AcceleratorComponent) componentRenderContext
                .getComponent();

        String keyBinding = acceleratorComponent.getKeyBinding(facesContext);
        if (keyBinding == null || keyBinding.length() == 0) {
            return;
        }

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        IHtmlComponentRenderContext htmlComponentRenderContext = htmlWriter
                .getHtmlComponentRenderContext();
        if (htmlComponentRenderContext.getHtmlRenderContext()
                .getJavaScriptRenderContext().isCollectorMode() == false) {

            htmlWriter.startElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);
            writeHtmlAttributes(htmlWriter);
            writeJavaScriptAttributes(htmlWriter);

            String forComponent = acceleratorComponent.getFor(facesContext);
            if (forComponent != null) {
                htmlWriter.writeAttribute("v:for", forComponent);
            }

            String forItemValue = acceleratorComponent
                    .getForItemValue(facesContext);
            if (forItemValue != null) {
                htmlWriter.writeAttribute("v:forItemValue", forItemValue);
            }

            KeyTools.State state = KeyTools.parseKeyBinding(keyBinding);

            if (state.character > 0) {
                htmlWriter.writeAttribute("v:character", String
                        .valueOf(state.character));
            }

            if (state.virtualKey != null) {
                htmlWriter.writeAttribute("v:virtualKey", state.virtualKey
                        .intValue());
            }

            if (state.keyFlags > 0) {
                htmlWriter.writeAttribute("v:keyFlags", state.keyFlags);
            }

            if (acceleratorComponent.isIgnoreEditableComponent(facesContext)) {
                htmlWriter.writeAttribute("v:ignoreEditableComponent", true);
            }

            htmlWriter.endElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);

            declareLazyJavaScriptRenderer(htmlWriter);
            htmlWriter.getJavaScriptEnableMode().enableOnInit();

        } else {
            htmlWriter.enableJavaScript();
        }

        super.encodeEnd(htmlWriter);
    }

    protected void encodeJavaScript(IJavaScriptWriter jsWriter)
            throws WriterException {
        super.encodeJavaScript(jsWriter);

        if (jsWriter.getJavaScriptRenderContext().isCollectorMode() == false) {
            return;
        }

        FacesContext facesContext = jsWriter.getFacesContext();

        IComponentRenderContext componentRenderContext = jsWriter
                .getComponentRenderContext();

        AcceleratorComponent acceleratorComponent = (AcceleratorComponent) componentRenderContext
                .getComponent();

        String keyBinding = acceleratorComponent.getKeyBinding(facesContext);

        jsWriter.setIgnoreComponentInitialization();

        String varName = jsWriter.getJavaScriptRenderContext()
                .allocateVarName();
        jsWriter.setComponentVarName(varName);

        jsWriter.write(varName).write('=').writeCall(getJavaScriptClassName(),
                "f_newInstance");

        int param = 0;

        KeyTools.State state = KeyTools.parseKeyBinding(keyBinding);
        if (state.character > 0) {
            jsWriter.writeString(String.valueOf(state.character));
        } else {
            jsWriter.writeNull();
        }

        if (state.virtualKey != null) {
            for (; param > 0; param--) {
                jsWriter.write(',').writeNull();
            }
            jsWriter.write(',').writeInt(state.virtualKey.intValue());

        } else {
            param++;
        }

        if (state.keyFlags > 0) {
            for (; param > 0; param--) {
                jsWriter.write(',').writeNull();
            }
            jsWriter.write(',').writeInt(state.keyFlags);

        } else {
            param++;
        }

        String forComponent = acceleratorComponent.getFor(facesContext);
        if (forComponent != null) {
            for (; param > 0; param--) {
                jsWriter.write(',').writeNull();
            }

            // Il faut calculer le for, car l'accelerator n'est plus rattaché à
            // un composant DOM !

            IRenderContext renderContext = componentRenderContext
                    .getRenderContext();

            String forComponentClientId = renderContext
                    .computeBrotherComponentClientId(acceleratorComponent,
                            forComponent);

            jsWriter.write(',').writeString(forComponentClientId);

        } else {
            param++;
        }

        String forItemValue = acceleratorComponent
                .getForItemValue(facesContext);
        if (forItemValue != null) {
            for (; param > 0; param--) {
                jsWriter.write(',').writeNull();
            }
            jsWriter.write(',').writeString(forItemValue);

        } else {
            param++;
        }

        if (acceleratorComponent.isIgnoreEditableComponent(facesContext)) {
            for (; param > 0; param--) {
                jsWriter.write(',').writeNull();
            }
            jsWriter.write(',').writeBoolean(true);

        } else {
            param++;
        }

        jsWriter.writeln(");");
    }

    /*
     * protected IWriter writeIdAttribute(IWriter htmlWriter) throws
     * WriterException { Pas ca car il nous faut un ID ! (en cas de premier
     * composant a initialiser ! if
     * (ComponentTools.isAnonymousComponentId(htmlWriter
     * .getComponentRenderContext().getComponentId())) { return htmlWriter; }
     * 
     * return super.writeIdAttribute(htmlWriter); }
     */

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.ACCELERATOR;
    }

    protected String getActionEventName(INameSpace nameSpace) {
        return nameSpace.getKeyPressEventName();
    }

    protected boolean sendCompleteComponent(
            IHtmlComponentRenderContext htmlComponentContext) {
        return false;
    }
}
