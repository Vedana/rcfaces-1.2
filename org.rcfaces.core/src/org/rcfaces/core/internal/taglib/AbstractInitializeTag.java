/*
 * $Id$
 */
package org.rcfaces.core.internal.taglib;

import java.util.Locale;

import javax.faces.FacesException;
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
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
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

        this.externalContext = initializeExternalContext(facesContext);

        initializeTag();

        if (attributesLocale != null) {
            Locale locale = (Locale) LocaleConverter.SINGLETON.getAsObject(
                    null, null, attributesLocale);
            if (locale == null) {
                throw new FacesException("Invalid locale name '"
                        + attributesLocale
                        + "' specified by attribute 'attributesLocale'.");
            }

            ContextTools.setAttributesLocale(facesContext, locale);
        }

        return super.doStartTag();
    }

    protected abstract IProcessContext initializeExternalContext(
            FacesContext facesContext);

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

    protected final IProcessContext getProcessContext() {
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
