/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/05 08:57:21  oeuillot
 * Dernières corrections pour la migration Rcfaces
 *
 * Revision 1.1  2006/08/29 16:13:12  oeuillot
 * Renommage  en rcfaces
 *
 */
package org.rcfaces.core.internal.taglib;

import java.util.Locale;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.converter.LocaleConverter;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.tools.ContextTools;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class AbstractInitializeTag extends TagSupport {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractInitializeTag.class);

    private static final String SCRIPT_TYPE_PROPERTY = "org.rcfaces.core.internal.taglib.SCRIPT_TYPE";

    private String attributesLocale;

    private IProcessContext externalContext;

    public int doStartTag() throws JspException {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null) {
            throw new JspException("FacesContext is not initialized !");
        }

        ExternalContext externalContext = facesContext.getExternalContext();

        this.externalContext = initializeExternalContext(externalContext);

        initializeTag();

        if (attributesLocale != null) {
            Locale locale = (Locale) LocaleConverter.SINGLETON.getAsObject(
                    null, null, attributesLocale);
            if (locale == null) {
                throw new FacesException("Invalid locale name '"
                        + attributesLocale
                        + "' specified by attribute 'attributesLocale'.");
            }

            ContextTools.setAttributesLocale(facesContext.getExternalContext(),
                    locale);
        }

        return super.doStartTag();
    }

    protected abstract IProcessContext initializeExternalContext(
            ExternalContext externalContext);

    protected void initializeTag() {
    }

    public void release() {
        externalContext = null;

        attributesLocale = null;

        super.release();
    }

    public String getAttributesLocale() {
        return attributesLocale;
    }

    public void setAttributesLocale(String attributesLocale) {
        this.attributesLocale = attributesLocale;
    }

    protected final IProcessContext getExternalContext() {
        return externalContext;
    }

    protected void setScriptType(String scriptType) {
        pageContext.getRequest().setAttribute(SCRIPT_TYPE_PROPERTY, scriptType);
    }

    static final String getScriptType(PageContext pageContext) {
        String scriptType = (String) pageContext.getRequest().getAttribute(
                SCRIPT_TYPE_PROPERTY);

        if (scriptType != null) {
            return scriptType;
        }

        throw new FacesException(
                "No script type defined ! (You may use an init tag to resolve this problem)");

    }
}
