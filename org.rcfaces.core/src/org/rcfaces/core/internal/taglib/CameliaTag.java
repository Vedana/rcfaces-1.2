/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.taglib;

import java.io.IOException;
import java.io.Writer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.webapp.UIComponentBodyTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.capability.IAsyncRenderComponent;
import org.rcfaces.core.internal.renderkit.IAsyncRenderer;
import org.rcfaces.core.internal.service.AbstractAsyncRenderService;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class CameliaTag extends UIComponentBodyTag {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(CameliaTag.class);

    private static final String RESPONSE_WRITER_PATCH_PARAMETER = Constants
            .getPackagePrefix()
            + ".RESPONSE_WRITER_PATCH";

    private static final String HIDDEN_MODE_SERVER_PROPERTY = Constants
            .getPackagePrefix()
            + ".HIDDEN_MODE_SERVER";

    private AbstractAsyncRenderService asyncRenderServer = null;

    private IAsyncRenderer asyncRender = null;

    private boolean ignoreBody;

    private boolean setupWriter;

    private boolean suppressedChildren = false;

    public void release() {
        asyncRender = null;
        asyncRenderServer = null;
        ignoreBody = false;
        setupWriter = false;
        suppressedChildren = false;

        super.release();
    }

    protected boolean isSuppressed() {
        if (suppressedChildren) {
            return true;
        }

        return super.isSuppressed();
    }

    protected int getDoStartValue() {
        UIComponent component = getComponentInstance();

        if (component instanceof IAsyncRenderComponent) {
            FacesContext facesContext = getFacesContext();

            asyncRenderServer = AbstractAsyncRenderService
                    .getInstance(facesContext);
            if (asyncRenderServer != null
                    && asyncRenderServer.isAsyncRenderEnable()) {
                asyncRender = ((IAsyncRenderComponent) component)
                        .getAsyncRenderer(facesContext);

                if (asyncRender != null) {
                    int mode = asyncRenderServer.getAsyncRendererBufferMode(
                            facesContext, component);

                    if (mode == AbstractAsyncRenderService.BUFFER_ASYNC_RENDER_BUFFER_MODE) {
                        return EVAL_BODY_BUFFERED;
                    }
                    if (mode == AbstractAsyncRenderService.IGNORE_ASYNC_RENDER_BUFFER_MODE) {
                        ignoreBody = true;
                        suppressedChildren = true;

                        // Il faut tout de même créer l'arbre !
                        // On peut pas mettre SKIP
                        return EVAL_BODY_BUFFERED;
                    }

                    asyncRender = null;
                }
            }
        }

        if (component.isRendered() == false) {
            ignoreBody = true;
            // Il faut tout de même créer l'arbre !
            // On peut pas mettre SKIP
            return EVAL_BODY_BUFFERED;
        }

        return EVAL_BODY_INCLUDE;
    }

    protected void setupResponseWriter() {

        if (setupWriter) {
            if (getFacesContext().getResponseWriter() == null) {
                // C'est le fichier principal !
                setupWriter = false;
            }
        }

        super.setupResponseWriter();

        if (setupWriter == false) {
            return;
        }

        if ("true".equalsIgnoreCase(pageContext.getServletContext()
                .getInitParameter(RESPONSE_WRITER_PATCH_PARAMETER)) == false) {
            return;
        }

        installNewResponseWriter();
    }

    private void installNewResponseWriter() {
        FacesContext facesContext = getFacesContext();
        ResponseWriter responseWriter = facesContext.getResponseWriter();

        responseWriter = responseWriter.cloneWithWriter(new Writer() {
            public void close() {
             //   pageContext.getOut().close();
            }

            public void flush() {
            }

            public void write(char cbuf) throws IOException {
                pageContext.getOut().write(cbuf);
            }

            public void write(char[] cbuf, int off, int len) throws IOException {
                pageContext.getOut().write(cbuf, off, len);
            }

            public void write(int c) throws IOException {
                pageContext.getOut().write(c);
            }

            public void write(String str) throws IOException {
                pageContext.getOut().write(str);
            }

            public void write(String str, int off, int len) throws IOException {
                pageContext.getOut().write(str, off, len);
            }
        });

        facesContext.setResponseWriter(responseWriter);
    }

    public void setParent(Tag parent) {
        super.setParent(parent);

        if (parent == null) {
            setupWriter = true;
        }
    }

    public int doEndTag() throws JspException {
        suppressedChildren = false;

        if (asyncRender == null || ignoreBody) {
            return super.doEndTag();
        }

        bodyContent = getBodyContent();

        asyncRenderServer.setContent(getFacesContext(), getComponentInstance(),
                bodyContent);

        return super.doEndTag();
    }

    public final String getRendererType() {
        return null;
    }

    protected static final boolean getBool(String value) {
        return Boolean.valueOf(value).booleanValue();
    }

    protected static final Boolean getBoolean(String value) {
        if (value == null) {
            return null;
        }

        return Boolean.valueOf(value);
    }

    protected static final Integer getInteger(String value) {
        if (value == null) {
            return null;
        }

        return Integer.valueOf(value);
    }

    protected static final int getInt(String value) {
        return Integer.parseInt(value);
    }

    protected static final double getDouble(String value) {
        return Double.parseDouble(value);
    }

    public boolean enableLazyDownload() {
        return false;
    }
}
