/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ContextTools;
import org.rcfaces.core.internal.webapp.IRepository.IFile;
import org.rcfaces.renderkit.html.component.LoadClientBundleComponent;
import org.rcfaces.renderkit.html.internal.AbstractHtmlRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.clientBundle.ClientResourceBundleServlet;
import org.rcfaces.renderkit.html.internal.clientBundle.IClientBundleRepository;
import org.rcfaces.renderkit.html.internal.javascript.IJavaScriptRepository;
import org.rcfaces.renderkit.html.internal.javascript.JavaScriptRepositoryServlet;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class LoadClientBundleRenderer extends AbstractHtmlRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(LoadClientBundleRenderer.class);

    private static final String BUNDLE_REQUIRED_CLASSES = "f_resourceBundle";

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        LoadClientBundleComponent loadClientBundleComponent = (LoadClientBundleComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        IClientBundleRepository bundleRepository = ClientResourceBundleServlet
                .getBundleRepository(facesContext);
        if (bundleRepository == null) {
            throw new WriterException(
                    "Client-Bundle engine is not initialized !", null,
                    loadClientBundleComponent);
        }

        Locale locale = ContextTools.getUserLocale(facesContext);

        String baseName = loadClientBundleComponent.getBaseName(facesContext);
        String bundleName = loadClientBundleComponent
                .getBundleName(facesContext);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Reference baseName=" + baseName + " bundleName="
                    + bundleName);
        }

        ResourceBundle bundle;
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Get resourceBundle name='" + baseName + "' locale='"
                        + locale + "'.");
            }

            ClassLoader classLoader = Thread.currentThread()
                    .getContextClassLoader();

            bundle = ResourceBundle.getBundle(baseName, locale, classLoader);

        } catch (MissingResourceException e) {
            LOG.error("Resource bundle '" + baseName + "' could not be found.");
            return;
        }

        boolean serverSide = loadClientBundleComponent
                .isServerSide(facesContext);
        if (serverSide) {
            facesContext.getExternalContext().getRequestMap().put(bundleName,
                    new BundleMap(bundle));
        }

        JavaScriptRenderer.addRequires(htmlWriter, null, BUNDLE_REQUIRED_CLASSES);

        IJavaScriptRepository repository = JavaScriptRepositoryServlet
                .getRepository(facesContext);

        IJavaScriptWriter jsWriter = InitRenderer.openScriptTag(htmlWriter);

        JavaScriptRenderContext.initializeJavaScript(jsWriter, repository);

        jsWriter.writeCall("f_resourceBundle", "Load").writeString(bundleName)
                .write(',').writeString(baseName).write(',');

        IFile file = bundleRepository.getFileByName(baseName);

        String bundleURI = file.getURI(locale);

        String uri = htmlWriter.getHtmlComponentRenderContext()
                .getHtmlRenderContext().getHtmlProcessContext()
                .getAbsolutePath(bundleURI, true);
        jsWriter.writeString(uri);

        jsWriter.writeln(");");

        jsWriter.end();
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class BundleMap implements Map {
        private static final String REVISION = "$Revision$";

        private final ResourceBundle bundle;

        private List values;

        private Set keys;

        private HashSet entrySet;

        public BundleMap(ResourceBundle bundle) {
            this.bundle = bundle;
        }

        // Optimized methods

        public Object get(Object key) {
            try {
                return bundle.getObject(key.toString());

            } catch (Throwable th) {
                return "MISSING: " + key + " :MISSING";
            }
        }

        public boolean isEmpty() {
            return keySet().isEmpty();
        }

        public boolean containsKey(Object key) {
            return keySet().contains(key);
        }

        public Collection values() {
            if (values != null) {
                return values;
            }

            values = new ArrayList(size());
            for (Iterator it = keySet().iterator(); it.hasNext();) {
                String key = (String) it.next();

                String value = bundle.getString(key);
                values.add(value);
            }

            return values;
        }

        public int size() {
            return keySet().size();
        }

        public boolean containsValue(Object value) {
            return values().contains(value);
        }

        public Set entrySet() {
            if (entrySet != null) {
                return entrySet;
            }

            entrySet = new HashSet(size());

            for (Iterator it = keySet().iterator(); it.hasNext();) {
                final String key = (String) it.next();

                entrySet.add(new Map.Entry() {
                    private static final String REVISION = "$Revision$";

                    public Object getKey() {
                        return key;
                    }

                    public Object getValue() {
                        return bundle.getObject(key);
                    }

                    public Object setValue(Object value) {
                        throw new UnsupportedOperationException(this.getClass()
                                .getName()
                                + " UnsupportedOperationException");
                    }
                });
            }

            return entrySet;
        }

        public Set keySet() {
            if (keys != null) {
                return keys;
            }

            keys = new HashSet();
            for (Enumeration en = bundle.getKeys(); en.hasMoreElements();) {
                keys.add(en.nextElement());
            }

            return keys;
        }

        // Unsupported methods

        public Object remove(Object key) {
            throw new UnsupportedOperationException(this.getClass().getName()
                    + " UnsupportedOperationException");
        }

        public void putAll(Map t) {
            throw new UnsupportedOperationException(this.getClass().getName()
                    + " UnsupportedOperationException");
        }

        public Object put(Object key, Object value) {
            throw new UnsupportedOperationException(this.getClass().getName()
                    + " UnsupportedOperationException");
        }

        public void clear() {
            throw new UnsupportedOperationException(this.getClass().getName()
                    + " UnsupportedOperationException");
        }

    }
}
