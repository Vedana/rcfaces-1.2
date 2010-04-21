/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.taglib;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.capability.IRCFacesComponent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class CameliaTag extends UIComponentELTag {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(CameliaTag.class);

    private static final boolean debugEnabled = LOG.isDebugEnabled();

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

    public String getRendererType() {
        return null;
    }

    public int doAfterBody() throws JspException {
        try {
            return super.doAfterBody();

        } catch (RuntimeException ex) {
            LOG.error("Can not doAfterBody component '" + getId() + "'.", ex);

            throw ex;
        }
    }

    public int doEndTag() throws JspException {
        try {
            return super.doEndTag();

        } catch (RuntimeException ex) {
            LOG.error("Can not doEndTag component '" + getId() + "'.", ex);

            throw ex;
        }
    }

    public void doInitBody() throws JspException {
        try {
            super.doInitBody();

        } catch (RuntimeException ex) {
            LOG.error("Can not doInitBody component '" + getId() + "'.", ex);

            throw ex;
        }

    }

    public int doStartTag() throws JspException {
        try {
            return super.doStartTag();

        } catch (RuntimeException ex) {
            LOG.error("Can not doStartTag component '" + getId() + "'.", ex);

            throw ex;
        }
    }

    protected void setProperties(UIComponent component) {

        if (hasBinding()) {
            ((IRCFacesComponent) component).clearListeners();
        }

        super.setProperties(component);
    }

    protected UIComponent createComponent(FacesContext facesContext,
            String newId) throws JspException {
        UIComponent component = super.createComponent(facesContext, newId);

        if (debugEnabled) {
            LOG.debug("Create component for id '" + newId + "' returns '"
                    + component + "'.");
        }

        return component;
    }

    protected UIComponent findComponent(FacesContext context)
            throws JspException {

        if (debugEnabled) {
            LOG.debug("Find component '" + getId() + "' ... (current="
                    + getComponentInstance() + ")");
        }

        UIComponent component = super.findComponent(context);

        if (debugEnabled) {
            LOG.debug("Find component '" + getId() + "' returns '" + component
                    + "'");
        }

        return component;
    }
}
