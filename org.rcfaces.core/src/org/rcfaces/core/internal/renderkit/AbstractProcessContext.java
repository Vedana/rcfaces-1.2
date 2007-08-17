/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.tools.ContextTools;
import org.rcfaces.core.internal.tools.PageConfiguration;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractProcessContext implements IProcessContext {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractProcessContext.class);

    private static final String EXTERNAL_CONTEXT_PROPERTY = "org.rcfaces.renderkit.core.EXTERNAL_CONTEXT";

    protected final RcfacesContext rcfacesContext;

    protected final FacesContext facesContext;

    private final String contextPath;

    private final String servletPath;

    private String baseHREF;

    private Locale userLocale;

    private boolean designerMode;

    private boolean pageConfiguratorInitialized;

    private Locale defaultAttributesLocale;

    private String scriptType;

    private TimeZone timeZone;

    private Calendar calendar;

    protected AbstractProcessContext(FacesContext facesContext) {
        this.facesContext = facesContext;

        ExternalContext externalContext = facesContext.getExternalContext();

        contextPath = externalContext.getRequestContextPath();
        String servletPath = externalContext.getRequestServletPath();
        int idx = servletPath.lastIndexOf('/');
        if (idx >= 0) {
            servletPath = servletPath.substring(0, idx);
        }

        this.servletPath = servletPath;

        rcfacesContext = RcfacesContext.getInstance(facesContext);

        this.designerMode = rcfacesContext.isDesignerMode();
    }

    public final FacesContext getFacesContext() {
        return facesContext;
    }

    public Boolean getDebugMode() {
        return null;
    }

    public Boolean getProfilerMode() {
        return null;
    }

    public boolean isDesignerMode() {
        return designerMode;
    }

    public final Locale getUserLocale() {
        if (userLocale != null) {
            return userLocale;
        }

        userLocale = ContextTools.getUserLocale(null);

        return userLocale;
    }

    public Calendar getUserCalendar() {
        if (calendar != null) {
            return calendar;
        }

        TimeZone timeZone = getUserTimeZone();
        Locale locale = getUserLocale();

        if (timeZone != null) {
            calendar = Calendar.getInstance(timeZone, locale);
            return calendar;
        }

        calendar = Calendar.getInstance(locale);

        return calendar;
    }

    public TimeZone getUserTimeZone() {
        if (timeZone != null) {
            return timeZone;
        }

        timeZone = ContextTools.getUserTimeZone(null);

        return timeZone;
    }

    public final String getAbsolutePath(String uri, boolean containsContextPath) {

        String contextPath;
        if (containsContextPath) {
            contextPath = this.contextPath;
        } else {
            contextPath = "";
        }

        if (uri == null || uri.length() < 1) {
            // Retourne le context path

            String p;
            if (containsContextPath) {
                p = contextPath + servletPath;
            } else {
                p = servletPath;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Returns path='" + p + "' [uri=null]  ('"
                        + contextPath + "''" + servletPath + "'.)");
            }

            return p;
        }

        if (uri.charAt(0) == '/') {
            // URL absolue
            String p;
            if (containsContextPath) {
                p = contextPath + uri;

            } else if (uri.startsWith(this.contextPath)) {
                p = uri.substring(this.contextPath.length());

            } else {
                p = uri;
            }

            p = normalizePath(p);
            if (LOG.isDebugEnabled()) {
                LOG
                        .debug("Returns path='" + p + "' [uri=absolute]  ('"
                                + contextPath + "''" + servletPath + "''" + uri
                                + "'.)");
            }
            return p;
        }

        // C'est un URI relatif !

        if (baseHREF != null) {
            if (baseHREF.charAt(0) == '/') {
                // base HREF absolue !

                String p = normalizePath(baseHREF + uri);

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Returns path='" + p
                            + "' [uri=relative,baseHREF=absolute]  ('"
                            + baseHREF + uri + "'.)");
                }
                return p;
            }
            // base HREF relatif !

            String p = normalizePath(contextPath + servletPath + "/" + baseHREF
                    + uri);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Returns path='" + p
                        + "' [uri=relative,baseHREF=relative]  ('"
                        + contextPath + "''" + servletPath + "'/'" + baseHREF
                        + uri + "'.)");
            }
            return p;
        }

        String p = normalizePath(contextPath + servletPath + "/" + uri);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Returns path='" + p
                    + "' [uri=relative,baseHREF=null] ('" + contextPath + "''"
                    + servletPath + "'/'" + uri + "'.)");
        }
        return p;
    }

    public final String getRelativePath(String uri) {
        return null;
    }

    /*
     * public final String computeFromContextPath(String uri, boolean
     * canBeRelative) { String baseURI = getAbsolutePath(canBeRelative);
     * 
     * String ret; if (uri == null) { ret = baseURI; } / * else if
     * (baseURI.length() == 0) { if (uri.length() > 0) { } } /else {
     * StringAppender u = new StringAppender(baseURI, uri.length() + 2);
     * 
     * if (baseURI.length() > 0) { u.append('/'); }
     * 
     * if (uri.length() > 0) { if (uri.charAt(0) == '/') {
     * u.append(uri.substring(1)); } else if (u.length() > 1 &&
     * u.charAt(u.length() - 1) == '/') { u.setLength(u.length() - 1);
     * u.append(uri); } else { u.append(uri); } }
     * 
     * ret = u.toString(); }
     * 
     * if (Constants.ENCODE_URI) { ret = externalContext.encodeResourceURL(ret); }
     * 
     * if (LOG.isDebugEnabled()) { LOG.debug("Compute uri '" + uri + "' => '" +
     * ret + "'."); }
     * 
     * return ret; }
     */

    public final String getBaseHREF() {
        return baseHREF;
    }

    public final void changeBaseHREF(String baseHREF) {
        String base = baseHREF;

        if (base != null) {
            base = normalizeBaseHREF(base);
        }

        this.baseHREF = base;

        if (LOG.isDebugEnabled()) {
            LOG.debug("Set baseHREF to '" + base + "' (param '" + baseHREF
                    + "'.");
        }
    }

    private String normalizeBaseHREF(String baseHREF) {
        if (baseHREF.equals("/")) {
            return baseHREF;
        }

        int idx = baseHREF.lastIndexOf('/'); // Retire le dernier segment qui
        // doit être un fichier
        if (idx < 1) {
            return null;
        }

        baseHREF = baseHREF.substring(0, idx);

        if (baseHREF.charAt(0) == '/') {
            return baseHREF;
        }

        return normalizePath(contextPath + servletPath + baseHREF);
    }

    protected String normalizePath(String path) {
        boolean modified = false;

        StringTokenizer st = new StringTokenizer(path, "/", true);

        List l = new ArrayList((st.countTokens() / 2) + 1);
        boolean sep = true;
        for (; st.hasMoreTokens();) {
            String segment = st.nextToken();
            if (segment.equals("/")) {
                if (sep) {
                    sep = false;
                    continue;
                }
                modified = true;
                continue;
            }
            sep = true;
            l.add(segment);
        }

        for (int i = 0; i < l.size();) {
            String segment = (String) l.get(i);

            if (segment.equals("..")) {
                modified = true;

                l.remove(i);
                if (i < 1) {
                    continue;
                }

                i--;
                l.remove(i);
                continue;
            }

            if (segment.equals(".")) {
                modified = true;

                l.remove(i);
                continue;
            }

            i++;
        }

        if (modified == false) {
            return path;
        }

        StringAppender sa = new StringAppender(path.length());
        for (Iterator it = l.iterator(); it.hasNext();) {
            sa.append('/');
            sa.append((String) it.next());
        }

        return sa.toString();
    }

    protected static void setProcessContext(IProcessContext externalContext) {
        Map requestMap = externalContext.getFacesContext().getExternalContext()
                .getRequestMap();
        IProcessContext old = (IProcessContext) requestMap.put(
                EXTERNAL_CONTEXT_PROPERTY, externalContext);
        if (old != null) {
            throw new FacesException("External constext is already defined ! ("
                    + old + ")");
        }
    }

    public static IProcessContext getProcessContext(FacesContext facesContext) {

        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        ExternalContext externalContext = facesContext.getExternalContext();

        Map requestMap = externalContext.getRequestMap();
        return (IProcessContext) requestMap.get(EXTERNAL_CONTEXT_PROPERTY);

    }

    public final String getScriptType() {
        initializePageConfigurator();

        return scriptType;
    }

    public final Locale getDefaultLiteralLocale() {
        initializePageConfigurator();

        return defaultAttributesLocale;
    }

    private void initializePageConfigurator() {
        if (pageConfiguratorInitialized) {
            return;
        }

        pageConfiguratorInitialized = true;

        scriptType = PageConfiguration.getScriptType(getFacesContext());
        defaultAttributesLocale = PageConfiguration
                .getDefaultLiteralLocale(getFacesContext());

        if (LOG.isDebugEnabled()) {
            LOG.debug("Page configurator of view "
                    + facesContext.getViewRoot().getId() + ": scriptType="
                    + scriptType + " defaultAttributesLocale="
                    + defaultAttributesLocale);

        }
    }

    public RcfacesContext getRcfacesContext() {
        return rcfacesContext;
    }

}
