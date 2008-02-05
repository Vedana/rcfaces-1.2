/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.rcfaces.core.internal.tools.ContextTools;
import org.rcfaces.core.internal.webapp.IHierarchicalRepository;
import org.rcfaces.core.internal.webapp.IRepository;
import org.rcfaces.core.internal.webapp.IHierarchicalRepository.IModule;
import org.rcfaces.core.internal.webapp.IHierarchicalRepository.ISet;
import org.rcfaces.core.internal.webapp.IRepository.IFile;
import org.rcfaces.renderkit.html.component.JavaScriptComponent;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.javascript.IJavaScriptRepository;
import org.rcfaces.renderkit.html.internal.javascript.JavaScriptRepositoryServlet;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class JavaScriptRenderer extends AbstractFilesCollectorRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(JavaScriptRenderer.class);

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        JavaScriptComponent javaScriptComponent = (JavaScriptComponent) writer
                .getComponentRenderContext().getComponent();

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        IJavaScriptRenderContext javaScriptRenderContext = htmlWriter
                .getHtmlComponentRenderContext().getHtmlRenderContext()
                .getJavaScriptRenderContext();

        String requiredFiles = javaScriptComponent
                .getRequiredFiles(facesContext);
        String requiredClasses = javaScriptComponent
                .getRequiredClasses(facesContext);
        String requiredModules = javaScriptComponent
                .getRequiredModules(facesContext);
        String requiredSets = javaScriptComponent.getRequiredSets(facesContext);

        if (requiredFiles != null || requiredClasses != null
                || requiredModules != null || requiredSets != null) {
            addRequires(htmlWriter, javaScriptRenderContext, requiredFiles,
                    requiredClasses, requiredModules, requiredSets);
        }

        String src = javaScriptComponent.getSrc(facesContext);
        if (src != null) {
            IContentAccessor contentAccessor = ContentAccessorFactory
                    .createFromWebResource(facesContext, src,
                            IContentType.SCRIPT);

            src = contentAccessor.resolveURL(facesContext, null, null);

            if (src != null) {
                includeScript(htmlWriter, javaScriptRenderContext, src);
            }
        }

        String sources[] = listSources(writer.getComponentRenderContext());
        if (sources.length > 0) {
            for (int i = 0; i < sources.length; i++) {
                String source = sources[i];

                IContentAccessor contentAccessor = ContentAccessorFactory
                        .createFromWebResource(facesContext, source,
                                IContentType.SCRIPT);

                source = contentAccessor.resolveURL(facesContext, null, null);

                if (source != null) {
                    includeScript(htmlWriter, javaScriptRenderContext, source);
                }
            }
        }

        String text = javaScriptComponent.getText(facesContext);
        if (text != null) {
            text = text.trim();

            if (text.length() > 0) {
                includeRawString(htmlWriter, javaScriptRenderContext, text);
            }
        }
    }

    private void includeRawString(IHtmlWriter htmlWriter,
            IJavaScriptRenderContext javaScriptRenderContext, String text)
            throws WriterException {

        javaScriptRenderContext.writeRaw(htmlWriter, text);
    }

    private void includeScript(IHtmlWriter htmlWriter,
            IJavaScriptRenderContext javaScriptRenderContext, String src)
            throws WriterException {

        JavaScriptComponent javaScriptComponent = (JavaScriptComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        String javaScriptSrcCharSet = javaScriptComponent
                .getSrcCharSet(htmlWriter.getComponentRenderContext()
                        .getFacesContext());

        javaScriptRenderContext.includeJavaScript(htmlWriter, src,
                javaScriptSrcCharSet);
    }

    public static void addRequires(IHtmlWriter writer,
            IJavaScriptRenderContext javaScriptRenderContext,
            String requiredFiles, String requiredClasses,
            String requiredModules, String requiredSets) throws WriterException {

        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();
        IJavaScriptRepository repository = JavaScriptRepositoryServlet
                .getRepository(facesContext);
        if (repository == null) {
            LOG.error("JavaScript repository is not created yet !");
            return;
        }

        final List files = new ArrayList(32);

        if (requiredFiles != null) {
            StringTokenizer st = new StringTokenizer(requiredFiles, ",");
            for (; st.hasMoreTokens();) {
                String requiredFile = st.nextToken().trim();

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
            StringTokenizer st = new StringTokenizer(requiredClasses, ",");
            for (; st.hasMoreTokens();) {
                String className = st.nextToken().trim();

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

        if (requiredModules != null) {
            StringTokenizer st = new StringTokenizer(requiredModules, ",");
            for (; st.hasMoreTokens();) {
                String moduleName = st.nextToken().trim();

                if ("all".equals(moduleName)) {
                    IModule modules[] = repository.listModules();

                    for (int i = 0; i < modules.length; i++) {
                        IModule module = modules[i];

                        files.addAll(Arrays.asList(module.listDependencies()));
                    }

                    continue;
                }

                IModule module = repository.getModuleByName(moduleName);

                if (module == null) {
                    LOG.error("Can not find required module '" + moduleName
                            + "' !");
                    continue;
                }

                files.addAll(Arrays.asList(module.listDependencies()));
            }
        }

        if (requiredSets != null) {
            StringTokenizer st = new StringTokenizer(requiredSets, ",");
            for (; st.hasMoreTokens();) {
                String setName = st.nextToken().trim();

                if ("all".equals(setName)) {
                    ISet sets[] = repository.listSets();

                    for (int i = 0; i < sets.length; i++) {
                        ISet set = sets[i];

                        files.addAll(Arrays.asList(set.listDependencies()));
                    }

                    continue;
                }

                ISet set = repository.getSetByName(setName);

                if (set == null) {
                    LOG.error("Can not find required set '" + setName + "' !");
                    continue;
                }

                files.addAll(Arrays.asList(set.listDependencies()));
            }
        }

        if (files.isEmpty()) {
            return;
        }

        if (javaScriptRenderContext.isCollectorMode()) {
            javaScriptRenderContext.appendRequiredFiles((IFile[]) files
                    .toArray(new IFile[files.size()]));

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
                .convertSymbol("f_classLoader", "_rcfacesClassLoader");

        jsWriter.writeCall(cameliaClassLoader, "f_requiresBundle");

        Locale locale = ContextTools.getUserLocale(facesContext);
        for (int i = 0; i < fs.length; i++) {
            String src = fs[i].getURI(locale);

            if (i > 0) {
                jsWriter.write(',');
            }
            jsWriter.writeString(src);
        }

        jsWriter.writeln(");");

        jsWriter.end();
    }
}
