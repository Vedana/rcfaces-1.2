/*
 * $Id: ForwardMethodExpression.java,v 1.1.12.1 2014/02/27 13:12:02 jbmeslin Exp $
 * 
 */
package org.rcfaces.core.internal.util;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.el.MethodNotFoundException;
import javax.el.PropertyNotFoundException;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author: jbmeslin $)
 * @version $Revision: 1.1.12.1 $ $Date: 2014/02/27 13:12:02 $
 */
public class ForwardMethodExpression extends MethodExpression implements
        StateHolder {

    

    private static final long serialVersionUID = 1L;

    private String forward = null;

    public ForwardMethodExpression() {
    }

    public ForwardMethodExpression(String forward) {
        this.forward = forward;
    }

    public MethodInfo getMethodInfo(ELContext context)
            throws NullPointerException, PropertyNotFoundException,
            MethodNotFoundException, ELException {
        return new MethodInfo("execute", String.class, new Class[0]);
    }

    public Object invoke(ELContext arg0, Object[] arg1) {
        return forward;
    }

    public String getExpressionString() {
        return forward;
    }

    public boolean isLiteralText() {
        return true;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((forward == null) ? 0 : forward.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ForwardMethodExpression other = (ForwardMethodExpression) obj;
        if (forward == null) {
            if (other.forward != null)
                return false;
        } else if (!forward.equals(other.forward))
            return false;
        return true;
    }

    // ----------------------------------------------------- StateHolder Methods

    public Object saveState(FacesContext context) {
        return forward;
    }

    public void restoreState(FacesContext context, Object state) {
        forward = (String) state;
    }

    private boolean transientFlag = false;

    public boolean isTransient() {
        return (this.transientFlag);
    }

    public void setTransient(boolean transientFlag) {
        this.transientFlag = transientFlag;
    }
}
