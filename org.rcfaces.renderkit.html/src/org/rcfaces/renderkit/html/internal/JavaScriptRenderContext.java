/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IClientDataCapability;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.webapp.IRepository;
import org.rcfaces.renderkit.html.internal.renderer.InitRenderer;

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

    private static final String STRING_EMPTY_ARRAY[] = new String[0];

    private Set uninitializedComponents = new HashSet();

    public JavaScriptRenderContext(FacesContext facesContext) {
        super(facesContext);
    }

    protected JavaScriptRenderContext(
            JavaScriptRenderContext javaScriptRenderContext) {
        super(javaScriptRenderContext);

        this.uninitializedComponents = new HashSet(
                javaScriptRenderContext.uninitializedComponents);
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

    public IJavaScriptRenderContext createChild() {
        if (isInitialized() == false) {
            throw new FacesException(
                    "Can not push interactive while javaScript is not initialized !");
        }
        return new JavaScriptRenderContext(this);
    }

    public void pushChild(IJavaScriptRenderContext javaScriptRenderContext,
            IHtmlWriter htmlWriter) {
    }

    public void popChild(IJavaScriptRenderContext javaScriptRenderContext,
            IHtmlWriter htmlWriter) {
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

    public void declareLazyJavaScriptRenderer(IHtmlWriter writer) {
        writer.getComponentRenderContext().setAttribute(
                LAZY_JAVASCRIPT_RENDERER, Boolean.TRUE);
    }

    public boolean isJavaScriptRendererDeclaredLazy(IHtmlWriter writer) {
        return writer.getComponentRenderContext().containsAttribute(
                LAZY_JAVASCRIPT_RENDERER);
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

    protected boolean isUnitializedComponentsPending() {
        return uninitializedComponents.size() > 0;
    }

    public String[] popUnitializedComponentsClientId() {
        if (uninitializedComponents.isEmpty()) {
            return STRING_EMPTY_ARRAY;
        }

        String old[] = (String[]) uninitializedComponents
                .toArray(new String[uninitializedComponents.size()]);
        uninitializedComponents.clear();

        return old;
    }

    public void pushUnitializedComponent(String clientId) {
        uninitializedComponents.add(clientId);
    }

    public void includeJavaScript(IHtmlWriter htmlWriter, String src,
            String javaScriptSrcCharSet) throws WriterException {
        HtmlTools.includeScript(htmlWriter, src, javaScriptSrcCharSet);
    }

    public void writeRaw(IHtmlWriter htmlWriter, String text)
            throws WriterException {

        IJavaScriptWriter jsWriter = InitRenderer.openScriptTag(htmlWriter);

        jsWriter.write(text);

        jsWriter.end();

    }

}
