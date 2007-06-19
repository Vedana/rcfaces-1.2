/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentType;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.webapp.IHierarchicalRepository;
import org.rcfaces.core.internal.webapp.IRepository;
import org.rcfaces.core.internal.webapp.IRepository.IFile;
import org.rcfaces.renderkit.html.component.JavaScriptComponent;
import org.rcfaces.renderkit.html.internal.AbstractHtmlRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.javascript.IJavaScriptRepository;
import org.rcfaces.renderkit.html.internal.javascript.JavaScriptRepositoryServlet;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class JavaScriptRenderer extends AbstractHtmlRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(JavaScriptRenderer.class);

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        JavaScriptComponent javaScriptComponent = (JavaScriptComponent) writer
                .getComponentRenderContext().getComponent();

        FacesContext facesContext = FacesContext.getCurrentInstance();

        String requiredFiles = javaScriptComponent
                .getRequiredFiles(facesContext);
        String requiredClasses = javaScriptComponent
                .getRequiredClasses(facesContext);

        if (requiredFiles != null || requiredClasses != null) {
            addRequires(htmlWriter, requiredFiles, requiredClasses);
        }

        String src = javaScriptComponent.getSrc(facesContext);
        if (src != null) {
            IContentAccessor contentAccessor = ContentAccessorFactory
                    .createFromWebResource(facesContext, src,
                            IContentType.SCRIPT);

            src = contentAccessor.resolveURL(facesContext, null, null);

            if (src != null) {
                String javaScriptSrcCharSet = javaScriptComponent
                        .getSrcCharSet(facesContext);

                InitRenderer.includeScript(htmlWriter, src,
                        javaScriptSrcCharSet);
            }
        }

        String text = javaScriptComponent.getText(facesContext);
        if (text != null && text.length() > 0) {
            IJavaScriptWriter jsWriter = InitRenderer.openScriptTag(htmlWriter);

            jsWriter.write(text);

            jsWriter.end();
        }
    }

    public static void addRequires(IHtmlWriter writer, String requiredFiles,
            String requiredClasses) throws WriterException {

        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();
        IJavaScriptRepository repository = JavaScriptRepositoryServlet
                .getRepository(facesContext);
        if (repository == null) {
            LOG.error("JavaScript repository is not created yet !");
            return;
        }

        List files = new ArrayList(32);

        if (requiredFiles != null) {
            StringTokenizer st = new StringTokenizer(requiredFiles, ", ");
            for (; st.hasMoreTokens();) {
                String requiredFile = st.nextToken();

                IRepository.IFile file = repository.getSetByName(requiredFile);
                if (file == null) {
                    file = repository.getModuleByName(requiredFile);
                    if (file == null) {
                        file = repository.getFileByName(requiredFile);
                    }
                }

                if (file == null) {
                    LOG.error("Can not find required file '" + requiredFile
                            + "' !");
                    continue;
                }

                files.add(file);
            }
        }
        if (requiredClasses != null) {
            StringTokenizer st = new StringTokenizer(requiredClasses, ", ");
            for (; st.hasMoreTokens();) {
                String className = st.nextToken();

                IJavaScriptRepository.IClass clazz = repository
                        .getClassByName(className);

                if (clazz == null) {
                    LOG.error("Can not find required class '" + className
                            + "' !");
                    continue;
                }

                files.add(clazz.getFile());
            }
        }

        if (files.isEmpty()) {
            return;
        }

        IRepository.IContext repositoryContext;

        repositoryContext = JavaScriptRepositoryServlet
                .getContextRepository(facesContext);

        IFile fs[] = repository
                .computeFiles(files,
                        IHierarchicalRepository.FILE_COLLECTION_TYPE,
                        repositoryContext);
        if (fs.length < 1) {
            return;
        }

        IJavaScriptWriter jsWriter = InitRenderer.openScriptTag(writer);

        JavaScriptRenderContext.initializeJavaScript(jsWriter, repository);

        String cameliaClassLoader = jsWriter.getJavaScriptRenderContext()
                .convertSymbol("f_classLoader", "_cameliaClassLoader");

        jsWriter.writeCall(cameliaClassLoader, "f_requiresBundle");
        jsWriter.write("document");

        Locale locale = repositoryContext.getLocale();
        for (int i = 0; i < fs.length; i++) {
            String src = fs[i].getURI(locale);

            jsWriter.write(", \"").write(src).write('"');
        }

        jsWriter.writeln(");");

        jsWriter.end();
    }
}
