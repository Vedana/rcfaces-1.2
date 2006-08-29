/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.10  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.9  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.8  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.7  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.6  2005/02/18 14:46:08  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.5  2005/01/04 13:02:51  oeuillot
 * Amelioration des tables. (Ajout des tris, scrollbars ...)
 *
 * Revision 1.4  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.3  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.2  2004/08/16 10:20:00  oeuillot
 * Ajout de immediate
 *
 * Revision 1.1  2004/08/16 08:00:08  oeuillot
 * Gestion des listeners
 *
 */
package org.rcfaces.core.internal.listener;

import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.util.ForwardMethodBinding;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
abstract class AbstractActionListener implements StateHolder,
        IServerActionListener {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractActionListener.class);

    private static final Class[] FACES_PARAMETERS = new Class[] { FacesEvent.class };

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

            String forwardName = getForwardName(expression);
            if (forwardName != null) {
                forwardNameMethodBinding = getNoArgsMethodBinding(application);
            }
        }

        if (forwardNameMethodBinding != null) {
            MethodNotFoundException th = tryMethodBinding(facesContext,
                    forwardNameMethodBinding, null, event);

            if (th == null) {
                return;
            }

            throw th;
        }

        Object parameters[] = new Object[] { event };
        MethodNotFoundException firstThrowable = null;

        if (argsMethodBindingInitialized == false) {
            argsMethodBindingInitialized = true;
            argsMethodBinding = getArgumentsMethodBinding(application);
        }
        if (argsMethodBinding != null) {
            MethodNotFoundException th = tryMethodBinding(facesContext,
                    argsMethodBinding, parameters, event);
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
            MethodNotFoundException th = tryMethodBinding(facesContext,
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
            MethodNotFoundException th = tryMethodBinding(facesContext,
                    noArgsMethodBinding, null, event);
            if (th == null) {
                return;
            }
            if (firstThrowable == null) {
                firstThrowable = th;
            }
        }
    }

    private MethodNotFoundException tryMethodBinding(FacesContext facesContext,
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
        return application.createMethodBinding(expression, null);
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

    /*
     * (non-Javadoc)
     * 
     * 
     * ee javax.faces.component.StateHolder#isTransient()
     */
    public boolean isTransient() {
        return transientValue;
    }

    /*
     * (non-Javadoc)
     * 
     * 
     * ee javax.faces.component.StateHolder#setTransient(boolean)
     */
    public void setTransient(boolean newTransientValue) {
        this.transientValue = newTransientValue;
    }

    /*
     * (non-Javadoc)
     * 
     * 
     * ee
     * javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext,
     * java.lang.Object)
     */
    public final void restoreState(FacesContext context, Object state) {
        Object objects[] = (Object[]) state;

        expression = (String) objects[0];
        if (expression == null) {
            throw new NullPointerException("Expression is null !");
        }

        forwarNameMethodInitialized = false;
    }

    /*
     * (non-Javadoc)
     * 
     * 
     * ee
     * javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
     */
    public final Object saveState(FacesContext context) {
        Object objects[] = new Object[1];

        objects[0] = expression;
        if (expression == null) {
            throw new NullPointerException("Expression is NULL");
        }

        return objects;
    }

    protected void processReturn(FacesContext facesContext,
            MethodBinding binding, FacesEvent event, Object ret) {
        if ((ret instanceof String) == false) {
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
