/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.taglib;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentType;
import org.rcfaces.renderkit.html.internal.HtmlProcessContextImpl;
import org.rcfaces.renderkit.html.internal.IHtmlProcessContext;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CssStyleTag extends BodyTagSupport implements Tag {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -7885621747032242759L;

    private String src;

    private boolean componentRules;

    private IHtmlProcessContext htmlRenderContext;

    public final String getSrc() {
        return src;
    }

    public final void setSrc(String src) {
        this.src = src;
    }

    public void release() {
        super.release();

        src = null;
        htmlRenderContext = null;
    }

    public int doStartTag() throws JspException {

        JspWriter writer = pageContext.getOut();

        FacesContext facesContext = FacesContext.getCurrentInstance();

        htmlRenderContext = HtmlProcessContextImpl
                .getHtmlProcessContext(facesContext);

        boolean useMetaContentStyleType = htmlRenderContext
                .useMetaContentStyleType()
                && InitializeTag.isMetaDataInitialized(pageContext);

        try {
            if (src != null) {
                writer.write("<LINK rel=\"stylesheet\"");
                if (useMetaContentStyleType == false) {
                    writer.write(" type=\"");
                    writer.write(IHtmlRenderContext.CSS_TYPE);
                    writer.write('\"');
                }

                IContentAccessor contentAccessor = ContentAccessorFactory
                        .createFromWebResource(facesContext, src,
                                IContentType.STYLE);

                src = contentAccessor.resolveURL(facesContext, null, null);

                if (componentRules) {
                    writer.write(" v:rules=\"true\"");
                }

                writer.write(" href=\"");
                writer.write(src);
                writer.write("\" />");

                return SKIP_BODY;
            }

            return EVAL_BODY_INCLUDE;

        } catch (IOException ex) {
            throw new JspException(ex);
        }
    }

    public int doEndTag() throws JspException {
        JspWriter writer = pageContext.getOut();

        BodyContent bodyContent = getBodyContent();
        if (bodyContent != null && src == null) {
            try {

                boolean useMetaContentStyleType = htmlRenderContext
                        .useMetaContentStyleType()
                        && InitializeTag.isMetaDataInitialized(pageContext);

                writer.print("<STYLE");
                if (useMetaContentStyleType == false) {
                    writer.write(" type=\"");
                    writer.write(IHtmlRenderContext.CSS_TYPE);
                    writer.write('\"');
                }

                if (componentRules) {
                    writer.write(" v:rules=\"true\"");
                }
                writer.println(">");

                bodyContent.writeOut(writer);

                writer.println("</STYLE>");

            } catch (IOException ex) {
                throw new JspException(ex);
            }
        }

        return super.doEndTag();
    }

    public final boolean isComponentRules() {
        return componentRules;
    }

    public final void setComponentRules(boolean componentRules) {
        this.componentRules = componentRules;
    }

}
