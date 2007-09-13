/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.util.ForwardMethodBinding;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
abstract class AbstractActionListener implements StateHolder,
        IServerActionListener {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractActionListener.class);

    private static final Class[] FACES_PARAMETERS = new Class[] { FacesEvent.class };

    private static final Class[] NO_PARAMETERS = new Class[0];

    private String expression;

    private transient boolean forwarNameMethodInitialized;

    private transient MethodBinding forwardNameMethodBinding;

    private transient boolean argsMethodBindingInitialized;

    private transient MethodBinding argsMethodBinding;

    private transient boolean noArgsMethodBindingInitialized;

    private transient MethodBinding noArgsMethodBinding;

    private transient boolean facesArgsMethodBindingInitialized;

    private transient MethodBinding facesArgsMethodBinding;

    private boolean transientValue;

    protected AbstractActionListener() {
        // Pour la deserialisation
    }

    protected AbstractActionListener(String expression) {
        this.expression = expression;
    }

    /*
     * public final MethodBinding getMethodBinding() { return methodBinding; }
     */
    protected void process(FacesEvent event) throws AbortProcessingException {

        FacesContext facesContext = getFacesContext();
        Application application = facesContext.getApplication();

        if (forwarNameMethodInitialized == false) {
            forwarNameMethodInitialized = true;

            // Format #[xxx] ???
            String forwardName = getForwardName(expression);
            if (forwardName != null) {
                // Oui !
                forwardNameMethodBinding = getForwardMethodBinding();
            }
        }

        if (forwardNameMethodBinding != null) {
            Exception th = tryMethodBinding(facesContext,
                    forwardNameMethodBinding, null, event);

            if (th == null) {
                return;
            }

            if (th instanceof RuntimeException) {
                throw (RuntimeException) th;
            }

            throw new FacesException(
                    "Exception when calling forward name method '" + expression
                            + "'.", th);
        }

        Object parameters[] = new Object[] { event };
        Exception firstThrowable = null;

        if (argsMethodBindingInitialized == false) {
            argsMethodBindingInitialized = true;
            argsMethodBinding = getArgumentsMethodBinding(application);
        }
        if (argsMethodBinding != null) {
            Exception th = tryMethodBinding(facesContext, argsMethodBinding,
                    parameters, event);
            if (th == null) {
                return;
            }
            firstThrowable = th;
        }

        if (facesArgsMethodBindingInitialized == false) {
            facesArgsMethodBindingInitialized = true;
            facesArgsMethodBinding = getFacesArgumentsMethodBinding(application);
        }
        if (facesArgsMethodBinding != null) {
            Exception th = tryMethodBinding(facesContext,
                    facesArgsMethodBinding, parameters, event);
            if (th == null) {
                return;
            }
            if (firstThrowable == null) {
                firstThrowable = th;
            }
        }

        if (noArgsMethodBindingInitialized == false) {
            noArgsMethodBindingInitialized = true;
            noArgsMethodBinding = getNoArgsMethodBinding(application);
        }
        if (noArgsMethodBinding != null) {
            Exception th = tryMethodBinding(facesContext, noArgsMethodBinding,
                    null, event);
            if (th == null) {
                return;
            }
            if (firstThrowable == null) {
                firstThrowable = th;
            }
        }

        LOG.error("Can not find method associated to expression '" + expression
                + "'.", firstThrowable);
    }

    private Exception tryMethodBinding(FacesContext facesContext,
            MethodBinding methodBinding, Object parameters[], FacesEvent event) {

        try {
            Object ret = methodBinding.invoke(facesContext, parameters);

            processReturn(facesContext, methodBinding, event, ret);

            return null;

        } catch (MethodNotFoundException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Method not found for expression '" + expression
                        + "'.", ex);
            }

            return ex;

        } catch (EvaluationException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Evaluation error for expression '" + expression
                        + "'.", ex);
            }

            Throwable cause = ex.getCause();

            if (cause instanceof AbortProcessingException) {
                throw (AbortProcessingException) cause;
            }

            if (cause instanceof MethodNotFoundException) {
                return (Exception) cause;
            }
            if (cause instanceof NoSuchMethodException) {
                return (Exception) cause;
            }

            throw ex;
        }
    }

    private MethodBinding getArgumentsMethodBinding(Application application) {
        return application.createMethodBinding(expression, FACES_PARAMETERS);
    }

    private MethodBinding getFacesArgumentsMethodBinding(Application application) {
        return application.createMethodBinding(expression,
                listParameterClasses());
    }

    private MethodBinding getNoArgsMethodBinding(Application application) {
        return application.createMethodBinding(expression, NO_PARAMETERS);
    }

    protected abstract Class[] listParameterClasses();

    private MethodBinding getForwardMethodBinding() {
        forwarNameMethodInitialized = true;

        noArgsMethodBinding = new ForwardMethodBinding(expression);
        return noArgsMethodBinding;
    }

    protected static final String getForwardName(String expression) {
        int len = expression.length() - 1;

        if (len < 3) {
            return null;
        }

        if (expression.startsWith("#[") == false) {
            return null;
        }

        if (expression.charAt(len) != ']') {
            return null;
        }

        return expression.substring(2, len);
    }

    protected final FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public boolean isTransient() {
        return transientValue;
    }

    public void setTransient(boolean newTransientValue) {
        this.transientValue = newTransientValue;
    }

    public final void restoreState(FacesContext context, Object state) {
        expression = (String) state;
        if (expression == null) {
            throw new NullPointerException("Expression is null !");
        }

        forwarNameMethodInitialized = false;
    }

    public final Object saveState(FacesContext context) {
        // Object objects[] = new Object[1];

        return expression;
    }

    protected void processReturn(FacesContext facesContext,
            MethodBinding binding, FacesEvent event, Object ret) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Call of method binding='" + binding + "' event='"
                    + event + "' returns " + ret);
        }

        if ((ret instanceof String) == false) {
            if (LOG.isInfoEnabled() && ret != null) {
                LOG.info("Return of method binding='" + binding + "' event='"
                        + event + "' is not a String !");
            }
            return;
        }

        String outcome = (String) ret;

        NavigationHandler navHandler = facesContext.getApplication()
                .getNavigationHandler();

        navHandler.handleNavigation(facesContext,
                binding.getExpressionString(), outcome);

        facesContext.renderResponse();
    }

    public boolean equals(Object object) {
        if (object == null
                || (object instanceof AbstractActionListener) == false) {
            return false;
        }

        AbstractActionListener s = (AbstractActionListener) object;

        if (expression != s.expression) {
            if (expression == null || expression.equals(s.expression) == false) {
                return false;
            }
        }

        return true;
    }

    public int hashCode() {
        if (expression == null) {
            return 0;
        }

        return expression.hashCode();
    }

}
