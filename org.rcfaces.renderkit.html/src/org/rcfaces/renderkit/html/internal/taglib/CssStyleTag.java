/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.4  2006/08/28 16:03:55  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.3  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.2  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.1  2005/09/16 09:54:41  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 */
package org.rcfaces.renderkit.html.internal.taglib;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.rcfaces.core.internal.config.AbstractURLRewritingProvider;
import org.rcfaces.core.provider.IURLRewritingProvider;
import org.rcfaces.renderkit.html.internal.HtmlRenderKit;
import org.rcfaces.renderkit.html.internal.IHtmlExternalContext;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class CssStyleTag extends BodyTagSupport {
    private static final String REVISION = "$Revision$";

    private String src;

    private IHtmlExternalContext htmlRenderContext;

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

        htmlRenderContext = HtmlRenderKit.getExternalContext(facesContext
                .getExternalContext());

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

                IURLRewritingProvider urlRewritingProvider = AbstractURLRewritingProvider
                        .getInstance(facesContext.getExternalContext());
                if (urlRewritingProvider != null) {
                    src = urlRewritingProvider.computeURL(facesContext, null,
                            IURLRewritingProvider.STYLE_URL_TYPE, null, src,
                            null, null);
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
                writer.println(">");

                bodyContent.writeOut(writer);

                writer.println("</STYLE>");

            } catch (IOException ex) {
                throw new JspException(ex);
            }
        }

        return super.doEndTag();
    }

}
