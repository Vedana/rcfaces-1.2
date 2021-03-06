/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.taglib;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.capability.IComponentLifeCycle;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class CameliaTag extends UIComponentELTag {
    private static final Log LOG = LogFactory.getLog(CameliaTag.class);

    private static final boolean debugEnabled = LOG.isDebugEnabled();

    private ValueExpression myBinding;

    protected static final boolean getBool(String value) {
        return Boolean.valueOf(value).booleanValue();
    }

    protected static final Boolean getBoolean(String value) {
        if (value == null) {
            return null;
        }

        return Boolean.valueOf(value);
    }

    protected static final Number getNumber(String value) {
        if (value == null || value.length() == 0) {
            return null;
        }

        if (value.indexOf('.') > 0) {
            Double dbl = Double.valueOf(value);

            return dbl;
        }

        long l = Long.parseLong(value);
        if (l >= Integer.MIN_VALUE && l <= Integer.MAX_VALUE) {
            return new Integer((int) l);
        }

        return new Long(l);
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

    @Override
    public void release() {
        myBinding = null;
        super.release();
    }

    @Override
    public String getRendererType() {
        return null;
    }

    @Override
    public int doAfterBody() throws JspException {
        try {
            return super.doAfterBody();

        } catch (RuntimeException ex) {
            LOG.error("Can not doAfterBody component '" + getId() + "'.", ex);

            throw ex;
        }
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            return super.doEndTag();

        } catch (RuntimeException ex) {
            LOG.error("Can not doEndTag component '" + getId() + "'.", ex);

            throw ex;
        }
    }

    @Override
    public void doInitBody() throws JspException {
        try {
            super.doInitBody();

        } catch (RuntimeException ex) {
            LOG.error("Can not doInitBody component '" + getId() + "'.", ex);

            throw ex;
        }

    }

    @Override
    public int doStartTag() throws JspException {
        try {
            return super.doStartTag();

        } catch (RuntimeException ex) {
            LOG.error("Can not doStartTag component '" + getId() + "'.", ex);

            throw ex;
        }
    }

    @Override
    protected void setProperties(UIComponent component) {

        if (myBinding == null) {
            if (component instanceof IComponentLifeCycle) {
                IComponentLifeCycle componentLifeCycle = (IComponentLifeCycle) component;

                componentLifeCycle.initializePhase(getFacesContext(), false);
            }

            if (debugEnabled) {
                LOG.debug("Create component for id '" + getId() + "' returns '"
                        + component + "'.");
            }
            super.setProperties(component);
            return;
        }

        Object bindingValue = myBinding.getValue(getELContext());

        if (component instanceof IComponentLifeCycle) {
            IComponentLifeCycle componentLifeCycle = (IComponentLifeCycle) component;

            componentLifeCycle.initializePhase(getFacesContext(),
                    bindingValue != null);
        }

        if (debugEnabled) {
            LOG.debug("Create component for id '" + getId() + "' returns '"
                    + component + "'.");
        }

        super.setProperties(component);
    }

    @Override
    public void setBinding(ValueExpression binding) throws JspException {
        this.myBinding = binding;

        super.setBinding(binding);
    }

    @Override
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

    @Override
    protected UIComponent createComponent(FacesContext facesContext,
            String newId) throws JspException {
        try {
            UIComponent component = super.createComponent(facesContext, newId);

            if (component instanceof IComponentLifeCycle) {
                IComponentLifeCycle componentLifeCycle = (IComponentLifeCycle) component;

                componentLifeCycle.settedPhase(facesContext);
            }

            return component;

        } catch (RuntimeException ex) {
            LOG.error("Can not create component '" + newId + "'.", ex);
            throw ex;
        }
    }

}
