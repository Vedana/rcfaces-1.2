/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Locale;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IClientDataCapability;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.webapp.IRepository;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class JavaScriptRenderContext extends AbstractJavaScriptRenderContext {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(JavaScriptRenderContext.class);

    private static final String LAZY_JAVASCRIPT_RENDERER = "camelia.lazy.js.renderer";

    public JavaScriptRenderContext(FacesContext facesContext) {
        super(facesContext);
    }

    protected JavaScriptRenderContext(
            JavaScriptRenderContext javaScriptRenderContext) {
        super(javaScriptRenderContext);
    }

    protected IJavaScriptWriter createJavaScriptWriter(IHtmlWriter writer,
            IJavaScriptComponentRenderer javaScriptComponent)
            throws WriterException {

        IHtmlProcessContext htmlProcessContext = writer
                .getHtmlComponentRenderContext().getHtmlRenderContext()
                .getHtmlProcessContext();

        JavaScriptWriterImpl jsImpl = new JavaScriptWriterImpl();

        jsImpl.setWriter(writer, javaScriptComponent, this, htmlProcessContext
                .useMetaContentScriptType(), htmlProcessContext
                .useScriptCData());

        return jsImpl;

    }

    public IJavaScriptRenderContext pushInteractive() {
        if (isInitialized() == false) {
            throw new FacesException(
                    "Can not push interactive while javaScript is not initialized !");
        }
        return new JavaScriptRenderContext(this);
    }

    public void popInteractive(
            IJavaScriptRenderContext oldJavaScriptRenderContext) {
    }

    public void initializePendingComponents(IJavaScriptWriter writer)
            throws WriterException {

        if (isUnitializedComponentsPending() == false) {
            return;
        }

        popUnitializedComponentsClientId();

        String cameliaClassLoader = convertSymbol("f_classLoader",
                "_rcfacesClassLoader");

        writer.writeCall(cameliaClassLoader, "f_initializeObjects").writeln(
                ");");
    }

    public void releaseComponentJavaScript(IJavaScriptWriter writer,
            boolean sendComplete, AbstractHtmlRenderer htmlComponentRenderer)
            throws WriterException {
        IComponentRenderContext componentRenderContext = writer
                .getHtmlComponentRenderContext();

        IHtmlRenderContext renderContext = (IHtmlRenderContext) componentRenderContext
                .getRenderContext();

        if (writer.isOpened() == false && renderContext.canUseLazyTag()) {
            // Le LAZY seulement en cas de non interactive, car IE ne
            // reconnait
            // pas les <v:init> dans le traitement interactif !

            if (isInitialized()) {
                if (componentRenderContext.setAttribute(
                        LAZY_JAVASCRIPT_RENDERER, Boolean.TRUE) == null) {
                    IHtmlWriter w = writer.getWriter();
                    // On ecrit à la main le tag, car on ne peut pas le
                    // fermer
                    // directement dans le m�me tag !
                    w.startElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);

                    if (canLazyTagUsesBrother() == false) {
                        w.writeAttribute("rid", componentRenderContext
                                .getComponentClientId());
                    }

                    IRepository.IFile files[] = renderContext
                            .getJavaScriptRenderContext().popRequiredFiles();
                    if (files != null && files.length > 0) {
                        Locale locale = getUserLocale();
                        StringAppender sb = new StringAppender(
                                files.length * 32);
                        for (int i = 0; i < files.length; i++) {
                            if (i > 0) {
                                sb.append(',');
                            }
                            sb.append(files[i].getURI(locale));
                        }

                        w.writeAttribute("requiresBundle", sb.toString());
                    }

                    // Il y a des nouveaux clientDatas ?
                    if (writer.getHtmlComponentRenderContext().hasClientDatas(
                            true)) {
                        htmlComponentRenderer.writeClientData(w,
                                (IClientDataCapability) componentRenderContext
                                        .getComponent());
                    }

                    w.endElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);
                }

                pushUnitializedComponent(componentRenderContext
                        .getComponentClientId());

                return;
            }
        }

        if (sendComplete) {
            writer.writeMethodCall("f_completeComponent").writeln(");");
        }
    }

    public void declareLazyJavaScriptRenderer(IComponentWriter writer) {
        writer.getComponentRenderContext().setAttribute(
                LAZY_JAVASCRIPT_RENDERER, Boolean.TRUE);
    }

    public void initializeJavaScriptComponent(IJavaScriptWriter writer)
            throws WriterException {

        writeJsInitComponent(writer);

        IHtmlComponentRenderContext componentRenderContext = writer
                .getHtmlComponentRenderContext();

        if (componentRenderContext.hasClientDatas(true)) {
            encodeClientData(writer);
        }
    }

    protected IJavaScriptWriter writeJsInitComponent(IJavaScriptWriter writer)
            throws WriterException {

        IComponentRenderContext componentRenderContext = writer
                .getHtmlComponentRenderContext();

        String componentId = componentRenderContext.getComponentClientId();

        boolean declare[] = new boolean[1];
        String componentVarName = allocateComponentVarId(componentId, declare);

        writer.setComponentVarName(componentVarName);

        String cameliaClassLoader = convertSymbol("f_classLoader",
                "_rcfacesClassLoader");

        if (declare[0]) {
            writer.write("var ").write(componentVarName);

            writer.write('=').writeCall(cameliaClassLoader, "f_init");
            writer.writeString(componentId);
            writer.writeln(");");

            return writer;
        }

        writer.writeCall(cameliaClassLoader, "f_init");
        writer.write(componentVarName).writeln(");");

        return writer;
    }

}
