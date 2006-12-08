/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.resource;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Globals;
import org.rcfaces.core.internal.util.ServletTools;
import org.rcfaces.renderkit.html.internal.css.StylesheetsServlet;
import org.rcfaces.renderkit.html.internal.javascript.JavaScriptRepositoryServlet;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ResourcesServlet extends HttpServlet {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 6219013927491730293L;

    private static final Log LOG = LogFactory.getLog(ResourcesServlet.class);

    private static final String DEFAULT_RESOURCES_URI = "/rcfaces";

    private Servlet javascriptServlet;

    private Servlet styleSheetServlet;

    private String resourcesURI;

    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String url = (String) request
                .getAttribute(Globals.INCLUDE_REQUEST_URI_ATTR);
        if (url == null) {
            url = request.getRequestURI();
        }

        if (url.endsWith(".js")) {
            javascriptServlet.service(request, response);
            return;
        }

        styleSheetServlet.service(request, response);
    }

    public void destroy() {
        javascriptServlet.destroy();
        javascriptServlet = null;

        styleSheetServlet.destroy();
        styleSheetServlet = null;
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        resourcesURI = ServletTools.computeResourceURI(config
                .getServletContext(), DEFAULT_RESOURCES_URI, getClass());

        javascriptServlet = createJavaScriptServlet();

        javascriptServlet.init(new ResourcesServletConfig(config,
                "Javascript resources"));

        styleSheetServlet = createStyleSheetServlet();

        styleSheetServlet.init(new ResourcesServletConfig(config,
                "Stylesheet resources"));
    }

    private Servlet createStyleSheetServlet() {
        return new StylesheetsServlet(resourcesURI);
    }

    protected Servlet createJavaScriptServlet() {
        return new JavaScriptRepositoryServlet(resourcesURI);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class ResourcesServletConfig implements ServletConfig {
        private static final String REVISION = "$Revision$";

        private final ServletConfig config;

        private final String servletName;

        private ResourcesServletConfig(ServletConfig servletConfig,
                String servletName) {
            this.config = servletConfig;
            this.servletName = servletName;
        }

        public String getInitParameter(String arg0) {
            return config.getInitParameter(arg0);
        }

        public Enumeration getInitParameterNames() {
            return config.getInitParameterNames();
        }

        public ServletContext getServletContext() {
            return config.getServletContext();
        }

        public String getServletName() {
            return config.getServletName() + ": " + servletName;
        }
    }
}
