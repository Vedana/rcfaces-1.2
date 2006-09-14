/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.taglib;

import java.io.IOException;
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
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ContextTools;
import org.rcfaces.core.internal.webapp.IRepository.IFile;
import org.rcfaces.renderkit.html.internal.HtmlProcessContextImpl;
import org.rcfaces.renderkit.html.internal.IHtmlProcessContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.clientBundle.ClientResourceBundleServlet;
import org.rcfaces.renderkit.html.internal.clientBundle.IClientBundleRepository;
import org.rcfaces.renderkit.html.internal.javascript.IJavaScriptRepository;
import org.rcfaces.renderkit.html.internal.javascript.JavaScriptRepositoryServlet;
import org.rcfaces.renderkit.html.internal.taglib.JavaScriptTag.JavaScriptWriterImpl;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class LoadClientBundleTag extends TagSupport implements Tag {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 7652529596505562593L;

    private static final Log LOG = LogFactory.getLog(LoadClientBundleTag.class);

    private static final boolean SERVER_SIDE_DEFAULT_VALUE = true;

    private static final String BUNDLE_REQUIRED_CLASSES = "f_resourceBundle";

    private String bundleName;

    private String baseName;

    private boolean serverSide = SERVER_SIDE_DEFAULT_VALUE;

    public final String getBaseName() {
        return baseName;
    }

    public final void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public final String getBundleName() {
        return bundleName;
    }

    public final void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }

    public final boolean isServerSide() {
        return serverSide;
    }

    public final void setServerSide(boolean serverSide) {
        this.serverSide = serverSide;
    }

    public int doStartTag() throws JspException {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null) {
            throw new JspException("No faces context initialized !");
        }

        IClientBundleRepository bundleRepository = ClientResourceBundleServlet
                .getBundleRepository(facesContext);
        if (bundleRepository == null) {
            throw new JspException("Client-Bundle engine is not initialized !");
        }

        Locale locale = ContextTools.getUserLocale(facesContext);

        if (UIComponentTag.isValueReference(baseName)) {
            baseName = String.valueOf(facesContext.getApplication()
                    .createValueBinding(baseName).getValue(facesContext));

            if (LOG.isDebugEnabled()) {
                LOG.debug("Reference baseName=" + baseName);
            }
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
            return Tag.SKIP_BODY;
        }

        if (serverSide) {
            facesContext.getExternalContext().getRequestMap().put(bundleName,
                    new BundleMap(bundle));
        }

        JspWriter writer = pageContext.getOut();

        IHtmlProcessContext htmlProcessContext = HtmlProcessContextImpl
                .getHtmlProcessContext(facesContext);

        try {
            boolean opened = JavaScriptTag.addRequires(facesContext, writer,
                    htmlProcessContext, null, BUNDLE_REQUIRED_CLASSES);

            if (opened) {
                JavaScriptTag.closeScriptTag(writer, htmlProcessContext);
            }

            IJavaScriptRepository repository = JavaScriptRepositoryServlet
                    .getRepository(facesContext);

            JavaScriptTag.openScriptTag(writer, htmlProcessContext);

            Map symbols = JavaScriptRepositoryServlet.getSymbols(facesContext);

            IJavaScriptWriter jsWriter = new JavaScriptWriterImpl(facesContext,
                    symbols, writer);

            JavaScriptRenderContext.initializeJavaScript(jsWriter, repository,
                    htmlProcessContext);

            jsWriter.writeCall("f_resourceBundle", "Load").writeString(
                    bundleName).write(',').writeString(baseName).write(',');

            IFile file = bundleRepository.getFileByName(baseName);

            String bundleURI = file.getURI(locale);

            String uri = htmlProcessContext.getAbsolutePath(bundleURI, true);
            jsWriter.writeString(uri);

            jsWriter.writeln(");");

            JavaScriptTag.closeScriptTag(writer, htmlProcessContext);
        } catch (IOException ex) {
            throw new JspException(ex);
        }

        return Tag.SKIP_BODY;
    }

    public void release() {
        baseName = null;
        bundleName = null;
        serverSide = SERVER_SIDE_DEFAULT_VALUE;

        super.release();
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
