/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.util;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class WebXmlParser {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(WebXmlParser.class);

    private static final String WEB_XML_PATH = "/WEB-INF/web.xml";

    private static final ServletBean[] SERVLET_BEAN_EMPTY_ARRAY = new ServletBean[0];

    // private final ServletContext servletContext;

    private final Map servletByClassName = new HashMap();

    private final Map servletByName = new HashMap();

    public WebXmlParser(ServletContext servletContext) {
        // this.servletContext = servletContext;

        URL url;
        try {
            url = servletContext.getResource(WEB_XML_PATH);

        } catch (IOException ex) {
            LOG.error("Can not get resource '" + WEB_XML_PATH + "'.", ex);
            return;
        }

        InputStream inputStream;
        try {
            inputStream = url.openStream();

        } catch (IOException ex) {
            LOG.error("Can not open resource '" + WEB_XML_PATH + "'.", ex);
            return;
        }

        try {
            parse(inputStream, url.toString());

        } finally {
            try {
                inputStream.close();

            } catch (IOException e) {
                LOG.error(e);
            }
        }
    }

    public ServletBean[] getServletsByClassName(String className) {
        List l = (List) servletByClassName.get(className);

        if (l == null) {
            return SERVLET_BEAN_EMPTY_ARRAY;
        }

        return (ServletBean[]) l.toArray(new ServletBean[l.size()]);
    }

    public ServletBean getServletByName(String name) {
        return (ServletBean) servletByName.get(name);
    }

    private void parse(InputStream inputStream, String resourceName) {
        Digester digester = new Digester();
        digester.setUseContextClassLoader(true);

        digester.setEntityResolver(new EntityResolver() {
            private static final String REVISION = "$Revision$";

            public InputSource resolveEntity(String string, String string1) {
                return new InputSource(new CharArrayReader(new char[0]));
            }

        });

        digester.addRule("web-app/servlet", new Rule() {
            private static final String REVISION = "$Revision$";

            public void begin(String namespace, String name,
                    Attributes attributes) throws Exception {

                ServletBean servletBean = new ServletBean();

                super.digester.push(servletBean);
            }

            public void end(String namespace, String name) throws Exception {
                ServletBean servletBean = (ServletBean) super.digester.pop();

                servletByName.put(servletBean.getName(), servletBean);

                List l = (List) servletByClassName.get(servletBean.className);
                if (l == null) {
                    l = new ArrayList(4);
                    servletByClassName.put(servletBean.className, l);
                }

                l.add(servletBean);
            }
        });
        digester.addBeanPropertySetter("web-app/servlet/servlet-name", "name");
        digester.addBeanPropertySetter("web-app/servlet/servlet-class",
                "className");
        digester.addRule("web-app/servlet-mapping", new Rule() {
            private static final String REVISION = "$Revision$";

            public void begin(String namespace, String name,
                    Attributes attributes) throws Exception {

                ServletMapping servletMapping = new ServletMapping();

                super.digester.push(servletMapping);
            }

            public void end(String namespace, String name) throws Exception {
                ServletMapping servletMapping = (ServletMapping) super.digester
                        .pop();

                ServletBean servletBean = getServletByName(servletMapping.servletName);
                if (servletBean == null) {
                    LOG.error("Can not find servlet '"
                            + servletMapping.servletName + "'.");
                    return;
                }

                servletBean.urlPatterns.add(servletMapping.urlPattern);
            }
        });
        digester.addBeanPropertySetter("web-app/servlet-mapping/servlet-name",
                "servletName");
        digester.addBeanPropertySetter("web-app/servlet-mapping/url-pattern",
                "urlPattern");

        try {
            digester.parse(inputStream);

        } catch (Exception e) {
            LOG.error("Can not parse '" + resourceName + "'.", e);

        } finally {
            try {
                inputStream.close();

            } catch (IOException e) {
                LOG.error("Can not close '" + resourceName + "'.", e);
            }
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class ServletBean {
        private static final String REVISION = "$Revision$";

        private String name;

        private String className;

        private List urlPatterns = new ArrayList();

        public String getClassName() {
            return className;
        }

        public String[] listUrlPatterns() {
            return (String[]) urlPatterns
                    .toArray(new String[urlPatterns.size()]);
        }

        public String getName() {
            return name;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class ServletMapping {
        private static final String REVISION = "$Revision$";

        private String servletName;

        private String urlPattern;

        public String getServletName() {
            return servletName;
        }

        public void setServletName(String servletName) {
            this.servletName = servletName;
        }

        public String getUrlPattern() {
            return urlPattern;
        }

        public void setUrlPattern(String urlPattern) {
            this.urlPattern = urlPattern;
        }

    }
}
